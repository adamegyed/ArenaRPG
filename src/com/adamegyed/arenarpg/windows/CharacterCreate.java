package com.adamegyed.arenarpg.windows;

import com.adamegyed.arenarpg.characters.Characters;
import com.adamegyed.arenarpg.game.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Adam on 10/21/15.
 * Character Creation Window - how the player creates their own character
 * This window is created once and then visibility is altered
 * Implements an actionlistener to manage button and radio button inputs
 */
public class CharacterCreate extends JFrame implements ActionListener {


    //Global components needed in actionPerformed
    JTextField name;
    JRadioButton warrior, rogue, wizard;
    JButton begin;
    JButton cancel;
    ButtonGroup classes;

    //Custom background color
    Color backColor = new Color(125,127,125);

    //Reference to its own gamemanager
    GameManager gameManager;


    public CharacterCreate(GameManager gM) {
        super("Character Creation");
        init();
        gameManager = gM;

    }


    //Add components
    private void init() {

        // Labels
        JLabel lname = new JLabel("Name: ");
        lname.setForeground(Color.green);
        JLabel lclass = new JLabel("Please pick a class:");
        lclass.setForeground(Color.green);

        // Text Fields
        name = new JTextField(15);
        name.setBackground(Color.darkGray);
        name.setForeground(Color.white);

        // Radio Buttons
        warrior = new JRadioButton("Warrior - Sturdy, starts with an extra potion.");
        warrior.setForeground(Color.green);
        rogue = new JRadioButton("Rogue - Strong but frail, higher critical hit chance.");
        rogue.setForeground(Color.green);
        wizard = new JRadioButton("Wizard - Powerful, can counter attacks.");
        wizard.setForeground(Color.green);

        // Buttons
        begin = new JButton("Begin!");
        cancel = new JButton("Cancel");

        //Radio Button Group
        classes = new ButtonGroup();
        classes.add(warrior);
        classes.add(rogue);
        classes.add(wizard);

        // Panels
        JPanel namePanel = new JPanel();
        JPanel classSelect = new JPanel(new GridLayout(4,1)); //Gridlayout to control orientation of labels and buttons
        JPanel buttonPanel = new JPanel();

        // Adding components to panels
        namePanel.add(lname);
        namePanel.add(name);
        namePanel.setBackground(backColor);

        classSelect.add(lclass);
        classSelect.add(warrior);
        classSelect.add(rogue);
        classSelect.add(wizard);
        classSelect.setForeground(Color.green);
        classSelect.setBackground(backColor);

        buttonPanel.add(begin);
        buttonPanel.add(cancel);
        buttonPanel.setForeground(Color.green);
        buttonPanel.setBackground(backColor);

        begin.addActionListener(this);
        cancel.addActionListener(this);


        this.setLayout(new BorderLayout());

        this.add(namePanel, BorderLayout.NORTH);
        this.add(classSelect, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.pack();
        this.setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==cancel) {
            gameManager.closeGame();
            this.disapear();
        }
         else if (e.getSource()==begin) {
            if (name.getText().equals("")) {
                JOptionPane.showMessageDialog(begin, "Please Enter a Name.","Error",JOptionPane.ERROR_MESSAGE);
            }
            else if (name.getText().length()>10) {
                JOptionPane.showMessageDialog(begin, "Please make your name less than 10 characters","Error",JOptionPane.ERROR_MESSAGE);

            }
            else if (warrior.isSelected()) {
                gameManager.createPlayer(name.getText(), Characters.WARRIOR);
            }
            else if (rogue.isSelected()) {
                gameManager.createPlayer(name.getText(), Characters.ROGUE);
            }
            else if (wizard.isSelected()) {
                gameManager.createPlayer(name.getText(), Characters.WIZARD);
            }

            else JOptionPane.showMessageDialog(begin, "Please Choose a Class","Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    //Called after successful character creation by the gamemanager
    //Hides itself and clears any user input, ready to be used again
    public void disapear() {
        this.setVisible(false);
        name.setText("");
        classes.clearSelection();
    }
}
