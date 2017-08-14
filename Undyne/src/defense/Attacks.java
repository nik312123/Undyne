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
    
    public Attacks(boolean isGenocide, boolean survival) {
        for(int i = 0; i < 13000; ++i)
            undyneAttacks.add(new ArrayList<Arrow>());
        if(survival) {
            // Super Easy
            normalAttackOne();
            normalAttackTwo();
            survivalAttackThree();
            survivalAttackFour();
            survivalAttackFive();
            survivalAttackSix();
            survivalAttackSeven();
            
            // Easy
            ArrayList<Integer> easyAttacks = new ArrayList<Integer>();
            for(int i = 0; i < 7; ++i)
                easyAttacks.add(i);
            for(int i = 7; i <= 13; ++i) {
                int choice = easyAttacks.remove(rand.nextInt(easyAttacks.size()));
                switch(choice) {
                    case 0:
                        survivalAttackEight(i, rand.nextInt(4), false);
                        break;
                    case 1:
                        survivalAttackNine(i, rand.nextInt(4), false);
                        break;
                    case 2:
                        survivalAttackTen(i, rand.nextInt(4), false);
                        break;
                    case 3:
                        survivalAttackEleven(i, rand.nextInt(4), false);
                        break;
                    case 4:
                        survivalAttackTwelve(i, rand.nextInt(4), false);
                        break;
                    case 5:
                        survivalAttackThirteen(i, rand.nextInt(4), false);
                        break;
                    case 6:
                        survivalAttackFourteen(i, rand.nextInt(4), false);
                        break;
                }
            }
            survivalAttackFifteen(14, rand.nextInt(4), false);
            for(int i = 0; i < 4; ++i)
                easyAttacks.add(i);
            for(int i = 14; i <= 17; ++i) {
                int choice = easyAttacks.remove(rand.nextInt(easyAttacks.size()));
                switch(choice) {
                    case 0:
                        survivalAttackSixteen(i, rand.nextInt(4), false);
                        break;
                    case 1:
                        survivalAttackSeventeen(i, rand.nextInt(4), false);
                        break;
                    case 2:
                        survivalAttackEighteen(i, rand.nextInt(4), false);
                        break;
                    case 3:
                        survivalAttackNineteen(i, rand.nextInt(4), false);
                        break;
                }
            }
            int base = 18;
            for(int i = 0; i < 2; ++i) {
                for(int j = 0; j < 12; ++j)
                    easyAttacks.add(j);
                for(int j = base; j < base + 12; ++j) {
                    int choice = easyAttacks.remove(rand.nextInt(easyAttacks.size()));
                    switch(choice) {
                        case 0:
                            survivalAttackEight(j, rand.nextInt(4), easyAttacks.size() == 0 && i == 1);
                            break;
                        case 1:
                            survivalAttackNine(j, rand.nextInt(4), easyAttacks.size() == 0 && i == 1);
                            break;
                        case 2:
                            survivalAttackTen(j, rand.nextInt(4), easyAttacks.size() == 0 && i == 1);
                            break;
                        case 3:
                            survivalAttackEleven(j, rand.nextInt(4), easyAttacks.size() == 0 && i == 1);
                            break;
                        case 4:
                            survivalAttackTwelve(j, rand.nextInt(4), easyAttacks.size() == 0 && i == 1);
                            break;
                        case 5:
                            survivalAttackThirteen(j, rand.nextInt(4), easyAttacks.size() == 0 && i == 1);
                            break;
                        case 6:
                            survivalAttackFourteen(j, rand.nextInt(4), easyAttacks.size() == 0 && i == 1);
                            break;
                        case 7:
                            survivalAttackFifteen(j, rand.nextInt(4), easyAttacks.size() == 0 && i == 1);
                            break;
                        case 8:
                            survivalAttackSixteen(j, rand.nextInt(4), easyAttacks.size() == 0 && i == 1);
                            break;
                        case 9:
                            survivalAttackSeventeen(j, rand.nextInt(4), easyAttacks.size() == 0 && i == 1);
                            break;
                        case 10:
                            survivalAttackEighteen(j, rand.nextInt(4), easyAttacks.size() == 0 && i == 1);
                            break;
                        case 11:
                            survivalAttackNineteen(j, rand.nextInt(4), easyAttacks.size() == 0 && i == 1);
                            break;
                    }
                }
                base += 12;
            }
            easyAttacks = null;
            
            // Hard
            ArrayList<Integer> hardAttacks = new ArrayList<Integer>();
            base = 42;
            for(int i = 0; i < 3; ++i) {
                for(int j = 0; j < 12; ++j)
                    hardAttacks.add(j);
                for(int j = base; j < base + 12; ++j) {
                    int choice = hardAttacks.remove(rand.nextInt(hardAttacks.size()));
                    switch(choice) {
                        case 0:
                            survivalAttackTwenty(j, rand.nextInt(4), hardAttacks.size() == 0 && i == 2);
                            break;
                        case 1:
                            survivalAttackTwentyOne(j, rand.nextInt(4), hardAttacks.size() == 0 && i == 2);
                            break;
                        case 2:
                            survivalAttackTwentyTwo(j, hardAttacks.size() == 0 && i == 2);
                            break;
                        case 3:
                            survivalAttackTwentyThree(j, rand.nextInt(4), hardAttacks.size() == 0 && i == 2);
                            break;
                        case 4:
                            survivalAttackTwentyFour(j, rand.nextInt(4), hardAttacks.size() == 0 && i == 2);
                            break;
                        case 5:
                            survivalAttackTwentyFive(j, rand.nextInt(4), hardAttacks.size() == 0 && i == 2);
                            break;
                        case 6:
                            survivalAttackTwentySix(j, rand.nextInt(4), hardAttacks.size() == 0 && i == 2);
                            break;
                        case 7:
                            survivalAttackTwentySeven(j, rand.nextInt(4), hardAttacks.size() == 0 && i == 2);
                            break;
                        case 8:
                            survivalAttackTwentyEight(j, rand.nextInt(4), hardAttacks.size() == 0 && i == 2);
                            break;
                        case 9:
                            survivalAttackTwentyNine(j, rand.nextInt(4), hardAttacks.size() == 0 && i == 2);
                            break;
                        case 10:
                            survivalAttackThirty(j, rand.nextInt(4), hardAttacks.size() == 0 && i == 2);
                            break;
                        case 11:
                            survivalAttackThirtyOne(j, rand.nextInt(4), hardAttacks.size() == 0 && i == 2);
                            break;
                    }
                }
                base += 12;
            }
            hardAttacks = null;
            
            // WTH
            ArrayList<Integer> superHardAttacks = new ArrayList<Integer>();
            base = 78;
            for(int i = 0; i < 1000; ++i) {
                for(int j = 0; j < 12; ++j)
                    superHardAttacks.add(j);
                for(int j = base; j < base + 12; ++j) {
                    int choice = superHardAttacks.remove(rand.nextInt(superHardAttacks.size()));
                    switch(choice) {
                        case 0:
                            survivalAttackThirtyTwo(j, rand.nextInt(4));
                            break;
                        case 1:
                            survivalAttackThirtyThree(j, rand.nextInt(4));
                            break;
                        case 2:
                            survivalAttackThirtyFour(j, rand.nextInt(4));
                            break;
                        case 3:
                            survivalAttackThirtyFive(j, rand.nextInt(4));
                            break;
                        case 4:
                            survivalAttackThirtySix(j);
                            break;
                        case 5:
                            survivalAttackThirtySeven(j, rand.nextInt(4));
                            break;
                        case 6:
                            survivalAttackThirtyEight(j);
                            break;
                        case 7:
                            survivalAttackThirtyNine(j);
                            break;
                        case 8:
                            survivalAttackForty(j);
                            break;
                        case 9:
                            survivalAttackFortyOne(j);
                            break;
                        case 10:
                            survivalAttackFortyTwo(j, rand.nextInt(4));
                            break;
                        case 11:
                            survivalAttackFortyThree(j, rand.nextInt(4));
                            break;
                    }
                }
                base += 12;
            }
            superHardAttacks = null;
        }
        else if(!isGenocide) {
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
            genocideAttackThreeAndTen(18, 2, false);
            genocideAttackFour();
            genocideAttackFive();
            genocideAttackSix();
            genocideAttackSeven();
            genocideAttackEight();
            genocideAttackNine();
            genocideAttackThreeAndTen(23, 9, false);
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
    
    private void survivalAttackThree() {
        for(int i = 0; i < 2; ++i) {
            addArrow(2, 2, false, 'd', 45);
            addArrow(2, 2, false, 'r', 45);
            addArrow(2, 2, false, 'u', 45);
            addArrow(2, 2, false, 'l', 45);
        }
    }
    
    private void survivalAttackFour() {
        addArrow(3, 2, false, 'd', 40);
        addArrow(3, 2, false, 'd', 40);
        addArrow(3, 2, false, 'l', 40);
        addArrow(3, 2, false, 'u', 40);
        addArrow(3, 2, false, 'r', 40);
        addArrow(3, 2, false, 'l', 40);
        addArrow(3, 2, false, 'l', 40);
        addArrow(3, 2, false, 'd', 40);
    }
    
    private void survivalAttackFive() {
        for(int i = 0; i < 2; ++i) {
            addArrow(4, 2, false, 'd', 35);
            addArrow(4, 2, false, 'u', 35);
            addArrow(4, 2, false, 'r', 35);
            addArrow(4, 2, false, 'l', 35);
        }
        addArrow(4, 2, false, 'd', 35);
    }
    
    private void survivalAttackSix() {
        addArrow(5, 3, false, 'd', 45);
        addArrow(5, 3, false, 'l', 45);
        addArrow(5, 3, false, 'l', 45);
        addArrow(5, 3, false, 'r', 45);
        addArrow(5, 3, false, 'r', 45);
        addArrow(5, 3, false, 'u', 45);
        addArrow(5, 3, false, 'd', 45);
    }
    
    private void survivalAttackSeven() {
        addArrow(6, 2, false, 'u', 1);
        addArrow(6, 3, false, 'l', 120);
        addArrow(6, 2, false, 'd', 1);
        addArrow(6, 3, false, 'r', 120);
        addArrow(6, 2, false, 'u', 1);
        addArrow(6, 3, false, 'r', 120);
        addArrow(6, 2, false, 'd', 1);
        addArrow(6, 3, false, 'u', 120);
    }
    
    private void survivalAttackEight(int index, int shift, boolean isLastEasy) {
        switch(shift) {
            case 0:
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'r', 35);
                if(isLastEasy)
                    addArrow(index, 3, false, 'l', 120);
                else
                    addArrow(index, 3, false, 'l', 35);
                break;
            case 1:
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'd', 35);
                if(isLastEasy)
                    addArrow(index, 3, false, 'u', 120);
                else
                    addArrow(index, 3, false, 'u', 35);
                break;
            case 2:
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'l', 35);
                if(isLastEasy)
                    addArrow(index, 3, false, 'r', 120);
                else
                    addArrow(index, 3, false, 'r', 35);
                break;
            case 3:
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'u', 35);
                if(isLastEasy)
                    addArrow(index, 3, false, 'd', 120);
                else
                    addArrow(index, 3, false, 'd', 35);
                break;
        }
    }
    
    private void survivalAttackNine(int index, int shift, boolean isLastEasy) {
        switch(shift) {
            case 0:
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'u', 30);
                if(isLastEasy)
                    addArrow(index, 4, false, 'd', 120);
                else
                    addArrow(index, 4, false, 'd', 45);
                break;
            case 1:
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'r', 30);
                if(isLastEasy)
                    addArrow(index, 4, false, 'l', 120);
                else
                    addArrow(index, 4, false, 'l', 45);
                break;
            case 2:
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'd', 30);
                if(isLastEasy)
                    addArrow(index, 4, false, 'u', 120);
                else
                    addArrow(index, 4, false, 'u', 45);
                break;
            case 3:
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'l', 30);
                if(isLastEasy)
                    addArrow(index, 4, false, 'r', 120);
                else
                    addArrow(index, 4, false, 'r', 45);
                break;
        }
    }
    
    private void survivalAttackTen(int index, int shift, boolean isLastEasy) {
        switch(shift) {
            case 0:
                addArrow(index, 2, false, 'd', 45);
                addArrow(index, 2, false, 'u', 20);
                addArrow(index, 2, false, 'd', 45);
                addArrow(index, 2, false, 'u', 45);
                addArrow(index, 2, false, 'u', 20);
                addArrow(index, 2, false, 'd', 45);
                addArrow(index, 2, false, 'd', 45);
                addArrow(index, 2, false, 'u', 45);
                if(isLastEasy)
                    addArrow(index, 2, false, 'd', 120);
                else
                    addArrow(index, 2, false, 'd', 45);
                break;
            case 1:
                addArrow(index, 2, false, 'l', 45);
                addArrow(index, 2, false, 'r', 20);
                addArrow(index, 2, false, 'l', 45);
                addArrow(index, 2, false, 'r', 45);
                addArrow(index, 2, false, 'r', 20);
                addArrow(index, 2, false, 'l', 45);
                addArrow(index, 2, false, 'l', 45);
                addArrow(index, 2, false, 'r', 45);
                if(isLastEasy)
                    addArrow(index, 2, false, 'l', 120);
                else
                    addArrow(index, 2, false, 'l', 45);
                break;
            case 2:
                addArrow(index, 2, false, 'u', 45);
                addArrow(index, 2, false, 'd', 20);
                addArrow(index, 2, false, 'u', 45);
                addArrow(index, 2, false, 'd', 45);
                addArrow(index, 2, false, 'd', 20);
                addArrow(index, 2, false, 'u', 45);
                addArrow(index, 2, false, 'u', 45);
                addArrow(index, 2, false, 'd', 45);
                if(isLastEasy)
                    addArrow(index, 2, false, 'u', 120);
                else
                    addArrow(index, 2, false, 'u', 45);
                break;
            case 3:
                addArrow(index, 2, false, 'r', 45);
                addArrow(index, 2, false, 'l', 20);
                addArrow(index, 2, false, 'r', 45);
                addArrow(index, 2, false, 'l', 45);
                addArrow(index, 2, false, 'l', 20);
                addArrow(index, 2, false, 'r', 45);
                addArrow(index, 2, false, 'r', 45);
                addArrow(index, 2, false, 'l', 45);
                if(isLastEasy)
                    addArrow(index, 2, false, 'r', 120);
                else
                    addArrow(index, 2, false, 'r', 45);
                break;
        }
    }
    
    private void survivalAttackEleven(int index, int shift, boolean isLastEasy) {
        switch(shift) {
            case 0:
                addArrow(index, 3, false, 'r', 1);
                addArrow(index, 4, false, 'r', 1);
                addArrow(index, 5, false, 'r', 70);
                addArrow(index, 3, false, 'l', 1);
                addArrow(index, 4, false, 'l', 1);
                addArrow(index, 5, false, 'l', 45);
                addArrow(index, 4, false, 'u', 40);
                addArrow(index, 4, false, 'd', 40);
                addArrow(index, 4, false, 'u', 40);
                if(isLastEasy)
                    addArrow(index, 4, false, 'd', 120);
                else
                    addArrow(index, 4, false, 'd', 45);
                break;
            case 1:
                addArrow(index, 3, false, 'd', 1);
                addArrow(index, 4, false, 'd', 1);
                addArrow(index, 5, false, 'd', 70);
                addArrow(index, 3, false, 'u', 1);
                addArrow(index, 4, false, 'u', 1);
                addArrow(index, 5, false, 'u', 45);
                addArrow(index, 4, false, 'r', 40);
                addArrow(index, 4, false, 'l', 40);
                addArrow(index, 4, false, 'r', 40);
                if(isLastEasy)
                    addArrow(index, 4, false, 'l', 120);
                else
                    addArrow(index, 4, false, 'l', 45);
                break;
            case 2:
                addArrow(index, 3, false, 'l', 1);
                addArrow(index, 4, false, 'l', 1);
                addArrow(index, 5, false, 'l', 70);
                addArrow(index, 3, false, 'r', 1);
                addArrow(index, 4, false, 'r', 1);
                addArrow(index, 5, false, 'r', 45);
                addArrow(index, 4, false, 'd', 40);
                addArrow(index, 4, false, 'u', 40);
                addArrow(index, 4, false, 'd', 40);
                if(isLastEasy)
                    addArrow(index, 4, false, 'u', 120);
                else
                    addArrow(index, 4, false, 'u', 45);
                break;
            case 3:
                addArrow(index, 3, false, 'u', 1);
                addArrow(index, 4, false, 'u', 1);
                addArrow(index, 5, false, 'u', 70);
                addArrow(index, 3, false, 'd', 1);
                addArrow(index, 4, false, 'd', 1);
                addArrow(index, 5, false, 'd', 45);
                addArrow(index, 4, false, 'l', 40);
                addArrow(index, 4, false, 'r', 40);
                addArrow(index, 4, false, 'l', 40);
                if(isLastEasy)
                    addArrow(index, 4, false, 'r', 120);
                else
                    addArrow(index, 4, false, 'r', 45);
                break;
        }
    }
    
    private void survivalAttackTwelve(int index, int shift, boolean isLastEasy) {
        switch(shift) {
            case 0:
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'l', 60);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'l', 30);
                if(isLastEasy)
                    addArrow(index, 4, false, 'u', 120);
                else
                    addArrow(index, 4, false, 'u', 45);
                break;
            case 1:
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'u', 60);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'u', 30);
                if(isLastEasy)
                    addArrow(index, 4, false, 'r', 120);
                else
                    addArrow(index, 4, false, 'r', 45);
                break;
            case 2:
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'r', 60);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'r', 30);
                if(isLastEasy)
                    addArrow(index, 4, false, 'd', 120);
                else
                    addArrow(index, 4, false, 'd', 45);
                break;
            case 3:
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'r', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'u', 30);
                addArrow(index, 4, false, 'd', 60);
                addArrow(index, 4, false, 'l', 30);
                addArrow(index, 4, false, 'd', 30);
                addArrow(index, 4, false, 'd', 30);
                if(isLastEasy)
                    addArrow(index, 4, false, 'l', 120);
                else
                    addArrow(index, 4, false, 'l', 45);
                break;
        }
    }
    
    private void survivalAttackThirteen(int index, int shift, boolean isLastEasy) {
        switch(shift) {
            case 0:
                addArrow(index, 3, false, 'd', 30);
                addArrow(index, 3, false, 'd', 30);
                addArrow(index, 3, false, 'l', 20);
                addArrow(index, 3, false, 'l', 20);
                addArrow(index, 3, false, 'r', 30);
                addArrow(index, 3, false, 'u', 30);
                addArrow(index, 3, false, 'u', 30);
                addArrow(index, 3, false, 'r', 20);
                addArrow(index, 3, false, 'r', 20);
                if(isLastEasy)
                    addArrow(index, 3, false, 'l', 120);
                else
                    addArrow(index, 3, false, 'l', 45);
                break;
            case 1:
                addArrow(index, 3, false, 'l', 30);
                addArrow(index, 3, false, 'l', 30);
                addArrow(index, 3, false, 'u', 20);
                addArrow(index, 3, false, 'u', 20);
                addArrow(index, 3, false, 'd', 30);
                addArrow(index, 3, false, 'r', 30);
                addArrow(index, 3, false, 'r', 30);
                addArrow(index, 3, false, 'd', 20);
                addArrow(index, 3, false, 'd', 20);
                if(isLastEasy)
                    addArrow(index, 3, false, 'u', 120);
                else
                    addArrow(index, 3, false, 'u', 45);
                break;
            case 2:
                addArrow(index, 3, false, 'u', 30);
                addArrow(index, 3, false, 'u', 30);
                addArrow(index, 3, false, 'r', 20);
                addArrow(index, 3, false, 'r', 20);
                addArrow(index, 3, false, 'l', 30);
                addArrow(index, 3, false, 'd', 30);
                addArrow(index, 3, false, 'd', 30);
                addArrow(index, 3, false, 'l', 20);
                addArrow(index, 3, false, 'l', 20);
                if(isLastEasy)
                    addArrow(index, 3, false, 'r', 120);
                else
                    addArrow(index, 3, false, 'r', 45);
                break;
            case 3:
                addArrow(index, 3, false, 'r', 30);
                addArrow(index, 3, false, 'r', 30);
                addArrow(index, 3, false, 'd', 20);
                addArrow(index, 3, false, 'd', 20);
                addArrow(index, 3, false, 'u', 30);
                addArrow(index, 3, false, 'l', 30);
                addArrow(index, 3, false, 'l', 30);
                addArrow(index, 3, false, 'u', 20);
                addArrow(index, 3, false, 'u', 20);
                if(isLastEasy)
                    addArrow(index, 3, false, 'd', 120);
                else
                    addArrow(index, 3, false, 'd', 45);
                break;
        }
    }
    
    private void survivalAttackFourteen(int index, int shift, boolean isLastEasy) {
        switch(shift) {
            case 0:
                addSlowArrow(index, false, 'r', 220);
                addArrow(index, 3, false, 'd', 25);
                addArrow(index, 3, false, 'l', 25);
                addArrow(index, 3, false, 'u', 25);
                addArrow(index, 3, false, 'd', 25);
                addArrow(index, 3, false, 'l', 25);
                if(isLastEasy)
                    addArrow(index, 3, false, 'u', 120);
                else
                    addArrow(index, 3, false, 'u', 45);
                break;
            case 1:
                addSlowArrow(index, false, 'd', 220);
                addArrow(index, 3, false, 'l', 25);
                addArrow(index, 3, false, 'u', 25);
                addArrow(index, 3, false, 'r', 25);
                addArrow(index, 3, false, 'l', 25);
                addArrow(index, 3, false, 'u', 25);
                if(isLastEasy)
                    addArrow(index, 3, false, 'r', 120);
                else
                    addArrow(index, 3, false, 'r', 45);
                break;
            case 2:
                addSlowArrow(index, false, 'l', 220);
                addArrow(index, 3, false, 'u', 25);
                addArrow(index, 3, false, 'r', 25);
                addArrow(index, 3, false, 'd', 25);
                addArrow(index, 3, false, 'u', 25);
                addArrow(index, 3, false, 'r', 25);
                if(isLastEasy)
                    addArrow(index, 3, false, 'd', 120);
                else
                    addArrow(index, 3, false, 'd', 45);
                break;
            case 3:
                addSlowArrow(index, false, 'u', 220);
                addArrow(index, 3, false, 'r', 25);
                addArrow(index, 3, false, 'd', 25);
                addArrow(index, 3, false, 'l', 25);
                addArrow(index, 3, false, 'r', 25);
                addArrow(index, 3, false, 'd', 25);
                if(isLastEasy)
                    addArrow(index, 3, false, 'l', 120);
                else
                    addArrow(index, 3, false, 'l', 45);
                break;
        }
    }
    
    private void survivalAttackFifteen(int index, int shift, boolean isLastEasy) {
        switch(shift) {
            case 0:
                addArrow(index, 2, false, 'l', 55);
                addArrow(index, 2, false, 'r', 55);
                addArrow(index, 2, false, 'l', 55);
                addArrow(index, 2, false, 'r', 65);
                if(isLastEasy)
                    addArrow(index, 2, true, 'l', 180);
                else
                    addArrow(index, 2, true, 'l', 120);
                break;
            case 1:
                addArrow(index, 2, false, 'u', 55);
                addArrow(index, 2, false, 'd', 55);
                addArrow(index, 2, false, 'u', 55);
                addArrow(index, 2, false, 'd', 65);
                if(isLastEasy)
                    addArrow(index, 2, true, 'u', 180);
                else
                    addArrow(index, 2, true, 'u', 120);
                break;
            case 2:
                addArrow(index, 2, false, 'r', 55);
                addArrow(index, 2, false, 'l', 55);
                addArrow(index, 2, false, 'r', 55);
                addArrow(index, 2, false, 'l', 65);
                if(isLastEasy)
                    addArrow(index, 2, true, 'r', 180);
                else
                    addArrow(index, 2, true, 'r', 120);
                break;
            case 3:
                addArrow(index, 2, false, 'd', 55);
                addArrow(index, 2, false, 'u', 55);
                addArrow(index, 2, false, 'd', 55);
                addArrow(index, 2, false, 'u', 65);
                if(isLastEasy)
                    addArrow(index, 2, true, 'd', 180);
                else
                    addArrow(index, 2, true, 'd', 120);
                break;
        }
    }
    
    private void survivalAttackSixteen(int index, int shift, boolean isLastEasy) {
        switch(shift) {
            case 0:
                addArrow(index, 2, false, 'r', 45);
                addArrow(index, 2, false, 'l', 75);
                addArrow(index, 2, true, 'u', 45);
                addArrow(index, 2, false, 'u', 45);
                addArrow(index, 2, false, 'r', 45);
                addArrow(index, 2, false, 'l', 45);
                addArrow(index, 2, false, 'd', 75);
                if(isLastEasy)
                    addArrow(index, 2, true, 'u', 120);
                else
                    addArrow(index, 2, true, 'u', 45);
                break;
            case 1:
                addArrow(index, 2, false, 'd', 45);
                addArrow(index, 2, false, 'u', 75);
                addArrow(index, 2, true, 'r', 45);
                addArrow(index, 2, false, 'r', 45);
                addArrow(index, 2, false, 'd', 45);
                addArrow(index, 2, false, 'u', 45);
                addArrow(index, 2, false, 'l', 75);
                if(isLastEasy)
                    addArrow(index, 2, true, 'r', 120);
                else
                    addArrow(index, 2, true, 'r', 45);
                break;
            case 2:
                addArrow(index, 2, false, 'l', 45);
                addArrow(index, 2, false, 'r', 75);
                addArrow(index, 2, true, 'd', 45);
                addArrow(index, 2, false, 'd', 45);
                addArrow(index, 2, false, 'l', 45);
                addArrow(index, 2, false, 'r', 45);
                addArrow(index, 2, false, 'u', 75);
                if(isLastEasy)
                    addArrow(index, 2, true, 'd', 120);
                else
                    addArrow(index, 2, true, 'd', 45);
                break;
            case 3:
                addArrow(index, 2, false, 'u', 45);
                addArrow(index, 2, false, 'd', 75);
                addArrow(index, 2, true, 'l', 45);
                addArrow(index, 2, false, 'l', 45);
                addArrow(index, 2, false, 'u', 45);
                addArrow(index, 2, false, 'd', 45);
                addArrow(index, 2, false, 'r', 75);
                if(isLastEasy)
                    addArrow(index, 2, true, 'l', 120);
                else
                    addArrow(index, 2, true, 'l', 45);
                break;
        }
    }
    
    private void survivalAttackSeventeen(int index, int shift, boolean isLastEasy) {
        switch(shift) {
            case 0:
                addArrow(index, 3, false, 'r', 45);
                addArrow(index, 3, false, 'u', 45);
                addArrow(index, 3, false, 'l', 45);
                addArrow(index, 3, false, 'd', 125);
                addArrow(index, 3, true, 'l', 45);
                addArrow(index, 3, true, 'd', 45);
                addArrow(index, 3, true, 'r', 45);
                if(isLastEasy)
                    addArrow(index, 3, true, 'u', 120);
                else
                    addArrow(index, 3, true, 'u', 45);
                break;
            case 1:
                addArrow(index, 3, false, 'd', 45);
                addArrow(index, 3, false, 'r', 45);
                addArrow(index, 3, false, 'u', 45);
                addArrow(index, 3, false, 'l', 125);
                addArrow(index, 3, true, 'u', 45);
                addArrow(index, 3, true, 'l', 45);
                addArrow(index, 3, true, 'd', 45);
                if(isLastEasy)
                    addArrow(index, 3, true, 'r', 120);
                else
                    addArrow(index, 3, true, 'r', 45);
                break;
            case 2:
                addArrow(index, 3, false, 'l', 45);
                addArrow(index, 3, false, 'd', 45);
                addArrow(index, 3, false, 'r', 45);
                addArrow(index, 3, false, 'u', 125);
                addArrow(index, 3, true, 'r', 45);
                addArrow(index, 3, true, 'u', 45);
                addArrow(index, 3, true, 'l', 45);
                if(isLastEasy)
                    addArrow(index, 3, true, 'd', 120);
                else
                    addArrow(index, 3, true, 'd', 45);
                break;
            case 3:
                addArrow(index, 3, false, 'u', 45);
                addArrow(index, 3, false, 'l', 45);
                addArrow(index, 3, false, 'd', 45);
                addArrow(index, 3, false, 'r', 125);
                addArrow(index, 3, true, 'd', 45);
                addArrow(index, 3, true, 'r', 45);
                addArrow(index, 3, true, 'u', 45);
                if(isLastEasy)
                    addArrow(index, 3, true, 'l', 120);
                else
                    addArrow(index, 3, true, 'l', 45);
                break;
        }
    }
    
    private void survivalAttackEighteen(int index, int shift, boolean isLastEasy) {
        switch(shift) {
            case 0:
                for(int i = 0; i < 2; ++i) {
                    addArrow(index, 2, true, 'l', 33);
                    addArrow(index, 2, true, 'd', 33);
                    addArrow(index, 2, true, 'r', 33);
                    addArrow(index, 2, true, 'u', 33);
                }
                addArrow(index, 2, true, 'l', 33);
                addArrow(index, 2, true, 'd', 33);
                addArrow(index, 2, true, 'r', 33);
                if(isLastEasy)
                    addArrow(index, 2, true, 'u', 120);
                else
                    addArrow(index, 2, true, 'u', 33);
                break;
            case 1:
                for(int i = 0; i < 2; ++i) {
                    addArrow(index, 2, true, 'u', 33);
                    addArrow(index, 2, true, 'l', 33);
                    addArrow(index, 2, true, 'd', 33);
                    addArrow(index, 2, true, 'r', 33);
                }
                addArrow(index, 2, true, 'u', 33);
                addArrow(index, 2, true, 'l', 33);
                addArrow(index, 2, true, 'd', 33);
                if(isLastEasy)
                    addArrow(index, 2, true, 'r', 120);
                else
                    addArrow(index, 2, true, 'r', 33);
                break;
            case 2:
                for(int i = 0; i < 2; ++i) {
                    addArrow(index, 2, true, 'r', 33);
                    addArrow(index, 2, true, 'u', 33);
                    addArrow(index, 2, true, 'l', 33);
                    addArrow(index, 2, true, 'd', 33);
                }
                addArrow(index, 2, true, 'r', 33);
                addArrow(index, 2, true, 'u', 33);
                addArrow(index, 2, true, 'l', 33);
                if(isLastEasy)
                    addArrow(index, 2, true, 'd', 120);
                else
                    addArrow(index, 2, true, 'd', 33);
                break;
            case 3:
                for(int i = 0; i < 2; ++i) {
                    addArrow(index, 2, true, 'd', 33);
                    addArrow(index, 2, true, 'r', 33);
                    addArrow(index, 2, true, 'u', 33);
                    addArrow(index, 2, true, 'l', 33);
                }
                addArrow(index, 2, true, 'd', 33);
                addArrow(index, 2, true, 'r', 33);
                addArrow(index, 2, true, 'u', 33);
                if(isLastEasy)
                    addArrow(index, 2, true, 'l', 120);
                else
                    addArrow(index, 2, true, 'l', 33);
                break;
        }
    }
    
    private void survivalAttackNineteen(int index, int shift, boolean isLastEasy) {
        switch(shift) {
            case 0:
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, false, 'r', 15);
                addArrow(index, 2, false, 'r', 15);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, true, 'l', 25);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, true, 'r', 25);
                addArrow(index, 2, false, 'l', 25);
                if(isLastEasy)
                    addArrow(index, 2, true, 'r', 120);
                else
                    addArrow(index, 2, true, 'r', 45);
                break;
            case 1:
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, false, 'd', 15);
                addArrow(index, 2, false, 'd', 15);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, true, 'u', 25);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, true, 'd', 25);
                addArrow(index, 2, false, 'u', 25);
                if(isLastEasy)
                    addArrow(index, 2, true, 'd', 120);
                else
                    addArrow(index, 2, true, 'd', 45);
                break;
            case 2:
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, false, 'l', 15);
                addArrow(index, 2, false, 'l', 15);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, true, 'r', 25);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, true, 'l', 25);
                addArrow(index, 2, false, 'r', 25);
                if(isLastEasy)
                    addArrow(index, 2, true, 'l', 120);
                else
                    addArrow(index, 2, true, 'l', 45);
                break;
            case 3:
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, false, 'u', 15);
                addArrow(index, 2, false, 'u', 15);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, true, 'd', 25);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, true, 'u', 25);
                addArrow(index, 2, false, 'd', 25);
                if(isLastEasy)
                    addArrow(index, 2, true, 'u', 120);
                else
                    addArrow(index, 2, true, 'u', 45);
                break;
        }
    }
    
    private void survivalAttackTwenty(int index, int shift, boolean isLastHard) {
        switch(shift) {
            case 0:
                addArrow(index, 2, false, 'd', 55);
                addArrow(index, 2, false, 'd', 55);
                addArrow(index, 2, false, 'd', 130);
                addArrow(index, 5, false, 'l', 25);
                addArrow(index, 5, false, 'u', 25);
                addArrow(index, 5, false, 'r', 25);
                addArrow(index, 5, false, 'd', 25);
                addArrow(index, 5, false, 'r', 25);
                addArrow(index, 5, false, 'u', 25);
                addArrow(index, 5, false, 'l', 25);
                addArrow(index, 5, false, 'u', 25);
                addArrow(index, 5, false, 'r', 25);
                if(isLastHard)
                    addArrow(index, 5, false, 'd', 120);
                else
                    addArrow(index, 5, false, 'd', 45);
                break;
            case 1:
                addArrow(index, 2, false, 'l', 55);
                addArrow(index, 2, false, 'l', 55);
                addArrow(index, 2, false, 'l', 130);
                addArrow(index, 5, false, 'u', 25);
                addArrow(index, 5, false, 'r', 25);
                addArrow(index, 5, false, 'd', 25);
                addArrow(index, 5, false, 'l', 25);
                addArrow(index, 5, false, 'd', 25);
                addArrow(index, 5, false, 'r', 25);
                addArrow(index, 5, false, 'u', 25);
                addArrow(index, 5, false, 'r', 25);
                addArrow(index, 5, false, 'd', 25);
                if(isLastHard)
                    addArrow(index, 5, false, 'l', 120);
                else
                    addArrow(index, 5, false, 'l', 45);
                break;
            case 2:
                addArrow(index, 2, false, 'u', 55);
                addArrow(index, 2, false, 'u', 55);
                addArrow(index, 2, false, 'u', 130);
                addArrow(index, 5, false, 'r', 25);
                addArrow(index, 5, false, 'd', 25);
                addArrow(index, 5, false, 'l', 25);
                addArrow(index, 5, false, 'u', 25);
                addArrow(index, 5, false, 'l', 25);
                addArrow(index, 5, false, 'd', 25);
                addArrow(index, 5, false, 'r', 25);
                addArrow(index, 5, false, 'd', 25);
                addArrow(index, 5, false, 'l', 25);
                if(isLastHard)
                    addArrow(index, 5, false, 'u', 120);
                else
                    addArrow(index, 5, false, 'u', 45);
                break;
            case 3:
                addArrow(index, 2, false, 'r', 55);
                addArrow(index, 2, false, 'r', 55);
                addArrow(index, 2, false, 'r', 130);
                addArrow(index, 5, false, 'd', 25);
                addArrow(index, 5, false, 'l', 25);
                addArrow(index, 5, false, 'u', 25);
                addArrow(index, 5, false, 'r', 25);
                addArrow(index, 5, false, 'u', 25);
                addArrow(index, 5, false, 'l', 25);
                addArrow(index, 5, false, 'd', 25);
                addArrow(index, 5, false, 'l', 25);
                addArrow(index, 5, false, 'u', 25);
                if(isLastHard)
                    addArrow(index, 5, false, 'r', 120);
                else
                    addArrow(index, 5, false, 'r', 45);
                break;
        }
    }
    
    private void survivalAttackTwentyOne(int index, int shift, boolean isLastHard) {
        switch(shift) {
            case 0:
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, false, 'r', 15);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, false, 'l', 15);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, false, 'l', 25);
                if(isLastHard)
                    addArrow(index, 4, false, 'r', 120);
                else
                    addArrow(index, 4, false, 'r', 45);
                break;
            case 1:
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, false, 'd', 15);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, false, 'u', 15);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, false, 'u', 25);
                if(isLastHard)
                    addArrow(index, 4, false, 'd', 120);
                else
                    addArrow(index, 4, false, 'd', 45);
                break;
            case 2:
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, false, 'l', 15);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, false, 'r', 15);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, false, 'r', 25);
                if(isLastHard)
                    addArrow(index, 4, false, 'l', 120);
                else
                    addArrow(index, 4, false, 'l', 45);
                break;
            case 3:
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, false, 'u', 15);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, false, 'd', 15);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, false, 'd', 25);
                if(isLastHard)
                    addArrow(index, 4, false, 'u', 120);
                else
                    addArrow(index, 4, false, 'u', 45);
                break;
        }
    }
    
    private void survivalAttackTwentyTwo(int index, boolean isLastHard) {
        genocideAttackThreeAndTen(22, index, isLastHard);
    }
    
    private void survivalAttackTwentyThree(int index, int shift, boolean isLastHard) {
        switch(shift) {
            case 0:
                addArrow(index, 5, false, 'r', 10);
                addArrow(index, 4, false, 'd', 40);
                addArrow(index, 5, false, 'l', 10);
                addArrow(index, 4, false, 'u', 35);
                addArrow(index, 4, false, 'r', 1);
                addArrow(index, 1, false, 'd', 30);
                addArrow(index, 1, false, 'u', 25);
                if(isLastHard)
                    addArrow(index, 5, false, 'l', 120);
                else
                    addArrow(index, 5, false, 'l', 70);
                break;
            case 1:
                addArrow(index, 5, false, 'd', 10);
                addArrow(index, 4, false, 'l', 40);
                addArrow(index, 5, false, 'u', 10);
                addArrow(index, 4, false, 'r', 35);
                addArrow(index, 4, false, 'd', 1);
                addArrow(index, 1, false, 'l', 30);
                addArrow(index, 1, false, 'r', 25);
                if(isLastHard)
                    addArrow(index, 5, false, 'u', 120);
                else
                    addArrow(index, 5, false, 'u', 70);
                break;
            case 2:
                addArrow(index, 5, false, 'l', 10);
                addArrow(index, 4, false, 'u', 40);
                addArrow(index, 5, false, 'r', 10);
                addArrow(index, 4, false, 'd', 35);
                addArrow(index, 4, false, 'l', 1);
                addArrow(index, 1, false, 'u', 30);
                addArrow(index, 1, false, 'd', 25);
                if(isLastHard)
                    addArrow(index, 5, false, 'r', 120);
                else
                    addArrow(index, 5, false, 'r', 70);
                break;
            case 3:
                addArrow(index, 5, false, 'u', 10);
                addArrow(index, 4, false, 'r', 40);
                addArrow(index, 5, false, 'd', 10);
                addArrow(index, 4, false, 'l', 35);
                addArrow(index, 4, false, 'u', 1);
                addArrow(index, 1, false, 'r', 30);
                addArrow(index, 1, false, 'l', 25);
                if(isLastHard)
                    addArrow(index, 5, false, 'd', 120);
                else
                    addArrow(index, 5, false, 'd', 70);
                break;
        }
    }
    
    private void survivalAttackTwentyFour(int index, int shift, boolean isLastHard) {
        switch(shift) {
            case 0:
                addArrow(index, 2, false, 'd', 1);
                addArrow(index, 3, true, 'd', 80);
                addArrow(index, 2, false, 'r', 1);
                addArrow(index, 3, true, 'r', 80);
                addArrow(index, 2, false, 'u', 1);
                addArrow(index, 3, true, 'u', 80);
                addArrow(index, 2, false, 'r', 1);
                addArrow(index, 3, true, 'r', 80);
                addArrow(index, 2, false, 'l', 1);
                addArrow(index, 3, true, 'l', 80);
                addArrow(index, 2, false, 'd', 1);
                addArrow(index, 3, true, 'd', 80);
                addArrow(index, 2, false, 'd', 1);
                addArrow(index, 3, true, 'd', 80);
                addArrow(index, 2, false, 'u', 1);
                if(isLastHard)
                    addArrow(index, 3, true, 'u', 45);
                else
                    addArrow(index, 3, true, 'u', 80);
                break;
            case 1:
                addArrow(index, 2, false, 'l', 1);
                addArrow(index, 3, true, 'l', 80);
                addArrow(index, 2, false, 'd', 1);
                addArrow(index, 3, true, 'd', 80);
                addArrow(index, 2, false, 'r', 1);
                addArrow(index, 3, true, 'r', 80);
                addArrow(index, 2, false, 'd', 1);
                addArrow(index, 3, true, 'd', 80);
                addArrow(index, 2, false, 'u', 1);
                addArrow(index, 3, true, 'u', 80);
                addArrow(index, 2, false, 'l', 1);
                addArrow(index, 3, true, 'l', 80);
                addArrow(index, 2, false, 'l', 1);
                addArrow(index, 3, true, 'l', 80);
                addArrow(index, 2, false, 'r', 1);
                if(isLastHard)
                    addArrow(index, 3, true, 'r', 45);
                else
                    addArrow(index, 3, true, 'r', 80);
                break;
            case 2:
                addArrow(index, 2, false, 'u', 1);
                addArrow(index, 3, true, 'u', 80);
                addArrow(index, 2, false, 'l', 1);
                addArrow(index, 3, true, 'l', 80);
                addArrow(index, 2, false, 'd', 1);
                addArrow(index, 3, true, 'd', 80);
                addArrow(index, 2, false, 'l', 1);
                addArrow(index, 3, true, 'l', 80);
                addArrow(index, 2, false, 'r', 1);
                addArrow(index, 3, true, 'r', 80);
                addArrow(index, 2, false, 'u', 1);
                addArrow(index, 3, true, 'u', 80);
                addArrow(index, 2, false, 'u', 1);
                addArrow(index, 3, true, 'u', 80);
                addArrow(index, 2, false, 'd', 1);
                if(isLastHard)
                    addArrow(index, 3, true, 'd', 45);
                else
                    addArrow(index, 3, true, 'd', 80);
                break;
            case 3:
                addArrow(index, 2, false, 'r', 1);
                addArrow(index, 3, true, 'r', 80);
                addArrow(index, 2, false, 'u', 1);
                addArrow(index, 3, true, 'u', 80);
                addArrow(index, 2, false, 'l', 1);
                addArrow(index, 3, true, 'l', 80);
                addArrow(index, 2, false, 'u', 1);
                addArrow(index, 3, true, 'u', 80);
                addArrow(index, 2, false, 'd', 1);
                addArrow(index, 3, true, 'd', 80);
                addArrow(index, 2, false, 'r', 1);
                addArrow(index, 3, true, 'r', 80);
                addArrow(index, 2, false, 'r', 1);
                addArrow(index, 3, true, 'r', 80);
                addArrow(index, 2, false, 'l', 1);
                if(isLastHard)
                    addArrow(index, 3, true, 'l', 45);
                else
                    addArrow(index, 3, true, 'l', 80);
                break;
        }
    }
    
    private void survivalAttackTwentyFive(int index, int shift, boolean isLastHard) {
        switch(shift) {
            case 0:
                for(int i = 0; i < 3; ++i) {
                    addArrow(index, 3, false, 'r', 28);
                    addArrow(index, 3, false, 'd', 28);
                }
                addArrow(index, 3, false, 'r', 28);
                addArrow(index, 3, false, 'd', 50);
                for(int i = 0; i < 2; ++i) {
                    addArrow(index, 3, true, 'r', 28);
                    addArrow(index, 3, true, 'd', 28);
                }
                addArrow(index, 3, true, 'r', 28);
                if(isLastHard)
                    addArrow(index, 3, true, 'd', 120);
                else
                    addArrow(index, 3, true, 'd', 28);
                break;
            case 1:
                for(int i = 0; i < 3; ++i) {
                    addArrow(index, 3, false, 'd', 28);
                    addArrow(index, 3, false, 'l', 28);
                }
                addArrow(index, 3, false, 'd', 28);
                addArrow(index, 3, false, 'l', 50);
                for(int i = 0; i < 2; ++i) {
                    addArrow(index, 3, true, 'd', 28);
                    addArrow(index, 3, true, 'l', 28);
                }
                addArrow(index, 3, true, 'd', 28);
                if(isLastHard)
                    addArrow(index, 3, true, 'l', 120);
                else
                    addArrow(index, 3, true, 'l', 28);
                break;
            case 2:
                for(int i = 0; i < 3; ++i) {
                    addArrow(index, 3, false, 'l', 28);
                    addArrow(index, 3, false, 'u', 28);
                }
                addArrow(index, 3, false, 'l', 28);
                addArrow(index, 3, false, 'u', 50);
                for(int i = 0; i < 2; ++i) {
                    addArrow(index, 3, true, 'l', 28);
                    addArrow(index, 3, true, 'u', 28);
                }
                addArrow(index, 3, true, 'l', 28);
                if(isLastHard)
                    addArrow(index, 3, true, 'u', 120);
                else
                    addArrow(index, 3, true, 'u', 28);
                break;
            case 3:
                for(int i = 0; i < 3; ++i) {
                    addArrow(index, 3, false, 'u', 28);
                    addArrow(index, 3, false, 'r', 28);
                }
                addArrow(index, 3, false, 'u', 28);
                addArrow(index, 3, false, 'r', 50);
                for(int i = 0; i < 2; ++i) {
                    addArrow(index, 3, true, 'u', 28);
                    addArrow(index, 3, true, 'r', 28);
                }
                addArrow(index, 3, true, 'u', 28);
                if(isLastHard)
                    addArrow(index, 3, true, 'r', 120);
                else
                    addArrow(index, 3, true, 'r', 28);
                break;
        }
    }
    
    private void survivalAttackTwentySix(int index, int shift, boolean isLastHard) {
        switch(shift) {
            case 0:
                addArrow(index, 3, true, 'l', 35);
                addArrow(index, 3, true, 'd', 35);
                addArrow(index, 3, true, 'r', 35);
                addArrow(index, 3, true, 'u', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'l', 50);
                addArrow(index, 3, true, 'u', 35);
                addArrow(index, 3, true, 'r', 35);
                addArrow(index, 3, true, 'l', 35);
                if(isLastHard)
                    addArrow(index, 3, true, 'd', 120);
                else
                    addArrow(index, 3, true, 'd', 35);
                break;
            case 1:
                addArrow(index, 3, true, 'u', 35);
                addArrow(index, 3, true, 'l', 35);
                addArrow(index, 3, true, 'd', 35);
                addArrow(index, 3, true, 'r', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'u', 50);
                addArrow(index, 3, true, 'r', 35);
                addArrow(index, 3, true, 'd', 35);
                addArrow(index, 3, true, 'u', 35);
                if(isLastHard)
                    addArrow(index, 3, true, 'l', 120);
                else
                    addArrow(index, 3, true, 'l', 35);
                break;
            case 2:
                addArrow(index, 3, true, 'r', 35);
                addArrow(index, 3, true, 'u', 35);
                addArrow(index, 3, true, 'l', 35);
                addArrow(index, 3, true, 'd', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'r', 50);
                addArrow(index, 3, true, 'd', 35);
                addArrow(index, 3, true, 'l', 35);
                addArrow(index, 3, true, 'r', 35);
                if(isLastHard)
                    addArrow(index, 3, true, 'u', 120);
                else
                    addArrow(index, 3, true, 'u', 35);
                break;
            case 3:
                addArrow(index, 3, true, 'd', 35);
                addArrow(index, 3, true, 'r', 35);
                addArrow(index, 3, true, 'u', 35);
                addArrow(index, 3, true, 'l', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'd', 50);
                addArrow(index, 3, true, 'l', 35);
                addArrow(index, 3, true, 'u', 35);
                addArrow(index, 3, true, 'd', 35);
                if(isLastHard)
                    addArrow(index, 3, true, 'r', 120);
                else
                    addArrow(index, 3, true, 'r', 35);
                break;
        }
    }
    
    private void survivalAttackTwentySeven(int index, int shift, boolean isLastHard) {
        switch(shift) {
            case 0:
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, true, 'u', 25);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, true, 'd', 25);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, true, 'u', 25);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, false, 'u', 25);
                if(isLastHard)
                    addArrow(index, 2, true, 'd', 120);
                else
                    addArrow(index, 2, true, 'd', 25);
                break;
            case 1:
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, true, 'r', 25);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, true, 'l', 25);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, true, 'r', 25);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, false, 'r', 25);
                if(isLastHard)
                    addArrow(index, 2, true, 'l', 120);
                else
                    addArrow(index, 2, true, 'l', 25);
                break;
            case 2:
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, true, 'd', 25);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, true, 'u', 25);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, true, 'd', 25);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, false, 'd', 25);
                if(isLastHard)
                    addArrow(index, 2, true, 'u', 120);
                else
                    addArrow(index, 2, true, 'u', 25);
                break;
            case 3:
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, true, 'l', 25);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, false, 'l', 25);
                addArrow(index, 2, true, 'r', 25);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, false, 'r', 25);
                addArrow(index, 2, true, 'l', 25);
                addArrow(index, 2, false, 'u', 25);
                addArrow(index, 2, false, 'd', 25);
                addArrow(index, 2, false, 'l', 25);
                if(isLastHard)
                    addArrow(index, 2, true, 'r', 120);
                else
                    addArrow(index, 2, true, 'r', 25);
                break;
        }
    }
    
    private void survivalAttackTwentyEight(int index, int shift, boolean isLastHard) {
        switch(shift) {
            case 0:
                addArrow(index, 2, false, 'r', 40);
                addArrow(index, 2, true, 'r', 35);
                addArrow(index, 2, false, 'u', 40);
                addArrow(index, 2, true, 'u', 35);
                addArrow(index, 2, false, 'l', 40);
                addArrow(index, 2, true, 'l', 35);
                addArrow(index, 2, false, 'd', 40);
                addArrow(index, 2, true, 'd', 35);
                addArrow(index, 2, false, 'r', 40);
                addArrow(index, 2, true, 'r', 35);
                addArrow(index, 2, false, 'u', 40);
                addArrow(index, 2, true, 'u', 35);
                addArrow(index, 2, false, 'l', 40);
                if(isLastHard)
                    addArrow(index, 2, true, 'l', 120);
                else
                    addArrow(index, 2, true, 'l', 35);
                break;
            case 1:
                addArrow(index, 2, false, 'd', 40);
                addArrow(index, 2, true, 'd', 35);
                addArrow(index, 2, false, 'r', 40);
                addArrow(index, 2, true, 'r', 35);
                addArrow(index, 2, false, 'u', 40);
                addArrow(index, 2, true, 'u', 35);
                addArrow(index, 2, false, 'l', 40);
                addArrow(index, 2, true, 'l', 35);
                addArrow(index, 2, false, 'd', 40);
                addArrow(index, 2, true, 'd', 35);
                addArrow(index, 2, false, 'r', 40);
                addArrow(index, 2, true, 'r', 35);
                addArrow(index, 2, false, 'u', 40);
                if(isLastHard)
                    addArrow(index, 2, true, 'u', 120);
                else
                    addArrow(index, 2, true, 'u', 35);
                break;
            case 2:
                addArrow(index, 2, false, 'l', 40);
                addArrow(index, 2, true, 'l', 35);
                addArrow(index, 2, false, 'd', 40);
                addArrow(index, 2, true, 'd', 35);
                addArrow(index, 2, false, 'r', 40);
                addArrow(index, 2, true, 'r', 35);
                addArrow(index, 2, false, 'u', 40);
                addArrow(index, 2, true, 'u', 35);
                addArrow(index, 2, false, 'l', 40);
                addArrow(index, 2, true, 'l', 35);
                addArrow(index, 2, false, 'd', 40);
                addArrow(index, 2, true, 'd', 35);
                addArrow(index, 2, false, 'r', 40);
                if(isLastHard)
                    addArrow(index, 2, true, 'r', 120);
                else
                    addArrow(index, 2, true, 'r', 35);
                break;
            case 3:
                addArrow(index, 2, false, 'u', 40);
                addArrow(index, 2, true, 'u', 35);
                addArrow(index, 2, false, 'l', 40);
                addArrow(index, 2, true, 'l', 35);
                addArrow(index, 2, false, 'd', 40);
                addArrow(index, 2, true, 'd', 35);
                addArrow(index, 2, false, 'r', 40);
                addArrow(index, 2, true, 'r', 35);
                addArrow(index, 2, false, 'u', 40);
                addArrow(index, 2, true, 'u', 35);
                addArrow(index, 2, false, 'l', 40);
                addArrow(index, 2, true, 'l', 35);
                addArrow(index, 2, false, 'd', 40);
                if(isLastHard)
                    addArrow(index, 2, true, 'd', 120);
                else
                    addArrow(index, 2, true, 'd', 35);
                break;
        }
    }
    
    private void survivalAttackTwentyNine(int index, int shift, boolean isLastHard) {
        switch(shift) {
            case 0:
                addArrow(index, 3, false, 'd', 20);
                addArrow(index, 3, false, 'l', 20);
                addArrow(index, 3, false, 'r', 20);
                addArrow(index, 3, false, 'u', 20);
                addArrow(index, 3, false, 'd', 20);
                addArrow(index, 3, false, 'l', 20);
                addArrow(index, 3, false, 'r', 20);
                addArrow(index, 3, false, 'u', 20);
                addArrow(index, 3, false, 'r', 20);
                if(isLastHard)
                    addArrow(index, 3, false, 'l', 120);
                else
                    addArrow(index, 3, false, 'l', 20);
                break;
            case 1:
                addArrow(index, 3, false, 'l', 20);
                addArrow(index, 3, false, 'u', 20);
                addArrow(index, 3, false, 'd', 20);
                addArrow(index, 3, false, 'r', 20);
                addArrow(index, 3, false, 'l', 20);
                addArrow(index, 3, false, 'u', 20);
                addArrow(index, 3, false, 'd', 20);
                addArrow(index, 3, false, 'r', 20);
                addArrow(index, 3, false, 'd', 20);
                if(isLastHard)
                    addArrow(index, 3, false, 'u', 120);
                else
                    addArrow(index, 3, false, 'u', 20);
                break;
            case 2:
                addArrow(index, 3, false, 'u', 20);
                addArrow(index, 3, false, 'r', 20);
                addArrow(index, 3, false, 'l', 20);
                addArrow(index, 3, false, 'd', 20);
                addArrow(index, 3, false, 'u', 20);
                addArrow(index, 3, false, 'r', 20);
                addArrow(index, 3, false, 'l', 20);
                addArrow(index, 3, false, 'd', 20);
                addArrow(index, 3, false, 'l', 20);
                if(isLastHard)
                    addArrow(index, 3, false, 'r', 120);
                else
                    addArrow(index, 3, false, 'r', 20);
                break;
            case 3:
                addArrow(index, 3, false, 'r', 20);
                addArrow(index, 3, false, 'd', 20);
                addArrow(index, 3, false, 'u', 20);
                addArrow(index, 3, false, 'l', 20);
                addArrow(index, 3, false, 'r', 20);
                addArrow(index, 3, false, 'd', 20);
                addArrow(index, 3, false, 'u', 20);
                addArrow(index, 3, false, 'l', 20);
                addArrow(index, 3, false, 'u', 20);
                if(isLastHard)
                    addArrow(index, 3, false, 'd', 120);
                else
                    addArrow(index, 3, false, 'd', 20);
                break;
        }
    }
    
    private void survivalAttackThirty(int index, int shift, boolean isLastHard) {
        switch(shift) {
            case 0:
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, true, 'd', 35);
                addArrow(index, 3, true, 'r', 35);
                addArrow(index, 3, true, 'l', 35);
                addArrow(index, 3, true, 'u', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, true, 'l', 35);
                addArrow(index, 3, true, 'd', 35);
                addArrow(index, 3, true, 'u', 35);
                if(isLastHard)
                    addArrow(index, 3, true, 'r', 120);
                else
                    addArrow(index, 3, true, 'r', 35);
                break;
            case 1:
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, true, 'l', 35);
                addArrow(index, 3, true, 'd', 35);
                addArrow(index, 3, true, 'u', 35);
                addArrow(index, 3, true, 'r', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, true, 'u', 35);
                addArrow(index, 3, true, 'l', 35);
                addArrow(index, 3, true, 'r', 35);
                if(isLastHard)
                    addArrow(index, 3, true, 'd', 120);
                else
                    addArrow(index, 3, true, 'd', 35);
                break;
            case 2:
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, true, 'u', 35);
                addArrow(index, 3, true, 'l', 35);
                addArrow(index, 3, true, 'r', 35);
                addArrow(index, 3, true, 'd', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, true, 'r', 35);
                addArrow(index, 3, true, 'u', 35);
                addArrow(index, 3, true, 'd', 35);
                if(isLastHard)
                    addArrow(index, 3, true, 'l', 120);
                else
                    addArrow(index, 3, true, 'l', 35);
                break;
            case 3:
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, true, 'r', 35);
                addArrow(index, 3, true, 'u', 35);
                addArrow(index, 3, true, 'd', 35);
                addArrow(index, 3, true, 'l', 35);
                addArrow(index, 3, false, 'd', 35);
                addArrow(index, 3, false, 'l', 35);
                addArrow(index, 3, false, 'r', 35);
                addArrow(index, 3, false, 'u', 35);
                addArrow(index, 3, true, 'd', 35);
                addArrow(index, 3, true, 'r', 35);
                addArrow(index, 3, true, 'l', 35);
                if(isLastHard)
                    addArrow(index, 3, true, 'u', 120);
                else
                    addArrow(index, 3, true, 'u', 35);
                break;
        }
    }
    
    private void survivalAttackThirtyOne(int index, int shift, boolean isLastHard) {
        switch(shift) {
            case 0:
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, true, 'd', 40);
                addArrow(index, 2, true, 'u', 40);
                addArrow(index, 2, true, 'r', 40);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, true, 'l', 40);
                addArrow(index, 2, true, 'u', 40);
                addArrow(index, 2, true, 'd', 40);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, true, 'r', 40);
                addArrow(index, 2, true, 'l', 40);
                addArrow(index, 2, true, 'd', 40);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, true, 'l', 40);
                addArrow(index, 2, true, 'd', 40);
                addArrow(index, 2, true, 'r', 40);
                if(isLastHard)
                    addArrow(index, 2, true, 'u', 120);
                else
                    addArrow(index, 2, true, 'u', 45);
                break;
            case 1:
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, true, 'l', 40);
                addArrow(index, 2, true, 'r', 40);
                addArrow(index, 2, true, 'd', 40);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, true, 'u', 40);
                addArrow(index, 2, true, 'r', 40);
                addArrow(index, 2, true, 'l', 40);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, true, 'd', 40);
                addArrow(index, 2, true, 'u', 40);
                addArrow(index, 2, true, 'l', 40);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, true, 'u', 40);
                addArrow(index, 2, true, 'l', 40);
                addArrow(index, 2, true, 'd', 40);
                if(isLastHard)
                    addArrow(index, 2, true, 'r', 120);
                else
                    addArrow(index, 2, true, 'r', 45);
                break;
            case 2:
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, true, 'u', 40);
                addArrow(index, 2, true, 'd', 40);
                addArrow(index, 2, true, 'l', 40);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, true, 'r', 40);
                addArrow(index, 2, true, 'd', 40);
                addArrow(index, 2, true, 'u', 40);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, true, 'l', 40);
                addArrow(index, 2, true, 'r', 40);
                addArrow(index, 2, true, 'u', 40);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, true, 'r', 40);
                addArrow(index, 2, true, 'u', 40);
                addArrow(index, 2, true, 'l', 40);
                if(isLastHard)
                    addArrow(index, 2, true, 'd', 120);
                else
                    addArrow(index, 2, true, 'd', 45);
                break;
            case 3:
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, true, 'r', 40);
                addArrow(index, 2, true, 'l', 40);
                addArrow(index, 2, true, 'u', 40);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, true, 'd', 40);
                addArrow(index, 2, true, 'l', 40);
                addArrow(index, 2, true, 'r', 40);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, true, 'u', 40);
                addArrow(index, 2, true, 'd', 40);
                addArrow(index, 2, true, 'r', 40);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, true, 'd', 40);
                addArrow(index, 2, true, 'r', 40);
                addArrow(index, 2, true, 'u', 40);
                if(isLastHard)
                    addArrow(index, 2, true, 'l', 120);
                else
                    addArrow(index, 2, true, 'l', 45);
                break;
        }
    }
    
    private void survivalAttackThirtyTwo(int index, int shift) {
        switch(shift) {
            case 0:
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'd', 20);
                break;
            case 1:
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'l', 20);
                break;
            case 2:
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'u', 20);
                break;
            case 3:
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'r', 20);
                break;
        }
    }
    
    private void survivalAttackThirtyThree(int index, int shift) {
        switch(shift) {
            case 0:
                addArrow(index, 4, false, 'd', 27);
                addArrow(index, 4, false, 'd', 27);
                addArrow(index, 4, true, 'd', 27);
                addArrow(index, 4, true, 'l', 27);
                addArrow(index, 4, false, 'l', 27);
                addArrow(index, 4, false, 'r', 27);
                addArrow(index, 4, true, 'u', 27);
                addArrow(index, 4, true, 'r', 27);
                break;
            case 1:
                addArrow(index, 4, false, 'l', 27);
                addArrow(index, 4, false, 'l', 27);
                addArrow(index, 4, true, 'l', 27);
                addArrow(index, 4, true, 'u', 27);
                addArrow(index, 4, false, 'u', 27);
                addArrow(index, 4, false, 'd', 27);
                addArrow(index, 4, true, 'r', 27);
                addArrow(index, 4, true, 'd', 27);
                break;
            case 2:
                addArrow(index, 4, false, 'u', 27);
                addArrow(index, 4, false, 'u', 27);
                addArrow(index, 4, true, 'u', 27);
                addArrow(index, 4, true, 'r', 27);
                addArrow(index, 4, false, 'r', 27);
                addArrow(index, 4, false, 'l', 27);
                addArrow(index, 4, true, 'd', 27);
                addArrow(index, 4, true, 'l', 27);
                break;
            case 3:
                addArrow(index, 4, false, 'r', 27);
                addArrow(index, 4, false, 'r', 27);
                addArrow(index, 4, true, 'r', 27);
                addArrow(index, 4, true, 'd', 27);
                addArrow(index, 4, false, 'd', 27);
                addArrow(index, 4, false, 'u', 27);
                addArrow(index, 4, true, 'l', 27);
                addArrow(index, 4, true, 'u', 27);
                break;
        }
    }
    
    private void survivalAttackThirtyFour(int index, int shift) {
        switch(shift) {
            case 0:
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'd', 15);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'l', 15);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'r', 45);
                break;
            case 1:
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'l', 15);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'u', 15);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'd', 45);
                break;
            case 2:
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'u', 15);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'r', 15);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'l', 45);
                break;
            case 3:
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'r', 15);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'd', 15);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'l', 20);
                addArrow(index, 4, false, 'd', 20);
                addArrow(index, 4, false, 'u', 20);
                addArrow(index, 4, false, 'r', 20);
                addArrow(index, 4, false, 'u', 45);
                break;
        }
    }
    
    private void survivalAttackThirtyFive(int index, int shift) {
        switch(shift) {
            case 0:
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, true, 'u', 35);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, true, 'l', 35);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, true, 'd', 35);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, true, 'r', 35);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, true, 'l', 35);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, true, 'u', 35);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, true, 'd', 35);
                break;
            case 1:
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, true, 'r', 35);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, true, 'u', 35);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, true, 'l', 35);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, true, 'd', 35);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, true, 'u', 35);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, true, 'r', 35);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, true, 'l', 35);
                break;
            case 2:
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, true, 'd', 35);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, true, 'r', 35);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, true, 'u', 35);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, true, 'l', 35);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, true, 'r', 35);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, true, 'd', 35);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, true, 'u', 35);
                break;
            case 3:
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, true, 'l', 35);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, true, 'd', 35);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, true, 'r', 35);
                addArrow(index, 4, false, 'u', 25);
                addArrow(index, 4, true, 'u', 35);
                addArrow(index, 4, false, 'd', 25);
                addArrow(index, 4, true, 'd', 35);
                addArrow(index, 4, false, 'l', 25);
                addArrow(index, 4, true, 'l', 35);
                addArrow(index, 4, false, 'r', 25);
                addArrow(index, 4, true, 'r', 35);
                break;
        }
    }
    
    private void survivalAttackThirtySix(int index) {
        for(int i = 0; i < 30; ++i) {
            char curChar = DIRS[rand.nextInt(4)];
            if(curChar == prevChar)
                curChar = DIRS[rand.nextInt(4)];
            prevChar = curChar;
            addArrow(index, 2, false, curChar, 23);
        }
        addArrow(index, 2, false, DIRS[rand.nextInt(4)], 110);
    }
    
    private void survivalAttackThirtySeven(int index, int shift) {
        switch(shift) {
            case 0:
                addArrow(index, 3, false, 'r', 33);
                addArrow(index, 3, false, 'd', 33);
                addArrow(index, 3, false, 'r', 33);
                addArrow(index, 3, false, 'l', 33);
                addArrow(index, 3, true, 'r', 33);
                addArrow(index, 3, true, 'd', 33);
                addArrow(index, 3, true, 'r', 33);
                addArrow(index, 3, true, 'l', 33);
                addArrow(index, 3, false, 'u', 33);
                addArrow(index, 3, false, 'd', 33);
                addArrow(index, 3, false, 'r', 33);
                addArrow(index, 3, false, 'u', 33);
                addArrow(index, 3, true, 'r', 33);
                addArrow(index, 3, true, 'u', 33);
                addArrow(index, 3, true, 'd', 33);
                addArrow(index, 3, true, 'u', 33);
                break;
            case 1:
                addArrow(index, 3, false, 'd', 33);
                addArrow(index, 3, false, 'l', 33);
                addArrow(index, 3, false, 'd', 33);
                addArrow(index, 3, false, 'u', 33);
                addArrow(index, 3, true, 'd', 33);
                addArrow(index, 3, true, 'l', 33);
                addArrow(index, 3, true, 'd', 33);
                addArrow(index, 3, true, 'u', 33);
                addArrow(index, 3, false, 'r', 33);
                addArrow(index, 3, false, 'l', 33);
                addArrow(index, 3, false, 'd', 33);
                addArrow(index, 3, false, 'r', 33);
                addArrow(index, 3, true, 'd', 33);
                addArrow(index, 3, true, 'r', 33);
                addArrow(index, 3, true, 'l', 33);
                addArrow(index, 3, true, 'r', 33);
                break;
            case 2:
                addArrow(index, 3, false, 'l', 33);
                addArrow(index, 3, false, 'u', 33);
                addArrow(index, 3, false, 'l', 33);
                addArrow(index, 3, false, 'r', 33);
                addArrow(index, 3, true, 'l', 33);
                addArrow(index, 3, true, 'u', 33);
                addArrow(index, 3, true, 'l', 33);
                addArrow(index, 3, true, 'r', 33);
                addArrow(index, 3, false, 'd', 33);
                addArrow(index, 3, false, 'u', 33);
                addArrow(index, 3, false, 'l', 33);
                addArrow(index, 3, false, 'd', 33);
                addArrow(index, 3, true, 'l', 33);
                addArrow(index, 3, true, 'd', 33);
                addArrow(index, 3, true, 'u', 33);
                addArrow(index, 3, true, 'd', 33);
                break;
            case 3:
                addArrow(index, 3, false, 'u', 33);
                addArrow(index, 3, false, 'r', 33);
                addArrow(index, 3, false, 'u', 33);
                addArrow(index, 3, false, 'd', 33);
                addArrow(index, 3, true, 'u', 33);
                addArrow(index, 3, true, 'r', 33);
                addArrow(index, 3, true, 'u', 33);
                addArrow(index, 3, true, 'd', 33);
                addArrow(index, 3, false, 'l', 33);
                addArrow(index, 3, false, 'r', 33);
                addArrow(index, 3, false, 'u', 33);
                addArrow(index, 3, false, 'l', 33);
                addArrow(index, 3, true, 'u', 33);
                addArrow(index, 3, true, 'l', 33);
                addArrow(index, 3, true, 'r', 33);
                addArrow(index, 3, true, 'l', 33);
                break;
        }
    }
    
    private void survivalAttackThirtyEight(int index) {
        for(int i = 0; i < 30; ++i)
            addArrow(index, 4, false, DIRS[rand.nextInt(4)], 20);
    }
    
    private void directionFour(char dir, int index) {
        for(int i = 0; i < 3; ++i)
            addArrow(index, 4, false, dir, 10);
        addArrow(index, 4, false, dir, 25);
    }
    
    private void survivalAttackThirtyNine(int index) {
        char four = DIRS[rand.nextInt(4)];
        char extra = DIRS[rand.nextInt(4)];
        for(int i = 0; i < 4; ++i) {
            do {
                four = DIRS[rand.nextInt(4)];
            } while(four == extra);
            directionFour(four, index);
            do {
                extra = DIRS[rand.nextInt(4)];
            } while(extra == four);
            addArrow(index, 4, false, extra, 10);
        }
    }
    
    private void survivalAttackForty(int index) {
        char direction = 'u';
        char prevDirection = 'u';
        for(int i = 0; i < 10; ++i) {
            direction = DIRS[rand.nextInt(4)];
            if(direction == prevDirection)
                direction = DIRS[rand.nextInt(4)];
            prevDirection = direction;
            addArrow(index, 2, false, direction, 1);
            addArrow(index, 3, true, direction, 53);
        }
    }
    
    private void survivalAttackFortyOne(int index) {
        char current = 'u';
        char prev = 'u';
        for(int i = 0; i < 20; ++i) {
            current = DIRS[rand.nextInt(4)];
            if(current == prev)
                current = DIRS[rand.nextInt(4)];
            prev = current;
            addArrow(index, 1, true, DIRS[rand.nextInt(4)], 50);
            
        }
    }
    
    private void survivalAttackFortyTwo(int index, int shift) {
        switch(shift) {
            case 0:
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'd', 25);
                addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, true, 'r', 30);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'l', 25);
                addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, true, 'd', 30);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'r', 25);
                addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, true, 'd', 30);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'u', 25);
                addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, true, 'u', 45);
                break;
            case 1:
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'l', 25);
                addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, true, 'd', 30);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'u', 25);
                addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, true, 'l', 30);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'd', 25);
                addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, true, 'l', 30);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'r', 25);
                addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, true, 'r', 45);
                break;
            case 2:
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'u', 25);
                addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, true, 'l', 30);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'r', 25);
                addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, true, 'u', 30);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'l', 25);
                addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, true, 'u', 30);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'd', 25);
                addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, true, 'd', 45);
                break;
            case 3:
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'r', 25);
                addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, true, 'u', 30);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'd', 25);
                addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, true, 'l', 30);
                addArrow(index, 2, true, 'r', 30);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'u', 25);
                addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, true, 'r', 30);
                for(int i = 0; i < 15; ++i)
                    addArrow(index, 2, true, 'l', 25);
                addArrow(index, 2, true, 'd', 30);
                addArrow(index, 2, true, 'r', 30);
                addArrow(index, 2, true, 'u', 30);
                addArrow(index, 2, true, 'l', 45);
                break;
        }
    }
    
    private void survivalAttackFortyThree(int index, int shift) {
        switch(shift) {
            case 0:
                addArrow(index, 2, false, 'd', 50);
                addArrow(index, 2, false, 'd', 50);
                addArrow(index, 2, false, 'd', 125);
                addArrow(index, 5, false, 'l', 20);
                addArrow(index, 5, false, 'u', 20);
                addArrow(index, 5, false, 'r', 20);
                addArrow(index, 5, false, 'd', 20);
                addArrow(index, 5, false, 'r', 20);
                addArrow(index, 5, false, 'u', 20);
                addArrow(index, 5, false, 'l', 20);
                addArrow(index, 5, false, 'u', 20);
                addArrow(index, 5, false, 'r', 20);
                addArrow(index, 5, false, 'd', 45);
                break;
            case 1:
                addArrow(index, 2, false, 'l', 50);
                addArrow(index, 2, false, 'l', 50);
                addArrow(index, 2, false, 'l', 125);
                addArrow(index, 5, false, 'u', 20);
                addArrow(index, 5, false, 'r', 20);
                addArrow(index, 5, false, 'd', 20);
                addArrow(index, 5, false, 'l', 20);
                addArrow(index, 5, false, 'd', 20);
                addArrow(index, 5, false, 'r', 20);
                addArrow(index, 5, false, 'u', 20);
                addArrow(index, 5, false, 'r', 20);
                addArrow(index, 5, false, 'd', 20);
                addArrow(index, 5, false, 'l', 45);
                break;
            case 2:
                addArrow(index, 2, false, 'u', 50);
                addArrow(index, 2, false, 'u', 50);
                addArrow(index, 2, false, 'u', 125);
                addArrow(index, 5, false, 'r', 20);
                addArrow(index, 5, false, 'd', 20);
                addArrow(index, 5, false, 'l', 20);
                addArrow(index, 5, false, 'u', 20);
                addArrow(index, 5, false, 'l', 20);
                addArrow(index, 5, false, 'd', 20);
                addArrow(index, 5, false, 'r', 20);
                addArrow(index, 5, false, 'd', 20);
                addArrow(index, 5, false, 'l', 20);
                addArrow(index, 5, false, 'u', 45);
                break;
            case 3:
                addArrow(index, 2, false, 'r', 50);
                addArrow(index, 2, false, 'r', 50);
                addArrow(index, 2, false, 'r', 125);
                addArrow(index, 5, false, 'd', 20);
                addArrow(index, 5, false, 'l', 20);
                addArrow(index, 5, false, 'u', 20);
                addArrow(index, 5, false, 'r', 20);
                addArrow(index, 5, false, 'u', 20);
                addArrow(index, 5, false, 'l', 20);
                addArrow(index, 5, false, 'd', 20);
                addArrow(index, 5, false, 'l', 20);
                addArrow(index, 5, false, 'u', 20);
                addArrow(index, 5, false, 'r', 45);
                break;
        }
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
    
    private void genocideAttackThreeAndTen(int count, int pos, boolean isLastHard) {
        for(int i = 0; i < count; ++i) {
            char curChar = DIRS[rand.nextInt(4)];
            if(curChar == prevChar)
                curChar = DIRS[rand.nextInt(4)];
            prevChar = curChar;
            if(i != 17)
                addArrow(pos, 1, false, curChar, 28);
        }
        if(isLastHard)
            addArrow(pos, 1, false, DIRS[rand.nextInt(4)], 180);
        else
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
        for(int i = 0; i < 15; ++i)
            addArrow(12, 2, true, 'd', 30);
        addArrow(12, 2, true, 'd', 40);
        addArrow(12, 2, true, 'u', 40);
        addArrow(12, 2, true, 'r', 40);
        for(int i = 0; i < 15; ++i)
            addArrow(12, 2, true, 'l', 30);
        addArrow(12, 2, true, 'l', 40);
        addArrow(12, 2, true, 'u', 40);
        addArrow(12, 2, true, 'd', 40);
        for(int i = 0; i < 15; ++i)
            addArrow(12, 2, true, 'r', 30);
        addArrow(12, 2, true, 'r', 40);
        addArrow(12, 2, true, 'l', 40);
        addArrow(12, 2, true, 'd', 40);
        for(int i = 0; i < 15; ++i)
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
    
    public int getCurrentAttack() {
        return currentAttack;
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
