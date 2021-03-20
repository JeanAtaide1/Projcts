package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Alvo extends AbstractSObject {
	
	public Alvo(Point2D position){
		super(position,1,false, false, false);
	}

	@Override
	public String getName() { return "Alvo"; }

	@Override
	public void doSomething(AbstractSObject a) { return; }

}
