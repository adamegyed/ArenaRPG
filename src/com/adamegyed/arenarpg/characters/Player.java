package com.adamegyed.arenarpg.characters;

import javax.swing.*;

/**
 * Created by Adam on 10/21/15.
 * The Player
 * This class is a subclass of Characters, and is further extended by
 * each player class - Warrior, Rogue, and Wizard, and is used to have a common type between all player classes
 * as well as provide functions needed for all players
 */
public class Player extends Characters {



    //Bounce any constructor straight up to the Characters constructor
    public Player(int sethealth, int setstrength, int setdefense, int setagility, int setlevel) {
        super (sethealth, setstrength, setdefense, setagility, setlevel);
    }

    //Raise stats on level up
    public void levelUp() {

        level++;
        health += 5;
        maxHealth += 5;
        strength += 3;
        defense += 3;
        agility += 1;


    }


    //Deals damage to the player and makes sure it doesn't go over into negative
    public void dealDamageToPlayer(int damage) {

        health -= damage;
        if (this.health <= 0) this.health = 0;


    }

    //Checks if player has died
    public boolean checkIfPlayerDead() {

        if (this.health <= 0) {
            this.health = 0;
            return true;
        }

        else return false;

    }

    //Heal to full health and remove one potion
    //This method should be called after checking if th player has more than one potion
    public void drinkPotion() {

        health = maxHealth;
        potions--;

    }
}
