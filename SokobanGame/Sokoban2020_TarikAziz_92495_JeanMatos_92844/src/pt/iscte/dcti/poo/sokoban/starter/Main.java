package pt.iscte.dcti.poo.sokoban.starter;


import pt.iul.ista.poo.gui.ImageMatrixGUI;


public class Main {

	public static void main(String[] args) {

		SokobanGame d = SokobanGame.getInstance() ;
			ImageMatrixGUI.setSize(SokobanGame.WIDTH, SokobanGame.HEIGHT);
			ImageMatrixGUI.getInstance().registerObserver(d);
		//	
			ImageMatrixGUI.getInstance().go();	
   }
}