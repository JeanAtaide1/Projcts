package pt.iscte.dcti.poo.sokoban.starter;

import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class AbstractSObject implements ImageTile {
   private Point2D position;
   private boolean movable;
   private boolean unpassable;
   private boolean swallowable;
   private boolean isObjective;
   private boolean hasPower;
   private int layer;
   
   public  AbstractSObject(Point2D p,int layer,boolean unpassable,boolean movable,boolean swallowable, boolean isObjective ) {
    position=p;
    this.layer=layer;
    this.movable=movable;
    this.unpassable=unpassable;
    this.swallowable=swallowable;
    this.isObjective=isObjective;
    this.hasPower = false;
    
   }

    @Override
    public abstract String getName();

	@Override
	public Point2D getPosition(){
		return position;
	}

	@Override
	public int getLayer() {
		return layer;
	}
	
    protected void setPosition(Point2D p) {
		position=p;
	}
    
    
    public abstract void doSomething(AbstractSObject a);
		
	public boolean isMoveable() {
		return movable;
	}
	public boolean isUnpassable() {
		return unpassable;
	}
	
	public boolean isSwallowable() {
		return swallowable;
	}
	
	public boolean isObjective() {
		return isObjective;
	}
	
	public boolean hasPower( ) {
		return hasPower;
	}
    
	public void setMobility(boolean t) {
	movable=t;
    }
	
	public void setHasPower(boolean t) {
	hasPower=t;	
	}
    
	public void validatePosition(Point2D newPosition) {
		
		 for (AbstractSObject a : SokobanGame.getInstance().getObjectsInPosition(newPosition))
	    	 if(newPosition.equals(a.getPosition()) && a.isUnpassable()) 
		        return;
		  
		 if (newPosition.getX() >= 0 && newPosition.getX() < SokobanGame.WIDTH && 
			newPosition.getY() >= 0 && newPosition.getY() < SokobanGame.HEIGHT )
			setPosition(newPosition);
	}
	
	
}

	
