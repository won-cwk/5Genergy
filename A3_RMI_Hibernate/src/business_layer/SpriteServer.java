package business_layer;

import java.util.ArrayList;
import java.util.Random;

import persistence_layer.Sprite;

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
    public void createSprite(Color color, int x, int y)// RGB integer value, Position X, Position Y
    {	
    	this.color = color;
    	this.x = x;
    	this.y = y;
    	System.out.println("about to add new sprite to list of size: "  + spritesVarList.size());
    	sprite = new Sprite(color, x, y, JPANEL_SIZE);
    	spritesVarList.add(sprite); // Add Sprite() to Array<Sprite>
    }

	@Override
    public int getSizeOfJPanel(){
    	return JPANEL_SIZE;    	
    }
	
	@Override
    public ArrayList<Sprite> getListOfSprites(){
		s = factory.getCurrentSession();
    	for(Sprite sprite: spritesVarList){
    		sprite.run();
    		s.beginTransaction();
    		s.update(sprite);
	    	s.getTransaction().commit();
    	}
    	return spritesVarList;
    }
    
    public void runServer(){

        try {
          System.setProperty("java.rmi.server.hostname", "localhost");
          spriteServer = new SpriteServer();
          
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
