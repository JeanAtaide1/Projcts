package pt.iscte.dcti.poo.sokoban.starter;



import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Empilhadora extends AbstractSObject implements ActiveObject {
	private String imageName = "Empilhadora_D";
	private int bateria = 100;
	private int moves = 0;
	private Point2D newPosition;

	public Empilhadora(Point2D position) {
		super(position, 2, true, true, false);
	}

	@Override
	public String getName() {
		return imageName;
	}


	// --------------------------------------------------------------------------//
	/**Ira verificar os objectos na proxima posiçao, se possivel interagir com os 
	 * objectos atraves da função "doSomething" (mover,deslizar, engolir) e por fim 
	 * atualizar a posição.  
	 */
	@Override
	public void move(int lastKeyPressed, int secondLastKeyPressed) {
		
		bateria--;
		moves++;

		Direction desiredDirection = Direction.directionFor(lastKeyPressed);
	
			if (lastKeyPressed == secondLastKeyPressed) {
				newPosition = super.getPosition().plus(desiredDirection.asVector());

				for (AbstractSObject a : SokobanGame.getInstance().getObjectsInPosition(newPosition)) {
				  
					super.validatePosition(newPosition);                             

					a.doSomething(this); //se a tiver a mesma posiçao que o player , então ira efectuar uma açao respectiva ao objecto .
				}
				super.validatePosition(newPosition);

			} else {
				imageName = "Empilhadora_" + desiredDirection.toString().charAt(0); // Mudar de direção sem se mover
			}
            
			ImageMatrixGUI.getInstance().update();

	}
	
	
	/**
	 *--hasBattery()-- Retorna true se a bateria for maior que 0.
	 */
	public boolean hasBattery() { return bateria > 0;}

	/**
	 *--getBattery()-- Retorna o valor da bateria.
	 */
	public int getBattery() { return bateria; }
	
	/**
	 * Irá recarregar a bateria na sua capacidade total.
	 */
	public void setBattery() { bateria = 100; }

	/**
	 * Irá retornar o numero de movimentos feito pela empilhadora nesse nivel.
	 */
	public int getMoves() { return moves; }

	@Override
	public void doSomething(AbstractSObject a) { return; }

	@Override
	public void setNewPosition(Point2D p) { newPosition = p; }

}
