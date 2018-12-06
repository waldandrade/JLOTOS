package bus.compiling.semanticStructure;


public class ProcessInstance{
	
	public String name = "";
	public int numberOfGates = 0;
	public int tokIndex = -1;
	
	public ProcessInstance(String name, int tokIndex){
		
		this.name = name;
		this.tokIndex = tokIndex;
		
	}
	
	public void setNumberOfGates(int numberOfGates){
		
		this.numberOfGates = numberOfGates;
		
	}

}
