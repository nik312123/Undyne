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
    
    private char prevChar = 'u';
    
    private final char[] DIRS = {'u', 'd', 'r', 'l'};
    
    private Random rand = new Random();
    private Attack att;
    
    private ArrayList<ArrayList<Arrow>> undyneAttacks = new ArrayList<ArrayList<Arrow>>();
    
    public Attacks(boolean isGenocide) {
        for(int i = 0; i < 18; ++i)
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
            normalAttackEleven();
            normalAttackTwelve();
            normalAttackThirteen();
            normalAttackFourteen();
            normalAttackFifteen();
            normalAttackSixteen();
            normalAttackSeventeen();
            normalAttackEighteen();
        }
        else {

          
            genocideAttackOne();
            genocideAttackTwo();
            genocideAttackThreeAndTen(18, 2);
            genocideAttackFour();
            genocideAttackFive();
            genocideAttackSix();
            genocideAttackSeven();
            genocideAttackEight();
            genocideAttackNine();
            genocideAttackThreeAndTen(23, 9);
            genocideAttackEleven();
            genocideAttackTwelve();
            genocideAttackThirteen();
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
    
    private boolean noAttacksLeft() {
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
        undyneAttacks.get(attack).add(new Arrow(speed, reversable, direction, delay, false));
    }
    
    private void addSlowArrow(int attack, boolean reversable, char direction, int delay) {
        undyneAttacks.get(attack).add(new Arrow(1, reversable, direction, delay, true));
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
    
    private void normalAttackEleven() {
        addArrow(10, 2, false, 'l', 55);
        addArrow(10, 2, false, 'r', 55);
        addArrow(10, 2, false, 'l', 55);
        addArrow(10, 2, false, 'r', 65);
        addArrow(10, 2, true, 'l', 45);
    }
    
    private void normalAttackTwelve() {
        addArrow(11, 2, false, 'r', 45);
        addArrow(11, 2, false, 'l', 75);
        addArrow(11, 2, true, 'u', 45);
        addArrow(11, 2, false, 'u', 45);
        addArrow(11, 2, false, 'r', 45);
        addArrow(11, 2, false, 'l', 45);
        addArrow(11, 2, false, 'd', 75);
        addArrow(11, 2, true, 'u', 45);
    }
    
    private void normalAttackThirteen() {
        addArrow(12, 3, false, 'r', 45);
        addArrow(12, 3, false, 'u', 45);
        addArrow(12, 3, false, 'l', 45);
        addArrow(12, 3, false, 'd', 125);
        addArrow(12, 3, true, 'l', 45);
        addArrow(12, 3, true, 'd', 45);
        addArrow(12, 3, true, 'r', 45);
        addArrow(12, 3, true, 'u', 45);
    }
    
    private void normalAttackFourteen() {
        addArrow(13, 2, false, 'l', 35);
        addArrow(13, 2, false, 'l', 35);
        addArrow(13, 2, false, 'r', 35);
        addArrow(13, 2, false, 'u', 35);
        addArrow(13, 2, true, 'd', 35);
        addArrow(13, 2, false, 'd', 35);
        addArrow(13, 2, true, 'u', 35);
        addArrow(13, 2, false, 'u', 125);
        addArrow(13, 4, false, 'l', 35);
        addArrow(13, 4, false, 'r', 45);
    }
    
    private void normalAttackFifteen() {
        addArrow(14, 2, false, 'r', 25);
        addArrow(14, 2, false, 'd', 25);
        addArrow(14, 2, false, 'd', 75);
        addArrow(14, 2, true, 'r', 25);
        addArrow(14, 2, false, 'l', 20);
        addArrow(14, 2, false, 'l', 20);
        addArrow(14, 2, false, 'r', 20);
        addArrow(14, 2, true, 'l', 20);
        addArrow(14, 2, false, 'u', 45);
    }
    
    private void normalAttackSixteen() {
        addArrow(15, 3, false, 'r', 25);
        addArrow(15, 3, false, 'u', 25);
        addArrow(15, 3, false, 'r', 25);
        addArrow(15, 3, false, 'u', 25);
        addArrow(15, 3, false, 'u', 25);
        addArrow(15, 3, false, 'l', 25);
        addArrow(15, 3, false, 'r', 25);
        addArrow(15, 3, false, 'r', 25);
        addArrow(15, 3, false, 'l', 25);
        addArrow(15, 3, false, 'u', 45);
    }
    
    private void normalAttackSeventeen() {
        addArrow(16, 2, false, 'r', 25);
        addArrow(16, 2, false, 'r', 25);
        addArrow(16, 2, false, 'r', 15);
        addArrow(16, 2, false, 'r', 15);
        addArrow(16, 2, false, 'r', 25);
        addArrow(16, 2, false, 'r', 25);
        addArrow(16, 2, true, 'l', 25);
        addArrow(16, 2, false, 'r', 25);
        addArrow(16, 2, true, 'l', 30);
        addArrow(16, 2, false, 'l', 25);
        addArrow(16, 2, true, 'r', 25);
        addArrow(16, 2, false, 'l', 25);
        addArrow(16, 2, true, 'r', 45);
    }
    
    private void normalAttackEighteen() {
        for(int i = 0; i < 3; ++i) {
            addArrow(17, 2, true, 'l', 33);
            addArrow(17, 2, true, 'd', 33);
            addArrow(17, 2, true, 'r', 33);
            addArrow(17, 2, true, 'u', 33);
        }
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
    
    private void genocideAttackThreeAndTen(int count, int pos) {
        for(int i = 0; i < count; ++i) {
            char curChar = DIRS[rand.nextInt(4)];
            if(curChar == prevChar)
                curChar = DIRS[rand.nextInt(4)];
            prevChar = curChar;
            if(i != 17)
                addArrow(pos, 1, false, curChar, 28);
        }
        addArrow(pos, 1, false, DIRS[rand.nextInt(4)], 110);
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
    
    private void upFour(boolean isLast) {
        for(int i = 0; i < 3; ++i)
            addArrow(4, 4, false, 'd', 10);
        addArrow(4, 4, false, 'd', (isLast ? 45 : 20));
    }
    
    private void genocideAttackSix() {
        for(int i = 0; i < 3; ++i) {
            addArrow(5, 3, false, 'r', 28);
            addArrow(5, 3, false, 'd', 28);
        }
        addArrow(5, 3, false, 'r', 28);
        addArrow(5, 3, false, 'd', 50);
        for(int i = 0; i < 3; ++i) {
            addArrow(5, 3, true, 'r', 28);
            addArrow(5, 3, true, 'd', 28);
        }
    }
    
    private void genocideAttackSeven() {
        addArrow(6, 3, true, 'l', 35);
        addArrow(6, 3, true, 'd', 35);
        addArrow(6, 3, true, 'r', 35);
        addArrow(6, 3, true, 'u', 35);
        addArrow(6, 3, false, 'd', 35);
        addArrow(6, 3, false, 'r', 35);
        addArrow(6, 3, false, 'u', 35);
        addArrow(6, 3, false, 'l', 50);
        addArrow(6, 3, true, 'u', 35);
        addArrow(6, 3, true, 'r', 35);
        addArrow(6, 3, true, 'l', 35);
        addArrow(6, 3, true, 'd', 35);
    }
    
    private void genocideAttackEight() {
        for(int i = 0; i < 2; ++i) {
            addArrow(7, 2, false, 'r', 25);
            addArrow(7, 2, false, 'l', 25);
            addArrow(7, 2, false, 'd', 25);
            addArrow(7, 2, true, 'u', 25);
            addArrow(7, 2, false, 'r', 25);
            addArrow(7, 2, false, 'l', 25);
            addArrow(7, 2, false, 'u', 25);
            addArrow(7, 2, true, 'd', 25);
        }
    }
    
    private void genocideAttackNine() {
        addArrow(8, 2, false, 'r', 40);
        addArrow(8, 2, true, 'r', 35);
        addArrow(8, 2, false, 'u', 40);
        addArrow(8, 2, true, 'u', 35);
        addArrow(8, 2, false, 'l', 40);
        addArrow(8, 2, true, 'l', 35);
        addArrow(8, 2, false, 'd', 40);
        addArrow(8, 2, true, 'd', 35);
        addArrow(8, 2, false, 'r', 40);
        addArrow(8, 2, true, 'r', 35);
        addArrow(8, 2, false, 'u', 40);
        addArrow(8, 2, true, 'u', 35);
        addArrow(8, 2, false, 'l', 40);
        addArrow(8, 2, true, 'l', 35);
    }
    
    private void genocideAttackEleven() {
        addArrow(10, 3, false, 'd', 20);
        addArrow(10, 3, false, 'l', 20);
        addArrow(10, 3, false, 'r', 20);
        addArrow(10, 3, false, 'u', 20);
        addArrow(10, 3, false, 'd', 20);
        addArrow(10, 3, false, 'l', 20);
        addArrow(10, 3, false, 'r', 20);
        addArrow(10, 3, false, 'u', 20);
        addArrow(10, 3, false, 'r', 20);
        addArrow(10, 3, false, 'l', 20);
    }
    
    private void genocideAttackTwelve() {
        addArrow(11, 3, false, 'u', 35);
        addArrow(11, 3, false, 'r', 35);
        addArrow(11, 3, false, 'l', 35);
        addArrow(11, 3, false, 'd', 35);
        addArrow(11, 3, true, 'd', 35);
        addArrow(11, 3, true, 'r', 35);
        addArrow(11, 3, true, 'l', 35);
        addArrow(11, 3, true, 'u', 35);
        addArrow(11, 3, false, 'l', 35);
        addArrow(11, 3, false, 'u', 35);
        addArrow(11, 3, false, 'd', 35);
        addArrow(11, 3, false, 'r', 35);
        addArrow(11, 3, true, 'l', 35);
        addArrow(11, 3, true, 'd', 35);
        addArrow(11, 3, true, 'u', 35);
        addArrow(11, 3, true, 'r', 35);
    }
    
    private void genocideAttackThirteen() {
        for(int i = 0; i < 15; ++ i)
            addArrow(12, 2, true, 'd', 30);
        addArrow(12, 2, true, 'd', 40);
        addArrow(12, 2, true, 'u', 40);
        addArrow(12, 2, true, 'r', 40);
        for(int i = 0; i < 15; ++ i)
            addArrow(12, 2, true, 'l', 30);
        addArrow(12, 2, true, 'l', 40);
        addArrow(12, 2, true, 'u', 40);
        addArrow(12, 2, true, 'd', 40);
        for(int i = 0; i < 15; ++ i)
            addArrow(12, 2, true, 'r', 30);
        addArrow(12, 2, true, 'r', 40);
        addArrow(12, 2, true, 'l', 40);
        addArrow(12, 2, true, 'd', 40);
        for(int i = 0; i < 15; ++ i)
            addArrow(12, 2, true, 'u', 30);
        addArrow(12, 2, true, 'l', 40);
        addArrow(12, 2, true, 'd', 40);
        addArrow(12, 2, true, 'r', 40);
        addArrow(12, 2, true, 'u', 45);
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
        prevChar = 'u';
        undyneAttacks = new ArrayList<ArrayList<Arrow>>();
        att = null;
    }
    
}
