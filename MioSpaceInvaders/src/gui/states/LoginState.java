package gui.states;

import logic.environment.Menu;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.awt.Font;

public class LoginState extends BasicGameState implements ComponentListener{

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

        login = new Image("res/images/login.png").getScaledCopy(gameContainer.getWidth()/3,
                gameContainer.getHeight()/10);
        loginButton = new MouseOverArea(gameContainer, login, gameContainer.getWidth()/3, 2*gameContainer.getHeight()/5,
                gameContainer.getWidth()/3, gameContainer.getHeight()/10, this);

        newAccount = new Image("res/images/account.png").getScaledCopy(gameContainer.getWidth()/3,
                gameContainer.getHeight()/10);
        newAccountButton = new MouseOverArea(gameContainer, newAccount,gameContainer.getWidth()/3,3*gameContainer.getHeight()/5,
                gameContainer.getWidth()/3, gameContainer.getHeight()/10, this);

        try{
            UIFont1 = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            UIFont1 = UIFont1.deriveFont(Font.BOLD, gameContainer.getWidth()/12f);


            uniFont = new UnicodeFont(UIFont1);

            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));

            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);
        loginButton.render(gameContainer, graphics);
        newAccountButton.render(gameContainer, graphics);

        uniFont.drawString(gameContainer.getWidth()/8f, gameContainer.getHeight()/8f, "SPACE INVADERS", Color.white);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void componentActivated(AbstractComponent source) {
        if (source == loginButton ) {
            stateBasedGame.enterState(0, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == newAccountButton ) {
            stateBasedGame.enterState(0, new FadeOutTransition(), new FadeInTransition());
        }
    }
}
