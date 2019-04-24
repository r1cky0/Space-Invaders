package main;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import states.GameOverState;
import states.MenuState;
import states.SinglePlayerState;

public class InvadersAStati extends StateBasedGame {

    public InvadersAStati() {
        super("Space Invaders");
    }

    public void initStatesList(GameContainer gameContainer) {
        this.addState(new MenuState());
        this.addState(new SinglePlayerState());
        this.addState(new GameOverState());
        this.enterState(0);
    }

    public static void main(String[] argv) {

        try {
            AppGameContainer container = new AppGameContainer(new InvadersAStati());
            container.setTargetFrameRate(100);
            //container.setFullscreen(true);
            container.setDisplayMode(1000,800,false);
            container.setShowFPS(false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
