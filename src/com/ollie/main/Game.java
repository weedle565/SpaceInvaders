package com.ollie.main;

import com.engine.main.GameClass;
import com.engine.main.Main;
import com.engine.main.image.Background;
import com.engine.main.sound.SoundHandler;
import com.ollie.main.characters.*;
import com.ollie.main.screens.Dead;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Game extends GameClass {

    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private static final String MOVE_LEFT = "move left";
    private static final String MOVE_RIGHT = "move right";
    private static final String SHOOT = "shoot";
    private static final String ESCAPE_MENU = "menu";

    private static Player p;
    private static Background b;

    private static boolean dead;
    private static boolean inGame;

    private static ArrayList<Alien> aliens;
    private static ArrayList<Bullet> bullets;
    private static ArrayList<Explosion> explosions;
    private static ArrayList<Barrier> barriers;

    private static SoundHandler sound;
    private static SoundHandler explosion;
    private static SoundHandler background;

    public Game() {
        super(97, "Invaders of Space");

        b = new Background();

        aliens = new ArrayList<>();
        bullets = new ArrayList<>();
        explosions = new ArrayList<>();
        barriers = new ArrayList<>();

        inGame = false;

    }

    public void initGame(){

        setFocusable(true);

        requestFocusInWindow();
        requestFocus();

        setPreferredSize(new Dimension(1280, 720));

        dead = false;

        try {
            sound = new SoundHandler("src/resources/sounds/PlayerShot.wav");
            explosion = new SoundHandler("src/resources/sounds/explosion-1.wav");

            background = new SoundHandler("src/resources/sounds/background.wav");

            background.restart(true);
        }catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {

            e.printStackTrace();

        }

        p = new Player(640, 650, 0, 3, 5);

        initAliens();

        initBarriers();

        initKeyBinds();

        initTimers();
    }

    private void initTimers(){
        Timer t = new Timer(100, this);

        Timer animationHandler = new Timer(300, event -> {

            if(!dead && inGame) {
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

    private void initKeyBinds(){

        getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), MOVE_LEFT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), MOVE_RIGHT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), SHOOT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ESCAPE_MENU);

        getActionMap().put(MOVE_LEFT, new MoveAction(-3));
        getActionMap().put(MOVE_RIGHT, new MoveAction(3));
        getActionMap().put(SHOOT, new FireAction());
        getActionMap().put(ESCAPE_MENU, new EscapeAction());

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

        System.out.println(hasFocus());

        if(!dead && inGame) {

            repaint();
        } else if(dead){

            Dead.drawDead(getGraphics());

        }

    }

    public void changeBack(){

        setVisible(true);

        inGame = true;

        try {
            background.resumeAudio(true);
            sound.resumeAudio(false);
            explosion.resumeAudio(false);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e ){
            e.printStackTrace();
        }
        repaint();

    }

    public void changeFrame() {

        inGame = false;

        this.setVisible(false);

        Main.getStopMenu().setVisible(true);
        Main.getStopMenu().repaint();
        Main.getStopMenu().requestFocus();

        background.pause();
        sound.pause();
        explosion.pause();

    }

    public static SoundHandler getSound() {
        return sound;
    }

    public static Player getPlayer(){
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

    public static void setInGame(boolean inGame) {
        Game.inGame = inGame;
    }

    private static class MoveAction extends AbstractAction {

        int direction;

        MoveAction(int direction){
            this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            p.move(direction);

        }
    }
    private static class FireAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {

            p.shoot();

        }
    }

    private class EscapeAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {

            changeFrame();

        }
    }

}
