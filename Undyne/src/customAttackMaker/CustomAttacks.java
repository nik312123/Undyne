package customAttackMaker;

import defense.Attack;
import defense.Runner;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

public class CustomAttacks {
    
    
    public static ArrayList<AttackBar> attacks = new ArrayList<>();
    
    public static Rectangle addAttack = new Rectangle();
    
    public static int scrollValue = 70;
    
    public static int dynamicLength = 0;
    
    public static Rectangle mouse = new Rectangle();
    
    public CustomAttacks() {
        }
    
    public void perform(Graphics g2) {
        Graphics2D g = (Graphics2D)g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        bg(g);
        
        dynamicLength = scrollValue;
        
        for(AttackBar attackBar : attacks) {
            attackBar.draw(g, 30, dynamicLength);
        }
        
        addAttackButton(g);
        
    }
    
    public void addAttack() {
        attacks.add(new AttackBar());
    }
    
    public void reassignNumbers() {
        int i = 0;
        for(AttackBar a : CustomAttacks.attacks) {
            a.setNumber(i);
            i++;
        }
    }
    
    public void addAttackButton(Graphics g) {
        g.drawImage(Runner.addAttack, 300 - 33, dynamicLength - 5, null);
        addAttack.setBounds(300 - 33, dynamicLength - 5, 66, 17);
    }
    
    public void bg(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 600, 600);
    }
    
    public static ArrayList<AttackBar> getAttacks() {
        return attacks;
    }
    
    public static void setAttacks(ArrayList<AttackBar> attacks) {
        CustomAttacks.attacks = attacks;
    }
    
    public static Rectangle getAddAttack() {
        return addAttack;
    }
    
    public static void setAddAttack(Rectangle addAttack) {
        CustomAttacks.addAttack = addAttack;
    }
    
    public static int getScrollValue() {
        return scrollValue;
    }
    
    public static void setScrollValue(int scrollValue) {
        CustomAttacks.scrollValue = scrollValue;
    }
    
    public static int getDynamicLength() {
        return dynamicLength;
    }
    
    public static void setDynamicLength(int dynamicLength) {
        CustomAttacks.dynamicLength = dynamicLength;
    }
    
    public static Rectangle getMouse() {
        return mouse;
    }
    
    public static void setMouse(Rectangle mouse) {
        CustomAttacks.mouse = mouse;
    }
    
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_L:
                for(AttackBar a : attacks) {
                    System.out.println("Attack: " + a.getNumber());
                    System.out.println();
                    for(ArrowBar b : a.getArrows()) {
                        System.out.println(b);
                    }
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    
                }
            
        }
        for(AttackBar a : attacks)
            a.keyBoardWork(e);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollValue += e.getWheelRotation() * -1;
    }
    
    public void mouseMoved(MouseEvent e) {
    }
    
    public void mouseDragged(MouseEvent e) {
        mouse.setBounds(e.getX(), e.getY(), 1, 1);
        for(AttackBar a : attacks)
            a.mouseDragWork();
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseReleased(MouseEvent e) {
        for(AttackBar a: attacks)
            a.mouseReleased();
    
    }
    
    public void mousePressed(MouseEvent e) {
        mouse.setBounds(e.getX(), e.getY(), 1, 1);
        for(AttackBar a : attacks){
            a.mousePressed();
        }
    
    }
    
    public void mouseClicked(MouseEvent e) {
        
        mouse.setBounds(e.getX(), e.getY(), 1, 1);
        
        for(AttackBar a : attacks) {
            if(a.mouseClickWork() == 1) {
                reassignNumbers();
                break;
            }
        }
        
        if(mouse.intersects(addAttack))
            addAttack();
        
    }
    
    
    public void mouseExited(MouseEvent e) {
    }
}
