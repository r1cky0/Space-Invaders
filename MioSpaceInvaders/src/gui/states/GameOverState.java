package gui.states;

import logic.environment.Menu;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import java.awt.Font;

public class GameOverState extends BasicGameState implements ComponentListener {

    private Image gameOver;
    private GameContainer container;
    private StateBasedGame stateBasedGame;

    private MouseOverArea newGameButton;
    private Image newGame;

    private Font UIFont1;
    private UnicodeFont uniFont;

    private Menu menu;

    public GameOverState(Menu menu) {
        this.menu = menu;
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container = gameContainer;
        this.stateBasedGame = stateBasedGame;

        newGame = new Image("res/images/ButtonNewGame.png").getScaledCopy(gameContainer.getWidth() / 3,
                gameContainer.getHeight() / 10);
        newGameButton = new MouseOverArea(gameContainer, newGame, gameContainer.getWidth() / 3, 5 * gameContainer.getHeight() / 7,
                gameContainer.getWidth() / 3, gameContainer.getHeight() / 10, this);

        gameOver = new Image("res/images/BackgroundGameOver.png");
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        gameOver.draw(container.getWidth() / 7, container.getHeight() / 6);
        newGameButton.render(gameContainer, graphics);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }


    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void componentActivated(AbstractComponent source) {
        if (source == newGameButton) {
            try {
                menu.startGame();
                stateBasedGame.getState(2).init(container, stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(2, new FadeOutTransition(), new FadeInTransition()); // prima bisogna inizializzare lo start game di nuovo
        }
    }
}
