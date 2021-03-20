package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class Buraco extends AbstractSObject {

	public Buraco(Point2D position) {
		super(position, 0, false, false, false);
	}

	@Override
	public String getName() { return "Buraco"; }
	
	/**
	 * Irá "engolir" o objecto dado como parametro se for Swallowable ou "entupir" o buraco caso não seja.
	 */
	public void swallow(AbstractSObject a) {
		
		if (a.isSwallowable()) {
			SokobanGame.getInstance().getAbsObj().remove(a);
			ImageMatrixGUI.getInstance().removeImage(a);
			if (a instanceof Empilhadora || a instanceof Caixote)// passar para sokoban metodo
				SokobanGame.getInstance().setPlayerNull();

		} else {
			a.setMobility(false);// no caso da BigStone, ou outro objecto que nao seja swallowable, deixara de ser movel..
			SokobanGame.getInstance().getAbsObj().remove(this);//remove o buraco do array para deixar de engolir.
			ImageMatrixGUI.getInstance().removeImage(this);//remove a imagem tambem.
		}
	}

	@Override
	public void doSomething(AbstractSObject obj) {
		this.swallow(obj);

	}

}
