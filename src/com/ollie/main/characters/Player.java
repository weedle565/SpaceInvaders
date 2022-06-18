package com.ollie.main.characters;

import com.engine.main.rendering.GameObject;
import com.ollie.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

    BufferedImage[] animation = new BufferedImage[2];

    public Player(int x, int y, int gridX, int gridY, int health) {
        super(x, y, gridX, gridY, health);

        animation[0] = super.getSprite();
    }

    public void move(int velocity){

        super.setX(super.getX() + velocity);

        super.getCollisionDetector().x = super.getX();


    }

    public void shoot(){

        Bullet b = new Bullet(super.getX(), super.getY(), 2, 2, 0, this);
        Game.getBullets().add(b);


    }

    @Override
    public void drawImage(Graphics g) {

        if(super.isAlive()) {
            g.drawImage(this.animation[0], super.getX(), super.getY(), super.getSprite().getWidth() / 2, super.getSprite().getHeight() / 2, null);
        }

    }

    @Override
    public void animate(Graphics g) {

        drawImage(g);

    }

    @Override
    public void destroy() {

        Game.getPlayer().setAlive(false);

    }

}
