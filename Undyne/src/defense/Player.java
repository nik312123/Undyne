package defense;

import customAttackMaker.CustomAttacks;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

/**
 * Represents the player with the shield, heart, and box
 */
class Player {
    /**
     * The direction the shield is facing
     */
    private char dir = 'u';
    
    /**
     * True if the player has been damaged and the damage animation is running
     */
    private boolean damaged = false;
    
    /**
     * The initial number of ticks before the invincibility frames after being damaged wears off
     */
    private static final int TIME = 75;
    
    /**
     * If this is less than 15, the shield is red to show it being hit. Else, it is not.
     */
    private int red = 15;
    
    /**
     * The current number of ticks before the invincibility frames after being damaged wears off
     */
    private int timeoutCounter = TIME;
    
    /**
     * The shift in the elements used to shake the screen when being damaged
     */
    private int elementPosition = 0;
    
    /**
     * The difference between the maximum and lowest damages the player can take
     */
    private int baseDamage;
    
    /**
     * The lowest damage the player can take from an attack
     */
    private int damageOffset;
    
    /**
     * The maximum (initial) health of the player
     */
    private int maxHealth;
    
    /**
     * The random object used for damage amount
     */
    private Random random = new Random();
    
    /**
     * The transformation used to rotate the shield
     */
    private AffineTransform playerTransform = new AffineTransform();
    
    /**
     * The images used for the shield (red aka blocking and normal)
     */
    private static BufferedImage[] shields = new BufferedImage[2];
    
    /**
     * The font used to draw the health values
     */
    private Font undyneFont;
    
    /*
     * Initial player health
     */
    private int health = 60;
    
    /*
     * How much the player's shield is currently rotated by in degrees
     */
    private int angle = 0;
    
    /**
     * Creates a new {@code Player}
     */
    Player() {
        //Gets the shield images and hp font the first time the program is initialization
        if(Runner.isFirstTime) {
            shields[0] = Runner.getCompatibleImage("/shieldH.png");
            shields[1] = Runner.getCompatibleImage("/shield.png");
        }
        URL fontUrl;
        try {
            fontUrl = Runner.class.getResource("/undyne.ttf");
            undyneFont = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(25.0f);
        }
        catch(FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Applies damage to the player
     */
    void damage() {
        //Applies damage to player
        health -= (int) (Math.random() * baseDamage + damageOffset);
        
        //Applies additional damage point 70% of the time for easy/medium damage
        if(baseDamage == 0 && random.nextInt(10) < 7)
            health -= 1;
        if(health < 0)
            health = 0;
    }
    
    /**
     * Draws the health for the player
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    void drawHealth(Graphics g) {
        //Sets the y-value of the health bar based on the bottom bar of the custom attack if in custom attacks else has a set position
        int yBase;
        if(Runner.canBeStopped)
            yBase = CustomAttacks.getBottomMenuBar().getYValue() - 30;
        else
            yBase = 570;
        
        //Draws the back of the health bar (max health) in red
        g.setColor(Color.RED);
        g.fillRect(430, yBase, 70, 20);
        
        //Draw the front of the health bar (current health) in yellow
        g.setColor(Color.YELLOW);
        g.fillRect(430, yBase, (int) (70 * ((double) health / maxHealth)), 20);
        
        //Draws the HP text with the numerical health values
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(undyneFont);
        g2d.setColor(Color.WHITE);
        g2d.drawString("HP", 390, yBase + 18);
        g2d.drawString(health + "/" + maxHealth, 513, yBase + 17);
    }
    
    /**
     * Moves and draws the shield
     *
     * @param g   The graphics object used for drawing the Runner JPanel
     * @param dir The direction the player inputted to block the arrows
     */
    void shield(Graphics g, char dir) {
        this.dir = dir;
        shieldMove();
        drawShield(g);
    }
    
    /**
     * Draws the shield
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawShield(Graphics g) {
        BufferedImage shield;
        
        //Sets the shield to have red if it recently blocked an arrow else normal
        if(red < 15) {
            shield = shields[0];
            ++red;
        }
        else
            shield = shields[1];
        
        //Moves and rotates the shield accordingly and draws it
        g.translate(300, 300);
        playerTransform.setToIdentity();
        playerTransform.translate(265 + elementPosition, 254 + 11 + elementPosition);
        playerTransform.rotate(Math.toRadians(angle), 35, 35);
        g.translate(-300, -300);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(shield, playerTransform, null);
    }
    
    /**
     * Moves the shield according to the direction the player chooses and its current location
     *
     * The shield moves clockwise or counterclockwise depending on what is fastest; if both are the same, clockwise is chosen
     */
    private void shieldMove() {
        switch(dir) {
            case 'r':
                if(angle == 90)
                    break;
                else if(angle > 90 && angle <= 180)
                    angle -= 15;
                else
                    angle += 15;
                break;
            case 'l':
                if(angle == 270)
                    break;
                else if(angle < 270 && angle >= 180)
                    angle += 15;
                else if(angle >= 0)
                    angle -= 15;
                if(angle < 0)
                    angle = 360 + angle;
                break;
            case 'u':
                if(angle == 0)
                    break;
                else if(angle <= 90 && angle > 0)
                    angle -= 15;
                else
                    angle += 15;
                break;
            case 'd':
                if(angle == 180)
                    break;
                else if(angle <= 270 && angle > 180)
                    angle -= 15;
                else if(angle <= 360 && angle > 270)
                    angle -= 15;
                else
                    angle += 15;
                break;
        }
        
        //If the angle goes over 360 degrees, it starts back at zero degrees
        if(angle > 360)
            angle = 0;
    }
    
    /**
     * Returns the direction the shield is facing
     *
     * @return The direction the shield is facing
     */
    char getDir() {
        return dir;
    }
    
    /**
     * Sets the direction the shield is facing to up
     */
    void setDirUp() {
        this.dir = 'u';
    }
    
    /**
     * Returns the current angle the shield is rotated
     *
     * @return The current angle the shield is rotated
     */
    int getAngle() {
        return angle;
    }
    
    /**
     * Sets the angle the shield is currently at to zero
     */
    void zeroAngle() {
        angle = 0;
    }
    
    /**
     * Sets the red value to zero to make the shield red
     */
    void setRedZero() {
        red = 0;
    }
    
    /**
     * Returns the player's current health
     *
     * @return The player's current health
     */
    int getHealth() {
        return health;
    }
    
    /**
     * Sets the health of the player to the given health and sets the value to the max health
     *
     * @param health The health of the player to the given health and sets the value to the max health
     */
    void setHealth(int health) {
        this.health = health;
        maxHealth = health;
    }
    
    /**
     * Returns the position shift of the elements when the player is damaged
     *
     * @return The position shift of the elements when the player is damaged
     */
    int getElementPosition() {
        return elementPosition;
    }
    
    /**
     * Sets the position shift of the elements when the player is damaged
     *
     * @param xy The position shift of the elements when the player is damaged
     */
    void setElementPosition(int xy) {
        elementPosition = xy;
    }
    
    /**
     * Sets if the player has been damaged
     *
     * @param damaged True if the player has been damaged
     */
    void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }
    
    /**
     * Returns if the player has been damaged
     *
     * @return True if the player has ben damaged
     */
    boolean getDamaged() {
        return damaged;
    }
    
    /**
     * Returns the current number of ticks before the invincibility frames after being damaged wears off
     *
     * @return The current number of ticks before the invincibility frames after being damaged wears off
     */
    int getTimeoutCounter() {
        return timeoutCounter;
    }
    
    /**
     * Resets the current number of ticks before the invincibility frames after being damaged wears off
     */
    void resetTimeoutCounter() {
        timeoutCounter = TIME;
    }
    
    /**
     * Decreases the current number of ticks before the invincibility frames after being damaged wears off
     */
    void decreaseCounter() {
        --timeoutCounter;
    }
    
    /**
     * Sets the lowest damage the player can take from an attack
     *
     * @param damageOffset The lowest damage the player can take from an attack
     */
    void setDamageOffset(int damageOffset) {
        this.damageOffset = damageOffset;
    }
    
    /**
     * Sets the difference between the maximum and lowest damages the player can take
     *
     * @param baseDamage The difference between the maximum and lowest damages the player can take
     */
    void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }
    
    /**
     * Sets the health to have a maximum of 60 instead of 20 and adds 40 to the current health to compensate
     */
    void convertHealth() {
        health += 40;
        maxHealth = 60;
    }
    
    /**
     * Boosts the health by forty with a maximum of 60
     */
    void healthBoost() {
        health = Math.min(health + 40, 60);
    }
    
}
