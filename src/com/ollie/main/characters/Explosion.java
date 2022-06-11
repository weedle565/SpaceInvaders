package com.ollie.main.characters;

import com.engine.main.image.SpriteHandler;
import com.engine.main.rendering.GameObject;
import com.ollie.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion extends GameObject {

    private final BufferedImage[] death;

    private int animationProcess;

    public Explosion(int x, int y, int gridX, int gridY, int health) {
        super(x, y, gridX, gridY, health);

        death = new BufferedImage[14];

        death[0] = super.getSprite();
        death[1] = SpriteHandler.getSprite(gridX + 1, gridY);
        death[2] = SpriteHandler.getSprite(gridX + 2, gridY);
        death[3] = SpriteHandler.getSprite(gridX + 3, gridY);

        gridY++;
        gridX = 2;

        death[4] = SpriteHandler.getSprite(gridX + 1, gridY);
        death[5] = SpriteHandler.getSprite(gridX + 2, gridY);
        death[6] = SpriteHandler.getSprite(gridX + 3, gridY);
        death[7] = SpriteHandler.getSprite(gridX + 4, gridY);
        death[8] = SpriteHandler.getSprite(gridX + 5, gridY);

        gridY++;
        gridX = 0;

        death[9] = SpriteHandler.getSprite(gridX + 1, gridY);
        death[10] = SpriteHandler.getSprite(gridX + 2, gridY);
        death[11] = SpriteHandler.getSprite(gridX + 3, gridY);
        death[12] = SpriteHandler.getSprite(gridX + 4, gridY);
        death[13] = SpriteHandler.getSprite(gridX + 5, gridY);

    }

    @Override
    public void drawImage(Graphics g) {

        g.drawImage(death[animationProcess], super.getX(), super.getY(), super.getSprite().getWidth()/2, super.getSprite().getHeight()/2, null);

    }

    @Override
    public void animate(Graphics g) {

        drawImage(g);

        if(animationProcess == death.length-1){

            return;
        }

        animationProcess++;


    }

    @Override
    public void destroy() {

        Game.getExplosions().remove(this);

    }
}
