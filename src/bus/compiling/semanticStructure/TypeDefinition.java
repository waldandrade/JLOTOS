package bus.compiling.semanticStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import books.LotosOperation;
import bus.compiling.Token;
import bus.compiling.semanticStructure.typeDefinition.EquationCollection;
import bus.compiling.semanticStructure.typeDefinition.OperatorCollection;
import bus.compiling.semanticStructure.typeDefinition.Sort;

public class TypeDefinition {
	
	private Token typeTitule = null;
	
	private List<Sort> sorts = null;
	
	private OperatorCollection operatorCollection = null;
	
	private List<EquationCollection> equationCollections = null;
	
	private List<TypeDefinition> extendingTypeDefinition = null;



	public OperatorCollection getOperatorCollection() {
		return operatorCollection;
	}

	public void setOperatorCollection(OperatorCollection operatorCollection) {
		this.operatorCollection = operatorCollection;
	}

	public List<EquationCollection> getEquationCollections() {
		return equationCollections;
	}

	public void setEquationCollections(List<EquationCollection> equationCollections) {
		this.equationCollections = equationCollections;
	}

	public Token getTypeTitule() {
		return typeTitule;
	}

	public void setTypeTitule(Token typeTitule) {
		this.typeTitule = typeTitule;
	}

	public List<TypeDefinition> getExtendingTypeDefinition() {
		return extendingTypeDefinition;
	}

	public void setExtendingTypeDefinition(
			List<TypeDefinition> extendingTypeDefinition) {
		this.extendingTypeDefinition = extendingTypeDefinition;
	}

	public boolean addEquantionCollection(EquationCollection e) {
		if(equationCollections == null){
			equationCollections = new ArrayList<EquationCollection>();
		}
		return equationCollections.add(e);
	}

	public void addOperation(LotosOperation lotosOperation) {
		operatorCollection.addOperation(lotosOperation);
	}

	public List<Sort> getSorts() {
		return sorts;
	}

	public void setSorts(List<Sort> sorts) {
		this.sorts = sorts;
	}

	public boolean addSort(Sort e) {
		if(sorts == null){
			sorts = new ArrayList<Sort>();
		}
		
		return sorts.add(e);
	}

	public boolean addAllSorts(Collection<? extends Sort> arg0) {
		if(sorts == null){
			sorts = new ArrayList<Sort>();
		}
		return sorts.addAll(arg0);
	}
	
	
	
}
