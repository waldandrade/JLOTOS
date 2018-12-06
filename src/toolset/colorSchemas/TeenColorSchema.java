package toolset.colorSchemas;

import java.awt.Color;

import toolset.ColorSchema;

public class TeenColorSchema implements ColorSchema {

	
	private final Color BACKGROUND_COLOR = new Color(45,170,153);
	private final Color BORDER_COLOR = new Color(50,15,40);
	private final Color BAR_COLOR = new Color(240,240,240);
	private final Color FIELD_COLOR = new Color(50,50,50);
	private final Color FILE_INFO_COLOR = new Color(201, 180, 137);
	private final Color SELECTED_FILE_INFO_COLOR = new Color(151,130,87);
	
	
	public Color getBackgroundColor(){
		return BACKGROUND_COLOR;
	}
	
	public Color getBorderColor(){
		return BORDER_COLOR;
	}
	
	public Color getBarColor(){
		return BAR_COLOR;
	}
	
	public Color getFieldColor(){
		return FIELD_COLOR;
	}
	
	public Color getTextFileInfoColor(){
		return FILE_INFO_COLOR;
	}
	

	public Color getSelectedTextFileInfoColor(){
		return SELECTED_FILE_INFO_COLOR;
	}
}
