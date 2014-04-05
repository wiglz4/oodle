// Oodle.java

package cps450;
import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.IllegalOptionValueException;
import jargs.gnu.CmdLineParser.UnknownOptionException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import cps450.oodle.node.*;
import cps450.Type;
import cps450.oodle.lexer.*;
import cps450.oodle.parser.*;
import java.io.*;


//command line argument processing
public class Oodle
{	
	/* printHelp
	 * Arguments:
	 * 
	 * Purpose: Writes a help statement to standard out
	 */
	public static void printHelp() {
		System.out.println("Oodle Compiler");
		System.out.println("v 0.1");
		System.out.println("Author: Ethan McGee");
		System.out.println("");
		System.out.println("Usage:");
		System.out.println(" java -jar oodle.jar [options] [files]");
		System.out.println("");
		System.out.println("Options:");
		System.out.println("-ds, --print-tokens");
		System.out.println("  display a list of tokens from the listed files");
		System.out.println("-?, --help");
		System.out.println("  display this message");
	}
	/* main
	 * Arguments:
	 *  @args - the list of command line arguments
	 * Purpose: main execution function for compiler
	 */
    public static void main(String[] args) throws IOException, IllegalOptionValueException, UnknownOptionException {
    	CmdLineParser parser = new CmdLineParser();
    	//command line options
		CmdLineParser.Option printToken1 = parser.addBooleanOption('d', "print-tokens");
		CmdLineParser.Option printToken2 = parser.addBooleanOption('s', "print-tokens");
		CmdLineParser.Option help = parser.addBooleanOption('?', "help");
		//parse command line arguments
		parser.parse(args);
		
		//set applicable values from options class
		if((Boolean)parser.getOptionValue(printToken1, false) && (Boolean)parser.getOptionValue(printToken2, false)) {
			Application.getOptions().printTokens();
		} else if ((Boolean)parser.getOptionValue(printToken1, false) || (Boolean)parser.getOptionValue(printToken2, false) || (Boolean)parser.getOptionValue(help, false)) {
			printHelp();
			return;
		}
		
		//read and merge all files into single file
		String source = "";
		boolean first = true;
		String[] files = parser.getRemainingArgs();
		for(String file: parser.getRemainingArgs()) {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			int lines = 0;
			while((line = reader.readLine()) != null) {
				source += ((first ? "" : "\n") + line);
				first = false;
				++lines;
			}
			reader.close();
			Application.getFileAndLineNumbers().addFile(file, lines);
		}
		
		//begin lexing and parsing
		try {
			OodleLexer lexer = new OodleLexer(new PushbackReader(new StringReader(source)));
			OodleParser oodleParser = new OodleParser(lexer);
			Start node = oodleParser.parse();
			// perform semantic checks (new code)
			if(Application.getErrors().getTotalErrors() == 0) {
				SemanticChecker checker = new SemanticChecker();
				node.apply(checker);  // invoke SemanticChecker traversal
				if (checker.classCounter != files.length) {
					System.out.println("Unsupported Feature: multiple classes");
					System.exit(1);
				}
			}
			if(Application.getErrors().getTotalErrors() == 0) { //no semantic errors either
				CodeGenerator codeGenerator = new CodeGenerator();
				node.apply(codeGenerator);
				codeGenerator.writeAssembly();
				Runtime r = Runtime.getRuntime();
				Process p = r.exec("gcc -g temp.s stdlib.o -o " + files[files.length-1].replace(".ood", "")); //invoke gcc to compile our assembly using first file name as executable filename
				try {
					int exitCode = p.waitFor(); //wait for process to finish and grab exit code
					if(exitCode != 0) { //there was some error
						BufferedReader b = new BufferedReader(new InputStreamReader(p.getErrorStream()));
						String line = "";
						line = b.readLine();
						while (line != null) {
						  System.out.println(line);  //print the error from gcc
						  line = b.readLine();
						}
					} else {
						//File f = new File("temp.s"); //delete our temporary file
						//f.delete();
					}
				} catch(Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
			Token t;
			while(!((t = lexer.next()) instanceof EOF)) { ; }
			System.out.println("0 errors found");
		} catch (LexerException e) {
			;
		} catch (ParserException e) {
			System.out.println(Application.getErrors().getTotalErrors() + " errors found");
			//e.printStackTrace();
		}
    }
}

