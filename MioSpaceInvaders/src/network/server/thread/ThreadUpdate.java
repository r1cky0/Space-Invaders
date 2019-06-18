package network.server.thread;

import logic.environment.manager.game.Multiplayer;
import logic.player.Player;
import logic.sprite.dinamic.bullets.InvaderBullet;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadUpdate implements Runnable{

    private Multiplayer multiplayer;
    private Thread thread;
    private AtomicBoolean running;

    public ThreadUpdate(Multiplayer multiplayer){
        this.multiplayer = multiplayer;
        running = new AtomicBoolean(false);
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        running.set(true);
        while(running.get()) {
            for (InvaderBullet bullet : multiplayer.getFieldManager().getInvaderBullets()) {
                bullet.move(multiplayer.getDelta());
            }
            for (Player player : multiplayer.getTeam().getPlayers()) {
                if (player.getSpaceShip().getShipBullet() != null) {
                    player.getSpaceShip().getShipBullet().move(multiplayer.getDelta());
                    multiplayer.getFieldManager().checkSpaceShipShotCollision(player.getSpaceShip());
                }
                multiplayer.getFieldManager().checkInvaderShotCollision(player.getSpaceShip());
            }
            multiplayer.getTeam().calculateTeamCurrentScore();
            multiplayer.threadInvaderManager();
            multiplayer.checkGameState();
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
