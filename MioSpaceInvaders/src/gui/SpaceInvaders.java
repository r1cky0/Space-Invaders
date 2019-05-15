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
        this.addState(new LoginState(menu));
        this.addState(new AddAccountState(menu));
        this.enterState(0); //per partire dalla schermata con login o add account decommentare questo e commentare sotto
        //this.enterState(2);  //per passare direttamente al gioco
    }

    public static void main(String[] args){
        try {
            int width = 1000;
            int height = 800;
            AppGameContainer container = new AppGameContainer(new SpaceInvaders(new Menu(width,height)));
            container.setTargetFrameRate(100);
            //container.setFullscreen(true);
            container.setDisplayMode(width,height,false);
            container.setShowFPS(false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
