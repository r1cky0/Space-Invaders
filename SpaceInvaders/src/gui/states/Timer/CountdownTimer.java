package gui.states.Timer;

import gui.states.IDStates;
import org.newdawn.slick.state.StateBasedGame;
import java.util.Timer;
import java.util.TimerTask;

class CountdownTimer {
    private StateBasedGame stateBasedGame;
    private Timer timer;
    private TimerTask timerTask;
    private final int delayTime = 3000; //millis oltre il quale esco da waiting state multiplayer
    private boolean timerStarted;

    CountdownTimer(StateBasedGame stateBasedGame){
        this.stateBasedGame = stateBasedGame;
    }

    void startTimer(){
        if(!timerStarted) {
            timerTask = new TimerTask() {
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
