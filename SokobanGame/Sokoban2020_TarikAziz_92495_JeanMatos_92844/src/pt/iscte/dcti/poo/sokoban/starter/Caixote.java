package pt.iscte.dcti.poo.sokoban.starter;



import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Caixote extends AbstractSObject implements ActiveObject {
	private Point2D newPosition;

	public Caixote(Point2D position) {
		super(position, 2, true, true,true);
	}

	@Override
	public String getName() { return "Caixote"; }
	
	/**Ira verificar os objectos na proxima posi�ao, se possivel interagir com os 
	 * objectos atraves da fun��o "doSomething" (mover,deslizar, engolir) e por fim 
	 * atualizar a posi��o.  
	 */
	public void move(int lastKeyPressed, int secondLastKeyPressed) {
		
		if (lastKeyPressed == secondLastKeyPressed) {
		Direction desiredDirection = Direction.directionFor(lastKeyPressed);
		newPosition = super.getPosition().plus(desiredDirection.asVector());
		for (AbstractSObject a : SokobanGame.getInstance().getObjectsInPosition(newPosition)) {
			super.validatePosition(newPosition);
			a.doSomething(this);
		}
		super.validatePosition(newPosition);
		ImageMatrixGUI.getInstance().update();
		
	}
	}
	@Override
	public void doSomething(AbstractSObject a) { 
		
		if(a instanceof Empilhadora)
		this.move(SokobanGame.getInstance().lastKeyPressed,SokobanGame.getInstance().secondLastKeyPressed);
	}

	@Override
	public void setNewPosition(Point2D p) { newPosition = p; }

}
