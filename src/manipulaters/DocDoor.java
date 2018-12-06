package manipulaters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;

import bus.SystemHandler;

public class DocDoor {
	
	public static String getMainTerm(int point){
	
		
		
		
		//String txt1 = SystemHandler.mainView.getTextFilejTextPane().getDocument().getText(point, 1);
		
		int caretPosition = point;
		
		point--;
		
		if(point>0){
				
			while(point>=0){

				String txt = "";
				try {
					txt = SystemHandler.mainView.getTextFilejTextPane().getDocument().getText(point, 1);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				Pattern p = Pattern.compile(SystemHandler.lotosBook.SEPARATOR);
				Matcher m = p.matcher(txt);
			
				if(m.matches()){
					point++;
					break;
				}else{
					point--;
				}
				
				if(point == 0)break;
			
			}
		
		}
		
		String result = "";
		try {
			result = SystemHandler.mainView.getTextFilejTextPane().getDocument().getText(point, caretPosition-point);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	
	}
	
}
