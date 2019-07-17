package gui.states.multi;

import gui.states.GameOverState;
import gui.states.IDStates;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverSateteMulti extends GameOverState implements ComponentListener {
    private int score;

    GameOverSateteMulti(int score){this.score = score;}

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer,stateBasedGame);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics){
        super.render(gameContainer,stateBasedGame,graphics);
        String title = "SCORE: " + score;
        getTitleFont().drawString((gameContainer.getWidth() - getTitleFont().getWidth(title))/2f,7*gameContainer.getHeight()/100f, title);
    }

    @Override
    public int getID() {
        return IDStates.GAMEOVERMULTI_STATE;
    }

}
