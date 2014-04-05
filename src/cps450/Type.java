/* Author: Ethan McGee
* Date: 2014-03-10
* Purpose: provides a method for getting primitive types as well as creating new ones  
*/
package cps450;


public class Type implements Comparable<Type> {
	public static final Type error    = new Type("Error"),
			                 voidType = new Type("Void"),
			                 integer  = new Type("Int"),
			                 bool     = new Type("Bool"),
			                 string   = new Type("String"),
							 inull	  = new Type("Null");
	
	private String name;		
	
	public Type(String name) {
		this.name = name;
	}
	
	/* getName
	 * Arguments:
	 * 
	 * Purpose: returns the name of the type
	 */
	public String getName() {
		return this.name;
	}

	/* compareTo
	 * Arguments:
	 *   arg0: Type - the type to compare the current type against
	 * Purpose: determines if two types are equal
	 */
	@Override
	public int compareTo(Type arg0) {
		return name.compareTo(arg0.getName());
	}
}

