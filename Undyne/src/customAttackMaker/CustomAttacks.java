package customAttackMaker;

import defense.Runner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomAttacks {
    
    static ArrayList<AttackBar> attacks = new ArrayList<>();
    
    private Rectangle addAttack = new Rectangle();
    
    private JFileChooser chooser = new JFileChooser();
    
    static int scrollValue = 70;
    
    static int dynamicLength = 0;
    
    static Rectangle mouse = new Rectangle();
    
    public CustomAttacks() {
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Text files", "txt");
        chooser.setFileFilter(textFilter);
    }
    
    public void perform(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bg(g);
        dynamicLength = scrollValue;
        for(AttackBar attackBar : attacks)
            attackBar.draw(g, dynamicLength);
        addAttackButton(g);
    }
    
    private void addAttack() {
        attacks.add(new AttackBar());
    }
    
    private void reassignNumbers() {
        int i = 0;
        for(AttackBar a : CustomAttacks.attacks) {
            a.setNumber(i);
            ++i;
        }
    }
    
    private void addAttackButton(Graphics g) {
        g.drawImage(Runner.addAttack, 300 - 33, dynamicLength - 5, null);
        addAttack.setBounds(300 - 33, dynamicLength - 5, 66, 17);
    }
    
    private void bg(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 600);
    }
    
    private void importFile() {
        ArrayList<AttackBar> importedAttacks = new ArrayList<>();
        chooser.setDialogTitle("Choose file to import...");
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File openLocation = chooser.getSelectedFile();
            Scanner s = null;
            try {
                s = new Scanner(new FileInputStream(openLocation));
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            if(s != null) {
                String[] inputArrow;
                int previousAttack = -1;
                while(s.hasNextLine()) {
                    inputArrow = s.nextLine().split(",");
                    if(inputArrow.length != 0 && inputArrow.length != 5)
                        return;
                    else if(inputArrow.length == 5) {
                        try {
                            int attack = Integer.parseInt(inputArrow[0]);
                            if(attack < 0)
                                return;
                            if(attack > previousAttack) {
                                importedAttacks.add(new AttackBar());
                                previousAttack = attack;
                            }
                            int speed = Integer.parseInt(inputArrow[1]);
                            if(speed < 1 || speed > 10)
                                return;
                            if(!inputArrow[2].equals("true") && !inputArrow[2].equals("false"))
                                return;
                            boolean reversable = Boolean.parseBoolean(inputArrow[2]);
                            char direction = inputArrow[3].charAt(0);
                            if(inputArrow[3].length() != 1 && direction != 'd' && direction != 'l' && direction != 'u' && direction != 'r')
                                return;
                            int delay = Integer.parseInt(inputArrow[4]);
                            if(delay < 1 || delay > 999)
                                return;
                            importedAttacks.get(importedAttacks.size() -1).add(new ArrowBar(speed, reversable, direction, delay));
                        }
                        catch(NumberFormatException e) {
                            return;
                        }
                    }
                }
                attacks = new ArrayList<>(importedAttacks);
            }
        }
    }
    
    private void exportFile() {
        ArrayList<String> output = new ArrayList<>();
        for(AttackBar attackBar : attacks) {
            for(ArrowBar arrowBar : attackBar.getArrows())
                output.add(String.format("%d,%d,%b,%c,%d", attackBar.getNumber(), arrowBar.getSpeed(), arrowBar.getReversable(), arrowBar.getDirection(), arrowBar.getDelay()));
        }
        chooser.setDialogTitle("Choose export location...");
        if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File saveLocation = chooser.getSelectedFile();
            if(!saveLocation.getName().endsWith(".txt"))
                saveLocation = new File(saveLocation.getAbsolutePath() + ".txt");
            PrintStream p = null;
            try {
                p = new PrintStream(saveLocation);
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            if(p != null) {
                for(String s : output)
                    p.println(s);
            }
        }
    }
    
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_L:
                for(AttackBar a : attacks) {
                    System.out.println("Attack: " + a.getNumber());
                    System.out.println();
                    for(ArrowBar b : a.getArrows())
                        System.out.println(b);
                    System.out.println();
                    System.out.println();
                    System.out.println();
                }
                break;
            case KeyEvent.VK_E:
                exportFile();
                break;
            case KeyEvent.VK_I:
                importFile();
                break;
        }
        for(AttackBar a : attacks)
            a.keyBoardWork(e);
    }
    
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollValue += e.getWheelRotation() * -1;
    }
    
    public void mouseMoved(MouseEvent e) {}
    
    public void mouseDragged(MouseEvent e) {
        mouse.setBounds(e.getX(), e.getY(), 1, 1);
        for(AttackBar a : attacks)
            a.mouseDragWork();
    }
    
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseReleased(MouseEvent e) {
        for(AttackBar a : attacks)
            a.mouseReleased();
    }
    
    public void mousePressed(MouseEvent e) {
        mouse.setBounds(e.getX(), e.getY(), 1, 1);
        for(AttackBar a : attacks) {
            a.mousePressed();
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        mouse.setBounds(e.getX(), e.getY(), 1, 1);
        for(AttackBar a : attacks) {
            if(a.mouseClickWork() == 1) {
                reassignNumbers();
                break;
            }
        }
        if(mouse.intersects(addAttack))
            addAttack();
    }
    
    public void mouseExited(MouseEvent e) {}
}
