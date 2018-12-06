package bus.compiling.semanticStructure.typeDefinition;

import bus.compiling.semanticStructure.TypeDefinition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Sort implements Cloneable{

	public static final String UNDEFINED_SORT = "UNDEFINED_SORT";
	
	private String sortName = UNDEFINED_SORT;
	
	private TypeDefinition typeDefinitionReference = null;
	

	private List<EquationCollection> equationsOfSort = null;
	
	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public TypeDefinition getTypeDefinitionReference() {
		return typeDefinitionReference;
	}

	public void setTypeDefinitionReference(TypeDefinition typeDefinitionReference) {
		this.typeDefinitionReference = typeDefinitionReference;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	public Sort getSortClone(){
		
		try {
			return (Sort) this.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	public List<EquationCollection> getEquationsOfSort() {
		return equationsOfSort;
	}

	public void setEquationsOfSort(List<EquationCollection> equationsOfSort) {
		this.equationsOfSort = equationsOfSort;
	}

	public boolean addEquationsOfSort(EquationCollection e) {
		if(equationsOfSort == null){
			equationsOfSort = new ArrayList<EquationCollection>();
		}
		return equationsOfSort.add(e);
	}

	public boolean addAllEquationsOfSorts(Collection<? extends EquationCollection> c) {
		if(equationsOfSort == null){
			equationsOfSort = new ArrayList<EquationCollection>();
		}
		return equationsOfSort.addAll(c);
	}
	
	
}
