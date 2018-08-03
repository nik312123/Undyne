package defense;

import nikunj.classes.PopUp;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

class Help {
    private int frameCounter = 0;
    private int helpFrame = 0;

    private PopUp helpPopUp;

    private static BufferedImage[] helpGif = new BufferedImage[502];

    Help() {
        if (Runner.isFirstTime) {
            try {
                for (int i = 0; i < 502; ++i) {
                    helpGif[i] = ImageIO.read(Runner.class.getResource("/help/help" + i + ".png"));
                    helpGif[i] = Runner.getCompatibleImage(helpGif[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        helpPopUp = new PopUp(65, 65, 470, 470, 46, Color.BLACK, Color.ORANGE, 5) {
            private static final long serialVersionUID = 1L;

            @Override
            public void mouseClicked(MouseEvent e) {}
    
            @Override
            public void afterDraw(Graphics g) {
                int heightWidth = Math.min(helpPopUp.getPopUpWidth(), 460);
                g.setFont(Runner.deteFontSpeech);
                helpPopUp.checkVisibility();
                if(helpPopUp.getExpanding() || helpPopUp.percentageExpanded() > 0)
                    g.drawImage(helpGif[helpFrame], 235 - heightWidth / 2, 235 - heightWidth / 2, heightWidth, heightWidth, null);
                g.setColor(Color.WHITE);
                if(helpPopUp.percentageExpanded() == 1.0)
                    g.drawString("PRESS X TO EXIT", 235 - g.getFontMetrics().stringWidth("PRESS X TO EXIT") / 2, 535 - heightWidth / 4 + 37);
                if(++frameCounter % 3 == 0) {
                    ++helpFrame;
                    frameCounter = 0;
                }
                if(helpFrame == 502)
                    helpFrame = 0;
            }
        };
        helpPopUp.setLayout(null);
    }

    void initiate(Graphics g, boolean start) {
        if(start) {
            helpPopUp.setExpanding(true);
            helpPopUp.setVisible(true);
        }
        else {
            helpPopUp.setExpanding(false);
            frameCounter = 0;
            helpFrame = 0;
        }
    }

    PopUp getHelpPopUp() {
        return helpPopUp;
    }

}
