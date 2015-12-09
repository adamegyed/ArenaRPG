package com.adamegyed.arenarpg.windows;

import com.adamegyed.arenarpg.characters.Enemy;
import com.adamegyed.arenarpg.characters.Player;
import com.adamegyed.arenarpg.game.Arena;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by Adam on 10/19/15.
 * The Game Window - main view of the game
 * This window is constructed and left for garbage collection repeatedly]
 * for each new game created
 *
 */
public class GameWindow extends JFrame implements ActionListener {

    //Window Components
    private JTextArea map;
    private JTextArea aConsole;

    private JScrollPane cScroll;

    private JButton bClose;

    private JButton bUp;
    private JButton bDown;
    private JButton bLeft;
    private JButton bRight;

    private JButton bAttack;
    private JButton bHeal;
    private JButton bDrinkPotion;

    private JButton bResizeW;

    private JButton bOptions;
    private JButton bSave;

    private JLabel lPname;
    private JLabel lPlevelcls;
    private JLabel lPhealth;
    private JLabel lPstrength;
    private JLabel lPdefense;
    private JLabel lPagility;
    private JLabel lPpotions;

    private JLabel lEname;
    private JLabel lElevelclss;
    private JLabel lEhealth;
    private JLabel lEstrength;
    private JLabel lEdefense;
    private JLabel lEagility;
    private JLabel lEpotions;

    private JPanel mapPanel;
    private JPanel playerPanel;
    private JPanel buttonPanel;
    private JPanel enemyPanel;
    private JPanel consolePanel;

    private Border mapBorder;
    private Border playerBorder;
    private Border enemyBorder;
    private Border consoleBorder;

    private BorderLayout GameWindowBordLay;


    private Arena game;

    private Font labelFont;

    private Action buttonListener;

    public GameWindow (Arena arena) {

        super("ArenaRPG");
        createButtonListener();
        init();
        this.setResizable(true);
        this.pack();
        this.pack(); //Called twice to avoid weird resizing bug that occasionally happens
        this.setLocationRelativeTo(null); //Center the window
        this.setVisible(false);
        game = arena; //Hold a reference to the its corresponding game

    }

    //Initial window configuration
    //Lots of Component construction and fonts and colors are set
    private void init() {

        //Labels
        labelFont = new Font("Consolas", Font.PLAIN, 14);

        // Player stat labels, blank initialization
        lPname = new JLabel("  Name  ");
        lPname.setFont(labelFont);
        lPlevelcls = new JLabel("  Lv. 1 Class  ");
        lPlevelcls.setFont(labelFont);
        lPhealth = new JLabel("  Health: -1  ");
        lPhealth.setFont(labelFont);
        lPstrength = new JLabel("  Strength: -1  ");
        lPstrength.setFont(labelFont);
        lPdefense = new JLabel("  Defense: -1  ");
        lPdefense.setFont(labelFont);
        lPagility = new JLabel("  Agility: -1  ");
        lPagility.setFont(labelFont);
        lPpotions = new JLabel("  Potions: -1  ");
        lPpotions.setFont(labelFont);

        //Enemy statpanel labels
        lEname = new JLabel("  Name  ");
        lEname.setFont(labelFont);
        lElevelclss = new JLabel("  Level -1 Class      ");
        lElevelclss.setFont(labelFont);
        lEhealth = new JLabel("  Health: -1");
        lEhealth.setFont(labelFont);
        lEstrength = new JLabel("  Strength: -1  ");
        lEstrength.setFont(labelFont);
        lEdefense = new JLabel("  Defense: -1");
        lEdefense.setFont(labelFont);
        lEagility = new JLabel("  Agility: -1  ");
        lEagility.setFont(labelFont);



        // Map
        map = new JTextArea(16,21);
        map.setEditable(false);
        map.setFont(new Font("Consolas", Font.BOLD, 18));

        map.setMargin(new Insets(2,2,2,2));
        map.setBackground(Color.black);
        map.setForeground(Color.green);

        aConsole = new JTextArea(8,40);
        aConsole.setMargin(new Insets(3,3,3,3));
        aConsole.setEditable(false);
        aConsole.setFont(new Font("Consolas", Font.PLAIN, 17));
        aConsole.setText("Welcome to ArenaRPG!!\n");
        aConsole.setBackground(Color.darkGray);
        aConsole.setForeground(Color.white);
        cScroll = new JScrollPane(aConsole,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Button construction and addition of
        // Action Listeners and Keypress listeners
        bClose = new JButton("Close Game");
        bClose.addActionListener(this);


        bUp = new JButton("North");
        bDown = new JButton("South");
        bLeft = new JButton("West");
        bRight = new JButton("East");
        bUp.addActionListener(this);
        bUp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"),
                "upEvent");
        bUp.getActionMap().put("upEvent",
                buttonListener);
        bDown.addActionListener(this);
        bDown.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"),
                "downEvent");
        bDown.getActionMap().put("downEvent",
                buttonListener);
        bLeft.addActionListener(this);
        bLeft.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"),
                "leftEvent");
        bLeft.getActionMap().put("leftEvent",
                buttonListener);
        bRight.addActionListener(this);
        bRight.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"),
                "rightEvent");
        bRight.getActionMap().put("rightEvent",
                buttonListener);

        bAttack = new JButton("Attack");
        bHeal = new JButton("Heal");

        bAttack.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"),
                "attackEvent");
        bAttack.getActionMap().put("attackEvent",
                buttonListener);
        bHeal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F"),
                "healEvent");
        bHeal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("H"),
                "healEvent");
        bHeal.getActionMap().put("healEvent",
                buttonListener);


        bAttack.addActionListener(this);
        bHeal.addActionListener(this);
        bDrinkPotion = new JButton("Drink Potion");
        bDrinkPotion.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Z"),
                "potionEvent");
        bDrinkPotion.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"),
                "potionEvent");
        bDrinkPotion.getActionMap().put("potionEvent",
                buttonListener);
        bDrinkPotion.addActionListener(this);

        bResizeW = new JButton("Reset Window");
        bResizeW.addActionListener(this);

        bOptions = new JButton("Options");
        bOptions.addActionListener(this);
        bSave = new JButton("Save Game");
        bSave.addActionListener(this);

        //Panels
        mapPanel = new JPanel();
        playerPanel = new JPanel(new GridLayout(4,2));
        enemyPanel = new JPanel(new GridLayout(4,2));
        buttonPanel = new JPanel(new GridLayout(3,3));
        consolePanel = new JPanel(new GridLayout(1,1));

        //Add components to Panels
        mapPanel.add(map);
        mapPanel.setBackground(Color.lightGray);

        playerPanel.add(lPname);
        playerPanel.add(lPlevelcls);
        playerPanel.add(lPhealth);
        playerPanel.add(lPstrength);
        playerPanel.add(lPdefense);
        playerPanel.add(lPagility);
        playerPanel.add(lPpotions);

        playerPanel.setBackground(Color.lightGray);


        enemyPanel.add(lEname);
        enemyPanel.add(lElevelclss);
        enemyPanel.add(lEhealth);
        enemyPanel.add(lEstrength);
        enemyPanel.add(lEdefense);
        enemyPanel.add(lEagility);

        enemyPanel.setBackground(Color.lightGray);

        buttonPanel.add(bDrinkPotion);
        buttonPanel.add(bUp);
        buttonPanel.add(bHeal);
        buttonPanel.add(bLeft);
        buttonPanel.add(bAttack);
        buttonPanel.add(bRight);
        buttonPanel.add(bResizeW);
        buttonPanel.add(bDown);
        buttonPanel.add(bClose);
        buttonPanel.setBackground(Color.lightGray);

        consolePanel.add(cScroll);

        consolePanel.setBackground(Color.lightGray);



        //Add Borders and Colors to Panels
        mapBorder = BorderFactory.createLineBorder(Color.BLACK, 4, true);
        mapPanel.setBorder(mapBorder);

        playerBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(7,7,7,7),BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"You",0,0,new Font("Consolas", Font.PLAIN, 16),Color.BLACK));
        playerPanel.setBorder(playerBorder);

        enemyBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(7,7,7,7),BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"Enemy",0,0,new Font("Consolas", Font.PLAIN, 16),Color.BLACK));
        enemyPanel.setBorder(enemyBorder);

        //Old style of Console Border - empty, but with an ethed center
        //consoleBorder = BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4,4,4,4),BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)),BorderFactory.createEmptyBorder(3,3,3,3));

        consoleBorder = BorderFactory.createEmptyBorder(7,7,7,7);
        consolePanel.setBorder(consoleBorder);

        GameWindowBordLay = new BorderLayout();
        this.setLayout(GameWindowBordLay);

        this.add(mapPanel, BorderLayout.CENTER);
        this.add(playerPanel,BorderLayout.WEST);
        this.add(enemyPanel, BorderLayout.EAST);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(consolePanel, BorderLayout.NORTH);

    }

    //Creates the ActionListener for Keypresses
    private void createButtonListener() {

        //Anonymous inner class defined to handle keypresses
        //Somewhat repeated code between here and the Actionperformed in the GameWindow Class.
        //This repetition is needed to have the same action happen with key presses and button clicks
        buttonListener = new AbstractAction() {

            public void actionPerformed(ActionEvent e) {

                if (e.getSource()==bAttack) {
                    game.playerAttackLocal();
                }
                else if (e.getSource()==bUp) {
                    game.movePlayer(Arena.UP);
                }
                else if (e.getSource()==bRight) {
                    game.movePlayer(Arena.RIGHT);
                }
                else if (e.getSource()==bDown) {
                    game.movePlayer(Arena.DOWN);
                }
                else if (e.getSource()==bLeft) {
                    game.movePlayer(Arena.LEFT);
                }
                else if (e.getSource()==bHeal) {
                    game.healPlayer();

                }
                else if (e.getSource()==bDrinkPotion) {
                    game.playerDrinkPotion();

                }

            }

        };

    }

    //Is similar to the AbstractAction defined earlier, but this one handles button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==bClose) {

            game.closeGame();

        }
        else if (e.getSource()==bUp) {
            game.movePlayer(Arena.UP);
        }
        else if (e.getSource()==bRight) {
            game.movePlayer(Arena.RIGHT);
        }
        else if (e.getSource()==bDown) {
            game.movePlayer(Arena.DOWN);
        }
        else if (e.getSource()==bLeft) {
            game.movePlayer(Arena.LEFT);
        }
        else if (e.getSource()==bResizeW) {
            this.pack();
            this.pack();
        }
        else if (e.getSource()==bHeal) {
            game.healPlayer();

        }

        else if (e.getSource()==bAttack) {
            game.playerAttackLocal();

        }
        else if (e.getSource()==bDrinkPotion) {
            game.playerDrinkPotion();

        }


    }

    //Called by its Arena once at the end of each turn, updates all components
    public void updateWindow() {

        //Updating Map
        map.setText(game.getMapText());
        mapPanel.repaint();


        // Updating Player stat panel
        Player pl = game.getPlayer();

        lPname.setText("  "+pl.getName()+pl.getSpacesAfterName()+"  ");
        lPlevelcls.setText("  Level "+pl.getSpacesBeforeLevel()+pl.getLevel()+" "+pl.getClss()+"  ");
        lPhealth.setText("  Health: "+pl.getHealth()+"  ");
        lPstrength.setText("  Strength: "+pl.getStrength()+"  ");
        lPdefense.setText("  Defense: "+pl.getDefense()+"  ");
        lPagility.setText("  Agility: "+pl.getAgility()+"  ");
        lPpotions.setText("  Potions: "+pl.getPotions()+"  ");

        playerPanel.repaint();

        //Updating enemy stat panel
        Enemy enemyMaybe = game.getEnemyAtPlayer();
        if (enemyMaybe!=null) { // Enemy Found
            lEname.setText("  "+enemyMaybe.getName()+enemyMaybe.getSpacesAfterName()+"  ");
            lElevelclss.setText("  Level "+enemyMaybe.getSpacesBeforeLevel()+enemyMaybe.getLevel()+" "+enemyMaybe.getClss()+"  ");
            lEhealth.setText("  Health: " + enemyMaybe.getHealth()+"  ");
            lEstrength.setText("  Strength: " + enemyMaybe.getStrength()+ "  ");
            lEdefense.setText("  Defense: " + enemyMaybe.getDefense());
            lEagility.setText("  Agility: " + enemyMaybe.getAgility()+"  ");
        }
        else { //No enemy
            lEname.setText("  No Enemy    ");
            lElevelclss.setText("  Level  0 Class   ");
            lEhealth.setText("  Health:  0  ");
            lEstrength.setText("  Strength:  0  ");
            lEdefense.setText("  Defense:  0  ");
            lEagility.setText("  Agility:  0  ");
        }
        enemyPanel.repaint();


        //Me checking how to align labels and spacing

        // Warrior+ 0 spaces
        // Wizard + 1 space
        // Rogue  + 2 spaces

        // Zombie+ 0 spaces
        // Alien + 1 space
        // Dragon+ 0 spaces



    }

    //Simple public method to pack the window
    public void packWindow() {

        this.pack();
        this.pack(); //Repeated to fix ocasionally resize bug
    }

    //Write out a message to the console, add a new line, and move the scroll position to the bottom of the console
    public void consoleOut(String message) {

        aConsole.append(message+"\n");
        aConsole.setCaretPosition(aConsole.getDocument().getLength());

    }




}
