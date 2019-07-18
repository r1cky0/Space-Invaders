package network.server.timer;

import network.server.Server;
import network.server.game.manager.Multiplayer;
import network.server.thread.ServerThread;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Timer di countdown di 15 secondi sul server per l'inizio della partita.
 * Crea l'azione da eseguire una volta terminato il timer.
 *
 */
public class AddConnectionTimer {
    private Server server;
    private Timer timer;
    private final int delayTime = 15000; //millis oltre il quale inizia la partita con il num di giocatori connessi
    private boolean timerStarted;

    public AddConnectionTimer(Server server){
        this.server = server;
    }

    /**
     * Metodo che avvia il timer.
     * Se il timer scade la partita inizia con il numero di giocatori connessi in quel momento.
     */
    public void startTimer(){
        if(!timerStarted) {
            TimerTask timerTask = new TimerTask() {
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

    /**
     * Metodo per fermare il timer se il client è riuscito a connettersi al server.
     */
    public void stopTimer(){
        timer.cancel();
        timerStarted = false;
    }
}
