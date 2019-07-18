package gui.states.Timer;

import gui.states.IDStates;
import org.newdawn.slick.state.StateBasedGame;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer di countdown di 3 secondi prima dell'inizio del gioco in modalità multiplayer.
 * Crea l'azione da eseguire una volta terminato il timer.
 *
 */
class CountdownTimer {
    private StateBasedGame stateBasedGame;
    private Timer timer;
    private final int delayTime = 3000; //countdown di 3 sec
    private boolean timerStarted;

    public CountdownTimer(StateBasedGame stateBasedGame){
        this.stateBasedGame = stateBasedGame;
    }

    /**
     * Metodo che avvia il timer.
     * Quando scade entra nello stato della partita multiplayer.
     */
    public void startTimer(){
        if(!timerStarted) {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    stateBasedGame.enterState(IDStates.MULTIPLAYER_STATE);
                }
            };
            timer = new Timer();
            timer.schedule(timerTask, delayTime); //timer attesa connessione a server o altri giocatori
            timerStarted = true;
        }
    }

}
