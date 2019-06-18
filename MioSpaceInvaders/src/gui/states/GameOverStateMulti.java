package gui.states;

import logic.environment.manager.file.ReadXmlFile;
import logic.environment.manager.game.SinglePlayer;
import logic.environment.manager.menu.Menu;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameOverStateMulti extends BasicInvaderState implements ComponentListener {

    private GameContainer gameContainer;
    private StateBasedGame stateBasedGame;

    private Image gameOver;
    private Image homeImage;
    private MouseOverArea homeButton;

    private UnicodeFont uniFontScore;
    private String score;

    private Menu menu;

    public GameOverStateMulti(Menu menu) {
        this.menu = menu;
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;

        homeImage = new Image(ReadXmlFile.read("buttonHome")).getScaledCopy(6*gameContainer.getWidth()/100,
                6*gameContainer.getWidth()/100);
        homeButton = new MouseOverArea(gameContainer, homeImage,(gameContainer.getWidth() - homeImage.getWidth())/2,
                80 * gameContainer.getHeight()/100,6*gameContainer.getWidth()/100,6*gameContainer.getHeight()/100,
                this);

        gameOver = new Image(ReadXmlFile.read("gameoverBackground"));

        uniFontScore = build(9 * gameContainer.getWidth() / 100f);
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        score = "SCORE: " + menu.getSinglePlayer().getPlayer().getSpaceShip().getCurrentScore();
        gameOver.draw((gameContainer.getWidth() - gameOver.getWidth())/2f,
                (gameContainer.getHeight() - gameOver.getHeight())/2f);
        homeButton.render(gameContainer, graphics);
        uniFontScore.drawString((this.gameContainer.getWidth() - uniFontScore.getWidth(score))/2f,
                7*this.gameContainer.getHeight()/100f, score);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    }

    /**
     * Funzione che setta i gestori degli eventi di click sui bottoni
     *
     * @param source Il tasto di cui dobbiamo settare il comportamento
     */
    @Override
    public void componentActivated(AbstractComponent source) {
        if (source == homeButton) {
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
        }
    }

    @Override
    public int getID() {
        return IDStates.GAMEOVERMULTI_STATE;
    }

}