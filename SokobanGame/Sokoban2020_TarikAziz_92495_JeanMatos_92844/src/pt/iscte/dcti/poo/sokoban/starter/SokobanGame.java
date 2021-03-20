package pt.iscte.dcti.poo.sokoban.starter;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class SokobanGame implements Observer {
  private HashMap<String,Integer> mapa=new HashMap<String,Integer>();
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
    private static String playername;
	private int targetNumber = 0;
	private Empilhadora player;
	private List<AbstractSObject> AbsObj = new ArrayList<AbstractSObject>();
	private int level = 0;
	private Highscores highscores = new Highscores();
	private static SokobanGame INSTANCE;

	private SokobanGame() {
	
			playername=JOptionPane.showInputDialog("Inserir Nome:");
		buildLevel(level);

	}

	public static SokobanGame getInstance() {
		
		
		if (INSTANCE == null)
			INSTANCE = new SokobanGame();
		return INSTANCE;
	}  
	
    /** Função usada para criar os niveis, a função lê o ficheiro referente ao nivel 
     * a partir da pasta "levels"  e vai iterando sobre cada caracter de cada linha,como 
     * cada caracter representa um objecto diferente, ao ler o caracter cria esse objecto 
     * adicionando-o à lista de AbsObj e à lista tiles(usada apenas para fazer upload da imagem).
	 */
	private void buildLevel(int i) {
		List<ImageTile> tiles = new ArrayList<ImageTile>();
		boolean builtPortal = false;
		try {

			File file = new File("levels/level" + i + ".txt");
			Scanner scanner = new Scanner(file);

			for (int y = 0; y != HEIGHT; y++) {
				String a = scanner.nextLine();
				System.out.println(a);
				for (int x = 0; x != WIDTH; x++) {
					char o = a.charAt(x);
					Point2D ip = new Point2D(x, y); // variable IP = Initial Position
					tiles.add(new Chao(ip));

					if (o == '#') {
						Parede p = new Parede(ip);
						AbsObj.add(p);
						tiles.add(p);
					}

					if (o == 'O') {
						Buraco b = new Buraco(ip);
						tiles.add(b);
						AbsObj.add(b);
					}

					if (o == 'C') {
						Caixote caixote = new Caixote(ip);
						AbsObj.add(caixote);
						tiles.add(caixote);
					}

					if (o == 'X') {
						AbstractSObject alvo = new Alvo(ip);
						AbsObj.add(alvo);
						tiles.add(alvo);
						targetNumber++;
					}

					if (o == 'b') {
						Bateria bateria = new Bateria(ip);
						tiles.add(bateria);
						AbsObj.add(bateria);
					}

					if (o == 'p') {
						AbstractSObject pedra = new SmallStone(ip);
						AbsObj.add(pedra);
						tiles.add(pedra);
					}

					if (o == 'P') {
						BigStone Pedra = new BigStone(ip);
						AbsObj.add(Pedra);
						tiles.add(Pedra);
					}

					if (o == 'E') {
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

					if (o == '%') {
						AbstractSObject paredePartida = new Parede_Partida(ip);
						AbsObj.add(paredePartida);
						tiles.add(paredePartida);
					}

					if (o == 't') {
						Portal portal = null;
						if (builtPortal) {
							portal = new Portal(ip, "Portal_Azul");
							AbsObj.add(portal);
							tiles.add(portal);
						} else {
							portal = new Portal(ip, "Portal_Verde");
							builtPortal = true;
							AbsObj.add(portal);
							tiles.add(portal);
						}

					}
				}
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ImageMatrixGUI.getInstance().addImages(tiles);

	}
	// ------------------------------------------------------//

	int secondLastKeyPressed = KeyEvent.VK_DOWN;
	int lastKeyPressed = 0;

	@Override
	public void update(Observed arg0) {
		
		lastKeyPressed = ((ImageMatrixGUI) arg0).keyPressed();
if(lastKeyPressed ==KeyEvent.VK_R) 
	reset();

		System.out.println("Key pressed " + lastKeyPressed);
		if (!isGameOver()) {
			if (Direction.isDirection(lastKeyPressed)) {
				System.out.println("movei");//Confirma se a tecla pressionada corresponde a uma seta que por sua vez é uma direcção
				player.move(lastKeyPressed, secondLastKeyPressed);
				if (hasPassed())LevelUp();

			}
			secondLastKeyPressed = lastKeyPressed;
			if (player != null && level < 7)
				ImageMatrixGUI.getInstance().setStatusMessage(
				"Bateria: " + player.getBattery() + "   Moves: " + player.getMoves() + " Nível: " + level + " Press r to reset level");
			if (isGameOver() && level < 7)
				ImageMatrixGUI.getInstance().setStatusMessage("GAME OVER BEACH!!!");

		}
	}
	
	
    /** --getObjectsInPosition--Esta função é usada em varios locais com o "move" dos objectos, 
    o proposito dessa  função é apenas de retornar uma lista "reduzida"com os objectos que  estão na 
    proxima posição do objecto em causa, para ai mais tarde ocorrer a interação entre os objectos.
	*/
	public List<AbstractSObject> getObjectsInPosition(Point2D position) {
		List<AbstractSObject> a = new ArrayList<AbstractSObject>();
		for (AbstractSObject b : getAbsObj())
			if (b.getPosition().equals(position))
				a.add(b);
		sort(a);
				
		
		return a;
		
	}
private void sort(List<AbstractSObject> a) {
	for(int i=1;i<a.size();i++) {
		if(a.get(i).isMoveable() && !a.get(i-1).isMoveable()) {	
			a.add(i-1, a.get(i));
			a.remove(i+1);
		}
			
	}
}
	public Empilhadora getPlayer() { return player; }

	public void setPlayerNull() { player = null; }

	public int getTargetNumber() { return targetNumber; }
	/**
	 * --Reset()--Função utilizada para reiniciar o nivel em que se está no momento.
	 */
	private void reset() {
		
		targetNumber = 0;
		AbsObj.clear();
		ImageMatrixGUI.getInstance().clearImages();
		buildLevel(level);
		ImageMatrixGUI.getInstance().update();
	}
    
	/**
	 * --LevelUp()--Função usada para subir o nivel em 1, primeiro guarda o Score do nivel na 
	 * pasta e depois incrementa o atributo level em 1.
	 */
	public void LevelUp() {
		
		
		highscores.addHighscore(level, getScore());
		 JOptionPane.showMessageDialog(null,"You passed level with "+getScore()+" points");
		level++;
		

		if (level < 7) {
			
			targetNumber = 0;
			AbsObj.clear();
			ImageMatrixGUI.getInstance().clearImages();
			buildLevel(level);
			highscores.printHighscores(level-1,playername);
		} else {
			
		
		  highscores.printHighscores(level-1,playername);
		  JOptionPane.showMessageDialog(null,"YOU WIN");
		  player=null;
		}
		ImageMatrixGUI.getInstance().update();
		
		
	}
	
	/**
	 * --getScore()--Função que retorna o score do nivel em questão, a formula 
	 * para calcular o score leva em consideração a bateria restante e o numero 
	 * de movimentos feitos no nivel. Quanto maior a bateria maior o score, pelo 
	 * contrario quanto menor os moves maior será o score. 
	 */
	public int getScore() {return  player.getBattery() * 100000 / player.getMoves();}
    
	
	/**
	 * --isGameOver--Confirma se o jogador perdeu o jogo. 
	 */
	public boolean isGameOver() { return this.player == null || !player.hasBattery(); }

	public List<AbstractSObject> getAbsObj() { return AbsObj; }
    
	/**
	 * --hasPassed()--Função que confirma se o o nivel está concluido, faz isto iterando 
	 * sobre todos os objectos da lista e depois confirma um a um se o objecto é um objectivo 
	 * e se esta em cima do alvo. 
	 */
	private boolean hasPassed() {
		int t = 0;
		for (int i = 0; i < AbsObj.size(); i++)
			if (AbsObj.get(i).isObjective() && isThereAlvo(AbsObj.get(i).getPosition())) {
				t++;
				if (t == targetNumber)return true;
			}
            return false;
	}
    
	/**
	 * --isThereAlvo(Point2D a)--Função que confirma se existe um alvo na posição dada pelo 
	 * parâmetro, faz isso iterando pelos ojectos de AbsObj e depois confirma se o objecto é 
	 * um alvo e tem a mesma posição do parâmetro recebido. OBS: usado no hasPassed(). 
	 */
	public boolean isThereAlvo(Point2D a) {
		for (int i = 0; i < AbsObj.size(); i++)
			if (AbsObj.get(i) instanceof Alvo && AbsObj.get(i).getPosition().equals(a))
				return true;
		return false;
	}
	
	/**
	 * Vai pesquisar no mapa o outro par do portal ,dado a sua posiçao.
	 */
	public Point2D getOtherPortal(Point2D a) {
		for (AbstractSObject a2 : AbsObj)
			if (a2 instanceof Portal && !a2.getPosition().equals(a))
				return a2.getPosition();
		return null;
	}
	
}
