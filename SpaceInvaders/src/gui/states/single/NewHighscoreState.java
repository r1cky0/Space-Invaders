package gui.states.single;

import gui.states.BasicState;
import gui.states.GameOverState;
import gui.states.IDStates;
import gui.states.buttons.Button;
import logic.manager.menu.Menu;
import logic.sprite.Coordinate;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class NewHighscoreState extends BasicState implements ComponentListener {
    private Menu menu;
    private Image cupImage;
    private Button newGameButton;

    public NewHighscoreState(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer, stateBasedGame);
        Image newGame = new Image(getReaderXmlFile().read("buttonNewGame")).getScaledCopy(30*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100);
        Coordinate posNewGame = new Coordinate((gameContainer.getWidth() - newGame.getWidth())/2,80*gameContainer.getHeight()/100);
        newGameButton = new Button(gameContainer, newGame, posNewGame, IDStates.SINGLEPLAYER_STATE, this);
        cupImage = new Image(getReaderXmlFile().read("buttonRanking")).getScaledCopy(35*gameContainer.getWidth()/100,35*gameContainer.getHeight()/100);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
        getAudioplayer().gameOver();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer, stateBasedGame, graphics);
        String title = "NEW HIGHSCORE!";
        String highscore = Integer.toString(menu.getPlayer().getHighScore());
        getTitleFont().drawString((gameContainer.getWidth() - getTitleFont().getWidth(title))/2f,7*gameContainer.getHeight()/100f, title);
        getTitleFont().drawString((gameContainer.getWidth() - getTitleFont().getWidth(highscore))/2f,20*gameContainer.getHeight()/100f, highscore);
        cupImage.draw((gameContainer.getWidth() - cupImage.getWidth())/2f,(gameContainer.getHeight() - cupImage.getHeight())/2f);

        newGameButton.render(graphics);
        getHomeButton().render(graphics);
    }

    @Override
    public void componentActivated(AbstractComponent source) {
        super.componentActivated(source);
        if (source == newGameButton.getMouseOverArea()) {
            getStateBasedGame().enterState(newGameButton.getIdState(), new FadeOutTransition(), new FadeInTransition());
        }
    }

    @Override
    public int getID() {
        return IDStates.NEWHIGHSCORE_STATE;
    }
}
