/*
 * Copyright (c) 2017, Nikunj and/or his affiliates. All rights reserved.
 * NIKUNJ PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package defense;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public abstract class GradientButtonTolerance extends JPanel implements MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;
    
    private int colorChange = 0;
    
    private long pressTime;
    
    private Point pressLocation;
    
    private Image buttonIcon;
    
    private Color initialColor;
    private Color finalColor;
    
    private Shape relativeS;
    
    private ToleranceComponent tc = new ToleranceComponent();
    
    private class ToleranceComponent extends Component {}
    
    /**
     * Initializes a newly created {@code GradientButtonTolerance} with a rectangular shape and 25 steps to a color change
     * @param buttonIcon        An image that is used for the button's icon
     * @param initialColor      The initial button color
     * @param finalColor        The color the button changes to when on the button
     * @param x                 The x coordinate of the button
     * @param y                 The y coordinate of the button
     * @param width             The width of the button
     * @param height            The height of the button
     */
    GradientButtonTolerance(Image buttonIcon, Color initialColor, Color finalColor, int x, int y, int width, int height) {
        this.buttonIcon = buttonIcon;
        this.initialColor = initialColor;
        this.finalColor = finalColor;
        relativeS = new Rectangle(0, 0, width, height);
        setBounds(x, y, width, height);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    final void visibilityCheck() {
        if(!isVisible())
            colorChange = 0;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawing(g);
    }
    
    private void drawing(Graphics g) {
        int x = 0, y = 0;
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        beforeDraw(g2d);
        Shape shapeToFill;
        shapeToFill = relativeS;
        int steps = 25;
        if(onButton())
            colorChange = Math.min(colorChange + 1, steps);
        else
            colorChange = Math.max(colorChange - 1, 0);
        g2d.setColor(initialColor);
        g2d.fill(shapeToFill);
        Composite original = g2d.getComposite();
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) colorChange/ steps);
        g2d.setComposite(alpha);
        g2d.setColor(finalColor);
        g2d.fill(shapeToFill);
        g2d.setComposite(original);
        g2d.drawImage(buttonIcon, x, y, null);
        afterDraw(g2d);
        g2d.dispose();
    }
    
    /**
     * Whether or not the mouse is on the button, can be overridden for different specifications
     */
    boolean onButton() {
        if(!isVisible() || !isDisplayable())
            return false;
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        Point location = getLocationOnScreen();
        mousePos = new Point((int) (mousePos.getX() - location.getX()), (int) (mousePos.getY() - location.getY()));
        return relativeS.contains(mousePos.x, mousePos.y);
    }
    
    /**
     * Overridable method to do something before every time the button is drawn
     * @param g    Graphics object from paintComponent
     */
    public void beforeDraw(Graphics g) {}
    
    /**
     * Overridable method to do something after every time the button is drawn
     * @param g    Graphics object from paintComponent
     */
    public void afterDraw(Graphics g) {}
    
    @Override
    public final void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof ToleranceComponent)
            onMouseClick(e);
    }
    
    @Override
    public final void mousePressed(MouseEvent e) {
        pressLocation = new Point(e.getX(), e.getY());
        pressTime = System.nanoTime();
        onMousePress(e);
    }
    
    @Override
    public final void mouseReleased(MouseEvent e) {
        Point releaseLocation = new Point(e.getX(), e.getY());
        long time = (System.nanoTime() - pressTime) / 1000000;
        if(time <= 750 && Math.hypot(releaseLocation.x - pressLocation.x, releaseLocation.y - pressLocation.y) <= 5)
            mouseClicked(new MouseEvent(tc, MouseEvent.MOUSE_CLICKED, System.nanoTime(), 0, pressLocation.x, pressLocation.y, 1, false));
        onMouseRelease(e);
    }
    
    abstract void onMousePress(MouseEvent e);
    
    abstract void onMouseRelease(MouseEvent e);
    
    abstract void onMouseClick(MouseEvent e);
    
}
