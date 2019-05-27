package gui.states;
import logic.environment.Menu;
import org.newdawn.slick.Color;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.Font;
import java.io.IOException;

public class StartState extends BasicGameState implements ComponentListener {

    private StateBasedGame stateBasedGame;
    private GameContainer gameContainer;

    private TextField nameField;
    private TextField passwordField;

    private String title;
    private String nameString;
    private String passwordString;
    private String errorMessage;
    private boolean errorFlag = false;

    private Font titleFont;
    private Font messageFont;
    private Font font = new Font("Verdana", Font.BOLD, 32);
    private TrueTypeFont ttf = new TrueTypeFont(font, true);
    private UnicodeFont uniFontTitle;
    private UnicodeFont uniFontMessage;

    private Image background;

    private Image login;
    private Image account;
    private MouseOverArea loginButton;
    private MouseOverArea accountButton;

    private Menu menu;

    public StartState(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;
        background = new Image("res/images/BackgroundSpace.png");

        try {
            titleFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            titleFont = titleFont.deriveFont(Font.BOLD, 60);
            uniFontTitle = new UnicodeFont(titleFont);
            uniFontTitle.getEffects().add(new ColorEffect(java.awt.Color.white));

            messageFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            messageFont = messageFont.deriveFont(Font.BOLD, 40);
            uniFontMessage = new UnicodeFont(messageFont);
            uniFontMessage.getEffects().add(new ColorEffect(java.awt.Color.white));


            uniFontTitle.addAsciiGlyphs();
            uniFontTitle.loadGlyphs();
            uniFontMessage.addAsciiGlyphs();
            uniFontMessage.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        title = "LOGIN AND ADD ACCOUNT";

        nameString = "NICKNAME:";
        passwordString = "PASSWORD:";

        nameField = new TextField(gameContainer, ttf, 6 * gameContainer.getWidth() / 15, gameContainer.getHeight() / 4,
                300, 40);

        passwordField = new TextField(gameContainer, ttf, 6 * gameContainer.getWidth() / 15, gameContainer.getHeight() / 3,
                300, 40);

        nameField.setBackgroundColor(Color.white);
        nameField.setTextColor(Color.black);

        passwordField.setBackgroundColor(Color.white);
        passwordField.setTextColor(Color.black);

        login = new Image("res/images/ButtonLogin.png").getScaledCopy(gameContainer.getWidth() / 3,
                gameContainer.getHeight() / 10);

        account = new Image("res/images/ButtonAccount.png").getScaledCopy(gameContainer.getWidth() / 3,
                gameContainer.getHeight() / 10);

        loginButton = new MouseOverArea(gameContainer, login, gameContainer.getWidth() / 3, 4 * gameContainer.getHeight() / 7,
                gameContainer.getWidth() / 3, gameContainer.getHeight() / 10, this);

        accountButton = new MouseOverArea(gameContainer, account, gameContainer.getWidth() / 3, 5 * gameContainer.getHeight() / 7,
                gameContainer.getWidth() / 3, gameContainer.getHeight() / 10, this);

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background, 0, 0);

        nameField.render(gameContainer, graphics);
        passwordField.render(gameContainer, graphics);

        uniFontTitle.drawString(gameContainer.getWidth() / 7, gameContainer.getHeight() / 14, title);
        uniFontMessage.drawString(gameContainer.getWidth() / 7, gameContainer.getHeight() / 4, nameString);
        uniFontMessage.drawString(gameContainer.getWidth() / 7, gameContainer.getHeight() / 3, passwordString);

        if (errorFlag) {
            uniFontMessage.drawString((gameContainer.getWidth() - uniFontMessage.getWidth(errorMessage)) / 2,
                    7 * gameContainer.getHeight() / 15, errorMessage, Color.yellow);
        }

        loginButton.render(gameContainer, graphics);
        accountButton.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    }

    @Override
    public int getID() {
        return 0;
    }

    public void componentActivated(AbstractComponent source) {
        if (source == loginButton) {
            String nickname = nameField.getText();
            String password = passwordField.getText();

            try {
                if (menu.logIn(nickname, password)) {
                    stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
                } else {
                    errorFlag = true;
                    errorMessage = "Nickname o password errati";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (source == accountButton) {
            String nickname = nameField.getText();
            String password = passwordField.getText();

            if (!(nickname.isEmpty() || password.isEmpty())) {
                try {
                    if (menu.newAccount(nickname, password)) {
                        stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
                    } else {
                        errorFlag = true;
                        errorMessage = "Account già esistente";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
