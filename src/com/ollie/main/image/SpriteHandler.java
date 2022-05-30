package com.ollie.main.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteHandler{

    private static BufferedImage spriteSheet;
    private static final int TILE_SIZE = 97;


    public static BufferedImage loadImage(String dir) {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File("src/resources/" + dir + ".png"));
        } catch (IOException e){
            e.printStackTrace();
        }

        return sprite;
    }

    public static BufferedImage getSprite(int xGrid, int yGrid){

        if(spriteSheet == null){
            spriteSheet = loadImage("spriteSheet2");
        }

        return spriteSheet.getSubimage(xGrid*TILE_SIZE, yGrid*TILE_SIZE, TILE_SIZE, TILE_SIZE);

    }

}
