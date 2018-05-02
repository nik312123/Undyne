package customAttackMaker;

import defense.Runner;

import java.awt.Graphics;
import java.awt.Rectangle;

class BottomMenuBar {
    private static int y = 580;
    
    private static boolean isShowing = false;
    
    private static Rectangle tab = new Rectangle();
    private static final Rectangle EXPORT = new Rectangle(457, 576, 53, 17);
    private static final Rectangle IMPORT = new Rectangle(518, 576, 53, 17);
    
    void work(Graphics g) {
        tab.setBounds(528, y, 46, 25);
        if(isShowing) {
            if(CustomAttacks.attacks.size() >= 1 && CustomAttacks.attacks.get(0).getArrows().size() >= 1)
                g.drawImage(Runner.bottomMenuBar, 0, y, null);
            else
                g.drawImage(Runner.bottomMenuBarButNoArrowsShowing, 0, y, null);
            if(--y < 548)
                y = 548;
        }
        else {
            if(isThereAnEmptyAttack())
                g.drawImage(Runner.bottomBarNotShowing, 0, y, null);
            else
                g.drawImage(Runner.bottomMenuBarButNoArrowsNotShowing, 0, y, null);
            if(++y > 580)
                y = 580;
        }
    }
    
    private boolean isThereAnEmptyAttack() {
        for(AttackBar a : CustomAttacks.attacks)
            if(a.getArrows().size() == 0)
                return true;
        return false;
    }
    
    int mouseWorks() {
        if(CustomAttacks.mouse.intersects(tab))
            isShowing = !isShowing;
        else if(CustomAttacks.attacks.size() >= 1 && CustomAttacks.attacks.get(0).getArrows().size() >= 1 && CustomAttacks.mouse.intersects(EXPORT) && isShowing)
            return 1;
        else if(CustomAttacks.mouse.intersects(IMPORT) && isShowing)
            return 0;
        return -1;
    }
    
}
