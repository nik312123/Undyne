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
     * The y position of the bottom menu bar
     */
    private int y = 0;

    /**
     * The x position of the checkbox
     */
    private final int CHECKBOX_X = 353;

    /**
     * Is when the menu bar is opened and false when closed
     */
    private boolean isShowing = true;

    /**
     * Changes to true if robot checkbox is checked
     */
    private boolean isRobotBoxChecked = false;

    /**
     * Changes to true if robot checkbox is checked
     */
    private boolean isGenocideBoxChecked = false;

    /**
     * The string used for whether "Undyne" should be drawn or "Automatic"
     */
    private String checkBoxMode;

    /**
     * A tab shaped button that opens and closes the bottom menu bar
     */
    private Rectangle tab = new Rectangle();

    /**
     * The checkbox rectangle used to set bounds for clicking
     */
    private Rectangle checkbox = new Rectangle(CHECKBOX_X, y + 31 + 548, 13, 12);
    /**
     * The play button's rectangle used to set bounds for clicking
     */
    private Rectangle play = new Rectangle(5, y + 28 + 548, 20, 18);

    /**
     * The stop button's rectangle used to set bounds for clicking
     */
    private Rectangle stop = new Rectangle(31, y + 28 + 548, 20, 18);

    /**
     * The export button's rectangle used to set bounds for clicking
     */
    private final Rectangle EXPORT = new Rectangle(457, 576, 53, 17);

    /**
     * The import button's rectangle used to set bounds for clicking
     */
    private final Rectangle IMPORT = new Rectangle(518, 576, 53, 17);

    /**
     * The main paint method
     * @param g Graphics object
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
            if(isShowing) {
                g.drawImage(Runner.bottomTabDown, 541, y + 7, null);
                if(--y < 0)
                    y = 0;
            }
            else {
                g.drawImage(Runner.bottomTabUp, 541, y + 7, null);
                if(++y > 32)
                    y = 32;
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
     * Method draws the check box
     * @param g Graphics object
     */
    private void drawBarCheck(Graphics g) {
        g.drawImage(Runner.checkBox, CHECKBOX_X, y + 31, null);
        if(Runner.isCustomAttack && isGenocideBoxChecked || Runner.canBeStopped && isRobotBoxChecked)
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
     * false if there are any empty fields
     * @return Boolean value of whether or not any fields are empty
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
     * True is at least one attack that is empty
     * @return Boolean value of whether or not there is at least one attack that is empty
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
     * True if all of the attacks are empty
     * @return boolean value of whether all of the attacks are empty
     */
    private boolean isAttacksEmpty() {
        for(AttackBar a : CustomAttacks.attacks) {
            if(a.getArrows().size() > 0)
                return false;
        }
        return true;
    }

    /**
     *  All mouse action like clicks happen here
     * @param mousePosition a rectangle object used to check intersections
     * @return An int value that represents whether the export button was clicked or the import
     */
    public int mouseWorks(Point mousePosition) {
        play.setLocation(5, y + 28 + 548);
        stop.setLocation(31, y + 28 + 548);
        if(tab.contains(mousePosition)) {
            StartScreen.playClick();
            isShowing = !isShowing;
        }
        else if(EXPORT.contains(mousePosition) && isShowing && !isAttacksEmpty())
            return 1;
        else if(IMPORT.contains(mousePosition) && isShowing)
            return 0;
        else if(play.contains(mousePosition) && !Runner.canBeStopped && CustomAttacks.attacks.size() != 0 && !CustomAttacks.areAnyAttacksEmpty() && noFieldsAreEmpty()) {
            StartScreen.playClick();
            Runner.play(false);
        }
        else if(stop.contains(mousePosition) && Runner.canBeStopped) {
            StartScreen.playClick();
            Runner.stop(false);
        }
        return -1;
    }

    /**
     * Returns check box's bounds
     * @return Rectangle object
     */
    public Rectangle getBarCheckBox() {
        checkbox.setLocation(CHECKBOX_X, y + 31 + 548);
        return checkbox;
    }

    /**
     * Toggles the Genocide check box
     */
    public void flipIsGenocideBoxChecked() {
        isGenocideBoxChecked = !isGenocideBoxChecked;
    }

    /**
     * Toggles the Robot check box
     */
    public void flipIsRobotBoxedChecked() {
        isRobotBoxChecked = !isRobotBoxChecked;
    }

    /**
     * Returns true if Genocide box is checked
     * @return Boolean Object
     */
    public boolean isGenocideBoxChecked() {
        return isGenocideBoxChecked;
    }

    /**
     * Sets GenocideBox to true or false
     * @param checked boolean value of what genocideBox should be set to
     */
    void setIsGenocideBoxChecked(boolean checked) {
        isGenocideBoxChecked = checked;
    }

    /**
     * Returns true if robot check box is checked
     * @return boolen value of if robot check box is checked
     */
    public boolean isRobotBoxChecked() {
        return isRobotBoxChecked;
    }

    /**
     * Returns the Y position of the menu bar
     * @return int value
     */
    public int getYValue() {
        return y;
    }
    
}
