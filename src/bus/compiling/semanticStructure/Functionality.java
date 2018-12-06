package bus.compiling.semanticStructure;

import java.util.List;

import bus.compiling.semanticStructure.typeDefinition.Sort;

public class Functionality {
	
	public static final double NO_EXIT = 1.899;
	public static final double EXIT = 2.899;
	public static final double DATA = 3.899;
	
	List<Sort> funcSorts = null;

	private double func = EXIT;
	
	public Functionality(double func) {
		super();
		this.func = func;
	}

	public List<Sort> getFuncSorts() {
		return funcSorts;
	}

	public void setFuncSorts(List<Sort> funcSorts) {
		this.funcSorts = funcSorts;
	}

	public double getFunc() {
		return func;
	}

	public void setFunc(double func) {
		this.func = func;
	}
	
	
	
}
