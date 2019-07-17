package network.server.timer;

import network.server.Server;
import network.server.game.manager.Multiplayer;
import network.server.thread.ServerThread;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class AddConnectionTimer {
    private Server server;
    private Timer timer;
    private TimerTask timerTask;
    private final int delayTime = 10000; //millis oltre il quale inizia la partita con il num di giocatori connessi
    private boolean timerStarted;

    public AddConnectionTimer(Server server){
        this.server = server;
    }

    public void startTimer(){
        if(!timerStarted) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    server.startGame();
                }
            };
            timer = new Timer();
            timer.schedule(timerTask, delayTime); //timer attesa connessione a server o altri giocatori
            timerStarted = true;
        }
    }

    public void stopTimer(){
        timer.cancel();
        timerStarted = false;
    }
}
