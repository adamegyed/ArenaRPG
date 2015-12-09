package com.adamegyed.arenarpg.game;

import com.adamegyed.arenarpg.windows.CharacterCreate;
import com.adamegyed.arenarpg.windows.HelpWindow;
import com.adamegyed.arenarpg.windows.MainMenu;
import com.adamegyed.arenarpg.characters.*;

import javax.swing.*;

/**
 * Created by Adam on 10/23/15.
 * One instance of a GameManager is created per running application,
 * While arena is constructed and left for garbage collection each time a new game is created
 */
public class GameManager {

    //References to its windows
    MainMenu mainMenu;
    CharacterCreate characterCreate;
    HelpWindow helpWindow;

    //The currently active game
    Arena activeGame;

    Player tempPlayer; //Temporary location to store the player during character creation


    boolean isGameActive; //Is the current game active, used to prevent multiple games from being created at once

    public GameManager() {

        //Construct windows
        mainMenu = new MainMenu(this);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        characterCreate = new CharacterCreate(this);
        characterCreate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Helpwindow has no buttons, so no reference is needed
        helpWindow = new HelpWindow();
        //Instead, attempting to close the window will hide it
        helpWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        isGameActive = false;
    }

    //Called when New Game is pressed on the Main Menu
    public void newGame() {

        //Checks if current game is active
        if (!isGameActive) {

            activeGame = new Arena(this); // New arena with its GameManager being this
            isGameActive = true;

            characterCreate.setVisible(true);
        }
    }

    public void closeGame() {

        //Leaves the game up to garbage collection
        //At this point, window is already disposed
        activeGame = null;
        isGameActive = false;

    }

    //Create the player according to class and name, as well as load the player into the game
    //Calling this makes the game window visible
    public void createPlayer(String name, int type) {

        if (type==Characters.WARRIOR) {
            tempPlayer = new Warrior();
            tempPlayer.setName(name);
            characterCreate.disapear();
            activeGame.loadPlayer(tempPlayer);
        }
        if (type==Characters.ROGUE) {
            tempPlayer = new Rogue();
            tempPlayer.setName(name);
            characterCreate.disapear();
            activeGame.loadPlayer(tempPlayer);
        }
        if (type==Characters.WIZARD) {
            tempPlayer = new Wizard(); 
            tempPlayer.setName(name);
            characterCreate.disapear();
            activeGame.loadPlayer(tempPlayer);
        }

    }

    //Toggle visiblilty of the help menu each time the help button is pressed
    public void toggleHelpMenu() {

        helpWindow.setVisible(!helpWindow.isShowing()); //Opposite of whether its showing or not

    }


}
