package bus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.TextUI;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import bus.compiling.Token;


public class SectionKit implements Cloneable {


	public ArrayList<Token> commentTokens = new ArrayList<Token>();
	
	//constant of the default result tab
	public static final String CONSOLE_RESULT_TAB = "CRT";
	
	//constant of the default info tab
	public static final String EXPLORER_TAB = "EXPLORER";
	
	//constants of type
	public static final String LOTOS = "lotos";

	//constants of type
	public static final String LIBRARY = "lib";
	
	public boolean saved = true;
	
	public boolean onProject = false;
	
	public String name = "";
	public int id = -1;
	
	public String path = "";
	public String type = "";
	
	private String lineText = "";
	private String dataText = "";
	
	public int caretPosition = -1;
	
	public int numberOfResultTabs = 0;
	//The list of tabs on south that had to be opened in current opened section
	public ArrayList <String> resultTabList = new ArrayList<String>();
	
	public StyledDocument document = new DefaultStyledDocument();
	
	
	public ArrayList<ArrayList<Token>> tokensGroups = null; 
	
	public String getPlagieOptionText(){
		return SystemHandler.mainView.stile.getActualLanguage().getPlagieOptionText();
	}
	
	public String getRenameOptionText(){
		return SystemHandler.mainView.stile.getActualLanguage().getRenameOptionText();
	}
	
	
	public SectionKit(String name){
		this.name = name;
		this.numberOfResultTabs++;
		this.resultTabList.add(SectionKit.CONSOLE_RESULT_TAB);
		MutableAttributeSet mat = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(mat, 0.1f);
        StyleConstants.setFontFamily(mat,"Courier");
		StyleConstants.setFontSize(mat,12);
		document.setParagraphAttributes(0, document.getLength(), mat, true);	
	}
	
	public SectionKit(File file, String fileType){
		String extension = fileType.toLowerCase();
		this.name = file.getName().replace("."+fileType, "."+extension);
		this.path = file.getPath();
		this.type = fileType;
		this.numberOfResultTabs++;
		this.resultTabList.add(SectionKit.CONSOLE_RESULT_TAB);
		MutableAttributeSet mat = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(mat, 0.1f);
        StyleConstants.setFontFamily(mat,"Courier");
		StyleConstants.setFontSize(mat,12);
		document.setParagraphAttributes(0, document.getLength(), mat, true);
	}
	
	private BackGroundJPanel textFileTabjPanel = null;

	private JLabel fecharJLabel = null;
	
	
	public BackGroundJPanel getTextFileTabjPanel() {
		if (textFileTabjPanel == null) {
			textFileTabjPanel = new BackGroundJPanel();
			FlowLayout thisLayout = new FlowLayout();
			thisLayout.setVgap(5);
			thisLayout.setHgap(7);
			thisLayout.setAlignment(FlowLayout.LEFT);
			textFileTabjPanel.setLayout(thisLayout);
			textFileTabjPanel.setOpaque(false);
			textFileTabjPanel.setBorder(BorderFactory.createEmptyBorder());
			//textFileTabjPanel.add(new Image(getClass().getResource("/resources/img/page_save.png")), BorderLayout.CENTER);
			textFileTabjPanel.add(getFileNamejTextField(), null);
			textFileTabjPanel.add(getFecharJLabel(), null);
			getFecharJLabel().setVisible(false);
			Dimension d = getFileNamejTextField().getUI().getPreferredSize(getFileNamejTextField());
			d.height = 23;
			d.width += 50;
			textFileTabjPanel.setPreferredSize(d);
			//textFileTabjPanel.setPreferredSize(new Dimension(width, 26));
			textFileTabjPanel.getInsets().set(0, 10, 0, 0);
			
			textFileTabjPanel.addMouseListener(mouseAdapter);
			getFecharJLabel().addMouseListener(mouseAdapter);
			getFecharJLabel().addMouseListener(fecharMouseAdapter);
			
			textFileTabjPanel.addFocusListener(new FocusAdapter() {

				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					super.focusLost(e);
					
					getFecharJLabel().setVisible(false);
				}
				
				
				
			});
		
			
		}
		return textFileTabjPanel;
	}
		
	private JLabel fileNamejTextField = null;
	
	public JLabel getFileNamejTextField() {
		if (fileNamejTextField == null) {
			fileNamejTextField = new JLabel();
			fileNamejTextField.setIcon(SystemHandler.lotosBook.lotosIcon);
			fileNamejTextField.setFont(new Font("Default", Font.LAYOUT_NO_LIMIT_CONTEXT, 10));
			fileNamejTextField.setText(name);
			//fileNamejTextField.setBackground(getTextFileInfoColor());
			fileNamejTextField.setFocusable(false);
			fileNamejTextField.setVerticalAlignment(JLabel.BOTTOM);
			fileNamejTextField.setVerticalTextPosition(JLabel.BOTTOM);
			//fileNamejTextField.setPreferredSize(new Dimension(0, 0));
		
		}
		return fileNamejTextField;
	}
	
	
	public int idFromResultTab(String resultTab){
		
		for(int cont = 0; cont < numberOfResultTabs; cont ++){
		      if(resultTabList.get(cont).equalsIgnoreCase(resultTab)){
		    	  return cont;
		      }
		}
		
		return -1;
		
	}
	
	/**
	 * @return the lineText
	 */
	public String getLineText() {
		return lineText;
	}

	/**
	 * @param lineText the lineText to set
	 */
	public void setLineText(String lineText) {
		this.lineText = lineText;
	}

	/**
	 * @return the dataText
	 */
	public String getDataText() {
		return dataText;
	}

	/**
	 * @param dataText the dataText to set
	 */
	public void setDataText(String dataText) {
		this.dataText = dataText;
	}
	
	public Color getTextFileInfoColor(){
		return SystemHandler.mainView.stile.getActualColorSchema().getTextFileInfoColor();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	/**
	 * @return the tokensGroups
	 */
	public ArrayList<ArrayList<Token>> getTokensGroups() {
		return tokensGroups;
	}

	/**
	 * @param tokensGroups the tokensGroups to set
	 */
	public void setTokensGroups(ArrayList<ArrayList<Token>> tokensGroups) {
		this.tokensGroups = tokensGroups;
	}

	public JLabel getFecharJLabel() {
		if(fecharJLabel == null)fecharJLabel = new JLabel();
		
		fecharJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/fechar.png")));
		
		return fecharJLabel;
	}

	public void setFecharJLabel(JLabel fecharJLabel) {
		this.fecharJLabel = fecharJLabel;
	}
	
	
	public void unselectBackground() {
		Image backgroundImage = new ImageIcon(getClass().getResource("/resources/img/fundo_aba_ns.png")).getImage();
		Image backgroundImageInicio = new ImageIcon(getClass().getResource("/resources/img/fundo_aba_inicio_ns.png")).getImage();
		Image backgroundImageFim = new ImageIcon(getClass().getResource("/resources/img/fundo_aba_fim_ns2.png")).getImage();
		

		getTextFileTabjPanel().setBackgroundImage(backgroundImage);
		getTextFileTabjPanel().setBackgroundImageInicio(backgroundImageInicio);
		getTextFileTabjPanel().setBackgroundImageFim(backgroundImageFim);
		
		Dimension d = getFileNamejTextField().getUI().getPreferredSize(getFileNamejTextField());
		d.height = 23;
		d.width += 50;
		getTextFileTabjPanel().setPreferredSize(d);
		getTextFileTabjPanel().repaint();
	}
	
	public void selectBackground() {
		Image backgroundImage = new ImageIcon(getClass().getResource("/resources/img/fundoAba.png")).getImage();
		Image backgroundImageInicio = new ImageIcon(getClass().getResource("/resources/img/fundo_aba_inicio.png")).getImage();
		Image backgroundImageFim = new ImageIcon(getClass().getResource("/resources/img/fundo_aba_fim.png")).getImage();
		
		
		getTextFileTabjPanel().setBackgroundImage(backgroundImage);
		getTextFileTabjPanel().setBackgroundImageInicio(backgroundImageInicio);
		getTextFileTabjPanel().setBackgroundImageFim(backgroundImageFim);
		
		Dimension d = getFileNamejTextField().getUI().getPreferredSize(getFileNamejTextField());
		d.height = 23;
		d.width += 50;
		getTextFileTabjPanel().setPreferredSize(d);

		getTextFileTabjPanel().validate();
		getTextFileTabjPanel().repaint();
	}

	public class BackGroundJPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Image backgroundImage = new ImageIcon(getClass().getResource("/resources/img/fundoAba.png")).getImage();
		public Image backgroundImageInicio = new ImageIcon(getClass().getResource("/resources/img/fundo_aba_inicio.png")).getImage();
		public Image backgroundImageFim = new ImageIcon(getClass().getResource("/resources/img/fundo_aba_fim.png")).getImage();
		
		
		
		public Image getBackgroundImage() {
			return backgroundImage;
		}

		public void setBackgroundImage(Image backgroundImage) {
			this.backgroundImage = backgroundImage;
		}

		public BackGroundJPanel(){
			 
			 super();
		 
		 } 
			 
		 	@Override
		    public void paintComponent(Graphics g){          
		    	super.paintComponent(g);       
		    	Dimension dDesktop = this.getSize(); 
		    	double  width = dDesktop.getWidth();  
		    	double height = dDesktop.getHeight();  
		    	Image background = new ImageIcon(this.backgroundImage.getScaledInstance(  
		    	(int) width-43, (int) height, 1)).getImage();  
		    	g.drawImage(background, 3, 0, this) ;   
		    	g.drawImage(backgroundImageInicio, 0, 0, this) ;   
		    	g.drawImage(backgroundImageFim, (int)this.getSize().getWidth()-40, 0, this) ; 
		    }

			public Image getBackgroundImageInicio() {
				return backgroundImageInicio;
			}

			public void setBackgroundImageInicio(Image backgroundImageInicio) {
				this.backgroundImageInicio = backgroundImageInicio;
			}

			public Image getBackgroundImageFim() {
				return backgroundImageFim;
			}

			public void setBackgroundImageFim(Image backgroundImageFim) {
				this.backgroundImageFim = backgroundImageFim;
			}
		
	}
	
	public MouseAdapter mouseAdapter = new MouseAdapter() {
		
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
				getFecharJLabel().setVisible(false);
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseEntered(e);
				getFecharJLabel().setVisible(true);
			
			}			
			
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				
				if(getFecharJLabel().isVisible()==false)
					getFecharJLabel().setVisible(true);
				
				SystemHandler.openSection(id);
				
				SystemHandler.mainView.getLineInfojTextPane().setText(SystemHandler.sectionKits.get(SystemHandler.openedSectionId).lineText);
				
				TextUI ui = SystemHandler.mainView.getTextFilejTextPane().getUI();
				Dimension pref = ui.getPreferredSize(SystemHandler.mainView.getTextFilejTextPane());
				SystemHandler.mainView.getRowInfojPanel().setPreferredSize(new Dimension(SystemHandler.mainView.getRowInfojPanel().getWidth(),pref.height));
														
			}
					
	};
	
	public MouseAdapter fecharMouseAdapter = new MouseAdapter() {
		
		public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseExited(e);
		fecharJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/fechar.png")));
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		SystemHandler.closeSection(id);
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseEntered(e);
		fecharJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/fechar_selecionado.png")));
		
	
	}
	};
	
}
