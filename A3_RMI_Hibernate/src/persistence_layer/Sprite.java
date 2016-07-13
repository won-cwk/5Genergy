package persistence_layer;
//%W%	%G%

import java.util.Calendar;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Sprite implements Runnable, Serializable {


	public final static Random random = new Random();

	public Color color;
	public int x;
	public int y;
	public final int SIZE = 10;
	public final int panelSize;
	private int dx;
	private int dy;
	
	
	public Sprite(Color color, int x, int y, int size){
		this.color = color;
		panelSize = size;
		this.x = x;
		this.y = y;
		dx = random.nextInt(10) - 5;
		dy = random.nextInt(10) - 5;
	}

	public void draw(Graphics g){
		g.setColor(color);
		g.fillOval(x, y, SIZE, SIZE);
		//animate();
	}
	
	public void move(){
		System.out.println("I am moving");
		// check for bounce and make the ball bounce if necessary
		//
		if (x < 0 && dx < 0){
			//bounce off the left wall 
			x = 0;
			dx = -dx;
		}
		if (y < 0 && dy < 0){
			//bounce off the top wall
			y = 0;
			dy = -dy;
		}
		if (x > panelSize - SIZE && dx > 0){
			//bounce off the right wall
			x = panelSize - SIZE;
			dx = - dx;
		}       
		if (y > panelSize - SIZE && dy > 0){
			//bounce off the bottom wall
			y = panelSize - SIZE;
			dy = -dy;
		}

		//make the ball move
		x += dx;
		y += dy;
	}

	/**
	 * run() method calls a move() method and sleep(40) that makes 25 frames per second
	 */
	public void run(){			
		move();
		try {
			Thread.sleep(40);  // wake up roughly 25 frames per second .... 40miliseconds
		}
		catch ( InterruptedException exception ) {
			exception.printStackTrace();
		}		
	}
	

}
