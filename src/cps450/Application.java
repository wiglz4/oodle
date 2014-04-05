/* Application.java
 * Author: Ethan McGee
 * Date: 2014-01-23
 * 
 * Purpose: A singleton class that shares resources across the entire compiler
 */

package cps450;

public class Application {
	private static Options options;
	private static FileAndLineNumbers fileAndLineNumbers;
	private static Errors errors;
	private static SymbolTable symbolTable;
	
	/* getOptions
	 * Arguments:
	 * 
	 * Purpose: grabs a copy of the Options class, creating one if the 
	 *          current class is null
	 */
	public static Options getOptions() {
		if(options == null) {
			options = new Options();
		}
		return options;
	}
	
	/* getFileAndLineNumbers
	 * Arguments:
	 * 
	 * Purpose: grabs a copy of the FileAndLineNumbers class, creating one if the 
	 *          current class is null
	 */
	public static FileAndLineNumbers getFileAndLineNumbers() {
		if(fileAndLineNumbers == null) {
			fileAndLineNumbers = new FileAndLineNumbers();
		}
		return fileAndLineNumbers;
	}
	
	/* getErrors
	 * Arguments:
	 * 
	 * Purpose: grabs a copy of the Errors class, creating one if the 
	 *          current class is null
	 */
	public static Errors getErrors() {
		if(errors == null) {
			errors = new Errors();
		}
		return errors;
	}
	
	/* getSymbolTable
	 * Arguments:
	 *   
	 * Purpose: singleton wrapper for global symbol table object
	 */
	public static SymbolTable getSymbolTable() {
		if(symbolTable == null) {
			symbolTable = new SymbolTable();
		}
		return symbolTable;
	}
}
