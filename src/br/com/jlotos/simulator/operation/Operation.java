package br.com.jlotos.simulator.operation;

import br.com.jlotos.simulator.jung.EventNode;

import edu.uci.ics.jung.graph.Graph;

public interface Operation {

	public void setSuperOperation(Operation operation);
	public Operation getSuperOperation();
	public void addOperation(Operation operation);
	public void addEvent(EventNode eventNode);
	public void realizeOperation(EventNode eventNode);
	public void cleanNodesOp(Operation operation);
	public void setGraphContext(Graph graph);
	public void clean(EventNode eventNode);
	public void clean(Operation operation);
	
	//Métodos referentes a passagem do contexto de sincronismo
	
}
