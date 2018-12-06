package br.com.jlotos.simulator.sync;

import br.com.jlotos.simulator.jung.EventNode;

public class AliasResolver {
	
	private AliasResolver origAlias;
	private EventNode origEventNode;
		
	public AliasResolver(AliasResolver origAlias){
		this.origAlias = origAlias;
	}
	
	public AliasResolver(EventNode origEventNode){
		this.origEventNode = origEventNode;
	}
	
	public AliasResolver getOrigAlias() {
		return origAlias;
	}
	public void setOrigAlias(AliasResolver origAlias) {
		this.origAlias = origAlias;
	}
	public EventNode getOrigEventNode() {
		return origEventNode;
	}
	public void setOrigEventNode(EventNode origEventNode) {
		this.origEventNode = origEventNode;
	}
	
	public boolean checkContextEvents(){
		boolean flag = false;
		
		if(origEventNode != null){
			flag = origEventNode.checkContextEvents();	
		}
		
		if(flag && origAlias != null){
			flag = origAlias.checkContextEvents();
		}
		
		return flag;
	}
	
}
