package bus.compiling;

import java.util.List;

public class OpTable {

	public static final String UNDEFINED_SORT =  "UNDEFINED";
	
	List <Op> opList;
	
	private int inicialIndex;
	private int finalIndex;
	
	public OpTable(int index){
		super();
		this.inicialIndex = index;
	}
	
	public String getOpReturnSort(){
		
		if(opList != null || opList.size() > 0)
		return opList.get(0).getReturnSortOfOperation();
		else	
		return UNDEFINED_SORT;
	}

	public List<Op> getOpList() {
		return opList;
	}

	public void setOpList(List<Op> opList) {
		this.opList = opList;
	}

	public int getInicialIndex() {
		return inicialIndex;
	}

	public void setInicialIndex(int inicialIndex) {
		this.inicialIndex = inicialIndex;
	}

	public int getFinalIndex() {
		return finalIndex;
	}

	public void setFinalIndex(int finalIndex) {
		this.finalIndex = finalIndex;
	}
	
}
