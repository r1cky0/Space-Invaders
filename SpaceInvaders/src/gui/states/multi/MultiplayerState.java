package gui.states.multi;

import gui.states.GameState;
import gui.states.IDStates;
import logic.manager.game.commands.CommandType;
import network.client.LocalMultiManager;
import logic.manager.game.States;
import network.client.LocalMultiRender;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Stato che rappresenta la partita in modalità multiplayer.
 */
public class MultiplayerState extends GameState {
    private LocalMultiManager localMultiManager;
    private LocalMultiRender localMultiRender;

    public MultiplayerState(LocalMultiManager localMultiManager) {
        this.localMultiManager = localMultiManager;
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.enter(gameContainer, stateBasedGame);
        localMultiRender = new LocalMultiRender();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer, stateBasedGame, graphics);
        getDataFont().drawString(2*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,"Score: " + localMultiRender.getScore(), Color.white);
        if(localMultiManager.getRcvdata() != null) {
            localMultiRender.draw(localMultiManager.getRcvdata(), localMultiManager.getID());
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        super.update(gameContainer, stateBasedGame, delta);
        Input input = gameContainer.getInput();
        //STATO DI GIOCO
        if(localMultiRender.getGameState() == States.GAMEOVER) {
            localMultiManager.sendCommand(CommandType.EXIT);
            stateBasedGame.addState(new GameOverStateMulti(localMultiRender.getScore()));
            stateBasedGame.getState(IDStates.GAMEOVERMULTI_STATE).init(gameContainer, stateBasedGame);
            stateBasedGame.enterState(IDStates.GAMEOVERMULTI_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if(getLife() > localMultiRender.getLife()){
            getAudioplayer().explosion();
        }
        setLife(localMultiRender.getLife());
        //COMANDI
        for(Integer key : getKeyboardKeys().keySet()){
            if(input.isKeyDown(key)){
                localMultiManager.sendCommand(getKeyboardKeys().get(key));
            }
        }
    }

    @Override
    public int getID() {
        return IDStates.MULTIPLAYER_STATE;
    }
}
