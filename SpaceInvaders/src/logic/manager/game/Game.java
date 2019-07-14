package logic.manager.game;

import logic.manager.field.FieldManager;
import logic.thread.ThreadInvader;

public abstract class Game {
    FieldManager fieldManager;
    States gameState;
    private ThreadInvader threadInvader;
    private boolean threadRunning;

    Game(){
        threadRunning = false;
    }

    public void startGame(){
        fieldManager = new FieldManager();
        threadRunning = false;
        gameState = States.START;
    }

    public void stopGame() {
        if(threadRunning) {
            threadInvader.stop();
        }
    }

    /**
     *  Attivazione del thread di gestione degli invader (movimento e sparo) e check completamento livello
     */
    public void threadInvaderManager(){
        if (!threadRunning) {
            threadInvader = new ThreadInvader(fieldManager.getDifficulty(), fieldManager);
            threadInvader.start();
            threadRunning = true;
        }
        if (fieldManager.isNewLevel()) {
            threadInvader.stop();
            fieldManager.setNewLevel(false);
            threadRunning = false;
        }
    }

    public abstract void update(int delta);

    public FieldManager getFieldManager(){
        return fieldManager;
    }

    public States getGameState(){
        return gameState;
    }

    boolean isThreadRunning(){
        return threadRunning;
    }
}
