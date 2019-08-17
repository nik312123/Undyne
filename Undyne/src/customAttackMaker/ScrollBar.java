package customAttackMaker;

import defense.Runner;

import java.awt.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ScrollBar {



    int y = 32 + 21;
    int pressedY = 32 + 21;
    Rectangle up_Scroll = new Rectangle(579, 32, 18, 18);
    Rectangle down_Scroll = new Rectangle(579, 579, 18, 18);
    Rectangle bar = new Rectangle(579, y, 18, 18);
    int scrollLengthh = 10;
    int actualSize = 0;

    boolean barPressed = false;
    boolean pressed = false;

    //length of scroll space = 523

    public void perform(Graphics2D g, int scrollLength, Point mousePosition) {
        actualSize = scrollLength - 450;
        scrollLength = (int) ((541 / (double) scrollLength) * 523);

        if (scrollLength > 523)
            scrollLength = 523;

        if (scrollLength < 18)
            scrollLength = 18;

        scrollLengthh = scrollLength;

        g.setColor(new Color(41, 41, 41, 180));
        g.fillRect(600 - 24, 0, 24, 600);
        g.drawImage(Runner.up_Scroll, 579, 32, null);
        g.drawImage(Runner.down_Scroll, 579, 579, null);
        g.setColor(new Color(255, 194, 0));
        g.fillRect(579, y, 18, scrollLength);
        bar.setBounds(579, y, 18, scrollLength);
        g.setColor(Color.BLACK);
        g.fillRect(579 + 2, y + 2, 18 - 4, scrollLength - 4);
        g.drawImage(Runner.scrollLines, 584, y + scrollLength / 2 - 3, null);
    }

    public void mouseStuff(Point mousePosition) {
        if (down_Scroll.contains(mousePosition)) {
            shift(-100); //this thing
            update();
        } else if (up_Scroll.contains(mousePosition)) {
            shift(100);
            update();

        }

    }

    public void shift(int deltaY) {
        y = (int) convertPixels((int) CustomAttacks.scrollValue + deltaY);
    }

    public void update() {

        if (y < 32 + 21)
            y = 32 + 21;
        if (y + scrollLengthh > 579 - 3)
            y = (579 - 3) - scrollLengthh;

        double percentage = ((y - (32 + 21)) / ((double) 576 - scrollLengthh - (32 + 21)));

        if (scrollLengthh < 523)
            CustomAttacks.scrollValue = (-actualSize * percentage) + 72;

    }

    double convertPixels(int pixels) {
        return -pixels / (double) actualSize * (523 - scrollLengthh) + 53;
    }

    public void mouseDragged(Point mousePosition) {
        if (barPressed) {
            y = mousePosition.y - pressedY;

            update();
        }
    }

    public void mousePressed(Point mousePosition) {
        mouseStuff(mousePosition);
        if (up_Scroll.contains(mousePosition) || down_Scroll.contains(mousePosition))
            pressed = true;
        if (bar.contains(mousePosition)) {
            pressedY = mousePosition.y - y;
            barPressed = true;
        }
    }

    public void mouseReleased(Point mousePosition) {
        if (pressed)
            pressed = false;
        if (barPressed)
            barPressed = false;
    }

}
