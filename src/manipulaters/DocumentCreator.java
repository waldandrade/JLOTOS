package manipulaters;


import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import bus.SystemHandler;
import bus.compiling.Token;

public class DocumentCreator{
		
    public void styleDocument(String text){
    	
    	int init = 0;
		
		Pattern p = Pattern.compile(SystemHandler.lotosBook.SEPARATOR);
		Matcher m = p.matcher(text);

		Pattern pComment = Pattern.compile(SystemHandler.lotosBook.COMMENT);
		while(m.find()){
			Matcher mComment = pComment.matcher(m.group());
			if(mComment.matches()){
				MutableAttributeSet att = new SimpleAttributeSet();
				StyleConstants.setForeground(att, new Color(140,170,255));
				StyleConstants.setBold(att, false);
				StyleConstants.setLineSpacing(att, 0.1f);
				StyleConstants.setFontFamily(att,"Courier");
				StyleConstants.setFontSize(att,12);
				String t = mComment.group();
				SystemHandler.mainView.replaceString(t, init+mComment.start(), att);
				Token tk = new Token();
				tk.setConteudo(mComment.group());
				tk.setInicio(init+mComment.start());
				tk.setFim(init+mComment.end());
				SystemHandler.sectionKits.get(SystemHandler.openedSectionId).commentTokens.add(tk);
			}
			if(m.start()!=0){
				if(init!=m.start()){
					extractToken(text.substring(init, m.start()), init); //fazer algum tipo de exibição de erro
				}
			}
			init=m.end();
		}
		if(init!=text.length()){
				extractToken(text.substring(init, text.length()),init);
		}
    	
    	
	}

    public boolean extractToken(String term, int init){
		
		Pattern p = Pattern.compile(SystemHandler.lotosBook.RESERVED_LEXICAL_TOKENS , Pattern.CASE_INSENSITIVE);
		Matcher mGeneral = p.matcher(term);
		
		int initLocal = 0;
				
		boolean leg = true;
		
		while(leg){
			if(!mGeneral.find()){
				String t = term.substring(initLocal,term.length());
				MutableAttributeSet att = SystemHandler.lotosBook.getStyledWord(t);
				SystemHandler.mainView.replaceString(t, init+initLocal, att);
				break;
			}else{
				if(mGeneral.start()!=initLocal){
					String t = term.substring(initLocal, mGeneral.start());
					MutableAttributeSet att = SystemHandler.lotosBook.getStyledWord(t);
					SystemHandler.mainView.replaceString(t, init+initLocal, att);
				}
				MutableAttributeSet att = SystemHandler.lotosBook.getStyledWord(mGeneral.group());
				SystemHandler.mainView.replaceString(mGeneral.group(), init+mGeneral.start(), att);
				initLocal=mGeneral.end();
			}
		}
		
		return leg;
		
	}
    
    public void formTokens(String tk, int point){
    			
		Pattern p = Pattern.compile(SystemHandler.lotosBook.SEPARATOR);
		Matcher m = p.matcher(tk);
		
		int init = 0;

		while(m.find()){
			if(m.start()!=0){
				if(init!=m.start()){
					extractToken(tk.substring(init, m.start()), init+point); //fazer algum tipo de exibição de erro
				}
			}
			init=m.end();
		}
		if(init!=tk.length()){
				extractToken(tk.substring(init, tk.length()),init+point);
		}
    	
    	
    }
    
    public void findComments(String text, int offset, boolean removing){
    	
    	ArrayList<Token> cmt = SystemHandler.sectionKits.get(SystemHandler.openedSectionId).commentTokens;
    	
    	if(removing){
    	
    		//System.out.println("is removing!");
    		
    	int i = 0;
  
    	while(i<cmt.size()){
    		//System.out.println(cmt.get(i).getInicio()+"<--"+offset+"-->"+cmt.get(i).getFim());
    		if(cmt.get(i).getInicio()<=offset && offset<cmt.get(i).getFim()-1){
    			//System.out.println("dentro!!");
    			//MutableAttributeSet att = new SimpleAttributeSet();
				//StyleConstants.setBold(att, false);
				//StyleConstants.setLineSpacing(att, 0.1f);
		        //StyleConstants.setFontFamily(att,"Courier");
				//StyleConstants.setFontSize(att,12);
				int fim = cmt.get(i).getFim()-1;
				if(fim>text.length())fim=text.length();
    			//SystemHandler.mainView.replaceString(text.substring(cmt.get(i).getInicio(),fim), cmt.get(i).getInicio(), att);
    			formTokens(text.substring(cmt.get(i).getInicio(),fim), cmt.get(i).getInicio());
    			
    			break;
    		}
    		i++;
    	}
    	
    	}else{
    		
    	int i = 0;
    	
    	while(i<cmt.size()){
    		if(cmt.get(i).getInicio()<offset && cmt.get(i).getFim()>offset){
    			int fim = cmt.get(i).getFim()+1;
				if(fim>text.length())fim=text.length();
    			formTokens(text.substring(cmt.get(i).getInicio(),fim), cmt.get(i).getInicio());
				break;
    		}
    		i++;
    	}
    		
    		
    	}
    	
    	SystemHandler.sectionKits.get(SystemHandler.openedSectionId).commentTokens = null;
    			
    	SystemHandler.sectionKits.get(SystemHandler.openedSectionId).commentTokens = new ArrayList<Token>();
    
    	Pattern pComment = Pattern.compile(SystemHandler.lotosBook.COMMENT);
		Matcher mComment = pComment.matcher(text);
		    	
		while(mComment.find()){
				SimpleAttributeSet att = new SimpleAttributeSet();
				StyleConstants.setForeground(att, new Color(140,170,255));
				StyleConstants.setBold(att, false);
				String t = mComment.group();
				SystemHandler.mainView.replaceString(t, mComment.start(), att);
				Token tk = new Token();
				tk.setConteudo(mComment.group());
				tk.setInicio(mComment.start());
				tk.setFim(mComment.end());
				SystemHandler.sectionKits.get(SystemHandler.openedSectionId).commentTokens.add(tk);
		}
    	
    }
    
    public static void insertCode(String block, int point){
    	
    	try {
			SystemHandler.mainView.getTextFilejTextPane().getDocument().insertString(point, block, new SimpleAttributeSet());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    
}


