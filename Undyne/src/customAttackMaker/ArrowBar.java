package customAttackMaker;

import defense.Runner;
import nikunj.classes.NumberField;

import javax.swing.InputMap;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.awt.geom.AffineTransform;

public class ArrowBar {

    /**
     * The Y position of the Arrow Bar
     */
    private int y = 0;

    /**
     * The speed value of the arrow
     */
    private int speed;

    /**
     * The delay valye of the arrow
     */
    private int delay;

    /**
     * True if the direction button is selected
     */
    private boolean isDirectionSelected = false;

    /**
     * Representing if the drag icon is being pressed
     */
    private boolean pressed = false;

    /**
     * True if the reverse check box is ticked
     */
    private boolean reversible;

    /**
     * Represents the direction of the arrow. 'u' for up, 'd' for down, 'r' for right, and 'l' for left
     */
    private char direction;

    /**
     *  The color object used to draw text
     */
    private Color textColor = new Color(255, 198, 0);

    /**
     * The color object used for the foreground of number fields
     */
    private static final Color FOREGROUND = new Color(255, 196, 0);

    /**
     * The delete arrow button's rectangle used to set bounds for clicking
     */
    private Rectangle deleteArrowButton = new Rectangle();

    /**
     * The direction button's rectangle used to set bounds for clicking
     */
    private Rectangle directionRectangle = new Rectangle();
    private Rectangle orderIntersection = new Rectangle();

    /**
     * The drag icon button's rectangle used to set bounds for clicking
     */
    private Rectangle dragArrowIcon = new Rectangle();

    /**
     * The reverse arrow tick box's rectangle used to set bounds for clicking
     */
    private Rectangle reverseTickBox = new Rectangle();

    /**
     * The transform object used for the arrow in direction button
     */
    private static AffineTransform arrowBarTransform = new AffineTransform();

    /**
     * NumberField object for speed value
     */
    private NumberField speedField;

    /**
     * NumberField object for delay value
     */
    private NumberField delayField;
    
    private static final FocusListener NUMBER_FIELD_LISTENER = new FocusListener() {
        
        @Override
        public void focusGained(FocusEvent e) {
            NumberFieldFocus nf = (NumberFieldFocus) e.getSource();
            Runner.setFocusedField(nf);
            nf.setFocused(true);
        }
        
        @Override
        public void focusLost(FocusEvent e) {
            NumberFieldFocus nf = (NumberFieldFocus) e.getSource();
            nf.setFocused(false);
        }
    };

    /**
     * Arrow Bar initializer
     * @param speed         Int value representing the speed of the arrow
     * @param reversible    Boolean value representing if the arrow is reversed
     * @param direction     Char value representing the direction of the arrow
     * @param delay         Int value representing the delay between previous arrow and current arrow
     */
    ArrowBar(int speed, boolean reversible, char direction, int delay) {
        this.speed = speed;
        this.reversible = reversible;
        this.direction = direction;
        this.delay = delay;
        try {
            speedField = new NumberFieldFocus(2, NumberField.STATE_NORMAL, false) {
                
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if(Runner.windowNotFocused()) {
                        g.setColor(Runner.UNFOCUSED_COLOR);
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                }
            };
            
            delayField = new NumberFieldFocus(3, NumberField.STATE_NORMAL, false) {
                
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if(Runner.windowNotFocused()) {
                        g.setColor(Runner.UNFOCUSED_COLOR);
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                }
            };
            
            speedField.setFont(Runner.deteFontEditor);
            delayField.setFont(Runner.deteFontEditor);
            
            speedField.setForeground(FOREGROUND);
            delayField.setForeground(FOREGROUND);
            
            speedField.setBackground(Color.BLACK);
            delayField.setBackground(Color.BLACK);
            
            speedField.setCaretColor(Color.WHITE);
            delayField.setCaretColor(Color.WHITE);
            
            speedField.setHorizontalAlignment(JTextField.CENTER);
            delayField.setHorizontalAlignment(JTextField.CENTER);
            
            speedField.setHighlighter(null);
            delayField.setHighlighter(null);
            
            speedField.addFocusListener(NUMBER_FIELD_LISTENER);
            delayField.addFocusListener(NUMBER_FIELD_LISTENER);
            
            speedField.setBounds(AttackBar.ATTACKBAR_X + 6 + 183, y + 7, 34, 13);
            delayField.setBounds(AttackBar.ATTACKBAR_X + 6 + 277, y + 7, 34, 13);
            
            if(speed != 0)
                speedField.setText(Integer.toString(speed));
            if(delay != 0)
                delayField.setText(Integer.toString(delay));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        Runner.addComponent(speedField, 9);
        Runner.addComponent(delayField, 9);
    }

    /**
     * Manages focus for NumberFields
     */
    public class NumberFieldFocus extends NumberField {
        
        private boolean focused = false;
        
        NumberFieldFocus(int limit, int state, boolean negativesAllowed) throws IOException {
            super(limit, state, negativesAllowed);
        }
        
        void setFocused(boolean focused) {
            this.focused = focused;
        }
        
        public boolean getFocused() {
            return focused;
        }
    }

    /**
     * Remove the NumberField components
     */
    void removeFields() {
        Runner.removeComponent(speedField);
        Runner.removeComponent(delayField);
    }

    /**
     * Returns the y position of the arrowBar
     * @return Int value representing the y position of the arrowBar
     */
    int getY() {
        return y;
    }

    /**
     * Sets the y position of the arrowbar
     * @param y Int value representing the y position of the arrowBar
     */
    void setY(int y) {
        this.y = y;
    }

    /**
     * Sets pressed to true if arrowbar is pressed with the mouse
     * @param pressed boolean value representing if arrowbar is pressed with the mouse
     */
    void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
    
    Rectangle getOrderIntersection() {
        return orderIntersection;
    }

    /**
     * Main paint method
     * @param g Graphics Object
     * @param x X position of the arrowBar
     * @param y Y Position of the ArrowBar
     */
    void draw(Graphics g, int x, int y) {
        if(!pressed)
            this.y = y;
        speedField.setLocation(AttackBar.ATTACKBAR_X + 6 + 183, this.y + 7);
        delayField.setLocation(AttackBar.ATTACKBAR_X + 6 + 277, this.y + 7);
        g.setColor(Color.BLACK);
        g.fillRect(AttackBar.ATTACKBAR_X + 10 + 183, this.y + 8, 26, 12);
        g.fillRect(AttackBar.ATTACKBAR_X + 10 + 277, this.y + 8, 26, 12);
        orderIntersection.setBounds(x, this.y, 100, 10);
        g.drawImage(Runner.arrowImg, x, this.y, null);
        deleteArrowButton(g, x - 24, this.y + 5);
        drawDirection(g, x, this.y);
        reverseTickBox(g, x, this.y);
        dragArrowIcon(g, x, this.y);
        if(!speedField.getText().isEmpty())
            speed = Integer.parseInt(speedField.getText());
        else
            speed = 0;
        if(!delayField.getText().isEmpty())
            delay = Integer.parseInt(delayField.getText());
        else
            delay = 0;
        boolean anySelected = CustomAttacks.areAnyDirectionsSelected();
        if(anySelected) {
            InputMap im = (InputMap) UIManager.get("TextField.focusInputMap");
            inputMapKeyStrokeReplacement(im, "RIGHT", "none");
            inputMapKeyStrokeReplacement(im, "LEFT", "none");
            inputMapKeyStrokeReplacement(im, "UP", "none");
            inputMapKeyStrokeReplacement(im, "DOWN", "none");
        }
        else {
            InputMap im = (InputMap) UIManager.get("TextField.focusInputMap");
            inputMapKeyStrokeReplacement(im, "RIGHT", "caret-forward");
            inputMapKeyStrokeReplacement(im, "LEFT", "caret-backward");
            inputMapKeyStrokeReplacement(im, "UP", "caret-begin-line");
            inputMapKeyStrokeReplacement(im, "DOWN", "caret-end-line");
        }
        setFieldUsability(!anySelected);
    }

    /**
     * Handles keystrokes when entering values into numberFields
     * @param im            InputMap Object
     * @param keyStrokeName String value representing the keyStroke's name
     * @param actionMapKey  String Value representing the actionMapKey's name
     */
    private void inputMapKeyStrokeReplacement(InputMap im, String keyStrokeName, String actionMapKey) {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeName);
        im.put(keyStroke, actionMapKey);
    }

    /**
     * Drawing method for direction button
     * @param g Graphics Object
     * @param x X position of the direction button
     * @param y Y position of the direction button
     */
    private void drawDirection(Graphics g, int x, int y) {
        directionRectangle.setBounds(x + 97, y + 6, 18, 16);
        if(!isDirectionSelected || Runner.customAttacksCounter % 75 >= 30)
            setImage(g, x, y);
    }

    /**
     * Sets the correct image for the arrowBar's direction button
     * @param g Graphics Object
     * @param x X position of the image
     * @param y Y position of the Image
     */
    private void setImage(Graphics g, int x, int y) {
        boolean drawArrow = true;
        arrowBarTransform.setToIdentity();
        arrowBarTransform.translate(x + 103, y + 8);
        double angle = 0;
        switch(direction) {
            case 'u':
                angle = 0;
                break;
            case 'd':
                angle = Math.PI;
                break;
            case 'r':
                angle = Math.PI / 2;
                break;
            case 'l':
                angle = -Math.PI / 2;
                break;
            case 'n':
                drawArrow = false;
                break;
        }
        arrowBarTransform.rotate(angle, 3, 6);
        Graphics2D g2d = (Graphics2D) g;
        if(drawArrow)
            g2d.drawImage(Runner.customArrowDirection, arrowBarTransform, null);
        else {
            g2d.setColor(textColor);
            g2d.setFont(Runner.deteFontSpeech);
            g2d.drawString("R", x + 102, y + 18);
        }
    }

    /**
     * Drawing method for reverse tick box
     * @param g Graphics object
     * @param x X position of the reverse tick box
     * @param y Y position of the reverse tick box
     */
    private void reverseTickBox(Graphics g, int x, int y) {
        if(reversible)
            g.drawImage(Runner.ticked, x + 400 - 10, y + 8, null);
        reverseTickBox.setBounds(x + 400 - 10, y + 8, 13, 12);
    }

    /**
     * Returns isPressed
     * @return Boolean value
     */
    boolean isPressed() {
        return pressed;
    }

    /**
     * Drawing method for drag icon
     * @param g Graphics Object
     * @param x X position of the drag icon
     * @param y Y position of the drag icon
     */
    private void dragArrowIcon(Graphics g, int x, int y) {
        if(!pressed)
            dragArrowIcon.setBounds(x + 424, y + 8, 16, 8 + 3);
        g.drawImage(Runner.dragArrowIcon, dragArrowIcon.x, dragArrowIcon.y, null);
    }

    /**
     * Returns the drag icon Rectangle object
     * @return  Rectangle object
     */
    Rectangle getDragArrowIcon() {
        return dragArrowIcon;
    }

    /**
     * Sets the bound of the drag icon's rectangle
     * @param dragArrowIcon Rectangle objects
     */
    void setDragArrowIcon(Rectangle dragArrowIcon) {
        this.dragArrowIcon = dragArrowIcon;
    }

    /**
     * Drawing method for delete arrow button
     * @param g Graphics Object
     * @param x X position of delete arrow button
     * @param y Y position of delete arrow button
     */
    private void deleteArrowButton(Graphics g, int x, int y) {
        g.drawImage(Runner.deleteArrow, x, y, null);
        deleteArrowButton.setBounds(x, y, 19, 17);
    }

    /**
     * Returns the deleteArrowButton's Rectangle object
     * @return  Rectangle object
     */
    Rectangle getDeleteArrowButton() {
        return deleteArrowButton;
    }

    /**
     * Returns the reverse tick box's Rectangle
     * @return  Rectangle object
     */
    Rectangle getReverseTickBox() {
        return reverseTickBox;
    }

    /**
     * Sets directionSelected to false
     */
    public void directionSelectedFalse() {
        this.isDirectionSelected = false;
    }

    /**
     * Returns delay value
     * @return Int value representing the delay of the arrow
     */
    public int getDelay() {
        if(!delayField.getText().isEmpty())
            delay = Integer.parseInt(delayField.getText());
        else
            delay = 0;
        return delay;
    }

    /**
     * Returns the speed value
     * @return Int value representing the speed of the arrow
     */
    public int getSpeed() {
        if(!speedField.getText().isEmpty())
            speed = Integer.parseInt(speedField.getText());
        else
            speed = 0;
        return speed;
    }

    /**
     * sets the direction of the arrow
     * @param direction char value representing the direction of the arrow
     */
    public void setDirection(char direction) {
        this.direction = direction;
    }

    /**
     * Returns the direction of the arrow
     * @return Char value
     */
    public char getDirection() {
        return direction;
    }

    /**
     * Toggles the reversible boolean variable
     */
    void switchReversible() {
        reversible = !reversible;
    }

    /**
     * Returns reversible variable
     * @return  Boolean value
     */
    public boolean isReversible() {
        return reversible;
    }

    /**
     * Returns Direction buttons rectangle
     * @return Rectangle Object
     */
    Rectangle getDirectionRectangle() {
        return directionRectangle;
    }

    /**
     * Checks if direction button is selected
     * @return Boolean value representing if direction button is selected
     */
    public boolean isDirectionSelected() {
        return isDirectionSelected;
    }

    /**
     * Toggles isDirectionSelected
     */
    void switchDirectionIsSelected() {
        isDirectionSelected = !isDirectionSelected;
    }

    /**
     * Sets the visibility of the numberFields to true or false
     * @param visibility Boolean value representing whether visibility of numberFields
     */
    void setFieldsVisibility(boolean visibility) {
        speedField.setVisible(visibility);
        delayField.setVisible(visibility);
    }

    /**
     * Sets numberFields to be usable
     * @param usable True or false
     */
    private void setFieldUsability(boolean usable) {
        if(usable) {
            speedField.setEditable(true);
            speedField.setCaretColor(Color.WHITE);
            delayField.setEditable(true);
            delayField.setCaretColor(Color.WHITE);
        }
        else {
            speedField.setEditable(false);
            speedField.setCaretColor(Color.BLACK);
            delayField.setEditable(false);
            delayField.setCaretColor(Color.BLACK);
        }
    }

    /**
     * Checks if there are any empty numberFields
     * @return Boolean value
     */
    boolean emptyFieldExists() {
        return speedField.getText().isEmpty() || delayField.getText().isEmpty();
    }
    
    @Override
    public String toString() {
        return "{" + "speed = " + speed + ", delay = " + delay + ", reverse = " + reversible + ", direction = " + direction + '}';
    }
}

