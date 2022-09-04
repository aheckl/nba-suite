package nbaSuite;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 * @author Andreas Heckl
 *
 */
public class DirekterVergleich extends JFrame {

	private final int taWidth = Konstanten.FRAME_WIDTH-100;
	private final int taHeight = 150; 
	
	private final int x_pos = 25;
	
	static int punkteindex = 4;
	static int reboundindex = 16;
	static int assistsindex = 17;
	
	private static String infoButtonText = "Überblick: Das Programm vergleicht Statistiken von NBA Spielern miteinander.\n\n"
			+ "Input: Ein Verzeichnis mit CSV Dateien mit Statistiken von NBA Spielern.\n"
			+ "optimales Dateiformat: Vorname_Nachname.csv z.B. Dirk_Nowitzki.csv\n"
			+ "In jeder Saison wird ein Gewinner in der jeweiligen Statistik (Punkte, Rebounds oder Assists) ermittelt.\n"
			+ "Begonnen wird bei der ältesten Saison, bei der alle angegebenen Spielder teilgenommen haben.\n"
			+ "CSV Dateien können von www.nba.com heruntergeladen werden.\n";
	

	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					DirekterVergleich frame = new DirekterVergleich();
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
	public DirekterVergleich() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("NBA Suite " + Konstanten.VERSION + " --> direkter Vergleich");
		setBounds(0, 0, Konstanten.FRAME_WIDTH, Konstanten.FRAME_HEIGHT);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//---------Bereich 1-------------------------------
		int y0 = 20;
		JButton btnZurueck = GuiKomponenten.createButton(contentPane, "Zurück zur Startseite", x_pos, y0, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		JButton btnInfo = GuiKomponenten.createButton(contentPane, "Info", x_pos+Konstanten.BTN_WIDTH+10, y0, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);

		int y1 = y0+Konstanten.BTN_HEIGHT+20;
		JLabel lblPfad = GuiKomponenten.createJLabel(contentPane, "Bitte Verzeichnis mit zu vergleichenden Spielerdateien angeben:", x_pos, y1, Konstanten.LBL_WIDTH, Konstanten.LBL_HEIGHT);
		
		int y2 = y1+Konstanten.LBL_HEIGHT+20;
		JTextField tfPfad = GuiKomponenten.createTextField(contentPane, x_pos, y2, Konstanten.TF_WIDTH, Konstanten.TF_HEIGHT);
		JButton btnDurchsuchen = GuiKomponenten.createButton(contentPane, "Durchsuchen ...", x_pos+Konstanten.TF_WIDTH+20, y2, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
			
		//----------------Bereich 2-----------------------------
		int y4 = y2+Konstanten.BTN_HEIGHT+40;
		JLabel lblDateiliste = GuiKomponenten.createJLabel(contentPane, "Im Verzeichnis enthaltene Dateien:", x_pos, y4, Konstanten.LBL_WIDTH, Konstanten.LBL_HEIGHT);
		
		int y5 = y4+Konstanten.LBL_HEIGHT+20;
		JScrollPane spDateiliste = GuiKomponenten.createJScrollPane(contentPane, x_pos, y5, taWidth, taHeight);
		
		JTextArea taDateiliste = new JTextArea();
		taDateiliste.setEditable(false);
		spDateiliste.setViewportView(taDateiliste);
		
		//----------------Bereich 3-----------------
		int y6 = y5+150+70;
		JButton btnStart1 = GuiKomponenten.createButton(contentPane, "vergleiche Punkte", x_pos, y6, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		JButton btnStart2 = GuiKomponenten.createButton(contentPane, "vergleiche Rebounds", x_pos+Konstanten.BTN_WIDTH+20, y6, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		JButton btnStart3 = GuiKomponenten.createButton(contentPane, "vergleiche Assists", x_pos+2*Konstanten.BTN_WIDTH+2*20, y6, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		
		int y7 = y6+Konstanten.BTN_HEIGHT+20;
		JLabel lblErgebnis = GuiKomponenten.createJLabel(contentPane, "Ergebnis:", x_pos, y7, Konstanten.LBL_WIDTH, Konstanten.LBL_HEIGHT);
		
		int y8 = y7+Konstanten.LBL_HEIGHT+20;
		int spHeight = Konstanten.FRAME_HEIGHT-y8-3*Konstanten.TASKBAR_HEIGHT;
		JScrollPane spErgebnis = GuiKomponenten.createJScrollPane(contentPane, x_pos, y8, taWidth, spHeight);
		
		JTextArea taErgebnis = new JTextArea();
		taErgebnis.setEditable(false);
		spErgebnis.setViewportView(taErgebnis);
		
		//-----------ActionListener--------------------
		
		btnZurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DirekterVergleich.super.dispose();
				SuiteStart.main(null);
			}
		});
		
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				infoBox(infoButtonText, "Was das Programm tut");
			}
		});
		
		
		tfPfad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = tfPfad.getText();
				taDateiliste.setText(FilenamesToString(path));
				taDateiliste.setCaretPosition(0);
			}
		});
		
		btnDurchsuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	String s = chooser.getSelectedFile().getAbsolutePath();
		        	tfPfad.setText(s);
		            taDateiliste.setText(FilenamesToString(s));
		            taDateiliste.setCaretPosition(0);
		        }
			}
		});
		
		btnStart1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pfad = tfPfad.getText();
				taErgebnis.setText(starteDirektenVergleich(pfad, punkteindex));
			}
		});
		
		btnStart2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pfad = tfPfad.getText();
				taErgebnis.setText(starteDirektenVergleich(pfad, reboundindex));
			}
		});
		
		btnStart3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pfad = tfPfad.getText();
				taErgebnis.setText(starteDirektenVergleich(pfad, assistsindex));
			}
		});
	}
	
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	
    
    
    
	public static String starteDirektenVergleich(String path, int index) {		
		File folder = new File(path);
		File[] listOfFilesAndDirectories = folder.listFiles();
		
		String disziplin = "Punkte";
		if(index == 16) {disziplin = "Rebounds";}
		if(index == 17) {disziplin = "Assists";}
		
		ArrayList<String[][]> playerArrays = new ArrayList<String[][]>();
		ArrayList<String> playerNames = new ArrayList<String>();
		
		for (File file : listOfFilesAndDirectories) {
			playerArrays.add(csvToArray(file));
			playerNames.add(file.getName().substring(0, file.getName().indexOf('.')));
		}
		
		String res = "";
		res += playerNames.toString();
		res += "\n\n";
		
		String[] relevantSeasons = getRelevantSeasons(playerArrays);
		//Durch Saisons iterieren
		for (int sai = 0; sai < relevantSeasons.length; sai++) {
			res += "----- Saison " + relevantSeasons[sai] + "-----\n";
			ArrayList<Float> scores = new ArrayList<Float>();
			// Durch Spieler iterieren
			for (int sp = 0; sp < playerArrays.size(); sp++) {
				//Druch zeilen eines Spielers iterieren
				for (int z = 0; z < playerArrays.get(sp).length; z++) {
					if(playerArrays.get(sp)[z][0].equals(relevantSeasons[sai])) {
						scores.add(Float.parseFloat(playerArrays.get(sp)[z][index]));					}
				}
			}
			
			res += scores.toString();
			res += "\n";
			Float maxScore = (float) 0;
			int maxScoreIndex = 0;
			for (int i = 0; i < scores.size(); i++) {
				if (scores.get(i) > maxScore) {
					maxScore = scores.get(i);
					maxScoreIndex = i;
				}
			}
			
			res += "Sieger: " + playerNames.get(maxScoreIndex) + " (" + maxScore.toString() + " " + disziplin + ")\n\n";
			
		}
		return res;
	}
	
	
	private static String[] getRelevantSeasons(ArrayList<String[][]> playerArrays) {
		ArrayList<String[]> allPlayerSeasons = getSeasonsOfPlayers(playerArrays);
		int player_id_with_least_seasons = 0;
		for (int i = 0; i < allPlayerSeasons.size(); i++) {
			if (allPlayerSeasons.get(i).length < allPlayerSeasons.get(player_id_with_least_seasons).length) {
				player_id_with_least_seasons = i;
			}
		}
		
		return allPlayerSeasons.get(player_id_with_least_seasons);
	}
	
	
	private static ArrayList<String[]> getSeasonsOfPlayers(ArrayList<String[][]> playerArrays) {
		ArrayList<String[]> allPlayerSeasons = new ArrayList<String[]>();
		for (int i = 0; i < playerArrays.size(); i++) {
			allPlayerSeasons.add(getSeasonsOfPlayer(playerArrays.get(i)));
		}
		return allPlayerSeasons;
	}
	
	
	private static String[] getSeasonsOfPlayer(String[][] playerArray) {
		int numberSeasons = playerArray.length-1; //wegen ueberschrift -1
		String[] seasons = new String[numberSeasons];
		for (int i = 0; i < numberSeasons; i++) {
			seasons[i] = playerArray[i+1][0];//i+1
		}
		return seasons;
	}

	
	private static String[][] csvToArray(File file) {
		return listOfLinesToArray(csvToListOfLines(file));
	}

	
	private static ArrayList<String> csvToListOfLines(File file) {
		var Arr = new ArrayList<String>();
		Scanner myReader;
		try {
			myReader = new Scanner(file, "UTF-8");
			while(myReader.hasNextLine()) {
				String line = myReader.nextLine();
				Arr.add(line);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return Arr;
	}
	
	private static String[][] listOfLinesToArray(ArrayList<String> listOfLines){
		int zeilen = listOfLines.size();
		int spalten = listOfLines.get(0).toString().split("	").length;
		var TwoDimdata = new String[zeilen][spalten];
	    for (var i = 0; i < zeilen; i++) {
	    	String[] arr = listOfLines.get(i).split("	");
	        for (var j = 0; j < arr.length; j++) {
	            TwoDimdata[i][j] = arr[j];
	        }
	    }
	    return TwoDimdata;
	}

	
	

	
	
	public static String FilenamesToString(String path) {
		String res = "";
		File folder = new File(path);
		File[] listOfFilesAndDirectories = folder.listFiles();
		ArrayList<File> listOfOnlyFiles = new ArrayList<File>();
		//Unterordner aus Liste entfernen, sodass nur Datien drin bleiben
		try {
			for(File file : listOfFilesAndDirectories) {
				if(!(file.isDirectory())) {
					listOfOnlyFiles.add(file);
				}
			}
		} catch (NullPointerException e) {
			return "Sie haben einen ungültigen Pfad angegeben";
		}		
		
		res += "Es befinden sich " + listOfOnlyFiles.size() + " Dateien im Verzeichnis:\n";
		
		for (File file : listOfOnlyFiles) {
			res += file.getName() + "\n";
		}
		return res;
	}
}
