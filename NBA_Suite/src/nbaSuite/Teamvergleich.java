package nbaSuite;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;



public class Teamvergleich extends JFrame{
	
	private final int frameWidth = Konstanten.SCREEN_WIDTH;
	private final int frameHeight = Konstanten.SCREEN_HEIGHT;
	
	private final int taWidth = frameWidth/2-100;
	private final int taHeight = 150; 
	
	private final int x = 25;
	private final int y = 20;
	
	
	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Teamvergleich frame = new Teamvergleich();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
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
	public Teamvergleich() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("NBA Suite " + Konstanten.VERSION + " --> Teamvergleich");
		//Auf folgende Größe wird gesetzt beim drücken auf "Verkleinern"
		setBounds ( frameWidth/4, frameHeight/4,frameWidth/2, frameHeight/2);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//-------------Bereich 1-------------------------------		
		JButton btnZurueck = GuiKomponenten.createButton(contentPane, "Zurück zur Startseite", x, y, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		
		int y2 = y+Konstanten.BTN_HEIGHT+20;
		JLabel lblPfadTeam1 = GuiKomponenten.createJLabel(contentPane, "Bitte Verzeichnis mit CSV Dateien von Team 1 angeben:", x, y2, Konstanten.LBL_WIDTH, Konstanten.LBL_HEIGHT);
		JLabel lblPfadTeam2 = GuiKomponenten.createJLabel(contentPane, "Bitte Verzeichnis mit CSV Dateien von Team 2 angeben:", x+taWidth+20, y2, Konstanten.LBL_WIDTH, Konstanten.LBL_HEIGHT);
		
		int y3 = y2+Konstanten.BTN_HEIGHT+20;
		JTextField tfPfadTeam1 = GuiKomponenten.createTextField(contentPane, x, y3, Konstanten.TF_WIDTH, Konstanten.TF_HEIGHT);
		JButton btnDurchsuchenTeam1 = GuiKomponenten.createButton(contentPane, "Durchsuchen Team 1",x+Konstanten.TF_WIDTH+20, y3, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		JTextField tfPfadTeam2 = GuiKomponenten.createTextField(contentPane, x+taWidth+20, y3, Konstanten.TF_WIDTH, Konstanten.TF_HEIGHT);
		JButton btnDurchsuchenTeam2 = GuiKomponenten.createButton(contentPane, "Durchsuchen Team 2",x+taWidth+20+Konstanten.TF_WIDTH+20, y3, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		
		//-----------------------Bereich 1.5--------------------
		int y4 = y3+Konstanten.LBL_HEIGHT+20;
		JLabel lblDateilisteTeam1 = GuiKomponenten.createJLabel(contentPane, "Im Team 1 Verzeichnis enthaltene Dateien:", x, y4, Konstanten.LBL_WIDTH, Konstanten.LBL_HEIGHT);
		JLabel lblDateilisteTeam2 = GuiKomponenten.createJLabel(contentPane, "Im Team 2 Verzeichnis enthaltene Dateien:", x+taWidth+20, y4, Konstanten.LBL_WIDTH, Konstanten.LBL_HEIGHT);
		
		int y5 = y4+Konstanten.LBL_HEIGHT+20;
		JScrollPane spDateilisteTeam1 = GuiKomponenten.createJScrollPane(contentPane, x, y5, taWidth, taHeight);
		
		JList jlDateilisteTeam1 = new JList();
		spDateilisteTeam1.setViewportView(jlDateilisteTeam1);
	
		JScrollPane spDateilisteTeam2 = GuiKomponenten.createJScrollPane(contentPane, x+taWidth+20, y5, taWidth, taHeight);
		
		JList jlDateilisteTeam2 = new JList();
		spDateilisteTeam2.setViewportView(jlDateilisteTeam2);
		
		//---------------------Bereich2---------------------
		int y6 = y5+taHeight+20;
		JButton btnPunkte = GuiKomponenten.createButton(contentPane, "Punkte",x, y6, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		
		int x2 = x+Konstanten.BTN_WIDTH+20;
		JButton btnRebounds = GuiKomponenten.createButton(contentPane, "Rebounds",x2, y6, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		
		int x3 = x2+Konstanten.BTN_WIDTH+20;
		JButton btnAssists = GuiKomponenten.createButton(contentPane, "Assists",x3, y6, Konstanten.BTN_WIDTH, Konstanten.BTN_HEIGHT);
		
		//--------------------Bereich 3----------------
		int y7 = y6+Konstanten.BTN_HEIGHT+20;
		JLabel lblErgebnisTeam1 = GuiKomponenten.createJLabel(contentPane, "Ergebnis Team 1:", x, y7, Konstanten.LBL_WIDTH, Konstanten.LBL_HEIGHT);
		JLabel lblErgebnisTeam2 = GuiKomponenten.createJLabel(contentPane, "Ergebnis Team 2:", x+taWidth+20, y7, Konstanten.LBL_WIDTH, Konstanten.LBL_HEIGHT);
		
		int y8 = y7+Konstanten.LBL_HEIGHT+20;
		int spHeight = frameHeight-y8-Konstanten.TASKBAR_HEIGHT*2-20;
		JScrollPane spErgebnisTeam1 = GuiKomponenten.createJScrollPane(contentPane,x, y8, taWidth, spHeight);
		
		JTextArea taErgebnisTeam1 = new JTextArea();
		taErgebnisTeam1.setEditable(false);
		spErgebnisTeam1.setViewportView(taErgebnisTeam1);
		
		JScrollPane spErgebnisTeam2 = GuiKomponenten.createJScrollPane(contentPane, x+taWidth+20, y8, taWidth, spHeight);
		
		JTextArea taErgebnisTeam2 = new JTextArea();
		taErgebnisTeam2.setEditable(false);
		spErgebnisTeam2.setViewportView(taErgebnisTeam2);

		
		//-------------Listener Funktionalitäten:------------------------------------------------
		
		btnZurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Teamvergleich.super.dispose();
				SuiteStart.main(null);
			}
		});
		
		tfPfadTeam1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = tfPfadTeam1.getText();
				jlDateilisteTeam1.setListData(FilenamesToArray(path));
			}
		});
		
		tfPfadTeam2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = tfPfadTeam2.getText();
				jlDateilisteTeam2.setListData(FilenamesToArray(path));
			}
		});
		

		btnDurchsuchenTeam1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(!(tfPfadTeam1.getText().contentEquals(""))) {
					File currentPath = new File(tfPfadTeam1.getText());
					chooser.setCurrentDirectory(currentPath);
				}

				int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	String path = chooser.getSelectedFile().getAbsolutePath();
		        	tfPfadTeam1.setText(path);
		            jlDateilisteTeam1.setListData(FilenamesToArray(path));
		        }
			}
		});
		
		btnDurchsuchenTeam2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(!(tfPfadTeam2.getText().contentEquals(""))) {
					File currentPath = new File(tfPfadTeam2.getText());
					chooser.setCurrentDirectory(currentPath);
				}

				int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	String path = chooser.getSelectedFile().getAbsolutePath();
		        	tfPfadTeam2.setText(path);
		            jlDateilisteTeam2.setListData(FilenamesToArray(path));
		        }
			}
		});
		
		
		btnPunkte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = tfPfadTeam1.getText();
				try {
					taErgebnisTeam1.setText("TODO: Punktevergleich");
					taErgebnisTeam1.setCaretPosition(0);
					taErgebnisTeam2.setText("TODO: Punktevergleich");
					taErgebnisTeam2.setCaretPosition(0);
				} catch (NullPointerException e) {
					taErgebnisTeam1.setText("Sie haben keinen gültigen Pfad angegeben");
				}
			}
		});
		
		
		btnRebounds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = tfPfadTeam2.getText();
				try {
					taErgebnisTeam1.setText("TODO: Reboundvergleich");
					taErgebnisTeam1.setCaretPosition(0);
					taErgebnisTeam2.setText("TODO: Reboundvergleich");
					taErgebnisTeam2.setCaretPosition(0);
					
				} catch (Exception e) {
					taErgebnisTeam1.setText("Sie haben keinen gültigen Pfad angegeben");
				}
			}
		});

		
		btnAssists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					taErgebnisTeam1.setText("TODO: Assistsvergleich");
					taErgebnisTeam1.setCaretPosition(0);
					taErgebnisTeam2.setText("TODO: Assistsvergleich");
					taErgebnisTeam2.setCaretPosition(0);
				} catch (Exception e) {
					taErgebnisTeam1.setText("Sie haben keinen gültigen Pfad angegeben");
				}
				
			}
		});
	}
	
	
	
	public static String punktevergleich(String path) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		String res = "";

		for (File file : listOfFiles) {
			String filename = file.getName();
			String fileType = filename.substring(filename.length()-4);
		    if (file.isFile() && fileType.equals(".csv")) {
		    	res += file.getName() + "\n" ;
		    }
		}
		if(res.contentEquals("")) {
			return "In dem von Ihnen angegeben Verzeichnis scheint es keine CSV Dateien zu geben.";
		} else {
			return res;
		}
	}
	
	

	
	public static Object[] FilenamesToArray(String path) {
		File folder = new File(path);
		File[] listOfFilesAndDirectories = folder.listFiles();
		ArrayList<File> listOfOnlyFiles = new ArrayList<File>();
		//Unterordner aus Liste entfernen, sodass nur Dateien drin bleiben
		try {
			for(File file : listOfFilesAndDirectories) {
				if(!file.isDirectory()) {
					listOfOnlyFiles.add(file);
				}
			}
		} catch (NullPointerException e) {
			return new String[] {"Sie haben einen ungültigen Pfad angegeben"};
		}		
		
		ArrayList<String> filenames = new ArrayList<String>();
		filenames.add("Es befinden sich " + listOfOnlyFiles.size() + " Dateien im Verzeichnis:\n");
		for (File file : listOfOnlyFiles) {
			filenames.add(file.getName());
		}
		return filenames.toArray();
	}
	


	


}