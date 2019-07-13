package gui.states.single;

import gui.states.GameOverState;
import gui.states.IDStates;
import logic.environment.manager.game.SinglePlayer;
import logic.environment.manager.menu.Menu;
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
    private MouseOverArea newGameButton;

    public GameOverStateSingle(Menu menu) {
        this.menu = menu;
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer,stateBasedGame);

        Image newGame = new Image(readerXmlFile.read("buttonNewGame")).getScaledCopy(30 * gameContainer.getWidth() / 100,
                10 * gameContainer.getHeight() / 100);
        newGameButton = new MouseOverArea(gameContainer, newGame,(gameContainer.getWidth() - newGame.getWidth())/2,
                80*gameContainer.getHeight()/100,30*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);
    }

    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
        singlePlayer = menu.getSinglePlayer();
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer,stateBasedGame,graphics);

        String score = "SCORE: " + singlePlayer.getPlayer().getSpaceShip().getCurrentScore();

        newGameButton.render(gameContainer, graphics);

        uniFontScore.drawString((this.gameContainer.getWidth() - uniFontScore.getWidth(score))/2f,
                7* this.gameContainer.getHeight()/100f, score);
    }

    /**
     * Funzione che setta i gestori degli eventi di click sui bottoni
     * @param source Il tasto di cui dobbiamo settare il comportamento
     */
    @Override
    public void componentActivated(AbstractComponent source) {
        super.componentActivated(source);
        if (source == newGameButton) {
            stateBasedGame.enterState(IDStates.SINGLEPLAYER_STATE, new FadeOutTransition(), new FadeInTransition());
            audioplayer.game();
        }
    }

    @Override
    public int getID() {
        return IDStates.GAMEOVERSINGLE_STATE;
    }
}
