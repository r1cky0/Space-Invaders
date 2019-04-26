package states;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.awt.Font;

public class GameOverState extends BasicGameState {

    private Image gameOver;
    private GameContainer container;
    private StateBasedGame stateBasedGame;

    private Font UIFont1;
    private UnicodeFont uniFont;

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container= gameContainer;
        this.stateBasedGame= stateBasedGame;

        gameOver = new Image("res/game_over.png");
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        gameOver.draw(container.getWidth()/7,container.getHeight()/6);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    public int getID() {
        return 2;
    }
}
