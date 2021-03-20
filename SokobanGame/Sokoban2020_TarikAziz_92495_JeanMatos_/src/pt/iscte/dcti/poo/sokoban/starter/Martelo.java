package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class Martelo extends AbstractSObject {

	public Martelo(Point2D p) {
		super(p, 2, false, false, false, false);
	}

	@Override
	public String getName() {
		return "Martelo";
	}
	
	public void Empower() {
		SokobanGame.getInstance().getPlayer().setHasPower(true);
		SokobanGame.getInstance().getAbsObj().remove(this);
		ImageMatrixGUI.getInstance().removeImage(this);
	}

	@Override
	public void doSomething(AbstractSObject a) {
		if (a instanceof Empilhadora && a.getPosition().equals(this.getPosition()));
		this.Empower();
	}

}
