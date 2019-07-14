package logic.thread;

import logic.manager.field.FieldManager;
import logic.manager.game.States;
import logic.manager.game.Multiplayer;
import logic.sprite.dinamic.bullets.Bullet;
import main.Dimensions;
import network.data.MessageBuilder;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadUpdate implements Runnable{
    private MessageBuilder messageBuilder;
    private Multiplayer multiplayer;
    private FieldManager fieldManager;
    private AtomicBoolean running;
    private int delta;

    public ThreadUpdate(Multiplayer multiplayer, MessageBuilder messageBuilder, int delta){
        this.multiplayer = multiplayer;
        this.messageBuilder = messageBuilder;
        this.delta = delta;
        fieldManager = multiplayer.getFieldManager();
        running = new AtomicBoolean(false);
    }

    public void start() {
        Thread thread = new Thread(this);
        messageBuilder.setGameStateInfo(States.START);
        thread.start();
    }

    /**
     * La funzione di run del thread genera il messaggio che verrá inviato ai client per la renderizzazione
     * degli elementi di gioco.
     * Inoltre gestisce l'aggiornamento dello stato e degli elementi di gioco
     */
    public void run() {
        running.set(true);
        while(running.get()) {
            messageBuilder.setInfo(multiplayer);
            for (Bullet bullet : fieldManager.getInvaderBullets()) {
                bullet.move(delta);
            }
            if(fieldManager.isBonusInvader() &&
                    !(fieldManager.getBonusInvader().getX() + Dimensions.INVADER_WIDTH < Dimensions.MIN_WIDTH)){
                fieldManager.getBonusInvader().moveLeft(delta);
            }

            for (int ID : multiplayer.getPlayers().keySet()) {
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
            if(multiplayer.getPlayers().isEmpty() || fieldManager.isEndReached()){
                messageBuilder.setGameStateInfo(States.GAMEOVER);
            }
            if(fieldManager.isNewLevel()){
                multiplayer.getTeam().incrementLife();
            }
            multiplayer.threadInvaderManager();

            try {
                Thread.sleep(delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running.set(false);
    }
}
