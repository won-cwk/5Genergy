

import java.rmi.*;
import java.util.ArrayList;
import java.awt.Color;


/**
 * Remote Interface for "Sprite"
 */
public interface SpriteInterface extends Remote {
  /**
   * Remotely invokable method.
   * @return the message of the remote object, such as "Hello, world!".
   * @exception RemoteException if the remote invocation fails.
   */

	public Color getColor() 	 throws RemoteException;
	public int getSizeOfJPanel() throws RemoteException;
	public void createSprite(Color color,int positionX, int positionY) 
								 throws RemoteException;
	public ArrayList<Sprite> getListOfSprites()
								 throws RemoteException;
}
