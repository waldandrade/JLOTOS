package bus.compiling;

import books.LotosOperation;

public class BinaryOp implements Op {

	private Op leftOpTable;
	private LotosOperation mainLotosOperation;
	private Op rightOpTable;
	private int indexOfOp;
	
	@Override
	public String getReturnSortOfOperation() {
		// TODO Auto-generated method stub
		return mainLotosOperation.returnSort;
	}

	public Op getLeftOpTable() {
		return leftOpTable;
	}

	public void setLeftOpTable(Op leftOpTable) {
		this.leftOpTable = leftOpTable;
	}

	public LotosOperation getMainLotosOperation() {
		return mainLotosOperation;
	}

	public void setMainLotosOperation(LotosOperation mainLotosOperation) {
		this.mainLotosOperation = mainLotosOperation;
	}

	public Op getRightOpTable() {
		return rightOpTable;
	}

	public void setRightOpTable(Op rightOpTable) {
		this.rightOpTable = rightOpTable;
	}
	
	@Override
	public int getIndexOfOp() {
		// TODO Auto-generated method stub
		return indexOfOp;
	}
	
	public void setIndexOfOp(int indexOfOp){
		this.indexOfOp = indexOfOp;
	}
	
}
