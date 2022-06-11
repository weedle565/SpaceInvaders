package com.ollie.main;

import com.engine.main.GameClass;
import com.engine.main.image.Background;
import com.engine.main.rendering.GameObject;
import com.ollie.main.characters.*;
import com.ollie.main.screens.Dead;
import com.engine.main.sound.SoundHandler;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Game extends GameClass {

    private static Player p;
    private static Background b;

    private static boolean dead;

    private static ArrayList<Alien> aliens;
    private static ArrayList<Bullet> bullets;
    private static ArrayList<Explosion> explosions;
    private static ArrayList<Barrier> barriers;

    private static SoundHandler sound;
    private static SoundHandler explosion;

    public Game() {
        super(97, "Invaders of Space");

        setPreferredSize(new Dimension(1280, 720));
    //    addKeyListener(new Movement());

        b = new Background();

        aliens = new ArrayList<>();
        bullets = new ArrayList<>();
        explosions = new ArrayList<>();
        barriers = new ArrayList<>();

    }

    public void initGame(){

        requestFocusInWindow();
        requestFocus();

        dead = false;

        try {
            sound = new SoundHandler("src/resources/sounds/PlayerShot.wav");
            explosion = new SoundHandler("src/resources/sounds/explosion-1.wav");

            SoundHandler background = new SoundHandler("src/resources/sounds/background.wav");

            background.restart(true);
        }catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {

            e.printStackTrace();

        }

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

                try {

                    checkAlienCollision(j);

                    checkPlayerCollision(j);

                    checkBarrierCollision(j);
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {

                    e.printStackTrace();

                }


            }
        } catch (ConcurrentModificationException ignored){

        }

    }

    private void checkBarrierCollision(Bullet j) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        for(Barrier barrier : barriers){
            if(j.getCollisionDetector().intersects(barrier.getCollisionDetector())) {

                Explosion e = new Explosion(barrier.getX(), barrier.getY(), 4, 2, 5);

                explosions.add(e);

                e.animate(this.getGraphics());

                j.destroy();

                barrier.addStage();

                explosion.restart(false);
                return;

            }
        }
    }

    private void checkPlayerCollision(Bullet j) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(j.getCollisionDetector().intersects(p.getCollisionDetector())){
            Explosion e = new Explosion(p.getX(), p.getY(), 4, 2, 5);

            explosions.add(e);

            e.animate(this.getGraphics());

            p.destroy();
            j.destroy();

            p.setX(-100);

            explosion.restart(false);
            dead = true;


        }
    }

    private void checkAlienCollision(Bullet j) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        for(Alien o : aliens){
            if(o.getCollisionDetector().intersects(j.getCollisionDetector()) && j.getShooter() instanceof Player){

                aliens.removeIf((a) -> a.getCollisionDetector().intersects(j.getCollisionDetector()));

                Explosion e = new Explosion(o.getX(), o.getY(), 4, 2, 5);

                explosions.add(e);

                e.animate(this.getGraphics());

                j.destroy();
                explosion.restart(false);
                return;

            }
        }

    }

    @Override
    public void paintComponent(Graphics g){

        b.drawImage(g);

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

    public static class Movement extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e){

            switch (e.getKeyCode()){

                case KeyEvent.VK_D -> p.move(3);
                case KeyEvent.VK_A -> p.move(-3);
                case KeyEvent.VK_SPACE ->{
                    p.shoot();
                    try {
                        sound.restart(false);
                    } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
                        ex.printStackTrace();
                    }
                }

            }

        }

    }

}
