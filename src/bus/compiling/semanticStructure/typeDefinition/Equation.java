package bus.compiling.semanticStructure.typeDefinition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bus.compiling.OpTable;

public class Equation {

	private Sort ofSort = null;
	
	private OpTable leftEqnsOpTable = null;
	private OpTable rightEqnsOpTable = null;
	
	private List<OpTable> implicationBooleansEqnsOpTables = null;

	public Sort getOfSort() {
		return ofSort;
	}

	public void setOfSort(Sort ofSort) {
		this.ofSort = ofSort;
	}

	public OpTable getRightEqnsOpTable() {
		return rightEqnsOpTable;
	}

	public void setRightEqnsOpTable(OpTable rightEqnsOpTable) {
		this.rightEqnsOpTable = rightEqnsOpTable;
	}

	public List<OpTable> getImplicationBooleansEqnsOpTables() {
		return implicationBooleansEqnsOpTables;
	}

	public void setImplicationBooleansEqnsOpTables(
			List<OpTable> implicationBooleansEqnsOpTables) {
		this.implicationBooleansEqnsOpTables = implicationBooleansEqnsOpTables;
	}

	public OpTable getLeftEqnsOpTable() {
		return leftEqnsOpTable;
	}

	public void setLeftEqnsOpTable(OpTable leftEqnsOpTable) {
		this.leftEqnsOpTable = leftEqnsOpTable;
	}

	public void addImplicationBooleansEqnsOpTable(int arg0, OpTable arg1) {
		if(implicationBooleansEqnsOpTables == null){
			implicationBooleansEqnsOpTables = new ArrayList<OpTable>();
		}
		implicationBooleansEqnsOpTables.add(arg0, arg1);
	}

	public boolean addAllImplicationBooleansEqnsOpTables(Collection<? extends OpTable> arg0) {
		if(implicationBooleansEqnsOpTables == null){
			implicationBooleansEqnsOpTables = new ArrayList<OpTable>();
		}
		return implicationBooleansEqnsOpTables.addAll(arg0);
	}
	
	
	
}
