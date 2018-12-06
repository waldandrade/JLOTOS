package toolset.colorSchemas;

import java.awt.Color;
import java.awt.SystemColor;

import toolset.ColorSchema;

public class ProfessionalColorSchema implements ColorSchema {
	
	private final Color BACKGROUND_COLOR = new Color(0,153, 153);
	private final Color BORDER_COLOR = new Color(0,0,0);
	private final Color BAR_COLOR = new Color(0,153, 153);
	private final Color FIELD_COLOR = new Color(150,170,170);
	private final Color FILE_INFO_COLOR = new Color(215,215,255);
	private final Color SELECTED_FILE_INFO_COLOR = SystemColor.activeCaption;
	
	
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
