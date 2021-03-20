package pt.iscte.dcti.poo.sokoban.starter;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class SokobanGame implements Observer {

	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	private int targetNumber=0;
	private Empilhadora player;
	private List<AbstractSObject> AbsObj = new ArrayList<AbstractSObject>();
	private int level=0;

	private static SokobanGame INSTANCE;


	private SokobanGame(){	
		buildLevel(level);
		file1 = new File("levels/Highscores.txt");

	}
	public static SokobanGame getInstance() {
		if (INSTANCE == null)
			INSTANCE = new SokobanGame();
		return INSTANCE;
	}




	private void buildLevel(int i) {
		List<ImageTile> tiles = new ArrayList<ImageTile>();
		try {
			File file = new File("levels/level"+i+".txt");
			Scanner scanner=new Scanner(file);

			for (int y = 0; y != HEIGHT; y++) {
				String a=scanner.nextLine();
				System.out.println(a);
				for (int x=0; x != WIDTH ; x++) {
					char o = a.charAt(x);
					Point2D ip =new Point2D(x,y); //variable IP = Initial Position
					tiles.add(new Chao(ip));

					if(o == '#') {
						Parede p=new Parede(ip);
						AbsObj.add(p);
						tiles.add(p);
					}

					if(o == 'O') {
						Buraco b=new Buraco(ip);
						tiles.add(b);
						AbsObj.add(b);
					}

					if(o == 'C') {	
						Caixote caixote=new Caixote(ip);
						AbsObj.add(caixote);
						tiles.add(caixote);	
					}

					if(o == 'X') {
						AbstractSObject alvo=new Alvo(ip);
						AbsObj.add(alvo);
						tiles.add(alvo);
						targetNumber++;
					}

					if(o == 'b') {
						Bateria bateria = new Bateria(ip);
						tiles.add(bateria);
						AbsObj.add(bateria);
					}

					if(o == 'p') {
						AbstractSObject pedra=new SmallStone(ip);
						AbsObj.add(pedra);
						tiles.add(pedra);
					}

					if(o == 'P') {
						BigStone Pedra=new BigStone(ip);
						AbsObj.add(Pedra);
						tiles.add(Pedra);
					}

					if(o == 'E') {
						Empilhadora player = new Empilhadora(ip);
						this.player = player;
						AbsObj.add(player);
						tiles.add(player);
					}
					
					if (o == 'g') {
						AbstractSObject gelo = new Gelo(ip);
						AbsObj.add(gelo);
						tiles.add(gelo);
					}
					
					if (o == 'm') {
						AbstractSObject martelo = new Martelo(ip);
						AbsObj.add(martelo);
						tiles.add(martelo);
					}
					
					if(o == '%') {
						AbstractSObject paredePartida = new Parede_Partida(ip);
						AbsObj.add(paredePartida);
						tiles.add(paredePartida);
					}
				}
			}	  
			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ImageMatrixGUI.getInstance().addImages(tiles);	   

	}
	//------------------------------------------------------//

	int secondLastKeyPressed = 0x28;
    int lastKeyPressed=0;
	private File file1;

	@Override
	public void update(Observed arg0) {
		 lastKeyPressed = ((ImageMatrixGUI)arg0).keyPressed();

		System.out.println("Key pressed " + lastKeyPressed);
		if (!hasEnded()) {
			if (lastKeyPressed >= 0x25 && lastKeyPressed <= 0x28 ) {
				player.move(lastKeyPressed , secondLastKeyPressed);
			
				if(hasPassed())
					LevelUp();
			}
			secondLastKeyPressed = lastKeyPressed;
			if(player != null)
				ImageMatrixGUI.getInstance().setStatusMessage("Bateria: "+player.EmpBattery()+"   Moves: "+player.getMoves()+" Nível: " +level);
			if(hasEnded()) 
				ImageMatrixGUI.getInstance().setStatusMessage("GAME OVER BEACH!!!");
		}
	}



	public List<AbstractSObject> getObjectsInPosition(Point2D position){
		List<AbstractSObject> a = new ArrayList<AbstractSObject>();
		for (AbstractSObject b : getAbsObj())
			if ( b.getPosition().equals(position))
				a.add(b);
		
		return a;
		
	}

	public Empilhadora getPlayer() {
		return player;
	}

	public void setPlayer() {
		player = null;
	}

	public int getTargetNumber() {
		return targetNumber;
	}


	private static void clear() {
		ImageMatrixGUI.getInstance().clearImages();
	}

	public void LevelUp() {
		try {
			PrintWriter pw = new PrintWriter(file1);
			pw.println("Highscore for level "+level+":");
			pw.println((player.EmpBattery()*100/player.getMoves()));
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		targetNumber=0;
		AbsObj.clear();
		level++;
		clear();

		buildLevel(level);
		ImageMatrixGUI.getInstance().update();

	}

	public boolean hasEnded() {
		return this.player==null || !player.hasBattery() ;
			
	}

	public List<AbstractSObject> getAbsObj(){
		return AbsObj;
	}

	private boolean hasPassed() {
		int t=0;
		for(int i=0; i<AbsObj.size();i++) 	
			if(AbsObj.get(i).isObjective() && isThereAlvo(AbsObj.get(i).getPosition())) {

				t++;
				if(t==targetNumber) 
					return true;
			}

		return false;
	}

	public boolean isThereAlvo(Point2D a){
      for(int i=0; i<AbsObj.size();i++) 
			if(AbsObj.get(i) instanceof Alvo && AbsObj.get(i).getPosition().equals(a))
				return true;
		return false;	
	}
void setEmpPosition(Point2D p) {
	player.setPosition(p);
}

}
