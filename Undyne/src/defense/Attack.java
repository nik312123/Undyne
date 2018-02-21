package defense;

import nikunj.classes.Sound;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

class Attack {
    private int counter = 0;
    private static boolean isFirst = true;
    /*
     * List of Arrow objects that make up one attack
     */
    private ArrayList<Arrow> attackPattern;
    
    private static String hit = "";
    private int adder = 1;
    private int hitPoint = 0;
    private int move = 0;
    private int attackDelay = 0;
    private int lastDelay = 0;
    private int lastImportantAttack = 0;
    private static double volume = 1;
    
    private boolean isDamaged = false;
    
    private Attacks a;
    
    private static Sound block;
    private static Sound damage;
    
    /*
     * Constructor for constant delay
     */
    Attack(ArrayList<Arrow> attackPattern, Attacks a) {
        this.attackPattern = attackPattern;
        this.a = a;
        if(Runner.isFirstTime) {
            try {
                block = new Sound(Runner.class.getResource("/block.wav"), false);
                damage = new Sound(Runner.class.getResource("/damage.wav"), false);
            }
            catch(UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void tick() {
        for(Arrow a : attackPattern)
            a.tick();
    }
    
    private void addArrow(Arrow a) {
        attackPattern.add(a);
    }
    
    private String removeArrow(char dir, Player p) {
        boolean hit = false;
        boolean damage = false;
        if(attackPattern.size() == 0)
            return "";
        for(int i = 0; i < attackPattern.size() && !hit && !damage; ++i) {
            Arrow tempArrow = attackPattern.get(i);
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
            if(tempArrow.getDirectionNotSwitched() && ((tempArrow.getDir() == 'l' && tempArrow.getX() + xShift - 300 <= 72 && tempArrow.getX() + xShift - 300 > 0) || (tempArrow.getDir() == 'r' && 300 - (tempArrow.getX() + xShift) <= 72 && 300 - (tempArrow.getX() + xShift) > 0) || (tempArrow.getDir() == 'd' && 300 - (tempArrow.getY() + yShift) <= 72 && 300 - (tempArrow.getY() + yShift) > 0) || (tempArrow.getDir() == 'u' && (tempArrow.getY() + yShift) - 300 <= 72 && (tempArrow.getY() + yShift) - 300 > 0)))
                tempArrow.switchDir();
            int angle = p.getAngle();
            switch(tempArrow.getDir()) {
                case 'l':
                    if(dir == 'r' && (angle <= 135 && angle >= 45 || p.getHit())) {
                        if(tempArrow.getX() + xShift < 266 + 67 && tempArrow.getY() == 270 + 11) {
                            if(!tempArrow.getDirectionNotSwitched() && (!tempArrow.getInside() || p.getHit())) {
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
                            if(!tempArrow.getDirectionNotSwitched() && (!tempArrow.getInside() || p.getHit())) {
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
                    if(dir == 'd' && (angle <= 225 && angle >= 135 || p.getHit())) {
                        if(tempArrow.getY() + yShift < 266 + 67 && tempArrow.getX() == 285) {
                            if(!tempArrow.getDirectionNotSwitched() && (!tempArrow.getInside() || p.getHit())) {
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
                    if(dir == 'u' && (angle <= 45 || angle >= 315 || p.getHit())) {
                        if(attackPattern.get(i).getY() + yShift > 266 && tempArrow.getX() == 285) {
                            if(!tempArrow.getDirectionNotSwitched() && (!tempArrow.getInside() || p.getHit())) {
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
        if(hit)
            return "H";
        else if(damage) {
            if(!p.getHit()) {
                p.damage();
                return "D";
            }
        }
        return "";
    }
    
    private void draw(Graphics g) {
        if(attackPattern.size() != 0) {
            attackPattern.get(0).draw(g, Color.RED);
            for(int i = 1; i < attackPattern.size(); ++i) {
                attackPattern.get(i).draw(g, Color.BLUE);
            }
        }
    }
    
    void spawnArrows(Graphics g, Player p) {
        tick();
        if(a.isNewAttack()) {
            if(++attackDelay == 125) {
                a.notNewAttack();
                attackDelay = 0;
            }
            if(Runner.isSurvival() && (a.getCurrentAttack() == 8 && lastImportantAttack != 8 || a.getCurrentAttack() == 43 && lastImportantAttack != 43 || a.getCurrentAttack() == 79 && lastImportantAttack != 79) && attackPattern.size() == 0 && attackDelay == 100) {
                if(a.getCurrentAttack() == 8)
                    lastImportantAttack = 8;
                else if(a.getCurrentAttack() == 43) {
                    lastImportantAttack = 43;
                    Runner.changeGif();
                }
                else if(a.getCurrentAttack() == 79) {
                    lastImportantAttack = 79;
                    Runner.finalBoost();
                }
                Runner.changeMain();
            }
        }
        else if(++counter == lastDelay || isFirst) {
            isFirst = false;
            Arrow temp = a.getCurrentArrow();
            if(temp.getSpeed() != 0) {
                addArrow(temp);
                lastDelay = temp.getDelay();
            }
            counter = 0;
        }
        hit = removeArrow(p.getDir(), p);
        block.changeVolume(volume);
        damage.changeVolume(volume);
        if(hit.equals("H")) {
            p.setRedZero();
            block.play();
        }
        else if(hit.equals("D") && !p.getHit()) {
            isDamaged = true;
            damage.play();
            p.setHit(true);
        }
        if(isDamaged) {
            p.setElementPosition(move);
            move += adder;
            if(move == 2)
                adder *= -1;
            if(move == -2) {
                adder *= -1;
                ++hitPoint;
            }
            if(hitPoint == 2 && move == 0) {
                hitPoint = 0;
                move = 0;
                isDamaged = false;
                p.setElementPosition(0);
            }
        }
        draw(g);
    }
    
    ArrayList<Arrow> getList() {
        return attackPattern;
    }
    
    void resetVars() {
        isFirst = true;
        hit = "";
        volume = 1;
    }
    
    static void changeVol(double change) {
        volume = change;
    }
    
}
