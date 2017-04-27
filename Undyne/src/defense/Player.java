package defense;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
	
	private int red = 30;
	
	public char dir = 'u';
	
	BufferedImage shield;
    /*
     * Delay in milliseconds between each movement of the player
     */
    /*
     * Initial player health
     */
    private int playerHealth = 100;
    /*
     * How much the player is currently rotated by
     */
    private int angle = 0;
    
    public Player() {}

    public void damage() {
        playerHealth -= (int) (Math.random() * 9 + 14);
    }
    
    public void shield(Graphics g, char dir){
    	this.dir = dir;
    	shieldDir();
    	drawShield(g);
    }
    
    public void drawShield(Graphics g) {
		try {
			if(red < 25) {
				shield = ImageIO.read(new File("images/shieldh.png"));
				++red;
			}
			else {
				shield = ImageIO.read(new File("images/shield.png"));
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		g.translate(300, 300);
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(angle), shield.getMinX() + shield.getWidth()/2, shield.getMinY() + shield.getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		shield = op.filter(shield, null);
		g.translate(-300, -300);
		g.drawImage(shield, 265, 254, null);
	}
    
    public void shieldDir() {
	    switch (dir) {
            case 'r':
                if(angle == 90)
                    break;
                else if(angle > 90 && angle <= 180)
                    angle -= 15;
                else
                    angle += 15;
                break;
            case 'l':
                if(angle == 270)
                    break;
                else if(angle < 270 && angle >= 180)
                    angle += 15;
                else if(angle >= 0)
                    angle -= 15;
                if(angle < 0)
                    angle = 360 + angle;
                break;
            case 'u':
                if(angle == 0)
                    break;
                else if(angle <= 90 && angle > 0)
                    angle -= 15;
                else
                    angle += 15;
                break;
            case 'd':
                if(angle == 180)
                    break;
                else if(angle <= 270 && angle > 180)
                    angle -= 15;
                else if(angle <= 360 && angle > 270)
                    angle -= 15;
                else
                    angle += 15;
                break;
        }
        if(angle > 360)
            angle = 0;
	}
    
   
    
    public char getDir(){
    	return dir;
    }
    
    public void setRed(int red){
    	this.red = red;
    }

    
}
