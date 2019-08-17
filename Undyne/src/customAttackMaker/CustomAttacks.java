package customAttackMaker;

import defense.Runner;
import defense.StartScreen;
import nikunj.classes.PopUp;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
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
import java.util.ArrayDeque;
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
    
    private static int currentLine;
    private static int newThingAlpha = 0;
    private static int importThingAlpha = 0;
    static double scrollValue = 70;
    static int dynamicLength = 0;


    private static String error;
    
    private static StringBuilder errorBuilder = new StringBuilder();
    
    private static final Color startScreenButtonsColor = new Color(157, 50, 100);
    
    private static Rectangle addAttack = new Rectangle();
    private static Rectangle newThing = new Rectangle(226, 211, 148, 63);
    private static Rectangle importThing = new Rectangle(226, 326, 148, 63);
    
    static Point mousePosition = new Point();
    
    public static ArrayList<AttackBar> attacks = new ArrayList<>();
    
    private static BottomMenuBar bottomMenuBar = new BottomMenuBar();

    private static ScrollBar sb = new ScrollBar();
    
    private static PopUp errorPopUp = new PopUp(170, 175, 260, 250, 15, Color.BLACK, Color.ORANGE, 5) {
        
        @Override
        public void afterDraw(Graphics g) {
            if(percentageExpanded() == 1.0) {
                g.setFont(Runner.deteFontError);
                g.setColor(Color.WHITE);
                String titleMessage;
                if(importingError)
                    titleMessage = "Error on line " + currentLine;
                else
                    titleMessage = "Error";
                g.drawString(titleMessage, 10, 25);
                String[] errorSplit = error.split("\\s+");
                errorBuilder.setLength(0);
                int lineIndex = 1;
                boolean drewOnLast = true;
                for(int i = 0; i < errorSplit.length; ++i) {
                    if(errorBuilder.length() + errorSplit[i].length() + 1 < 19) {
                        drewOnLast = false;
                        errorBuilder.append(errorSplit[i]).append(" ");
                    }
                    else {
                        drewOnLast = true;
                        g.drawString(errorBuilder.toString(), 10, 25 + 15 + 25 * lineIndex);
                        ++lineIndex;
                        errorBuilder.setLength(0);
                        --i;
                    }
                }
                if(!drewOnLast)
                    g.drawString(errorBuilder.toString(), 10, 25 + 15 + 25 * lineIndex);
            }
        }
    };
    
    private static JFileChooser chooser = new JFileChooser() {
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
    
    private static final FileNameExtensionFilter TEXT_FILTER = new FileNameExtensionFilter("Text files", "txt");
    
    private static boolean anyArrowsExist() {
        for(AttackBar attBar : attacks) {
            if(attBar.getArrows().size() > 0)
                return true;
        }
        return false;
    }
    
    static {
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(TEXT_FILTER);
    }
    
    public CustomAttacks() {}
    
    public void perform(Graphics g2) {
        JFrame frame = Runner.getFrame();
        Point absoluteMousePosition = MouseInfo.getPointerInfo().getLocation();
        mousePosition.setLocation(absoluteMousePosition.x - frame.getX(), absoluteMousePosition.y - frame.getY());
        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(isIn || optionSelected && (!importChosen || importingComplete)) {
            isIn = true;
            dynamicLength = (int) scrollValue;
            int top = dynamicLength - 50;
            for(AttackBar attackBar : attacks)
                attackBar.draw(g, dynamicLength);
            addAttackButton(g);
            g.setColor(Color.RED);
            int bottom = dynamicLength;
            int length = bottom - top;
            sb.perform(g,length ,mousePosition);
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
            if(newThingAlpha < 5)
                newThingAlpha = 5;
        }
        g.setColor(startScreenButtonsColor);
        Composite original = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, newThingAlpha / 255.0f));
        g.fill(newThing);
        g.setComposite(original);
        if(importThing.contains(mousePosition) || fileBeingChosen) {
            importThingAlpha += 5;
            if(importThingAlpha > 255)
                importThingAlpha = 255;
        }
        else {
            if(importThingAlpha > 0)
                importThingAlpha -= 5;
            if(importThingAlpha < 5)
                importThingAlpha = 5;
        }
        g.setColor(startScreenButtonsColor);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, importThingAlpha / 255.0f));
        g.fill(importThing);
        g.setComposite(original);
        g.drawImage(Runner.cat, 129, 21, null);
        g.drawImage(Runner.newImage, 226, 211, null);
        g.drawImage(Runner.importImage, 226, 326, null);
        g.setFont(Runner.deteFontEditorAttack);
        g.setColor(Color.WHITE);
        String exit = "Press X to Exit";
        g.drawString(exit, 300 - g.getFontMetrics().stringWidth(exit) / 2, 550);

    }
    
    private void addAttack() {
        attacks.add(new AttackBar());
    }
    
    private void reassignNumbers() {
        for(int i = 0; i < attacks.size(); ++i)
            attacks.get(i).setNumber(i);
    }
    
    private void addAttackButton(Graphics g) {
        if(attacks.size() >= 13000)
            g.drawImage(Runner.addAttackDisabled, 300 - 33, dynamicLength - 5, null);
        else
            g.drawImage(Runner.addAttack, 300 - 33, dynamicLength - 5, null);
        addAttack.setBounds(300 - 33, dynamicLength - 5, 66, 17);
    }
    
    /**
     * Checks to make sure the given file and its contents have the proper formatting and imports it into the custom
     * attack creator
     *
     * @return The {@code ArrayList} of {@code AttackBar}s that are imported into the attack creator
     */
    private ArrayDeque<AttackBar> importFile() {
        StartScreen.playClick();
        ArrayDeque<AttackBar> importedAttacks = new ArrayDeque<>();
        
        //Opens the file chooser for the user to be able to select the file to import
        chooser.setDialogTitle("Choose file to import...");
        fileBeingChosen = true;
        int dialogChoice = chooser.showOpenDialog(null);
        fileBeingChosen = false;
        
        //If the user has selected a file and hit the option to approve the selected file for importing
        if(dialogChoice == JFileChooser.APPROVE_OPTION) {
            //The file will be imported using a Scanner
            Scanner s = null;
            try {
                s = new Scanner(new FileInputStream(chooser.getSelectedFile()));
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            //If the file was found
            if(s != null) {
                //The current line separated into an array of Strings by commas
                String[] inputArrow;
                
                //The number of the previous attack
                int previousAttack = -1;
                
                //The current line the Scanner is on
                currentLine = 0;
                
                //The error to be used for the error pop-up
                error = "";
                
                //Continues importing as long as the Scanner has lines left
                while(s.hasNextLine()) {
                    ++currentLine;
                    switch(currentLine) {
                        case 1:
                            //Skips the first line of input
                            s.nextLine();
                            break;
                        case 2:
                            //This value specifies whether Undyne should be in normal or Undying mode
                            String isGenocide = s.nextLine();
                            
                            /*
                             * Line 2 should only be a boolean
                             *
                             * If it isn't, the error pop-up is shown; else, the bottom menu bar Undying checkbox is set accordingly
                             */
                            if(!isGenocide.equals("true") && !isGenocide.equals("false")) {
                                error = "The Undying value must be true or false only";
                                startError();
                                return importedAttacks;
                            }
                            else
                                bottomMenuBar.setIsGenocideBoxChecked(Boolean.parseBoolean(isGenocide));
                            break;
                    }
                    if(currentLine > 2) {
                        //The input on the given line is split by commas
                        inputArrow = s.nextLine().split(",");
                        
                        /*
                         * The input can be one of the following:
                         * An arrow (four elements)
                         * An attack (two elements)
                         * A blank line
                         *
                         * If it is not one of the above, the error pop-up is shown
                         */
                        if(inputArrow.length != 4 && inputArrow.length != 2 && (inputArrow.length != 1 || !inputArrow[0].equals(""))) {
                            error = "Incorrect number of items in the given comma-separated list";
                            startError();
                            return importedAttacks;
                        }
                        
                        //If the length of the comma-separated input line is two, it should be an attack and tested as such
                        else if(inputArrow.length == 2) {
                            
                            /*
                             * The first element should be the attack number string in the format "a(num)" where (num) is an integer value
                             *
                             * If this is not the case, the error pop-up is shown
                             */
                            String attackNumString = inputArrow[0];
                            if(attackNumString.charAt(0) != 'a' || !stringIsNumber(attackNumString.substring(1))) {
                                error = "Attack must be represented as a(n) where (n) is a number";
                                startError();
                                return importedAttacks;
                            }
                            int attackNum = Integer.parseInt(inputArrow[0].substring(1));
    
                            //If the integer (num) attached to the attackNumString is negative, the error pop-up is shown
                            if(attackNum < 0) {
                                error = "Attack must be greater than zero";
                                startError();
                                return importedAttacks;
                            }

                            //If the integer (num) attached to the attackNumString is less than the previous attack number, the error pop-up is shown
                            else if(attackNum <= previousAttack) {
                                error = "Attacks must be monotonically increasing";
                                startError();
                                return importedAttacks;
                            }
                            else {
                                //If the integer (num) attached to the attackNumString is greater than 13000, the error pop-up is shown (max 13000 attacks)
                                if(attackNum >= 13000) {
                                    error = "Maximum number of attacks is 13000";
                                    startError();
                                    return importedAttacks;
                                }
                                
                                //If the attack number is more than simply one greater than the previous attack, empty {@code AttackBar}s should be added in between
                                if(attackNum > 1 + previousAttack) {
                                    for(int i = previousAttack + 1; i < attackNum; ++i) {
                                        AttackBar newBar = new AttackBar();
                                        newBar.setNumber(i);
                                        importedAttacks.add(newBar);
                                    }
                                }
                            }
    
                            /*
                             * The second element should be a boolean representing whether or not that attack should have a random orientation shift applied to it
                             *
                             * If this is not a boolean value, the error pop-up is shown
                             */
                            String orientationShiftString = inputArrow[1];
                            if(!orientationShiftString.equals("true") && !orientationShiftString.equals("false")) {
                                error = "Orientation shift can only be true or false";
                                startError();
                                return importedAttacks;
                            }
                            boolean orientationShift = Boolean.parseBoolean(orientationShiftString);
                            
                            //A new attack bar is added due to the new attack line
                            AttackBar newBar = new AttackBar();
                            newBar.setNumber(attackNum);
                            importedAttacks.add(newBar);
                            previousAttack = attackNum;
                            if(orientationShift)
                                newBar.switchOrientationShift();
                        }

                        //If the length of the comma-separated input line is four, it should be an arrow and tested as such
                        else if(inputArrow.length == 4) {
                            
                            //If speed is not a space (meaning empty value) or a valid integer between 1 and 10 inclusive, the error pop-up is shown
                            int speed;
                            try {
                                if(!inputArrow[0].equals(" ")) {
                                    speed = Integer.parseInt(inputArrow[0]);
                                    if(speed < 1 || speed > 10) {
                                        error = "Speed must be between 1 and 10 inclusive";
                                        startError();
                                        return importedAttacks;
                                    }
                                }
                                else
                                    speed = 0;
                            }
                            catch(NumberFormatException e) {
                                error = "Speed must be a valid integer";
                                startError();
                                return importedAttacks;
                            }
                            
                            //The string that represents whether the arrow is a reverse arrow should be a boolean value or the error-pop up is shown
                            String reversibleString = inputArrow[1];
                            if(!reversibleString.equals("true") && !reversibleString.equals("false")) {
                                error = "Second item in list must be true or false";
                                startError();
                                return importedAttacks;
                            }
                            boolean reversible = Boolean.parseBoolean(reversibleString);
                            
                            //If the direction input is not a character long or not equivalent to 'd', 'l', 'u', 'r', or 'n', the error pop-up is shown
                            char direction = inputArrow[2].charAt(0);
                            if(inputArrow[2].length() != 1 || direction != 'd' && direction != 'l' && direction != 'u' && direction != 'r' && direction != 'n') {
                                error = "Direction must be one character long and consist of one of the following characters: d, l, u, or r";
                                startError();
                                return importedAttacks;
                            }
    
                            //If speed is not a space (meaning empty value) or a valid integer between 1 and 999 inclusive, the error pop-up is shown
                            int delay;
                            if(!inputArrow[3].equals(" ")) {
                                try {
                                    delay = Integer.parseInt(inputArrow[3]);
                                }
                                catch(NumberFormatException e) {
                                    error = "Delay must be between a valid integer";
                                    startError();
                                    return importedAttacks;
                                }
                                if(delay < 1 || delay > 999) {
                                    error = "Delay must be between 1 and 999 inclusive";
                                    startError();
                                    return importedAttacks;
                                }
                            }
                            else
                                delay = 0;
                            
                            AttackBar currentAttack = importedAttacks.getLast();
                            
                            //The number of arrows in the current attack must not exceed 13000 or the error pop-up is shown
                            if(currentAttack.getArrows().size() > 13000) {
                                error = "Number of attacks must not exceed 13000";
                                startError();
                                return importedAttacks;
                            }
                            currentAttack.add(new ArrowBar(speed, reversible, direction, delay));
                        }
                    }
                }
                attacks = new ArrayList<>(importedAttacks);
                importingComplete = true;
            }
        }
        return null;
    }
    
    /**
     * Starts the error message
     */
    private void startError() {
        importingError = true;
        errorPopUp.setExpanding(true);
        errorPopUp.setVisible(true);
        StartScreen.playClick();
    }
    
    private void removeImported(ArrayDeque<AttackBar> imported) {
        for(AttackBar attBar : imported) {
            for(ArrowBar arrBar : attBar.getArrows())
                arrBar.removeFields();
        }
    }
    
    private boolean stringIsNumber(String number) {
        if(number.length() == 0)
            return false;
        for(char c : number.toCharArray()) {
            if('0' > c || c > '9')
                return false;
        }
        return true;
    }
    
    /**
     * Exports the attack creator attacks, arrows, and other options to a file
     */
    private void exportFile() {
        StartScreen.playClick();
        ArrayList<String> output = new ArrayList<>();
        
        //Adds text for the text at the top of a file to import
        output.add("Note: Editing the file may result in errors. Empty lines are acceptable. This (the first line) is fine for modification as it is ignored, but don't remove it because the first line is always skipped.");
        
        //Adds the Undying value to the lines to export for the text file
        output.add(String.valueOf(bottomMenuBar.isGenocideBoxChecked()));
        
        for(AttackBar attackBar : attacks) {
            
            //Adds the attack string and orientation shift value to the lines list to be exported
            output.add(String.format("%s,%b", "a" + attackBar.getNumber(), attackBar.isOrientationShift()));
            for(ArrowBar arrowBar : attackBar.getArrows()) {
                
                //Gets the values of each arrow bar to be exported
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
        
        //Gets the user's selected export location for the txt file
        chooser.setDialogTitle("Choose export location...");
        if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File saveLocation = chooser.getSelectedFile();
            
            //Makes sure that the text file ends with .txt or adds the extension if not
            if(!saveLocation.getName().endsWith(".txt"))
                saveLocation = new File(saveLocation.getAbsolutePath() + ".txt");
            PrintStream p = null;
            try {
                p = new PrintStream(saveLocation);
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            
            //Prints the text to the given file location
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
    }
    
    public void mouseDragged() {
        if(isIn && Runner.isCustomAttack) {
            sb.mouseDragged(mousePosition);
            for(AttackBar a : attacks)
                a.mouseDragWork();
        }
    }
    
    public void mouseReleased() {
        sb.mouseReleased(mousePosition);
        for(AttackBar a : attacks)
            a.mouseReleased();
    }
    
    public void mousePressed() {
        sb.mousePressed(mousePosition);
        for(AttackBar a : attacks)
            a.mousePressed();
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
                ArrayDeque<AttackBar> imported = importFile();
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
                ArrayDeque<AttackBar> imported = importFile();
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
