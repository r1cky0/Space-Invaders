package gui;

import gui.states.GameOverState;
import gui.states.LoginState;
import gui.states.MenuState;
import gui.states.SinglePlayerState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SpaceInvaders extends StateBasedGame {

    public SpaceInvaders() {
        super("Space Invaders");
    }

    public void initStatesList(GameContainer gameContainer) {
        this.addState(new LoginState());
        this.addState(new MenuState());
        this.addState(new SinglePlayerState());
        this.addState(new GameOverState());
        this.enterState(0);
    }

    public static void main(String[] args){

    }
}
