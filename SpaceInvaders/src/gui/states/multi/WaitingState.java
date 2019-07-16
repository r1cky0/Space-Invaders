package gui.states.multi;

import gui.states.Timer.ConnectionTimer;
import gui.states.Timer.CountdownState;
import gui.states.BasicState;
import gui.states.IDStates;
import logic.manager.game.multi.LocalMultiManger;
import logic.manager.game.States;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class WaitingState extends BasicState {
    private LocalMultiManger localMultiManger;
    private Animation movingAnimation;
    private ConnectionTimer connectionTimer;
    private String title;
    private boolean first;

    public WaitingState(){
        localMultiManger = new LocalMultiManger();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer, stateBasedGame);
        title = "CONNECTION...";
        connectionTimer = new ConnectionTimer(stateBasedGame, localMultiManger);
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
        stateBasedGame.addState(new MultiplayerState(localMultiManger));
        stateBasedGame.getState(IDStates.MULTIPLAYER_STATE).init(gameContainer, stateBasedGame);
        stateBasedGame.addState(new CountdownState(IDStates.MULTIPLAYER_STATE));
        stateBasedGame.getState(IDStates.COUNTDOWN_STATE).init(gameContainer,stateBasedGame);
        localMultiManger.init();
        connectionTimer.startTimer();
        first = true;
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
        localMultiManger.checkConnection();
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            localMultiManger.exit();
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if (localMultiManger.getGameState().equals(States.START)) {
            connectionTimer.stopTimer();
                stateBasedGame.enterState(IDStates.COUNTDOWN_STATE);
        } else if ((localMultiManger.getGameState().equals(States.WAITING)) && (first)) {
            connectionTimer.restart();
            title = " WAITING FOR\nOTHER PLAYERS...";
            first = false;
        }
    }

    @Override
    public int getID() {
        return IDStates.WAITING_STATE;
    }

}
