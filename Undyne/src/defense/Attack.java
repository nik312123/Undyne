package defense;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import nikunj.classes.NewerSound;

public class Attack {
    public static int currentDirection = 0;
    public static final char[] DIRS = {'u', 'd', 'r', 'l'};
    private int counter = 0;
    /*
     * List of Arrow objects that make up one attack
     */
    private ArrayList<Arrow> attackPattern = new ArrayList<Arrow>();
    /*
     * ifthe delay for an attack is constant, this is it (milliseconds)
     */
    private double delay;
    /*
     * ifthe delays vary in amount for the arrows, this is it (milliseconds)
     */
    private double[] delayGroup;
    
    public static String hit = "";
    Random rand = new Random();
    int adder = 1;
    int hitPoint = 0;
    int move = 0;
    
    boolean isDamaged = false;
    
    Player p;
    
    /*
     * 
     * Constructor for constant delay
     */
    public Attack(ArrayList<Arrow> attackPattern, double delay, Player p) {
        this.attackPattern = attackPattern;
        this.delay = delay;
        this.delayGroup = new double[0];
        this.p = p;
    }
    
    /*
     * Constructor for varying delays
     */
    public Attack(ArrayList<Arrow> attackPattern, double[] delayGroup) throws IOException {
        if(delayGroup.length == attackPattern.size() - 1)
            throw new IOException("Error: Number of delays must be one less than the number of attacks");
        this.attackPattern = attackPattern;
        this.delay = 0;
        this.delayGroup = delayGroup;
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
        boolean hit = false;
        boolean damage = false;
        if(attackPattern.size() == 0)
            return "";
        for(int i = 0; i < attackPattern.size(); i++) {
            Arrow tempArrow = attackPattern.get(i);
            switch(tempArrow.getDir()) {
                case 'l':
                    if(dir == 'r') {
                        if(tempArrow.getX() < 325) {
                            attackPattern.remove(i);
                            hit = true;
                        }
                    }
                    else if(attackPattern.get(i).getX() < 308) {
                        attackPattern.remove(i);
                        damage = true;
                    }
                    break;
                case 'r':
                    if(dir == 'l') {
                        if(tempArrow.getX() > 240) {
                            attackPattern.remove(i);
                            hit = true;
                        }
                    }
                    else if(attackPattern.get(i).getX() > 261) {
                        attackPattern.remove(i);
                        damage = true;
                    }
                    break;
                case 'u':
                    if(dir == 'd') {
                        if(tempArrow.getY() < 320) {
                            attackPattern.remove(i);
                            hit = true;
                        }
                    }
                    else if(attackPattern.get(i).getY() < 295) {
                        attackPattern.remove(i);
                        damage = true;
                    }
                    break;
                case 'd':
                    if(dir == 'u') {
                        if(attackPattern.get(i).getY() > 230) {
                            attackPattern.remove(i);
                            hit = true;
                        }
                    }
                    else if(attackPattern.get(i).getY() > 252) {
                        attackPattern.remove(i);
                        damage = true;
                    }
                    break;
            }
        }
        if(hit)
            return "H";
        else if(damage) {
            if(!p.getHit()){
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
        if(++counter == 30) {
            addArrow(new Arrow(5, false, DIRS[rand.nextInt(DIRS.length)], p));
            if(currentDirection == DIRS.length)
                currentDirection = 0;
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
            if(!p.getHit()){
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
}
