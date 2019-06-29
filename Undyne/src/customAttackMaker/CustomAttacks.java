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
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomAttacks {
    /**
     * Used to check if user has selected an option at start screen
     */
    private static boolean optionSelected = false;
    
    /**
     * Used to check if user as selected import button
     */
    private static boolean importChosen = false;
    
    /**
     * Changes to true when importing process is completed
     */
    private static boolean importingComplete = false;
    
    /**
     * Changes to true if user selected import attacks and is browsing files
     */
    private static boolean fileBeingChosen = false;
    
    /**
     * Changes to true once the user has selected an option on Custom Attacks start screen
     */
    private static boolean isIn = false;
    
    /**
     * Changes to true if the error popup is closing (contracting)
     */
    private static boolean errorIsContracting = false;
    
    /**
     * Is true if there is an error when importing a file
     */
    private static boolean importingError = true;
    
    /**
     * Line at which there is an error in the imported file
     */
    private static int currentLine;
    
    /**
     * Color's alpha value of the newThing button background
     */
    private static int newThingAlpha = 0;
    
    /**
     * Color's alpha value of the import button background
     */
    private static int importThingAlpha = 0;
    
    /**
     * Scrolling position that changes when mouseWheelListener is triggered
     */
    static double scrollValue = 70;
    
    /**
     * The current y-position that changes for every element added to the attack creator UI
     */
    static int dynamicLength = 0;
    
    /**
     * The error message for when importing a file that doesn't adhere to the correct format
     */
    private static String error;
    
    /**
     * Used to build the error message for the error pop-up
     */
    private static StringBuilder errorBuilder = new StringBuilder();
    
    /**
     * Background color of the start screen buttons
     */
    private static final Color startScreenButtonsColor = new Color(157, 50, 100);
    
    /**
     * Add attack button
     */
    private static Rectangle addAttack = new Rectangle();
    
    /**
     * Screen screen new button
     */
    private static Rectangle newThing = new Rectangle(226, 211, 148, 63);
    
    /**
     * Start screen import file button
     */
    private static Rectangle importThing = new Rectangle(226, 326, 148, 63);
    
    /**
     * Position of the mouse
     */
    static Point mousePosition = new Point();
    
    /**
     * The {@code AttackBar}s in the attack creator
     */
    public static ArrayList<AttackBar> attacks = new ArrayList<>();
    
    /**
     * The bottom menu bar that contains several options for controlling certain aspects of the attack creator
     */
    private static BottomMenuBar bottomMenuBar = new BottomMenuBar();
    
    /**
     * The {@code PopUp} used to display errors for {@code CustomAttacks}
     */
    private static PopUp errorPopUp = new PopUp(170, 175, 260, 250, 15, Color.BLACK, Color.ORANGE, 5) {
        
        @Override
        public void afterDraw(Graphics g) {
            //If the error pop-up has been fully expanded
            if(percentageExpanded() == 1.0) {
                //Draws the line number of the error if applicable
                g.setFont(Runner.deteFontError);
                g.setColor(Color.WHITE);
                String titleMessage;
                if(importingError)
                    titleMessage = "Error on line " + currentLine;
                else
                    titleMessage = "Error";
                g.drawString(titleMessage, 10, 25);
                
                //Splits up the error message into words to put into the error pop-up
                String[] errorSplit = error.split("\\s+");
                errorBuilder.setLength(0);
                int lineIndex = 1;
                boolean drewOnLast = true;
                
                //Draws each line of the error message
                for(int i = 0; i < errorSplit.length; ++i) {
                    //If the error message part for the line has not hit the maximum value
                    if(errorBuilder.length() + errorSplit[i].length() + 1 < 19) {
                        drewOnLast = false;
                        errorBuilder.append(errorSplit[i]).append(" ");
                    }
                    
                    //Draws the line if the maximum value was reached and continues to the next line
                    else {
                        drewOnLast = true;
                        g.drawString(errorBuilder.toString(), 10, 25 + 15 + 25 * lineIndex);
                        ++lineIndex;
                        errorBuilder.setLength(0);
                        --i;
                    }
                }
                
                //If there is any text left to put on the pop-up, display it
                if(!drewOnLast)
                    g.drawString(errorBuilder.toString(), 10, 25 + 15 + 25 * lineIndex);
            }
        }
    };
    
    /**
     * Dialog used to import and export to and from the custom attack creator
     */
    private static JFileChooser chooser = new JFileChooser() {
        @Override
        public void approveSelection() {
            File f = getSelectedFile();
            
            //If the file chosen already exists, and we are attempting to save the file, present a dialog to ask if the user wishes to overwrite
            // the file
            if(f.exists() && getDialogType() == SAVE_DIALOG) {
                int result = JOptionPane.showConfirmDialog(this, "The file already exists. Overwrite?", "Existing File",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, Runner.warning);
                
                //If the user does not wish to overwrite the file, exit the method
                if(checkIfNegativeSelected(result))
                    return;
            }
            
            //If a file is being imported and there are arrows already existing in the editor, ask if the user wishes to overwrite the current
            // editor data
            else if(getDialogType() == OPEN_DIALOG && anyArrowsExist()) {
                int result = JOptionPane.showConfirmDialog(this, "Overwrite current editor data?", "Overwrite data", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, Runner.warning);
                
                //If the user does not wish to overwrite the editor dat, exit the method
                if(checkIfNegativeSelected(result))
                    return;
            }
            super.approveSelection();
        }
        
        /**
         * If the user chooses to cancel the dialog, clicks no, or clicks cancel, then a negative response was given, so true is returned. If
         * cancel is choosen, the dialog is also closed
         *
         * @param result The number that corresponds to the {@code JOptionPane} response given
         * @return True if the user gives a negative response to the given {@code JOptionPane}
         */
        private boolean checkIfNegativeSelected(int result) {
            switch(result) {
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                case JOptionPane.NO_OPTION:
                case JOptionPane.CLOSED_OPTION:
                    return true;
            }
            return false;
        }
    };
    
    /**
     * The filter used to ensure that the file chosen is a text file
     */
    private static final FileNameExtensionFilter TEXT_FILTER = new FileNameExtensionFilter("Text files", "txt");
    
    /**
     * Returns true if there are any {@code ArrowBar}s in the UI
     *
     * @return True if there are any {@code ArrowBar}s in the UI
     */
    private static boolean anyArrowsExist() {
        for(AttackBar attBar : attacks) {
            if(attBar.getArrows().size() > 0)
                return true;
        }
        return false;
    }
    
     //The static initializer block used to set the JFileChooser to only accept text files
    static {
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(TEXT_FILTER);
    }
    
    /**
     * The main paint method
     *
     * @param g2 Graphics object from Runner.java paintComponent
     */
    public void perform(Graphics g2) {
        JFrame frame = Runner.getFrame();
        Point absoluteMousePosition = MouseInfo.getPointerInfo().getLocation();
        mousePosition.setLocation(absoluteMousePosition.x - frame.getX(), absoluteMousePosition.y - frame.getY());
        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(isIn || optionSelected && (!importChosen || importingComplete)) {
            isIn = true;
            dynamicLength = (int) scrollValue;
            for(AttackBar attackBar : attacks)
                attackBar.draw(g, dynamicLength);
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
    
    /**
     * Custom Attack's start screen paint method
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
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
    
    /**
     * Adds a new {@code AttackBar} to the attack creator
     */
    private void addAttack() {
        attacks.add(new AttackBar());
    }
    
    /**
     * Reassigns the id numbers for each of the {@code AttackBar}s
     */
    private void reassignNumbers() {
        for(int i = 0; i < attacks.size(); ++i)
            attacks.get(i).setNumber(i);
    }
    
    /**
     * Draws the add attack button
     *
     * @param g The graphics object used for drawing the Runner JPanel
     */
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
    
    /**
     * Returns true if a given {@code String} only has characters '0'-'9'
     *
     * @param number The {@code String} to be tested
     * @return True if a given {@code String} only has characters '0'-'9'
     */
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
            PrintWriter p = null;
            try {
                p = new PrintWriter(saveLocation);
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
    
    /**
     * Returns true if there are any {@code AttackBar}s with no {@code ArrowBar}s
     *
     * @return True if there are any {@code AttackBar}s with no {@code ArrowBar}s
     */
    static boolean areAnyAttacksEmpty() {
        for(AttackBar attackBar : attacks) {
            if(attackBar.getArrows().size() == 0)
                return true;
        }
        return false;
    }
    
    /**
     * MouseWheelListener that changes scrollValue and is called in Runner.java
     *
     * @param e MouseWheelEvent
     */
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(isIn && Runner.isCustomAttack)
            scrollValue += e.getWheelRotation() * -1;
    }
    
    /**
     * Triggers when mouse is dragged and is called in Runner.java
     */
    public void mouseDragged() {
        if(isIn && Runner.isCustomAttack) {
            for(AttackBar a : attacks)
                a.mouseDragWork();
        }
    }
    
    /**
     * Triggers when mouse click is released and is called in Runner.java
     */
    public void mouseReleased() {
        for(AttackBar a : attacks)
            a.mouseReleased();
    }
    
    /**
     * Triggers when mouse is pressed and is called in Runner.java
     */
    public void mousePressed() {
        for(AttackBar a : attacks)
            a.mousePressed();
    }
    
    /**
     * Triggers when mouse is clicked and is called in Runner.java
     */
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
    
    /**
     * Sets the visibility of all of the {@code NumberField}s to the given value
     *
     * @param visibility The value to set the visibility of the {@code NumberField}s to
     */
    public void setAllFieldsVisibility(boolean visibility) {
        for(AttackBar at : attacks) {
            for(ArrowBar ab : at.getArrows()) {
                ab.setFieldsVisibility(visibility);
            }
        }
    }
    
    /**
     * Given whether the error was done in importing and the error message, presents the error pop-up
     *
     * @param importingError True if there's an importing error
     * @param message        The error message
     */
    public static void setError(boolean importingError, String message) {
        CustomAttacks.importingError = importingError;
        error = message;
        errorPopUp.setExpanding(true);
        errorPopUp.setVisible(true);
        StartScreen.playClick();
    }
    
    /**
     * Returns the error {@code PopUp}
     *
     * @return The error {@code PopUp}
     */
    public PopUp getErrorPopUp() {
        return errorPopUp;
    }
    
    /**
     * Returns the {@code AttackBar}s in the attack creator
     *
     * @return The {@code AttackBar}s in the attack creator
     */
    public ArrayList<AttackBar> getAttacks() {
        return attacks;
    }
    
    /**
     * Returns the bottom menu bar that contains several options for controlling certain aspects of the attack creator
     *
     * @return The bottom menu bar that contains several options for controlling certain aspects of the attack creator
     */
    public static BottomMenuBar getBottomMenuBar() {
        return bottomMenuBar;
    }
    
    /**
     * Returns true if the user selects an option on the {@code CustomAttacks} start screen
     *
     * @return true if the user selects an option on the {@code CustomAttacks} start screen
     */
    public static boolean isIn() {
        return isIn;
    }
    
    /**
     * Returns true if the user is currently choosing a file
     *
     * @return True if the user is currently choosing a file
     */
    public static boolean isFileBeingChosen() {
        return fileBeingChosen;
    }
    
    /**
     * Returns true if the user has selected any of the {@code ArrowBar}s' direction arrows
     *
     * @return True if the user has selected any of the {@code ArrowBar}s' direction arrows
     */
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
