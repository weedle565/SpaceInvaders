package com.engine.main;

import com.ollie.main.Game;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class MovementHandler extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_D -> Game.getPlayer().move(3);
            case KeyEvent.VK_A -> Game.getPlayer().move(-3);
            case KeyEvent.VK_SPACE -> {
                Game.getPlayer().shoot();
                try {
                    Game.getSound().restart(false);
                } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                }

            }
            case KeyEvent.VK_ESCAPE -> Main.getFrame().changeFrame();

        }

    }
}