package resources;

import java.awt.Component;

import javax.swing.JTextPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.StyledDocument;

public class NonWrappingTextPane extends JTextPane {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
	 * 
	 */

public NonWrappingTextPane() {
    super();
  }

  public NonWrappingTextPane(StyledDocument doc) {
    super(doc);
  }

  // Override getScrollableTracksViewportWidth
  // to preserve the full width of the text
  public boolean getScrollableTracksViewportWidth() {
    Component parent = getParent();
    ComponentUI ui = getUI();
    
    
    return parent != null ? (ui.getPreferredSize(this).width <= parent
        .getSize().width) : true;
  }


}
