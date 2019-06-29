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
     * The y-position of the Arrow Bar
     */
    private int y = 0;
    
    /**
     * The speed value of the arrow
     */
    private int speed;
    
    /**
     * The delay value of the arrow
     */
    private int delay;
    
    /**
     * True if the direction for the {@code ArrowBar} is selected
     */
    private boolean directionSelected = false;
    
    /**
     * Represents if the drag icon is being pressed down
     */
    private boolean pressed = false;
    
    /**
     * True if the reverse checkbox is ticked
     */
    private boolean reversible;
    
    /**
     * Represents the direction of the arrow
     */
    private char direction;
    
    /**
     * The color used for drawing the "R" text when the "random" direction is chosen
     */
    private Color textColor = new Color(255, 198, 0);
    
    /**
     * The color object used for the foreground of {@code NumberField}s
     */
    private static final Color FOREGROUND = new Color(255, 196, 0);
    
    /**
     * The delete arrow button's rectangle used for clicking bounds
     */
    private Rectangle deleteArrowButton = new Rectangle();
    
    /**
     * The direction button's rectangle used for clicking bounds
     */
    private Rectangle directionRectangle = new Rectangle();
    private Rectangle orderIntersection = new Rectangle();
    
    /**
     * The drag icon button's rectangle used for clicking bounds
     */
    private Rectangle dragArrowIcon = new Rectangle();
    
    /**
     * The reverse arrow checkbox's rectangle used for clicking bounds
     */
    private Rectangle reverseTickBox = new Rectangle();
    
    /**
     * The transform object used for the arrow in direction icon
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
    
    /**
     * The {@code FocusListener} that tracks which {@code NumberField} is currently focused
     */
    private static final FocusListener NUMBER_FIELD_LISTENER = new FocusListener() {
        
        /**
         * If a {@code NumberField} has gained focus, add a glow around it, and let it know that it is focused
         *
         * @param e The {@code FocusEvent} that indicates that a {@code NumberField} has gained focus
         */
        @Override
        public void focusGained(FocusEvent e) {
            NumberFieldFocus nf = (NumberFieldFocus) e.getSource();
            Runner.setFocusedField(nf);
            nf.setFocused(true);
        }
    
        /**
         * If a {@code NumberField} has lost focus, let it know that it has lost focus
         *
         * @param e The {@code FocusEvent} that indicates that a {@code NumberField} has lost focus
         */
        @Override
        public void focusLost(FocusEvent e) {
            NumberFieldFocus nf = (NumberFieldFocus) e.getSource();
            nf.setFocused(false);
        }
    };
    
    /**
     * Arrow Bar initializer
     *
     * @param speed      Int value representing the speed of the arrow
     * @param reversible Boolean value representing if the arrow is reversed
     * @param direction  Char value representing the direction of the arrow
     * @param delay      Int value representing the delay between previous arrow and current arrow
     */
    ArrowBar(int speed, boolean reversible, char direction, int delay) {
        this.speed = speed;
        this.reversible = reversible;
        this.direction = direction;
        this.delay = delay;
        
        //Initializes the speed and delay NumberFields and adds them to the Runner JPanel
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
     * A subclass of {@code NumberField} that adds knowledge of whether or not the {@code NumberField} has focus
     */
    public class NumberFieldFocus extends NumberField {
    
        /**
         * True if the {@code NumberFieldFocus} has focus
         */
        private boolean focused = false;
    
        /**
         * Initializes a {@code NumberFieldFocus}
         *
         * @param limit The character limit for the {@code NumberFieldFocus} field
         * @param state Whether the {@code NumberFieldFocus} should allow decimals or just integers
         * @param negativesAllowed True if the {@code NumberFieldFocus} should allow negative numbers
         * @throws IOException Thrown if the limit given is less than zero
         */
        NumberFieldFocus(int limit, int state, boolean negativesAllowed) throws IOException {
            super(limit, state, negativesAllowed);
        }
    
        /**
         * Sets whether or not this {@code NumberFieldFocus} has focus
         * @param focused True if the {@code NumberFieldFocus} has focus
         */
        void setFocused(boolean focused) {
            this.focused = focused;
        }
    
        /**
         * Returns true if the {@code NumberFieldFocus} has focus
         * @return True if the {@code NumberFieldFocus} has focus
         */
        public boolean getFocused() {
            return focused;
        }
    }
    
    /**
     * Remove the NumberField components from the Runner JPanel
     */
    void removeFields() {
        Runner.removeComponent(speedField);
        Runner.removeComponent(delayField);
    }
    
    /**
     * Returns the y-position of the {@code ArrowBar}
     *
     * @return The y-position of the {@code ArrowBar}
     */
    int getY() {
        return y;
    }
    
    /**
     * Sets the y-position of the {@code ArrowBar}
     *
     * @param y The y-position of the {@code ArrowBar}
     */
    void setY(int y) {
        this.y = y;
    }
    
    /**
     * Sets pressed to true if the drag icon of the {@code ArrowBar} is pressed with the mouse
     *
     * @param pressed True if the drag icon of the {@code ArrowBar} is pressed with the mouse
     */
    void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
    
    Rectangle getOrderIntersection() {
        return orderIntersection;
    }
    
    /**
     * Draws the {@code ArrowBar} and its corresponding components
     *
     * @param g The graphics object used for drawing the Runner JPanel
     * @param x The x-position of the {@code ArrowBar}
     * @param y The y-position of the {@code ArrowBar}
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
     * Changes what a certain keystroke does for the given {@code InputMap}
     *
     * @param im            The {@code InputMap} for a certain component
     * @param keyStrokeName The name of the keystroke for which the action is being changed
     * @param actionMapKey  The action that is being mapped to the given keystroke
     */
    private void inputMapKeyStrokeReplacement(InputMap im, String keyStrokeName, String actionMapKey) {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeName);
        im.put(keyStroke, actionMapKey);
    }
    
    /**
     * Sets location of the direction rectangle bounds and draws the image if it is not selected or if it is selected and matches the counter
     *
     * @param g The graphics object used for drawing the Runner JPanel
     * @param x The x-position of the {@code ArrowBar}
     * @param y The y-position of the {@code ArrowBar}
     */
    private void drawDirection(Graphics g, int x, int y) {
        directionRectangle.setBounds(x + 97, y + 6, 18, 16);
        if(!directionSelected || Runner.customAttacksCounter % 75 >= 30)
            setImage(g, x, y);
    }
    
    /**
     * Draws the {@code ArrowBar}'s direction arrow based on the direction it should be facing
     *
     * @param g The graphics object used for drawing the Runner JPanel
     * @param x The x-position of the {@code ArrowBar}
     * @param y The y-position of the {@code ArrowBar}
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
     * Drawing method for reverse tick box that also sets its bounds for clicking
     *
     * @param g The graphics object used for drawing the Runner JPanel
     * @param x The x-position of the reverse tick box
     * @param y The y-position of the reverse tick box
     */
    private void reverseTickBox(Graphics g, int x, int y) {
        if(reversible)
            g.drawImage(Runner.ticked, x + 400 - 10, y + 8, null);
        reverseTickBox.setBounds(x + 400 - 10, y + 8, 13, 12);
    }
    
    /**
     * Returns if the drag icon of this {@code ArrowBar} is being pressed down
     *
     * @return True if drag icon is being pressed down and false otherwise
     */
    boolean isPressed() {
        return pressed;
    }
    
    /**
     * Drawing method for drag icon that also sets its bounds for clicking if it is not being pressed
     *
     * @param g The graphics object used for drawing the Runner JPanel
     * @param x The x-position of the {@code ArrowBar}
     * @param y The y-position of the {@code ArrowBar}
     */
    private void dragArrowIcon(Graphics g, int x, int y) {
        if(!pressed)
            dragArrowIcon.setBounds(x + 424, y + 8, 16, 8 + 3);
        g.drawImage(Runner.dragArrowIcon, dragArrowIcon.x, dragArrowIcon.y, null);
    }
    
    /**
     * Returns the drag icon bounds for clicking
     *
     * @return Drag icon bounds for clicking
     */
    Rectangle getDragArrowIcon() {
        return dragArrowIcon;
    }
    
    /**
     * Sets the bounds of the drag icon's {@code Rectangle}
     *
     * @param dragArrowIcon Drag icon's {@code Rectangle} bounds
     */
    void setDragArrowIcon(Rectangle dragArrowIcon) {
        this.dragArrowIcon = dragArrowIcon;
    }
    
    /**
     * Drawing method for delete arrow button that also sets its bounds for clicking
     *
     * @param g The graphics object used for drawing the Runner JPanel
     * @param x The x-position of {@code ArrowBar}
     * @param y The y-position of {@code ArrowBar}
     */
    private void deleteArrowButton(Graphics g, int x, int y) {
        g.drawImage(Runner.deleteArrow, x, y, null);
        deleteArrowButton.setBounds(x, y, 19, 17);
    }
    
    /**
     * Returns the delete arrow button's bounds for clicking
     *
     * @return delete arrow button's clicking bounds
     */
    Rectangle getDeleteArrowButton() {
        return deleteArrowButton;
    }
    
    /**
     * Returns the reverse checkbox's bounds for clicking
     *
     * @return reverse checkbox's clicking bounds
     */
    Rectangle getReverseTickBox() {
        return reverseTickBox;
    }
    
    /**
     * Makes the direction arrow unselected
     */
    public void directionSelectedFalse() {
        this.directionSelected = false;
    }
    
    /**
     * Returns the delay value for the {@code ArrowBar}
     *
     * @return The delay value for the {@code ArrowBar}
     */
    public int getDelay() {
        if(!delayField.getText().isEmpty())
            delay = Integer.parseInt(delayField.getText());
        else
            delay = 0;
        return delay;
    }
    
    /**
     * Returns the speed value for the {@code ArrowBar}
     *
     * @return The speed value for the {@code ArrowBar}
     */
    public int getSpeed() {
        if(!speedField.getText().isEmpty())
            speed = Integer.parseInt(speedField.getText());
        else
            speed = 0;
        return speed;
    }
    
    /**
     * Sets the direction of the direction arrow
     *
     * @param direction The direction the direction arrow should point
     */
    public void setDirection(char direction) {
        this.direction = direction;
    }
    
    /**
     * Returns the direction of the direction arrow
     *
     * @return The direction of the direction arrow
     */
    public char getDirection() {
        return direction;
    }
    
    /**
     * Toggles the {@code ArrowBar}'s reversible state
     */
    void switchReversible() {
        reversible = !reversible;
    }
    
    /**
     * Returns the arrow's reversible state
     *
     * @return The arrow's reversible state
     */
    public boolean isReversible() {
        return reversible;
    }
    
    /**
     * Returns the direction icon's clicking bounds
     *
     * @return The direction icon's clicking bounds
     */
    Rectangle getDirectionRectangle() {
        return directionRectangle;
    }
    
    /**
     * Checks if the direction icon is selected
     *
     * @return True if the direction icon is selected and false otherwise
     */
    public boolean isDirectionSelected() {
        return directionSelected;
    }
    
    /**
     * Toggles if the direction icon is selected
     */
    void switchDirectionIsSelected() {
        directionSelected = !directionSelected;
    }
    
    /**
     * Sets the visibility of the {@code NumberField}s to the given value
     *
     * @param visibility The visibility of {@code NumberField}s
     */
    void setFieldsVisibility(boolean visibility) {
        speedField.setVisible(visibility);
        delayField.setVisible(visibility);
    }
    
    /**
     * Sets numberFields to be usable (editable)
     *
     * @param usable True if they should be usable and false otherwise
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
     * Checks if there are any empty {@code NumberField}s
     *
     * @return Boolean value
     */
    boolean emptyFieldExists() {
        return speedField.getText().isEmpty() || delayField.getText().isEmpty();
    }
    
    /**
     * Returns a {@code String} representation of the {@code ArrowBar}
     *
     * @return A {@code String} representation of the {@code ArrowBar}
     */
    @Override
    public String toString() {
        return "{" + "speed = " + speed + ", delay = " + delay + ", reverse = " + reversible + ", direction = " + direction + '}';
    }
}

