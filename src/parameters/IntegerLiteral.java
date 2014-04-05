/* IntegerLiteral.java
 * Author: Ethan McGee
 * Date: 2014-03-13
 * Purpose: provides a representation of an assembly integer literal
 */
package parameters;

public class IntegerLiteral extends Parameter {
	
	private int integer;
	
	public IntegerLiteral(int integer) {
		this.integer = integer;
	}
	
	/* toString
	 * Arguments:
	 *   
	 * returns the integer literal as a formatted string
	 */
	public String toString() {
		return "$" + this.integer;
	}

}
