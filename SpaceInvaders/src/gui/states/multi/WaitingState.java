package gui.states.multi;

import gui.states.Timer.ConnectionTimer;
import gui.states.BasicState;
import gui.states.IDStates;
import logic.manager.game.commands.CommandType;
import network.client.LocalMultiManager;
import logic.manager.game.States;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Stato di attesa connessione e inizio partita multiplayer.
 */
public class WaitingState extends BasicState {
    private LocalMultiManager localMultiManager;
    private Animation movingAnimation;
    private ConnectionTimer connectionTimer;
    private String title;

    public WaitingState(){
        localMultiManager = new LocalMultiManager();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer, stateBasedGame);
        connectionTimer = new ConnectionTimer(stateBasedGame, localMultiManager);
        int[] duration = {500,500};
        int invaderSide = 20*gameContainer.getHeight()/100;
        Image[] moving = new Image[2];
        for(int i = 0; i<moving.length; i++){
            moving[i] = new Image(getReaderXmlFile().read("defaultInvader" + i)).getScaledCopy(invaderSide, invaderSide);
        }
        movingAnimation = new Animation(moving, duration, true);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        title = "CONNECTION...";
        localMultiManager.init();
        stateBasedGame.addState(new MultiplayerState(localMultiManager));
        stateBasedGame.getState(IDStates.MULTIPLAYER_STATE).init(gameContainer, stateBasedGame);
        connectionTimer.startTimer();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer, stateBasedGame, graphics);
        getTitleFont().drawString((gameContainer.getWidth() - getTitleFont().getWidth(title))/2f,8*gameContainer.getHeight()/100f, title);
        movingAnimation.draw((gameContainer.getWidth() - movingAnimation.getWidth())/2f,(gameContainer.getHeight() - movingAnimation.getHeight())/2f);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        Input input = gameContainer.getInput();
        localMultiManager.checkConnection();
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            localMultiManager.sendCommand(CommandType.EXIT);
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if (localMultiManager.getConnectionState().equals(States.COUNTDOWN)) {
            connectionTimer.stopTimer();
            stateBasedGame.enterState(IDStates.COUNTDOWN_STATE);
        } else if (localMultiManager.getConnectionState().equals(States.WAITING)) {
            title = " LOADING...";
        }
    }

    @Override
    public int getID() {
        return IDStates.WAITING_STATE;
    }

}
