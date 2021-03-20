package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public class Alvo extends AbstractSObject {
	

	public Alvo(Point2D position){
		super(position,1,false,false,false,false);
	}

	
	
	@Override
	public String getName() {
		return "Alvo";
	}
	
	/*public void checkVictory(AbstractSObject a) {
		//for(AbstractSObject b : SokobanGame.getInstance().getAbsObj() )
		if (a.isObjective())
		  targetsOnMark++;
		if(targetsOnMark == SokobanGame.getInstance().getTargetNumber())	
		  SokobanGame.getInstance().LevelUp();
	}
     */

	@Override
	public void doSomething(AbstractSObject a) {
		//while(this.getPosition().equals(a.getPosition())) {
		//this.checkVictory(a);
		
	}
    
	

	
   

}
