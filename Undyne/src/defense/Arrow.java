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
    private int radius = 0;
    
    static Player p;
    
    private boolean inside = false;
    private boolean isOne = true;
    private boolean isSlow;
    private boolean switchDir = false;
    private boolean directionNotSwitched;
    
    public Arrow(int speed, boolean reverse, char direction, int delay, boolean isSlow) {
        this.speed = speed;
        this.reverse = reverse;
        directionNotSwitched = reverse;
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
                if(reverse) {
                    x = 17 + 11;
                    y = 270 + 11;
                }
                else {
                    x = 11;
                    y = 270 + 11;
                }
                break;
            case 'l':
                if(reverse) {
                    x = 557 - 9 - 11;
                    y = 270 + 11;
                }
                else {
                    x = 557;
                    y = 270 + 11;
                }
                break;
            case 'u':
                if(reverse) {
                    x = 285;
                    y = 545 - 8;
                }
                else {
                    x = 285;
                    y = 545 + 11;
                }
                break;
            case 'd':
                if(reverse) {
                    x = 285;
                    y = 0 + 11 + 8 + 11;
                }
                else {
                    x = 285;
                    y = 0 + 11;
                }
                break;
        }
    }
    
    public void switchDir() {
        switchDir = true;
        if(radius == 0) {
            switch(direction) {
                case 'l':
                    radius = x - 300;
                    break;
                case 'r':
                    radius = 300 - x;
                    break;
                case 'u':
                    radius = y - 300;
                    break;
                case 'd':
                    radius = 300 - y;
                    break;
            }
            speed *= 3;
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
        if(switchDir && directionNotSwitched) {
            switch(direction) {
                case 'l':
                    if(Math.pow(x - 300 - (radius - 72), 2) < Math.pow(72, 2) && !(x < 300 && y == 281))
                        y = 281 - (int) Math.sqrt(Math.pow(72, 2) - Math.pow(x - 300 - (radius - 72), 2));
                    else {
                        y = 281;
                        directionNotSwitched = false;
                        direction = 'r';
                    }
                    break;
                case 'r':
                    if(Math.pow(300 - x - (radius - 72), 2) < Math.pow(radius, 2) && !(x > 300 && y == 281)) {
                        y = 281 + (int) Math.sqrt(Math.pow(72, 2) - Math.pow(300 - x - (radius - 72), 2));
                    }
                    else {
                        x -= 5;
                        y = 281;
                        directionNotSwitched = false;
                        direction = 'l';
                    }
                    break;
                case 'u':
                    if(Math.pow(y - 300 - (radius - 72), 2) < Math.pow(72, 2) && !(y < 300 && x == 285))
                        x = 285 + (int) Math.sqrt(Math.pow(72, 2) - Math.pow(y - 300 - (radius - 72), 2));
                    else {
                        y += 9;
                        x = 285;
                        directionNotSwitched = false;
                        direction = 'd';
                    }
                    break;
                case 'd':
                    if(Math.pow(300 - y - (radius - 72), 2) < Math.pow(72, 2) && !(y > 300 && x == 285))
                        x = 285 - (int) Math.sqrt(Math.pow(72, 2) - Math.pow(300 - y - (radius - 72), 2));
                    else {
                        x = 285;
                        directionNotSwitched = false;
                        direction = 'u';
                    }
                    break;
            }
        }
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
                if(!reverse || reverse && directionNotSwitched)
                    angle = 0;
                else
                    angle = 180;
                break;
            case 'd':
                if(!reverse || reverse && directionNotSwitched)
                    angle = 90;
                else
                    angle = -90;
                break;
            case 'l':
                if(!reverse || reverse && directionNotSwitched)
                    angle = 180;
                else
                    angle = 0;
                break;
            case 'u':
                if(!reverse || reverse && directionNotSwitched)
                    angle = -90;
                else
                    angle = 90;
                break;
        }
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(angle), arr.getMinX() + arr.getWidth() / 2, arr.getMinY() + arr.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        arr = op.filter(arr, null);
        g.drawImage(arr, x + p.getElementPosition(), y + p.getElementPosition(), null);
        int xShift = 0, yShift = 0;
        switch(direction) {
            case 'r':
                if(reverse) {
                    xShift = 22;
                    yShift = 15;
                }
                else {
                    xShift = 30;
                    yShift = 17;
                }
                inside = x + xShift >= 277 && (!reverse || !directionNotSwitched);
                break;
            case 'l':
                if(reverse) {
                    yShift = 17;
                    xShift = 10;
                }
                else {
                    yShift = 17;
                    xShift = 1;
                }
                inside = x + xShift <= 322 && (!reverse || !directionNotSwitched);
                break;
            case 'd':
                if(reverse) {
                    xShift = 17;
                    yShift = 22;
                }
                else {
                    xShift = 17;
                    yShift = 30;
                }
                inside = y + yShift >= 277 && (!reverse || !directionNotSwitched);
                break;
            case 'u':
                if(reverse) {
                    yShift = 10;
                    xShift = 15;
                }
                else {
                    yShift = 1;
                    xShift = 17;
                }
                inside = y + yShift <= 323 && (!reverse || !directionNotSwitched);
                break;
        }
    }
    
    public boolean getInside() {
        return inside;
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
    
    public boolean getReverse() {
        return reverse;
    }
    
    public boolean getDirectionNotSwitched() {
        return directionNotSwitched;
    }
    
    @Override
    public String toString() {
        return String.format("Arrow[speed = %d, reverse = %b, direction = %c, delay = %d]", speed, reverse, direction, delay);
    }
    
}
