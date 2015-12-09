package com.adamegyed.arenarpg.characters;


/**
 * Created by Adam on 10/29/15.
 * The Warrior player class
 * This class is defensive and plays like a tank
 * Starts off with an extra potion to heal himself
 */
public class Warrior extends Player {



    //Constructor
    public Warrior() {

        //Set the Warrior's stats - health, strength, defense, agility, and level
        super( 120, 35, 28, 5, 1);
        //Give warrior two potions
        this.potions = 2;
        this.clss = "Warrior";

    }



}
