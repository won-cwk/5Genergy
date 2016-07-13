

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.awt.Color;

import java.rmi.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class SpriteServer implements SpriteInterface{

	final static int JPANEL_SIZE = 500;
	private SpriteServer spriteServer;
	private ArrayList<Sprite> spritesVarList = new ArrayList<Sprite>();
	private Sprite sprite;
	private Color color; // Color of a Sprite
	private int x; //Position X
	private int y; //Position Y

	final String[] colorRandom = {"RED","GREEN","BLUE","YELLOW","ORANGE","BLACK","PINK"};

	public final static Random random = new Random();

	//ServiceRegistryBuilder sRBuilder;
	SessionFactory factory;
	Configuration config = new Configuration();
	Session s;


	public SpriteServer (){

		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();

		try {
			MetadataImplementor meta = 
					(MetadataImplementor) new MetadataSources( registry ).addAnnotatedClass(Sprite.class).buildMetadata();
			new SchemaExport(meta).create(true, true);
			factory = meta.buildSessionFactory();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}

	}


	@Override
	public void createSprite(Color color, int x, int y)//Color of a sprite, Position X, Position Y
	{	
		this.color = color;
		this.x = x;
		this.y = y;
		sprite = new Sprite(color, x, y, JPANEL_SIZE);
		spritesVarList.add(sprite); // Add Sprite() to Array<Sprite>
		s = factory.getCurrentSession();
		s.beginTransaction();
		sprite.setColor(color);
		sprite.setPositionX(x);
		sprite.setPositionY(y);
		s.save(sprite);
		s.getTransaction().commit();
	}

	@Override
	public int getSizeOfJPanel(){
		return JPANEL_SIZE;    	
	}

	@Override
	public ArrayList<Sprite> getListOfSprites(){
		for(Sprite sprite: spritesVarList){
			sprite.move();  	     
			s = factory.getCurrentSession();
			s.beginTransaction();
			s.update(sprite);
			s.getTransaction().commit();
		}
		return spritesVarList;
	}
	
	@Override
	public Color getColor(){
		return color;
	}
	
	public void createdClientColor(){
		Random random = new Random();
		int index = random.nextInt(colorRandom.length);
		switch(colorRandom[index]){
			case "RED":   color = Color.RED;
			break;
			case "GREEN": color = Color.GREEN;
			break;
			case "BLUE":  color = Color.BLUE;
			break;
			case "YELLOW":color = Color.YELLOW;
			break;
			case "ORANGE":color = Color.ORANGE;
			break;
			case "BLACK": color = Color.BLACK;
			break;
			case "PINK":  color = Color.PINK;
			break;
		}
	}

	public void runServer(){

		try {
			//System.setProperty("java.rmi.server.hostname", "169.254.113.82");
			spriteServer = new SpriteServer();
			// Each client has a unique color
			this.createdClientColor();

			// This server runs the registry on port 8080
			LocateRegistry.createRegistry(1099);
			System.out.println( "Registry created" );

			// The calculator object will be reached at this server on port 8081
			UnicastRemoteObject.exportObject(spriteServer,8081);//(c,0) => It doesn't matter any post
			System.out.println( "Exported" );

			// A client will need to look up the calculator remote object at this machine's
			// address, using the service name "Sprite"
			Naming.rebind("rmi://localhost/Sprites", spriteServer);
		} catch (Exception e) {
			System.out.println("Trouble: " + e);
		}
	}

	public static void main(String args[]) {
		new SpriteServer().runServer();
	}
}
