package bus.compiling.semanticStructure;

import java.util.Collection;
import java.util.List;

import bus.compiling.Token;
import bus.compiling.semanticStructure.syncronize.VariableDeclaration;
import bus.compiling.semanticStructure.typeDefinition.Sort;

public interface Scope {

	public List<TypeDefinition> getTypeDefitions();
	public Scope getDadScope();
	public Scope getActualSubscope();
	public void renewActualSubscope();
	
	public void setTitule(Token tituleToken);
	public Token getTitule();
	
	public BehaviourExpression getBehaviourExpression();
	public void setBehaviourExpression(BehaviourExpression behaviourExpression);
	
	public void addProcessDefinition(ProcessDefinition processDefinition);
	public void addTypeDefinition(int id , TypeDefinition typeDefinition);
	
	public boolean addAllParameterList(Collection<? extends VariableDeclaration> c);
	
	public void setFunctionality(Functionality functionality);
	public Functionality getFunctionality();
	
	public void setVisibleGates(List<Gate> visibleGates);
	public List<Gate> getVisibleGates();
	
	public void addGate(Gate gate);
	public void addParameter(VariableDeclaration variableDeclaration);
	
	public int getNumberOfGatesExternal();
	public int getNumberOfParameters();
	
	public boolean addAllTypeDefinitionList(Collection<? extends TypeDefinition> c);
	
	public void addSortToScope(Sort sort);
	public List<Sort> getScopeSorts();
	public Sort searchForSortsInScope(Token sortToken);
	
	public List<ProcessDefinition> getProcessDefinitions();
	
	public int getTokenId();
	public void setTokenId(int tokenId);
	
	public List<VariableDeclaration> getParameterList();
	
}
