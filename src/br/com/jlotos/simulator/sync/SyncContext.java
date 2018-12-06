package br.com.jlotos.simulator.sync;

import java.util.ArrayList;
import br.com.jlotos.simulator.jung.EventNode;
import java.util.List;

public class SyncContext {
	
	private List<EventNode> contextEvents = new ArrayList<EventNode>();
		
	public List<EventNode> getContextEvents() {
		return contextEvents;
	}

	public void setContextEvents(List<EventNode> contextEvents) {
		this.contextEvents = contextEvents;
	}
	
	public boolean checkContextEvents(){
		boolean flag = true;
		for(EventNode event : contextEvents){
			if(!event.isSyncronized()){
				return false;
			}
		}
		return flag;
	}
	
}
