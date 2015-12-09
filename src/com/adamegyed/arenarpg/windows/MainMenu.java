package com.adamegyed.arenarpg.windows;

import com.adamegyed.arenarpg.game.GameManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

/**
 * Created by Adam on 10/21/15.
 * The Main Menu Window
 * This window is constructed once and never hidden
 * Holds a reference to its gamemanager in order to handle actions
 * on button presses
 */
public class MainMenu extends JFrame implements ActionListener {


    GameManager gameManager;

    JPanel titlepanel;
    JLabel title;


    JButton ngButton;
    JButton bHelp;

    JPanel buttonPanel;

    Color backColor = new Color(125,127,125);

    public MainMenu(GameManager gM) {

        super("ArenaRPG");
        init();
        gameManager = gM;
        this.setSize(400,160); //Manually set the size
        this.setResizable(false);
        this.setLocation(5,5); // Prevents weird bug on OsX where windows moved to the same location can't be clicked
        this.setVisible(true);


    }

    //Initialize and draw all components
    private void init() {

        titlepanel = new JPanel();
        titlepanel.setBackground(backColor);
        titlepanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)),BorderFactory.createEmptyBorder(2,2,2,2)));
        title = new JLabel("ArenaRPG");
        title.setFont(new Font("Consolas", Font.BOLD, 72));
        title.setForeground(Color.white);

        titlepanel.add(title);

        ngButton = new JButton("New Game");
        ngButton.addActionListener(this);
        bHelp = new JButton("Help");
        bHelp.addActionListener(this);

        buttonPanel = new JPanel(new GridLayout(1,2));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)),BorderFactory.createEmptyBorder(2,2,2,2)));
        buttonPanel.setBackground(backColor);

        buttonPanel.add(ngButton);
        buttonPanel.add(bHelp);

        this.setLayout(new BorderLayout());
        this.add(titlepanel,BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==ngButton) {

            gameManager.newGame();

        }
        else if (e.getSource()== bHelp) {
            gameManager.toggleHelpMenu();
        }

    }
}
