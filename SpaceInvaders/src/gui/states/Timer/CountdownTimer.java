package gui.states.Timer;

import org.newdawn.slick.state.StateBasedGame;
import java.util.Timer;
import java.util.TimerTask;

public class CountdownTimer {
    private StateBasedGame stateBasedGame;
    private Timer timer;
    private TimerTask timerTask;
    private int idStates;
    private final int delayTime = 3000; //millis oltre il quale esco da waiting state multiplayer
    private boolean timerStarted;

    public CountdownTimer(StateBasedGame stateBasedGame, int idStates){
        this.stateBasedGame = stateBasedGame;
        this.idStates = idStates;
    }

    public void startTimer(){
        if(!timerStarted) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    stateBasedGame.enterState(idStates);
                }
            };
            timer = new Timer();
            timer.schedule(timerTask, delayTime); //timer attesa connessione a server o altri giocatori
            timerStarted = true;
        }
    }
}
