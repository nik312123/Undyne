package defense;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Runner extends JPanel implements ActionListener, KeyListener {
	public static String dir = "l"; // Could you tell mw where it shows the
									// shield?
	private static final long serialVersionUID = 1L;
	Random random = new Random();
	private static int delay = 10;
	protected Timer timer;
	public static String hit = "";
	int x = 0;
	BufferedImage heart;
	BufferedImage shield;
	int hitCount = 0;
	public static int angle = 0;
	public static int red = 30;

	public static boolean right, left, up, down = false;

	
	public Runner(String s) {
		JFrame frame = new JFrame(s);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Runner bp = new Runner();
		// new Game();
		frame.add(bp);
		frame.addKeyListener(this);
		frame.setSize(600, 600);

		// Color r = new Color(51,0,0);
		// frame.setBackground(r);
		frame.setVisible(true);

	}

	public static void main(String args[]) {

		Runner a = new Runner("Game");

	}

	public Runner() {
		timer = new Timer(delay, this);
		timer.start();

	}

	@Override
	public void paintComponent(Graphics g) { 
		super.paintComponent(g); 

		shieldDir();
		drawBG(g);

		

		 drawSqu(g);
		drawCircle(g);
		drawHeart(g);
		drawShield(g);
		
	}


	public void shieldDir() {

		if (right) { 
			if (angle == 90)
				right = false;
			else if (angle > 90 && angle <= 180) {
				angle -= 15;

			} else
				angle += 15;

			if (angle > 360)
				angle = 0;
		}

		if (left) { // done

			if (angle == 270)
				left = false;
			else if (angle < 270 && angle >= 180) {
				angle += 15;

			} else if (angle >= 0) {
				angle -= 15;
				if (angle < 0) {
					angle = 360 + angle;
				}

			}
			
			if (angle > 360)
				angle = 0;
		}

		if (up) { // done
			if (angle == 0)
				up = false;
			else if (angle <= 90 && angle > 0) {
				angle -= 15;

			} else
				angle += 15;
			if (angle > 360)
				angle = 0;
		}

		if (down) { // done
			if (angle == 180)
				down = false;
			else if (angle <= 270 && angle > 180) {
				angle -= 15;

			} else if (angle <= 360 && angle > 270) {
				angle -= 15;
			}

			else {
				angle += 15;
			}
			if (angle > 360)
				angle = 0;
		}

		

	}

	public void drawSqu(Graphics g) {
		int size = 80;
		Color clr = new Color(255, 255, 255, 200);
		// if(hit.equals("H"))
		// clr = new Color(255,0,0);
		g.setColor(clr);
		g.drawRect(getWidth() / 2 - size / 2, getHeight() / 2 - size / 2, size, size);

		while (size > 73) {
			size = size - 1;
			g.drawRect(getWidth() / 2 - size / 2, getHeight() / 2 - size / 2, size, size);
		}

	}

	public void drawBG(Graphics g) {

		Color clr = new Color(20, 20, 20);

		g.setColor(clr);
		g.fillRect(0, 0, getWidth(), getHeight());

	}

	public void drawHeart(Graphics g) {

		try {
			heart = ImageIO.read(new File("/Users/64009455/git/Undyne/Undyne/heart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int width = heart.getWidth();
		int height = heart.getHeight();

		g.drawImage(heart,
				(getWidth() / 2 - (width / 2) + 1) /* + random.nextInt(10) -5 */ ,
				(getHeight() / 2 - height / 2) /* + random.nextInt(10) -5 */, null);

	}

	public void drawShield(Graphics g) {

		try {
			if (red < 30) {
				shield = ImageIO.read(new File("/Users/64009455/Documents/shield" + "h" + ".png"));
				red++;
			} else {
				shield = ImageIO.read(new File("/Users/64009455/Documents/shield" + "" + ".png"));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.translate(300, 300);
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(angle), shield.getMinX() + shield.getWidth() / 2, 
				shield.getMinY() + shield.getHeight() / 2);

		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		shield = op.filter(shield, null);

		g.translate(-300, -300);

		g.drawImage(shield, 265, 254, null); // -- at 0

	}

	public void drawCircle(Graphics g) {
		Color clr = new Color(0, 255, 0);
		g.setColor(clr);
		g.drawOval(getWidth() / 2 - 25, getHeight() / 2 - 25, 50, 50);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			right = false;
			left = false;
			up = true;
			down = false;

		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {

			right = false;
			left = false;
			up = false;
			down = true;

		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

			right = true;
			left = false;
			up = false;
			down = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
			right = false;
			up = false;
			down = false;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	
}
