package customAttackMaker;

import defense.Runner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class ArrowBar {
    private int y = 0;
    private int speed;
    private int delay;
    
    private boolean isDirectionSelected = false;
    private boolean pressed = false;
    private boolean reverseable;
    
    private char direction;
    
    private Color textColor = new Color(255, 198, 0);
    
    private Rectangle deleteArrowButton = new Rectangle();
    private Rectangle directionRectangle = new Rectangle();
    private Rectangle orderIntersecton = new Rectangle();
    private Rectangle dragArrowIcon = new Rectangle();
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
    
    void setPressed(boolean pressed) {
        this.pressed = pressed;
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
    
    private void drawDirection(Graphics g, int x, int y) {
        directionRectangle.setBounds(x + 97, y + 6, 18, 16);
        if(!isDirectionSelected || Runner.customAttacksCounter % 75 >= 30)
            setImage(g, x, y);
    }
    
    private void setImage(Graphics g, int x, int y) {
        boolean drawArrow = true;
        AffineTransform arrowTransform = new AffineTransform();
        arrowTransform.translate(x + 103, y + 8);
        double angle = 0;
        switch(direction) {
            case 'u':
                angle = 0;
                break;
            case 'd':
                angle = Math.PI;
                break;
            case 'r':
                angle = Math.PI / 2;
                break;
            case 'l':
                angle = -Math.PI / 2;
                break;
            case 'n':
                drawArrow = false;
                break;
        }
        arrowTransform.rotate(angle, 3, 6);
        Graphics2D g2d = (Graphics2D) g;
        if(drawArrow)
            g2d.drawImage(Runner.customArrowDirection, arrowTransform, null);
        else {
            g2d.setColor(textColor);
            g2d.setFont(Runner.deteFontSpeech);
            g2d.drawString("R", x + 102, y + 18);
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
        g.drawImage(Runner.deleteArrow, x, y, null);
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
    
    public int getDelay() {
        return delay;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    void setDirection(char direction) {
        this.direction = direction;
    }
    
    public char getDirection() {
        return direction;
    }
    
    void setReverseable(boolean reverse) {
        reverseable = reverse;
    }
    
    void switchReversable() {
        reverseable = !reverseable;
    }
    
    public boolean getReversable() {
        return reverseable;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    Rectangle getDirectionRectangle() {
        return directionRectangle;
    }
    
    boolean isDirectionSelected() {
        return isDirectionSelected;
    }
    
    void switchDirectionIsSelected() {
        isDirectionSelected = !isDirectionSelected;
    }
    
    @Override
    public String toString() {
        return "{" + "speed = " + speed + ", delay = " + delay + ", reverse = " + reverseable + ", direction = " + direction + '}';
    }
}

