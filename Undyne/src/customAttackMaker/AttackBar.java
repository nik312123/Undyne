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
    /**
     * The index of the {@code AttackBar} in the list of {@code CustomAttack} attacks
     */
    private int number;
    
    /**
     * The constant x-position for {@code AttackBar}s
     */
    static final int ATTACKBAR_X = 30;
    
    /**
     * True if an {@code AttackBar}'s {@code ArrowBar}s are collapsed and false otherwise
     */
    private boolean collapsed = true;
    
    /**
     * True if the {@code AttackBar}'s random orientation shift option is selected and false otherwise
     */
    private boolean orientationShift = false;
    
    /**
     * The dropdown button's clicking bounds
     */
    private Rectangle dropDownButton = new Rectangle();
    
    /**
     * The delete attack button's clicking bounds
     */
    private Rectangle deleteAttack = new Rectangle();
    
    /**
     * The orientation shift button's clicking bounds
     */
    private Rectangle orientationShiftButton = new Rectangle();
    
    /**
     * The new arrow button's clicking bounds
     */
    private Rectangle newArrowButton = new Rectangle();
    
    /**
     * The top bound to constrict the AttackBar's area
     */
    private Rectangle topBound = new Rectangle();
    
    /**
     * The bottom bound to constrict the AttackBar's area
     */
    private Rectangle bottomBound = new Rectangle();
    
    private Rectangle upScrollRect = new Rectangle(0, 0, 600, 5);
    private Rectangle downScrollRect = new Rectangle(0, 595, 600, 5);
    private Rectangle dragArrowRect = new Rectangle(0, 0, 16, 18);
    
    /**
     * The {@code ArrowBar}s under this {@code AttackBar}
     */
    private ArrayList<ArrowBar> arrows = new ArrayList<>();
    
    /**
     * Initializes an {@code AttackBar} with its corresponding number
     */
    AttackBar() {
        number = CustomAttacks.attacks.size() - 1;
    }
    
    /**
     * Returns arrows
     *
     * @return an ArrayList of ArrowBar objects
     */
    public ArrayList<ArrowBar> getArrows() {
        return arrows;
    }
    
    /**
     * Runs when the add new arrow button is clicked and adds an arrow
     *
     * @param bar An AttackBar object
     */
    void add(ArrowBar bar) {
        arrows.add(bar);
    }
    
    /**
     * Returns the id number of the AttackBar
     *
     * @return An int value representing the id number of the AttackBar
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Sets the id number for the AttackBar
     *
     * @param num The number to set the AttackBar to (represents the index in customAttacks)
     */
    void setNumber(int num) {
        this.number = num;
    }
    
    /**
     * Draws the {@code AttackBar} and its corresponding components
     *
     * @param g The graphics object used for drawing the Runner JPanel
     * @param y The y-position of the AttackBar
     */
    void draw(Graphics g, int y) {
        int x = ATTACKBAR_X;
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GREEN);
        drawString(g, x, y);
        deleteAttackButton(g, x, y);
        orientationShiftButton(g, x, y);
        dropDownButton(g, x, y);
        if(collapsed) {
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
    
    /**
     * Draws the "Attack [id]" {@code String} for the {@code AttackBar}
     *
     * @param g The graphics object used for drawing the {@code Runner} {@code JPanel}
     * @param x The x-position of every {@code AttackBar}
     * @param y The y-position of this {@code AttackBar}
     */
    private void drawString(Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.setFont(Runner.deteFontEditorAttack);
        int displayNum = number + 1;
        g.drawString("Attack ", x, y);
        g.drawString(displayNum + "", 10 + x + g.getFontMetrics().stringWidth("Attack"), y);
    }
    
    /**
     * Draws the delete attack button
     *
     * @param g The graphics object used for drawing the Runner JPanel
     * @param x The x-position of every {@code AttackBar}
     * @param y The y-position of this {@code AttackBar}
     */
    private void deleteAttackButton(Graphics g, int x, int y) {
        int displayNum = number + 1;
        int deleteAttackX = 10 + x + g.getFontMetrics().stringWidth(displayNum + "") + x + 70 + 36, deleteAttackY = y - 16;
        g.drawImage(Runner.deleteAttack, deleteAttackX, deleteAttackY, null);
        deleteAttack.setBounds(deleteAttackX, deleteAttackY, 44, 17);
    }
    
    /**
     * Draws the orientation shift button
     *
     * @param g The graphics object used for drawing the Runner JPanel
     * @param x The x-position of every {@code AttackBar}
     * @param y The y-position of this {@code AttackBar}
     */
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
    
    /**
     * Draws the add new arrow button
     *
     * @param g The graphics object used for drawing the Runner JPanel
     * @param x The x-position of every {@code AttackBar}
     * @param y The y-position of this {@code AttackBar}
     */
    private void newArrowButton(Graphics g, int x, int y) {
        g.drawImage(Runner.newArrow, x + 10, y + 7, null);
        newArrowButton.setBounds(x + 10, y + 7, 19, 17);
    }
    
    /**
     * Draws the drop-down button
     *
     * @param g The graphics object used for drawing the Runner JPanel
     * @param x The x-position of every {@code AttackBar}
     * @param y The y-position of this {@code AttackBar}
     */
    private void dropDownButton(Graphics g, int x, int y) {
        if(collapsed) {
            g.drawImage(Runner.droppedDown, x - 15, y - 10, null);
            dropDownButton.setBounds(x - 15, y - 10, 6, 5);
        }
        else {
            g.drawImage(Runner.droppedClosed, x - 15, y - 10, null);
            dropDownButton.setBounds(x - 15, y - 10, 5, 6);
        }
    }
    
    /**
     * Draws all the {@code ArrowBar}s for this {@code AttackBar}
     *
     * @param g The graphics object used for drawing the Runner JPanel
     * @param x The x-position of every {@code AttackBar}
     * @param y The y-position of this {@code AttackBar}
     * @return The number of {@code ArrowBar}s drawn
     */
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
            Rectangle orderIntersection = arrows.get(beingDragged).getOrderIntersection();
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
    
    /**
     * Toggles whether or not the {@code ArrowBar}s for this {@code AttackBar} should have a random orientation shift applied to them
     */
    void switchOrientationShift() {
        orientationShift = !orientationShift;
    }
    
    /**
     * Returns true if the {@code ArrowBar}s for this {@code AttackBar} should have a random orientation shift applied to them
     *
     * @return True if the {@code ArrowBar}s for this {@code AttackBar} should have a random orientation shift applied to them
     */
    public boolean isOrientationShift() {
        return orientationShift;
    }
    
    /**
     * Handles mouse clicking action
     *
     * @return int value representing what specific action was performed
     */
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
            collapsed = !collapsed;
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
                ab.switchReversible();
            }
        }
        return 0;
    }
    
    /**
     * Handles mouse dragging action
     */
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
    
    /**
     * Handles the mouse release action
     */
    void mouseReleased() {
        for(ArrowBar a : arrows)
            a.setPressed(false);
    }
    
    /**
     * Handles the mouse press action
     */
    void mousePressed() {
        for(ArrowBar a : arrows) {
            if(a.getDragArrowIcon().contains(CustomAttacks.mousePosition))
                a.setPressed(true);
        }
    }
    
    /**
     * Handles the arrowbar order changing stuff. (Needs fixing – complete revamp)
     */
    private void order() {
        for(int i = 0; i < arrows.size(); ++i) {
            for(int j = i + 1; j < arrows.size(); ++j) {
                if(arrows.get(i).getOrderIntersection().intersects(arrows.get(j).getOrderIntersection())) {
                    Collections.swap(arrows, i, j);
                    return;
                }
            }
        }
    }
    
}
