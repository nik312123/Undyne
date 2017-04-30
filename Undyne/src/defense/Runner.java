package defense;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import nikunj.classes.NewerSound;

public class Runner extends JPanel implements ActionListener, KeyListener {
  private static final long serialVersionUID = 1L;
  
	static char dir = 'u';
  
  static final char[] DIRS = {'u', 'd', 'r', 'l'};
  
  String hit = "";
  
	static int move = 0;
	static int delay = 10;
	static int angle = 0;
	static int breakCount = 0;
	static int breakFrame = 0;
	static int count = 0;
	static int gifCount = 0;
	static int currentDirection = 0;
	static int gameOverCount = 0;
	static int gameOverFrame = 0;
	
	static boolean isGenocide = true;
	static boolean runsGif = false;
	static boolean heartDone = false;
	static boolean gameOverDone = false;
	static boolean firstEnd = true;
	static boolean secondEnd = true;
	
	protected Timer timer;

	BufferedImage gif;
	BufferedImage heart;
	BufferedImage heartBreak;
	BufferedImage gameOver;
	
	private static NewerSound main;
	private static NewerSound gameDone;

	
	Player p = new Player();
	
	Attack a1 = new Attack(new LinkedList<Arrow>(), 2, p);

	public Runner(String s) {
		JFrame frame = new JFrame(s);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Runner bp = new Runner();
		frame.add(bp);
		frame.addKeyListener(this);
		frame.setSize(600, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(600, 600);
		frame.setLocation(dim.width/2 - frame.getSize().width/2, dim.height/2 - frame.getSize().height/2);
		frame.setResizable(false);
		frame.setVisible(true);	
	}

	public static void main(String args[]) throws IOException, UnsupportedAudioFileException, InterruptedException, LineUnavailableException {
		@SuppressWarnings("unused")
        Runner a = new Runner("Game");
		if(isGenocide)
            main = new NewerSound("audio/bath.wav", true);
        else
            main = new NewerSound("audio/soj.wav", true);
	    main.play();
	}

	public Runner() {
		timer = new Timer(delay, this);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(p.getHealth() != 0) {
    		drawBG(g);
    		drawSqu(g);
    		drawCircle(g);
    		drawHeart(g);
    		p.shield(g, dir);
    		gif(g);
    		try {
    		    a1.spawnArrows(g, p);
                p.drawHealth(g);
            }
    		catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
		}
		else {
		    drawBG(g);
		    if(firstEnd) {
		        main.stop();
		        firstEnd = false;
		    }
		    else if(!heartDone) {
		        breakHeart(g);
		    }
		    else if(secondEnd){
		        secondEnd = false;
                gameDone = new NewerSound("audio/dt.wav", true);
                gameDone.play();
		    }
		    else if(!gameOverDone){
		        gameOver(g);
		    }
		    else {
		        drawGameOver(g);
		    }
		}
		try {
        p.drawHealth(g);
    }
    catch (FontFormatException | IOException e) {
        e.printStackTrace();
    }
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
	        g2d.drawImage(gif, 198+p.getElementPosition(), 10+p.getElementPosition(), null);
	    else
	        g2d.drawImage(gif, 189+p.getElementPosition(), 10+p.getElementPosition(), null);
	    g2d.dispose();
	}
	
	public boolean breakHeartException(int breakFrame) {
	    int[] exceptions = {2, 6, 8, 12, 14, 18, 20, 22, 23};
	    for(int exception : exceptions) {
	        if(breakFrame == exception)
	            return true;
	    }
	    return false;
	}
	
	public void makeBreakHeart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = heartBreak.getWidth();
        int height = heartBreak.getHeight();
        g2d.drawImage(heartBreak, getWidth() / 2 - (width / 2) + 11, getHeight() / 2 - height / 2 + 78, null);
        g2d.dispose();
	}
	
	public void breakHeart(Graphics g) {
	    ++breakCount;
	    boolean exception = breakHeartException(breakFrame);
	    try {
	        heartBreak = ImageIO.read(new File("images/gif/heartBreak" + breakFrame + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	    if(breakCount % 4 == 0 && breakCount != 0 && !exception) {
	        ++breakFrame;
    	    if(breakFrame == 25) {
                NewerSound split = new NewerSound("audio/split.wav", false);
                split.play();
    	    }
    	    if(breakFrame == 48)
    	        heartDone = true;
    	    breakCount = 0;
	    }
	    else if(exception) {
	        switch(breakFrame) {
	            case 2:
	            case 12:
	                if(breakCount % 24 == 0) {
	                    ++breakFrame;
	                    breakCount = 0;
	                }
	                break;
	            case 14:
	                if(breakCount % 28 == 0) {
	                    ++breakFrame;
	                    breakCount = 0;
	                }
	                break;
	            default:
	                if(breakCount % 8 == 0) {
	                    ++breakFrame;
	                    if(breakFrame == 9) {
	                        NewerSound broke = new NewerSound("audio/heartBreak.wav", false);
	                        broke.play();
	                    }
	                    breakCount = 0;
	                }
	        }
	    }
	    makeBreakHeart(g);
	}
	
	public void gameOver(Graphics g) {
	    ++gameOverCount;
        try {
            gameOver = ImageIO.read(new File("images/gif/gameOver" + gameOverFrame + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(gameOverCount % 4 == 0 && gameOverCount != 0) {
            ++gameOverFrame;
            switch(gameOverFrame) {
                case 68:
                case 70:
                case 73:
                case 75:
                case 76:
                case 78:
                case 80:
                case 81:
                case 85:
                case 86:
                case 88:
                case 93:
                case 95:
                case 96:
                case 98:
                case 138:
                case 140:
                case 141:
                case 143:
                case 145:
                case 146:
                case 148:
                case 163:
                case 165:
                case 166:
                case 168:
                case 171:
                case 173:
                case 175:
                case 176:
                case 178:
                case 180:
                case 181:
                case 183:
                case 185:
                case 186:
                case 188:
                case 190:
                case 191:
                    try {
                        NewerSound asgore = new NewerSound("audio/asgore.wav", false);
                        asgore.play();
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            if(gameOverFrame == 225)
                gameOverDone = true;
            gameOverCount = 0;
        }
        drawGameOver(g);
	}
	
	public void drawGameOver(Graphics g) {
	    Graphics2D g2d = (Graphics2D) g.create();
        int width = gameOver.getWidth();
        int height = gameOver.getHeight();
        g2d.drawImage(gameOver, getWidth() / 2 - (width / 2) + 1, getHeight() / 2 - height / 2, null);
        g2d.dispose();
	}

	public void drawSqu(Graphics g) {
		int size = 80;
		Color translucentWhite = new Color(255, 255, 255, 200);
		g.setColor(translucentWhite);
		g.drawRect(getWidth() / 2 - size / 2+p.getElementPosition(), getHeight() / 2 - size / 2+p.getElementPosition(), size, size);
		while (size > 73) {
			--size;
			g.drawRect(getWidth() / 2 - size / 2+p.getElementPosition(), getHeight() / 2 - size / 2+p.getElementPosition(), size, size);
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
		g.drawImage(heart, getWidth() / 2 - (width / 2) + 1+p.getElementPosition(), getHeight() / 2 - height / 2+p.getElementPosition(), null);
	}

	public void drawCircle(Graphics g) {
		Color clr = new Color(0, 255, 0);
		g.setColor(clr);
		g.drawOval(getWidth() / 2 - 25+p.getElementPosition(), getHeight() / 2 - 25+p.getElementPosition(), 50, 50);
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
