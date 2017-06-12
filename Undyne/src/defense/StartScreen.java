package defense;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;

import nikunj.classes.NewerSound;

public class StartScreen {
    private static double fadeIn = 0;
    private static double fadeStart = 0;
    private static double volume = 1;
    
    private BufferedImage undyne;
    private BufferedImage start;
    private BufferedImage heartMouse;
    private BufferedImage select;
    private BufferedImage subtitle;
    private BufferedImage buttons;
    private BufferedImage[] keys = new BufferedImage[2];
    private BufferedImage[] fire = new BufferedImage[38];
    private BufferedImage[] dog = new BufferedImage[2];
    
    private static int speed = 2;
    private static int zCounter = 0;
    private static int hardButtonRect = 0;
    private static int easyButtonRect = 0;
    private static int survivalButtonRect = 0;
    private int frameCounter = 0;
    private static int count2 = -100;
    private int undyneCount = 0;
    private static int randX, randY;
    private static int flashCount = 0;
    private static int hardButtonCount = 0;
    private static int easyButtonCount = 0;
    private static int survivalButtonCount = 0;
    private static int dogCount = 0;
    private static int dogFrame = 0;
    private static int scaleSub = 10;
    private static int scale = 500;
    private static int shift = 0;
    private static int heartX = 5;
    private static int heartY = 100 + shift;
    private static int frameCounter1 = 0;
    private static int moveCounter = 1;
    
    private static boolean right = false;
    private static boolean left = false;
    private static boolean up = false;
    private static boolean down = false;
    private static boolean switchFade = false;
    private static boolean hardButtonRectRed = false;
    private static boolean easyButtonRectRed = false;
    private static boolean survivalButtonRectRed = false;
    private static boolean fire2 = false;
    private static boolean playFire = true;
    private static boolean playBark = true;
    private static boolean playSpear = true;
    private static boolean isOnHard;
    private static boolean isOnEasy;
    private static boolean isOnSurvival;
    
    private NewerSound flare;
    private NewerSound bark;
    private NewerSound spearAppear;
    private NewerSound spearFly;
    private NewerSound spearHit;
    
    private Random rand = new Random();
        
    public StartScreen() {
        try { //Credit to wjl from goo.gl/ofAZRS
            flare = new NewerSound(Runner.class.getResource("/fire.wav"), false);
            bark = new NewerSound(Runner.class.getResource("/bark.wav"), false);
            spearAppear = new NewerSound(Runner.class.getResource("/spearAppear.wav"), false);
            spearFly = new NewerSound(Runner.class.getResource("/spearFly.wav"), false);
            spearHit = new NewerSound(Runner.class.getResource("/spearHit.wav"), false);
        }
        catch(UnsupportedAudioFileException | IOException e1) {
            e1.printStackTrace();
        }
        try {
            subtitle = ImageIO.read(Runner.class.getResource("/sub.png"));
            keys[0] = ImageIO.read(Runner.class.getResource("/keysRed.png"));
            keys[1] = ImageIO.read(Runner.class.getResource("/keys.png"));
            undyne = ImageIO.read(Runner.class.getResource("/undyne.png"));
            start = ImageIO.read(Runner.class.getResource("/start.png"));
            select = ImageIO.read(Runner.class.getResource("/select.png"));
            heartMouse = ImageIO.read(Runner.class.getResource("/heartMouse.png"));
            buttons = ImageIO.read(Runner.class.getResource("/buttons.png"));
            for(int i = 0; i <= 37; ++i) //Credit: nevit from goo.gl/QR3vVj
                fire[i] = ImageIO.read(Runner.class.getResource("/fireGif/fire" + i + ".png"));
            dog[0] = ImageIO.read(Runner.class.getResource("/annoyingDog/dog1.png"));
            dog[1] = ImageIO.read(Runner.class.getResource("/annoyingDog/dog2.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void run(Graphics g) {
        gifFire(g);
        g.drawImage(Runner.blueArr, -100, 1000, null);
        g.drawImage(Runner.redArr, -100, 1000, null);
        g.drawImage(Runner.reverseArr, -100, 1000, null);
        if(frameCounter1 != 251)
            ++frameCounter1;
        if(frameCounter1 > 100) {
            if(frameCounter1 > 100 && frameCounter1 != 251)
                ++frameCounter1;
            if(frameCounter1 > 200) {
                if(scale > 1)
                    scale -= scaleSub;
                drawSubtitle(g);
            }
            gifDog(g);
            starterTitle(g, fadeIn);
            if(frameCounter1 > 250) {
                moveHeart();
                constrain();
                zToStart(g);
                hardButton(g);
                easyButton(g);
                survivalButton(g);
                g.drawImage(buttons, 0, 0, null);
                heartMouse(g);
            }
        }
    }
    
    public void drawSubtitle(Graphics g) {
        g.drawImage(subtitle, 0 - scale, 0 - scale, subtitle.getWidth() + scale, subtitle.getHeight() + scale, null);
    }
    
    public void moveHeart() {
        if(zCounter > 10) {
            ++moveCounter;
            if(moveCounter > 2) {
                if(right)
                    heartX += speed;
                if(left)
                    heartX -= speed;
                if(up)
                    heartY -= speed;
                if(down)
                    heartY += speed;
                if(moveCounter == 4)
                    moveCounter = 1;
            }
        }
    }
    
    public void starterTitle(Graphics g, double fade) {
        Graphics2D g2d = (Graphics2D) g.create();
        if(fadeIn < 1)
            fadeIn += 0.02;
        else if(zCounter < 11)
            ++zCounter;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) fade));
        if(undyneCount % 7 == 0 && scale < 1) {
            randX = rand.nextInt(3);
            randY = rand.nextInt(3);
        }
        g2d.drawImage(undyne, randX, -100 + randY, null);
        g2d.dispose();
        ++undyneCount;
        if(undyneCount == 7)
            undyneCount = 0;
    }
    
    public void constrain() {
        if(heartX < -288)
            heartX = -288;
        else if(heartX > 296)
            heartX = 296;
        if(heartY < -300)
            heartY = -300;
        else if(heartY > 281)
            heartY = 281;
    }
    
    public void zToStart(Graphics g) {
        float opacity = (float) fadeStart;
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        if(!easyButtonRectRed && !hardButtonRectRed){
            g2d.drawImage(select, 0, 50 + shift, null);
            g2d.drawImage(keys[0], 0, 50 - 20, null);
        }
        else{
            g2d.drawImage(start, 0, 50 + shift, null);
            g2d.drawImage(keys[1], 0, 50 - 20, null);
        }
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
        isOnHard = (heartX > 78+ 37 && heartX < 231 + 37 && heartY < 57 + shift && heartY > -11 + shift);
        if(hardButtonCount % 6 == 0) {
            if(isOnHard) {
                if(hardButtonRect < 60) {
                    hardButtonRect += 5;
                    playFire = true;
                }
                else {
                    if(playFire) {
                        playFire = false;
                        flare.changeVolume(volume);
                        flare.play();
                    }
                    hardButtonRectRed = true;
                    easyButtonRectRed = false;
                    survivalButtonRectRed = false;
                }
            }
            else if(hardButtonRect > 0 && !hardButtonRectRed) {
                hardButtonRect -= 5;
                playFire = true;
            }
        }
        ++hardButtonCount;
        if(hardButtonCount == 6)
            hardButtonCount = 0;
        if(!hardButtonRectRed)
            g.fillRect(380 + 37, 360 - (300 - (Math.abs(300 - hardButtonRect))) + shift, 140, hardButtonRect);
        else {
            fire2 = true;
            g.setColor(Color.RED);
            g.fillRect(380 + 37, 300 + shift, 140, 60);
        }
    }
    
    public void easyButton(Graphics g) {
        g.setColor(new Color(246, 138, 21));
        isOnEasy = (heartX > -220 - 37 && heartX < -70 - 37 && heartY < 57 + shift && heartY > -11 + shift);
        if(easyButtonCount % 6 == 0) {
            if(isOnEasy) {
                if(easyButtonRect < 60) {
                    easyButtonRect += 5;
                    playBark = true;
                }
                else {
                    if(playBark) {
                        bark.changeVolume(volume);
                        bark.play();
                        playBark = false;
                    }
                    easyButtonRectRed = true;
                    hardButtonRectRed = false;
                    survivalButtonRectRed = false;
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
        if(easyButtonCount == 6)
            easyButtonCount = 0;
        if(!easyButtonRectRed)
            g.fillRect(80 - 37, 360 - (300 - (Math.abs(300 - easyButtonRect))) + shift, 140, easyButtonRect);
        else {
            g.setColor(new Color(0, 234 - 30, 77 - 30));
            g.fillRect(80 - 37, 300 + shift, 140, 60);
        }
    }
    
    public void survivalButton(Graphics g) {
        g.setColor(new Color(246, 138, 21));
        isOnSurvival = (heartX > -68 && heartX < 80 && heartY < 57 + shift && heartY > -11 + shift);
        if(survivalButtonCount % 6 == 0) {
            if(isOnSurvival) {
                if(survivalButtonRect < 60) {
                    survivalButtonRect += 5;
                    playSpear = true;
                }
                else {
                    if(playSpear) {
//                        spearSound.changeVolume(volume);
//                        spearSound.play();
                        playSpear = false;
                    }
                    easyButtonRectRed = false;
                    hardButtonRectRed = false;
                    survivalButtonRectRed = true;
                }
            }
            else {
                if(survivalButtonRect > 0 && !survivalButtonRectRed) {
                    survivalButtonRect -= 5;
                    playSpear = true;
                }
            }
        }
        ++survivalButtonCount;
        if(survivalButtonCount == 6)
            survivalButtonCount = 0;
        if(!survivalButtonRectRed)
            g.fillRect(80 - 37 + 148 + 39, 360 - (300 - (Math.abs(300 - survivalButtonRect))) + shift, 140, survivalButtonRect);
        else {
            g.setColor(new Color(220, 220, 36));
            g.fillRect(80 - 37 + 148 + 39, 300 + shift, 140, 60);
        }
    }
    
    public void heartMouse(Graphics g) {
        g.drawImage(heartMouse, heartX, heartY, null);
    }
    
    public void gifFire(Graphics g) {
        if(frameCounter % 4 == 0 || count2 < 0)
            ++count2;
        ++frameCounter;
        if(frameCounter == 4)
            frameCounter = 0;
        if(count2 == 38)
            count2 = 0;
        if(fire2 && count2 >= 0 && hardButtonRectRed)
            g.drawImage(fire[count2], 330 + 39, 160 + shift, null);
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
            g.drawImage(dog[dogFrame], 130 - 39, 261 + shift, null);
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
    
    public boolean isHard() {
        return hardButtonRectRed;
    }
    
    public boolean isSurvival() {
        return survivalButtonRectRed;
    }
    
    public void openCreditsLink() {
        try {
            Desktop.getDesktop().browse(new URI("http://athena.edenpr.org/~chawlan/undyne.html"));
        }
        catch(IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    
    public void resetVars() {
        fadeIn = 0;
        fadeStart = 0;
        undyne = null;
        start = null;
        heartMouse = null;
        select = null;
        subtitle = null;
        buttons = null;
        fire = new BufferedImage[38];
        dog = new BufferedImage[2];
        speed = 2;
        zCounter = 0;
        heartX = 5;
        hardButtonRect = 0;
        easyButtonRect = 0;
        survivalButtonRect = 0;
        frameCounter = 0;
        count2 = -100;
        undyneCount = 0;
        flashCount = 0;
        hardButtonCount = 0;
        easyButtonCount = 0;
        survivalButtonCount = 0;
        dogCount = 0;
        dogFrame = 0;
        scaleSub = 10;
        scale = 500;
        shift = 0;
        heartY = 100 + shift;
        frameCounter1 = 0;
        right = false;
        left = false;
        up = false;
        down = false;
        switchFade = false;
        hardButtonRectRed = false;
        easyButtonRectRed = false;
        survivalButtonRectRed = false;
        fire2 = false;
        playFire = true;
        playBark = true;
        playSpear = true;
    }
    
    public boolean shouldStart() {
        return !isOnLink() && !isOnHelp() && (hardButtonRectRed && !isOnEasy && !isOnSurvival || easyButtonRectRed && !isOnHard && !isOnSurvival || survivalButtonRectRed && !isOnHard && !isOnEasy);
    }
    
    public boolean shouldShow() {
        return frameCounter1 > 250;
    }
    
    public static void changeVol(double change) {
        volume = change;
    }
    
  public boolean isOnLink() {
      return (heartX + 288 + 16 >= 76 && heartX + 288 <= 224 && heartY + 300 <= 442 + 20 && heartY + 300 + 16 >= 380 + 20);
  }
  
  public boolean isOnHelp() {
      return (heartX + 288 + 16 >= 376 && heartX + 288 <= 524 && heartY + 300 <= 442 + 20 && heartY + 300 + 16 >= 380 + 20);
  }
    
}
