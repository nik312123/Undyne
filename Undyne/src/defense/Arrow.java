package defense;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Composes an arrow to be implemented in the Attack class that is launched at the player
 */
class Arrow {
    /**
     * The speed the arrow should go in pixels/tick between 1 – 100
     */
    private int speed;
    
    /**
     * This determines whether or not the arrow will be reversed
     */
    private boolean reverse;
    
    /**
     * This determines the direction the arrow should be traveling
     */
    private char direction;
    
    /**
     * These are the coordinates of the arrow
     */
    private int x, y;
    
    /**
     * This is the delay before the next arrow is launched after this arrow is lauched
     */
    private int delay;
    
    /**
     * The distance between the tip of the front of the arrow and the center
     */
    private int radius = 0;
    
    /**
     * What the user uses to block arrows and how the player gets hit
     */
    static Player p;
    
    /**
     * True if the arrow gets past the player's shield
     */
    private boolean inside = false;
    
    /**
     * If an arrow is a slow arrow, whether or not the arrow should move one pixel (alternates every tick)
     */
    private boolean slowShouldGo = true;
    
    /**
     * True if an arrow is a slow arrow (moves one pixel every other timer tick)
     */
    private boolean isSlow;
    
    /**
     * True when the arrow is in the process of rotating or has been rotated around the player (reverse arrow)
     */
    private boolean switchDir = false;
    
    /**
     * True until the arrow has been fully rotated around the player (reverse arrow) or false if it isn't a reverse
     * arrow
     */
    private boolean directionNotSwitched;
    
    /**
     * Transform used to set the arrow's orientation and rotate/move the arrow
     */
    private static AffineTransform arrowTransform = new AffineTransform();
    
    /**
     * The color of the arrow
     */
    enum ArrowColor {
        RED,
        BLUE}
    
    /**
     * Creates a new {@code Arrow} object
     *
     * @param speed     The speed the arrow should go in pixels/tick between 1 – 100
     * @param reverse   This determines whether or not the arrow will be reversed (flips from one side to another)
     * @param direction This determines the direction the arrow should come travel in the direction of
     * @param delay     This is the delay before the next arrow is launched after this arrow is lauched
     * @param isSlow    True if an arrow is a slow arrow (moves one pixel every other timer tick)
     */
    Arrow(int speed, boolean reverse, char direction, int delay, boolean isSlow) {
        this.speed = speed;
        this.reverse = reverse;
        directionNotSwitched = reverse;
        this.direction = direction;
        this.delay = delay;
        this.isSlow = isSlow;
        setCoordinates(direction);
    }
    
    /*
     * Helper method for the constructor setting the original arrow coordinates based on direction and if it's reversible
     */
    private void setCoordinates(char direction) {
        if(reverse) {
            switch(direction) {
                case 'r':
                    x = 17 + 11;
                    y = 270 + 11;
                    break;
                case 'l':
                    x = 557 - 9 - 11;
                    y = 270 + 11;
                    break;
                case 'u':
                    x = 285;
                    y = 545 - 8;
                    break;
                case 'd':
                    x = 285;
                    y = 11 + 8 + 11;
                    break;
            }
        }
        else {
            switch(direction) {
                case 'r':
                    x = 11;
                    y = 270 + 11;
                    break;
                case 'l':
                    x = 557;
                    y = 270 + 11;
                    break;
                case 'u':
                    x = 285;
                    y = 545 + 11;
                    break;
                case 'd':
                    x = 285;
                    y = 11;
                    break;
            }
        }
    }
    
    /**
     * Starts the rotation of a reverse arrow at a 3x speed and gets the distance from the center
     */
    void switchDir() {
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
    
    /**
     * Controls the movement of the arrow
     */
    void tick() {
        //Moved the arrow 'speed' pixels every tick towards the heart
        if(!Runner.isOneSecondDelayRunning()) {
            if(speed != 1 || slowShouldGo || !isSlow) {
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
                slowShouldGo = !slowShouldGo;
            
            //Controls the rotation of reverse arrows if it is in the right position based on the formula for the graph of a circle
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
    }
    
    /**
     * Draws the arrow and checks if it the arrow has gotten past the player's shield
     *
     * @param g The graphics object used for drawing graphics
     * @param c The arrow color that the arrow should be (based on which one was added earliest in the ArrayList)
     */
    void draw(Graphics g, ArrowColor c) {
        //Sets arrow to red arrow or blue arrow
        BufferedImage arr;
        if(reverse)
            arr = Runner.reverseArr;
        else if(c == ArrowColor.RED)
            arr = Runner.redArr;
        else
            arr = Runner.blueArr;
        //Sets the angle the arrow should be oriented when coming at the player
        int angle = 0;
        switch(direction) {
            case 'r':
                if(!reverse || directionNotSwitched)
                    angle = 0;
                else
                    angle = 180;
                break;
            case 'd':
                if(!reverse || directionNotSwitched)
                    angle = 90;
                else
                    angle = -90;
                break;
            case 'l':
                if(!reverse || directionNotSwitched)
                    angle = 180;
                else
                    angle = 0;
                break;
            case 'u':
                if(!reverse || directionNotSwitched)
                    angle = -90;
                else
                    angle = 90;
                break;
        }
        //Draws the arrow with the necessary location and orientation
        arrowTransform.setToIdentity();
        arrowTransform.translate(x + p.getElementPosition(), y + p.getElementPosition());
        arrowTransform.rotate(Math.toRadians(angle), arr.getMinX() + arr.getWidth() / 2.0, arr.getMinY() + arr.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(arr, arrowTransform, null);
        
        //Checks if the arrow has gotten past the shield
        int xShift, yShift;
        switch(direction) {
            case 'r':
                if(reverse)
                    xShift = 22;
                else
                    xShift = 30;
                inside = x + xShift >= 277 && (!reverse || !directionNotSwitched);
                break;
            case 'l':
                if(reverse)
                    xShift = 10;
                else
                    xShift = 1;
                inside = x + xShift <= 322 && (!reverse || !directionNotSwitched);
                break;
            case 'd':
                if(reverse)
                    yShift = 22;
                else
                    yShift = 30;
                inside = y + yShift >= 277 && (!reverse || !directionNotSwitched);
                break;
            case 'u':
                if(reverse)
                    yShift = 10;
                else
                    yShift = 1;
                inside = y + yShift <= 323 && (!reverse || !directionNotSwitched);
                break;
        }
    }
    
    /**
     * Returns true if the arrow has not gotten past the shield
     *
     * @return If the arrow has not gotten past the shield
     */
    boolean notInside() {
        return !inside;
    }
    
    /**
     * Returns the x-position of the arrow's tip
     *
     * @return The x-position of the arrow's tip
     */
    int getX() {
        return x;
    }
    
    /**
     * Returns the y-position of the arrow's tip
     *
     * @return The y-position of the arrow's tip
     */
    int getY() {
        return y;
    }
    
    /**
     * Returns the direction the arrow should be traveling
     *
     * @return The direction the arrow should be traveling
     */
    char getDir() {
        return direction;
    }
    
    /**
     * Returns the delay before the following arrow is added
     *
     * @return The delay before the following arrow is added
     */
    int getDelay() {
        return delay;
    }
    
    /**
     * Returns the number of pixels the arrow travels per timer tick
     *
     * @return The number of pixels the arrow travels per timer tick
     */
    int getSpeed() {
        return speed;
    }
    
    /**
     * Returns true if the arrow is a reverse arrow
     *
     * @return True if the arrow is a reverse arrow
     */
    boolean getReverse() {
        return reverse;
    }
    
    /**
     * Returns true until the arrow has been fully rotated around the player if it is a reverse arrow and false if it is
     * not
     *
     * @return True if reverse arrow has been rotated fully around player
     */
    boolean getDirectionNotSwitched() {
        return directionNotSwitched;
    }
    
    /**
     * Returns the arrow in a String format
     *
     * @return The arro win a String format
     */
    @Override
    public String toString() {
        return String.format("Arrow[speed = %d, reverse = %b, direction = %c, delay = %d]", speed, reverse, direction, delay);
    }
    
}
