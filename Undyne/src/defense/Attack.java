package defense;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

import nikunj.classes.NewerSound;

public class Attack {
    private int counter = 0;
    private static boolean isFirst = true;
    /*
     * List of Arrow objects that make up one attack
     */
    private ArrayList<Arrow> attackPattern = new ArrayList<Arrow>();
    
    private static String hit = "";
    private int adder = 1;
    private int hitPoint = 0;
    private int move = 0;
    private int attackDelay = 0;
    private int lastDelay = 0;
    
    private boolean isDamaged = false;
    
    private Attacks a;
        
    /*
     * Constructor for constant delay
     */
    public Attack(ArrayList<Arrow> attackPattern, Player p, Attacks a) {
        this.attackPattern = attackPattern;
        this.a = a;
    }
    
    public void tick() {
        for(Arrow a : attackPattern)
            a.tick();
    }
    
    public void addArrow(Arrow a) {
        attackPattern.add(a);
    }
    
    public void removeArrow(Arrow a) {
        attackPattern.remove(a);
    }
    
    public String removeArrow(char dir, Player p) {
        int min = Integer.MAX_VALUE;
        boolean hit = false;
        boolean damage = false;
        if(attackPattern.size() == 0)
            return "";
        for(int i = 0; i < attackPattern.size() && !hit && !damage; i++) {
            Arrow tempArrow = attackPattern.get(i);
            if(tempArrow.getX()<min)
                min = tempArrow.getX();
            switch(tempArrow.getDir()) {
                case 'l':
                    if(dir == 'r') {
                        if(tempArrow.getX() < 324) {
                            if(!tempArrow.getInside()) {
                                hit = true;
                                attackPattern.remove(i);
                            }
                        }
                    }
                    if(!hit && attackPattern.get(i).getX() < 308) {
                        attackPattern.remove(i);
                        damage = true;
                    }
                    break;
                case 'r':
                    if(dir == 'l') {
                        if(tempArrow.getX() > 240) {
                            if(!tempArrow.getInside()) {
                                hit = true;
                                attackPattern.remove(i);
                            }
                        }
                    }
                    if(!hit && attackPattern.get(i).getX() > 261) {
                        attackPattern.remove(i);
                        damage = true;
                    }
                    break;
                case 'u':
                    if(dir == 'd') {
                        if(tempArrow.getY() < 320) {
                            if(!tempArrow.getInside()) {
                                hit = true;
                                attackPattern.remove(i);
                            }
                        }
                    }
                    if(!hit && attackPattern.get(i).getY() < 295) {
                        attackPattern.remove(i);
                        damage = true;
                    }
                    break;
                case 'd':
                    if(dir == 'u') {
                        if(attackPattern.get(i).getY() > 230) {
                            if(!tempArrow.getInside()) {
                                hit = true;
                                attackPattern.remove(i);
                            }
                        }
                    }
                    if(!hit && attackPattern.get(i).getY() > 252) {
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
    
    public void draw(Graphics g) throws IOException {
        if(attackPattern.size() == 0)
            return;
        attackPattern.get(0).draw(g, Color.RED);
        for(int i = 1; i < attackPattern.size(); ++i) {
            attackPattern.get(i).draw(g, Color.BLUE);
        }
    }
    
    public void spawnArrows(Graphics g, Player p) throws IOException {
        tick();
        if(a.isNewAttack()) {
            if(++attackDelay == 125) {
                a.notNewAttack();
                attackDelay = 0;
            }
        }
        else if(isFirst || ++counter == lastDelay) {
            isFirst = false;
            Arrow temp = a.getCurrentArrow();
            if(temp.getSpeed() != 0) {
                addArrow(temp);
                lastDelay = temp.getDelay();
            }
            counter = 0;
        }
        hit = removeArrow(p.getDir(), p);
        NewerSound block = new NewerSound("audio/block.wav", false);
        NewerSound damage = new NewerSound("audio/damage.wav", false);
        if(hit.equals("H")) {
            p.setRed(0);
            block.play();
        }
        else if(hit.equals("D")) {
            if(!p.getHit()) {
                isDamaged = true;
                damage.play();
                p.setHit(true);
            }
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
    
    public ArrayList<Arrow> getList() {
        return attackPattern;
    }
    
    public void resetVars() {
        counter = 0;
        isFirst = true;
        attackPattern = new ArrayList<Arrow>();
        hit = "";
        adder = 1;
        hitPoint = 0;
        move = 0;
        attackDelay = 0;
        lastDelay = 0;
        isDamaged = false;
        a = null;
    }
    
}
