package gui.states.menu;

import gui.states.BasicInvaderState;
import gui.states.IDStates;
import logic.environment.manager.file.ReadXmlFile;
import logic.environment.manager.menu.Menu;
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

public class MenuState extends BasicInvaderState implements ComponentListener {

    private GameContainer gameContainer;
    private StateBasedGame stateBasedGame;

    private Image single;
    private Image multi;
    private Image exit;
    private Image settings;
    private Image background;
    private Image ranking;

    private MouseOverArea singleButton;
    private MouseOverArea multiButton;
    private MouseOverArea exitButton;
    private MouseOverArea customizationButton;
    private MouseOverArea rankingButton;

    private UnicodeFont uniFontTitle;
    private String title;

    private Menu menu;

    public MenuState(Menu menu){
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;

        this.background = new Image(ReadXmlFile.read("defaultBackground"));
        title = "SPACE INVADERS";

        single = new Image(ReadXmlFile.read("buttonSinglePlayer")).getScaledCopy(30*gameContainer.getWidth()/100,
                10*gameContainer.getHeight()/100);
        singleButton = new MouseOverArea(gameContainer, single,(gameContainer.getWidth() - single.getWidth())/2,
                26*gameContainer.getHeight()/100,30*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        multi = new Image(ReadXmlFile.read("buttonMultiplayer")).getScaledCopy(30*gameContainer.getWidth()/100,
                10*gameContainer.getHeight()/100);
        multiButton = new MouseOverArea(gameContainer, multi,(gameContainer.getWidth() - multi.getWidth())/2,
                45*gameContainer.getHeight()/100,30*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        settings = new Image(ReadXmlFile.read("buttonSettings")).getScaledCopy(8*gameContainer.getWidth()/100,
                10*gameContainer.getHeight()/100);
        customizationButton = new MouseOverArea(gameContainer, settings,35*gameContainer.getWidth()/100,
                63*gameContainer.getHeight()/100,8*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        exit = new Image(ReadXmlFile.read("buttonExit")).getScaledCopy(15*gameContainer.getWidth()/100,
                10*gameContainer.getHeight()/100);
        exitButton = new MouseOverArea(gameContainer, exit,(gameContainer.getWidth() - exit.getWidth())/2,
                80*gameContainer.getHeight()/100,15*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        ranking = new Image(ReadXmlFile.read("buttonRanking")).getScaledCopy(12*gameContainer.getWidth()/100,
                12*gameContainer.getHeight()/100);
        rankingButton = new MouseOverArea(gameContainer, ranking,55*gameContainer.getWidth()/100,
                63*gameContainer.getHeight()/100,12*gameContainer.getWidth()/100,12*gameContainer.getHeight()/100,
                this);

        uniFontTitle = build(9*gameContainer.getWidth()/100f);
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
            try {
                menu.restartGame();
                stateBasedGame.getState(IDStates.SINGLEPLAYER_STATE).init(gameContainer,stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(IDStates.SINGLEPLAYER_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == multiButton ) {
            try {
                stateBasedGame.getState(IDStates.WAITING_STATE).init(gameContainer,stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(IDStates.WAITING_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == customizationButton) {
            try {
                stateBasedGame.getState(IDStates.CUSTOMIZATION_STATE).init(gameContainer, stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(IDStates.CUSTOMIZATION_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == rankingButton ) {
            try {
                stateBasedGame.getState(IDStates.RANKING_STATE).init(gameContainer, stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(IDStates.RANKING_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if(source == exitButton){
            menu.logOut();
            stateBasedGame.enterState(IDStates.START_STATE, new FadeOutTransition(), new FadeInTransition());
        }
    }

    @Override
    public int getID() {
        return IDStates.MENU_STATE;
    }
}
