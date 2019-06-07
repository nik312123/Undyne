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
    
    /**
     * The counter used to increment helpFrame every certain number of ticks
     */
    private int helpFrameCounter = 0;
    
    /**
     * The animation frame number the helpGif is on
     */
    private int helpFrame = 0;
    
    /**
     * The pop-up used to display the help animation
     */
    private PopUp helpPopUp;
    
    /**
     * The animation used in the help pop-up to show the user how to play when a game mode is chosen
     */
    private static BufferedImage[] helpGif = new BufferedImage[502];
    
    /**
     * The {@code AffineTransform} used to draw the unfocused color on the help pop-up when the application window is
     * not focused
     */
    private static final AffineTransform helpTransform = new AffineTransform();
    
    /**
     * The constructor used to initialize the help pop-up and animation
     */
    Help() {
        
        //Gets all of the images for the help animation
        if(Runner.isFirstTime) {
            for(int i = 0; i < 502; ++i)
                helpGif[i] = Runner.getCompatibleImage("/help/help" + i + ".png");
        }
        
        //Initializes the help pop-up
        helpPopUp = new PopUp(65, 65, 470, 470, 46, Color.BLACK, Color.ORANGE, 5) {
            private static final long serialVersionUID = 1L;
            
            /**
             * The shape of the expanded help pop-up
             */
            private final Rectangle HELP_POP_UP_SHAPE = new Rectangle(0, 0, 470, 470);
            
            @Override
            public void mouseClicked(MouseEvent e) {}
            
            @Override
            public void afterDraw(Graphics g) {
                int heightWidth = Math.min(helpPopUp.getPopUpWidth(), 460);
                g.setFont(Runner.deteFontSpeech);
                helpPopUp.checkVisibility();
                
                //If the help pop-up can be seen, draw the help animation
                if(helpPopUp.getExpanding() || helpPopUp.percentageExpanded() > 0)
                    g.drawImage(helpGif[helpFrame], 235 - heightWidth / 2, 235 - heightWidth / 2, heightWidth, heightWidth, null);
                g.setColor(Color.WHITE);
                
                //Draws PRESS X TO EXIT once the pop-up is expanded
                if(helpPopUp.percentageExpanded() == 1.0) {
                    String xToExit = "PRESS X TO EXIT";
                    g.drawString(xToExit, 235 - g.getFontMetrics().stringWidth(xToExit) / 2, 535 - heightWidth / 4 + 37);
                }
                
                //Increments the help frame every time the help counter is equal
                if(++helpFrameCounter % 3 == 0) {
                    ++helpFrame;
                    helpFrameCounter = 0;
                }
                
                //Resets the help frame once it reaches the max frame
                if(helpFrame == helpGif.length)
                    helpFrame = 0;
                
                //If the Runner window isn't focused, draw the unfocused color on the help pop-up
                if(Runner.windowNotFocused()) {
                    g.setColor(Runner.UNFOCUSED_COLOR);
                    Graphics2D g2d = (Graphics2D) g;
                    helpTransform.setToIdentity();
                    helpTransform.translate(getWidth() / 2.0 * (1 - percentageExpanded()), getHeight() / 2.0 * (1 - percentageExpanded()));
                    helpTransform.scale(percentageExpanded(), percentageExpanded());
                    Path2D.Double transformedShape = (Path2D.Double) helpTransform.createTransformedShape(HELP_POP_UP_SHAPE);
                    g2d.fill(transformedShape);
                }
            }
        };
        
        helpPopUp.setLayout(null);
    }
    
    /**
     * Sets the help pop-up to show or hide based on the given start boolean
     *
     * @param start Shows help pop-up if true and hides it if false
     */
    void initiate(boolean start) {
        if(start) {
            helpPopUp.setExpanding(true);
            helpPopUp.setVisible(true);
        }
        else {
            helpPopUp.setExpanding(false);
            helpFrameCounter = 0;
            helpFrame = 0;
        }
    }
    
    /**
     * Returns the help pop-up
     *
     * @return The help pop-up
     */
    PopUp getHelpPopUp() {
        return helpPopUp;
    }
    
    /**
     * Returns true if the help pop-up is expanding
     *
     * @return True if the help pop-up is expanding
     */
    boolean getExpanding() {
        return helpPopUp.getExpanding();
    }
    
}
