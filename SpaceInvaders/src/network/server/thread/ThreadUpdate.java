package network.server.thread;

import logic.manager.field.FieldManager;
import logic.manager.game.States;
import network.server.game.manager.Multiplayer;
import logic.sprite.dinamic.bullets.Bullet;
import main.Dimensions;
import network.data.MessageBuilder;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Thread che controlla movimento degli sprite, le collisioni tra bullet e lo stato di gioco.
 * Contiene secondo thread che si occupa della creazione periodica del messaggio che il server invia al client
 * per la renderizzazione.
 */
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

    /**
     * Avvio thread update e di creazione messaggio.
     */
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
        createInfo();
    }

    /**
     * Thread che gestisce l'aggiornamento dello stato e degli elementi di gioco.
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
            checkCollision();
            checkGameState();
            try {
                Thread.sleep(delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo per il controllo delle collisioni.
     */
    private void checkCollision(){
        for (int ID : multiplayer.getPlayers().keySet()) {
            if (multiplayer.getSpaceShip(ID).isShipShot()) {
                multiplayer.getSpaceShipBullet(ID).move(delta);
                if (fieldManager.checkSpaceShipShotCollision(multiplayer.getSpaceShip(ID))) {
                    multiplayer.getTeam().calculateTeamCurrentScore();
                }
            }
            fieldManager.checkInvaderShotCollision(multiplayer.getSpaceShip(ID));
            if (multiplayer.getSpaceShip(ID).getLife() == 0) {
                multiplayer.getTeam().removePlayer(ID);
            }
        }
    }

    /**
     * Metodo per il controllo dello stato di gioco.
     */
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

    /**
     * Thread che periodicamente crea il messaggio da inviare ai client.
     */
    private void createInfo(){
        Thread threadMessageCreator = new Thread(() -> {
            running.set(true);
            while (running.get()) {
                messageBuilder.setInfo(multiplayer);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadMessageCreator.start();
    }

    /**
     * Metodo che termina l'esecuzione dei thread.
     */
    public void stop() {
        running.set(false);
    }
}
