package defense;

import nikunj.classes.GradientButton;
import nikunj.classes.PopUp;
import nikunj.classes.Slider;
import nikunj.classes.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Runner extends JPanel implements ActionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    
    private static char dir = 'u';
    
    private static final String NOTHING = "bad time";
    private static String typed = "";
    private static String activated = "";
    private static final String[] easyMessage = {"You did well...      ", "only because", "I went easy."};
    private static final String[] mediumMessage = {"Not bad, punk!", "Let me go", "harder on you."};
    private static final String[] hardMessage = {"You really are", "something, human.", "Nice job!"};
    private static final String[] MAIN_SOUND_NAMES = {"/soj.ogg", "/survivalSoj.ogg", "/bath.ogg", "/survivalBath.ogg"};
    
    private static double fadeStart = 0;
    private static double musicMutedVolume = 1;
    private static double sfxMutedVolume = 1;
    
    private static int nothingCounter = 0;
    private static final int DELAY = 10;
    private static int breakCount = 0;
    private static int breakFrame = 0;
    private static int flickeringHeart = 0;
    private static int count = 0;
    private static int gifCount = 0;
    private static int gameOverCount = 0;
    private static int gameOverFrame = 0;
    private static int frameCounter = 0;
    private static int flashCount = 0;
    private static int alwaysOnTopCounter = 0;
    private static int speechCounter = 0;
    private static int speechCounterPrev = 0;
    private static int speechDelayCounter = 0;
    private static int speechX, speechY;
    private static int mainIndex = 0;
    private static int levelIndex = 0;
    private static int score = 0;
    private static int lastAttack = 1;
    
    private static boolean isGenocide = false;
    private static boolean survival = false;
    private static boolean heartDone = false;
    private static boolean gameOverDone = false;
    private static boolean firstEnd = true;
    private static boolean secondEnd = true;
    private static boolean beginning = true;
    private static boolean automatic = false;
    private static boolean isGameOver = false;
    private static boolean switchFade = false;
    private static boolean allStopped = false;
    private static boolean musicMuted = false;
    private static boolean sfxMuted = false;
    private static boolean speechDone = false;
    private static boolean helpStarter = false;
    private static boolean isReplaying = false;
    static boolean isFirstTime = true;
    
    private static Timer timer;
    
    private static FocusListener checkFocus;
    
    private static BufferedImage heart;
    private static BufferedImage replay;
    private static BufferedImage close;
    private static BufferedImage draggable;
    private static BufferedImage music;
    private static BufferedImage sfx;
    private static BufferedImage speech;
    private static BufferedImage credits;
    private static BufferedImage help;
    private static BufferedImage play;
    private static BufferedImage creator;
    static BufferedImage blueArr;
    static BufferedImage redArr;
    static BufferedImage reverseArr;
    private static BufferedImage[] heartBreak;
    private static BufferedImage[] gameOver;
    private static BufferedImage[] levels = new BufferedImage[4];
    static BufferedImage[] gif;
    static BufferedImage[] gif2;
    
    private static Sound main;
    private static Sound sojSlow;
    private static Sound gameDone;
    private static Sound startScreen;
    private static Sound undyne;
    private static Sound undying;
    private static Sound heal;
    private static Sound block;
    private static Sound split;
    private static Sound broke;
    private static Sound asgore;
    private static Sound error;
    private static Sound[] mainSounds = new Sound[4];
    
    private static GradientButton closeButton;
    private static GradientButton draggableButton;
    private static GradientButton musicButton;
    private static GradientButton sfxButton;
    private static GradientButton creditsButton;
    private static GradientButton helpButton;
    private static GradientButton playButton;
    private static GradientButton creatorButton;
    
    private static Slider musicSlider;
    private static Slider sfxSlider;
    
    private static PopUp creditsList;
    
    private static Font deteFontNorm;
    static Font deteFontSpeech;
    static Font deteFontScore;
    
    private static Attack a1;
    private static Attacks a;
    private static StartScreen stage = new StartScreen();
    private static Player p = new Player();
    private static Help helper = new Help();
    
    private static SplashScreen loading;
    
    private static JFrame frame;
    
    public static void main(String... args) throws IOException, UnsupportedAudioFileException, FontFormatException {
        Arrow.p = p;
        if(isFirstTime) {
            EventQueue.invokeLater(() -> {
                SwingWorker<Object, Void> worker = new SwingWorker<Object, Void>() {
                    @Override
                    protected Object doInBackground() throws NullPointerException, IllegalStateException {
                        loading = SplashScreen.getSplashScreen();
                        int height = 210, width = 410;
                        Graphics2D g2d = loading.createGraphics();
                        g2d.setComposite(AlphaComposite.Clear);
                        g2d.fillRect(0, 0, width, height);
                        g2d.setPaintMode();
                        loading.update();
                        loading.close();
                        return null;
                    }
                };
                worker.execute();
                try {
                    worker.get();
                }
                catch(InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
            sojSlow = new Sound(Runner.class.getResource("/sojSlow.ogg"), true);
            startScreen = new Sound(Runner.class.getResource("/WF.ogg"), true);
            undyne = new Sound(Runner.class.getResource("/undyne.ogg"), false);
            undying = new Sound(Runner.class.getResource("/undying.ogg"), false);
            heal = new Sound(Runner.class.getResource("/heal.ogg"), false);
            block = new Sound(Runner.class.getResource("/block.ogg"), false);
            gameDone = new Sound(Runner.class.getResource("/dt.ogg"), true);
            split = new Sound(Runner.class.getResource("/split.ogg"), false);
            broke = new Sound(Runner.class.getResource("/heartBreak.ogg"), false);
            asgore = new Sound(Runner.class.getResource("/asgore.ogg"), false);
            error = new Sound(Runner.class.getResource("/error.ogg"), false);
            heart = ImageIO.read(Runner.class.getResource("/heart.png"));
            heart = getCompatibleImage(heart);
            heartBreak = new BufferedImage[49];
            for(int i = 0; i <= 48; ++i) {
                heartBreak[i] = ImageIO.read(Runner.class.getResource("/gif/heartBreak" + i + ".png"));
                heartBreak[i] = getCompatibleImage(heartBreak[i]);
            }
            gameOver = new BufferedImage[226];
            for(int i = 0; i <= 225; ++i) {
                gameOver[i] = ImageIO.read(Runner.class.getResource("/gif/gameOver" + i + ".png"));
                gameOver[i] = getCompatibleImage(gameOver[i]);
            }
            gif2 = new BufferedImage[80];
            for(int i = 0; i <= 79; ++i) {
                gif2[i] = ImageIO.read(Runner.class.getResource("/gif/undying" + i + ".png"));
                gif2[i] = getCompatibleImage(gif2[i]);
            }
            levels[0] = ImageIO.read(Runner.class.getResource("/levelOne.png"));
            levels[1] = ImageIO.read(Runner.class.getResource("/levelTwo.png"));
            levels[2] = ImageIO.read(Runner.class.getResource("/levelThree.png"));
            levels[3] = ImageIO.read(Runner.class.getResource("/levelFour.png"));
            for(int i = 0; i < levels.length; ++i)
                levels[i] = getCompatibleImage(levels[i]);
            blueArr = ImageIO.read(Runner.class.getResource("/arrowB.png"));
            blueArr = getCompatibleImage(blueArr);
            redArr = ImageIO.read(Runner.class.getResource("/arrowR.png"));
            redArr = getCompatibleImage(redArr);
            reverseArr = ImageIO.read(Runner.class.getResource("/arrowRE.png"));
            reverseArr = getCompatibleImage(reverseArr);
            replay = ImageIO.read(Runner.class.getResource("/replay.png"));
            replay = getCompatibleImage(replay);
            close = ImageIO.read(Runner.class.getResource("/close.png"));
            close = getCompatibleImage(close);
            draggable = ImageIO.read(Runner.class.getResource("/draggable.png"));
            draggable = getCompatibleImage(draggable);
            music = ImageIO.read(Runner.class.getResource("/music.png"));
            music = getCompatibleImage(music);
            sfx = ImageIO.read(Runner.class.getResource("/sfx.png"));
            sfx = getCompatibleImage(sfx);
            speech = ImageIO.read(Runner.class.getResource("/speech.png"));
            speech = getCompatibleImage(speech);
            credits = ImageIO.read(Runner.class.getResource("/credits.png"));
            credits = getCompatibleImage(credits);
            help = ImageIO.read(Runner.class.getResource("/help.png"));
            help = getCompatibleImage(help);
            play = ImageIO.read(Runner.class.getResourceAsStream("/play.png"));
            play = Runner.getCompatibleImage(play);
            creator = ImageIO.read(Runner.class.getResourceAsStream("/creator.png"));
            creator = Runner.getCompatibleImage(creator);
            for(int i = 0; i < MAIN_SOUND_NAMES.length; ++i)
                mainSounds[i] = new Sound(Runner.class.getResource(MAIN_SOUND_NAMES[i]), true);
            URL fontUrl = Runner.class.getResource("/dete.otf");
            deteFontNorm = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(12.0f);
            deteFontSpeech = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(14.0f);
            deteFontScore = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(22.0f);
        }
        if(gif == null || gif.length != 32) {
            gif = new BufferedImage[32];
            for(int i = 0; i <= 31; ++i) {
                gif[i] = ImageIO.read(Runner.class.getResource("/gif/frame" + i + ".png"));
                gif[i] = getCompatibleImage(gif[i]);
            }
        }
        new Runner("Undyne: Absolute");
        startScreen.changeVolume(musicMutedVolume);
        if(!isReplaying)
            startScreen.play();
        isReplaying = false;
    }
    
    private Runner(String s) {
        frame = new JFrame(s);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Runner bp = new Runner();
        bp.setBounds(0, 0, 600, 600);
        frame.add(bp);
        closeButton = new GradientButton(close, Color.BLACK, Color.RED, 2, 2, 24, 24) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {}
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            
            @Override
            public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseDragged(MouseEvent e) {}
            
            @Override
            public void mouseMoved(MouseEvent e) {}
            
            @Override
            public void beforeDraw(Graphics g) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 26, 26);
            }
            
        };
        draggableButton = new GradientButton(draggable, Color.BLACK, Color.BLUE, 30, 2, 24, 24) {
            private static final long serialVersionUID = 1L;
            private int xPos, yPos;
            
            @Override
            public void mouseClicked(MouseEvent e) {}
            
            @Override
            public void mousePressed(MouseEvent e) {
                xPos = e.getX();
                yPos = e.getY();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            
            @Override
            public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseDragged(MouseEvent e) {
                frame.setLocation((int) (frame.getLocation().getX() + e.getX() - xPos), (int) (frame.getLocation().getY() + e.getY() - yPos));
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {}
            
        };
        musicButton = new GradientButton(music, Color.BLACK, new Color(0, 208, 208), 545, 2, 24, 24) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if(musicMuted) {
                    musicSlider.setPercentage(musicMutedVolume);
                    if(main != null)
                        main.changeVolume(musicMutedVolume);
                    if(gameDone != null)
                        gameDone.changeVolume(musicMutedVolume);
                    if(startScreen != null)
                        startScreen.changeVolume(musicMutedVolume);
                    if(stage != null)
                        stage.changeMusicVol(musicMutedVolume);
                }
                else {
                    musicMutedVolume = musicSlider.getPercentage();
                    musicSlider.setPercentage(0);
                    if(main != null)
                        main.changeVolume(0);
                    if(gameDone != null)
                        gameDone.changeVolume(0);
                    if(startScreen != null)
                        startScreen.changeVolume(0);
                    if(stage != null)
                        stage.changeMusicVol(0);
                }
                musicMuted = !musicMuted;
            }
            
            @Override
            public void mousePressed(MouseEvent e) {}
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            
            @Override
            public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseDragged(MouseEvent e) {}
            
            @Override
            public void mouseMoved(MouseEvent e) {}
            
            @Override
            public void afterDraw(Graphics g) {
                if(musicMuted) {
                    if(musicSlider.getPercentage() > 0)
                        musicMuted = false;
                    else {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setColor(Color.WHITE);
                        g2d.setStroke(new BasicStroke(2));
                        g2d.draw(new Line2D.Float(549, 22, 565, 4));
                    }
                }
                else if(musicSlider.getPercentage() == 0) {
                    musicMuted = true;
                    musicMutedVolume = 0;
                }
            }
            
        };
        
        sfxButton = new GradientButton(sfx, Color.BLACK, Color.GREEN, 573, 2, 24, 24) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if(sfxMuted) {
                    sfxSlider.setPercentage(sfxMutedVolume);
                    Attack.changeVol(sfxMutedVolume);
                    StartScreen.changeSfxVol(sfxMutedVolume);
                    undyne.changeVolume(sfxMutedVolume);
                    undying.changeVolume(sfxMutedVolume);
                    heal.changeVolume(sfxMutedVolume);
                    block.changeVolume(sfxMutedVolume);
                    split.changeVolume(sfxMutedVolume);
                    broke.changeVolume(sfxMutedVolume);
                    asgore.changeVolume(sfxMutedVolume);
                    heal.changeVolume(sfxMutedVolume);
                    error.changeVolume(sfxMutedVolume);
                }
                else {
                    sfxMutedVolume = sfxSlider.getPercentage();
                    sfxSlider.setPercentage(0);
                    Attack.changeVol(0);
                    StartScreen.changeSfxVol(0);
                    undyne.changeVolume(0);
                    undying.changeVolume(0);
                    heal.changeVolume(0);
                    block.changeVolume(0);
                    split.changeVolume(0);
                    broke.changeVolume(0);
                    asgore.changeVolume(0);
                    heal.changeVolume(0);
                    error.changeVolume(0);
                }
                sfxMuted = !sfxMuted;
            }
            
            @Override
            public void mousePressed(MouseEvent e) {}
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            
            @Override
            public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseDragged(MouseEvent e) {}
            
            @Override
            public void mouseMoved(MouseEvent e) {}
            
            @Override
            public void afterDraw(Graphics g) {
                if(sfxMuted) {
                    if(sfxSlider.getPercentage() > 0)
                        sfxMuted = false;
                    else {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setColor(Color.WHITE);
                        g2d.setStroke(new BasicStroke(2));
                        g2d.draw(new Line2D.Float(577, 22, 593, 4));
                    }
                }
                else if(sfxSlider.getPercentage() == 0) {
                    sfxMuted = true;
                    sfxMutedVolume = 0;
                }
            }
            
        };
        
        creditsButton = new GradientButton(credits, Color.BLACK, new Color(148, 0, 211), 76, 380 + 20, 148, 62) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void mouseClicked(MouseEvent e) {}
            
            @Override
            public void mousePressed(MouseEvent e) {}
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            
            @Override
            public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseDragged(MouseEvent e) {}
            
            @Override
            public void mouseMoved(MouseEvent e) {}
            
            @Override
            public boolean onButton() {
                return isDisplayable() && stage.isOnLink();
            }
            
        };
        
        helpButton = new GradientButton(help, Color.BLACK, new Color(148, 0, 211), 376, 380 + 20, 148, 62) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void mouseClicked(MouseEvent e) {}
            
            @Override
            public void mousePressed(MouseEvent e) {}
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            
            @Override
            public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseDragged(MouseEvent e) {}
            
            @Override
            public void mouseMoved(MouseEvent e) {}
            
            @Override
            public boolean onButton() {
                return isDisplayable() && stage.isOnHelp();
            }
            
        };
        
        playButton = new GradientButton(play, Color.BLACK, new Color(148, 0, 211), 76, 300, 148, 62) {
            
            @Override
            public void mouseClicked(MouseEvent e) {}
            
            @Override
            public void mousePressed(MouseEvent e) {}
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            
            @Override
            public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseDragged(MouseEvent e) {}
            
            @Override
            public void mouseMoved(MouseEvent e) {}
            
            @Override
            public boolean onButton() {
                return isDisplayable() && stage.isOnPlay();
            }
            
        };
        
        creatorButton = new GradientButton(creator, Color.BLACK, new Color(148, 0, 211), 376, 300, 148, 62) {
            
            @Override
            public void mouseClicked(MouseEvent e) {}
            
            @Override
            public void mousePressed(MouseEvent e) {}
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            
            @Override
            public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseDragged(MouseEvent e) {}
            
            @Override
            public void mouseMoved(MouseEvent e) {}
            
            @Override
            public boolean onButton() {
                return isDisplayable() && stage.isOnCreator();
            }
            
        };
        
        checkFocus = new FocusListener();
        
        if(isFirstTime) {
            musicSlider = new Slider(Color.WHITE, new Color(150, 150, 150), new Color(0, 208, 208), true, 553, 30, 10, 50);
            sfxSlider = new Slider(Color.WHITE, new Color(150, 150, 150), Color.GREEN, true, 581, 30, 10, 50);
        }
        
        creditsList = stage.getCreditsList();
        PopUp helpPopUp = helper.getHelpPopUp();
        
        frame.add(closeButton);
        frame.add(draggableButton);
        frame.add(musicButton);
        frame.add(sfxButton);
        frame.add(creditsButton);
        frame.add(helpButton);
        frame.add(playButton);
        frame.add(creatorButton);
        frame.add(musicSlider);
        frame.add(sfxSlider);
        frame.add(creditsList);
        frame.add(helpPopUp);
        
        MouseListener errorListener = new MouseListener() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if(beginning && StartScreen.isLoaded && !checkFocus.isJustFocused()) {
                    if(error.isFinished())
                        error.play();
                    stage.warningOn();
                }
                else if(checkFocus.isJustFocused())
                    checkFocus.deactivateJustFocused();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {}
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            
            @Override
            public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseExited(MouseEvent e) {}
            
        };
        
        frame.addMouseListener(errorListener);
        creditsButton.addMouseListener(errorListener);
        helpButton.addMouseListener(errorListener);
        playButton.addMouseListener(errorListener);
        creatorButton.addMouseListener(errorListener);
        frame.addKeyListener(this);
        frame.addWindowListener(checkFocus);
    
        musicSlider.setVisible(true);
        sfxSlider.setVisible(true);
        creditsList.setVisible(true);
        helpPopUp.setVisible(true);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(600, 600);
        frame.setLocation((dim.width - frame.getWidth()) / 2, (dim.height - frame.getHeight()) / 2);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.getContentPane().setLayout(null);
        frame.requestFocus();
        frame.setVisible(true);
    }
    
    private class FocusListener implements WindowListener {
        private boolean justFocused = false;
        
        @Override
        public void windowOpened(WindowEvent e) {}
        
        @Override
        public void windowClosing(WindowEvent e) {}
        
        @Override
        public void windowClosed(WindowEvent e) {}
        
        @Override
        public void windowIconified(WindowEvent e) {}
        
        @Override
        public void windowDeiconified(WindowEvent e) {}
        
        @Override
        public void windowActivated(WindowEvent e) {
            justFocused = true;
        }
        
        @Override
        public void windowDeactivated(WindowEvent e) {}
        
        boolean isJustFocused() {
            return justFocused;
        }
        
        void deactivateJustFocused() {
            justFocused = false;
        }
        
    }
    
    private Runner() {
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    static BufferedImage getCompatibleImage(BufferedImage current) {
        GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        if(current.getColorModel().equals(gfxConfig.getColorModel()))
            return current;
        BufferedImage optimized = gfxConfig.createCompatibleImage(current.getWidth(), current.getHeight(), current.getTransparency());
        Graphics2D g2d = optimized.createGraphics();
        g2d.drawImage(current, 0, 0, null);
        optimized.setAccelerationPriority(1);
        return optimized;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!allStopped) {
            if(++alwaysOnTopCounter >= 20) {
                alwaysOnTopCounter = 20;
                frame.setAlwaysOnTop(false);
            }
            if(main != null)
                main.changeVolume(musicSlider.getPercentage());
            if(gameDone != null)
                gameDone.changeVolume(musicSlider.getPercentage());
            if(startScreen != null)
                startScreen.changeVolume(musicSlider.getPercentage());
            if(stage != null)
                stage.changeMusicVol(musicSlider.getPercentage());
            Attack.changeVol(sfxSlider.getPercentage());
            StartScreen.changeSfxVol(sfxSlider.getPercentage());
            undyne.changeVolume(sfxSlider.getPercentage());
            undying.changeVolume(sfxSlider.getPercentage());
            heal.changeVolume(sfxSlider.getPercentage());
            block.changeVolume(sfxSlider.getPercentage());
            split.changeVolume(sfxSlider.getPercentage());
            broke.changeVolume(sfxSlider.getPercentage());
            asgore.changeVolume(sfxSlider.getPercentage());
            heal.changeVolume(sfxSlider.getPercentage());
            error.changeVolume(sfxSlider.getPercentage());
            ++frameCounter;
            if(frameCounter == 1000)
                frameCounter = 0;
            if(beginning) {
                drawBG(g);
                if(stage.shouldShow()) {
                    if(!stage.isPlayChosen()) {
                        creditsButton.draw(g);
                        creditsButton.setVisible(true);
                        helpButton.draw(g);
                        helpButton.setVisible(true);
                        playButton.draw(g);
                        playButton.setVisible(true);
                        creatorButton.draw(g);
                        creatorButton.setVisible(true);
                    }
                    else
                        moveButtons(true);
                    if(stage.heartsActivated())
                        startScreen.stop();
                    else if(beginning && startScreen.isStopped())
                        startScreen.play();
                }
                stage.run(g);
                drawCheat(g);
            }
            else {
                if(p.getHit()) {
                    p.decreaseCounter();
                    if(frameCounter % 16 == 0) {
                        if(flickeringHeart == 0)
                            flickeringHeart = 9000;
                        else
                            flickeringHeart = 0;
                    }
                    if(p.getTimeoutCounter() == 0) {
                        p.setHit(false);
                        p.resetTimeoutCounter();
                        flickeringHeart = 0;
                    }
                }
                if(p.getHealth() != 0) {
                    drawBG(g);
                    drawCheat(g);
                    drawSqu(g);
                    drawCircle(g);
                    drawHeart(g);
                    p.shield(g, dir);
                    gif(g);
                    if(survival)
                        g.drawImage(levels[levelIndex], 433, 4, null);
                    a1.spawnArrows(g, p);
                    p.drawHealth(g);
                    automatic();
                }
                else {
                    drawBG(g);
                    if(firstEnd) {
                        main.stop();
                        firstEnd = false;
                    }
                    else if(!heartDone) {
                        breakHeart(g);
                    }
                    else if(secondEnd) {
                        secondEnd = false;
                        gameDone.play();
                    }
                    else if(!gameOverDone)
                        gameOver(g);
                    else {
                        drawGameOver(g, gameOverFrame);
                        if(isGameOver)
                            drawReplay(g, 0);
                    }
                }
            }
        }
        if(a != null && a.getIsFinished()) {
            if(isGenocide && count == 19 || !isGenocide && count == 10) {
                if(main != null)
                    main.stop();
                g.drawImage(speech, speechX, speechY, null);
            }
        }
        if(a != null && a.getIsFinished() && (isGenocide && count == 19 || !isGenocide && count == 10))
            undyneSpeech(g);
        closeButton.draw(g);
        draggableButton.draw(g);
        musicButton.draw(g);
        sfxButton.draw(g);
        if(musicButton.onButton() || musicSlider.isVisible() && onSlider("music") || musicSlider.actionPerforming()) {
            musicSlider.setVisible(true);
            musicSlider.draw(g);
        }
        else
            musicSlider.setVisible(false);
        if(sfxButton.onButton() || sfxSlider.isVisible() && onSlider("sfx") || sfxSlider.actionPerforming()) {
            sfxSlider.setVisible(true);
            sfxSlider.draw(g);
        }
        else
            sfxSlider.setVisible(false);
        if(speechDone)
            drawReplay(g, 10);
        helper.initiate(g, helpStarter);
        if(survival && !beginning)
            printScore(g);
        g.dispose();
    }
    
    private void drawCheat(Graphics g) {
        if(automatic)
            activated = "Cheat Activated";
        else {
            activated = "";
            nothingCounter = 0;
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(deteFontNorm);
        g2.setColor(Color.GREEN);
        if(!activated.equals(""))
            g2.drawString(activated.substring(0, nothingCounter), 0, 13 + 30);
        if(frameCounter % 7 == 0 && nothingCounter < activated.length())
            nothingCounter++;
    }
    
    private void automatic() {
        if(automatic && a1.getList().size() > 0) {
            ArrayList<Arrow> arrows = a1.getList();
            double time = Integer.MAX_VALUE;
            char pointTowards = 'u';
            for(Arrow a : arrows) {
                switch(a.getDir()) {
                    case 'l':
                        if(time > (a.getX() - 308) / (double) a.getSpeed()) {
                            time = (a.getX() - 308) / (double) a.getSpeed();
                            if(a.getDirectionNotSwitched() && a.getReverse())
                                pointTowards = 'l';
                            else
                                pointTowards = 'r';
                        }
                        break;
                    case 'r':
                        if(time > (261 - a.getX()) / (double) a.getSpeed()) {
                            time = (261 - a.getX()) / (double) a.getSpeed();
                            if(a.getDirectionNotSwitched() && a.getReverse())
                                pointTowards = 'r';
                            else
                                pointTowards = 'l';
                        }
                        break;
                    case 'u':
                        if(time > (a.getY() - 295) / (double) a.getSpeed()) {
                            time = (a.getY() - 295) / (double) a.getSpeed();
                            if(a.getDirectionNotSwitched() && a.getReverse())
                                pointTowards = 'u';
                            else
                                pointTowards = 'd';
                        }
                        break;
                    case 'd':
                        if(time > (252 - a.getY()) / (double) a.getSpeed()) {
                            time = (252 - a.getY()) / (double) a.getSpeed();
                            if(a.getDirectionNotSwitched() && a.getReverse())
                                pointTowards = 'd';
                            else
                                pointTowards = 'u';
                        }
                        break;
                }
            }
            dir = pointTowards;
        }
    }
    
    private void nothing() {
        if(typed.length() > NOTHING.length())
            typed = typed.substring(typed.length() - NOTHING.length(), typed.length());
        if(typed.length() == NOTHING.length()) {
            if(typed.equalsIgnoreCase(NOTHING)) {
                automatic = !automatic;
                typed = typed.substring(0, typed.length() - NOTHING.length());
            }
        }
    }
    
    private void gif(Graphics g) {
        if(a == null || !a.getIsFinished() || a.getIsFinished() && ((isGenocide && count != 19) || (!isGenocide && count != 10))) {
            int maxCount;
            int gifChange;
            if(isGenocide) {
                maxCount = 79;
                gifChange = 4;
            }
            else {
                maxCount = 31;
                gifChange = 3;
            }
            if(isGenocide) {
                if(count == maxCount)
                    count = 0;
                else if(gifCount % gifChange == 0)
                    ++count;
                ++gifCount;
                if(gifCount == gifChange)
                    gifCount = 0;
            }
            else {
                if(count == maxCount)
                    count = 0;
                else if(gifCount % gifChange == 0 && (count - 1) % 3 != 0)
                    ++count;
                else if((count - 1) % 3 == 0 && gifCount % 4 == 0)
                    ++count;
                ++gifCount;
                if(gifCount == gifChange && (count - 1) % 3 != 0)
                    gifCount = 0;
                else if((count - 1) % 3 == 0 && gifCount == 4)
                    gifCount = 0;
            }
        }
        Graphics2D g2d = (Graphics2D) g.create();
        float opacity = 0.3f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        int gifXShift;
        if(isGenocide)
            gifXShift = 198;
        else
            gifXShift = 189;
        g2d.drawImage(gif[count], gifXShift + p.getElementPosition(), 10 + p.getElementPosition(), null);
        g2d.dispose();
    }
    
    private boolean breakHeartException(int breakFrame) {
        int[] exceptions = {2, 6, 8, 12, 14, 18, 20, 22, 23};
        for(int exception : exceptions) {
            if(breakFrame == exception)
                return true;
        }
        return false;
    }
    
    private void makeBreakHeart(Graphics g, int breakFrame) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = heartBreak[breakFrame].getWidth();
        int height = heartBreak[breakFrame].getHeight();
        g2d.drawImage(heartBreak[breakFrame], getWidth() / 2 - width / 2 + 11, getHeight() / 2 - height / 2 + 78, null);
        g2d.dispose();
    }
    
    private void breakHeart(Graphics g) {
        ++breakCount;
        boolean exception = breakHeartException(breakFrame);
        if(breakCount % 4 == 0 && breakCount != 0 && !exception) {
            ++breakFrame;
            if(breakFrame == 25) {
                split.play();
            }
            if(breakFrame == 48)
                heartDone = true;
            breakCount = 0;
        }
        else if(exception) {
            switch(breakFrame) {
                case 2:
                case 12:
                    if(breakCount % 24 == 0) {
                        ++breakFrame;
                        breakCount = 0;
                    }
                    break;
                case 14:
                    if(breakCount % 28 == 0) {
                        ++breakFrame;
                        breakCount = 0;
                    }
                    break;
                default:
                    if(breakCount % 8 == 0) {
                        ++breakFrame;
                        if(breakFrame == 9)
                            broke.play();
                        breakCount = 0;
                    }
            }
        }
        makeBreakHeart(g, breakFrame);
    }
    
    private void gameOver(Graphics g) {
        ++gameOverCount;
        if(gameOverCount % 4 == 0 && gameOverCount != 0) {
            ++gameOverFrame;
            if(gameOverFrame % 2 == 0 && (gameOverFrame > 67 && gameOverFrame < 99 || gameOverFrame > 137 && gameOverFrame < 149 || gameOverFrame > 162 && gameOverFrame < 192))
                asgore.play();
            if(gameOverFrame >= 225)
                gameOverDone = true;
            gameOverCount = 0;
        }
        drawGameOver(g, gameOverFrame);
    }
    
    private void drawGameOver(Graphics g, int gameOverFrame) {
        Graphics2D g2d = (Graphics2D) g.create();
        if(gameOverFrame > 225)
            gameOverFrame = 225;
        g2d.drawImage(gameOver[gameOverFrame], 154 + getWidth() / 2 - 600 / 2 + 1, 25 + getHeight() / 2 - 338 / 2, null);
        g2d.dispose();
        if(gameOverFrame == 225)
            isGameOver = true;
    }
    
    private void drawSqu(Graphics g) {
        int size = 80;
        Color translucentWhite = new Color(255, 255, 255, 200);
        g.setColor(translucentWhite);
        while(size > 72) {
            g.drawRect(getWidth() / 2 - size / 2 + p.getElementPosition(), getHeight() / 2 - size / 2 + p.getElementPosition(), size, size);
            --size;
        }
    }
    
    private void drawBG(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    private void drawHeart(Graphics g) {
        int width = 30;
        int height = 30;
        g.drawImage(heart, getWidth() / 2 - width / 2 + 1 + p.getElementPosition() + flickeringHeart, getHeight() / 2 - height / 2 + p.getElementPosition(), null);
    }
    
    private void drawCircle(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawOval(getWidth() / 2 - 25 + p.getElementPosition(), getHeight() / 2 - 25 + p.getElementPosition(), 50, 50);
    }
    
    private void drawReplay(Graphics g, int xShift) {
        float opacity = (float) fadeStart;
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(replay, 187 + xShift, 421, null);
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
    
    private void undyneSpeech(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(deteFontSpeech);
        g2d.setColor(Color.BLACK);
        String[] message;
        if(!isGenocide && !stage.isMedium())
            message = easyMessage;
        else if(!isGenocide)
            message = mediumMessage;
        else
            message = hardMessage;
        String print = "";
        if(speechCounter < message[0].length() + 1) {
            print = message[0].substring(0, speechCounter);
            g.drawString(message[0].substring(0, speechCounter), speechX + 30, speechY + 20);
        }
        else if(speechCounter < message[1].length() + message[0].length() + 2) {
            print = message[1].substring(0, speechCounter - (message[0].length() + 1));
            g.drawString(message[0], speechX + 30, speechY + 20);
            g.drawString(message[1].substring(0, speechCounter - (message[0].length() + 1)), speechX + 30, speechY + 40);
        }
        else if(speechCounter < message[2].length() + message[1].length() + message[0].length() + 3) {
            print = message[2].substring(0, speechCounter - (message[0].length() + message[1].length() + 2));
            g.drawString(message[0], speechX + 30, speechY + 20);
            g.drawString(message[1], speechX + 30, speechY + 40);
            g.drawString(message[2].substring(0, speechCounter - (message[0].length() + message[1].length() + 2)), speechX + 30, speechY + 60);
        }
        else {
            g.drawString(message[0], speechX + 30, speechY + 20);
            g.drawString(message[1], speechX + 30, speechY + 40);
            g.drawString(message[2], speechX + 30, speechY + 60);
            speechDone = true;
        }
        if(speechCounter != speechCounterPrev && print.length() != 0 && print.charAt(print.length() - 1) != ' ') {
            if(isGenocide)
                undying.play();
            else
                undyne.play();
        }
        speechCounterPrev = speechCounter;
        if(speechCounter < message[2].length() + message[1].length() + message[0].length() + 3 && speechDelayCounter % 6 == 0)
            ++speechCounter;
        ++speechDelayCounter;
        if(speechDelayCounter == 6)
            speechDelayCounter = 0;
    }
    
    private static void printScore(Graphics g) {
        if(a != null && a.isNewAttack() && a.getCurrentAttack() != lastAttack && a1.getList().size() == 0) {
            lastAttack = a.getCurrentAttack();
            switch(levelIndex) {
                case 0:
                    score += 5;
                    break;
                case 1:
                    score += 15;
                    break;
                case 2:
                    score += 20;
                    break;
                case 3:
                    score += 25;
                    break;
            }
        }
        g.setFont(deteFontScore);
        g.setColor(Color.YELLOW);
        g.drawString("Score: " + score, 5, 590);
    }
    
    private static void hideButtons() {
        creditsButton.setVisible(false);
        helpButton.setVisible(false);
        playButton.setVisible(false);
        creatorButton.setVisible(false);
    }
    
    static void moveButtons(boolean shouldMove) {
        if(shouldMove) {
            creditsButton.setY(600);
            helpButton.setY(600);
            playButton.setY(600);
            helpButton.setY(600);
        }
        else {
            creditsButton.setY(400);
            helpButton.setY(400);
            playButton.setY(300);
            creatorButton.setY(300);
        }
    }
    
    static void changeMain() {
        ++levelIndex;
        if(levelIndex > 1)
            heal.play();
        main.stop();
        main = mainSounds[++mainIndex];
        main.changeVolume(musicMutedVolume);
        main.play();
    }
    
    static void changeGif() {
        isGenocide = true;
        gif = gif2;
        p.setBaseDamage(2);
        p.setDamageOffset(4);
        p.convertHealth();
    }
    
    static void finalBoost() {
        heal.play();
        p.healthBoost();
    }
    
    static boolean isSurvival() {
        return survival;
    }
    
    static boolean getHelpStarter() {
        return helpStarter;
    }
    
    static JFrame getFrame() {
        return frame;
    }
    
    private void restartApplication() {
        timer.stop();
        allStopped = true;
        stage.resetVars(isReplaying);
        a.resetVars();
        a1.resetVars();
        if(gameDone != null)
            gameDone.stop();
        dir = 'u';
        typed = "";
        activated = "";
        fadeStart = 0;
        nothingCounter = 0;
        breakCount = 0;
        breakFrame = 0;
        flickeringHeart = 0;
        count = 0;
        gifCount = 0;
        gameOverCount = 0;
        gameOverFrame = 0;
        frameCounter = 0;
        flashCount = 0;
        alwaysOnTopCounter = 0;
        speechCounter = 0;
        speechCounterPrev = 0;
        speechDelayCounter = 0;
        score = 0;
        mainIndex = 0;
        levelIndex = 0;
        lastAttack = 1;
        isGenocide = false;
        survival = false;
        heartDone = false;
        gameOverDone = false;
        firstEnd = true;
        secondEnd = true;
        beginning = true;
        automatic = false;
        isGameOver = false;
        switchFade = false;
        allStopped = false;
        isFirstTime = false;
        speechDone = false;
        timer = null;
        gif = null;
        main = null;
        closeButton = null;
        draggableButton = null;
        musicButton = null;
        sfxButton = null;
        creditsButton = null;
        helpButton = null;
        playButton = null;
        creatorButton = null;
        a1 = null;
        a = null;
        frame.dispose();
        frame = null;
        stage = new StartScreen();
        p = new Player();
        allStopped = false;
        System.gc();
        try {
            main();
        }
        catch(IOException | UnsupportedAudioFileException | FontFormatException e) {
            e.printStackTrace();
        }
    }
    
    private void start() {
        isGenocide = stage.isHard();
        survival = stage.isSurvival();
        a = new Attacks(isGenocide, survival, stage.isMedium());
        a1 = new Attack(new ArrayList<>(), a);
        a.setAttack(a1);
        hideButtons();
        if(isGenocide) {
            p.setHealth(60);
            p.setBaseDamage(3);
            p.setDamageOffset(12);
        }
        else {
            p.setHealth(20);
            p.setBaseDamage(0);
            p.setDamageOffset(2);
        }
        if(isGenocide) {
            main = mainSounds[2];
            speechX = 310;
            speechY = 60;
            gif = gif2;
        }
        else {
            if(stage.isMedium())
                main = mainSounds[0];
            else
                main = sojSlow;
            speechX = 305;
            speechY = 50;
        }
        beginning = false;
        startScreen.stop();
        main.changeVolume(musicMutedVolume);
        main.play();
        dir = 'u';
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                dir = 'u';
                stage.setUp();
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                dir = 'd';
                stage.setDown();
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                dir = 'r';
                stage.setRight();
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                dir = 'l';
                stage.setLeft();
                break;
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            case KeyEvent.VK_X:
                if(helpStarter) {
                    stage.playClick();
                    helpStarter = false;
                }
                else if(creditsList.getExpanding()) {
                    stage.playClick();
                    creditsList.setExpanding(false);
                }
                else if(stage.isPlayChosen() && beginning) {
                    stage.playClick();
                    stage.deactivateSpears();
                    stage.playChosen(false);
                    moveButtons(false);
                    stage.setHeartX(5);
                    stage.setHeartY(72);
                }
                break;
            case KeyEvent.VK_Z:
                if(beginning) {
                    if(!stage.isPlayChosen()) {
                        if(creditsButton.onButton()) {
                            stage.playClick();
                            creditsList.setExpanding(true);
                        }
                        else if(helpButton.onButton()) {
                            stage.playClick();
                            helpStarter = true;
                        }
                        else if(playButton.onButton()) {
                            stage.playClick();
                            stage.playChosen(true);
                            moveButtons(true);
                            stage.setHeartX(5);
                            stage.setHeartY(75);
                        }
                    }
                    if(stage.isOnHeartOne() && !stage.heartOneActivated()) {
                        block.play();
                        stage.activateHeartOne();
                        stage.activateBlueHeartFlash();
                    }
                    else if(stage.isOnHeartTwo() && !stage.heartTwoActivated()) {
                        block.play();
                        stage.activateHeartTwo();
                        stage.activateBlueHeartFlash();
                    }
                    else if(stage.isOnHeartThree() && !stage.heartThreeActivated()) {
                        block.play();
                        stage.activateHeartThree();
                        stage.activateBlueHeartFlash();
                    }
                    else if(stage.shouldStart()) {
                        stage.playClick();
                        start();
                    }
                    else if(stage.numHeartsActivated() > 0 && !stage.heartsActivated()) {
                        stage.deactivateHearts();
                        stage.playDamage();
                    }
                }
                else if(!secondEnd && !isGameOver && !isFirstTime) {
                    gameOverFrame = 225;
                    isGameOver = true;
                }
                else if(isGameOver && !allStopped || speechDone && !allStopped)
                    restartApplication();
                break;
            case KeyEvent.VK_V:
                if(timer.getDelay() != 0)
                    timer.setDelay(0);
                else
                    timer.setDelay(10);
                break;
            case KeyEvent.VK_R:
                if(isGameOver && !allStopped || speechDone && !allStopped) {
                    isReplaying = true;
                    restartApplication();
                    start();
                }
                break;
        }
        if(e.getKeyChar() != '￿')
            typed += e.getKeyChar();
        nothing();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                stage.setUpf();
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                stage.setDownf();
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                stage.setRightf();
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                stage.setLeftf();
                break;
        }
    }
    
    private static boolean onSlider(String slider) {
        Slider s;
        GradientButton gb;
        if(slider.equals("music")) {
            s = musicSlider;
            gb = musicButton;
        }
        else {
            s = sfxSlider;
            gb = sfxButton;
        }
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        mousePos = new Point((int) (mousePos.getX() - s.getLocationOnScreen().getX()), (int) (mousePos.getY() - s.getLocationOnScreen().getY()));
        Rectangle bounds = s.getBounds();
        bounds.setBounds(gb.getX() - s.getX(), -6, gb.getWidth(), s.getHeight() + 6);
        return bounds.contains(mousePos);
    }
    
    static boolean onFrontButton() {
        return playButton.onButton() || creatorButton.onButton() || creditsButton.onButton() || helpButton.onButton();
    }
    
}
