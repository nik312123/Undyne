package customAttackMaker;

import defense.Runner;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class BottomMenuBar extends JPanel {
    private int y = 0;
    private final int CHECKBOX_X = 353;
    
    private boolean isShowing = true;
    private boolean isRobotBoxChecked = false;
    private boolean isGenocideBoxChecked = false;
    
    private String checkBoxMode;
    
    private Rectangle tab = new Rectangle();
    private Rectangle checkbox = new Rectangle(CHECKBOX_X, y + 31 + 548, 13, 12);
    private Rectangle play = new Rectangle(5, y + 28 + 548, 20, 18);
    private Rectangle stop = new Rectangle(31, y + 28 + 548, 20, 18);
    private final Rectangle EXPORT = new Rectangle(457, 576, 53, 17);
    private final Rectangle IMPORT = new Rectangle(518, 576, 53, 17);

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 600, 600);
        if(Runner.bottomBarShouldDraw()) {
            tab.setBounds(528, y, 46, 25);
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
        g.setColor(Color.PINK);
    }
    
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
    
    private boolean noFieldsAreEmpty() {
        for(AttackBar attBar : CustomAttacks.attacks) {
            for(ArrowBar arrBar : attBar.getArrows()) {
                if(arrBar.emptyFieldExists())
                    return false;
            }
        }
        return true;
    }
    
    private boolean isThereAnEmptyAttack() {
        if(CustomAttacks.attacks.size() == 0)
            return true;
        for(AttackBar a : CustomAttacks.attacks)
            if(a.getArrows().size() == 0)
                return true;
        return false;
    }
    
    private boolean isAttacksEmpty() {
        for(AttackBar a : CustomAttacks.attacks) {
            if(a.getArrows().size() > 0)
                return false;
        }
        return true;
    }
    
    public int mouseWorks(Point mousePosition) {
        play.setLocation(5, y + 28 + 548);
        stop.setLocation(31, y + 28 + 548);
        if(tab.contains(mousePosition))
            isShowing = !isShowing;
        else if(EXPORT.contains(mousePosition) && isShowing && !isAttacksEmpty() && !isThereAnEmptyAttack())
            return 1;
        else if(IMPORT.contains(mousePosition) && isShowing)
            return 0;
        else if(play.contains(mousePosition) && !Runner.canBeStopped && CustomAttacks.attacks.size() != 0 && !CustomAttacks.areAnyAttacksEmpty() && noFieldsAreEmpty())
            Runner.play(false);
        else if(stop.contains(mousePosition) && Runner.canBeStopped)
            Runner.stop(false);
        return -1;
    }
    
    public Rectangle getBarCheckBox() {
        checkbox.setLocation(CHECKBOX_X, y + 31 + 548);
        return checkbox;
    }
    
    public void flipIsGenocideBoxChecked() {
        isGenocideBoxChecked = !isGenocideBoxChecked;
    }
    
    public void flipIsRobotBoxedChecked() {
        isRobotBoxChecked = !isRobotBoxChecked;
    }
    
    public boolean isGenocideBoxChecked() {
        return isGenocideBoxChecked;
    }
    
    void setIsGenocideBoxChecked(boolean checked) {
        isGenocideBoxChecked = checked;
    }
    
    public boolean isRobotBoxChecked() {
        return isRobotBoxChecked;
    }
    
    public int getYValue() {
        return y;
    }
    
}
