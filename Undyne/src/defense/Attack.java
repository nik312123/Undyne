package defense;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.LinkedList;


public class Attack {
    /*
     * List of Arrow objects that make up one attack
     */
    private LinkedList<Arrow> attackPattern;
    /*
     * If the delay for an attack is constant, this is it (milliseconds)
     */
    private double delay;
    /*
     * If the delays vary in amount for the arrows, this is it (milliseconds)
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
    
    public void addArrow(Arrow a) {
        attackPattern.add(a);
    }
    
    public void removeArrow(Arrow a) {
        attackPattern.remove(a);
    }
    
    public void removeArrow() {
        if(attackPattern.get(0).getX() > 220)
            attackPattern.remove(attackPattern.get(0));
    }
    
    public void draw(Graphics g) {
        attackPattern.get(0).draw(g, Color.RED);
        for(int i = 1; i < attackPattern.size(); ++i) {
            attackPattern.get(i).draw(g, Color.BLUE);
        }
    }
    
}
