package gui.thread;

import logic.environment.Field;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadInvader implements Runnable {

    private Field field;
    private Thread thread;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private int sleepInterval;

    public ThreadInvader(int sleepInterval, Field field) {
        this.sleepInterval = sleepInterval;
        this.field = field;
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
             field.invaderMovement(field.checkInvaderDirection());
            if(rand.nextInt(10) > 4){
                field.invaderShot();
            }
            try {
                Thread.sleep(sleepInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}