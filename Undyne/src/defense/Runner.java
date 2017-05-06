package defense;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import nikunj.classes.NewerSound;

public class Runner extends JPanel implements ActionListener, KeyListener {
    
    private static final long serialVersionUID = 1L;
    
    static char dir = 'u';
    
    static final char[] DIRS = {'u', 'd', 'r', 'l'};
    
    static String nothing = "badtime";
    static String hit = "";
    static String typed = "";
    static String activated = "";
    
    static int nothingCounter = 0;
    static int move = 0;
    static int delay = 10;
    static int angle = 0;
    static int breakCount = 0;
    static int breakFrame = 0;
    static int flickeringHeart = 0;
    static int count = 0;
    static int gifCount = 0;
    static int currentDirection = 0;
    static int gameOverCount = 0;
    static int gameOverFrame = 0;
    static int subTitleMovement = 330;
    static int frameCounter = 0;
    static boolean isGenocide = false;
    static boolean runsGif = false;
    static boolean heartDone = false;
    static boolean gameOverDone = false;
    static boolean firstEnd = true;
    static boolean secondEnd = true;
    static boolean startEnter = false;
    static boolean beginning = true;
    static boolean automatic = false;
    
    protected Timer timer;
    
    static double fadeStartAdder = 1;
    
    static BufferedImage[] gif;
    static BufferedImage heart;
    static BufferedImage[] heartBreak;
    static BufferedImage[] gameOver;
    public static BufferedImage blueArr;
    public static BufferedImage redArr;
    public static BufferedImage reverseArr;
    
    private static NewerSound main;
    private static NewerSound gameDone;
    private static NewerSound startScreen;
    
    static Font font;
    
    Player p = new Player();
    Attack a1 = new Attack(new ArrayList<Arrow>(), 2, p);
    StartScreen stage = new StartScreen();
    
    public Runner(String s) {
        JFrame frame = new JFrame(s);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Runner bp = new Runner();
        frame.add(bp);
        frame.addKeyListener(this);
        frame.setSize(600, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(600, 600);
        frame.setLocation(dim.width/2 - frame.getSize().width/2, dim.height/2 - frame.getSize().height/2);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public static void main(String args[]) throws IOException, UnsupportedAudioFileException, InterruptedException,
            LineUnavailableException, FontFormatException {
        
        startScreen = new NewerSound("audio/WF.wav", true);
        heart = ImageIO.read(new File("images/heart.png"));
        heartBreak = new BufferedImage[49];
        for(int i = 0; i <= 48; ++i)
            heartBreak[i] = ImageIO.read(new File("images/gif/heartBreak" + i + ".png"));
        gameOver = new BufferedImage[226];
        for(int i = 0; i <= 225; ++i)
            gameOver[i] = ImageIO.read(new File("images/gif/gameOver" + i + ".png"));
        blueArr = ImageIO.read(new File("images/arrowB.png"));
        redArr = ImageIO.read(new File("images/arrowR.png"));
        reverseArr = ImageIO.read(new File("images/arrowRE.png"));
        URL fontUrl = new URL("file:font/dete.otf");
        font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(12.0f);
        @SuppressWarnings("unused")
        Runner a = new Runner("Game");
        startScreen.play();
        
    }
    
    public Runner() {
        timer = new Timer(delay, this);
        timer.start();
    }
    
    public void drawCheat(Graphics g) throws FontFormatException, IOException {
        if(automatic)
            activated = "Cheat Activated";
        else {
            activated = "";
            nothingCounter = 0;
        }
        
        Graphics2D g1 = (Graphics2D) g;
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g1.setFont(font);
        g1.setColor(Color.GREEN);
        if(!activated.equals(""))
            g1.drawString(activated.substring(0, nothingCounter), 0, 13);
        if(frameCounter % 7 == 0 && nothingCounter < activated.length())
            nothingCounter++;
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        ++frameCounter;
        if(frameCounter == 1000)
            frameCounter = 0;
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
        if(beginning) {
            stage.run(g);
            
            try {
                drawCheat(g);
            }
            catch(FontFormatException | IOException e1) {
                e1.printStackTrace();
            }
            
        }
        else {
            if(p.getHealth() != 0) {
                drawBG(g);
                
                try {
                    drawCheat(g);
                }
                catch(FontFormatException | IOException e1) {
                    e1.printStackTrace();
                }
                
                drawSqu(g);
                drawCircle(g);
                drawHeart(g);
                
                p.shield(g, dir);
                gif(g);
                try {
                    a1.spawnArrows(g, p);
                    p.drawHealth(g);
                    automatic();
                }
                catch(FontFormatException | IOException e) {
                    e.printStackTrace();
                }
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
                    gameDone = new NewerSound("audio/dt.wav", true);
                    gameDone.play();
                }
                else if(!gameOverDone) {
                    gameOver(g);
                }
                else {
                    drawGameOver(g, gameOverFrame);
                }
            }
        }
        
    }
    
    private void automatic() {
        if(automatic && a1.getList().size() > 0) {
            switch(a1.getList().get(0).getDir()) {
                case 'd':
                    dir = 'u';
                    break;
                case 'r':
                    dir = 'l';
                    break;
                case 'u':
                    dir = 'd';
                    break;
                case 'l':
                    dir = 'r';
                    break;
            }
        }
        
    }
    
    public void nothing() {
        
        if(typed.length() > nothing.length())
            typed = typed.substring(typed.length() - nothing.length(), typed.length());
        if(typed.length() == nothing.length()) {
            if(typed.equals((nothing))) {
                automatic = !automatic;
                if(automatic)
                    System.out.println("Cheat code activaed");
                else
                    System.out.println("Cheat code deactivaed");
                typed = typed.substring(0, typed.length() - nothing.length());
            }
            
        }
    }
    
    public void gif(Graphics g) {
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
                count = 1;
            else if(gifCount % gifChange == 0)
                ++count;
            ++gifCount;
            if(gifCount == gifChange)
                gifCount = 0;
        }
        else {
            if(count == maxCount)
                count = 1;
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
        Graphics2D g2d = (Graphics2D) g.create();
        float opacity = 0.5f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        if(isGenocide)
            g2d.drawImage(gif[count], 198 + p.getElementPosition(), 10 + p.getElementPosition(), null);
        else
            g2d.drawImage(gif[count], 189 + p.getElementPosition(), 10 + p.getElementPosition(), null);
        g2d.dispose();
    }
    
    public boolean breakHeartException(int breakFrame) {
        int[] exceptions = {2, 6, 8, 12, 14, 18, 20, 22, 23};
        for(int exception : exceptions) {
            if(breakFrame == exception)
                return true;
        }
        return false;
    }
    
    public void makeBreakHeart(Graphics g, int breakFrame) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = heartBreak[breakFrame].getWidth();
        int height = heartBreak[breakFrame].getHeight();
        g2d.drawImage(heartBreak[breakFrame], getWidth()/2 - width/2 + 11, getHeight()/2 - height/2 + 78, null);
        g2d.dispose();
    }
    
    public void breakHeart(Graphics g) {
        ++breakCount;
        boolean exception = breakHeartException(breakFrame);
        if(breakCount % 4 == 0 && breakCount != 0 && !exception) {
            ++breakFrame;
            if(breakFrame == 25) {
                NewerSound split = new NewerSound("audio/split.wav", false);
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
                        if(breakFrame == 9) {
                            NewerSound broke = new NewerSound("audio/heartBreak.wav", false);
                            broke.play();
                        }
                        breakCount = 0;
                    }
            }
        }
        makeBreakHeart(g, breakFrame);
    }
    
    public void gameOver(Graphics g) {
        ++gameOverCount;
        if(gameOverCount % 4 == 0 && gameOverCount != 0) {
            ++gameOverFrame;
            switch(gameOverFrame) {
                case 68:
                case 70:
                case 73:
                case 75:
                case 76:
                case 78:
                case 80:
                case 81:
                case 85:
                case 86:
                case 88:
                case 93:
                case 95:
                case 96:
                case 98:
                case 138:
                case 140:
                case 141:
                case 143:
                case 145:
                case 146:
                case 148:
                case 163:
                case 165:
                case 166:
                case 168:
                case 171:
                case 173:
                case 175:
                case 176:
                case 178:
                case 180:
                case 181:
                case 183:
                case 185:
                case 186:
                case 188:
                case 190:
                case 191:
                    try {
                        NewerSound asgore = new NewerSound("audio/asgore.wav", false);
                        asgore.play();
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            if(gameOverFrame == 225)
                gameOverDone = true;
            gameOverCount = 0;
        }
        drawGameOver(g, gameOverFrame);
    }
    
    public void drawGameOver(Graphics g, int gameOverFrame) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = gameOver[gameOverFrame].getWidth();
        int height = gameOver[gameOverFrame].getHeight();
        g2d.drawImage(gameOver[gameOverFrame], getWidth()/2 - width/2 + 1, getHeight()/2 - height/2, null);
        g2d.dispose();
    }
    
    public void drawSqu(Graphics g) {
        int size = 80;
        Color translucentWhite = new Color(255, 255, 255, 200);
        g.setColor(translucentWhite);
        g.drawRect(getWidth()/2 - size/2 + p.getElementPosition(),
                getHeight()/2 - size/2 + p.getElementPosition(), size, size);
        while(size > 73) {
            --size;
            g.drawRect(getWidth()/2 - size/2 + p.getElementPosition(),
                    getHeight()/2 - size/2 + p.getElementPosition(), size, size);
        }
    }
    
    public void drawBG(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    public void drawHeart(Graphics g) {
        int width = 30;
        int height = 30;
        g.drawImage(heart, getWidth()/2 - width/2 + 1 + p.getElementPosition() + flickeringHeart,
                getHeight()/2 - height/2 + p.getElementPosition(), null);
    }
    
    public void drawCircle(Graphics g) {
        Color clr = new Color(0, 255, 0);
        g.setColor(clr);
        g.drawOval(getWidth()/2 - 25 + p.getElementPosition(), getHeight()/2 - 25 + p.getElementPosition(), 50, 50);
    }
    
    public void subTitle(Graphics g) {
        try {
            heart = ImageIO.read(new File("/Users/64009455/Documents/undertaleSub.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        g.translate(300, 300);
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(-6), heart.getMinX() + heart.getWidth()/2, heart.getMinY() + heart.getHeight()/2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        heart = op.filter(heart, null);
        g.translate(-300, -300);
        g.drawImage(heart, 300, 300, null);
        
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
                dir = 'u';
                stage.setUp();
                break;
            case KeyEvent.VK_DOWN:
                dir = 'd';
                stage.setDown();
                break;
            case KeyEvent.VK_RIGHT:
                dir = 'r';
                stage.setRight();
                break;
            case KeyEvent.VK_LEFT:
                dir = 'l';
                stage.setLeft();
                break;
            case KeyEvent.VK_ENTER:
                if(beginning) {
                    if(stage.hasSelected()) {
                        isGenocide = stage.isHard();
                        int gifMax;
                        String baseName;
                        if(isGenocide) {
                            main = new NewerSound("audio/bath.wav", true);
                            gifMax = 79;
                            baseName = "undying";
                        }
                        else {
                            main = new NewerSound("audio/soj.wav", true);
                            gifMax = 31;
                            baseName = "frame";
                        }
                        gif = new BufferedImage[gifMax + 1];
                        try {
                            for(int i = 0; i <= gifMax; ++i)
                                gif[i] = ImageIO.read(new File("images/gif/" + baseName + i + ".png"));
                        }
                        catch(IOException err) {
                            err.printStackTrace();
                        }
                        beginning = false;
                        startScreen.stop();
                        main.play();
                        dir = 'u';
                    }
                }
                break;
            
        }
        if(!(e.getKeyChar() == '￿')) { 
                                      
            typed += e.getKeyChar();
        }
        
        nothing();
        
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
                stage.setUpf();
                break;
            case KeyEvent.VK_DOWN:
                stage.setDownf();
                break;
            case KeyEvent.VK_RIGHT:
                stage.setRightf();
                break;
            case KeyEvent.VK_LEFT:
                stage.setLeftf();
                break;
        }
    }
    
}
