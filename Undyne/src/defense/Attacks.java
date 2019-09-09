package defense;

import customAttackMaker.ArrowBar;
import customAttackMaker.AttackBar;
import customAttackMaker.CustomAttacks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

/**
 * Runs the arrow initialization, management, and giving giving for the application
 */
class Attacks {
    
    /**
     * True if an attack is done and a new attack has started
     */
    private boolean newAttack = true;
    
    /**
     * Stores the number of the last attack that has been done
     */
    private int lastAttack = 1;
    
    /**
     * Stores the number of the attack that is currently in play
     */
    private int currentAttack = 1;
    
    /**
     * Stores the previous character in attacks with random directions selected so the probability of getting the same
     * direction twice is 1/16 and the probability of getting each of the other directions is 5/16 each (15/16
     * altogether)
     */
    private char prevChar = 'u';
    
    /**
     * Array of possible directions used in random selection of a direction and the random orientation shifting of an
     * attack
     */
    private final char[] DIRS = {'d', 'r', 'u', 'l'};
    
    /**
     * Generates pseudorandom numbers for use in randomly selecting a direction from DIRS or choosing a random
     * orientation shift clockwise of an attack
     */
    private Random rand = new Random();
    
    /**
     * {@code Attack} object used to get the list of current arrows in play in order to determine whether all of the
     * arrows from Attacks and the ones in play are empty
     */
    private Attack att;
    
    /**
     * This {@code ArrayList} is a container of "attacks." Each "attack" is an {@code ArrayList} that contains {@code
     * Arrow}s.
     */
    private ArrayList<ArrayDeque<Arrow>> undyneAttacks = new ArrayList<>();
    
    /**
     * The game modes the user can choose from
     */
    enum GameMode {
        EASY,
        MEDIUM,
        HARD,
        SURVIVAL}
    
    /**
     * The {@code Attacks} constructor that is used for importing attacks from {@code CustomAttacks} to be used in
     * gameplay
     *
     * @param attacks The list of attacks from {@code CustomAttacks} to import
     * @throws Exception An exception thrown either due to the speed or delay not being in the correct range of values
     */
    Attacks(ArrayList<AttackBar> attacks) throws Exception {
        //Adds ArrayLists to undyneAttacks to match the number of attacks given
        for(int i = 0; i < attacks.size(); ++i)
            undyneAttacks.add(new ArrayDeque<>());
        
        //Goes through given AttackBars to add the given arrows based on the ArrowBars
        for(AttackBar at : attacks) {
            
            //The orientation shift is set to zero by default and is only assigned to a random number between 0 and 3 if the AttackBar orientation shift option is selected
            int shift = 0;
            if(at.isOrientationShift())
                shift = rand.nextInt(4);
            
            //The shiftedDirs list of directions clones the typical directions and is shifted clockwise by shift
            char[] shiftedDirs = DIRS.clone();
            shiftDirs(shiftedDirs, shift);
            
            //Goes through all the given ArrowBars for each given AttackBar
            for(ArrowBar ar : at.getArrows()) {
                //Assigns the direction based on the orientation shift
                char dir = ar.getDirection();
                switch(dir) {
                    case 'd':
                        dir = shiftedDirs[0];
                        break;
                    case 'r':
                        dir = shiftedDirs[1];
                        break;
                    case 'u':
                        dir = shiftedDirs[2];
                        break;
                    case 'l':
                        dir = shiftedDirs[3];
                        break;
                    case 'n':
                        dir = DIRS[rand.nextInt(4)];
                }
                
                //Gets the delay for the arrow and confirms it is between 1 and 999, and if that is not the case, an Exception is thrown
                int delay = ar.getDelay();
                if(delay < 1 || delay > 999) {
                    CustomAttacks.setError(false, "Delay must be between 1 – 999 inclusive");
                    undyneAttacks = new ArrayList<>();
                    throw new Exception();
                }
                
                //Gets the speed for the arrow and confirms it is between 1 and 10, and if that is not the case, an Exception is thrown
                int speed = ar.getSpeed();
                if(speed < 1 || speed > 10) {
                    CustomAttacks.setError(false, "Speed must be between 1 – 10 inclusive");
                    undyneAttacks = new ArrayList<>();
                    throw new Exception();
                }
                
                //If the given speed is 1, we will use a slow arrow; otherwise, we will use a typical arrow
                if(speed == 1)
                    addSlowArrow(at.getNumber(), ar.isReversible(), dir, ar.getDelay());
                else
                    addArrow(at.getNumber(), speed - 1, ar.isReversible(), dir, ar.getDelay());
            }
        }
    }
    
    /**
     * Shifts the given array of characters by the given shift
     *
     * @param dirs  An array of characters (directions in this case)
     * @param shift The number to shift the dirs array by
     */
    private void shiftDirs(char[] dirs, int shift) {
        for(int i = 0; i < dirs.length; ++i) {
            //The array starts out by going to the index i + shift of the dirs array and setting it equal to the given index of the DIRS array
            if(i + shift <= dirs.length - 1)
                dirs[i + shift] = DIRS[i];
                
                /*
                 * Once i + shift exceeds the length of the dirs array, we need to subtract the length of the dirs array, creating a wrap-around effect to start populating
                 * the dirs array from 0 onwards to the index before i + shift
                 */
            else
                dirs[i + shift - dirs.length] = DIRS[i];
        }
    }
    
    /**
     * The {@code Attacks} constructor that is used when a game mode is chosen
     *
     * @param gm The chosen game mode by the player
     */
    Attacks(GameMode gm) {
        //Adds ArrayLists to undyneAttacks to be filled with Arrows
        for(int i = 0; i < 13000; ++i)
            undyneAttacks.add(new ArrayDeque<>());
        if(gm == GameMode.SURVIVAL) {
            // Super Easy
            normalAttackOne();
            normalAttackTwo();
            survivalAttackThree();
            survivalAttackFour();
            survivalAttackFive();
            survivalAttackSix();
            survivalAttackSeven();
            
            // Easy
            ArrayList<Integer> easyAttacks = new ArrayList<>();
            
            //Seven possible easy attacks
            for(int i = 0; i < 7; ++i)
                easyAttacks.add(i);
            
            /*
             * The i index represents the index of undyneAttacks to place the attack in. This allows randomization in the order of the attack methods
             * chosen for survival
             *
             * Survival attacks eight through nine are attacks without any reverse arrows
             */
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
            //After the easy attacks with no reverse arrows, this attack introduces the reverse arrow to the player
            survivalAttackFifteen(14, rand.nextInt(4), false);
            
            //We now have four attacks left after the attacks without reverse arrows have completed; we add these to the easy attack choice list
            for(int i = 0; i < 4; ++i)
                easyAttacks.add(i);
            
            //Attacks 14 - 17 are randomly chosen between four attacks that include reverse arrows
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
            
            /*
             * All of the easy attacks have now been introduced. We will now have two more randomized rounds of the above easy attacks,
             * starting with attack number 18 (as the base)
             */
            int base = 18;
            //This for loop represents the number of remaining rounds (two) to give to the player of the given attacks
            for(int round = 0; round < 2; ++round) {
                
                //There are 12 attacks to choose from
                for(int i = 0; i < 12; ++i)
                    easyAttacks.add(i);
                
                //The attack index is based on the base index and go up to the base index plus 11; the attack order is then randomized
                for(int i = base; i < base + 12; ++i) {
                    int choice = easyAttacks.remove(rand.nextInt(easyAttacks.size()));
                    /*
                     * As one may notice, there is an isLastEasy boolean that is true if the attack will be the last easy attack
                     * due to it being the second round and the easy attacks choices running out; this is used to increase the wait
                     * time on the last attack
                     */
                    switch(choice) {
                        case 0:
                            survivalAttackEight(i, rand.nextInt(4), easyAttacks.size() == 0 && round == 1);
                            break;
                        case 1:
                            survivalAttackNine(i, rand.nextInt(4), easyAttacks.size() == 0 && round == 1);
                            break;
                        case 2:
                            survivalAttackTen(i, rand.nextInt(4), easyAttacks.size() == 0 && round == 1);
                            break;
                        case 3:
                            survivalAttackEleven(i, rand.nextInt(4), easyAttacks.size() == 0 && round == 1);
                            break;
                        case 4:
                            survivalAttackTwelve(i, rand.nextInt(4), easyAttacks.size() == 0 && round == 1);
                            break;
                        case 5:
                            survivalAttackThirteen(i, rand.nextInt(4), easyAttacks.size() == 0 && round == 1);
                            break;
                        case 6:
                            survivalAttackFourteen(i, rand.nextInt(4), easyAttacks.size() == 0 && round == 1);
                            break;
                        case 7:
                            survivalAttackFifteen(i, rand.nextInt(4), easyAttacks.size() == 0 && round == 1);
                            break;
                        case 8:
                            survivalAttackSixteen(i, rand.nextInt(4), easyAttacks.size() == 0 && round == 1);
                            break;
                        case 9:
                            survivalAttackSeventeen(i, rand.nextInt(4), easyAttacks.size() == 0 && round == 1);
                            break;
                        case 10:
                            survivalAttackEighteen(i, rand.nextInt(4), easyAttacks.size() == 0 && round == 1);
                            break;
                        case 11:
                            survivalAttackNineteen(i, rand.nextInt(4), easyAttacks.size() == 0 && round == 1);
                            break;
                    }
                }
                //This increment of 12 is used to update the correct attack index for the next round
                base += 12;
            }
            
            /*
             * For attacks after the easy attacks, they all have three rounds of randomized attacks since the random arrows have already been introduced
             * with the exception of the super hard attacks, which is supposed to last forever
             */
            
            // Hard
            ArrayList<Integer> hardAttacks = new ArrayList<>();
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
            
            // Very hard
            ArrayList<Integer> superHardAttacks = new ArrayList<>();
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
        }
        
        //Easy mode
        else if(gm == GameMode.EASY) {
            easyAttackOne();
            easyAttackTwo();
            easyAttackThree();
            normalAttackFour();
            easyAttackFive();
            easyAttackSix();
            easyAttackSeven();
            normalAttackEight();
            easyAttackNine();
            easyAttackTen();
            normalAttackEleven();
            easyAttackTwelve();
            easyAttackThirteen();
            easyAttackFourteen();
            easyAttackFifteen();
            easyAttackSixteen();
        }
        
        //Medium mode
        else if(gm == GameMode.MEDIUM) {
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
        //Hard mode
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
    
    /**
     * Gets the next arrow for the attack pattern
     *
     * @return The next arrow for the attack pattern
     */
    Arrow getCurrentArrow() {
        //Used to check if the attack has changed
        boolean attackChanged = false;
        
        //Used to stop the for loop once an attack with a size greater than zero is found
        boolean loopDone = false;
        
        //Loops through the attacks starting at the position of the last attack an arrow was fount at until an attack with at least one arrow is found
        for(int i = currentAttack - 1; i < undyneAttacks.size() && !loopDone; ++i) {
            if(undyneAttacks.get(i).size() != 0) {
                currentAttack = i + 1;
                loopDone = true;
            }
            else
                attackChanged = true;
        }
        //If the current attack number is different than the last attack number, it is a new attack
        if(currentAttack != lastAttack) {
            newAttack = true;
            lastAttack = currentAttack;
        }
        //If the attack has not changed, get the arrow from the current attack
        else if(!attackChanged) {
            newAttack = false;
            return undyneAttacks.get(currentAttack - 1).removeFirst();
        }
        return null;
    }
    
    /**
     * Returns true if there are no arrows in the attack pattern {@code ArrayList} or the undyneAttacks {@code
     * ArrayList}
     *
     * @return True if there are no arrows in the attack pattern or undyneAttacks {@code ArrayList}s
     */
    boolean isFinished() {
        if(att == null)
            return false;
        ArrayList<Arrow> attackPattern = att.getList();
        if(attackPattern.size() != 0)
            return false;
        for(int i = currentAttack - 1; i < undyneAttacks.size(); ++i) {
            for(Arrow a : undyneAttacks.get(i)) {
                if(a != null && a.getSpeed() != 0)
                    return false;
            }
        }
        return true;
    }
    
    /**
     * Returns true if an attack is done and a new attack has started
     *
     * @return True if an attack is done and a new attack has started
     */
    boolean isNewAttack() {
        return newAttack;
    }
    
    /**
     * Sets newAttack to false
     */
    void notNewAttack() {
        newAttack = false;
    }
    
    /**
     * Adds an {@code Arrow} with the given properties to the given attack's {@code ArrayDeque}
     * @param attack The attack to add the {@code Arrow}
     * @param speed The speed of the {@code Arrow}
     * @param reversible True if the {@code Arrow} is a reverse arrow
     * @param direction The direction of the {@code Arrow}
     * @param delay The delay until the next {@code Arrow} should spawn
     */
    private void addArrow(int attack, int speed, boolean reversible, char direction, int delay) {
        undyneAttacks.get(attack).add(new Arrow(speed, reversible, direction, delay, false));
    }
    
    /**
     * Adds a slow {@code Arrow} with the given properties to the given attack's {@code ArrayDeque}
     * @param attack The attack to add the {@code Arrow}
     * @param reversible True if the {@code Arrow} is a reverse arrow
     * @param direction The direction of the {@code Arrow}
     * @param delay The delay until the next {@code Arrow} should spawn
     */
    private void addSlowArrow(int attack, boolean reversible, char direction, int delay) {
        undyneAttacks.get(attack).add(new Arrow(1, reversible, direction, delay, true));
    }
    
    //Here starts the actual attacks that are added
    
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
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 3, false, shiftedDirs[0], 35);
        addArrow(index, 3, false, shiftedDirs[2], 35);
        addArrow(index, 3, false, shiftedDirs[2], 35);
        addArrow(index, 3, false, shiftedDirs[1], 35);
        addArrow(index, 3, false, shiftedDirs[3], 35);
        addArrow(index, 3, false, shiftedDirs[2], 35);
        addArrow(index, 3, false, shiftedDirs[1], 35);
        addArrow(index, 3, false, shiftedDirs[1], 35);
        if(isLastEasy)
            addArrow(index, 3, false, shiftedDirs[3], 120);
        else
            addArrow(index, 3, false, shiftedDirs[3], 35);
    }
    
    private void survivalAttackNine(int index, int shift, boolean isLastEasy) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 4, false, shiftedDirs[1], 30);
        addArrow(index, 4, false, shiftedDirs[0], 30);
        addArrow(index, 4, false, shiftedDirs[2], 30);
        addArrow(index, 4, false, shiftedDirs[3], 30);
        addArrow(index, 4, false, shiftedDirs[1], 30);
        addArrow(index, 4, false, shiftedDirs[0], 30);
        addArrow(index, 4, false, shiftedDirs[2], 30);
        addArrow(index, 4, false, shiftedDirs[3], 30);
        addArrow(index, 4, false, shiftedDirs[2], 30);
        if(isLastEasy)
            addArrow(index, 4, false, shiftedDirs[0], 120);
        else
            addArrow(index, 4, false, shiftedDirs[0], 45);
    }
    
    private void survivalAttackTen(int index, int shift, boolean isLastEasy) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 2, false, shiftedDirs[0], 45);
        addArrow(index, 2, false, shiftedDirs[2], 20);
        addArrow(index, 2, false, shiftedDirs[0], 45);
        addArrow(index, 2, false, shiftedDirs[2], 45);
        addArrow(index, 2, false, shiftedDirs[2], 20);
        addArrow(index, 2, false, shiftedDirs[0], 45);
        addArrow(index, 2, false, shiftedDirs[0], 45);
        addArrow(index, 2, false, shiftedDirs[2], 45);
        if(isLastEasy)
            addArrow(index, 2, false, shiftedDirs[0], 120);
        else
            addArrow(index, 2, false, shiftedDirs[0], 45);
    }
    
    private void survivalAttackEleven(int index, int shift, boolean isLastEasy) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 3, false, shiftedDirs[1], 1);
        addArrow(index, 4, false, shiftedDirs[1], 1);
        addArrow(index, 5, false, shiftedDirs[1], 70);
        addArrow(index, 3, false, shiftedDirs[3], 1);
        addArrow(index, 4, false, shiftedDirs[3], 1);
        addArrow(index, 5, false, shiftedDirs[3], 45);
        addArrow(index, 4, false, shiftedDirs[2], 40);
        addArrow(index, 4, false, shiftedDirs[0], 40);
        addArrow(index, 4, false, shiftedDirs[2], 40);
        if(isLastEasy)
            addArrow(index, 4, false, shiftedDirs[0], 120);
        else
            addArrow(index, 4, false, shiftedDirs[0], 45);
    }
    
    private void survivalAttackTwelve(int index, int shift, boolean isLastEasy) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 4, false, shiftedDirs[0], 30);
        addArrow(index, 4, false, shiftedDirs[1], 30);
        addArrow(index, 4, false, shiftedDirs[0], 30);
        addArrow(index, 4, false, shiftedDirs[3], 30);
        addArrow(index, 4, false, shiftedDirs[2], 30);
        addArrow(index, 4, false, shiftedDirs[1], 30);
        addArrow(index, 4, false, shiftedDirs[1], 30);
        addArrow(index, 4, false, shiftedDirs[3], 60);
        addArrow(index, 4, false, shiftedDirs[2], 30);
        addArrow(index, 4, false, shiftedDirs[3], 30);
        addArrow(index, 4, false, shiftedDirs[3], 30);
        if(isLastEasy)
            addArrow(index, 4, false, shiftedDirs[2], 120);
        else
            addArrow(index, 4, false, shiftedDirs[2], 45);
    }
    
    private void survivalAttackThirteen(int index, int shift, boolean isLastEasy) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 3, false, shiftedDirs[0], 30);
        addArrow(index, 3, false, shiftedDirs[0], 30);
        addArrow(index, 3, false, shiftedDirs[3], 20);
        addArrow(index, 3, false, shiftedDirs[3], 20);
        addArrow(index, 3, false, shiftedDirs[1], 30);
        addArrow(index, 3, false, shiftedDirs[2], 30);
        addArrow(index, 3, false, shiftedDirs[2], 30);
        addArrow(index, 3, false, shiftedDirs[1], 20);
        addArrow(index, 3, false, shiftedDirs[1], 20);
        if(isLastEasy)
            addArrow(index, 3, false, shiftedDirs[3], 120);
        else
            addArrow(index, 3, false, shiftedDirs[3], 45);
    }
    
    private void survivalAttackFourteen(int index, int shift, boolean isLastEasy) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addSlowArrow(index, false, shiftedDirs[1], 220);
        addArrow(index, 3, false, shiftedDirs[0], 25);
        addArrow(index, 3, false, shiftedDirs[3], 25);
        addArrow(index, 3, false, shiftedDirs[2], 25);
        addArrow(index, 3, false, shiftedDirs[0], 25);
        addArrow(index, 3, false, shiftedDirs[3], 25);
        if(isLastEasy)
            addArrow(index, 3, false, shiftedDirs[2], 120);
        else
            addArrow(index, 3, false, shiftedDirs[2], 45);
    }
    
    private void survivalAttackFifteen(int index, int shift, boolean isLastEasy) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 2, false, shiftedDirs[3], 55);
        addArrow(index, 2, false, shiftedDirs[1], 55);
        addArrow(index, 2, false, shiftedDirs[3], 55);
        addArrow(index, 2, false, shiftedDirs[1], 65);
        if(isLastEasy)
            addArrow(index, 2, true, shiftedDirs[3], 180);
        else
            addArrow(index, 2, true, shiftedDirs[3], 120);
    }
    
    private void survivalAttackSixteen(int index, int shift, boolean isLastEasy) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 2, false, shiftedDirs[1], 45);
        addArrow(index, 2, false, shiftedDirs[3], 75);
        addArrow(index, 2, true, shiftedDirs[2], 45);
        addArrow(index, 2, false, shiftedDirs[2], 45);
        addArrow(index, 2, false, shiftedDirs[1], 45);
        addArrow(index, 2, false, shiftedDirs[3], 45);
        addArrow(index, 2, false, shiftedDirs[0], 75);
        if(isLastEasy)
            addArrow(index, 2, true, shiftedDirs[2], 120);
        else
            addArrow(index, 2, true, shiftedDirs[2], 45);
    }
    
    private void survivalAttackSeventeen(int index, int shift, boolean isLastEasy) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 3, false, shiftedDirs[1], 45);
        addArrow(index, 3, false, shiftedDirs[2], 45);
        addArrow(index, 3, false, shiftedDirs[3], 45);
        addArrow(index, 3, false, shiftedDirs[0], 80);
        addArrow(index, 3, true, shiftedDirs[3], 45);
        addArrow(index, 3, true, shiftedDirs[0], 45);
        addArrow(index, 3, true, shiftedDirs[1], 45);
        if(isLastEasy)
            addArrow(index, 3, true, shiftedDirs[2], 120);
        else
            addArrow(index, 3, true, shiftedDirs[2], 45);
    }
    
    private void survivalAttackEighteen(int index, int shift, boolean isLastEasy) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        for(int i = 0; i < 2; ++i) {
            addArrow(index, 2, true, shiftedDirs[3], 33);
            addArrow(index, 2, true, shiftedDirs[0], 33);
            addArrow(index, 2, true, shiftedDirs[1], 33);
            addArrow(index, 2, true, shiftedDirs[2], 33);
        }
        addArrow(index, 2, true, shiftedDirs[3], 33);
        addArrow(index, 2, true, shiftedDirs[0], 33);
        addArrow(index, 2, true, shiftedDirs[1], 33);
        if(isLastEasy)
            addArrow(index, 2, true, shiftedDirs[2], 120);
        else
            addArrow(index, 2, true, shiftedDirs[2], 33);
    }
    
    private void survivalAttackNineteen(int index, int shift, boolean isLastEasy) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 2, false, shiftedDirs[1], 25);
        addArrow(index, 2, false, shiftedDirs[1], 25);
        addArrow(index, 2, false, shiftedDirs[1], 15);
        addArrow(index, 2, false, shiftedDirs[1], 15);
        addArrow(index, 2, false, shiftedDirs[1], 25);
        addArrow(index, 2, false, shiftedDirs[1], 25);
        addArrow(index, 2, true, shiftedDirs[3], 25);
        addArrow(index, 2, false, shiftedDirs[1], 25);
        addArrow(index, 2, true, shiftedDirs[3], 30);
        addArrow(index, 2, false, shiftedDirs[3], 25);
        addArrow(index, 2, true, shiftedDirs[1], 25);
        addArrow(index, 2, false, shiftedDirs[3], 25);
        if(isLastEasy)
            addArrow(index, 2, true, shiftedDirs[1], 120);
        else
            addArrow(index, 2, true, shiftedDirs[1], 45);
    }
    
    private void survivalAttackTwenty(int index, int shift, boolean isLastHard) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 2, false, shiftedDirs[0], 55);
        addArrow(index, 2, false, shiftedDirs[0], 55);
        addArrow(index, 2, false, shiftedDirs[0], 130);
        addArrow(index, 5, false, shiftedDirs[3], 25);
        addArrow(index, 5, false, shiftedDirs[2], 25);
        addArrow(index, 5, false, shiftedDirs[1], 25);
        addArrow(index, 5, false, shiftedDirs[0], 25);
        addArrow(index, 5, false, shiftedDirs[1], 25);
        addArrow(index, 5, false, shiftedDirs[2], 25);
        addArrow(index, 5, false, shiftedDirs[3], 25);
        addArrow(index, 5, false, shiftedDirs[2], 25);
        addArrow(index, 5, false, shiftedDirs[1], 25);
        if(isLastHard)
            addArrow(index, 5, false, shiftedDirs[0], 120);
        else
            addArrow(index, 5, false, shiftedDirs[0], 45);
    }
    
    private void survivalAttackTwentyOne(int index, int shift, boolean isLastHard) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 4, false, shiftedDirs[1], 25);
        addArrow(index, 4, false, shiftedDirs[3], 25);
        addArrow(index, 4, false, shiftedDirs[1], 15);
        addArrow(index, 4, false, shiftedDirs[1], 25);
        addArrow(index, 4, false, shiftedDirs[3], 25);
        addArrow(index, 4, false, shiftedDirs[3], 25);
        addArrow(index, 4, false, shiftedDirs[1], 25);
        addArrow(index, 4, false, shiftedDirs[3], 15);
        addArrow(index, 4, false, shiftedDirs[3], 25);
        addArrow(index, 4, false, shiftedDirs[1], 25);
        addArrow(index, 4, false, shiftedDirs[3], 25);
        addArrow(index, 4, false, shiftedDirs[1], 25);
        addArrow(index, 4, false, shiftedDirs[3], 25);
        if(isLastHard)
            addArrow(index, 4, false, shiftedDirs[1], 120);
        else
            addArrow(index, 4, false, shiftedDirs[1], 45);
    }
    
    private void survivalAttackTwentyTwo(int index, boolean isLastHard) {
        genocideAttackThreeAndTen(22, index, isLastHard);
    }
    
    private void survivalAttackTwentyThree(int index, int shift, boolean isLastHard) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 5, false, shiftedDirs[1], 10);
        addArrow(index, 4, false, shiftedDirs[0], 40);
        addArrow(index, 5, false, shiftedDirs[3], 10);
        addArrow(index, 4, false, shiftedDirs[2], 35);
        addArrow(index, 4, false, shiftedDirs[1], 1);
        addArrow(index, 1, false, shiftedDirs[0], 30);
        addArrow(index, 1, false, shiftedDirs[2], 25);
        if(isLastHard)
            addArrow(index, 5, false, shiftedDirs[3], 120);
        else
            addArrow(index, 5, false, shiftedDirs[3], 70);
    }
    
    private void survivalAttackTwentyFour(int index, int shift, boolean isLastHard) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 2, false, shiftedDirs[0], 1);
        addArrow(index, 3, true, shiftedDirs[0], 80);
        addArrow(index, 2, false, shiftedDirs[1], 1);
        addArrow(index, 3, true, shiftedDirs[1], 80);
        addArrow(index, 2, false, shiftedDirs[2], 1);
        addArrow(index, 3, true, shiftedDirs[2], 80);
        addArrow(index, 2, false, shiftedDirs[1], 1);
        addArrow(index, 3, true, shiftedDirs[1], 80);
        addArrow(index, 2, false, shiftedDirs[3], 1);
        addArrow(index, 3, true, shiftedDirs[3], 80);
        addArrow(index, 2, false, shiftedDirs[0], 1);
        addArrow(index, 3, true, shiftedDirs[0], 80);
        addArrow(index, 2, false, shiftedDirs[0], 1);
        addArrow(index, 3, true, shiftedDirs[0], 80);
        addArrow(index, 2, false, shiftedDirs[2], 1);
        if(isLastHard)
            addArrow(index, 3, true, shiftedDirs[2], 45);
        else
            addArrow(index, 3, true, shiftedDirs[2], 80);
    }
    
    private void survivalAttackTwentyFive(int index, int shift, boolean isLastHard) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        for(int i = 0; i < 3; ++i) {
            addArrow(index, 3, false, shiftedDirs[1], 28);
            addArrow(index, 3, false, shiftedDirs[0], 28);
        }
        addArrow(index, 3, false, shiftedDirs[1], 28);
        addArrow(index, 3, false, shiftedDirs[0], 50);
        for(int i = 0; i < 2; ++i) {
            addArrow(index, 3, true, shiftedDirs[1], 28);
            addArrow(index, 3, true, shiftedDirs[0], 28);
        }
        addArrow(index, 3, true, shiftedDirs[1], 28);
        if(isLastHard)
            addArrow(index, 3, true, shiftedDirs[0], 120);
        else
            addArrow(index, 3, true, shiftedDirs[0], 28);
    }
    
    private void survivalAttackTwentySix(int index, int shift, boolean isLastHard) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 3, true, shiftedDirs[3], 35);
        addArrow(index, 3, true, shiftedDirs[0], 35);
        addArrow(index, 3, true, shiftedDirs[1], 35);
        addArrow(index, 3, true, shiftedDirs[2], 35);
        addArrow(index, 3, false, shiftedDirs[0], 35);
        addArrow(index, 3, false, shiftedDirs[1], 35);
        addArrow(index, 3, false, shiftedDirs[2], 35);
        addArrow(index, 3, false, shiftedDirs[3], 50);
        addArrow(index, 3, true, shiftedDirs[2], 35);
        addArrow(index, 3, true, shiftedDirs[1], 35);
        addArrow(index, 3, true, shiftedDirs[3], 35);
        if(isLastHard)
            addArrow(index, 3, true, shiftedDirs[0], 120);
        else
            addArrow(index, 3, true, shiftedDirs[0], 35);
    }
    
    private void survivalAttackTwentySeven(int index, int shift, boolean isLastHard) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 2, false, shiftedDirs[1], 25);
        addArrow(index, 2, false, shiftedDirs[3], 25);
        addArrow(index, 2, false, shiftedDirs[0], 25);
        addArrow(index, 2, true, shiftedDirs[2], 25);
        addArrow(index, 2, false, shiftedDirs[1], 25);
        addArrow(index, 2, false, shiftedDirs[3], 25);
        addArrow(index, 2, false, shiftedDirs[2], 25);
        addArrow(index, 2, true, shiftedDirs[0], 25);
        addArrow(index, 2, false, shiftedDirs[1], 25);
        addArrow(index, 2, false, shiftedDirs[3], 25);
        addArrow(index, 2, false, shiftedDirs[0], 25);
        addArrow(index, 2, true, shiftedDirs[2], 25);
        addArrow(index, 2, false, shiftedDirs[1], 25);
        addArrow(index, 2, false, shiftedDirs[3], 25);
        addArrow(index, 2, false, shiftedDirs[2], 25);
        if(isLastHard)
            addArrow(index, 2, true, shiftedDirs[0], 120);
        else
            addArrow(index, 2, true, shiftedDirs[0], 25);
    }
    
    private void survivalAttackTwentyEight(int index, int shift, boolean isLastHard) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 2, false, shiftedDirs[1], 40);
        addArrow(index, 2, true, shiftedDirs[1], 35);
        addArrow(index, 2, false, shiftedDirs[2], 40);
        addArrow(index, 2, true, shiftedDirs[2], 35);
        addArrow(index, 2, false, shiftedDirs[3], 40);
        addArrow(index, 2, true, shiftedDirs[3], 35);
        addArrow(index, 2, false, shiftedDirs[0], 40);
        addArrow(index, 2, true, shiftedDirs[0], 35);
        addArrow(index, 2, false, shiftedDirs[1], 40);
        addArrow(index, 2, true, shiftedDirs[1], 35);
        addArrow(index, 2, false, shiftedDirs[2], 40);
        addArrow(index, 2, true, shiftedDirs[2], 35);
        addArrow(index, 2, false, shiftedDirs[3], 40);
        if(isLastHard)
            addArrow(index, 2, true, shiftedDirs[3], 120);
        else
            addArrow(index, 2, true, shiftedDirs[3], 35);
    }
    
    private void survivalAttackTwentyNine(int index, int shift, boolean isLastHard) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 3, false, shiftedDirs[0], 20);
        addArrow(index, 3, false, shiftedDirs[3], 20);
        addArrow(index, 3, false, shiftedDirs[1], 20);
        addArrow(index, 3, false, shiftedDirs[2], 20);
        addArrow(index, 3, false, shiftedDirs[0], 20);
        addArrow(index, 3, false, shiftedDirs[3], 20);
        addArrow(index, 3, false, shiftedDirs[1], 20);
        addArrow(index, 3, false, shiftedDirs[2], 20);
        addArrow(index, 3, false, shiftedDirs[1], 20);
        if(isLastHard)
            addArrow(index, 3, false, shiftedDirs[3], 120);
        else
            addArrow(index, 3, false, shiftedDirs[3], 20);
    }
    
    private void survivalAttackThirty(int index, int shift, boolean isLastHard) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 3, false, shiftedDirs[2], 35);
        addArrow(index, 3, false, shiftedDirs[1], 35);
        addArrow(index, 3, false, shiftedDirs[3], 35);
        addArrow(index, 3, false, shiftedDirs[0], 35);
        addArrow(index, 3, true, shiftedDirs[0], 35);
        addArrow(index, 3, true, shiftedDirs[1], 35);
        addArrow(index, 3, true, shiftedDirs[3], 35);
        addArrow(index, 3, true, shiftedDirs[2], 35);
        addArrow(index, 3, false, shiftedDirs[3], 35);
        addArrow(index, 3, false, shiftedDirs[2], 35);
        addArrow(index, 3, false, shiftedDirs[0], 35);
        addArrow(index, 3, false, shiftedDirs[1], 35);
        addArrow(index, 3, true, shiftedDirs[3], 35);
        addArrow(index, 3, true, shiftedDirs[0], 35);
        addArrow(index, 3, true, shiftedDirs[2], 35);
        if(isLastHard)
            addArrow(index, 3, true, shiftedDirs[1], 120);
        else
            addArrow(index, 3, true, shiftedDirs[1], 35);
    }
    
    private void survivalAttackThirtyOne(int index, int shift, boolean isLastHard) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        for(int i = 0; i < 6; ++i)
            addArrow(index, 2, true, shiftedDirs[0], 30);
        addArrow(index, 2, true, shiftedDirs[0], 40);
        addArrow(index, 2, true, shiftedDirs[2], 40);
        addArrow(index, 2, true, shiftedDirs[1], 40);
        for(int i = 0; i < 6; ++i)
            addArrow(index, 2, true, shiftedDirs[3], 30);
        addArrow(index, 2, true, shiftedDirs[3], 40);
        addArrow(index, 2, true, shiftedDirs[2], 40);
        addArrow(index, 2, true, shiftedDirs[0], 40);
        for(int i = 0; i < 6; ++i)
            addArrow(index, 2, true, shiftedDirs[1], 30);
        addArrow(index, 2, true, shiftedDirs[1], 40);
        addArrow(index, 2, true, shiftedDirs[3], 40);
        addArrow(index, 2, true, shiftedDirs[0], 40);
        for(int i = 0; i < 6; ++i)
            addArrow(index, 2, true, shiftedDirs[2], 30);
        addArrow(index, 2, true, shiftedDirs[3], 40);
        addArrow(index, 2, true, shiftedDirs[0], 40);
        addArrow(index, 2, true, shiftedDirs[1], 40);
        if(isLastHard)
            addArrow(index, 2, true, shiftedDirs[2], 120);
        else
            addArrow(index, 2, true, shiftedDirs[2], 45);
    }
    
    private void survivalAttackThirtyTwo(int index, int shift) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 4, false, shiftedDirs[0], 20);
        addArrow(index, 4, false, shiftedDirs[0], 20);
        addArrow(index, 4, false, shiftedDirs[3], 20);
        addArrow(index, 4, false, shiftedDirs[2], 20);
        addArrow(index, 4, false, shiftedDirs[1], 20);
        addArrow(index, 4, false, shiftedDirs[3], 20);
        addArrow(index, 4, false, shiftedDirs[2], 20);
        addArrow(index, 4, false, shiftedDirs[0], 20);
    }
    
    private void survivalAttackThirtyThree(int index, int shift) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 4, false, shiftedDirs[0], 27);
        addArrow(index, 4, false, shiftedDirs[0], 27);
        addArrow(index, 4, true, shiftedDirs[0], 27);
        addArrow(index, 4, true, shiftedDirs[3], 27);
        addArrow(index, 4, false, shiftedDirs[3], 27);
        addArrow(index, 4, false, shiftedDirs[1], 27);
        addArrow(index, 4, true, shiftedDirs[2], 27);
        addArrow(index, 4, true, shiftedDirs[1], 27);
    }
    
    private void survivalAttackThirtyFour(int index, int shift) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 4, false, shiftedDirs[1], 20);
        addArrow(index, 4, false, shiftedDirs[3], 20);
        addArrow(index, 4, false, shiftedDirs[0], 15);
        addArrow(index, 4, false, shiftedDirs[1], 20);
        addArrow(index, 4, false, shiftedDirs[3], 20);
        addArrow(index, 4, false, shiftedDirs[3], 20);
        addArrow(index, 4, false, shiftedDirs[2], 20);
        addArrow(index, 4, false, shiftedDirs[3], 15);
        addArrow(index, 4, false, shiftedDirs[3], 20);
        addArrow(index, 4, false, shiftedDirs[2], 20);
        addArrow(index, 4, false, shiftedDirs[3], 20);
        addArrow(index, 4, false, shiftedDirs[1], 20);
        addArrow(index, 4, false, shiftedDirs[0], 20);
        addArrow(index, 4, false, shiftedDirs[1], 45);
    }
    
    private void survivalAttackThirtyFive(int index, int shift) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 4, false, shiftedDirs[2], 25);
        addArrow(index, 4, true, shiftedDirs[2], 35);
        addArrow(index, 4, false, shiftedDirs[3], 25);
        addArrow(index, 4, true, shiftedDirs[3], 35);
        addArrow(index, 4, false, shiftedDirs[0], 25);
        addArrow(index, 4, true, shiftedDirs[0], 35);
        addArrow(index, 4, false, shiftedDirs[1], 25);
        addArrow(index, 4, true, shiftedDirs[1], 35);
        addArrow(index, 4, false, shiftedDirs[3], 25);
        addArrow(index, 4, true, shiftedDirs[3], 35);
        addArrow(index, 4, false, shiftedDirs[2], 25);
        addArrow(index, 4, true, shiftedDirs[2], 35);
        addArrow(index, 4, false, shiftedDirs[0], 25);
        addArrow(index, 4, true, shiftedDirs[0], 35);
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
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 3, false, shiftedDirs[1], 33);
        addArrow(index, 3, false, shiftedDirs[0], 33);
        addArrow(index, 3, false, shiftedDirs[1], 33);
        addArrow(index, 3, false, shiftedDirs[3], 33);
        addArrow(index, 3, true, shiftedDirs[1], 33);
        addArrow(index, 3, true, shiftedDirs[0], 33);
        addArrow(index, 3, true, shiftedDirs[1], 33);
        addArrow(index, 3, true, shiftedDirs[3], 33);
        addArrow(index, 3, false, shiftedDirs[2], 33);
        addArrow(index, 3, false, shiftedDirs[0], 33);
        addArrow(index, 3, false, shiftedDirs[1], 33);
        addArrow(index, 3, false, shiftedDirs[2], 33);
        addArrow(index, 3, true, shiftedDirs[1], 33);
        addArrow(index, 3, true, shiftedDirs[2], 33);
        addArrow(index, 3, true, shiftedDirs[0], 33);
        addArrow(index, 3, true, shiftedDirs[2], 33);
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
        char four;
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
        char direction;
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
        char current;
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
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        for(int i = 0; i < 6; ++i)
            addArrow(index, 2, true, shiftedDirs[0], 25);
        addArrow(index, 2, true, shiftedDirs[0], 30);
        addArrow(index, 2, true, shiftedDirs[2], 30);
        addArrow(index, 2, true, shiftedDirs[1], 30);
        for(int i = 0; i < 6; ++i)
            addArrow(index, 2, true, shiftedDirs[3], 25);
        addArrow(index, 2, true, shiftedDirs[3], 30);
        addArrow(index, 2, true, shiftedDirs[2], 30);
        addArrow(index, 2, true, shiftedDirs[0], 30);
        for(int i = 0; i < 6; ++i)
            addArrow(index, 2, true, shiftedDirs[1], 25);
        addArrow(index, 2, true, shiftedDirs[1], 30);
        addArrow(index, 2, true, shiftedDirs[3], 30);
        addArrow(index, 2, true, shiftedDirs[0], 30);
        for(int i = 0; i < 6; ++i)
            addArrow(index, 2, true, shiftedDirs[2], 25);
        addArrow(index, 2, true, shiftedDirs[3], 30);
        addArrow(index, 2, true, shiftedDirs[0], 30);
        addArrow(index, 2, true, shiftedDirs[1], 30);
        addArrow(index, 2, true, shiftedDirs[2], 45);
    }
    
    private void survivalAttackFortyThree(int index, int shift) {
        char[] shiftedDirs = DIRS.clone();
        shiftDirs(shiftedDirs, shift);
        addArrow(index, 2, false, shiftedDirs[0], 50);
        addArrow(index, 2, false, shiftedDirs[0], 50);
        addArrow(index, 2, false, shiftedDirs[0], 125);
        addArrow(index, 5, false, shiftedDirs[3], 20);
        addArrow(index, 5, false, shiftedDirs[2], 20);
        addArrow(index, 5, false, shiftedDirs[1], 20);
        addArrow(index, 5, false, shiftedDirs[0], 20);
        addArrow(index, 5, false, shiftedDirs[1], 20);
        addArrow(index, 5, false, shiftedDirs[2], 20);
        addArrow(index, 5, false, shiftedDirs[3], 20);
        addArrow(index, 5, false, shiftedDirs[2], 20);
        addArrow(index, 5, false, shiftedDirs[1], 20);
        addArrow(index, 5, false, shiftedDirs[0], 45);
    }
    
    private void easyAttackOne() {
        for(int i = 0; i < 3; ++i)
            addArrow(0, 2, false, 'd', 75);
    }
    
    private void easyAttackTwo() {
        for(int i = 0; i < 3; ++i)
            addArrow(1, 2, false, 'd', 75);
        for(int i = 0; i < 3; ++i)
            addArrow(1, 2, false, 'l', 75);
        for(int i = 0; i < 3; ++i)
            addArrow(1, 2, false, 'r', 75);
        for(int i = 0; i < 3; ++i)
            addArrow(1, 2, false, 'u', 75);
    }
    
    private void easyAttackThree() {
        for(int i = 0; i < 2; ++i) {
            addArrow(2, 2, false, 'u', 50);
            addArrow(2, 2, false, 'd', 50);
            addArrow(2, 2, false, 'l', 50);
            addArrow(2, 2, false, 'r', 50);
        }
    }
    
    private void easyAttackFive() {
        addArrow(4, 3, false, 'r', 35);
        addArrow(4, 3, false, 'd', 35);
        addArrow(4, 3, false, 'u', 35);
        addArrow(4, 3, false, 'l', 35);
        addArrow(4, 3, false, 'r', 35);
        addArrow(4, 3, false, 'd', 35);
        addArrow(4, 3, false, 'u', 35);
        addArrow(4, 3, false, 'l', 35);
        addArrow(4, 3, false, 'u', 35);
        addArrow(4, 3, false, 'd', 45);
    }
    
    private void easyAttackSix() {
        addArrow(5, 2, false, 'l', 45);
        addArrow(5, 2, false, 'd', 15);
        addArrow(5, 2, false, 'd', 45);
        addArrow(5, 2, false, 'r', 45);
        addArrow(5, 2, false, 'u', 15);
        addArrow(5, 2, false, 'u', 45);
        addArrow(5, 2, false, 'l', 45);
        addArrow(5, 2, false, 'd', 45);
        addArrow(5, 2, false, 'u', 45);
    }
    
    private void easyAttackSeven() {
        addArrow(6, 2, false, 'r', 1);
        addArrow(6, 3, false, 'r', 1);
        addArrow(6, 4, false, 'r', 100);
        addArrow(6, 2, false, 'l', 1);
        addArrow(6, 3, false, 'l', 1);
        addArrow(6, 4, false, 'l', 80);
        addArrow(6, 3, false, 'u', 40);
        addArrow(6, 3, false, 'd', 40);
        addArrow(6, 3, false, 'u', 40);
        addArrow(6, 3, false, 'd', 45);
    }
    
    private void easyAttackNine() {
        addArrow(8, 2, false, 'd', 45);
        addArrow(8, 2, false, 'u', 30);
        addArrow(8, 2, false, 'd', 45);
        addArrow(8, 2, false, 'u', 45);
        addArrow(8, 2, false, 'u', 30);
        addArrow(8, 2, false, 'd', 45);
        addArrow(8, 2, false, 'd', 45);
        addArrow(8, 2, false, 'u', 45);
        addArrow(8, 2, false, 'd', 45);
    }
    
    private void easyAttackTen() {
        addArrow(9, 2, false, 'u', 1);
        addArrow(9, 3, false, 'l', 120);
        addArrow(9, 2, false, 'd', 1);
        addArrow(9, 3, false, 'r', 120);
        addArrow(9, 2, false, 'u', 1);
        addArrow(9, 3, false, 'r', 120);
        addArrow(9, 2, false, 'd', 1);
        addArrow(9, 3, false, 'u', 45);
    }
    
    private void easyAttackTwelve() {
        addArrow(11, 2, false, 'r', 40);
        addArrow(11, 2, false, 'l', 45);
        addArrow(11, 2, true, 'd', 70);
        addArrow(11, 2, false, 'l', 40);
        addArrow(11, 2, false, 'r', 45);
        addArrow(11, 2, true, 'u', 70);
    }
    
    private void easyAttackThirteen() {
        addArrow(12, 2, true, 'l', 60);
        addArrow(12, 2, true, 'u', 60);
        addArrow(12, 2, false, 'l', 60);
        addArrow(12, 2, false, 'u', 60);
        addArrow(12, 2, true, 'r', 60);
        addArrow(12, 2, true, 'd', 60);
        addArrow(12, 2, false, 'r', 60);
        addArrow(12, 2, false, 'd', 60);
    }
    
    private void easyAttackFourteen() {
        addArrow(13, 2, false, 'r', 50);
        addArrow(13, 2, false, 'l', 50);
        addArrow(13, 2, true, 'u', 50);
        addArrow(13, 2, true, 'd', 50);
        addArrow(13, 2, false, 'l', 50);
        addArrow(13, 2, false, 'r', 50);
        addArrow(13, 2, true, 'd', 50);
        addArrow(13, 2, true, 'u', 50);
    }
    
    private void easyAttackFifteen() {
        addArrow(14, 2, false, 'd', 50);
        addArrow(14, 2, false, 'l', 50);
        addArrow(14, 2, false, 'u', 50);
        addArrow(14, 2, false, 'r', 50);
        addArrow(14, 2, false, 'd', 50);
        addArrow(14, 2, false, 'l', 50);
        addArrow(14, 2, false, 'u', 50);
        addArrow(14, 2, false, 'r', 60);
        addArrow(14, 2, true, 'u', 60);
        addArrow(14, 2, true, 'r', 60);
        addArrow(14, 2, true, 'd', 60);
        addArrow(14, 2, true, 'l', 50);
        addArrow(14, 2, true, 'u', 50);
        addArrow(14, 2, true, 'r', 50);
        addArrow(14, 2, true, 'd', 50);
        addArrow(14, 2, true, 'l', 45);
    }
    
    private void easyAttackSixteen() {
        for(int i = 0; i < 10; ++i)
            addArrow(15, 2, rand.nextBoolean(), DIRS[rand.nextInt(4)], 60);
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
        for(int i = 0; i < 5; ++i)
            addArrow(12, 2, true, 'd', 30);
        addArrow(12, 2, true, 'd', 40);
        addArrow(12, 2, true, 'u', 40);
        addArrow(12, 2, true, 'r', 40);
        for(int i = 0; i < 5; ++i)
            addArrow(12, 2, true, 'l', 30);
        addArrow(12, 2, true, 'l', 40);
        addArrow(12, 2, true, 'u', 40);
        addArrow(12, 2, true, 'd', 40);
        for(int i = 0; i < 5; ++i)
            addArrow(12, 2, true, 'r', 30);
        addArrow(12, 2, true, 'r', 40);
        addArrow(12, 2, true, 'l', 40);
        addArrow(12, 2, true, 'd', 40);
        for(int i = 0; i < 5; ++i)
            addArrow(12, 2, true, 'u', 30);
        addArrow(12, 2, true, 'l', 40);
        addArrow(12, 2, true, 'd', 40);
        addArrow(12, 2, true, 'r', 40);
        addArrow(12, 2, true, 'u', 45);
    }
    
    /**
     * Sets the {@code Attack} object so it can be used to get the {@code ArrayList} of {@code Arrow}s currently in play
     * @param att The {@code Attack} object being used for gameplay
     */
    void setAttack(Attack att) {
        this.att = att;
    }
    
    /**
     * Returns the current attack number that the gameplay is currently at
     * @return The current attack number that the gameplay is currently at
     */
    int getCurrentAttack() {
        return currentAttack;
    }
    
    /**
     * Resets the necessary Attacks variables
     */
    void resetVars() {
        newAttack = true;
        lastAttack = 1;
        currentAttack = 1;
        prevChar = 'u';
        undyneAttacks = new ArrayList<>();
        att = null;
    }
    
}
