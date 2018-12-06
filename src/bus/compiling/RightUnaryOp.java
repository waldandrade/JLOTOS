package bus.compiling;

import books.LotosOperation;

public class RightUnaryOp implements Op {


	private LotosOperation mainLotosOperation;
	private ConstantOp rightOpTable;
	private int indexOfOp;
	
	@Override
	public String getReturnSortOfOperation() {
		// TODO Auto-generated method stub
		return mainLotosOperation.returnSort;
	}

	public LotosOperation getMainLotosOperation() {
		return mainLotosOperation;
	}

	public void setMainLotosOperation(LotosOperation mainLotosOperation) {
		this.mainLotosOperation = mainLotosOperation;
	}

	public ConstantOp getRightOpTable() {
		return rightOpTable;
	}

	public void setRightOpTable(ConstantOp rightOpTable) {
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
