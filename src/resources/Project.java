package resources;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

import javax.swing.JTree.DynamicUtilTreeNode;


public class Project extends DynamicUtilTreeNode{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String path = "";
	public String name = "";
	public Vector<LotosFile> lotosFiles = null;
	
	public Project(File projectFile){
		super(projectFile.getName(), new Vector<Object>());
		lotosFiles = new Vector<LotosFile>();
		//Depois criar método para verificar se dentro da pasta do projeto tem um arquivo .fbproject
		path=projectFile.getPath();
		name=projectFile.getName();
	}
	
	public void chargeFiles(){
		for(int cont=0; cont<lotosFiles.size(); cont++){
			add(lotosFiles.get(cont));
		}
	}
	
	class LotosFilter implements FilenameFilter {

		public static final String LOTOS = ".lotos";

		private String filter = "";
		
		public LotosFilter(String filter){
			
			this.filter = filter;
			
		}
		
		@Override
		public boolean accept(File dir, String name) {
			// TODO Auto-generated method stub
			
			return (name.endsWith(filter));
		}
		
		
		
	}
	
}
