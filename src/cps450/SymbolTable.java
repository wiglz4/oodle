/* SymbolTable.java
 * Author: Ethan McGee
 * Date: 2014-03-10
 * Purpose: provides an implementation of a symbol table for the compiler  
 */
package cps450;

import java.util.HashMap;
import java.util.Stack;

import declarations.ClassDeclaration;
import declarations.Declaration;
import declarations.MethodDeclaration;
import declarations.ParameterDeclaration;
import declarations.VariableDeclaration;

public class SymbolTable {
	public class Symbol {
		private String name;
		private Declaration declaration;
		
		public Symbol(String name, Declaration declaration) {
			this.name = name;
			this.declaration = declaration;
		}
		
		/* getName
		 * Arguments:
		 *  
		 * Purpose: get the name of the current symbol
		 */
		public String getName() {
			return this.name;
		}
		
		/* getDeclaration
		 * Arguments:
		 *  
		 * Purpose: get the name of the current declaration
		 */
		public Declaration getDeclaration() {
			return this.declaration;
		}
	}
	
	private int currentScope;
	
	private ClassDeclaration lastClassDeclaration;
	private MethodDeclaration lastMethodDeclaration;
	private VariableDeclaration lastVariableDeclaration;
	
	public static final int GLOBAL_SCOPE = 0;
	public static final int CLASS_SCOPE  = 1;
	public static final int METHOD_SCOPE = 2;
	
	private Stack<HashMap<String, Symbol>> symbolTable;
	
	public SymbolTable() {
		//push global Reader and Writer classes to stack
		currentScope = 0;
		symbolTable = new Stack<HashMap<String, Symbol>>();
		symbolTable.add(new HashMap<String, Symbol>());
		
		ClassDeclaration writerDecl = new ClassDeclaration("Writer", new Type("Writer"));
		MethodDeclaration writeIntDecl = new MethodDeclaration("writeint", Type.voidType);
		try {
			writerDecl.addMethod(writeIntDecl);
		} catch (Exception e) { ; }
		try {
			writeIntDecl.addParameter(new ParameterDeclaration("int", Type.integer));
		} catch (Exception e) { ; }
		symbolTable.peek().put("Writer", new Symbol("Writer", writerDecl));
		
		ClassDeclaration readerDecl = new ClassDeclaration("Reader", new Type("Reader"));
		MethodDeclaration readIntDecl = new MethodDeclaration("readint", Type.integer);
		try {
			readerDecl.addMethod(readIntDecl);
		} catch (Exception e) { ; }
		symbolTable.peek().put("Reader", new Symbol("Reader", readerDecl));
		
		//push global in and out variables onto stack
		symbolTable.peek().put("out", new Symbol("out", new VariableDeclaration("out", new Type("Writer"))));
		symbolTable.peek().put("in", new Symbol("in", new VariableDeclaration("in", new Type("Reader"))));
	}
	
	/* push
	 * Arguments:
	 *  name: String - name of symbol to push
	 *  declaration: Declaration - declaration of symbol to push
	 * Purpose: push a new symbol on the stack
	 */
	public Symbol push(String name, Declaration declaration) throws Exception {
		Symbol s = new Symbol(name, declaration);
		if(symbolTable.get(symbolTable.size() - 1).get(name) == null) {
			symbolTable.get(symbolTable.size() - 1).put(name, s);
		} else {
			throw new Exception("an object of the same type and same level with the same name already exists");
		}
		
		if(declaration instanceof ClassDeclaration) {
			this.lastClassDeclaration = (ClassDeclaration)declaration;
		}
		if(declaration instanceof MethodDeclaration) {
			this.lastMethodDeclaration = (MethodDeclaration)declaration;
		}
		if(declaration instanceof VariableDeclaration) {
			this.lastVariableDeclaration = (VariableDeclaration)declaration;
		}
		
		return s;
	}
	
	/* lookup
	 * Arguments:
	 *  name: String - name of symbol to lookup
	 * Purpose: looks up a symbol starting at the top of the stack
	 */
	public Symbol lookup(String name) throws Exception {
		return lookup(name, symbolTable.size() - 1);
	}
	
	/* lookup
	 * Arguments:
	 *  name: String - name of symbol to lookup
	 *  startAt: Int - start looking at this symbol table level
	 * Purpose: looks up a symbol starting at the specified level of the stack
	 */
	public Symbol lookup(String name, int startAt) throws Exception {
		if(startAt < 0) { throw new Exception("startAt must be greater than 0"); }
		for(int i = startAt; i >= 0; --i) {
			if(symbolTable.get(i).get(name) != null) {
				return symbolTable.get(i).get(name);
			}
		}
		throw new Exception("name " + name + " not found in symbol table");
	}
	
	/* beginScope
	 * Arguments:
	 *  
	 * Purpose: start a new scope
	 */
	public void beginScope() throws Exception {
		++currentScope;
		symbolTable.add(new HashMap<String, Symbol>());
		
		if(currentScope > METHOD_SCOPE) {
			throw new Exception("stack overflow");
		}
	}
	
	/* endScope
	 * Arguments:
	 *  
	 * Purpose: ends the current scope merging the contents down into the lower level of the symbol table of the stack
	 */
	public void endScope() throws Exception {
		--currentScope;
		
		if(currentScope < GLOBAL_SCOPE) {
			throw new Exception("stack underflow");
		}
		symbolTable.remove(symbolTable.size() - 1);
	}
	 
	/* getScope
	 * Arguments:
	 *  
	 * Purpose: get the current scope
	 */
	public int getScope() {
		return currentScope;
	}
	
	/* getLastClassDeclaration
	 * Arguments:
	 *  
	 * Purpose: get the last pushed class declaration
	 */
	public ClassDeclaration getLastClassDeclaration() {
		return this.lastClassDeclaration;
	}
	
	/* getLastMethodDeclaration
	 * Arguments:
	 *  
	 * Purpose: get the last pushed method declaration
	 */
	public MethodDeclaration getLastMethodDeclaration() {
		return this.lastMethodDeclaration;
	}
	
	/* getLastVariableDeclaration
	 * Arguments:
	 *  
	 * Purpose: get the last pushed variable declaration
	 */
	public VariableDeclaration getLastVariableDeclaration() {
		return this.lastVariableDeclaration;
	}
}
