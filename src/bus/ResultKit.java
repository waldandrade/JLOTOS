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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.TextUI;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ResultKit {
	
	//constants of type
	public static final String LOTOS = "lotos";
	
	public boolean saved = true;
		
	public String name = "";
	public int id = -1;
	
	public String logPath = "";
	
	public String type = "";
	
	private String resultText = "";
	
	public StyledDocument resultDocument = new DefaultStyledDocument();
	public StyledDocument resultDocumentC2 = new DefaultStyledDocument();
	

	private JComponent resultComponent = null;

	private JLabel fecharJLabel = null;
	
	public ResultKit(String name, String type){
		this.name = name;
		this.type = type;
		MutableAttributeSet mat = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(mat, 0.1f);
        StyleConstants.setFontFamily(mat,"Courier");
		StyleConstants.setFontSize(mat,12);
		resultDocument.setParagraphAttributes(0, resultDocument.getLength(), mat, true);
		StyleConstants.setAlignment(mat, StyleConstants.ALIGN_RIGHT);
		resultDocumentC2.setParagraphAttributes(0, resultDocumentC2.getLength(), mat, true);
	}
	
	private BackGroundJPanel resultWindowTabjPanel = null;
	
	public BackGroundJPanel getResultWindowTabjPanel() {
		if (resultWindowTabjPanel == null) {
			
			resultWindowTabjPanel = new BackGroundJPanel();
			FlowLayout thisLayout = new FlowLayout();
			thisLayout.setVgap(5);
			thisLayout.setHgap(7);
			thisLayout.setAlignment(FlowLayout.LEFT);
			resultWindowTabjPanel.setLayout(thisLayout);
			resultWindowTabjPanel.setOpaque(false);
			resultWindowTabjPanel.setBorder(BorderFactory.createEmptyBorder());
			//textFileTabjPanel.add(new Image(getClass().getResource("/resources/img/page_save.png")), BorderLayout.CENTER);
			resultWindowTabjPanel.add(getFileNamejTextField(), null);
			resultWindowTabjPanel.add(getFecharJLabel(), null);
			getFecharJLabel().setVisible(false);
			Dimension d = getFileNamejTextField().getUI().getPreferredSize(getFileNamejTextField());
			d.height = 23;
			d.width += 50;
			resultWindowTabjPanel.setPreferredSize(d);
			//textFileTabjPanel.setPreferredSize(new Dimension(width, 26));
			resultWindowTabjPanel.getInsets().set(0, 10, 0, 0);
			
			resultWindowTabjPanel.addMouseListener(mouseAdapter);
			getFecharJLabel().addMouseListener(mouseAdapter);
			getFecharJLabel().addMouseListener(fecharMouseAdapter);
			
			resultWindowTabjPanel.addFocusListener(new FocusAdapter() {

				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					super.focusLost(e);
					
					getFecharJLabel().setVisible(false);
				}
				
				
				
			});
			
		}
		return resultWindowTabjPanel;
	}
	
	private JLabel resultWindowNamejTextField = null;
	
	public JLabel getFileNamejTextField() {
		if (resultWindowNamejTextField == null) {
			

			resultWindowNamejTextField = new JLabel();
			resultWindowNamejTextField.setIcon(SystemHandler.lotosBook.lotosIcon);
			resultWindowNamejTextField.setFont(new Font("Default", Font.LAYOUT_NO_LIMIT_CONTEXT, 10));
			resultWindowNamejTextField.setText(name);
			resultWindowNamejTextField.setFocusable(false);
			resultWindowNamejTextField.setVerticalAlignment(JLabel.BOTTOM);
			resultWindowNamejTextField.setVerticalTextPosition(JLabel.BOTTOM);
			
		}
		return resultWindowNamejTextField;
	}

	/**
	 * @return the dataText
	 */
	public String getResultDataText() {
		return resultText;
	}

	/**
	 * @param dataText the dataText to set
	 */
	public void setResultText(String resultText) {
		this.resultText = resultText;
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
	
	public void unselectBackground() {
		Image backgroundImage = new ImageIcon(getClass().getResource("/resources/img/fundo_aba_ns.png")).getImage();
		Image backgroundImageInicio = new ImageIcon(getClass().getResource("/resources/img/fundo_aba_inicio_ns.png")).getImage();
		Image backgroundImageFim = new ImageIcon(getClass().getResource("/resources/img/fundo_aba_fim_ns2.png")).getImage();
		

		getResultWindowTabjPanel().setBackgroundImage(backgroundImage);
		getResultWindowTabjPanel().setBackgroundImageInicio(backgroundImageInicio);
		getResultWindowTabjPanel().setBackgroundImageFim(backgroundImageFim);
		
		Dimension d = getFileNamejTextField().getUI().getPreferredSize(getFileNamejTextField());
		d.height = 23;
		d.width += 50;
		getResultWindowTabjPanel().setPreferredSize(d);
		getResultWindowTabjPanel().repaint();
	}
	
	public void selectBackground() {
		Image backgroundImage = new ImageIcon(getClass().getResource("/resources/img/fundoAba.png")).getImage();
		Image backgroundImageInicio = new ImageIcon(getClass().getResource("/resources/img/fundo_aba_inicio.png")).getImage();
		Image backgroundImageFim = new ImageIcon(getClass().getResource("/resources/img/fundo_aba_fim.png")).getImage();
		
		
		getResultWindowTabjPanel().setBackgroundImage(backgroundImage);
		getResultWindowTabjPanel().setBackgroundImageInicio(backgroundImageInicio);
		getResultWindowTabjPanel().setBackgroundImageFim(backgroundImageFim);
		
		Dimension d = getFileNamejTextField().getUI().getPreferredSize(getFileNamejTextField());
		d.height = 23;
		d.width += 50;
		getResultWindowTabjPanel().setPreferredSize(d);

		getResultWindowTabjPanel().validate();
		getResultWindowTabjPanel().repaint();
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
				
				SystemHandler.openResultWindow(id);
				
				SystemHandler.mainView.getResultColumn2jTextPane().setDocument(resultDocumentC2);
				SystemHandler.mainView.getResultsjTextPane().setDocument(resultDocument);
				
				TextUI ui = SystemHandler.mainView.getResultColumn2jTextPane().getUI();
				Dimension pref = ui.getPreferredSize(SystemHandler.mainView.getResultColumn2jTextPane());
				
				TextUI ui2 = SystemHandler.mainView.getResultsjTextPane().getUI();
				Dimension pref2 = ui2.getPreferredSize(SystemHandler.mainView.getResultsjTextPane());
				
				
				SystemHandler.mainView.getResultColumn2jPanel().setPreferredSize(new Dimension(pref.width,pref2.height));
				
				SystemHandler.mainView.validate();
																		
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
		SystemHandler.closeResult(id);
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseEntered(e);
		fecharJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/fechar_selecionado.png")));
		
	
	}
	};

	public JLabel getFecharJLabel() {
		if(fecharJLabel == null)fecharJLabel = new JLabel();
		
		fecharJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/fechar.png")));
		
		return fecharJLabel;
	}

	public void setFecharJLabel(JLabel fecharJLabel) {
		this.fecharJLabel = fecharJLabel;
	}

	public JComponent getResultComponent() {
		return resultComponent;
	}

	public void setResultComponent(JComponent resultComponent) {
		this.resultComponent = resultComponent;
	}
	
}
