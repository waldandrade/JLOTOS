package books;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import bus.SectionKit;
import bus.SystemHandler;

public class docListener implements DocumentListener, Runnable {

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
		
	}
	
	public int point = 0;
	public String token = "";
	public MutableAttributeSet att = null;
	public int caretPosition = 0;
	public String text = "";
	public int length;
	
	public int offset = 0;
	public boolean removing = false;
	
	public int endCommentPastToken = -1;
	public int startCommentPastToken = -1;
	
	public static boolean isRunning = false;
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
				
	
		if (isRunning==false){
		text = "";
		try {
			text = e.getDocument().getText(0, e.getDocument().getLength());
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		removing = false;
		offset = e.getOffset();
		point = e.getOffset();
		caretPosition = e.getOffset()+1;
		length = e.getLength();
		

		if(point>0){
			
			boolean leg = true;
			
			while(leg){
		
				Pattern p = Pattern.compile(SystemHandler.lotosBook.SEPARATOR);
				Matcher m = p.matcher(text.charAt(--point)+"");
				
				if(m.matches()){
					point++;
					break;
				}
				
				if(point==0){
					leg = false;
				}
				
			}
		}
			
		token = getTokenSet(text, point, caretPosition);
		
		SwingUtilities.invokeLater(this);
			
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		

		if (isRunning==false){
		text = "";
		try {
			text = e.getDocument().getText(0, e.getDocument().getLength());
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		removing = true;
		offset = e.getOffset();
		caretPosition = e.getOffset();
		length = e.getLength();
		
		point = e.getOffset();
		
		if(point>0){
			
			boolean leg = true;
			
			while(leg){
		
				Pattern p = Pattern.compile(SystemHandler.lotosBook.SEPARATOR);
				Matcher m = p.matcher(text.charAt(--point)+"");
				
				if(m.matches()){
					point++;
					break;
				}
				
				if(point==0){
					leg = false;
				}
				
			}
			
		}
		
		
		String txt = SystemHandler.sectionKits.get(SystemHandler.openedSectionId).getDataText();				
		setCommentEnd(txt, e.getOffset()+1);
		
		token = getTokenSet(text,point, caretPosition);
						
		SwingUtilities.invokeLater(this);
				
		}
	}
	
	private void setCommentEnd(String txt, int i) {
		// TODO Auto-generated method stub
 			
		    	Pattern pComment = Pattern.compile(SystemHandler.lotosBook.COMMENT);
				Matcher mComment = pComment.matcher(txt);
				
		    	while(mComment.find()){
		    		if(mComment.end()>i){
		    			if(mComment.start()<i){
		    				endCommentPastToken = mComment.end();
		    				break;
		    			}	
		    			break;
		    		}
				}
	}

	public String getTokenSet(String text, int point, int caretPosition){
		
		
		Pattern p = Pattern.compile(SystemHandler.lotosBook.SEPARATOR);
		Matcher m  = p.matcher(text);
		
		String tk = "";
		
		if(m.find(caretPosition)){
			
			tk = text.substring(point, m.start());
			
			
		}else{
			tk = text.substring(point, text.length());
			
		}
		if(tk.length()>0){
		m.reset(tk.charAt(tk.length()-1)+"");
		if(m.matches()){
			tk = tk.substring(0,tk.length()-1);
		}}
		
		return tk;
		
	}
		

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		SystemHandler.sectionKits.get(SystemHandler.openedSectionId).caretPosition = caretPosition;

		isRunning=true;
		//if(length==1){
		if(removing==false && length>1){
			SystemHandler.documentCreator.formTokens(text.substring(offset, offset+length), offset);
			SystemHandler.sectionKits.get(SystemHandler.openedSectionId).caretPosition = offset+length;
			caretPosition = offset+length;
		}
		if(SystemHandler.sectionKits.get(SystemHandler.openedSectionId).type.equals(SectionKit.LOTOS) || 
				SystemHandler.sectionKits.get(SystemHandler.openedSectionId).type.equals(SectionKit.LIBRARY)){
			SystemHandler.documentCreator.formTokens(token, point);
		}
		
		 SystemHandler.documentCreator.findComments(text, offset, removing);
        //SystemHandler.mainView.replaceString(token, point, att);
        SystemHandler.mainView.setCaretPosition(caretPosition);
        if(SystemHandler.isInAutoCompleteProcess){
        	SystemHandler.mainView.setCaretPosition(SystemHandler.caretPositionAfterAutoComplete);
        }
        
		//}
        att = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(att, 0.1f);
        StyleConstants.setFontFamily(att,"Courier");
		StyleConstants.setFontSize(att,12);
        isRunning=false;
		
	}
	
	
	

}
