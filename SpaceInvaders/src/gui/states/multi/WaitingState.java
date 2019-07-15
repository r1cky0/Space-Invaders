package gui.states.multi;

import gui.states.BasicState;
import gui.states.IDStates;
import logic.manager.game.Commands;
import logic.manager.menu.Menu;
import network.client.Client;
import network.data.PacketHandler;
import logic.manager.game.States;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.Timer;
import java.util.TimerTask;

public class WaitingState extends BasicState {
    private UnicodeFont uniFontTitle;
    private String title;
    private LocalMultiManger localMultiManger;
    private Image background;
    private Animation movingAnimation;
    private int[] duration = {500,500};

    private ConnectionTimer connectionTimer;
    private boolean first;

    public WaitingState(){
        localMultiManger = new LocalMultiManger();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        connectionTimer = new ConnectionTimer(stateBasedGame,localMultiManger);
        background = new Image(readerXmlFile.read("defaultBackground"));
        uniFontTitle = build(5 * gameContainer.getWidth() / 100f);
        title = "WAITING FOR CONNECTION...";

        Image[] moving= {new Image(readerXmlFile.read("defaultInvader")).getScaledCopy(20*gameContainer.getWidth()/100,
                20*gameContainer.getHeight()/100), new Image(readerXmlFile.read("defaultInvaderb")).
                getScaledCopy(20*gameContainer.getWidth()/100,20*gameContainer.getHeight()/100)};

        movingAnimation = new Animation(moving, duration, true);
    }

    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
        localMultiManger.init();
        connectionTimer.startTimer();
        first = true;
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
        localMultiManger.checkConnection();

        if(input.isKeyDown(Input.KEY_ESCAPE)){
            localMultiManger.exit();
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if(localMultiManger.getGameState().equals(States.START)){
            try {
                connectionTimer.stopTimer();
                stateBasedGame.addState(new MultiplayerState(localMultiManger));
                stateBasedGame.getState(IDStates.MULTIPLAYER_STATE).init(gameContainer,stateBasedGame);
                stateBasedGame.enterState(IDStates.MULTIPLAYER_STATE);
                audioplayer.game();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }else if((localMultiManger.getGameState().equals(States.WAITING)) && (first)){
            connectionTimer.restart();
            title = "WAITING FOR OTHER PLAYERS...";
            first = false;
        }
    }

    @Override
    public int getID() {
        return IDStates.WAITING_STATE;
    }

}
