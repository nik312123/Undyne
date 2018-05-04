package defense;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

class Player {
    private char dir = 'u';
    
    private boolean hit = false;
    
    private static final int TIME = 75;
    private int red = 30;
    private int timeoutCounter = TIME;
    private int elementPosition = 0;
    private int baseDamage;
    private int damageOffset;
    private int maxHealth;
    
    private Random random = new Random();
    
    private static BufferedImage[] shields = new BufferedImage[2];
    
    private Font undyneFont;
    
    /*
     * Initial player health
     */
    private int health = 60;
    /*
     * How much the player is currently rotated by
     */
    private int angle = 0;
    
    Player() {
        if(Runner.isFirstTime) {
            try {
                shields[0] = ImageIO.read(Runner.class.getResource("/shieldH.png"));
                shields[1] = ImageIO.read(Runner.class.getResource("/shield.png"));
            }
            catch(IOException e) {
                e.printStackTrace();
            }
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
    
    void damage() {
        health -= (int) (Math.random() * baseDamage + damageOffset);
        if(baseDamage == 0 && random.nextInt(10) < 7)
            health -= 1;
        if(health < 0)
            health = 0;
    }
    
    void drawHealth(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(430, 530 + 40, 70, 20);
        g.setColor(Color.YELLOW);
        g.fillRect(430, 530 + 40, (int) (70 * ((double) health / maxHealth)), 20);
        Graphics2D g3 = (Graphics2D) g;
        g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g3.setFont(undyneFont);
        g3.setColor(Color.WHITE);
        g3.drawString("HP", 390, 548 + 40);
        Graphics2D g4 = (Graphics2D) g;
        g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g4.setFont(undyneFont);
        g4.setColor(Color.WHITE);
        g4.drawString(health + "/" + maxHealth, 513, 547 + 40);
        
    }
    
    void shield(Graphics g, char dir) {
        this.dir = dir;
        shieldDir();
        drawShield(g);
    }
    
    private void drawShield(Graphics g) {
        BufferedImage shield;
        if(red < 15) {
            shield = shields[0];
            ++red;
        }
        else
            shield = shields[1];
        g.translate(300, 300);
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(angle), 71 / 2, 71 / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        shield = op.filter(shield, null);
        g.translate(-300, -300);
        g.drawImage(shield, 265 + elementPosition, 254 + 11 + elementPosition, null);
    }
    
    private void shieldDir() {
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
        if(angle > 360)
            angle = 0;
    }
    
    char getDir() {
        return dir;
    }
    
    void setDir(char dir) {
        this.dir = dir;
    }
    
    int getAngle() {
        return angle;
    }
    
    void zeroAngle() {
        angle  = 0;
    }
    
    void setRedZero() {
        red = 0;
    }
    
    int getHealth() {
        return health;
    }
    
    void setHealth(int health) {
        this.health = health;
        maxHealth = health;
    }
    
    int getElementPosition() {
        return elementPosition;
    }
    
    void setElementPosition(int x) {
        elementPosition = x;
    }
    
    void setHit(boolean hit) {
        this.hit = hit;
    }
    
    boolean getHit() {
        return hit;
    }
    
    int getTimeoutCounter() {
        return timeoutCounter;
    }
    
    void resetTimeoutCounter() {
        timeoutCounter = TIME;
    }
    
    void decreaseCounter() {
        --timeoutCounter;
    }
    
    void setDamageOffset(int damageOffset) {
        this.damageOffset = damageOffset;
    }
    
    void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }
    
    void convertHealth() {
        health += 40;
        maxHealth = 60;
    }
    
    void healthBoost() {
        health = Math.min(health + 40, 60);
    }
    
}
