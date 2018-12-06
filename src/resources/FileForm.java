package resources;

import java.util.Vector;

public class FileForm {
	
	//private static final int OPT_SPECIFICATION = 0;
	//private static final int OPT_PROCESS = 1;

	private String sourceFolderPath = "";
	private String importedProjectPath = "";
	private String fileName = "";
	private String superSpecPath = "";
	
	private Vector<String> importedLibrarys = null;
	
	public FileForm(){
		importedLibrarys = new Vector<String>();
	}
	
	public boolean addLybrary(String libraryPath){
		
		if(importedLibrarys==null)importedLibrarys= new Vector<String>();
		if(importedLibrarys.contains(libraryPath)){
			return false;
		}
		importedLibrarys.add(libraryPath);
		return true;
		
	}
	
	public boolean removeLybrary(int libraryID){
		
		try{
			importedLibrarys.remove(libraryID);
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
		
	}

	/**
	 * @return the sourceFolderPath
	 */
	public String getSourceFolderPath() {
		return sourceFolderPath;
	}

	/**
	 * @param sourceFolderPath the sourceFolderPath to set
	 */
	public void setSourceFolderPath(String sourceFolderPath) {
		this.sourceFolderPath = sourceFolderPath;
	}

	/**
	 * @return the importedProjectPath
	 */
	public String getImportedProjectPath() {
		return importedProjectPath;
	}

	/**
	 * @param importedProjectPath the importedProjectPath to set
	 */
	public void setImportedProjectPath(String importedProjectPath) {
		this.importedProjectPath = importedProjectPath;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the superSpecPath
	 */
	public String getSuperSpecPath() {
		return superSpecPath;
	}

	/**
	 * @param superSpecPath the superSpecPath to set
	 */
	public void setSuperSpecPath(String superSpecPath) {
		this.superSpecPath = superSpecPath;
	}

	/**
	 * @return the importedLibrarys
	 */
	public Vector<String> getImportedLibrarys() {
		return importedLibrarys;
	}

	/**
	 * @param importedLibrarys the importedLibrarys to set
	 */
	public void setImportedLibrarys(Vector<String> importedLibrarys) {
		this.importedLibrarys = importedLibrarys;
	}
			
}
