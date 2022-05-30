package com.ollie.main.characters;

import com.ollie.main.Game;
import com.ollie.main.image.SpriteHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;

public class Barrier extends GameObject {

    private BufferedImage[] stage;
    private int destruction;

    public Barrier(int x, int y, int gridX, int gridY, int health) {
        super(x, y, gridX, gridY, health);

        stage = new BufferedImage[6];

        destruction = 0;

        stage[0] = super.getSprite();
        stage[1] = SpriteHandler.getSprite(7, 4);
        stage[2] = SpriteHandler.getSprite(0, 5);
        stage[3] = SpriteHandler.getSprite(1, 5);
        stage[4] = SpriteHandler.getSprite(2, 5);

    }

    public void addStage(){
        destruction++;
    }

    @Override
    public void drawImage(Graphics g) {

        if(destruction == 5){
            destroy();
        } else if (destruction < 5) {

            g.drawImage(stage[destruction], super.getX(), super.getY(), getSprite().getWidth(), getSprite().getHeight(), null);

        }
    }

    @Override
    public void animate(Graphics g) {

        drawImage(g);

    }

    @Override
    public void destroy() {

        try {
            Game.getBarriers().remove(this);
        } catch (ConcurrentModificationException e){

            System.out.println("Issue but it doesnt affect anything at all");

        }
    }
}
