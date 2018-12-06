package bus.compiling.semanticStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bus.SystemHandler;
import bus.compiling.OpTable;
import bus.compiling.Token;
import bus.compiling.semanticStructure.syncronize.EventGate;

public class BehaviourExpression {


	public static final double EXIT = 0.001;
	public static final String EXIT_STR = "exit";
	
	public static final double STOP = 0.002;
	public static final String STOP_STR = "stop";
	
	public static final double PROCESS_INSTANTIATION = 0.003;
	public static final String PROCESS_INSTANTIATION_STR = "process";
	
	public static final double PARENTHESIS = 0.999;
	public static final String PARENTHESIS_STR = "(...)";
	
	public static final double ACTION_PREFIX = 1.999;
	public static final String ACTION_PREFIX_STR = ";";
		
	public static final double CHOICE = 2.999;
	public static final String CHOICE_STR = "[]";
	
	public static final double PARALLEL_COMPOSITION = 3.999;
	public static final String PARALLEL_COMPOSITION_STR = "|||,||,|[...]|";
	
	public static final double DISABLING = 4.999; 
	public static final String DISABLING_STR = "[>"; 
	
	public static final double ENABLING = 5.999;
	public static final String ENABLING_STR = ">>";
	
	public static final double GUARD = 6.999;
	public static final String GUARD_STR = "[.]->";
	
	public static final double HIDING = 7.999;
	public static final String HIDING_STR = "hide";
	
	public static final float PARTIAL_SYNC = 3.9991f;
	public static final String PARTIAL_SYNC_STR = "|[...]|";
	
	public static final float FULL_SYNC = 3.9992f;
	public static final String FULL_SYNC_STR = "||";
	
	public static final float PARALLEL = 3.9993f;
	public static final String PARALLEL_STR = "|||";
	
	private double operator = 0;
	private String operatorDescription = "UNDEFINED";
	
	private float parallelCompositionType = 0;
	private String parallelCompositionTypeDescription = "UNDEFINED";
	
	//used in Binary operators like CHOICE, PARALLEL_COMPOSITION, DISABLING, ENABLING
	private BehaviourExpression leftBehaviourExpression = null;
	
	//used in Binary operators, and in unary operators like ACTION_PREFIX
	//this will be used to get the on parenthesis behaviour expression
	private BehaviourExpression rightBehaviourExpression = null;
		
	private BehaviourExpression behaviourExpressionDad = null;
	
	private Scope scope = null;
	
	//defines the process referenced by the instance
	private ProcessDefinition processDefinition = null;
	
	//Action prefix
	
	//used to identify the action that precede um behaviour expression
	private Action prefixedAction = null;
	
	public Action getPrefixedAction() {
		return prefixedAction;
	}

	public void setPrefixedAction(Action prefixedAction) {
		this.prefixedAction = prefixedAction;
	}
	
	//FIM action prefix
	
	private int index;
	
	//HIDE
	
	//used to list the hidden gates
	private List<Gate> hiddenGates = null;
	

	public List<Gate> getHiddenGates() {
		return hiddenGates;
	}

	public void setHiddenGates(List<Gate> hiddenGates) {
		this.hiddenGates = hiddenGates;
	}


	public boolean addHiddenGate(Gate arg0) {
		if(hiddenGates == null){
			hiddenGates = new ArrayList<Gate>();
		}
		return hiddenGates.add(arg0);
	}

	public boolean addAllHiddenGates(Collection<? extends Gate> arg0) {
		if(hiddenGates == null){
			hiddenGates = new ArrayList<Gate>();
		}
		return hiddenGates.addAll(arg0);
	}

	
	
	//FIM HIDE
	
	private Token processNameToken = null;
	
	private SelectPredicate selectPredicate = null;
	
	
	private List<Gate> processGates = null;
	private List<Gate> syncEventGates = null;
	
	private List<OpTable> parsingOpTables = null;
	
	public BehaviourExpression (Scope scope, int index){
		super();
		this.scope = scope;
		this.index = index;
	}
	
	public BehaviourExpression getLeftBehaviourExpression(){
		return leftBehaviourExpression;
	}
	public BehaviourExpression getRightBehaviourExpression(){
		return rightBehaviourExpression;
	}
	
	public void setLeftBehaviourExpression(BehaviourExpression leftBehaviourExpression){
		this.leftBehaviourExpression = leftBehaviourExpression;
		this.leftBehaviourExpression.setBehaviourExpressionDad(this);
	}
	
	public void setRightBehaviourExpression(BehaviourExpression rightBehaviourExpression){
		this.rightBehaviourExpression = rightBehaviourExpression;
		this.rightBehaviourExpression.setBehaviourExpressionDad(this);
	}

	public double getOperator() {
		return operator;
	}

	public void setOperator(double operator) {
		this.operator = operator;
		
		if(operator == BehaviourExpression.ACTION_PREFIX){
			operatorDescription = BehaviourExpression.ACTION_PREFIX_STR;
		}else if(operator == BehaviourExpression.CHOICE){
			operatorDescription = BehaviourExpression.CHOICE_STR;			
		}else if(operator == BehaviourExpression.DISABLING){
			operatorDescription = BehaviourExpression.DISABLING_STR;			
		}else if(operator == BehaviourExpression.ENABLING){
			operatorDescription = BehaviourExpression.ENABLING_STR;			
		}else if(operator == BehaviourExpression.EXIT){
			operatorDescription = BehaviourExpression.EXIT_STR;			
		}else if(operator == BehaviourExpression.GUARD){
			operatorDescription = BehaviourExpression.GUARD_STR;			
		}else if(operator == BehaviourExpression.HIDING){
			operatorDescription = BehaviourExpression.HIDING_STR;			
		}else if(operator == BehaviourExpression.PARALLEL_COMPOSITION){
			operatorDescription = BehaviourExpression.PARALLEL_COMPOSITION_STR;			
		}else if(operator == BehaviourExpression.PARENTHESIS){
			operatorDescription = BehaviourExpression.PARENTHESIS_STR;			
		}else if(operator == BehaviourExpression.PROCESS_INSTANTIATION){
			operatorDescription = BehaviourExpression.PROCESS_INSTANTIATION_STR;			
		}else if(operator == BehaviourExpression.STOP){
			operatorDescription = BehaviourExpression.STOP_STR;			
		}
		
	}
	
	public void setOperator(Token operatorToken) {
		if(operatorToken.getConteudo().equals(";")){
			this.operator = BehaviourExpression.ACTION_PREFIX;
		}else if (operatorToken.getConteudo().equals("[]")){
			this.operator = BehaviourExpression.CHOICE;
		}else if (operatorToken.getConteudo().equals("||") || operatorToken.getConteudo().equals("|||") || operatorToken.getTipo().equals(SystemHandler.lotosBook.PARTIAL_SYNCHRONIZATION)){
			this.operator = BehaviourExpression.PARALLEL_COMPOSITION;
		}else if (operatorToken.getConteudo().equals("[>")){
			this.operator = BehaviourExpression.DISABLING;
		}else if (operatorToken.getConteudo().equals(">>")){
			this.operator = BehaviourExpression.ENABLING;
		}else if (operatorToken.getTipo().equals(SystemHandler.lotosBook.HIDING)){
			this.operator = BehaviourExpression.HIDING;
		}else if(operatorToken.getTipo().equals(SystemHandler.lotosBook.GUARD)){
			
		}
	}
	
	
	
	public SelectPredicate getSelectPredicate() {
		return selectPredicate;
	}
	public void setSelectPredicate(SelectPredicate selectPredicate) {
		this.selectPredicate = selectPredicate;
	}
	public boolean addProcessGate(Gate e) {
		if(processGates == null)
			processGates = new ArrayList<Gate>();
		return processGates.add(e);
	}
	public boolean addAllProcessGates(Collection<? extends Gate> c) {
		if(processGates == null)
			processGates = new ArrayList<Gate>();
		return processGates.addAll(c);
	}
	public List<Gate> getProcessGates() {
		return processGates;
	}
	public void setProcessGates(List<Gate> processGates) {
		this.processGates = processGates;
	}
	public List<OpTable> getParsingOpTables() {
		return parsingOpTables;
	}
	public void setParsingOpTables(List<OpTable> parsingOpTables) {
		this.parsingOpTables = parsingOpTables;
	}
	public boolean addAllParsingOpTables(Collection<? extends OpTable> c) {
		if(parsingOpTables == null)
			parsingOpTables = new ArrayList<OpTable>();
		return parsingOpTables.addAll(c);
	}
	public boolean addParsingOpTables(OpTable e) {
		if(parsingOpTables == null)
			parsingOpTables = new ArrayList<OpTable>();
		return parsingOpTables.add(e);
	}
	public float getParallelCompositionType() {
		return parallelCompositionType;
	}
	public void setParallelCompositionType(float parallelCompositionType) {
		
		if(parallelCompositionType == BehaviourExpression.FULL_SYNC){
			parallelCompositionTypeDescription = FULL_SYNC_STR;
			operatorDescription = FULL_SYNC_STR;
		}else if(parallelCompositionType == BehaviourExpression.PARALLEL){
			parallelCompositionTypeDescription = PARALLEL_STR;
			operatorDescription = PARALLEL_STR;
		}else if(parallelCompositionType == BehaviourExpression.PARTIAL_SYNC){
			parallelCompositionTypeDescription = PARTIAL_SYNC_STR;
			operatorDescription = PARTIAL_SYNC_STR;
		}
		
		this.parallelCompositionType = parallelCompositionType;
	}
	public List<Gate> getSyncEventGates() {
		return syncEventGates;
	}
	public void setSyncEventGates(List<Gate> syncEventGates) {
		this.syncEventGates = syncEventGates;
	}
	public boolean addSyncEventGates(Gate arg0) {
		if(syncEventGates == null)
			syncEventGates = new ArrayList<Gate>();
		return syncEventGates.add(arg0);
	}
	public boolean addAllSyncEventGates(Collection<? extends Gate> arg0) {
		if(syncEventGates == null)
			syncEventGates = new ArrayList<Gate>();
		
		List <Gate> aux = new ArrayList<Gate>();
		
		for (Gate gate : arg0) {
			EventGate eventGate = new EventGate(gate.getGateToken());
			eventGate.setTokenId(gate.getTokenId());
			aux.add(eventGate);
		}
		
		return syncEventGates.addAll(aux);
	}
	public void setOperatorDescription(String operatorDescription) {
		this.operatorDescription = operatorDescription;
	}
	public String getOperatorDescription() {
		return operatorDescription;
	}
	public void setParallelCompositionTypeDescription(
			String parallelCompositionTypeDescription) {
		this.parallelCompositionTypeDescription = parallelCompositionTypeDescription;
	}
	public String getParallelCompositionTypeDescription() {
		return parallelCompositionTypeDescription;
	}
	public void setProcessNameToken(Token processNameToken) {
		
		this.processNameToken = processNameToken;
	}
	public Token getProcessNameToken() {
		return processNameToken;
	}
	public BehaviourExpression getBehaviourExpressionDad() {
		return behaviourExpressionDad;
	}
	public void setBehaviourExpressionDad(BehaviourExpression behaviourExpressionDad) {
		this.behaviourExpressionDad = behaviourExpressionDad;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}
	
	
}
