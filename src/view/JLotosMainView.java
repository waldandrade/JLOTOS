package view;

import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import resources.FileForm;
import resources.LotosFile;
import resources.NonWrappingTextPane;
import resources.Project;
import toolset.ViewStile;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.ComponentOrientation;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import bus.SectionKit;
import bus.SystemHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import manipulaters.TextActualizer;
import java.awt.GridBagConstraints;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JList;
import javax.swing.JSplitPane;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class JLotosMainView extends JFrame{

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	private NewLotosFileWizard newLotosFileWizard = null;
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar mainMenuJMenuBar = null;
	private JMenu filejMenu = null;
	public ViewStile stile = null;
	private JMenu editjMenu = null;
	private JPanel toolsBarjPanel = null;
	private JPanel actionsjPanel = null;
	private JPanel windowsjPanel = null;
	private JPanel mainjPanel = null;
	private JMenuItem openProjectjMenuItem = null;
	private JMenuItem saveFilejMenuItem = null;
	private JMenuItem exitjMenuItem = null;
	private JMenuItem undojMenuItem = null;
	private JMenuItem redojMenuItem = null;
	private JPanel actionjPanel = null;
	private JScrollPane textFilejScrollPane = null;
	private JPanel toolbar1jPanel = null;
	private JScrollPane resultsjScrollPane = null;
	private JPanel windowInfojPanel = null;
	private JPanel actionInfojPanel = null;
	private JPanel textFilejPanel = null;
	private JTextPane lineInfojTextPane = null;
	private  NonWrappingTextPane textFilejTextPane = null;
	
	private Vector<Boolean> saveStateOfFiles = new Vector<Boolean>();  //  @jve:decl-index=0:
	private Vector<String> nameOfFiles = new Vector<String>();
	private JMenuItem newFilejMenuItem = null;
	
	private JToolBar leftJToolBar = null;
	private JToolBar rightJToolBar = null;
	
	private JPanel viewSideJPanel = null;

	private JPanel toolSideJPanel = null;
	
	/**
	 * This method initializes mainMenuJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	
	private JMenuBar getMainMenuJMenuBar() {
		if (mainMenuJMenuBar == null){
			mainMenuJMenuBar = new JMenuBar();
			//mainMenuJMenuBar.setBackground(stile.getActualColorSchema().getBarColor());
			mainMenuJMenuBar.add(getFilejMenu());
			//mainMenuJMenuBar.add(getEditjMenu());
			//mainMenuJMenuBar.add(getTemplatesjMenu());
			//mainMenuJMenuBar.add(getEditorjMenu());
			mainMenuJMenuBar.add(getCompileToolsjMenu());
			// mainMenuJMenuBar.add(getDebugjMenu());
			// mainMenuJMenuBar.add(getGraphicsjMenu());
			// mainMenuJMenuBar.add(getConfigurationjMenu());
			mainMenuJMenuBar.add(getHelpjMenu());
		}
		return mainMenuJMenuBar;
	}
	
	public Color getBorderColor(){
		return stile.getActualColorSchema().getBorderColor();
	}
	
	public Color getBarColor(){
		return stile.getActualColorSchema().getBarColor();
	}
	
	public Color getBackgroundColor(){
		return stile.getActualColorSchema().getBackgroundColor();
	}
	
	public Color getFieldColor(){
		return stile.getActualColorSchema().getFieldColor();
	}
	
	public Color getTextFileInfoColor(){
		return stile.getActualColorSchema().getTextFileInfoColor();
	}
	
	public Color getSelectedTextFileInfoColor(){
		return stile.getActualColorSchema().getSelectedTextFileInfoColor();
	}
	
	public String getFileMenuText(){
		return stile.getActualLanguage().getFileMenuText();
	}
	
	public String getEditMenuText(){
		return stile.getActualLanguage().getEditMenuText();
	}
	
	public String getHelpMenuText(){
		return stile.getActualLanguage().getHelpMenuText();
	}
			
	public int getWindowWidth(){
		
		return stile.getWindowWidth();
	}
	
	public int getWindowHeight(){
		return stile.getWindowHeight();
	}
	
	public int getToolBarWidth() {
		return stile.getToolBarWidth();
	}

	public int getToolBarHeight() {
		return stile.getToolBarHeight();
	}

	public int getActionWidth() {
		return stile.getActionWidth();
	}

	public int getActionHeight() {
		return stile.getActionHeight();
	}

	public int getWindowsPanelWidth() {
		return stile.getWindowsPanelWidth();
	}

	public int getWindowsPanelHeight() {
		return stile.getWindowsPanelHeight();
	}

	public int getMainWidth() {
		return stile.getMainWidth();
	}

	public int getMainHeight() {
		return stile.getMainHeight();
	}
	
	public String getUndoMenuItemText(){
		return stile.getActualLanguage().getUndoMenuItemText();
	}
	
	public String getRedoMenuItemText(){
		return stile.getActualLanguage().getRedoMenuItemText();
	}
	
	public String getOpenMenuText() {
		return stile.getActualLanguage().getOpenMenuText();
	}
	
	public String getSaveMenuText(){
		return stile.getActualLanguage().getSaveMenuText();
	}
	
	public String getExitMenuItemText(){
		return stile.getActualLanguage().getExitMenuItemText();
	}
	
	public String getProjectText(){
		return stile.getActualLanguage().getProjectText();
	}
	
	public String getFileText(){
		return stile.getActualLanguage().getFileText();
	}
	
	
	/**
	 * This method initializes filejMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFilejMenu() {
		if (filejMenu == null) {
			filejMenu = new JMenu();
			filejMenu.setText("File");
			filejMenu.add(getOpenFilejMenuItem());
			
			/*
			 * Criar estrutura de abrir projeto
			 * */
			// filejMenu.add(getOpenProjectjMenuItem());
			filejMenu.add(getNewFilejMenuItem());
			
			/*
			 * Criar estrutura de novo projeto
			 * */
			// filejMenu.add(getNewProjectjMenuItem());
			filejMenu.add(getSaveFilejMenuItem());
			
			/*
			 * Criar estrutura de salvar projeto
			 * */
			// filejMenu.add(getSaveProjectjMenuItem());
			filejMenu.add(getExitjMenuItem());
		}
		return filejMenu;
	}

	/**
	 * This method initializes editjMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getEditjMenu() {
		if (editjMenu == null) {
			editjMenu = new JMenu();
			editjMenu.setText("Edit");
			editjMenu.add(getUndojMenuItem());
			editjMenu.add(getRedojMenuItem());
			editjMenu.add(getRefreshjMenuItem());
			editjMenu.add(getCopyjMenuItem());
			editjMenu.add(getCutjMenuItem());
			editjMenu.add(getPastejMenuItem());
			editjMenu.add(getFindReplacejMenuItem());
		}
		return editjMenu;
	}

	/**
	 * This method initializes toolsBarjPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getToolsBarjPanel() {
		if (toolsBarjPanel == null) {
			GridLayout gridLayout = new GridLayout(1, 1);
			toolsBarjPanel = new JPanel();
			toolsBarjPanel.setLayout(gridLayout);
			toolsBarjPanel.setBackground(new Color(150,170,170));
			toolsBarjPanel.setPreferredSize(new java.awt.Dimension(1210, 30));
			toolsBarjPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			toolsBarjPanel.add(getToolbar1jPanel(), null);
		}
		return toolsBarjPanel;
	}

	/**
	 * This method initializes actionsjPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getActionsjPanel() {
		if (actionsjPanel == null) {
			actionsjPanel = new JPanel();
			actionsjPanel.setLayout(new BorderLayout());
			actionsjPanel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 1));
			actionsjPanel.add(getRightJToolBar(),BorderLayout.EAST);
			getRightJToolBar().setPreferredSize(new Dimension(25, actionsjPanel.getPreferredSize().height));
			actionsjPanel.add(getAuxJPanel2(),BorderLayout.CENTER);
		}
		return actionsjPanel;
	}
private JPanel auxJPanel2 = null;
	
	/**
	 * This method initializes auxJPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getAuxJPanel2(){
		
		if(auxJPanel2==null){
			
			auxJPanel2 = new JPanel();
			auxJPanel2.setLayout(new BorderLayout());
			auxJPanel2.setBorder(BorderFactory.createEmptyBorder());
			auxJPanel2.add(getActionjScrollPane(), BorderLayout.CENTER);
			auxJPanel2.add(getActionInfojPanel(), BorderLayout.NORTH);
			auxJPanel2.setVisible(false);
		}
		return auxJPanel2;
	}
	
	/**
	 * This method initializes windowsjPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getWindowsjPanel() {
		if (windowsjPanel == null) {
			windowsjPanel = new JPanel();
			windowsjPanel.setLayout(new BorderLayout());
			windowsjPanel.setBackground(new Color(150,170,170));
			windowsjPanel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 1));
			//windowsjPanel.setPreferredSize(new Dimension(0,SystemHandler.Height/5));
			windowsjPanel.add(getWindowInfojPanel(), BorderLayout.NORTH);
			windowsjPanel.add(getResultsjScrollPane(), BorderLayout.CENTER);
		}
		return windowsjPanel;
	}

	/**
	 * This method initializes mainjPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getMainjPanel(){
		if (mainjPanel == null) {
			mainjPanel = new MyJPanel();
			mainjPanel.setLayout(new BorderLayout());
			mainjPanel.setOpaque(false);
			mainjPanel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 1));
			//mainjPanel.add(getTextFilejScrollPane(), BorderLayout.CENTER);
			mainjPanel.setPreferredSize(new Dimension(SystemHandler.Width*13/14,0));
			mainjPanel.add(getInfoAreajPanel(), BorderLayout.NORTH);
			mainjPanel.add(getTextFilejPanel(), BorderLayout.CENTER);
		}
		return mainjPanel;
	}
	/**
	 * This method initializes openProjectjMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getOpenProjectjMenuItem(){
		if (openProjectjMenuItem == null){
			openProjectjMenuItem = new JMenuItem();
			openProjectjMenuItem.setText("Open project");
			openProjectjMenuItem.addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) {
					File projectFile = getProjectFile();
					if(projectFile != null && projectFile.canRead()){
						File file = new File(projectFile.getPath()+"\\"+projectFile.getName()+".actproject");
						if(file.canRead()){
						SystemHandler.openedProject = new Project(projectFile);
						SystemHandler.hasProjectOpened=true;						
						String str =  "";
						try {
			                BufferedReader in = new BufferedReader(new FileReader(file));
			                File includedFile = null;
			                while ((str = in.readLine()) != null) {
			                	includedFile = new File(projectFile.getPath()+"\\"+str);
			                	if(includedFile.canRead()){
			                		if(includedFile.getName().contains("."+SectionKit.LOTOS)){
			                			SystemHandler.openedProject.lotosFiles.add(new LotosFile(includedFile));
			                		}
			                	}else{
			                		JOptionPane.showMessageDialog(SystemHandler.mainView,"This file: \n\t"+includedFile.getPath()+"\ncan�t be readed!!");
			                	}
			                }
			                in.close();
			            } catch (IOException ioe) {
			                ioe.printStackTrace();
			            }
						
			            SystemHandler.actualizeExplorer();
						}else{
							JOptionPane.showMessageDialog(SystemHandler.mainView, "This file is not a valid project path");
						}
					}
				}
			
			});
		}
		return openProjectjMenuItem;
	}
	
	
	public static File getProjectFile(){
		JFileChooser chooser = new JFileChooser();
		chooser.setName("Project");
		chooser.setDialogType(JFileChooser.CUSTOM_DIALOG);
		chooser.setDialogTitle("Project");
		File file = new File(SystemHandler.userHome);//tem que mudar!!!
		if(!file.canRead()){
			file.mkdir();
		}
		chooser.setCurrentDirectory(file);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(new JFrame());
		if(returnVal == JFileChooser.APPROVE_OPTION){
		System.out.println("You chose to open this file:" +
		chooser.getSelectedFile().getPath());
		return chooser.getSelectedFile();
		}else{
			return null;
		}
	}
	
	public static File getFile(){
		JFileChooser chooser = new JFileChooser();
		chooser.setName("File");
		chooser.setDialogType(JFileChooser.CUSTOM_DIALOG);
		chooser.setDialogTitle("LOTOS Files");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		File file = new File(SystemHandler.userHome);//tem que mudar
		if(!file.canRead())file.mkdir();
		chooser.setCurrentDirectory(file);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		"LOTOS files", "lotos");
		FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
				"LIBRARY files", "lib");
		chooser.addChoosableFileFilter(filter2);
		chooser.addChoosableFileFilter(filter);
		int returnVal = chooser.showOpenDialog(new JFrame());
		if(returnVal == JFileChooser.APPROVE_OPTION){
		/*System.out.println("You chose to open this file:" +
		chooser.getSelectedFile().getPath());*/
		return chooser.getSelectedFile();
		}else{
			return null;
		}
	}
	
	
	/**
	 * This method initializes saveFilejMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	
	public JMenuItem getSaveFilejMenuItem() {
		if (saveFilejMenuItem == null) {
			saveFilejMenuItem = new JMenuItem();
			saveFilejMenuItem.setText("Save file");
			saveFilejMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/page_save.png")));
			KeyStroke ctrlS = KeyStroke.getKeyStroke("control S");
			saveFilejMenuItem.setAccelerator(ctrlS);
			saveFilejMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if(SystemHandler.sectionKits.size()>0){
					
						String filePath = SystemHandler.sectionKits.get(SystemHandler.openedSectionId).path;
						
						if (filePath.equals("")){
							filePath = saveArquivo();
						}
						
						File textFile = new File(filePath);
						
						String dataText = SystemHandler.sectionKits.get(SystemHandler.openedSectionId).getDataText();
						
						SystemHandler.saveFile(textFile, dataText, false);

						if(SystemHandler.sectionKits != null && SystemHandler.sectionKits.size() > 0)
						SystemHandler.performAnalyse();
					
					}
					
					//PrintWriter writer = null;
					//try {
					//    writer = new PrintWriter(textFile);
					//	writer.write(SystemHandler.sectionKits.get(SystemHandler.openedSectionId).getDataText());
					//    writer.close();
					//    SystemHandler.sectionKits.get(SystemHandler.openedSectionId).saved = true;
					//} catch (FileNotFoundException fnfe) {
					//	fnfe.printStackTrace();
					//}
				}
			});
		}
		return saveFilejMenuItem;
	}
	
	
	public String saveArquivo(){
		JFileChooser chooser = new JFileChooser();
		chooser.setName("");
		chooser.setDialogType(JFileChooser.CUSTOM_DIALOG);
		chooser.setDialogTitle(getSaveMenuText());
		chooser.setSelectedFile(new File(SystemHandler.sectionKits.get(SystemHandler.openedSectionId).name+".lotos"));
		chooser.setApproveButtonText(getSaveMenuText());
		chooser.cancelSelection();
		chooser.setAcceptAllFileFilterUsed(false);
		int returnVal = chooser.showSaveDialog(new JFrame());
		if(returnVal == JFileChooser.APPROVE_OPTION){
		return chooser.getSelectedFile().getPath();
		}else{
			return "STOP SAVE";
		}
	}
	
	/**
	 * This method initializes exitjMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitjMenuItem() {
		if (exitjMenuItem == null) {
			exitjMenuItem = new JMenuItem();
			exitjMenuItem.setText("Exit");
			exitjMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/exit.png")));

			exitjMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitjMenuItem;
	}


	/**
	 * This method initializes undojMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getUndojMenuItem() {
		if (undojMenuItem == null) {
			undojMenuItem = new JMenuItem();
			undojMenuItem.setText("Undo");
			undojMenuItem.setText(getUndoMenuItemText());
			undojMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/arrow_undo.png")));
			
		}
		return undojMenuItem;
	}


	/**
	 * This method initializes redojMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getRedojMenuItem() {
		if (redojMenuItem == null) {
			redojMenuItem = new JMenuItem();
			redojMenuItem.setText("Redo");
			redojMenuItem.setText(getRedoMenuItemText());
			redojMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/arrow_redo.png")));
			
			
		}
		return redojMenuItem;
	}


	/**
	 * This method initializes filesjTabbedPane1	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	


	/**
	 * This method initializes actionjPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getActionjPanel(){
		if (actionjPanel == null){
			FlowLayout flowLayout2 = new FlowLayout();
			flowLayout2.setAlignment(FlowLayout.LEFT);
			actionjPanel = new JPanel();
			actionjPanel.setLayout(flowLayout2);
			actionjPanel.setBackground(Color.white);
			actionjPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		return actionjPanel;
	}


	/**
	 * This method initializes textFilejScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	public JScrollPane getTextFilejScrollPane(){
		if (textFilejScrollPane == null){
			textFilejScrollPane = new JScrollPane();
			textFilejScrollPane.setWheelScrollingEnabled(false);
		}
		return textFilejScrollPane;
	}


	/**
	 * This method initializes toolbar1jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getToolbar1jPanel() {
		if (toolbar1jPanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints.weightx = 2.0;
			toolbar1jPanel = new JPanel();
			FlowLayout toolbar1jPanelLayout = new FlowLayout();
			toolbar1jPanelLayout.setAlignment(FlowLayout.LEFT);
			toolbar1jPanelLayout.setVgap(7);
			toolbar1jPanel.setLayout(toolbar1jPanelLayout);
			toolbar1jPanel.setAlignmentX(JToolBar.LEFT_ALIGNMENT);
			toolbar1jPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			// toolbar1jPanel.add(getArquivo());
			// toolbar1jPanel.add(getEditar());
			// toolbar1jPanel.add(getTemplatesJToolBar());
			// toolbar1jPanel.add(getTemplates());
			toolbar1jPanel.add(getCompilar());
			// toolbar1jPanel.add(getDebug());
			// toolbar1jPanel.add(getLooks());
			// toolbar1jPanel.add(getConfiguration());
			// toolbar1jPanel.add(getAjuda());
		}
		return toolbar1jPanel;
	}

	/**
	 * This method initializes resultsjScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	public JScrollPane getResultsjScrollPane() {
		if (resultsjScrollPane == null) {
			resultsjScrollPane = new JScrollPane();
			//resultsjScrollPane.setPreferredSize(new Dimension(0, 20));
			resultsjScrollPane.setViewportView(getResultsjTextPane());
		}
		return resultsjScrollPane;
	}


	/**
	 * This method initializes windowInfojPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getWindowInfojPanel() {
		if (windowInfojPanel == null) {
			windowInfojPanel = new JPanel();
			windowInfojPanel.setLayout(new BorderLayout());
			windowInfojPanel.add(getWindowInfojScrollPane(), BorderLayout.CENTER);
			windowInfojPanel.add(getWindowResultsInfojScrollBar(), BorderLayout.EAST);
		}
		return windowInfojPanel;
	}


	/**
	 * This method initializes actionInfojPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getActionInfojPanel() {
		if (actionInfojPanel == null) {
			actionInfojPanel = new JPanel();
			actionInfojPanel.setLayout(new BorderLayout());
			actionInfojPanel.add(getActionInfojScrollPane(), BorderLayout.CENTER);
			actionInfojPanel.add(getActionInfoAreajScrollBar(), BorderLayout.EAST);
		}
		return actionInfojPanel;
	}


	/**
	 * This method initializes textFilejPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getTextFilejPanel() {
		if (textFilejPanel == null) {
			textFilejPanel = new JPanel();
			textFilejPanel.setLayout(new BorderLayout());
			textFilejPanel.setOpaque(false);
			textFilejPanel.setVisible(false);
			textFilejPanel.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 4));
			textFilejPanel.add(getJtextScrollPane(), BorderLayout.CENTER);
			textFilejPanel.add(getJtextFileVerticalScrollBar(), BorderLayout.EAST);
			textFilejPanel.add(getJtextFileHorizontalScrollBar(), BorderLayout.SOUTH);
		}
		return textFilejPanel;
	}


	/**
	 * This method initializes lineInfojTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	public JTextPane getLineInfojTextPane() {
		if (lineInfojTextPane == null) {
			lineInfojTextPane = new JTextPane();
			lineInfojTextPane.setPreferredSize(new Dimension(39, 0));
			lineInfojTextPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lineInfojTextPane.setEditable(false);
			MutableAttributeSet mat = new SimpleAttributeSet();
	        StyleConstants.setLineSpacing(mat, 0.21f);
	        StyleConstants.setFontFamily(mat,"Courier");
			StyleConstants.setFontSize(mat,12);
	        lineInfojTextPane.getStyledDocument().setParagraphAttributes(0, lineInfojTextPane.getStyledDocument().getLength(), mat,true);
			lineInfojTextPane.setBackground(new Color(245,245,255));
			lineInfojTextPane.getMargin().top = 0;
			lineInfojTextPane.getMargin().left = 0;
		}
		return lineInfojTextPane;
	}

		
		
	/**
	 * This method initializes textFilejTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	public NonWrappingTextPane getTextFilejTextPane(){
		
		
		if (textFilejTextPane == null) {
			textFilejTextPane = new NonWrappingTextPane();
			//textFilejTextPane.setBackground(new Color(245,245,255));
			textFilejTextPane.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					
					//Depois padronizar a cor do caret
					
					boolean valid = true;
												
					int caretPosition  = SystemHandler.mainView.getTextFilejTextPane().getCaretPosition();
					 
					if(e.isControlDown() && e.getKeyChar()==' '){
												
						SystemHandler.isInAutoCompleteProcess = true;
						SystemHandler.caretPositionAfterAutoComplete = caretPosition+1;
						
						valid = SystemHandler.putCodeBlock(caretPosition);
												
					}else{
						SystemHandler.isInAutoCompleteProcess=false;
					}
					
					if(valid){
					
						latestLineCount = getLineCount(getLineInfojTextPane().getText());
					
						renoveLineNumbers();
					
						SystemHandler.sectionKits.get(SystemHandler.openedSectionId).saved=false;
						SwingUtilities.invokeLater(new TextActualizer());
													
					}
										
				}
				
				
				
				

				
			});
		}
		return textFilejTextPane;
	}
		
	public void replaceString(String s, int initialPoint, MutableAttributeSet attributes){
           
		try {
            getTextFilejTextPane().getDocument().remove(initialPoint, s.length());
            getTextFilejTextPane().getDocument().insertString(initialPoint, s, attributes);
        } catch (BadLocationException ble) {
            JOptionPane.showMessageDialog(null, ble.getMessage());
        }
     }
	
	public void setCaretPosition(int position){
		
		getTextFilejTextPane().setCaretPosition(position);		
		
	}

		
	private JScrollPane jfileInfoScrollPane = null;
	private JPanel textFileInfojPanel = null;
	private JPanel infoAreajPanel = null;
	private JScrollBar infoAreajScrollBar = null;
	private JScrollPane actionInfojScrollPane = null;
	private JPanel actionInfoPanel = null;
	private JScrollBar actionInfoAreajScrollBar = null;
	private JScrollPane actionjScrollPane = null;
	private JScrollBar windowResultsInfojScrollBar = null;
	private JScrollPane windowInfojScrollPane = null;
	private JPanel windowsInfoPanel = null;
	private JMenuItem openFilejMenuItem = null;
	private JMenuItem newProjectjMenuItem = null;
	private JMenuItem saveProjectjMenuItem = null;
	private JScrollPane jtextScrollPane = null;
	private JScrollBar jtextFileVerticalScrollBar = null;
	private JScrollBar jtextFileHorizontalScrollBar = null;
	
	/**
	 * This method initializes newFilejMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getNewFilejMenuItem() {
		if (newFilejMenuItem == null) {
			newFilejMenuItem = new JMenuItem();
			newFilejMenuItem.setText("New file");
			newFilejMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/page_add.png")));
			newFilejMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//newFileOptionsFrame frame = new newFileOptionsFrame();
					//frame.setVisible(true);
					newLotosFileWizard = new NewLotosFileWizard();
					newLotosFileWizard.setVisible(true);
					
					newLotosFileWizard.getFinishjButton().addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.out.println("actionPerformed()"); 
								FileForm newFileForm = newLotosFileWizard.getNewFileForm();
								
								String fileName = newFileForm.getFileName();
								if(fileName.length()<6 || !fileName.substring(fileName.length()-6).equals(".lotos")){
									System.out.println("N�o tem extens�o .lotos");
									fileName = fileName.concat(".lotos");
								}
								
								File textFile = new File(newFileForm.getSourceFolderPath()+"/"+fileName);
								
								SystemHandler.saveFile(textFile, "", true);
								SystemHandler.openFile(textFile);
								newLotosFileWizard.dispose();
								try {
									this.finalize();
								} catch (Throwable e2) {
									e2.printStackTrace();
								}
						}});
							
					
				}
			});
		}
		return newFilejMenuItem;
	}

	

	/**
	 * This is the default constructor
	 */
	public JLotosMainView(){
		super();
				
		/*try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
				    
		stile = new ViewStile();
		
		//save the state of the openned files
		nameOfFiles.add("unsaved");
		saveStateOfFiles.add(false);
		//----------------------------------
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	

	
	private void initialize(){
		this.setSize(SystemHandler.Width, SystemHandler.Height);
		this.setJMenuBar(getMainMenuJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("JLOTOS");
		//Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/lotos2.png"));
		//Graphics2D g = (Graphics2D)img.getGraphics();
		//g.setBackground(new Color(255,255,255,0));
		try {
			this.setIconImage(ImageIO.read(getClass().getResource("/resources/img/lotosLOGOmini.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setOpaque(false);
			jContentPane.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 2));
			//jContentPane.add(getMainjPanel(), java.awt.BorderLayout.CENTER);
			FlowLayout thisLayout = new FlowLayout();
			thisLayout.setVgap(0);
			thisLayout.setHgap(0);
			thisLayout.setAlignment(FlowLayout.LEFT);
			getJSplitPane().setOpaque(false);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
			//jContentPane.add(getJPanel1(), BorderLayout.NORTH);
			//jContentPane.add(getActionsjPanel(), BorderLayout.WEST);
			//jContentPane.add(getWindowsjPanel(), BorderLayout.SOUTH);
			//jContentPane.add(getJSplitPane(), BorderLayout.EAST);
		}
		return jContentPane;
	}
		
	public void addFileTab(JPanel newTextFileTabjPanel) {
		//testes
		SystemHandler.mainView.getTextFileInfojPanel().add(newTextFileTabjPanel);
		int count = SystemHandler.mainView.getTextFileInfojPanel().getComponentCount();
		Dimension d = SystemHandler.mainView.getTextFileInfojPanel().getPreferredSize();
		Dimension dTab = newTextFileTabjPanel.getPreferredSize();
		
		
		Dimension dInfo = newTextFileTabjPanel.getPreferredSize();
		newTextFileTabjPanel.setBounds(SystemHandler.actualXBound, 0, dInfo.width, dInfo.height);
		
		SystemHandler.actualXBound += dInfo.width-15;
		
		SystemHandler.mainView.getTextFileInfojPanel().setPreferredSize(new Dimension(SystemHandler.actualXBound+20,d.height));
		this.validate();
		SystemHandler.mainView.getTextFileInfojPanel().repaint();
		
	}
	
	public void addResultWindowTab(JPanel newResultWindowTabjPanel) {
				
		SystemHandler.mainView.getWindowsInfoPanel().add(newResultWindowTabjPanel, null);
		int count = SystemHandler.mainView.getWindowsInfoPanel().getComponentCount();
		Dimension d = SystemHandler.mainView.getWindowsInfoPanel().getPreferredSize();
		Dimension dTab = newResultWindowTabjPanel.getPreferredSize();
		
		Dimension dInfo = newResultWindowTabjPanel.getPreferredSize();
		newResultWindowTabjPanel.setBounds(SystemHandler.actualResultXBound, 0, dInfo.width, dInfo.height);
		
		SystemHandler.actualResultXBound += dInfo.width-15;
		
		SystemHandler.mainView.getWindowsInfoPanel().setPreferredSize(new Dimension(dTab.width*(count+1),d.height));
		this.validate();
		SystemHandler.mainView.getWindowsInfoPanel().repaint();
	}
	
	public void refreshDataTabs(){
		int mark = SystemHandler.numberOfSections-1;
		int aux = -1;
		
		for(int cont = 0; cont< SystemHandler.numberOfSections; cont++){
			if(cont==SystemHandler.openedSectionId){
				aux = 1;
				mark = 1;
				//SystemHandler.mainView.getTextFileInfojPanel().getComponent(cont).setBackground(getSelectedTextFileInfoColor());
				//((JPanel)SystemHandler.mainView.getTextFileInfojPanel().getComponent(cont)).getComponent(0).setBackground(getSelectedTextFileInfoColor());
				int value = SystemHandler.sectionKits.get(cont).getTextFileTabjPanel().getX()+SystemHandler.sectionKits.get(cont).getTextFileTabjPanel().getWidth()+15 - getInfoAreajPanel().getWidth();
				if (value < 0 )value = 0;
				getInfoAreajScrollBar().setValue(value);
				SystemHandler.mainView.getTextFileInfojPanel().setComponentZOrder(SystemHandler.sectionKits.get(cont).getTextFileTabjPanel(), 0);
				SystemHandler.sectionKits.get(cont).selectBackground();
				SystemHandler.sectionKits.get(cont).getFecharJLabel().setVisible(true);
			}else{
				//SystemHandler.mainView.getTextFileInfojPanel().getComponent(cont).setBackground(getTextFileInfoColor());
				//((JPanel)SystemHandler.mainView.getTextFileInfojPanel().getComponent(cont)).getComponent(0).setBackground(getTextFileInfoColor());

				SystemHandler.mainView.getTextFileInfojPanel().setComponentZOrder(SystemHandler.sectionKits.get(cont).getTextFileTabjPanel(), mark);
				mark += aux;
				SystemHandler.sectionKits.get(cont).unselectBackground();
				SystemHandler.sectionKits.get(cont).getFecharJLabel().setVisible(false);
				
			}
			
		}
		
		TextUI ui = getTextFilejTextPane().getUI();
		Dimension pref = ui.getPreferredSize(getTextFilejTextPane());
		textFilejTextPane.setSize(new Dimension(pref.width+100,pref.height));
		//getRowInfojPanel().setPreferredSize(new Dimension(getRowInfojPanel().getWidth(),pref.height));
		
		getTextFileInfojPanel().repaint();
		
		this.validate();
		
	}
	
	public void refreshResultTabs(){
		
		int mark = SystemHandler.numberOfResultWindows-1;
		int aux = -1;
		
		for(int cont = 0; cont< SystemHandler.numberOfResultWindows; cont++){
			if(cont==SystemHandler.openedResultWindow){
				aux = 1;
				mark = 1;
				//SystemHandler.mainView.getTextFileInfojPanel().getComponent(cont).setBackground(getSelectedTextFileInfoColor());
				//((JPanel)SystemHandler.mainView.getTextFileInfojPanel().getComponent(cont)).getComponent(0).setBackground(getSelectedTextFileInfoColor());
				int value = SystemHandler.resultKits.get(cont).getResultWindowTabjPanel().getX()+SystemHandler.resultKits.get(cont).getResultWindowTabjPanel().getWidth()+15 - getWindowsInfoPanel().getWidth();
				if (value < 0 )value = 0;
				getWindowResultsInfojScrollBar().setValue(value);
				SystemHandler.mainView.getWindowsInfoPanel().setComponentZOrder(SystemHandler.resultKits.get(cont).getResultWindowTabjPanel(), 0);
				SystemHandler.resultKits.get(cont).selectBackground();
				SystemHandler.resultKits.get(cont).getFecharJLabel().setVisible(true);
			}else{
				//SystemHandler.mainView.getTextFileInfojPanel().getComponent(cont).setBackground(getTextFileInfoColor());
				//((JPanel)SystemHandler.mainView.getTextFileInfojPanel().getComponent(cont)).getComponent(0).setBackground(getTextFileInfoColor());

				SystemHandler.mainView.getWindowsInfoPanel().setComponentZOrder(SystemHandler.resultKits.get(cont).getResultWindowTabjPanel(), mark);
				mark += aux;
				SystemHandler.resultKits.get(cont).unselectBackground();
				SystemHandler.resultKits.get(cont).getFecharJLabel().setVisible(false);
				
			}
			
		}
		
		TextUI ui = getResultsjTextPane().getUI();
		Dimension pref = ui.getPreferredSize(getResultsjTextPane());
		resultsjTextPane.setSize(new Dimension(pref.width+100,pref.height));
		//getRowInfojPanel().setPreferredSize(new Dimension(getRowInfojPanel().getWidth(),pref.height));
		

		getWindowsInfoPanel().repaint();
		
		this.validate();
		
		/*
		
		for(int cont = 0; cont< SystemHandler.numberOfResultWindows; cont++){
			
			if(cont==SystemHandler.openedResultWindow){
				
				SystemHandler.mainView.getWindowsInfoPanel().getComponent(cont).setBackground(getSelectedTextFileInfoColor());
				((JPanel)SystemHandler.mainView.getWindowsInfoPanel().getComponent(cont)).getComponent(0).setBackground(getSelectedTextFileInfoColor());
				
				
			}else{
				SystemHandler.mainView.getWindowsInfoPanel().getComponent(cont).setBackground(getTextFileInfoColor());
				((JPanel)SystemHandler.mainView.getWindowsInfoPanel().getComponent(cont)).getComponent(0).setBackground(getTextFileInfoColor());
				
				
			}
			
		}
		
		TextUI ui = getResultsjTextPane().getUI();
		Dimension pref = ui.getPreferredSize(getResultsjTextPane());
		resultsjTextPane.setSize(new Dimension(pref.width+100,pref.height));
		//getRowInfojPanel().setPreferredSize(new Dimension(getRowInfojPanel().getWidth(),pref.height));
		
		
		this.validate();*/
		
	}

	/**
	 * This method initializes jfileInfoScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJfileInfoScrollPane() {
		if (jfileInfoScrollPane == null) {
			jfileInfoScrollPane = new JScrollPane();
			jfileInfoScrollPane.setBorder(BorderFactory.createEmptyBorder());
			
			jfileInfoScrollPane.getInsets().set(0, 0, 0, 0);
			jfileInfoScrollPane.setViewportView(getTextFileInfojPanel());
			jfileInfoScrollPane.setHorizontalScrollBar(getInfoAreajScrollBar());
			//jfileInfoScrollPane.setPreferredSize(new java.awt.Dimension(831, 30));
		}
		return jfileInfoScrollPane;
	}

	/**
	 * This method initializes textFileInfojPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getTextFileInfojPanel() {
		if (textFileInfojPanel == null) {
			//FlowLayout flowLayout = new FlowLayout();
			//flowLayout.setHgap(0);
			//flowLayout.setAlignment(FlowLayout.LEFT);
			//flowLayout.setVgap(0);
			
			textFileInfojPanel = new JPanel();
			textFileInfojPanel.setLayout(null);
			textFileInfojPanel.setDoubleBuffered(true);
			textFileInfojPanel.getInsets().set(0, 0, 0, 0);
			textFileInfojPanel.setPreferredSize(new Dimension(0, 25));
			textFileInfojPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		}
		return textFileInfojPanel;
	}

	/**
	 * This method initializes infoAreajPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getInfoAreajPanel() {
		if (infoAreajPanel == null) {
			infoAreajPanel = new JPanel();
			infoAreajPanel.setLayout(new BorderLayout());
			infoAreajPanel.add(getJfileInfoScrollPane(), BorderLayout.CENTER);
			infoAreajPanel.add(getInfoAreajScrollBar(), BorderLayout.EAST);
		}
		return infoAreajPanel;
	}

	/**
	 * This method initializes infoAreajScrollBar	
	 * 	
	 * @return javax.swing.JScrollBar	
	 */
	private JScrollBar getInfoAreajScrollBar() {
		if (infoAreajScrollBar == null) {
			infoAreajScrollBar = new JScrollBar();
			infoAreajScrollBar.setOrientation(JScrollBar.HORIZONTAL);
			infoAreajScrollBar.setPreferredSize(new Dimension(20,0));
			infoAreajScrollBar.setUnitIncrement(30);
		}
		return infoAreajScrollBar;
	}
	

	public int latestLineCount = 0;
	private JMenuItem shortcutsjMenuItem;
	private JMenuItem optionsjMenuItem;
	private JMenuItem metricsjMenuItem;
	private JMenuItem debugjMenuItem;
	private JMenuItem contactjMenuItem;
	private JMenuItem languagejMenuItem;
	private JMenuItem manualjMenuItem;
	private JMenuItem aboutjMenuItem;
	private JMenuItem findDeadLockjMenuItem;
	private JMenuItem errorReportjMenuItem;
	private JMenuItem compilejMenuItem;
	private JMenuItem editorColorsjMenuItem;
	private JMenuItem fontsjMenuItem;
	private JMenuItem refreshjMenuItem;
	private JMenuItem findReplacejMenuItem;
	private JMenuItem pastejMenuItem;
	private JMenuItem cutjMenuItem;
	private JMenuItem copyjMenuItem;
	private JToolBar templatesjToolBar;
	private JToggleButton exampleLotosjToggleButton;
	private JMenuItem exampleLotosjMenuItem;
	private JMenu helpjMenu;
	private JMenu configurationjMenu;
	private JMenu graphicsjMenu;
	private JMenu debugjMenu;
	private JMenu editorjMenu;
	private JMenu templatesjMenu;
	private JButton atalhosTool;
	private JButton alarmesTool;
	private JButton configuracoesTool;
	private JToolBar configuration;
	private JButton contatoTool;
	private JButton ajudaLOTOS;
	private JButton ajudaManual;
	private JButton ajudaJlotos;
	private JToolBar Ajuda;
	private JButton looksTool;
	private JToolBar Looks;
	private JButton debugTool5;
	private JButton debugTool;
	private JToolBar Debug;
	private JToolBar Templates;
	private JButton coresEditorTool;
	private JButton fontesEditorTool;
	private JButton deadlocksTool;
	private JButton relatorioErrosTool;
	private JButton compilarTool;
	private JToolBar Compilar;
	private JButton undoTool;
	private JButton redoTool;
	private JButton atualizarTool;
	private JButton pesquisarTool;
	private JButton colarTool;
	private JButton recortarTool;
	private JButton copiarTool;
	private JToolBar Editar;
	private JToggleButton salvarAutoTool;
	private JButton imprimirTool;
	private JButton fecharTool;
	private JButton novoTool;
	private JButton salvarTool;
	private JButton abrirTool;
	private JToolBar Arquivo;
	private JLabel jLabel1;
	private JPanel jPanel2;
	private JPanel jPanel1;
	private JMenu CompileToolsjMenu = null;

	private JPanel rowInfojPanel = null;

	private JList lineConditionsjList = null;

	private JSplitPane jSplitPane = null;

	private JSplitPane upjSplitPane = null;

	private JTextPane resultsjTextPane = null;

	private JPanel resultColumn2jPanel = null;

	private JTextPane resultColumn2jTextPane = null;

	private JSplitPane jSplitPaneLeft = null;

	private JPanel viewsjPanel = null;

	private JScrollPane viewsjScrollPane = null;

	private JPanel viewsInfojPanel = null;

	private JPanel viewjPanel = null;

	private JScrollPane viewInfojScrollPane = null;

	private JScrollBar viewInfojScrollBar = null;

	private JPanel viewInfojPanel = null;
	
	public void renoveLineNumbers() {
		
		MutableAttributeSet att = new SimpleAttributeSet();
		StyleConstants.setLineSpacing(att, 0.1f);

        StyleConstants.setFontFamily(att,"Courier");

		StyleConstants.setFontSize(att,12);
						
		int lineCount = getLineCount(textFilejTextPane.getText());
				
		int cont;
		String str = "";
		
		if(lineCount>latestLineCount){
		cont = latestLineCount;
		
		if(cont!=0)
			try {
				getLineInfojTextPane().getDocument().insertString(getLineInfojTextPane().getDocument().getLength(),"\n", att);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

		while(cont < lineCount){
			
			try {
			getLineInfojTextPane().getDocument().insertString(getLineInfojTextPane().getDocument().getLength(),"."+(cont+1), att);
			if(cont<lineCount-1)getLineInfojTextPane().getDocument().insertString(getLineInfojTextPane().getDocument().getLength(),"\n", att);

			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			
			cont++;
			
			//str = str.concat(cont+".\n");
		}
		}else if(lineCount<latestLineCount){
			cont = latestLineCount - lineCount;
			
			while(cont > 0){
				

				str = getLineInfojTextPane().getText();
				
				int removeMark  = str.lastIndexOf("\n");
				
				String removed = str.substring(removeMark);

				try {
					getLineInfojTextPane().getDocument().remove((getLineInfojTextPane().getDocument().getLength()- removed.length()), removed.length());
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				
				cont--;
				
			}
			
			
		}else{
			if(latestLineCount==1){
				getLineInfojTextPane().setText(".1");
			}
		}
		
		//add the line count to the current SectionKit
		SystemHandler.sectionKits.get(SystemHandler.openedSectionId).setLineText(getLineInfojTextPane().getText());
		
		//validate();
		
	}
	
	public int getLineCount(String text){
		int lineCount = 1;
		
		for(int cont=0; cont < text.length(); cont ++){
			
			if(text.charAt(cont)=='\n'){
				lineCount++;
			}
		}
		
		return lineCount;
		
	}
	
	
	/**
	 * This method initializes actionInfojScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getActionInfojScrollPane() {
		if (actionInfojScrollPane == null) {
			actionInfojScrollPane = new JScrollPane();
			actionInfojScrollPane.setViewportView(getActionInfoPanel());
			actionInfojScrollPane.setHorizontalScrollBar(getActionInfoAreajScrollBar());
		}
		return actionInfojScrollPane;
	}

	/**
	 * This method initializes actionInfoPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getActionInfoPanel() {
		if (actionInfoPanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setHgap(0);
			flowLayout1.setAlignment(FlowLayout.LEFT);
			flowLayout1.setVgap(0);
			actionInfoPanel = new JPanel();
			actionInfoPanel.setPreferredSize(new Dimension(0, 25));
			actionInfoPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			actionInfoPanel.setLayout(flowLayout1);
			actionInfoPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		return actionInfoPanel;
	}


	/**
	 * This method initializes actionInfoAreajScrollBar1	
	 * 	
	 * @return javax.swing.JScrollBar	
	 */
	private JScrollBar getActionInfoAreajScrollBar() {
		if (actionInfoAreajScrollBar == null) {
			actionInfoAreajScrollBar = new JScrollBar();
			actionInfoAreajScrollBar.setPreferredSize(new Dimension(20, 0));
			actionInfoAreajScrollBar.setUnitIncrement(30);
			actionInfoAreajScrollBar.setOrientation(JScrollBar.HORIZONTAL);
		}
		return actionInfoAreajScrollBar;
	}

	/**
	 * This method initializes actionjScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	public JScrollPane getActionjScrollPane() {
		if (actionjScrollPane == null) {
			actionjScrollPane = new JScrollPane();
			actionjScrollPane.setViewportView(getActionjPanel());
			actionjScrollPane.setPreferredSize(new Dimension(0, 0));
		}
		return actionjScrollPane;
	}

	/**
	 * This method initializes windowResultsInfojScrollBar	
	 * 	
	 * @return javax.swing.JScrollBar	
	 */
	private JScrollBar getWindowResultsInfojScrollBar() {
		if (windowResultsInfojScrollBar == null) {
			windowResultsInfojScrollBar = new JScrollBar();
			windowResultsInfojScrollBar.setPreferredSize(new Dimension(20, 0));
			windowResultsInfojScrollBar.setUnitIncrement(30);
			windowResultsInfojScrollBar.setOrientation(JScrollBar.HORIZONTAL);
		}
		return windowResultsInfojScrollBar;
	}

	/**
	 * This method initializes windowInfojScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getWindowInfojScrollPane() {
		if (windowInfojScrollPane == null) {
			windowInfojScrollPane = new JScrollPane();
			windowInfojScrollPane.setViewportView(getWindowsInfoPanel());
			windowInfojScrollPane.setHorizontalScrollBar(getWindowResultsInfojScrollBar());
		}
		return windowInfojScrollPane;
	}

	/**
	 * This method initializes windowsInfoPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getWindowsInfoPanel() {
		if (windowsInfoPanel == null) {
			windowsInfoPanel = new JPanel();
			windowsInfoPanel.setLayout(null);		
			windowsInfoPanel.setPreferredSize(new Dimension(0, 25));
			windowsInfoPanel.getInsets().set(0, 0, 0, 0);
			windowsInfoPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		}
		return windowsInfoPanel;
	}

	
	/**
	 * This method initializes openFilejMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getOpenFilejMenuItem() {
		if (openFilejMenuItem == null) {
			openFilejMenuItem = new JMenuItem();
			openFilejMenuItem.setText("Open file");
			openFilejMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/page_edit.png")));
			KeyStroke ctrlO = KeyStroke.getKeyStroke("control O");
			openFilejMenuItem.setAccelerator(ctrlO);
			openFilejMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					File newFile = getFile();
					
					if(newFile!=null)SystemHandler.openFile(newFile);
				}
			});
		}
		return openFilejMenuItem;
	}

	/**
	 * This method initializes newProjectjMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getNewProjectjMenuItem() {
		if (newProjectjMenuItem == null) {
			newProjectjMenuItem = new JMenuItem();
			newProjectjMenuItem.setText("New project");
		}
		return newProjectjMenuItem;
	}


	/**
	 * This method initializes saveProjectjMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveProjectjMenuItem() {
		if (saveProjectjMenuItem == null) {
			saveProjectjMenuItem = new JMenuItem();
			saveProjectjMenuItem.setText("Save project");
			saveProjectjMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(SystemHandler.openedProject!=null){
						for(int cont=0; cont<SystemHandler.openedProject.lotosFiles.size();cont++){
							SystemHandler.openedProject.lotosFiles.get(cont).saveThisFile();
						}
					}else{
						JOptionPane.showMessageDialog(SystemHandler.mainView, "Procedimento imcompleto\nN�o existe projeto aberto!");
					}
				}
			});
		}
		return saveProjectjMenuItem;
	}
	

	/**
	 * This method initializes jtextScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 * 
	 */
	public JScrollPane getJtextScrollPane() {
		if (jtextScrollPane == null) {
			jtextScrollPane = new JScrollPane();
			jtextScrollPane.setOpaque(false);
			//jtextScrollPane.setRowHeaderView(view);
			jtextScrollPane.setVerticalScrollBar(getJtextFileVerticalScrollBar());
			jtextScrollPane.setHorizontalScrollBar(getJtextFileHorizontalScrollBar());
			jtextScrollPane.getVerticalScrollBar().setBlockIncrement(30);
			jtextScrollPane.setViewportView(getTextFilejTextPane());
			
		}
		return jtextScrollPane;
	}

	/**
	 * This method initializes jtextFileVerticalScrollBar	
	 * 	
	 * @return javax.swing.JScrollBar	
	 */
	public JScrollBar getJtextFileVerticalScrollBar() {
		if (jtextFileVerticalScrollBar == null) {
			jtextFileVerticalScrollBar = new JScrollBar();
			jtextFileVerticalScrollBar.setOrientation(JScrollBar.VERTICAL);
			jtextFileVerticalScrollBar.setPreferredSize(new Dimension(20,0));
			jtextFileVerticalScrollBar.setUnitIncrement(10);
			jtextFileVerticalScrollBar.setBlockIncrement(50);
			jtextFileVerticalScrollBar.addAdjustmentListener(new AdjustmentListener(){

				@Override
				public void adjustmentValueChanged(AdjustmentEvent e) {
										
					//System.out.println(getJlineInfoScrollPane().getVerticalScrollBar().getValue());
															
					SystemHandler.mainView.validate();
					
				}
				
			});
		}
		return jtextFileVerticalScrollBar;
	}

	/**
	 * This method initializes jtextFileHorizontalScrollBar	
	 * 	
	 * @return javax.swing.JScrollBar	
	 */
	public JScrollBar getJtextFileHorizontalScrollBar() {
		if (jtextFileHorizontalScrollBar == null) {
			jtextFileHorizontalScrollBar = new JScrollBar();
			jtextFileHorizontalScrollBar.setOrientation(JScrollBar.HORIZONTAL);
			jtextFileHorizontalScrollBar.setPreferredSize(new Dimension(0,20));
		}
		return jtextFileHorizontalScrollBar;
	}

	/**
	 * This method initializes CompileToolsjMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getCompileToolsjMenu() {
		if (CompileToolsjMenu == null) {
			CompileToolsjMenu = new JMenu();
			CompileToolsjMenu.setText("Run");
			CompileToolsjMenu.add(getCompilejMenuItem());
			// CompileToolsjMenu.add(getErrorReportjMenuItem());
			// CompileToolsjMenu.add(getFindDeadLockjMenuItem());
		}
		return CompileToolsjMenu;
	}

	/**
	 * This method initializes rowInfojPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getRowInfojPanel() {
		if (rowInfojPanel == null) {
			rowInfojPanel = new JPanel();
			rowInfojPanel.getInsets().top = 0;
			rowInfojPanel.setLayout(new BorderLayout());
			rowInfojPanel.setSize(new Dimension(48, 10));
			rowInfojPanel.add(getLineConditionsjList(), BorderLayout.WEST);
			rowInfojPanel.add(getLineInfojTextPane(), BorderLayout.EAST);
			rowInfojPanel.setBorder(BorderFactory.createLineBorder(SystemColor.inactiveCaption, 1));
		}
		return rowInfojPanel;
	}

	/**
	 * This method initializes lineConditionsjList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getLineConditionsjList() {
		if (lineConditionsjList == null) {
			lineConditionsjList = new JList();
			lineConditionsjList.setPreferredSize(new Dimension(9, 0));
			lineConditionsjList.setBackground(new Color(215,215,255));
		}
		return lineConditionsjList;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOneTouchExpandable(true);
			//jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			//jSplitPane.setRightComponent(getWindowsjPanel());
			jSplitPane.setRightComponent(getUpjSplitPane());
			jSplitPane.setOpaque(false);
			jSplitPane.setInheritsPopupMenu(true);
			//getUpjSplitPane().setVisible(false);					
			jSplitPane.setLeftComponent(null);	
			/**
			 * Depois de verificar a estrutura de projeto, descomentar
			 */
			//jSplitPane.setLeftComponent(getViewsjPanel());
			//getViewsjPanel().setVisible(false);
		}
		return jSplitPane;
	}

	
	/**
	 * This method initializes upjSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	public JSplitPane getUpjSplitPane() {
		if (upjSplitPane == null) {
			upjSplitPane = new JSplitPane();
			upjSplitPane.setOneTouchExpandable(true);
			upjSplitPane.setContinuousLayout(true);
			upjSplitPane.setOpaque(false);
			upjSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			upjSplitPane.setRightComponent(getWindowsjPanel());
			upjSplitPane.setRightComponent(getWindowsjPanel());
			upjSplitPane.setLeftComponent(getJSplitPaneLeft());
		}
		return upjSplitPane;
	}

	/**
	 * This method initializes resultsjTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	public JTextPane getResultsjTextPane() {
		if (resultsjTextPane == null) {
			resultsjTextPane = new JTextPane();
			resultsjTextPane.setEditable(false);
		}
		return resultsjTextPane;
	}

	/**
	 * This method initializes resultColumn2jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getResultColumn2jPanel() {
		if (resultColumn2jPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.weightx = 1.0;
			resultColumn2jPanel = new JPanel();
			resultColumn2jPanel.setPreferredSize(new Dimension(80,1));
			resultColumn2jPanel.setLayout(new GridBagLayout());
			resultColumn2jPanel.add(getResultColumn2jTextPane(), gridBagConstraints1);
		}
		return resultColumn2jPanel;
	}
	
	/**
	 * This method initializes resultColumn2jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	public JTextPane getResultColumn2jTextPane() {
		if (resultColumn2jTextPane == null) {
			resultColumn2jTextPane = new JTextPane();
			resultColumn2jTextPane.setBackground(new Color(245,245,255));
			resultColumn2jTextPane.setEditable(false);
			MutableAttributeSet mat = new SimpleAttributeSet();
			StyleConstants.setAlignment(mat, StyleConstants.ALIGN_RIGHT);
			resultColumn2jTextPane.setParagraphAttributes(mat, false);
		}
		return resultColumn2jTextPane;
	}

	/**
	 * This method initializes jSplitPaneLeft	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPaneLeft() {
		if (jSplitPaneLeft == null) {
			jSplitPaneLeft = new JSplitPane();
			jSplitPaneLeft.setOneTouchExpandable(true);
			//jSplitPaneLeft.setForeground(new Color(100,100,100));
			jSplitPaneLeft.setPreferredSize(new java.awt.Dimension(0, SystemHandler.Height*3/4));
			jSplitPaneLeft.setOpaque(false);
			jSplitPaneLeft.setLeftComponent(getMainjPanel());
			/**
			 * Depois de verificar a estrutura de projeto, descomentar
			 */
			// jSplitPaneLeft.setRightComponent(getActionsjPanel());
			jSplitPaneLeft.setRightComponent(null);
			//jSplitPaneLeft.setLeftComponent(getViewsjPanel());
		}
		return jSplitPaneLeft;
	}

	/**
	 * This method initializes viewsjPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getViewsjPanel() {
		if (viewsjPanel == null) {
			viewsjPanel = new JPanel();
			viewsjPanel.setLayout(new BorderLayout());
			//viewsjPanel.setPreferredSize(new Dimension(SystemHandler.Width*2/13,0));
			viewsjPanel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 1));
			viewsjPanel.add(getLeftJToolBar(),BorderLayout.WEST);
			getLeftJToolBar().setPreferredSize(new Dimension(25, viewsjPanel.getPreferredSize().height));
			viewsjPanel.add(getAuxJPanel(),BorderLayout.CENTER);
		}
		return viewsjPanel;
	}
	
	private JPanel auxJPanel = null;
	
	/**
	 * This method initializes auxJPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getAuxJPanel(){
		
		if(auxJPanel==null){
			
			auxJPanel = new JPanel();
			auxJPanel.setLayout(new BorderLayout());
		auxJPanel.setBorder(BorderFactory.createEmptyBorder());
		auxJPanel.add(getViewsjScrollPane(), BorderLayout.CENTER);
		auxJPanel.add(getViewsInfojPanel(), BorderLayout.NORTH);
		auxJPanel.setVisible(false);
		}
		return auxJPanel;
	}

	/**
	 * This method initializes viewsjScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getViewsjScrollPane() {
		if (viewsjScrollPane == null) {
			viewsjScrollPane = new JScrollPane();
			viewsjScrollPane.setViewportView(getViewjPanel());
			viewsjScrollPane.setPreferredSize(new Dimension(0, 0));
		}
		return viewsjScrollPane;
	}

	/**
	 * This method initializes viewsInfojPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getViewsInfojPanel() {
		if (viewsInfojPanel == null) {
			viewsInfojPanel = new JPanel();
			viewsInfojPanel.setLayout(new BorderLayout());
			viewsInfojPanel.add(getViewInfojScrollPane(), BorderLayout.CENTER);
			viewsInfojPanel.add(getViewInfojScrollBar(), BorderLayout.EAST);
		}
		return viewsInfojPanel;
	}

	/**
	 * This method initializes viewjPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getViewjPanel() {
		if (viewjPanel == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			flowLayout2.setAlignment(FlowLayout.LEFT);
			viewjPanel = new JPanel();
			viewjPanel.setLayout(flowLayout2);
			viewjPanel.setBackground(Color.white);
			viewjPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		return viewjPanel;
	}

	/**
	 * This method initializes viewInfojScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getViewInfojScrollPane() {
		if (viewInfojScrollPane == null) {
			viewInfojScrollPane = new JScrollPane();
			viewInfojScrollPane.setViewportView(getViewInfojPanel());
			viewInfojScrollPane.setHorizontalScrollBar(getViewInfojScrollBar());
		}
		return viewInfojScrollPane;
	}

	/**
	 * This method initializes viewInfojScrollBar	
	 * 	
	 * @return javax.swing.JScrollBar	
	 */
	private JScrollBar getViewInfojScrollBar() {
		if (viewInfojScrollBar == null) {
			viewInfojScrollBar = new JScrollBar();
			viewInfojScrollBar.setPreferredSize(new Dimension(20, 0));
			viewInfojScrollBar.setUnitIncrement(30);
			viewInfojScrollBar.setOrientation(JScrollBar.HORIZONTAL);
		}
		return viewInfojScrollBar;
	}

	/**
	 * This method initializes viewInfojPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getViewInfojPanel() {
		if (viewInfojPanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setHgap(0);
			flowLayout1.setAlignment(FlowLayout.LEFT);
			flowLayout1.setVgap(0);
			viewInfojPanel = new JPanel();
			viewInfojPanel.setPreferredSize(new Dimension(0, 25));
			viewInfojPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			viewInfojPanel.setLayout(flowLayout1);
			viewInfojPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		return viewInfojPanel;
	}
	
	private JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new JPanel();
			BorderLayout jPanel1Layout = new BorderLayout();
			jPanel1.setLayout(jPanel1Layout);
			jPanel1.add(getToolsBarjPanel(), BorderLayout.CENTER);
			jPanel1.add(getJPanel2(), BorderLayout.WEST);
		}
		return jPanel1;
	}
	
	private JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel2.add(getJLabel1());
		}
		return jPanel2;
	}
	
	private JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setIcon(new ImageIcon(getClass().getResource("/resources/img/flordelotuscomnome.png")));
			jLabel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		return jLabel1;
	}
	
	private JToolBar getArquivo() {
		if(Arquivo == null) {
			Arquivo = new JToolBar();
			Arquivo.setRollover(true);
			Arquivo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
			Arquivo.setToolTipText("File");
			Arquivo.setName("Arquivo");
			Arquivo.add(getNovoTool());
			Arquivo.add(getAbrirTool());
			Arquivo.add(getSalvarTool());
			Arquivo.add(getFecharTool());
			Arquivo.add(getImprimirTool());
			Arquivo.add(getSalvarAutoTool());
		}
		return Arquivo;
	}
	
	private JButton getAbrirTool() {
		if(abrirTool == null) {
			abrirTool = new JButton();
			abrirTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			abrirTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/page_edit.png")));
			abrirTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			abrirTool.setToolTipText("Open (Ctrl+O)");
			abrirTool.setFocusable(false);
			abrirTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//abrirToolActionPerformed(evt);
				}
			});
		}
		return abrirTool;
	}
	
	private JButton getSalvarTool() {
		if(salvarTool == null) {
			salvarTool = new JButton();
			salvarTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			salvarTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/page_save.png")));
			salvarTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			salvarTool.setToolTipText("Save (Ctrl+S)");
			salvarTool.setFocusable(false);
			salvarTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//salvarToolActionPerformed(evt);
				}
			});
		}
		return salvarTool;
	}
	
	private JButton getNovoTool() {
		if(novoTool == null) {
			novoTool = new JButton();
			novoTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			novoTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/page_add.png")));
			novoTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			novoTool.setToolTipText("New (Ctrl+N)");
			novoTool.setFocusable(false);
			novoTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//novoToolActionPerformed(evt);
				}
			});
		}
		return novoTool;
	}
	
	private JButton getFecharTool() {
		if(fecharTool == null) {
			fecharTool = new JButton();
			fecharTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			fecharTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/page_delete.png")));
			fecharTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			fecharTool.setToolTipText(" Close (Ctrl+Shift+C)");
			fecharTool.setFocusable(false);
			fecharTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//fecharToolActionPerformed(evt);
				}
			});
		}
		return fecharTool;
	}
	
	private JButton getImprimirTool() {
		if(imprimirTool == null) {
			imprimirTool = new JButton();
			imprimirTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			imprimirTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/printer.png")));
			imprimirTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			imprimirTool.setToolTipText("Print (Ctrl+P)");
			imprimirTool.setFocusable(false);
			imprimirTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//imprimirToolActionPerformed(evt);
				}
			});
		}
		return imprimirTool;
	}
	
	private JToggleButton getSalvarAutoTool() {
		if(salvarAutoTool == null) {
			salvarAutoTool = new JToggleButton();
			salvarAutoTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			salvarAutoTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/clock.png")));
			salvarAutoTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			//salvarAutoTool.setSelected(salvarAuto);
			salvarAutoTool.setFocusable(false);
			salvarAutoTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//salvarAutoToolActionPerformed(evt);
				}
			});
		}
		return salvarAutoTool;
	}
	
	private JToolBar getEditar() {
		if(Editar == null) {
			Editar = new JToolBar();
			Editar.setRollover(true);
			Editar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			Editar.setToolTipText("Edit");
			Editar.setName("Editar");
			Editar.add(getCopiarTool());
			Editar.add(getRecortarTool());
			Editar.add(getColarTool());
			Editar.add(getPesquisarTool());
			Editar.add(getAtualizarTool());
			Editar.add(getRedoTool());
			Editar.add(getUndoTool());
		}
		return Editar;
	}
	
	private JButton getCopiarTool() {
		if(copiarTool == null) {
			copiarTool = new JButton();
			copiarTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			copiarTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/copy.png")));
			copiarTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			copiarTool.setToolTipText("Copy (Ctr+C)");
			copiarTool.setFocusable(false);
			copiarTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//copiarToolActionPerformed(evt);
				}
			});
		}
		return copiarTool;
	}
	
	private JButton getRecortarTool() {
		if(recortarTool == null) {
			recortarTool = new JButton();
			recortarTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			recortarTool.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/cut.png")));
			recortarTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			recortarTool.setToolTipText("Cut (Crt+X)");
			recortarTool.setFocusable(false);
			recortarTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//recortarToolActionPerformed(evt);
				}
			});
		}
		return recortarTool;
	}
	
	private JButton getColarTool() {
		if(colarTool == null) {
			colarTool = new JButton();
			colarTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			colarTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/briefcase.png")));
			colarTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			colarTool.setToolTipText("Paste (Ctr+V)");
			colarTool.setFocusable(false);
			colarTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//colarToolActionPerformed(evt);
				}
			});
		}
		return colarTool;
	}
	
	private JButton getPesquisarTool() {
		if(pesquisarTool == null) {
			pesquisarTool = new JButton();
			pesquisarTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			pesquisarTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/find.png")));
			pesquisarTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			pesquisarTool.setToolTipText("Find/Replace (Ctrl+H)");
			pesquisarTool.setFocusable(false);
			pesquisarTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//pesquisarToolActionPerformed(evt);
				}
			});
		}
		return pesquisarTool;
	}
	
	private JButton getAtualizarTool() {
		if(atualizarTool == null) {
			atualizarTool = new JButton();
			atualizarTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			atualizarTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/arrow_refresh.png")));
			atualizarTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			atualizarTool.setToolTipText("Refresh Text (Ctrl+F5)");
			atualizarTool.setFocusable(false);
			atualizarTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//atualizarToolActionPerformed(evt);
				}
			});
		}
		return atualizarTool;
	}
	
	private JButton getRedoTool() {
		if(redoTool == null) {
			redoTool = new JButton();
			redoTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			redoTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/arrow_redo.png")));
			redoTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			redoTool.setToolTipText("Redo (Ctrl+Y)");
			redoTool.setFocusable(false);
			redoTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//redoToolActionPerformed(evt);
				}
			});
		}
		return redoTool;
	}
	
	private JButton getUndoTool() {
		if(undoTool == null) {
			undoTool = new JButton();
			undoTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			undoTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/arrow_undo.png")));
			undoTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			undoTool.setToolTipText("Undo (Ctrl+Z)");
			undoTool.setFocusable(false);
			undoTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//undoToolActionPerformed(evt);
				}
			});
		}
		return undoTool;
	}
	
	private JToolBar getCompilar() {
		if(Compilar == null) {
			Compilar = new JToolBar();
			Compilar.setRollover(true);
			Compilar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
			Compilar.setToolTipText("Run");
			Compilar.setName("Compilar");
			Compilar.add(getCompilarTool());
			Compilar.add(getRelatorioErrosTool());
			Compilar.add(getDeadlocksTool());
		}
		return Compilar;
	}
	
	private JButton getCompilarTool() {
		if(compilarTool == null) {
			compilarTool = new JButton();
			compilarTool.setEnabled(true);
			compilarTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			compilarTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/accept.png")));
			compilarTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			compilarTool.setToolTipText("Compile (F6)");
			compilarTool.setFocusable(false);
			compilarTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					getCompilejMenuItem().doClick();
				}
			});
		}
		return compilarTool;
	}
	
	private JButton getRelatorioErrosTool() {
		if(relatorioErrosTool == null) {
			relatorioErrosTool = new JButton();
			relatorioErrosTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			relatorioErrosTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/error_report.png")));
			relatorioErrosTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			relatorioErrosTool.setToolTipText("Error Report (F7)");
			relatorioErrosTool.setFocusable(false);
			relatorioErrosTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//relatorioErrosToolActionPerformed(evt);
				}
			});
		}
		return relatorioErrosTool;
	}
	
	private JButton getDeadlocksTool() {
		if(deadlocksTool == null) {
			deadlocksTool = new JButton();
			deadlocksTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			deadlocksTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/bomb.png")));
			deadlocksTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			deadlocksTool.setToolTipText("Find DeadLocks (F8)");
			deadlocksTool.setFocusable(false);
		}
		return deadlocksTool;
	}

	private JButton getFontesEditorTool() {
		if(fontesEditorTool == null) {
			fontesEditorTool = new JButton();
			fontesEditorTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			fontesEditorTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/style.png")));
			fontesEditorTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			fontesEditorTool.setToolTipText("Configure Font Size and Type (F4)");
			fontesEditorTool.setFocusable(false);
			fontesEditorTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//fontesEditorToolActionPerformed(evt);
				}
			});
		}
		return fontesEditorTool;
	}
	
	private JButton getCoresEditorTool() {
		if(coresEditorTool == null) {
			coresEditorTool = new JButton();
			coresEditorTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			coresEditorTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/color_wheel.png")));
			coresEditorTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			coresEditorTool.setToolTipText("Configure LOTOS Colors (F5)");
			coresEditorTool.setFocusable(false);
			coresEditorTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//coresEditorToolActionPerformed(evt);
				}
			});
		}
		return coresEditorTool;
	}
	
	private JToolBar getTemplates() {
		if(Templates == null) {
			Templates = new JToolBar();
			Templates.setRollover(true);
			Templates.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));Templates.setToolTipText("Templates");
			Templates.setName("Templates");
			Templates.add(getFontesEditorTool());
			Templates.add(getCoresEditorTool());
		}
		return Templates;
	}

	private JToolBar getDebug() {
		if(Debug == null) {
			Debug = new JToolBar();
			Debug.setRollover(true);
			Debug.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			Debug.setToolTipText("Debug");
			Debug.setName("Debug");
			Debug.add(getDebugTool());
			Debug.add(getDebugTool5());
		}
		return Debug;
	}
	
	private JButton getDebugTool() {
		if(debugTool == null) {
			debugTool = new JButton();
			debugTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			debugTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/bug.png")));
			debugTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			debugTool.setToolTipText("Debug Gates (F9)");
			debugTool.setFocusable(false);
		}
		return debugTool;
	}
	
	private JButton getDebugTool5() {
		if(debugTool5 == null) {
			debugTool5 = new JButton();
			debugTool5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			debugTool5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/bug_delete.png")));
			debugTool5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			debugTool5.setToolTipText("Debug Process (F10)");
			debugTool5.setFocusable(false);
		}
		return debugTool5;
	} 
	
	private JToolBar getLooks() {
		if(Looks == null) {
			Looks = new JToolBar();
			Looks.setRollover(true);
			Looks.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
			Looks.setToolTipText("Appearence");
			Looks.setName("Looks");
			Looks.add(getLooksTool());
		}
		return Looks;
	}
	
	private JButton getLooksTool() {
		if(looksTool == null) {
			looksTool = new JButton();
			looksTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			looksTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/camera.png")));
			looksTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			looksTool.setToolTipText("Change Java Look And Feel");
			looksTool.setFocusable(false);
			looksTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//looksToolActionPerformed(evt);
				}
			});
		}
		return looksTool;
	}

	private JToolBar getAjuda() {
		if(Ajuda == null) {
			Ajuda = new JToolBar();
			Ajuda.setRollover(true);
			Ajuda.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
			Ajuda.setToolTipText("Help");
			Ajuda.setName("Ajuda");
			Ajuda.add(getAjudaJlotos());
			Ajuda.add(getAjudaManual());
			Ajuda.add(getAjudaLOTOS());
			Ajuda.add(getContatoTool());
		}
		return Ajuda;
	}
	
	private JButton getAjudaJlotos() {
		if(ajudaJlotos == null) {
			ajudaJlotos = new JButton();
			ajudaJlotos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			ajudaJlotos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/help.png")));
			ajudaJlotos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			ajudaJlotos.setToolTipText("About JLotos (F1)");
			ajudaJlotos.setFocusable(false);
			ajudaJlotos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//ajudaJlotosActionPerformed(evt);
				}
			});
		}
		return ajudaJlotos;
	}
	
	private JButton getAjudaManual() {
		if(ajudaManual == null) {
			ajudaManual = new JButton();
			ajudaManual.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			ajudaManual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/help.png")));
			ajudaManual.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			ajudaManual.setToolTipText("JLotos Manual (F2)");
			ajudaManual.setFocusable(false);
			ajudaManual.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//ajudaManualActionPerformed(evt);
				}
			});
		}
		return ajudaManual;
	}
	
	private JButton getAjudaLOTOS() {
		if(ajudaLOTOS == null) {
			ajudaLOTOS = new JButton();
			ajudaLOTOS.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			ajudaLOTOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/mind.png")));
			ajudaLOTOS.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			ajudaLOTOS.setToolTipText("About LOTOS language (F3)");
			ajudaLOTOS.setFocusable(false);
			ajudaLOTOS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//ajudaLOTOSActionPerformed(evt);
				}
			});
		}
		return ajudaLOTOS;
	}
	
	private JButton getContatoTool() {
		if(contatoTool == null) {
			contatoTool = new JButton();
			contatoTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			contatoTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/ask.png")));
			contatoTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			contatoTool.setToolTipText("Contact Us");
			contatoTool.setFocusable(false);
			contatoTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//contatoToolActionPerformed(evt);
				}
			});
		}
		return contatoTool;
	}
	
	private JToolBar getConfiguration() {
		if(configuration == null) {
			configuration = new JToolBar();
			configuration.setRollover(true);
			configuration.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			configuration.setToolTipText("Configuration");
			configuration.setName("configuration");
			configuration.add(getConfiguracoesTool());
			configuration.add(getAlarmesTool());
			configuration.add(getAtalhosTool());
		}
		return configuration;
	}
	
	private JButton getConfiguracoesTool() {
		if(configuracoesTool == null) {
			configuracoesTool = new JButton();
			configuracoesTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			configuracoesTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/cog.png")));
			configuracoesTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			configuracoesTool.setToolTipText("Options (F12)");
			configuracoesTool.setFocusable(false);
			configuracoesTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//configuracoesToolActionPerformed(evt);
				}
			});
		}
		return configuracoesTool;
	}
	
	private JButton getAlarmesTool() {
		if(alarmesTool == null) {
			alarmesTool = new JButton();
			alarmesTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			alarmesTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/bell.png")));
			alarmesTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			alarmesTool.setToolTipText("Sounds (Alt+S)");
			alarmesTool.setFocusable(false);
			alarmesTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//alarmesToolActionPerformed(evt);
				}
			});
		}
		return alarmesTool;
	}
	
	private JButton getAtalhosTool() {
		if(atalhosTool == null) {
			atalhosTool = new JButton();
			atalhosTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			atalhosTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/controller.png")));
			atalhosTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			atalhosTool.setToolTipText("Shortcuts (Ctrl+Alt+S)");
			atalhosTool.setFocusable(false);
			atalhosTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//atalhosToolActionPerformed(evt);
				}
			});
		}
		return atalhosTool;
	}
	
	public JMenu getTemplatesjMenu() {
		if(templatesjMenu == null) {
			templatesjMenu = new JMenu();
			templatesjMenu.setText("Templates");
			templatesjMenu.add(getExampleLotosjMenuItem());
		}
		return templatesjMenu;
	}
	
	public JMenu getEditorjMenu() {
		if(editorjMenu == null) {
			editorjMenu = new JMenu();
			editorjMenu.setText("Editor");
			editorjMenu.add(getFontsjMenuItem());
			editorjMenu.add(getEditorColorsjMenuItem());
		}
		return editorjMenu;
	}
	
	public JMenu getDebugjMenu() {
		if(debugjMenu == null) {
			debugjMenu = new JMenu();
			debugjMenu.setText("Debug");
			debugjMenu.add(getDebugjMenuItem());
		}
		return debugjMenu;
	}
	
	public JMenu getGraphicsjMenu() {
		if(graphicsjMenu == null) {
			graphicsjMenu = new JMenu();
			graphicsjMenu.setText("Graphics");
			graphicsjMenu.add(getMetricsjMenuItem());
		}
		return graphicsjMenu;
	}

	public JMenu getConfigurationjMenu() {
		if(configurationjMenu == null) {
			configurationjMenu = new JMenu();
			configurationjMenu.setText("Configuration");
			configurationjMenu.add(getOptionsjMenuItem());
			configurationjMenu.add(getShortcutsjMenuItem());
		}
		return configurationjMenu;
	}
	
	public JMenu getHelpjMenu() {
		if(helpjMenu == null) {
			helpjMenu = new JMenu();
			helpjMenu.setText("Help");
			helpjMenu.add(getAboutjMenuItem());
			helpjMenu.add(getManualjMenuItem());
			helpjMenu.add(getLanguagejMenuItem());
			helpjMenu.add(getContactjMenuItem());
		}
		return helpjMenu;
	}
	
	public JMenuItem getExampleLotosjMenuItem() {
		if(exampleLotosjMenuItem == null) {
			exampleLotosjMenuItem = new JMenuItem();
			exampleLotosjMenuItem.setText("Use a Full lotos example");
			exampleLotosjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/exampleLotos.png")));
		}
		return exampleLotosjMenuItem;
	}
	
	private JToggleButton getExampleLotosjToggleButton() {
		if(exampleLotosjToggleButton == null) {
			exampleLotosjToggleButton = new JToggleButton();
			exampleLotosjToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			exampleLotosjToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/exampleLotos.png")));
			exampleLotosjToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			exampleLotosjToggleButton.setFocusable(false);
			exampleLotosjToggleButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//salvarAutoToolActionPerformed(evt);
				}
			});
		}
		return exampleLotosjToggleButton;
	}
	
	private JToolBar getTemplatesJToolBar() {
		if(templatesjToolBar == null) {
			templatesjToolBar = new JToolBar();
			templatesjToolBar.setRollover(true);
			templatesjToolBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
			templatesjToolBar.setToolTipText("Graphics");
			templatesjToolBar.setName("jToolBar1");
			templatesjToolBar.add(getExampleLotosjToggleButton());
		}
		return templatesjToolBar;
	}
	
	public JMenuItem getCopyjMenuItem() {
		if(copyjMenuItem == null) {
			copyjMenuItem = new JMenuItem();
			copyjMenuItem.setText("Copy");
			copyjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/copy.png")));
			
		}
		return copyjMenuItem;
	}
	
	public JMenuItem getCutjMenuItem() {
		if(cutjMenuItem == null) {
			cutjMenuItem = new JMenuItem();
			cutjMenuItem.setText("Cut");
			cutjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/cut.png")));
			
		}
		return cutjMenuItem;
	}
	
	public JMenuItem getPastejMenuItem() {
		if(pastejMenuItem == null) {
			pastejMenuItem = new JMenuItem();
			pastejMenuItem.setText("Paste");
			pastejMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/briefcase.png")));
			
		}
		return pastejMenuItem;
	}
	
	public JMenuItem getFindReplacejMenuItem() {
		if(findReplacejMenuItem == null) {
			findReplacejMenuItem = new JMenuItem();
			findReplacejMenuItem.setText("Find/Replace");
			findReplacejMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/find.png")));
		}
		return findReplacejMenuItem;
	}
	
	public JMenuItem getRefreshjMenuItem() {
		if(refreshjMenuItem == null) {
			refreshjMenuItem = new JMenuItem();
			refreshjMenuItem.setText("Refresh");
			refreshjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/arrow_refresh.png")));
		}
		return refreshjMenuItem;
	}
	
	public JMenuItem getFontsjMenuItem() {
		if(fontsjMenuItem == null) {
			fontsjMenuItem = new JMenuItem();
			fontsjMenuItem.setText("Fonts");
			fontsjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/style.png")));
		}
		return fontsjMenuItem;
	}
	
	public JMenuItem getEditorColorsjMenuItem() {
		if(editorColorsjMenuItem == null) {
			editorColorsjMenuItem = new JMenuItem();
			editorColorsjMenuItem.setText("Editor colors");
			editorColorsjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/color_wheel.png")));
		}
		return editorColorsjMenuItem;
	}
	
	public JMenuItem getCompilejMenuItem() {
		if(compilejMenuItem == null) {
			compilejMenuItem = new JMenuItem();
			compilejMenuItem.setText("Compile");
			compilejMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/accept.png")));
			KeyStroke ctrlF9 = KeyStroke.getKeyStroke("control F9");
			compilejMenuItem.setAccelerator(ctrlF9);
			compilejMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()");
					if(SystemHandler.sectionKits != null && SystemHandler.sectionKits.size() > 0)
					SystemHandler.performAnalyse();
					else{
						JOptionPane.showMessageDialog(getJContentPane(), "You need to open a specification, or a library file.", "There are no sections", JOptionPane.ERROR_MESSAGE);
					}
				
				}
			});
		}
		return compilejMenuItem;
	}
	
	public JMenuItem getErrorReportjMenuItem() {
		if(errorReportjMenuItem == null) {
			errorReportjMenuItem = new JMenuItem();
			errorReportjMenuItem.setText("Error report");
			errorReportjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/error_report.png")));
		}
		return errorReportjMenuItem;
	}
	
	public JMenuItem getFindDeadLockjMenuItem() {
		if(findDeadLockjMenuItem == null) {
			findDeadLockjMenuItem = new JMenuItem();
			findDeadLockjMenuItem.setText("Find dead locks");
			findDeadLockjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/bomb.png")));
		}
		return findDeadLockjMenuItem;
	}
	
	public JMenuItem getAboutjMenuItem() {
		if(aboutjMenuItem == null) {
			aboutjMenuItem = new JMenuItem();
			aboutjMenuItem.setText("About JLOTOS");
			aboutjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/help.png")));
		}
		return aboutjMenuItem;
	}
	
	private JMenuItem getManualjMenuItem() {
		if(manualjMenuItem == null) {
			manualjMenuItem = new JMenuItem();
			manualjMenuItem.setText("JLOTOS Manual");
			manualjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/help.png")));
			
		}
		return manualjMenuItem;
	}
	
	public JMenuItem getLanguagejMenuItem() {
		if(languagejMenuItem == null) {
			languagejMenuItem = new JMenuItem();
			languagejMenuItem.setText("LOTOS language");
			languagejMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/mind.png")));
			
		}
		return languagejMenuItem;
	}
	
	public JMenuItem getContactjMenuItem() {
		if(contactjMenuItem == null) {
			contactjMenuItem = new JMenuItem();
			contactjMenuItem.setText("Contact us");
			contactjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/ask.png")));
			
			
		}
		return contactjMenuItem;
	}
	
	public JMenuItem getDebugjMenuItem() {
		if(debugjMenuItem == null) {
			debugjMenuItem = new JMenuItem();
			debugjMenuItem.setText("Debug");
			debugjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/bug.png")));
		}
		return debugjMenuItem;
	}
	
	public JMenuItem getMetricsjMenuItem() {
		if(metricsjMenuItem == null) {
			metricsjMenuItem = new JMenuItem();
			metricsjMenuItem.setText("Metrics");
			metricsjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/chart_bar.png")));
		}
		return metricsjMenuItem;
	}
	
	public JMenuItem getOptionsjMenuItem() {
		if(optionsjMenuItem == null) {
			optionsjMenuItem = new JMenuItem();
			optionsjMenuItem.setText("Options");
			optionsjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/cog.png")));
		}
		return optionsjMenuItem;
	}
	
	public JMenuItem getShortcutsjMenuItem() {
		if(shortcutsjMenuItem == null) {
			shortcutsjMenuItem = new JMenuItem();
			shortcutsjMenuItem.setText("Shortcuts");
			shortcutsjMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/controller.png")));
		}
		return shortcutsjMenuItem;
	}


	public JToolBar getLeftJToolBar() {
		if(leftJToolBar== null){
			leftJToolBar = new JToolBar();
			leftJToolBar.setFloatable(false);			
		}
		return leftJToolBar;
	}

	public void setLeftJToolBar(JToolBar leftJToolBar) {
		this.leftJToolBar = leftJToolBar;
	}

	public JToolBar getRightJToolBar() {
		if(rightJToolBar==null){
			rightJToolBar = new JToolBar();
			rightJToolBar.setFloatable(false);
			rightJToolBar.getPreferredSize().width = 30;
			System.out.println("");
		}
		return rightJToolBar;
	}

	public void setRightJToolBar(JToolBar rightJToolBar) {
		this.rightJToolBar = rightJToolBar;
	}

	public JPanel getViewSideJPanel() {
		if(viewSideJPanel ==  null){
			viewSideJPanel = new JPanel();
			BorderLayout jPanel1Layout = new BorderLayout();
			viewSideJPanel.setLayout(jPanel1Layout);
			viewSideJPanel.setBorder(BorderFactory.createEmptyBorder());
			viewSideJPanel.add(getLeftJToolBar(),BorderLayout.CENTER);
			
			getLeftJToolBar().setPreferredSize(new Dimension(30, viewSideJPanel.getHeight()));
			viewSideJPanel.add(getViewsjPanel(), BorderLayout.EAST);
		}
		
		return viewSideJPanel;
	}

	public void setViewSideJPanel(JPanel viewSideJPanel) {
		this.viewSideJPanel = viewSideJPanel;
	}

	public void setToolSideJPanel(JPanel toolSideJPanel) {
		this.toolSideJPanel = toolSideJPanel;
	}

	public void refreshTabsLocation(int closedSection, int updateWidth) {
		// TODO Auto-generated method stub
		for (int i = closedSection; i < SystemHandler.numberOfSections; i++) {
			
			SystemHandler.sectionKits.get(i).id = i;
			Point location = SystemHandler.sectionKits.get(i).getTextFileTabjPanel().getLocation();
			SystemHandler.sectionKits.get(i).getTextFileTabjPanel().setLocation(location.x-updateWidth, location.y);
			
		}
	}
	
	public void refreshResultTabsLocation(int closedResult, int updateResultWidth) {
		// TODO Auto-generated method stub
		for (int i = closedResult; i < SystemHandler.numberOfResultWindows; i++) {
			
			SystemHandler.resultKits.get(i).id = i;
			Point location = SystemHandler.resultKits.get(i).getResultWindowTabjPanel().getLocation();
			SystemHandler.resultKits.get(i).getResultWindowTabjPanel().setLocation(location.x-updateResultWidth, location.y);
			
		}
	}

}