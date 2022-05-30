package com.ollie.main.characters;

import com.ollie.main.Game;
import com.ollie.main.image.SpriteHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

    private BufferedImage[] animation;
    private int animationStage;

    private GameObject shooter;

    public Bullet(int x, int y, int gridX, int gridY, int health, GameObject shooter) {
        super(x, y, gridX, gridY, health);

        animation = new BufferedImage[2];

        animationStage = 0;

        this.shooter = shooter;

        animation[0] = super.getSprite();
        animation[1] = SpriteHandler.getSprite(gridX+1, gridY);
    }

    @Override
    public void drawImage(Graphics g) {

        g.drawImage(animation[animationStage], super.getX(), super.getY(), super.getSprite().getWidth()/2, super.getSprite().getHeight()/2, null);

        if(shooter instanceof Player) {
            setY(super.getY() - 10);
        } else {
            super.setY(super.getY() + 10);
        }

        super.getCollisionDetector().y = super.getY();

    }

    @Override
    public void animate(Graphics g) {

        drawImage(g);

        if(animationStage == 1){
            animationStage = 0;
            return;
        }

        animationStage = 1;

    }

    @Override
    public void destroy() {

        Game.getBullets().remove(this);

    }

    public GameObject getShooter(){
        return shooter;
    }
}
