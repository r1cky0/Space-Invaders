package gui.states.single;

import gui.states.GameOverState;
import gui.states.IDStates;
import gui.states.buttons.Button;
import logic.manager.game.single.SinglePlayer;
import logic.manager.menu.Menu;
import logic.sprite.Coordinate;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameOverStateSingle extends GameOverState implements ComponentListener {
    private SinglePlayer singlePlayer;
    private Menu menu;
    private Button newGameButton;

    public GameOverStateSingle(Menu menu) {
        this.menu = menu;
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer,stateBasedGame);
        Image newGame = new Image(getReaderXmlFile().read("buttonNewGame")).getScaledCopy(30*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100);
        Coordinate posNewGame = new Coordinate((gameContainer.getWidth() - newGame.getWidth())/2,80*gameContainer.getHeight()/100);
        newGameButton = new Button(gameContainer, newGame, posNewGame, IDStates.SINGLEPLAYER_STATE, this);
    }

    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
        super.enter(gameContainer, stateBasedGame);
        singlePlayer = menu.getSinglePlayer();
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer,stateBasedGame,graphics);
        String score = "SCORE: " + singlePlayer.getPlayer().getSpaceShip().getCurrentScore();
        newGameButton.render(graphics);
        getTitleFont().drawString((gameContainer.getWidth() - getTitleFont().getWidth(score))/2f,7*gameContainer.getHeight()/100f, score);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        super.update(gameContainer, stateBasedGame, delta);
    }

    /**
     * Funzione che si attiva quando si clicca su un bottone
     * @param source bottone premuto
     */
    @Override
    public void componentActivated(AbstractComponent source) {
        super.componentActivated(source);
        if (source == newGameButton.getMouseOverArea()) {
            getStateBasedGame().enterState(newGameButton.getIdState(), new FadeOutTransition(), new FadeInTransition());
        }
    }

    @Override
    public int getID() {
        return IDStates.GAMEOVERSINGLE_STATE;
    }
}
