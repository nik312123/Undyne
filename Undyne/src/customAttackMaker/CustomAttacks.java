package customAttackMaker;

import defense.Arrow;
import defense.Runner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CustomAttacks extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public static JFrame anotherOne;

    private static Timer timer;

    public static ArrayList<ArrayList<Arrow>> attacks = new ArrayList<>();
    public static ArrayList<Rectangle> deleteAttack = new ArrayList<>();
    public static ArrayList<Rectangle> addArrow = new ArrayList<>();
    public static ArrayList<DropDownButton> dropDowns = new ArrayList<>();
    public static ArrayList<DeleteArrow> deleteArrows = new ArrayList<>();



    private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

    private static Rectangle exit = new Rectangle(347, 2, 36, 18);
    private static Rectangle addAttack = new Rectangle();

    private static Rectangle mouse = new Rectangle();

    private static ArrayList<Rectangle> rectangles = new ArrayList<>();


    private static int length = 0;
    private static int lengthStart = 30;


    CustomAttacks() {
        timer = new Timer(0, this);
        timer.start();
    }

    public CustomAttacks(String s) {
        rectangles.add(exit);
        rectangles.add(addAttack);

        anotherOne = new JFrame(s);
        anotherOne.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        CustomAttacks bp = new CustomAttacks();
        bp.setBounds(0, 0, 400, 600);
        anotherOne.add(bp);

        anotherOne.addMouseListener(this);
        anotherOne.addMouseWheelListener(this);
        anotherOne.addKeyListener(this);
        anotherOne.addMouseMotionListener(this);
        anotherOne.setSize(400, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        anotherOne.setLocation(dim.width / 2 - Runner.frame.getWidth() / 2 - 410, dim.height / 2 - Runner.frame.getHeight() / 2);
        anotherOne.setResizable(false);
        anotherOne.setAlwaysOnTop(false);
        anotherOne.setUndecorated(true);
        anotherOne.getContentPane().setLayout(null);
        anotherOne.setVisible(false);
    }

    private void addArrow(int attack, int speed, boolean reversable, char direction, int delay) {
        attacks.get(attack).add(new Arrow(speed, reversable, direction, delay, false));
        deleteArrows.add(new DeleteArrow(attack,attacks.get(attack).size()-1));

    }

    private void addAttack() {
        attacks.add(new ArrayList<>());
        deleteAttack.add(new Rectangle());
        dropDowns.add(new DropDownButton(attacks.size() - 1));
    }

    @Override
    public void paintComponent(Graphics g) {
        frameWork(g);

        //System.out.println(deleteArrows);

        while (attacks.size() > deleteAttack.size()) {
            deleteAttack.add(new Rectangle());
        }

        while (attacks.size() > addArrow.size()) {
            addArrow.add(new Rectangle());
        }

        g.drawImage(Runner.exit, 347, 2, null);

        int numberOfAttacks = 0;
        length = lengthStart;
        for (ArrayList<Arrow> a : attacks) {
            numberOfAttacks++;
            length += 10;
            g.setColor(Color.WHITE);
            g.setFont(Runner.deteFontNorm);
            Font currentFont = g.getFont();
            Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.8F);
            g.setFont(newFont);
            g.drawString("Attack " + numberOfAttacks, 15, length);
            dropDowns.get(numberOfAttacks - 1).drawImage(g, 15 - 10, length - 10);
            g.drawImage(Runner.deleteAttack, g.getFontMetrics().stringWidth("Attack " + numberOfAttacks) + 30
                    , length - 15, null);
            deleteAttack.get(numberOfAttacks - 1).setBounds(g.getFontMetrics().stringWidth("Attack " + numberOfAttacks) + 30
                    , length - 15, 44, 17);
            int arrowNum = 0;
            length += 20;
            if (dropDowns.get(numberOfAttacks - 1).getStatus() == 0)
                continue;
            for (Arrow arrow : a) {
                arrowNum++;
               // System.out.println(deleteArrows);
                deleteArrows.get(arrowNum-1).drawImage(g,6,length + 2);
                g.drawImage(Runner.arrowImg, 30, length, null);
                // g.drawString("Arrow " +arrowNum,30, length);
                length += 25;
            }
            g.drawImage(Runner.newArrow, 30, length, null);
            addArrow.get(numberOfAttacks - 1).setBounds(30, length, 19, 17);

            length += 40;
        }

        g.drawImage(Runner.addAttack, 155, length, null);
        addAttack.setBounds(155, length, 66, 17);

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (exit.intersects(mouse))
            anotherOne.setVisible(false);
        if (addAttack.intersects(mouse))
            addAttack();

        for (int i = 0; i < deleteAttack.size(); i++) {
            if (deleteAttack.get(i).intersects(mouse)) {
                deleteAttack.remove(i);
                attacks.remove(i);
                for(int j = i; j < dropDowns.size(); j++){
                    dropDowns.get(j).setIndex(dropDowns.get(j).getIndex()-1);
                }
                dropDowns.remove(i);

                for(int k = 0; k < deleteArrows.size(); k++){

                    if(deleteArrows.get(k).getAttackNum() == i) {
                       // System.out.println("k = " + k);
                        deleteArrows.remove(k);
                        k--;
                    }

                }

                for(int k = 0; k < deleteArrows.size(); k++) {
                    if (deleteArrows.get(k).getAttackNum() > i) {
                        deleteArrows.get(k).setAttackNum(deleteArrows.get(k).getAttackNum()-1);
                    }
                }



                break;
            }
        }

        for (int i = 0; i < addArrow.size(); i++) {
            if (addArrow.get(i).intersects(mouse)) {
                System.out.println("BOOOOOIIIIIII == " + i);
                addArrow(i, 1, false, 'c', 2);
                break;
            }
        }

        for (int i = 0; i < dropDowns.size(); i++) {
            if (dropDowns.get(i).getPosition().intersects(mouse)) {
                dropDowns.get(i).switchStatus();
                break;
            }
        }


        for(int i = 0; i < deleteArrows.size(); i++){
            if(deleteArrows.get(i).getPosition().intersects(mouse)){
                attacks.get(deleteArrows.get(i).getAttackNum()).remove(deleteArrows.get(i).getArrownum());

                for(DeleteArrow a : deleteArrows){
                    if(a.getAttackNum()==deleteArrows.get(i).getAttackNum() && a.getArrownum() > deleteArrows.get(i).getArrownum()){
                        a.setArrownum(a.getArrownum()-1);
                    }
                }
                deleteArrows.remove(i);

            }
        }



    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                lengthStart += 10;
                break;
            case KeyEvent.VK_DOWN:
                lengthStart -= 10;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse.setBounds(e.getX(), e.getY(), 1, 1);

        for(int i = 0; i < deleteArrows.size(); i++){
            if(deleteArrows.get(i).getPosition().intersects(mouse)) {
                        System.out.println(deleteArrows.get(i));
                }
            }

        for (int i = 0; i < addArrow.size(); i++) {
            if (addArrow.get(i).intersects(mouse)) {
                System.out.println(addArrow.get(i)+" Index = " + i);

                     }
            }


    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

       lengthStart += e.getWheelRotation() * -1;
    }


    public void frameWork(Graphics g) {
        g.fillRect(0, 0, 400, 600);
        anotherOne.setLocation(Runner.frame.getX() - 410, Runner.frame.getY());

    }
}
