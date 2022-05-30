package com.ollie.main;

import com.ollie.main.characters.*;
import com.ollie.main.screens.Dead;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Game extends JPanel implements ActionListener {

    private static Player p;

    private static boolean dead;

    private static ArrayList<Alien> aliens;
    private static ArrayList<Bullet> bullets;
    private static ArrayList<Explosion> explosions;
    private static ArrayList<Barrier> barriers;

    public Game(){
        initGame();
    }

    public void initGame(){

        setFocusable(true);
        addKeyListener(new Movement());

        aliens = new ArrayList<>();
        bullets = new ArrayList<>();
        explosions = new ArrayList<>();
        barriers = new ArrayList<>();

        dead = false;

        p = new Player(640, 650, 0, 3, 5);

        initAliens();

        initBarriers();

        initTimers();
    }

    private void initTimers(){
        Timer t = new Timer(100, this);

        Timer animationHandler = new Timer(300, event -> {

            if(!dead) {
                p.animate(this.getGraphics());

                aliens.iterator().forEachRemaining((a) -> {
                    a.animate(this.getGraphics());
                    a.shoot();
                });

                if (!bullets.isEmpty()) {
                    bullets.iterator().forEachRemaining((b) -> b.animate(this.getGraphics()));
                }

                if (!explosions.isEmpty()) {
                    explosions.iterator().forEachRemaining((b) -> b.animate(this.getGraphics()));
                }
            }

        });

        Timer movementHandler = new Timer(1000, event -> aliens.iterator().forEachRemaining(Alien::move));

        movementHandler.start();
        animationHandler.start();

        t.start();
    }

    private void initBarriers(){

        for(int i = 0; i < 3; i++){
            barriers.add(new Barrier(i * 590, 500, 6,4, 10));
        }

    }

    private void initAliens(){

        int height = 0;
        int column = 0;

        for(int i = 0; i <= 3; i++) {

            for(int z = 0; z <= 14; z++) {
                if(z % 2 == 0) {
                    aliens.add(new Alien(z * 30, i*50, column, height, 1));

                    column+=2;

                    if(column == 8){
                        column = 0;
                    }
                }


            }
            height++;

            if (height == 2){
                    height = 0;
            }

        }

    }

    private void checkCollisions() {

        try {
            for (Bullet j : bullets) {

                checkAlienCollision(j);

                checkPlayerCollision(j);

                checkBarrierCollision(j);

            }
        } catch (ConcurrentModificationException ignore){

        }

    }

    private void checkBarrierCollision(Bullet j){

        for(Barrier barrier : barriers){
            if(j.getCollisionDetector().intersects(barrier.getCollisionDetector())) {

                Explosion e = new Explosion(barrier.getX(), barrier.getY(), 4, 2, 5);

                explosions.add(e);

                e.animate(this.getGraphics());

                j.destroy();

                barrier.addStage();
                return;

            }
        }
    }

    private void checkPlayerCollision(Bullet j){
        if(j.getCollisionDetector().intersects(p.getCollisionDetector())){
            Explosion e = new Explosion(p.getX(), p.getY(), 4, 2, 5);

            explosions.add(e);

            e.animate(this.getGraphics());

            p.destroy();
            j.destroy();

            p.setX(-100);

            dead = true;

        }
    }

    private void checkAlienCollision(Bullet j){

        for(Alien o : aliens){
            if(o.getCollisionDetector().intersects(j.getCollisionDetector()) && j.getShooter() instanceof Player){

                aliens.removeIf((a) -> a.getCollisionDetector().intersects(j.getCollisionDetector()));

                Explosion e = new Explosion(o.getX(), o.getY(), 4, 2, 5);

                explosions.add(e);

                e.animate(this.getGraphics());

                j.destroy();

                return;

            }
        }

    }

    @Override
    public void paintComponent(Graphics g){

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        aliens.iterator().forEachRemaining((a) -> a.drawImage(g));

        try {
            barriers.iterator().forEachRemaining((ba) -> ba.drawImage(g));
        } catch (ConcurrentModificationException ignore){

        }

        p.drawImage(g);

        bullets.iterator().forEachRemaining((b) -> b.drawImage(g));

        explosions.iterator().forEachRemaining((e) -> e.drawImage(g));

        checkCollisions();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(!dead) {
            repaint();
        } else {

            Dead.drawDead(getGraphics());

        }

    }

    public static GameObject getPlayer(){
        return p;
    }

    public static ArrayList<Barrier> getBarriers(){
        return barriers;
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public static ArrayList<Alien> getAliens(){
        return aliens;
    }

    public static ArrayList<Explosion> getExplosions(){
        return explosions;
    }

    private static class Movement extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e){

            switch (e.getKeyCode()){

                case KeyEvent.VK_D -> p.move(3);
                case KeyEvent.VK_A -> p.move(-3);
                case KeyEvent.VK_SPACE -> p.shoot();

            }

        }

    }

}
