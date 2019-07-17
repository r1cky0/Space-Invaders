package gui.states;

import logic.manager.game.commands.CommandType;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import java.util.HashMap;

/**
 * Classe che rappresenta l'astrazione del campo di gioco.
 * Viene estesa dal singlePlayer e dal Multiplayer state.
 * Contiene una mappa di tasti che associa il tasto premuto al comando da eseguire.
 */
public abstract class GameState extends BasicState {
    private HashMap<Integer, CommandType> keyboardKeys;
    private int life;

    public GameState(){
        initKeyboardKeys();
        life = 3;
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        getAudioplayer().game();
        gameContainer.getInput().clearKeyPressedRecord();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics){
        super.render(gameContainer, stateBasedGame, graphics);
        getDataFont().drawString(85*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,"Lives: " + life, Color.red);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gameContainer.getInput();
        if (input.isKeyDown(Input.KEY_ESCAPE)){
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
        }
    }

    /**
     * Inizializzazione dell'hashmap che associa input tastiera a comando da eseguire.
     */
    private void initKeyboardKeys(){
        keyboardKeys = new HashMap<>();
        keyboardKeys.put(Input.KEY_RIGHT, CommandType.MOVE_RIGHT);
        keyboardKeys.put(Input.KEY_LEFT, CommandType.MOVE_LEFT);
        keyboardKeys.put(Input.KEY_SPACE, CommandType.SHOT);
        keyboardKeys.put(Input.KEY_ESCAPE, CommandType.EXIT);
    }

    public void setLife(int life){
        this.life = life;
    }

    public HashMap<Integer, CommandType> getKeyboardKeys(){
        return keyboardKeys;
    }

    public int getLife() {
        return life;
    }
}
