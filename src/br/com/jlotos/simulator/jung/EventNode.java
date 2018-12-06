package br.com.jlotos.simulator.jung;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;

import books.LotosBook;
import br.com.jlotos.simulator.operation.Choice;
import br.com.jlotos.simulator.operation.DefaultOperation;
import br.com.jlotos.simulator.operation.Operation;
import br.com.jlotos.simulator.sync.AliasResolver;
import br.com.jlotos.simulator.sync.SyncContext;
import bus.SystemHandler;
import bus.compiling.semanticStructure.BehaviourExpression;
import bus.compiling.semanticStructure.Scope;
import bus.compiling.semanticStructure.syncronize.EventGate;

public class EventNode {
	
	public static final String START_NODE = "STARTNODE";
	
	private String nodeType = "UNKNOWN";
	
	private String nodeDescription = "UNKNOWN";
	
	private BehaviourExpression behaviour;
	
	private boolean availableEvent = true;
	
	private Scope scope = null;
	
	private Operation operation = null;
	
	private Graph graph = null;
	
	private SyncContext syncContext;
	
	private AliasResolver aliasResolver;
	
	private boolean syncronized = false;
	
	public EventNode(String nodeType, String nodeDescription, Graph graph){
		this.nodeType = nodeType;
		this.nodeDescription = nodeDescription;
		this.graph = graph;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeDescription() {
		return nodeDescription;
	}

	public void setNodeDescription(String nodeDescription) {
		this.nodeDescription = nodeDescription;
	}
	
	public String toString(){
		return nodeDescription;
	}
	
	public boolean isAvailableEvent() {
		return availableEvent;
	}

	public void setAvailableEvent(boolean availableEvent) {
		this.availableEvent = availableEvent;
	}
	
	public List<EventNode> getNextNodes(BehaviourExpression behaviour, Operation operation){
		
		List<EventNode> eventsNode = new ArrayList<EventNode>();
		
		
		Operation op = null;
		
		if(behaviour.getOperator() == BehaviourExpression.CHOICE){
			op = new Choice(graph);
		}else{
			op = new DefaultOperation(graph);
		}
		if(operation != null){
			op.setSuperOperation(operation);
			operation.addOperation(op);
		}
		
		if(behaviour.getLeftBehaviourExpression() != null){
			
			eventsNode.addAll(getNextEventNodes(behaviour.getLeftBehaviourExpression(), op));
			
		}
		
		if(behaviour.getRightBehaviourExpression() != null){
			
			eventsNode.addAll(getNextEventNodes(behaviour.getRightBehaviourExpression(), op));
			
		}
		
		return eventsNode;
	}
	
	public List<EventNode> getNextEventNodes(BehaviourExpression behaviour, Operation operation){
		List<EventNode> eventsNode = new ArrayList<EventNode>();
		
		if(behaviour.getOperator() == BehaviourExpression.ACTION_PREFIX
				&& behaviour.getPrefixedAction().getActionGate().getGateToken().getTipo() != SystemHandler.lotosBook.INTERNAL_EVENT){
			EventNode newEventNode = new EventNode("Event", behaviour.getPrefixedAction().getActionGate().getGateToken().getConteudo(), graph);
			newEventNode.setBehaviour(behaviour);
			newEventNode.setScope(behaviour.getScope());
			newEventNode.setOperation(operation);
			operation.addEvent(newEventNode);
			eventsNode.add(newEventNode);
		}else if(behaviour.getOperator() ==  BehaviourExpression.PROCESS_INSTANTIATION){
			eventsNode.addAll(getNextEventNodes(behaviour.getProcessDefinition().getBehaviourExpression(), operation));			
		}else{
			eventsNode.addAll(getNextNodes(behaviour, operation));
		}

		return eventsNode;
	}

	public BehaviourExpression getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(BehaviourExpression behaviour) {
		this.behaviour = behaviour;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public SyncContext getSyncContext() {
		return syncContext;
	}

	public void setSyncContext(SyncContext syncContext) {
		this.syncContext = syncContext;
	}

	public AliasResolver getAliasResolver() {
		return aliasResolver;
	}

	public void setAliasResolver(AliasResolver aliasResolver) {
		this.aliasResolver = aliasResolver;
	}

	public boolean isSyncronized() {
		return syncronized;
	}

	public void setSyncronized(boolean syncronized) {
		this.syncronized = syncronized;
	}	
	
	public boolean checkContextEvents(){
		
		boolean flag = true;
		
		if(syncContext != null){
			flag = syncContext.checkContextEvents();
		}
		
		if(flag && aliasResolver != null){
			flag = aliasResolver.checkContextEvents();
		}
		
		return flag;
	}
	
}
