package gui.states.multi;

import gui.states.BasicInvaderState;
import gui.states.IDStates;
import logic.environment.manager.game.Commands;
import logic.environment.manager.menu.Menu;
import network.client.Client;
import network.data.PacketHandler;
import logic.environment.manager.game.States;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class WaitingState extends BasicInvaderState {
    private UnicodeFont uniFontTitle;
    private String title;

    private Image background;
    private Animation movingAnimation;
    private int[] duration = {500,500};

    private Menu menu;
    private Client client;
    private PacketHandler handler;

    public WaitingState(Menu menu) {
        this.menu = menu;
        handler = new PacketHandler();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        background = new Image(readerXmlFile.read("defaultBackground"));
        uniFontTitle = build(5 * gameContainer.getWidth() / 100f);
        title = "WAITING FOR CONNECTION...";

        Image[] moving= {new Image(readerXmlFile.read("defaultInvader")).getScaledCopy(20*gameContainer.getWidth()/100,
                20*gameContainer.getHeight()/100), new Image(readerXmlFile.read("defaultInvaderb")).
                getScaledCopy(20*gameContainer.getWidth()/100,20*gameContainer.getHeight()/100)};

        movingAnimation = new Animation(moving, duration, true);
    }

    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
        client = new Client(menu.getPlayer(), "localhost", 9999);
        client.send(handler.build(client.getPlayer().getName(), client.getConnection()));
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.drawImage(background, 0, 0);

        uniFontTitle.drawString((gameContainer.getWidth() - uniFontTitle.getWidth(title)) / 2f,
                8 * gameContainer.getHeight() / 100f, title);
        movingAnimation.draw((gameContainer.getWidth() - movingAnimation.getWidth())/2f,
                (gameContainer.getHeight() - movingAnimation.getHeight())/2f);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        Input input = gameContainer.getInput();

        if(input.isKeyDown(Input.KEY_ESCAPE)){
            String message = client.getID() + "\n" + Commands.EXIT.toString();
            client.send(handler.build(message, client.getConnection()));
            client.close();
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if(client.getGameState() == States.START){
            try {
                stateBasedGame.addState(new MultiplayerState(client));
                stateBasedGame.getState(IDStates.MULTIPLAYER_STATE).init(gameContainer,stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(IDStates.MULTIPLAYER_STATE, new FadeOutTransition(), new FadeInTransition());
            audioplayer.game();
        }else if(client.getGameState() == States.WAITING){
            title = "WAITING FOR OTHER PLAYERS...";
        }
    }

    @Override
    public int getID() {
        return IDStates.WAITING_STATE;
    }

}
