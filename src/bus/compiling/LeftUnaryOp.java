package bus.compiling;

import books.LotosOperation;

public class LeftUnaryOp implements Op {

	private Op leftOpTable;
	private LotosOperation mainLotosOperation;
	private int indexOfOp = 0;
	
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

	@Override
	public int getIndexOfOp() {
		// TODO Auto-generated method stub
		return this.indexOfOp;
	}

	@Override
	public void setIndexOfOp(int indexOfOp) {
		// TODO Auto-generated method stub
		this.indexOfOp = indexOfOp;
	}

	
	
	

}
