package logic.thread;

import logic.environment.manager.game.OfflineGameManager;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadInvader implements Runnable {

    private OfflineGameManager offlineGameManager;
    private Thread thread;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private int sleepInterval;

    public ThreadInvader(int sleepInterval, OfflineGameManager offlineGameManager) {
        this.sleepInterval = sleepInterval;
        this.offlineGameManager = offlineGameManager;
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