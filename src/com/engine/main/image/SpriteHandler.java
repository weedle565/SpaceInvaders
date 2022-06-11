package com.engine.main.image;

import com.engine.main.GameClass;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteHandler{

    private static BufferedImage spriteSheet;


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
            spriteSheet = loadImage("spriteSheet");
        }

        System.out.println(GameClass.getTileSize());
        return spriteSheet.getSubimage(xGrid*GameClass.getTileSize(), yGrid*GameClass.getTileSize(), GameClass.getTileSize(), GameClass.getTileSize());

    }

}
