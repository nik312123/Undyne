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
    private int y = 0;
    private int speed;
    private int delay;
    
    private boolean isDirectionSelected = false;
    private boolean pressed = false;
    private boolean reversible;
    
    private char direction;
    
    private Color textColor = new Color(255, 198, 0);
    private static final Color FOREGROUND = new Color(255, 196, 0);
    
    private Rectangle deleteArrowButton = new Rectangle();
    private Rectangle directionRectangle = new Rectangle();
    private Rectangle orderIntersection = new Rectangle();
    private Rectangle dragArrowIcon = new Rectangle();
    private Rectangle reverseTickBox = new Rectangle();
    
    private static AffineTransform arrowBarTransform = new AffineTransform();
    
    private NumberField speedField;
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
            
            speedField.setFont(Runner.deteFontTen);
            delayField.setFont(Runner.deteFontTen);
            
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
    
    void removeFields() {
        Runner.removeComponent(speedField);
        Runner.removeComponent(delayField);
    }
    
    int getY() {
        return y;
    }
    
    void setY(int y) {
        this.y = y;
    }
    
    void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
    
    Rectangle getOrderIntersection() {
        return orderIntersection;
    }
    
    void draw(Graphics g, int x, int y) {
        if(!pressed)
            this.y = y;
        speedField.setLocation(AttackBar.ATTACKBAR_X + 6 + 183, this.y + 7);
        delayField.setLocation(AttackBar.ATTACKBAR_X + 6 + 277, this.y + 7);
        g.setColor(Color.BLACK);
        g.fillRect(AttackBar.ATTACKBAR_X + 10 + 183, this.y + 8, 26, 12);
        g.fillRect(AttackBar.ATTACKBAR_X + 10 + 277, this.y + 8, 26, 12);
        orderIntersection.setBounds(x, this.y, 100, 10);
        g.drawImage(Runner.arrowBarImage, x, this.y, null);
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
    
    private void inputMapKeyStrokeReplacement(InputMap im, String keyStrokeName, String actionMapKey) {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeName);
        im.put(keyStroke, actionMapKey);
    }
    
    private void drawDirection(Graphics g, int x, int y) {
        directionRectangle.setBounds(x + 97, y + 6, 18, 16);
        if(!isDirectionSelected || Runner.creatorArrowDirectionCounter % 75 >= 30)
            setImage(g, x, y);
    }
    
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
            g2d.setFont(Runner.deteFontFourteen);
            g2d.drawString("R", x + 102, y + 18);
        }
    }
    
    private void reverseTickBox(Graphics g, int x, int y) {
        if(reversible)
            g.drawImage(Runner.ticked, x + 400 - 10, y + 8, null);
        reverseTickBox.setBounds(x + 400 - 10, y + 8, 13, 12);
    }
    
    boolean isPressed() {
        return pressed;
    }
    
    private void dragArrowIcon(Graphics g, int x, int y) {
        if(!pressed)
            dragArrowIcon.setBounds(x + 424, y + 8, 16, 8 + 3);
        g.drawImage(Runner.dragArrowIcon, dragArrowIcon.x, dragArrowIcon.y, null);
    }
    
    Rectangle getDragArrowIcon() {
        return dragArrowIcon;
    }
    
    void setDragArrowIcon(Rectangle dragArrowIcon) {
        this.dragArrowIcon = dragArrowIcon;
    }
    
    private void deleteArrowButton(Graphics g, int x, int y) {
        g.drawImage(Runner.deleteArrow, x, y, null);
        deleteArrowButton.setBounds(x, y, 19, 17);
    }
    
    Rectangle getDeleteArrowButton() {
        return deleteArrowButton;
    }
    
    Rectangle getReverseTickBox() {
        return reverseTickBox;
    }
    
    public void directionSelectedFalse() {
        this.isDirectionSelected = false;
    }
    
    public int getDelay() {
        if(!delayField.getText().isEmpty())
            delay = Integer.parseInt(delayField.getText());
        else
            delay = 0;
        return delay;
    }
    
    public int getSpeed() {
        if(!speedField.getText().isEmpty())
            speed = Integer.parseInt(speedField.getText());
        else
            speed = 0;
        return speed;
    }
    
    public void setDirection(char direction) {
        this.direction = direction;
    }
    
    public char getDirection() {
        return direction;
    }
    
    void switchReversible() {
        reversible = !reversible;
    }
    
    public boolean isReversible() {
        return reversible;
    }
    
    Rectangle getDirectionRectangle() {
        return directionRectangle;
    }
    
    public boolean isDirectionSelected() {
        return isDirectionSelected;
    }
    
    void switchDirectionIsSelected() {
        isDirectionSelected = !isDirectionSelected;
    }
    
    void setFieldsVisibility(boolean visibility) {
        speedField.setVisible(visibility);
        delayField.setVisible(visibility);
    }
    
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
    
    boolean emptyFieldExists() {
        return speedField.getText().isEmpty() || delayField.getText().isEmpty();
    }
    
    @Override
    public String toString() {
        return "{" + "speed = " + speed + ", delay = " + delay + ", reverse = " + reversible + ", direction = " + direction + '}';
    }
}

