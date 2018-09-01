package customAttackMaker;

import defense.Runner;
import defense.StartScreen;
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
    private static boolean errorIsContracting = false;
    private static boolean importingError = true;
    
    private static int errorLine;
    private static int newThingAlpha = 0;
    private static int importThingAlpha = 0;
    static double scrollValue = 70;
    static double dynamicLength = 0;
    
    private static String error;
    
    private static Rectangle addAttack = new Rectangle();
    private static Rectangle newThing = new Rectangle(226, 211, 148, 63);
    private static Rectangle importThing = new Rectangle(226, 326, 148, 63);
    
    static Point mousePosition = new Point();
    
    public static ArrayList<AttackBar> attacks = new ArrayList<>();
    
    private static BottomMenuBar bottomMenuBar = new BottomMenuBar();
    
    private static PopUp errorPopUp = new PopUp(170, 175, 260, 250, 15, Color.BLACK, Color.ORANGE, 5) {
        
        @Override
        public void afterDraw(Graphics g) {
            if(percentageExpanded() == 1.0) {
                g.setFont(Runner.deteFontError);
                g.setColor(Color.WHITE);
                String titleMessage;
                if(importingError)
                    titleMessage = "Error on line " + errorLine;
                else
                    titleMessage = "Error";
                g.drawString(titleMessage, 10, 25);
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
                        g.drawString(line.toString(), 10, 25 + 15 + 25 * lineIndex);
                        ++lineIndex;
                        line = new StringBuilder();
                        --i;
                    }
                }
                if(!drewOnLast)
                    g.drawString(line.toString(), 10, 25 + 15 + 25 * lineIndex);
            }
        }
    };
    
    private JFileChooser chooser = new JFileChooser() {
        @Override
        public void approveSelection() {
            File f = getSelectedFile();
            if(f.exists() && getDialogType() == SAVE_DIALOG) {
                int result = JOptionPane.showConfirmDialog(this, "The file already exists. Overwrite?", "Existing File", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, Runner.warning);
                if(checkIfNegativeSelected(result))
                    return;
            }
            else if(getDialogType() == OPEN_DIALOG && anyArrowsExist()) {
                int result = JOptionPane.showConfirmDialog(this, "Overwrite current editor data?", "Overwrite data", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, Runner.warning);
                if(checkIfNegativeSelected(result))
                    return;
            }
            super.approveSelection();
        }
        
        private boolean checkIfNegativeSelected(int result) {
            switch(result) {
                case JOptionPane.NO_OPTION:
                    return true;
                case JOptionPane.CLOSED_OPTION:
                    return true;
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return true;
            }
            return false;
        }
    };
    
    private boolean anyArrowsExist() {
        for(AttackBar attBar : attacks) {
            if(attBar.getArrows().size() > 0)
                return true;
        }
        return false;
    }
    
    public CustomAttacks() {
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Text files", "txt");
        chooser.setFileFilter(textFilter);
    }
    
    public void perform(Graphics g2) {
        JFrame frame = Runner.getFrame();
        Point absoluteMousePosition = MouseInfo.getPointerInfo().getLocation();
        mousePosition = new Point(absoluteMousePosition.x - frame.getX(), absoluteMousePosition.y - frame.getY());
        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(isIn || optionSelected && (!importChosen || importingComplete)) {
            isIn = true;
            dynamicLength = (int) scrollValue;
            for(AttackBar attackBar : attacks)
                attackBar.draw(g, (int) dynamicLength);
            addAttackButton(g);
            Runner.setTopBarVisibility(true);
            setAllFieldsVisibility(true);
        }
        else {
            Runner.setTopBarVisibility(false);
            startScreen(g);
            setAllFieldsVisibility(false);
        }
        if(errorIsContracting && errorPopUp.percentageExpanded() == 0.0) {
            errorPopUp.setVisible(false);
            errorIsContracting = false;
        }
        errorPopUp.checkVisibility();
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
        g.drawImage(Runner.cat, 129, 21, null);
        g.drawImage(Runner.newThing, 226, 211, null);
        g.drawImage(Runner.importThing, 226, 326, null);
        g.setFont(Runner.deteFontEditorAttack);
        g.setColor(Color.WHITE);
        String exit = "Press X to Exit";
        g.drawString(exit, 300 - g.getFontMetrics().stringWidth(exit) / 2, 550);
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
        if(attacks.size() >= 13000)
            g.drawImage(Runner.addAttackDisabled, 300 - 33, (int) dynamicLength - 5, null);
        else
            g.drawImage(Runner.addAttack, 300 - 33, (int) dynamicLength - 5, null);
        addAttack.setBounds(300 - 33, (int) dynamicLength - 5, 66, 17);
    }
    
    private ArrayList<AttackBar> importFile() {
        StartScreen.playClick();
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
                while(s.hasNextLine()) {
                    ++errorLine;
                    switch(errorLine) {
                        case 1:
                            s.nextLine();
                            break;
                        case 2:
                            String isGenocide = s.nextLine();
                            if(!isGenocide.equals("true") && !isGenocide.equals("false")) {
                                error = "The Undying value must be true or false only";
                                importingError = true;
                                errorPopUp.setExpanding(true);
                                errorPopUp.setVisible(true);
                                StartScreen.playClick();
                                return importedAttacks;
                            }
                            else
                                bottomMenuBar.setIsGenocideBoxChecked(Boolean.parseBoolean(isGenocide));
                            break;
                    }
                    if(errorLine > 2) {
                        inputArrow = s.nextLine().split(",");
                        if(inputArrow.length != 4 && inputArrow.length != 2 && (inputArrow.length != 1 || !inputArrow[0].equals(""))) {
                            error = "Incorrect number of items in the given comma-separated list";
                            importingError = true;
                            errorPopUp.setExpanding(true);
                            errorPopUp.setVisible(true);
                            StartScreen.playClick();
                            return importedAttacks;
                        }
                        else if(inputArrow.length == 2) {
                            String attackNumString = inputArrow[0];
                            if(attackNumString.charAt(0) != 'a' || !stringIsNumber(attackNumString.substring(1))) {
                                error = "Attack must be represented as a(n) where (n) is a number";
                                importingError = true;
                                errorPopUp.setExpanding(true);
                                errorPopUp.setVisible(true);
                                StartScreen.playClick();
                                return importedAttacks;
                            }
                            int attackNum = Integer.parseInt(inputArrow[0].substring(1));
                            if(attackNum < 0) {
                                error = "Attack must be greater than zero";
                                importingError = true;
                                errorPopUp.setExpanding(true);
                                errorPopUp.setVisible(true);
                                StartScreen.playClick();
                                return importedAttacks;
                            }
                            else if(attackNum < previousAttack) {
                                error = "Attacks must be in increasing order";
                                importingError = true;
                                errorPopUp.setExpanding(true);
                                errorPopUp.setVisible(true);
                                StartScreen.playClick();
                                return importedAttacks;
                            }
                            else if(attackNum > previousAttack) {
                                if(attackNum >= 13000) {
                                    error = "Maximum number of attacks is 13000";
                                    importingError = true;
                                    errorPopUp.setExpanding(true);
                                    errorPopUp.setVisible(true);
                                    StartScreen.playClick();
                                    return importedAttacks;
                                }
                                if(attackNum > 1 + previousAttack) {
                                    for(int i = previousAttack + 1; i < attackNum; ++i) {
                                        AttackBar newBar = new AttackBar();
                                        newBar.setNumber(i);
                                        importedAttacks.add(newBar);
                                    }
                                }
                            }
                            String orientationShiftString = inputArrow[1];
                            if(!orientationShiftString.equals("true") && !orientationShiftString.equals("false")) {
                                error = "Orientation shift can only be true or false";
                                importingError = true;
                                errorPopUp.setExpanding(true);
                                errorPopUp.setVisible(true);
                                StartScreen.playClick();
                                return importedAttacks;
                            }
                            boolean orientationShift = Boolean.parseBoolean(orientationShiftString);
                            AttackBar newBar = new AttackBar();
                            newBar.setNumber(attackNum);
                            importedAttacks.add(newBar);
                            previousAttack = attackNum;
                            if(orientationShift)
                                newBar.switchOrientationShift();
                        }
                        else if(inputArrow.length == 4) {
                            try {
                                int speed;
                                if(!inputArrow[0].equals(" ")) {
                                    speed = Integer.parseInt(inputArrow[0]);
                                    if(speed < 1 || speed > 10) {
                                        error = "Speed must be between 1 and 10 inclusive";
                                        importingError = true;
                                        errorPopUp.setExpanding(true);
                                        errorPopUp.setVisible(true);
                                        StartScreen.playClick();
                                        return importedAttacks;
                                    }
                                }
                                else
                                    speed = 0;
                                String reversableString = inputArrow[1];
                                if(!reversableString.equals("true") && !reversableString.equals("false")) {
                                    error = "Second item in list must be true or false";
                                    importingError = true;
                                    errorPopUp.setExpanding(true);
                                    errorPopUp.setVisible(true);
                                    StartScreen.playClick();
                                    return importedAttacks;
                                }
                                boolean reversable = Boolean.parseBoolean(reversableString);
                                char direction = inputArrow[2].charAt(0);
                                if(inputArrow[2].length() != 1 || direction != 'd' && direction != 'l' && direction != 'u' && direction != 'r' && direction != 'n') {
                                    error = "Direction character must be of size one and consist of one of the following characters: d, l, u, or r";
                                    importingError = true;
                                    errorPopUp.setExpanding(true);
                                    errorPopUp.setVisible(true);
                                    StartScreen.playClick();
                                    return importedAttacks;
                                }
                                int delay;
                                if(!inputArrow[3].equals(" ")) {
                                    delay = Integer.parseInt(inputArrow[3]);
                                    if(delay < 1 || delay > 999) {
                                        error = "Delay must be between 1 and 999 inclusive";
                                        importingError = true;
                                        errorPopUp.setExpanding(true);
                                        errorPopUp.setVisible(true);
                                        StartScreen.playClick();
                                        return importedAttacks;
                                    }
                                }
                                else
                                    delay = 0;
                                if(importedAttacks.size() > 13000) {
                                    error = "Number of attacks must not exceed 13000";
                                    importingError = true;
                                    errorPopUp.setExpanding(true);
                                    errorPopUp.setVisible(true);
                                    StartScreen.playClick();
                                    return importedAttacks;
                                }
                                importedAttacks.get(importedAttacks.size() - 1).add(new ArrowBar(speed, reversable, direction, delay));
                            }
                            catch(NumberFormatException e) {
                                error = "Attack number, speed, an delay must all be valid integers";
                                importingError = true;
                                errorPopUp.setExpanding(true);
                                errorPopUp.setVisible(true);
                                StartScreen.playClick();
                                return importedAttacks;
                            }
                        }
                    }
                }
                attacks = new ArrayList<>(importedAttacks);
                importingComplete = true;
            }
        }
        return null;
    }
    
    private void removeImported(ArrayList<AttackBar> imported) {
        for(AttackBar attBar : imported) {
            for(ArrowBar arrBar : attBar.getArrows())
                arrBar.removeFields();
        }
    }
    
    private boolean stringIsNumber(String number) {
        if(number.length() == 0)
            return false;
        for(char c : number.toCharArray()) {
            if('0' < c && c > '9')
                return false;
        }
        return true;
    }
    
    private void exportFile() {
        StartScreen.playClick();
        ArrayList<String> output = new ArrayList<>();
        output.add("Note: Editing the file may result in errors. Empty lines are acceptable. This (the first line) is fine for modification as it is ignored, but don't remove it because the first line is always skipped.");
        output.add(String.valueOf(bottomMenuBar.isGenocideBoxChecked()));
        for(AttackBar attackBar : attacks) {
            output.add(String.format("%s,%b", "a" + attackBar.getNumber(), attackBar.isOrientationShift()));
            for(ArrowBar arrowBar : attackBar.getArrows()) {
                int speed = arrowBar.getSpeed();
                int delay = arrowBar.getDelay();
                String speedString = Integer.toString(speed);
                String delayString = Integer.toString(delay);
                if(speed < 1 || speed > 10)
                    speedString = " ";
                if(delay < 1 || delay > 999)
                    delayString = " ";
                output.add(String.format("%s,%b,%c,%s", speedString, arrowBar.isReversible(), arrowBar.getDirection(), delayString));
            }
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
    
    public void mouseDragged() {
        for(AttackBar a : attacks)
            a.mouseDragWork();
    }
    
    public void mouseReleased() {
        for(AttackBar a : attacks)
            a.mouseReleased();
    }
    
    public void mousePressed() {
        for(AttackBar a : attacks) {
            a.mousePressed();
        }
    }
    
    public void mouseClicked() {
        if(errorPopUp.percentageExpanded() == 1.0) {
            errorPopUp.setExpanding(false);
            StartScreen.playClick();
            errorIsContracting = true;
        }
        else if(!optionSelected) {
            boolean newChosen = newThing.contains(mousePosition);
            importChosen = importThing.contains(mousePosition);
            if(newChosen)
                StartScreen.playClick();
            if(importChosen) {
                ArrayList<AttackBar> imported = importFile();
                if(imported != null)
                    removeImported(imported);
            }
            if(importingComplete)
                StartScreen.playClick();
            optionSelected = importChosen && importingComplete || newChosen;
        }
        else {
            int check = bottomMenuBar.mouseWorks(mousePosition);
            if(check == 1)
                exportFile();
            else if(check == 0) {
                ArrayList<AttackBar> imported = importFile();
                if(imported != null)
                    removeImported(imported);
            }
            else if(addAttack.contains(mousePosition) && attacks.size() < 13000) {
                StartScreen.playClick();
                addAttack();
            }
            else {
                for(AttackBar a : attacks) {
                    if(a.mouseClickWork() == 1) {
                        reassignNumbers();
                        break;
                    }
                }
            }
        }
    }
    
    public void setAllFieldsVisibility(boolean visibility) {
        for(AttackBar at : attacks) {
            for(ArrowBar ab : at.getArrows()) {
                ab.setFieldsVisibility(visibility);
            }
        }
    }
    
    public static void setError(boolean importingError, String message) {
        CustomAttacks.importingError = importingError;
        error = message;
        errorPopUp.setExpanding(true);
        errorPopUp.setVisible(true);
        StartScreen.playClick();
    }
    
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
    
    public static boolean isFileBeingChosen() {
        return fileBeingChosen;
    }
    
    public static boolean areAnyDirectionsSelected() {
        for(AttackBar at : attacks) {
            for(ArrowBar ab : at.getArrows()) {
                if(ab.isDirectionSelected())
                    return true;
            }
        }
        return false;
    }
    
}
