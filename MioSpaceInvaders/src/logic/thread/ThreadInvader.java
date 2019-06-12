package logic.thread;

import logic.environment.manager.game.GameManager;
import logic.environment.manager.game.OfflineGameManager;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadInvader implements Runnable {

    private OfflineGameManager offlineGameManager;
    private Thread thread;
    private AtomicBoolean running;
    private int sleepInterval;

    public ThreadInvader(int sleepInterval, GameManager gameManager) {
        this.sleepInterval = sleepInterval;
        this.offlineGameManager = (OfflineGameManager) gameManager;
        running = new AtomicBoolean(false);
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running.set(false);
    }

    public void run() {
        running.set(true);
        Random rand = new Random();
        while (running.get()) {
             offlineGameManager.invaderMovement(offlineGameManager.checkInvaderDirection());
            if(rand.nextInt(10) > 4){
                offlineGameManager.invaderShot();
            }
            try {
                Thread.sleep(sleepInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}