package main;

import gui.states.*;
import logic.environment.manager.menu.Menu;
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
        this.addState(new NewHighscoreState(menu));
        this.addState(new CustomizationState(menu));
        this.addState(new WaitingState(menu));
        this.addState(new MultiplayerState(menu));
        this.enterState(0);
    }

    public static void main(String[] args) {

        try {
            int width = 1000;
            int height = 800;
            Menu menu = new Menu(width, height);
            AppGameContainer container = new AppGameContainer(new SpaceInvaders(menu));
            container.setDisplayMode(width, height, false);
            container.setSmoothDeltas(false);
            container.setShowFPS(false);
            container.setVSync(false);
            container.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
