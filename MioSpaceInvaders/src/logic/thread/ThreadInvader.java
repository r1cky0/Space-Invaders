package logic.thread;

import gui.states.SinglePlayer;
import logic.environment.manager.game.OfflineGameManager;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadInvader implements Runnable {

    private OfflineGameManager offlineGameManager;
    private SinglePlayer singlePlayer;
    private Thread thread;
    private AtomicBoolean running;
    private int sleepInterval;

    public ThreadInvader(int sleepInterval, SinglePlayer singlePlayer) {
        this.sleepInterval = sleepInterval;
        this.singlePlayer = singlePlayer;
        this.offlineGameManager = singlePlayer.getOfflineGameManager();
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