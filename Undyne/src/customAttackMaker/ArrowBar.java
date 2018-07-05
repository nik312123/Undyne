package customAttackMaker;

import defense.Runner;
import nikunj.classes.NumberField;

import javax.swing.JTextField;
import javax.swing.text.Highlighter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
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
    
    private NumberField speedField;
    private NumberField delayField;
    
    private Highlighter defaultHighlighter;
    
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
            
            speedField.setCaretColor(Color.WHITE);
            delayField.setCaretColor(Color.WHITE);
            
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
    
    void setPressed(boolean pressed) {
        this.pressed = pressed;
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
        if(!speedField.getText().isEmpty())
            speed = Integer.parseInt(speedField.getText());
        if(!delayField.getText().isEmpty())
            delay = Integer.parseInt(delayField.getText());
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
    
    public void directionSelectedFalse() {
        this.isDirectionSelected = false;
    }
    
    public int getDelay() {
        if(!delayField.getText().isEmpty())
            delay = Integer.parseInt(delayField.getText());
        return delay;
    }
    
    public int getSpeed() {
        if(!speedField.getText().isEmpty())
            speed = Integer.parseInt(speedField.getText());
        return speed;
    }
    
    public void setDirection(char direction) {
        this.direction = direction;
    }
    
    public char getDirection() {
        return direction;
    }
    
    void switchReversable() {
        reverseable = !reverseable;
    }
    
    public boolean isReversible() {
        return reverseable;
    }
    
    Rectangle getDirectionRectangle() {
        return directionRectangle;
    }
    
    public boolean isDirectionSelected() {
        return isDirectionSelected;
    }
    
    void switchDirectionIsSelected() {
        isDirectionSelected = !isDirectionSelected;
    }
    
    void setFieldsVisibility(boolean visibility) {
        speedField.setVisible(visibility);
        delayField.setVisible(visibility);
    }
    
    void setFieldUsability(boolean usability) {
        if(usability) {
            speedField.setEditable(true);
            speedField.setHighlighter(defaultHighlighter);
            speedField.setCaretColor(Color.WHITE);
            delayField.setEditable(true);
            delayField.setHighlighter(defaultHighlighter);
            delayField.setCaretColor(Color.WHITE);
        }
        else {
            defaultHighlighter = speedField.getHighlighter();
            speedField.setEditable(false);
            speedField.setHighlighter(null);
            speedField.setCaretColor(Color.BLACK);
            delayField.setEditable(false);
            delayField.setHighlighter(null);
            delayField.setCaretColor(Color.BLACK);
        }
    }
    
    boolean emptyFieldExists() {
        return speedField.getText().isEmpty() || delayField.getText().isEmpty();
    }
    
    public Rectangle getSpeedFieldBounds() {
        Rectangle speedRect = (Rectangle) speedField.getBounds().clone();
        speedRect.setLocation(speedField.getLocationOnScreen());
        return speedRect;
    }
    
    public Rectangle getDelayFieldBounds() {
        Rectangle delayRect = (Rectangle) delayField.getBounds().clone();
        delayRect.setLocation(delayField.getLocationOnScreen());
        return delayRect;
    }
    
    public NumberField getSpeedField() {
        return speedField;
    }
    
    public NumberField getDelayField() {
        return delayField;
    }
    
    @Override
    public String toString() {
        return "{" + "speed = " + speed + ", delay = " + delay + ", reverse = " + reverseable + ", direction = " + direction + '}';
    }
}

