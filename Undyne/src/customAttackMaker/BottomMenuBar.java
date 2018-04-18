package customAttackMaker;

import defense.Runner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BottomMenuBar {
    
    private static boolean isShowing = false;
    
    private static Rectangle tab = new Rectangle();
    
    private static final Rectangle EXPORT = new Rectangle(457,576,53,17);
    
    private static final Rectangle IMPORT = new Rectangle(518,576,53,17);
    
    
    private static int y = 580;
    
    public void work(Graphics g){
        tab.setBounds(528,y,46,25);
        if(isShowing) {
            if(CustomAttacks.attacks.size() >= 1 && CustomAttacks.attacks.get(0).getArrows().size() >= 1)
            g.drawImage(Runner.bottomMenuBar, 0, y, null);
            else
                g.drawImage(Runner.bottomMenuBarButNoArrowsShowing, 0, y, null);
            y--;
            if(y < 548)
                y = 548;
        } else {
            if(CustomAttacks.attacks.size() >= 1 && CustomAttacks.attacks.get(0).getArrows().size() >= 1)
                g.drawImage(Runner.bottomBarNotShowing, 0, y, null);
            else
                g.drawImage(Runner.bottomMenuBarButNoArrowsNotShowing, 0, y, null);
            y++;
            if(y > 580)
                y = 580;
    
        }
    
    }
    
    public int mouseWorks(){
        if(CustomAttacks.mouse.intersects(tab)) {
            isShowing = !isShowing;
            return -1;
        }
    
        if(CustomAttacks.attacks.size() >= 1 && CustomAttacks.attacks.get(0).getArrows().size() >= 1) {
    
            if(CustomAttacks.mouse.intersects(EXPORT) && isShowing)
                return 1;
    
           
        }
    
        if(CustomAttacks.mouse.intersects(IMPORT) && isShowing)
            return 0;
            
    return -1;
    }
    
}
