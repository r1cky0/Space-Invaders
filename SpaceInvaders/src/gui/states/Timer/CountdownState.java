package gui.states.Timer;

import gui.states.BasicState;
import gui.states.IDStates;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class CountdownState extends BasicState {
    private Animation animation;
    private Image[] moving;
    private int idState;
    private CountdownTimer countdownTimer;

    public CountdownState(int idState){
        this.idState = idState;
        moving = new Image[3];
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer, stateBasedGame);
        countdownTimer = new CountdownTimer(stateBasedGame, idState);
        int imageSide = 30*gameContainer.getWidth()/100;
        int number;
        for(int i=0;i<3;i++){
            number = i+1;
            moving[i] = new Image(getReaderXmlFile().read("number" + number)).getScaledCopy(imageSide, imageSide);
        }
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
        animation = new Animation(moving,1000);
        countdownTimer.startTimer();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer, stateBasedGame, graphics);
        animation.draw((gameContainer.getWidth() - animation.getWidth())/2f,(gameContainer.getHeight() - animation.getHeight())/2f);
    }

    @Override
    public int getID(){
        return IDStates.COUNTDOWN_STATE;
    }
}
