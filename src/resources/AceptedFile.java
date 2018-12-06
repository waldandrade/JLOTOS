package resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JTree.DynamicUtilTreeNode;

import bus.SystemHandler;

public class AceptedFile extends DynamicUtilTreeNode{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String filePath = "";
	public String publicName = "";
	
	public String content = "";
		
	public boolean opened = false;
	public int referredSection = -1; 
	
	public AceptedFile(String filePath, String publicName){
		super(publicName, null);
		this.filePath = filePath;
		this.publicName = publicName;
		File file = new File(filePath);
		if(file.canRead()){
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
		}
		
		int referredSection = SystemHandler.getSectionbyPath(this.filePath);
		if(referredSection!=-1){
			this.opened=true;
			this.referredSection=referredSection;
		}
	}
	
	/**
	 * @return the path
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * @param path the path to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * @return the publicName
	 */
	public String getPublicName() {
		return publicName;
	}
	
	/**
	 * @param publicName the publicName to set
	 */
	public void setPublicName(String publicName) {
		this.publicName = publicName;
	}
	
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	public static final int SAVERESULT_OK = 1;
	public static final int SAVERESULT_NOK = 0;
	public static final int SAVERESULT_OTHER = -1;

	public int saveThisFile(){
// 
	    	if(opened==true && SystemHandler.sectionKits.get(this.referredSection).saved==false){
	    		 
	    			content = SystemHandler.sectionKits.get(this.referredSection).getDataText();
	    			File newFile = new File(filePath);
	    			PrintWriter writer = null;
					try {
					    writer = new PrintWriter(newFile);
						writer.write(content);
					    writer.close();
					    SystemHandler.sectionKits.get(this.referredSection).saved = true;
					}catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return SAVERESULT_NOK;
					}catch(Exception e){
						e.printStackTrace();
						return SAVERESULT_NOK;
					}
					return SAVERESULT_OK;				
	    	}
			return SAVERESULT_OTHER;
		
	}
	
	
	
}
