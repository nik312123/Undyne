package customAttackMaker;

import defense.Runner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class BottomMenuBar {
    private int y = 548;
    private final int CHECKBOX_X = 353;
    
    private boolean isShowing = true;
    private boolean isRobotBoxChecked = false;
    private boolean isGenocideBoxChecked = false;
    
    private String checkBoxMode;
    
    private Rectangle tab = new Rectangle();
    private Rectangle checkbox = new Rectangle(CHECKBOX_X, y + 31, 13, 12);
    private Rectangle play = new Rectangle(5, y + 28, 20, 18);
    private Rectangle stop = new Rectangle(31, y + 28, 20, 18);
    private final Rectangle EXPORT = new Rectangle(457, 576, 53, 17);
    private final Rectangle IMPORT = new Rectangle(518, 576, 53, 17);
    
    public void work(Graphics g) {
        tab.setBounds(528, y, 46, 25);
        g.drawImage(Runner.bottomMenuBar, 0, y, null);
        if(!isThereAnEmptyAttack()) {
            g.drawImage(Runner.bottomPlayButton, 5, y + 28, null);
            g.drawImage(Runner.bottomStopButton, 31, y + 28, null);
            g.drawImage(Runner.exportButton, 456, y + 28, null);
        }
        else {
            g.drawImage(Runner.bottomPlayButtonDisabled, 5, y + 28, null);
            g.drawImage(Runner.bottomStopButtonDisabled, 31, y + 28, null);
            g.drawImage(Runner.exportButtonDisabled, 456, y + 28, null);
        }
        if(isShowing) {
            g.drawImage(Runner.bottomTabDown, 541, y + 7, null);
            if(--y < 548)
                y = 548;
        }
        else {
            g.drawImage(Runner.bottomTabUp, 541, y + 7, null);
            if(++y > 580)
                y = 580;
        }
        drawBarCheck(g);
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
    
    private boolean isThereAnEmptyAttack() {
        if(CustomAttacks.attacks.size() == 0)
            return true;
        for(AttackBar a : CustomAttacks.attacks)
            if(a.getArrows().size() == 0)
                return true;
        return false;
    }
    
    public int mouseWorks(Point mousePosition) {
        play.setLocation(5, y + 28);
        stop.setLocation(31, y + 28);
        if(tab.contains(mousePosition))
            isShowing = !isShowing;
        else if(EXPORT.contains(mousePosition) && isShowing && CustomAttacks.attacks.size() >= 1 && CustomAttacks.attacks.get(0).getArrows().size() >= 1)
            return 1;
        else if(IMPORT.contains(mousePosition) && isShowing)
            return 0;
        else if(play.contains(mousePosition) && !Runner.canBeStopped && CustomAttacks.attacks.size() != 0 && !CustomAttacks.areAnyAttacksEmpty())
            Runner.play(false);
        else if(stop.contains(mousePosition) && Runner.canBeStopped)
            Runner.stop(false);
        return -1;
    }
    
    public Rectangle getBarCheckBox() {
        checkbox.setLocation(CHECKBOX_X, y + 31);
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
    
    public boolean isRobotBoxChecked() {
        return isRobotBoxChecked;
    }
    
}
