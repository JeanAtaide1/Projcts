package pt.iscte.dcti.poo.sokoban.starter;

import java.awt.event.KeyEvent;
import java.util.Random;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Empilhadora extends AbstractSObject implements ActiveObject{
  private String imageName="Empilhadora_D";
  private int bateria=100;
  private int moves=0;
  private Point2D newPosition;
 

  public Empilhadora(Point2D position) {//,int l,boolean unpassable, boolean movable,boolean swallowable, boolean isObjective){
		super(position,2,true,true,true,false);
	}

	
	@Override
	public String getName() {
		return imageName ;
	}

	
	public void setImageName( Direction desiredDirection) {
		imageName="Empilhadora_"+desiredDirection.toString().charAt(0);
		bateria--;
		moves++;
		ImageMatrixGUI.getInstance().update();
	}
	
	//--------------------------------------------------------------------------//
	@Override
	public void move(int lastKeyPressed, int secondLastKeyPressed) {
		bateria--;
		moves++;
		
		Direction desiredDirection = Direction.directionFor(lastKeyPressed);		
		 if(hasBattery()) {  
		 if (lastKeyPressed == secondLastKeyPressed) {
		      newPosition =super.getPosition().plus(desiredDirection.asVector());
		     
		     
		     for (AbstractSObject a : SokobanGame.getInstance().getObjectsInPosition(newPosition)) { 
		    	 if(a.isMoveable()) 
		    	    ((ActiveObject)a).move(lastKeyPressed, secondLastKeyPressed);	
		    	 super.validatePosition(newPosition);
		    	 
		    	 a.doSomething(this);
		    	 
		     }
		     
		       super.validatePosition(newPosition);
		      
		
		}else{
			imageName="Empilhadora_"+desiredDirection.toString().charAt(0); //Mudar de direção sem se mover
		}
		
		ImageMatrixGUI.getInstance().update();
		 }
			
	}

	

	public boolean hasBattery() {
		return bateria > 0;
	}
	public int EmpBattery() {
		return bateria;

	}
	

	public void setBattery() {
		bateria=100;
	}
	
	public int getMoves() {
		return moves;
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
