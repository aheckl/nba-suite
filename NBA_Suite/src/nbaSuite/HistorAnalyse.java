package nbaSuite;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.*;  

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 * @author Andreas Heckl
 *
 */
public class HistorAnalyse extends JFrame {
	//Hilfsvariablen für Layout:
	private final int taWidth = Konstanten.FRAME_WIDTH-100;
	private final int x = 25;
	
	File csvFile;
	
	//Dieser Text wird beim Klick auf den Info Button angezeigt:
	private static String toolBeschreibung = "Das Programm analysiert den historischen Verlauf der Statistiken eines einzelnen Spielers.\n"
			+ "Folgende Analysen werden gemacht:\n"
			+ "TODO";


	/**
	 * Diese Methode wird beim Drücken auf den histor. Analyse Button in der Einsstiegssicht der NBA Suite ausgeführt.
	 * Sie lässt die GUI erscheinen.
	 */
	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
					try {
						HistorAnalyse frame = new HistorAnalyse();
						ImageIcon img = new ImageIcon(Konstanten.NBA_ICON_PATH);
						frame.setIconImage(img.getImage());
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		});
	}

	//Layout erstellen:
	public HistorAnalyse() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("NBA Suite " + Konstanten.VERSION + " --> historische Analyse (BETA)");
		setBounds(0, 0, Konstanten.FRAME_WIDTH, Konstanten.FRAME_WIDTH);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//---------Bereich 1-------------------------------
		int y0 = 20;
		JButton btnZurueck = GuiKomponenten.createButton(contentPane, "Zurück zur Startseite", x, y0, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		JButton btnInfo = GuiKomponenten.createButton(contentPane, "Info", x+Konstanten.BTN_WIDTH+10, y0, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		
		int y1 = y0+Konstanten.BTN_HEIGHT+20;
		JLabel lblPfad = GuiKomponenten.createJLabel(contentPane, "Bitte zu analysierende CSV Datei eines Spielers angeben:", x, y1, Konstanten.LBL_WIDTH, Konstanten.LBL_HEIGHT);
		
		int y2 = y1+Konstanten.LBL_HEIGHT+20;
		JTextField tfPfad = GuiKomponenten.createTextField(contentPane, x, y2, Konstanten.TF_WIDTH, Konstanten.TF_HEIGHT);
		JButton btnDurchsuchen = GuiKomponenten.createButton(contentPane, "Durchsuchen ...", x+Konstanten.TF_WIDTH+20, y2, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		
		//----------------Bereich 2-----------------
		int y3 = y2+Konstanten.BTN_HEIGHT+40;
		JButton btnStart = GuiKomponenten.createButton(contentPane, "Start", x, y3, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		
		int y4 = y3+Konstanten.BTN_HEIGHT+20;
		JLabel lblErgebnis = GuiKomponenten.createJLabel(contentPane, "Ergebnis:", x, y4, Konstanten.LBL_WIDTH, Konstanten.LBL_HEIGHT);
		
		int y5 = y4+Konstanten.LBL_HEIGHT+20;
		int spHeight = Konstanten.FRAME_HEIGHT-y5-3*Konstanten.TASKBAR_HEIGHT;
		JScrollPane spErgebnis = GuiKomponenten.createJScrollPane(contentPane, x, y5, taWidth, spHeight);
		
		JTextArea taErgebnis = new JTextArea();
		taErgebnis.setEditable(false);
		spErgebnis.setViewportView(taErgebnis);
		
		//-----------ActionListener--------------------
		btnZurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HistorAnalyse.super.dispose();
				SuiteStart.main(null);
			}
		});
		
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				infoBox(toolBeschreibung, "Was das Programm tut");
			}
		});
		
		btnDurchsuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	String s = chooser.getSelectedFile().getAbsolutePath();
		        	if(s.toUpperCase().contains(".CSV")) {
			        	tfPfad.setText(s);
			        	csvFile = chooser.getSelectedFile();
		        	} else {
		        		infoBox("Fehler: Es wurde keine CSV Datei gewählt!\n"
		        				+ " Bitte wählen Sie eine andere Datei.", "falscher Dateityp");
		        	}
		        }
			}
		});
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pfad = tfPfad.getText();
				csvFile = new File(pfad);
				long startTime = System.currentTimeMillis();
				try {
					String txt = analyze(csvFile);
					taErgebnis.setText(txt);
				} catch (Exception e) {
					infoBox("Eine unbekannte Exception ist aufgetreten. Versuchen sie es später erneut. Das Program wird nun beendet.","Unbekannte Exception aufgetreten");
					e.printStackTrace();
					System.exit(0);
				}
			}
		});
	}
	

    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.PLAIN_MESSAGE);
    }
    
	//alle Variablen zuruecksetzen, sodass eine weitere Datei analysiert werden kann
	public static void variablenZuruecksetzen() {
	}
    
	
    public static String analyze(File file) {
    	StringBuffer res = new StringBuffer();
    	res.append("Folgende Datei wird analysiert:\n" + file.getAbsolutePath() + "\n\n");
    	String filename = file.getName();
    		BufferedReader reader;
    		try {
    			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-15")); 
    			String line = reader.readLine();
    			
    			//bevor eine neue datei geprueft wird ggfs die Variablenwerte einer vorherigen Analyse zuruecksetzen
    			variablenZuruecksetzen();
    	        
    			//durch Zeilen iterieren:
    			while(line != null) {;
    				//TODO: Analyse implementieren
    				line = reader.readLine();
    			}
    			reader.close();
    			return res.toString();	
    		} catch (IOException e) {
    			infoBox("ACHTUNG: Eine I/O Exception ist aufgetreten. Überprüfen Sie den Dateipfad auf Korrektheit und versuchen Sie es erneut.", "FileNotFoundException aufgetreten");
    			return null;
    		}
    }
    
	

	



	
}
