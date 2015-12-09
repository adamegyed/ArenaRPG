package com.adamegyed.arenarpg.characters;

/**
 * Created by Adam on 11/11/15.
 * The Rogue
 * This class is a player class that is a glass cannon - powerful, but frail
 * Has a much higher critical hit chance
 */
public class Rogue extends Player {

    public Rogue() {

        //Set the Rogue's stats - health, strength, defense, agility, and level
        super( 110, 33, 25, 17, 1);
        this.potions = 1;
        this.clss = "Rogue  ";

    }

    //Overrides other getAttackPower method to increase critical hit chance
    public int getAttackPower() {
        int damage = this.getAttackFlux();
        double isCrit = randGen.nextDouble();
        if(isCrit <= 0.33) {
            damage= damage * 2;
        }
        return damage;

    }
}
