package instructions;

public class Instruction {
	public static Instruction add = new Instruction("addl"),
            sub = new Instruction("subl"),
            div = new Instruction("divl"),
            mul = new Instruction("mull"),
            push = new Instruction("pushl"),
            pop = new Instruction("popl"),
            call = new Instruction("call"),
			ret = new Instruction("ret"),
			not = new Instruction("not"),
			cmp = new Instruction ("cmpl"),
			je = new Instruction ("je"),
			jge = new Instruction ("jge"),
			jg = new Instruction ("jg"),
			jmp = new Instruction ("jmp"),
			or = new Instruction ("orl"),
			and = new Instruction ("andl"),
			neg = new Instruction("negl");

	private String name;
	
	public Instruction(String name) {
	this.name = name;
	}
	
	/* getName
	* Arguments:
	*   
	* Purpose: returns the name of an instruction
	*/
	public String getName() {
	return this.name;
	}

}
