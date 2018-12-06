package bus.compiling;

import bus.compiling.semanticStructure.Scope;

public class SemanticTable {
	
	public Scope globalScope;
	
	public Scope seenScope;
	
	
	public SemanticTable(Scope tableScope){
		
		globalScope = tableScope;
		seenScope = tableScope;
		
	}
	
	public Scope getSeenScope(){
		return seenScope;
	}

	public Scope getGlobalScope() {
		return globalScope;
	}

	public void setGlobalScope(Scope globalScope) {
		this.globalScope = globalScope;
	}

	public void setSeenScope(Scope seenScope) {
		this.seenScope = seenScope;
	}

	
}
