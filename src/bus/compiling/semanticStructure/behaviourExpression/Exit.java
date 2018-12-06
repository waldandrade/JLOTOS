package bus.compiling.semanticStructure.behaviourExpression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bus.compiling.OpTable;
import bus.compiling.semanticStructure.BehaviourExpression;
import bus.compiling.semanticStructure.Scope;

public class Exit extends BehaviourExpression{

	List<OpTable> funcData = null;

	public Exit(Scope scope, int index){
		super(scope, index);
		this.setOperator(BehaviourExpression.EXIT);
	}
	
	public List<OpTable> getFuncData() {
		return funcData;
	}

	public void setFuncData(List<OpTable> funcData) {
		this.funcData = funcData;
	}

	public boolean add(OpTable e) {
		if(funcData == null)funcData = new ArrayList<OpTable>();
		return funcData.add(e);
	}

	public int size() {
		return funcData.size();
	}

	public boolean addAll(Collection<? extends OpTable> c) {
		if(funcData == null)funcData = new ArrayList<OpTable>();
		return funcData.addAll(c);
	}
	
	
	
}
