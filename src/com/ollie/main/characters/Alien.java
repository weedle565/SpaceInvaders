package com.ollie.main.characters;

import com.ollie.main.Game;
import com.ollie.main.image.SpriteHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Alien extends GameObject {

    private int which;
    private int shootCounter;

    private final BufferedImage[] animate = new BufferedImage[2];
    private int direction;

    public Alien(int x, int y, int gridX, int gridY, int health) {
        super(x, y, gridX, gridY, health);

        which = 0;
        shootCounter = 0;
        direction = 1;

        animate[0] = super.getSprite();
        animate[1] = SpriteHandler.getSprite(gridX+1, gridY);

    }

    public void move(){

        Game.getAliens().iterator().forEachRemaining((o) -> {
            if( Game.getAliens().stream().iterator().next().getX() == 800 && o.direction == 1) {
                o.setY(o.getY() + 50);
                o.direction = -1;
            }
             else if(Game.getAliens().stream().iterator().next().getX() == 0 && o.direction == -1){
                o.setY(o.getY() + 50);
                o.direction = 1;
            } else {
                o.setX(o.getX() + (direction));
            }

             o.getCollisionDetector().x = o.getX();
             o.getCollisionDetector().y = o.getY();

        });

    }

    public void shoot(){

        Random r = new Random();

        if(shootCounter == r.nextInt(10, 100)){

            Bullet b = new Bullet(super.getX(), super.getY(), 2, 2, 0, this);
            Game.getBullets().add(b);

            shootCounter = 0;
        }

        shootCounter++;

    }

    @Override
    public void drawImage(Graphics g) {

        g.drawImage(animate[which], super.getX(), super.getY(), super.getSprite().getWidth()/2, super.getSprite().getHeight()/2, null);

    }

    @Override
    public void animate(Graphics g) {

        drawImage(g);

        if (which == 1) {
            which = 0;
            return;
        }

        which = 1;

    }

    @Override
    public void destroy() {

        System.out.println("removed");
        Game.getAliens().remove(this);

    }
}
