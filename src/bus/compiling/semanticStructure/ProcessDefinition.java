package bus.compiling.semanticStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bus.compiling.Token;
import bus.compiling.semanticStructure.syncronize.VariableDeclaration;
/*
 * 
 * processDefinition:
 * 
 * 	process 'typical_proc' '[gateList]' '{parameterList}':'functionality' :=
 * 		'behaviourExpression'
 * 	where
 * 		'typeDefinitions'
 * 		'processDefinitions'
 * 	endproc
 */
import bus.compiling.semanticStructure.typeDefinition.Sort;

public class ProcessDefinition implements Scope{

	//Name of specification
	private Token typicalProc;
	
	private int tokenId;
	
	//List of externally visible system gates
	private List <Gate> gateList = null;
	
	//List of system parameters definitions
	private List <VariableDeclaration> parameterList = null;
	
	//System Global functionality 
	private Functionality functionality = null;
		
	//System Behaviour expression
	private BehaviourExpression behaviourExpression = null;
	
	//System global type definitions
	private List<TypeDefinition> typeDefinitions = null;
	
	//System process definitions
	private List<ProcessDefinition> processDefinitions = null;

	//System existing sorts typefy variables.
	private List<Sort> scopeSorts = null;

	
	
	//Mark the actual inner subscope in the system on that analyser will get in
	private int indexOfActualScope = -1;
	
	//The dad scope of this scope
	private Scope dadScope = null;
		
	@Override
	public void addGate(Gate gate) {
		// TODO Auto-generated method stub
		if(gateList == null){
			gateList = new ArrayList<Gate>();
		}
		gateList.add(gate);
	}

	@Override
	public void addParameter(VariableDeclaration variableDeclaration) {
		// TODO Auto-generated method stub
		if(parameterList == null){
			parameterList = new ArrayList<VariableDeclaration>();
		}
		parameterList.add(variableDeclaration);
	}

	@Override
	public void addProcessDefinition(ProcessDefinition processDefinition) {
		// TODO Auto-generated method stub
		if(processDefinitions == null){
			processDefinitions = new ArrayList<ProcessDefinition>();
		}
		processDefinitions.add(processDefinition);
	}

	@Override
	public void addTypeDefinition(int id, TypeDefinition typeDefinition) {
		// TODO Auto-generated method stub
		if(typeDefinitions == null){
			typeDefinitions = new ArrayList<TypeDefinition>();
		}
		typeDefinitions.add(id,typeDefinition);
	}

	@Override
	public Scope getActualSubscope() {
		// TODO Auto-generated method stub
		if(processDefinitions == null || indexOfActualScope == -1 || indexOfActualScope >= processDefinitions.size()){
			return null;
		}else{
			return processDefinitions.get(indexOfActualScope);
		}
	}
	
	@Override
	public void renewActualSubscope() {
		// TODO Auto-generated method stub
		indexOfActualScope++;
	}

	@Override
	public BehaviourExpression getBehaviourExpression() {
		// TODO Auto-generated method stub
		return this.behaviourExpression;
	}

	@Override
	public Scope getDadScope() {
		// TODO Auto-generated method stub
		return this.dadScope;
	}

	@Override
	public Functionality getFunctionality() {
		// TODO Auto-generated method stub
		return this.functionality;
	}

	@Override
	public List<TypeDefinition> getTypeDefitions() {
		// TODO Auto-generated method stub
		return this.typeDefinitions;
	}

	@Override
	public void setBehaviourExpression(BehaviourExpression behaviourExpression) {
		// TODO Auto-generated method stub
		this.behaviourExpression = behaviourExpression;
	}

	@Override
	public void setFunctionality(Functionality functionality) {
		// TODO Auto-generated method stub
		this.functionality = functionality;
	}

	@Override
	public int getNumberOfGatesExternal() {
		// TODO Auto-generated method stub
		if(gateList == null){
			return 0;
		}else{
			return gateList.size();
		}
		
	}

	@Override
	public int getNumberOfParameters() {
		// TODO Auto-generated method stub
		if(parameterList == null){
			return 0;
		}else{
			return parameterList.size();
		}
	}

	public void setDadScope(Scope dadScope) {
		this.dadScope = dadScope;
	}

	@Override
	public Token getTitule() {
		// TODO Auto-generated method stub
		return typicalProc;
	}

	@Override
	public void setTitule(Token tituleToken) {
		// TODO Auto-generated method stub
		this.typicalProc = tituleToken;
	}

	@Override
	public List<Gate> getVisibleGates() {
		// TODO Auto-generated method stub
		return this.gateList;
	}

	@Override
	public void setVisibleGates(List<Gate> visibleGates) {
		// TODO Auto-generated method stub
		this.gateList = visibleGates;
	}
	
	public boolean addAllParameterList(Collection<? extends VariableDeclaration> c) {
		if(parameterList == null){
			parameterList = new ArrayList<VariableDeclaration>();
		}
		return parameterList.addAll(c);
	}
	
	@Override
	public boolean addAllTypeDefinitionList(
			Collection<? extends TypeDefinition> c) {
		// TODO Auto-generated method stub
		
		if(typeDefinitions == null){
			typeDefinitions = new ArrayList<TypeDefinition>();
		}
		return typeDefinitions.addAll(c);
		
	}

	@Override
	public void addSortToScope(Sort sort) {
		// TODO Auto-generated method stub
		if(scopeSorts == null){
			scopeSorts = new ArrayList<Sort>();
		}
		scopeSorts.add(sort);
	}

	@Override
	public List<Sort> getScopeSorts() {
		// TODO Auto-generated method stub
		return scopeSorts;
	}

	@Override
	public Sort searchForSortsInScope(Token sortToken) {
		// TODO Auto-generated method stub
		Sort resultSort = null;
		
		if(scopeSorts != null){
			for (int i = 0; i < scopeSorts.size(); i++) {
				if(scopeSorts.get(i).getSortName().equals(sortToken.getConteudo())){
					resultSort = scopeSorts.get(i);
					break;
				}
			}
		}
		
		return resultSort;
	}

	@Override
	public List<ProcessDefinition> getProcessDefinitions() {
		// TODO Auto-generated method stub
		return processDefinitions;
	}

	@Override
	public int getTokenId() {
		// TODO Auto-generated method stub
		return tokenId;
	}

	@Override
	public void setTokenId(int tokenId) {
		// TODO Auto-generated method stub
		this.tokenId = tokenId;
	}

	public List<VariableDeclaration> getParameterList() {
		return parameterList;
	}
			
}
