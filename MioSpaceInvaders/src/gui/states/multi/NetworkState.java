package gui.states.multi;

import gui.states.BasicInvaderState;
import gui.states.IDStates;
import logic.environment.manager.file.ReadXmlFile;
import logic.environment.manager.menu.Menu;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.awt.Font;

public class NetworkState extends BasicInvaderState implements ComponentListener {
    private StateBasedGame stateBasedGame;

    private TextField ipField;
    private TextField portField;

    private String title;
    private String ipString;
    private String portString;
    private String errorMessage;
    private boolean errorFlag = false;

    private UnicodeFont uniFontTitle;
    private UnicodeFont uniFontMessage;

    private Image background;
    private Image multiplayer;

    private MouseOverArea multiplayerButton;

    private Menu menu;

    public NetworkState(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;

        background = new Image(ReadXmlFile.read("defaultBackground"));
        uniFontTitle = build(6*gameContainer.getWidth()/100f);
        uniFontMessage = build(3*gameContainer.getWidth()/100f);

        title = "SERVER IP AND PORT";
        ipString = "IP:";
        portString = "PORT:";

        Font font = new Font("Verdana", Font.BOLD, 3*gameContainer.getWidth()/100);
        TrueTypeFont ttf = new TrueTypeFont(font, true);

        ipField = new TextField(gameContainer, ttf,43*gameContainer.getWidth()/100,28*gameContainer.getHeight()/100,
                gameContainer.getWidth()/3, gameContainer.getHeight()/18);

        ipField.setFocus(true);

        portField = new TextField(gameContainer, ttf,43*gameContainer.getWidth()/100,36*gameContainer.getHeight()/100,
                gameContainer.getWidth()/3,gameContainer.getHeight()/18);

        ipField.setBackgroundColor(org.newdawn.slick.Color.white);
        ipField.setTextColor(org.newdawn.slick.Color.black);
        portField.setBackgroundColor(org.newdawn.slick.Color.white);
        portField.setTextColor(org.newdawn.slick.Color.black);

        multiplayer = new Image(ReadXmlFile.read("buttonMultiplayer")).getScaledCopy(25*gameContainer.getWidth()/100,
                10*gameContainer.getHeight()/100);

        multiplayerButton = new MouseOverArea(gameContainer, multiplayer,(gameContainer.getWidth() - multiplayer.getWidth())/2,
                59*gameContainer.getHeight()/100,25*gameContainer.getWidth()/100,
                10*gameContainer.getHeight()/100,this);
    }

    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        errorMessage = "";
        ipField.setText("");
        portField.setText("");
        ipField.setFocus(true);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.drawImage(background, 0, 0);

        ipField.render(gameContainer, graphics);
        portField.render(gameContainer, graphics);

        uniFontTitle.drawString((gameContainer.getWidth() - uniFontTitle.getWidth(title))/2f,
                7*gameContainer.getHeight()/100f, title);
        uniFontMessage.drawString(23*gameContainer.getWidth()/100f,29*gameContainer.getHeight()/100f, ipString);
        uniFontMessage.drawString(23*gameContainer.getWidth()/100f,37*gameContainer.getHeight()/100f, portString);

        if (errorFlag) {
            uniFontMessage.drawString((gameContainer.getWidth() - uniFontMessage.getWidth(errorMessage))/2f,
                    45*gameContainer.getHeight()/100f, errorMessage, Color.red);
        }

        multiplayerButton.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        Input input = gameContainer.getInput();

        if (input.isKeyPressed(Input.KEY_TAB)) {
            portField.setFocus(true);
        }
        if(input.isKeyPressed(Input.KEY_ENTER)){
            setParameters();
        }
    }

    /**
     * Funzione che setta i gestori degli eventi di click sui bottoni
     *
     * @param source Il tasto di cui dobbiamo settare il comportamento
     */
    public void componentActivated(AbstractComponent source) {
        if (source == multiplayerButton) {
            setParameters();
        }
    }

    public void setParameters(){
        String ip = ipField.getText();
        String port = portField.getText();
        if(!(ip.isEmpty() || port.isEmpty()) && Integer.parseInt(port) <= 0 || Integer.parseInt(port) > 9999) {
            stateBasedGame.addState(new WaitingState(menu, ip, Integer.parseInt(port)));
            stateBasedGame.enterState(IDStates.WAITING_STATE, new FadeOutTransition(), new FadeInTransition());
        } else {
            errorFlag = true;
            errorMessage = "Porta non valida";
        }
    }

    @Override
    public int getID() {
        return IDStates.NETWORK_STATE;
    }
}
