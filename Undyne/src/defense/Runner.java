package defense;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Runner {
    
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame();
        mainWindow.setBackground(new Color(20, 20, 20));
        mainWindow.setSize(600, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setLocation(dim.width/2 - mainWindow.getSize().width/2, dim.height/2 - mainWindow.getSize().height/2);
    }
    
}
