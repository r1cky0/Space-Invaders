package gui.states;
import logic.environment.Menu;
import logic.environment.MovingDirections;
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

public class StartState extends BasicInvaderState implements ComponentListener {

    private StateBasedGame stateBasedGame;
    private GameContainer gameContainer;

    private TextField nameField;
    private TextField passwordField;

    private String title;
    private String nameString;
    private String passwordString;
    private String errorMessage;
    private boolean errorFlag = false;

    // private Font fontTitle;
    // private Font fontMessage;
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

        uniFontTitle = Build(5 * gameContainer.getWidth() / 100);
        uniFontMessage = Build(3 * gameContainer.getWidth() / 100);

        title = "LOGIN AND ADD ACCOUNT";

        nameString = "NICKNAME:";
        passwordString = "PASSWORD:";
        Font font = new Font("Verdana", Font.BOLD, 32);
        TrueTypeFont ttf = new TrueTypeFont(font, true);
        nameField = new TextField(gameContainer, ttf, 40 * gameContainer.getWidth() / 100, 25 * gameContainer.getHeight() / 100,
                gameContainer.getWidth() / 3, gameContainer.getHeight() / 18);

        nameField.setFocus(true);

        passwordField = new TextField(gameContainer, ttf, 40 * gameContainer.getWidth() / 100, 33 * gameContainer.getHeight() / 100,
                gameContainer.getWidth() / 3, gameContainer.getHeight() / 18);

        nameField.setBackgroundColor(Color.white);
        nameField.setTextColor(Color.black);

        passwordField.setBackgroundColor(Color.white);
        passwordField.setTextColor(Color.black);

        login = new Image("res/images/ButtonLogin.png").getScaledCopy(25 * gameContainer.getWidth() / 100,
                10 * gameContainer.getHeight() / 100);

        account = new Image("res/images/ButtonAccount.png").getScaledCopy(25 * gameContainer.getWidth() / 100,
                10 * gameContainer.getHeight() / 100);

        loginButton = new MouseOverArea(gameContainer, login, (gameContainer.getWidth() - login.getWidth()) / 2,
                55 * gameContainer.getHeight() / 100, 25 * gameContainer.getWidth() / 100, 10 * gameContainer.getHeight() / 100,
                this);

        accountButton = new MouseOverArea(gameContainer, account, (gameContainer.getWidth() - account.getWidth()) / 2,
                70 * gameContainer.getHeight() / 100, 25 * gameContainer.getWidth() / 100, 10 * gameContainer.getHeight() / 100,
                this);

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background, 0, 0);

        nameField.render(gameContainer, graphics);
        passwordField.render(gameContainer, graphics);

        uniFontTitle.drawString((gameContainer.getWidth() - uniFontTitle.getWidth(title)) / 2f,
                7 * gameContainer.getHeight() / 100f, title);
        uniFontMessage.drawString(15 * gameContainer.getWidth() / 100f, 25 * gameContainer.getHeight() / 100f, nameString);
        uniFontMessage.drawString(15 * gameContainer.getWidth() / 100f, 33 * gameContainer.getHeight() / 100f, passwordString);

        if (errorFlag) {
            uniFontMessage.drawString((gameContainer.getWidth() - uniFontMessage.getWidth(errorMessage)) / 2f,
                    45 * gameContainer.getHeight() / 100f, errorMessage, Color.red);
        }

        loginButton.render(gameContainer, graphics);
        accountButton.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gameContainer.getInput();

        if (input.isKeyDown(Input.KEY_TAB)) {
            passwordField.setFocus(true);
        }
    }

    @Override
    public int getID() {
        return 0;
    }

    /**
     * Funzione che setta i gestori degli eventi di click sui bottoni
     *
     * @param source Il tasto di cui dobbiamo settare il comportamento
     */
    public void componentActivated(AbstractComponent source) {
        if (source == loginButton) {
            String nickname = nameField.getText();
            String password = passwordField.getText();

            if (menu.logIn(nickname, password)) {
                stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
            } else {
                errorFlag = true;
                errorMessage = "Nickname o password errati";
            }
        }

        if (source == accountButton) {
            String nickname = nameField.getText();
            String password = passwordField.getText();

            if (!(nickname.isEmpty() || password.isEmpty())) {
                if (menu.newAccount(nickname, password)) {
                    stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
                } else {
                    errorFlag = true;
                    errorMessage = "Account già esistente";
                }
            }
        }
    }
}
