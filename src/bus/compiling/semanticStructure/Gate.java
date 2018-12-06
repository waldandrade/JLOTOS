package bus.compiling.semanticStructure;

import bus.compiling.Token;


public class Gate{
	
	
	public static final String HIDEN_GATE = "HIDEN_GATE";
	public static final String VISIBLE_GATE = "VISIBLE_GATE";	
	
	private Token gateToken = null;
	private int tokenId;

	private String gateVisibility = VISIBLE_GATE;
	
	private Gate gateBridge = null;
		
	private SelectPredicate selectPredicate = null;
	
	public Gate(Token gateToken) {
		super();
		this.gateToken = gateToken;
	}
	public Gate(Token gateToken, String gateVisibility) {
		super();
		this.gateToken = gateToken;
		this.gateVisibility = gateVisibility;
	}

	public Token getGateToken() {
		return gateToken;
	}

	public void setGateToken(Token gateToken) {
		this.gateToken = gateToken;
	}
	public String getGateVisibility() {
		return gateVisibility;
	}
	public void setGateVisibility(String gateVisibility) {
		this.gateVisibility = gateVisibility;
	}
	public SelectPredicate getSelectPredicate() {
		return selectPredicate;
	}
	public void setSelectPredicate(SelectPredicate selectPredicate) {
		this.selectPredicate = selectPredicate;
	}
	
	public int getTokenId(){
		return tokenId;
	}
	
	public void setTokenId(int tokenId){
		this.tokenId = tokenId;
	}
	public Gate getGateBridge() {
		return gateBridge;
	}
	public void setGateBridge(Gate gateBridge) {
		this.gateBridge = gateBridge;
	}

}
