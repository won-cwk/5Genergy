package business_layer;

import java.rmi.*;
import java.util.ArrayList;
import java.awt.Color;
import persistence_layer.Sprite;


/**
 * Remote Interface for "Sprite"
 */
public interface SpriteInterface extends Remote {
  /**
   * Remotely invokable method.
   * @return the message of the remote object, such as "Hello, world!".
   * @exception RemoteException if the remote invocation fails.
   */
	public void createSprite(Color color,int positionX, int positionY) throws RemoteException;
	public int getSizeOfJPanel() throws RemoteException;
	public ArrayList<Sprite> getListOfSprites()
			throws java.rmi.RemoteException;
}
