package defense;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Runner extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private int delay = 5;
    protected Timer timer;
    BufferedImage heart;
    
    public Runner(String s) {
        JFrame frame = new JFrame(s);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Runner bp = new Runner();
        frame.add(bp);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        Runner a = new Runner("Game");
    }
    
    public Runner() {
        timer = new Timer(delay, this);
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        drawBG(g);
        draw(g);
        drawCircle(g);
        drawHeart(g);
    }
    
    public void draw(Graphics g) {
        int size = 80;
        Color opaqueWhite = new Color(255, 255, 255, 150);
        g.setColor(opaqueWhite);
        g.drawRect(getWidth() / 2 - size / 2, getHeight() / 2 - size / 2, size, size);
        while (--size > 73) {
            size = size - 1;
            g.drawRect(getWidth() / 2 - size / 2, getHeight() / 2 - size / 2, size, size);
        }
    }
    
    public void drawBG(Graphics g) {
        Color almostBlack = new Color(20, 20, 20);
        g.setColor(almostBlack);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    public void drawHeart(Graphics g) {
        try {
            heart = ImageIO.read(new File("heart.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        int width = heart.getWidth();
        int height = heart.getHeight();
        g.drawImage(heart, (getWidth() / 2 - (width / 2) + 1), (getHeight() / 2 - height / 2), null);
    }
    
    public void drawCircle(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawOval(getWidth() / 2 - 25, getHeight() / 2 - 25, 50, 50);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
}
