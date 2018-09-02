package defense;

import nikunj.classes.PopUp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

class Help {
    private int frameCounter = 0;
    private int helpFrame = 0;
    
    private PopUp helpPopUp;
    
    private static BufferedImage[] helpGif = new BufferedImage[502];
    
    private static final AffineTransform helpTransform = new AffineTransform();
    
    Help() {
        if(Runner.isFirstTime) {
            for(int i = 0; i < 502; ++i)
                helpGif[i] = Runner.getCompatibleImage("/help/help" + i + ".png");
        }
        
        helpPopUp = new PopUp(65, 65, 470, 470, 46, Color.BLACK, Color.ORANGE, 5) {
            private static final long serialVersionUID = 1L;
            
            private final Rectangle popUpShape = new Rectangle(0, 0, 470, 470);
            
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
                if(Runner.windowNotFocused()) {
                    g.setColor(Runner.UNFOCUSED_COLOR);
                    Graphics2D g2d = (Graphics2D) g;
                    helpTransform.setToIdentity();
                    helpTransform.translate(getWidth() / 2.0 * (1 - percentageExpanded()), getHeight() / 2.0 * (1 - percentageExpanded()));
                    helpTransform.scale(percentageExpanded(), percentageExpanded());
                    Path2D.Double transformedShape = (Path2D.Double) helpTransform.createTransformedShape(popUpShape);
                    g2d.fill(transformedShape);
                }
            }
        };
        helpPopUp.setLayout(null);
    }
    
    void initiate(boolean start) {
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
