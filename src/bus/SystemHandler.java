package bus;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension; 
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import resources.AceptedFile;
import resources.Project;
import books.LotosBook;
import books.docListener;
import br.com.jlotos.simulator.JLotosSimulator;
import bus.compiling.LexicalAnalyzer;
import bus.compiling.SemanticAnalyser;
import bus.compiling.SyntacticAnalyzer;
import bus.compiling.Token;
import bus.compiling.exceptions.LexicalErrorException;
import bus.compiling.exceptions.PossibleDeadLock;
import bus.compiling.exceptions.SemanticErrorException;
import bus.compiling.exceptions.SemanticProblem;
import bus.compiling.exceptions.SyntaticErrorException;
import bus.compiling.semanticStructure.Specification;
import manipulaters.DocDoor;
import manipulaters.DocumentCreator;
import manipulaters.TextActualizer;
import manipulaters.TreeManipulatorListener;
import view.JLotosMainView;


public class SystemHandler{
		
	
	public static final String LEXICAL = "Lexical Analyzer";
	public static final String SYNTATIC = "Syntatic Analyzer";
	public static final String SEMANTIC = "Semantic Analyzer";
	public static final String SIMULATION = "Simulation";	
	public static DocumentCreator documentCreator = new DocumentCreator();
	public static String userHome = new File(SystemHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent().replace("%20"," ");
	public static String codeResourcesHome = new File(SystemHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getPath().replace("%20"," ")+"\\resources\\codeBlocks";
	public static ArrayList<SectionKit> sectionKits = new ArrayList<SectionKit>();
	public static ArrayList<ResultKit> resultKits = new ArrayList<ResultKit>();
	public static int numberOfSections = 0;
	public static int numberOfResultWindows = 0;
	public static int openedSectionId = -1;
	public static int openedResultWindow = -1;
	public static String openedSectionName = "unknown";
	public static JLotosMainView mainView = null;
	public static TextActualizer textActualizer = new TextActualizer();
	public static boolean hasProjectOpened = false;
	public static Project openedProject = null;
	public static Project orfansProject = null;
	public static int numberOfActionTabs = 0;
	public static int openedActionTabId = -1;
	//The list of tabs on east that had to be opened in current opened section
	public static ArrayList <String> ActionTabList = new ArrayList<String>();
	//The list of data on tabs on west that had to be opened in current opened section
	public static ArrayList <JPanel> ActionPanelList = new ArrayList<JPanel>();
	
	public static LotosBook lotosBook = null;
	
	
	public static int caretLinePosition = 0;
	public static int verticalScrollBarValue = 0;
	public static boolean doCompensation = false;
	public static Rectangle viewRectangle = null;
	
	public static JTree projectTree = null;
	
	public static JPanel explorerJPanel = null;
	
	public static LexicalAnalyzer lexAnalyzer = null;
	public static SyntacticAnalyzer syntAnalyzer = null;
	public static SemanticAnalyser semanticAnalyzer = null;
	
	public static boolean isInAutoCompleteProcess = false;
	public static int caretPositionAfterAutoComplete = 0;

	public static int caretPositionBeforeAutoComplete = 0;
	
	public static int actualXBound = 0;
	public static int actualResultXBound = 0;
	
	private static Specification specification = null;
	
	private static boolean allRight = true;

	public static int getLineByToken(Token tk){ 
		
		return mainView.getLineCount(sectionKits.get(openedSectionId).getDataText().substring(0, tk.getFim()));
		
	}
	
	public static boolean putCodeBlock(int caretPosition){
		
		String word = DocDoor.getMainTerm(caretPosition);
		
		if(word.length()==0)return false;
				
		URL fileURL = Object.class.getClass().getResource("/resources/codeBlocks/"+word);
		
		if(fileURL==null){
			return false;
		}
		
		String path = fileURL.getPath();
		
		Reader reader = null;
		
		if(path.substring(0, 4).equals("file")){
			
			try {
				fileURL = new URL(SystemHandler.class.getProtectionDomain().getCodeSource().getLocation()+"");
				ZipInputStream zip = new ZipInputStream(new DataInputStream(fileURL.openStream()));
				ZipEntry entry;
				while ((entry = zip.getNextEntry()) != null){
					if(entry.getName().equals("resources/codeBlocks/"+word)){
						reader = new InputStreamReader(zip);
						break;
					}	
				}
				if(entry==null)return false;
			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				System.out.println("erro");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
									
		}else{
						
			File codeFile = new File(path);
		
			if(!codeFile.exists()){
				return false;
			}
			
			try {
				reader = new FileReader(codeFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String block = "";
		String str =  "";
		try {
            BufferedReader in = new BufferedReader(reader);
            while ((str = in.readLine()) != null) {
            	if(str.equals("#pos#")){
            		SystemHandler.isInAutoCompleteProcess=false;
            	}else{
            		str = str.replace("{$filename}", SystemHandler.sectionKits.
            				get(SystemHandler.openedSectionId).
            				name.replace("."+SystemHandler.sectionKits.
            						get(SystemHandler.openedSectionId).type, "").toUpperCase());
            		block+=str+"\n";
            	}
            }
            in.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        block = block.substring(0, block.length()-1);
        
        DocumentCreator.insertCode(block,caretPosition);
              
		return true;
	}
		
		
	public static int getSectionbyPath(String filePath){
		
		for(int cont = 0; cont < numberOfSections; cont ++){
		      if(sectionKits.get(cont).path.equals(filePath)){
		    	  return cont;
		      }
		}
		
		return -1;
		
	}
	
	public static int getResultWindowbyName(String resultWindowName){
		
		for(int cont = 0; cont < numberOfResultWindows; cont ++){
		      if(resultKits.get(cont).name.equals(resultWindowName)){
		    	  return cont;
		      }
		}
		
		return -1;
		
	}
	
	public static boolean addSection(SectionKit sk){
		
		mainView.getTextFilejTextPane().setVisible(true);
		mainView.getTextFilejTextPane().setOpaque(true);
		
		if(numberOfSections>0){
			for(int cont = 0; cont < numberOfSections; cont ++){
			   if(sk.name.equals("")){
				   return false;
			   }
			   //if(sectionKits.get(cont).path.equalsIgnoreCase(sk.path)){
			   //	   return false;
			   //}
			}
		}
		
		sk.id = numberOfSections;
		numberOfSections++;
		sectionKits.add(sk);

		//add the new tab of the newest added file
		mainView.addFileTab(sk.getTextFileTabjPanel());
		
		if(hasProjectOpened)setReferredSectionIds(sk.path, sk.id);
				
		mainView.validate();
		
		return true;
	}
	
	public static boolean addResultWindow(ResultKit rk){
		
		mainView.getWindowsjPanel().setVisible(true);
		//mainView.getWindowsjPanel().setPreferredSize(new Dimension(0, 200));
		mainView.getUpjSplitPane().setDividerLocation(0.8);
		mainView.getResultsjTextPane().setVisible(true);
		mainView.getResultsjTextPane().setOpaque(true);
		
		if(numberOfResultWindows>0){
			for(int cont = 0; cont < numberOfResultWindows; cont ++){
			   if(rk.name.equals("")){
				   return false;
			   }
			   //if(sectionKits.get(cont).path.equalsIgnoreCase(sk.path)){
			   //	   return false;
			   //}
			}
		}
		
		rk.id = numberOfResultWindows;
		numberOfResultWindows++;
		resultKits.add(rk);

		//add the new tab of the newest added file
		mainView.addResultWindowTab(rk.getResultWindowTabjPanel());
		if(hasProjectOpened)setReferredSectionIds(rk.logPath, rk.id);
				
		mainView.validate();
		
		return true;
	}
	
	public static void closeSection(int idSectionToClose){

		
		//visual
		int updateWidth = sectionKits.get(idSectionToClose).getTextFileTabjPanel().getWidth()-15;
		actualXBound -= updateWidth;

		
		
		//remove dos panels
		mainView.getTextFileInfojPanel().remove(SystemHandler.sectionKits.get(idSectionToClose).getTextFileTabjPanel());

		//remove das se��es
		sectionKits.remove(idSectionToClose);
		
		mainView.getTextFileInfojPanel().repaint();
		
		//diminue o n�mero de se��es abertas
		numberOfSections--;
		

		if(numberOfSections==0)
			mainView.getTextFilejPanel().setVisible(false);
		
		//recoloca as tabs se a tela n�o for a �ltima
		if(idSectionToClose!=numberOfSections)
		mainView.refreshTabsLocation(idSectionToClose,updateWidth);

		mainView.getMainjPanel().repaint();
				
		if(idSectionToClose==openedSectionId){
			if(numberOfSections==0 || idSectionToClose != 0)
			openedSectionId--;
		
		
		if(sectionKits.size()!=0)
		openSection(openedSectionId);
		
		}
		
			
		
	}
	
public static void closeResult(int idResultToClose){

		
		//visual
		int updateWidth = resultKits.get(idResultToClose).getResultWindowTabjPanel().getWidth()-15;
		actualResultXBound -= updateWidth;

		
		
		//remove dos panels
		mainView.getWindowsInfoPanel().remove(SystemHandler.resultKits.get(idResultToClose).getResultWindowTabjPanel());

		//remove das se��es
		resultKits.remove(idResultToClose);
		
		mainView.getWindowsInfoPanel().repaint();
		
		//diminue o n�mero de se��es abertas
		numberOfResultWindows--;
		

		if(numberOfResultWindows==0)
			mainView.getWindowsjPanel().setVisible(false);
		
		//recoloca as tabs se a tela n�o for a �ltima
		if(idResultToClose!=numberOfResultWindows)
		mainView.refreshResultTabsLocation(idResultToClose,updateWidth);

		mainView.getWindowsjPanel().repaint();
				
		if(idResultToClose==openedResultWindow){
			if(numberOfResultWindows==0 || idResultToClose != 0)
			openedResultWindow--;
		
		
		if(resultKits.size()!=0)
		openResultWindow(openedResultWindow);
		
		}
		
			
		
	}
	
	public static void openSection(int sectionId){

		//if(openedResultWindow!=-1){
		//	SystemHandler.sectionKits.get(openedResultWindow).caretPosition=SystemHandler.mainView.getTextFilejTextPane().getCaretPosition();
		//}
		
		if(openedSectionId!=-1){
			SystemHandler.sectionKits.get(openedSectionId).caretPosition=SystemHandler.mainView.getTextFilejTextPane().getCaretPosition();
		}
		
		mainView.getTextFilejTextPane().setStyledDocument(SystemHandler.sectionKits.get(sectionId).document);
		
		mainView.getLineInfojTextPane().setText(".1");
		openedSectionId=sectionId;
		openedSectionName=sectionKits.get(sectionId).name;
		
		//Refresh the Z order to lets on top the current section
		mainView.refreshDataTabs();
		
		int caretPosition = SystemHandler.sectionKits.get(SystemHandler.openedSectionId).caretPosition;
		
		if(caretPosition!=-1){
			SystemHandler.mainView.getTextFilejTextPane().grabFocus();
			SystemHandler.mainView.setCaretPosition(caretPosition);
		}

		mainView.latestLineCount = mainView.getLineCount(mainView.getLineInfojTextPane().getText());
		
		mainView.renoveLineNumbers();
				
		mainView.validate();
		
		
		//System.out.println(mainView.getJtextFileVerticalScrollBar().getValue());
		
		//mainView.getJlineInfoScrollPane().getVerticalScrollBar().setValue(mainView.getJtextFileVerticalScrollBar().getValue());
		
		//System.out.println("--"+mainView.getJlineInfoScrollPane().getVerticalScrollBar().getValue());
		
	
	}
	
	public static JComponent component = new JLabel();
	
	
	public static void openResultWindow(int resultId){
			
			
		if(resultId < SystemHandler.resultKits.size()){
		
		
			
			mainView.getResultsjTextPane().setStyledDocument(SystemHandler.resultKits.get(resultId).resultDocument);
			component = SystemHandler.resultKits.get(resultId).getResultComponent();
			mainView.getResultsjTextPane().insertComponent(component);
			
			mainView.getResultColumn2jTextPane().setStyledDocument(SystemHandler.resultKits.get(resultId).resultDocumentC2);
			
			openedResultWindow=resultId;
			openedSectionName=resultKits.get(resultId).name;
			
			//Refresh the Z order to lets on top the current section
			mainView.refreshResultTabs();
			
			mainView.validate();
			
			
			//System.out.println(mainView.getJtextFileVerticalScrollBar().getValue());
			
			//mainView.getJlineInfoScrollPane().getVerticalScrollBar().setValue(mainView.getJtextFileVerticalScrollBar().getValue());
			
			//System.out.println("--"+mainView.getJlineInfoScrollPane().getVerticalScrollBar().getValue());
			
		}
		
	}
	
	public static int idActionTab(String actionTab){
		
		for(int cont = 0; cont < numberOfActionTabs; cont ++){
		      if(ActionTabList.get(cont).equalsIgnoreCase(actionTab)){
		    	  return cont;
		      }
		}
		return -1;
		
	}
	
	
	public static void addActionTab(String actionTab){
		if(idActionTab(actionTab)==-1){
			numberOfActionTabs++;
			ActionTabList.add(actionTab);
			if(actionTab.equals(SectionKit.EXPLORER_TAB)){
				explorerJPanel =  new JPanel();
				if(hasProjectOpened==true){
					explorerJPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
					explorerJPanel.setLayout(new FlowLayout());
					projectTree = new JTree(openedProject);
					explorerJPanel.add(projectTree);
				}
				mainView.getActionjPanel().add(explorerJPanel);
			}
			//ActionPanelList.add(/* The opened file explorer informations */"");
			mainView.validate();
		}
	}
	
	public static void setReferredSectionIds(String filePath, int sectionId){
		for(int cont = 0; cont<SystemHandler.openedProject.lotosFiles.size();cont++){
			if(SystemHandler.openedProject.lotosFiles.get(cont).filePath.equals(filePath)){
				SystemHandler.openedProject.lotosFiles.get(cont).opened=true;
				SystemHandler.openedProject.lotosFiles.get(cont).referredSection=sectionId;
				SystemHandler.sectionKits.get(sectionId).onProject=true;
			}
		}
	}
	
	public static void openFileonProject (int selRow){
		
		SystemHandler.openFile(new File(((AceptedFile)SystemHandler.openedProject.getChildAt(selRow-1)).filePath));
	
	}
	
	public static void actualizeExplorer(){
		explorerJPanel.removeAll();
		openedProject.chargeFiles();
		projectTree = new JTree(openedProject);
		projectTree.addMouseListener(new TreeManipulatorListener(projectTree));
		explorerJPanel.add(projectTree);
		projectTree.setPreferredSize(explorerJPanel.getPreferredSize());
		mainView.validate();
		
	}
	
	public static boolean saveFile(File textFile, String dataText, boolean newer){
	
		PrintWriter writer = null;
		try {
		    writer = new PrintWriter(textFile);
			writer.write(dataText);
		    writer.close();
		    if(!newer)SystemHandler.sectionKits.get(SystemHandler.openedSectionId).saved = true;
		} catch (FileNotFoundException fnfe) {
			// TODO Auto-generated catch block
			fnfe.printStackTrace();
		}
		
		return true;
	}
	
	public static boolean openFile(File file){
		
		
		
		if(file!=null){
		int sectionId = getSectionbyPath(file.getPath());
		if(sectionId==-1){
		boolean isFileValid = false;
		String fileType = "";
		if(file.canRead()){
		while(isFileValid==false){
			if(file.getName().contains("."+SectionKit.LOTOS)){
				System.out.println("lotos");
				isFileValid=true;
				fileType=SectionKit.LOTOS;
			}else if(file.getName().contains("."+SectionKit.LIBRARY)){
				System.out.println("library");
				isFileValid=true;
				fileType=SectionKit.LIBRARY;
			}else{
				JOptionPane.showMessageDialog(SystemHandler.mainView, "LOTOS file!!");
				return false;
			}
		}

		mainView.getTextFilejPanel().setVisible(true);
		Dimension d = mainView.getMainjPanel().getPreferredSize();
		mainView.getMainjPanel().getGraphics().clearRect(0, 0, d.width, d.height);
		
		SectionKit newSectionFromFile = new SectionKit(file,fileType);
		SystemHandler.addSection(newSectionFromFile);
		SystemHandler.openSection(SystemHandler.numberOfSections-1);
		String str =  "";
		String text = "";
		try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            while ((str = in.readLine()) != null) {
                text += str + "\n"; //String.format("%-6d %s %s", linha++, str, "\n");
            }
            in.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        SystemHandler.sectionKits.get(SystemHandler.openedSectionId).setDataText(text);
        mainView.getTextFilejTextPane().setText(text);
        documentCreator.styleDocument(text);
		SystemHandler.mainView.getTextFilejTextPane().grabFocus();
		SystemHandler.mainView.setCaretPosition(0);
        SystemHandler.mainView.renoveLineNumbers();
        SystemHandler.sectionKits.get(SystemHandler.openedSectionId).document.addDocumentListener(new docListener());     
        TextUI ui = mainView.getTextFilejTextPane().getUI();
		Dimension pref = ui.getPreferredSize(mainView.getTextFilejTextPane());
		mainView.getTextFilejPanel().setPreferredSize(new Dimension(pref.width+100,pref.height));
		mainView.getRowInfojPanel().setPreferredSize(new Dimension(mainView.getRowInfojPanel().getWidth(),pref.height));
		
		//mainView.getRowInfojPanel().setPreferredSize(new Dimension(50,1000));
		
		return true;
		}
		}else{
			openSection(sectionId);
		}
		}
		return false;
		
	}
	
	public static void performAnalyse(){

		SystemHandler.sectionKits.get(SystemHandler.openedSectionId).tokensGroups = null;
		specification = null;
		
		allRight = false;
		
		if(numberOfSections!=0){
		
		int resultWindowId = getResultWindowbyName(SystemHandler.LEXICAL);
		
		ResultKit newResultWindow = null;
		
		if(resultWindowId==-1){
			newResultWindow = new ResultKit(SystemHandler.LEXICAL,"lexic");
			StyledDocument docs[] = performLexicalAnalyse();
			newResultWindow.resultDocument = docs[0];
			newResultWindow.resultDocumentC2 = docs[1];
			newResultWindow.setResultComponent(new JLabel());
			SystemHandler.addResultWindow(newResultWindow);
		}else{
			newResultWindow = resultKits.get(resultWindowId);
			StyledDocument docs[] = performLexicalAnalyse();
			newResultWindow.resultDocument = docs[0];
			newResultWindow.resultDocumentC2 = docs[1];
			newResultWindow.setResultComponent(new JLabel());
		}
		
		SystemHandler.openResultWindow(0);
		
		resultWindowId = getResultWindowbyName(SystemHandler.SYNTATIC);
		
		if(SystemHandler.sectionKits.get(SystemHandler.openedSectionId).tokensGroups!=null){
			
		if(resultWindowId==-1){

			newResultWindow = new ResultKit(SystemHandler.SYNTATIC,"syntatic");
			StyledDocument docs[] = performSyntacticAnalyse();
			newResultWindow.resultDocument = docs[0];
			newResultWindow.resultDocumentC2 = docs[1];
			newResultWindow.setResultComponent(new JLabel());
			SystemHandler.addResultWindow(newResultWindow);
			
		}else{

			newResultWindow = resultKits.get(resultWindowId);
			StyledDocument docs[] = performSyntacticAnalyse();
			newResultWindow.resultDocument = docs[0];
			newResultWindow.resultDocumentC2 = docs[1];
			newResultWindow.setResultComponent(new JLabel());
			
		}		
			SystemHandler.openResultWindow(1);
			
		}else{
			if(resultWindowId!=-1){
				int cont = resultWindowId;
				while(cont < SystemHandler.resultKits.size()){
					SystemHandler.closeResult(SystemHandler.resultKits.size()-1);
				}
			}
		}
		

		resultWindowId = getResultWindowbyName(SystemHandler.SEMANTIC);
		
		if(specification != null){
			if(resultWindowId==-1){

				newResultWindow = new ResultKit(SystemHandler.SEMANTIC,"semantic");
				StyledDocument docs[] = performSemanticAnalyse();
				newResultWindow.resultDocument = docs[0];
				newResultWindow.resultDocumentC2 = docs[1];
				newResultWindow.setResultComponent(new JLabel());
				SystemHandler.addResultWindow(newResultWindow);
				
			}else{

				newResultWindow = resultKits.get(resultWindowId);
				StyledDocument docs[] = performSemanticAnalyse();
				newResultWindow.resultDocument = docs[0];
				newResultWindow.resultDocumentC2 = docs[1];

				newResultWindow.setResultComponent(new JLabel());
				
			}		
			
			SystemHandler.openResultWindow(2);
			
		}else{
			if(resultWindowId!=-1){
				int cont = resultWindowId;
				while(cont < SystemHandler.resultKits.size()){
					SystemHandler.closeResult(SystemHandler.resultKits.size()-1);
				}
			}
		}
		


		/**
		 * Descomentar quando melhorar a simulação
		 */
//		resultWindowId = getResultWindowbyName(SystemHandler.SIMULATION);
//		if(allRight){
//						
//				JLotosSimulator jLotosSimulator = performSimulation();
//				JComponent component = new JPanel();
//				component.add(jLotosSimulator);
//				
//				newResultWindow = new ResultKit(SystemHandler.SIMULATION,"simulation");
//				newResultWindow.setResultComponent(component);
//				SystemHandler.addResultWindow(newResultWindow);
//				SystemHandler.openResultWindow(3);
//			
//		}else{
//			if(resultWindowId!=-1){
//				int cont = resultWindowId;
//				while(cont < SystemHandler.resultKits.size()){
//					SystemHandler.closeResult(SystemHandler.resultKits.size()-1);
//				}
//			}
//		}
		
		}
		
		TextUI ui = SystemHandler.mainView.getResultColumn2jTextPane().getUI();
		Dimension pref = ui.getPreferredSize(SystemHandler.mainView.getResultColumn2jTextPane());
		
		TextUI ui2 = SystemHandler.mainView.getResultsjTextPane().getUI();
		Dimension pref2 = ui2.getPreferredSize(SystemHandler.mainView.getResultsjTextPane());
		
		
		SystemHandler.mainView.getResultColumn2jPanel().setPreferredSize(new Dimension(pref.width,pref2.height));
		
		
	}
	
	private static JLotosSimulator performSimulation() {
		// TODO Auto-generated method stub
		return new JLotosSimulator(specification);
	}

	private static StyledDocument [] performLexicalAnalyse(){
		
		StyledDocument doc = new DefaultStyledDocument();
		StyledDocument doc2 = new DefaultStyledDocument();
		
		lexAnalyzer = new LexicalAnalyzer();
		
		try{
		
		SystemHandler.sectionKits.get(openedSectionId).setTokensGroups(lexAnalyzer.analyse(SystemHandler.sectionKits.get(openedSectionId).getDataText()));
		
		ArrayList<Token> tks = SystemHandler.sectionKits.get(SystemHandler.openedSectionId).getTokensGroups().get(0);
		
		for(Token tk : tks){
			
			doc.insertString(doc.getLength(), tk.getConteudo()+" \n", new SimpleAttributeSet());
			doc2.insertString(doc2.getLength(), "  "+tk.getTipo()+"\n", new SimpleAttributeSet());
			
		}
		
		}catch(LexicalErrorException e){
			
			MutableAttributeSet mat = new SimpleAttributeSet();
			
			StyleConstants.setForeground(mat,Color.red);
			
			String text = SystemHandler.sectionKits.get(SystemHandler.openedSectionId).getDataText();
			
			try {
				int i = 0;
				
				while(i<e.getWrongTokensIds().size()){
					int tkId = e.getWrongTokensIds().get(i);
					Token tk = e.getTokenGroup().get(0).get(tkId);
					doc.insertString(doc.getLength(), "At line:\t"+SystemHandler.mainView.getLineCount(text.substring(0, tk.getInicio()))+e.getErrorCause()+" --> "+tk.getConteudo()+"\n", mat);
					i++;
				}
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
						
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StyledDocument docs [] = {doc,doc2};
		
		return  docs;	
		
	}
	
	private static StyledDocument[] performSyntacticAnalyse(){
			
			StyledDocument doc = new DefaultStyledDocument();
			StyledDocument doc2 = new DefaultStyledDocument();
				
			syntAnalyzer = new SyntacticAnalyzer();
		
			try{
			
			specification = syntAnalyzer.analyse(SystemHandler.sectionKits.get(openedSectionId).getTokensGroups());
			
			
			doc.insertString(0, "SINTATIC OK", new SimpleAttributeSet());
			
			
			}catch(SyntaticErrorException e){
				
				specification = null;
				
				MutableAttributeSet mat = new SimpleAttributeSet();
				
				StyleConstants.setForeground(mat,Color.red);
				
				String text = SystemHandler.sectionKits.get(SystemHandler.openedSectionId).getDataText();
				
				boolean interruptError = false;
				
				Token tk = null;
				
				if(e.getTokenId()<SystemHandler.sectionKits.get(openedSectionId).getTokensGroups().get(0).size()){
					tk = SystemHandler.sectionKits.get(openedSectionId).getTokensGroups().get(0).get(e.getTokenId());
				}else{
					tk = SystemHandler.sectionKits.get(openedSectionId).getTokensGroups().get(0).get(e.getTokenId()-1);
					interruptError = true;
				}
				
				try {
					
					mainView.getResultColumn2jPanel().setPreferredSize(new Dimension(80,1));
					doc.insertString(doc.getLength(), "  "+e.getErrorCause()+"\n", mat);
					StyleConstants.setAlignment(mat, StyleConstants.ALIGN_RIGHT);
					if(!interruptError)
					doc2.insertString(doc2.getLength(), "  At line: "+SystemHandler.mainView.getLineCount(text.substring(0, tk.getInicio()))+"  \n", mat);
					else
					doc2.insertString(doc2.getLength(), "  After line: "+SystemHandler.mainView.getLineCount(text.substring(0, tk.getInicio()))+"  \n", mat);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
				
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			StyledDocument docs [] = {doc,doc2};
			
		return docs;
	}
	
	private static StyledDocument[] performSemanticAnalyse(){
		

		StyledDocument doc = new DefaultStyledDocument();
		StyledDocument doc2 = new DefaultStyledDocument();
		

		semanticAnalyzer = new SemanticAnalyser();
		

		SemanticProblem semanticProblem = semanticAnalyzer.analyse(specification,SystemHandler.sectionKits.get(openedSectionId).getTokensGroups().get(0));
		
		if(semanticProblem.getListOfPossibleDeadLocks().size() > 0){
			
			MutableAttributeSet mat = new SimpleAttributeSet();
			
			StyleConstants.setForeground(mat,Color.BLACK);
			
			String text = SystemHandler.sectionKits.get(SystemHandler.openedSectionId).getDataText();
			
				
				for (PossibleDeadLock possibleDeadLock : semanticProblem.getListOfPossibleDeadLocks()) {
					
							
					int tkId = possibleDeadLock.getTokenId();
					Token tk = SystemHandler.sectionKits.get(openedSectionId).getTokensGroups().get(0).get(tkId);
					
					try {
						
						mainView.getResultColumn2jPanel().setPreferredSize(new Dimension(80,1));
						doc.insertString(doc.getLength(), "  "+possibleDeadLock.getErrorCause()+"\n", mat);
						StyleConstants.setAlignment(mat, StyleConstants.ALIGN_RIGHT);
						doc2.insertString(doc2.getLength(), "  At line: "+SystemHandler.mainView.getLineCount(text.substring(0, tk.getInicio()))+"  \n", mat);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			
		} else {
			
			try {
				doc2.insertString(doc.getLength(), "NO POSSIBLE DEADLOCKS\n", new SimpleAttributeSet());
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
			
		if(semanticProblem.getListOfSemanticError().size() > 0) {
			// TODO: handle exception
						
			MutableAttributeSet mat = new SimpleAttributeSet();
			
			StyleConstants.setForeground(mat,Color.red);
			
			String text = SystemHandler.sectionKits.get(SystemHandler.openedSectionId).getDataText();
			
				
				for (SemanticErrorException semanticErrorException : semanticProblem.getListOfSemanticError()) {
					
							
					int tkId = semanticErrorException.getTokenId();
					Token tk = SystemHandler.sectionKits.get(openedSectionId).getTokensGroups().get(0).get(tkId);
					
					try {
						
						mainView.getResultColumn2jPanel().setPreferredSize(new Dimension(80,1));
						doc.insertString(doc.getLength(), "  "+semanticErrorException.getErrorCause()+"\n", mat);
						StyleConstants.setAlignment(mat, StyleConstants.ALIGN_RIGHT);
						doc2.insertString(doc2.getLength(), "  At line: "+SystemHandler.mainView.getLineCount(text.substring(0, tk.getInicio()))+"  \n", mat);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
						
		}else{
			allRight = true;
				
			try {
				doc2.insertString(doc.getLength(), "NO SEMANTIC ERRORS\n", new SimpleAttributeSet());
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		StyledDocument docs [] = {doc,doc2};
		
		return  docs;
		
	}
	
	public static int Width = GraphicsEnvironment
	.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	public static int Height = GraphicsEnvironment
	.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;

	public static List<Token> getLibraryTokens(String conteudo) throws LexicalErrorException{
		// TODO Auto-generated method stub

		Reader reader = null;
		
		File file = null;
			
			String url = new File(SystemHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent().replace("%20"," ")+"\\"+conteudo+".lib";
			file = new File(url);
			
			
			if(!file.exists() || !file.canRead()){

				String url2 = new File(SystemHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent().replace("%20"," ")+"\\libs\\"+conteudo+".lib";
				file = new File(url2);
				
				if(!file.exists() || !file.canRead()){
					
					URL fileURL = SystemHandler.class.getClass().getResource("/libs/"+conteudo+".lib");
					
					if(fileURL!=null){
											
						if(fileURL.getPath().substring(0, 4).equals("file")){
													
							try {
								fileURL = new URL(SystemHandler.class.getProtectionDomain().getCodeSource().getLocation()+"");
								ZipInputStream zip = new ZipInputStream(new DataInputStream(fileURL.openStream()));
								ZipEntry entry;
								while ((entry = zip.getNextEntry()) != null){
									if(entry.getName().equals("libs/"+conteudo+".lib")){
										reader = new InputStreamReader(zip);
										break;
									}	
								}
								
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								System.out.println("erro");
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
													
						}else{
							file = new File(fileURL.getPath());
						}
					}
				}		
				
			}else{
				
				if(file.getPath().substring(0, 4).equals("file")){
					
					try {
						URL fileURL = new URL(SystemHandler.class.getProtectionDomain().getCodeSource().getLocation()+"");
						ZipInputStream zip = new ZipInputStream(new DataInputStream(fileURL.openStream()));
						ZipEntry entry;
						while ((entry = zip.getNextEntry()) != null){
							if(entry.getName().equals("libs/"+conteudo+".lib")){
								reader = new InputStreamReader(zip);
								break;
							}	
						}
						
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						System.out.println("erro");
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
											
				}		
			}
		
		
			
		if(reader != null || (file!=null &&  file.exists() && file.canRead()) ){
			
			if(reader == null){
				try {
					reader = new FileReader(file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			String str =  "";
			String text = "";
			try {
	            BufferedReader in = new BufferedReader(reader);
	            while ((str = in.readLine()) != null) {
	                text += str + "\n"; //String.format("%-6d %s %s", linha++, str, "\n");
	            }
	            in.close();
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        }
	        
	        
			lexAnalyzer = new LexicalAnalyzer();
			
			ArrayList<ArrayList<Token>> tokensGroups = lexAnalyzer.analyse(text);
			
			return tokensGroups.get(0);
			
		}
		
		return new ArrayList<Token>();
	}
	
}
