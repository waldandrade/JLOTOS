package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image backgroundImage = new ImageIcon(getClass().getResource("/resources/img/fundoJLOTOS.png")).getImage();
	Image lotos = new ImageIcon(getClass().getResource("/resources/img/lotosLOGO.png")).getImage();
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Dimension dDesktop = this.getSize(); 
    	double  width = dDesktop.getWidth();  
    	double height = dDesktop.getHeight();  
    	Image background = new ImageIcon(this.backgroundImage.getScaledInstance(  
    	(int) width, (int) height, 1)).getImage();  
    	g.drawImage(background, 0, 0, this) ;
    	g.drawImage(lotos, (int)(100), (int)(height/6), this) ;   
  
	}
	
	
}
