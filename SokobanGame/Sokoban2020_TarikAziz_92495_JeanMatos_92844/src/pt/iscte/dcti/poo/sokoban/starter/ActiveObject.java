package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public interface ActiveObject {
	 
    public void setNewPosition(Point2D p);
    
    /**Ira verificar os objectos na proxima posi�ao, se possivel interagir com os 
	 * objectos atraves da fun��o "doSomething" (mover,deslizar, engolir) e por fim 
	 * atualizar a posi��o.  
	 */
	public void move(int lastKeyPressed, int secondLastKeyPressed);

}
