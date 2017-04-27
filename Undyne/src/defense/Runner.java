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
    
	public static char dir = 'u';
	public static String hit = "";
	
	public static final char[] DIRS = {'u', 'd', 'r', 'l'};
	private static int delay = 10;
	public static int angle = 0;

	static int count = 0;
	static int gifCount = 0;
	public static int currentDirection = 0;
	public static boolean isGenocide = true;
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
		ArrayList<Sound> mainTheme = new ArrayList<Sound>();
		int max;
		String base;
		if(isGenocide) {
		    max = 16;
		    base = "BATH";
		}
		else {
		    max = 12;
		    base = "SOJ";
		}
		for(int i = 1; i <= max; ++i) {
		    mainTheme.add(new Sound(base + i + ".wav", false));
		}
		Sound.playGroup(mainTheme, true);
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
	public void gif(Graphics g) {
	    int maxCount;
	    int gifChange;
	    String baseName;
	    if(isGenocide) {
	        maxCount = 79;
	        baseName = "undying";
	        gifChange = 4;
	    }
	    else {
	        maxCount = 31;
	        baseName = "frame";
	        gifChange = 3;
	    }
	    try {
	        gif = ImageIO.read(new File("images/gif/" + baseName + count + ".png"));
	        if(isGenocide) {
    	        if(count == maxCount)
    	            count = 1;
    	        else if(gifCount % gifChange == 0)
    	            ++count;
    	        ++gifCount;
    	        if(gifCount == gifChange)
    	            gifCount = 0;
	        }
	        else {
	            if(count == maxCount)
                    count = 1;
                else if(gifCount % gifChange == 0 && (count - 1) % 3 != 0)
                    ++count;
                else if((count - 1) % 3 == 0 && gifCount % 4 == 0)
                    ++count;
                ++gifCount;
                if(gifCount == gifChange && (count - 1) % 3 != 0)
                    gifCount = 0;
                else if((count - 1) % 3 == 0 && gifCount == 4)
                    gifCount = 0;
	        }
	    }
	    catch(IOException e) {
	        e.printStackTrace();
	    }
	    Graphics2D g2d = (Graphics2D) g.create();
	    float opacity = 0.5f;
	    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
	    if(isGenocide)
	        g2d.drawImage(gif, 198, 10, null);
	    else
	        g2d.drawImage(gif, 189, 10, null);
	    g2d.dispose();
	}
	
	public void spawnArrows(Graphics g) throws IOException {
	    a1.tick();
	    if(++counter == 50) {
	        a1.addArrow(new Arrow(2, false, DIRS[(int) (Math.random() * 4)]));
	        if(currentDirection == DIRS.length)
	            currentDirection = 0;
	        counter = 0;
	    }
	    hit = a1.removeArrow(dir);
	    Sound block = null;
	    Sound damage = null;
        try {
            block = new Sound("block.wav", false);
            damage = new Sound("damage.wav", false);
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
	    if(hit.equals("H")) {
	        red = 0;
	        block.play();
	    }
	    else if(hit.equals("D"))
	        damage.play();
	    a1.draw(g);
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
