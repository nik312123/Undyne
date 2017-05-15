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
    
    private static char dir = 'u';
    
    private static String nothing = "bad time";
    private static String typed = "";
    private static String activated = "";
    
    private static double fadeStart = 0;
    
    private static int nothingCounter = 0;
    private final static int DELAY = 10;
    private static int breakCount = 0;
    private static int breakFrame = 0;
    private static int flickeringHeart = 0;
    private static int count = 0;
    private static int gifCount = 0;
    private static int gameOverCount = 0;
    private static int gameOverFrame = 0;
    private static int frameCounter = 0;
    private static int flashCount = 0;
    
    private static boolean isGenocide = false;
    private static boolean heartDone = false;
    private static boolean gameOverDone = false;
    private static boolean firstEnd = true;
    private static boolean secondEnd = true;
    private static boolean beginning = true;
    private static boolean automatic = false;
    private static boolean isGameOver = false;
    private static boolean switchFade = false;
    private static boolean allStopped = false;
    
    public static Timer timer;
    
    private static BufferedImage[] gif;
    private static BufferedImage[] heartBreak;
    private static BufferedImage[] gameOver;
    
    private static BufferedImage heart;
    private static BufferedImage replay;
    public static BufferedImage blueArr;
    public static BufferedImage redArr;
    public static BufferedImage reverseArr;
    
    private static NewerSound main;
    private static NewerSound gameDone;
    private static NewerSound startScreen;
    
    private static Attack a1;
    private static Attacks a;
    private static Font font;
    private StartScreen stage = new StartScreen();
    private static Player p = new Player();
    
    static JFrame frame;
    
    public Runner(String s) {
        frame = new JFrame(s);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Runner bp = new Runner();
        frame.add(bp);
        frame.addKeyListener(this);
        frame.setSize(600, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(600, 600);
        frame.setLocation(dim.width/2 - frame.getSize().width/2, dim.height/2 - frame.getSize().height/2);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }
    
    public static void main(String... args) throws IOException, UnsupportedAudioFileException, InterruptedException, LineUnavailableException, FontFormatException {
        Arrow.p = p;
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
        replay = ImageIO.read(new File("images/replay.png"));
        URL fontUrl = new URL("file:font/dete.otf");
        font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(12.0f);
        @SuppressWarnings("unused")
        Runner a = new Runner("Game");
        startScreen.play();
    }
    
    public Runner() {
        timer = new Timer(DELAY, this);
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
        if(!allStopped) {
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
                    else if(!gameOverDone)
                        gameOver(g);
                    else {
                        drawGameOver(g, gameOverFrame);
                        if(isGameOver)
                            drawReplay(g);
                    }
                }
            }
        }
        drawPoint(g);
        g.dispose();
    }
    
    private void automatic() {
        if(automatic && a1.getList().size() > 0) {
            ArrayList<Arrow> arrows = a1.getList();
            int distance = Integer.MAX_VALUE;
            char pointTowards = 'u';
            for(int i = 0; i < arrows.size(); ++i) {
                switch(arrows.get(i).getDir()) {
                    case 'l':
                        if(distance > arrows.get(i).getX() - 308) {
                            distance = arrows.get(i).getX() - 308;
                            pointTowards = 'r';
                        }
                        break;
                    case 'r':
                        if(distance > 261 - arrows.get(i).getX()) {
                            distance = 261 - arrows.get(i).getX();
                            pointTowards = 'l';
                        }
                        break;
                    case 'u':
                        if(distance > arrows.get(i).getY() - 295) {
                            distance = arrows.get(i).getY() - 295;
                            pointTowards = 'd';
                        }
                        break;
                    case 'd':
                        if(distance > 252 - arrows.get(i).getY()) {
                            distance = 252 - arrows.get(i).getY();
                            pointTowards = 'u';
                        }
                        break;
                }
            }
            dir = pointTowards;
        }
    }
    
    public void nothing() {
        if(typed.length() > nothing.length())
            typed = typed.substring(typed.length() - nothing.length(), typed.length());
        if(typed.length() == nothing.length()) {
            if(typed.equals((nothing))) {
                automatic = !automatic;
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
        if(gameOverFrame == 225)
            isGameOver = true;
    }
    
    public void drawSqu(Graphics g) {
        int size = 80;
        Color translucentWhite = new Color(255, 255, 255, 200);
        g.setColor(translucentWhite);
        g.drawRect(getWidth()/2 - size/2 + p.getElementPosition(), getHeight()/2 - size/2 + p.getElementPosition(), size, size);
        while(size > 73) {
            --size;
            g.drawRect(getWidth()/2 - size/2 + p.getElementPosition(), getHeight()/2 - size/2 + p.getElementPosition(), size, size);
        }
    }
    
    public void drawBG(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    public void drawHeart(Graphics g) {
        int width = 30;
        int height = 30;
        g.drawImage(heart, getWidth()/2 - width/2 + 1 + p.getElementPosition() + flickeringHeart, getHeight()/2 - height/2 + p.getElementPosition(), null);
    }
    
    public void drawCircle(Graphics g) {
        g.setColor(Color.GREEN);
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
    
    public void drawReplay(Graphics g) {
        float opacity = (float) fadeStart;
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(replay, 3, -40, null);
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
    
    public void drawPoint(Graphics g) {
        Graphics g2d = g;
        g2d.setColor(Color.RED);
        try {
            int xChange = 0;
            int yChange = 0;
            for(Arrow temp : a1.getList()) {
                if(temp.getDir() == 'u' || temp.getDir() == 'd') {
                    xChange = 16;
                    if(temp.getDir() == 'u')
                        yChange = 0;
                    else
                        yChange = 31;
                }
                else {
                    yChange = 17;
                    if(temp.getDir() == 'l')
                        xChange = 0;
                    else
                        xChange = 31;
                }
                g2d.drawOval(temp.getX() + xChange, temp.getY() + yChange, 1, 1);
            }
        }
        catch(NullPointerException | IndexOutOfBoundsException e) {}
        g2d.dispose();
    }
    
    public void restartApplication() {
        timer.stop();
        allStopped = true;
        stage.resetVars();
        a.resetVars();
        p.resetVars();
        a1.resetVars();
        gameDone.stop();
        dir = 'u';
        nothing = "bad time";
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
        isGenocide = false;
        heartDone = false;
        gameOverDone = false;
        firstEnd = true;
        secondEnd = true;
        beginning = true;
        automatic = false;
        isGameOver = false;
        switchFade = false;
        timer = null;
        gif = null;
        heartBreak = null;
        gameOver = null;
        heart = null;
        replay = null;
        main = null;
        gameDone = null;
        startScreen = null;
        a1 = null;
        a = null;
        font = null;
        stage = null;
        p = null;
        frame = null;
        System.gc();
        allStopped = false;
        stage = new StartScreen();
        p = new Player();
        try {
            Runner.main();
        }
        catch(IOException | UnsupportedAudioFileException | InterruptedException | LineUnavailableException
                | FontFormatException e) {
            e.printStackTrace();
        }
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
            case KeyEvent.VK_Z:
                if(beginning) {
                    if(stage.shouldStart()) {
                        isGenocide = stage.isHard();
                        a = new Attacks(isGenocide);
                        a1 = new Attack(new ArrayList<Arrow>(), p, a);
                        a.setAttack(a1);
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
                else if(isGameOver) {
                    restartApplication();
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
    
}
