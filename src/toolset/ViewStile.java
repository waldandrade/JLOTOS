package toolset;

import config.Configs;
import toolset.colorSchemas.ChildColorSchema;
import toolset.colorSchemas.ProfessionalColorSchema;
import toolset.colorSchemas.TeenColorSchema;
import toolset.languages.EN_US_Language;
import toolset.languages.PT_BR_Language;

public class ViewStile{

	//constants defines language
	public static final String PT_BR = "PT-BR";
	public static final String EN_US = "EN-US";
	public static final String DEFAULT_LANGUAGE = "EN-US";
	//----------------------------------
	
	//Constants defines Color schemas of the view appearance
	public static final String PROFESSIONAL_SCHEMA = "PROFESSIONAL-SCHEMA";
	public static final String TEEN_SCHEMA = "TEEN-SCHEMA";
	public static final String CHILD_SCHEMA = "CHILD-SCHEMA";
	public static final String DEFAULT_SCHEMA = "PROFESSIONAL-SCHEMA";
	//-----------------------------------
	
	//Constants defines system weight
	public static final String LIGHT_SYSTEM = "LIGHT-SYSTEM";
	public static final String HEAVY_SYSTEM = "HEAVY-SYSTEM";
	public static final String DEFAULT_WEIGHT = "HEAVY-SYSTEM";
	//-----------------------------------------------
	
	
	private Language actualLanguage = null;
	private ColorSchema actualColorSchema = null;
	//private SystemWeight actualSystemWeight = null;
	
	//it defines dimensions of the fields include on the Program view
	private int windowWidth;
	private int windowHeight;
	private int toolBarWidth;
	private int toolBarHeight;
	private int actionWidth;
	private int actionHeight;
	private int windowsPanelWidth;
	private int windowsPanelHeight;
	private int mainWidth;
	private int mainHeight;	
	//---------------------------------------------------------------
	
	
	
	public ViewStile(){
			
			
			//FileReader confFileReader = new FileReader(getClass().getClassLoader().getResource("config/config").getFile());
			//BufferedReader br = new BufferedReader(confFileReader);
				
			//String line = "";
			
			//while((line = br.readLine())!= null){
			
				//String definition = (line.substring(0, line.indexOf("="))).trim();
				//String value = (line.substring(line.indexOf("=")+1,line.indexOf(";"))).trim();
				
				
					
			
					if(Configs.Language.equals(DEFAULT_LANGUAGE)){
					      
					}
					if(Configs.Language.equals(PT_BR)){
						actualLanguage = new PT_BR_Language();	
					}else if(Configs.Language.equals(EN_US)){
						actualLanguage = new EN_US_Language();		
					}else{
						System.out.println("Tha Language not exists!!!");
					}
					
					if(Configs.ColorSchema.equals(DEFAULT_SCHEMA)){
				}
					if(Configs.ColorSchema.equals(PROFESSIONAL_SCHEMA)){
						actualColorSchema = new ProfessionalColorSchema();
					}else if(Configs.ColorSchema.equals(CHILD_SCHEMA)){
						actualColorSchema = new ChildColorSchema();
					}else if(Configs.ColorSchema.equals(TEEN_SCHEMA)){
						actualColorSchema = new TeenColorSchema();
					}else{
						System.out.println("The Color schema not exists!");
					}
					
					
					if(Configs.SystemWeight.equals(DEFAULT_WEIGHT)){
						
					}else if(Configs.SystemWeight.equals(LIGHT_SYSTEM)){
						
					}else if(Configs.SystemWeight.equals(HEAVY_SYSTEM)){
						
					}else{
						
					}
										
					windowWidth = Configs.Width;
					setFieldsWidths();
					
					
					windowHeight = Configs.Height;
					setFieldsHeights();
		
	}

	private void setFieldsHeights() {
		// TODO Auto-generated method stub
		
		toolBarHeight = windowHeight*(15/100);
		windowsPanelHeight = windowHeight*(20/100);
		mainHeight = windowHeight*(55/100);
		actionHeight = windowHeight;
		
	}

	private void setFieldsWidths() {
		// TODO Auto-generated method stub
		toolBarWidth = windowWidth;
		windowsPanelWidth = windowWidth;
		actionWidth = windowWidth*(20/100);
		mainWidth = windowWidth*(80/100);
		
	}

	public Language getActualLanguage() {
		return actualLanguage;
	}

	public ColorSchema getActualColorSchema() {
		return actualColorSchema;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public int getToolBarWidth() {
		return toolBarWidth;
	}

	public int getToolBarHeight() {
		return toolBarHeight;
	}

	public int getActionWidth() {
		return actionWidth;
	}

	public int getActionHeight() {
		return actionHeight;
	}

	public int getWindowsPanelWidth() {
		return windowsPanelWidth;
	}

	public int getWindowsPanelHeight() {
		return windowsPanelHeight;
	}

	public int getMainWidth() {
		return mainWidth;
	}

	public int getMainHeight() {
		return mainHeight;
	}
	
	
	
	
	
	
}
