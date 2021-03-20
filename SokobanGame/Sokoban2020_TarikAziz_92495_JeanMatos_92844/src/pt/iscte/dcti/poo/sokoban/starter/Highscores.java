package pt.iscte.dcti.poo.sokoban.starter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Highscores {
	
	private List<Integer> highscores;
	private List<Integer> oldHighscores;
    private List<String> Names;
    
    
    
	public Highscores() {
		highscores = new ArrayList<Integer>();
		oldHighscores = new ArrayList<Integer>();
        Names=new ArrayList<String>();
		File fil = new File("levels/Topscores.txt");
		Scanner scanner;
		try {
			scanner = new Scanner(fil);
           if(scanner .hasNextLine()) {
			while (scanner.hasNextLine()) {// vai pecorrer o antigo ficheiro de highscores e por em uma lista de inteiros os antigos scores

				String a1 = scanner.nextLine();
				Scanner scan = new Scanner(a1);
				int a = scan.nextInt();
				String text =a1.replaceAll("[\\d]", "");
				Names.add(text);
				oldHighscores.add(a);
				scan.close();

			}
			scanner.close();
           }else {
        	   for (int i=0;i< 7;i++) {
        		   oldHighscores.add(0);
        		   Names.add(" - RECORD AT LEVEL");
        	   }
           }
        	   
		} catch (FileNotFoundException e1) {
		
			e1.printStackTrace();
		}

	}

	public void addHighscore(int level, int highscore) {
		highscores.add(highscore);
	}

	/**
	 * printHighscores() will print in the "Highscores" file the new best scores.
	 */
	public void printHighscores(int lvl,String name) {

		try {

			for (int i = 0; i < highscores.size(); i++) {//Vai comparar o novo score com o antigo score do mesmo nivel e deixar
				                                         //  apenas o maior no Array OldHighScores
				if (oldHighscores.get(i) < highscores.get(i)) {
					oldHighscores.add(i, highscores.get(i));//adiciona na posiçao do nivel respectivo.
					oldHighscores.remove(i + 1);//retira o antigo score que foi empurrado para a posiçao seguinte
					Names.add(i," - by "+name+" RECORD AT LEVEL");
					Names.remove(i+1);
				}
			}
			
                File file1 = new File("levels/Highscore"+lvl+".txt");
				PrintWriter fw = new PrintWriter(new FileWriter(file1,true));
				fw.println(highscores.get(lvl) + "  points by "+name );
				fw.close();
			
			PrintWriter pw=new PrintWriter(new File("levels/Topscores.txt"));
			
			for (int i = 0; i < oldHighscores.size(); i++) {
				
				pw.println(oldHighscores.get(i)+Names.get(i)+i);
				System.out.println(Names.get(i));
			}
pw.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
