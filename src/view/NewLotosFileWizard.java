package view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JList;
import javax.swing.SwingConstants;

import bus.SystemHandler;

import resources.FileForm;

public class NewLotosFileWizard extends JFrame {
	
	public boolean commited = false;

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel infojPanel = null;
	private JPanel definitionsjPanel = null;
	private JPanel buttonsjPanel = null;
	private JLabel lotosImagejLabel = null;
	private JLabel lotosFileNamejLabel = null;
	private JLabel lotosDescriptionjLabel = null;
	private JButton finishjButton = null;
	private JButton canceljButton = null;
	private JLabel namejLabel = null;
	private JLabel specTypejLabel = null;
	private JLabel librarysjLabel = null;
	private JLabel superSpecjLabel = null;
	private JLabel jLabel = null;
	private JTextField namejTextField = null;
	private JRadioButton specjRadioButton = null;
	private JLabel specjLabel = null;
	private JRadioButton procjRadioButton = null;
	private JLabel SourceFolderjLabel = null;
	private JLabel projectjLabel = null;
	private JLabel procjLabel = null;
	private JTextField sourceFolderjTextField = null;
	private JTextField projectjTextField = null;
	private JButton browseSFjButton = null;
	private JButton browseProjjButton = null;
	private JButton browseSuperSpecjButton = null;
	private JButton addLibjButton = null;
	private JButton removeLibjButton = null;
	private JTextField superSpecjTextField = null;
	private JList libsjList = null;
	private JLabel divjLabel1 = null;
	/**
	 * This is the default constructor
	 */
	public NewLotosFileWizard() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(481, 521);
		this.setContentPane(getJContentPane());
		this.setTitle("new Lotos File");
		this.getLibsjList().setListData(newFileForm.getImportedLibrarys());
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
			jContentPane.add(getButtonsjPanel(), BorderLayout.SOUTH);
			jContentPane.add(getInfojPanel(), BorderLayout.NORTH);
			jContentPane.add(getDefinitionsjPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes infojPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getInfojPanel() {
		if (infojPanel == null) {
			lotosDescriptionjLabel = new JLabel();
			lotosDescriptionjLabel.setBounds(new Rectangle(29, 49, 225, 16));
			lotosDescriptionjLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			lotosDescriptionjLabel.setText("Create a new LOTOS specification file");
			lotosFileNamejLabel = new JLabel();
			lotosFileNamejLabel.setBounds(new Rectangle(14, 11, 147, 28));
			lotosFileNamejLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 14));
			lotosFileNamejLabel.setText("Lotos Specification");
			lotosImagejLabel = new JLabel();
			lotosImagejLabel.setText("");
			ImageIcon img = new ImageIcon(getClass().getResource("/resources/img/lotosFILE.png"));
			lotosImagejLabel.setIcon(img);
			lotosImagejLabel.setBounds(new Rectangle(350, 5, 97, 74));
			infojPanel = new JPanel();
			infojPanel.setLayout(null);
			infojPanel.setPreferredSize(new Dimension(0, 90));
			infojPanel.setBackground(Color.white);
			infojPanel.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaptionText, 1));
			infojPanel.add(lotosImagejLabel, null);
			infojPanel.add(lotosFileNamejLabel, null);
			infojPanel.add(lotosDescriptionjLabel, null);
		}
		return infojPanel;
	}

	/**
	 * This method initializes definitionsjPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDefinitionsjPanel() {
		if (definitionsjPanel == null) {
			divjLabel1 = new JLabel();
			divjLabel1.setBounds(new Rectangle(0, 80, 465, 15));
			divjLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
			divjLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			divjLabel1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 10));
			divjLabel1.setText("_________________________________________________________________________");
			procjLabel = new JLabel();
			procjLabel.setBounds(new Rectangle(290, 147, 63, 16));
			procjLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			procjLabel.setText("processo");
			projectjLabel = new JLabel();
			projectjLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			projectjLabel.setSize(new Dimension(96, 16));
			projectjLabel.setLocation(new Point(13, 57));
			projectjLabel.setText("Import to Project:");
			SourceFolderjLabel = new JLabel();
			SourceFolderjLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			SourceFolderjLabel.setSize(new Dimension(96, 16));
			SourceFolderjLabel.setLocation(new Point(13, 24));
			SourceFolderjLabel.setText("Source Folder:");
			specjLabel = new JLabel();
			specjLabel.setBounds(new Rectangle(173, 147, 84, 16));
			specjLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			specjLabel.setText("specification");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(24, 203, 0, 0));
			jLabel.setText("JLabel");
			superSpecjLabel = new JLabel();
			superSpecjLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			superSpecjLabel.setSize(new Dimension(120, 20));
			superSpecjLabel.setLocation(new Point(13, 188));
			superSpecjLabel.setText("Super Specification:");
			librarysjLabel = new JLabel();
			librarysjLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			librarysjLabel.setSize(new Dimension(118, 21));
			librarysjLabel.setLocation(new Point(13, 236));
			librarysjLabel.setText("Librarys to import:");
			specTypejLabel = new JLabel();
			specTypejLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			specTypejLabel.setSize(new Dimension(53, 24));
			specTypejLabel.setLocation(new Point(13, 147));
			specTypejLabel.setText("Type:");
			namejLabel = new JLabel();
			namejLabel.setText("Name:");
			namejLabel.setSize(new Dimension(52, 20));
			namejLabel.setLocation(new Point(13, 111));
			namejLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			definitionsjPanel = new JPanel();
			definitionsjPanel.setLayout(null);
			definitionsjPanel.setPreferredSize(new Dimension(0, 400));
			definitionsjPanel.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaptionText, 1));
			definitionsjPanel.add(namejLabel, null);
			definitionsjPanel.add(specTypejLabel, null);
			definitionsjPanel.add(superSpecjLabel, null);
			definitionsjPanel.add(librarysjLabel, null);
			definitionsjPanel.add(getNamejTextField(), null);
			definitionsjPanel.add(getSpecjRadioButton(), null);
			definitionsjPanel.add(specjLabel, null);
			definitionsjPanel.add(getProcjRadioButton(), null);
			definitionsjPanel.add(SourceFolderjLabel, null);
			definitionsjPanel.add(projectjLabel, null);
			definitionsjPanel.add(procjLabel, null);
			definitionsjPanel.add(getSourceFolderjTextField(), null);
			definitionsjPanel.add(getProjectjTextField(), null);
			definitionsjPanel.add(getBrowseSFjButton(), null);
			definitionsjPanel.add(getBrowseProjjButton(), null);
			definitionsjPanel.add(getBrowseSuperSpecjButton(), null);
			definitionsjPanel.add(getAddLibjButton(), null);
			definitionsjPanel.add(getRemoveLibjButton(), null);
			definitionsjPanel.add(getSuperSpecjTextField(), null);
			definitionsjPanel.add(getLibsjList(), null);
			definitionsjPanel.add(divjLabel1, null);
		}
		return definitionsjPanel;
	}

	/**
	 * This method initializes buttonsjPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonsjPanel() {
		if (buttonsjPanel == null) {
			buttonsjPanel = new JPanel();
			buttonsjPanel.setLayout(null);
			buttonsjPanel.setPreferredSize(new Dimension(0, 50));
			buttonsjPanel.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaptionText, 1));
			buttonsjPanel.add(getFinishjButton(), null);
			buttonsjPanel.add(getCanceljButton(), null);
		}
		return buttonsjPanel;
	}

	
	private FileForm newFileForm = new FileForm();;  //  @jve:decl-index=0:
	
	/**
	 * This method initializes finishjButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getFinishjButton() {
		if (finishjButton == null) {
			finishjButton = new JButton();
			finishjButton.setBounds(new Rectangle(283, 15, 79, 24));
			finishjButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			finishjButton.setText("Finish");
		}
		return finishjButton;
	}

	/**
	 * This method initializes canceljButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCanceljButton() {
		if (canceljButton == null) {
			canceljButton = new JButton();
			canceljButton.setBounds(new Rectangle(375, 15, 79, 24));
			canceljButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			canceljButton.setText("Cancel");
		}
		return canceljButton;
	}

	/**
	 * This method initializes namejTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNamejTextField() {
		if (namejTextField == null) {
			namejTextField = new JTextField();
			namejTextField.setSize(new Dimension(209, 20));
			namejTextField.setLocation(new Point(144, 109));
		}
		return namejTextField;
	}

	/**
	 * This method initializes specjRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getSpecjRadioButton() {
		if (specjRadioButton == null) {
			specjRadioButton = new JRadioButton();
			specjRadioButton.setBounds(new Rectangle(152, 146, 21, 21));
		}
		return specjRadioButton;
	}

	/**
	 * This method initializes procjRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getProcjRadioButton() {
		if (procjRadioButton == null) {
			procjRadioButton = new JRadioButton();
			procjRadioButton.setBounds(new Rectangle(269, 146, 21, 21));
		}
		return procjRadioButton;
	}

	/**
	 * This method initializes sourceFolderjTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSourceFolderjTextField() {
		if (sourceFolderjTextField == null) {
			sourceFolderjTextField = new JTextField();
			sourceFolderjTextField.setBounds(new Rectangle(144, 22, 209, 20));
		}
		return sourceFolderjTextField;
	}

	/**
	 * This method initializes projectjTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getProjectjTextField() {
		if (projectjTextField == null) {
			projectjTextField = new JTextField();
			projectjTextField.setLocation(new Point(144, 55));
			projectjTextField.setSize(new Dimension(209, 20));
		}
		return projectjTextField;
	}

	/**
	 * This method initializes browseSFjButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBrowseSFjButton() {
		if (browseSFjButton == null) {
			browseSFjButton = new JButton();
			browseSFjButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			browseSFjButton.setLocation(new Point(366, 20));
			browseSFjButton.setSize(new Dimension(85, 22));
			browseSFjButton.setText("Browse...");
			browseSFjButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					chooser = new JFileChooser();
					File sourceFolder = getSourceFolder();
					if(sourceFolder!=null && sourceFolder.canRead()){
						sourceFolderjTextField.setText(sourceFolder.getPath());
					}
				}
			});
			
		}
		return browseSFjButton;
	}

	/**
	 * This method initializes browseProjjButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBrowseProjjButton() {
		if (browseProjjButton == null) {
			browseProjjButton = new JButton();
			browseProjjButton.setPreferredSize(new Dimension(34, 10));
			browseProjjButton.setSize(new Dimension(85, 22));
			browseProjjButton.setText("Browse...");
			browseProjjButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			browseProjjButton.setLocation(new Point(366, 53));
		}
		return browseProjjButton;
	}

	/**
	 * This method initializes browseSuperSpecjButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBrowseSuperSpecjButton() {
		if (browseSuperSpecjButton == null) {
			browseSuperSpecjButton = new JButton();
			browseSuperSpecjButton.setLocation(new Point(366, 187));
			browseSuperSpecjButton.setText("Browse...");
			browseSuperSpecjButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			browseSuperSpecjButton.setSize(new Dimension(85, 22));
		}
		return browseSuperSpecjButton;
	}

	/**
	 * This method initializes addLibjButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddLibjButton() {
		if (addLibjButton == null) {
			addLibjButton = new JButton();
			addLibjButton.setLocation(new Point(366, 236));
			addLibjButton.setMnemonic(KeyEvent.VK_UNDEFINED);
			addLibjButton.setText("Add...");
			addLibjButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			addLibjButton.setSize(new Dimension(85, 22));
		}
		return addLibjButton;
	}

	/**
	 * This method initializes removeLibjButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRemoveLibjButton() {
		if (removeLibjButton == null) {
			removeLibjButton = new JButton();
			removeLibjButton.setLocation(new Point(366, 270));
			removeLibjButton.setText("Remove.");
			removeLibjButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
			removeLibjButton.setSize(new Dimension(85, 22));
		}
		return removeLibjButton;
	}

	/**
	 * This method initializes superSpecjTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSuperSpecjTextField() {
		if (superSpecjTextField == null) {
			superSpecjTextField = new JTextField();
			superSpecjTextField.setLocation(new Point(144, 189));
			superSpecjTextField.setSize(new Dimension(209, 20));
		}
		return superSpecjTextField;
	}

	/**
	 * This method initializes libsjList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getLibsjList() {
		if (libsjList == null) {
			libsjList = new JList();
			libsjList.setLocation(new Point(144, 238));
			libsjList.setSize(new Dimension(209, 68));
		}
		return libsjList;
	}

	/**
	 * @return the newFileForm
	 */
	public FileForm getNewFileForm() {

		newFileForm.setFileName(namejTextField.getText());
		newFileForm.setSourceFolderPath(sourceFolderjTextField.getText());
		
		return newFileForm;
	}
	
	public JFileChooser chooser = null;
	
	private File getSourceFolder(){
				
		chooser.setName("Source Folder");
		chooser.setDialogType(JFileChooser.CUSTOM_DIALOG);
		chooser.setDialogTitle("Source Folder");
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
	
	

}  //  @jve:decl-index=0:visual-constraint="10,10"
