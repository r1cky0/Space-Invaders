package gui.states.menu;

import gui.states.BasicState;
import gui.states.IDStates;
import gui.states.buttons.Button;
import logic.manager.menu.Menu;
import logic.sprite.Coordinate;
import org.newdawn.slick.Color;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import java.awt.Font;

/**
 * Stato iniziale per il login o l'add account.
 */
public class StartState extends BasicState implements ComponentListener {
    private Menu menu;

    private TextField nameField;
    private TextField passwordField;
    private Button loginButton;
    private Button accountButton;
    private String errorMessage;

    public StartState(Menu menu){
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer,stateBasedGame);
        createTextField();
        createButton();
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        errorMessage = "";
        nameField.setText("");
        passwordField.setText("");
        nameField.setFocus(true);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer, stateBasedGame, graphics);
        String title = "LOGIN";
        String nameString = "NICKNAME:";
        String passwordString = "PASSWORD:";

        nameField.render(gameContainer, graphics);
        passwordField.render(gameContainer, graphics);
        loginButton.render(graphics);
        accountButton.render(graphics);

        getTitleFont().drawString((gameContainer.getWidth() - getTitleFont().getWidth(title))/2f,7*gameContainer.getHeight()/100f, title);
        getDataFont().drawString(23*gameContainer.getWidth()/100f,25*gameContainer.getHeight()/100f, nameString);
        getDataFont().drawString(23*gameContainer.getWidth()/100f,33*gameContainer.getHeight()/100f, passwordString);
        getDataFont().drawString((gameContainer.getWidth() - getDataFont().getWidth(errorMessage))/2f,45*gameContainer.getHeight()/100f, errorMessage, Color.red);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        Input input = gameContainer.getInput();
        if (input.isKeyPressed(Input.KEY_TAB)) {
            if(nameField.hasFocus()) {
                passwordField.setFocus(true);
            }
            else{
                nameField.setFocus(true);
            }
        }
        if(input.isKeyPressed(Input.KEY_ENTER)){
            componentActivated(loginButton.getMouseOverArea());
        }
    }

    /**
     * Funzione che si attiva quando si clicca su un bottone
     * Esegue login o add account.
     * @param source bottone premuto
     */
    public void componentActivated(AbstractComponent source) {
        String nickname = nameField.getText();
        String password = passwordField.getText();

        if (!(nickname.isEmpty() || password.isEmpty())) {
            if (source == loginButton.getMouseOverArea()) {
                if (!menu.logIn(nickname, password)) {
                    errorMessage = "Nickname o password errati";
                    return;
                }
            } else if (source == accountButton.getMouseOverArea()) {
                if (!menu.newAccount(nickname, password)) {
                    errorMessage = "Account già esistente";
                    return;
                }
            }
            getStateBasedGame().enterState(accountButton.getIdState(), new FadeOutTransition(), new FadeInTransition());
        } else {
            errorMessage = "Inserire dati";
        }
    }

    /**
     * Funzione che crea i campi di testo per inserire i dati.
     */
    private void createTextField(){
        Font font = new Font("Verdana", Font.BOLD,3*getGameContainer().getWidth()/100);
        TrueTypeFont ttf = new TrueTypeFont(font,true);
        int fieldWidth = getGameContainer().getWidth()/3;
        int fieldHeight = getGameContainer().getHeight()/18;
        nameField = new TextField(getGameContainer(), ttf,43*getGameContainer().getWidth()/100,24*getGameContainer().getHeight()/100, fieldWidth, fieldHeight);
        nameField.setBackgroundColor(Color.white);
        nameField.setTextColor(Color.black);
        passwordField= new TextField(getGameContainer(), ttf,43*getGameContainer().getWidth()/100,32*getGameContainer().getHeight()/100, fieldWidth, fieldHeight);
        passwordField.setBackgroundColor(Color.white);
        passwordField.setTextColor(Color.black);
    }

    /**
     * Funzione che crea i bottoni dello stato.
     */
    private void createButton() throws SlickException {
        int width = 25*getGameContainer().getWidth()/100;
        int height = 10*getGameContainer().getHeight()/100;

        Image login = new Image(getReaderXmlFile().read("buttonLogin")).getScaledCopy(width, height);
        Coordinate posLogin = new Coordinate((getGameContainer().getWidth() - login.getWidth())/2,55*getGameContainer().getHeight()/100);
        loginButton = new Button(getGameContainer(), login, posLogin, IDStates.MENU_STATE, this);

        Image account = new Image(getReaderXmlFile().read("buttonAccount")).getScaledCopy(width, height);
        Coordinate posAccount = new Coordinate((getGameContainer().getWidth() - account.getWidth())/2,70*getGameContainer().getHeight()/100);
        accountButton = new Button(getGameContainer(), account, posAccount, IDStates.MENU_STATE, this);
    }

    @Override
    public int getID() {
        return IDStates.START_STATE;
    }
}
