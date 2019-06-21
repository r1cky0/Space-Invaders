package logic.thread;

import logic.environment.manager.game.States;
import logic.environment.manager.game.Multiplayer;
import logic.sprite.dinamic.bullets.InvaderBullet;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadUpdate implements Runnable{
    private Multiplayer multiplayer;
    private AtomicBoolean running;

    public ThreadUpdate(Multiplayer multiplayer){
        this.multiplayer = multiplayer;
        running = new AtomicBoolean(false);
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        running.set(true);
        while(running.get()) {
            for (InvaderBullet bullet : multiplayer.getFieldManager().getInvaderBullets()) {
                bullet.move(multiplayer.getDelta());
            }
            for (int ID : multiplayer.getPlayers().keySet()) {
                if (multiplayer.getSpaceShipBullet(ID) != null) {
                    multiplayer.getSpaceShipBullet(ID).move(multiplayer.getDelta());
                    if(multiplayer.getFieldManager().checkSpaceShipShotCollision(multiplayer.getSpaceShip(ID))){
                        multiplayer.getTeam().calculateTeamCurrentScore();
                    }
                }
                multiplayer.getFieldManager().checkInvaderShotCollision(multiplayer.getSpaceShip(ID));
                if(multiplayer.getSpaceShip(ID).getLife() == 0){
                    multiplayer.getTeam().removePlayer(ID);
                }
            }
            if(multiplayer.getPlayers().isEmpty() || multiplayer.getFieldManager().isEndReached()){
                multiplayer.setStates(States.GAMEOVER);
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
