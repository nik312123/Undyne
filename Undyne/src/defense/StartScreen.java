package defense;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.AttributedString;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;

import nikunj.classes.PopUp;
import nikunj.classes.Sound;

public class StartScreen {
    private double fadeIn = 0;
    private double fadeStart = 0;
    private static double sfxVolume = 1;
    private double musicVolume = 1;
    
    private float blueHeartOpacity = 0.02f;
    
    private static BufferedImage undyne;
    private static BufferedImage start;
    private static BufferedImage heartMouse;
    private static BufferedImage heartMouseBlue;
    private static BufferedImage select;
    private static BufferedImage subtitle;
    private static BufferedImage subtitleBlue;
    private static BufferedImage buttons;
    private static BufferedImage bones;
    private static BufferedImage blueHeartFlash;
    private static BufferedImage spear;
    private static BufferedImage arrows;
    private static BufferedImage[] cracks = new BufferedImage[4];
    private static BufferedImage[] keys = new BufferedImage[2];
    private static BufferedImage[] fire = new BufferedImage[38];
    private static BufferedImage[] dog = new BufferedImage[2];
    private static BufferedImage[] sans = new BufferedImage[10];

    private int zCounter = 0;
    private int crackFrame = 0;
    private int hardButtonRect = 0;
    private int easyButtonRect = 0;
    private int survivalButtonRect = 0;
    private int frameCounter = 0;
    private int count2 = -100;
    private int undyneCount = 0;
    private int randX, randY;
    private int flashCount = 0;
    private int hardButtonCount = 0;
    private int easyButtonCount = 0;
    private int survivalButtonCount = 0;
    private int dogCount = 0;
    private int dogFrame = 0;
    private int scale = 3990;
    private int shift = 0;
    private int heartX = 5;
    private int heartY = 100 + shift;
    private int frameCounter1 = 0;
    private int moveCounter = 1;
    private int boneCounter = 0;
    private int boneY = 600;
    private int sansCount = 0;
    private int sansFrame = 0;
    private int flickerCounter = 0;
    private int flickerChangeY = 0;
    private int flickeringCountdown = 75;
    private int blueHeartFlashCounter = 0;
    private int gifOneIndex = 0, gifTwoIndex = 0;
    private int spearCounter = 1, spearFrame = 0;
    private int arrowsCounter = 1;
    private int nameStringX = 610;
    private int nameStringCounter = 0;
    private int warningCounter = 0;
    
    private boolean right = false;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;
    private boolean switchFade = false;
    private boolean hardButtonRectRed = false;
    private boolean easyButtonRectRed = false;
    private boolean survivalButtonRectRed = false;
    private boolean fire2 = false;
    private boolean playFire = true;
    private boolean playBark = true;
    private boolean playSpear = true;
    private boolean isOnHard;
    private boolean isOnEasy;
    private boolean isOnSurvival;
    private boolean hitGround = false;
    private boolean showBones = false;
    private boolean hideSans = false;
    private boolean flickering = false;
    private boolean activateSpears = false;
    private boolean spearAppearPlayed = false;
    private boolean spearHitPlayed = false;
    private boolean heartMoved = false;
    private boolean arrowsShouldShow = true;
    static boolean isLoaded = false;
    private boolean[] heartsActivated = new boolean[3];
    
    private static Sound flare;
    private static Sound bark;
    private static Sound wall;
    private static Sound boneSound;
    private static Sound damage;
    private static Sound megalovania;
    private static Sound spearAppear;
    private static Sound spearFly;
    private static Sound spearHit;
    
    private PopUp creditsList;
    
    private static final Point2D SPEAR_SPAWN = new Point2D.Double(310, 197 - 60 * Math.tan(Math.toRadians(75)));
    private static final Point2D SPEAR_END = new Point2D.Double(250, 197);
    private Point2D spearLocation = (Point2D) SPEAR_SPAWN.clone();
    
    private Random rand = new Random();
    
    private AttributedString[] creditsText = new AttributedString[8];
    
    StartScreen() {
        if(Runner.isFirstTime) {
            try {
                flare = new Sound(Runner.class.getResource("/fire.wav"), false);
                bark = new Sound(Runner.class.getResource("/bark.wav"), false);
                spearAppear = new Sound(Runner.class.getResource("/spearAppear.wav"), false);
                spearFly = new Sound(Runner.class.getResource("/spearFly.wav"), false);
                spearHit = new Sound(Runner.class.getResource("/spearHit.wav"), false);
                wall = new Sound(Runner.class.getResource("/wall.wav"), false);
                boneSound = new Sound(Runner.class.getResource("/bones.wav"), false);
                damage = new Sound(Runner.class.getResource("/damage.wav"), false);
                megalovania = new Sound(Runner.class.getResource("/megalovania.wav"), true);
            }
            catch(UnsupportedAudioFileException | IOException e1) {
                e1.printStackTrace();
            }
            try {
                subtitle = ImageIO.read(Runner.class.getResource("/sub.png"));
                subtitle = Runner.getCompatibleImage(subtitle);
                keys[0] = ImageIO.read(Runner.class.getResource("/keysRed.png"));
                keys[0] = Runner.getCompatibleImage(keys[0]);
                keys[1] = ImageIO.read(Runner.class.getResource("/keys.png"));
                keys[1] = Runner.getCompatibleImage(keys[1]);
                undyne = ImageIO.read(Runner.class.getResource("/undyne.png"));
                undyne = Runner.getCompatibleImage(undyne);
                start = ImageIO.read(Runner.class.getResource("/start.png"));
                start = Runner.getCompatibleImage(start);
                select = ImageIO.read(Runner.class.getResource("/select.png"));
                select = Runner.getCompatibleImage(select);
                heartMouse = ImageIO.read(Runner.class.getResource("/heartMouse.png"));
                heartMouse = Runner.getCompatibleImage(heartMouse);
                heartMouseBlue = ImageIO.read(Runner.class.getResource("/heartMouseBlue.png"));
                heartMouseBlue = Runner.getCompatibleImage(heartMouseBlue);
                buttons = ImageIO.read(Runner.class.getResource("/buttons.png"));
                buttons = Runner.getCompatibleImage(buttons);
                for(int i = 0; i <= 37; ++i) {
                    fire[i] = ImageIO.read(Runner.class.getResource("/fireGif/fire" + i + ".png"));
                    fire[i] = Runner.getCompatibleImage(fire[i]);
                }
                dog[0] = ImageIO.read(Runner.class.getResource("/annoyingDog/dog1.png"));
                dog[0] = Runner.getCompatibleImage(dog[0]);
                dog[1] = ImageIO.read(Runner.class.getResource("/annoyingDog/dog2.png"));
                dog[1] = Runner.getCompatibleImage(dog[1]);
                subtitleBlue = ImageIO.read(Runner.class.getResource("/subBlue.png"));
                subtitleBlue = Runner.getCompatibleImage(subtitleBlue);
                bones = ImageIO.read(Runner.class.getResource("/bones.png"));
                bones = Runner.getCompatibleImage(bones);
                for(int i = 0; i <= 9; ++i) {
                    sans[i] = ImageIO.read(Runner.class.getResource("/sans/sans" + i + ".png"));
                    sans[i] = Runner.getCompatibleImage(sans[i]);
                }
                blueHeartFlash = ImageIO.read(Runner.class.getResource("/blueHeartFlash.png"));
                blueHeartFlash = Runner.getCompatibleImage(blueHeartFlash);
                spear = ImageIO.read(Runner.class.getResource("/spear.png"));
                spear = Runner.getCompatibleImage(spear);
                arrows = ImageIO.read(Runner.class.getResource("/arrows.png"));
                arrows = Runner.getCompatibleImage(arrows);
                for(int i = 0; i < 4; ++i) {
                        cracks[i] = ImageIO.read(Runner.class.getResource("/cracks"+ i +".png"));
                        cracks[i] = Runner.getCompatibleImage(cracks[i]);
                }
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        creditsList = new PopUp(65, 65, 470, 470, 46, Color.BLACK, Color.ORANGE, 5) {
            private static final long serialVersionUID = 1L;

            @Override
            public void mouseClicked(MouseEvent e) {}
            
        };
        creditsText[0] = new AttributedString("Toby Fox: Undyne sprites, Annoying Dog sprite,");
        addLinkFormatting(0, 0, 9);
        creditsText[1] = new AttributedString("wjl: Fire sound effect");
        addLinkFormatting(1, 0, 4);
        creditsText[2] = new AttributedString("fins: Error sound effect");
        addLinkFormatting(2, 0, 5);
        creditsText[3] = new AttributedString("Klemens Wöhrer: Fire gif");
        addLinkFormatting(3, 0, 15);
        creditsText[4] = new AttributedString("Sayonara Maxwell: Spear of Justice Remix");
        addLinkFormatting(4, 0, 17);
        creditsText[5] = new AttributedString("Kamex: Battle Against A True Hero Remix");
        addLinkFormatting(5, 0, 6);
        creditsText[6] = new AttributedString("Nikunj Chawla and Aaron Kandikatla: All other sprites");
        addLinkFormatting(6, 0, 13);
        addLinkFormatting(6, 18, 35);
        creditsText[7] = new AttributedString("And most importantly, thanks to you for enjoying our");
        for(AttributedString a : creditsText) {
            try {
                a.addAttribute(TextAttribute.FONT, Font.createFont(Font.TRUETYPE_FONT, Runner.class.getResource("/dete.otf").openStream()).deriveFont(14.0f));
            }
            catch(FontFormatException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void addLinkFormatting(int creditsIndex, int beginningIndex, int lastIndex) {
        creditsText[creditsIndex].addAttribute(TextAttribute.FOREGROUND, new Color(0, 82, 232), beginningIndex, lastIndex);
        creditsText[creditsIndex].addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE, beginningIndex, lastIndex);
    }
    
    void run(Graphics g) {
        gifFire(g);
        drawArrows(g);
        drawGif(g);
        if(frameCounter1 != 251)
            ++frameCounter1;
        if(frameCounter1 > 100) {
            if(frameCounter1 != 251)
                ++frameCounter1;
            if(frameCounter1 > 200) {
                if(scale > 0)
                    scale -= 70;
                drawSubtitle(g);
            }
            gifDog(g);
            starterTitle(g);
            drawSubtitle(g);
            if(frameCounter1 > 250) {
                moveHeart();
                constrain();
                zToStart(g);
                hardButton(g);
                easyButton(g);
                survivalButton(g);
                startArrows(g);
                g.drawImage(buttons, 0, 0, null);
                drawBlueHeartFlash(g);
                drawSpears(g);
                drawNames(g);
                heartMouse(g);
                creditsList.draw(g);
                if(creditsList.percentageExpanded() == 1.0) {
                    TextAttribute[] t = {TextAttribute.FONT};
                    int originalY = 85 + g.getFontMetrics((Font) creditsText[0].getIterator(t).getAttribute(t[0])).getHeight()/2;
                    int x = 85, y = originalY;
                    g.setColor(Color.WHITE);
                    for(AttributedString a : creditsText) {
                        g.drawString(a.getIterator(), x, y);
                        y += 40;
                    }
                    g.setFont(Runner.deteFontSpeech);
                    g.drawString("Undertale SFX, and creating Undertale", x, originalY + 15);
                    g.drawString("and code", x, originalY + 15 + 40 * 6);
                    g.drawString("game! :)", x, originalY + 15 + 40 * 7);
                }
                isLoaded = true;
                if(heartsActivated()) {
                    drawSans(g);
                    ++flickerCounter;
                    flickeringHeart();
                    if(megalovania.isStopped()) {
                        megalovania.changeVolume(musicVolume);
                        megalovania.play();
                    }
                    if(hitGround)
                        drawBones(g);
                }
                if(scale <= 1) {
                        g.drawImage(cracks[crackFrame], 0, 0, null);
                        if(crackFrame < 3)
                            ++crackFrame;
                }
            }
        }
    }
    
    private void drawArrows(Graphics g) {
        g.drawImage(Runner.blueArr, -100, 1000, null);
        g.drawImage(Runner.redArr, -100, 1000, null);
        g.drawImage(Runner.reverseArr, -100, 1000, null);
    }
    
    private void drawGif(Graphics g) {
        if(gifOneIndex > 31)
            gifOneIndex = 0;
        if(gifTwoIndex > 79)
            gifTwoIndex = 0;
        g.drawImage(Runner.gif[gifOneIndex], -100, 1000, null);
        g.drawImage(Runner.gif2[gifTwoIndex], -100, 1000, null);
        ++gifOneIndex;
        ++gifTwoIndex;
    }
    
    private void drawSubtitle(Graphics g) {
        if(heartsActivated())
            g.drawImage(subtitleBlue, 0 - scale / 2, 0 - scale / 2, subtitle.getWidth() + scale, subtitle.getHeight() + scale, null);
        else
            g.drawImage(subtitle, 0 - scale / 2, 0 - scale / 2, subtitle.getWidth() + scale, subtitle.getHeight() + scale, null);
    }
    
    private void moveHeart() {
        if(zCounter > 10 && !Runner.getHelpStarter() && !creditsList.getExpanding()) {
            ++moveCounter;
            if(moveCounter > 2) {
                final int speed = 2;
                if(right) {
                    heartX += speed;
                    heartMoved = true;
                }
                if(left) {
                    heartX -= speed;
                    heartMoved = true;
                }
                if(up && !heartsActivated() || up && hitGround) {
                    heartY -= speed;
                    heartMoved = true;
                }
                if(down && !heartsActivated() || down && hitGround) {
                    heartY += speed;
                    heartMoved = true;
                }
                if(heartsActivated() && heartY == 281 && !hitGround) {
                    wall.changeVolume(sfxVolume);
                    wall.play();
                    hitGround = true;
                }
                if(heartsActivated() && !hitGround)
                    heartY += 2 * speed;
                if(moveCounter == 4)
                    moveCounter = 1;
            }
        }
    }
    
    private void starterTitle(Graphics g) {
        if(!heartsActivated()) {
            if(fadeIn < 1)
                fadeIn += 0.02;
            else if(zCounter < 11)
                ++zCounter;
            if(undyneCount % 7 == 0) {
                randX = rand.nextInt(3);
                randY = rand.nextInt(3);
            }
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) fadeIn));
            g2d.drawImage(undyne, randX, -100 + randY, null);
            g2d.dispose();
            ++undyneCount;
            if(undyneCount == 7)
                undyneCount = 0;
        }
    }
    
    private void constrain() {
        if(heartX < -288)
            heartX = -288;
        else if(heartX > 296)
            heartX = 296;
        if(heartY < -300)
            heartY = -300;
        else if(heartY > 281)
            heartY = 281;
    }
    
    private void zToStart(Graphics g) {
        if(!heartsActivated()) {
            float opacity = (float) fadeStart;
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            if(!easyButtonRectRed && !hardButtonRectRed && !survivalButtonRectRed) {
                g2d.drawImage(select, 0, 50 + shift, null);
                if(warningCounter == 0)
                    g2d.drawImage(keys[0], 0, 50 - 20, null);
                else {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2d.drawImage(keys[0], 0 - 150, -220, (int) (1.5 * keys[0].getWidth()), (int) (1.5 * keys[0].getHeight()), null);
                }
            }
            else {
                if(warningCounter == 0)
                    g2d.drawImage(start, 0, 50 + shift, null);
                else {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2d.drawImage(start, 0 - 150, -175, (int) (1.5 * start.getWidth()), (int) (1.5 * start.getHeight()), null);
                }
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(keys[0], 0, 50 - 20, null);
            }
            if(warningCounter != 0)
                --warningCounter;
            g2d.dispose();
        }
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
    
    private void hardButton(Graphics g) {
        g.setColor(new Color(246, 138, 21));
        isOnHard = (heartX > 78 + 37 && heartX < 231 + 37 && heartY < 57 + shift && heartY > -11 + shift);
        if(hardButtonCount % 6 == 0) {
            if(isOnHard) {
                if(hardButtonRect < 60) {
                    hardButtonRect += 5;
                    playFire = true;
                }
                else {
                    if(playFire) {
                        playFire = false;
                        flare.changeVolume(sfxVolume);
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
    
    private void easyButton(Graphics g) {
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
                        bark.changeVolume(sfxVolume);
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
    
    private void survivalButton(Graphics g) {
        g.setColor(new Color(246, 138, 21));
        isOnSurvival = (heartX > -68 && heartX < 80 && heartY < 57 + shift && heartY > -11 + shift);
        if(survivalButtonCount % 6 == 0) {
            if(isOnSurvival) {
                if(survivalButtonRect < 60) {
                    survivalButtonRect += 5;
                    playSpear = true;
                    activateSpears = false;
                }
                else {
                    if(playSpear) {
                        activateSpears = true;
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
                    activateSpears = false;
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
    
    private void heartMouse(Graphics g) {
        if(heartsActivated() && !hitGround)
            g.drawImage(heartMouseBlue, heartX, heartY, null);
        else
            g.drawImage(heartMouse, heartX, heartY + flickerChangeY, null);
    }
    
    private void gifFire(Graphics g) {
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
    
    private void gifDog(Graphics g) {
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
    
    private void drawSpears(Graphics g) {
        if(activateSpears) {
            Graphics2D g2d = (Graphics2D) g.create();
            if(!spearAppearPlayed) {
                spearLocation = (Point2D) SPEAR_SPAWN.clone();
                spearAppear.changeVolume(sfxVolume);
                spearAppear.play();
                spearAppearPlayed = true;
            }
            if(spearFrame <= 30) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, spearFrame / 30f));
                g2d.drawImage(spear, (int) Math.round(SPEAR_SPAWN.getX()), (int) Math.round(SPEAR_SPAWN.getY()), null);
            }
            else if(!spearLocation.equals(SPEAR_END)) {
                spearLocation = new Point2D.Double(spearLocation.getX() + (SPEAR_END.getX() - SPEAR_SPAWN.getX()) / 20, spearLocation.getY() + (SPEAR_END.getY() - SPEAR_SPAWN.getY()) / 20);
                g2d.drawImage(spear, (int) Math.round(spearLocation.getX()), (int) Math.round(spearLocation.getY()), null);
            }
            else {
                if(!spearHitPlayed) {
                    spearHit.changeVolume(sfxVolume);
                    spearHit.play();
                    spearHitPlayed = true;
                }
                g2d.drawImage(spear, (int) Math.round(SPEAR_END.getX()), (int) Math.round(SPEAR_END.getY()), null);
            }
            if(spearFrame == 30) {
                spearFly.changeVolume(sfxVolume);
                spearFly.play();
            }
            if(spearCounter % 3 == 0) {
                ++spearFrame;
                spearCounter = 1;
            }
            ++spearCounter;
            g2d.dispose();
        }
        else {
            spearCounter = 1;
            spearFrame = 0;
            spearAppearPlayed = false;
            spearHitPlayed = false;
        }
    }
    
    private void startArrows(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        if(!heartMoved && arrowsShouldShow)
            g2d.drawImage(arrows, 5, 100, null);
        if(arrowsCounter % 85 == 0) {
            arrowsShouldShow = !arrowsShouldShow;
            arrowsCounter = 1;
        }
        ++arrowsCounter;
        g2d.dispose();
    }
    
    private void drawBones(Graphics g) {
        ++boneCounter;
        if(boneCounter > 50) {
            if(!showBones) {
                boneSound.changeVolume(sfxVolume);
                boneSound.play();
            }
            showBones = true;
            if(boneCounter < 300)
                g.drawImage(bones, -11, boneY, null);
            else {
                hideSans = true;
                resetEgg();
            }
            if(boneY > 510)
                boneY -= 3;
            if(heartY + 300 >= boneY) {
                if(!flickering) {
                    damage.changeVolume(sfxVolume);
                    damage.play();
                }
                flickering = true;
            }
        }
    }
    
    private void drawSans(Graphics g) {
        if(sansCount % 10 == 0) {
            if(sansFrame < 4 || hitGround)
                ++sansFrame;
        }
        if(sansFrame > 10)
            sansFrame = 10;
        if(sansFrame < 10)
            g.drawImage(sans[sansFrame], 237, 15, null);
        else if(!hideSans)
            g.drawImage(sans[9], 237, 15, null);
        if(sansFrame < 4 || hitGround)
            ++sansCount;
        if(sansCount == 10)
            sansCount = 0;
    }
    
    private void drawBlueHeartFlash(Graphics g) {
        if(heartsActivated())
            blueHeartOpacity = 1f;
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, blueHeartOpacity));
        g2d.drawImage(blueHeartFlash, 0, 0, null);
        g2d.dispose();
        if(blueHeartOpacity > 0.02f && blueHeartFlashCounter % 2 == 0)
            blueHeartOpacity -= 0.02f;
        ++blueHeartFlashCounter;
        if(blueHeartFlashCounter == 2)
            blueHeartFlashCounter = 0;
    }
    
    private void drawNames(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(Runner.deteFontScore);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Made by Nikunj Chawla and Aaron Kandikatla", nameStringX, 580);
        g2d.dispose();
        if(++nameStringCounter % 2 == 0) {
            --nameStringX;
            nameStringCounter = 0;
            if(nameStringX == -560)
                nameStringX = 610;
        }
    }
    
    private void flickeringHeart() {
        if(flickering) {
            --flickeringCountdown;
            if(flickerCounter % 16 == 0) {
                if(flickerChangeY == 0)
                    flickerChangeY = 9000;
                else
                    flickerChangeY = 0;
            }
        }
        if(flickeringCountdown == 0) {
            flickering = false;
            flickeringCountdown = 75;
            flickerChangeY = 0;
        }
    }
    
    private void resetEgg() {
        heartsActivated[0] = false;
        heartsActivated[1] = false;
        heartsActivated[2] = false;
        boneCounter = 0;
        boneY = 600;
        sansCount = 0;
        sansFrame = 0;
        flickerCounter = 0;
        flickerChangeY = 0;
        flickeringCountdown = 75;
        blueHeartOpacity = 0f;
        flickering = false;
        hitGround = false;
        showBones = false;
        hideSans = false;
        megalovania.stop();
    }
    
    void setRightf() {
        right = false;
    }
    
    void setLeftf() {
        left = false;
    }
    
    void setUpf() {
        up = false;
    }
    
    void setDownf() {
        down = false;
    }
    
    void setRight() {
        right = true;
    }
    
    void setLeft() {
        left = true;
    }
    
    public void setUp() {
        up = true;
    }
    
    void setDown() {
        down = true;
    }
    
    boolean isHard() {
        return hardButtonRectRed;
    }
    
    boolean isSurvival() {
        return survivalButtonRectRed;
    }
    
    boolean isOnHeartOne() {
        return heartX >= -33 && heartX <= -9 && heartY >= -100 && heartY <= -80;
    }
    
    boolean isOnHeartTwo() {
        return heartX >= -186 && heartX <= -167 && heartY >= -106 && heartY <= -84;
    }
    
    boolean isOnHeartThree() {
        return heartX >= -47 && heartX <= -19 && heartY >= -218 && heartY <= -190;
    }
    
    void activateHeartOne() {
        heartsActivated[0] = true;
    }
    
    void activateHeartTwo() {
        heartsActivated[1] = true;
    }
    
    void activateHeartThree() {
        heartsActivated[2] = true;
    }
    
    boolean heartOneActivated() {
        return heartsActivated[0];
    }
    
    boolean heartTwoActivated() {
        return heartsActivated[1];
    }
    
    boolean heartThreeActivated() {
        return heartsActivated[2];
    }
    
    boolean heartsActivated() {
        for(boolean heartActivated : heartsActivated) {
            if(!heartActivated)
                return false;
        }
        return true;
    }
    
    void activateBlueHeartFlash() {
        blueHeartOpacity = 1f;
    }
    
    public void openCreditsLink(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        }
        catch(IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    
    void resetVars() {
        isLoaded = false;
    }
    
    boolean shouldStart() {
        return !isOnLink() && !isOnHelp() && !heartsActivated() && (hardButtonRectRed && !isOnEasy && !isOnSurvival || easyButtonRectRed && !isOnHard && !isOnSurvival || survivalButtonRectRed && !isOnHard && !isOnEasy);
    }
    
    boolean shouldShow() {
        return frameCounter1 > 250;
    }
    
    static void changeSfxVol(double change) {
        sfxVolume = change;
    }
    
    void changeMusicVol(double change) {
        musicVolume = change;
        megalovania.changeVolume(change);
    }
    
    boolean isOnLink() {
        return (heartX + 288 + 16 >= 76 && heartX + 288 <= 224 && heartY + 300 <= 442 + 20 && heartY + 300 + 16 >= 380 + 20 && !heartsActivated());
    }
    
    boolean isOnHelp() {
        return (heartX + 288 + 16 >= 376 && heartX + 288 <= 524 && heartY + 300 <= 442 + 20 && heartY + 300 + 16 >= 380 + 20 && !heartsActivated());
    }
    
    void warningOn() {
        warningCounter = 50;
    }
    
    PopUp getCreditsList() {
        return creditsList;
    }
    
}
