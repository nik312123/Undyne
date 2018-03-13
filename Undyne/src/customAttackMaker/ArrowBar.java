package customAttackMaker;

import defense.Runner;

import java.awt.*;

public class ArrowBar {
    
    private Rectangle deleteArrowButton = new Rectangle();
    
    private int speed;
    
    private int delay;
    
    private boolean reverse;
    
    private char direction;
    
    private Rectangle reverseTickBox = new Rectangle();
    
    public ArrowBar(int speed, boolean reverse, char direction, int delay, boolean isSlow) {
        this.speed = speed;
        this.reverse = reverse;
        this.direction = direction;
        this.delay = delay;
        
    }
    
    public void draw(Graphics g, int x, int y) {
        g.drawImage(Runner.arrowImg, x, y, null);
        deleteArrowButton(g, x - 24, y + 5);
        
        reverseTickBox(g , x, y);
        
        
    }
    
    public void reverseTickBox(Graphics g, int x, int y){
        if(reverse)
            g.drawImage(Runner.ticked, x + 400 - 10, y + 8, null);
        reverseTickBox.setBounds(x + 400 - 10, y + 8, 13, 12);
    
    }
    
    public void deleteArrowButton(Graphics g, int x, int y) {
        g.drawImage(Runner.deleteArrow, x, y, null); // 19 x 17
        deleteArrowButton.setBounds(x, y, 19, 17);
    }
    
    public Rectangle getDeleteArrowButton() {
        return deleteArrowButton;
    }
    
    public Rectangle getReverseTickBox() {
        return reverseTickBox;
    }
    
    public boolean isReverse() {
        return reverse;
    }
    
    public char getDirection() {
        return direction;
    }
    
    public int getDelay() {
        return delay;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    public void setDirection(char direction) {
        this.direction = direction;
    }
    
    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    @Override
    public String toString() {
        return "{" + "speed = " + speed + ", delay = " + delay + ", reverse = " + reverse + ", direction = " + direction + '}';
    }
}
