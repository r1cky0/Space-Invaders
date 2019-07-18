package gui.states.Timer;

import gui.states.IDStates;
import logic.manager.game.commands.CommandType;
import network.client.LocalMultiManager;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer di countdown di 30 secondi per la connessione al server.
 * Crea l'azione da eseguire una volta terminato il timer.
 *
 */
public class ConnectionTimer {
    private StateBasedGame stateBasedGame;
    private LocalMultiManager localMultiManager;
    private Timer timer;
    private final int delayTime = 30000; //millis oltre il quale esco da waiting state multiplayer
    private boolean timerStarted;

    public ConnectionTimer(StateBasedGame stateBasedGame, LocalMultiManager localMultiManager){
        this.stateBasedGame = stateBasedGame;
        this.localMultiManager = localMultiManager;
    }

    /**
     * Metodo che avvia il timer.
     * Se il timer scade e quindi il client non è riuscito a connettersi al server, chiude la connessione e rientra
     * nel stato del menu.
     */
    public void startTimer(){
        if(!timerStarted) {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    localMultiManager.sendCommand(CommandType.EXIT);
                    stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
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
