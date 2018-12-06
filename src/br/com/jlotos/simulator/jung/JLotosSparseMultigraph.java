package br.com.jlotos.simulator.jung;

import bus.compiling.semanticStructure.Specification;
import edu.uci.ics.jung.graph.SparseMultigraph;

public class JLotosSparseMultigraph<E, V> extends SparseMultigraph<V, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Specification specification;

	public JLotosSparseMultigraph(Specification specification) {
		super();
		// TODO Auto-generated constructor stub
		
		this.specification = specification;
	}

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	
	
}
