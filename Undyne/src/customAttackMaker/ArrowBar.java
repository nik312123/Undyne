package customAttackMaker;

import defense.Runner;

import java.awt.Graphics;
import java.awt.Rectangle;

public class ArrowBar {
    
    private Rectangle deleteArrowButton = new Rectangle();
    
    private Rectangle directionRectangle = new Rectangle();
    
    private Rectangle orderIntersecton = new Rectangle();
    
    private int y = 0;
    
    private boolean isDirectionSelected = false;
    private boolean pressed = false;
    
    void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
    
    private Rectangle dragArrowIcon = new Rectangle();
    
    private int speed;
    
    private int delay;
    
    private boolean reverseable;
    
    private char direction;
    
    private Rectangle reverseTickBox = new Rectangle();
    
    ArrowBar(int speed, boolean reverseable, char direction, int delay) {
        this.speed = speed;
        this.reverseable = reverseable;
        this.direction = direction;
        this.delay = delay;
    }
    
    int getY() {
        return y;
    }
    
    void setY(int y) {
        this.y = y;
    }
    
    Rectangle getOrderIntersecton() {
        return orderIntersecton;
    }
    
    void draw(Graphics g, int x, int y) {
        if(!pressed)
            this.y = y;
        orderIntersecton.setBounds(x, this.y, 100, 10);
        g.drawImage(Runner.arrowImg, x, this.y, null);
        deleteArrowButton(g, x - 24, this.y + 5);
        drawDirection(g, x, this.y);
        reverseTickBox(g, x, this.y);
        dragArrowIcon(g, x, this.y);
    }
    
    boolean isDirectionIsSelected() {
        return isDirectionSelected;
    }
    
    private void drawDirection(Graphics g, int x, int y) {
        directionRectangle.setBounds(x + 97, y + 6, 18, 16);
        if(!isDirectionSelected || Runner.customAttacksCounter % 75 >= 30)
            setImage(g, x, y);
    }
    
    private void setImage(Graphics g, int x, int y) {
        switch(getDirection()) {
            case 'u':
                g.drawImage(Runner.arrowUp, x + 101, y + 5, null);
                break;
            case 'd':
                g.drawImage(Runner.arrowDown, x + 101, y + 5, null);
                break;
            case 'r':
                g.drawImage(Runner.arrowRight, x + 97, y + 9, null);
                break;
            case 'l':
                g.drawImage(Runner.arrowLeft, x + 97, y + 9, null);
                break;
        }
    }
    
    private void reverseTickBox(Graphics g, int x, int y) {
        if(reverseable)
            g.drawImage(Runner.ticked, x + 400 - 10, y + 8, null);
        reverseTickBox.setBounds(x + 400 - 10, y + 8, 13, 12);
    }
    
    boolean isPressed() {
        return pressed;
    }
    
    private void dragArrowIcon(Graphics g, int x, int y) {
        if(!pressed)
            dragArrowIcon.setBounds(x + 424, y + 8, 16, 8);
        g.drawImage(Runner.dragArrowIcon, dragArrowIcon.x, dragArrowIcon.y, null);
    }
    
    Rectangle getDragArrowIcon() {
        return dragArrowIcon;
    }
    
    void setDragArrowIcon(Rectangle dragArrowIcon) {
        this.dragArrowIcon = dragArrowIcon;
    }
    
    private void deleteArrowButton(Graphics g, int x, int y) {
        g.drawImage(Runner.deleteArrow, x, y, null); // 19 x 17
        deleteArrowButton.setBounds(x, y, 19, 17);
    }
    
    Rectangle getDeleteArrowButton() {
        return deleteArrowButton;
    }
    
    Rectangle getReverseTickBox() {
        return reverseTickBox;
    }
    
    boolean isReverse() {
        return reverseable;
    }
    
    void directionSelectedFalse() {
        this.isDirectionSelected = false;
    }
    
    int getDelay() {
        return delay;
    }
    
    int getSpeed() {
        return speed;
    }
    
    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    void setDirection(char direction) {
        this.direction = direction;
    }
    
    char getDirection() {
        return direction;
    }
    
    void setReverseable(boolean reverse) {
        this.reverseable = reverse;
    }
    
    boolean getReversable() {
        return reverseable;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    Rectangle getDirectionRectangle() {
        return directionRectangle;
    }
    
    void switchDirectionIsSelected() {
        isDirectionSelected = !isDirectionSelected;
    }
    
    @Override
    public String toString() {
        return "{" + "speed = " + speed + ", delay = " + delay + ", reverse = " + reverseable + ", direction = " + direction + '}';
    }
}

