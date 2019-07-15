package gui.states.multi;

import gui.states.IDStates;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectionTimer {

    private StateBasedGame stateBasedGame;
    private LocalMultiManger localMultiManger;
    private Timer timer;
    private TimerTask timerTask;
    private final int delayTime = 10000; //millis oltre il quale esco da waiting state multiplayer
    private boolean timerStarted;

    public ConnectionTimer(StateBasedGame stateBasedGame, LocalMultiManger localMultiManger){
        this.stateBasedGame = stateBasedGame;
        this.localMultiManger = localMultiManger;
    }

    public void startTimer(){
        if(!timerStarted) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    localMultiManger.exit();
                    stateBasedGame.enterState(IDStates.MENU_STATE,new FadeOutTransition(),new FadeInTransition());
                }
            };
            timer = new Timer();
            timer.schedule(timerTask, delayTime); //timer attesa connessione a server o altri giocatori
            timerStarted = true;
        }
    }

    public void restart(){
        stopTimer();
        startTimer();
    }

    public void stopTimer(){
        timer.cancel();
        timerStarted = false;
    }

}
