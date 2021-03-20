package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public interface ActiveObject {
	 
    public void setNewPosition(Point2D p);
    
	public void move(int lastKeyPressed , int secondLastKeyPressed); 
		
	
}
