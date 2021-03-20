package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Parede extends AbstractSObject {

	public Parede(Point2D position) {
		super(position, 2, true, false, false);
	}

	@Override
	public String getName() { return "Parede"; }

	@Override
	public void doSomething(AbstractSObject a) { return; }
}
