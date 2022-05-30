package com.ollie.main.characters;

import com.ollie.main.image.ImageLoaderIO;
import com.ollie.main.image.SpriteHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject implements ImageLoaderIO {

    private int x;
    private int y;
    private int health;

    private boolean alive;

    private BufferedImage sprite;

    private Rectangle collisionDetector;

    public GameObject(int x, int y, int gridX, int gridY, int health){

        this.setX(x);
        this.setY(y);
        this.health = health;

        this.alive = true;

        setSprite(SpriteHandler.getSprite(gridX, gridY));

        if(health > 0 && health < 10) {
            collisionDetector = new Rectangle(x, y, 47, 47);
        } else if(health == 10){

            collisionDetector = new Rectangle(x + 25, y+30, 60, 50);

        }else {
            collisionDetector = new Rectangle(x+10, y+10, 7, 10);
        }
    }

    public void drawCollision(Graphics g){
        g.setColor(Color.CYAN);
        g.drawRect(collisionDetector.x, collisionDetector.y, collisionDetector.width, collisionDetector.height);
    }

    public Rectangle getCollisionDetector() {
        return collisionDetector;
    }

    public void setCollisionDetector(Rectangle collisionDetector){
        this.collisionDetector = collisionDetector;
    }

    public boolean isAlive(){
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public abstract void drawImage(Graphics g);

    public abstract void animate(Graphics g);

    public abstract void destroy();

}
