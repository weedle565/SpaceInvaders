package com.engine.main;

import com.ollie.main.Game;
import com.ollie.main.screens.MainMenu;
import com.ollie.main.screens.Settings;
import com.ollie.main.screens.StopMenu;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private static MainMenu menu;
    private static Settings settings;
    private static Game frame;
    private static StopMenu stopMenu;

    public Main(){

        menu = new MainMenu(this);
        frame = new Game();
        settings = new Settings(this);
        stopMenu = new StopMenu(this);

        setResizable(false);

        setLayout(new CardLayout());

        add(menu);

        pack();

        add(frame);

        add(settings);

        add(stopMenu);

        menu.setVisible(true);
        frame.setVisible(false);
        settings.setVisible(false);
        stopMenu.setVisible(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(GameClass.getGameName());
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static Game getFrame() {
        return frame;
    }

    public static MainMenu getMenu() {
        return menu;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static StopMenu getStopMenu() {
        return stopMenu;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Main::new);
    }
}
