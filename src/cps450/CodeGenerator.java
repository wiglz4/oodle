package cps450;

import instructions.CommentInstruction;
import instructions.Instruction;
import instructions.InstructionCommand;
import instructions.InstructionSet;
import instructions.LabelInstruction;
import instructions.StabsInstruction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import parameters.IntegerLiteral;
import parameters.Label;
import parameters.Register;
import cps450.oodle.analysis.DepthFirstAdapter;
import cps450.oodle.node.AAddExpression;
import cps450.oodle.node.AAndExpression;
import cps450.oodle.node.AAssstmtStatement;
import cps450.oodle.node.ACallxprExpression;
import cps450.oodle.node.AClassDef;
import cps450.oodle.node.AClstmtStatement;
import cps450.oodle.node.ADivExpression;
import cps450.oodle.node.AEqExpression;
import cps450.oodle.node.AFlExpression;
import cps450.oodle.node.AGtExpression;
import cps450.oodle.node.AGteExpression;
import cps450.oodle.node.AIdExpression;
import cps450.oodle.node.AIfstmtStatement;
import cps450.oodle.node.AIntlitExpression;
import cps450.oodle.node.ALpstmtStatement;
import cps450.oodle.node.AMethodDecl;
import cps450.oodle.node.AMulExpression;
import cps450.oodle.node.ANegExpression;
import cps450.oodle.node.ANotExpression;
import cps450.oodle.node.AOrExpression;
import cps450.oodle.node.AParenExpression;
import cps450.oodle.node.APosExpression;
import cps450.oodle.node.ASubExpression;
import cps450.oodle.node.ATrExpression;
import cps450.oodle.node.AVarDecl;
import cps450.oodle.node.Node;
import declarations.ClassDeclaration;
import declarations.MethodDeclaration;
import declarations.VariableDeclaration;

public class CodeGenerator extends DepthFirstAdapter {
	
	private int counter = 0;
	
	private HashMap<Node, HashMap<String, Object>> attributeGrammarMap = new HashMap<Node, HashMap<String, Object>>();
	private ArrayList<InstructionSet> classAssembly = new ArrayList<InstructionSet>();
	private ClassDeclaration currentClass;
	private MethodDeclaration currentMethod;
	private String startMethod;
	
	/* initNode
	 * Arguments:
	 *   node : Node - the node to init
	 * Purpose: helper to prevent null point exceptions
	 */
	private void initNode(Node node) {
		if(attributeGrammarMap.get(node) == null) {
			attributeGrammarMap.put(node, new HashMap<String, Object>());
		}
	}
	
	/* writeAssembly
	 * Arguments:
	 *   
	 * Purpose: generates a temporary assembly file containing the assembly code for the classes
	 */
	public void writeAssembly() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("temp.s"));
			writer.write("STDOUT = 1\n");
			writer.write("STDIN = 0\n");
			for(InstructionSet assm : classAssembly) {
				writer.write(assm.toAssembly());
			}
			writer.write(".text\n");
			writer.write(".global main\n");
			writer.write("main:\n");
			writer.write("\tcall " + startMethod + "\n");
			writer.write("\tpushl $0\n");
			writer.write("\tcall exit\n");
			writer.close();
		} catch (IOException e) {
			System.out.println("could not generate assembly due to " + e.getMessage());
		}	
	}
	
	@Override
	public void inAClassDef(AClassDef node) {
		super.inAClassDef(node);
		try {
			currentClass = (ClassDeclaration)Application.getSymbolTable().lookup(node.getFirst().getText(), 0).getDeclaration();
		} catch (Exception e) {
			//TODO: print error
		}
	}

	@Override
	public void inAMethodDecl(AMethodDecl node) {
		super.inAMethodDecl(node);
		try {
			currentMethod = currentClass.lookupMethod(node.getFirstId().getText());
			if (node.getFirstId().getText().equals("start")) {
				startMethod = currentMethod.getAssemblyName();				
			}
		} catch (Exception e) {
			//TODO: print error
		}
	}
	
	@Override
	public void outAClassDef(AClassDef node) {
		super.outAClassDef(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstruction(new StabsInstruction("data"));
		for(int i = 0; i < node.getVarDecl().size(); ++i) {
			instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getVarDecl().get(i)).get("code"));
		}
		for(int i = 0; i < node.getMethodDecl().size(); ++i) {
			instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getMethodDecl().get(i)).get("code"));
		}
		classAssembly.add(instructions);
	}

	
	/*@Override
	public void outAExtends(AExtends node) {
		// TODO (during phase 5)
		super.outAExtends(node);
	}*/

	@Override
	public void outAVarDecl(AVarDecl node) {
		super.outAVarDecl(node);
		initNode(node);
		try {
			initNode(node);
			InstructionSet instructions = new InstructionSet();
			VariableDeclaration var = null;
			if(currentMethod == null) {
				var = currentClass.lookupVariable(node.getIdentifier().getText());
			} else {
				var = currentMethod.lookupVariable(node.getIdentifier().getText());
			}
			instructions.appendInstruction(new StabsInstruction("comm", var.getAssemblyName() + ", 4, 4"));
			attributeGrammarMap.get(node).put("code", instructions);
		} catch (Exception e) {
			// TODO print error
		}
	}
	
	@Override
	public void outAMethodDecl(AMethodDecl node) {
		super.outAMethodDecl(node);
		initNode(node);
		
		if(currentMethod.getName().equals("main")) {
			startMethod = currentMethod.getAssemblyName();
		}
		
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstruction(new StabsInstruction("text"));
		instructions.appendInstruction(new LabelInstruction(currentMethod.getAssemblyName()));
		instructions.appendInstruction(new StabsInstruction("data"));
		for(int i = 0; i < node.getVarDecl().size(); ++i) {
			instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getVarDecl().get(i)).get("code"));
		}
		instructions.appendInstruction(new StabsInstruction("text"));
		for(int i = 0; i < node.getStatement().size(); ++i) {
			instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getStatement().get(i)).get("code"));
		}
		instructions.appendInstruction(new InstructionCommand(Instruction.ret));
		attributeGrammarMap.get(node).put("code", instructions);
	}
	
	
	@Override
	public void outAIfstmtStatement(AIfstmtStatement node) {
		counter++;
		super.outAIfstmtStatement(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpression()).get("code"));
		instructions.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.cmp, new IntegerLiteral(1), Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.je, new Label("IFlabel" + counter)));
		instructions.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("IFmidLabel" + counter)));
		instructions.appendInstruction(new LabelInstruction("IFlabel" + counter));
		for(int i = 0; i < node.getTrue().size(); ++i) {
			instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getTrue().get(i)).get("code"));
		}
		instructions.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("IFendLabel" + counter)));
		instructions.appendInstruction(new LabelInstruction("IFmidLabel" + counter));
		for(int i = 0; i < node.getFalse().size(); ++i) {
			instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getFalse().get(i)).get("code"));
		}
		instructions.appendInstruction(new LabelInstruction("IFendLabel" + counter));
		attributeGrammarMap.get(node).put("code", instructions);
	}

	@Override
	public void outALpstmtStatement(ALpstmtStatement node) {
		counter++;
		super.outALpstmtStatement(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstruction(new LabelInstruction("LPbeginlabel" + counter));
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpression()).get("code"));
		instructions.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.cmp, new IntegerLiteral(1), Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.je, new Label("LPlabel" + counter)));
		instructions.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("LPendLabel" + counter)));
		instructions.appendInstruction(new LabelInstruction("LPlabel" + counter));
		for(int i = 0; i < node.getStatement().size(); ++i) {
			instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getStatement().get(i)).get("code"));
		}
		instructions.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("LPbeginlabel" + counter)));
		instructions.appendInstruction(new LabelInstruction("LPendLabel" + counter));
		attributeGrammarMap.get(node).put("code", instructions);
	}

	/*@Override
	public void outAReturnStatement(AReturnStatement node) {
		// TODO (during phase 5)
		super.outAReturnStatement(node);
	}*/

	@Override
	public void outAAssstmtStatement(AAssstmtStatement node) {
		super.outAAssstmtStatement(node);
		initNode(node);
		try {
			VariableDeclaration var = currentMethod.lookupVariable(node.getIdentifier().getText());
			InstructionSet instructions = new InstructionSet();
			instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getLast()).get("code"));
			instructions.appendInstruction(new InstructionCommand(Instruction.pop, new Label(var.getAssemblyName())));
			attributeGrammarMap.get(node).put("code", instructions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*@Override
	public void outAArrayAssignmentStatement(AArrayAssignmentStatement node) {
		// TODO (during phase 5)
		super.outAArrayAssignmentStatement(node);
	}*/
	
	@Override
	public void outAOrExpression(AOrExpression node) {
		super.outAOrExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getLhs()).get("code"));
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getRhs()).get("code"));
		instructions.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
		instructions.appendInstruction(new InstructionCommand(Instruction.or, Register.ebx, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		attributeGrammarMap.get(node).put("code", instructions);
	}

	@Override
	public void outAAndExpression(AAndExpression node) {
		super.outAAndExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getLhs()).get("code"));
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getRhs()).get("code"));
		instructions.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
		instructions.appendInstruction(new InstructionCommand(Instruction.and, Register.ebx, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		attributeGrammarMap.get(node).put("code", instructions);
	}

	@Override
	public void outAGtExpression(AGtExpression node) {
		counter++;
		super.outAGtExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getRhs()).get("code"));
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getLhs()).get("code"));
		instructions.appendInstruction(new CommentInstruction("outAGtExpression"));
		instructions.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
		instructions.appendInstruction(new InstructionCommand(Instruction.cmp, Register.ebx, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.jg, new Label("GTlabel" + counter)));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(0)));
		instructions.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("GTendLabel" + counter)));
		instructions.appendInstruction(new LabelInstruction("GTlabel" + counter));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(1)));
		instructions.appendInstruction(new LabelInstruction("GTendLabel" + counter));
		attributeGrammarMap.get(node).put("code", instructions);
	}

	@Override
	public void outAGteExpression(AGteExpression node) {
		counter++;
		super.outAGteExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getRhs()).get("code"));
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getLhs()).get("code"));
		instructions.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
		instructions.appendInstruction(new InstructionCommand(Instruction.cmp, Register.ebx, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.jge, new Label("GTElabel" + counter)));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(0)));
		instructions.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("GTEendLabel" + counter)));
		instructions.appendInstruction(new LabelInstruction("GTElabel" + counter));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(1)));
		instructions.appendInstruction(new LabelInstruction("GTEendLabel" + counter));
		attributeGrammarMap.get(node).put("code", instructions);
	}

	@Override
	public void outAEqExpression(AEqExpression node) {
		super.outAEqExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getLhs()).get("code"));
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getRhs()).get("code"));
		instructions.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
		instructions.appendInstruction(new InstructionCommand(Instruction.cmp, Register.ebx, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.je, new Label("EQlabel" + counter)));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(0)));
		instructions.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("EQendLabel" + counter)));
		instructions.appendInstruction(new LabelInstruction("EQlabel" + counter));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(1)));
		instructions.appendInstruction(new LabelInstruction("EQendLabel" + counter));
		counter++;
		attributeGrammarMap.get(node).put("code", instructions);
	}
	
	@Override
	public void outAAddExpression(AAddExpression node) {
		super.outAAddExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getLhs()).get("code"));
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getRhs()).get("code"));
		instructions.appendInstruction(new InstructionCommand(Instruction.call, new Label("add")));
		instructions.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(8), Register.esp));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		attributeGrammarMap.get(node).put("code", instructions);
	}

	@Override
	public void outASubExpression(ASubExpression node) {
		super.outASubExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getLhs()).get("code"));
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getRhs()).get("code"));
		instructions.appendInstruction(new InstructionCommand(Instruction.call, new Label("subtract")));
		instructions.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(8), Register.esp));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		attributeGrammarMap.get(node).put("code", instructions);
	}

	//OVERRIDE THESE IN STDLIBO
	@Override
	public void outADivExpression(ADivExpression node) {
		super.outADivExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getLhs()).get("code"));
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getRhs()).get("code"));
		instructions.appendInstruction(new InstructionCommand(Instruction.call, new Label("divide")));
		instructions.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(8), Register.esp));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		attributeGrammarMap.get(node).put("code", instructions);
	}

	@Override
	public void outAMulExpression(AMulExpression node) {
		super.outAMulExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getLhs()).get("code"));
		instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getRhs()).get("code"));
		instructions.appendInstruction(new InstructionCommand(Instruction.call, new Label("multiply")));
		instructions.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(8), Register.esp));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		attributeGrammarMap.get(node).put("code", instructions);
	}

	@Override
	public void outANotExpression(ANotExpression node) {
		super.outANotExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions = (InstructionSet) attributeGrammarMap.get(node.getExpression()).get("code");
		instructions.appendInstruction(new InstructionCommand(Instruction.call, new Label("not")));
		instructions.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4), Register.esp));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		attributeGrammarMap.get(node).put("code", instructions);
	}

	@Override
	public void outANegExpression(ANegExpression node) {
		super.outANegExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions = (InstructionSet) attributeGrammarMap.get(node.getExpression()).get("code");
		instructions.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.neg, Register.eax));
		instructions.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		attributeGrammarMap.get(node).put("code", instructions);
	}
	
	@Override
	public void outAPosExpression(APosExpression node) {
		super.outAPosExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions = (InstructionSet) attributeGrammarMap.get(node.getExpression()).get("code");
		attributeGrammarMap.get(node).put("code", instructions);
	}
	
	@Override
	public void outACallxprExpression(ACallxprExpression node) {
		super.outACallxprExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		for(int i = node.getRhs().size() - 1; i >= 0; --i) {
			instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getRhs().get(i)).get("code"));
		}
		InstructionSet dotAssembly = new InstructionSet();
		if(node.getLhs() != null) {
			dotAssembly = (InstructionSet)attributeGrammarMap.get(node.getLhs()).get("code");
		}
		if(dotAssembly.getCommands().size() == 0 && (node.getIdentifier().getText().equals("readint") || node.getIdentifier().getText().equals("writeint"))) {
			if(node.getIdentifier().getText().equals("readint")) {
				instructions.appendInstruction(new InstructionCommand(Instruction.call, new Label("readint")));
				instructions.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
			} else {
				instructions.appendInstruction(new InstructionCommand(Instruction.call, new Label("writeint")));
				instructions.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(Integer.parseInt("4")), Register.esp));
			}
		} else {
			try {
				MethodDeclaration method = currentClass.lookupMethod(node.getIdentifier().getText());
				instructions.appendInstruction(new InstructionCommand(Instruction.call, new Label(method.getAssemblyName())));
				instructions.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(node.getRhs().size() * 4), Register.esp));
				instructions.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
			} catch (Exception ex) { ; }
		}
		attributeGrammarMap.get(node).put("code", instructions);
	}
	
	
	
	@Override
	public void outAClstmtStatement(AClstmtStatement node) {
		super.outAClstmtStatement(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		for(int i = node.getRhs().size() - 1; i >= 0; --i) {
			instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getRhs().get(i)).get("code"));
		}
		InstructionSet dotAssembly = new InstructionSet();
		if(node.getLhs() != null) {
			dotAssembly = (InstructionSet)attributeGrammarMap.get(node.getLhs()).get("code");
		}
		if(dotAssembly.getCommands().size() == 0 && (node.getIdentifier().getText().equals("readint") || node.getIdentifier().getText().equals("writeint"))) {
			if(node.getIdentifier().getText().equals("readint")) {
				instructions.appendInstruction(new InstructionCommand(Instruction.call, new Label("readint")));
			} else {
				instructions.appendInstruction(new InstructionCommand(Instruction.call, new Label("writeint")));
				instructions.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(Integer.parseInt("4")), Register.esp));
			}
		} else {
			try {
				MethodDeclaration method = currentClass.lookupMethod(node.getIdentifier().getText());
				instructions.appendInstruction(new InstructionCommand(Instruction.call, new Label(method.getAssemblyName())));
				instructions.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(node.getRhs().size() * 4), Register.esp));
			} catch (Exception ex) { ; }
		}
		attributeGrammarMap.get(node).put("code", instructions);
	}

	@Override
	public void outAIdExpression(AIdExpression node) {
		super.outAIdExpression(node);
		try {
			initNode(node);
			InstructionSet instructions = new InstructionSet();
			if(node.getIdentifier().getText().equals("out") || node.getIdentifier().getText().equals("in")) {
				//no-op
			} else {
				VariableDeclaration var;
				if(currentMethod == null) {
					var = currentClass.lookupVariable(node.getIdentifier().getText());
				} else {
					var = currentMethod.lookupVariable(node.getIdentifier().getText());
				}
				instructions.appendInstruction(new InstructionCommand(Instruction.push, new Label(var.getAssemblyName())));
			}
			attributeGrammarMap.get(node).put("code", instructions);
		} catch (Exception e) {
			// TODO print error
		}
	}
	
	
	@Override
	public void outAIntlitExpression(AIntlitExpression node) {
		super.outAIntlitExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(Integer.parseInt(node.getIntegerLiteral().getText()))));
		attributeGrammarMap.get(node).put("code", instructions);
	}

	/*@Override
	public void outAStrlitExpression(AStrlitExpression node) {
		// TODO (during phase 5)
		super.outAStrlitExpression(node);
	}*/

	@Override
	public void outATrExpression(ATrExpression node) {
		super.outATrExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(Integer.parseInt("1"))));
		attributeGrammarMap.get(node).put("code", instructions);
	}

	@Override
	public void outAFlExpression(AFlExpression node) {
		super.outAFlExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(Integer.parseInt("0"))));
		attributeGrammarMap.get(node).put("code", instructions);
	}
	
	/*
	@Override
	public void outANewtypeExpression(ANewtypeExpression node) {
		super.outANewtypeExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		// TODO write assembly
		attributeGrammarMap.get(node).put("code", instructions);
	}
	 */
	@Override
	public void outAParenExpression(AParenExpression node) {
		super.outAParenExpression(node);
		initNode(node);
		InstructionSet instructions = new InstructionSet();
		instructions = (InstructionSet) attributeGrammarMap.get(node.getExpression()).get("code");
		attributeGrammarMap.get(node).put("code", instructions);
	}
	
}
