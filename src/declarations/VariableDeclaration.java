/* VariableDeclaration.java
 * Author: Ethan McGee
 * Date: 2014-03-10
 * Purpose: a declaration holder for variable declarations 
 */
package declarations;

import cps450.Type;

public class VariableDeclaration extends Declaration {
	
	private String prefix;
	
	public VariableDeclaration() {
		super();
	}
	
	public VariableDeclaration(String name, Type t) {
		super(name, t);
	}
}
