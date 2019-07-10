package gui.states.single;

import gui.states.BasicInvaderState;
import gui.states.IDStates;
import logic.environment.manager.menu.Menu;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class NewHighscoreState extends BasicInvaderState implements ComponentListener {

    private GameContainer gameContainer;
    private StateBasedGame stateBasedGame;

    private Image cupImage;
    private Image background;
    private Image newGame;
    private Image homeImage;

    private MouseOverArea newGameButton;
    private MouseOverArea homeButton;

    private UnicodeFont uniFontTitle;
    private String title;
    private String highscore;

    private Menu menu;

    public NewHighscoreState(Menu menu) {
        this.menu = menu;
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;

        background = new Image(readerXmlFile.read("defaultBackground"));
        title = "NEW HIGHSCORE:";

        newGame = new Image(readerXmlFile.read("buttonNewGame")).getScaledCopy(30*gameContainer.getWidth()/100,
                10*gameContainer.getHeight()/100);
        newGameButton = new MouseOverArea(gameContainer, newGame,(gameContainer.getWidth() - newGame.getWidth())/2,
                80*gameContainer.getHeight()/100,30*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        homeImage = new Image(readerXmlFile.read("buttonHome")).getScaledCopy(6*gameContainer.getWidth()/100,
                6*gameContainer.getWidth()/100);
        homeButton = new MouseOverArea(gameContainer, homeImage,5*gameContainer.getWidth()/100,
                7*gameContainer.getHeight()/100,6*gameContainer.getWidth()/100,6*gameContainer.getHeight()/100,
                this);

        cupImage = new Image(readerXmlFile.read("buttonRanking")).getScaledCopy(40*gameContainer.getWidth()/100,
                40*gameContainer.getHeight()/100);

        uniFontTitle = build(8*gameContainer.getWidth()/100f);
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.drawImage(background,0,0);

        highscore = Integer.toString(menu.getPlayer().getHighScore());
        cupImage.draw((gameContainer.getWidth() - cupImage.getWidth())/2f,
                (gameContainer.getHeight() - cupImage.getHeight())/2f);

        newGameButton.render(gameContainer, graphics);
        homeButton.render(gameContainer,graphics);

        uniFontTitle.drawString((gameContainer.getWidth() - uniFontTitle.getWidth(title))/2f,
                7*gameContainer.getHeight()/100f, title);
        uniFontTitle.drawString((gameContainer.getWidth() - uniFontTitle.getWidth(highscore))/2f,
                20*gameContainer.getHeight()/100f, highscore);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
    }

    @Override
    public void componentActivated(AbstractComponent source) {
        if (source == newGameButton) {
            try {
                menu.restartGame();
                stateBasedGame.getState(IDStates.SINGLEPLAYER_STATE).init(gameContainer, stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(IDStates.SINGLEPLAYER_STATE, new FadeOutTransition(), new FadeInTransition());
            audioplayer.game();
        }
        if (source == homeButton) {
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
            audioplayer.menu();
        }
    }

    @Override
    public int getID() {
        return IDStates.NEWHIGHSCORE_STATE;
    }
}
