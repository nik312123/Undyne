package customAttackMaker;

import defense.Runner;
import nikunj.classes.PopUp;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
    
    static Rectangle mouse = new Rectangle();
    
    private PopUp errorPopUp = new PopUp(170, 175, 260, 250, 15, Color.BLACK, Color.ORANGE, 5);
    
    private String error;
    
    private int errorLine;
    static int scrollValue = 70;
    static int dynamicLength = 0;
    
    private JFileChooser chooser = new JFileChooser() {
        @Override
        public void approveSelection() {
            File f = getSelectedFile();
            if(f.exists() && getDialogType() == SAVE_DIALOG) {
                int result = JOptionPane.showConfirmDialog(this, "The file already exists. Overwrite?", "Existing File", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, Runner.warning);
                switch(result) {
                    case JOptionPane.NO_OPTION:
                        return;
                    case JOptionPane.CLOSED_OPTION:
                        return;
                    case JOptionPane.CANCEL_OPTION:
                        cancelSelection();
                        return;
                }
            }
            super.approveSelection();
        }
    };
    
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
        errorPopUp.draw(g);
        drawErrorText(g);
    }
    
    private void addAttack() {
        attacks.add(new AttackBar());
    }
    
    private void reassignNumbers() {
        int i = -1;
        for(AttackBar a : CustomAttacks.attacks)
            a.setNumber(++i);
    }
    
    private void addAttackButton(Graphics g) {
        g.drawImage(Runner.addAttack, 300 - 33, dynamicLength - 5, null);
        addAttack.setBounds(300 - 33, dynamicLength - 5, 66, 17);
    }
    
    private void bg(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 600);
    }
    
    private void drawErrorText(Graphics g) {
        if(errorPopUp.percentageExpanded() == 1.0) {
            g.setFont(Runner.deteFontNorm.deriveFont(20.0f));
            g.setColor(Color.WHITE);
            g.drawString("Error on line " + errorLine, 180, 200);
            String[] errorSplit = error.split("\\s+");
            StringBuilder line = new StringBuilder();
            int lineIndex = 1;
            boolean drewOnLast = true;
            for(int i = 0; i < errorSplit.length; ++i) {
                if(line.length() + errorSplit[i].length() + 1 < 19) {
                    drewOnLast = false;
                    line.append(errorSplit[i]).append(" ");
                }
                else {
                    drewOnLast = true;
                    g.drawString(line.toString(), 180, 200 + 15 + 25 * lineIndex);
                    ++lineIndex;
                    line = new StringBuilder();
                    --i;
                }
            }
            if(!drewOnLast)
                g.drawString(line.toString(), 180, 200 + 15 + 25 * lineIndex);
        }
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
                errorLine = 0;
                error = "";
                boolean isFirstLine = true;
                while(s.hasNextLine()) {
                    ++errorLine;
                    if(isFirstLine) {
                        s.nextLine();
                        isFirstLine = false;
                    }
                    inputArrow = s.nextLine().split(",");
                    if(inputArrow.length != 0 && inputArrow.length != 5) {
                        error = "Incorrect number of items in the given comma-separated list";
                        errorPopUp.setExpanding(true);
                        return;
                    }
                    else if(inputArrow.length == 5) {
                        try {
                            int attack = Integer.parseInt(inputArrow[0]);
                            if(attack < 0) {
                                error = "Attack must be greater than zero";
                                errorPopUp.setExpanding(true);
                                return;
                            }
                            if(attack < previousAttack) {
                                error = "Attacks must be in increasing order";
                                errorPopUp.setExpanding(true);
                                return;
                            }
                            if(attack > previousAttack) {
                                if(attack > 1 + previousAttack) {
                                    error = "Attack numbers can't increase more than one";
                                    errorPopUp.setExpanding(true);
                                    return;
                                }
                                AttackBar newBar = new AttackBar();
                                newBar.setNumber(attack);
                                importedAttacks.add(newBar);
                                previousAttack = attack;
                            }
                            int speed = Integer.parseInt(inputArrow[1]);
                            if(speed < 1 || speed > 10) {
                                error = "Speed must be between 1 and 10 inclusive";
                                errorPopUp.setExpanding(true);
                                return;
                            }
                            if(!inputArrow[2].equals("true") && !inputArrow[2].equals("false")) {
                                error = "Third item in list must be true or false";
                                errorPopUp.setExpanding(true);
                                return;
                            }
                            boolean reversable = Boolean.parseBoolean(inputArrow[2]);
                            char direction = inputArrow[3].charAt(0);
                            if(inputArrow[3].length() != 1 || direction != 'd' && direction != 'l' && direction != 'u' && direction != 'r') {
                                error = "Direction character must be of size one and consist of one of the following characters: d, l, u, or r";
                                errorPopUp.setExpanding(true);
                                return;
                            }
                            int delay = Integer.parseInt(inputArrow[4]);
                            if(delay < 1 || delay > 999) {
                                error = "Delay must be between 1 and 999 inclusive";
                                errorPopUp.setExpanding(true);
                                return;
                            }
                            importedAttacks.get(importedAttacks.size() - 1).add(new ArrowBar(speed, reversable, direction, delay));
                        }
                        catch(NumberFormatException e) {
                            error = "Attack number, speed, an delay must all be valid integers";
                            errorPopUp.setExpanding(true);
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
        output.add("Note: Editing the file may result in errors. Empty lines are acceptable. This (the first line) is fine for modification as it is ignored and would be still ignored if it were an arrow code.");
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
        if(errorPopUp.percentageExpanded() == 1.0)
            errorPopUp.setExpanding(false);
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
    
    public PopUp getErrorPopUp() {
        return errorPopUp;
    }
    
}
