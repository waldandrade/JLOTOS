package bus.compiling.semanticStructure.syncronize;

import bus.compiling.OpTable;

public class ValueDeclaration implements Offer{
	
	private OpTable valueDeclarationTable = null;

	public OpTable getValueDeclarationTable() {
		return valueDeclarationTable;
	}

	public void setValueDeclarationTable(OpTable valueDeclarationTable) {
		this.valueDeclarationTable = valueDeclarationTable;
	}

	@Override
	public String getOfferSort() {
		// TODO Auto-generated method stub
		return valueDeclarationTable.getOpReturnSort();
	}
	
	
		
}
