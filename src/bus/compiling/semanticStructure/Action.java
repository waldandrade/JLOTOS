package bus.compiling.semanticStructure;

public class Action {
	
	private Gate actionGate = null;
	
	private SelectPredicate selectPredicate = null;

	public Action(Gate actionGate){
		this.actionGate = actionGate;
	}
	
	public Gate getActionGate() {
		return actionGate;
	}

	public void setActionGate(Gate actionGate) {
		this.actionGate = actionGate;
	}

	public SelectPredicate getSelectPredicate() {
		return selectPredicate;
	}

	public void setSelectPredicate(SelectPredicate selectPredicate) {
		this.selectPredicate = selectPredicate;
	}
	
	
	
}
