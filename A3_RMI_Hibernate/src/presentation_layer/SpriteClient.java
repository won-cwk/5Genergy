package presentation_layer;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Random;

import business_layer.SpriteInterface;
import persistence_layer.Sprite;

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
	private static int jpanelSize;
	final static int SIZE = 10;
	final static int MAX_SPEED = 5;
	private Color color = Color.BLUE;
    private JFrame frame;

	public final static Random random = new Random();

	private Sprite sprite;
	private SpriteClient spriteClient;
	private ArrayList<Sprite> sprites;
	private static SpriteInterface si;
	
	
	String[] colorRandom = {"RED","GREEN","BLUE","YELLOW","ORANGE","BLACK","PINK"};
	
	public SpriteClient() throws RemoteException{
		//jpanelSize = getSizeOfJPanel();
		bouncingSprite();
		addMouseListener(new Mouse());
		System.out.println("Test----After addMouseListener----");
	}

	//method for creating a new ball
	private void createNewSprite (MouseEvent event) throws RemoteException{
		System.out.println("Test----createNewSprite----");
		x = event.getX();
		y = event.getY();
		System.out.println("Test----After event.getY();----");
		
		switch(colorRandom[random.nextInt(colorRandom.length)]){
		case "RED":   color = Color.RED;
		case "GREEN": color = Color.GREEN;
		case "BLUE":  color = Color.BLUE;
		case "YELLOW":color = Color.YELLOW;
		case "ORANGE":color = Color.ORANGE;
		case "BLACK": color = Color.BLACK;
		case "PINK":  color = Color.PINK;
		}

		System.out.println("Test----For random Color----");
		si.createSprite(color, x, y);
//		sprites = getListOfSprites();
//		for(Sprite sprite: sprites){
//			System.out.println("Test----For sprites----");
//		}
		System.out.println("New Sprite created");
	}

    public void bouncingSprite() {
        frame = new JFrame("Bouncing Sprites");
        frame.setSize(jpanelSize, jpanelSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
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
    	String myHostName = "localhost";
    	try {
			InetAddress myHost = Inet4Address.getLocalHost();
			myHostName = myHost.getHostName();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	int port = 1099;
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
			System.out.println("Test----getSizeOfJPanel----"+ jpanelSize);
			SpriteClient sc = new SpriteClient();
			
            sc.animate();
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
				System.out.println("Test----Mouse----");
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
		List<Sprite> myList;
		try {
			myList = (List<Sprite>) si.getListOfSprites();
			//System.out.println("the size is " + myList.size());
			for (Sprite s: myList){
			s.draw(g);
		}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
