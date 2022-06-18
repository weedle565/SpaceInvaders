package com.ollie.main.screens;

import com.engine.main.Main;
import com.ollie.main.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Settings extends JPanel implements ChangeListener {

    private final Main panel;

    private JSlider slider;
    private JLabel volumeLabel;
    private JLabel image;

    private BufferedImage back;

    private Rectangle backRectangle;

    private int volume;

    private boolean main;

    public Settings(Main frame) {

        volume = 100;

        main = true;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        setPreferredSize(new Dimension(512, 512));

        initUI();

        panel = frame;

        addMouseListener(new MouseListener());

        add(image);
        add(volumeLabel);
        add(slider);

    }

    private void initUI(){

        requestFocusInWindow();

        setupSlider();

        setupImage();

        setupVolumeLabel();

        setupBackButton();
    }

    private void setupBackButton(){

        try{
            back = ImageIO.read(new File("src/resources/Menu/back.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

        backRectangle = new Rectangle(0, 0, back.getWidth()/5, back.getHeight()/5);

    }

    private void setupImage(){

        image = new JLabel();

        ImageIcon icon = new ImageIcon("src/resources/menu/logo.png");

        Image newImg = icon.getImage();

        Image scaledImg = newImg.getScaledInstance(icon.getIconWidth()/5, icon.getIconHeight()/7, Image.SCALE_SMOOTH);

        icon = new ImageIcon(scaledImg);

        image.setIcon(icon);

        image.setBounds(0, 0, image.getPreferredSize().width/5, image.getPreferredSize().height/7);

        image.setHorizontalAlignment(JLabel.CENTER);
        image.setAlignmentX(Component.CENTER_ALIGNMENT);

    }

    private void setupVolumeLabel(){
        volumeLabel = new JLabel("Volume:", JLabel.CENTER);

        volumeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        volumeLabel.setForeground(Color.WHITE);

        volumeLabel.setFont(new Font("Arial", Font.BOLD, 25));
    }

    private void setupSlider(){

        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);

        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);

        slider.setBackground(Color.BLACK);
        slider.setForeground(Color.WHITE);
        slider.setSize(new Dimension(150, 150));

        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        slider.addChangeListener(this);

        slider.setFont(new Font("Arial", Font.BOLD, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {

        g.setColor(Color.black);

        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(back, 0, 0, back.getWidth()/5, back.getHeight()/5, null);

    }

    public int getVolume() {
        return volume;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    private void changeFrame(){
        setFocusable(false);

        EventQueue.invokeLater(() -> {

            if(main) {
                this.setVisible(false);
                Main.getMenu().setVisible(true);
                Main.getMenu().requestFocus();

                panel.pack();
                panel.setLocationRelativeTo(null);
            } else {
                this.setVisible(false);

                Main.getStopMenu().setVisible(true);
                Main.getStopMenu().repaint();
                Main.getStopMenu().requestFocus();
            }
        });

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if(!source.getValueIsAdjusting()){
            volume = source.getValue();
        }

    }

    class MouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getButton() == MouseEvent.BUTTON1 && backRectangle.contains(e.getPoint())){
                changeFrame();
            }
        }
    }


}
