package bus.compiling.semanticStructure.typeDefinition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import books.LotosOperation;

public class EquationCollection {

	private OperatorCollection forallOperations = null;
	
	private Sort equationOfSort = null;
	
	private List<Equation> equations = null;
	
	public void addForallOperation(LotosOperation lotosOperation){
		if(forallOperations == null){
			forallOperations = new OperatorCollection();
		}
		forallOperations.addOperation(lotosOperation);
	}
	
	public OperatorCollection getForallOperations() {
		return forallOperations;
	}

	public void setForallOperations(OperatorCollection forallOperations) {
		this.forallOperations = forallOperations;
	}

	public List<Equation> getEquations() {
		return equations;
	}

	public void setEquations(List<Equation> equations) {
		this.equations = equations;
	}

	public Sort getEquationOfSort() {
		return equationOfSort;
	}

	public void setEquationOfSort(Sort equationOfSort) {
		this.equationOfSort = equationOfSort;
	}

	public boolean addEquation(Equation e) {
		
		if(equations == null){
			equations = new ArrayList<Equation>();
		}
		
		return equations.add(e);
	}

	public boolean addAllEquations(Collection<? extends Equation> c) {
		
		if(equations == null){
			equations = new ArrayList<Equation>();
		}
		
		return equations.addAll(c);
	}
	
	
	
}
