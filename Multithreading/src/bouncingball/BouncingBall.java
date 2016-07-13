package bouncingball;
//%W%	%G%
/*
 This app bounces a blue ball inside a JPanel.  The ball is created and begins
 moving with a mousePressed event.  When the ball hits the edge of
 the JPanel, it bounces off the edge and continues in the opposite
 direction.  
*/
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BouncingBall {
	
    private JFrame frame;
    private BallPanel panel = new BallPanel();
    private Ball ball;
    
    // Set the value of a JFrame 500 * 500.
    public BouncingBall() {
        frame = new JFrame("Bouncing Ball");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new BouncingBall().panel.animate();
    }
}
