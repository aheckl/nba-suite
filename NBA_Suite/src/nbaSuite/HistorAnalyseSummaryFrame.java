package nbaSuite;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;

/**
 * @author Andreas Heckl
 */
public class HistorAnalyseSummaryFrame extends JFrame {
		
	private final int frameWidth = Konstanten.SCREEN_WIDTH/3;
	private final int frameHeight = Konstanten.SCREEN_HEIGHT/3;
	
	private final int scrollPaneWidth = frameWidth-30;
	private final int scrollPaneHeight = frameHeight-Konstanten.TASKBAR_HEIGHT;
	
	private static String[] warnings;

	
	public static void run(String[] warningsAsStringArr) {
		warnings = warningsAsStringArr;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HistorAnalyseSummaryFrame frame = new HistorAnalyseSummaryFrame();
					ImageIcon img = new ImageIcon(Konstanten.NBA_ICON_PATH);
					frame.setIconImage(img.getImage());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HistorAnalyseSummaryFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Zusammenfassung");
		setBounds ( frameWidth, frameHeight,frameWidth, frameHeight);
		
		//leeres Inhaltsfeld erzeugen
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//Scrollbar hinzufuegen
		JScrollPane scrollPane = GuiKomponenten.createJScrollPane(contentPane, 0, 0, scrollPaneWidth, scrollPaneHeight);
		
		//Inhaltsfeld mit Warnungen der Analyse befuellen
		JList<String> textList = new JList<String>();
		scrollPane.setViewportView(textList);
		textList.setListData(warnings);
	}
}
