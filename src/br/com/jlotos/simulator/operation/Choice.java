package br.com.jlotos.simulator.operation;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;

import br.com.jlotos.simulator.jung.EventNode;


public class Choice implements Operation {

	private Graph graph;
	private List<Operation> operations = new ArrayList<Operation>();
	private List<EventNode> events = new ArrayList<EventNode>();
	private Operation superOperation = null;
	
	public Choice(Graph graph){
		this.graph = graph;
	}
	
	@Override
	public void addEvent(EventNode eventNode) {
		// TODO Auto-generated method stub
		events.add(eventNode);
	}
	
	@Override
	public void addOperation(Operation operation) {
		// TODO Auto-generated method stub
		operations.add(operation);
	}	
	
	/**
	 * 
	 * Returns true if find the eventNode that was clicked, and so, will not be removed.
	 */
	public boolean cleanNodes(EventNode eventNode) {
		// TODO Auto-generated method stub
		
		boolean flag = false;
		
		for (int i = operations.size()-1; i >= 0; i--) {
			operations.get(i).cleanNodesOp(null);
			operations.remove(i);
		}
		
		for (int i = events.size()-1; i >= 0; i--) {
			if(eventNode != events.get(i)){
				graph.removeVertex(events.get(i));
				events.remove(i);
			}else{
				flag = true;
			}
		}
		
		return flag;
		
	}
	
	@Override
	public void realizeOperation(EventNode eventNode) {
		// TODO Auto-generated method stub
		
		cleanNodes(eventNode);
		
	}

	@Override
	public void setGraphContext(Graph graph) {
		// TODO Auto-generated method stub
		this.graph = graph;
	}

	@Override
	public void setSuperOperation(Operation operation) {
		// TODO Auto-generated method stub
		this.superOperation = operation;
	}

	@Override
	public Operation getSuperOperation() {
		// TODO Auto-generated method stub
		return superOperation;
	}

	@Override
	public void cleanNodesOp(Operation operation) {
		// TODO Auto-generated method stub
		
		for (int i = operations.size()-1; i >= 0; i--) {
			if(operation == null || operations.get(i) != operation){
				operations.get(i).cleanNodesOp(null);
				operations.remove(i);
			}
		}
		
		for (int i = events.size()-1; i >= 0; i--) {
			graph.removeVertex(events.get(i));
			events.remove(i);
		}		
		
	}

	@Override
	public void clean(EventNode eventNode) {
		// TODO Auto-generated method stub
		cleanNodes(eventNode);
		if(superOperation != null){
			superOperation.clean(this);
		}
	}

	@Override
	public void clean(Operation operation) {
		// TODO Auto-generated method stub
		cleanNodesOp(operation);
		if(superOperation != null){
			superOperation.clean(this);
		}
	}

}
