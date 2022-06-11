package com.engine.main.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background implements ImageLoaderIO {

    private BufferedImage background;

    public Background() {
        try {
            background = ImageIO.read(new File("src/resources/background.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawImage(Graphics g) {

        g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);

    }

    @Override
    public void animate(Graphics g) {

    }
}
