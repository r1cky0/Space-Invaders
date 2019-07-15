package logic.thread;

import logic.manager.field.FieldManager;
import logic.manager.game.States;
import logic.manager.game.Multiplayer;
import logic.sprite.dinamic.bullets.Bullet;
import main.Dimensions;
import network.data.MessageBuilder;
import org.newdawn.slick.state.GameState;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadUpdate implements Runnable{
    private MessageBuilder messageBuilder;
    private Multiplayer multiplayer;
    private FieldManager fieldManager;
    private AtomicBoolean running;
    private int delta;

    public ThreadUpdate(Multiplayer multiplayer, MessageBuilder messageBuilder, int delta){
        this.multiplayer = multiplayer;
        this.delta = delta;
        this.messageBuilder = messageBuilder;
        fieldManager = multiplayer.getFieldManager();
        running = new AtomicBoolean(false);
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
        createInfo();
    }

    /**
     * La funzione di run del thread genera il messaggio che verrá inviato ai client per la renderizzazione
     * degli elementi di gioco.
     * Inoltre gestisce l'aggiornamento dello stato e degli elementi di gioco
     */
    public void run() {
        running.set(true);
        while(running.get()) {
            for (Bullet bullet : fieldManager.getInvaderBullets()) {
                bullet.move(delta);
            }
            if(fieldManager.isBonusInvader() &&
                    !(fieldManager.getBonusInvader().getX() + Dimensions.BONUSINVADER_WIDTH < Dimensions.MIN_WIDTH)){
                fieldManager.getBonusInvader().moveLeft(delta);
            }
            for (int ID : multiplayer.getPlayers().keySet()) {
                checkCollision(ID);
            }
            checkGameState();
            try {
                Thread.sleep(delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkCollision(int ID){
        if (multiplayer.getSpaceShip(ID).isShipShot()) {
            multiplayer.getSpaceShipBullet(ID).move(delta);
            if(fieldManager.checkSpaceShipShotCollision(multiplayer.getSpaceShip(ID))){
                multiplayer.getTeam().calculateTeamCurrentScore();
            }
        }
        fieldManager.checkInvaderShotCollision(multiplayer.getSpaceShip(ID));
        if(multiplayer.getSpaceShip(ID).getLife() == 0){
            multiplayer.getTeam().removePlayer(ID);
        }
    }

    private void checkGameState(){
        if(multiplayer.getPlayers().isEmpty() || fieldManager.isEndReached()){
            multiplayer.setGameState(States.GAMEOVER);
        }
        if(fieldManager.isNewLevel()){
            multiplayer.getTeam().incrementLife();
            multiplayer.stopThreadInvader();
            multiplayer.startThreadInvader();
            multiplayer.getFieldManager().setNewLevel(false);
        }
    }

    public void createInfo(){
        Thread threadMessageCreator = new Thread(() -> {
            running.set(true);
            while (running.get())
            messageBuilder.setInfo(multiplayer);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadMessageCreator.start();
    }

    public void stop() {
        running.set(false);
    }
}
