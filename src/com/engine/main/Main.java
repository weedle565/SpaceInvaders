package com.engine.main;

import com.ollie.main.Game;
import com.ollie.main.screens.MainMenu;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private final MainMenu menu;
    private final Game frame;

    public Main(){

        menu = new MainMenu(this);
        frame = new Game();

        addKeyListener(new Game.Movement());

        setResizable(false);

        setLayout(new CardLayout());

        add(menu);

        pack();

        add(frame);

        menu.setVisible(true);
        frame.setVisible(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(GameClass.getGameName());
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public Game getFrame() {
        return frame;
    }

    public MainMenu getMenu() {
        return menu;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Main::new);
    }
}
