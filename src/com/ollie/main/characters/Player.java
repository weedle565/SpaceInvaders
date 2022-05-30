package com.ollie.main.characters;

import com.ollie.main.Game;
import com.ollie.main.image.SpriteHandler;
import com.ollie.main.sound.SoundHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject  {

    int which;

    BufferedImage[] animation = new BufferedImage[2];

    public Player(int x, int y, int gridX, int gridY, int health) {
        super(x, y, gridX, gridY, health);

        which = 0;

        animation[0] = super.getSprite();
        animation[1] = SpriteHandler.getSprite(gridX+1, gridY);
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
            g.drawImage(this.animation[which], super.getX() + which, super.getY(), super.getSprite().getWidth() / 2, super.getSprite().getHeight() / 2, null);
        }

    }

    @Override
    public void animate(Graphics g) {

        if(super.isAlive()) {
            drawImage(g);

            if (which == 1) {
                which = 0;
                return;
            }

            which = 1;
        }
        System.out.println(super.isAlive());
    }

    @Override
    public void destroy() {

        Game.getPlayer().setAlive(false);

    }

}
