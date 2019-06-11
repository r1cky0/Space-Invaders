package gui.states;

import logic.environment.manager.file.ReadXmlFile;
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

public class GameOverState extends BasicInvaderState implements ComponentListener {

    private GameContainer gameContainer;
    private StateBasedGame stateBasedGame;

    private Image gameOver;
    private Image newGame;
    private Image homeImage;
    private MouseOverArea homeButton;
    private MouseOverArea newGameButton;

    private UnicodeFont uniFontScore;

    private String score;

    private Menu menu;

    public GameOverState(Menu menu) {
        this.menu = menu;
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;

        newGame = new Image(ReadXmlFile.read(5, "button")).getScaledCopy(30*gameContainer.getWidth()/100,
                10*gameContainer.getHeight()/100);
        newGameButton = new MouseOverArea(gameContainer, newGame,(gameContainer.getWidth() - newGame.getWidth())/2,
                80*gameContainer.getHeight()/100,30*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        homeImage = new Image(ReadXmlFile.read(7, "button")).getScaledCopy(6*gameContainer.getWidth()/100,
                6*gameContainer.getWidth()/100);
        homeButton = new MouseOverArea(gameContainer, homeImage,5*gameContainer.getWidth()/100,7*gameContainer.getHeight()/100,
                6*gameContainer.getWidth()/100,6*gameContainer.getHeight()/100,this);

        gameOver = new Image(ReadXmlFile.read(1, "background"));

        uniFontScore = Build(9*gameContainer.getWidth()/100f);
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        score = "SCORE: " + menu.getOfflineGameManager().getSpaceShip().getCurrentScore();
        gameOver.draw((gameContainer.getWidth() - gameOver.getWidth())/2f,(gameContainer.getHeight() - gameOver.getHeight())/2f);
        newGameButton.render(gameContainer, graphics);
        homeButton.render(gameContainer,graphics);

        uniFontScore.drawString((this.gameContainer.getWidth() - uniFontScore.getWidth(score))/2f,
                7* this.gameContainer.getHeight()/100f, score);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return 3;
    }

    /**
     * Funzione che setta i gestori degli eventi di click sui bottoni
     * @param source Il tasto di cui dobbiamo settare il comportamento
     */
    @Override
    public void componentActivated(AbstractComponent source) {
        if (source == newGameButton) {
            try {
                menu.restartGame();
                stateBasedGame.getState(2).init(gameContainer, stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(2, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == homeButton) {
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
    }
}
