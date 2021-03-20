package pt.iscte.dcti.poo.sokoban.starter;

import java.util.List;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Caixote extends AbstractSObject implements ActiveObject{
	private Point2D newPosition;
     private boolean isObjective=true;
     
	public Caixote(Point2D position) {
		super(position,2,true,true,true,true);
	}

	@Override
	public String getName() {
		
		return "Caixote";
	}


	public void move(int lastKeyPressed, int secondLastKeyPressed) {
		
		 Direction desiredDirection = Direction.directionFor(lastKeyPressed);
		  newPosition =super.getPosition().plus(desiredDirection.asVector());
		 for (AbstractSObject a : SokobanGame.getInstance().getObjectsInPosition(newPosition)) {
			 super.validatePosition(newPosition);
			 a.doSomething(this);
		 }
				super.validatePosition(newPosition); 
		 ImageMatrixGUI.getInstance().update();
	}

	
	@Override
	public void doSomething(AbstractSObject a) {
		return;
		
	}

	@Override
	public void setNewPosition(Point2D p) {
		newPosition=p;
		
	}
		      
		
	
	
 
}
