package gui.states.main;

import gui.states.*;
import logic.environment.Menu;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;

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
        this.addState(new SettingsState(menu));
        this.enterState(0);
    }

    public static void main(String[] args) {

        try {
            DisplayMode bestMode = DisplayModeManager.getBiggestWithRatio(3, 2);
            Menu menu = new Menu(bestMode.getWidth(), bestMode.getHeight());

            AppGameContainer container = new AppGameContainer(new SpaceInvaders(menu));
            container.setDisplayMode(bestMode.getWidth(), bestMode.getHeight(), false);
            container.setSmoothDeltas(false);
            container.setTargetFrameRate(125);
            container.setShowFPS(false);
            container.setVSync(false);
            container.start();

        } catch (SlickException | LWJGLException e) {
            e.printStackTrace();
        }
    }
}
