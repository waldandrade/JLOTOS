package manipulaters;

import java.awt.Dimension;

import javax.swing.plaf.TextUI;

import bus.SystemHandler;

public class TextActualizer implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		SystemHandler.sectionKits.get(SystemHandler.openedSectionId).setDataText(SystemHandler.mainView.getTextFilejTextPane().getText());
		TextUI ui = SystemHandler.mainView.getTextFilejTextPane().getUI();
		Dimension pref = ui.getPreferredSize(SystemHandler.mainView.getTextFilejTextPane());
		SystemHandler.mainView.getRowInfojPanel().setPreferredSize(new Dimension(SystemHandler.mainView.getRowInfojPanel().getWidth(),pref.height));
		
		//SystemHandler.mainView.getJlineInfoScrollPane().getVerticalScrollBar().setValue(SystemHandler.mainView.getJtextFileVerticalScrollBar().getValue());
		//SystemHandler.mainView.actualizeText();
	}

}
