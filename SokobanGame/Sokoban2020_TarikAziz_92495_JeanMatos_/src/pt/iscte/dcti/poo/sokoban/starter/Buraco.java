package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public class Buraco extends AbstractSObject {
	
		public Buraco(Point2D position) {
			super(position,0,false,false,false,false);
		}

	@Override
	public String getName() {	
		return "Buraco";
	}

	
	public void swallow(AbstractSObject a) {
		if (a.isSwallowable()) { 
			SokobanGame.getInstance().getAbsObj().remove(a);
			ImageMatrixGUI.getInstance().removeImage(a);
			if (a instanceof Empilhadora || a instanceof Caixote)
				SokobanGame.getInstance().setPlayer();
			
		}else{
			a.setMobility(false);
			SokobanGame.getInstance().getAbsObj().remove(this);
			ImageMatrixGUI.getInstance().removeImage(this);
		}
	}


	@Override
	public void doSomething(AbstractSObject obj) {
		this.swallow(obj);
		
	}

	
}
