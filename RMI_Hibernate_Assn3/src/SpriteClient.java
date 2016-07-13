

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Random;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.net.*;
import java.rmi.NotBoundException;

public class SpriteClient extends JPanel{

	private int x;
	private int y;
	final static int SIZE = 10;
	final static int MAX_SPEED = 5;
	private static int jpanelSize;
	private static Color color;
    private JFrame frame;

	public final static Random random = new Random();

	private Sprite sprite;
	private static ArrayList<Sprite> sprites;
	private static SpriteInterface si;
	
	
//	final String[] colorRandom = {"RED","GREEN","BLUE","YELLOW","ORANGE","BLACK","PINK"};

	
	public SpriteClient() throws RemoteException{
		//jpanelSize = getSizeOfJPanel();
		setFrame();
		addMouseListener(new Mouse());
	}

	//method for creating a new ball
	private void createNewSprite (MouseEvent event) throws RemoteException{
		x = event.getX();
		y = event.getY();
		si.createSprite(color, x, y);
	}

    public void setFrame() {
        frame = new JFrame("Bouncing Sprites");
        frame.setSize(jpanelSize + SIZE, jpanelSize + SIZE + 30);// Added Menu bar's lentgh to y position.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
        frame.setResizable(false);
    }
	

	public void animate(){
	    while (true){
	    	
	        repaint();
	        //sleep while waiting to display the next frame of the animation
	        try {
	            Thread.sleep(40);  // wake up roughly 25 frames per second .... 40miliseconds
	        }
	        catch ( InterruptedException exception ) {
	            exception.printStackTrace();
	        }
	    }
	}

    public static void main(String[] args) {
    	//String myHostName = "10.70.200.208";
    	String myHostName = "localhost";
    	try {
			InetAddress myHost = Inet4Address.getLocalHost();
			myHostName = myHost.getHostName();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	int port = 1099;
    	//String serverName = new String("10.70.200.208");
    	String serverName = new String("localhost");
        switch (args.length) {
        case 0:
            break;
        case 1: 
        	serverName = args[0];
        	break;
        case 2:
        	serverName = args[0];
        	port = Integer.parseInt(args[1]);
        	break;
        default:
        	System.out.println("usage: SpriteClient");
        	break;
        }  
        try {
        	System.out.println("Attempting to connect to rmi://"+serverName+":"+port+"/Sprites");
        	si = (SpriteInterface) 
            Naming.lookup(
                "rmi://"+serverName+":"+"/Sprites");

        	jpanelSize = si.getSizeOfJPanel();
        	color = si.getColor();
			SpriteClient sc = new SpriteClient();
			sc.animate();
			sprites = si.getListOfSprites();

        }
        catch (MalformedURLException murle) {
            System.out.println();
            System.out.println(
              "MalformedURLException");
            System.out.println(murle);
        }
        catch (RemoteException re) {
            System.out.println();
            System.out.println("RemoteException");
            System.out.println(re);
        }
        catch (NotBoundException nbe) {
            System.out.println();
            System.out.println("NotBoundException");
            System.out.println(nbe);
        }
        catch (java.lang.ArithmeticException ae) {
            System.out.println();
            System.out.println(
             "java.lang.ArithmeticException");
            System.out.println(ae);
        }
    }

	private class Mouse extends MouseAdapter {
		@Override
		public void mousePressed( final MouseEvent event ){
			try {
				createNewSprite(event);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		try {
			sprites = (ArrayList<Sprite>) si.getListOfSprites();
			//System.out.println("the size is " + sprites.size());
			for (Sprite sprite: sprites){
				sprite.draw(g);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
