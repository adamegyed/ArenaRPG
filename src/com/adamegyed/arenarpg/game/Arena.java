package com.adamegyed.arenarpg.game;

import com.adamegyed.arenarpg.characters.Characters;
import com.adamegyed.arenarpg.characters.Enemy;
import com.adamegyed.arenarpg.characters.Player;
import com.adamegyed.arenarpg.windows.GameWindow;

import javax.swing.*;
import java.util.*;

/**
 * Created by Adam on 10/19/15.
 * Arena defines one instance of the game, including the player, a list of enemies, an instance
 * of the GameWindow class, and provides the functions needed to power game actions
 */
public class Arena {

    private GameWindow gWindow; // Reference to its own window
    private GameManager gameManager; // Reference to the gamemanger, only used to close itself.

    private Hashtable<String, Enemy> enemyMap; // hashtable to hold enemies by their location coordinates

    private Player player; //The player

    private char[] pMD = new char[25]; // player map display
    private char[] eMD = new char[25]; // enemy map display

    private Random randGen; //Random generator used by the game

    private int defeatedEnemies; // int used to count player's defeated enemies, also in checking win conditions

    // Final variables used to dictate amount of enemies
    private final int maxZombies;
    private final int maxAliens;
    private final int maxDragons;
    private final int maxEnemies;

    private final int ySize = 5; // Size of the map vertically
    private final int xSize = 5; // Size of the map horizontally

    //Arrays to use for the names of enemies during spawning
    private static final String[] possibleZombieNames = {"Philip","Napoleon","Henry","George"};
    private static final String[] possibleAlienNames = {"Nimrod","Wiggles","Dimwit"};
    private static final String[] possibleDragonNames = {"Nogard"}; // Dragon backwards

    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;



    // Create new game, window hidden
    // Will generate and spawn enemies
    public Arena(GameManager gM) {

        //Keep a reference to its own gamemanager
        gameManager = gM;

        //Clear the map displays
        for (int i = 0; i < 25; i++) {
            pMD[i]=' ';
            eMD[i]=' ';
        }
        //Construct the random number generator
        randGen = new Random();

        //Set the maximum enemy counts
        maxZombies = 4;
        maxAliens = 3;
        maxDragons = 1;
        maxEnemies = maxZombies + maxAliens + maxDragons;
        defeatedEnemies = 0;

        //Construct enemy map hashtable
        enemyMap = new Hashtable<>();

        //Fill the enemy map with newly constructed enemies
        spawnEnemies();

        gWindow = new GameWindow(this);
        gWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    //Loads the player created in the game manager and makes the game window visible
    public void loadPlayer(Player setPlayer) {

        player = setPlayer;
        player.setPosition(2,2);

        gWindow.updateWindow();
        gWindow.packWindow();
        gWindow.setVisible(true);
    }

    //Disposes of the window and closes the game
    //Only reference to the gamemanager in this class
    public void closeGame() {

        gWindow.dispose();
        gameManager.closeGame();


    }

    //Called at the end of every turn, wanders the enemies, attacks the player, and updates the game window
    private void endOfTurn() {


        //Old solution with iterators, replaced with forEach

        /*
        for (Enemy enemy : new ArrayList<Enemy>(enemyMap.values())) {

            enemyAction(enemy);
        }*/


        //Preferred over iterators because forEach does actions concurrently
        new ArrayList<>(enemyMap.values())
                .stream()
                .forEach( enemy -> enemyAction(enemy));

        //Heal 1 hp per turn
        player.heal(1);

        gWindow.updateWindow();

    }

    // Returns a string combination of an coordinate and a y coordinate
    // Is used to store enemy positions by coordinates in the hastable
    public String coordToHKey(int x, int y) {

        return "" + x + "_" + y;

    }

    //Called by the gamewindow to get the text to display to the map
    public String getMapText() {

        clearPMap();
        clearEMap();

        findPMap();
        findEMap();

        //No StringBuilder necessary, compiler will use that automatically
        return      "+---+---+---+---+---+\n"
                +   "| "+pMD[20]+" | "+pMD[21]+" | "+pMD[22]+" | "+pMD[23]+" | "+pMD[24]+" |\n"
                +   "| "+eMD[20]+" | "+eMD[21]+" | "+eMD[22]+" | "+eMD[23]+" | "+eMD[24]+" |\n"
                + 	"+---+---+---+---+---+\n"
                +   "| "+pMD[15]+" | "+pMD[16]+" | "+pMD[17]+" | "+pMD[18]+" | "+pMD[19]+" |\n"
                +   "| "+eMD[15]+" | "+eMD[16]+" | "+eMD[17]+" | "+eMD[18]+" | "+eMD[19]+" |\n"
                + 	"+---+---+---+---+---+\n"
                +   "| "+pMD[10]+" | "+pMD[11]+" | "+pMD[12]+" | "+pMD[13]+" | "+pMD[14]+" |\n"
                +   "| "+eMD[10]+" | "+eMD[11]+" | "+eMD[12]+" | "+eMD[13]+" | "+eMD[14]+" |\n"
                + 	"+---+---+---+---+---+\n"
                +   "| "+pMD[5]+" | "+pMD[6]+" | "+pMD[7]+" | "+pMD[8]+" | "+pMD[9]+" |\n"
                +   "| "+eMD[5]+" | "+eMD[6]+" | "+eMD[7]+" | "+eMD[8]+" | "+eMD[9]+" |\n"
                + 	"+---+---+---+---+---+\n"
                +   "| "+pMD[0]+" | "+pMD[1]+" | "+pMD[2]+" | "+pMD[3]+" | "+pMD[4]+" |\n"
                +   "| "+eMD[0]+" | "+eMD[1]+" | "+eMD[2]+" | "+eMD[3]+" | "+eMD[4]+" |\n"
                + 	"+---+---+---+---+---+";
    }

    // Clear player map
    private void clearPMap() {

        //Sets the character at the player's previous position to a space
        pMD[(player.getPrevXpos() + (xSize * player.getPrevYpos()))] = ' ';

    }

    //Clear enemy map
    private void clearEMap() {

        for (int i = 0; i < 25; i++) {
            eMD[i] = ' ';
        }


    }

    //Find player map
    private void findPMap() {

        pMD[(player.getXpos() + (xSize * player.getYpos()))] = 'P';

    }

    //Find enemy map
    private void findEMap() {

        Enemy enemyMaybe;

        for (int x = 0; x < 5; x++) {

            for (int y = 0; y < 5; y++) {

                enemyMaybe = enemyMap.get(coordToHKey(x,y));
                if (enemyMaybe!= null) {
                    if (enemyMaybe.getClss().equals("Zombie")) {
                        eMD[(x+(5*y))] = 'Z';
                    }
                    else if (enemyMaybe.getClss().equals("Alien ")) {
                        eMD[(x+(5*y))] = 'A';
                    }
                    else if (enemyMaybe.getClss().equals("Dragon")) {
                        eMD[(x+(5*y))] = 'D';
                    }
                }
            }

        }




    }

    //Function called by gamewindow upon pressing a movement button
    public void movePlayer(int direction) {

        if (direction==UP) {

            player.move(direction);
            gWindow.consoleOut("You moved north.");

        }
        else if (direction==RIGHT) {

            player.move(direction);
            gWindow.consoleOut("You moved east.");

        }
        else if (direction==DOWN) {

            player.move(direction);
            gWindow.consoleOut("You moved south.");

        }
        else if (direction==LEFT) {

            player.move(direction);
            gWindow.consoleOut("You moved west.");

        }

        endOfTurn();
    }

    // Called by gamewindow on pressing the heal button
    public void healPlayer() {

        if(player.getHealth() < player.getMaxHealth()) {
            player.heal(15);
            // heal will automatically cap out at the player's max health
            if (player.getHealth()==player.getMaxHealth()) gWindow.consoleOut("You healed fully.");
            else gWindow.consoleOut("You healed 15 health.");
        }
        else gWindow.consoleOut("You are already at full health!");

        endOfTurn();
    }

    public Enemy getEnemyAtPlayer() {

        return enemyMap.get(coordToHKey(player.getXpos(),player.getYpos()));

    }

    //Spawns enemies according to max enemy counts and stores them in the hashtable
    private void spawnEnemies() {



        for (int i = 0; i < maxZombies; i++) {

            spawnEnemy(possibleZombieNames[i],Characters.ZOMBIE);

        }
        for (int i = 0; i < maxAliens; i++) {

            spawnEnemy(possibleAlienNames[i],Characters.ALIEN);

        }
        for (int i = 0; i < maxDragons; i++) {

            spawnEnemy(possibleDragonNames[i],Characters.DRAGON);

        }


    }

    //Constructs and returns a reference to a new enemy with a specified name and type
    public Enemy spawnEnemy(String name, int type) {

        Enemy spawnEnemyInPlace;

        Enemy temp = new Enemy(type);
        temp.setName(name);

        //Check to see if there is another enemy already in that space
        do {
            temp.randomSpawn();
            spawnEnemyInPlace = enemyMap.get(coordToHKey(temp.getXpos(),temp.getYpos()));
        } while (spawnEnemyInPlace!=null);

        enemyMap.put(coordToHKey(temp.getXpos(),temp.getYpos()), temp);

        return temp;




    }

    //nMoving an enemy with a specified direction
    private void moveEnemy(Enemy enemy, int direction) {

        // move the enemy and reassign them in the hashtable
        // checks to see if there is an enemy in the position it is trying to move, if it fails
        //if enemymaybe is null, there is not enemy entered into the hashtable at that coordinate

        Enemy maybeEnemy;

        if (direction==UP) {

            maybeEnemy = enemyMap.get(coordToHKey(enemy.getXpos(),enemy.getYpos()+1));

            if(maybeEnemy==null) {
                enemyMap.remove(coordToHKey(enemy.getXpos(),enemy.getYpos()));
                enemy.move(direction);
                enemyMap.put(coordToHKey(enemy.getXpos(),enemy.getYpos()),enemy);

            }

        }
        else if (direction==DOWN) {

            maybeEnemy = enemyMap.get(coordToHKey(enemy.getXpos(),enemy.getYpos()-1));

            if(maybeEnemy==null) {
                enemyMap.remove(coordToHKey(enemy.getXpos(),enemy.getYpos()));
                enemy.move(direction);
                enemyMap.put(coordToHKey(enemy.getXpos(),enemy.getYpos()),enemy);

            }

        }
        else if (direction==RIGHT) {

            maybeEnemy = enemyMap.get(coordToHKey(enemy.getXpos()+1,enemy.getYpos()));

            if(maybeEnemy==null) {
                enemyMap.remove(coordToHKey(enemy.getXpos(),enemy.getYpos()));
                enemy.move(direction);
                enemyMap.put(coordToHKey(enemy.getXpos(),enemy.getYpos()),enemy);

            }

        }
        else if (direction==LEFT) {

            maybeEnemy = enemyMap.get(coordToHKey(enemy.getXpos()-1,enemy.getYpos()));

            if(maybeEnemy==null) {
                enemyMap.remove(coordToHKey(enemy.getXpos(),enemy.getYpos()));
                enemy.move(direction);
                enemyMap.put(coordToHKey(enemy.getXpos(),enemy.getYpos()),enemy);

            }

        }

    }

    //Wanders one enemy in a random direction if the enemy's time to wander has arrived
    // if not, deducts on from time to wander
    private void wanderEnemy(Enemy enemy) {

        if (enemy.timeToWander<=0) {

            moveEnemy(enemy,randGen.nextInt(4) + 1);

            if (enemy.getPrevXpos()==enemy.getXpos() && enemy.getPrevYpos()==enemy.getYpos()) {
                moveEnemy(enemy, randGen.nextInt(4) + 1);
            }

            enemy.timeToWander += 3;



        }
        else enemy.timeToWander--;

    }

    //Called when an enemy attacks the player
    public void enemyAttackPlayer(Enemy enemy) {

        boolean didDodge = false;

        int chanceToDodge;

        int enemyAttackPower;
        int blockedDamage;
        int finalTakenDamage;

        String enemyClass = enemy.getClss().trim();

        gWindow.consoleOut("The enemy "+enemyClass+" "+enemy.getName()+" attacks you.");

        chanceToDodge = randGen.nextInt(100) + 1;

        //Dodge based on the player's agility
        if (chanceToDodge < player.getAgility()) {
            didDodge = true;
        }

        if(!didDodge) { // IF FAILED TO DODGE

            //Get the player's defense power, accounting for randomness
            blockedDamage = player.getDefensePower();
            if (blockedDamage > (int) (1.3 * player.getDefense())) {
                gWindow.consoleOut("*** COUNTERED ***");
            }

            //Get the enemy's attack power, accounting for randomness, and check if it is a critical hit or not
            enemyAttackPower = enemy.getAttackPower();
            if (enemyAttackPower > (int) (1.3 * enemy.getStrength())) {
                gWindow.consoleOut("*** CRITICAL HIT ***");
            }

            if (blockedDamage >= enemyAttackPower) {     //Blocked All Damage

                gWindow.consoleOut("You blocked all damage from the enemy.");

            }
            else { // Block is less than attack

                finalTakenDamage = enemyAttackPower - blockedDamage;
                gWindow.consoleOut("The enemy dealt " + enemyAttackPower + " damage, but you "
                        + "blocked " + blockedDamage + " \nand received " + finalTakenDamage +".");
                player.dealDamageToPlayer(finalTakenDamage);
                if (player.checkIfPlayerDead()) playerDeadAction();

            }
        }
        else { // IF SUCCESSFUL DODGE

            gWindow.consoleOut("You dodged the enemy's attack!");

        }


    }

    //Very similar to enemy attacking player method, but changed console output
    public void playerAttackEnemy(Enemy enemy) {


        boolean didDodge = false;

        int chanceToDodge;

        int playerAttackPower;
        int blockedDamage;
        int finalTakenDamage;

        //Have to trim enemy class because Alien has an extra space to keep it flush with the window
        String enemyClass = enemy.getClss().trim();

        gWindow.consoleOut("You attack the "+enemyClass+" "+enemy.getName());

        chanceToDodge = randGen.nextInt(100) + 1;

        if (chanceToDodge < enemy.getAgility()) {
            didDodge = true;
        }

        if(!didDodge) { // IF FAILED TO DODGE

            blockedDamage = enemy.getDefensePower();
            if (blockedDamage > (int) (1.3 * enemy.getDefense())) {
                gWindow.consoleOut("*** COUNTERED ***");
            }

            playerAttackPower = player.getAttackPower();
            if (playerAttackPower > (int) (1.3 * player.getStrength())) {
                gWindow.consoleOut("*** CRITICAL HIT ***");
            }

            if (blockedDamage >= playerAttackPower) {     //Blocked All Damage

                gWindow.consoleOut("The enemy blocked all damage from you.");

            }
            else { // Block is less than attack

                finalTakenDamage = playerAttackPower - blockedDamage;
                gWindow.consoleOut("You dealt " + playerAttackPower + " damage, but the enemy "
                        + "blocked " + blockedDamage + " \nand received " + finalTakenDamage +".");
                enemy.dealDamageToEnemy(finalTakenDamage);
                if (enemy.checkIfEnemyDead()) {

                    enemyMap.remove(coordToHKey(enemy.getXpos(),enemy.getYpos()));
                    player.levelUp();
                    gWindow.consoleOut("You defeated the enemy and leveled up!");

                    defeatedEnemies++;
                    if(defeatedEnemies>=maxEnemies) playerWinAction();

                }

            }
        }
        else { // IF SUCCESSFUL DODGE

            gWindow.consoleOut("The enemy dodged your attack");

        }
    }

    //Player attempts to attack an enemy in own tile
    public void playerAttackLocal() {
        if (getEnemyAtPlayer()!=null) playerAttackEnemy(getEnemyAtPlayer());
        else gWindow.consoleOut("There is no enemy to attack!");
        endOfTurn();
    }

    //Called by gamewindow on pressing drinkpotion button
    public void playerDrinkPotion() {

        if (player.getHealth()==player.getMaxHealth()) {
            gWindow.consoleOut("You are already at full health!");
        }
        else if (player.getPotions()==0) {
            gWindow.consoleOut("You have no potions!");
        }
        else {
            //Bring player to max health
            player.drinkPotion();
            gWindow.consoleOut("You drank the potion and healed fully.");
            endOfTurn();
        }

    }

    public Player getPlayer() {
        return player;
    }

    //Close the game if the player loses
    private void playerDeadAction() {

        JOptionPane.showMessageDialog(null,"You died... \nTry looking in the help section for tips.","U DED LOL",JOptionPane.WARNING_MESSAGE);
        closeGame();

    }

    //Show a win message after all enemies have been defeated
    private void playerWinAction() {

        gWindow.consoleOut("You Win!");
        JOptionPane.showMessageDialog(null,"You Win!! ","Victory!",JOptionPane.PLAIN_MESSAGE);

    }

    //Determines what an enemy will do on its own turn
    private void enemyAction(Enemy enemy) {

        //If health is less that half of max health, heal
        if (enemy.getHealth() < enemy.getMaxHealth() / 2) {
            enemy.heal(7);
        }

        //If on the same tile as player, attack
        if (enemy.getXpos()==player.getXpos() && enemy.getYpos()==player.getYpos()) enemyAttackPlayer(enemy);

        //Else wander around
        else wanderEnemy(enemy);


    }




}
