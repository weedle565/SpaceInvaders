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
    private BufferedImage start;
    private Rectangle startRect;
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
    }

    @Override
    protected void paintComponent(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(title, 170, 0, title.getWidth()/5, title.getHeight()/7, null);

        g.drawImage(start, 200, 120, start.getWidth()/3, start.getHeight()/3, null);

        g.drawImage(settings, 200, 240, settings.getWidth()/3, settings.getHeight()/3, null);

        g.drawRect(startRect.getLocation().x, startRect.getLocation().y, startRect.width, startRect.height);
    }

    private void changeFrame(){
        setFocusable(false);

        EventQueue.invokeLater(() -> {
            this.setVisible(false);
            panel.getFrame().initGame();
            panel.pack();
            panel.getFrame().requestFocus();
            panel.setLocationRelativeTo(null);
            panel.getFrame().setVisible(true);

        });

    }

    class MouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {

            Point p = e.getPoint();

            if( startRect.contains(p)){

                changeFrame();

            }
        }
    }

}
