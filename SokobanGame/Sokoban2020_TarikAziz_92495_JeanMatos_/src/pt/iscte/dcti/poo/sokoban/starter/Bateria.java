package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public class Bateria extends AbstractSObject{

	public Bateria(Point2D position) {
		super(position,2,false,false,false,false);
	}

	
	@Override
	public String getName() {
		return "Bateria";
	}

	
	public void Recharge() {
			SokobanGame.getInstance().getPlayer().setBattery();
			SokobanGame.getInstance().getAbsObj().remove(this);
			ImageMatrixGUI.getInstance().removeImage(this);
	}


	@Override
	public void doSomething(AbstractSObject a) {
		if (a instanceof Empilhadora && a.getPosition().equals(this.getPosition())) 
		this.Recharge();
		
	}



	

}
