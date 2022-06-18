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

public class StopMenu extends JPanel {

    private final Main panel;

    private final Rectangle resumeRect;
    private final Rectangle settingsRect;

    private BufferedImage resume;
    private BufferedImage settings;
    private BufferedImage menu;

    public StopMenu(Main frame) {

        panel = frame;

        addMouseListener(new MouseListener());

        setPreferredSize(new Dimension(512, 512));
        panel.pack();

        requestFocusInWindow();

        try {
            resume = ImageIO.read(new File("src/resources/menu/resume.png"));
            settings = ImageIO.read(new File("src/resources/menu/settings.png"));
            menu = ImageIO.read(new File("src/resources/menu/menu.png"));

        } catch (IOException e){
            e.printStackTrace();
        }

        resumeRect = new Rectangle(590, 120, resume.getWidth()/3, resume.getHeight()/3);
        settingsRect = new Rectangle(580, 185, settings.getWidth()/3, settings.getHeight()/3);

    }

    @Override
    protected void paintComponent(Graphics g) {

        g.setColor(Color.black);

        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(resume, 590, 120, resume.getWidth()/3, resume.getHeight()/3, null);

        g.drawImage(settings, 580, 185, settings.getWidth()/3, settings.getHeight()/3, null);

        g.drawImage(menu, 590, 240, menu.getWidth()/3, menu.getHeight()/3, null);

    }

    private void changeFrame(JPanel swap){
        setFocusable(false);

        EventQueue.invokeLater(() -> {

            if(swap instanceof Game g) {
                Main.getStopMenu().setVisible(false);
                Main.getFrame().validate();
                g.requestFocus();
                g.requestFocusInWindow();
                g.changeBack();
                g.repaint();
            } else if (swap instanceof Settings s){
                this.setVisible(false);
                s.requestFocus();

                Main.getSettings().setMain(false);
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

            if( resumeRect.contains(p)){

                changeFrame(Main.getFrame());

            } else if(settingsRect.contains(p)){

                changeFrame(Main.getSettings());

            }
        }
    }

}
