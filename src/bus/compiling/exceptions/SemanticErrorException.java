package bus.compiling.exceptions;

public class SemanticErrorException extends Exception {

	private static final long serialVersionUID = 1L;
	private String errorCause = "";
	private int tokenId = -1;
	
	public SemanticErrorException(String cause, int tokenId){
		this.errorCause = cause;
		this.tokenId = tokenId;
	}

	/**
	 * @return the wrongExpression
	 */
	public String getErrorCause() {
		return errorCause;
	}

	/**
	 * @return the positionOnText
	 */
	public int getTokenId() {
		return tokenId;
	}
	
}
