package defense;

import customAttackMaker.ArrowBar;
import customAttackMaker.AttackBar;
import customAttackMaker.BottomMenuBar;
import customAttackMaker.CustomAttacks;
import nikunj.classes.GradientButton;
import nikunj.classes.KeyAction;
import nikunj.classes.MouseClickTolerance;
import nikunj.classes.PopUp;
import nikunj.classes.Slider;
import nikunj.classes.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
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
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Runner extends JPanel implements ActionListener, KeyListener, MouseWheelListener {
    private static final long serialVersionUID = 1L;
    
    private static int nothingCounter = 0;
    private static int delay = 10;
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
    private static int loadingCounter = 0;
    private static int loadingFrame = 0;
    public static int customAttacksCounter = 0;
    
    private static double fadeStart = 0;
    private static double musicMutedVolume = 1;
    private static double sfxMutedVolume = 1;
    
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
    private static boolean isPlayTimerDone = false;
    private static boolean isStopTimerDone = false;
    private static boolean isStartTimerDone = false;
    private static boolean isOpenCreatorTimerDone = false;
    private static boolean isCloseCreatorTimerDone = false;
    static boolean isFirstTime = true;
    public static boolean isCustomAttack = false;
    public static boolean canBeStopped = false;
    
    private static char dir = 'u';
    
    private static final String NOTHING = "bad time";
    private static String typed = "";
    private static String activated = "";
    private static final String[] EASY_MESSAGE = {"You did well...      ", "only because", "I went easy."};
    private static final String[] MEDIUM_MESSAGE = {"Not bad, punk!", "Let me go", "harder on you."};
    private static final String[] HARD_MESSAGE = {"You really are", "something, human.", "Nice job!"};
    private static final String[] MAIN_SOUND_NAMES = {"/soj.ogg", "/survivalSoj.ogg", "/bath.ogg", "/survivalBath.ogg"};
    
    private static Timer mainTimer;
    private static Timer oneSecondDelay;
    
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
    private static BufferedImage numberFieldGlow;
    static BufferedImage blueArr;
    static BufferedImage redArr;
    static BufferedImage reverseArr;
    public static BufferedImage dragArrowIcon;
    public static BufferedImage bottomMenuBar;
    public static BufferedImage cat;
    public static BufferedImage importThing;
    public static BufferedImage newThing;
    public static BufferedImage addAttack;
    public static BufferedImage addAttackDisabled;
    public static BufferedImage ticked;
    public static BufferedImage customArrowDirection;
    public static BufferedImage deleteArrow;
    public static BufferedImage deleteAttack;
    public static BufferedImage droppedDown;
    public static BufferedImage droppedClosed;
    public static BufferedImage newArrow;
    public static BufferedImage arrowImg;
    public static BufferedImage checkBox;
    public static BufferedImage bottomPlayButton;
    public static BufferedImage bottomPlayButtonDisabled;
    public static BufferedImage bottomStopButton;
    public static BufferedImage bottomStopButtonDisabled;
    public static BufferedImage exportButton;
    public static BufferedImage exportButtonDisabled;
    public static BufferedImage bottomTabUp;
    public static BufferedImage bottomTabDown;
    public static BufferedImage orientationShiftButton;
    private static BufferedImage[] heartBreak;
    private static BufferedImage[] gameOver;
    private static BufferedImage[] levels = new BufferedImage[4];
    private static BufferedImage[] loadingCreator = new BufferedImage[48];
    private static BufferedImage[] gif;
    static BufferedImage[] gifUndyne;
    static BufferedImage[] gifUndying;
    
    public static ImageIcon warning;
    
    private static Sound main;
    private static Sound sojBeta;
    private static Sound gameDone;
    private static Sound startScreen;
    private static Sound creatorMusic;
    private static Sound undyne;
    private static Sound undying;
    private static Sound heal;
    private static Sound block;
    private static Sound split;
    private static Sound broke;
    private static Sound asgore;
    private static Sound error;
    private static Sound[] mainSounds = new Sound[4];
    
    private static GradientButtonTolerance closeButton;
    private static GradientButtonTolerance draggableButton;
    private static GradientButtonTolerance musicButton;
    private static GradientButtonTolerance sfxButton;
    
    private static GradientButton creditsButton;
    private static GradientButton helpButton;
    private static GradientButton playButton;
    private static GradientButton creatorButton;
    
    private static Slider musicSlider;
    private static Slider sfxSlider;
    
    private static PopUp creditsList;
    
    private static ArrowBar.NumberFieldFocus focused;
    
    private static Font deteFontNorm;
    static Font deteFontScore;
    public static Font deteFontSpeech;
    public static Font deteFontEditor;
    public static Font deteFontEditorAttack;
    public static Font deteFontError;
    
    private static UndyneWindowFocusListener checkFocus;
    
    private static Attack a1;
    private static Attacks a;
    private static StartScreen stage = new StartScreen();
    private static CustomAttacks customAttackMaker = new CustomAttacks();
    private static Player p = new Player();
    private static Help helper = new Help();
    private static SplashScreen loading;
    private static BottomMenuBar bottomBar = CustomAttacks.getBottomMenuBar();
    
    public static final Color UNFOCUSED_COLOR = new Color(255, 255, 255, 127);
    private static final Color HEART_BOX_COLOR = new Color(255, 255, 255, 200);
    
    private static final BasicStroke AUDIO_SLASH_STROKE = new BasicStroke(2);
    
    private static final Line2D.Float AUDIO_SLASH_LINE = new Line2D.Float(4, 20, 20, 2);
    
    private static AffineTransform loadingTransform = new AffineTransform();
    
    private static JPanel topBar;
    
    private static JFrame frame;
    
    public static void main(String... args) throws IOException, UnsupportedAudioFileException, FontFormatException {
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
            
            if("1.8.0_172".compareTo(System.getProperty("java.version")) > 0) {
                int updateJava = JOptionPane.showConfirmDialog(null, "You need to update your Java version to play. Go to download site?", "Java Outdated", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, Runner.warning);
                if(updateJava == 0) {
                    try {
                        Desktop.getDesktop().browse(new URI("http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html"));
                    }
                    catch(URISyntaxException | IOException e) {
                        e.printStackTrace();
                    }
                }
                System.exit(0);
            }
            
            Arrow.p = p;
            
            sojBeta = new Sound(Runner.class.getResource("/sojBeta.ogg"), true);
            startScreen = new Sound(Runner.class.getResource("/WF.ogg"), true);
            creatorMusic = new Sound(Runner.class.getResource("/DS.ogg"), true);
            undyne = new Sound(Runner.class.getResource("/undyne.wav"), false);
            undying = new Sound(Runner.class.getResource("/undying.wav"), false);
            heal = new Sound(Runner.class.getResource("/heal.ogg"), false);
            block = new Sound(Runner.class.getResource("/block.ogg"), false);
            gameDone = new Sound(Runner.class.getResource("/dt.ogg"), true);
            split = new Sound(Runner.class.getResource("/split.ogg"), false);
            broke = new Sound(Runner.class.getResource("/heartBreak.ogg"), false);
            asgore = new Sound(Runner.class.getResource("/asgore.ogg"), false);
            error = new Sound(Runner.class.getResource("/error.ogg"), false);
            for(int i = 0; i < MAIN_SOUND_NAMES.length; ++i)
                mainSounds[i] = new Sound(Runner.class.getResource(MAIN_SOUND_NAMES[i]), true);
            heart = getCompatibleImage("/heart.png");
            heartBreak = new BufferedImage[49];
            for(int i = 0; i <= 48; ++i)
                heartBreak[i] = getCompatibleImage("/gif/heartBreak" + i + ".png");
            gameOver = new BufferedImage[226];
            for(int i = 0; i <= 225; ++i)
                gameOver[i] = getCompatibleImage("/gif/gameOver" + i + ".png");
            gifUndying = new BufferedImage[80];
            for(int i = 0; i <= 79; ++i)
                gifUndying[i] = getCompatibleImage("/gif/undying" + i + ".png");
            levels[0] = getCompatibleImage("/levelOne.png");
            levels[1] = getCompatibleImage("/levelTwo.png");
            levels[2] = getCompatibleImage("/levelThree.png");
            levels[3] = getCompatibleImage("/levelFour.png");
            blueArr = getCompatibleImage("/arrowB.png");
            dragArrowIcon = getCompatibleImage("/DragArrowIcon.png");
            arrowImg = getCompatibleImage("/arrow.png");
            cat = getCompatibleImage("/UAT.png");
            importThing = getCompatibleImage("/import.png");
            newThing = getCompatibleImage("/new.png");
            bottomMenuBar = getCompatibleImage("/bottomBar/bottomMenuBar.png");
            ticked = getCompatibleImage("/ticked.png");
            customArrowDirection = getCompatibleImage("/customArrowDirection.png");
            addAttack = getCompatibleImage("/addAttack.png");
            addAttackDisabled = getCompatibleImage("/addAttackDisabled.png");
            deleteAttack = getCompatibleImage("/deleteAttack.png");
            newArrow = getCompatibleImage("/newArrow.png");
            droppedDown = getCompatibleImage("/droppedDown.png");
            droppedClosed = getCompatibleImage("/droppedClosed.png");
            deleteArrow = getCompatibleImage("/deleteArrow.png");
            redArr = getCompatibleImage("/arrowR.png");
            reverseArr = getCompatibleImage("/arrowRE.png");
            replay = getCompatibleImage("/replay.png");
            close = getCompatibleImage("/close.png");
            draggable = getCompatibleImage("/draggable.png");
            music = getCompatibleImage("/music.png");
            sfx = getCompatibleImage("/sfx.png");
            speech = getCompatibleImage("/speech.png");
            credits = getCompatibleImage("/credits.png");
            help = getCompatibleImage("/help.png");
            play = getCompatibleImage("/play.png");
            creator = getCompatibleImage("/creator.png");
            warning = new ImageIcon(Runner.class.getResource("/warning.png"));
            checkBox = getCompatibleImage("/checkBox.png");
            bottomPlayButton = getCompatibleImage("/bottomBar/playButton.png");
            bottomPlayButtonDisabled = getCompatibleImage("/bottomBar/playButtonDisabled.png");
            bottomStopButton = getCompatibleImage("/bottomBar/stopButton.png");
            bottomStopButtonDisabled = getCompatibleImage("/bottomBar/stopButtonDisabled.png");
            exportButton = getCompatibleImage("/bottomBar/exportButton.png");
            exportButtonDisabled = getCompatibleImage("/bottomBar/exportButtonDisabled.png");
            bottomTabDown = getCompatibleImage("/bottomBar/tabDown.png");
            orientationShiftButton = getCompatibleImage("/orientationShiftButton.png");
            bottomTabUp = getCompatibleImage("/bottomBar/tabUp.png");
            numberFieldGlow = getCompatibleImage("/numberFieldGlow.png");
            for(int i = 0; i < 48; ++i)
                loadingCreator[i] = getCompatibleImage("/loading/loading" + i + ".png");
            URL fontUrl = Runner.class.getResource("/dete.otf");
            deteFontNorm = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(12.0f);
            deteFontSpeech = deteFontNorm.deriveFont(14.0f);
            deteFontScore = deteFontNorm.deriveFont(22.0f);
            deteFontEditor = deteFontNorm.deriveFont(10.0f);
            deteFontEditorAttack = deteFontNorm.deriveFont(24.0f);
            deteFontError = deteFontNorm.deriveFont(20.0f);
        }
        if(gifUndyne == null || gifUndyne.length != 32) {
            gifUndyne = new BufferedImage[32];
            for(int i = 0; i <= 31; ++i)
                gifUndyne[i] = getCompatibleImage("/gif/frame" + i + ".png");
        }
        
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        
        Runner runner = new Runner();
        runner.setBounds(0, 0, 600, 600);
        
        mainTimer = new Timer(delay, runner);
        mainTimer.setActionCommand("main");
        mainTimer.start();
        oneSecondDelay = new Timer(1000, runner);
        oneSecondDelay.setRepeats(false);
        
        closeButton = new GradientButtonTolerance(close, Color.BLACK, Color.RED, 2, 2, 24, 24) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void onMouseClick(MouseEvent e) {
                System.exit(0);
            }
            
            @Override
            public void onMousePress(MouseEvent e) {}
            
            @Override
            public void onMouseRelease(MouseEvent e) {}
            
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
                g.fillRect(0, 0, 24, 24);
            }
            
            @Override
            public void afterDraw(Graphics g) {
                if(checkFocus.windowNotFocused()) {
                    g.setColor(UNFOCUSED_COLOR);
                    g.fillRect(0, 0, 24, 24);
                }
            }
        };
        
        draggableButton = new GradientButtonTolerance(draggable, Color.BLACK, Color.BLUE, 30, 2, 24, 24) {
            private static final long serialVersionUID = 1L;
            
            private Point originalLocation;
            private Point pressLocation;
            
            @Override
            public void onMouseClick(MouseEvent e) {}
            
            @Override
            public void onMousePress(MouseEvent e) {
                originalLocation = frame.getLocation();
                pressLocation = e.getLocationOnScreen();
            }
            
            @Override
            public void onMouseRelease(MouseEvent e) {}
            
            @Override
            public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseDragged(MouseEvent e) {
                Point drag = e.getLocationOnScreen();
                int x = Math.round(originalLocation.x + drag.x - pressLocation.x);
                int y = Math.round(originalLocation.y + drag.y - pressLocation.y);
                frame.setLocation(x, y);
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {}
            
            @Override
            public void beforeDraw(Graphics g) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 24, 24);
            }
            
            @Override
            public void afterDraw(Graphics g) {
                if(checkFocus.windowNotFocused()) {
                    g.setColor(UNFOCUSED_COLOR);
                    g.fillRect(0, 0, 24, 24);
                }
            }
        };
        
        musicButton = new GradientButtonTolerance(music, Color.BLACK, new Color(0, 208, 208), 545, 2, 24, 24) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void onMouseClick(MouseEvent e) {
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
            public void onMousePress(MouseEvent e) {}
            
            @Override
            public void onMouseRelease(MouseEvent e) {}
            
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
                g.fillRect(0, 0, 24, 24);
            }
            
            @Override
            public void afterDraw(Graphics g) {
                if(musicMuted) {
                    if(musicSlider.getPercentage() > 0)
                        musicMuted = false;
                    else {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setColor(Color.WHITE);
                        g2d.setStroke(AUDIO_SLASH_STROKE);
                        g2d.draw(AUDIO_SLASH_LINE);
                    }
                }
                else if(musicSlider.getPercentage() == 0) {
                    musicMuted = true;
                    musicMutedVolume = 0;
                }
                if(checkFocus.windowNotFocused()) {
                    g.setColor(UNFOCUSED_COLOR);
                    g.fillRect(0, 0, 24, 24);
                }
            }
        };
        
        sfxButton = new GradientButtonTolerance(sfx, Color.BLACK, Color.GREEN, 573, 2, 24, 24) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void onMouseClick(MouseEvent e) {
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
            public void onMousePress(MouseEvent e) {}
            
            @Override
            public void onMouseRelease(MouseEvent e) {}
            
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
                g.fillRect(0, 0, 24, 24);
            }
            
            @Override
            public void afterDraw(Graphics g) {
                if(sfxMuted) {
                    if(sfxSlider.getPercentage() > 0)
                        sfxMuted = false;
                    else {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setColor(Color.WHITE);
                        g2d.setStroke(AUDIO_SLASH_STROKE);
                        g2d.draw(AUDIO_SLASH_LINE);
                    }
                }
                else if(sfxSlider.getPercentage() == 0) {
                    sfxMuted = true;
                    sfxMutedVolume = 0;
                }
                if(checkFocus.windowNotFocused()) {
                    g.setColor(UNFOCUSED_COLOR);
                    g.fillRect(0, 0, 24, 24);
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
                return isVisible() && stage.isOnLink();
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
                return isVisible() && stage.isOnHelp();
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
                return isVisible() && stage.isOnPlay();
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
                return isVisible() && stage.isOnCreator();
            }
        };
        
        checkFocus = new UndyneWindowFocusListener();
        
        if(isFirstTime) {
            musicSlider = new Slider(Color.WHITE, new Color(150, 150, 150), new Color(0, 208, 208), true, 553, 30, 10, 50);
            sfxSlider = new Slider(Color.WHITE, new Color(150, 150, 150), Color.GREEN, true, 581, 30, 10, 50);
        }
        
        creditsList = stage.getCreditsList();
        PopUp helpPopUp = helper.getHelpPopUp();
        PopUp errorPopUp = customAttackMaker.getErrorPopUp();
        
        setUpKeyBindings(runner);
        
        if(isFirstTime) {
            Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
                private Point pressLocation = new Point(), releaseLocation = new Point();
                
                private long pressTime = 0;
                
                @Override
                public void eventDispatched(AWTEvent event) {
                    MouseEvent e = (MouseEvent) event;
                    
                    switch(e.getID()) {
                        case MouseEvent.MOUSE_PRESSED:
                            pressLocation.setLocation(e.getX(), e.getY());
                            pressTime = System.nanoTime();
                            customAttackMaker.mousePressed();
                            break;
                        case MouseEvent.MOUSE_RELEASED:
                            releaseLocation.setLocation(e.getX(), e.getY());
                            long time = (System.nanoTime() - pressTime) / 1000000;
                            if(time <= 750 && Math.hypot(releaseLocation.x - pressLocation.x, releaseLocation.y - pressLocation.y) <= 5) {
                                MouseEvent clickEvent = new MouseEvent(frame, MouseEvent.MOUSE_CLICKED, System.nanoTime(), 0, pressLocation.x, pressLocation.y, 1, false);
                                if(!CustomAttacks.isFileBeingChosen()) {
                                    barCheckBoxClicked(clickEvent);
                                    if(canBeStopped)
                                        bottomBar.mouseWorks(clickEvent.getPoint());
                                    customAttackMaker.mouseClicked();
                                }
                                if(!beginning || !StartScreen.isLoaded)
                                    checkFocus.deactivateJustFocused();
                            }
                            customAttackMaker.mouseReleased();
                            break;
                    }
                }
            }, AWTEvent.MOUSE_EVENT_MASK);
            
            Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
                MouseEvent e = (MouseEvent) event;
                
                switch(e.getID()) {
                    case MouseEvent.MOUSE_DRAGGED:
                        customAttackMaker.mouseDragged();
                        break;
                }
            }, AWTEvent.MOUSE_MOTION_EVENT_MASK);
        }
        
        bottomBar.setBounds(0, 548, 600, 52);
        
        topBar = new JPanel() {
            
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 600, 28);
                g.setColor(Color.WHITE);
                g.drawLine(0, 28, 600, 28);
                if(checkFocus.windowNotFocused()) {
                    g.setColor(UNFOCUSED_COLOR);
                    g.fillRect(0, 0, 600, 28);
                }
                g.setColor(Color.WHITE);
                g.setFont(deteFontSpeech);
                String exit = "Press X to Exit";
                g.drawString(exit, 300 - g.getFontMetrics().stringWidth(exit) / 2, 20);
            }
            
        };
        
        topBar.setBounds(0, 0, 600, 28);
        
        bottomBar.setOpaque(false);
        topBar.setOpaque(false);
        creditsList.setOpaque(false);
        helpPopUp.setOpaque(false);
        errorPopUp.setOpaque(false);
        closeButton.setOpaque(false);
        draggableButton.setOpaque(false);
        musicButton.setOpaque(false);
        sfxButton.setOpaque(false);
        musicSlider.setOpaque(false);
        sfxSlider.setOpaque(false);
        creditsButton.setOpaque(false);
        helpButton.setOpaque(false);
        playButton.setOpaque(false);
        creatorButton.setOpaque(false);
        
        frame.add(bottomBar);
        frame.add(closeButton);
        frame.add(draggableButton);
        frame.add(musicButton);
        frame.add(sfxButton);
        frame.add(topBar);
        frame.add(creditsList);
        frame.add(helpPopUp);
        frame.add(errorPopUp);
        frame.add(musicSlider);
        frame.add(sfxSlider);
        frame.add(runner);
        
        MouseClickTolerance errorListener = new MouseClickTolerance(5, 750, MouseClickTolerance.ClickLocation.PRESS) {
            
            @Override
            public void onMouseClick(MouseEvent e) {
                if(beginning && StartScreen.isLoaded && !checkFocus.isJustFocused() && !oneSecondDelay.isRunning()) {
                    error.play();
                    checkFocus.deactivateJustFocused();
                    stage.warningOn();
                }
                else if(checkFocus.isJustFocused())
                    checkFocus.deactivateJustFocused();
            }
            
            @Override
            public void onMousePress(MouseEvent e) {}
            
            @Override
            public void onMouseRelease(MouseEvent e) {}
            
            @Override
            public void onMouseEnter(MouseEvent e) {}
            
            @Override
            public void onMouseExit(MouseEvent e) {}
            
        };
        
        frame.addMouseListener(errorListener);
        creditsButton.addMouseListener(errorListener);
        helpButton.addMouseListener(errorListener);
        playButton.addMouseListener(errorListener);
        creatorButton.addMouseListener(errorListener);
        helpPopUp.addMouseListener(errorListener);
        runner.addMouseListener(errorListener);
        frame.getContentPane().addMouseListener(errorListener);
        frame.addMouseWheelListener(runner);
        frame.addKeyListener(runner);
        frame.addWindowListener(checkFocus);
        
        musicSlider.setVisible(true);
        sfxSlider.setVisible(true);
        creditsList.setVisible(false);
        helpPopUp.setVisible(false);
        errorPopUp.setVisible(false);
        playButton.setVisible(false);
        creatorButton.setVisible(false);
        helpButton.setVisible(false);
        creditsButton.setVisible(false);
        topBar.setVisible(false);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(600, 600);
        frame.setLocation((dim.width - frame.getWidth()) / 2, (dim.height - frame.getHeight()) / 2);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.getContentPane().setLayout(null);
        frame.requestFocus();
        frame.setVisible(true);
        
        startScreen.changeVolume(musicSlider.getPercentage());
        if(!isReplaying)
            startScreen.play();
        isReplaying = false;
    }
    
    private static class UndyneWindowFocusListener implements WindowListener {
        private boolean windowFocused = false, justFocused = false;
        
        private Thread justFocusedDisabler;
        
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
            windowFocused = true;
            justFocused = true;
            justFocusedDisabler = new Thread(() -> {
                try {
                    Thread.sleep(500);
                }
                catch(InterruptedException ignored) {}
                justFocused = false;
            });
            justFocusedDisabler.start();
        }
        
        @Override
        public void windowDeactivated(WindowEvent e) {
            justFocusedDisabler.interrupt();
            windowFocused = false;
        }
        
        boolean windowNotFocused() {
            return !windowFocused;
        }
        
        boolean isJustFocused() {
            return justFocused;
        }
        
        void deactivateJustFocused() {
            justFocused = false;
        }
        
    }
    
    static BufferedImage getCompatibleImage(String pathToImage) {
        BufferedImage current = null;
        try {
            current = ImageIO.read(Runner.class.getResource(pathToImage));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        if(current != null) {
            if(current.getColorModel().equals(gfxConfig.getColorModel()))
                return current;
            BufferedImage optimized = gfxConfig.createCompatibleImage(current.getWidth(), current.getHeight(), current.getTransparency());
            Graphics2D g2d = optimized.createGraphics();
            g2d.drawImage(current, 0, 0, null);
            optimized.setAccelerationPriority(1);
            return optimized;
        }
        return null;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!allStopped) {
            if(++alwaysOnTopCounter >= 20) {
                alwaysOnTopCounter = 20;
                frame.setAlwaysOnTop(false);
            }
            if(++customAttacksCounter == 75)
                customAttacksCounter = 0;
            if(main != null)
                main.changeVolume(musicSlider.getPercentage());
            if(gameDone != null)
                gameDone.changeVolume(musicSlider.getPercentage());
            if(startScreen != null)
                startScreen.changeVolume(musicSlider.getPercentage());
            if(stage != null)
                stage.changeMusicVol(musicSlider.getPercentage());
            if(sojBeta != null)
                sojBeta.changeVolume(musicSlider.getPercentage());
            if(creatorMusic != null)
                creatorMusic.changeVolume(musicSlider.getPercentage());
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
            drawBG(g);
            if(beginning && !oneSecondDelay.isRunning()) {
                if(stage.shouldShow()) {
                    if(!stage.isPlayChosen()) {
                        creditsButton.setVisible(true);
                        creditsButton.draw(g);
                        helpButton.setVisible(true);
                        helpButton.draw(g);
                        playButton.setVisible(true);
                        playButton.draw(g);
                        creatorButton.setVisible(true);
                        creatorButton.draw(g);
                    }
                    else
                        moveButtons(true);
                    if(stage.heartsActivated())
                        startScreen.stop();
                    else if(beginning && startScreen.isStopped() && !oneSecondDelay.isRunning() && !isCustomAttack)
                        startScreen.play();
                }
                stage.run(g);
                drawCheat(g);
            }
            else if(isCustomAttack && !oneSecondDelay.isRunning())
                customAttackMaker.perform(g);
            else {
                topBar.setVisible(false);
                customAttackMaker.setAllFieldsVisibility(false);
                if(!oneSecondDelay.isRunning()) {
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
                        drawCheat(g);
                        drawHeartBox(g);
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
                        if(canBeStopped) {
                            if(!oneSecondDelay.isRunning())
                                stop(true);
                        }
                        else if(firstEnd) {
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
            if(!oneSecondDelay.isRunning() && CustomAttacks.isIn() && (isCustomAttack || canBeStopped))
                bottomBar.setVisible(true);
            else
                bottomBar.setVisible(false);
            if(a != null && a.getIsFinished() && !oneSecondDelay.isRunning()) {
                if(isGenocide && count == 19 || !isGenocide && count == 10 && !canBeStopped) {
                    if(main != null)
                        main.stop();
                    g.drawImage(speech, speechX, speechY, null);
                }
            }
            if(a != null && a.getIsFinished() && (isGenocide && count == 19 || !isGenocide && count == 10) && !oneSecondDelay.isRunning()) {
                if(canBeStopped)
                    stop(true);
                else
                    undyneSpeech(g);
            }
            closeButton.visibilityCheck();
            draggableButton.visibilityCheck();
            musicButton.visibilityCheck();
            sfxButton.visibilityCheck();
            if(musicButton.onButton() || musicSlider.isVisible() && onSlider("music") || musicSlider.actionPerforming())
                musicSlider.setVisible(true);
            else
                musicSlider.setVisible(false);
            if(sfxButton.onButton() || sfxSlider.isVisible() && onSlider("sfx") || sfxSlider.actionPerforming())
                sfxSlider.setVisible(true);
            else
                sfxSlider.setVisible(false);
            if(speechDone)
                drawReplay(g, 10);
            helper.initiate(helpStarter);
            if(survival && !beginning)
                printScore(g);
            if(oneSecondDelay.isRunning()) {
                Graphics2D g2d = (Graphics2D) g;
                if(++loadingCounter == 2) {
                    loadingCounter = 0;
                    ++loadingFrame;
                }
                if(loadingFrame == 48)
                    loadingFrame = 0;
                loadingTransform.setToIdentity();
                loadingTransform.translate(216.5, 265.5);
                g2d.drawImage(loadingCreator[loadingFrame], loadingTransform, null);
            }
            else {
                loadingCounter = 0;
                loadingFrame = 0;
            }
        }
        drawFieldFocus(g);
        if(checkFocus.windowNotFocused()) {
            g.setColor(UNFOCUSED_COLOR);
            g.fillRect(0, 0, 600, 600);
        }
        g.dispose();
    }
    
    private void drawFieldFocus(Graphics g) {
        if(focused != null && focused.getFocused() && !CustomAttacks.areAnyDirectionsSelected())
            g.drawImage(numberFieldGlow, -(numberFieldGlow.getWidth() - 30) / 2 + focused.getX() + 1, -(numberFieldGlow.getWidth() - 16) / 2 + focused.getY() + 5, null);
    }
    
    public static void setFocusedField(ArrowBar.NumberFieldFocus nf) {
        focused = nf;
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
        if((automatic || bottomBar.isRobotBoxChecked()) && a1.getList().size() > 0) {
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
            typed = typed.substring(typed.length() - NOTHING.length());
        if(typed.length() == NOTHING.length()) {
            if(typed.equalsIgnoreCase(NOTHING)) {
                automatic = !automatic;
                typed = typed.substring(0, typed.length() - NOTHING.length());
            }
        }
    }
    
    private void gif(Graphics g) {
        if(a == null || !a.getIsFinished() || isGenocide && count != 19 || !isGenocide && count != 10) {
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
                if(count >= maxCount)
                    count = 0;
                else if(gifCount % gifChange == 0)
                    ++count;
                ++gifCount;
                if(gifCount == gifChange)
                    gifCount = 0;
            }
            else {
                if(count >= maxCount)
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
    
    private void drawHeartBox(Graphics g) {
        int size = 80;
        g.setColor(HEART_BOX_COLOR);
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
        if(isGenocide)
            message = HARD_MESSAGE;
        else if(stage.isMedium())
            message = MEDIUM_MESSAGE;
        else
            message = EASY_MESSAGE;
        String print = "";
        int speechTextX = speechX + 30 - (isGenocide ? 8 : 0);
        if(speechCounter < message[0].length() + 1) {
            print = message[0].substring(0, speechCounter);
            g.drawString(message[0].substring(0, speechCounter), speechTextX, speechY + 20);
        }
        else if(speechCounter < message[1].length() + message[0].length() + 2) {
            print = message[1].substring(0, speechCounter - (message[0].length() + 1));
            g.drawString(message[0], speechTextX, speechY + 20);
            g.drawString(message[1].substring(0, speechCounter - (message[0].length() + 1)), speechTextX, speechY + 40);
        }
        else if(speechCounter < message[2].length() + message[1].length() + message[0].length() + 3) {
            print = message[2].substring(0, speechCounter - (message[0].length() + message[1].length() + 2));
            g.drawString(message[0], speechTextX, speechY + 20);
            g.drawString(message[1], speechTextX, speechY + 40);
            g.drawString(message[2].substring(0, speechCounter - (message[0].length() + message[1].length() + 2)), speechTextX, speechY + 60);
        }
        else {
            g.drawString(message[0], speechTextX, speechY + 20);
            g.drawString(message[1], speechTextX, speechY + 40);
            g.drawString(message[2], speechTextX, speechY + 60);
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
        if(++speechDelayCounter == 6)
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
            creatorButton.setY(600);
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
        main.changeVolume(musicSlider.getPercentage());
        main.play();
    }
    
    static void changeGif() {
        isGenocide = true;
        gif = gifUndying.clone();
        p.setBaseDamage(2);
        p.setDamageOffset(4);
        p.convertHealth();
    }
    
    static void finalBoost() {
        heal.play();
        p.healthBoost();
    }
    
    static void showInitialButtons() {
        playButton.setVisible(true);
        creditsButton.setVisible(true);
        creatorButton.setVisible(true);
        helpButton.setVisible(true);
    }
    
    static boolean isSurvival() {
        return survival;
    }
    
    static boolean getHelpStarter() {
        return helpStarter;
    }
    
    static boolean isOneSecondDelayRunning() {
        return oneSecondDelay.isRunning();
    }
    
    public static JFrame getFrame() {
        return frame;
    }
    
    private static void restartApplication() {
        mainTimer.stop();
        allStopped = true;
        stage.resetVars(isReplaying);
        a.resetVars();
        a1.resetVars();
        if(gameDone != null)
            gameDone.stop();
        dir = 'u';
        typed = "";
        activated = "";
        delay = 10;
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
        loadingCounter = 0;
        loadingFrame = 0;
        customAttacksCounter = 0;
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
        isFirstTime = false;
        speechDone = false;
        helpStarter = false;
        isPlayTimerDone = false;
        isStopTimerDone = false;
        isStartTimerDone = false;
        isOpenCreatorTimerDone = false;
        isCloseCreatorTimerDone = false;
        isCustomAttack = false;
        canBeStopped = false;
        mainTimer = null;
        oneSecondDelay = null;
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
        focused = null;
        checkFocus = null;
        a1 = null;
        a = null;
        frame.dispose();
        frame = null;
        stage = new StartScreen();
        p = new Player();
        loading = null;
        topBar = null;
        allStopped = false;
        System.gc();
        try {
            main();
        }
        catch(IOException | UnsupportedAudioFileException | FontFormatException e) {
            e.printStackTrace();
        }
    }
    
    private static void start() {
        if(!oneSecondDelay.isRunning()) {
            if(!isStartTimerDone) {
                startScreen.stop();
                oneSecondDelay.setActionCommand("start");
                oneSecondDelay.start();
                isStartTimerDone = true;
            }
            else {
                dir = 'u';
                isGenocide = stage.isHard();
                survival = stage.isSurvival();
                a = new Attacks(isGenocide, survival, stage.isMedium());
                a1 = new Attack(new ArrayList<>(), a);
                a.setAttack(a1);
                hideButtons();
                setUpUndyne(isGenocide);
                beginning = false;
                main.changeVolume(musicSlider.getPercentage());
                main.play();
                isStartTimerDone = false;
            }
        }
    }
    
    public static void play(boolean isCalledByTimer) {
        if(!oneSecondDelay.isRunning()) {
            if(!isPlayTimerDone) {
                creatorMusic.stop();
                oneSecondDelay.setActionCommand("play");
                oneSecondDelay.start();
                dir = 'u';
                p.setDirUp();
                p.zeroAngle();
                isPlayTimerDone = true;
            }
            else if(isCalledByTimer) {
                try {
                    a = new Attacks(customAttackMaker.getAttacks());
                }
                catch(Exception e) {
                    creatorMusic.play();
                    isPlayTimerDone = false;
                    return;
                }
                a1 = new Attack(new ArrayList<>(), a);
                a.setAttack(a1);
                delay = 10;
                canBeStopped = true;
                setUpUndyne(bottomBar.isGenocideBoxChecked());
                beginning = false;
                isCustomAttack = false;
                main.changeVolume(musicSlider.getPercentage());
                main.play();
                dir = 'u';
                isPlayTimerDone = false;
            }
        }
    }
    
    public static void stop(boolean isCalledByTimer) {
        if(!oneSecondDelay.isRunning()) {
            if(!isStopTimerDone) {
                main.stop();
                oneSecondDelay.setActionCommand("stop");
                oneSecondDelay.start();
                isStopTimerDone = true;
            }
            else if(canBeStopped && isCalledByTimer) {
                creatorMusic.play();
                a.resetVars();
                a1.resetVars();
                isCustomAttack = true;
                canBeStopped = false;
                p.setHit(false);
                p.resetTimeoutCounter();
                flickeringHeart = 0;
                isStopTimerDone = false;
            }
        }
    }
    
    private static void openCreator() {
        if(!oneSecondDelay.isRunning()) {
            if(!isOpenCreatorTimerDone) {
                startScreen.stop();
                oneSecondDelay.setActionCommand("openCreator");
                oneSecondDelay.start();
                isOpenCreatorTimerDone = true;
            }
            else {
                isCustomAttack = !isCustomAttack;
                beginning = !beginning;
                moveButtons(!beginning);
                if(delay == 10)
                    delay = 0;
                else
                    delay = 10;
                creatorMusic.play();
                isOpenCreatorTimerDone = false;
            }
        }
    }
    
    private static void closeCreator() {
        if(!oneSecondDelay.isRunning()) {
            if(!isCloseCreatorTimerDone) {
                creatorMusic.stop();
                oneSecondDelay.setActionCommand("closeCreator");
                oneSecondDelay.start();
                isCloseCreatorTimerDone = true;
            }
            else {
                delay = 10;
                isCustomAttack = false;
                beginning = true;
                isCloseCreatorTimerDone = false;
            }
        }
    }
    
    private static void setUpUndyne(boolean isGenocide) {
        Runner.isGenocide = isGenocide;
        if(isGenocide) {
            p.setHealth(60);
            p.setBaseDamage(3);
            p.setDamageOffset(12);
            main = mainSounds[2];
            speechX = 310;
            speechY = 60;
            gif = gifUndying.clone();
        }
        else {
            p.setHealth(20);
            p.setBaseDamage(0);
            p.setDamageOffset(2);
            if(stage.isMedium() || stage.isSurvival() || canBeStopped)
                main = mainSounds[0];
            else
                main = sojBeta;
            speechX = 305;
            speechY = 50;
            gif = gifUndyne.clone();
        }
    }
    
    public static void addComponent(Component component, int index) {
        frame.add(component, index);
    }
    
    public static void removeComponent(Component component) {
        frame.remove(component);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == null)
            repaint();
        else {
            switch(e.getActionCommand()) {
                case "main":
                    frame.repaint();
                    break;
                case "start":
                    start();
                    break;
                case "openCreator":
                    openCreator();
                    break;
                case "closeCreator":
                    closeCreator();
                    break;
                case "play":
                    play(true);
                    break;
                case "stop":
                    stop(true);
                    break;
            }
        }
    }
    
    private static void setUpKeyBindings(Runner runner) {
        int condition = WHEN_IN_FOCUSED_WINDOW;
        
        new KeyAction(runner, condition, KeyEvent.VK_UP, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                upPressed();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_W, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                upPressed();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_DOWN, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                downPressed();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_S, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                downPressed();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_RIGHT, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPressed();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_D, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPressed();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_LEFT, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftPressed();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_A, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftPressed();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_X, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(helpStarter) {
                    StartScreen.playClick();
                    helpStarter = false;
                }
                else if(creditsList.getExpanding()) {
                    StartScreen.playClick();
                    creditsList.setExpanding(false);
                }
                else if(stage.isPlayChosen() && beginning) {
                    StartScreen.playClick();
                    stage.deactivateSpears();
                    stage.playChosen(false);
                    moveButtons(false);
                    stage.setHeartX(5);
                    stage.setHeartY(72);
                }
                else if(isCustomAttack && !oneSecondDelay.isRunning() && !CustomAttacks.areAnyDirectionsSelected()) {
                    StartScreen.playClick();
                    closeCreator();
                }
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_Z, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(beginning) {
                    if(!stage.isPlayChosen()) {
                        if(creditsButton.onButton()) {
                            StartScreen.playClick();
                            creditsList.setExpanding(true);
                            creditsList.setVisible(true);
                        }
                        else if(helpButton.onButton()) {
                            StartScreen.playClick();
                            helpStarter = true;
                        }
                        else if(playButton.onButton()) {
                            StartScreen.playClick();
                            stage.playChosen(true);
                            moveButtons(true);
                            stage.setHeartX(5);
                            stage.setHeartY(75);
                        }
                        else if(creatorButton.onButton() && !oneSecondDelay.isRunning()) {
                            StartScreen.playClick();
                            openCreator();
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
                        StartScreen.playClick();
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
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_V, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mainTimer.getDelay() != 0)
                    mainTimer.setDelay(0);
                else
                    mainTimer.setDelay(10);
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_R, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCustomDirection('n');
                if(isGameOver && !allStopped || speechDone && !allStopped) {
                    isReplaying = true;
                    restartApplication();
                    start();
                }
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_ENTER, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(AttackBar attBar : CustomAttacks.attacks) {
                    for(ArrowBar arrBar : attBar.getArrows()) {
                        if(arrBar.isDirectionSelected())
                            arrBar.directionSelectedFalse();
                    }
                }
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_UP, 0, true) {
            @Override
            public void actionPerformed(ActionEvent e) {
                upReleased();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_W, 0, true) {
            @Override
            public void actionPerformed(ActionEvent e) {
                upReleased();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_DOWN, 0, true) {
            @Override
            public void actionPerformed(ActionEvent e) {
                downReleased();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_S, 0, true) {
            @Override
            public void actionPerformed(ActionEvent e) {
                downReleased();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_RIGHT, 0, true) {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightReleased();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_D, 0, true) {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightReleased();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_LEFT, 0, true) {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftReleased();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_A, 0, true) {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftReleased();
            }
        };
    }
    
    private static void upPressed() {
        setCustomDirection('u');
        dir = 'u';
        stage.setUp();
    }
    
    private static void downPressed() {
        setCustomDirection('d');
        dir = 'd';
        stage.setDown();
    }
    
    private static void rightPressed() {
        setCustomDirection('r');
        dir = 'r';
        stage.setRight();
    }
    
    private static void leftPressed() {
        setCustomDirection('l');
        dir = 'l';
        stage.setLeft();
    }
    
    private static void upReleased() {
        stage.setUpf();
    }
    
    private static void downReleased() {
        stage.setDownf();
    }
    
    private static void rightReleased() {
        stage.setRightf();
    }
    
    private static void leftReleased() {
        stage.setLeftf();
    }
    
    private static void setCustomDirection(char dir) {
        for(AttackBar attBar : CustomAttacks.attacks) {
            for(ArrowBar arrBar : attBar.getArrows()) {
                if(arrBar.isDirectionSelected())
                    arrBar.setDirection(dir);
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() != KeyEvent.CHAR_UNDEFINED)
            typed += e.getKeyChar();
        nothing();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    private static boolean onSlider(String slider) {
        Slider s;
        GradientButtonTolerance gb;
        if(slider.equals("music")) {
            s = musicSlider;
            gb = musicButton;
        }
        else {
            s = sfxSlider;
            gb = sfxButton;
        }
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        Point sliderPoint = s.getLocationOnScreen();
        mousePos.translate((int) Math.round(-sliderPoint.getX()), (int) Math.round(-sliderPoint.getY()));
        Rectangle bounds = s.getBounds();
        bounds.setBounds(gb.getX() - s.getX(), -6, gb.getWidth(), s.getHeight() + 6);
        return bounds.contains(mousePos);
    }
    
    private static void barCheckBoxClicked(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        boolean mouseIntersectsCheckBox = bottomBar.getBarCheckBox().contains(x, y);
        if(mouseIntersectsCheckBox) {
            StartScreen.playClick();
            if(canBeStopped)
                bottomBar.flipIsRobotBoxedChecked();
            else if(isCustomAttack)
                bottomBar.flipIsGenocideBoxChecked();
        }
    }
    
    public static void setTopBarVisibility(boolean visibility) {
        topBar.setVisible(visibility);
    }
    
    static boolean onFrontButton() {
        return playButton.onButton() || creatorButton.onButton() || creditsButton.onButton() || helpButton.onButton();
    }
    
    public static boolean bottomBarShouldDraw() {
        return !allStopped && !oneSecondDelay.isRunning() && CustomAttacks.isIn() && (isCustomAttack || canBeStopped);
    }
    
    public static boolean windowNotFocused() {
        return checkFocus.windowNotFocused();
    }
    
    static int getTimerDelay() {
        return mainTimer.getDelay();
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        customAttackMaker.mouseWheelMoved(e);
    }
    
}
