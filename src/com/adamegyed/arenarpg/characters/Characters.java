package com.adamegyed.arenarpg.characters;

import com.adamegyed.arenarpg.game.Arena;

import java.util.Random;

/**
 * Created by Adam on 10/21/15.
 * All characters, player or enemy, are instances of classes that
 * extend this basic outline. Named "Characters" to avoid confusion with
 * "Character"
 */
public class Characters {

    protected String name;
    protected String clss;

    protected int level;

    protected int health;
    protected int maxHealth;
    protected int strength;
    protected int defense;
    protected int agility;
    protected int potions;


    protected int xpos;
    protected int ypos;
    protected int prevXpos;
    protected int prevYpos;

    protected Random randGen;

    private String spacesAfterName;
    protected String spacesBeforeLevel;


    //All the Constants used during character creation

    public static final int WARRIOR = 1;
    public static final int ROGUE = 2;
    public static final int WIZARD = 3;

    public static final int ZOMBIE = 4;
    public static final int ALIEN = 5;
    public static final int DRAGON = 6;

    public static final int MAX_NAME_SIZE = 10;


    public Characters(int sethealth, int setstrength, int setdefense, int setagility, int setlevel) {

        maxHealth = sethealth;
        health = sethealth;
        strength = setstrength;
        defense = setdefense;
        agility = setagility;
        level = setlevel;
        potions = 0;
        spacesBeforeLevel = " "; //Manually override this if needed
        randGen = new Random();

    }

    public void setAllStats(int sethealth, int setstrength, int setdefense, int setagility, int setlevel) {

        maxHealth = sethealth;
        health = sethealth;
        strength = setstrength;
        defense = setdefense;
        agility = setagility;
        level = setlevel;
    }



    public void normalizeLocation() {

        if (xpos > 4) {
            xpos = 4;
        }
        if (xpos < 0) {
            xpos = 0;
        }
        if (ypos > 4) {
            ypos = 4;
        }
        if (ypos < 0) {
            ypos = 0;
        }

    }

    protected int getAttackFlux() {

        //Have the attack strength fluctuate by +- 15%
        int finalDamage = 0;
        Random getRand = new Random();
        double rFluct = getRand.nextDouble() * 0.15;
        boolean side = getRand.nextBoolean();
        if (!side) rFluct = -rFluct;
        rFluct++;
        finalDamage = (int) (strength * rFluct);

        return finalDamage;
    }

    protected int getDefenseFlux() {

        //Have the defense strength fluctuate by +- 8%
        int finalBlocked = 0;
        Random getRand = new Random();
        double rFluct = getRand.nextDouble() * 0.08;
        boolean side = getRand.nextBoolean();
        if (!side) rFluct = -rFluct;
        rFluct++;
        finalBlocked = (int) ((defense * rFluct) * 0.7);

        return finalBlocked;
    }

    public boolean setName(String setname) {

        if(setname.length()>10) return false;

        name = setname;

        StringBuilder spaces = new StringBuilder(10);

        for (int i = 0; i < name.length()-MAX_NAME_SIZE; i++) {

            spaces.append(' ');

        }

        spacesAfterName = spaces.toString();

        return true;


    }

    public String getName() {
        return name;
    }
    public String getSpacesAfterName() {
        return spacesAfterName;
    }
    public int getLevel() {
        return level;
    }
    public String getClss() {
        return clss;
    }
    public int getHealth() {
        return health;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public int getStrength() {
        return strength;
    }
    public int getDefense() {
        return defense;
    }
    public int getAgility() {
        return agility;
    }

    public int getXpos() {
        return xpos;
    }
    public int getYpos() {
        return ypos;
    }
    public int getPrevXpos() {
        return prevXpos;
    }
    public int getPrevYpos() {
        return prevYpos;
    }

    public int getPotions() {
        return potions;
    }

    public String getSpacesBeforeLevel() {
        return spacesBeforeLevel;
    }

    public void heal(int amount) {

        health+=amount;

        if (health > maxHealth) health = maxHealth;


    }

    public int getAttackPower() {
        int damage = this.getAttackFlux();
        double isCrit = randGen.nextDouble();
        if(isCrit <= 0.02) {
            damage= damage * 2;
        }
        return damage;
    }

    public int getDefensePower() {
        return this.getDefenseFlux();
    }

    //Moves the character with a specified direction
    //For enemies, use moveEnemy in Arena instead
    public void move(int direction) {

        prevXpos = xpos;
        prevYpos = ypos;

        if(direction == Arena.UP) {

            ypos++;
            normalizeLocation();

        }
        else if(direction == Arena.DOWN) {

            ypos--;
            normalizeLocation();

        }
        if(direction == Arena.RIGHT) {

            xpos++;
            normalizeLocation();

        }
        if(direction == Arena.LEFT) {

            xpos--;
            normalizeLocation();

        }


    }

    public void setPosition(int x, int y) {

        xpos = x;
        prevXpos = x;
        ypos = y;
        prevYpos = y;

    }



}
