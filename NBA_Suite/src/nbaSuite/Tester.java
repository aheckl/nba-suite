package nbaSuite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

public class Tester {

	static int punkteindex = 4;
	static int reboundindex = 16;
	static int assistsindex = 17;
	
	public static void main(String[] args) {

		starteDirektenVergleich("C:\\Users\\Andi Heckl\\Desktop\\Spielderdateien");		
		
	}
	
	
	public static void starteDirektenVergleich(String path) {		
		File folder = new File(path);
		File[] listOfFilesAndDirectories = folder.listFiles();
		
		ArrayList<String[][]> playerArrays = new ArrayList<String[][]>();
		ArrayList<String> playerNames = new ArrayList<String>();
		
		for (File file : listOfFilesAndDirectories) {
			playerArrays.add(csvToArray(file));
			playerNames.add(file.getName().substring(0, file.getName().indexOf('.')));
		}
		
		System.out.println(playerNames.toString());
		
		String[] relevantSeasons = getRelevantSeasons(playerArrays);
		//Durch Saisons iterieren
		for (int sai = 0; sai < relevantSeasons.length; sai++) {
			System.out.println("----- Saison " + relevantSeasons[sai] + "-----");
			ArrayList<Float> scores = new ArrayList<Float>();
			// Durch Spieler iterieren
			for (int sp = 0; sp < playerArrays.size(); sp++) {
				//Durch zeilen eines Spielers iterieren
				for (int z = 0; z < playerArrays.get(sp).length; z++) {
					if(playerArrays.get(sp)[z][0].equals(relevantSeasons[sai])) {
						scores.add(Float.parseFloat(playerArrays.get(sp)[z][punkteindex]));					}
				}
			}
			
			System.out.println(scores.toString());
			Float maxScore = (float) 0;
			int maxScoreIndex = 0;
			for (int i = 0; i < scores.size(); i++) {
				if (scores.get(i) > maxScore) {
					maxScore = scores.get(i);
					maxScoreIndex = i;
				}
			}
			
			System.out.println("Sieger: " + playerNames.get(maxScoreIndex) + " (" + maxScore.toString() + " Punkte)\n" );
			
		}
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

}
