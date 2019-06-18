package gui.states;

import logic.environment.manager.file.ReadXmlFile;
import logic.environment.manager.menu.Menu;
import network.client.Client;
import network.server.GameStates;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.awt.Font;


public class WaitingState extends BasicInvaderState implements ComponentListener {

    private StateBasedGame stateBasedGame;
    private GameContainer gameContainer;

    private UnicodeFont uniFontTitle;
    private String title;

    private Image background;
    private Animation movingAnimation;
    private int[] duration = {500,500};

    private Menu menu;
    private GameStates gameStates;
    private Client client;
    private boolean connectionOpened;

    public WaitingState(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;
        background = new Image(ReadXmlFile.read("defaultBackground"));
        uniFontTitle = build(5 * gameContainer.getWidth() / 100f);
        title = "WAITING FOR OTHER PLAYERS...";
        Image[] moving= {new Image(ReadXmlFile.read("defaultInvader")).getScaledCopy(20*gameContainer.getWidth()/100,
                20*gameContainer.getHeight()/100), new Image(ReadXmlFile.read("defaultInvaderb")).
                getScaledCopy(20*gameContainer.getWidth()/100,20*gameContainer.getHeight()/100)};
        movingAnimation = new Animation(moving, duration, true);
        connectionOpened = false;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background, 0, 0);
        uniFontTitle.drawString((gameContainer.getWidth() - uniFontTitle.getWidth(title)) / 2f,
                8 * gameContainer.getHeight() / 100f, title);

        movingAnimation.draw((gameContainer.getWidth() - movingAnimation.getWidth())/2f,
                (gameContainer.getHeight() - movingAnimation.getHeight())/2f);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();

        if(input.isKeyDown(Input.KEY_ESCAPE)){
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }

        if(!connectionOpened){
            client = new Client(menu.getPlayer(), "localhost", 9999);
            connectionOpened = true;
        }

        if(client.getGameState() == GameStates.START){
            MultiplayerState.setClient(client);
            stateBasedGame.enterState(9, new FadeOutTransition(), new FadeInTransition());
        }
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {

    }

    @Override
    public int getID() {
        return 8;
    }

}
