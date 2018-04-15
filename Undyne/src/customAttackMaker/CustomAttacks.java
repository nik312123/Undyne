package customAttackMaker;

import defense.Runner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

public class CustomAttacks {
    
    static ArrayList<AttackBar> attacks = new ArrayList<>();
    
    private static Rectangle addAttack = new Rectangle();
    
    static int scrollValue = 70;
    
    static int dynamicLength = 0;
    
    static Rectangle mouse = new Rectangle();
    
    public CustomAttacks() {}
    
    public void perform(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bg(g);
        dynamicLength = scrollValue;
        for(AttackBar attackBar : attacks)
            attackBar.draw(g, 30, dynamicLength);
        addAttackButton(g);
    }
    
    private void addAttack() {
        attacks.add(new AttackBar());
    }
    
    private void reassignNumbers() {
        int i = 0;
        for(AttackBar a : CustomAttacks.attacks) {
            a.setNumber(i);
            ++i;
        }
    }
    
    private void addAttackButton(Graphics g) {
        g.drawImage(Runner.addAttack, 300 - 33, dynamicLength - 5, null);
        addAttack.setBounds(300 - 33, dynamicLength - 5, 66, 17);
    }
    
    private void bg(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 600);
    }
    
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_L:
                for(AttackBar a : attacks) {
                    System.out.println("Attack: " + a.getNumber());
                    System.out.println();
                    for(ArrowBar b : a.getArrows())
                        System.out.println(b);
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
    
    public void mouseMoved(MouseEvent e) {}
    
    public void mouseDragged(MouseEvent e) {
        mouse.setBounds(e.getX(), e.getY(), 1, 1);
        for(AttackBar a : attacks)
            a.mouseDragWork();
    }
    
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseReleased(MouseEvent e) {
        for(AttackBar a : attacks)
            a.mouseReleased();
    }
    
    public void mousePressed(MouseEvent e) {
        mouse.setBounds(e.getX(), e.getY(), 1, 1);
        for(AttackBar a : attacks) {
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
    
    public void mouseExited(MouseEvent e) {}
}
