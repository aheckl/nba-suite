package nbaSuite;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

/**
 * Diese Klasse beinhaltet Konstanten fuer das Layout, Versionsnummer und den Pfad des NBA Icons.
 * @author Andreas Heckl
 */
public class Konstanten {
	
	static final String VERSION = "1.0";
	
	static final String NBA_ICON_PATH = "nba.PNG";
	
	static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit ().getScreenSize ().width;
	static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit ().getScreenSize ().height;
	
	static final int TASKBAR_HEIGHT = SCREEN_HEIGHT- GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	
	static final int FRAME_WIDTH = SCREEN_WIDTH/2;
	static final int FRAME_HEIGHT = SCREEN_HEIGHT-2*TASKBAR_HEIGHT;
	
	static final int LBL_WIDTH = SCREEN_WIDTH/4;
	static final int LBL_HEIGHT = 35;
	
	static final int BTN_WIDTH = SCREEN_WIDTH/15;
	static final int BTN_HEIGHT = 35;
	
	static final int TF_WIDTH = SCREEN_WIDTH/4;
	static final int TF_HEIGHT = 35;
	
	static final int x_pos = 25;

}
