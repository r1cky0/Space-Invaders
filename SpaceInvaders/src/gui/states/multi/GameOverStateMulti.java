package gui.states.multi;

import gui.states.BasicInvaderState;
import gui.states.IDStates;
import logic.environment.manager.file.FileModifier;
import logic.environment.manager.file.ReadXmlFile;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameOverStateMulti extends BasicInvaderState implements ComponentListener {

    private GameContainer gameContainer;
    private StateBasedGame stateBasedGame;

    private Image gameOver;
    private Image homeImage;
    private MouseOverArea homeButton;

    private UnicodeFont uniFontScore;
    private String score;
    private ReadXmlFile readXmlFile;

    public GameOverStateMulti(String score) {
        this.score = score;
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;
        this.readXmlFile = new ReadXmlFile();

        homeImage = new Image(readXmlFile.read("buttonHome")).getScaledCopy(6*gameContainer.getWidth()/100,
                6*gameContainer.getWidth()/100);
        homeButton = new MouseOverArea(gameContainer, homeImage,(gameContainer.getWidth() - homeImage.getWidth())/2,
                80 * gameContainer.getHeight()/100,6*gameContainer.getWidth()/100,6*gameContainer.getHeight()/100,
                this);

        gameOver = new Image(readXmlFile.read("gameoverBackground"));

        uniFontScore = build(9 * gameContainer.getWidth() / 100f);
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics){
        String title = "SCORE: " + score;
        gameOver.draw((gameContainer.getWidth() - gameOver.getWidth())/2f,
                (gameContainer.getHeight() - gameOver.getHeight())/2f);
        homeButton.render(gameContainer, graphics);

        uniFontScore.drawString((this.gameContainer.getWidth() - uniFontScore.getWidth(title))/2f,
                7*this.gameContainer.getHeight()/100f, title);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i){
    }

    /**
     * Funzione che setta i gestori degli eventi di click sui bottoni
     *
     * @param source Il tasto di cui dobbiamo settare il comportamento
     */
    @Override
    public void componentActivated(AbstractComponent source) {
        if (source == homeButton) {
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
            audioplayer.menu();
        }
    }

    @Override
    public int getID() {
        return IDStates.GAMEOVERMULTI_STATE;
    }

}