package bus.compiling.semanticStructure.syncronize;

import bus.compiling.Token;
import bus.compiling.semanticStructure.typeDefinition.Sort;

public class VariableDeclaration implements Offer{
	
	public Token variable = null;
	public Sort sort = null;
	
	private int variableIndex;
	private int sortIndex;
	
	public Token getVariable() {
		return variable;
	}
	public void setVariable(Token variable,int variableIndex) {
		this.variableIndex = variableIndex;
		this.variable = variable;
	}
	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort,int sortIndex) {
		this.sortIndex = sortIndex;
		this.sort = sort;
	}
	public int getVariableIndex() {
		return variableIndex;
	}
	public void setVariableIndex(int variableIndex) {
		this.variableIndex = variableIndex;
	}
	public int getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}
	@Override
	public String getOfferSort() {
		// TODO Auto-generated method stub
		return sort.getSortName();
	}
	
}