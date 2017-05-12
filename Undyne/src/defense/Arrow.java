package defense;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import java.io.IOException;

/*
 * Composes an arrow to be implemented in the Attack class
 */
public class Arrow {
    /*
     * This determines the speed the arrow should go at between 1 – 100
     */
    private int speed;
    /*
     * This determines whether or not the arrow will be reversed
     */
    private boolean reverse;
    /*
     * This determines the direction the arrow should come from
     */
    private char direction;
    /*
     * These are the coordinates of the arrow
     */
    private int x, y;
    /*
     * This is the delay after the arrow for the next arrow
     */
    private int delay;
    
    static Player p;
    
    private boolean isOne = true;
    private boolean isSlow;
    
    public Arrow(int speed, boolean reverse, char direction, int delay, boolean isSlow) {
        this.speed = speed;
        this.reverse = reverse;
        this.direction = direction;
        this.delay = delay;
        this.isSlow = isSlow;
        setCoordinates(direction);
    }
    
    /*
     * Helper method for the constructor setting the original arrow coordinates
     */
    private void setCoordinates(char direction) {
        switch(direction) {
            case 'r':
                x = 11;
                y = 270;
                break;
            case 'l':
                x = 557;
                y = 270;
                break;
            case 'u':
                x = 285;
                y = 545;
                break;
            case 'd':
                x = 285;
                y = 0;
                break;
        }
    }
    
    public void tick() {
        if(speed != 1 || speed == 1 && isOne || !isSlow) {
            switch(direction) {
                case 'l':
                    x -= speed;
                    break;
                case 'r':
                    x += speed;
                    break;
                case 'u':
                    y -= speed;
                    break;
                case 'd':
                    y += speed;
                    break;
            }
        }
        if(isSlow)
            isOne = !isOne;
    }
    
    public void draw(Graphics g, Color c) throws IOException {
        BufferedImage arr;
        if(reverse)
            arr = Runner.reverseArr;
        else if(c.equals(Color.RED))
            arr = Runner.redArr;
        else
            arr = Runner.blueArr;
        int angle = 0;
        switch(direction) {
            case 'r':
                angle = 0;
                break;
            case 'd':
                angle = 90;
                break;
            case 'l':
                angle = 180;
                break;
            case 'u':
                angle = -90;
                break;
        }
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(angle), arr.getMinX() + arr.getWidth()/2, arr.getMinY() + arr.getHeight()/2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        arr = op.filter(arr, null);
        g.drawImage(arr, x + p.getElementPosition(), y + p.getElementPosition(), null);
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public char getDir() {
        return direction;
    }
    
    public int getDelay() {
        return delay;
    }
    
    public int getSpeed() {
        return speed;
    }
}
