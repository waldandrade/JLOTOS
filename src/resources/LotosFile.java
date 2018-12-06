package resources;

import java.io.File;

public class LotosFile extends AceptedFile{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LotosFile(File file){
		
		super(file.getPath(),file.getName());
		
	}
}
