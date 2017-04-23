package defense;

/*
 * Composes an arrow to be implemented in the Attack class
 */
public class Arrow {
    /*
     * This determines the speed the arrow should go at between 1 – 100
     */
    private int speed;
    /*
     * This determines whether or not the arrow will be reversed
     */
    private boolean reverse;
    /*
     * This determines the direction the arrow should come from
     */
    private char direction;
    
    public Arrow(int speed, boolean reverse, char direction) {
        this.speed = speed;
        this.reverse = reverse;
        this.direction = direction;
    }
    
}
