package defense;

import java.util.ArrayList;
import java.util.Random;

public class Attacks {
    
    private boolean isNewAttack = true;
    private boolean loopDone = false;
    private boolean isFinished = false;
    
    private int lastAttack = 1;
    private int currentAttack = 1;
    private int position = 0;
    private int counter = 0;
    private int speed = 0;
    
    private char prevChar = 'u';
    
    private final char[] DIRS = {'u', 'd', 'r', 'l'};
    
    private Random rand = new Random();
    private Attack att;
    
    private ArrayList<ArrayList<Arrow>> undyneAttacks = new ArrayList<ArrayList<Arrow>>();
    
    public Attacks(boolean isGenocide) {
        for(int i = 0; i < 11; ++i)
            undyneAttacks.add(new ArrayList<Arrow>());
        if(!isGenocide) {
            normalAttackOne();
            normalAttackTwo();
            normalAttackThree();
            normalAttackFour();
            normalAttackFive();
            normalAttackSix();
            normalAttackSeven();
            normalAttackEight();
            normalAttackNine();
            normalAttackTen();

            addArrow(10, 2, false, 'l', 55);
            addArrow(10, 2, false, 'r', 55);
            addArrow(10, 2, false, 'l', 55);
            addArrow(10, 2, false, 'r', 55);
        }
        else {
            genocideAttackOne();
            genocideAttackTwo();
            genocideAttackThree();
            genocideAttackFour();
            genocideAttackFive();
            
            for(int i = 0; i < 4; ++i) {
                addArrow(5, 3, false, 'r', 25);
                addArrow(5, 3, false, 'd', 25);
            }
        }
    }
    
    public Arrow getCurrentArrow() {
        for(int i = 0; i < undyneAttacks.size() && !loopDone; ++i) {
            if(undyneAttacks.get(i).size() != 0) {
                currentAttack = i + 1;
                position = i;
                loopDone = true;
            }
            else
                ++counter;
        }
        loopDone = false;
        if(currentAttack != lastAttack) {
            isNewAttack = true;
            lastAttack = currentAttack;
        }
        else if(counter != undyneAttacks.size()) {
            isNewAttack = false;
            counter = 0;
            return undyneAttacks.get(position).remove(0);
        }
        counter = 0;
        isFinished = noAttacksLeft();
        return new Arrow(0, false, 'u', 0, false);
    }
    
    public boolean noAttacksLeft() {
        ArrayList<Arrow> attackPattern = att.getList();
        if(attackPattern.size() != 0)
            return false;
        for(int i = 0; i < undyneAttacks.size(); ++i) {
            for(int j = 0; j < undyneAttacks.get(i).size(); ++j) {
                Arrow temp = undyneAttacks.get(i).get(j);
                if(temp != null && temp.getSpeed() != 0)
                    return false;
            }
        }
        return true;
    }
    
    public boolean isNewAttack() {
        return isNewAttack;
    }
    
    public void notNewAttack() {
        isNewAttack = false;
    }
    
    private void addArrow(int attack, int speed, boolean reversable, char direction, int delay) {
        undyneAttacks.get(attack).add(new Arrow(speed + this.speed, reversable, direction, delay, false));
    }
    
    private void addSlowArrow(int attack, boolean reversable, char direction, int delay) {
        undyneAttacks.get(attack).add(new Arrow(1 + this.speed, reversable, direction, delay, true));
    }
    
    private void upFour(boolean isLast) {
        for(int i = 0; i < 3; ++i)
            addArrow(4, 4, false, 'd', 10);
        addArrow(4, 4, false, 'd', (isLast ? 45 : 20));
    }
    
    private void normalAttackOne() {
        for(int i = 0; i < 3; ++i)
            addArrow(0, 2, false, 'd', 45);
    }
    
    private void normalAttackTwo() {
        addArrow(1, 2, false, 'd', 45);
        addArrow(1, 2, false, 'd', 45);
        addArrow(1, 2, false, 'r', 45);
        addArrow(1, 2, false, 'r', 45);
        addArrow(1, 2, false, 'l', 45);
        addArrow(1, 2, false, 'l', 45);
    }
    
    private void normalAttackThree() {
        addArrow(2, 2, false, 'r', 45);
        addArrow(2, 2, false, 'l', 45);
        addArrow(2, 2, false, 'r', 45);
        addArrow(2, 2, false, 'l', 45);
        addArrow(2, 2, false, 'l', 45);
        addArrow(2, 2, false, 'r', 45);
        addArrow(2, 2, false, 'r', 45);
        addArrow(2, 2, false, 'u', 45);
    }
    
    private void normalAttackFour() {
        addArrow(3, 2, false, 'd', 45);
        addArrow(3, 2, false, 'l', 45);
        addArrow(3, 2, false, 'u', 45);
        addArrow(3, 2, false, 'r', 45);
        addArrow(3, 2, false, 'd', 45);
        addArrow(3, 2, false, 'l', 45);
        addArrow(3, 2, false, 'u', 45);
        addArrow(3, 2, false, 'r', 45);
        addArrow(3, 2, false, 'd', 15);
        addArrow(3, 2, false, 'd', 15);
        addArrow(3, 2, false, 'd', 15);
        addArrow(3, 2, false, 'd', 45);
    }
    
    private void normalAttackFive() {
        addArrow(4, 2, false, 'r', 45);
        addArrow(4, 2, false, 'd', 15);
        addArrow(4, 2, false, 'd', 45);
        addArrow(4, 2, false, 'l', 45);
        addArrow(4, 2, false, 'd', 15);
        addArrow(4, 2, false, 'd', 45);
        addArrow(4, 2, false, 'r', 45);
        addArrow(4, 2, false, 'd', 45);
        addArrow(4, 2, false, 'u', 45);
    }
    
    private void normalAttackSix() {
        addArrow(5, 4, false, 'r', 30);
        addArrow(5, 4, false, 'd', 30);
        addArrow(5, 4, false, 'u', 30);
        addArrow(5, 4, false, 'l', 30);
        addArrow(5, 4, false, 'r', 30);
        addArrow(5, 4, false, 'd', 30);
        addArrow(5, 4, false, 'u', 30);
        addArrow(5, 4, false, 'l', 30);
        addArrow(5, 4, false, 'u', 30);
        addArrow(5, 4, false, 'd', 45);
    }
    
    private void normalAttackSeven() {
        addArrow(6, 2, false, 'd', 45);
        addArrow(6, 2, false, 'u', 15);
        addArrow(6, 2, false, 'd', 45);
        addArrow(6, 2, false, 'u', 45);
        addArrow(6, 2, false, 'u', 15);
        addArrow(6, 2, false, 'd', 45);
        addArrow(6, 2, false, 'd', 45);
        addArrow(6, 2, false, 'u', 45);
        addArrow(6, 2, false, 'd', 45);
    }
    
    private void normalAttackEight() {
        addSlowArrow(7, false, 'r', 115);
        addArrow(7, 3, false, 'd', 45);
        addArrow(7, 3, false, 'l', 45);
        addArrow(7, 3, false, 'u', 45);
        addArrow(7, 3, false, 'd', 45);
        addArrow(7, 3, false, 'l', 45);
        addArrow(7, 3, false, 'u', 45);
    }
    
    private void normalAttackNine() {
        addArrow(8, 3, false, 'r', 1);
        addArrow(8, 4, false, 'r', 1);
        addArrow(8, 5, false, 'r', 70);
        addArrow(8, 3, false, 'l', 1);
        addArrow(8, 4, false, 'l', 1);
        addArrow(8, 5, false, 'l', 45);
        addArrow(8, 4, false, 'u', 40);
        addArrow(8, 4, false, 'd', 40);
        addArrow(8, 4, false, 'u', 40);
        addArrow(8, 4, false, 'd', 45);
    }
    
    private void normalAttackTen() {
        addArrow(9, 4, false, 'd', 30);
        addArrow(9, 4, false, 'r', 30);
        addArrow(9, 4, false, 'd', 30);
        addArrow(9, 4, false, 'l', 30);
        addArrow(9, 4, false, 'u', 30);
        addArrow(9, 4, false, 'r', 30);
        addArrow(9, 4, false, 'r', 30);
        addArrow(9, 4, false, 'l', 60);
        addArrow(9, 4, false, 'u', 30);
        addArrow(9, 4, false, 'l', 30);
        addArrow(9, 4, false, 'l', 30);
        addArrow(9, 4, false, 'u', 45);
    }
    
    private void genocideAttackOne() {
        addArrow(0, 2, false, 'd', 55);
        addArrow(0, 2, false, 'd', 55);
        addArrow(0, 2, false, 'd', 130);
        addArrow(0, 5, false, 'l', 25);
        addArrow(0, 5, false, 'u', 25);
        addArrow(0, 5, false, 'r', 25);
        addArrow(0, 5, false, 'd', 25);
        addArrow(0, 5, false, 'r', 25);
        addArrow(0, 5, false, 'u', 25);
        addArrow(0, 5, false, 'l', 25);
        addArrow(0, 5, false, 'u', 25);
        addArrow(0, 5, false, 'r', 25);
        addArrow(0, 5, false, 'd', 45);
    }
    
    private void genocideAttackTwo() {
        addArrow(1, 4, false, 'r', 25);
        addArrow(1, 4, false, 'l', 25);
        addArrow(1, 4, false, 'r', 15);
        addArrow(1, 4, false, 'r', 25);
        addArrow(1, 4, false, 'l', 25);
        addArrow(1, 4, false, 'l', 25);
        addArrow(1, 4, false, 'r', 25);
        addArrow(1, 4, false, 'l', 15);
        addArrow(1, 4, false, 'l', 25);
        addArrow(1, 4, false, 'r', 25);
        addArrow(1, 4, false, 'l', 25);
        addArrow(1, 4, false, 'r', 25);
        addArrow(1, 4, false, 'l', 25);
        addArrow(1, 4, false, 'r', 45);
    }
    
    private void genocideAttackThree() {
        for(int i = 0; i < 18; ++i) {
            char curChar = DIRS[rand.nextInt(4)];
            if(curChar == prevChar)
                curChar = DIRS[rand.nextInt(4)];
            prevChar = curChar;
            if(i != 17)
                addArrow(2, 1, false, curChar, 26);
        }
        addArrow(2, 1, false, DIRS[rand.nextInt(4)], 110);
    }
    
    private void genocideAttackFour() {
        addArrow(3, 5, false, 'r', 10);
        addArrow(3, 4, false, 'd', 40);
        addArrow(3, 5, false, 'l', 10);
        addArrow(3, 4, false, 'u', 35);
        addArrow(3, 4, false, 'r', 1);
        addArrow(3, 1, false, 'd', 30);
        addArrow(3, 1, false, 'u', 25);
        addArrow(3, 5, false, 'l', 70);
    }
    
    private void genocideAttackFive() {
        upFour(false);
        addArrow(4, 4, false, 'r', 10);
        upFour(false);
        addArrow(4, 4, false, 'l', 10);
        upFour(false);
        addArrow(4, 4, false, 'r', 10);
        upFour(false);
        addArrow(4, 4, false, 'l', 10);
        upFour(true);
    }
    
    public void setAttack(Attack att) {
        this.att = att;
    }
    
    public boolean getIsFinished() {
        return isFinished;
    }
    
    public void resetVars() {
        isNewAttack = true;
        loopDone = false;
        isFinished = false;
        lastAttack = 1;
        currentAttack = 1;
        position = 0;
        counter = 0;
        speed = 0;
        prevChar = 'u';
        undyneAttacks = new ArrayList<ArrayList<Arrow>>();
        att = null;
    }
    
}
