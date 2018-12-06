package books;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class LotosBook {
	
	public final javax.swing.ImageIcon lotosIcon = new javax.swing.ImageIcon(getClass().getResource("/resources/img/page_lotos.png"));
	public final javax.swing.ImageIcon libIcon = new javax.swing.ImageIcon(getClass().getResource("/resources/img/page_save.png"));
	
	/*SEMANTIC DEFINITIONS*/
	
	public final String GLOBAL_PROC_TITLE = "###GLOBAL#PROC#TITLE###";
	public final String SPEC_TITLE = "SPEC_TITLE"; //Título da especificação
	public final String SPEC_GATE_DEF = "SPEC_GATE_DEF"; // gate definido da especificação
	public final String PROC_GATE_DEF = "PROC_GATE_DEF"; // gate definido no processo
	public final String PROC_INST_NAME = "PROC_INST_NAME"; // gate
	public final String VARID = "VARID";
	public final String VARIDDECL = "VARIDDECL";
	public final String SORTTD = "SORTID";
	public final String GATEID = "GATEID";
	public final String PROCID = "PROCID";
	public final String PARTIAL_SYNCHRONIZATION = "PARTIAL_SYNCHRONIZATION";
	public final String HIDING = "HIDING";
	public final String GUARD = "GUARD";
	
	public final String INTERNAL_EVENT = "INTERNAL_EVENT";
	
	
	//--------------------
	
	
	public final String RENDEZVOUS = "RENDEZVOUS_SYMBOL";
	public final String KEYWORK = "KEY_WORD";
	public final String ID = "IDENTIFIER";
	public final String COMMENTS = "COMMENT";
	public final String	OPERATOR = "OPERATOR";
	
	public final String SPECIFICATION = "specification";
	public final String PROCESS = "process";
	public final String ENDSPEC = "endspec";
	public final String ENDPROC = "endproc";
	public final String WHERE = "where";
	public final String BEHAVIOR = "behavior";
	
	public final String EXIT = "exit";
	public final String NOEXIT = "noexit";
	public final String STOP = "stop";
	
	
	
	public final String SEQUENCE_COMPOSITION = ";";
	public final String BLOCK_OPEN = "(";
	public final String BLOCK_CLOSE = ")";
	public final String BRACKET_OPEN = "[";
	public final String BRACKET_CLOSE = "]";
	public final String OPTIONAL = "[]";

	//bloco de comentário
	public final String COMMENT_OPEN = "(*";
	public final String COMMENT_CLOSE = "*)"; 
	
	//estrutura léxica do E-LOTOS
	
	public final String LETTER = "a" +"|"+ "b" +"|"+ "c" +"|"+ "d" +"|"+ "e" +"|"+ "f" +"|"+ "g" +"|"
	+ "h" +"|"+ "i" +"|"+ "j" +"|"+ "k" +"|"+ "l" +"|"+ "m" +"|"+ "n" +"|"+ "o" +"|"+ "p" +"|"+ "q" +"|"+ "r" 
	+"|"+ "s" +"|"+ "t" +"|"+ "u" +"|"+ "v" +"|"+ "w" +"|"+ "x" +"|"+ "y" +"|"+ "z";
	
	public  final String DIGIT = "0" +"|"+ "1" +"|"+ "2" +"|"+ "3" +"|"+ "4" +"|"+ "5" +"|"+ "6" +"|"+ "7" +"|"+ "8" +"|"+ "9";

	public  final String NORMAL_CHARACTER = LETTER+"|"+DIGIT;
	
	//SP space - substitui um comentário
	//HT horizontal tab
	//VT vertical tab
	//FF Form Feed
	//NL new line
	//LF line feed
	//CR Carriage Return
	
	//Não sei se vai dar certo
	//Não trata o NL -> Next Line .. isso no caso é quando a nova linha gera o começo de um novo parágrafo
	
	public  final String SP = new Character((char)32).toString();
	public  final String HT = new Character((char)9).toString();
	public  final String VT = new Character((char)11).toString();
	public  final String FF = new Character((char)12).toString();
	public  final String NL = new Character((char)133).toString();
	public  final String LF = new Character((char)10).toString();
	public  final String CR = new Character((char)13).toString();
	
	public  final String BLANK_CHARACTER = SP +"|"+ HT +"|"+ VT +"|"+ FF +"|"+ NL +"|"+ LF +"|"+ CR;
	
	//public  final String CHARACTER_SET = LETTER + "|" + DIGIT + "|" + SPECIAL_CHARACTER + "|" + BLANK_CHARACTER;
			
	
	//Expressões regulares para comentários
	
	//     /\*([^*]|[\r\n]|(\*+([^*/]|[\r\n])))*\*+/
	//  \\(\\*([^*]|[\\r\\n]|(\\*+([^*\\)]|[\\r\\n])))*\\*+\\)
	
	//public  final String COMMENT_CONT = "([^*]|[\\r\\n]|(\\*+([^*\\)]|[\\r\\n])))*";
	
	//public  final String COMMENT_CONT = "(((\\*?[^\\)])*)|(([^\\*]\\)?))*)";
	
	//public  final String COMMENT = "\\(\\*" + COMMENT_CONT+ "\\*\\)";
	
	public final String COMMENT = "(\\(\\*(?:.|[\\n\\r])*?\\*\\))|(\\(\\*(?:.|[\\n\\r])*)";
	
	
	
	//public static final String SPECIAL_IDENTIFIER = "(["+SPECIAL_CHARACTER+"]["+SPECIAL_CHARACTER +"]*)";
	
	//---------------------------------------------------------------
	
	public  final String SEPARATOR = "("+COMMENT+")" +"|"+ BLANK_CHARACTER  ;
	
	public  final String IDENTIFIER = "["+LETTER+"](_?[" + NORMAL_CHARACTER +"])*";
	
	public  final String RV_SYMBOL = "\\?" + "|" + "\\!";
	
	public  final String RESERVED_WORD =  "accept" +"|"+ "actualizedby" +"|"+ "any" +"|"+ "behaviour" +"|"+ "choice" +"|"+ "endlib" +"|"+ "endproc" +"|"+ "endspec" +"|"
	+ "endtype" +"|"+ "eqns" +"|"+ "exit" +"|"+ "for" +"|"+ "forall" +"|"+ "formaleqns" +"|" + "formalopns" +"|" + "formalsorts" +"|"+ "hide" +"|"+ "i" +"|"+ "in" +"|"
	+ "is" +"|"+ "let" +"|"+ "library" + "|" + "noexit" + "|" + "of" + "|" + "ofsort" +"|"+ "opnnames" +"|"+ "opns" +"|"+ "par" +"|"+ "process" +"|"+ "renamedby" +"|"+ "sortnames" +"|"+ "sorts" +"|"+ 
	"specification" +"|"+ "stop" +"|"+ "type" +"|"+ "using" +"|"+ "where" +"|"+ "with" +"|"+ "\\=";
	
	public  final String RESERVED_LEXICAL_TOKENS = "\\>\\>" +"|"+ "\\|\\|\\|"+ "|"+ "\\]\\|" +"|"+ "\\|\\[" +"|"+ "\\[\\]" +"|"+ "\\[\\>" +"|"+ "\\(" +"|"+ "\\)" +"|"+ "\\{" +"|"+ "\\}" +"|"+ "\\," +"|"+ "\\." + "|"+ "\\;" 
	+"|"+ "\\?" +"|"+ "\\!" +"|"+ "\\=\\>" +"|"+ "\\-\\>" +"|"+ "\\:\\=" +"|"+ "\\:" +"|"+  "\\[" +"|"+ "\\]" +"|"+ "\\|";
	
	public static final String SPECIAL_CHARACTER = "\\#" +"|"+ "\\%" +"|"+ "\\&" +"|"+ "\\*" +"|"+ "\\+" +"|"+ "\\-" +"|"+ "\\." +"|"+ "\\/" +"|"+ "\\<" 
	+"|"+ "\\>" +"|"+ "\\@" +"|"+ "\\\\" +"|"+ "\\^" +"|"+ "\\~" +"|"+ "\\{" +"|"+ "\\}" +"|"+ "\\=";
	
	
	public final String EQUATION_OPNS = "(["+NORMAL_CHARACTER+"]" + "|" + "["+SPECIAL_CHARACTER+"])+";
	
	//L = Left, operando virá á esquerda     R = Right, operando virá à direita  LR - Operando virá em ambos os lados
	public final String L_EQUATION_OPNS = "_"+EQUATION_OPNS;
	public final String R_EQUATION_OPNS = EQUATION_OPNS+"_";
	public final String LR_EQUATION_OPNS = "_"+EQUATION_OPNS+"_";
	public final String UNDERLINE = "_";
	
	
	public final String OPNS = LR_EQUATION_OPNS + "|" + L_EQUATION_OPNS + "|" + R_EQUATION_OPNS + "|" + EQUATION_OPNS + "|" + UNDERLINE;
	
	//tipos para os tokens

	
	public final String L_UNDERLINE = "L_UNDERLINE";
	public final String R_UNDERLINE = "R_UNDERLINE";
	public final String OPERATOR_KEY = "OPERATOR_KEY";

	public final String OPERAND_UNDERLINE = "OPERAND_UNDERLINE";
	
	public final String OPERAND = "OPERAND";
	public final String L_OPERAND = "LEFT_OPERAND";
	public final String R_OPERAND = "RIGHT_OPERAND";
	public final String LR_OPERAND = "BOTH_SIDE_OPERANDS";
	
	//public final String id
	
	public  LotosBook book = null;
	
	//styled=
	
	
	public MutableAttributeSet getStyledWord(String word){
		
		
		MutableAttributeSet att = new SimpleAttributeSet();
		StyleConstants.setLineSpacing(att, 0.1f);
        StyleConstants.setFontFamily(att,"Courier");
		StyleConstants.setFontSize(att,12);
        
		Pattern p = Pattern.compile(RESERVED_WORD);
		Matcher m = p.matcher(word);
		
		if (m.matches()){
			StyleConstants.setForeground(att, new Color(21,21,129));
			StyleConstants.setBold(att, true);		
		}else{
			p = Pattern.compile(RESERVED_LEXICAL_TOKENS);
			m = p.matcher(word);
			if (m.matches()){
				StyleConstants.setForeground(att, new Color(80,21,21));
				StyleConstants.setBold(att, true);		
			}else{
				p = Pattern.compile(IDENTIFIER, Pattern.CASE_INSENSITIVE);
				m = p.matcher(word);
				if(!m.matches()){
					p = Pattern.compile(OPNS, Pattern.CASE_INSENSITIVE);
					m = p.matcher(word);
					if(m.matches()){
						StyleConstants.setForeground(att, new Color(200,5,5));
						StyleConstants.setBold(att, false);
					}
				}
			}
			
		}
		
		
		
		
		
		//if(KEYWORDS.contains(word)){
		//	StyleConstants.setForeground(att, Color.BLUE);
		//	StyleConstants.setBold(att, true);
		//}else if(FUNCTIONWORDS.contains(word)){
		//	StyleConstants.setForeground(att, Color.RED);
		//}
		
		return att;
		
	}
	
	
	
	public JPanel selfComplete(String refer){
		
		JPanel selfCompletejPanel = new JPanel();
		
		/*
		
		Vector <String> selfCompletVector = new Vector<String>();
		
		for (int cont = 0; cont < KEYWORDS.size(); cont++){
			
			String str = KEYWORDS.get(cont).substring(0, refer.length());
			if(str.equalsIgnoreCase(refer)){
				selfCompletVector.add(KEYWORDS.get(cont));
			}
			
			
		}
		
		JList selfCompletjList = new JList(selfCompletVector);*/
		
		return selfCompletejPanel;
		
	}
	
	//Método que analiza léxicamente o comentário
	
	//0 significa que não é um comentario, pois não abri com (*
	//nesse caso esse mesmo Token deve continuar a ser verificado nas deais expressões
	
	//1 significa que é um comentário, e pede para que seja concatenado um novo token para saber se o comentário chegou ao fim 
	//nesse caso tudo o que existir está comentado .. no caso se não for fechado então existe um erro lexico
	
	//-1 significa que o comentário foi fechado pois o token *) foi concatenado a String
	//Todo o comentário deve ser adicionado a um único token
	//O conjunto de tokens que formava o comentário agora é apenas um. (* XXXX *)
	
	public  int isComment(String comment){
	
		if(!comment.substring(0, 1).equals(COMMENT_OPEN)){
			return 0;
		}else if(!comment.substring(comment.length()-2, comment.length()-1).equals(COMMENT_CLOSE)){
			return 1;
		}
		else{
			return -1;
		}
		
	}
}

//Depois deverei implementar para, antes de qualquer coisa, colocar todos os comentários em uma lista de comentários
//... e no código devo substituir todo o comentário por (*#*);
