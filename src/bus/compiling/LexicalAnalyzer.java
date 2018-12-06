package bus.compiling;

import java.util.ArrayList;

import bus.compiling.exceptions.LexicalErrorException;

public class LexicalAnalyzer {

	private ArrayList<Token> tokenList = null;
    private ArrayList<Token> commentTokens = null;
	
	
	public ArrayList<ArrayList<Token>> analyse(String cod) throws LexicalErrorException{
		
		TokensGenerator tg = new TokensGenerator();
		
				tokenList = tg.tokenazer(cod);
		
		    commentTokens = tg.getCommentTokens();
		
		
		/*for(Token t : tokenList){
			
			System.out.println(t.getConteudo());
			
		}
		
		System.out.println("\nComments");
		
		for(Token t : commentTokens){
			
			System.out.println(t.getConteudo());
			
		}*/
				
		ArrayList<ArrayList<Token>> array = new ArrayList<ArrayList<Token>>();
		array.add(tokenList);
		array.add(commentTokens);
		
				
		return array;
	
	}
	
	public LexicalAnalyzer(){
		
	
	}

	/**
	 * @return the tokenList
	 */
	public ArrayList<Token> getTokenList() {
		return tokenList;
	}

	/**
	 * @return the tokensComments
	 */
	public ArrayList<Token> getcommentTokens() {
		return commentTokens;
	}
	
	
	
	
}
