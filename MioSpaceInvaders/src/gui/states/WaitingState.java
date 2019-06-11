package gui.states;

import logic.environment.manager.file.ReadXmlFile;
import logic.environment.manager.menu.Menu;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.awt.Font;


public class WaitingState extends BasicInvaderState implements ComponentListener {

    private StateBasedGame stateBasedGame;
    private GameContainer gameContainer;
    private Menu menu;
    private String title;
    private Image background;
    private UnicodeFont uniFontTitle;


    public WaitingState(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;
        background = new Image(ReadXmlFile.read(0, "background"));

        uniFontTitle = Build(5 * gameContainer.getWidth() / 100f);
        title = "WAITING FOR OTHER PLAYERS...";
        Font font = new java.awt.Font("Verdana", java.awt.Font.BOLD, 3*gameContainer.getWidth()/100);
        TrueTypeFont ttf = new TrueTypeFont(font, true);


    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background, 0, 0);
        uniFontTitle.drawString((gameContainer.getWidth() - uniFontTitle.getWidth(title)) / 2f,
                7 * gameContainer.getHeight() / 100f, title);

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();

        if(input.isKeyDown(Input.KEY_ESCAPE)){
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
    }


    @Override
    public void componentActivated(AbstractComponent abstractComponent) {

    }

    @Override
    public int getID() {
        return 8;
    }

}
