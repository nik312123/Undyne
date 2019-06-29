package customAttackMaker;

import defense.Runner;
import defense.StartScreen;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class BottomMenuBar extends JPanel {
    /**
     * The change in the y-position of the {@code BottomMenuBar} (y = 0 is where it is showing completely)
     */
    private int y = 0;
    
    /**
     * The x-position of the {@code BottomMenuBar} checkbox
     */
    private final int CHECKBOX_X = 353;
    
    /**
     * True if the {@code BottomMenuBar} should be showing and false if it should not
     */
    private boolean showing = true;
    
    /**
     * True if the robot (automatic play) checkbox is checked
     */
    private boolean robotBoxChecked = false;
    
    /**
     * True if the genocide (Undyne's undying mode) checkbox is checked
     */
    private boolean genocideBoxChecked = false;
    
    /**
     * The string used for whether "Undyne" should be drawn or "Automatic" for the checkbox
     */
    private String checkBoxMode;
    
    /**
     * A tab-shaped rectangle used for showing and hiding the {@code BottomMenuBar}
     */
    private Rectangle tab = new Rectangle();
    
    /**
     * The bounds of the {@code BottomMenuBar} checkbox
     */
    private Rectangle checkbox = new Rectangle(CHECKBOX_X, y + 31 + 548, 13, 12);
    
    /**
     * The bounds of the {@code BottomMenuBar} play button
     */
    private Rectangle play = new Rectangle(5, y + 28 + 548, 20, 18);
    
    /**
     * The bounds of the {@code BottomMenuBar} stop button
     */
    private Rectangle stop = new Rectangle(31, y + 28 + 548, 20, 18);
    
    /**
     * The bounds of the {@code BottomMenuBar} export button
     */
    private final Rectangle EXPORT = new Rectangle(457, 576, 53, 17);
    
    /**
     * The bounds of the {@code BottomMenuBar} import button
     */
    private final Rectangle IMPORT = new Rectangle(518, 576, 53, 17);
    
    /**
     * Draws the {@code BottomMenuBar}
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        if(Runner.bottomBarShouldDraw()) {
            tab.setBounds(528, y + 548, 46, 25);
            g.drawImage(Runner.bottomMenuBar, 0, y, null);
            boolean emptyAttackExists = isThereAnEmptyAttack();
            if(!emptyAttackExists && noFieldsAreEmpty()) {
                g.drawImage(Runner.bottomPlayButton, 5, y + 28, null);
                g.drawImage(Runner.bottomStopButton, 31, y + 28, null);
            }
            else {
                g.drawImage(Runner.bottomPlayButtonDisabled, 5, y + 28, null);
                g.drawImage(Runner.bottomStopButtonDisabled, 31, y + 28, null);
            }
            if(emptyAttackExists && isAttacksEmpty())
                g.drawImage(Runner.exportButtonDisabled, 456, y + 28, null);
            else
                g.drawImage(Runner.exportButton, 456, y + 28, null);
            if(showing) {
                g.drawImage(Runner.bottomTabDown, 541, y + 7, null);
                y = Math.max(y - 1, 0);
            }
            else {
                g.drawImage(Runner.bottomTabUp, 541, y + 7, null);
                y = Math.min(y + 1, 32);
            }
            
            drawBarCheck(g);
        }
        if(Runner.windowNotFocused()) {
            g.setColor(new Color(255, 255, 255, 127));
            Rectangle tabBounds = tab.getBounds();
            g.fillRect(tabBounds.x - getX(), tabBounds.y - getY(), tabBounds.width, tabBounds.height);
            g.fillRect(0, y + 25, 600, 52);
        }
    }
    
    /**
     * Draws the checkbox for the {@code BottomMenuBar}
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawBarCheck(Graphics g) {
        g.drawImage(Runner.checkBox, CHECKBOX_X, y + 31, null);
        if(Runner.isCustomAttack && genocideBoxChecked || Runner.canBeStopped && robotBoxChecked)
            g.drawImage(Runner.ticked, CHECKBOX_X, y + 31, null);
        if(Runner.isCustomAttack)
            checkBoxMode = "Undying";
        else if(Runner.canBeStopped)
            checkBoxMode = "Automatic";
        g.setFont(Runner.deteFontSpeech);
        g.setColor(Color.WHITE);
        g.drawString(checkBoxMode, CHECKBOX_X + 17, y + 41);
    }
    
    /**
     * Returns true if all {@code NumberFieldFocus} instances have been filled
     *
     * @return True if all {@code NumberFieldFocus} instances have been filled
     */
    private boolean noFieldsAreEmpty() {
        for(AttackBar attBar : CustomAttacks.attacks) {
            for(ArrowBar arrBar : attBar.getArrows()) {
                if(arrBar.emptyFieldExists())
                    return false;
            }
        }
        return true;
    }
    
    /**
     * Returns true if at least one {@code AttackBar} has no {@code ArrowBar}s under it
     *
     * @return True if at least one {@code AttackBar} has no {@code ArrowBar}s under it
     */
    private boolean isThereAnEmptyAttack() {
        if(CustomAttacks.attacks.size() == 0)
            return true;
        for(AttackBar a : CustomAttacks.attacks)
            if(a.getArrows().size() == 0)
                return true;
        return false;
    }
    
    /**
     * Returns true if there aren't any {@code AttackBar} that have {@code ArrowBar}s under it
     *
     * @return True if there aren't any {@code AttackBar} that have {@code ArrowBar}s under it
     */
    private boolean isAttacksEmpty() {
        for(AttackBar a : CustomAttacks.attacks) {
            if(a.getArrows().size() > 0)
                return false;
        }
        return true;
    }
    
    /**
     * All mouse action like clicks happen here
     *
     * @param mousePosition a rectangle object used to check intersections
     * @return An int value that represents whether the export button was clicked or the import
     */
    public int mouseWorks(Point mousePosition) {
        play.setLocation(5, y + 28 + 548);
        stop.setLocation(31, y + 28 + 548);
        if(tab.contains(mousePosition)) {
            StartScreen.playClick();
            showing = !showing;
        }
        else if(EXPORT.contains(mousePosition) && showing && !isAttacksEmpty())
            return 1;
        else if(IMPORT.contains(mousePosition) && showing)
            return 0;
        else if(play.contains(mousePosition) && !Runner.canBeStopped && CustomAttacks.attacks.size() != 0 && !CustomAttacks.areAnyAttacksEmpty() && noFieldsAreEmpty()) {
            StartScreen.playClick();
            Runner.playCreatorAttacks(false);
        }
        else if(stop.contains(mousePosition) && Runner.canBeStopped) {
            StartScreen.playClick();
            Runner.stop(false);
        }
        return -1;
    }
    
    /**
     * Returns the bounds of the {@code BottomMenuBar} checkbox
     *
     * @return The bounds of the {@code BottomMenuBar} checkbox
     */
    public Rectangle getCheckbox() {
        checkbox.setLocation(CHECKBOX_X, y + 31 + 548);
        return checkbox;
    }
    
    /**
     * Toggles whether the genocide (Undyne's undying mode) is activated
     */
    public void flipIsGenocideBoxChecked() {
        genocideBoxChecked = !genocideBoxChecked;
    }
    
    /**
     * Toggles whether the robot (automatic play mode) is activated
     */
    public void flipIsRobotBoxedChecked() {
        robotBoxChecked = !robotBoxChecked;
    }
    
    /**
     * Returns true if the genocide (Undyne's undying mode) checkbox is checked
     *
     * @return True if the genocide (Undyne's undying mode) checkbox is checked
     */
    public boolean isGenocideBoxChecked() {
        return genocideBoxChecked;
    }
    
    /**
     * Sets whether the genocide (Undyne's undying mode) checkbox is checked to the given value
     *
     * @param checked Whether the genocide (Undyne's undying mode) checkbox is checke
     */
    void setIsGenocideBoxChecked(boolean checked) {
        genocideBoxChecked = checked;
    }
    
    /**
     * Returns true if the robot (automatic play) checkbox is checked
     *
     * @return True if the robot (automatic play) checkbox is checked
     */
    public boolean isRobotBoxChecked() {
        return robotBoxChecked;
    }
    
    /**
     * Returns the change in the y-position of the {@code BottomMenuBar} (y = 0 is where it is showing completely)
     *
     * @return The change in the y-position of the {@code BottomMenuBar}
     */
    public int getYValue() {
        return y;
    }
    
}
