package gui.states;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Classe che rappresenta l'astrazione del gameOverState.
 * Viene estesa dal GameOver del single e del multi player.
 */
public abstract class GameOverState extends BasicState implements ComponentListener {

    private Image gameOver;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer, stateBasedGame);
        gameOver = new Image(getReaderXmlFile().read("gameoverBackground"));
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
        getAudioplayer().gameOver();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics){
        gameOver.draw((gameContainer.getWidth() - gameOver.getWidth())/2f,(gameContainer.getHeight() - gameOver.getHeight())/2f);
        getHomeButton().render(graphics);
    }

}
