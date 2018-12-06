package bus.compiling.exceptions;

import java.util.ArrayList;
import java.util.List;

public class SemanticProblem {

	private List<SemanticErrorException> listOfSemanticError = new ArrayList<SemanticErrorException>();
	private List<PossibleDeadLock> listOfPossibleDeadLocks = new ArrayList<PossibleDeadLock>();
	
	public List<SemanticErrorException> getListOfSemanticError() {
		return listOfSemanticError;
	}
	public void setListOfSemanticError(
			List<SemanticErrorException> listOfSemanticError) {
		this.listOfSemanticError = listOfSemanticError;
	}
	public List<PossibleDeadLock> getListOfPossibleDeadLocks() {
		return listOfPossibleDeadLocks;
	}
	public void setListOfPossibleDeadLocks(
			List<PossibleDeadLock> listOfPossibleDeadLocks) {
		this.listOfPossibleDeadLocks = listOfPossibleDeadLocks;
	}
	public boolean addPossibleDeadLock(PossibleDeadLock e) {
		return listOfPossibleDeadLocks.add(e);
	}
	public boolean addSemanticErrorException(SemanticErrorException e) {
		return listOfSemanticError.add(e);
	}
	
	
	
	
}
