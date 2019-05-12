package gui.states;

import logic.environment.Menu;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.Font;

public class LoginState extends BasicGameState {

    private Menu menu;

    private StateBasedGame stateBasedGame;
    private GameContainer gameContainer;

    private TextField nameField;
    private TextField passwordField;

    private String message;
    private String nameString;
    private String passwordString;

    private Font UIFont1;
    private UnicodeFont uniFont;

    private Image background;



    public LoginState(Menu menu){
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer= gameContainer;
        this.stateBasedGame= stateBasedGame;
        background = new Image("res/images/space.png");
        try{
            UIFont1 = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            UIFont1 = UIFont1.deriveFont(Font.PLAIN, 40);
            uniFont = new UnicodeFont(UIFont1);
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();
        }catch(Exception e){
            e.printStackTrace();
        }

        message = "login";
        nameString = "nickname:";
        passwordString = "password:";

        nameField = new TextField(gameContainer,uniFont,260,200,300,40);
        passwordField = new TextField(gameContainer,uniFont,260,250,300,40);

        nameField.setBackgroundColor(Color.white);
        nameField.setTextColor(Color.black);

        passwordField.setBackgroundColor(Color.white);
        passwordField.setTextColor(Color.black);

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);

        nameField.render(gameContainer, graphics);
        passwordField.render(gameContainer, graphics);

         uniFont.drawString(150, 50, message);
        uniFont.drawString(50,200, nameString);
        uniFont.drawString(50,250, passwordString);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return 5;
    }
}
