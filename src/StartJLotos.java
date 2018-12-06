import javax.swing.JFrame;

import books.LotosBook;
import bus.SystemHandler;
import view.JLotosMainView;
import view.SplashWindow;



/**
 * This class is the first called when the user gives a double click on
 * the JLotos.jar file. It calls the splash screen, closes it and calls the
 * JLotos main window.
 * @author diego
 */
public class StartJLotos {
    public static void main(String[] args) {
        final SplashWindow sp = new SplashWindow();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                sp.setVisible(true);
            }
        });
        //Espera um tempo para que a imagem de splash seja exibida
    	Runnable runner = new Runnable() {
    		public void run() {
            try {
            	Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

         }
    	};
    	runner.run();

        sp.dispose();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	SystemHandler.mainView = new JLotosMainView();
        		
        		
        		
            	SystemHandler.mainView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	SystemHandler.mainView.setVisible(true);
            	


            	SystemHandler.mainView.getResultsjTextPane().insertComponent(SystemHandler.component);
    		
            	
            	SystemHandler.mainView.getTextFilejTextPane().setVisible(false);
            	SystemHandler.mainView.getTextFilejTextPane().setOpaque(false);
        		
            	SystemHandler.mainView.getLineInfojTextPane().getCaret().deinstall(SystemHandler.mainView.getLineInfojTextPane());
        		
            	SystemHandler.mainView.getJtextScrollPane().setRowHeaderView(SystemHandler.mainView.getRowInfojPanel());
            	SystemHandler.mainView.getResultsjScrollPane().setRowHeaderView(SystemHandler.mainView.getResultColumn2jPanel());
        		
            	SystemHandler.mainView.getWindowsjPanel().setVisible(false);
            	

        		
        		SystemHandler.viewRectangle = SystemHandler.mainView.getTextFilejTextPane().getVisibleRect();
        		
        		//addActionTab(SectionKit.EXPLORER_TAB);
        		
        		        		
        		SystemHandler.lotosBook = new LotosBook();
            }
        });

    }
}
