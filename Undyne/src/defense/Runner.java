package defense;

import customAttackMaker.ArrowBar;
import customAttackMaker.AttackBar;
import customAttackMaker.BottomMenuBar;
import customAttackMaker.CustomAttacks;
import nikunj.classes.GradientButton;
import nikunj.classes.KeyAction;
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

/**
 * The main class responsible for running the application
 */
public class Runner extends JPanel implements ActionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    
    /**
     * The last index used in ROBOT_STRING.substring(0, nothingEndIndex) to draw "Cheat Activated"
     */
    private static int nothingEndIndex = 0;
    
    /**
     * The delay per tick of the {@code javax.swing.Timer} used for drawing
     */
    private static int delay = 10;
    
    /**
     * Counter used in changing the breakFrame
     */
    private static int breakCount = 0;
    
    /**
     * The 'frame' index of the {@code BufferedImage} array that represents the heart breaking
     */
    private static int breakFrame = 0;
    
    /**
     * The shift in the y-value to hide the heart in flickering
     */
    private static int flickeringHeart = 0;
    
    /**
     * The 'frame' index of the {@code BufferedImage} array that represents the Undyne gif
     */
    private static int gifFrame = 0;
    
    /**
     * The counter used in changing the gif frame
     */
    private static int gifCount = 0;
    
    /**
     * The counter used in changing the game over frame
     */
    private static int gameOverCount = 0;
    
    /**
     * The 'frame' index of the {@code BufferedImage} array that represents the game over gif
     */
    private static int gameOverFrame = 0;
    
    /**
     * Used to increase number of characters displayed of cheat text
     */
    private static int nothingCounter = 0;
    
    /**
     * Used to change whether the heart is visible or not when heart is flickering
     */
    private static int flickerCounter = 0;
    
    /**
     * Used to switch between whether the replay text is fading out or in
     */
    private static int flashCount = 0;
    
    /**
     * Used to turn the JFrame's always on top value to false
     */
    private static int alwaysOnTopCounter = 0;
    
    /**
     * Used in increasing the number of characters displayed during Undyne's ending speech
     */
    private static int speechCounter = 0;
    
    /**
     * The previous value of the speechCounter used to check if speechCounter has changed
     */
    private static int speechCounterPrev = 0;
    
    /**
     * Used in increasing speechCounter every certain number of ticks as long as the message isn't finished
     */
    private static int speechDelayCounter = 0;
    
    /**
     * The x and y positions to draw the speech text for Undyne
     */
    private static int speechX, speechY;
    
    /**
     * The index in the mainSounds array for playing the background audio when playing the game
     */
    private static int mainIndex = 0;
    
    /**
     * The number that represents the frame of the level pictures used in survival
     */
    private static int levelIndex = 0;
    
    /**
     * The score of the player in survival
     */
    private static int score = 0;
    
    /**
     * Used in checking if the current attack has changed and changing the score accordingly for survival
     */
    private static int lastAttack = 1;
    
    /**
     * Counter used to advance loadingFrame
     */
    private static int loadingCounter = 0;
    
    /**
     * The current frame of the loading animation
     */
    private static int loadingFrame = 0;
    
    /**
     * Used for drawing the creator arrows, particularly the flashing effect
     */
    public static int creatorArrowDirectionCounter = 0;
    
    /**
     * Used as the opacity of the replay text after losing or winning a game mode
     */
    private static double fadeStart = 0;
    
    /**
     * Used to store the music volume before it was muted
     */
    private static double musicMutedVolume = 1;
    
    /**
     * Used to store the sfx volume before it was muted
     */
    private static double sfxMutedVolume = 1;
    
    private static final int[] HEART_BREAK_EXCEPTIONS = {2, 6, 8, 12, 14, 18, 20, 22, 23};
    
    /**
     * True if the Undyne mode should be Undyne the Undying (Undyne sprite and Undyne speaking sounds)
     */
    private static boolean genocide = false;
    
    /**
     * True if the game mode chosen is survival
     */
    private static boolean survival = false;
    
    /**
     * True if the heart breaking animation is done
     */
    private static boolean heartDone = false;
    
    /**
     * True if the game over animation is done
     */
    private static boolean gameOverDone = false;
    
    /**
     * True when the heart-breaking portion of the game over has started
     */
    private static boolean heartBreakStarted = true;
    
    /**
     * True when the game over screen shows and the music plays
     */
    private static boolean gameOverScreenStarted = true;
    
    /**
     * True if on the menu screen
     */
    private static boolean beginning = true;
    
    /**
     * True if the cheat has been activated
     */
    private static boolean automatic = false;
    
    /**
     * True if the game over screen is playing and it is at the point where it is asking the player to replay
     */
    private static boolean isGameOver = false;
    
    /**
     * This is used for switching between the replay text fading in and out. It is true if the replay text is fading out
     * and false otherwise.
     */
    private static boolean switchFade = false;
    
    /**
     * True when the application is in the process of resetting and restarting (nothing should be going on)
     */
    private static boolean allStopped = false;
    
    /**
     * True if the music has been muted via the music button
     */
    private static boolean musicMuted = false;
    
    /**
     * True if the sfx had been muted via the sfx button
     */
    private static boolean sfxMuted = false;
    
    /**
     * True if Undyne is done speaking her text at the end of a game
     */
    private static boolean speechDone = false;
    
    /**
     * True if the health pop-up should be showing
     */
    private static boolean helpStarter = false;
    
    /**
     * True if the application is being replayed (current game mode is being replayed)
     */
    private static boolean isReplaying = false;
    
    /**
     * True if the play timer ticks (one second delay timer)
     */
    private static boolean isPlayTimerDone = false;
    
    /**
     * True if the stop timer ticks (one second delay timer)
     */
    private static boolean isStopTimerDone = false;
    
    /**
     * True if the start timer ticks (one second delay timer)
     */
    private static boolean isStartTimerDone = false;
    
    /**
     * True if the open creator timer ticks (one second delay timer)
     */
    private static boolean isOpenCreatorTimerDone = false;
    
    /**
     * True if the close creator timer ticks (one second delay timer)
     */
    private static boolean isCloseCreatorTimerDone = false;
    
    /**
     * True if the application has not been used more than once (by replaying a game mode or choosing another game
     * mode)
     */
    static boolean isFirstTime = true;
    
    /**
     * True if the application is in the custom attack maker
     */
    public static boolean isCustomAttack = false;
    
    /**
     * True if the application is playing attacks from the custom attack maker
     */
    public static boolean canBeStopped = false;
    
    /**
     * The direction the player inputted to block the arrows
     */
    private static char dir = 'u';
    
    /**
     * String matched with input to determine whether the cheat has been activated or deactivated
     */
    private static final String ROBOT_STRING = "bad time";
    
    /**
     * The current text input of the user with a max length of the ROBOT_STRING (old input chars are chopped off)
     */
    private static String typed = "";
    
    /**
     * The string that is shown on screen to show the status of whether or not the cheat is activated
     */
    private static String activated = "";
    
    /**
     * The array of strings that represent what Undyne says at the end of the easy mode (each element being a line)
     */
    private static final String[] EASY_MESSAGE = {"You did well...      ", "only because", "I went easy."};
    
    /**
     * The array of strings that represent what Undyne says at the end of the medium mode (each element being a line)
     */
    private static final String[] MEDIUM_MESSAGE = {"Not bad, punk!", "Let me go", "harder on you."};
    
    /**
     * The array of strings that represent what Undyne says at the end of the hard mode (each element being a line)
     */
    private static final String[] HARD_MESSAGE = {"You really are", "something, human.", "Nice job!"};
    
    /**
     * The audio file names used in progression for survival mode
     */
    private static final String[] MAIN_SOUND_NAMES = {"/soj.ogg", "/survivalSoj.ogg", "/bath.ogg", "/survivalBath.ogg"};
    
    /**
     * The main timer used for redrawing the JFrame
     */
    private static Timer timer;
    
    /**
     * The timer used for any delays that require one second (has its own loading screen)
     */
    private static Timer oneSecondDelay;
    
    /**
     * The image used to draw the green player heart in game
     */
    private static BufferedImage heart;
    
    /**
     * The image that contains the replay text shown after a game mode results in a death or completion
     */
    private static BufferedImage replay;
    
    /**
     * The image for the X button that can be used to close the application in the upper left
     */
    private static BufferedImage close;
    
    /**
     * The image for the button with a cross of arrows that can be used to drag the application around the screen
     */
    private static BufferedImage draggable;
    
    /**
     * The image for the button that is used to show the music slider or can be clicked to mute/unmute the music
     */
    private static BufferedImage music;
    
    /**
     * The image for the button that is used to show the sfx slider or can be clicked to mute/unmute the sfx
     */
    private static BufferedImage sfx;
    
    /**
     * The image used as a speech bubble for when Undyne is talking
     */
    private static BufferedImage speech;
    
    /**
     * The image used for the credits button
     */
    private static BufferedImage credits;
    
    /**
     * The image used for the help button
     */
    private static BufferedImage help;
    
    /**
     * The image used for the play button
     */
    private static BufferedImage play;
    
    /**
     * The image used for the attack creator button
     */
    private static BufferedImage creator;
    
    /**
     * The image used as a glow effect around the currently-selected number field in custom attacks
     */
    private static BufferedImage numberFieldGlow;
    
    /**
     * The image used for any arrow that isn't the first arrow in the attacks array if it is not a reverse arrow
     */
    static BufferedImage blueArr;
    
    /**
     * The image used for the first arrow in the attacks array if it is not a reverse arrow
     */
    static BufferedImage redArr;
    
    /**
     * The image used for the reverse arrow
     */
    static BufferedImage reverseArr;
    
    /**
     * The image used for the area that can be used to drag the arrow bars
     */
    public static BufferedImage dragArrowIcon;
    
    /**
     * The image used for the generic bottom menu bar
     */
    public static BufferedImage bottomMenuBar;
    
    /**
     * The custom attack creator title used in the custom attack creator's start screen
     */
    public static BufferedImage cat;
    
    /**
     * The image used for the import button on the custom attack start screen
     */
    public static BufferedImage importImage;
    
    /**
     * The image used for the new button on the custom attack start screen
     */
    public static BufferedImage newImage;
    
    /**
     * The image used for adding another attack in the attack creator
     */
    public static BufferedImage addAttack;
    
    /**
     * A grayed-out version of the addAttack button that is used when the max number of attack bars have been made
     */
    public static BufferedImage addAttackDisabled;
    
    /**
     * The checkbox checkmark used in the reversible checkboxes in the {@code ArrowBar}s and the checkbox on the bottom bar
     */
    public static BufferedImage ticked;
    
    /**
     * The direction arrow drawn for each arrow bar
     */
    public static BufferedImage customArrowDirection;
    
    /**
     * The image used for deleting an arrow bar
     */
    public static BufferedImage deleteArrow;
    
    /**
     * The image used for deleting an attack bar
     */
    public static BufferedImage deleteAttack;
    
    /**
     * The image used when an attack bar is expanded, showing its arrows
     */
    public static BufferedImage droppedDown;
    
    /**
     * The image used when an attack bar is contracted, hiding its arrows
     */
    public static BufferedImage droppedClosed;
    
    /**
     * The image used to add another arrow to an attack bar
     */
    public static BufferedImage newArrow;
    
    /**
     * The image used for the generic arrow bar
     */
    public static BufferedImage arrowBarImage;
    
    /**
     * The image used for checkboxes
     */
    public static BufferedImage checkBox;
    
    /**
     * The image used for the play button on the bottom bar in custom attacks
     */
    public static BufferedImage bottomPlayButton;
    
    /**
     * The image used for the disabled play button on the bottom bar in custom attacks
     */
    public static BufferedImage bottomPlayButtonDisabled;
    
    /**
     * The image used for the stop button on the bottom bar in custom attacks
     */
    public static BufferedImage bottomStopButton;
    
    /**
     * The image used for the disabled stop button on the bottom bar in custom attacks
     */
    public static BufferedImage bottomStopButtonDisabled;
    
    /**
     * The image used for the export button on the bottom bar in custom attacks
     */
    public static BufferedImage exportButton;
    
    /**
     * The image used for the disabled export button on the bottom bar in custom attacks
     */
    public static BufferedImage exportButtonDisabled;
    
    /**
     * An image of the tab on the bottom bar that indicates that the bottom bar is in the process of being hidden or is
     * hidden
     */
    public static BufferedImage bottomTabUp;
    
    /**
     * An image of the tab on the bottom bar that indicates that the bottom bar is in the process of being shown or is
     * shown
     */
    public static BufferedImage bottomTabDown;
    
    /**
     * The image of the orientation shift button for an attack bar
     */
    public static BufferedImage orientationShiftButton;
    
    /**
     * The images used for the heart breaking upon health hitting zero
     */
    private static BufferedImage[] heartBreak;
    
    /**
     * The images used for the game over scene with asgore's lines
     */
    private static BufferedImage[] gameOver;
    
    /**
     * The images used for showing the level the player is at in survival
     */
    private static BufferedImage[] levels = new BufferedImage[4];
    
    /**
     * The images used for the loading screen during the one second delay timer
     */
    private static BufferedImage[] loadingScreen = new BufferedImage[48];
    
    /**
     * The set of images currently being used for the Undyne animation
     */
    private static BufferedImage[] gifUndyne;
    
    /**
     * The set of images used for the normal Undyne animation
     */
    private static BufferedImage[] gifUndyneNormal;
    
    /**
     * The set of images used for the Undyne the Undying animation
     */
    private static BufferedImage[] gifUndyneUndying;
    
    /**
     * The image used in option dialogs on occasion
     */
    public static ImageIcon warning;
    
    /**
     * The audio being played during gameplay
     */
    private static Sound main;
    
    /**
     * The audio for the Spear of Justice Beta theme (used in easy mode)
     */
    private static Sound sojBeta;
    
    /**
     * The audio used during a game over
     */
    private static Sound gameDone;
    
    /**
     * The audio used at the start screen at the start of the application
     */
    private static Sound startScreen;
    
    /**
     * The audio used when the attack creator is open
     */
    private static Sound creatorMusic;
    
    /**
     * The audio used when normal Undyne is speaking
     */
    private static Sound undyne;
    
    /**
     * The audio used when Undyne the Undying is speaking
     */
    private static Sound undying;
    
    /**
     * The audio used when the player's HP is increased
     */
    private static Sound heal;
    
    /**
     * The audio used when the player blocks an arrow with the shield
     */
    private static Sound block;
    
    /**
     * The audio used when the heart splits into two pieces upon health hitting zero
     */
    private static Sound split;
    
    /**
     * The audio used when the split heart breaks into pieces
     */
    private static Sound broke;
    
    /**
     * The audio used when Asgore is speaking in the game over screen
     */
    private static Sound asgore;
    
    /**
     * The audio played when the player clicks the start screen instead of using the arrow keys and other buttons as the
     * player is supposed to
     */
    private static Sound error;
    
    /**
     * The audio used during each of the survival phases
     */
    private static Sound[] mainSounds = new Sound[4];
    
    /**
     * The button used to close the application in the upper left
     */
    private static GradientButtonTolerance closeButton;
    
    /**
     * The button with a cross of arrows that can be used to drag the application around the screen
     */
    private static GradientButtonTolerance draggableButton;
    
    /**
     * The button that is used to show the music slider or can be clicked to mute/unmute the music
     */
    private static GradientButtonTolerance musicButton;
    
    /**
     * The button that is used to show the sfx slider or can be clicked to mute/unmute the sfx
     */
    private static GradientButtonTolerance sfxButton;
    
    /**
     * The credits button on the start screen
     */
    private static GradientButton creditsButton;
    
    /**
     * The help button on the start screen
     */
    private static GradientButton helpButton;
    
    /**
     * The play button on the start screen
     */
    private static GradientButton playButton;
    
    /**
     * The attack creator button on the start screen
     */
    private static GradientButton creatorButton;
    
    /**
     * The slider used for adjusting the volume of the music
     */
    private static Slider musicSlider;
    
    /**
     * The slider used for adjusting the volume of the sfx
     */
    private static Slider sfxSlider;
    
    /**
     * The pop-up used for showing the credits
     */
    private static PopUp creditsList;
    
    /**
     * The NumberField that is currently in focus
     */
    private static ArrowBar.NumberFieldFocus focused;
    
    /**
     * The determination font at 12 point size
     */
    private static Font deteFontNorm;
    
    /**
     * The determination font at 22 point size
     */
    static Font deteFontScore;
    
    /**
     * The determination font at 14 point size
     */
    public static Font deteFontSpeech;
    
    /**
     * The determination font at 10 point size
     */
    public static Font deteFontEditor;
    
    /**
     * The determination font at 24 point size
     */
    public static Font deteFontEditorAttack;
    
    /**
     * The determination font at 20 point size
     */
    public static Font deteFontError;
    
    /**
     * The focus listener used to determine whether the Undyne application window is in focus
     */
    private static UndyneWindowFocusListener checkFocus;
    
    /**
     * Performs operations for running the arrows and attack for the game
     */
    private static Attack a1;
    
    /**
     * Runs the arrow initialization, management, and giving giving for the application
     */
    private static Attacks a;
    
    /**
     * Runs what happens on the application start screen
     */
    private static StartScreen stage;
    
    /**
     * Runs the attack creator related tasks
     */
    private static CustomAttacks customAttackMaker = new CustomAttacks();
    
    /**
     * Represents the player with the shield, heart, and box
     */
    private static Player p = new Player();
    
    /**
     * Runs the help pop-up
     */
    private static Help helper = new Help();
    
    /**
     * Used for running the splash screen on the start up of the application
     */
    private static SplashScreen loading;
    
    /**
     * Used for the bottom bar tasks in the attack creator
     */
    private static BottomMenuBar bottomBar = CustomAttacks.getBottomMenuBar();
    
    /**
     * The color drawn on top of the application when it is unfocused
     */
    public static final Color UNFOCUSED_COLOR = new Color(255, 255, 255, 127);
    
    /**
     * The color of the box that contains the heart in the game
     */
    private static final Color HEART_BOX_COLOR = new Color(255, 255, 255, 200);
    
    /**
     * The width of the line that is used to draw a slash through the music and sfx buttons when either are muted
     */
    private static final BasicStroke AUDIO_SLASH_STROKE = new BasicStroke(2);
    
    /**
     * The line that is used to draw a slash through the music and sfx buttons when either are muted
     */
    private static final Line2D.Float AUDIO_SLASH_LINE = new Line2D.Float(4, 20, 20, 2);
    
    /**
     * The transformation used to draw the loading screen for the one second delay
     */
    private static AffineTransform loadingTransform = new AffineTransform();
    
    /**
     * The JPanel that is used for the top bar of the attack creator
     */
    private static JPanel topBar;
    
    /**
     * The main JFrame that houses all of the JComponents for the application
     */
    private static JFrame frame;
    
    /**
     * The main method for initializing and setting up the application
     *
     * @param args The arguments that can be passed by the command line for the application (not used)
     * @throws IOException                   Thrown if audio or font file cannot be found
     * @throws UnsupportedAudioFileException Thrown if the file imported for audio is not a supported audio file
     * @throws FontFormatException           Thrown if the given font is not acceptable
     */
    public static void main(String... args) throws IOException, UnsupportedAudioFileException, FontFormatException {
        //Runs if the application is running for the first time (not being replayed/choosing different game mode)
        if(isFirstTime) {
            
            //Used for running splash screen while the application is loading
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
            
            //Checks user's Java version, and if it is not high enough, prompts the user to update the Java version.
            if("1.8.0_191".compareTo(System.getProperty("java.version")) > 0) {
                int updateJava = JOptionPane.showConfirmDialog(null, "You need to update your Java version to play. Go to download site?", "Java Outdated", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, Runner.warning);
                if(updateJava == JOptionPane.YES_OPTION) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://java.com/en/download/"));
                    }
                    catch(URISyntaxException | IOException e) {
                        e.printStackTrace();
                    }
                }
                System.exit(0);
            }
            
            //Sets the Arrow class' player object to the Runner player object
            Arrow.p = p;
            
            //Initializes audio
            sojBeta = new Sound(Runner.class.getResource("/sojBeta.ogg"), true);
            startScreen = new Sound(Runner.class.getResource("/WF.ogg"), true);
            creatorMusic = new Sound(Runner.class.getResource("/DS.ogg"), true);
            undyne = new Sound(Runner.class.getResource("/undyne.ogg"), false);
            undying = new Sound(Runner.class.getResource("/undying.ogg"), false);
            heal = new Sound(Runner.class.getResource("/heal.ogg"), false);
            block = new Sound(Runner.class.getResource("/block.ogg"), false);
            gameDone = new Sound(Runner.class.getResource("/dt.ogg"), true);
            split = new Sound(Runner.class.getResource("/split.ogg"), false);
            broke = new Sound(Runner.class.getResource("/heartBreak.ogg"), false);
            asgore = new Sound(Runner.class.getResource("/asgore.ogg"), false);
            error = new Sound(Runner.class.getResource("/error.ogg"), false);
            for(int i = 0; i < MAIN_SOUND_NAMES.length; ++i)
                mainSounds[i] = new Sound(Runner.class.getResource(MAIN_SOUND_NAMES[i]), true);
            
            //Initializes images
            heart = getCompatibleImage("/heart.png");
            heartBreak = new BufferedImage[49];
            for(int i = 0; i <= 48; ++i)
                heartBreak[i] = getCompatibleImage("/gif/heartBreak" + i + ".png");
            gameOver = new BufferedImage[226];
            for(int i = 0; i <= 225; ++i)
                gameOver[i] = getCompatibleImage("/gif/gameOver" + i + ".png");
            gifUndyneUndying = new BufferedImage[80];
            for(int i = 0; i <= 79; ++i)
                gifUndyneUndying[i] = getCompatibleImage("/gif/undying" + i + ".png");
            levels[0] = getCompatibleImage("/levelOne.png");
            levels[1] = getCompatibleImage("/levelTwo.png");
            levels[2] = getCompatibleImage("/levelThree.png");
            levels[3] = getCompatibleImage("/levelFour.png");
            blueArr = getCompatibleImage("/arrowB.png");
            dragArrowIcon = getCompatibleImage("/dragArrowIcon.png");
            arrowBarImage = getCompatibleImage("/arrowBar.png");
            cat = getCompatibleImage("/cat.png");
            importImage = getCompatibleImage("/import.png");
            newImage = getCompatibleImage("/new.png");
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
                loadingScreen[i] = getCompatibleImage("/loading/loading" + i + ".png");
            
            //Initializes fonts
            URL fontUrl = Runner.class.getResource("/dete.otf");
            deteFontNorm = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(12.0f);
            deteFontSpeech = deteFontNorm.deriveFont(14.0f);
            deteFontScore = deteFontNorm.deriveFont(22.0f);
            deteFontEditor = deteFontNorm.deriveFont(10.0f);
            deteFontEditorAttack = deteFontNorm.deriveFont(24.0f);
            deteFontError = deteFontNorm.deriveFont(20.0f);
        }
        
        //Initializes gifUndyneNormal if necessary
        if(gifUndyneNormal == null || gifUndyneNormal.length != 32) {
            gifUndyneNormal = new BufferedImage[32];
            for(int i = 0; i <= 31; ++i)
                gifUndyneNormal[i] = getCompatibleImage("/gif/frame" + i + ".png");
        }
        
        //Initializes the JFrame (main window container)
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        
        //Initializes the Runner JPanel
        Runner runner = new Runner();
        runner.setBounds(0, 0, 600, 600);
        
        //Initializes the timers
        timer = new Timer(delay, runner);
        timer.setActionCommand("main");
        timer.start();
        oneSecondDelay = new Timer(1000, runner);
        oneSecondDelay.setRepeats(false);
        
        //Initializes the start screen of the application
        stage = new StartScreen();
        
        //Initializes the application close button
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
        
        //Initializes the application draggable button
        draggableButton = new GradientButtonTolerance(draggable, Color.BLACK, Color.BLUE, 30, 2, 24, 24) {
            private static final long serialVersionUID = 1L;
            
            private Point originalLocation;
            private Point pressLocation;
            
            @Override
            public void onMouseClick(MouseEvent e) {}
            
            @Override
            public void onMousePress(MouseEvent e) {
                //Gets the location of the JFrame window before any movement (on pressing the draggable button) and the location pressed
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
                /*
                 * Gets the location of the drag event on screen and then gets the difference in x and y between it and the press location
                 * The difference between the drag and press x and y are then added to the original location of the JFrame, and the JFrame is moved.
                 */
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
        
        //Initializes the application music button
        musicButton = new GradientButtonTolerance(music, Color.BLACK, new Color(0, 208, 208), 545, 2, 24, 24) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void onMouseClick(MouseEvent e) {
                //If the music is muted, then a mouse click results in all of the music being set to the volume before the music was muted and the music being unmuted
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
                //Otherwise, (if the music is not muted), the volume is stored, all of the music volume is set to zero, and the music is muted
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
            
            //Initializes the application sfx button
            @Override
            public void afterDraw(Graphics g) {
                if(musicMuted) {
                    //If the music is muted and the slider is at a nonzero value, that means the music has been changed and is no longer muted
                    if(musicSlider.getPercentage() > 0)
                        musicMuted = false;
                        //Otherwise, the music slider is drawn as normal with the mute slash on the music button
                    else {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setColor(Color.WHITE);
                        g2d.setStroke(AUDIO_SLASH_STROKE);
                        g2d.draw(AUDIO_SLASH_LINE);
                    }
                }
                //If the music slider is put to zero, that means the user manually muted the audio
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
                //If the sfx is muted, then a mouse click results in all of the sfx being set to the volume before the sfx was muted and the sfx being unmuted
                if(sfxMuted) {
                    sfxSlider.setPercentage(sfxMutedVolume);
                    if(a1 != null)
                        a1.changeVol(sfxMutedVolume);
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
                //Otherwise, (if the sfx is not muted), the volume is stored, all of the sfx volume is set to zero, and the sfx is muted
                else {
                    sfxMutedVolume = sfxSlider.getPercentage();
                    sfxSlider.setPercentage(0);
                    if(a1 != null)
                        a1.changeVol(0);
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
                    //If the sfx is muted and the slider is at a nonzero value, that means the sfx has been changed and is no longer muted
                    if(sfxSlider.getPercentage() > 0)
                        sfxMuted = false;
                    else {
                        //Otherwise, the sfx slider is drawn as normal with the mute slash on the sfx button
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setColor(Color.WHITE);
                        g2d.setStroke(AUDIO_SLASH_STROKE);
                        g2d.draw(AUDIO_SLASH_LINE);
                    }
                }
                //If the sfx slider is put to zero, that means the user manually muted the audio
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
        
        //Initializes the start screen credits button
        creditsButton = new GradientButton(credits, Color.BLACK, new Color(148, 0, 211), 76, 380 + 20, 148, 62) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean onButton() {
                return isVisible() && stage.isOnLink();
            }
        };
        
        //Initializes the start screen help button
        helpButton = new GradientButton(help, Color.BLACK, new Color(148, 0, 211), 376, 380 + 20, 148, 62) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean onButton() {
                return isVisible() && stage.isOnHelp();
            }
        };
        
        //Initializes the start screen play button
        playButton = new GradientButton(play, Color.BLACK, new Color(148, 0, 211), 76, 300, 148, 62) {
            
            @Override
            public boolean onButton() {
                return isVisible() && stage.isOnPlay();
            }
        };
        
        //Initializes the start screen attack creator button
        creatorButton = new GradientButton(creator, Color.BLACK, new Color(148, 0, 211), 376, 300, 148, 62) {
            
            @Override
            public boolean onButton() {
                return isVisible() && stage.isOnCreator();
            }
        };
        
        //Initializes the window focus listener
        checkFocus = new UndyneWindowFocusListener();
        
        //Initializes the music and sfx sliders if they have not been initialized on the first run
        if(isFirstTime) {
            musicSlider = new Slider(Color.WHITE, new Color(150, 150, 150), new Color(0, 208, 208), true, 553, 30, 10, 50);
            sfxSlider = new Slider(Color.WHITE, new Color(150, 150, 150), Color.GREEN, true, 581, 30, 10, 50);
        }
        
        //Retrieves the credits, help, and error pop-ups from their usual classes (StartScreen, Help, and CustomAttacks)
        creditsList = stage.getCreditsList();
        PopUp helpPopUp = helper.getHelpPopUp();
        PopUp errorPopUp = customAttackMaker.getErrorPopUp();
        
        //Sets up the keybindings (listens for specific key actions)
        setUpKeyBindings(runner);
        
        if(isFirstTime) {
            Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
                
                /**
                 * The location that the user presses down the mouse on the JFrame
                 */
                private Point pressLocation = new Point();
                
                /**
                 * The location that the user releases the mouse from being held down on the
                 * JFrame
                 */
                private Point releaseLocation = new Point();
                
                /**
                 * The time the user started pressing down on the mouse
                 */
                private long pressTime = 0;
                
                @Override
                public void eventDispatched(AWTEvent event) {
                    MouseEvent e = (MouseEvent) event;
                    
                    switch(e.getID()) {
                        case MouseEvent.MOUSE_PRESSED:
                            //If the mouse is pressed, set the initial press location, time, and trigger custom attack's mouse pressed method
                            pressLocation.setLocation(e.getX(), e.getY());
                            pressTime = System.nanoTime();
                            customAttackMaker.mousePressed();
                            break;
                        case MouseEvent.MOUSE_RELEASED:
                            //If the mouse is released, set the initial release location and get the time between release and press in milliseconds
                            releaseLocation.setLocation(e.getX(), e.getY());
                            long time = (System.nanoTime() - pressTime) / 1000000;
                            
                            /*
                             * If the time between press and release is 750 milliseconds or less, and the distance between the press and release locations is less than
                             * five pixels, we will consider that a mouse click. So, we create a mouse click event based on that and do things based on the event.
                             */
                            if(time <= 750 && Math.hypot(releaseLocation.x - pressLocation.x, releaseLocation.y - pressLocation.y) <= 5) {
                                MouseEvent clickEvent = new MouseEvent(frame, MouseEvent.MOUSE_CLICKED, System.nanoTime(), 0, pressLocation.x, pressLocation.y, 1, false);
                                
                                //If the user is on the start screen, an error sfx is played if they click
                                if(beginning && StartScreen.isLoaded && !checkFocus.isJustFocused() && !oneSecondDelay.isRunning() && creditsList.percentageExpanded() != 1.0) {
                                    error.play();
                                    checkFocus.deactivateJustFocused();
                                    stage.warningOn();
                                }
                                else if(checkFocus.isJustFocused())
                                    checkFocus.deactivateJustFocused();
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
        }
        
        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if(event.getID() == MouseEvent.MOUSE_DRAGGED)
                customAttackMaker.mouseDragged();
        }, AWTEvent.MOUSE_MOTION_EVENT_MASK);
        
        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if(event.getID() == MouseEvent.MOUSE_WHEEL)
                customAttackMaker.mouseWheelMoved((MouseWheelEvent) event);
        }, AWTEvent.MOUSE_WHEEL_EVENT_MASK);
        
        //Set bottom bar bounds
        bottomBar.setBounds(0, 548, 600, 52);
        
        //Initialize top bar and set bounds
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
        
        //Sets many JComponents "setOpaque" value to false so underlying pixels can show through
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
        
        //Adds JComponents to JFrame
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
        frame.addKeyListener(runner);
        frame.addWindowListener(checkFocus);
        
        //Sets component visibility to true or false as they should be initially
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
        
        //Sets up JFrame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(600, 600);
        frame.setLocation((dim.width - frame.getWidth()) / 2, (dim.height - frame.getHeight()) / 2);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.getContentPane().setLayout(null);
        frame.requestFocus();
        frame.setVisible(true);
        
        //Initializes the one second delay loading screen location
        loadingTransform.setToIdentity();
        loadingTransform.translate(216.5, 265.5);
        
        //Sets and plays start screen music
        startScreen.changeVolume(musicSlider.getPercentage());
        if(!isReplaying)
            startScreen.play();
        isReplaying = false;
    }
    
    /**
     * WindowListener used to check if the JFrame window is focused and changes a boolean value to add a semitransparent
     * white overlay if it is not
     */
    private static class UndyneWindowFocusListener implements WindowListener {
        
        /**
         * True if the window is focused and false otherwise
         */
        private boolean windowFocused = false;
        
        /**
         * True if the window has been focused for under 500 milliseconds and false otherwise
         */
        private boolean justFocused = false;
        
        /**
         * The thread used to set justFocused to false after 500 milliseconds of the window being focused
         */
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
            //Sets focused variables to true and sets up and runs thread to set justFocused to false after 500 milliseconds
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
            //If the window loses focus, the thread to set justFocused to false after 500 milliseconds is interrupted, and windowFocused is set to false
            justFocusedDisabler.interrupt();
            windowFocused = false;
        }
        
        /**
         * Returns the negation of windowFocused
         *
         * @return The negation of windowFocused
         */
        boolean windowNotFocused() {
            return !windowFocused;
        }
        
        /**
         * Returns justFocused
         *
         * @return justFocused
         */
        boolean isJustFocused() {
            return justFocused;
        }
        
        /**
         * Sets justFocused to false
         */
        void deactivateJustFocused() {
            justFocused = false;
        }
        
    }
    
    /**
     * Returns a BufferedImage of the given path optimized for the computer's local graphics environment configuration
     *
     * @param pathToImage A string representing the path to the image
     * @return Optimized BufferedImage given the path
     */
    static BufferedImage getCompatibleImage(String pathToImage) {
        //Retrieves a BufferedImage from the path
        BufferedImage current = null;
        try {
            current = ImageIO.read(Runner.class.getResource(pathToImage));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        //Gets the graphics configuration from the local graphics environment
        GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        if(current != null) {
            //If the color model of the BufferedImage is already optimized for the current graphics configuration, just return it
            if(current.getColorModel().equals(gfxConfig.getColorModel()))
                return current;
            
            //Otherwise, create a blank BufferedImage and a Graphics object with the color model matching that of the local graphics environment's configuration
            BufferedImage optimized = gfxConfig.createCompatibleImage(current.getWidth(), current.getHeight(), current.getTransparency());
            Graphics2D g2d = optimized.createGraphics();
            
            //Draw the unoptimized image using the above graphics object (hence optimizing the image)
            g2d.drawImage(current, 0, 0, null);
            
            //Sets the optimization priority of the given image to the max priority before returning it
            optimized.setAccelerationPriority(1);
            return optimized;
        }
        return null;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Things are drawn only if allStopped is false
        if(!allStopped) {
            //The JFrame is set to always on top for 20 ticks at the start to ensure it gets on top upon initialization
            if(++alwaysOnTopCounter >= 20) {
                alwaysOnTopCounter = 20;
                frame.setAlwaysOnTop(false);
            }
            
            //Used in the blinking of the creator arrows
            if(++creatorArrowDirectionCounter == 75)
                creatorArrowDirectionCounter = 0;
            
            //Updates all of the music volume to the level of the music slider
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
            
            //Updates all of the sfx volume to the level of the sfx slider
            if(a1 != null)
                a1.changeVol(sfxSlider.getPercentage());
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
            
            //Draws the typical black background
            drawBG(g);
            
            //Draws all of the start screen stuff at the beginning of the application start-up
            if(beginning && !oneSecondDelay.isRunning()) {
                customAttackMaker.setAllFieldsVisibility(false);
                //Drawn if the start screen should be showing (after the delay at the beginning)
                if(stage.shouldShow()) {
                    //Drawn if on the first screen of the start screen (not the game selection mode)
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
                    //If on the game mode choosing part of the start screen, the buttons are moved
                    else
                        moveButtons(true);
                    //If the hearts are activated, the start screen music will stop playing for the easter egg audio
                    if(stage.heartsActivated())
                        startScreen.stop();
                    else if(beginning && startScreen.isStopped() && !oneSecondDelay.isRunning() && !isCustomAttack)
                        //If the start screen should be showing, the start screen music is played if it isn't playing
                        startScreen.play();
                }
                //The start screen draw method is called
                stage.run(g);
                //This draws the cheat activated text
                drawCheat(g);
            }
            //Runs the custom attack editor's draw methods
            else if(isCustomAttack && !oneSecondDelay.isRunning())
                customAttackMaker.perform(g);
            else {
                //Sets the top bar and numberfield visibilities to false
                topBar.setVisible(false);
                customAttackMaker.setAllFieldsVisibility(false);
                
                //If the one second delay is not running
                if(!oneSecondDelay.isRunning()) {
                    //If the player is damaged, this controls the countdown and flickering for the heart
                    if(p.getDamaged()) {
                        p.decreaseCounter();
                        if(++flickerCounter % 16 == 0) {
                            if(flickeringHeart == 0)
                                flickeringHeart = 9000;
                            else
                                flickeringHeart = 0;
                            flickerCounter = 0;
                        }
                        if(p.getTimeoutCounter() == 0) {
                            p.setDamaged(false);
                            p.resetTimeoutCounter();
                            flickeringHeart = 0;
                        }
                    }
                    //If the player isn't dead, the game graphics are drawn, and the robot is controlled
                    if(p.getHealth() != 0) {
                        drawCheat(g);
                        drawHeartBox(g);
                        drawCircle(g);
                        drawHeart(g);
                        p.shield(g, dir);
                        drawUndyneGif(g);
                        if(survival)
                            g.drawImage(levels[levelIndex], 433, 4, null);
                        if(a1 != null)
                            a1.spawnArrows(g, p);
                        p.drawHealth(g);
                        automatic();
                    }
                    else {
                        //If the player dies when running attacks from the attack creator, they are sent back to the attack creator
                        if(canBeStopped) {
                            if(!oneSecondDelay.isRunning())
                                stop(true);
                        }
                        
                        //Stops the main music for the heart break beginning
                        else if(heartBreakStarted) {
                            main.stop();
                            heartBreakStarted = false;
                        }
                        
                        //Runs the heart breaking animation until the shards are all gone
                        else if(!heartDone)
                            breakHeart(g);
                            
                            //Starts the game over music when the game over screen should start
                        else if(gameOverScreenStarted) {
                            gameOverScreenStarted = false;
                            gameDone.play();
                        }
                        
                        //Runs the game over screen until the last frame
                        else if(!gameOverDone)
                            gameOver(g);
                        else {
                            //Draws the final frame of the game over screen along with the replay text
                            drawGameOver(g, gameOverFrame);
                            if(isGameOver)
                                drawReplay(g, 0);
                        }
                    }
                }
            }
            /*
             * Sets the bottom bar visibility to true if the one second delay loading screen isn't running, the user is past the custom attacks start screen, and the user
             * is either in the custom attacks mode or is playing attacks from custom attacks or false otherwise
             */
            if(!oneSecondDelay.isRunning() && CustomAttacks.isIn() && (isCustomAttack || canBeStopped))
                bottomBar.setVisible(true);
            else
                bottomBar.setVisible(false);
            //If the attacks are done, the main audio is cut, and the speech bubble is drawn
            if(a != null && a.isFinished() && !oneSecondDelay.isRunning()) {
                if(genocide && gifFrame == 19 || !genocide && gifFrame == 10 && !canBeStopped) {
                    if(main != null)
                        main.stop();
                    g.drawImage(speech, speechX, speechY, null);
                }
            }
            //If the attacks are done, the player is either sent back to custom attacks if they were running attacks from custom attacks. Otherwise, the Undyne speech is displayed
            if(a != null && a.isFinished() && (genocide && gifFrame == 19 || !genocide && gifFrame == 10) && !oneSecondDelay.isRunning()) {
                if(canBeStopped)
                    stop(true);
                else
                    undyneSpeech(g);
            }
            //For the gradient button tolerances, if they are not visible, there color changes are reset to their initial value
            closeButton.visibilityCheck();
            draggableButton.visibilityCheck();
            musicButton.visibilityCheck();
            sfxButton.visibilityCheck();
            
            //If the mouse is on the music button, music slider, or the music volume is being changed by means of the music slider, the music slider is shown.
            if(musicButton.onButton() || musicSlider.isVisible() && onSlider("music") || musicSlider.actionPerforming())
                musicSlider.setVisible(true);
            else
                musicSlider.setVisible(false);
            //If the mouse is on the sfx button, sfx slider, or the sfx volume is being changed by means of the sfx slider, the sfx slider is shown.
            if(sfxButton.onButton() || sfxSlider.isVisible() && onSlider("sfx") || sfxSlider.actionPerforming())
                sfxSlider.setVisible(true);
            else
                sfxSlider.setVisible(false);
            
            //If Undyne is done speaking, the replay text is shown
            if(speechDone)
                drawReplay(g, 10);
            
            //Sets up the expanding and contracting of the help pop-up based on whether or not it should be shown
            helper.initiate(helpStarter);
            
            //Prints the user's score if in survival
            if(survival && !beginning)
                printScore(g);
            
            //Controls the loading animation
            if(oneSecondDelay.isRunning()) {
                Graphics2D g2d = (Graphics2D) g;
                if(++loadingCounter == 2) {
                    loadingCounter = 0;
                    ++loadingFrame;
                }
                if(loadingFrame == 48)
                    loadingFrame = 0;
                g2d.drawImage(loadingScreen[loadingFrame], loadingTransform, null);
            }
            else {
                loadingCounter = 0;
                loadingFrame = 0;
            }
            
            //Draws the glow around the focused NumberField in custom attacks
            drawFieldFocus(g);
            
            //If the JFrame is unfocused, a semitransparent sheet of white is put ovr the screen
            if(checkFocus.windowNotFocused()) {
                g.setColor(UNFOCUSED_COLOR);
                g.fillRect(0, 0, 600, 600);
            }
        }
        g.dispose();
    }
    
    /**
     * Draws the glow around the focused NumberField in custom attacks
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawFieldFocus(Graphics g) {
        if(focused != null && focused.getFocused() && !CustomAttacks.areAnyDirectionsSelected())
            g.drawImage(numberFieldGlow, -(numberFieldGlow.getWidth() - 30) / 2 + focused.getX() + 1, -(numberFieldGlow.getWidth() - 16) / 2 + focused.getY() + 5, null);
    }
    
    /**
     * Sets the focused numberfield
     *
     * @param nf The newly-focused numberfield
     */
    public static void setFocusedField(ArrowBar.NumberFieldFocus nf) {
        focused = nf;
    }
    
    /**
     * Draws the cheat activated message
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawCheat(Graphics g) {
        if(automatic)
            activated = "Cheat Activated";
        else {
            activated = "";
            nothingEndIndex = 0;
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(deteFontNorm);
        g2.setColor(Color.GREEN);
        if(!activated.equals(""))
            g2.drawString(activated.substring(0, nothingEndIndex), 0, 13 + 30);
        if(++nothingCounter % 7 == 0 && nothingEndIndex < activated.length())
            nothingEndIndex++;
    }
    
    /**
     * Sets the direction the player should face based on which direction has the arrow that will arrive in the least
     * amount of time
     */
    private void automatic() {
        //If the cheat is activated, the bottom bar automatic box is checked, and there is at least one arrow in the current attacks list, the direction is set
        if((automatic || bottomBar.isRobotBoxChecked()) && a1.getList().size() > 0) {
            ArrayList<Arrow> arrows = a1.getList();
            double time = Integer.MAX_VALUE;
            char pointTowards = 'u';
            
            /*
             * For each arrow, the time is calculated based on the direction the arrow is calculated.
             * Then, the time is compared to the minimum time, and if it is less, the direction the user
             * should face is changed accordingly. The final direction chosen after iterating through all
             * of the arrows is the one the player's direction is set to
             */
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
    
    /**
     * Activates the cheat if the user types in the cheat code
     */
    private void automaticTester() {
        //The string of typed characters is shortened to the length of the cheat string to prevent it continuously growing longer
        if(typed.length() > ROBOT_STRING.length())
            typed = typed.substring(typed.length() - ROBOT_STRING.length());
        //If the string of typed characters is equal to the cheat code, the cheat is either switched from deactivated to activated or vice versa
        if(typed.length() == ROBOT_STRING.length() && typed.equalsIgnoreCase(ROBOT_STRING)) {
            automatic = !automatic;
            typed = typed.substring(0, typed.length() - ROBOT_STRING.length());
        }
    }
    
    /**
     * Draws the undyne animation in the game
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawUndyneGif(Graphics g) {
        //If the game is not finished, the animation takes place
        if(a == null || !a.isFinished() || genocide && gifFrame != 19 || !genocide && gifFrame != 10) {
            int maxCount;
            int gifChange;
            if(genocide) {
                maxCount = 79;
                gifChange = 4;
            }
            else {
                maxCount = 31;
                gifChange = 3;
            }
            //Animation adjuster for the genocide animation
            if(genocide) {
                if(gifFrame >= maxCount)
                    gifFrame = 0;
                else if(gifCount % gifChange == 0)
                    ++gifFrame;
                ++gifCount;
                if(gifCount == gifChange)
                    gifCount = 0;
            }
            /*
             * Animation adjuster for the normal animation
             *
             * Note: The way the animation works is such that, every three frames, it takes four counts instead of three counts
             * before the gif is changed. However, the counting is 1, 4, 7, etc., so you have to subtract one to get it to match
             * correctly. That's why the below code looks a bit strange.
             */
            else {
                if(gifFrame >= maxCount)
                    gifFrame = 0;
                else if(gifCount % gifChange == 0 && (gifFrame - 1) % 3 != 0)
                    ++gifFrame;
                else if((gifFrame - 1) % 3 == 0 && gifCount % 4 == 0)
                    ++gifFrame;
                ++gifCount;
                if(gifCount == gifChange && (gifFrame - 1) % 3 != 0)
                    gifCount = 0;
                else if((gifFrame - 1) % 3 == 0 && gifCount == 4)
                    gifCount = 0;
            }
        }
        Graphics2D g2d = (Graphics2D) g.create();
        
        //The gif is drawn with a 30% opacity to make it less distracting to the player
        float opacity = 0.3f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        
        //The gif is shifted based on which animation is being used
        int gifXShift;
        if(genocide)
            gifXShift = 198;
        else
            gifXShift = 189;
        if(gifUndyne != null && p != null)
            g2d.drawImage(gifUndyne[gifFrame], gifXShift + p.getElementPosition(), 10 + p.getElementPosition(), null);
        g2d.dispose();
    }
    
    /**
     * The exceptions to the typical counter for the heartbreak animation are in the given exceptions list
     *
     * @param breakFrame The heartbreak frame being tested for if it is in the exceptions list
     * @return True if there is an exception to the typical counter for the heartbreak animation
     */
    private boolean breakHeartException(int breakFrame) {
        for(int exception : HEART_BREAK_EXCEPTIONS) {
            if(breakFrame == exception)
                return true;
        }
        return false;
    }
    
    /**
     * Draws the heart break animation given the frame
     *
     * @param g          The graphics object used for drawing the Runner JPanel
     * @param breakFrame The frame of the heartbreak animation
     */
    private void makeBreakHeart(Graphics g, int breakFrame) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = heartBreak[breakFrame].getWidth();
        int height = heartBreak[breakFrame].getHeight();
        g2d.drawImage(heartBreak[breakFrame], getWidth() / 2 - width / 2 + 11, getHeight() / 2 - height / 2 + 78, null);
        g2d.dispose();
    }
    
    /**
     * Sets the frame of the heartbreak animation and calls the drawing method for the heart break animation
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void breakHeart(Graphics g) {
        boolean exception = breakHeartException(breakFrame);
        //Advances the heart break animation to the next frames every four ticks unless there is an exception
        if(++breakCount % 4 == 0 && !exception) {
            ++breakFrame;
            //If the break frame is 25, the heart is split into two, so the split sound should play
            if(breakFrame == 25) {
                split.play();
            }
            //If the break frame is 48, the heart is done breaking
            if(breakFrame == 48)
                heartDone = true;
            breakCount = 0;
        }
        //If there is an exception to the typical counting rules for a frame, the counting follows a rule based on the frame
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
    
    /**
     * Increases the game over frames based on the counter, plays the asgore talking sfx during certain frames, and
     * calls the game over drawing method
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void gameOver(Graphics g) {
        ++gameOverCount;
        if(gameOverCount % 4 == 0 && gameOverCount != 0) {
            ++gameOverFrame;
            //Plays the asgore talking noise during certain frames
            if(gameOverFrame % 2 == 0 && (gameOverFrame > 67 && gameOverFrame < 99 || gameOverFrame > 137 && gameOverFrame < 149 || gameOverFrame > 162 && gameOverFrame < 192))
                asgore.play();
            if(gameOverFrame >= 225) {
                gameOverDone = true;
                gameOverFrame = 225;
            }
            gameOverCount = 0;
        }
        drawGameOver(g, gameOverFrame);
    }
    
    /**
     * Draws the game over animation
     *
     * @param g             The graphics object used for drawing the Runner JPanel
     * @param gameOverFrame The current frame of the game over animation
     */
    private void drawGameOver(Graphics g, int gameOverFrame) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(gameOver[gameOverFrame], 154 + getWidth() / 2 - 600 / 2 + 1, 25 + getHeight() / 2 - 338 / 2, null);
        g2d.dispose();
        if(gameOverFrame == 225)
            isGameOver = true;
    }
    
    /**
     * Draws the box that the heart is in during gameplay
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawHeartBox(Graphics g) {
        g.setColor(HEART_BOX_COLOR);
        for(int size = 80; size > 72; --size)
            g.drawRect(getWidth() / 2 - size / 2 + p.getElementPosition(), getHeight() / 2 - size / 2 + p.getElementPosition(), size, size);
    }
    
    /**
     * Draws the black background of the application
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawBG(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    /**
     * Draws the green heart seen during gameplay
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawHeart(Graphics g) {
        int width = 30;
        int height = 30;
        g.drawImage(heart, getWidth() / 2 - width / 2 + 1 + p.getElementPosition() + flickeringHeart, getHeight() / 2 - height / 2 + p.getElementPosition(), null);
    }
    
    /**
     * Draws the circle the shield travels around during gameplay
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawCircle(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawOval(getWidth() / 2 - 25 + p.getElementPosition(), getHeight() / 2 - 25 + p.getElementPosition(), 50, 50);
    }
    
    /**
     * Draws the replay text seen at a game over or end of a game mode
     *
     * @param g      The graphics object used for drawing the Runner JPanel
     * @param xShift The shift in x-position of the text based on whether the replay text is shown in a game over or end
     *               of a game mode
     */
    private void drawReplay(Graphics g, int xShift) {
        float opacity = (float) fadeStart;
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(replay, 187 + xShift, 421, null);
        g2d.dispose();
        
        //Controls the flashing aspect of the replay text
        if(++flashCount % 3 == 0) {
            //Increases the opacity if the opacity is less than or equal to 1. Otherwise, it switches to decreasing until the opacity becomes less than 0.03 and switches back.
            if(fadeStart <= 1 && !switchFade)
                fadeStart += 0.02;
            else {
                switchFade = true;
                fadeStart -= 0.02;
                if(fadeStart < 0.03)
                    switchFade = false;
            }
            flashCount = 0;
        }
    }
    
    /**
     * Draws the Undyne speech text at the end of a game mode
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void undyneSpeech(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(deteFontSpeech);
        g2d.setColor(Color.BLACK);
        
        //Sets the message to the easy, medium, or hard message based on the game mode
        String[] message;
        if(genocide)
            message = HARD_MESSAGE;
        else if(stage.isMedium())
            message = MEDIUM_MESSAGE;
        else
            message = EASY_MESSAGE;
        
        //The print text is used for determining whether the last character said was or wasn't a space
        String print = "";
        
        //The speechX is shifted left by 8 if Undyne is in Undying mode
        int speechTextX = speechX + 30 - (genocide ? 8 : 0);
        
        //If the Undyne speech is on the first message, it will draw the first message up to the point of the speechCounter
        if(speechCounter < message[0].length() + 1) {
            print = message[0].substring(0, speechCounter);
            g.drawString(message[0].substring(0, speechCounter), speechTextX, speechY + 20);
        }
        
        //If the Undyne speech is on the second message, it will draw the first and second messages up to the point of the speechCounter
        else if(speechCounter < message[1].length() + message[0].length() + 2) {
            print = message[1].substring(0, speechCounter - (message[0].length() + 1));
            g.drawString(message[0], speechTextX, speechY + 20);
            g.drawString(message[1].substring(0, speechCounter - (message[0].length() + 1)), speechTextX, speechY + 40);
        }
        
        //If the Undyne speech is on the third message, it will draw the first, second, and third messages up to the point of the speechCounter
        else if(speechCounter < message[2].length() + message[1].length() + message[0].length() + 3) {
            print = message[2].substring(0, speechCounter - (message[0].length() + message[1].length() + 2));
            g.drawString(message[0], speechTextX, speechY + 20);
            g.drawString(message[1], speechTextX, speechY + 40);
            g.drawString(message[2].substring(0, speechCounter - (message[0].length() + message[1].length() + 2)), speechTextX, speechY + 60);
        }
        //If the Undyne speech is complete, it just draws all three messages.
        else {
            g.drawString(message[0], speechTextX, speechY + 20);
            g.drawString(message[1], speechTextX, speechY + 40);
            g.drawString(message[2], speechTextX, speechY + 60);
            speechDone = true;
        }
        //Plays the Undyne speaking sfx if the speech has advanced, and the last character said wasn't a space
        if(speechCounter != speechCounterPrev && print.length() != 0 && print.charAt(print.length() - 1) != ' ') {
            if(genocide)
                undying.play();
            else
                undyne.play();
        }
        speechCounterPrev = speechCounter;
        
        //Increments the speech counter if it has not already completed and six counts have occurred
        if(speechCounter < message[2].length() + message[1].length() + message[0].length() + 3 && speechDelayCounter % 6 == 0)
            ++speechCounter;
        if(++speechDelayCounter == 6)
            speechDelayCounter = 0;
    }
    
    /**
     * Prints the score during the survival game mode
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private static void printScore(Graphics g) {
        //If a new attack has occurred, then the score is incremented based on the level
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
    
    /**
     * Toggles the visibility of the start screen buttons to the visibility state given
     *
     * @param visibility True if the buttons should be shown and false otherwise
     */
    static void toggleInitialButtonsVisibility(boolean visibility) {
        creditsButton.setVisible(visibility);
        helpButton.setVisible(visibility);
        playButton.setVisible(visibility);
        creatorButton.setVisible(visibility);
    }
    
    /**
     * Moved the buttons either to their normal positions or offscreen based on the given input
     *
     * @param shouldMove True if the buttons should be put offscreen and false otherwise
     */
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
    
    /**
     * Changes the main audio and increments the current level for survival
     */
    static void changeMain() {
        ++levelIndex;
        if(levelIndex > 1)
            heal.play();
        main.stop();
        main = mainSounds[++mainIndex];
        main.changeVolume(musicSlider.getPercentage());
        main.play();
    }
    
    /**
     * Switches the Undyne gif from normal mode to Undying mode and converts the health system accordingly
     */
    static void changeUndyneGif() {
        genocide = true;
        gifUndyne = gifUndyneUndying.clone();
        p.setBaseDamage(2);
        p.setDamageOffset(4);
        p.convertHealth();
    }
    
    /**
     * Plays the healing sound and increases the health for the final level mode
     */
    static void finalBoost() {
        heal.play();
        p.healthBoost();
    }
    
    /**
     * Returns the survival state
     *
     * @return The survival state
     */
    static boolean isSurvival() {
        return survival;
    }
    
    /**
     * Returns whether the health pop-up should be showing
     *
     * @return The health pop-up visibility state
     */
    static boolean getHelpStarter() {
        return helpStarter;
    }
    
    /**
     * Returns if the one second delay is running
     *
     * @return True if the one second delay timer is running and false otherwise
     */
    static boolean isOneSecondDelayRunning() {
        return oneSecondDelay.isRunning();
    }
    
    /**
     * Returns the main JFrame
     *
     * @return The main JFrame
     */
    public static JFrame getFrame() {
        return frame;
    }
    
    /**
     * Resets necessary static values and restarts application
     */
    private static void restartApplication() {
        timer.stop();
        allStopped = true;
        stage.resetVars(isReplaying);
        a.resetVars();
        if(gameDone != null)
            gameDone.stop();
        dir = 'u';
        typed = "";
        activated = "";
        delay = 10;
        fadeStart = 0;
        nothingEndIndex = 0;
        breakCount = 0;
        breakFrame = 0;
        flickeringHeart = 0;
        gifFrame = 0;
        gifCount = 0;
        gameOverCount = 0;
        gameOverFrame = 0;
        flickerCounter = 0;
        nothingCounter = 0;
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
        creatorArrowDirectionCounter = 0;
        genocide = false;
        survival = false;
        heartDone = false;
        gameOverDone = false;
        heartBreakStarted = true;
        gameOverScreenStarted = true;
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
        timer = null;
        oneSecondDelay = null;
        gifUndyne = null;
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
    
    /**
     * Starts the game for a game mode
     */
    private static void startGameMode() {
        //Prevents the player from starting the game multiple times when the loading screen is active, which can cause errors
        if(!oneSecondDelay.isRunning()) {
            //If the start game mode one second delay has not occurred yet, the start screen music is stopped, and the loading screen starts
            if(!isStartTimerDone) {
                startScreen.stop();
                oneSecondDelay.setActionCommand("start");
                oneSecondDelay.start();
                isStartTimerDone = true;
            }
            //After the one second delay is done, the method will be called again, and it will set up everything necessary for the game
            else {
                dir = 'u';
                genocide = stage.isHard();
                survival = stage.isSurvival();
                a = null;
                if(survival)
                    a = new Attacks(Attacks.GameMode.SURVIVAL);
                else if(!genocide && !stage.isMedium())
                    a = new Attacks(Attacks.GameMode.EASY);
                else if(!genocide)
                    a = new Attacks(Attacks.GameMode.MEDIUM);
                else
                    a = new Attacks(Attacks.GameMode.HARD);
                a1 = new Attack(a);
                a.setAttack(a1);
                toggleInitialButtonsVisibility(false);
                setUpUndyne(genocide);
                beginning = false;
                main.changeVolume(musicSlider.getPercentage());
                main.play();
                isStartTimerDone = false;
            }
        }
    }
    
    /**
     * Starts the game for creator attacks
     *
     * @param isCalledByTimer True if the timer calls the method
     */
    public static void playCreatorAttacks(boolean isCalledByTimer) {
        //Prevents clicking the play button multiple times when the loading screen is active, which can cause errors
        if(!oneSecondDelay.isRunning()) {
            
            //If the play timer has not occurred, it will set up the loading screen and reset the player's shield direction and angle
            if(!isPlayTimerDone) {
                creatorMusic.stop();
                oneSecondDelay.setActionCommand("play");
                oneSecondDelay.start();
                dir = 'u';
                p.setDirUp();
                p.zeroAngle();
                isPlayTimerDone = true;
            }
            //Once the loading screen is done, the method will be called again, and it will set up everything necessary for the game
            else if(isCalledByTimer) {
                try {
                    a = new Attacks(customAttackMaker.getAttacks());
                }
                catch(Exception e) {
                    creatorMusic.play();
                    isPlayTimerDone = false;
                    return;
                }
                a1 = new Attack(a);
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
    
    /**
     * The stop method returns the user back to the attack creator after a delay loading screen
     *
     * @param isCalledByTimer True if the timer calls the method, the attacks run out, or the player dies
     */
    public static void stop(boolean isCalledByTimer) {
        //Prevents clicking the stop button multiple times when the loading screen is active, which can cause errors
        if(!oneSecondDelay.isRunning()) {
            //If the stop timer hasn't occurred, stop the main music and do the one second delay loading screen
            if(!isStopTimerDone) {
                main.stop();
                oneSecondDelay.setActionCommand("stop");
                oneSecondDelay.start();
                isStopTimerDone = true;
            }
            //Once the loading screen is done, the method will be called again, and it will set everything ready for the custom attack creator
            else if(canBeStopped && isCalledByTimer) {
                creatorMusic.play();
                a.resetVars();
                a1.resetVars();
                isCustomAttack = true;
                canBeStopped = false;
                p.setDamaged(false);
                p.resetTimeoutCounter();
                flickeringHeart = 0;
                isStopTimerDone = false;
            }
        }
    }
    
    /**
     * Opens the attack creator (switches from start screen to attack creator)
     */
    private static void openCreator() {
        //Prevents the player from opening the attack creator multiple times when the loading screen is active, which can cause errors
        if(!oneSecondDelay.isRunning()) {
            //If the one second delay loading screen hasn't been shown between the start screen and the attack creator, start it
            if(!isOpenCreatorTimerDone) {
                startScreen.stop();
                oneSecondDelay.setActionCommand("openCreator");
                oneSecondDelay.start();
                isOpenCreatorTimerDone = true;
            }
            //After the one second delay loading screen is done, prepare the attack creator
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
    
    /**
     * Closes the attack creator (switches from attack creator to start screen)
     */
    private static void closeCreator() {
        //Prevents the player from closing the attack creator multiple times when the loading screen is active, which can cause errors
        if(!oneSecondDelay.isRunning()) {
            //If the one second delay loading screen hasn't been shown between the attack creator and the start screen, start it
            if(!isCloseCreatorTimerDone) {
                creatorMusic.stop();
                oneSecondDelay.setActionCommand("closeCreator");
                oneSecondDelay.start();
                isCloseCreatorTimerDone = true;
            }
            //After the one second delay loading screen is done, prepare the start screen
            else {
                delay = 10;
                isCustomAttack = false;
                beginning = true;
                isCloseCreatorTimerDone = false;
            }
        }
    }
    
    /**
     * Sets up the player and Undyne animation based on the genocide mode
     *
     * @param isGenocide True if Undyne should be in Undying mode and false otherwise
     */
    private static void setUpUndyne(boolean isGenocide) {
        Runner.genocide = isGenocide;
        if(isGenocide) {
            p.setHealth(60);
            p.setBaseDamage(3);
            p.setDamageOffset(12);
            main = mainSounds[2];
            speechX = 310;
            speechY = 60;
            gifUndyne = gifUndyneUndying.clone();
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
            gifUndyne = gifUndyneNormal.clone();
        }
    }
    
    /**
     * Adds a Component to the JFrame at the given index
     *
     * @param component The component to be added
     * @param index     The index at which the component should be added
     */
    public static void addComponent(Component component, int index) {
        frame.add(component, index);
    }
    
    /**
     * Removes a Component from the JFrame
     *
     * @param component The component to be removed
     */
    public static void removeComponent(Component component) {
        frame.remove(component);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //If the action command is null, it is the main timer, and the frame.repaint() method should be called
        if(e.getActionCommand() == null)
            frame.repaint();
        else {
            //Otherwise, based on the timer action command given, it will do certain actions
            switch(e.getActionCommand()) {
                case "main":
                    frame.repaint();
                    break;
                case "start":
                    startGameMode();
                    break;
                case "openCreator":
                    openCreator();
                    break;
                case "closeCreator":
                    closeCreator();
                    break;
                case "play":
                    playCreatorAttacks(true);
                    break;
                case "stop":
                    stop(true);
                    break;
            }
        }
    }
    
    /**
     * Sets up the KeyBindings for keyboard actions for the game
     *
     * @param runner The Runner JPanel that the KeyBindings are added to
     */
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
                cancelPressed();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_SHIFT, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelPressed();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_Z, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPressed();
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_V, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timer.getDelay() != 0)
                    timer.setDelay(0);
                else
                    timer.setDelay(10);
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_R, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //If the player is in the attack creator with an arrow direction selected, that direction will be changed to the random direction
                setCustomDirection('n');
                
                //If the replay text is showing, it restarts the application to the game mode selection screen
                if(isGameOver && !allStopped || speechDone && !allStopped) {
                    isReplaying = true;
                    restartApplication();
                    startGameMode();
                }
            }
        };
        
        new KeyAction(runner, condition, KeyEvent.VK_ENTER, 0, false) {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPressed();
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
    
    /**
     * When W or up arrow keys are pressed, any directions in custom attack are set to up, the player's shield direction
     * is set to up, and the heart mouse is set to move up
     */
    private static void upPressed() {
        setCustomDirection('u');
        dir = 'u';
        stage.setUp(true);
    }
    
    /**
     * When S or down arrow keys are pressed, any directions in custom attack are set to down, the player's shield
     * direction is set to down, and the heart mouse is set to move down
     */
    private static void downPressed() {
        setCustomDirection('d');
        dir = 'd';
        stage.setDown(true);
    }
    
    /**
     * When D or right arrow keys are pressed, any directions in custom attack are set to right, the player's shield
     * direction is set to right, and the heart mouse is set to move right
     */
    private static void rightPressed() {
        setCustomDirection('r');
        dir = 'r';
        stage.setRight(true);
    }
    
    /**
     * When A or left arrow keys are pressed, any directions in custom attack are set to left, the player's shield
     * direction is set to left, and the heart mouse is set to move left
     */
    private static void leftPressed() {
        setCustomDirection('l');
        dir = 'l';
        stage.setLeft(true);
    }
    
    /**
     * When the W or up arrow key is released, the up direction in the stage is set to false
     */
    private static void upReleased() {
        stage.setUp(false);
    }
    
    /**
     * When the S or down arrow key is released, the down direction in the stage is set to false
     */
    private static void downReleased() {
        stage.setDown(false);
    }
    
    /**
     * When the D or right arrow key is released, the right direction in the stage is set to false
     */
    private static void rightReleased() {
        stage.setRight(false);
    }
    
    /**
     * When the A or left arrow key is released, the left direction in the stage is set to false
     */
    private static void leftReleased() {
        stage.setLeft(false);
    }
    
    /**
     * When Z or enter is pressed, a variety of actions can happen based on the context (selection/confirm options)
     */
    private static void selectPressed() {
        if(beginning) {
            //If at the beginning ant the play button has not been chosen
            if(!stage.isPlayChosen()) {
                //If the user is on the credits button, the credits pop-up will expand
                if(creditsButton.onButton() && !creditsList.getExpanding()) {
                    StartScreen.playClick();
                    creditsList.setExpanding(true);
                    creditsList.setVisible(true);
                }
                //If the user is on the help button, the help pop-up will expand
                else if(helpButton.onButton() && !helper.getExpanding()) {
                    StartScreen.playClick();
                    helpStarter = true;
                }
                //If the user is on the play button, the start screen will switch to the game mode selection screen
                else if(playButton.onButton()) {
                    StartScreen.playClick();
                    stage.playChosen(true);
                    moveButtons(true);
                    stage.setHeartX(5);
                    stage.setHeartY(75);
                }
                //If the user is on the attack creator button, the start screen will switch to the attack creator screen
                else if(creatorButton.onButton() && !oneSecondDelay.isRunning()) {
                    StartScreen.playClick();
                    openCreator();
                }
            }
            //If the user is on the first heart (the one on the Undyne title), that heart will be activated for the easter egg
            if(stage.isOnHeartOne() && !stage.heartOneActivated()) {
                block.play();
                stage.activateHeartOne();
                stage.activateBlueHeartFlash();
            }
            //If the user is on the second heart (the left one on the Absolute title), that heart will be activated for the easter egg
            else if(stage.isOnHeartTwo() && !stage.heartTwoActivated()) {
                block.play();
                stage.activateHeartTwo();
                stage.activateBlueHeartFlash();
            }
            
            //If the user is on the third heart (the right one on the absolute title), that heart will be activated for the easter egg
            else if(stage.isOnHeartThree() && !stage.heartThreeActivated()) {
                block.play();
                stage.activateHeartThree();
                stage.activateBlueHeartFlash();
            }
            //If the user is in the game mode selection screen, and the user is on an activated game mode, the game mode will start
            else if(stage.shouldStart()) {
                StartScreen.playClick();
                startGameMode();
            }
            
            /*
             * If the user is not on one of the easter egg hearts, at least one heart has been activated, and
             * all hearts are not activated the activations will be erased, so the user will have to start over
             */
            else if(stage.numHeartsActivated() > 0 && !stage.heartsActivated() && !stage.isOnHeartOne() && !stage.isOnHeartTwo() && !stage.isOnHeartThree()) {
                stage.deactivateHearts();
                stage.playDamage();
            }
        }
        
        //If the replay text is showing, restart the application
        else if(isGameOver && !allStopped || speechDone && !allStopped)
            restartApplication();
        
        //Sets selected directions for custom attack arrows to false
        for(AttackBar attBar : CustomAttacks.attacks) {
            for(ArrowBar arrBar : attBar.getArrows()) {
                if(arrBar.isDirectionSelected())
                    arrBar.directionSelectedFalse();
            }
        }
    }
    
    /**
     * When X or shift is pressed, a variety of actions can happen based on the context (cancellation/skipping options)
     */
    private static void cancelPressed() {
        //If the help pop-up is activated, deactivate it
        if(helpStarter) {
            StartScreen.playClick();
            helpStarter = false;
        }
        //If the credits pop-up is activated, deactivate it
        else if(creditsList.getExpanding()) {
            StartScreen.playClick();
            creditsList.setExpanding(false);
        }
        
        //If the user is in the game selection screen, go back to the start screen
        else if(stage.isPlayChosen() && beginning && !oneSecondDelay.isRunning() && !stage.heartsActivated()) {
            StartScreen.playClick();
            stage.deactivateSpears();
            stage.playChosen(false);
            moveButtons(false);
            stage.setHeartX(5);
            stage.setHeartY(72);
        }
        
        //If the user is in the attack creator, go back to the start screen
        else if(isCustomAttack && !oneSecondDelay.isRunning() && !CustomAttacks.areAnyDirectionsSelected()) {
            StartScreen.playClick();
            closeCreator();
        }
        
        //If the user is in the game over screen after at least one time of seeing the replay text, the user can skip the game over text
        else if(!gameOverScreenStarted && !isGameOver && !isFirstTime) {
            gameOverFrame = 225;
            isGameOver = true;
        }
    }
    
    /**
     * Sets all selected arrow directions is custom attacks to the given direction
     *
     * @param dir The direction to set all the selected arrows in custom attacks
     */
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
        //If the key character given by the KeyEvent is not undefined, add it to the typed string
        if(e.getKeyChar() != KeyEvent.CHAR_UNDEFINED)
            typed += e.getKeyChar();
        automaticTester();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    /**
     * Returns true if the user is considered to be on the given audio slider
     *
     * @param slider Either the music or sfx slider
     * @return True if the user is considered to be on the given audio slider
     */
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
        
        //Gets the mouse position
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        
        //Gets the location of the slider
        Point sliderPoint = s.getLocationOnScreen();
        
        //Translates the mouse position such that it is relative to the slider's position (the slider position is like the origin)
        mousePos.translate((int) Math.round(-sliderPoint.getX()), (int) Math.round(-sliderPoint.getY()));
        
        //Gets the slider's bounds (rectangle)
        Rectangle bounds = s.getBounds();
        
        /*
         * Creates bounds that create a rectangle around the slider with the width of a gradient
         * button and the height of the bottom of the gradient button to the height of the slider
         */
        bounds.setBounds(gb.getX() - s.getX(), -6, gb.getWidth(), s.getHeight() + 6);
        return bounds.contains(mousePos);
    }
    
    /**
     * Checks the bottom bar checkbox if the mouse is in the bottom bar checkbox and the checkbox can be seen
     *
     * @param e The mouse click {@code MouseEvent}
     */
    private static void barCheckBoxClicked(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        boolean mouseIntersectsCheckBox = bottomBar.getBarCheckBox().contains(x, y);
        if(mouseIntersectsCheckBox) {
            StartScreen.playClick();
            //If the user clicks the checkbox when testing attacks in the attack creator, it switches off/on the automatic play option
            if(canBeStopped)
                bottomBar.flipIsRobotBoxedChecked();
                
                //If the user clicks the checkbox when in the attack creator editor, it switches between the Undyne Undying and normal modes
            else if(isCustomAttack)
                bottomBar.flipIsGenocideBoxChecked();
        }
    }
    
    /**
     * Sets the visibility of the attack creator editor top bar
     *
     * @param visibility True if the top bar should be visible and false otherwise
     */
    public static void setTopBarVisibility(boolean visibility) {
        topBar.setVisible(visibility);
    }
    
    /**
     * Returns true if the user is on one of the initial start screen buttons
     *
     * @return True if the user is on one of the initial start screen buttons
     */
    static boolean onFrontButton() {
        return playButton.onButton() || creatorButton.onButton() || creditsButton.onButton() || helpButton.onButton();
    }
    
    /**
     * Returns true if the bottom bar should be drawn
     *
     * @return True if the bottom bar should be drawn
     */
    public static boolean bottomBarShouldDraw() {
        return !allStopped && !oneSecondDelay.isRunning() && CustomAttacks.isIn() && (isCustomAttack || canBeStopped);
    }
    
    /**
     * Returns true if the JFrame window is not focused
     *
     * @return True if the JFrame window is not focused
     */
    public static boolean windowNotFocused() {
        return checkFocus.windowNotFocused();
    }
    
}
