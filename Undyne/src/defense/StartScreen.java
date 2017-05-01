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
    
    BufferedImage undyne;
    BufferedImage start;
    BufferedImage hard;
    BufferedImage easy;
    BufferedImage heartMouse;
    BufferedImage[] fire;
    
    static int speed = 2;
    static int enterCounter = 0;
    static int heartX = 0;
    static int heartY = 0;
    static int hardButtonRect = 0;
    static int easyButtonRect = 0;
    int frame = -1;
    int frameCounter = 0;
    static int count2 = -100;
    int undyneCount = 0;
    static int randX, randY;
    static int flashCount = 0;
    static int hardButtonCount = 0;
    static int easyButtonCount = 0;
    
    static boolean right = false;
    static boolean left = false;
    static boolean up = false;
    static boolean down = false;
    static boolean switchFade = false;
    static boolean hardButtonRectRed = false;
    static boolean easyButtonRectRed = false;
    static boolean fire2 = false;
    
    public StartScreen() {
        fire = new BufferedImage[38];
        try {
            undyne = ImageIO.read(new File("images/undyne.png"));
            start = ImageIO.read(new File("images/start.png"));
            hard = ImageIO.read(new File("images/hard.png"));
            easy = ImageIO.read(new File("images/easy.png"));
            heartMouse = ImageIO.read(new File("images/heartMouse.png"));
            for(int i = 0; i <= 37; ++i)
                fire[i] = ImageIO.read(new File("images/fireGif/" + i + "frames.png"));
            }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
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
        Graphics2D g2d = (Graphics2D) g.create();
        float opacity = (float) fade;
        if(fadeIn < 1)
            fadeIn += 0.02;
        else {
            enterCounter++;
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        if(undyneCount % 5 == 0) {
            randX = (int) (Math.random() * 3);
            randY = (int) (Math.random() * 3);
        }
        g2d.drawImage(undyne, randX, -100 + randY, null);
        g2d.dispose();
        ++undyneCount;
        if(undyneCount == 5)
            undyneCount = 0;
    }
    
    public void constrain() {
        if(heartX < -288)
            heartX = -288;
        else if(heartX > 296)
            heartX = 296;
        if(heartY < -300)
            heartY = -300;
        else if(heartY > 260)
            heartY = 260;
    }
    
    public void enterToStart(Graphics g) {
        float opacity = (float) fadeStart;
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(start, 0, 0, null);
        g2d.dispose();
        if(flashCount % 2 == 0) {
            if(fadeStart <= 1 && !switchFade)
                fadeStart += 0.02;
            else {
                switchFade = true;
                fadeStart -= 0.02;
                if(fadeStart < 0.03)
                    switchFade = false;
            }
        }
        ++flashCount;
        if(flashCount == 2)
            flashCount = 0;
    }
    
    public void hardButton(Graphics g) {
        g.setColor(new Color(246, 138, 21));
        if(hardButtonCount % 5 == 0) {
            if(heartX > 78 && heartX < 231 && heartY < 57 && heartY > -11) {
                if(hardButtonRect < 60)
                    hardButtonRect += 6;
                else
                    hardButtonRectRed = true;
            }
            else if(hardButtonRect > 0)
                hardButtonRect -= 6;
        }
        ++hardButtonCount;
        if(hardButtonCount == 5)
            hardButtonCount = 0;
        if(!hardButtonRectRed)
            g.fillRect(380, 360 - (300 - (Math.abs(300 - hardButtonRect))), 140, hardButtonRect);
        else {
            fire2 = true;
            g.setColor(Color.red);
            g.fillRect(380, 300, 140, 60);
            
        }
        g.drawImage(hard, 0, 0, null);
    }
    
    public void easyButton(Graphics g) {
        g.setColor(new Color(246, 138, 21));
        if(easyButtonCount % 5 == 0) {
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
        }
        ++easyButtonCount;
        if(easyButtonCount == 5)
            easyButtonCount = 0;
        if(!easyButtonRectRed)
            g.fillRect(80, 360 - (300 - (Math.abs(300 - easyButtonRect))), 140, easyButtonRect);
        else {
            g.setColor(new Color(0, 234 - 30, 77 - 30));
            g.fillRect(80, 300, 140, 60);
        }
        g.drawImage(easy, 0, 0, null);
    }
    
    public void heartMouse(Graphics g) {
        g.drawImage(heartMouse, heartX, heartY, null);
    }
    
    public void gifFire(Graphics g) {
        if(frameCounter % 3 == 0 || count2 < 0)
            count2++;
        ++frameCounter;
        if(frameCounter == 3)
            frameCounter = 0;
        if(count2 == 37)
            count2 = 0;
        if(fire2 && count2 >= 0)
            g.drawImage(fire[count2], 330, 160, null);
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
