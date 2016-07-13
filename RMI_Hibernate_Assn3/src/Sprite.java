import java.util.Calendar;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Sprite class makes a sprite move and treat a table that name is "Sprite"
 * The Sprite table has Color, Position X, Position Y of a Sprite
 * @author Won Kyoung Chung
 * Created Date : 11 Nove 2015
 */

@Entity
//@Table(name="sprite")
public class Sprite implements Serializable {

   /**
    * It is used to make a random integer number for changing a positon of a sprite
    */
	public final static Random random = new Random();
	/**
	 * It is saved in the Sprite table as a primary key.
	 */
	private int spriteID;
	/**
	 * Color of a Sprite
	 */
	public Color color;
	/**
	 * Position X of a Sprite
	 */
	public int x;
	/**
	 * Position Y 
	 */
	public int y;
	public final int SIZE = 10;
	private int panelSize;
	private int dx;
	private int dy;

	private Calendar spriteDate;
	private String client;
	
	public Sprite(Color color, int x, int y, int panelSize){
		this.color = color;
		this.panelSize = panelSize;
		this.x = x;
		this.y = y;
		dx = random.nextInt(10)-5;
		dy = random.nextInt(10)-5;
	}

	public void draw(Graphics g){
		g.setColor(color);
		g.fillOval(x, y, SIZE, SIZE);
	}
	
	public void move(){
		// check for bounce and make the ball bounce if necessary
		
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
			x = panelSize - SIZE ;
			dx = -dx;
		}       
		if (y > panelSize - SIZE && dy > 0){
			//bounce off the bottom wall
			y = panelSize - SIZE ;
			dy = -dy;
		}


		//make the ball move
		x += dx;
		y += dy;
	}


	@Id
	@GeneratedValue
	public int getSpriteID() {
		return spriteID;
	}
	public void setSpriteID(int spriteID) {
		this.spriteID = spriteID;
	}

	@Column
	public Color getColor(){
		return color;
	}
	public void setColor(Color color){
		this.color = color;
	}
	
	@Column
	public int getPositionX(){
		return x;
	}
	public void setPositionX(int x){
		this.x = x;
	}

	@Column
	public int getPositionY(){
		return y;
	}
	public void setPositionY(int y){
		this.y = y;
	}
	

}
