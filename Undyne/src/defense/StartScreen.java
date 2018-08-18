package defense;

import nikunj.classes.PopUp;
import nikunj.classes.Sound;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.AttributedString;
import java.util.Random;

public class StartScreen {
    private double fadeIn = 0;
    private double fadeStart = 0;
    private static double sfxVolume = 1;
    private double musicVolume = 1;

    private float blueHeartOpacity = 0.02f;

    private static BufferedImage undyne;
    private static BufferedImage start;
    private static BufferedImage zSelect;
    private static BufferedImage heartMouse;
    private static BufferedImage heartMouseBlue;
    private static BufferedImage selectDifficulty;
    private static BufferedImage selectOption;
    private static BufferedImage subtitle;
    private static BufferedImage subtitleBlue;
    private static BufferedImage buttons;
    private static BufferedImage bones;
    private static BufferedImage blueHeartFlash;
    private static BufferedImage spear;
    private static BufferedImage arrows;
    private static BufferedImage keys;
    private static BufferedImage survival;
    private static BufferedImage[] fire = new BufferedImage[38];
    private static BufferedImage[] dog = new BufferedImage[2];
    private static BufferedImage[] greaterDog = new BufferedImage[3];
    private static BufferedImage[] sans = new BufferedImage[10];
    private static BufferedImage[] dots = new BufferedImage[4];

    private int zCounter = 0;
    private int hardButtonRect = 0;
    private int easyButtonRect = 0;
    private int mediumButtonRect = 0;
    private int survivalButtonRect = 0;
    private int frameCounter = 0;
    private int count2 = -100;
    private int undyneCount = 0;
    private int randX, randY;
    private int flashCount = 0;
    private int hardButtonCount = 0;
    private int easyButtonCount = 0;
    private int survivalButtonCount = 0;
    private int mediumButtonCount = 0;
    private int dogCount = 0;
    private int greaterDogCount = 0;
    private int dogFrame = 0;
    private int greaterDogFrame = 1;
    private int greaterDogDirection = 1;
    private int scale = 800;
    private int shift = 0;
    private int heartX = 5;
    private int heartY = 72 + shift;
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
    private boolean fire2 = false;
    private boolean playFire = true;
    private boolean playBark = true;
    private boolean playBork = true;
    private boolean playSpear = true;
    private boolean isOnHard;
    private boolean isOnEasy;
    private boolean isOnSurvival;
    private boolean isOnMedium;
    private boolean hitGround = false;
    private boolean showBones = false;
    private boolean hideSans = false;
    private boolean flickering = false;
    private boolean activateSpears = false;
    private boolean spearAppearPlayed = false;
    private boolean spearHitPlayed = false;
    private boolean heartMoved = false;
    private boolean arrowsShouldShow = true;
    private boolean slammed = false;
    private boolean playChosen = false;
    private static boolean hardButtonRectRed = false;
    private static boolean easyButtonRectRed = false;
    private static boolean survivalButtonRectRed = false;
    private static boolean mediumButtonRectRed = false;
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
    private static Sound slam;
    private static Sound bork;
    private static Sound click;

    private PopUp creditsList;

    private static JPanel[] clickableNames = new JPanel[12];

    private static final Point2D SPEAR_SPAWN = new Point2D.Double(310, 197 + 110 - 60 * Math.tan(Math.toRadians(75)));
    private static final Point2D SPEAR_END = new Point2D.Double(250, 197 + 110);
    private Point2D spearLocation = (Point2D) SPEAR_SPAWN.clone();

    private Random rand = new Random();

    private AttributedString[] creditsText = new AttributedString[11];

    StartScreen() {
        if (Runner.isFirstTime) {
            try {
                flare = new Sound(Runner.class.getResource("/fire.ogg"), false);
                bark = new Sound(Runner.class.getResource("/bark.ogg"), false);
                spearAppear = new Sound(Runner.class.getResource("/spearAppear.ogg"), false);
                spearFly = new Sound(Runner.class.getResource("/spearFly.ogg"), false);
                spearHit = new Sound(Runner.class.getResource("/spearHit.ogg"), false);
                wall = new Sound(Runner.class.getResource("/wall.ogg"), false);
                boneSound = new Sound(Runner.class.getResource("/bones.ogg"), false);
                damage = new Sound(Runner.class.getResource("/damage.ogg"), false);
                megalovania = new Sound(Runner.class.getResource("/megalovania.ogg"), true);
                slam = new Sound(Runner.class.getResource("/slam.ogg"), false);
                bork = new Sound(Runner.class.getResource("/bork.ogg"), false);
                click = new Sound(Runner.class.getResource("/click.wav"), false);
            }
            catch(UnsupportedAudioFileException | IOException e1) {
                e1.printStackTrace();
            }
            subtitle = Runner.getCompatibleImage("/sub.png");
            keys = Runner.getCompatibleImage("/keys.png");
            undyne = Runner.getCompatibleImage("/undyne.png");
            start = Runner.getCompatibleImage("/start.png");
            zSelect = Runner.getCompatibleImage("/zSelect.png");
            selectDifficulty = Runner.getCompatibleImage("/selectDifficulty.png");
            selectOption = Runner.getCompatibleImage("/selectOption.png");
            heartMouse = Runner.getCompatibleImage("/heartMouse.png");
            heartMouseBlue = Runner.getCompatibleImage("/heartMouseBlue.png");
            buttons = Runner.getCompatibleImage("/buttons.png");
            survival = Runner.getCompatibleImage("/survival.png");
            for(int i = 0; i <= 37; ++i)
                fire[i] = Runner.getCompatibleImage("/fireGif/fire" + i + ".png");
            dog[0] = Runner.getCompatibleImage("/annoyingDog/dog1.png");
            dog[1] = Runner.getCompatibleImage("/annoyingDog/dog2.png");
            greaterDog[0] = Runner.getCompatibleImage("/greaterDog/greaterDogRight.png");
            greaterDog[1] = Runner.getCompatibleImage("/greaterDog/greaterDogMid.png");
            greaterDog[2] = Runner.getCompatibleImage("/greaterDog/greaterDogLeft.png");
            subtitleBlue = Runner.getCompatibleImage("/subBlue.png");
            bones = Runner.getCompatibleImage("/bones.png");
            for(int i = 0; i <= 9; ++i)
                sans[i] = Runner.getCompatibleImage("/sans/sans" + i + ".png");
            blueHeartFlash = Runner.getCompatibleImage("/blueHeartFlash.png");
            spear = Runner.getCompatibleImage("/spear.png");
            arrows = Runner.getCompatibleImage("/arrows.png");
            for(int i = 0; i < 4; ++i)
                dots[i] = Runner.getCompatibleImage("/dots/dots" + i + ".png");
        }
        else {
            heartMoved = true;
            playChosen = true;
        }
        creditsList = new PopUp(65, 65, 470, 470, 46, Color.BLACK, Color.ORANGE, 5) {
            private static final long serialVersionUID = 1L;

            @Override
            public void mouseClicked(MouseEvent e) {}
            
            @Override
            public void afterDraw(Graphics g) {
                if(creditsList.percentageExpanded() == 1.0) {
                    Runner.moveButtons(true);
                    TextAttribute[] t = {TextAttribute.FONT};
                    int originalY = 20 + g.getFontMetrics((Font) creditsText[0].getIterator(t).getAttribute(t[0])).getHeight() / 2;
                    int x = 20, y = originalY;
                    g.setColor(Color.WHITE);
                    for(AttributedString a : creditsText) {
                        g.drawString(a.getIterator(), x, y);
                        y += 40;
                    }
                    g.setFont(Runner.deteFontSpeech);
                    g.drawString("Undertale SFX & music, and creating Undertale", x, originalY + 15);
                    g.drawString("and code", x, originalY + 15 + 40 * 9);
                    g.drawString("game!", x, originalY + 15 + 40 * 10);
                    for(JPanel b : clickableNames)
                        b.setVisible(true);
                    g.setFont(Runner.deteFontSpeech);
                    
                    g.drawString("PRESS X TO EXIT", 235 - g.getFontMetrics().stringWidth("PRESS X TO EXIT") / 2, 535 - Math.min(creditsList.getPopUpWidth(), 460) / 4 + 37);
                }
            }
            
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
        creditsText[6] = new AttributedString("Sound Bible: Sub-title slamming SFX");
        addLinkFormatting(6, 0, 13);
        creditsText[7] = new AttributedString("Sound Bible: Subtitle cracking SFX");
        addLinkFormatting(7, 0, 13);
        creditsText[8] = new AttributedString("Free SFX: Button click SFX");
        addLinkFormatting(8, 0, 10);
        creditsText[9] = new AttributedString("Nikunj Chawla and Aaron Kandikatla: All other sprites");
        addLinkFormatting(9, 0, 13);
        addLinkFormatting(9, 18, 35);
        creditsText[10] = new AttributedString("And most importantly, thank you for enjoying our");
        for(AttributedString a : creditsText) {
            try {
                a.addAttribute(TextAttribute.FONT, Font.createFont(Font.TRUETYPE_FONT, Runner.class.getResource("/dete.otf").openStream()).deriveFont(14.0f));
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0, y = 82; i < clickableNames.length; ++i, y += 40) {
            clickableNames[i] = new JPanel();
            JPanel b = clickableNames[i];
            b.setVisible(false);
            b.setLocation(85 - creditsList.getX(), y - creditsList.getY());
            if(i == 11)
                b.setLocation(230 - creditsList.getX(), 85 + 40 * 9 - creditsList.getY());
            b.setOpaque(false);
            b.setName(Integer.toString(i));
            b.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    String url = "";
                    String name = ((JPanel) e.getSource()).getName();
                    int nameInt = Integer.parseInt(name);
                    switch (nameInt) {
                        case 0:
                            url = "http://undertale.com/";
                            break;
                        case 1:
                            url = "https://goo.gl/ofAZRS";
                            break;
                        case 2:
                            url = "https://goo.gl/YuLmCt";
                            break;
                        case 3:
                            url = "https://goo.gl/uNnWl8";
                            break;
                        case 4:
                            url = "https://goo.gl/H2By5H";
                            break;
                        case 5:
                            url = "https://goo.gl/DY0LJB";
                            break;
                        case 6:
                            url = "http://soundbible.com/992-Right-Cross.html";
                            break;
                        case 7:
                            url = "";
                            break;
                        case 8:
                            url = "http://www.freesfx.co.uk/sfx/button";
                            break;
                        case 9:
                            url = "mailto:nikchawla312@gmail.com";
                            break;
                        case 10:
                            url = "";
                            break;
                        case 11:
                            url = "mailto:aaron4game@gmail.com";
                            break;
                    }
                    try {
                        if (!url.equals(""))
                            Desktop.getDesktop().browse(new URI(url));
                    } catch (IOException | URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (b.isVisible()) {
                        Runner.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        creditsList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    Runner.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    creditsList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    b.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }

            });
        }
        clickableNames[0].setSize(69, 13);
        clickableNames[1].setSize(29, 13);
        clickableNames[2].setSize(37, 13);
        clickableNames[3].setSize(117, 13);
        clickableNames[4].setSize(133, 13);
        clickableNames[5].setSize(45, 13);
        clickableNames[6].setSize(93, 13);
        clickableNames[7].setSize(103, 13);
        clickableNames[8].setSize(69, 13);
        clickableNames[9].setSize(103, 13);
        clickableNames[10].setSize(0, 13);
        clickableNames[11].setSize(132, 13);
        for (JPanel b : clickableNames)
            creditsList.add(b);
        creditsList.setLayout(null);
    }

    private void addLinkFormatting(int creditsIndex, int beginningIndex, int lastIndex) {
        creditsText[creditsIndex].addAttribute(TextAttribute.FOREGROUND, new Color(0, 82, 232), beginningIndex, lastIndex);
        creditsText[creditsIndex].addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE, beginningIndex, lastIndex);
    }

    void run(Graphics g) {
        drawArrows(g);
        drawGif(g);
        if (frameCounter1 != 251)
            ++frameCounter1;
        if (frameCounter1 > 100) {
            if (frameCounter1 != 251)
                ++frameCounter1;
            if (frameCounter1 > 200) {
                if (scale > 0)
                    scale -= 20;
                else if (!slammed) {
                    slam.changeVolume(sfxVolume);
                    slam.play();
                    slammed = true;
                }
            }
            starterTitle(g);
            drawSubtitle(g);
            if(frameCounter1 > 250) {
                Runner.showInitialButtons();
                moveHeart();
                constrain();
                zToStart(g);
                startArrows(g);
                gifDog(g);
                gifFire(g);
                gifGreaterDog(g);
                easyButton(g);
                survivalButton(g);
                mediumButton(g);
                hardButton(g);
                drawButtons(g);
                drawBackMessage(g);
                drawBlueHeartFlash(g);
                drawSpears(g);
                drawNames(g);
                drawDots(g);
                if (heartsActivated())
                    drawSans(g);
                heartMouse(g);
                creditsList.checkVisibility();
                if(creditsList.percentageExpanded() != 1.0) {
                    for(JPanel b : clickableNames)
                        b.setVisible(false);
                    if (!playChosen)
                        Runner.moveButtons(false);
                }
                isLoaded = true;
                if (heartsActivated()) {
                    ++flickerCounter;
                    flickeringHeart();
                    if (megalovania.isStopped() && boneCounter <= 265) {
                        megalovania.changeVolume(musicVolume);
                        megalovania.play();
                    }
                    if (hitGround)
                        drawBones(g);
                }
            }
        }
    }

    private void drawDots(Graphics g) {
        g.drawImage(dots[numHeartsActivated()], 4, 588, null);
    }

    private void drawArrows(Graphics g) {
        g.drawImage(Runner.blueArr, -100, 1000, null);
        g.drawImage(Runner.redArr, -100, 1000, null);
        g.drawImage(Runner.reverseArr, -100, 1000, null);
    }

    private void drawGif(Graphics g) {
        if (gifOneIndex > 31)
            gifOneIndex = 0;
        if (gifTwoIndex > 79)
            gifTwoIndex = 0;
        g.drawImage(Runner.gifUndyne[gifOneIndex], -100, 1000, null);
        g.drawImage(Runner.gifUndying[gifTwoIndex], -100, 1000, null);
        ++gifOneIndex;
        ++gifTwoIndex;
    }

    private void drawSubtitle(Graphics g) {
        AffineTransform trans = new AffineTransform();
        trans.translate(96 - scale / 2.0, 195 - scale / 2.0);
        trans.scale((subtitle.getWidth() + scale) / (double) subtitle.getWidth(), (subtitle.getHeight() + scale * 49.0 / 460.0) / (double) subtitle.getHeight());
        Graphics2D g2d = (Graphics2D) g;
        if (heartsActivated())
            g2d.drawImage(subtitleBlue, trans, null);
        else
            g2d.drawImage(subtitle, trans, null);
    }

    private void moveHeart() {
        if (zCounter > 10 && !Runner.getHelpStarter() && !creditsList.getExpanding()) {
            ++moveCounter;
            if (moveCounter > 2) {
                final int speed = 2;
                if (right) {
                    heartX += speed;
                    heartMoved = true;
                }
                if (left) {
                    heartX -= speed;
                    heartMoved = true;
                }
                if (up && !heartsActivated() || up && hitGround) {
                    heartY -= speed;
                    heartMoved = true;
                }
                if (down && !heartsActivated() || down && hitGround) {
                    heartY += speed;
                    heartMoved = true;
                }
                if (heartsActivated() && heartY >= 283 && !hitGround) {
                    wall.changeVolume(sfxVolume);
                    wall.play();
                    hitGround = true;
                }
                if (heartsActivated() && !hitGround)
                    heartY += 2 * speed;
                if (moveCounter == 4)
                    moveCounter = 1;
            }
        }
    }

    private void starterTitle(Graphics g) {
        if (!heartsActivated()) {
            if (fadeIn < 1)
                fadeIn += 0.02;
            else if (zCounter < 11)
                ++zCounter;
            if (undyneCount % 7 == 0) {
                randX = rand.nextInt(3);
                randY = rand.nextInt(3);
            }
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) fadeIn));
            g2d.drawImage(undyne, 62 + randX, 69 + randY, null);
            g2d.dispose();
            ++undyneCount;
            if (undyneCount == 7)
                undyneCount = 0;
        }
    }

    private void constrain() {
        if (heartX < -288)
            heartX = -288;
        else if (heartX > 296)
            heartX = 296;
        if (heartY < -301)
            heartY = -301;
        else if (heartY > 283)
            heartY = 283;
    }

    private void zToStart(Graphics g) {
        if (!heartsActivated()) {
            float opacity = (float) fadeStart;
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            if (Runner.onFrontButton() && !playChosen) {
                if (warningCounter == 0)
                    g2d.drawImage(zSelect, 166, 485 + shift, null);
                else {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2d.drawImage(zSelect, (int) (166 - zSelect.getWidth() * 0.25), (int) (485 + shift - 0.25 * zSelect.getHeight()), (int) (1.5 * zSelect.getWidth()), (int) (1.5 * zSelect.getHeight()), null);
                }
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(keys, 179, 490 + 50 - 20, null);
            }
            else if(!playChosen) {
                AffineTransform trans = new AffineTransform();
                trans.translate(174.5, 486 + shift);
                g2d.drawImage(selectOption, trans, null);
                if(warningCounter == 0)
                    g2d.drawImage(keys, 179, 490 + 50 - 20, null);
                else {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2d.drawImage(keys, (int) (179 - 0.5 * keys.getWidth() / 2), (int) (490 + 50 - 20 - 0.5 * keys.getHeight() / 2), (int) (1.5 * keys.getWidth()), (int) (1.5 * keys.getHeight()), null);
                }
            }
            else if((!easyButtonRectRed || !isOnEasy) && (!hardButtonRectRed || !isOnHard) && (!survivalButtonRectRed || !isOnSurvival) && (!mediumButtonRectRed || !isOnMedium)) {
                AffineTransform trans = new AffineTransform();
                trans.translate(174.5, 486 + shift);
                g2d.drawImage(selectDifficulty, trans, null);
                if(warningCounter == 0)
                    g2d.drawImage(keys, 179, 490 + 50 - 20, null);
                else {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2d.drawImage(keys, (int) (179 - 0.5 * keys.getWidth() / 2), (int) (490 + 50 - 20 - 0.5 * keys.getHeight() / 2), (int) (1.5 * keys.getWidth()), (int) (1.5 * keys.getHeight()), null);
                }
            } else {
                if (warningCounter == 0)
                    g2d.drawImage(start, 174, 485 + shift, null);
                else {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2d.drawImage(start, (int) (174 - start.getWidth() * 0.25), (int) (485 + shift - 0.25 * start.getHeight()), (int) (1.5 * start.getWidth()), (int) (1.5 * start.getHeight()), null);
                }
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(keys, 179, 490 + 50 - 20, null);
            }
            if (warningCounter != 0)
                --warningCounter;
            g2d.dispose();
        }
        if (flashCount % 2 == 0) {
            if (fadeStart <= 1 && !switchFade)
                fadeStart += 0.02;
            else {
                switchFade = true;
                fadeStart -= 0.02;
                if (fadeStart < 0.03)
                    switchFade = false;
            }
        }
        ++flashCount;
        if (flashCount == 2)
            flashCount = 0;
    }

    private void hardButton(Graphics g) {
        if (playChosen) {
            g.setColor(new Color(246, 138, 21));
            isOnHard = (heartX + 288 + 16 >= 413 && heartX + 288 <= 561 && heartY + 300 <= 363 + shift && heartY + 300 + 16 >= 300 + shift);
            if (hardButtonCount % 6 == 0) {
                if (isOnHard) {
                    if (hardButtonRect < 60) {
                        hardButtonRect += 5;
                        playFire = true;
                    } else {
                        if (playFire) {
                            playFire = false;
                            flare.changeVolume(sfxVolume);
                            flare.play();
                        }
                        hardButtonRectRed = true;
                        easyButtonRectRed = false;
                        survivalButtonRectRed = false;
                        mediumButtonRectRed = false;
                    }
                } else if (hardButtonRect > 0 && !hardButtonRectRed) {
                    hardButtonRect -= 5;
                    playFire = true;
                }
            }
            ++hardButtonCount;
            if (hardButtonCount == 6)
                hardButtonCount = 0;
            if (!hardButtonRectRed)
                g.fillRect(380 + 37, 360 - (300 - (Math.abs(300 - hardButtonRect))) + shift, 140, hardButtonRect);
            else {
                fire2 = true;
                g.setColor(Color.RED);
                g.fillRect(380 + 37, 300 + shift, 140, 60);
            }
        } else {
            hardButtonRect = 0;
            hardButtonRectRed = false;
        }
    }

    private void mediumButton(Graphics g) {
        if (playChosen) {
            g.setColor(new Color(246, 138, 21));
            isOnMedium = (heartX + 288 + 16 >= 226 && heartX + 288 <= 374 && heartY + 300 <= 363 + shift && heartY + 300 + 16 >= 300 + shift);
            if (mediumButtonCount % 6 == 0) {
                if (isOnMedium) {
                    if (mediumButtonRect < 60) {
                        mediumButtonRect += 5;
                        playBork = true;
                    } else {
                        if (playBork) {
                            playBork = false;
                            bork.changeVolume(sfxVolume);
                            bork.play();
                        }
                        mediumButtonRectRed = true;
                        easyButtonRectRed = false;
                        hardButtonRectRed = false;
                        survivalButtonRectRed = false;
                    }
                } else if (mediumButtonRect > 0 && !mediumButtonRectRed) {
                    mediumButtonRect -= 5;
                    playBork = true;
                }
            }
            ++mediumButtonCount;
            if (mediumButtonCount == 6)
                mediumButtonCount = 0;
            if (!mediumButtonRectRed)
                g.fillRect(380 + 37 - 187, 360 - (300 - (Math.abs(300 - mediumButtonRect))) + shift, 140, mediumButtonRect);
            else {
                g.setColor(Color.ORANGE);
                g.fillRect(380 + 37 - 187, 300 + shift, 140, 60);
            }
        } else {
            mediumButtonRect = 0;
            mediumButtonRectRed = false;
        }
    }

    private void easyButton(Graphics g) {
        if (playChosen) {
            g.setColor(new Color(246, 138, 21));
            isOnEasy = (heartX + 288 + 16 >= 39 && heartX + 288 <= 187 && heartY + 300 <= 363 + shift && heartY + 300 + 16 >= 300 + shift);
            if (easyButtonCount % 6 == 0) {
                if (isOnEasy) {
                    if (easyButtonRect < 60) {
                        easyButtonRect += 5;
                        playBark = true;
                    } else {
                        if (playBark) {
                            bark.changeVolume(sfxVolume);
                            bark.play();
                            playBark = false;
                        }
                        easyButtonRectRed = true;
                        hardButtonRectRed = false;
                        survivalButtonRectRed = false;
                        mediumButtonRectRed = false;
                    }
                } else if (easyButtonRect > 0 && !easyButtonRectRed) {
                    easyButtonRect -= 5;
                    playBark = true;
                }
            }
            ++easyButtonCount;
            if (easyButtonCount == 6)
                easyButtonCount = 0;
            if (!easyButtonRectRed)
                g.fillRect(80 - 37, 360 - (300 - (Math.abs(300 - easyButtonRect))) + shift, 140, easyButtonRect);
            else {
                g.setColor(new Color(0, 234 - 30, 77 - 30));
                g.fillRect(80 - 37, 300 + shift, 140, 60);
            }
        } else {
            easyButtonRect = 0;
            easyButtonRectRed = false;
        }
    }

    private void survivalButton(Graphics g) {
        if (playChosen) {
            g.setColor(new Color(246, 138, 21));
            isOnSurvival = (heartX + 288 + 16 >= 226 && heartX + 288 <= 374 && heartY + 300 <= 468 + shift && heartY + 300 + 16 >= 405 + shift);
            if (survivalButtonCount % 6 == 0) {
                if (isOnSurvival) {
                    if (survivalButtonRect < 60) {
                        survivalButtonRect += 5;
                        playSpear = true;
                        activateSpears = false;
                    } else {
                        if (playSpear) {
                            activateSpears = true;
                            playSpear = false;
                        }
                        easyButtonRectRed = false;
                        hardButtonRectRed = false;
                        survivalButtonRectRed = true;
                        mediumButtonRectRed = false;
                    }
                } else {
                    if (survivalButtonRect > 0 && !survivalButtonRectRed) {
                        survivalButtonRect -= 5;
                        playSpear = true;
                        activateSpears = false;
                    }
                }
            }
            ++survivalButtonCount;
            if (survivalButtonCount == 6)
                survivalButtonCount = 0;
            if (!survivalButtonRectRed)
                g.fillRect(80 - 37 + 148 + 39, 360 + 105 - (300 - (Math.abs(300 - survivalButtonRect))) + shift, 140, survivalButtonRect);
            else {
                g.setColor(new Color(0, 191, 255));
                g.fillRect(80 - 37 + 148 + 39, 300 + 105 + shift, 140, 60);
            }
        } else {
            survivalButtonRect = 0;
            survivalButtonRectRed = false;
        }
    }

    private void heartMouse(Graphics g) {
        if (heartsActivated() && !hitGround)
            g.drawImage(heartMouseBlue, heartX + 283, heartY + 298, null);
        else
            g.drawImage(heartMouse, heartX + 283, heartY + 298 + flickerChangeY, null);
    }

    private void gifFire(Graphics g) {
        if (frameCounter % 4 == 0 || count2 < 0)
            ++count2;
        ++frameCounter;
        if (frameCounter == 4)
            frameCounter = 0;
        if (count2 == 38)
            count2 = 0;
        if (fire2 && count2 >= 0 && hardButtonRectRed)
            g.drawImage(fire[count2], 432, 173 + shift, null);
    }

    private void gifDog(Graphics g) {
        if (easyButtonRectRed) {
            ++dogCount;
            if (dogCount % 20 == 0) {
                if (dogFrame == 0)
                    dogFrame = 1;
                else
                    dogFrame = 0;
                dogCount = 0;
            }
            g.drawImage(dog[dogFrame], 130 - 39, 261 + shift, null);
        }
    }

    private void gifGreaterDog(Graphics g) {
        if (mediumButtonRectRed) {
            ++greaterDogCount;
            if (greaterDogCount % 20 == 0) {
                if (greaterDogFrame != 1)
                    greaterDogFrame = 1;
                else if (greaterDogDirection == 1) {
                    greaterDogFrame = 2;
                    greaterDogDirection = -1;
                } else {
                    greaterDogFrame = 0;
                    greaterDogDirection = 1;
                }
                greaterDogCount = 0;
            }
            g.drawImage(greaterDog[greaterDogFrame], 259, 164 + shift, null);
        }
    }

    private void drawSpears(Graphics g) {
        if (activateSpears) {
            Graphics2D g2d = (Graphics2D) g.create();
            if (!spearAppearPlayed) {
                spearLocation = (Point2D) SPEAR_SPAWN.clone();
                spearAppear.changeVolume(sfxVolume);
                spearAppear.play();
                spearAppearPlayed = true;
            }
            if (spearFrame <= 30) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, spearFrame / 30f));
                g2d.drawImage(spear, (int) Math.round(SPEAR_SPAWN.getX()), (int) Math.round(SPEAR_SPAWN.getY()), null);
            } else if (!spearLocation.equals(SPEAR_END)) {
                spearLocation = new Point2D.Double(spearLocation.getX() + (SPEAR_END.getX() - SPEAR_SPAWN.getX()) / 20, spearLocation.getY() + (SPEAR_END.getY() - SPEAR_SPAWN.getY()) / 20);
                g2d.drawImage(spear, (int) Math.round(spearLocation.getX()), (int) Math.round(spearLocation.getY()), null);
            } else {
                if (!spearHitPlayed) {
                    spearHit.changeVolume(sfxVolume);
                    spearHit.play();
                    spearHitPlayed = true;
                }
                g2d.drawImage(spear, (int) Math.round(SPEAR_END.getX()), (int) Math.round(SPEAR_END.getY()), null);
            }
            if (spearFrame == 30) {
                spearFly.changeVolume(sfxVolume);
                spearFly.play();
            }
            if (spearCounter % 3 == 0) {
                ++spearFrame;
                spearCounter = 1;
            }
            ++spearCounter;
            g2d.dispose();
        } else {
            spearCounter = 1;
            spearFrame = 0;
            spearAppearPlayed = false;
            spearHitPlayed = false;
        }
    }

    private void startArrows(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        if (!heartMoved && arrowsShouldShow)
            g2d.drawImage(arrows, 271, 352, null);
        if (arrowsCounter % 85 == 0) {
            arrowsShouldShow = !arrowsShouldShow;
            arrowsCounter = 1;
        }
        ++arrowsCounter;
        g2d.dispose();
    }

    private void drawButtons(Graphics g) {
        if (playChosen) {
            g.drawImage(buttons, 39, 300, null);
            g.drawImage(survival, 226, 405, null);
        }
    }

    private void drawBackMessage(Graphics g) {
        if (playChosen) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setFont(Runner.deteFontScore);
            g2d.setColor(Color.WHITE);
            String backMessage = "Press X to go back";
            g2d.drawString(backMessage, (Runner.getFrame().getWidth() - g.getFontMetrics(Runner.deteFontScore).stringWidth(backMessage)) / 2, 30);
        }
    }

    private void drawBones(Graphics g) {
        ++boneCounter;
        if (boneCounter > 50) {
            if (!showBones) {
                boneSound.changeVolume(sfxVolume);
                boneSound.play();
            }
            showBones = true;
            if (boneCounter < 300) {
                g.drawImage(bones, -11, boneY, null);
                if (boneCounter > 265 && !megalovania.isStopped())
                    megalovania.stop();
            } else {
                hideSans = true;
                resetEgg();
            }
            if (boneY > 510)
                boneY -= 3;
            if (heartY + 300 + 20 >= boneY) {
                if (!flickering) {
                    damage.changeVolume(sfxVolume);
                    damage.play();
                }
                flickering = true;
            }
        }
    }

    private void drawSans(Graphics g) {
        if (sansCount % 10 == 0) {
            if (sansFrame < 4 || hitGround)
                ++sansFrame;
        }
        if (sansFrame > 10)
            sansFrame = 10;
        if (sansFrame < 10)
            g.drawImage(sans[sansFrame], 237, 15, null);
        else if (!hideSans)
            g.drawImage(sans[9], 237, 15, null);
        if (sansFrame < 4 || hitGround)
            ++sansCount;
        if (sansCount == 10)
            sansCount = 0;
    }

    private void drawBlueHeartFlash(Graphics g) {
        if (heartsActivated())
            blueHeartOpacity = 1f;
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, blueHeartOpacity));
        g2d.drawImage(blueHeartFlash, 163, 205, null);
        g2d.dispose();
        if (blueHeartOpacity > 0.02f && blueHeartFlashCounter % 2 == 0)
            blueHeartOpacity -= 0.02f;
        ++blueHeartFlashCounter;
        if (blueHeartFlashCounter == 2)
            blueHeartFlashCounter = 0;
    }

    private void drawNames(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(Runner.deteFontScore);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Made by Nikunj Chawla and Aaron Kandikatla", nameStringX, 580);
        g2d.dispose();
        if (++nameStringCounter % 2 == 0) {
            --nameStringX;
            nameStringCounter = 0;
            if (nameStringX == -560)
                nameStringX = 610;
        }
    }

    private void flickeringHeart() {
        if (flickering) {
            --flickeringCountdown;
            if (flickerCounter % 16 == 0) {
                if (flickerChangeY == 0)
                    flickerChangeY = 9000;
                else
                    flickerChangeY = 0;
            }
        }
        if (flickeringCountdown == 0) {
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

    boolean isMedium() {
        return mediumButtonRectRed;
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

    void deactivateHearts() {
        heartsActivated[0] = false;
        heartsActivated[1] = false;
        heartsActivated[2] = false;
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
        return heartsActivated[0] && heartsActivated[1] && heartsActivated[2];
    }

    int numHeartsActivated() {
        int activatedHearts = 0;
        for (boolean activated : heartsActivated) {
            if (activated)
                activatedHearts += 1;
        }
        return activatedHearts;
    }

    void activateBlueHeartFlash() {
        blueHeartOpacity = 1f;
    }

    void resetVars(boolean isReplaying) {
        isLoaded = false;
        if (!isReplaying) {
            hardButtonRectRed = false;
            easyButtonRectRed = false;
            mediumButtonRectRed = false;
            survivalButtonRectRed = false;
        }

    }

    boolean shouldStart() {
        return !isOnLink() && !isOnHelp() && !heartsActivated() && (hardButtonRectRed && isOnHard || easyButtonRectRed && isOnEasy || survivalButtonRectRed && isOnSurvival || mediumButtonRectRed && isOnMedium);
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

    boolean isOnPlay() {
        return (heartX + 288 + 16 >= 76 && heartX + 288 <= 224 && heartY + 300 <= 342 + 20 && heartY + 300 + 16 >= 280 + 20 && !heartsActivated());
    }

    boolean isOnCreator() {
        return (heartX + 288 + 16 >= 376 && heartX + 288 <= 524 && heartY + 300 <= 342 + 20 && heartY + 300 + 16 >= 280 + 20 && !heartsActivated());
    }

    void warningOn() {
        warningCounter = 50;
    }

    PopUp getCreditsList() {
        return creditsList;
    }

    void playDamage() {
        damage.changeVolume(sfxVolume);
        damage.play();
    }

    void playChosen(boolean play) {
        playChosen = play;
    }

    void deactivateSpears() {
        activateSpears = false;
    }

    boolean isPlayChosen() {
        return playChosen;
    }

    void setHeartX(int x) {
        heartX = x;
    }

    void setHeartY(int y) {
        heartY = y;
    }
    
    public static void playClick() {
        click.changeVolume(sfxVolume);
        click.play();
    }

}
