package bus.compiling.semanticStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bus.compiling.Token;
import bus.compiling.semanticStructure.syncronize.VariableDeclaration;
import bus.compiling.semanticStructure.typeDefinition.Sort;

/*
 * specification:
 * 
 * 	specification  'typical_spec' '[gateList]' '{parameterList}':'functionality'
 * 		'GlobalTypeDefinitions'
 * 	behaviour
 * 		'behaviourExpression'
 * 	where
 * 		'typeDefinitions'
 * 		'processDefinitions'
 * 	endspec
 * 
 */

public class Specification implements Scope{
	
	//Name of specification
	private Token typicalSpec;
	
	private int tokenId;
	
	//List of externally visible system gates
	private List <Gate> gateList = null;
	
	//List of system parameters definitions
	private List <VariableDeclaration> parameterList = null;
	
	//System Global functionality 
	private Functionality functionality = null;
	
	//System global type definitions
	private List<TypeDefinition> globalTypeDefinitions = null;
	
	//System Behaviour expression
	private BehaviourExpression behaviourExpression = null;
	
	//System global type definitions
	private List<TypeDefinition> typeDefinitions = null;
	
	//System process definitions
	private List<ProcessDefinition> processDefinitions = null;
	
	//Mark the actual inner subscope in the system on that analyser will get in
	private int indexOfActualScope = -1;
		
	//System existing sorts typefy variables.
	private List<Sort> innerScopeSorts = null;

	//System existing sorts typefy variables.
	private List<Sort> globalScopeSorts = null;

	
	/*
	 * 
	 * true - Must return innerTypeDefinitions
	 * 
	 * false - Must return globalTypeDefinitions
	 */
	private Boolean flagInnerOrGlobalTypeDefinition = false;

	@Override
	public List<TypeDefinition> getTypeDefitions() {
		// TODO Auto-generated method stub
		if(flagInnerOrGlobalTypeDefinition){
			return typeDefinitions;
		}else{
			return globalTypeDefinitions;
		}
	}

	@Override
	public Scope getDadScope() {
		// TODO Auto-generated method stub
		
			return null;
		
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

	
	/*
	 * true - Must add typeDefinition in innetTypeDefinitions
	 * 
	 * false - Must add typeDefinition in globalTypeDefinitions
	 */
	@Override
	public void addTypeDefinition(int id , TypeDefinition typeDefinition) {
		// TODO Auto-generated method stub
		if(flagInnerOrGlobalTypeDefinition){
			if(typeDefinitions == null){
				typeDefinitions = new ArrayList<TypeDefinition>();
			}
			typeDefinitions.add(id, typeDefinition);
		}else{
			if(globalTypeDefinitions == null){
				globalTypeDefinitions = new ArrayList<TypeDefinition>();
			}
			globalTypeDefinitions.add(id, typeDefinition);
		}
	}

	@Override
	public BehaviourExpression getBehaviourExpression() {
		// TODO Auto-generated method stub
		
		return behaviourExpression;
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
	public Functionality getFunctionality() {
		// TODO Auto-generated method stub
		return functionality;
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

	@Override
	public void setTitule(Token tituleToken) {
		// TODO Auto-generated method stub
		typicalSpec = tituleToken;
	}

	@Override
	public Token getTitule() {
		// TODO Auto-generated method stub
		return this.typicalSpec;
	}

	@Override
	public List<Gate> getVisibleGates() {
		// TODO Auto-generated method stub
		return gateList;
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
		if(flagInnerOrGlobalTypeDefinition){
			if(typeDefinitions == null){
				typeDefinitions = new ArrayList<TypeDefinition>();
			}
			return typeDefinitions.addAll(c);
		}else{
			if(globalTypeDefinitions == null){
				globalTypeDefinitions = new ArrayList<TypeDefinition>();
			}
			return  globalTypeDefinitions.addAll(c);
		}
	}
	
	public void globalToInnerTD(){
		flagInnerOrGlobalTypeDefinition = true;
	}

	public Boolean getFlagInnerOrGlobalTypeDefinition() {
		return flagInnerOrGlobalTypeDefinition;
	}

	public void setFlagInnerOrGlobalTypeDefinition(
			Boolean flagInnerOrGlobalTypeDefinition) {
		this.flagInnerOrGlobalTypeDefinition = flagInnerOrGlobalTypeDefinition;
	}

	@Override
	public void addSortToScope(Sort sort) {
		// TODO Auto-generated method stub
		if(flagInnerOrGlobalTypeDefinition){
			if(innerScopeSorts == null){
				innerScopeSorts = new ArrayList<Sort>();
			}
			innerScopeSorts.add(0,sort);
		}else{
			if(globalScopeSorts == null){
				globalScopeSorts = new ArrayList<Sort>();
			}
			globalScopeSorts.add(0,sort);
		}
		
		
	}

	@Override
	public List<Sort> getScopeSorts() {
		// TODO Auto-generated method stub
		if(flagInnerOrGlobalTypeDefinition){
			return innerScopeSorts;
		}else{
			return globalScopeSorts;
		}
	}

	@Override
	public Sort searchForSortsInScope(Token sortToken) {
		// TODO Auto-generated method stub
		Sort resultSort = null;
		
		if(flagInnerOrGlobalTypeDefinition){
			if(innerScopeSorts != null){
				for (int i = 0; i < innerScopeSorts.size(); i++) {
					if(innerScopeSorts.get(i).getSortName().equals(sortToken.getConteudo())){
						resultSort = innerScopeSorts.get(i);
						break;
					}
				}
			}
		}else{
			if(globalScopeSorts != null){
				for (int i = 0; i < globalScopeSorts.size(); i++) {
					if(globalScopeSorts.get(i).getSortName().equals(sortToken.getConteudo())){
						resultSort = globalScopeSorts.get(i);
						break;
					}
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
	
	public List<VariableDeclaration> getParameterList(){
		return null;
	}

}
