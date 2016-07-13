package bouncingball;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
/**
 * BallPanel.java
 * This class counts ball inside and draw Jpanel, Rectangle and balls
 * @author WonKyoung Chung
 * Date : 2nd Oct 2015
 */
public class BallPanel extends JPanel{
	/**
	 * When clicked a ball on the Jpanel, ball is added to the Arraylist<Ball>
	 */
	ArrayList<Ball> ballList = new ArrayList<Ball>();
	private int x; // Ball's x codination
	private int y; // Ball's y codination
	private int w; // 
	private int h;
	private int numInRoom = 0;
	
	public BallPanel(){
		addMouseListener(new Mouse());
	}
	
	//method for creating a new ball
	private void newBall (MouseEvent event){
		Ball ball = new Ball(this);
    	Thread myThread = new Thread(ball);
    	myThread.start();
		ballList.add(ball);
		System.out.println("New ball created");
	}
	/**
	 * animate method repaint every moment of ball is moving and makes 25frames/s
	 */
	public void animate(){
	    while (true){

	    	//System.out.println("balls in room = " + numInRoom);
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
	/**
	 * Whenever occure a mouseevent,Mouse method call newBall(event)
	 *
	 */
	private class Mouse extends MouseAdapter {
		@Override
	    public void mousePressed( final MouseEvent event ){
	        newBall(event);
	    }
	}
	//When a ball goes inside, If there are 2 and more balls in the recktagle, Wait.
	public synchronized void consume() throws InterruptedException{
		while (numInRoom >= 2){
				wait();
		}
			numInRoom++;
			// realistic code would remove data from buffer
			notifyAll();
	}
	//When a ball goes Outside, If there isn't or one ball in the box, 
	//  Wait, the ball is unable to goes out.
	public synchronized void produce() throws InterruptedException{

		while (numInRoom < 2) {
			wait();
		}
		numInRoom--;
		// realistic code would put data in buffer
		notifyAll();
	}

	/**
	 * @param ball_x Ball's x coordinate
	 * @param ball_y Ball's y coordinate
	 * @return boolean whether a ball is inside or outside
	 */
	public boolean now_inside(int ball_x, int ball_y){
		// To compare Ball postion and the Rectangle boundary
		//System.out.println(" x =" + x + " y = " + y +" w = " + w + "  hights = " + h) ;
		//System.out.println(" ball_x =" + ball_x + " ball_y = " + ball_y);
		return ((ball_x > x) && (ball_x < (x + w))) && ((ball_y > y) && (ball_y < (y + h)));
	}
	
	/**
	 * As the paint Component, draw the rectangle and balls
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		x = this.getWidth()  / 10; // The x coordinate of Rectangle
		y = this.getHeight() / 10; // The y coordinate of Rectangle
		w = this.getWidth()  / 10 * 8;// Width of Rectangle(80% size of JFram(500))
		h = this.getHeight() / 10 * 8;// Height of Rectangle(80% size of JFram(500))
		g.drawRect(x,y,w,h);//Draw the Rectangle
		for (Ball b: ballList){
			b.draw(g);
		}// Draw clicked balls
	}
}
