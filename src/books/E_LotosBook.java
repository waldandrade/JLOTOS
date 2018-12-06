package books;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class E_LotosBook {
	
	public static ArrayList <String> KEYWORDS = new ArrayList<String>();
	public static ArrayList <String> FUNCTIONWORDS = new ArrayList<String>();
	
	public static final String SPECIFICATION = "specification";
	public static final String PROCESS = "process";
	public static final String ENDSPEC = "endspec";
	public static final String ENDPROC = "endproc";
	public static final String WHERE = "where";
	public static final String BEHAVIOR = "behavior";
	
	public static final String EXIT = "exit";
	public static final String NOEXIT = "noexit";
	public static final String STOP = "stop";
	
	
	
	public static final String SEQUENCE_COMPOSITION = ";";
	public static final String BLOCK_OPEN = "(";
	public static final String BLOCK_CLOSE = ")";
	public static final String BRACKET_OPEN = "[";
	public static final String BRACKET_CLOSE = "]";
	public static final String OPTIONAL = "[]";

	//bloco de comentário
	public static final String COMMENT_OPEN = "(*";
	public static final String COMMENT_CLOSE = "*)"; 
	
	//estrutura léxica do E-LOTOS
	
	public static final String LETTER = "a" +"|"+ "b" +"|"+ "c" +"|"+ "d" +"|"+ "e" +"|"+ "f" +"|"+ "g" +"|"
	+ "h" +"|"+ "i" +"|"+ "j" +"|"+ "k" +"|"+ "l" +"|"+ "m" +"|"+ "n" +"|"+ "o" +"|"+ "p" +"|"+ "q" +"|"+ "r" 
	+"|"+ "s" +"|"+ "t" +"|"+ "u" +"|"+ "v" +"|"+ "w" +"|"+ "x" +"|"+ "y" +"|"+ "z";
	
	public static final String DIGIT = "0" +"|"+ "1" +"|"+ "2" +"|"+ "3" +"|"+ "4" +"|"+ "5" +"|"+ "6" +"|"+ "7" +"|"+ "8" +"|"+ "9";

	public static final String NORMAL_CHARACTER = LETTER+"|"+DIGIT;
	
	public static final String SPECIAL_CHARACTER = "\\#" +"|"+ "\\%" +"|"+ "\\&" +"|"+ "\\*" +"|"+ "\\+" +"|"+ "\\-" +"|"+ "\\." +"|"+ "\\/" +"|"+ "\\<" +"|"+ "\\="
	+"|"+ "\\>" +"|"+ "\\@" +"|"+ "\\\\" +"|"+ "\\^" +"|"+ "\\~" +"|"+ "\\{" +"|"+ "\\}";
	
	//SP space - substitui um comentário
	//HT horizontal tab
	//VT vertical tab
	//FF Form Feed
	//NL new line
	//LF line feed
	//CR Carriage Return
	
	//Não sei se vai dar certo
	//Não trata o NL -> Next Line .. isso no caso é quando a nova linha gera o começo de um novo parágrafo
	
	public static final String SP = new Character((char)32).toString();
	public static final String HT = new Character((char)9).toString();
	public static final String VT = new Character((char)11).toString();
	public static final String FF = new Character((char)12).toString();
	public static final String NL = new Character((char)133).toString();
	public static final String LF = new Character((char)10).toString();
	public static final String CR = new Character((char)13).toString();
	
	public static final String BLANK_CHARACTER = SP +"|"+ HT +"|"+ VT +"|"+ FF +"|"+ NL +"|"+ LF +"|"+ CR;
	
	public static final String CHARACTER_SET = LETTER + "|" + DIGIT + "|" + SPECIAL_CHARACTER + "|" + BLANK_CHARACTER;
			
	
	//Expressões regulares para comentários
	
	public static final String COMMENT_CONT = "(((\\*?[^\\)])*)|(([^\\*]\\)?))*)";
	
	public static final String COMMENT = "\\(\\*" + COMMENT_CONT+ "\\*\\)";
	
	//---------------------------------------------------------------
	
	public static final String SEPARATOR = BLANK_CHARACTER +"|"+ COMMENT;
	
	public static final String IDENTIFIER = "["+LETTER+"](_?[" + NORMAL_CHARACTER +"])*";
	
	public static final String SPECIAL_IDENTIFIER = "(["+SPECIAL_CHARACTER+"]["+SPECIAL_CHARACTER +"]*)";
			//"|(["+DIGIT+"](_?[" + NORMAL_CHARACTER +"])*))";
	
	public static final String RESERVED_WORD = "and" +"|"+ "andalso" +"|"+ "any" +"|"+ "as" +"|"+ "behavior" +"|"+
	"behaviour" +"|"+ "block" +"|"+ "break" +"|"+ "by" +"|"+ "case" +"|"+ "choice" +"|"+ "conc" +"|"+ "dis" +"|"+ "do" +"|"+ "else" +"|"+ "elsif" +"|"+
	"endcase" +"|"+ "endch" +"|"+ "endconc" +"|"+ "enddis" +"|"+ "endeqns" +"|"+ "endexit" +"|"+ "endexn" +"|"+ "endfor" +"|"+ 
	"endfunc" +"|"+ "endfullsync" +"|"+ "endgen" +"|"+ "endhide" +"|"+ "endif" +"|"+ "endint" +"|"+ "endinter" +"|"+ "endloop" +"|"+
	"endmod" +"|"+ "endpar" +"|"+ "endproc" +"|"+ "endren" +"|"+ "endsel" +"|"+ "endspec" +"|"+ "endsuspend" +"|"+ "endtrap" +"|"+
	"endtype" +"|"+ "endval" +"|"+ "endvar" +"|"+ "endwhile" +"|"+ "eqns" +"|"+ "etc" +"|"+ "exception" +"|"+ "exceptions" +"|"+
	"exit" +"|"+ "external" +"|"+ "for" +"|"+ "forall" +"|"+ "fullsync" +"|"+ "function" +"|"+ "gate" +"|"+ "gates" +"|"+ "generic" +"|"+
	"hide" +"|"+ "i" +"|"+ "if" +"|"+	"imports" +"|"+ "in" +"|"+ "infix" +"|"+ "inter" +"|"+ "interface" +"|"+ "is" +"|"+ "loop" +"|"+
	"module" +"|"+ "none" +"|"+ "null" +"|"+ "ofsort" +"|"+ "opns" +"|"+ "orelse" +"|"+ "out" +"|"+ "par" +"|"+ "process" +"|"+ "procs" +"|"+ 
	"raise" +"|"+ "raises" +"|"+ "rename" +"|"+ "renames" +"|"+ "renaming" +"|"+ "sel" +"|"+ "signal" +"|"+ "specification" +"|"+ 
	"stop" +"|"+ "suspend" +"|"+ "then" +"|"+ "trap" +"|"+ "type" +"|"+ "types" +"|"+ "value" +"|"+ "values" +"|"+ "var" +"|"+ "wait" +"|"+ "while";
	
	
	public static final String RESERVED_LEXICAL_TOKENS = "\\(" +"|"+ "\\)" +"|"+ "\\{" +"|"+ "\\}" +"|"+ "\\," +"|"+ "\\." + "|"+ "\\;" 
	+ "|"+ "\\|" +"|"+ "\\?" +"|"+ "\\!"  +"|"+ "\\=" +"|"+ "\\=\\>" +"|"+ "\\:" +"|"+ "\\:\\=" +"|"+ "\\[" +"|"+ "\\]" +"|"+ "\\->" +"|"+ "\\#"+"|"+ "\\>" +"|"+ "\\<\\>";
	
	//public static final String TOKEN = IDE
	
	
/*		identifier 	domain meaning 			abbreviation
		Con 		constructor identifier 	C
		Const 		constant identifier 		K
		Exc 		exception identifier 	X
		Fun 		function identifier 		F
		Gat 		gate identifier 			G
		GenId 		generics identifiers 	gen-id
		IntId 		interface identifiers 	int-id
		ModId 		module identifiers 		mod-id
		Proc 		process identifier 		
		Spec 		specification identifier 	
		Typ 		type identifier 			S
		Var 		variable identifier 		V
*/
	
	public static final String IDENTIFIERS_CLASSES = "Con" +"|"+ "Const" + "|" +"Exc"+ "|" + "Fun" + "|" + "Gat" + 
	"|" + "GenId" + "|" + "IntId" + "|" + "ModId" + "|" + "Proc" + "|" + "Spec" + "|" + "Typ" + "|" + "Var";
	
/*	symbol domain 	meaning 						abbreviation
 	APL 			actual parameter list 			APL
	Behav 			behaviour expression 			B
	BehavTerm 		behaviour term 					BT
	BehavAtom 		behaviour atom 					BA
	BMatch 			behaviour match 				BM
	Decl 			declaration 					D
	EMatch 			expression match 				EM
	EqnDecList 		equations declaration list 		eqn-dec-list
	EqnDec 			simple equations declaration 	eqn-dec
	Exp 			expression 						E
	ExpAtom 		expression atom 				EA
	FPL 			formal parameter list 			FPL
	GPL 			gate parameter list 			GPL
	InParList 		in parameter list 				IPL
	InPar 			in parameter 					IP
	IntBody 		interface body 					i-body
	IntExp 			interface expressions 			int-exp
	LocVar 			local variables 				LV
	ModBody 		module body 					m-body
	ModExp 			module expressions 				mod-exp
	ModPar 			module formal parameters 		MP
	Pat 			pattern 						P
	RecModExp 		record module expressions 		RME
	Reninst 		module renaming/instantiation 	reninst
	RPat 			record pattern 					RP
	RTyExp 			record type expression 			RT
	RVal 			record value expression 		RN
	RVar 			record of variables 			RV
	SCon 			special constant 				K
	Spec 			specification 					spec
	TopDec 			top-level declarations 			top-dec
	TyExp 			type expression 				T
	Val 			value expression 				N
	XPL 			exception parameter list 		XPL
*/	
	public static final String NON_TERMINALS_CLASSES =
	"APL"+ "|" +"Behav"+ "|" +"BehavTerm"+ "|" +"BehavAtom"+ "|" +"BMatch"+ "|" +"Decl"+ "|" +"EMatch"+ "|" 
	+"EqnDecList"+ "|" +"EqnDec"+ "|" +"Exp"+ "|" +"ExpAtom"+ "|" +"FPL"+ "|" +"GPL"+ "|" +"InParList"+ "|"
	+"InPar"+ "|" +"IntBody"+ "|" +"IntExp"+ "|" +"LocVar"+ "|" +"ModBody"+ "|" +"ModExp"+ "|" +"ModPar"+ "|"
	+"Pat"+ "|" +"RecModExp"+ "|" +"Reninst"+ "|" +"RPat"+ "|" +"RTyExp"+ "|" +"RVal"+ "|" +"RVar"+ "|"
	+"SCon"+ "|" +"Spec"+ "|" +"TopDec"+ "|" +"TyExp"+ "|" +"Val"+ "|" +"XPL";
		
	public static E_LotosBook book = null;
	
	public void chargeKeyWords(){
		
		KEYWORDS.add(SPECIFICATION);
		KEYWORDS.add(PROCESS);
		KEYWORDS.add(ENDSPEC);
		KEYWORDS.add(ENDPROC);
		KEYWORDS.add(WHERE);
		KEYWORDS.add(BEHAVIOR);

		FUNCTIONWORDS.add(EXIT);
		FUNCTIONWORDS.add(NOEXIT);
		FUNCTIONWORDS.add(STOP);

		
	}
	//styled=
	
	
	public MutableAttributeSet getStyledWord(String word){
		
		
		MutableAttributeSet att = new SimpleAttributeSet();
		StyleConstants.setLineSpacing(att, 0.1f);
        StyleConstants.setFontFamily(att,"Courier");
		StyleConstants.setFontSize(att,12);
        
		Pattern p = Pattern.compile(E_LotosBook.RESERVED_WORD);
		Matcher m = p.matcher(word);
		
		if (m.matches()){
			StyleConstants.setForeground(att, new Color(21,21,129));
			StyleConstants.setBold(att, true);		
		}
		
		p = Pattern.compile(E_LotosBook.RESERVED_LEXICAL_TOKENS);
		m = p.matcher(word);
		
		if (m.matches()){
			StyleConstants.setForeground(att, new Color(139,21,21));
			StyleConstants.setBold(att, true);		
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
		
		Vector <String> selfCompletVector = new Vector<String>();
		
		for (int cont = 0; cont < KEYWORDS.size(); cont++){
			
			String str = KEYWORDS.get(cont).substring(0, refer.length());
			if(str.equalsIgnoreCase(refer)){
				selfCompletVector.add(KEYWORDS.get(cont));
			}
			
			
		}
				
		return selfCompletejPanel;
		
	}
	
	public static E_LotosBook getE_LotosBook(){
		if (book==null){
			book=new E_LotosBook();
			book.chargeKeyWords();
		}
		
		return book;
	}
	
	//Método que analiza léxicamente o comentário
	
	//0 significa que não é um comentario, pois não abri com (*
	//nesse caso esse mesmo Token deve continuar a ser verificado nas deais expressões
	
	//1 significa que é um comentário, e pede para que seja concatenado um novo token para saber se o comentário chegou ao fim 
	//nesse caso tudo o que existir está comentado .. no caso se não for fechado então existe um erro lexico
	
	//-1 significa que o comentário foi fechado pois o token *) foi concatenado a String
	//Todo o comentário deve ser adicionado a um único token
	//O conjunto de tokens que formava o comentário agora é apenas um. (* XXXX *)
	
	public static int isComment(String comment){
	
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
