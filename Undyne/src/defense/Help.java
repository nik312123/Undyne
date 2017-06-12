package defense;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Help {
    
    private static int incrementer = 10;
    private static int frameCounter = 0;
    private final int  size = 450;
    static int dynamicSize = 0;
    private ArrayList<BufferedImage> helpGif = new ArrayList<BufferedImage>();
    static int counter = 0;
    
    public Help(){
        try {
            for(int i = 0; i <= 464; ++i) 
                helpGif.add(ImageIO.read(Runner.class.getResource("/help/frame" + i + ".png")));
        }
        catch(IOException e) {
            
            e.printStackTrace();
        }
    }
    
    
    public void initiate(Graphics g, boolean start){
        frameCounter++;
        if(start)
            grow();
        else
            shrink();
        g.setColor(Color.ORANGE);
        g.setFont(Runner.deteFontSpeech);
        if(dynamicSize>0){
            g.fillRect(300-(dynamicSize+10)/2, 300-(dynamicSize+10)/2, dynamicSize+10, dynamicSize+10);
            g.drawImage(helpGif.get(counter),300-dynamicSize/2, 300-dynamicSize/2,dynamicSize, dynamicSize, null);
        }
        g.setColor(Color.WHITE);
        g.drawString("PRESS X TO EXIT", 300-g.getFontMetrics().stringWidth("PRESS X TO EXIT")/2, 600-dynamicSize/4+37);
       
        if(frameCounter%3==0)
            counter++;
            if(counter==464)
                counter = 0;
         
        
    }
    
    public void grow(){
        if(dynamicSize <= size)
            dynamicSize+=incrementer;
    }
    
    public void shrink(){
        if(dynamicSize>0)
            dynamicSize-=incrementer*2;
    }
    
}
