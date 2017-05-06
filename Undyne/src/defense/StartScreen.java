package defense;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import nikunj.classes.NewerSound;

public class StartScreen {
    static double fadeIn = 0;
    static double fadeStart = 0;
    
    BufferedImage undyne;
    BufferedImage start;
    BufferedImage hard;
    BufferedImage easy;
    BufferedImage heartMouse;
    BufferedImage select;
    BufferedImage subtitle;
    BufferedImage constrains;
    BufferedImage[] fire;
    BufferedImage[] dog = new BufferedImage[2];
    
    static int scaleSub = 40;
    static int w = 600;
    static int h = 600;
    static int floatSub = 0;
    static int scale = 500;
    static int dropX = 0;
    static int dropY = -10;
    static int shift = 0;
    static int speed = 4;
    static int enterCounter = 0;
    static int heartX = 0;
    static int heartY = 0 + shift;
    static int hardButtonRect = 0;
    static int easyButtonRect = 0;
    static int frame = -1;
    static int frameCounter = 0;
    static int frameCounter1 = 0;
    static int count2 = -100;
    static int undyneCount = 0;
    static int randX, randY;
    static int flashCount = 0;
    static int hardButtonCount = 0;
    static int easyButtonCount = 0;
    static int dogCount = 0;
    static int dogFrame = 0;
    
    static boolean right = false;
    static boolean left = false;
    static boolean up = false;
    static boolean down = false;
    static boolean switchFade = false;
    static boolean hardButtonRectRed = false;
    static boolean easyButtonRectRed = false;
    static boolean fire2 = false;
    static boolean playFire = true;
    static boolean needDog = false;
    static boolean playBark = true;
    static boolean floatSubBoolean = false;
    
    NewerSound flare;
    NewerSound bark;
    
    public StartScreen() {
        fire = new BufferedImage[38];
        flare = new NewerSound("audio/fire.wav", false); // Credit to wjl from
                                                         // https://goo.gl/ofAZRS
        bark = new NewerSound("audio/bark.wav", false);
        try {
            subtitle = ImageIO.read(new File("images/sub.png"));
            undyne = ImageIO.read(new File("images/undyne.png"));
            start = ImageIO.read(new File("images/start.png"));
            select = ImageIO.read(new File("images/select.png"));
            hard = ImageIO.read(new File("images/hard.png"));
            easy = ImageIO.read(new File("images/easy.png"));
            heartMouse = ImageIO.read(new File("images/heartMouse1.png"));
            for(int i = 0; i <= 37; ++i)
                fire[i] = ImageIO.read(new File("images/fireGif/" + i + "frames.png"));
            dog[0] = ImageIO.read(new File("images/annoyingDog/dog1.png"));
            dog[1] = ImageIO.read(new File("images/annoyingDog/dog2.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void run(Graphics g) {
        
        drawBG(g);
        
        if(frameCounter1++ > 100) {
            
            if(frameCounter1++ > 200) {
                if(scale > 1)
                    scale -= scaleSub;
                drawSubtitle(g);
            }
            
            gifFire(g);
            gifDog(g);
            starterTitle(g, fadeIn);
            if(frameCounter1 > 250) {
                moveHeart();
                constrain();
                enterToStart(g);
                hardButton(g);
                easyButton(g);
                heartMouse(g);
            }
            
        }
        
    }
    
    public void drawSubtitle(Graphics g) {
        if(floatSubBoolean) {
            //floatSub++;
            if(floatSub == 10)
                floatSubBoolean = !floatSubBoolean;
        }
        else {
            //floatSub--;
            if(floatSub == -10)
                floatSubBoolean = !floatSubBoolean;
        }
        
        if(frameCounter1 > 200) {
            g.drawImage(resize(subtitle, subtitle.getHeight() + scale, subtitle.getWidth() + scale), dropX - scale/2,
                    dropY - scale/2 + floatSub, null);
        }
        else {
            g.drawImage(resize(subtitle, subtitle.getHeight() + scale, subtitle.getWidth() + scale), dropX - scale/2,
                    dropY - scale/2, null);
        }
    }
    
    public void moveHeart() {
        if(enterCounter > 10) {
            
            if(right)
                heartX += speed;
            if(left)
                heartX -= speed;
            if(up)
                heartY -= speed;
            if(down)
                heartY += speed;
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
        if(undyneCount % 5 == 0 && scale < 1) {
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
        if(!(easyButtonRectRed || hardButtonRectRed))
            g2d.drawImage(select, 0, 0 + shift, null);
        else
            g2d.drawImage(start, 0, 0 + shift, null);
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
            if(heartX > 78 && heartX < 231 && heartY < 57 + shift && heartY > -11 + shift) {
                if(hardButtonRect < 60) {
                    hardButtonRect += 5;
                    playFire = true;
                }
                else {
                    if(playFire) {
                        playFire = false;
                        flare.play();
                    }
                    hardButtonRectRed = true;
                    easyButtonRectRed = false;
                }
            }
            else if(hardButtonRect > 0 && !hardButtonRectRed) {
                hardButtonRect -= 5;
                playFire = true;
            }
        }
        ++hardButtonCount;
        if(hardButtonCount == 5)
            hardButtonCount = 0;
        if(!hardButtonRectRed)
            g.fillRect(380, 360 - (300 - (Math.abs(300 - hardButtonRect))) + shift, 140, hardButtonRect);
        else {
            fire2 = true;
            g.setColor(Color.RED);
            g.fillRect(380, 300 + shift, 140, 60);
            
        }
        g.drawImage(hard, 0, 0 + shift, null);
    }
    
    public void easyButton(Graphics g) {
        g.setColor(new Color(246, 138, 21));
        if(easyButtonCount % 5 == 0) {
            if(heartX > -220 && heartX < -70 && heartY < 57 + shift && heartY > -11 + shift) {
                if(easyButtonRect < 60) {
                    easyButtonRect += 5;
                    playBark = true;
                }
                else {
                    if(playBark) {
                        bark.play();
                        playBark = false;
                    }
                    easyButtonRectRed = true;
                    hardButtonRectRed = false;
                }
            }
            else {
                if(easyButtonRect > 0 && !easyButtonRectRed) {
                    easyButtonRect -= 5;
                    playBark = true;
                }
            }
        }
        ++easyButtonCount;
        if(easyButtonCount == 5)
            easyButtonCount = 0;
        if(!easyButtonRectRed)
            g.fillRect(80, 360 - (300 - (Math.abs(300 - easyButtonRect))) + shift, 140, easyButtonRect);
        else {
            g.setColor(new Color(0, 234 - 30, 77 - 30));
            g.fillRect(80, 300 + shift, 140, 60);
        }
        g.drawImage(easy, 0, 0 + shift, null);
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
        if(fire2 && count2 >= 0 && (hardButtonRectRed))
            g.drawImage(fire[count2], 330, 160 + shift, null);
    }
    
    public void gifDog(Graphics g) {
        if(easyButtonRectRed) {
            ++dogCount;
            if(dogCount != 0 && dogCount % 20 == 0) {
                if(dogFrame == 0)
                    dogFrame = 1;
                else
                    dogFrame = 0;
                dogCount = 0;
            }
            g.drawImage(dog[dogFrame], 130, 261 + shift, null);
        }
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
    
    public boolean hasSelected() {
        return hardButtonRectRed || easyButtonRectRed;
    }
    
    public boolean isHard() {
        return hardButtonRectRed;
    }
    
    public BufferedImage resize(BufferedImage img, int newW, int newH) {
        
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }
}
