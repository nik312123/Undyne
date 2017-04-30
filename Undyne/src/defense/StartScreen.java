package defense;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StartScreen {
    static double fadeIn = 0;
    static double fadeStart = 0;
    
    BufferedImage heart;
    BufferedImage fire;
    
    static int speed = 10;
    static int enterCounter = 0;
    static int heartX = 0;
    static int heartY = 0;
    static int hardButtonRect = 0;
    static int easyButtonRect = 0;
    int frame = -1;
    int frameCounter = 0;
    static int count2 = -100;
    
    static boolean right = false;
    static boolean left = false;
    static boolean up = false;
    static boolean down = false;
    static boolean switchFade = false;
    static boolean hardButtonRectRed = false;
    static boolean easyButtonRectRed = false;
    static boolean fire2 = false;
    
    public StartScreen() {}
    
    public void run(Graphics g) {
        drawBG(g);
        gifFire(g);
        starterTitle(g, fadeIn);
        if(enterCounter > 10) {
            if(right)
                heartX += speed;
            if(left)
                heartX -= speed;
            if(up)
                heartY -= speed;
            if(down)
                heartY += speed;
            constrain();
            enterToStart(g);
            hardButton(g);
            easyButton(g);
            heartMouse(g);
        }
    }
    
    public void drawBG(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 600);
    }
    
    public void starterTitle(Graphics g, double fade) {
        try {
            heart = ImageIO.read(new File("images/undyne.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        Graphics2D g2d = (Graphics2D) g.create();
        float opacity = (float) fade;
        if(fadeIn < 1)
            fadeIn += 0.02;
        else {
            enterCounter++;
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        
        g2d.drawImage(heart, 0 + (int) (Math.random() * 3), -100 + (int) (Math.random() * 3), null);
        g2d.dispose();
        
    }
    
    public void constrain() {
        if(heartX < -288)
            heartX = 296;
        if(heartX > 296)
            heartX = -288;
        if(heartY < -300)
            heartY = 256;
        if(heartY > 256)
            heartY = -300;
    }
    
    public void enterToStart(Graphics g) {
        try {
            heart = ImageIO.read(new File("images/START.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        float opacity = (float) fadeStart;
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        
        g2d.drawImage(heart, 0, 0, null);
        g2d.dispose();
        if(fadeStart <= 1 && !switchFade)
            fadeStart += 0.02;
        else {
            switchFade = true;
            fadeStart -= 0.02;
            if(fadeStart < 0.03)
                switchFade = false;
        }
    }
    
    public void hardButton(Graphics g) {
        g.setColor(new Color(246, 138, 21));
        if(heartX > 78 && heartX < 231 && heartY < 57 && heartY > -11) {
            if(hardButtonRect < 60)
                hardButtonRect += 6;
            else
                hardButtonRectRed = true;
        }
        else {
            if(hardButtonRect > 0)
                hardButtonRect -= 6;
        }
        if(!hardButtonRectRed)
            g.fillRect(380, 360 - (300 - (Math.abs(300 - hardButtonRect))), 140, hardButtonRect);
        else {
            fire2 = true;
            g.setColor(Color.red);
            g.fillRect(380, 300, 140, 60);
            
        }
        try {
            heart = ImageIO.read(new File("images/hard.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        g.drawImage(heart, 0, 0, null);
    }
    
    public void easyButton(Graphics g) {
        g.setColor(new Color(246, 138, 21));
        if(heartX > -220 && heartX < -70 && heartY < 57 && heartY > -11) {
            if(easyButtonRect < 60)
                easyButtonRect += 6;
            else
                easyButtonRectRed = true;
        }
        else {
            if(easyButtonRect > 0)
                easyButtonRect -= 6;
        }
        if(!easyButtonRectRed)
            g.fillRect(80, 360 - (300 - (Math.abs(300 - easyButtonRect))), 140, easyButtonRect);
        else {
            g.setColor(new Color(0, 234 - 30, 77 - 30));
            g.fillRect(80, 300, 140, 60);
        }
        try {
            heart = ImageIO.read(new File("images/easy.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        g.drawImage(heart, 0, 0, null);
    }
    
    public void heartMouse(Graphics g) {
        try {
            heart = ImageIO.read(new File("images/heartMouse.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        g.drawImage(heart, heartX, heartY, null);
    }
    
    public void gifFire(Graphics g) {
        if(frameCounter % 3 == 0)
            count2++;
        if(count2 >= 0) {
            try {
                fire = ImageIO.read(new File("images/fireGif/" + count2 + "frames.png"));
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        if(count2 == 37)
            count2 = 0;
        if(fire2)
            g.drawImage(fire, 330, 160, null);
    }
    
    public void setRightf() {
        right = false;
    }
    
    public void setLeftf() {
        left = false;
    }
    
    public void setUpf() {
        up = false;
    }
    
    public void setDownf() {
        down = false;
    }
    
    public void setRight() {
        right = true;
    }
    
    public void setLeft() {
        left = true;
    }
    
    public void setUp() {
        up = true;
    }
    
    public void setDown() {
        down = true;
    }
    
}
