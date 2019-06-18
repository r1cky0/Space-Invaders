package logic.thread;

import logic.environment.manager.field.FieldManager;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadInvader implements Runnable {

    private FieldManager fieldManager;
    private Thread thread;
    private AtomicBoolean running;
    private int sleepInterval;

    public ThreadInvader(int sleepInterval, FieldManager fieldManager) {
        this.sleepInterval = sleepInterval;
        this.fieldManager = fieldManager;
        running = new AtomicBoolean(false);
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        running.set(true);
        Random rand = new Random();
        while (running.get()) {
             fieldManager.invaderMovement(fieldManager.checkInvaderDirection());
            if(rand.nextInt(10) > 4){
                fieldManager.invaderShot();
            }
            try {
                Thread.sleep(sleepInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running.set(false);
    }

}