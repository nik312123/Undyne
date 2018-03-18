package customAttackMaker;

import defense.Runner;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

public class AttackBar {
    
    private int number;
    
    private boolean isDropped = true;
    
    private Rectangle dropDownButton = new Rectangle();
    private Rectangle deleteAttack = new Rectangle();
    private Rectangle newArrowButton = new Rectangle();
    private Rectangle topBound = new Rectangle();
    private Rectangle bottomBound = new Rectangle();
    
    private ArrayList<ArrowBar> arrows = new ArrayList<>();
    
    public AttackBar() {
        this.number = CustomAttacks.attacks.size();
    }
    
    public ArrayList<ArrowBar> getArrows() {
        return arrows;
    }
    
    public int getNumber() {
        return number;
    }
    
    public void setNumber(int num) {
        this.number = num;
    }
    
    public void draw(Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.GREEN);
        
        drawString(g, x, y);
        
        deleteAttackButton(g, x, y);
        
        dropDownButton(g, x, y);
        
        if(isDropped) {
            
            topBound.setBounds(0, y + 10, 600,1);
            
            CustomAttacks.dynamicLength += 10;
            y += 10;
            
            y += 30 * drawArrows(g, x, y) - 10;
    
            bottomBound.setBounds(0, y + 6, 600,1);
    
    
            newArrowButton(g, x, y);
            
        }
        
        if(isDropped)
            CustomAttacks.dynamicLength += 45;
        else
            CustomAttacks.dynamicLength += 35;
    }
    
    public void drawString(Graphics g, int x, int y) {
        g.setColor(Color.white);
        g.setFont(Runner.deteFontNorm);
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 2F);
        g.setFont(newFont);
        int displayNum = number + 1;
        g.drawString("Attack ", x, y);
        g.drawString(displayNum + "", 10 + x + g.getFontMetrics().stringWidth("Attack"), y);
    }
    
    public void deleteAttackButton(Graphics g, int x, int y) {
        int displayNum = number + 1;
        g.drawImage(Runner.deleteAttack, 10 + x + g.getFontMetrics().stringWidth(displayNum + "") + x + 70, y - 16, null);
        deleteAttack.setBounds(x + 130, y - 16, 44, 17);
    }
    
    public void newArrowButton(Graphics g, int x, int y) {
        g.drawImage(Runner.newArrow, x + 10, y + 7, null);
        newArrowButton.setBounds(x + 10, y + 7, 19, 17);
    }
    
    public void dropDownButton(Graphics g, int x, int y) {
        if(isDropped) {
            g.drawImage(Runner.droppedDown, x - 15, y - 10, null);
            dropDownButton.setBounds(x - 15, y - 10, 6, 5);
        }
        else {
            g.drawImage(Runner.droppedClosed, x - 15, y - 10, null);
            dropDownButton.setBounds(x - 15, y - 10, 5, 6);
        }
    }
    
    public int drawArrows(Graphics g, int x, int y) {
        int counter = 0;
        int beingDragged = -1;
        for(int i = 0; i < arrows.size(); i++) {
            if(arrows.get(i).isPressed())
                beingDragged = i;
            counter++;
            arrows.get(i).draw(g, x + 10, y);
            y += 30;
            CustomAttacks.dynamicLength += 30;
        }
        
        if(beingDragged != -1) {
            g.setColor(Color.BLACK);
            g.fillRect(41, arrows.get(beingDragged).getY() + 3, 415, 22);
            arrows.get(beingDragged).draw(g, x + 10, y);
        }
        
        
        
        return counter;
    }
    
    public boolean isDropped() {
        return isDropped;
    }
    
    public void setDropped(boolean dropped) {
        isDropped = dropped;
    }
    
    public Rectangle getDropDownButton() {
        return dropDownButton;
    }
    
    public void setDropDownButton(Rectangle dropDownButton) {
        this.dropDownButton = dropDownButton;
    }
    
    public Rectangle getDeleteAttack() {
        return deleteAttack;
    }
    
    public void setDeleteAttack(Rectangle deleteAttack) {
        this.deleteAttack = deleteAttack;
    }
    
    public Rectangle getNewArrowButton() {
        return newArrowButton;
    }
    
    public void setNewArrowButton(Rectangle newArrowButton) {
        this.newArrowButton = newArrowButton;
    }
    
    public void setArrows(ArrayList<ArrowBar> arrows) {
        this.arrows = arrows;
    }
    
    public int mouseClickWork() {
        if(CustomAttacks.mouse.intersects(deleteAttack)) {
            CustomAttacks.attacks.remove(number);
            return 1;
            
        }
        
        if(CustomAttacks.mouse.intersects(dropDownButton))
            isDropped ^= true;
        
        if(CustomAttacks.mouse.intersects(newArrowButton))
            arrows.add(new ArrowBar(1, false, 'u', 2, false));
        
        for(int i = 0; i < arrows.size(); i++) {
            if(CustomAttacks.mouse.intersects(arrows.get(i).getDeleteArrowButton())) {
                arrows.remove(i);
            }
    
            if(CustomAttacks.mouse.intersects(arrows.get(i).getReverseTickBox()))
                arrows.get(i).setReverse(!arrows.get(i).isReverse());
            
            if(CustomAttacks.mouse.intersects(arrows.get(i).getDirectionRectangle()))
                arrows.get(i).switchDirectionIsSelected();
            
          
            
    
        }
     
        return 0;
    }
    
    public void mouseDragWork(){
        for(ArrowBar a : arrows){
            if(a.isPressed()){
                int iconMovement = CustomAttacks.mouse.y-8;
                
                if(iconMovement < topBound.getY() + 8)
                    iconMovement = (int) topBound.getY() + 8;
    
                if(iconMovement > bottomBound.getY() - 18)
                    iconMovement = (int) bottomBound.getY() - 18;
                
                a.setDragArrowIcon(new Rectangle(a.getDragArrowIcon().x, iconMovement, 16, 8));
                a.setY(iconMovement - 8);
            }
        }
        
        order();
    
    }
    
    public void mouseReleased(){
        for(ArrowBar a : arrows)
            a.setPressed(false);
    }
    
    public void mousePressed(){
        for(ArrowBar a : arrows){
            if(CustomAttacks.mouse.intersects(a.getDragArrowIcon())){
                a.setPressed(true);
            }
        }
    }
    
    public void order(){
        for(int i = 0; i < arrows.size(); i++){
            for(int j = 0; j < arrows.size(); j++){
                if(arrows.get(i).getOrderIntersecton().intersects(arrows.get(j).getOrderIntersecton()) && i != j){
                    Collections.swap(arrows, i, j);
                
                return;
                
                }
            }
        }
    }
    
    public void keyBoardWork(KeyEvent e){
        
        for(ArrowBar a : arrows){
            if(a.isDirectionIsSelected())
                switch (e.getKeyCode()){
                    case KeyEvent.VK_ENTER : a.setisDirectionSelected(false); break;
                    case KeyEvent.VK_UP : a.setDirection('u'); break;
                    case KeyEvent.VK_DOWN : a.setDirection('d'); break;
                    case KeyEvent.VK_RIGHT : a.setDirection('r'); break;
                    case KeyEvent.VK_LEFT : a.setDirection('l'); break;
    
                }
        }
    }
}
