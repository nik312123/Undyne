package defense;

import nikunj.classes.MouseClickTolerance;
import nikunj.classes.PopUp;
import nikunj.classes.Sound;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.AttributedString;
import java.util.Random;

/**
 * Runs what happens on the application start screen
 */
public class StartScreen {
    
    /**
     * The opacity of the Undyne title
     */
    private double undyneTitleOpacity = 0;
    
    /**
     * The opacity of the "Press Z to start" message
     */
    private double fadeStart = 0;
    
    /**
     * The sfx volume for start screen audio. This is changed when the sfx volume in Runner is changed.
     */
    private static double sfxVolume = 1;
    
    /**
     * The music volume for start screen audio. This is changed when the music volume in Runner is changed.
     */
    private static double musicVolume = 1;
    
    /**
     * The opacity of the blue heart that flashes in the empty B of the Absolute text in the title
     */
    private float blueHeartOpacity = 0.02f;
    
    /**
     * The "Undyne" part of the title image
     */
    private static BufferedImage undyne;
    
    /**
     * The "Press Z to start" message that pops up when a game mode has been selected
     */
    private static BufferedImage start;
    
    /**
     * The "Press Z to select" message that pops up when hovering over a button
     */
    private static BufferedImage zSelect;
    
    /**
     * The heart used to select options at the start and game mode selection screens
     */
    private static BufferedImage heartMouse;
    
    /**
     * A blue version of the heartMouse used during the easter egg
     */
    private static BufferedImage heartMouseBlue;
    
    /**
     * The image used in the game mode selection screen to indicate the user to select a difficulty level
     */
    private static BufferedImage selectDifficulty;
    
    /**
     * The image used in the start screen to indicate the user to select one of the given option buttons
     */
    private static BufferedImage selectOption;
    
    /**
     * The "Absolute" part of the title
     */
    private static BufferedImage subtitle;
    
    /**
     * The "Absolute" part of the title with all hearts colored blue used during the easter egg
     */
    private static BufferedImage subtitleBlue;
    
    /**
     * The image containing the easy, medium, and hard buttons for the game mode selection screen
     */
    private static BufferedImage buttons;
    
    /**
     * The bones image used during the easter egg
     */
    private static BufferedImage bones;
    
    /**
     * The image of the blue heart that flashes in the empty B of the Absolute text in the title
     */
    private static BufferedImage blueHeartFlash;
    
    /**
     * The spear used when the survival game mode is highlighted
     */
    private static BufferedImage spear;
    
    /**
     * The arrows indicating the user to move the heart around at the start screen
     */
    private static BufferedImage arrows;
    
    /**
     * The message telling the user to use the WASD or arrow keys
     */
    private static BufferedImage keys;
    
    /**
     * The image with the survival mode button
     */
    private static BufferedImage survival;
    
    /**
     * The images that contain the fire animation when the hard mode is selected
     */
    private static BufferedImage[] fire = new BufferedImage[38];
    
    /**
     * The images that contain the dog gif when easy mode is selected
     */
    private static BufferedImage[] dog = new BufferedImage[2];
    
    /**
     * The images that contain the dog gif when hard mode is selected
     */
    private static BufferedImage[] greaterDog = new BufferedImage[3];
    
    /**
     * The images that contain the sans gif for the easter egg
     */
    private static BufferedImage[] sans = new BufferedImage[10];
    
    /**
     * The images that contain the dots used to indicate the number of activated hearts
     */
    private static BufferedImage[] dots = new BufferedImage[4];
    
    /**
     * The loading level of the hard button
     */
    private int hardButtonRect = 0;
    
    /**
     * The loading level of the easy button
     */
    private int easyButtonRect = 0;
    
    /**
     * The loading level of the medium button
     */
    private int mediumButtonRect = 0;
    
    /**
     * The loading level of the survival button
     */
    private int survivalButtonRect = 0;
    
    /**
     * The counter used for the fire animation
     */
    private int fireCounter = 0;
    
    /**
     * The current frame of the fire animation
     */
    private int fireFrame = 0;
    
    /**
     * The counter used for setting the frequency of the shifting of the Undyne title
     */
    private int undyneCount = 0;
    
    /**
     * The random x shift of the Undyne title
     */
    private int undyneXShift;
    
    /**
     * The random y shift of the Undyne title
     */
    private int undyneYShift;
    
    /**
     * Counter used for changing rate at which the increasing and decreasing of the visibility of the heartMouse when it
     * is damaged
     */
    private int flashCount = 0;
    
    /**
     * Counter used for changing the frequency at which hardButtonRect changes
     */
    private int hardButtonCount = 0;
    
    /**
     * Counter used for changing the frequency at which easyButtonRect changes
     */
    private int easyButtonCount = 0;
    
    /**
     * Counter used for changing the frequency at which survivalButtonRect changes
     */
    private int survivalButtonCount = 0;
    
    /**
     * Counter used for changing the frequency at which mediumButtonRect changes
     */
    private int mediumButtonCount = 0;
    
    /**
     * Counter used for the annoying dog animation
     */
    private int dogCounter = 0;
    
    /**
     * Counter used for the greater dog animation
     */
    private int greaterDogCount = 0;
    
    /**
     * The current frame the annoying dog animation is at
     */
    private int dogFrame = 0;
    
    /**
     * The current frame the greater dog animation is at
     */
    private int greaterDogFrame = 1;
    
    /**
     * The direction the greater dog animation should go (forwards a frame or back a frame)
     */
    private int greaterDogDirection = 1;
    
    /**
     * The addition to the width and 460/49 of the addition to the height of the "Absolute" title in pixels
     */
    private int subtitleScaler = 800;
    
    /**
     * The x-position of the heart on the start screen relative to the center of the screen and the left edge of the
     * heart graphic
     */
    private int heartX = 5;
    
    /**
     * The y-position of the heart on the start screen relative to the center of the screen and the top edge of the
     * heart graphic
     */
    private int heartY = 72;
    
    private int frameCounter1 = 0;
    
    /**
     * Counter used for the movement of the heart on the start screen
     */
    private int moveCounter = 0;
    
    /**
     * The counter used for showing the bones and hiding the bones during the easter egg
     */
    private int boneCounter = 0;
    
    /**
     * The y-position of the bones image
     */
    private int boneY = 600;
    
    /**
     * The counter used for the Sans animation during the easter egg
     */
    private int sansCount = 0;
    
    /**
     * The current frame the sans animation is at
     */
    private int sansFrame = 0;
    
    /**
     * The counter used to show and hide the heart when it is flickering
     */
    private int flickerCounter = 0;
    
    /**
     * The change in y-position of the heart (used to hide the heart when 9000 or show the heart when 0)
     */
    private int flickerChangeY = 0;
    
    /**
     * Counter that stops the flickering animation once it hits zero
     */
    private int flickeringCountdown = 75;
    
    /**
     * The counter used to decrease the opacity of the blue heart that flashes in absolute when a heart is activated
     */
    private int blueHeartFlashCounter = 0;
    
    /**
     * Counter used for the spear animation
     */
    private int spearCounter = 0;
    
    /**
     * The opacity of the spear in the survival spear animation out of 30
     */
    private int spearOpacity = 0;
    
    /**
     * The counter used for showing and hiding the arrows on the start screen (the arrows that show you to move around
     * the heart)
     */
    private int arrowsCounter = 0;
    
    /**
     * The x-position of the "Made by Nikunj Chawla and Aaron Kandikatla" text
     */
    private int nameStringX = 610;
    
    /**
     * The counter used to change the x-position of the name string
     */
    private int nameStringCounter = 0;
    
    /**
     * The warning counter that shows the warning to use the arrow keys or WASD if someone chooses to click with the
     * mouse instead until it hits zero
     */
    private int warningCounter = 0;
    
    /**
     * True if the right arrow key or D is being pressed down
     */
    private boolean right = false;
    
    /**
     * True if the left arrow key or A is being pressed down
     */
    private boolean left = false;
    
    /**
     * True if the up arrow key or W is being pressed down
     */
    private boolean up = false;
    
    /**
     * True if the down arrow key or S is being pressed down
     */
    private boolean down = false;
    
    /**
     * If true, the "Press Z to start" message is fading out; otherwise, it is fading in.
     */
    private boolean switchFade = false;
    
    /**
     * This is true unless the hard button is activated and the fire sound has been played; it is used to play the fire
     * sound only once when the hard button is activated
     */
    private boolean playFire = true;
    
    /**
     * This is true unless the easy button is activated and the annoying dog barking sound has been played; it is used
     * to play the annoying dog barking sound only once when the easy button is activated
     */
    private boolean playBark = true;
    
    /**
     * This is true unless the medium button is activated and the greater dog barking sound has been played; it is used
     * to play the greater dog barking sound only once when the medium button is activated
     */
    private boolean playBork = true;
    
    /**
     * This is true unless the survival button is activated and the spear sound effects have been played; it is used to
     * play the spear sound effects only once when the easy button is activated
     */
    private boolean playSpear = true;
    
    /**
     * True if the heart is on the hard button
     */
    private boolean onHard;
    
    /**
     * True if the heart is on the easy button
     */
    private boolean onEasy;
    
    /**
     * True if the heart is on the survival button
     */
    private boolean onSurvival;
    
    /**
     * True if the heart is on the medium button
     */
    private boolean onMedium;
    
    /**
     * True if the player has hit the ground in the easter egg
     */
    private boolean hitGround = false;
    
    /**
     * Set to true after the bone sound has been played during the easter egg
     */
    private boolean showBones = false;
    
    /**
     * True if Sans should not be visible and the easter egg is not finished resetting
     */
    private boolean hideSans = false;
    
    /**
     * True when the heart has hit the bones during the easter egg and should be flickering
     */
    private boolean flickering = false;
    
    /**
     * True if the spear should be visible (when the survival button is fully selected)
     */
    private boolean activateSpear = false;
    
    /**
     * True if the spear appear sfx has been played while activateSpear is true
     */
    private boolean spearAppearPlayed = false;
    
    /**
     * True if the spear hit sfx has been played while activateSpear is true
     */
    private boolean spearHitPlayed = false;
    
    /**
     * True if the heart has been moved using the arrow keys or WASD
     */
    private boolean heartMoved = false;
    
    /**
     * True if the arrows should be showing in the arrows animation
     */
    private boolean arrowsShouldShow = true;
    
    /**
     * True if the "Absolute" part of the title has hit the screen, and the slam sfx has been played
     */
    private boolean slammed = false;
    
    /**
     * True if the play button has been selected on the start screen and the game mode selection screen is showing
     */
    private boolean playChosen = false;
    
    /**
     * True if the hard button is full
     */
    private static boolean hardButtonFull = false;
    
    /**
     * True if the easy button is full
     */
    private static boolean easyButtonFull = false;
    
    /**
     * True if the survival button is full
     */
    private static boolean survivalButtonFull = false;
    
    /**
     * True if the medium button is full
     */
    private static boolean mediumButtonFull = false;
    
    /**
     * Is used to check if the start screen beginning animation has been played
     */
    static boolean isLoaded = false;
    
    /**
     * A boolean array that represents which hearts are activated
     */
    private boolean[] heartsActivated = new boolean[3];
    
    /**
     * The color of a game mode button that is being filled up
     */
    private static final Color BUTTON_LOADING_COLOR = new Color(246, 138, 21);
    
    /**
     * The color of the hard game mode button when full
     */
    private static final Color HARD_BUTTON_COLOR = Color.RED;
    
    /**
     * The color of the medium game mode button when full
     */
    private static final Color MEDIUM_BUTTON_COLOR = Color.ORANGE;
    
    /**
     * The color of the easy game mode button when full
     */
    private static final Color EASY_BUTTON_COLOR = new Color(0, 234 - 30, 77 - 30);
    
    /**
     * The color of the survival game mode button when full
     */
    private static final Color SURVIVAL_BUTTON_COLOR = new Color(0, 191, 255);
    
    /**
     * The flare sound effect that is played when the hard button is loaded
     */
    private static Sound flare;
    
    /**
     * The annoying dog barking sound that is played when the easy button is loaded
     */
    private static Sound bark;
    
    /**
     * The wall hit sound effect when the player hits the bottom of the screen during the easter egg
     */
    private static Sound wall;
    
    /**
     * The sound effect that is played when Sans raises the bones
     */
    private static Sound boneSound;
    
    /**
     * The sound effect that is played when the player is damaged by the easter egg bone attack
     */
    private static Sound damage;
    
    /**
     * Sans' boss fight music that is played during the easter egg
     */
    private static Sound megalovania;
    
    /**
     * The sfx that is played when the spear is materializing during the survival button full animation
     */
    private static Sound spearAppear;
    
    /**
     * The sfx that is played when the spear is moving during the survival button full animation
     */
    private static Sound spearFly;
    
    /**
     * The sfx that is played when the spear hits the survival button during the survival button full animation
     */
    private static Sound spearHit;
    
    /**
     * The sfx that is played when the player is slammed against the floor during the easter egg
     */
    private static Sound slam;
    
    /**
     * The greater dog barking sound that is played when the medium button is loaded
     */
    private static Sound bork;
    
    /**
     * The click sound effect that is played upon the selection or confirmation of certain things
     */
    private static Sound click;
    
    /**
     * The credits pop-up that shows where the non-original content comes from
     */
    private PopUp creditsList;
    
    /**
     * The JPanels that are shown on the credits pop-up that, when clicked, go to the original content's source
     */
    private static JPanel[] clickableNames = new JPanel[10];
    
    /**
     * The starting location of the spear in the spear animation
     */
    private static final Point2D SPEAR_SPAWN = new Point2D.Double(310, 197 + 110 - 60 * Math.tan(Math.toRadians(75)));
    
    /**
     * The ending location of the spear in the spear animation
     */
    private static final Point2D SPEAR_END = new Point2D.Double(250, 197 + 110);
    
    /**
     * The current location of he spear in the spear animation
     */
    private Point2D spearLocation = (Point2D) SPEAR_SPAWN.clone();
    
    /**
     * The Random object used in the random shifts of the Undyne title
     */
    private Random rand = new Random();
    
    /**
     * The {@code AffineTransform} that is used during some of the start screen drawing and animations
     */
    private AffineTransform startScreenTransform = new AffineTransform();
    
    /**
     * The text that is used for the credits text with each item being credits to a different source
     */
    private AttributedString[] creditsText = new AttributedString[9];
    
    /**
     * The {@code StartScreen} constructor that is used for everything to do with the start screen shown after the
     * application starts
     */
    StartScreen() {
        if(Runner.isFirstTime) {
            try {
                //Imports the audio files used on the start screen
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
                click = new Sound(Runner.class.getResource("/click.ogg"), false);
            }
            catch(UnsupportedAudioFileException | IOException e1) {
                e1.printStackTrace();
            }
            //Imports the images used for the start screen
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
            /*
             * If the player has already played the game before, there is no need to show the arrows
             * indicating to move the heart. Also, if they are replaying and showing the start screen,
             * it is assumed they wish to choose a game mode, so playChosen is set to true.
             */
            heartMoved = true;
            playChosen = true;
        }
        
        //Sets up text for credits pop-up
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
        creditsText[6] = new AttributedString("Free SFX: Button click SFX");
        addLinkFormatting(6, 0, 10);
        creditsText[7] = new AttributedString("Nikunj Chawla and Aaron Kandikatla: All other sprites");
        addLinkFormatting(7, 0, 13);
        addLinkFormatting(7, 18, 35);
        creditsText[8] = new AttributedString("And most importantly, thank you for enjoying our");
        for(AttributedString a : creditsText)
            a.addAttribute(TextAttribute.FONT, Runner.deteFontSpeech);
        
        //Initializes the credits pop-up
        creditsList = new PopUp(65, 65, 470, 470, 46, Color.BLACK, Color.ORANGE, 5) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void mouseClicked(MouseEvent e) {}
            
            @Override
            public void afterDraw(Graphics g) {
                if(creditsList.percentageExpanded() == 1.0) {
                    Runner.moveButtons(true);
                    int originalY = 20 + g.getFontMetrics(Runner.deteFontSpeech).getHeight() / 2;
                    int x = 20, y = originalY;
                    g.setColor(Color.WHITE);
                    g.setFont(Runner.deteFontSpeech);
                    for(AttributedString a : creditsText) {
                        a.addAttribute(TextAttribute.FONT, Runner.deteFontSpeech);
                        g.drawString(a.getIterator(), x, y);
                        y += 40;
                    }
                    g.drawString("Undertale SFX & music, and creating Undertale", x, originalY + 15);
                    g.drawString("and code", x, originalY + 15 + 40 * 7);
                    g.drawString("game!", x, originalY + 15 + 40 * 8);
                    for(JPanel b : clickableNames)
                        b.setVisible(true);
                    String pressX = "PRESS X TO EXIT";
                    String useMouse = "You may use the mouse to click on a blue link.";
                    g.drawString(useMouse, 235 - g.getFontMetrics().stringWidth(useMouse) / 2, 535 - Math.min(creditsList.getPopUpWidth(), 460) / 4);
                    g.drawString(pressX, 235 - g.getFontMetrics().stringWidth(pressX) / 2, 535 - Math.min(creditsList.getPopUpWidth(), 460) / 4 + 37);
                }
                if(Runner.windowNotFocused()) {
                    g.setColor(Runner.UNFOCUSED_COLOR);
                    Graphics2D g2d = (Graphics2D) g;
                    startScreenTransform.setToIdentity();
                    startScreenTransform.translate(getWidth() / 2.0 * (1 - percentageExpanded()), getHeight() / 2.0 * (1 - percentageExpanded()));
                    startScreenTransform.scale(percentageExpanded(), percentageExpanded());
                    Rectangle bounds = getBounds();
                    bounds.setLocation(0, 0);
                    Path2D.Double transformedShape = (Path2D.Double) startScreenTransform.createTransformedShape(bounds);
                    g2d.fill(transformedShape);
                }
            }
            
        };
        
        //The listener added to every name JPanel and goes to different sources based on the JPanel name
        MouseClickTolerance clickableNamesListener = new MouseClickTolerance(5, 750, MouseClickTolerance.ClickLocation.PRESS) {
            @Override
            public void onMousePress(MouseEvent e) {}
            
            @Override
            public void onMouseRelease(MouseEvent e) {}
            
            @Override
            public void onMouseClick(MouseEvent e) {
                String url = "";
                
                //Gets the integer set as the name of the clickable name JPanel and uses it to set the URL
                String name = ((JPanel) getRealSource()).getName();
                int nameInt = Integer.parseInt(name);
                switch(nameInt) {
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
                    //If the URL is not empty and the Desktop interface is supported, open the URL in the user's default browser
                    if(!url.isEmpty() && Desktop.isDesktopSupported())
                        Desktop.getDesktop().browse(new URI(url));
                }
                catch(IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
            
            @Override
            public void onMouseEnter(MouseEvent e) {
                //Change the cursor image to the hand cursor like a typical clickable link if the user hovers the mouse on a clickable name
                JPanel realSource = (JPanel) getRealSource();
                if(realSource.isVisible()) {
                    Runner.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    creditsList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    realSource.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }
            
            @Override
            public void onMouseExit(MouseEvent e) {
                //Changes the cursor back to the default cursor once the mouse is not hovering over a clickable name
                Runner.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                creditsList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                ((JPanel) getRealSource()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        };
        
        //Loops through the clickable names array and adds and initializes the clickable name JPanels
        for(int i = 0, y = 82; i < clickableNames.length; ++i, y += 40) {
            clickableNames[i] = new JPanel();
            JPanel b = clickableNames[i];
            b.setVisible(false);
            b.setLocation(85 - creditsList.getX(), y - creditsList.getY());
            if(i == 9)
                b.setLocation(237 - creditsList.getX(), 82 + 40 * 7 - creditsList.getY());
            b.setOpaque(false);
            b.setName(Integer.toString(i));
            b.addMouseListener(clickableNamesListener);
        }
        
        //Sets the sizes of each clickable name JPanel such that its width matches the corresponding text
        clickableNames[0].setSize(72, 13);
        clickableNames[1].setSize(30, 13);
        clickableNames[2].setSize(38, 13);
        clickableNames[3].setSize(123, 13);
        clickableNames[4].setSize(140, 13);
        clickableNames[5].setSize(47, 13);
        clickableNames[6].setSize(73, 13);
        clickableNames[7].setSize(109, 13);
        clickableNames[8].setSize(0, 13);
        clickableNames[9].setSize(139, 13);
        
        //Adds each of the clickable name JPanels to the credits pop-up
        for(JPanel b : clickableNames)
            creditsList.add(b);
        creditsList.setLayout(null);
    }
    
    /**
     * Adds formatting to the text at the given index starting at the beginning index of the string and ending at the
     * index before the last index such that the color of said portion of text is blue and italicized
     *
     * @param creditsIndex   The index of the text in the credits text to change to blue and italicized
     * @param beginningIndex The beginning index of the string to add the above formatting to
     * @param lastIndex      The index of the string after the last index to add the above formatting to
     */
    private void addLinkFormatting(int creditsIndex, int beginningIndex, int lastIndex) {
        creditsText[creditsIndex].addAttribute(TextAttribute.FOREGROUND, new Color(0, 82, 232), beginningIndex, lastIndex);
        creditsText[creditsIndex].addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE, beginningIndex, lastIndex);
    }
    
    /**
     * Runs the graphics for the {@code StartScreen}
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    void run(Graphics g) {
        if(frameCounter1 != 251)
            ++frameCounter1;
        if(frameCounter1 > 100) {
            if(frameCounter1 != 251)
                ++frameCounter1;
            if(frameCounter1 > 200) {
                if(subtitleScaler > 0)
                    subtitleScaler -= 20;
                else if(!slammed) {
                    slam.changeVolume(sfxVolume);
                    slam.play();
                    slammed = true;
                }
            }
            //Draws the Undyne part of the title for the start screen
            starterTitle(g);
            
            //Draws the Absolute part of the title for the start screen
            drawSubtitle(g);
            if(frameCounter1 > 250) {
                //Sets the visibility of the start screen initial buttons to true
                Runner.toggleInitialButtonsVisibility(true);
                
                //Responsible for moving the heart based on user input
                moveHeart();
                
                //Constrains the heart bounds such that it stays in the application window at all times
                constrain();
                
                //Draws the "Press Z to Start", "Press Z to Select", "Select an Option", "Select a Difficulty", and "Use WASD or arrow keys" text near the bottom of the start screen
                zToStart(g);
                
                //Draws the arrows on the start screen to indicate that the player should move the heart
                drawArrows(g);
                
                //Draws the gifs for the easy, hard, and medium animations
                gifDog(g);
                gifFire(g);
                gifGreaterDog(g);
                
                //Draws the filling effects and starts the animations and audio for the button animations
                easyButton(g);
                survivalButton(g);
                mediumButton(g);
                hardButton(g);
                
                //Draws the easy, medium, hard, and survival game mode buttons
                drawButtons(g);
                
                //Draws the "Press X to go back" message on the game mode selection screen
                drawBackMessage(g);
                
                //Draws the blue heart flashing effect when a heart is activated
                drawBlueHeartFlash(g);
                
                //Draws the spear survival button animation
                drawSpear(g);
                
                //Draws the constantly scrolling names of the game creators (Nikunj Chawla and Aaron Kandikatla)
                drawNames(g);
                
                //Draws a number of dots equal to that of the number of hearts activated
                drawDots(g);
                
                //Draws Sans if all of the hearts are activated
                if(heartsActivated())
                    drawSans(g);
                
                //Draws the red heart
                heartMouse(g);
                
                /*
                 * Draws the credits pop-up and ensures that the credits list clickable
                 * names are not clickable unless the credits pop-up is fully expanded
                 */
                creditsList.checkVisibility();
                if(creditsList.percentageExpanded() != 1.0) {
                    for(JPanel b : clickableNames)
                        b.setVisible(false);
                    if(!playChosen)
                        Runner.moveButtons(false);
                }
                
                //Used to signify that the start screen has been loaded
                isLoaded = true;
                
                //If the hearts are activated, the flickering heart animation is readied, megalovania is played, and the bone drawing is done
                if(heartsActivated()) {
                    flickeringHeart();
                    if(megalovania.isStopped() && boneCounter <= 265) {
                        megalovania.changeVolume(musicVolume);
                        megalovania.play();
                    }
                    if(hitGround)
                        drawBones(g);
                }
            }
        }
    }
    
    /**
     * Draws dots in the lower-left corner of the screen with the number of dots corresponding to the number of
     * activated hearts
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawDots(Graphics g) {
        g.drawImage(dots[numHeartsActivated()], 4, 588, null);
    }
    
    /**
     * Draws the "Absolute" part of the title
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawSubtitle(Graphics g) {
        startScreenTransform.setToIdentity();
        startScreenTransform.translate(96 - subtitleScaler / 2.0, 195 - subtitleScaler / 2.0);
        
        /*
         * The subtitle is scaled for animation purposes until the subtitleSclaer hits zero; the height part of the scaler is
         * multiplied by 49/460 since the width of the subtitle is 460 and the height is 490, which keeps it proportional
         */
        startScreenTransform.scale(1 + subtitleScaler / (double) subtitle.getWidth(), 1 + (subtitleScaler * 49.0 / 460.0) / (double) subtitle.getHeight());
        Graphics2D g2d = (Graphics2D) g;
        if(heartsActivated())
            g2d.drawImage(subtitleBlue, startScreenTransform, null);
        else
            g2d.drawImage(subtitle, startScreenTransform, null);
    }
    
    /**
     * Controls the movement of the start screen heart
     */
    private void moveHeart() {
        //Allows movement if the credits and help pop-ups are not expanding or expanded
        if(!Runner.getHelpStarter() && !creditsList.getExpanding()) {
            if(++moveCounter > 1) {
                final int speed = 2;
                //If the right arrow and/or D keys are pressed, move the heart right
                if(right) {
                    heartX += speed;
                    heartMoved = true;
                }
                //If the left arrow and/or A keys are pressed, move the heart left
                if(left) {
                    heartX -= speed;
                    heartMoved = true;
                }
                //If the hearts are not activated or the ground has been hit, then up or down can be considered
                if(!heartsActivated() || hitGround) {
                    //If the up arrow and/or W keys are pressed, move the heart up
                    if(up) {
                        heartY -= speed;
                        heartMoved = true;
                    }
                    
                    //If the down arrow and/or S keys are pressed, move the heart down
                    if(down) {
                        heartY += speed;
                        heartMoved = true;
                    }
                }
                else {
                    //The heart Y is accelerated downwards at 2 times the speed of usual movement if Sans changes it to blue
                    heartY += 2 * speed;
                    
                    //If the hearts are activated and the heartY exceeds or equals 293, the player has hit the ground and the wall hit sfx is played
                    if(heartY >= 283) {
                        wall.changeVolume(sfxVolume);
                        wall.play();
                        hitGround = true;
                    }
                }
                
                //Resets the move counter if it hits 3
                if(moveCounter == 3)
                    moveCounter = 0;
            }
        }
    }
    
    /**
     * Draws the "Undyne" part of the title (and controls the random shifting of it)
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void starterTitle(Graphics g) {
        if(!heartsActivated()) {
            //If the opacity is less than one, it is increased until it hits 1
            if(undyneTitleOpacity < 1)
                undyneTitleOpacity += 0.02;
            
            //Sets the random undyne x and y shift
            if(undyneCount % 7 == 0) {
                undyneXShift = rand.nextInt(3);
                undyneYShift = rand.nextInt(3);
            }
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) undyneTitleOpacity));
            g2d.drawImage(undyne, 62 + undyneXShift, 69 + undyneYShift, null);
            g2d.dispose();
            if(++undyneCount == 7)
                undyneCount = 0;
        }
    }
    
    /**
     * Constrains the heart on the start screen such that it can't go past the application boundaries
     */
    private void constrain() {
        if(heartX < -288)
            heartX = -288;
        else if(heartX > 296)
            heartX = 296;
        if(heartY < -301)
            heartY = -301;
        else if(heartY > 283)
            heartY = 283;
    }
    
    /**
     * Draws the "Press Z to Start", "Press Z to Select", "Select an Option", "Select a Difficulty", and "Use WASD or
     * arrow keys" text near the bottom of the start screen
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void zToStart(Graphics g) {
        if(!heartsActivated()) {
            //The opacity of the images is set to fadeStart
            float opacity = (float) fadeStart;
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            
            //If the heart is on the play, creator, credits, or help buttons, then the "Press Z to Select" message comes up along with the "Use WASD or arrow keys" message
            if(Runner.onFrontButton() && !playChosen) {
                if(warningCounter == 0)
                    g2d.drawImage(zSelect, 166, 485, null);
                else {
                    /*
                     * If the user has clicked the screen and the warning is activated, the "Press Z to select
                     * message is increased to 1.5 times its original size to make it clearer to the user what to do
                     */
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2d.drawImage(zSelect, (int) (166 - zSelect.getWidth() * 0.25), (int) (485 - 0.25 * zSelect.getHeight()), (int) (1.5 * zSelect.getWidth()), (int) (1.5 * zSelect.getHeight()), null);
                }
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(keys, 179, 490 + 50 - 20, null);
            }
            //If the heart is on the main start screen page but not on a front button, it draws the "Select an Option" message along with the "Use WASD or arrow keys" message
            else if(!playChosen) {
                startScreenTransform.setToIdentity();
                startScreenTransform.translate(174.5, 486);
                g2d.drawImage(selectOption, startScreenTransform, null);
                if(warningCounter == 0)
                    g2d.drawImage(keys, 179, 490 + 50 - 20, null);
                else {
                    /*
                     * If the user has clicked the screen and the warning is activated, the "Use WASD or arrow keys"
                     * message is increased to 1.5 times its original size to make it clearer to the user what to do
                     */
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2d.drawImage(keys, (int) (179 - 0.25 * keys.getWidth()), (int) (490 + 50 - 20 - 0.25 * keys.getHeight()), (int) (1.5 * keys.getWidth()), (int) (1.5 * keys.getHeight()), null);
                }
            }
            /*
             * If the user is not on the easy, medium, hard, or survival buttons, and none of the aforementioned buttons
             * are full, the "Select a Difficulty" message is drawn along with the "Use WASD or arrow keys" message
             */
            else if((!easyButtonFull || !onEasy) && (!hardButtonFull || !onHard) && (!survivalButtonFull || !onSurvival) && (!mediumButtonFull || !onMedium)) {
                startScreenTransform.setToIdentity();
                startScreenTransform.translate(174.5, 486);
                g2d.drawImage(selectDifficulty, startScreenTransform, null);
                if(warningCounter == 0)
                    g2d.drawImage(keys, 179, 490 + 50 - 20, null);
                else {
                    /*
                     * If the user has clicked the screen and the warning is activated, the "Use WASD or arrow keys"
                     * message is increased to 1.5 times its original size to make it clearer to the user what to do
                     */
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2d.drawImage(keys, (int) (179 - 0.5 * keys.getWidth() / 2), (int) (490 + 50 - 20 - 0.5 * keys.getHeight() / 2), (int) (1.5 * keys.getWidth()), (int) (1.5 * keys.getHeight()), null);
                }
            }
            //Otherwise, the "Press Z to Start message" is drawn along with the "Use WASD or arrow keys" message
            else {
                if(warningCounter == 0)
                    g2d.drawImage(start, 174, 485, null);
                else {
                    /*
                     * If the user has clicked the screen and the warning is activated, the "Press Z to start
                     * message is increased to 1.5 times its original size to make it clearer to the user what to do
                     */
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2d.drawImage(start, (int) (174 - start.getWidth() * 0.25), (int) (485 - 0.25 * start.getHeight()), (int) (1.5 * start.getWidth()), (int) (1.5 * start.getHeight()), null);
                }
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(keys, 179, 490 + 50 - 20, null);
            }
            if(warningCounter != 0)
                --warningCounter;
            g2d.dispose();
        }
        //Controls the flashing of the above messages
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
        if(++flashCount == 2)
            flashCount = 0;
    }
    
    /**
     * Draws the hard button filling effect and manages the fire animation and sfx
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void hardButton(Graphics g) {
        if(playChosen) {
            g.setColor(BUTTON_LOADING_COLOR);
            onHard = (heartX + 288 + 16 >= 413 && heartX + 288 <= 561 && heartY + 300 <= 363 && heartY + 300 + 16 >= 300);
            if(hardButtonCount % 6 == 0) {
                //If the heart is on the hard button
                if(onHard) {
                    //If the button is not full, fill it up more
                    if(hardButtonRect < 60) {
                        hardButtonRect += 5;
                        playFire = true;
                    }
                    else {
                        //If the fire sfx has not been played while the button is full, play it
                        if(playFire) {
                            playFire = false;
                            flare.changeVolume(sfxVolume);
                            flare.play();
                        }
                        //The hard button is set to full, and the other buttons are set to not full
                        hardButtonFull = true;
                        easyButtonFull = false;
                        survivalButtonFull = false;
                        mediumButtonFull = false;
                    }
                }
                //If the heart is not on the hard button and the hard button isn't completely full but is filled to some extent, empty it
                else if(hardButtonRect > 0 && !hardButtonFull) {
                    hardButtonRect -= 5;
                    playFire = true;
                }
            }
            if(++hardButtonCount == 6)
                hardButtonCount = 0;
            if(!hardButtonFull)
                g.fillRect(380 + 37, 360 - hardButtonRect, 140, hardButtonRect);
            else {
                g.setColor(HARD_BUTTON_COLOR);
                g.fillRect(380 + 37, 300, 140, 60);
            }
        }
        else {
            hardButtonRect = 0;
            hardButtonFull = false;
        }
    }
    
    /**
     * Draws the medium button filling effect and manages the greater dog animation and sfx
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void mediumButton(Graphics g) {
        if(playChosen) {
            g.setColor(BUTTON_LOADING_COLOR);
            onMedium = (heartX + 288 + 16 >= 226 && heartX + 288 <= 374 && heartY + 300 <= 363 && heartY + 300 + 16 >= 300);
            if(mediumButtonCount % 6 == 0) {
                //If the heart is on the medium button
                if(onMedium) {
                    //If the button is not full, fill it up more
                    if(mediumButtonRect < 60) {
                        mediumButtonRect += 5;
                        playBork = true;
                    }
                    else {
                        //If the bork sfx has not been played while the button is full, play it
                        if(playBork) {
                            playBork = false;
                            bork.changeVolume(sfxVolume);
                            bork.play();
                        }
                        //The medium button is set to full, and the other buttons are set to not full
                        mediumButtonFull = true;
                        easyButtonFull = false;
                        hardButtonFull = false;
                        survivalButtonFull = false;
                    }
                }
                //If the heart is not on the medium button and the medium button isn't completely full but is filled to some extent, empty it
                else if(mediumButtonRect > 0 && !mediumButtonFull) {
                    mediumButtonRect -= 5;
                    playBork = true;
                }
            }
            if(++mediumButtonCount == 6)
                mediumButtonCount = 0;
            if(!mediumButtonFull)
                g.fillRect(380 + 37 - 187, 360 - mediumButtonRect, 140, mediumButtonRect);
            else {
                g.setColor(MEDIUM_BUTTON_COLOR);
                g.fillRect(380 + 37 - 187, 300, 140, 60);
            }
        }
        else {
            mediumButtonRect = 0;
            mediumButtonFull = false;
        }
    }
    
    /**
     * Draws the easy button filling effect and manages the annoying dog animation and sfx
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void easyButton(Graphics g) {
        if(playChosen) {
            g.setColor(BUTTON_LOADING_COLOR);
            onEasy = (heartX + 288 + 16 >= 39 && heartX + 288 <= 187 && heartY + 300 <= 363 && heartY + 300 + 16 >= 300);
            if(easyButtonCount % 6 == 0) {
                //If the heart is on the easy button
                if(onEasy) {
                    //If the button is not full, fill it up more
                    if(easyButtonRect < 60) {
                        easyButtonRect += 5;
                        playBark = true;
                    }
                    else {
                        //If the bark sfx has not been played while the button is full, play it
                        if(playBark) {
                            bark.changeVolume(sfxVolume);
                            bark.play();
                            playBark = false;
                        }
                        //The easy button is set to full, and the other buttons are set to not full
                        easyButtonFull = true;
                        hardButtonFull = false;
                        survivalButtonFull = false;
                        mediumButtonFull = false;
                    }
                }
                //If the heart is not on the easy button and the easy button isn't completely full but is filled to some extent, empty it
                else if(easyButtonRect > 0 && !easyButtonFull) {
                    easyButtonRect -= 5;
                    playBark = true;
                }
            }
            if(++easyButtonCount == 6)
                easyButtonCount = 0;
            if(!easyButtonFull)
                g.fillRect(80 - 37, 360 - easyButtonRect, 140, easyButtonRect);
            else {
                g.setColor(EASY_BUTTON_COLOR);
                g.fillRect(80 - 37, 300, 140, 60);
            }
        }
        else {
            easyButtonRect = 0;
            easyButtonFull = false;
        }
    }
    
    /**
     * Draws the survival button filling effect and manages the spear animation and sfx
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void survivalButton(Graphics g) {
        if(playChosen) {
            g.setColor(BUTTON_LOADING_COLOR);
            onSurvival = (heartX + 288 + 16 >= 226 && heartX + 288 <= 374 && heartY + 300 <= 468 && heartY + 300 + 16 >= 405);
            if(survivalButtonCount % 6 == 0) {
                //If the heart is on the survival button
                if(onSurvival) {
                    //If the button is not full, fill it up more
                    if(survivalButtonRect < 60) {
                        survivalButtonRect += 5;
                        playSpear = true;
                        activateSpear = false;
                    }
                    else {
                        //If the spear animation has not been played while the button is full, play it
                        if(playSpear) {
                            activateSpear = true;
                            playSpear = false;
                        }
                        //The survival button is set to full, and the other buttons are set to not full
                        easyButtonFull = false;
                        hardButtonFull = false;
                        survivalButtonFull = true;
                        mediumButtonFull = false;
                    }
                }
                //If the heart is not on the survival button and the survival button isn't completely full but is filled to some extent, empty it
                else if(survivalButtonRect > 0 && !survivalButtonFull) {
                    survivalButtonRect -= 5;
                    playSpear = true;
                    activateSpear = false;
                }
            }
            if(++survivalButtonCount == 6)
                survivalButtonCount = 0;
            if(!survivalButtonFull)
                g.fillRect(80 - 37 + 148 + 39, 360 + 105 - survivalButtonRect, 140, survivalButtonRect);
            else {
                g.setColor(SURVIVAL_BUTTON_COLOR);
                g.fillRect(80 - 37 + 148 + 39, 300 + 105, 140, 60);
            }
        }
        else {
            survivalButtonRect = 0;
            survivalButtonFull = false;
        }
    }
    
    /**
     * Draws the red heart on the start screen (blue during the easter egg)
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void heartMouse(Graphics g) {
        if(heartsActivated() && !hitGround)
            g.drawImage(heartMouseBlue, heartX + 283, heartY + 298, null);
        else
            g.drawImage(heartMouse, heartX + 283, heartY + 298 + flickerChangeY, null);
    }
    
    /**
     * Draws the hard button fire animation
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void gifFire(Graphics g) {
        if(hardButtonFull) {
            if(++fireCounter % 4 == 0) {
                if(++fireFrame == 38)
                    fireFrame = 0;
                fireCounter = 0;
            }
            g.drawImage(fire[fireFrame], 432, 173, null);
        }
        else {
            fireFrame = 0;
            fireCounter = 0;
        }
    }
    
    /**
     * Draws the easy button annoying dog animation
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void gifDog(Graphics g) {
        if(easyButtonFull) {
            if(++dogCounter % 20 == 0) {
                if(dogFrame == 0)
                    dogFrame = 1;
                else
                    dogFrame = 0;
                dogCounter = 0;
            }
            g.drawImage(dog[dogFrame], 130 - 39, 261, null);
        }
    }
    
    /**
     * Draws the medium button greater dog animation
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void gifGreaterDog(Graphics g) {
        if(mediumButtonFull) {
            if(++greaterDogCount % 20 == 0) {
                
                /*
                 * There are three frames of the greater dog animation
                 *
                 * The set up is such that the frame will go 0, 1, 2, 1 then repeat
                 */
                if(greaterDogFrame != 1)
                    greaterDogFrame = 1;
                else {
                    greaterDogFrame += greaterDogDirection;
                    greaterDogDirection *= -1;
                }
                greaterDogCount = 0;
            }
            g.drawImage(greaterDog[greaterDogFrame], 259, 164, null);
        }
    }
    
    /**
     * Draws the spear and plays the sfx for the survival spear animation
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawSpear(Graphics g) {
        if(activateSpear) {
            Graphics2D g2d = (Graphics2D) g.create();
            startScreenTransform.setToIdentity();
            
            //If the spear hasn't materialized, the spear location will be set to the spear spawn, and the spear appear sfx plays
            if(!spearAppearPlayed) {
                spearLocation.setLocation(SPEAR_SPAWN);
                spearAppear.changeVolume(sfxVolume);
                spearAppear.play();
                spearAppearPlayed = true;
            }
            
            //Controls the spear materialization part of the animation
            if(spearOpacity <= 25) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, spearOpacity / 25f));
                startScreenTransform.translate(SPEAR_SPAWN.getX(), SPEAR_SPAWN.getY());
                if(++spearCounter % 2 == 0) {
                    ++spearOpacity;
                    spearCounter = 0;
                }
            }
            else if(!spearLocation.equals(SPEAR_END)) {
                
                //Plays the spear fly noise after the spear has materialized since it is now flying towards the survival button
                if(spearOpacity == 26) {
                    spearFly.changeVolume(sfxVolume);
                    spearFly.play();
                    ++spearOpacity;
                }
                
                //The spears location is constantly shifted by 5% of the distance between the spear's spawn location and end location
                spearLocation.setLocation(spearLocation.getX() + (SPEAR_END.getX() - SPEAR_SPAWN.getX()) / 20, spearLocation.getY() + (SPEAR_END.getY() - SPEAR_SPAWN.getY()) / 20);
                startScreenTransform.translate(spearLocation.getX(), spearLocation.getY());
            }
            else {
                //If the spear has just hit the survival button, the spear hit sound plays
                if(!spearHitPlayed) {
                    spearHit.changeVolume(sfxVolume);
                    spearHit.play();
                    spearHitPlayed = true;
                }
                startScreenTransform.translate(SPEAR_END.getX(), SPEAR_END.getY());
            }
            g2d.drawImage(spear, startScreenTransform, null);
            g2d.dispose();
        }
        else {
            //Resets the spear animation
            spearCounter = 0;
            spearOpacity = 0;
            spearAppearPlayed = false;
            spearHitPlayed = false;
        }
    }
    
    /**
     * Draws the arrows indicating the player to move the red heart
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawArrows(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        if(!heartMoved && arrowsShouldShow)
            g2d.drawImage(arrows, 271, 352, null);
        if(++arrowsCounter % 85 == 0) {
            arrowsShouldShow = !arrowsShouldShow;
            arrowsCounter = 0;
        }
        g2d.dispose();
    }
    
    /**
     * Draws the game mode buttons
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawButtons(Graphics g) {
        if(playChosen) {
            g.drawImage(buttons, 39, 300, null);
            g.drawImage(survival, 226, 405, null);
        }
    }
    
    /**
     * Draws the "Press X to go back" message on the game mode selection screen
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawBackMessage(Graphics g) {
        if(playChosen && !heartsActivated()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setFont(Runner.deteFontScore);
            g2d.setColor(Color.WHITE);
            String backMessage = "Press X to go back";
            g2d.drawString(backMessage, (Runner.getFrame().getWidth() - g.getFontMetrics(Runner.deteFontScore).stringWidth(backMessage)) / 2, 30);
        }
    }
    
    /**
     * Draws the bones used in the easter egg, controls the resetting of the easter egg, and initializes the flickering
     * animation
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawBones(Graphics g) {
        if(++boneCounter > 50) {
            //Plays the sound effect for when the bones are supposed to start showing
            if(!showBones) {
                boneSound.changeVolume(sfxVolume);
                boneSound.play();
                showBones = true;
            }
            if(boneCounter < 300) {
                //Draws the bones and stops megalovania once the easter egg should finish
                g.drawImage(bones, -11, boneY, null);
                if(boneCounter > 265 && !megalovania.isStopped())
                    megalovania.stop();
            }
            else {
                //Hides sans and resets the easter egg
                hideSans = true;
                resetEgg();
            }
            
            //Decreases the bone y-position to make them go up
            if(boneY > 510)
                boneY -= 3;
            
            //If the heart goes into the bones, the flickering animation starts
            if(heartY + 300 + 20 >= boneY && !flickering) {
                damage.changeVolume(sfxVolume);
                damage.play();
                flickering = true;
            }
        }
    }
    
    /**
     * Draws the sans animation for the easter egg
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawSans(Graphics g) {
        if(sansCount % 10 == 0 && (sansFrame < 4 || hitGround) && ++sansFrame > 10)
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
    
    /**
     * Draws the blue heart flashing animation on the start screen
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawBlueHeartFlash(Graphics g) {
        if(heartsActivated())
            blueHeartOpacity = 1f;
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, blueHeartOpacity));
        g2d.drawImage(blueHeartFlash, 163, 205, null);
        g2d.dispose();
        if(++blueHeartFlashCounter % 2 == 0 && blueHeartOpacity > 0.02f) {
            blueHeartOpacity -= 0.02f;
            blueHeartFlashCounter = 0;
        }
    }
    
    /**
     * Draws the game creators' names scrolling on the bottom of the start screen
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
    private void drawNames(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(Runner.deteFontScore);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Made by Nikunj Chawla and Aaron Kandikatla", nameStringX, 580);
        g2d.dispose();
        
        //Shifts the names to the left every two counts (except once the x-position hits -560, in which case it will restart from the right
        if(++nameStringCounter % 2 == 0) {
            if(--nameStringX == -560)
                nameStringX = 610;
            nameStringCounter = 0;
        }
    }
    
    /**
     * Controls the flickering of the red heart
     */
    private void flickeringHeart() {
        if(flickering) {
            if(++flickerCounter % 16 == 0) {
                if(flickerChangeY == 0)
                    flickerChangeY = 9000;
                else
                    flickerChangeY = 0;
                flickerCounter = 0;
            }
            if(--flickeringCountdown == 0) {
                flickering = false;
                flickeringCountdown = 75;
                flickerChangeY = 0;
            }
        }
    }
    
    /**
     * Resets all of the variables used during the easter egg
     */
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
    
    /**
     * Sets right to true if D or the right key is pressed and sets it to false when they are released
     *
     * @param right Whether at least one "right" key is being pressed down
     */
    void setRight(boolean right) {
        this.right = right;
    }
    
    /**
     * Sets left to true if A or the left key is pressed and sets it to false when they are released
     *
     * @param left Whether at least one "left" key is being pressed down
     */
    void setLeft(boolean left) {
        this.left = left;
    }
    
    /**
     * Sets up to true if W or the up key is pressed and sets it to false when they are released
     *
     * @param up Whether at least one "up" key is being pressed down
     */
    public void setUp(boolean up) {
        this.up = up;
    }
    
    /**
     * Sets down to true if S or the down key is pressed and sets it to false when they are released
     *
     * @param down Whether at least one "down" key is being pressed down
     */
    void setDown(boolean down) {
        this.down = down;
    }
    
    /**
     * Returns true if the hard button has been completely filled
     *
     * @return True if the hard button has been completely filled
     */
    boolean isHard() {
        return hardButtonFull;
    }
    
    /**
     * Returns true if the survival button has been completely filled
     *
     * @return True if the survival button has been completely filled
     */
    boolean isSurvival() {
        return survivalButtonFull;
    }
    
    /**
     * Returns true if the medium button has been completely filled
     *
     * @return True if the medium button has been completely filled
     */
    boolean isMedium() {
        return mediumButtonFull;
    }
    
    /**
     * Returns true if the red start screen heart is on the heart on the Undyne title
     *
     * @return True if the red start screen heart is on the heart on the Undyne title
     */
    boolean isOnHeartOne() {
        return heartX >= -33 && heartX <= -9 && heartY >= -100 && heartY <= -80;
    }
    
    /**
     * Returns true if the red start screen heart is on the left heart on the Absolute title
     *
     * @return True if the red start screen heart is on the left heart on the Absolute title
     */
    boolean isOnHeartTwo() {
        return heartX >= -186 && heartX <= -167 && heartY >= -106 && heartY <= -84;
    }
    
    /**
     * Returns true if the red start screen heart is on the right heart on the Absolute title
     *
     * @return True if the red start screen heart is on the right heart on the Absolute title
     */
    boolean isOnHeartThree() {
        return heartX >= -47 && heartX <= -19 && heartY >= -218 && heartY <= -190;
    }
    
    /**
     * Activates the Undyne title heart
     */
    void activateHeartOne() {
        heartsActivated[0] = true;
    }
    
    /**
     * Activates the left Absolute title heart
     */
    void activateHeartTwo() {
        heartsActivated[1] = true;
    }
    
    /**
     * Activates the right Absolute title heart
     */
    void activateHeartThree() {
        heartsActivated[2] = true;
    }
    
    /**
     * Deactivates all of the start screen title hearts
     */
    void deactivateHearts() {
        heartsActivated[0] = false;
        heartsActivated[1] = false;
        heartsActivated[2] = false;
    }
    
    /**
     * Returns true if the Undyne title heart is activated
     *
     * @return True if the Undyne title heart is activated
     */
    boolean heartOneActivated() {
        return heartsActivated[0];
    }
    
    /**
     * Returns true if the left Absolute title heart is activated
     *
     * @return True if the left Absolute title heart is activated
     */
    boolean heartTwoActivated() {
        return heartsActivated[1];
    }
    
    /**
     * Returns true if the right Absolute title heart is activated
     *
     * @return True if the right Absolute title heart is activated
     */
    boolean heartThreeActivated() {
        return heartsActivated[2];
    }
    
    /**
     * Returns true if all of the title hearts are activated
     *
     * @return True if all of the title hearts are activated
     */
    boolean heartsActivated() {
        return heartsActivated[0] && heartsActivated[1] && heartsActivated[2];
    }
    
    /**
     * Returns the number of hearts that are activated in the heartsActivated array
     *
     * @return The number of hearts that are activated in the heartsActivated array
     */
    int numHeartsActivated() {
        int activatedHearts = 0;
        for(boolean activated : heartsActivated) {
            if(activated)
                activatedHearts += 1;
        }
        return activatedHearts;
    }
    
    /**
     * Sets the opactity of the blue heart that flashes in the empty heart of the Absolute title to 100%
     */
    void activateBlueHeartFlash() {
        blueHeartOpacity = 1f;
    }
    
    /**
     * Resets StartScreen variables
     *
     * @param isReplaying True if the application is being replayed (current game mode is being replayed)
     */
    void resetVars(boolean isReplaying) {
        isLoaded = false;
        if(!isReplaying) {
            hardButtonFull = false;
            easyButtonFull = false;
            mediumButtonFull = false;
            survivalButtonFull = false;
        }
        
    }
    
    /**
     * Returns true if a game mode should start, which is if the heart is on a filled game mode button and the easter
     * egg isn't playing
     *
     * @return True if a game mode should start
     */
    boolean shouldStart() {
        return !heartsActivated() && (hardButtonFull && onHard || easyButtonFull && onEasy || survivalButtonFull && onSurvival || mediumButtonFull && onMedium);
    }
    
    /**
     * Returns true if the stage elements are showing
     *
     * @return True if the stage elements are showing
     */
    boolean shouldShow() {
        return frameCounter1 > 250;
    }
    
    /**
     * Sets the sfx volume for the start screen to the given value
     *
     * @param change The main sfx volume
     */
    static void changeSfxVol(double change) {
        sfxVolume = change;
    }
    
    /**
     * Changes the music volume for the start screen to the given value
     *
     * @param change The main music volume
     */
    void changeMusicVol(double change) {
        musicVolume = change;
        megalovania.changeVolume(change);
    }
    
    /**
     * Returns true if the start screen heart is on the credits button
     *
     * @return True if the start screen heart is on the credits button
     */
    boolean isOnLink() {
        return (heartX + 288 + 16 >= 76 && heartX + 288 <= 224 && heartY + 300 <= 442 + 20 && heartY + 300 + 16 >= 380 + 20 && !heartsActivated());
    }
    
    /**
     * Returns true if the start screen heart is on the help button
     *
     * @return True if the start screen heart is on the help button
     */
    boolean isOnHelp() {
        return (heartX + 288 + 16 >= 376 && heartX + 288 <= 524 && heartY + 300 <= 442 + 20 && heartY + 300 + 16 >= 380 + 20 && !heartsActivated());
    }
    
    /**
     * Returns true if the start screen heart is on the play button
     *
     * @return True if the start screen heart is on the play button
     */
    boolean isOnPlay() {
        return (heartX + 288 + 16 >= 76 && heartX + 288 <= 224 && heartY + 300 <= 342 + 20 && heartY + 300 + 16 >= 280 + 20 && !heartsActivated());
    }
    
    /**
     * Returns true if the start screen heart is on the attack creator button
     *
     * @return True if the start screen heart is on the attack creator button
     */
    boolean isOnCreator() {
        return (heartX + 288 + 16 >= 376 && heartX + 288 <= 524 && heartY + 300 <= 342 + 20 && heartY + 300 + 16 >= 280 + 20 && !heartsActivated());
    }
    
    /**
     * Activates the visual warning effect by setting the warning counter to a positive number
     */
    void warningOn() {
        warningCounter = 50;
    }
    
    /**
     * Returns the credit pop-up
     *
     * @return The credit pop-up
     */
    PopUp getCreditsList() {
        return creditsList;
    }
    
    /**
     * Plays the damage sfx
     */
    void playDamage() {
        damage.changeVolume(sfxVolume);
        damage.play();
    }
    
    /**
     * Sets the value of whether the play button has been chosen or not
     *
     * @param play True if play has been chosen and false otherwise
     */
    void playChosen(boolean play) {
        playChosen = play;
    }
    
    /**
     * Stops the survival button spear animation
     */
    void deactivateSpears() {
        activateSpear = false;
    }
    
    /**
     * Returns true if the play button has been selected
     *
     * @return True if the play button has been selected
     */
    boolean isPlayChosen() {
        return playChosen;
    }
    
    /**
     * Sets the x-position of the heart relative to the center of the screen
     *
     * @param x The x-position of the heart mouse
     */
    void setHeartX(int x) {
        heartX = x;
    }
    
    /**
     * Sets the y-position of the heart relative to the center of the screen
     *
     * @param y The y-position of the heart mouse
     */
    void setHeartY(int y) {
        heartY = y;
    }
    
    /**
     * Plays the click sfx
     */
    public static void playClick() {
        click.changeVolume(sfxVolume);
        click.play();
    }
    
}
