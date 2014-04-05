package instructions;

public 

class CommentInstruction extends InstructionCommand {
	private String label;
	
	public CommentInstruction(String label) {
		this.label = label;
	}

	@Override
	public String emit() {
		return "# my method is: " + label + "\n";
	}
}

