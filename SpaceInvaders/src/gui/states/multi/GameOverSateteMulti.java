package gui.states.multi;

import gui.states.BasicGameOver;
import gui.states.IDStates;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverSateteMulti extends BasicGameOver implements ComponentListener {
    private String score;

    private UnicodeFont uniFontScore;

    public GameOverSateteMulti(String score){this.score = score;}

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer,stateBasedGame);
        uniFontScore = build(9 * gameContainer.getWidth() / 100f);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics){
        super.render(gameContainer,stateBasedGame,graphics);
        String title = "SCORE: " + score;
        uniFontScore.drawString((this.gameContainer.getWidth() - uniFontScore.getWidth(title))/2f,
                7*this.gameContainer.getHeight()/100f, title);
    }

    @Override
    public int getID() {
        return IDStates.GAMEOVERMULTI_STATE;
    }

}
