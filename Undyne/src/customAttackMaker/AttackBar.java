package customAttackMaker;

import defense.Runner;

import java.awt.*;
import java.util.ArrayList;

public class AttackBar {
    
    private int number;
    
    private boolean isDropped = true;
    
    private Rectangle dropDownButton = new Rectangle();
    private Rectangle deleteAttack = new Rectangle();
    private Rectangle newArrowButton = new Rectangle();
    
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
        drawString(g, x, y);
        
        deleteAttackButton(g, x, y);
        
        dropDownButton(g, x, y);
        
        if(isDropped) {
            
            CustomAttacks.dynamicLength += 10;
            y += 10;
            
            y += 30 * drawArrows(g, x, y) - 10;
            
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
        for(ArrowBar a : arrows) {
            counter++;
            a.draw(g, x + 10, y);
            y += 30;
            CustomAttacks.dynamicLength += 30;
        }
        return counter;
    }
    
    public int mouseWork() {
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
        }
        
        for(ArrowBar a : arrows){
            if(CustomAttacks.mouse.intersects(a.getReverseTickBox()))
                a.setReverse(!a.isReverse());
        }
        
        return 0;
    }
}
