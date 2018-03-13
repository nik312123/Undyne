package customAttackMaker;

import defense.Runner;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

public class CustomAttacks {
    
    public static ArrayList<AttackBar> attacks = new ArrayList<>();
    
    public static Rectangle addAttack = new Rectangle();
    
    private static int scrollValue = 70;
    
    public static int dynamicLength = 0;
    
    public static Rectangle mouse = new Rectangle();
    
    public CustomAttacks() {
    }
    
    public void perform(Graphics g) {
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
    
    public void keyPressed(KeyEvent e) {
        System.out.println();
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
    }
    
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollValue += e.getWheelRotation() * -1;
    }
    
    public void mouseMoved(MouseEvent e) {
    }
    
    public void mouseDragged(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mousePressed(MouseEvent e) {
    }
    
    public void mouseClicked(MouseEvent e) {
        mouse.setBounds(e.getX(), e.getY(), 1, 1);
        
        for(AttackBar a : attacks) {
            if(a.mouseWork() == 1) {
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
