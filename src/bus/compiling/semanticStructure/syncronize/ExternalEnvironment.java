package bus.compiling.semanticStructure.syncronize;

import java.util.ArrayList;
import java.util.Collection;

import bus.compiling.semanticStructure.Gate;

public class ExternalEnvironment {

	/*
	 * Must test if have a
	 */
	public static ArrayList <Gate> syncEventGates = null;
	public static Gate syncPoint = null;
	
	
	public static ArrayList<Gate> getSyncEventGates() {
		return syncEventGates;
	}

	public static void setSyncEventGates(ArrayList<Gate> syncEventGates) {
		ExternalEnvironment.syncEventGates = syncEventGates;
	}
	
	

	public static Gate getSyncPoint() {
		return syncPoint;
	}

	public static void setSyncPoint(Gate syncPoint) {
		ExternalEnvironment.syncPoint = syncPoint;
	}
	
	public static void cleanExternalEnvironment(){
		syncEventGates = null;
		syncPoint = null;
	}

	public static boolean addSyncEventGate(Gate e) {
		if(syncEventGates == null){
			syncEventGates = new ArrayList<Gate>();
		}
		return syncEventGates.add(e);
	}

	public static boolean addAllSyncEventGates(Collection<? extends Gate> c) {
		if(syncEventGates == null){
			syncEventGates = new ArrayList<Gate>();
		}
		return syncEventGates.addAll(c);
	}
}
