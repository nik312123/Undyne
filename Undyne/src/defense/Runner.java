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
import java.util.ArrayList;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import nikunj.classes.Sound;

public class Runner extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private static char dir = 'u';

	private static int delay = 10;
	public static int angle = 0;

	static int count = 0;
	static int gifCount = 0;

	protected Timer timer;

	BufferedImage gif;
	BufferedImage heart;

	static boolean runsGif = false;

	Attack a1 = new Attack(new LinkedList<Arrow>(), 2);
	Player p = new Player();

	public Runner(String s) {
		JFrame frame = new JFrame(s);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Runner bp = new Runner();
		frame.add(bp);
		frame.addKeyListener(this);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}

	public static void main(String args[]) throws IOException, UnsupportedAudioFileException, InterruptedException {
		@SuppressWarnings("unused")
		Runner a = new Runner("Game");
		ArrayList<Sound> spiritOfJustice = new ArrayList<Sound>();
		for (int i = 1; i <= 12; ++i) {
			spiritOfJustice.add(new Sound("Audio/SOJ" + i + ".wav", false));
		}
		Sound.playGroup(spiritOfJustice, true);
	}

	public Runner() {
		timer = new Timer(delay, this);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBG(g);
		drawSqu(g);
		drawCircle(g);
		drawHeart(g);
		p.shield(g, dir);
		gif(g);
		try {
			a1.spawnArrows(g, p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void gif(Graphics g) {
		try {
			gif = ImageIO.read(new File("images/gif/frame" + count + ".png"));
			if (count == 31) {
				count = 1;
			} else if (gifCount % 3 == 0) {
				++count;
			}
			++gifCount;
		} catch (IOException e) {
			e.printStackTrace();
		}
		Graphics2D g2d = (Graphics2D) g.create();
		float opacity = 0.5f;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g2d.drawImage(gif, 189, 10, null);
		g2d.dispose();
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
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public void drawHeart(Graphics g) {
		try {
			heart = ImageIO.read(new File("images/heart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int width = heart.getWidth();
		int height = heart.getHeight();
		g.drawImage(heart, getWidth() / 2 - (width / 2) + 1, getHeight() / 2 - height / 2, null);
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
