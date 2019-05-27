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

public class GameOverState extends BasicGameState implements ComponentListener {

    private Image gameOver;
    private GameContainer container;
    private StateBasedGame stateBasedGame;

    private MouseOverArea newGameButton;
    private Image newGame;
    private MouseOverArea menuButton;
    private Image menuImage;

    private Menu menu;

    public GameOverState(Menu menu) {
        this.menu = menu;
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container = gameContainer;
        this.stateBasedGame = stateBasedGame;

        newGame = new Image("res/images/ButtonNewGame.png").getScaledCopy(gameContainer.getWidth()/3,
                gameContainer.getHeight()/10);
        newGameButton = new MouseOverArea(gameContainer, newGame,gameContainer.getWidth()/3,4*gameContainer.getHeight()/7,
                gameContainer.getWidth()/3,gameContainer.getHeight()/10,this);

        menuImage = new Image("res/images/ButtonMenu.png");
        menuButton = new MouseOverArea(gameContainer, menuImage,gameContainer.getWidth()/3,5*gameContainer.getHeight()/7,
                gameContainer.getWidth()/3,gameContainer.getHeight()/10,this);

        gameOver = new Image("res/images/BackgroundGameOver.png");
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        gameOver.draw(container.getWidth()/7, container.getHeight()/20);
        newGameButton.render(gameContainer, graphics);
        menuButton.render(gameContainer,graphics);
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
                menu.restartGame();
                stateBasedGame.getState(2).init(container, stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(2, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == menuButton) {
            try {
                menu.restartGame();
                stateBasedGame.getState(2).init(container, stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
    }
}
