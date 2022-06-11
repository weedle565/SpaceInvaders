package com.engine.main;

import com.ollie.main.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GameClass extends JPanel implements ActionListener {

    private static int tileSize;
    private static String gameName;

    public GameClass(int tileSize, String name){
        GameClass.tileSize = tileSize;
        GameClass.gameName = name;
    }

    public static String getGameName() {
        return gameName;
    }

    public static int getTileSize() {
        return tileSize;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);

}
