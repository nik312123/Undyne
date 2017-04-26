package defense;

import java.awt.Color;
import java.awt.Graphics;

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
     * Color of the arrow
     */
    private Color arrColor;
    
    public Arrow(int speed, boolean reverse, char direction) {
        this.speed = speed;
        this.reverse = reverse;
        this.direction = direction;
        setCoordinates(direction);
    }
    
    /*
     * Helper method for the constructor setting the arrow coordinates
     */
    private void setCoordinates(char direction) {
        switch(direction) {
            case 'r':
                x = 590;
                y = 300;
                break;
            case 'l':
                x = 0;
                y = 300;
                break;
            case 'u':
                x = 300;
                y = 590;
                break;
            case 'd':
                x = 300;
                y = 0;
                break;
        }
    }
    
    public void tick() {
        switch(direction) {
            case 'l':
                x += speed;
                break;
            case 'r':
                x -= speed;
                break;
            case 'u':
                y -= speed;
                break;
            case 'd':
                y += speed;
                break;
        }
    }
    
    public void draw(Graphics g, Color c) {
        g.setColor(c);
        g.fillRect(x, y, 10, 10);
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
    
}
