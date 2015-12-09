package com.adamegyed.arenarpg.characters;

import java.util.Random;

/**
 * Created by Adam on 10/21/15.
 * The Enemy Class
 * This is a subclass of Characters and modifies it to have different constructors
 * for different Enemy types
 */
public class Enemy extends Characters {

    private Random randGen = new Random();

    public int timeToWander = 3;

    //Constructor
    public Enemy(int type) {

        super( 50, 30, 20, 5, 1);
        //Set the Enemy's stats - health, strength, defense, agility, and level
        //super( 50, 30, 20, 5, 1);

        if (type==Characters.ZOMBIE) {
            setAllStats(50, 30, 30, 5, 1);
            this.clss = "Zombie";
            spacesBeforeLevel = " ";
        }
        if (type==Characters.ALIEN) {
            setAllStats( 80, 40, 38, 7, 5);
            this.clss = "Alien ";
            spacesBeforeLevel = " ";
        }
        if (type==Characters.DRAGON) {
            setAllStats( 200, 60, 50, 10, 10);
            this.clss = "Dragon";
            spacesBeforeLevel = "";
        }

        timeToWander = randGen.nextInt(4) + 1;


    }

    //Randomize its location, within the proper bounds
    public void randomSpawn() {

        xpos = randGen.nextInt(5);
        ypos = randGen.nextInt(5);
        prevXpos = xpos;
        prevYpos = ypos;


    }


    //Manage dealing damage to the enemy and to prevent negative health values
    public void dealDamageToEnemy(int damage) {

            health -= damage;
            if (this.health <= 0) this.health = 0;


    }





    // Simply checks if the enemy has died yet
    public boolean checkIfEnemyDead() {


        if (health <= 0) {

            health = 0;
            return true;


        }
        else return false;

    }

}
