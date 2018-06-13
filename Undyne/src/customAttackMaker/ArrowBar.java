package customAttackMaker;

import defense.Runner;
import nikunj.classes.NumberField;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

public class ArrowBar {
    
    private Rectangle deleteArrowButton = new Rectangle();
    private Rectangle directionRectangle = new Rectangle();
    private Rectangle orderIntersecton = new Rectangle();
    private Rectangle dragArrowIcon = new Rectangle();
    private Rectangle reverseTickBox = new Rectangle();
    
    private int speed;
    private int y = 0;
    private int delay;
    
    private boolean isDirectionSelected = false;
    private boolean pressed = false;
    private boolean reverseable;
    
    private char direction;
    
    private NumberField speedField;
    private NumberField delayField;
    
    ArrowBar(int speed, boolean reverseable, char direction, int delay) {
        this.speed = speed;
        this.reverseable = reverseable;
        this.direction = direction;
        this.delay = delay;
        Color foreground = new Color(255, 196, 0);
        try {
            speedField = new NumberField(2, NumberField.STATE_NORMAL, false);
            delayField = new NumberField(3, NumberField.STATE_NORMAL, false);
            speedField.setFont(Runner.deteFontEditor);
            delayField.setFont(Runner.deteFontEditor);
            speedField.setForeground(foreground);
            delayField.setForeground(foreground);
            speedField.setBackground(Color.BLACK);
            delayField.setBackground(Color.BLACK);
            speedField.setHorizontalAlignment(JTextField.CENTER);
            delayField.setHorizontalAlignment(JTextField.CENTER);
            speedField.setBounds(AttackBar.ATTACKBAR_X + 6 + 183, y + 7, 34, 13);
            delayField.setBounds(AttackBar.ATTACKBAR_X + 6 + 277, y + 7, 34, 13);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        Runner.addComponent(speedField, 0);
        Runner.addComponent(delayField, 0);
    }
    
    void removeFields() {
        Runner.removeComponent(speedField);
        Runner.removeComponent(delayField);
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
        speedField.setLocation(AttackBar.ATTACKBAR_X + 6 + 183, this.y + 7);
        delayField.setLocation(AttackBar.ATTACKBAR_X + 6 + 277, this.y + 7);
        g.setColor(Color.BLACK);
        g.fillRect(AttackBar.ATTACKBAR_X + 10 + 183, this.y + 8, 26, 12);
        g.fillRect(AttackBar.ATTACKBAR_X + 10 + 277, this.y + 8, 26, 12);
        orderIntersecton.setBounds(x, this.y, 100, 10);
        g.drawImage(Runner.arrowImg, x, this.y, null);
        deleteArrowButton(g, x - 24, this.y + 5);
        drawDirection(g, x, this.y);
        reverseTickBox(g, x, this.y);
        dragArrowIcon(g, x, this.y);
    }
    
    void setPressed(boolean pressed) {
        this.pressed = pressed;
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
        this.reverseable = reverse;
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
    
    void switchDirectionIsSelected() {
        isDirectionSelected = !isDirectionSelected;
    }
    
    @Override
    public String toString() {
        return "{" + "speed = " + speed + ", delay = " + delay + ", reverse = " + reverseable + ", direction = " + direction + '}';
    }
}

