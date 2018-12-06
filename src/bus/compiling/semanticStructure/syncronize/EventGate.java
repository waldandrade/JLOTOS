package bus.compiling.semanticStructure.syncronize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bus.compiling.Token;
import bus.compiling.semanticStructure.Gate;

public class EventGate extends Gate{

	//public List<ValueDeclaration> valueDeclarations;
	//public List<VariableDeclaration> variableDeclarations;

	public List<Offer> offers;
	
	public EventGate(Token gateToken) {
		super(gateToken);
		// TODO Auto-generated constructor stub
	}

	public boolean add(ValueDeclaration arg0) {
		if(offers == null)
			offers = new ArrayList<Offer>();
		return offers.add(arg0);
	}

	public boolean addAllValueDeclarations(Collection<? extends Offer> arg0) {
		if(offers == null)
			offers = new ArrayList<Offer>();
		return offers.addAll(arg0);
	}

	public boolean add(VariableDeclaration arg0) {
		if(offers == null){
			offers = new ArrayList<Offer>();
		}
		return offers.add(arg0);
	}

	public boolean addAllVariableDeclarations(Collection<? extends Offer> c) {
		if(offers == null){
			offers = new ArrayList<Offer>();
		}
		return offers.addAll(c);
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	
	
	
	
}
