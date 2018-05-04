package customAttackMaker;

import defense.Runner;
import nikunj.classes.PopUp;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
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
    private static boolean optionSelected = false;
    private static boolean importChosen = false;
    private static boolean importingComplete = false;
    private static boolean fileBeingChosen = false;
    private static boolean isIn = false;
    
    private int errorLine;
    private static int newThingAlpha = 0;
    private static int importThingAlpha = 0;
    static int scrollValue = 70;
    static int dynamicLength = 0;
    
    private String error;
    
    private Rectangle addAttack = new Rectangle();
    private static Rectangle newThing = new Rectangle(226, 211, 148, 63);
    private static Rectangle importThing = new Rectangle(226, 326, 148, 63);
    
    static Point mousePosition = new Point();
    
    static ArrayList<AttackBar> attacks = new ArrayList<>();
    
    private static BottomMenuBar bottomMenuBar = new BottomMenuBar();
    
    private PopUp errorPopUp = new PopUp(170, 175, 260, 250, 15, Color.BLACK, Color.ORANGE, 5);
    
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
        Point absoluteMousePosition = MouseInfo.getPointerInfo().getLocation();
        JFrame frame = Runner.getFrame();
        mousePosition = new Point(absoluteMousePosition.x - frame.getX(), absoluteMousePosition.y - frame.getY());
        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(isIn || optionSelected && (!importChosen || importingComplete)) {
            isIn = true;
            dynamicLength = scrollValue;
            for(AttackBar attackBar : attacks)
                attackBar.draw(g, dynamicLength);
            addAttackButton(g);
            drawTopBar(g);
        }
        else
            startScreen(g);
    }
    
    private void drawTopBar(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 28);
        g.setColor(Color.WHITE);
        g.drawLine(0, 28, 600, 28);
    }
    
    private void startScreen(Graphics2D g) {
        if(newThing.contains(mousePosition)) {
            newThingAlpha += 5;
            if(newThingAlpha > 255)
                newThingAlpha = 255;
        }
        else {
            if(newThingAlpha > 0)
                newThingAlpha -= 5;
            if(newThingAlpha < 0)
                newThingAlpha = 0;
        }
        g.setColor(new Color(157, 50, 100, newThingAlpha));
        g.fill(newThing);
        if(importThing.contains(mousePosition) || fileBeingChosen) {
            importThingAlpha += 5;
            if(importThingAlpha > 255)
                importThingAlpha = 255;
        }
        else {
            if(importThingAlpha > 0)
                importThingAlpha -= 5;
            if(importThingAlpha < 0)
                importThingAlpha = 0;
        }
        g.setColor(new Color(157, 50, 100, importThingAlpha));
        g.fill(importThing);
        g.drawImage(Runner.CAT, 129, 21, null);
        g.drawImage(Runner.newThing, 226, 211, null);
        g.drawImage(Runner.importThing, 226, 326, null);
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
        fileBeingChosen = true;
        int dialogChoice = chooser.showOpenDialog(null);
        fileBeingChosen = false;
        if(dialogChoice == JFileChooser.APPROVE_OPTION) {
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
                    if(inputArrow.length != 5 && (inputArrow.length != 1 || !inputArrow[0].equals(""))) {
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
                            else if(attack < previousAttack) {
                                error = "Attacks must be in increasing order";
                                errorPopUp.setExpanding(true);
                                return;
                            }
                            else if(attack > previousAttack) {
                                if(attack >= 13000) {
                                    error = "Maximum number of attacks is 13000";
                                    errorPopUp.setExpanding(true);
                                    return;
                                }
                                if(attack > 1 + previousAttack) {
                                    for(int i = previousAttack + 1; i < attack; ++i) {
                                        AttackBar newBar = new AttackBar();
                                        newBar.setNumber(i);
                                        importedAttacks.add(newBar);
                                    }
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
                importingComplete = true;
            }
        }
        else
            optionSelected = false;
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
        if(Runner.isCustomAttack) {
            for(AttackBar a : attacks)
                a.keyBoardWork(e);
        }
    }
    
    static boolean areAnyAttacksEmpty() {
        for(AttackBar attackBar : attacks) {
            if(attackBar.getArrows().size() == 0)
                return true;
        }
        return false;
    }
    
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollValue += e.getWheelRotation() * -1;
    }
    
    public void mouseMoved(MouseEvent e) {}
    
    public void mouseDragged(MouseEvent e) {
        for(AttackBar a : attacks)
            a.mouseDragWork();
    }
    
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseReleased(MouseEvent e) {
        for(AttackBar a : attacks)
            a.mouseReleased();
    }
    
    public void mousePressed(MouseEvent e) {
        for(AttackBar a : attacks) {
            a.mousePressed();
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        if(errorPopUp.percentageExpanded() == 1.0)
            errorPopUp.setExpanding(false);
        for(AttackBar a : attacks) {
            if(a.mouseClickWork() == 1) {
                reassignNumbers();
                break;
            }
        }
        if(addAttack.contains(mousePosition))
            addAttack();
        if(!optionSelected) {
            importChosen = importThing.contains(mousePosition);
            optionSelected = newThing.contains(mousePosition) || importChosen;
            if(importChosen)
                importFile();
        }
        int check = bottomMenuBar.mouseWorks(mousePosition);
        if(check == 1)
            exportFile();
        else if(check == 0)
            importFile();
    }
    
    public void mouseExited(MouseEvent e) {}
    
    public PopUp getErrorPopUp() {
        return errorPopUp;
    }
    
    public ArrayList<AttackBar> getAttacks() {
        return attacks;
    }
    
    public static BottomMenuBar getBottomMenuBar() {
        return bottomMenuBar;
    }
    
    public static boolean isIn() {
        return isIn;
    }
    
}
