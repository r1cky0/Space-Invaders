package gui.states.multi;

import gui.states.BasicState;
import gui.states.IDStates;
import main.Dimensions;
import logic.manager.game.Commands;
import logic.manager.game.States;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MultiplayerState extends BasicState {
    private UnicodeFont uniFontData;
    private LocalMultiManger localMultiManger;

    //IMAGES
    private Image background;

    public MultiplayerState(LocalMultiManger localMultiManger) {
        this.localMultiManger = localMultiManger;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        background = new Image(readerXmlFile.read("defaultBackground"));
        uniFontData = build(3 * gameContainer.getWidth() / 100f);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.drawImage(background, 0, 0);
        uniFontData.drawString(85*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Lives: " + localMultiManger.getLife(), Color.red);
        uniFontData.drawString(2*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Score: " + localMultiManger.getScore(), Color.white);
        localMultiManger.render();
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        Input input = gameContainer.getInput();
        if(input.isKeyPressed(Input.KEY_SPACE)){
            localMultiManger.execCommand(Input.KEY_SPACE, delta);
        }
        if(input.isKeyDown(Input.KEY_RIGHT)){
            localMultiManger.execCommand(Input.KEY_RIGHT, delta);
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            localMultiManger.execCommand(Input.KEY_LEFT, delta);
        }
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            localMultiManger.exit();
            stateBasedGame.enterState(IDStates.MENU_STATE,new FadeOutTransition(),new FadeInTransition());
        }
        if(localMultiManger.getGameState() == States.GAMEOVER){
            localMultiManger.exit();
            try {
                stateBasedGame.addState(new GameOverSateteMulti(localMultiManger.getScore()));
                stateBasedGame.getState(IDStates.GAMEOVERMULTI_STATE).init(gameContainer,stateBasedGame);
                stateBasedGame.enterState(IDStates.GAMEOVERMULTI_STATE,new FadeOutTransition(),new FadeInTransition());
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getID() {
        return IDStates.MULTIPLAYER_STATE;
    }
}
