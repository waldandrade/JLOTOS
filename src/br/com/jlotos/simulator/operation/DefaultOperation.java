package br.com.jlotos.simulator.operation;

import java.util.ArrayList;
import java.util.List;

import br.com.jlotos.simulator.jung.EventNode;
import edu.uci.ics.jung.graph.Graph;

public class DefaultOperation implements Operation {

	private Graph graph;
	private List<Operation> operations = new ArrayList<Operation>();
	private List<EventNode> events = new ArrayList<EventNode>();
	private Operation superOperation = null;

	public DefaultOperation(Graph graph){
		this.graph = graph;
	}

	@Override
	public void addOperation(Operation operation) {
		// TODO Auto-generated method stub
		operations.add(operation);
	}

	@Override
	public void addEvent(EventNode eventNode) {
		// TODO Auto-generated method stub
		events.add(eventNode);
	}

	@Override
	public void realizeOperation(EventNode eventNode) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setGraphContext(Graph graph) {
		// TODO Auto-generated method stub
		this.graph = graph;
	}

	@Override
	public Operation getSuperOperation() {
		// TODO Auto-generated method stub
		return superOperation;
	}

	@Override
	public void setSuperOperation(Operation operation) {
		// TODO Auto-generated method stub
		this.superOperation = operation;
	}

	@Override
	public void cleanNodesOp(Operation operation) {
		// TODO Auto-generated method stub

		if(operation == null){
			for (int i = operations.size()-1; i >= 0; i--) {
					operations.get(i).cleanNodesOp(null);
					operations.remove(i);
			}
	
			for (int i = events.size()-1; i >= 0; i--) {
				graph.removeVertex(events.get(i));
				events.remove(i);
			}

		}
		
	}

	@Override
	public void clean(EventNode eventNode) {
		// TODO Auto-generated method stub
		if(superOperation != null){
			superOperation.clean(this);
		}		
	}

	@Override
	public void clean(Operation operation) {
		// TODO Auto-generated method stub
		if(superOperation != null){
			superOperation.clean(this);
		}
	}
	
}