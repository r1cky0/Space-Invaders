package gui.states.Timer;

import gui.states.BasicState;
import gui.states.IDStates;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Stato del countdown prima dell'inizio della partita in modalità multiplayer.
 * Contiene l'animazione di attesa con il countdown.
 */
public class CountdownState extends BasicState {
    private Animation animation;
    private Image[] moving;
    private CountdownTimer countdownTimer;

    public CountdownState(){
        moving = new Image[3];
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer, stateBasedGame);
        int imageSide = 30*gameContainer.getWidth()/100;
        int number;
        for(int i=0;i<3;i++){
            number = 3-i;
            moving[i] = new Image(getReaderXmlFile().read("number" + number)).getScaledCopy(imageSide, imageSide);
        }
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
        countdownTimer = new CountdownTimer(stateBasedGame);
        animation = new Animation(moving,1000);
        countdownTimer.startTimer();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer, stateBasedGame, graphics);
        String title = "SPACE INVADERS";
        getTitleFont().drawString((gameContainer.getWidth() - getTitleFont().getWidth(title))/2f,7*gameContainer.getHeight()/100f, title, Color.white);
        animation.draw((gameContainer.getWidth() - animation.getWidth())/2f,(gameContainer.getHeight() - animation.getHeight())/2f);
    }

    @Override
    public int getID(){
        return IDStates.COUNTDOWN_STATE;
    }

}
