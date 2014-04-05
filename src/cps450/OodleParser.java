package cps450;

import java.io.IOException;

import cps450.oodle.lexer.*;
import cps450.oodle.node.EOF;
import cps450.oodle.node.Start;
import cps450.oodle.parser.*;

public class OodleParser extends Parser{

	Lexer lexer;
	
	public OodleParser(Lexer lexer) {
		super(lexer);
		this.lexer = lexer;
	}
	
	@Override
	public Start parse() throws ParserException, LexerException, IOException {
		try {
			return super.parse();
		} catch (ParserException e) {
			
			String output = Application.getFileAndLineNumbers().getFile(this.lexer.peek().getLine()) + ":" + Application.getFileAndLineNumbers().getLine(this.lexer.peek().getLine()) + "," + this.lexer.peek().getPos() + ":";
			output += " " + e.getMessage();
			
			while(!((lexer.next()) instanceof EOF)) { ; }
			
			System.out.println(output);
			//System.out.println(this.lexer.peek().getClass().toString() + " " + this.lexer.peek().getText());
			Application.getErrors().addParserErrors();
			throw e;
			//find a way to stop once you print lexical errors and not send them to parser.
		} 
	}
}
