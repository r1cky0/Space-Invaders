package gui.states.menu;

import gui.states.BasicState;
import gui.states.IDStates;
import logic.manager.menu.Menu;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MenuState extends BasicState implements ComponentListener {

    private GameContainer gameContainer;
    private StateBasedGame stateBasedGame;

    private MouseOverArea singleButton;
    private MouseOverArea multiButton;
    private MouseOverArea exitButton;
    private MouseOverArea customizationButton;
    private MouseOverArea rankingButton;

    private UnicodeFont uniFontTitle;
    private String title;
    private Image background;

    private Menu menu;

    public MenuState(Menu menu){
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;

        background = new Image(readerXmlFile.read("defaultBackground"));
        title = "SPACE INVADERS";

        Image single = new Image(readerXmlFile.read("buttonSinglePlayer")).getScaledCopy(30 * gameContainer.getWidth() / 100,
                10 * gameContainer.getHeight() / 100);
        singleButton = new MouseOverArea(gameContainer, single,(gameContainer.getWidth() - single.getWidth())/2,
                26*gameContainer.getHeight()/100,30*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        Image multi = new Image(readerXmlFile.read("buttonMultiplayer")).getScaledCopy(30 * gameContainer.getWidth() / 100,
                10 * gameContainer.getHeight() / 100);
        multiButton = new MouseOverArea(gameContainer, multi,(gameContainer.getWidth() - multi.getWidth())/2,
                45*gameContainer.getHeight()/100,30*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        Image settings = new Image(readerXmlFile.read("buttonSettings")).getScaledCopy(8 * gameContainer.getWidth() / 100,
                10 * gameContainer.getHeight() / 100);
        customizationButton = new MouseOverArea(gameContainer, settings,35*gameContainer.getWidth()/100,
                63*gameContainer.getHeight()/100,8*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        Image exit = new Image(readerXmlFile.read("buttonExit")).getScaledCopy(15 * gameContainer.getWidth() / 100,
                10 * gameContainer.getHeight() / 100);
        exitButton = new MouseOverArea(gameContainer, exit,(gameContainer.getWidth() - exit.getWidth())/2,
                80*gameContainer.getHeight()/100,15*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        Image ranking = new Image(readerXmlFile.read("buttonRanking")).getScaledCopy(12 * gameContainer.getWidth() / 100,
                12 * gameContainer.getHeight() / 100);
        rankingButton = new MouseOverArea(gameContainer, ranking,55*gameContainer.getWidth()/100,
                63*gameContainer.getHeight()/100,12*gameContainer.getWidth()/100,12*gameContainer.getHeight()/100,
                this);

        uniFontTitle = build(9*gameContainer.getWidth()/100f);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException{
        menu.saveToFile();
        audioplayer.menu();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.drawImage(background,0,0);
        singleButton.render(gameContainer, graphics);
        multiButton.render(gameContainer, graphics);
        customizationButton.render(gameContainer,graphics);
        exitButton.render(gameContainer,graphics);
        rankingButton.render(gameContainer,graphics);
        uniFontTitle.drawString((gameContainer.getWidth() - uniFontTitle.getWidth(title))/2f,
                7*gameContainer.getHeight()/100f, title, Color.white);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
    }

    /**
     * Funzione che setta i gestori degli eventi di click sui bottoni
     * @param source Il tasto di cui dobbiamo settare il comportamento
     */
    public void componentActivated(AbstractComponent source) {
        if (source == singleButton ) {
            stateBasedGame.enterState(IDStates.SINGLEPLAYER_STATE, new FadeOutTransition(), new FadeInTransition());
            audioplayer.game();
        }
        if (source == multiButton ) {
            stateBasedGame.enterState(IDStates.WAITING_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == customizationButton) {
            stateBasedGame.enterState(IDStates.CUSTOMIZATION_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == rankingButton ) {
            stateBasedGame.enterState(IDStates.RANKING_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if(source == exitButton){
            stateBasedGame.enterState(IDStates.START_STATE, new FadeOutTransition(), new FadeInTransition());
        }
    }

    @Override
    public int getID() {
        return IDStates.MENU_STATE;
    }
}
