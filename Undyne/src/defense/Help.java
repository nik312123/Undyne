package defense;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import nikunj.classes.PopUp;

class Help {
    private int frameCounter = 0;
    private int helpFrame = 0;
    
    private PopUp helpPopUp;
    
    private static BufferedImage[] helpGif = new BufferedImage[739];
    
    Help() {
        if(Runner.isFirstTime) {
            try {
                for(int i = 0; i <= 738; ++i)
                    helpGif[i] = ImageIO.read(Runner.class.getResource("/help/help" + i + ".jpg"));
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        helpPopUp = new PopUp(65, 65, 470, 470, 46, Color.BLACK, Color.ORANGE, 5);
        helpPopUp.setVisible(true);
    }
    
    void initiate(Graphics g, boolean start) {
        if(start)
            helpPopUp.setExpanding(true);
        else {
            helpPopUp.setExpanding(false);
            frameCounter = 0;
            helpFrame = 0;
        }
        int heightWidth = Math.min(helpPopUp.getWidth(), 460);
        g.setFont(Runner.deteFontSpeech);
        if(helpPopUp.getExpanding() || helpPopUp.getWidth() > 0) {
            helpPopUp.draw(g);
            g.drawImage(helpGif[helpFrame], 300 - heightWidth / 2, 300 - heightWidth / 2, heightWidth, heightWidth, null);
        }
        g.setColor(Color.WHITE);
        if(helpPopUp.getWidth() == helpPopUp.getExpandedWidth())
            g.drawString("PRESS X TO EXIT", 300 - g.getFontMetrics().stringWidth("PRESS X TO EXIT") / 2, 600 - heightWidth / 4 + 37);
        if(++frameCounter % 3 == 0) {
            ++helpFrame;
            frameCounter = 0;
        }
        if(helpFrame == 739)
            helpFrame = 0;
    }
    
}
