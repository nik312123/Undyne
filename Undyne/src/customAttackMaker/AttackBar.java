package customAttackMaker;

import defense.Runner;
import defense.StartScreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

public class AttackBar {
    private int number;
    
    static final int ATTACKBAR_X = 30;
    
    private boolean isDropped = true;
    private boolean orientationShift = false;
    
    private Rectangle dropDownButton = new Rectangle();
    private Rectangle deleteAttack = new Rectangle();
    private Rectangle orientationShiftButton = new Rectangle();
    private Rectangle newArrowButton = new Rectangle();
    private Rectangle topBound = new Rectangle();
    private Rectangle bottomBound = new Rectangle();
    private Rectangle upScrollRect = new Rectangle(0, 0, 600, 5);
    private Rectangle downScrollRect = new Rectangle(0, 595, 600, 5);
    private Rectangle dragArrowRect = new Rectangle(0, 0, 16, 18);
    
    private ArrayList<ArrowBar> arrows = new ArrayList<>();
    
    AttackBar() {
        number = CustomAttacks.attacks.size();
    }
    
    public ArrayList<ArrowBar> getArrows() {
        return arrows;
    }
    
    void add(ArrowBar bar) {
        arrows.add(bar);
    }
    
    public int getNumber() {
        return number;
    }
    
    void setNumber(int num) {
        this.number = num;
    }
    
    void draw(Graphics g, int y) {
        int x = ATTACKBAR_X;
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GREEN);
        drawString(g, x, y);
        deleteAttackButton(g, x, y);
        orientationShiftButton(g, x, y);
        dropDownButton(g, x, y);
        if(isDropped) {
            topBound.setBounds(0, y + 10, 600, 1);
            CustomAttacks.dynamicLength += 10;
            y += 30 * drawArrows(g, x, y);
            bottomBound.setBounds(0, y + 6, 600, 1);
            newArrowButton(g, x, y);
            CustomAttacks.dynamicLength += 45;
        }
        else
            CustomAttacks.dynamicLength += 35;
    }
    
    private void drawString(Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.setFont(Runner.deteFontEditorAttack);
        int displayNum = number + 1;
        g.drawString("Attack ", x, y);
        g.drawString(displayNum + "", 10 + x + g.getFontMetrics().stringWidth("Attack"), y);
    }
    
    private void deleteAttackButton(Graphics g, int x, int y) {
        int displayNum = number + 1;
        int deleteAttackX = 10 + x + g.getFontMetrics().stringWidth(displayNum + "") + x + 70 + 36, deleteAttackY = y - 16;
        g.drawImage(Runner.deleteAttack, deleteAttackX, deleteAttackY, null);
        deleteAttack.setBounds(deleteAttackX, deleteAttackY, 44, 17);
    }
    
    private void orientationShiftButton(Graphics g, int x, int y) {
        int displayNum = number + 1;
        int orientationShiftX = 10 + x + g.getFontMetrics().stringWidth(displayNum + "") + x + 70, orientationShiftY = y - 17;
        if(orientationShift) {
            g.setColor(Color.GREEN);
            g.fillRect(orientationShiftX + 1, orientationShiftY + 1, 21 - 2, 19 - 2);
        }
        g.drawImage(Runner.orientationShiftButton, orientationShiftX, orientationShiftY, null);
        orientationShiftButton.setBounds(orientationShiftX, orientationShiftY, 21, 19);
    }
    
    private void newArrowButton(Graphics g, int x, int y) {
        g.drawImage(Runner.newArrow, x + 10, y + 7, null);
        newArrowButton.setBounds(x + 10, y + 7, 19, 17);
    }
    
    private void dropDownButton(Graphics g, int x, int y) {
        if(isDropped) {
            g.drawImage(Runner.droppedDown, x - 15, y - 10, null);
            dropDownButton.setBounds(x - 15, y - 10, 6, 5);
        }
        else {
            g.drawImage(Runner.droppedClosed, x - 15, y - 10, null);
            dropDownButton.setBounds(x - 15, y - 10, 5, 6);
        }
    }
    
    private int drawArrows(Graphics g, int x, int y) {
        int counter = 0;
        int beingDragged = -1;
        for(int i = 0; i < arrows.size(); ++i) {
            if(arrows.get(i).isPressed())
                beingDragged = i;
            ++counter;
            arrows.get(i).draw(g, x + 10, y + 10);
            y += 30;
            CustomAttacks.dynamicLength += 30;
        }
        if(beingDragged != -1) {
            Rectangle orderIntersection = arrows.get(beingDragged).getOrderIntersecton();
            if(orderIntersection.intersects(topBound) || orderIntersection.intersects(bottomBound))
                return counter;
            g.setColor(Color.BLACK);
            g.fillRect(41, arrows.get(beingDragged).getY() + 3, 415, 22);
            arrows.get(beingDragged).draw(g, x + 10, y);
            if(orderIntersection.intersects(upScrollRect)) {
                CustomAttacks.scrollValue += 0.5;
                order();
            }
            else if(orderIntersection.intersects(downScrollRect)) {
                CustomAttacks.scrollValue -= 0.5;
                order();
            }
            
        }
        return counter;
    }
    
    void switchOrientationShift() {
        orientationShift = !orientationShift;
    }
    
    public boolean isOrientationShift() {
        return orientationShift;
    }
    
    int mouseClickWork() {
        boolean anySelected = CustomAttacks.areAnyDirectionsSelected();
        if(deleteAttack.contains(CustomAttacks.mousePosition) && !anySelected) {
            StartScreen.playClick();
            ArrayList<AttackBar> attacks = CustomAttacks.attacks;
            AttackBar attBar = attacks.get(number);
            for(ArrowBar arrBar : attBar.getArrows())
                arrBar.removeFields();
            attacks.remove(number);
            return 1;
        }
        else if(orientationShiftButton.contains(CustomAttacks.mousePosition) && !anySelected) {
            StartScreen.playClick();
            switchOrientationShift();
        }
        else if(dropDownButton.contains(CustomAttacks.mousePosition)) {
            StartScreen.playClick();
            isDropped = !isDropped;
        }
        else if(newArrowButton.contains(CustomAttacks.mousePosition) && !anySelected) {
            StartScreen.playClick();
            arrows.add(new ArrowBar(0, false, 'u', 0));
        }
        for(int i = 0; i < arrows.size(); ++i) {
            ArrowBar ab = arrows.get(i);
            if(ab.getDirectionRectangle().contains(CustomAttacks.mousePosition)) {
                StartScreen.playClick();
                ab.switchDirectionIsSelected();
            }
            else if(ab.getDeleteArrowButton().contains(CustomAttacks.mousePosition) && !anySelected) {
                StartScreen.playClick();
                ab.removeFields();
                arrows.remove(i);
                --i;
            }
            else if(ab.getReverseTickBox().contains(CustomAttacks.mousePosition) && !anySelected) {
                StartScreen.playClick();
                ab.switchReversable();
            }
        }
        return 0;
    }
    
    void mouseDragWork() {
        for(ArrowBar a : arrows) {
            if(a.isPressed()) {
                int iconMovement = CustomAttacks.mousePosition.y - 8;
                if(iconMovement < topBound.getY() + 8)
                    iconMovement = (int) topBound.getY() + 8;
                if(iconMovement > bottomBound.getY() - 18)
                    iconMovement = (int) bottomBound.getY() - 18;
                dragArrowRect.setLocation(a.getDragArrowIcon().x, iconMovement);
                a.setDragArrowIcon(dragArrowRect);
                a.setY(iconMovement - 8);
            }
        }
        order();
    }
    
    void mouseReleased() {
        for(ArrowBar a : arrows)
            a.setPressed(false);
    }
    
    void mousePressed() {
        for(ArrowBar a : arrows) {
            if(a.getDragArrowIcon().contains(CustomAttacks.mousePosition))
                a.setPressed(true);
        }
    }
    
    private void order() {
        for(int i = 0; i < arrows.size(); ++i) {
            for(int j = i + 1; j < arrows.size(); ++j) {
                if(arrows.get(i).getOrderIntersecton().intersects(arrows.get(j).getOrderIntersecton())) {
                    Collections.swap(arrows, i, j);
                    return;
                }
            }
        }
    }
    
}
