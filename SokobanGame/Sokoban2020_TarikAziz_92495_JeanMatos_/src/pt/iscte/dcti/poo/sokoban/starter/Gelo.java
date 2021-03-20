package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Gelo extends AbstractSObject {

	public Gelo(Point2D position) {
		super(position, 1 , false ,false,false,false);
	}

	@Override
	public String getName() {
		return "Gelo";
	}

	@Override
	public void doSomething(AbstractSObject a) {
		
    	  this.slide(a);  
      
	}
	
	public void slide(AbstractSObject a) {
		Direction desiredDirection = Direction.directionFor(SokobanGame.getInstance().lastKeyPressed);
		Point2D newPosition=a.getPosition().plus(desiredDirection.asVector());
		
		a.validatePosition(newPosition);
		((ActiveObject)a).setNewPosition(newPosition);
		
		 for (AbstractSObject a1 : SokobanGame.getInstance().getObjectsInPosition(newPosition)) { 
	   
	    	 a1.doSomething(a);
		 }
	}

}
