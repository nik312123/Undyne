package defense;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class KeyboardAnimations {
    BufferedImage arrows;
    BufferedImage wasd;
    static int x = 40;
    static int y = -40;
   

    
    public KeyboardAnimations() {
        
        try {
            arrows = ImageIO.read(Runner.class.getResource("/arrowKeys.png"));
            
        }
        catch(IOException e) {
            
            e.printStackTrace();
        }
    }
    
    public void show(Graphics g, char dir) {
        g.setColor(Color.ORANGE);
        
        if(Runner.keyCounter > 0)
            switch(dir) {
                case 'd':
                    g.fillRect(97+x, 474+y, 55, 52);
                    break;
                case 'u':
                    g.fillRect(97+x, 410+y, 55, 52);
                    break;
                case 'r':
                    g.fillRect(167+x, 474+y, 55, 52);
                    break;
                case 'l':
                    g.fillRect(30+x, 474+y, 55, 52);
                    break;
                
            }
        Runner.keyCounter--;
        
        g.drawImage(arrows, 20+x, 400+y, 848 / 4, 542 / 4, null);
        
    }
    
}
