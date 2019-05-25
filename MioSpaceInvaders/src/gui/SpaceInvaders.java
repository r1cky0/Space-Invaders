package gui;

import gui.states.*;
import logic.environment.Menu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SpaceInvaders extends StateBasedGame {

    private Menu menu;

    public SpaceInvaders(Menu menu) {
        super("Space Invaders");
        this.menu = menu;
    }

    public void initStatesList(GameContainer gameContainer) {
        this.addState(new StartState(menu));
        this.addState(new MenuState(menu));
        this.addState(new SinglePlayerState(menu));
        this.addState(new GameOverState(menu));
        this.addState(new RankingState(menu));
        this.enterState(0);
    }

    public static void main(String[] args){
        try {
            int width = 1000;
            int height = 800;
            Menu menu = new Menu(width,height);

            AppGameContainer container = new AppGameContainer(new SpaceInvaders(menu));
            container.setTargetFrameRate(100);
            container.setDisplayMode(width,height,false);
            container.setShowFPS(false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
