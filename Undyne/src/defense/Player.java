package defense;

public class Player {
    /*
     * Delay in milliseconds between each movement of the player
     */
    private double playerDelay = 0;
    /*
     * Initial player health
     */
    private int playerHealth = 100;
    /*
     * How much the player is currently rotated by
     */
    private int rotated = 180;
    
    public Player() {}
    
    public void right() {
        
    }
    
    public void left() {
        
    }
    
    public void up() {
        
    }
    
    public void down() {
        
    }
    
    public void damage() {
        playerHealth -= (int) (Math.random() * 9 + 14);
    }
    
}
