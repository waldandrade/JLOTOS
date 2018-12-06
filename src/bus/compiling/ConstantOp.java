package bus.compiling;

import books.LotosOperation;

public class ConstantOp implements Op {

	private OpTable opTable;
	private LotosOperation mainLotosOperation;
	private int indexOfOp;
	
	@Override
	public String getReturnSortOfOperation() {
		// TODO Auto-generated method stub
		if(isOpTableReference()){
			return opTable.getOpReturnSort();
		}else{
			return mainLotosOperation.returnSort;
		}
	}
	
	public boolean isOpTableReference(){
		if(mainLotosOperation == null || opTable != null){
			return true;
		}
		return false;
	}

	public OpTable getOpTable() {
		return opTable;
	}

	public void setOpTable(OpTable opTable) {
		this.opTable = opTable;
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
		return indexOfOp;
	}
	
	public void setIndexOfOp(int indexOfOp){
		this.indexOfOp = indexOfOp;
	}

}
