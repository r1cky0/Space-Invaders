package logic.manager.game;

import logic.manager.field.FieldManager;
import logic.thread.ThreadInvader;

public abstract class Game {
    private FieldManager fieldManager;
    private States gameState;
    private ThreadInvader threadInvader;
    private boolean threadRunning;

    public Game(){
        threadRunning = false;
    }

    public void startGame(){
        fieldManager = new FieldManager();
        gameState = States.START;
        startThreadInvader();
    }

    public void stopThreadInvader() {
        if(threadRunning) {
            threadInvader.stop();
        }
    }

    public void startThreadInvader(){
        threadInvader = new ThreadInvader(fieldManager.getDifficulty(), fieldManager);
        threadInvader.start();
        threadRunning = true;
    }

    public abstract void update(int delta);

    public void setGameState(States gameState){
        this.gameState = gameState;
    }

    public FieldManager getFieldManager(){
        return fieldManager;
    }

    public States getGameState(){
        return gameState;
    }

    public boolean isThreadRunning(){
        return threadRunning;
    }
}
