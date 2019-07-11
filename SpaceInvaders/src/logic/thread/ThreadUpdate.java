package logic.thread;

import logic.environment.manager.field.FieldManager;
import logic.environment.manager.game.States;
import logic.environment.manager.game.Multiplayer;
import logic.sprite.dinamic.bullets.InvaderBullet;
import main.Dimensions;
import network.data.MessageBuilder;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadUpdate implements Runnable{
    private MessageBuilder messageBuilder;
    private Multiplayer multiplayer;
    private FieldManager fieldManager;
    private AtomicBoolean running;

    public ThreadUpdate(Multiplayer multiplayer, MessageBuilder messageBuilder){
        this.multiplayer = multiplayer;
        fieldManager = multiplayer.getFieldManager();
        this.messageBuilder = messageBuilder;
        running = new AtomicBoolean(false);
    }

    public void start() {
        Thread thread = new Thread(this);
        messageBuilder.setGameStateInfos(States.START);
        thread.start();
    }

    public void run() {
        running.set(true);
        while(running.get()) {
            messageBuilder.setInfos(multiplayer);
            for (InvaderBullet bullet : fieldManager.getInvaderBullets()) {
                bullet.move(multiplayer.getDelta());
            }
            if(fieldManager.isBonusInvader() &&
                    !(fieldManager.getBonusInvader().getX() + Dimensions.INVADER_WIDTH < Dimensions.MIN_WIDTH)){
                fieldManager.getBonusInvader().moveLeft(multiplayer.getDelta());
            }

            for (int ID : multiplayer.getPlayers().keySet()) {
                if (multiplayer.getSpaceShip(ID).isShipShot()) {
                    multiplayer.getSpaceShipBullet(ID).move(multiplayer.getDelta());
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
                messageBuilder.setGameStateInfos(States.GAMEOVER);
            }
            multiplayer.threadInvaderManager();

            try {
                Thread.sleep(multiplayer.getDelta());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running.set(false);
    }
}
