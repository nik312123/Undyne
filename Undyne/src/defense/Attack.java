package defense;

import java.io.IOException;
import java.util.List;

public class Attack {
    /*
     * List of Arrow objects that make up one attack
     */
    private List<Arrow> attackPattern;
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
    public Attack(List<Arrow> attackPattern, double delay) {
        this.attackPattern = attackPattern;
        this.delay = delay;
        this.delayGroup = new double[0];
    }
    
    /*
     * Constructor for varying delays
     */
    public Attack(List<Arrow> attackPattern, double[] delayGroup) throws IOException {
        if(delayGroup.length == attackPattern.size() - 1)
            throw new IOException("Error: Number of delays must be one less than the number of attacks");
        this.attackPattern = attackPattern;
        this.delay = 0;
        this.delayGroup = delayGroup;
    }
    
}
