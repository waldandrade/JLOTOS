package bus.compiling.exceptions;

import java.util.ArrayList;

import bus.compiling.Token;

public class LexicalErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCause = "";
	private ArrayList<Integer> wrongTokensIds = new ArrayList<Integer>();
	
	private ArrayList<ArrayList<Token>> tokenGroup = new ArrayList<ArrayList<Token>>();
	
	
	
	/**
	 * @return the tokenGroup
	 */
	public ArrayList<ArrayList<Token>> getTokenGroup() {
		return tokenGroup;
	}

	/**
	 * @param tokenGroup the tokenGroup to set
	 */
	public void setTokenGroup(ArrayList<ArrayList<Token>> tokenGroup) {
		this.tokenGroup = tokenGroup;
	}

	public LexicalErrorException(String cause, ArrayList<Integer> wrongTokensIds, ArrayList<Token> tokens, ArrayList<Token> commentTokens){
		this.errorCause = cause;
		this.wrongTokensIds = wrongTokensIds;
		this.tokenGroup.add(tokens);
		this.tokenGroup.add(commentTokens);
	}
	
	

	/**
	 * @return the wrongTokensIds
	 */
	public ArrayList<Integer> getWrongTokensIds() {
		return wrongTokensIds;
	}

	/**
	 * @return the wrongExpression
	 */
	public String getErrorCause() {
		return errorCause;
	}

	
	
	
	
}
