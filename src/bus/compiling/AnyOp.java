package bus.compiling;

import books.LotosOperation;

public class AnyOp implements Op {

	private LotosOperation mainLotosOperation = null;
	
	private int indexOfOp;
	
	private String returnSort;
	
	public AnyOp(Token returnSortToken){
		this.mainLotosOperation.operador = "any";
		this.mainLotosOperation.returnSort = returnSortToken.getConteudo();
	}
	
	@Override
	public int getIndexOfOp() {
		// TODO Auto-generated method stub
		return indexOfOp;
	}

	@Override
	public LotosOperation getMainLotosOperation() {
		// TODO Auto-generated method stub
		return mainLotosOperation;
	}

	@Override
	public String getReturnSortOfOperation() {
		// TODO Auto-generated method stub
		return returnSort;
	}

	@Override
	public void setIndexOfOp(int indexOfOp) {
		// TODO Auto-generated method stub
		this.indexOfOp = indexOfOp;
	}

	@Override
	public void setMainLotosOperation(LotosOperation mainLotosOperation) {
		// TODO Auto-generated method stub
		this.mainLotosOperation = mainLotosOperation;
	}

}
