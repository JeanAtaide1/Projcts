package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Portal extends AbstractSObject {
	private String imageName;

	public Portal(Point2D p, String imageName) {
		super(p, 1, false, false, false);
		this.imageName = imageName;
	}

	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public void doSomething(AbstractSObject a) {
		this.teleport(a);
	}
	/**
	 * --teleport(AbstractSObject a)--A função ira teleportar a empilhadora para o outro portal, adquirindo a posição do outro portal 
	 * atraves de outra função auxiliar (getOtherPortal)
	 */
	public void teleport(AbstractSObject a) {
		if (this.getPosition().equals(a.getPosition())) {
			Point2D aux = SokobanGame.getInstance().getOtherPortal(this.getPosition());
			
			a.validatePosition(aux);
			((ActiveObject) a).setNewPosition(aux);
		}
	}
}
