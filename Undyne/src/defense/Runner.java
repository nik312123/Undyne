package defense;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Runner extends JPanel implements ActionListener, KeyListener {
	public static char dir = 'u';
	private static final long serialVersionUID = 1L;
	private static int delay = 10;
	public static int shieldDelay = 0;
	static int count = 0;
	protected Timer timer;
	BufferedImage gif;
	BufferedImage heart;
	BufferedImage shield;
	public static int angle = 0;
	public static int red = 30;
	static boolean runGif = false;
	static int countsCount = 0;
	Attack c = new Attack();
	int counter = 0;

	public Runner(String s) {
		JFrame frame = new JFrame(s);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Runner bp = new Runner();
		frame.add(bp);
		frame.addKeyListener(this);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}

	public static void main(String args[]) {
		@SuppressWarnings("unused")
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
		drawCircle(g);
		drawHeart(g);
		drawShield(g);
		gif(g);
		drawSqu(g);
		try {
			spawnArrows(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
	

	}

	public void gif(Graphics g) {

		try {
			
			gif = ImageIO.read(new File("frame"+count+".png"));
			
			if (count == 31){
				count = 1;
			}
			else if(countsCount%3==0){
				count++;
			}
			countsCount++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Graphics2D g2d = (Graphics2D)g.create();
		float opacity = 0.5f;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g2d.drawImage(gif,
				189 /* + random.nextInt(10) -5 */ ,
				10 /* + random.nextInt(10) -5 */, null);
	    g2d.dispose();

	}
	
	public void spawnArrows(Graphics g) throws IOException {
		c.tick();
		if (counter == 0)
			c.addArrow(new Arrow(2, false, 'l'));
		if (counter++ > 30) {
			c.addArrow(new Arrow(2, false, 'r'));
			//c.addArrow(new Arrow(2, true, 'u'));
			c.addArrow(new Arrow(2, false, 'u'));

			c.addArrow(new Arrow(2, false, 'd'));
			c.addArrow(new Arrow(2, false, 'l'));





			counter = 1;
		}
		c.removeArrow(dir);

		c.draw(g);
	}

	public void shieldDir() {
		if (shieldDelay > 10) { 
			switch (dir) {
			case 'r':
				if (angle == 90) {
					dir = 't';
					shieldDelay = 0;

				} else if (angle > 90 && angle <= 180)
					angle -= 15;
				else
					angle += 15;
				break;
			case 'l':
				if (angle == 270) {
					dir = 't';
					shieldDelay = 0;

				} else if (angle < 270 && angle >= 180)
					angle += 15;
				else if (angle >= 0)
					angle -= 15;
				if (angle < 0)
					angle = 360 + angle;
				break;
			case 'u':
				if (angle == 0) {
					dir = 't';
					shieldDelay = 0;

				} else if (angle <= 90 && angle > 0)
					angle -= 15;
				else
					angle += 15;
				break;
			case 'd':
				if (angle == 180) {
					dir = 't';
					shieldDelay = 0;

				} else if (angle <= 270 && angle > 180)
					angle -= 15;
				else if (angle <= 360 && angle > 270)
					angle -= 15;
				else
					angle += 15;
				break;
			}
			if (angle > 360)
				angle = 0;

		} else
			shieldDelay++;
	}

	public void drawSqu(Graphics g) {
		int size = 80;
		Color translucentWhite = new Color(255, 255, 255, 200);
		g.setColor(translucentWhite);
		g.drawRect(getWidth() / 2 - size / 2, getHeight() / 2 - size / 2, size, size);
		while (size > 73) {
			--size;
			g.drawRect(getWidth() / 2 - size / 2, getHeight() / 2 - size / 2, size, size);
		}
	}

	public void drawBG(Graphics g) {
		Color almostBlack = new Color(0, 0, 0);
		g.setColor(almostBlack);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public void drawHeart(Graphics g) {
		try {
			heart = ImageIO.read(new File("heart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int width = heart.getWidth();
		int height = heart.getHeight();
		g.drawImage(heart, getWidth() / 2 - (width / 2) + 1, getHeight() / 2 - height / 2, null);
	}

	public void drawShield(Graphics g) {
		try {
			if (red < 30) {
				shield = ImageIO.read(new File("shieldh.png"));
				++red;
			} else {
				shield = ImageIO.read(new File("shield.png"));
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
		g.drawImage(shield, 265, 254, null); // At 0
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
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			dir = 'u';
			break;
		case KeyEvent.VK_DOWN:
			dir = 'd';
			break;
		case KeyEvent.VK_RIGHT:
			dir = 'r';
			break;
		case KeyEvent.VK_LEFT:
			dir = 'l';
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
