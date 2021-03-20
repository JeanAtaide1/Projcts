package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class SmallStone extends AbstractSObject implements ActiveObject {

	private Point2D newPosition;

	public SmallStone(Point2D position) {
		super(position, 2, true, true, false);
	}

	@Override
	public String getName() { return "SmallStone"; }

	/**Ira verificar os objectos na proxima posiçao, se possivel interagir com os 
	 * objectos atraves da função "doSomething" (mover,deslizar, engolir) e por fim 
	 * atualizar a posição.  
	 */
	@Override
	public void move(int lastKeyPressed, int secondLastKeyPressed) {

		Direction desiredDirection = Direction.directionFor(lastKeyPressed);
		newPosition = super.getPosition().plus(desiredDirection.asVector());
		for (AbstractSObject a : SokobanGame.getInstance().getObjectsInPosition(newPosition)) {
			super.validatePosition(newPosition);
			a.doSomething(this);
		}
		super.validatePosition(newPosition);
		ImageMatrixGUI.getInstance().update();
	}

	@Override
	public void doSomething(AbstractSObject a) { 
		if(a instanceof Empilhadora)
		this.move(SokobanGame.getInstance().lastKeyPressed,SokobanGame.getInstance().secondLastKeyPressed);
	}

	@Override
	public void setNewPosition(Point2D p) { newPosition = p; }

}
