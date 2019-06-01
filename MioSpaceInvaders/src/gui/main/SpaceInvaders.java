package gui.main;

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
            int width = 1280;
            int height = 720;
            Menu menu = new Menu(width, height);
           /* DisplayMode[] modes = Display.getAvailableDisplayModes();
            for(int i =0;i<modes.length;i++){
                DisplayMode current = modes[i];
                System.err.println(current.getWidth() + "x "+ current.getHeight()+ "y" +current.getBitsPerPixel()+ " "+current.getFrequency()+"Hz");
            }*/

            AppGameContainer container = new AppGameContainer(new SpaceInvaders(menu));
            container.setDisplayMode(width, height, false);
            container.setSmoothDeltas(false);
            container.setShowFPS(false);
            container.setVSync(false);
            container.start();

        } catch (SlickException e) {
            e.printStackTrace();
        /*} catch (LWJGLException e) {
            e.printStackTrace();*/
        }
    }
}
