/* Options.java
 * Author: Ethan McGee
 * Date: 2014-01-23
 * 
 * Purpose: A class holding the options passed to the compiler
 */

package cps450;

public class Options {
	private boolean shouldPrintTokens;
	
	public Options() {
		this.shouldPrintTokens = false;
	}
	
	/* printTokens
	 * Arguments:
	 *   
	 * Purpose: alerts the compiler that it should print tokens
	 */
	public void printTokens() {
		this.shouldPrintTokens = true;
	}
	
	/* shouldPrintTokens
	 * Arguments:
	 *   
	 * Purpose: retrieve the value of shouldPrintTokens
	 */
	public boolean shouldPrintTokens() {
		return this.shouldPrintTokens;
	}
}
