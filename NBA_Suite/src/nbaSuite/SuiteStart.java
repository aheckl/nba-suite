package nbaSuite;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Diese Klasse ist die Einstiegssicht der NBA Suite. Hier gibt es einen Button f�r jeden Service.
 * 
 * @author Andreas Heckl
 * @version 1.0 April 2022
 */
public class SuiteStart extends JFrame {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				
				try {
					SuiteStart frame = new SuiteStart();
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
	public SuiteStart() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("NBA Suite " + Konstanten.VERSION);
		setBounds(0, 0, Konstanten.FRAME_WIDTH, Konstanten.FRAME_HEIGHT);
		
		//Geruest für GUI erstellen
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Begruessungstexte erzeugen
		JLabel lblWelcome = GuiKomponenten.createJLabel(contentPane, "Willkommen in der NBA Suite!", 100, 20, Konstanten.LBL_WIDTH, Konstanten.LBL_HEIGHT);
		JLabel lblAskService = GuiKomponenten.createJLabel(contentPane, "Welchen Service möchten Sie nutzen?", 100, 60, Konstanten.LBL_WIDTH, Konstanten.LBL_HEIGHT);
		
		//Button für Teamvergleich erstellen
		JButton btnTeamvergleich = GuiKomponenten.createButton(contentPane, "Teamvergleich (BETA)", 100, 115, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		btnTeamvergleich.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Teamvergleich.run();
				SuiteStart.super.dispose();
			}
		});
		
		//Button für Tool zum direkten Vergleich erstellen
		JButton btnDirekterVergleich = GuiKomponenten.createButton(contentPane, "Direkter Vergleich", 100, 198, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		btnDirekterVergleich.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DirekterVergleich.run();
				SuiteStart.super.dispose();
			}
		});
		
		//Button für histor. Analyse erstellen
		JButton btnAnalyse = GuiKomponenten.createButton(contentPane, "histor. Analyse (BETA)", 100, 280, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		btnAnalyse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HistorAnalyse.run();
				SuiteStart.super.dispose();
			}
		});
		
	}
	
}
