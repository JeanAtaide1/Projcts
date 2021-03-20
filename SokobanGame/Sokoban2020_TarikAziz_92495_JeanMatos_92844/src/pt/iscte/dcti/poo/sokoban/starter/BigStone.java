package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class BigStone extends AbstractSObject implements ActiveObject {
	private Point2D newPosition;

	public BigStone(Point2D position) {
		super(position, 2, true, true, false);
	}

	@Override
	public String getName() { return "BigStone"; }
	
	@Override
    public boolean isSwallowable() { return false; }
	
	/**Ira verificar a proxima posiçao e realizar a ação respectiva ao objecto (mover,deslizar, engolir) 
	 * 
	 */
	public void move(int lastKeyPressed, int secondLastKeyPressed) {
		
		Direction desiredDirection = Direction.directionFor(lastKeyPressed);
		newPosition = super.getPosition().plus(desiredDirection.asVector());
		for (AbstractSObject a : SokobanGame.getInstance().getObjectsInPosition(newPosition))
			a.doSomething(this);
		super.validatePosition(newPosition);
		ImageMatrixGUI.getInstance().update();
	}

	@Override
	public void doSomething(AbstractSObject a) { 
		
		if(this.isMoveable() && a instanceof Empilhadora)
		this.move(SokobanGame.getInstance().lastKeyPressed,SokobanGame.getInstance().secondLastKeyPressed);
	}

	@Override
	public void setNewPosition(Point2D p) { newPosition = p; }

}