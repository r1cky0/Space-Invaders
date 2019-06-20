package main;

import gui.states.*;
import logic.environment.manager.menu.Menu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import java.awt.*;
import java.awt.Dimension;


public class SpaceInvaders extends StateBasedGame {

    private Menu menu;
    public static double SCALE_X;
    public static double SCALE_Y;

    public SpaceInvaders(Menu menu) {
        super("Space Invaders");
        this.menu = menu;
    }

    public void initStatesList(GameContainer gameContainer) {
        this.addState(new StartState(menu));
        this.addState(new MenuState(menu));
        this.addState(new SinglePlayerState(menu));
        this.addState(new GameOverStateSingle(menu));
        this.addState(new RankingState(menu));
        this.addState(new NewHighscoreState(menu));
        this.addState(new CustomizationState(menu));
        this.addState(new WaitingState(menu));
        this.enterState(0);
    }

    public static void main(String[] args) {
        try{
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            SCALE_X = screenSize.getWidth()/190;
            SCALE_Y = screenSize.getHeight()/125;
            Menu menu = new Menu();
            AppGameContainer container = new AppGameContainer(new SpaceInvaders(menu));
            container.setDisplayMode((int) (main.Dimension.MAX_WIDTH*SCALE_X),(int) (main.Dimension.MAX_HEIGHT*SCALE_Y),
                    false);
            container.setSmoothDeltas(false);
            container.setShowFPS(false);
            container.setVSync(false);
            container.start();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
}
