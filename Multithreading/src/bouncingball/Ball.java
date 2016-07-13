package bouncingball;
//%W%	%G%
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class Ball implements Runnable{

	public final static Random random = new Random();

	final static int SIZE = 10;
	final static int MAX_SPEED = 5;

	BallPanel panel;

	private int x;
	private int y;

	private int dx;
	private int dy;
	private Color color = Color.BLUE;

	private int rect_x;
	private int rect_y;
	private int rect_w;
	private int rect_h;
	
	private boolean preInside = false;

	public Ball (BallPanel panel)
	{
		this.panel = panel;
		x = random.nextInt(panel.getWidth());
		y = random.nextInt(panel.getHeight());
		dx = random.nextInt(2*MAX_SPEED) - MAX_SPEED;
		dy = random.nextInt(2*MAX_SPEED) - MAX_SPEED;

		rect_x = panel.getWidth()  / 10;
		rect_y = panel.getHeight() / 10; 
		rect_w = panel.getWidth()  / 10 * 8;
		rect_h = panel.getHeight() / 10 * 8;

		// All balls start to run in the ourside boundary.
		// It makes ball to go to the same y(cliced) and the rect_x value on the rectacle line as 
		if ((x > rect_x) && (x < (rect_x + rect_w)) && (y > rect_y) && (y < (rect_y + rect_h))){
			x = rect_x; 
		}

	}

	public void draw(Graphics g){
		g.setColor(color);
		g.fillOval(x, y, SIZE, SIZE);
	}

	public void move(){

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
		if (x > panel.getWidth() - SIZE && dx > 0){
			//bounce off the right wall
			x = panel.getWidth() - SIZE;
			dx = - dx;
		}       
		if (y > panel.getHeight() - SIZE && dy > 0){
			//bounce off the bottom wall
			y = panel.getHeight() - SIZE;
			dy = -dy;
		}
		
		//make the ball move
		x += dx;
		y += dy;
		
		if (panel.now_inside(x,y) && !preInside){
			try {
				//goes inside
				panel.consume();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}else if(!panel.now_inside(x,y) && preInside){
			try {
				//goes outside. 
				panel.produce();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Save a state of now position whether inside or outside as a boolean(True, False)	
		preInside = panel.now_inside(x,y);

	}
	/**
	 * run() mothod calls a move() method and sleep(40) that makes 25 frames per second
	 */
	public void run(){
		while(true){
			
			move();
			try {
				Thread.sleep(40);  // wake up roughly 25 frames per second .... 40miliseconds
			}
			catch ( InterruptedException exception ) {
				exception.printStackTrace();
			}	
		}		
	}
}
