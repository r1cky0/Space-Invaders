package main;

import gui.states.IDStates;
import gui.states.menu.*;
import gui.states.multi.WaitingState;
import gui.states.single.GameOverStateSingle;
import gui.states.single.NewHighscoreState;
import gui.states.single.SinglePlayerState;
import logic.manager.menu.Menu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;


public class SpaceInvaders extends StateBasedGame {

    private Menu menu;
    public static float SCALE_X;
    public static float SCALE_Y;

    public SpaceInvaders(Menu menu) {
        super("Space Invaders");
        this.menu = menu;
    }

    public void initStatesList(GameContainer gameContainer) {
        addState(new StartState(menu));
        addState(new MenuState(menu));
        addState(new SinglePlayerState(menu));
        addState(new WaitingState());
        addState(new CustomizationState(menu));
        addState(new RankingState(menu));
        addState(new GameOverStateSingle(menu));
        addState(new NewHighscoreState(menu));
        addState(new TutorialState());
        enterState(IDStates.START_STATE);
    }

    public static void main(String[] args) {
        try{
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            SCALE_X = (float) screenSize.getWidth()/175;
            SCALE_Y = (float) screenSize.getHeight()/120;
            Menu menu = new Menu();
            AppGameContainer container = new AppGameContainer(new SpaceInvaders(menu));
            container.setDisplayMode((int) (Dimensions.MAX_WIDTH*SCALE_X),(int) (Dimensions.MAX_HEIGHT*SCALE_Y),
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
