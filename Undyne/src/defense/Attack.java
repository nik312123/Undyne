package defense;

import nikunj.classes.Sound;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Performs operations for running the arrows and attack for the game
 */
class Attack {
    
    /**
     * Counter to check if the delay from the last arrow is finished and the next arrow should be launched
     */
    private int delayCounter = 0;
    
    /**
     * Added to screenShift to be used for the position of all of the elements to be shifted
     */
    private int screenShiftAdder = 1;
    
    /**
     * Counter that resets screen shifting once it hits two
     */
    private int screenShiftCounter = 0;
    
    /**
     * The position shift for the position of all of the elements to be shifted
     */
    private int screenShift = 0;
    
    /**
     * The counter that delays the next attack until it reaches 125
     */
    private int attackDelay = 0;
    
    /**
     * The delay of the last arrow added
     */
    private int lastDelay = 0;
    
    /**
     * If survival, this is the attack before the next level is reached
     */
    private int lastImportantAttack = 0;
    
    /**
     * True if the arrow is the first arrow in all of the attacks
     */
    private boolean first = true;
    
    /**
     * The volume used for the blocking and damage sfx
     */
    private static double volume = 1;
    
    /**
     * True if the player has been damaged
     */
    private boolean damaged = false;
    
    /*
     * List of Arrow objects that are used in the attacks
     */
    private ArrayList<Arrow> attackPattern;
    
    /**
     * Used for determining whether an arrow has hit the shield or hit the player
     */
    private ArrowStatus hit = ArrowStatus.NEITHER;
    
    /**
     * The Attacks instance that gives Attack arrows
     */
    private Attacks a;
    
    /**
     * Sound used when an arrow is blocked by the shield
     */
    private static Sound block;
    
    /**
     * Sound used when an arrow hits the heart
     */
    private static Sound damage;
    
    /**
     * Whether an arrow has hit the shield, damaged the player, or neither
     */
    private enum ArrowStatus {
        HIT,
        DAMAGED,
        NEITHER}
    
    /**
     * Creates a new {@code Attack}
     *
     * @param a The Attacks reference that passes Attack arrows
     */
    Attack(Attacks a) {
        this.attackPattern = new ArrayList<>();
        this.a = a;
        if(Runner.isFirstTime) {
            try {
                block = new Sound(Runner.class.getResource("/block.ogg"), false);
                damage = new Sound(Runner.class.getResource("/damage.ogg"), false);
            }
            catch(UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Calls the tick method of each arrow in attackPattern
     */
    private void tick() {
        for(Arrow a : attackPattern)
            a.tick();
    }
    
    /**
     * Adds an arrow to the attack pattern
     *
     * @param a The arrow to be added to the attack pattern
     */
    private void addArrow(Arrow a) {
        attackPattern.add(a);
    }
    
    /**
     * Reverses arrows if they are in range, removes arrows if they have hit the shield or damage the player, and
     * returns whether any arrows have hit the shield, damaged the player, or neither
     *
     * @param dir The direction the shield is facing
     * @param p   The Player object
     * @return Whether any arrows have hit the shield, damaged the player, or neither
     */
    private ArrowStatus removeArrow(char dir, Player p) {
        //Used to determine whether or not the arrow has been hit by the shield or has damaged the player
        boolean hit = false;
        boolean damage = false;
        
        //Goes through all of the arrows in the attack pattern to check if any arrow has been hit by the shield or has damaged the player
        //If that is the case, the loop is exited
        for(int i = 0; i < attackPattern.size() && !hit && !damage; ++i) {
            Arrow tempArrow = attackPattern.get(i);
            
            //Gets an xShift and yShift that represents the point of the arrow's location relative to the upper-left corner of the image
            int xShift = 0, yShift = 0;
            switch(tempArrow.getDir()) {
                case 'r':
                    if(tempArrow.getReverse()) {
                        xShift = 22;
                        yShift = 15;
                    }
                    else {
                        xShift = 30;
                        yShift = 17;
                    }
                    break;
                case 'l':
                    if(tempArrow.getReverse()) {
                        yShift = 17;
                        xShift = 10;
                    }
                    else {
                        yShift = 17;
                        xShift = 1;
                    }
                    break;
                case 'd':
                    if(tempArrow.getReverse()) {
                        xShift = 17;
                        yShift = 22;
                    }
                    else {
                        xShift = 17;
                        yShift = 30;
                    }
                    break;
                case 'u':
                    if(tempArrow.getReverse()) {
                        yShift = 10;
                        xShift = 15;
                    }
                    else {
                        yShift = 1;
                        xShift = 17;
                    }
                    break;
            }
            
            //If the arrow is a reverse arrow, this starts the reverse arrow's rotation process within a certain distance of the center
            if(tempArrow.getDirectionNotSwitched() && ((tempArrow.getDir() == 'l' && tempArrow.getX() + xShift - 300 <= 72 && tempArrow.getX() + xShift - 300 > 0) || (tempArrow.getDir() == 'r' && 300 - (tempArrow.getX() + xShift) <= 72 && 300 - (tempArrow.getX() + xShift) > 0) || (tempArrow.getDir() == 'd' && 300 - (tempArrow.getY() + yShift) <= 72 && 300 - (tempArrow.getY() + yShift) > 0) || (tempArrow.getDir() == 'u' && (tempArrow.getY() + yShift) - 300 <= 72 && (tempArrow.getY() + yShift) - 300 > 0)))
                tempArrow.switchDir();
            
            //Checks whether or not the arrow has damaged the player or hit the shield based on the position of the arrow and the angle the shield is at
            int angle = p.getAngle();
            switch(tempArrow.getDir()) {
                case 'l':
                    if(dir == 'r' && (angle <= 135 && angle >= 45 || p.getDamaged())) {
                        if(tempArrow.getX() + xShift < 266 + 67 && tempArrow.getY() == 270 + 11) {
                            if(!tempArrow.getDirectionNotSwitched() && (tempArrow.notInside() || p.getDamaged())) {
                                hit = true;
                                attackPattern.remove(i);
                            }
                        }
                    }
                    if(!tempArrow.getDirectionNotSwitched() && !hit && tempArrow.getX() + xShift < 291 + 19 && tempArrow.getY() == 270 + 11) {
                        attackPattern.remove(i);
                        damage = true;
                    }
                    break;
                case 'r':
                    if(dir == 'l' && angle <= 315 && angle >= 225) {
                        if(tempArrow.getX() + xShift > 266 && tempArrow.getY() == 270 + 11) {
                            if(!tempArrow.getDirectionNotSwitched() && (tempArrow.notInside() || p.getDamaged())) {
                                hit = true;
                                attackPattern.remove(i);
                            }
                        }
                    }
                    if(!tempArrow.getDirectionNotSwitched() && !hit && tempArrow.getX() + xShift > 291 && tempArrow.getY() == 270 + 11) {
                        attackPattern.remove(i);
                        damage = true;
                    }
                    break;
                case 'u':
                    if(dir == 'd' && (angle <= 225 && angle >= 135 || p.getDamaged())) {
                        if(tempArrow.getY() + yShift < 266 + 67 && tempArrow.getX() == 285) {
                            if(!tempArrow.getDirectionNotSwitched() && (tempArrow.notInside() || p.getDamaged())) {
                                hit = true;
                                attackPattern.remove(i);
                            }
                        }
                    }
                    if(!tempArrow.getDirectionNotSwitched() && !hit && tempArrow.getY() + yShift < 291 + 19 && tempArrow.getX() == 285) {
                        attackPattern.remove(i);
                        damage = true;
                    }
                    break;
                case 'd':
                    if(dir == 'u' && (angle <= 45 || angle >= 315 || p.getDamaged())) {
                        if(attackPattern.get(i).getY() + yShift > 266 && tempArrow.getX() == 285) {
                            if(!tempArrow.getDirectionNotSwitched() && (tempArrow.notInside() || p.getDamaged())) {
                                hit = true;
                                attackPattern.remove(i);
                            }
                        }
                    }
                    if(!tempArrow.getDirectionNotSwitched() && !hit && tempArrow.getY() + yShift > 291 && tempArrow.getX() == 285) {
                        attackPattern.remove(i);
                        damage = true;
                    }
                    break;
            }
        }
        
        //Returns whether the arrow has been hit by the shield, damaged the player, or neither
        if(hit)
            return ArrowStatus.HIT;
        else if(damage) {
            if(!p.getDamaged()) {
                p.damage();
                return ArrowStatus.DAMAGED;
            }
        }
        return ArrowStatus.NEITHER;
    }
    
    /**
     * Draws each arrow in attackPattern with the arrow that was added earliest from the group being the red one
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void draw(Graphics g) {
        if(attackPattern.size() != 0) {
            attackPattern.get(0).draw(g, Arrow.ArrowColor.RED);
            for(int i = 1; i < attackPattern.size(); ++i) {
                attackPattern.get(i).draw(g, Arrow.ArrowColor.BLUE);
            }
        }
    }
    
    /**
     * Moves the arrows, delays attacks and arrows accordingly, adjusts level for survival, adds arrows to
     * attackPattern, performs actions based on if an arrow has hit the shield, damaged the player or neither, and calls
     * the method to draw the arrows
     *
     * @param g The graphics object used for drawing the Runner JPanel
     * @param p The Player object
     */
    void spawnArrows(Graphics g, Player p) {
        //Moves all of the arrows accordingly
        tick();
        
        //If an attack is over and the next one is coming up
        if(a.isNewAttack()) {
            
            //Delays until next attack should start
            if(++attackDelay == 125) {
                a.notNewAttack();
                attackDelay = 0;
            }
            
            //If the mode is survival, it checks what attack number the game is at and adjusts the level accordingly
            int currentAttack = a.getCurrentAttack();
            if(Runner.isSurvival() && (currentAttack == 8 && lastImportantAttack != 8 || currentAttack == 43 && lastImportantAttack != 43 || currentAttack == 79 && lastImportantAttack != 79) && attackPattern.size() == 0 && attackDelay == 100) {
                if(currentAttack == 8)
                    lastImportantAttack = 8;
                else if(currentAttack == 43) {
                    lastImportantAttack = 43;
                    Runner.changeUndyneGif();
                }
                else {
                    lastImportantAttack = 79;
                    Runner.finalBoost();
                }
                Runner.changeMain();
            }
        }
        
        //Adds arrow to attackPattern if the delay from the last arrow is over or it is the first arrow in all of the attacks
        else if(++delayCounter == lastDelay || first) {
            first = false;
            Arrow temp = a.getCurrentArrow();
            if(temp != null) {
                addArrow(temp);
                lastDelay = temp.getDelay();
            }
            delayCounter = 0;
        }
        hit = removeArrow(p.getDir(), p);
        block.changeVolume(volume);
        damage.changeVolume(volume);
        
        //If an arrow has hit the shield or damaged the player, actions are taken accordingly
        if(hit == ArrowStatus.HIT) {
            p.setRedZero();
            block.play();
        }
        else if(hit == ArrowStatus.DAMAGED && !p.getDamaged()) {
            damaged = true;
            damage.play();
            p.setDamaged(true);
        }
        
        //If the player has been damaged and the animation for the damage (shifting screen) is still going, the animation will continue
        if(damaged) {
            p.setElementPosition(screenShift);
            screenShift += screenShiftAdder;
            if(screenShift == 2)
                screenShiftAdder *= -1;
            else if(screenShift == -2) {
                screenShiftAdder *= -1;
                ++screenShiftCounter;
            }
            if(screenShiftCounter == 2 && screenShift == 0) {
                screenShiftCounter = 0;
                screenShift = 0;
                damaged = false;
                p.setElementPosition(0);
            }
        }
        
        //Draws the arrows
        draw(g);
    }
    
    /**
     * Returns the attackPattern list of arrows that are being drawn on screen
     *
     * @return The attackPattern list of arrows that are being drawn on screen
     */
    ArrayList<Arrow> getList() {
        return attackPattern;
    }
    
    /**
     * Resets certain variables of the attack object when the stop button is pressed in custom attacks
     */
    void resetVars() {
        first = true;
        hit = ArrowStatus.NEITHER;
    }
    
    /**
     * Changes the volume to the given sfx volume
     *
     * @param change The new sfx volume
     */
    void changeVol(double change) {
        volume = change;
    }
    
}
