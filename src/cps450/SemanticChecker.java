package cps450;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cps450.oodle.analysis.DepthFirstAdapter;
import cps450.Type;
import cps450.SymbolTable;
import declarations.ClassDeclaration;
import declarations.Declaration;
import declarations.MethodDeclaration;
import declarations.ParameterDeclaration;
import declarations.VariableDeclaration;
import cps450.oodle.node.AAddExpression;
import cps450.oodle.node.AAndExpression;
import cps450.oodle.node.AArgumentDecl;
import cps450.oodle.node.AAssstmtStatement;
import cps450.oodle.node.ABooleanType;
import cps450.oodle.node.ACallxprExpression;
import cps450.oodle.node.ACatExpression;
import cps450.oodle.node.AClassDef;
import cps450.oodle.node.AClstmtStatement;
import cps450.oodle.node.ADivExpression;
import cps450.oodle.node.AEqExpression;
import cps450.oodle.node.AExtends;
import cps450.oodle.node.AFlExpression;
import cps450.oodle.node.AGtExpression;
import cps450.oodle.node.AGteExpression;
import cps450.oodle.node.AIdExpression;
import cps450.oodle.node.AIdentifyType;
import cps450.oodle.node.AIfstmtStatement;
import cps450.oodle.node.AIntegerType;
import cps450.oodle.node.AIntlitExpression;
import cps450.oodle.node.ALpstmtStatement;
import cps450.oodle.node.AMeExpression;
import cps450.oodle.node.AMethodDecl;
import cps450.oodle.node.AMulExpression;
import cps450.oodle.node.ANegExpression;
import cps450.oodle.node.ANewtypeExpression;
import cps450.oodle.node.ANotExpression;
import cps450.oodle.node.ANullExpression;
import cps450.oodle.node.AOrExpression;
import cps450.oodle.node.AParenExpression;
import cps450.oodle.node.APosExpression;
import cps450.oodle.node.ARecursiveType;
import cps450.oodle.node.AStringType;
import cps450.oodle.node.AStrlitExpression;
import cps450.oodle.node.ASubExpression;
import cps450.oodle.node.ATrExpression;
import cps450.oodle.node.AVarDecl;
import cps450.oodle.node.Node;
import cps450.oodle.node.PArgumentDecl;
import cps450.oodle.node.PStatement;
import cps450.oodle.node.PVarDecl;
import cps450.oodle.node.TBoolean;
import cps450.oodle.node.TClasskey;
import cps450.oodle.node.TColon;
import cps450.oodle.node.TComma;
import cps450.oodle.node.TComment;
import cps450.oodle.node.TConcatenate;
import cps450.oodle.node.TContinuation;
import cps450.oodle.node.TDivide;
import cps450.oodle.node.TDot;
import cps450.oodle.node.TElse;
import cps450.oodle.node.TEnd;
import cps450.oodle.node.TEquals;
import cps450.oodle.node.TFalse;
import cps450.oodle.node.TFrom;
import cps450.oodle.node.TGreater;
import cps450.oodle.node.TGreaterEqual;
import cps450.oodle.node.TIdentifier;
import cps450.oodle.node.TIf;
import cps450.oodle.node.TIllegalString;
import cps450.oodle.node.TInherits;
import cps450.oodle.node.TInt;
import cps450.oodle.node.TIntegerLiteral;
import cps450.oodle.node.TIs;
import cps450.oodle.node.TLBracket;
import cps450.oodle.node.TLoop;
import cps450.oodle.node.TLParen;
import cps450.oodle.node.TMe;
import cps450.oodle.node.TMinus;
import cps450.oodle.node.TMultiply;
import cps450.oodle.node.TNew;
import cps450.oodle.node.TNewline;
import cps450.oodle.node.TNot;
import cps450.oodle.node.TNull;
import cps450.oodle.node.Token;
import cps450.oodle.node.TOr;
import cps450.oodle.node.TPlus;
import cps450.oodle.node.TRBracket;
import cps450.oodle.node.TRParen;
import cps450.oodle.node.TSemicolon;
import cps450.oodle.node.TString;
import cps450.oodle.node.TStringLiteral;
import cps450.oodle.node.TThen;
import cps450.oodle.node.TTrue;
import cps450.oodle.node.TUnknownCharacter;
import cps450.oodle.node.TUnterminatedString;
import cps450.oodle.node.TWhile;
import cps450.oodle.node.TWhitespace;

public class SemanticChecker extends DepthFirstAdapter {
	private HashMap<Node, HashMap<String,Object>> attributeGrammarMap = new HashMap<Node, HashMap<String,Object>>();
	private int lastLine;
	public int classCounter = 0;
	
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
	/* printError
	 * Arguments:
	 *   error : String - the error to print
	 * Purpose: print error messages to standard out
	 */
	private void printError(String error) {
		Application.getErrors().addSemanticErrors();
		System.out.println("At line " + lastLine + " - " + error);
	}
	
	/* checkOperator
	 * Arguments:
	 *   lhs : Type - the type of the left hand side
	 *   rhs : Type - the type of the right hand side
	 *   expected : List<Type> - the list of expected types
	 *   result : Type - the result type (if null same as operand types
	 *   operator : String - the operator
	 * Purpose: checks to ensure that operators have compatible types
	 */
	private Type checkOperator(Type lhs, Type rhs, List<Type> expected, Type result, String operator) {
		Type ret = Type.error;
		if(lhs.compareTo(rhs) != 0) {
			printError(operator + " does not know how to operate on type " + lhs.getName() + " and type " + rhs.getName());
		} else {
			for(int i = 0; i < expected.size(); ++i) {
				if(lhs.compareTo(expected.get(i)) == 0) {
					if(result != null) {
						ret = result;
					} else {
						ret = lhs;
					}
				}
			}
			if(ret.equals(Type.error)) {
				String errorMsg = operator + " expected type " + expected.get(0).getName();
				for(int i = 1; i < expected.size(); ++i) {
					errorMsg += " or " + expected.get(i).getName();
				}
				errorMsg += " but got type " + lhs.getName();
				printError(errorMsg);
			}
		}
		return ret;
	}
	
	
	@Override
	public void inAClassDef(AClassDef node) {
		classCounter++;
		super.inAClassDef(node);
		initNode(node);
		lastLine = node.getFirst().getLine();
		String name = node.getFirst().getText();
		try { //create class declaration and attempt to push onto symbol table
			ClassDeclaration classDecl = new ClassDeclaration(name, new Type(name));
			Application.getSymbolTable().push(name, classDecl);
		} catch (Exception ex) {
			printError(ex.getMessage());
		}
		try { //start a new scope
			Application.getSymbolTable().beginScope();
		} catch (Exception ex) {
			printError(ex.getMessage());
		}
	}

	@Override
	public void outAClassDef(AClassDef node) {
		super.outAClassDef(node);
		initNode(node);
		try { //end the current class's scope
			Application.getSymbolTable().endScope();
		} catch (Exception ex) {
			printError(ex.getMessage());
		}
	}
	
	@Override
	public void outAExtends(AExtends node) {
		//currently unsupported 
        	printError("unsupported feature");
		super.outAExtends(node);
		initNode(node);
		lastLine = node.getIdentifier().getLine();
		String name = node.getIdentifier().getText();
		ClassDeclaration parent = null;
		try { //try to find parent and set parent of last class declaration
			parent = (ClassDeclaration)Application.getSymbolTable().lookup(name, SymbolTable.CLASS_SCOPE).getDeclaration();
		} catch (Exception ex) {
			printError(ex.getMessage());
		}
		if(parent != null) {
			Application.getSymbolTable().getLastClassDeclaration().setParent(parent);
		}
	}

	
	
	@Override
	public void caseAMethodDecl(AMethodDecl node) {
		inAMethodDecl(node);
        if(node.getFirstId() != null)
        {
            node.getFirstId().apply(this);
        }
        {
            List<PArgumentDecl> copy = new ArrayList<PArgumentDecl>(node.getArgumentDecl());
            for(PArgumentDecl e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getType() != null)
        {
            node.getType().apply(this);
            Type t = (Type)attributeGrammarMap.get(node.getType()).get("type");
            Application.getSymbolTable().getLastMethodDeclaration().setType(t);
            VariableDeclaration varDecl = new VariableDeclaration(node.getFirstId().getText(), t);
            try {
				Application.getSymbolTable().push(node.getFirstId().getText(), varDecl);
			} catch (Exception ex) {
				printError(ex.getMessage());
			}
        } else {
        	Application.getSymbolTable().getLastMethodDeclaration().setType(Type.voidType);
            VariableDeclaration varDecl = new VariableDeclaration(node.getFirstId().getText(), Type.voidType);
            try {
				Application.getSymbolTable().push(node.getFirstId().getText(), varDecl);
			} catch (Exception ex) {
				printError(ex.getMessage());
			}
        	
        }
        {
            List<PVarDecl> copy = new ArrayList<PVarDecl>(node.getVarDecl());
            for(PVarDecl e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PStatement> copy = new ArrayList<PStatement>(node.getStatement());
            for(PStatement e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getSecondId() != null)
        {
            node.getSecondId().apply(this);
        }
        outAMethodDecl(node);
	}
	
	
	@Override
	public void inAMethodDecl(AMethodDecl node) {
		super.inAMethodDecl(node);
		initNode(node);
		lastLine = node.getFirstId().getLine();
		String name = node.getFirstId().getText();
		try { //create method declaration and attempt to push onto symbol table
			MethodDeclaration methodDecl = new MethodDeclaration();
			methodDecl.setName(name);
			Application.getSymbolTable().push(name, methodDecl);
			Application.getSymbolTable().getLastClassDeclaration().addMethod(methodDecl);
		} catch (Exception ex) {
			printError(ex.getMessage());
		}
		try { //start a new scope
			Application.getSymbolTable().beginScope();
		} catch (Exception ex) {
			printError(ex.getMessage());
		}
	}

	@Override
	public void outAMethodDecl(AMethodDecl node) {
		super.outAMethodDecl(node);
		initNode(node);
		try { //end the current method's scope
			Application.getSymbolTable().endScope();
		} catch (Exception ex) {
			printError(ex.getMessage());
		}
	}
	
	@Override
	public void outAArgumentDecl(AArgumentDecl node) {
		super.outAArgumentDecl(node);
		initNode(node);
		lastLine = node.getIdentifier().getLine();
		Type t = (Type)attributeGrammarMap.get(node.getType()).get("type");
		String name = node.getIdentifier().getText();
		ParameterDeclaration parameterDecl = new ParameterDeclaration(name, t);
		try { //add a parameter to the last method
			Application.getSymbolTable().getLastMethodDeclaration().addParameter(parameterDecl);
		} catch (Exception ex) {
			printError(ex.getMessage());
		}
		try { //psuedo treat a parameter as a variable so we can perform lookups
			VariableDeclaration variableDecl = new VariableDeclaration(name, t);
			Application.getSymbolTable().push(name, variableDecl);
		} catch (Exception ex) {
			printError(ex.getMessage());
		}
	}
	
	@Override
	public void outAIntegerType(AIntegerType node) {
		super.outAIntegerType(node);
		initNode(node);
		lastLine = node.getInt().getLine();
		attributeGrammarMap.get(node).put("type", Type.integer);
	}
	
	@Override
	public void outABooleanType(ABooleanType node) {
		super.outABooleanType(node);
		initNode(node);
		lastLine = node.getBoolean().getLine();
		attributeGrammarMap.get(node).put("type", Type.bool);
	}

	@Override
	public void outAStringType(AStringType node) {
   //currently unsupported
        printError("unsupported feature");
		super.outAStringType(node);
		initNode(node);
		lastLine = node.getString().getLine();
		attributeGrammarMap.get(node).put("type", Type.string);
	}
	
	@Override
	public void outAIdentifyType(AIdentifyType node) {
		super.outAIdentifyType(node);
		initNode(node);
		lastLine = node.getIdentifier().getLine();
		String name = node.getIdentifier().getText();
		ClassDeclaration parent = null;
		try { //try to class associated with the identifier
			parent = (ClassDeclaration)Application.getSymbolTable().lookup(name, SymbolTable.CLASS_SCOPE).getDeclaration();
		} catch (Exception ex) {
			printError(ex.getMessage());
		}
		if(parent != null) {
			attributeGrammarMap.get(node).put("type", parent.getType());
		} else {
			attributeGrammarMap.get(node).put("type", Type.error);
		}
	}
	
	@Override
	public void outARecursiveType(ARecursiveType node) {
        //not supported right now
        	printError("unsupported feature");
		super.outARecursiveType(node);
		initNode(node);
		Type t = (Type)attributeGrammarMap.get(node.getType()).get("type");
		Type newT = new Type(t.getName() + "_r");
		attributeGrammarMap.get(node).put("type", newT);
		if(node.getExpression()!=null){
			Type tt = (Type)attributeGrammarMap.get(node.getExpression()).get("type");
			if(tt.compareTo(Type.integer)!=0){
				printError("this is a problem");
			}
		}
	}
	
	@Override
	public void outAVarDecl(AVarDecl node) {
		super.outAVarDecl(node);
		initNode(node);
		lastLine = node.getIdentifier().getLine();
        if(node.getExpression()!=null) {
        	printError("unsupported feature");
        }
	    if(node.getType() == null) {
	      printError("this is a problem");
	    } else { Type t = (Type)attributeGrammarMap.get(node.getType()).get("type");
	    	String name = node.getIdentifier().getText();
	        try {
	          VariableDeclaration variableDecl = new VariableDeclaration(name, t);
	          Application.getSymbolTable().push(name, variableDecl);
	          try {
	            if(Application.getSymbolTable().getScope() == SymbolTable.CLASS_SCOPE) { 
	              Application.getSymbolTable().getLastClassDeclaration().addVariable(variableDecl);
	            } else if(Application.getSymbolTable().getScope() == SymbolTable.METHOD_SCOPE) {
	              Application.getSymbolTable().getLastMethodDeclaration().addVariable(variableDecl);
	            }
	          } catch (Exception ex) {
	            printError(ex.getMessage());
	          }
	        } catch (Exception ex) {
	          printError(ex.getMessage());
	        }
	    }
	}
	
	@Override
	public void outAIfstmtStatement(AIfstmtStatement node) {
		super.outAIfstmtStatement(node);
		Type t = (Type)attributeGrammarMap.get(node.getExpression()).get("type");
		if(!t.equals(Type.bool)) {
			printError("if statement expects condition to be type bool but was type " + t.getName());
		}
	}

	@Override
	public void outALpstmtStatement(ALpstmtStatement node) {
		super.outALpstmtStatement(node);
		Type t = (Type)attributeGrammarMap.get(node.getExpression()).get("type");
		if(!t.equals(Type.bool)) {
			printError("while statement expects condition to be type bool but was type " + t.getName());
		}
	}
	
	@Override
	public void outAClstmtStatement(AClstmtStatement node) {
		super.outAClstmtStatement(node);
		initNode(node);
		lastLine = node.getIdentifier().getLine();
		String name = node.getIdentifier().getText();
		ClassDeclaration parent = Application.getSymbolTable().getLastClassDeclaration();
		if(node.getLhs() != null) {
			Type t = (Type)attributeGrammarMap.get(node.getLhs()).get("type");
			try {
				parent = (ClassDeclaration)Application.getSymbolTable().lookup(t.getName(), 0).getDeclaration();
			} catch(Exception e) {
				printError("no class of name " + t.getName() + " was found in the current scope");
			}
		}
		MethodDeclaration method = null;
		try { // try to find the method
			method = parent.lookupMethod(name);
		} catch(Exception e) {
			printError("no method of name " + node.getIdentifier().getText() + " was found in class " + parent.getType().getName());
		}
		if(method != null) { // if found check parameters
			if(method.getParamters().size() != node.getRhs().size()) {
				printError("method " + method.getName() + " wanted " + method.getParamters().size() + " parameters but got " + node.getRhs().size() + " parameters");
			}
			for(int i = 0; i < method.getParamters().size() && i < node.getRhs().size(); ++i) {
				Type wanted = method.getParamters().get(i).getType();
				Type got = (Type)attributeGrammarMap.get(node.getRhs().get(i)).get("type");
				if(!wanted.equals(got) || got.equals(Type.voidType)) {
					printError("method " + method.getName() + "'s parameter " + i + " wanted type " + wanted.getName() + " but got " + got.getName());
				}
			}
		}
	}

	@Override
	public void outAAssstmtStatement(AAssstmtStatement node) {
		super.outAAssstmtStatement(node);
		initNode(node);
		VariableDeclaration variable = null;
		String name = node.getIdentifier().getText();
		lastLine = node.getIdentifier().getLine();
		try { //try to find the requested variable
			Declaration decl = Application.getSymbolTable().lookup(name).getDeclaration();
			if(decl instanceof VariableDeclaration) {
				variable = (VariableDeclaration)decl;
			} else {
				throw new Exception("no variable with name " + name + " exists in the current scope");
			}
		} catch(Exception ex) {
			printError(ex.getMessage());
		}
		if(variable != null) { // if found check types
			Type t = (Type)attributeGrammarMap.get(node.getLast()).get("type");
			if(variable.getType().compareTo(t) != 0) {
				printError("variable " + name + " wanted type " + variable.getType().getName() + " but got type " + t.getName());
			}
		}
	}
	
	
	//EXPRESSIONS
	//YOU ARE HERE
	
	@Override
	public void outAOrExpression(AOrExpression node) {
		super.outAOrExpression(node);
		initNode(node);
		Type lhsType = (Type)attributeGrammarMap.get(node.getLhs()).get("type");
		Type rhsType = (Type)attributeGrammarMap.get(node.getRhs()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.bool);
		attributeGrammarMap.get(node).put("type", checkOperator(lhsType, rhsType, expectedTypes, Type.bool, "or"));
	}

	@Override
	public void outAAndExpression(AAndExpression node) {
		super.outAAndExpression(node);
		initNode(node);
		Type lhsType = (Type)attributeGrammarMap.get(node.getLhs()).get("type");
		Type rhsType = (Type)attributeGrammarMap.get(node.getRhs()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.bool);
		attributeGrammarMap.get(node).put("type", checkOperator(lhsType, rhsType, expectedTypes, Type.bool, "and"));
	}
	
	@Override
	public void outAGtExpression(AGtExpression node) {
		super.outAGtExpression(node);
		initNode(node);
		Type lhsType = (Type)attributeGrammarMap.get(node.getLhs()).get("type");
		Type rhsType = (Type)attributeGrammarMap.get(node.getRhs()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.integer);
		expectedTypes.add(Type.string);
		attributeGrammarMap.get(node).put("type", checkOperator(lhsType, rhsType, expectedTypes, Type.bool, "gt"));
	}


	@Override
	public void outAGteExpression(AGteExpression node) {
		super.outAGteExpression(node);
		initNode(node);
		Type lhsType = (Type)attributeGrammarMap.get(node.getLhs()).get("type");
		Type rhsType = (Type)attributeGrammarMap.get(node.getRhs()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.integer);
		expectedTypes.add(Type.string);
		attributeGrammarMap.get(node).put("type", checkOperator(lhsType, rhsType, expectedTypes, Type.bool, "gte"));
	}
	
	@Override
	public void outAEqExpression(AEqExpression node) {
		super.outAEqExpression(node);
		initNode(node);
		Type lhsType = (Type)attributeGrammarMap.get(node.getLhs()).get("type");
		Type rhsType = (Type)attributeGrammarMap.get(node.getRhs()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.integer);
		expectedTypes.add(Type.string);
		expectedTypes.add(Type.bool);
		attributeGrammarMap.get(node).put("type", checkOperator(lhsType, rhsType, expectedTypes, Type.bool, "equals"));
	}
	
	@Override
	public void outACatExpression(ACatExpression node) {
		super.outACatExpression(node);
		initNode(node);
		Type lhsType = (Type)attributeGrammarMap.get(node.getLhs()).get("type");
		Type rhsType = (Type)attributeGrammarMap.get(node.getRhs()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.string);
		attributeGrammarMap.get(node).put("type", checkOperator(lhsType, rhsType, expectedTypes, Type.string, "cat"));
	}
	
	@Override
	public void outASubExpression(ASubExpression node) {
		super.outASubExpression(node);
		initNode(node);
		Type lhsType = (Type)attributeGrammarMap.get(node.getLhs()).get("type");
		Type rhsType = (Type)attributeGrammarMap.get(node.getRhs()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.integer);
		attributeGrammarMap.get(node).put("type", checkOperator(lhsType, rhsType, expectedTypes, Type.integer, "minus"));
	}

	@Override
	public void outAAddExpression(AAddExpression node) {
		super.outAAddExpression(node);
		initNode(node);
		Type lhsType = (Type)attributeGrammarMap.get(node.getLhs()).get("type");
		Type rhsType = (Type)attributeGrammarMap.get(node.getRhs()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.integer);
		attributeGrammarMap.get(node).put("type", checkOperator(lhsType, rhsType, expectedTypes, null, "plus"));
	}
	
	@Override
	public void outAMulExpression(AMulExpression node) {
		super.outAMulExpression(node);
		initNode(node);
		Type lhsType = (Type)attributeGrammarMap.get(node.getLhs()).get("type");
		Type rhsType = (Type)attributeGrammarMap.get(node.getRhs()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.integer);
		attributeGrammarMap.get(node).put("type", checkOperator(lhsType, rhsType, expectedTypes, Type.integer, "multiply"));
	}

	@Override
	public void outADivExpression(ADivExpression node) {
		super.outADivExpression(node);
		initNode(node);
		Type lhsType = (Type)attributeGrammarMap.get(node.getLhs()).get("type");
		Type rhsType = (Type)attributeGrammarMap.get(node.getRhs()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.integer);
		attributeGrammarMap.get(node).put("type", checkOperator(lhsType, rhsType, expectedTypes, Type.integer, "divide"));
	}
	
	@Override
	public void outANotExpression(ANotExpression node) {
		super.outANotExpression(node);
		initNode(node);
		Type rhsType = (Type)attributeGrammarMap.get(node.getExpression()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.bool);
		attributeGrammarMap.get(node).put("type", checkOperator(Type.bool, rhsType, expectedTypes, Type.bool, "not"));
	}
	
	@Override
	public void outANegExpression(ANegExpression node) {
		super.outANegExpression(node);
		initNode(node);
		Type rhsType = (Type)attributeGrammarMap.get(node.getExpression()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.integer);
		attributeGrammarMap.get(node).put("type", checkOperator(Type.integer, rhsType, expectedTypes, Type.integer, "negation"));
	}
	
	@Override
	public void outAPosExpression(APosExpression node) {
		super.outAPosExpression(node);
		initNode(node);
		Type rhsType = (Type)attributeGrammarMap.get(node.getExpression()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.integer);
		attributeGrammarMap.get(node).put("type", checkOperator(Type.integer, rhsType, expectedTypes, Type.integer, "positive"));
	}
	
	@Override
	public void outACallxprExpression(ACallxprExpression node) {
		super.outACallxprExpression(node);
		initNode(node);
		lastLine = node.getIdentifier().getLine();
		String name = node.getIdentifier().getText();
		ClassDeclaration parent = Application.getSymbolTable().getLastClassDeclaration();
		if(node.getLhs() != null) { // if there is something before the dot use that class for lookups
			Type t = (Type)attributeGrammarMap.get(node.getLhs()).get("type");
			try {
				parent = (ClassDeclaration)Application.getSymbolTable().lookup(t.getName(), 0).getDeclaration();
			} catch(Exception e) {
				printError("no class of name " + t.getName() + " was found in the current scope");
			}
		}
		MethodDeclaration method = null;
		try { //try to find method
			method = parent.lookupMethod(name);
		} catch(Exception e) {
			printError("no method of name " + node.getIdentifier().getText() + " was found in class " + parent.getType().getName());
		}
		if(method != null) { //if found, check parameters
			if(method.getParamters().size() != node.getRhs().size()) {
				printError("method " + method.getName() + " wanted " + method.getParamters().size() + " parameters but got " + node.getRhs().size() + " parameters");
			}
			for(int i = 0; i < method.getParamters().size() && i < node.getRhs().size(); ++i) {
				Type wanted = method.getParamters().get(i).getType();
				Type got = (Type)attributeGrammarMap.get(node.getRhs().get(i)).get("type");
				if(!wanted.equals(got) || got.equals(Type.voidType)) {
					printError("method " + method.getName() + "'s parameter " + i + " wanted type " + wanted.getName() + " but got " + got.getName());
				}
			}
			attributeGrammarMap.get(node).put("type", method.getType());
		} else {
			attributeGrammarMap.get(node).put("type", Type.error);
		}
	}
	
	@Override
	public void outAIdExpression(AIdExpression node) {
		super.outAIdExpression(node);
		initNode(node);
		VariableDeclaration varDecl = null;
		String name = node.getIdentifier().getText();
		lastLine = node.getIdentifier().getLine();
		try { // try to find our class on the symbol table
			Declaration decl = Application.getSymbolTable().lookup(name).getDeclaration();
			if(decl instanceof VariableDeclaration) {
				varDecl = (VariableDeclaration)decl;
			} else {
				throw new Exception("no variable with name " + name + " exists in the current scope");
			}
		} catch(Exception ex) {
			printError(ex.getMessage());
		}
		if(varDecl != null) {
			attributeGrammarMap.get(node).put("type", varDecl.getType());
		} else {
			attributeGrammarMap.get(node).put("type", Type.error);
		}
	}
	
	@Override
	public void outAIntlitExpression(AIntlitExpression node) {
		super.outAIntlitExpression(node);
		initNode(node);
		attributeGrammarMap.get(node).put("type", Type.integer);
		lastLine = node.getIntegerLiteral().getLine();
	}

	@Override
	public void outAStrlitExpression(AStrlitExpression node) {
      //currently unsupported
        	printError("unsupported feature");
		super.outAStrlitExpression(node);
		initNode(node);
		attributeGrammarMap.get(node).put("type", Type.string);
		lastLine = node.getStringLiteral().getLine();
	}
	
	@Override
	public void outATrExpression(ATrExpression node) {
		super.outATrExpression(node);
		initNode(node);
		attributeGrammarMap.get(node).put("type", Type.bool);
		lastLine = node.getTrue().getLine();
	}

	@Override
	public void outAFlExpression(AFlExpression node) {
		super.outAFlExpression(node);
		initNode(node);
		attributeGrammarMap.get(node).put("type", Type.bool);
		lastLine = node.getFalse().getLine();
	}
	
	@Override
	public void outANullExpression(ANullExpression node) {
        //unsupported
        	printError("unsupported feature");
		super.outANullExpression(node);
		initNode(node);
		attributeGrammarMap.get(node).put("type", Type.inull);
		lastLine = node.getNull().getLine();
	}
	
	@Override
	public void outAMeExpression(AMeExpression node) {
		//unsupported
        	printError("unsupported feature");
		super.outAMeExpression(node);
		initNode(node);
		attributeGrammarMap.get(node).put("type", Application.getSymbolTable().getLastClassDeclaration().getType());
		lastLine = node.getMe().getLine();
	}
	
	@Override
	public void outANewtypeExpression(ANewtypeExpression node) {
		//currently unsupported 
    		printError("unsupported feature");
		super.outANewtypeExpression(node);
		initNode(node);
		Type t = (Type)attributeGrammarMap.get(node.getType()).get("type");
		attributeGrammarMap.get(node).put("type", t);
	}
	
	@Override
	public void outAParenExpression(AParenExpression node) {
		super.outAParenExpression(node);
		initNode(node);
		Type rhsType = (Type)attributeGrammarMap.get(node.getExpression()).get("type");
		List<Type> expectedTypes = new ArrayList<Type>();
		expectedTypes.add(Type.bool);
		attributeGrammarMap.get(node).put("type", checkOperator(Type.bool, rhsType, expectedTypes, Type.bool, "not"));
	}
	
}
