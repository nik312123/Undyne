package defense;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Player {

	private int red = 30;
	
	public char dir = 'u';

	boolean hit = false;
	int time = 75; 
	int timeoutCounter = time;
	
	BufferedImage shield;
	
	BufferedImage[] shields = new BufferedImage[2];
	
	int elementPosition = 0;
	
	Font font;
	
	/*
	 * Initial player health
	 */
	private int health = 60;
	/*
	 * How much the player is currently rotated by
	 */
	private int angle = 0;

	public Player() {
	    try {
            shields[0] = ImageIO.read(new File("images/shieldh.png"));
            shields[1] = ImageIO.read(new File("images/shield.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
	    
	    URL fontUrl;
        try {
            fontUrl = new URL("file:font/undyne.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(25.0f);
        }
        catch(MalformedURLException | FontFormatException | IOException e) {
            e1.printStackTrace();
        }
       
	}

	public void damage() {
		health -= (int) (Math.random() * 3 + 12);
		if(health < 0)
		    health = 0;
	}
	
	public void drawHealth(Graphics g) throws FontFormatException, IOException{
	        g.setColor(Color.RED);
	        g.fillRect(430, 530, 70, 20);
	        g.setColor(Color.YELLOW);
            g.fillRect(430, 530, (int) (70 * ((double) health/60)), 20);
	       
	        Graphics2D g3 = (Graphics2D) g;
	        g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g3.setFont(font);
	        g3.setColor(Color.WHITE);
	        g3.drawString("HP", 390, 548);
	        
	        Graphics2D g4 = (Graphics2D) g;
            g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g4.setFont(font);
            g4.setColor(Color.WHITE);
            g4.drawString(health + "/60", 513, 547);

	    }

	public void shield(Graphics g, char dir) {
		this.dir = dir;
		shieldDir();
		drawShield(g);
	}

	public void drawShield(Graphics g) {
		if (red < 25) {
			shield = shields[0];
			++red;
		} else {
			shield = shields[1];
		}
		g.translate(300, 300);
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(angle), shield.getMinX() + 71/2,
				shield.getMinY() + 71/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		shield = op.filter(shield, null);
		g.translate(-300, -300);
		g.drawImage(shield, 265+getElementPosition(), 254+getElementPosition(), null);
	}

	public void shieldDir() {
		switch (dir) {
		case 'r':
			if (angle == 90)
				break;
			else if (angle > 90 && angle <= 180)
				angle -= 15;
			else
				angle += 15;
			break;
		case 'l':
			if (angle == 270)
				break;
			else if (angle < 270 && angle >= 180)
				angle += 15;
			else if (angle >= 0)
				angle -= 15;
			if (angle < 0)
				angle = 360 + angle;
			break;
		case 'u':
			if (angle == 0)
				break;
			else if (angle <= 90 && angle > 0)
				angle -= 15;
			else
				angle += 15;
			break;
		case 'd':
			if (angle == 180)
				break;
			else if (angle <= 270 && angle > 180)
				angle -= 15;
			else if (angle <= 360 && angle > 270)
				angle -= 15;
			else
				angle += 15;
			break;
		}
		if (angle > 360)
			angle = 0;
	}

	public char getDir() {
		return dir;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getHealth(){
	    return health;
	}
	 
    public int getElementPosition(){
        return elementPosition;
    }
    
    public void setElementPosition(int x){
        elementPosition = x;
    }
    
    public void setHit(boolean hit){
        this.hit = hit;
    }
    
    public boolean getHit(){
        return hit;
    }
    
    public int getTimeoutCounter(){
        return timeoutCounter;
    }
    
    public void resetTimeoutCounter(){
        timeoutCounter = time;
    }
    
    public void decreaseCounter(){
        --timeoutCounter;
    }
}
