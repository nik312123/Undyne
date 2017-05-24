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
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
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
import nikunj.classes.GradientButton;

public class Runner extends JPanel implements ActionListener, KeyListener {
    
    private static final long serialVersionUID = 1L;
    
    private static char dir = 'u';
    
    private static String nothing = "bad time";
    private static String typed = "";
    private static String activated = "";
    
    private static double fadeStart = 0;
    
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
    private static boolean isFirstTime = true;
    
    private static Timer timer;
    
    private static BufferedImage[] gif;
    private static BufferedImage[] heartBreak;
    private static BufferedImage[] gameOver;
    
    private static BufferedImage heart;
    private static BufferedImage replay;
    private static BufferedImage close;
    private static BufferedImage draggable;
    public static BufferedImage blueArr;
    public static BufferedImage redArr;
    public static BufferedImage reverseArr;
        
    private static NewerSound main;
    private static NewerSound gameDone;
    private static NewerSound startScreen;
    
    private static GradientButton closeButton;
    private static GradientButton draggableButton;
    
    private static Attack a1;
    private static Attacks a;
    private static Font font;
    private static StartScreen stage = new StartScreen();
    private static Player p = new Player();
    
    private static JFrame frame;
    
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
        close = ImageIO.read(new File("images/close.png"));
        draggable = ImageIO.read(new File("images/draggable.png"));
        URL fontUrl = new URL("file:font/dete.otf");
        font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(12.0f);
        @SuppressWarnings("unused")
        Runner a = new Runner("Undyne: Absolute");
        startScreen.play();
    }
    
    public Runner(String s) {
        frame = new JFrame(s);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Runner bp = new Runner();
        bp.setBounds(0, 0, 600, 600);
        frame.add(bp);
        closeButton = new GradientButton(close, Color.BLACK, Color.RED, 2, 2, 24, 24) {
            private static final long serialVersionUID = 1L;

            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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
        frame.add(closeButton);
        frame.add(draggableButton);
        frame.addKeyListener(this);
        frame.setSize(600, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(600, 600);
        frame.setLocation(dim.width/2 - frame.getWidth()/2, dim.height/2 - frame.getHeight()/2);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
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
            g1.drawString(activated.substring(0, nothingCounter), 0, 13 + 30);
        if(frameCounter % 7 == 0 && nothingCounter < activated.length())
            nothingCounter++;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if(!allStopped) {
            super.paintComponent(g);
            if(++alwaysOnTopCounter >= 20) {
                alwaysOnTopCounter = 20;
                frame.setAlwaysOnTop(false);
            }
            ++frameCounter;
            if(frameCounter == 1000)
                frameCounter = 0;
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
                        try {
                            gameDone = new NewerSound("audio/dt.wav", true);
                        }
                        catch(UnsupportedAudioFileException | IOException e) {
                            e.printStackTrace();
                        }
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
        closeButton.draw(g);
        draggableButton.draw(g);
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
        Graphics2D g2d = (Graphics2D) g.create();
        float opacity = 0.3f;
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
                NewerSound split;
                try {
                    split = new NewerSound("audio/split.wav", false);
                    split.play();
                }
                catch(UnsupportedAudioFileException | IOException e) {
                    e.printStackTrace();
                }
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
                            NewerSound broke;
                            try {
                                broke = new NewerSound("audio/heartBreak.wav", false);
                                broke.play();
                            }
                            catch(UnsupportedAudioFileException | IOException e) {
                                e.printStackTrace();
                            }
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
            if(gameOverFrame % 2 == 0 && (gameOverFrame > 67 && gameOverFrame < 99 || gameOverFrame > 137 && gameOverFrame < 149 || gameOverFrame > 162 && gameOverFrame < 192)) {
                    try {
                        NewerSound asgore = new NewerSound("audio/asgore.wav", false);
                        asgore.play();
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
            }
            if(gameOverFrame >= 225)
                gameOverDone = true;
            gameOverCount = 0;
        }
        drawGameOver(g, gameOverFrame);
    }
    
    public void drawGameOver(Graphics g, int gameOverFrame) {
        Graphics2D g2d = (Graphics2D) g.create();
        if(gameOverFrame > 225)
            gameOverFrame = 225;
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
        while(size > 72) {
            g.drawRect(getWidth()/2 - size/2 + p.getElementPosition(), getHeight()/2 - size/2 + p.getElementPosition(), size, size);
            --size;
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
        isFirstTime = false;
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
            main();
        }
        catch(IOException | UnsupportedAudioFileException | InterruptedException | LineUnavailableException | FontFormatException e) {
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
                        a1 = new Attack(new ArrayList<Arrow>(), a);
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
                            try {
                                main = new NewerSound("audio/bath.ogg", true);
                            }
                            catch(UnsupportedAudioFileException | IOException e1) {
                                e1.printStackTrace();
                            }
                            gifMax = 79;
                            baseName = "undying";
                        }
                        else {
                            try {
                                main = new NewerSound("audio/soj.wav", true);
                            }
                            catch(UnsupportedAudioFileException | IOException e1) {
                                e1.printStackTrace();
                            }
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
                else if(!secondEnd && !isGameOver && !isFirstTime) {
                    gameOverFrame = 225;
                    isGameOver = true;
                }
                else if(isGameOver && !allStopped)
                    restartApplication();
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
    
}
