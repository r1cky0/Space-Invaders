package gui.states.multi;

import gui.states.BasicState;
import gui.states.IDStates;
import network.client.LocalMultiManger;
import logic.manager.game.States;
import network.client.LocalMultiRender;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Stato che rappresenta la partita in modalità multiplayer.
 */
public class MultiplayerState extends BasicState {
    private LocalMultiManger localMultiManger;
    private LocalMultiRender localMultiRender;
    private int life;

    MultiplayerState(LocalMultiManger localMultiManger) {
        this.localMultiManger = localMultiManger;
        life = 3;
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
        localMultiRender = new LocalMultiRender();
        getAudioplayer().game();
        gameContainer.getInput().clearKeyPressedRecord();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer, stateBasedGame, graphics);
        getDataFont().drawString(85*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,"Lives: " + life, Color.red);
        getDataFont().drawString(2*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,"Score: " + localMultiRender.getScore(), Color.white);
        if(localMultiManger.getRcvdata() != null) {
            localMultiRender.draw(localMultiManger.getRcvdata(), localMultiManger.getID());
        }
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        Input input = gameContainer.getInput();
        if(input.isKeyPressed(Input.KEY_SPACE)){
            localMultiManger.execCommand(Input.KEY_SPACE);
        }
        if(input.isKeyDown(Input.KEY_RIGHT)){
            localMultiManger.execCommand(Input.KEY_RIGHT);
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            localMultiManger.execCommand(Input.KEY_LEFT);
        }
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            localMultiManger.exit();
            stateBasedGame.enterState(IDStates.MENU_STATE,new FadeOutTransition(),new FadeInTransition());
        }
        if(localMultiRender.getGameState() == States.GAMEOVER){
            localMultiManger.exit();
            try {
                stateBasedGame.addState(new GameOverSateteMulti(localMultiRender.getScore()));
                stateBasedGame.getState(IDStates.GAMEOVERMULTI_STATE).init(gameContainer,stateBasedGame);
                stateBasedGame.enterState(IDStates.GAMEOVERMULTI_STATE,new FadeOutTransition(),new FadeInTransition());
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        if(life > localMultiRender.getLife()){
            getAudioplayer().explosion();
        }
        life = localMultiRender.getLife();
    }

    @Override
    public int getID() {
        return IDStates.MULTIPLAYER_STATE;
    }
}
