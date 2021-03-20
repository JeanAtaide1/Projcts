package pt.iscte.dcti.poo.sokoban.starter;


import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Point2D;

public class Main {

	public static void main(String[] args) {
		SokobanGame s = SokobanGame.getInstance() ;
			ImageMatrixGUI.setSize(SokobanGame.WIDTH, SokobanGame.HEIGHT);
			ImageMatrixGUI.getInstance().registerObserver(s);
			ImageMatrixGUI.getInstance().go();	
   }
}