package com.ollie.main;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main(){

        setSize(new Dimension(1280, 720));
        setResizable(false);
        add(new Game());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Space invaders");
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Main::new);
    }
}
