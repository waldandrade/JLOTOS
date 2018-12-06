package bus.compiling.semanticStructure.typeDefinition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import books.LotosOperation;

public class OperatorCollection {


	public List<LotosOperation> possibleOperations = null;
	public OperatorCollection operatorCollectionDad = null;
	public static final int NO_MORE_REQUIRED = 0;
	public static final int PARAM_REQUIRED = 1;
	public static final int RIGHT_OPERAND_REQUIRED = 2;
	
	
		
	public void addOperation(LotosOperation lotosOperation){
		if(possibleOperations==null)possibleOperations= new ArrayList<LotosOperation>();
		possibleOperations.add(lotosOperation);
	}



	public boolean addAllOperation(Collection<? extends LotosOperation> c) {
		if(possibleOperations==null)possibleOperations= new ArrayList<LotosOperation>();
		return possibleOperations.addAll(c);
	}



	public List<LotosOperation> getPossibleOperations() {
		return possibleOperations;
	}



	public void setPossibleOperations(List<LotosOperation> possibleOperations) {
		this.possibleOperations = possibleOperations;
	}
	
	
	
}
