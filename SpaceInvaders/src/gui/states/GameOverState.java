package gui.states;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public abstract class GameOverState extends BasicState implements ComponentListener {

    protected GameContainer gameContainer;
    protected StateBasedGame stateBasedGame;

    private Image gameOver;
    private MouseOverArea homeButton;

    protected UnicodeFont uniFontScore;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;

        Image homeImage = new Image(readerXmlFile.read("buttonHome")).getScaledCopy(6 * gameContainer.getWidth() / 100,
                6 * gameContainer.getWidth() / 100);
        homeButton = new MouseOverArea(gameContainer, homeImage,5*gameContainer.getWidth()/100,
                7*gameContainer.getHeight()/100,6*gameContainer.getWidth()/100,6*gameContainer.getHeight()/100,
                this);

        gameOver = new Image(readerXmlFile.read("gameoverBackground"));

        uniFontScore = build(9 * gameContainer.getWidth() / 100f);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
        audioplayer.gameOver();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics){
        gameOver.draw((gameContainer.getWidth() - gameOver.getWidth())/2f,
                (gameContainer.getHeight() - gameOver.getHeight())/2f);
        homeButton.render(gameContainer, graphics);
    }

    /**
     * Funzione che setta i gestori degli eventi di click sui bottoni
     * @param source Il tasto di cui dobbiamo settare il comportamento
     */
    @Override
    public void componentActivated(AbstractComponent source) {
        if (source == homeButton) {
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
            audioplayer.menu();
        }
    }

}
