package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class Parede_Partida extends AbstractSObject {

	public Parede_Partida(Point2D p) {
		super(p, 2, true, false, false);
	}

	@Override
	public String getName() { return "Parede_Partida"; }

	@Override
	public void doSomething(AbstractSObject a) {
		if (a.hasPower()) this.breakObject();
	}

	/**
    * Esta função ira destruir a parede, caso a Empilhadora esteja "Empowered"
    */
	public void breakObject() {
		SokobanGame.getInstance().getAbsObj().remove(this);
		ImageMatrixGUI.getInstance().removeImage(this);
	}

}
