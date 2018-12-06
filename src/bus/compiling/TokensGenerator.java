package bus.compiling;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bus.SystemHandler;
import bus.compiling.exceptions.LexicalErrorException;

public class TokensGenerator {
	
	public int tkDelim [] = new int [2];
	
	public ArrayList<Token> commentTokens = null;
	
	//Enquanto o proximo caractere (em uma string) não for um separador  ... concatena ele no token atual
	//quando for um separador ... adiciona o token ao ArrayList e passa para o elemento depois do separado, 
	//se ele também não for um separador
	private	ArrayList<Token> codTokens = null;
	
	private boolean errorLeg = false;
	
	private ArrayList<Integer> tokenErrorIds = new ArrayList<Integer>();
	
	public ArrayList<Token> tokenazer(String cod) throws LexicalErrorException{
		
		commentTokens = new ArrayList<Token>();
		codTokens = new ArrayList<Token>();
		
		int init = 0;
		
		Pattern p = Pattern.compile(SystemHandler.lotosBook.SEPARATOR);
		Matcher m = p.matcher(cod);
		while(m.find()){
			Pattern pComment = Pattern.compile(SystemHandler.lotosBook.COMMENT);
			Matcher mComment = pComment.matcher(m.group());
			if(mComment.matches()){
				commentTokens.add(new Token(m.group(),"COMMENT",m.start(),m.end(),true));
			}
			if(m.start()!=0){
				if(init!=m.start()){
				if(!extractToken(cod.substring(init, m.start()), init)){
					errorLeg = true;
					//fazer algum tipo de exibição de erro
				}
				}
				//codTokens.add(new Token(cod.substring(init, m.start()),"",init,m.start()-1,true));
			}
			init=m.end();
		}
		if(init!=cod.length()){
				if(!extractToken(cod.substring(init, cod.length()),init)){
					errorLeg = true;
				}
			//codTokens.add(new Token(cod.substring(init, cod.length()),"",init,cod.length()-1,true));
		}
		
		if(errorLeg)throw new LexicalErrorException("Wrong TOKEN!", tokenErrorIds, codTokens, commentTokens);
		
		return codTokens;
	}
	
	public boolean extractToken(String term, int init){

		Pattern p = Pattern.compile(SystemHandler.lotosBook.RESERVED_LEXICAL_TOKENS , Pattern.CASE_INSENSITIVE);
		Matcher mGeneral = p.matcher(term);
		

		Pattern pWord = Pattern.compile(SystemHandler.lotosBook.IDENTIFIER+"|"+SystemHandler.lotosBook.RESERVED_WORD, Pattern.CASE_INSENSITIVE);
		Matcher mWord = pWord.matcher(term);
		
		Pattern pOpns = Pattern.compile(SystemHandler.lotosBook.OPNS, Pattern.CASE_INSENSITIVE);
		Matcher mOpns = pOpns.matcher(term);
		
		int initLocal = 0;
				
		boolean leg = true;
		
		while(leg){
			if(mWord.matches()){
				//adicionar o Token como identificador
				codTokens.add(new Token(term,setTypeOfToken(term),init,init+term.length(),true));
				break;
			}else if(!mGeneral.find()){
				if(mOpns.matches()){
					codTokens.add(new Token(term,setTypeOfToken(term),init,init+term.length(),true));
					break;
				}else{
					tokenErrorIds.add(codTokens.size());
					tkDelim[0] = init;
					tkDelim[1] = init+term.length();
					codTokens.add(new Token(term,setTypeOfToken(term),init,init+term.length(),true));
					leg = false;
					break;
				}
			}else{
				if(mGeneral.start()!=initLocal){
					String t = term.substring(initLocal, mGeneral.start());
					if(!extractToken(t, init+initLocal)){
						leg=false;
					}
				}
					codTokens.add(new Token(mGeneral.group(),setTypeOfToken(mGeneral.group()),init+mGeneral.start(),init+mGeneral.end(),true));
					if(mGeneral.end()!=term.length()){
						String t = term.substring(mGeneral.end());
						if(!extractToken(t, init+mGeneral.end())){
							leg=false;
							break;
						}
					}
					break;
			}
		}
				
		return leg;
		
	}
	
	public boolean isIdentifier(String str){
		
		int init = 0;
		
		Pattern p = Pattern.compile(SystemHandler.lotosBook.IDENTIFIER);
		Matcher m = p.matcher(str);
		
		if(m.matches()){
					System.out.println(str.substring(init, m.start()));
			return true;
		}
		return false;
	
	}
	
	public boolean isSpecialIdentifier(String str, String SPECIAL_IDENTIFIER){
		
		int init = 0;
		
		Pattern p = Pattern.compile(SPECIAL_IDENTIFIER);
		Matcher m = p.matcher(str);
		
		if(m.matches()){
					System.out.println(str.substring(init, m.start()));
			return true;
		}
		return false;
	
	}
	
	
	public String setTypeOfToken(String tk){
		
		String type = "";
		
		Pattern p = Pattern.compile(SystemHandler.lotosBook.RESERVED_WORD, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(tk);
		
		if(m.matches()){
			type = SystemHandler.lotosBook.KEYWORK;
		}else{
			m.usePattern(Pattern.compile(SystemHandler.lotosBook.RV_SYMBOL, Pattern.CASE_INSENSITIVE));
			if(m.matches())type = SystemHandler.lotosBook.RENDEZVOUS;
			else{
				m.usePattern(Pattern.compile(SystemHandler.lotosBook.RESERVED_LEXICAL_TOKENS, Pattern.CASE_INSENSITIVE));
				if(m.matches())type = SystemHandler.lotosBook.OPERATOR;
				else{
					m.usePattern(Pattern.compile(SystemHandler.lotosBook.IDENTIFIER, Pattern.CASE_INSENSITIVE));
					if(m.matches())type = SystemHandler.lotosBook.ID;
					else {
						m.usePattern(Pattern.compile(SystemHandler.lotosBook.LR_EQUATION_OPNS, Pattern.CASE_INSENSITIVE));
						if(m.matches())type = SystemHandler.lotosBook.LR_OPERAND;
						else{
							m.usePattern(Pattern.compile(SystemHandler.lotosBook.L_EQUATION_OPNS, Pattern.CASE_INSENSITIVE));
							if(m.matches())type = SystemHandler.lotosBook.L_OPERAND;
							else{
								m.usePattern(Pattern.compile(SystemHandler.lotosBook.R_EQUATION_OPNS, Pattern.CASE_INSENSITIVE));
								if(m.matches())type = SystemHandler.lotosBook.R_OPERAND;
								else{
									m.usePattern(Pattern.compile(SystemHandler.lotosBook.EQUATION_OPNS, Pattern.CASE_INSENSITIVE));
									if(m.matches())type = SystemHandler.lotosBook.OPERAND;
									else{
										m.usePattern(Pattern.compile(SystemHandler.lotosBook.UNDERLINE, Pattern.CASE_INSENSITIVE));
										if(m.matches())type = SystemHandler.lotosBook.OPERAND_UNDERLINE;
										else{
											type = "WRONG";
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return type;
		
	}
	
	public TokensGenerator(){
		
	}

	/**
	 * @return the commentTokens
	 */
	public ArrayList<Token> getCommentTokens() {
		if (commentTokens==null)commentTokens = new ArrayList<Token>();
		
		return commentTokens;
	}
	
	
	
}
