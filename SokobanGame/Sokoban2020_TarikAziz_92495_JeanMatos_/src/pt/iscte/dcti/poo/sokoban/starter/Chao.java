package pt.iscte.dcti.poo.sokoban.starter;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public class Chao extends AbstractSObject {

	
	public Chao(Point2D position) {
		super(position,0,false,false,false,false);
	}

	@Override
	public String getName() {
		return "Chao";
	}

	

	@Override
	public void doSomething(AbstractSObject a) {
		return;
		
	}


}
