package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class BigStone extends AbstractSObject implements ActiveObject{
	
      public BigStone(Point2D position) {
  		super(position,2,true,true,false,false);
  	}

	@Override
	public String getName() {
		return "BigStone";
	}

    
	public void move(int lastKeyPressed, int secondLastKeyPressed) {
		
		 Direction desiredDirection = Direction.directionFor(lastKeyPressed);
		 Point2D newPosition =super.getPosition().plus(desiredDirection.asVector());
		 for (AbstractSObject a : SokobanGame.getInstance().getObjectsInPosition(newPosition))
			 a.doSomething(this);
		 super.validatePosition(newPosition); 
		 ImageMatrixGUI.getInstance().update();
	}


	@Override
	public void doSomething(AbstractSObject a) {
		return;
		
	}

	@Override
	public void setNewPosition(Point2D p) {
		// TODO Auto-generated method stub
		
	}
		      
	








}