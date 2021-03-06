package com.ollie.main.screens;

import com.engine.main.Main;
import com.ollie.main.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel {

    private final Main panel;

    private final Rectangle startRect;
    private final Rectangle settingsRect;

    private BufferedImage start;
    private BufferedImage settings;
    private BufferedImage title;

    public MainMenu(Main frame) {

        panel = frame;

        addMouseListener(new MouseListener());

        setPreferredSize(new Dimension(512, 512));

        requestFocusInWindow();

        try {
            start = ImageIO.read(new File("src/resources/menu/start.png"));
            settings = ImageIO.read(new File("src/resources/menu/settings.png"));
            title = ImageIO.read(new File("src/resources/menu/logo.png"));

        } catch (IOException e){
            e.printStackTrace();
        }

        startRect = new Rectangle(200, 120, start.getWidth()/3, start.getHeight()/3);
        settingsRect = new Rectangle(190, 190, settings.getWidth()/3, settings.getHeight()/3);
    }

    @Override
    protected void paintComponent(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(title, 170, 0, title.getWidth()/5, title.getHeight()/7, null);

        g.drawImage(start, 200, 120, start.getWidth()/3, start.getHeight()/3, null);

        g.drawImage(settings, 190, 190, settings.getWidth()/3, settings.getHeight()/3, null);

        g.drawRect(startRect.getLocation().x, startRect.getLocation().y, startRect.width, startRect.height);
    }

    private void changeFrame(JPanel swap){
        setFocusable(false);

        EventQueue.invokeLater(() -> {

            if(swap instanceof Game g) {
                Main.getMenu().setVisible(false);
                Game.setInGame(true);
                g.initGame();
                g.requestFocus();
                g.setVisible(true);
            } else if (swap instanceof Settings s){
                this.setVisible(false);
                s.requestFocus();
                Main.getSettings().setVisible(true);

            }

            panel.pack();
            panel.setLocationRelativeTo(null);

        });

    }

    class MouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {

            Point p = e.getPoint();

            if( startRect.contains(p)){

                changeFrame(Main.getFrame());

            } else if(settingsRect.contains(p)){

                changeFrame(Main.getSettings());

            }
        }
    }

}
