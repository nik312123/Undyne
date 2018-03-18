package customAttackMaker;

import defense.Runner;

import java.awt.*;

public class ArrowBar {
    
    private Rectangle deleteArrowButton = new Rectangle();
    
    private Rectangle directionRectangle = new Rectangle();
    
    private Rectangle orderIntersecton = new Rectangle();
    
    private int y = 0;
    
    private boolean isDirectionSelected = false;
    private boolean pressed = false;
    
    
    
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
    
    private Rectangle dragArrowIcon = new Rectangle();
    
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
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public Rectangle getOrderIntersecton() {
        return orderIntersecton;
    }
    
    public void draw(Graphics g, int x, int y) {
        
        if(!pressed)
            this.y = y;
    
        orderIntersecton.setBounds(x, this.y, 100, 10);
    
        g.drawImage(Runner.arrowImg, x, this.y, null);
        deleteArrowButton(g, x - 24, this.y + 5);
        
        drawDirection(g, x, this.y);
        reverseTickBox(g , x, this.y);
        dragArrowIcon(g, x, this.y);
    }
    
    public boolean isDirectionIsSelected() {
        return isDirectionSelected;
    }
    
    public void drawDirection(Graphics g, int x, int y){
        directionRectangle.setBounds(x + 97, y + 6, 18, 16);
        
        if(isDirectionSelected) {
            if(Runner.customAttacksCounter % 50 < 20)
                setImage(g, x, y);
        }
        
        else {
            setImage(g, x, y);
    }
    
    
    }
    
    public void setImage(Graphics g, int x, int y){
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
    
    public void reverseTickBox(Graphics g, int x, int y){
        if(reverse)
            g.drawImage(Runner.ticked, x + 400 - 10, y + 8, null);
        reverseTickBox.setBounds(x + 400 - 10, y + 8, 13, 12);
    
    }
    
    public boolean isPressed() {
        return pressed;
    }
    
    public void dragArrowIcon(Graphics g, int x, int y){
        if(!pressed)
        dragArrowIcon.setBounds(x + 424, y + 8, 16, 8);
        g.drawImage(Runner.dragArrowIcon, dragArrowIcon.x, dragArrowIcon.y, null);
    
    }
    
    
    public Rectangle getDragArrowIcon() {
        return dragArrowIcon;
    }
    
    public void setDeleteArrowButton(Rectangle deleteArrowButton) {
        this.deleteArrowButton = deleteArrowButton;
    }
    
    public void setDirectionRectangle(Rectangle directionRectangle) {
        this.directionRectangle = directionRectangle;
    }
    
    public boolean isDirectionSelected() {
        return isDirectionSelected;
    }
    
    public void setDirectionSelected(boolean directionSelected) {
        isDirectionSelected = directionSelected;
    }
    
    public void setDragArrowIcon(Rectangle dragArrowIcon) {
        this.dragArrowIcon = dragArrowIcon;
    }
    
    public void setReverseTickBox(Rectangle reverseTickBox) {
        this.reverseTickBox = reverseTickBox;
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
    
    public void setisDirectionSelected(boolean directionIsSelected) {
        this.isDirectionSelected = directionIsSelected;
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
    
    public Rectangle getDirectionRectangle(){
        return directionRectangle;
    }
    
    public void switchDirectionIsSelected(){
        isDirectionSelected ^= true;
    }
    
    @Override
    public String toString() {
        return "{" + "speed = " + speed + ", delay = " + delay + ", reverse = " + reverse + ", direction = " + direction + '}';
    }
}
