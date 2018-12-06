package bus.compiling;

import java.util.ArrayList;
import java.util.List;

import books.LotosOperation;

public class ParameterizedOp implements Op {

	private int indexOfOp = 0;
	private LotosOperation mainLotosOperation;
	List <OpTable> parametersOpTables;
	private int numberOfParameters = 0;
	
	@Override
	public int getIndexOfOp() {
		// TODO Auto-generated method stub
		return this.indexOfOp;
	}

	@Override
	public String getReturnSortOfOperation() {
		// TODO Auto-generated method stub
		return mainLotosOperation.returnSort;
	}

	@Override
	public void setIndexOfOp(int indexOfOp) {
		// TODO Auto-generated method stub
		this.indexOfOp = indexOfOp;
	}

	public LotosOperation getMainLotosOperation() {
		return mainLotosOperation;
	}

	public void setMainLotosOperation(LotosOperation mainLotosOperation) {
		this.mainLotosOperation = mainLotosOperation;
	}

	public List<OpTable> getParametersOpTables() {
		return parametersOpTables;
	}

	public void setParametersOpTables(List<OpTable> parametersOpTables) {
		this.parametersOpTables = parametersOpTables;
	}

	public int getNumberOfParameters() {
		return numberOfParameters;
	}

	public void setNumberOfParameters(int numberOfParameters) {
		this.numberOfParameters = numberOfParameters;
	}
	
	public List<String> getParametersReturnSortList(){
		
		if(parametersOpTables == null){
			return null;
		}else{
			
			List<String> parametersReturnSorts = new ArrayList<String>();
			
			for (OpTable parameterOpTable : parametersOpTables) {
				parametersReturnSorts.add(parameterOpTable.getOpReturnSort());
			}
			
			return parametersReturnSorts;
		}
	}

}
