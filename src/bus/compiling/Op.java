package bus.compiling;

import books.LotosOperation;

public interface Op {

	public LotosOperation getMainLotosOperation();
	public void setMainLotosOperation(LotosOperation mainLotosOperation);
	
	public String getReturnSortOfOperation();
	
	public int getIndexOfOp();
	
	public void setIndexOfOp(int indexOfOp);
		
}
