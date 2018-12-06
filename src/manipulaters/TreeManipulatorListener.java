package manipulaters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;

import bus.SystemHandler;

public class TreeManipulatorListener extends MouseAdapter{

			JTree tree = null;
	
			public TreeManipulatorListener(JTree tree){
				super();
				this.tree = tree;
			}
	
	
		   public void mousePressed(MouseEvent e) {  
		           int selRow = tree.getRowForLocation(e.getX(), e.getY());  
		            if(selRow > 0) {
		                //if(e.getClickCount() == 1) {  
		                //    mySingleClick(selRow, selPath);  
		                //}  
		            	
		                if(e.getClickCount() == 2){
		                    SystemHandler.openFileonProject(selRow);
		                }
		            }  
		        }  
  		

}
