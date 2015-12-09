package com.adamegyed.arenarpg.characters;

/**
 * Created by Adam on 11/17/15.
 * The Wizard player class
 * THe class plays similarly offensive as the rogue,
 * but has risky defense - can counter attacks, but low initial defense
 */
public class Wizard extends Player {

    public Wizard() {

        //Set the Wizard's stats - health, strength, defense, agility, and level
        super( 105, 38, 25, 9, 1);
        this.potions = 1;
        this.clss = "Wizard ";

    }

    //Overide the method in the superclass by giving it a chance to double defense power
    public int getDefensePower() {
        int power = this.getDefenseFlux();
        double isCounter = randGen.nextDouble();
        if (isCounter <= 0.25) {
            power = power * 2;
        }
        return power;
    }
}
