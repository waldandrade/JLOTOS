package bus.compiling.semanticStructure;

import bus.compiling.OpTable;

public class SelectPredicate {
	
	//The dataExp before the "=" keyword or the Boolean value
	private OpTable leftValueOpTable = null;
	
	//The dataExp after the "=" keyword
	private OpTable rightValueOpTable = null;

	public OpTable getLeftValueOpTable() {
		return leftValueOpTable;
	}

	public void setLeftValueOpTable(OpTable leftValueOpTable) {
		this.leftValueOpTable = leftValueOpTable;
	}

	public OpTable getRightValueOpTable() {
		return rightValueOpTable;
	}

	public void setRightValueOpTable(OpTable rightValueOpTable) {
		this.rightValueOpTable = rightValueOpTable;
	}
	
	
	
}
