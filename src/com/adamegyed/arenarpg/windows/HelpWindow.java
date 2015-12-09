package com.adamegyed.arenarpg.windows;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Adam on 11/25/15.
 * The Help Window
 * A simple text area within a scroll pane is used
 * to display all help text. No ActionListener is needed
 * because the only interaction with this window is
 * Scrolling, and attempting to close it will hide it.
 */
public class HelpWindow extends JFrame {



    JTextArea helpText;

    JScrollPane scrollPane;

    JPanel helpPanel;

    public HelpWindow() {

        super("ArenaRPG Help");
        init();
        this.setLocation(3,3);
        this.setResizable(false);
        this.pack();
        //this.setVisible(true); not made visible oon construction
    }

    //Create and draw all components
    private void init() {

        helpText = new JTextArea(25,55);
        helpText.setMargin(new Insets(3,3,3,3)); //Leave some room to the scrollbar won't clutter the reading area
        helpText.setEditable(false);
        helpText.setFont(new Font("Monaco", Font.PLAIN, 13));

        scrollPane = new JScrollPane(helpText,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        helpPanel = new JPanel();


        helpPanel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4)); //Space the text away from the border
        helpPanel.setBackground(Color.lightGray);
        helpPanel.add(scrollPane);

        this.setLayout(new BorderLayout());

        this.add(helpPanel,BorderLayout.CENTER);

        //The help text
        String help = "------------------WELCOME TO ARENARPG------------------\n" +
                "\n" +
                "You are an adventurer, trapped inside of an arena and\n" +
                "pitted against zombies, aliens, and a powerful dragon.\n" +
                "Fight off all these opponents to survive and escape \n" +
                "the arena!\n" +
                "\n" +
                "---Start-----------------------------------------------\n" +
                "\n" +
                "You can start your game as a warior, rogue, or wizard. \n" +
                "Enter your name and pick your class from the character \n" +
                "creation screen. Please note, your name must be 10 \n" +
                "characters or fewer.\n" +
                "\n" +
                "---Controls--------------------------------------------\n" +
                "The game has buttons at the bottom of the game window \n" +
                "to control your character. These buttons are also bound\n" +
                "to keys:\n" +
                "\n" +
                "W/A/S/D - Move north, south, east, and west\n" +
                "SPACE - Attack\n" +
                "H/F - Heal\n" +
                "P/Z - Drink potion\n" +
                "\n" +
                "---The Window------------------------------------------\n" +
                "The left hand side of your screen will show details \n" +
                "about your character. Your character has stats for \n" +
                "health, strength, defense, and agility. In addition to\n" +
                "these, you have a level and a number of potions at your\n" +
                "disposal.\n" +
                "\n" +
                "In the center of the screen will be the map of the \n" +
                "arena. It will show where you and the enemies are.\n" +
                "\n" +
                "  Key:\n" +
                "P - You, the player\n" +
                "Z - Zombie\n" +
                "A - Alien\n" +
                "D - Dragon\n" +
                "\n" +
                "You can occupy the same space as an enemy, but no two \n" +
                "enemies can be in the same space.\n" +
                "\n" +
                "On the right hand side of the screen will be the stats\n" +
                "of the enemy currently on your space. This information\n" +
                "will be important to your survival!\n" +
                "\n" +
                "Above the map will be the console. This will log \n" +
                "everything that happens to you in the arena. It will \n" +
                "also show important information regarding attack \n" +
                "damage, so monitor it carefully.\n" +
                "\n" +
                "Below the map will be the buttons used to control the \n" +
                "game. You can use these or the keyboard binds shown \n" +
                "earlier. Two other buttons exist on this panel: \"Reset \n" +
                "Window\" and \"Close Game\", resizing the window to its \n" +
                "original size and closing the game, respectively.\n" +
                "\n" +
                "---Gameplay--------------------------------------------\n" +
                "You can chose one action per turn, afterwards, each \n" +
                "enemy takes their turn. If, at the end of your turn,\n" +
                "you are on the same tile as an enemy, it will attack \n" +
                "you. The enemies will also wander around the arena, \n" +
                "but will not run from a fight. \n" +
                "\n" +
                "Attacking an enemy and how much damage you deal will be\n" +
                "based on your strength and the enemy's defense. As you \n" +
                "attack the enemies, watch your health. Should it fall \n" +
                "too low for your comfort, simply move away from the \n" +
                "enemy and heal for a few turns, then return to the \n" +
                "battle. \n" +
                "\n" +
                "When you defeat an enemy, you will level up and all \n" +
                "your stats will increase. Knowing this, it might not \n" +
                "be wise to attack the dragon straight from the start. \n" +
                "Try defeating weaker enemies first before attacking \n" +
                "the higher-leveled ones.\n" +
                "\n" +
                "Any attack, including attacks from enemies, has a \n" +
                "chance to be a critical hit. Critical hits double the\n" +
                "power of the attack and inflict serious damage.\n" +
                "Normally, the chance for one to happen is very low, \n" +
                "at about 2%, but this changes if you are playing as\n" +
                "the rogue.\n" +
                "\n" +
                "While defending, the wizard has the ability to counter\n" +
                "attacks. A successful counter will double your defense\n" +
                "power for that turn.\n" +
                "\n" +
                "Drinking a potion will restore you to full health. This\n" +
                "can be very handy in dire situations, so make use of \n" +
                "your potions.\n" +
                "\n" +
                "---Differences between classes-------------------------\n" +
                "Each class has different attributes and playstyles. \n" +
                "\n" +
                "Warrior: Highest HP and defense, and starts with two \n" +
                "potions instead of one.\n" +
                "\n" +
                "Rogue: Weaker stat-wise, but has a 1/3 chance to land a\n" +
                "critical hit each attak. Has high starting agility.\n" +
                "\n" +
                "Wizard: High attack stat, and has a 1/4 chance to \n" +
                "counter anytime you are attacked.\n" +
                "\n" +
                "-----------------Thanks, and have fun!-----------------";

        helpText.setText(help);



    }



}
