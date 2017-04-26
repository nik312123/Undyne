package defense;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.LinkedList;


public class Attack {
    /*
     * List of Arrow objects that make up one attack
     */
    private LinkedList<Arrow> attackPattern = new LinkedList<Arrow>();
    /*
     * ifthe delay for an attack is constant, this is it (milliseconds)
     */
    private double delay;
    /*
     * ifthe delays vary in amount for the arrows, this is it (milliseconds)
     */
    private double[] delayGroup;
    
    /*
     * Constructor for constant delay
     */
    public Attack(LinkedList<Arrow> attackPattern, double delay) {
        this.attackPattern = attackPattern;
        this.delay = delay;
        this.delayGroup = new double[0];
    }
    
    /*
     * Constructor for varying delays
     */
    public Attack(LinkedList<Arrow> attackPattern, double[] delayGroup) throws IOException {
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
    
    public String removeArrow(char dir) {
        boolean hit = false;
        if(attackPattern.size() == 0)
            return "";
        for (int i = 0; i < attackPattern.size(); i++) {
            Arrow tempArrow = attackPattern.get(i);
            switch(tempArrow.getDir()) {
                case 'l':
                    if(dir == 'r') {
                        if(tempArrow.getX() < 325) {
                            attackPattern.remove(i);
                            hit = true;
                        }
                    }
                    else if(attackPattern.get(i).getX() < 295)
                        attackPattern.remove(i);
                    break;
                case 'r':
                    if(dir == 'l') {
                        if(tempArrow.getX() > 240) {
                            attackPattern.remove(i);
                            hit = true;
                        }
                    }
                    else if(attackPattern.get(i).getX() > 280)
                        attackPattern.remove(i);
                    break;
                case 'u':
                    if(dir == 'd') {
                        if(tempArrow.getY() < 320) {
                            attackPattern.remove(i);
                            hit = true;
                        }
                    }
                    else if(attackPattern.get(i).getY() < 280)
                        attackPattern.remove(i);
                    break;
                case 'd':
                    if(dir == 'u') {
                        if(attackPattern.get(i).getY() > 230) {
                            attackPattern.remove(i);
                            hit = true;
                        }
                    }
                    else if(attackPattern.get(i).getY() > 260)
                        attackPattern.remove(i);
                    break;
            }
        }
        if(hit)
            return "H";
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
    
}
