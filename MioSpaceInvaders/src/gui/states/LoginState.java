package gui.states;

import logic.environment.Menu;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.awt.Font;

public class LoginState extends BasicGameState {

    private Menu menu;

    private GameContainer gameContainer;

    private Image login;
    private Image newAccount;
    //private Image exit;
    private Image background;

    private StateBasedGame stateBasedGame;
    private MouseOverArea loginButton;
    private MouseOverArea newAccountButton;
    //private MouseOverArea exitButton;

    private Font UIFont1;
    private UnicodeFont uniFont;


    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        menu = new Menu(gameContainer.getHeight(),gameContainer.getWidth());
        this.gameContainer= gameContainer;
        this.stateBasedGame= stateBasedGame;
        this.background = new Image("res/images/space.png");

        //login = new Image("");

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return 0;
    }

}
