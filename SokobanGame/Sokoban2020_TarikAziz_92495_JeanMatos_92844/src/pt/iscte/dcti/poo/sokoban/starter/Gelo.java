package pt.iscte.dcti.poo.sokoban.starter;

import java.util.List;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Gelo extends AbstractSObject {

	public Gelo(Point2D position) {
		super(position, 1, false, false, false);
	}

	@Override
	public String getName() { return "Gelo"; }

	@Override
	public void doSomething(AbstractSObject a) { this.slide(a); }
	
	/**
	 * Vai deslizar o objecto ate o fim do gelo ou ate encontrar algum objecto 
	 * fisico a bloquear o caminho.
	 */
	public void slide(AbstractSObject a) {
		
		Direction desiredDirection = Direction.directionFor(SokobanGame.getInstance().lastKeyPressed);
		Point2D newPosition = a.getPosition().plus(desiredDirection.asVector());
		List<AbstractSObject> ab = SokobanGame.getInstance().getObjectsInPosition(newPosition);
		a.validatePosition(newPosition);
		
		if (a.getPosition().equals(newPosition)) {
			((ActiveObject) a).setNewPosition(newPosition);//temos de salvar esta variavel pois esta a ser alterada,
			for (AbstractSObject a1 : ab)              //e vamos utilizar isto no move apos os ciclos recursivos do doSomething
				a1.doSomething(a);
				
			

		}
	}

}
