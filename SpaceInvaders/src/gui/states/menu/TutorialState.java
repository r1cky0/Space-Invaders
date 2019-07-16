package gui.states.menu;

import gui.states.BasicState;
import gui.states.IDStates;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class TutorialState extends BasicState implements ComponentListener {
    private StateBasedGame stateBasedGame;
    private String title;
    private String leftString;
    private String rightString;
    private String shotString;

    private UnicodeFont uniFontData;
    private UnicodeFont uniFontTitle;

    private Image leftImage;
    private Image rightImage;
    private Image shotImage;
    private Image background;

    private MouseOverArea homeButton;

    public TutorialState(){}


    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;

        background = new Image(readerXmlFile.read("defaultBackground"));

        leftImage = new Image(readerXmlFile.read("left")).getScaledCopy(18*gameContainer.getWidth()/100,
                18*gameContainer.getWidth()/100);

        rightImage = new Image(readerXmlFile.read("right")).getScaledCopy(18*gameContainer.getWidth()/100,
                18*gameContainer.getWidth()/100);

        shotImage = new Image(readerXmlFile.read("spacebar")).getScaledCopy(24*gameContainer.getWidth()/100,
                6*gameContainer.getWidth()/100);

        Image homeImage = new Image(readerXmlFile.read("buttonHome")).getScaledCopy(6 * gameContainer.getWidth() / 100,
                6 * gameContainer.getWidth() / 100);
        homeButton = new MouseOverArea(gameContainer, homeImage,5*gameContainer.getWidth()/100,
                7*gameContainer.getHeight()/100,6*gameContainer.getWidth()/100,6*gameContainer.getHeight()/100,
                this);

        title = "TUTORIAL";
        leftString = "Keep pressed to move left";
        rightString = "Keep pressed to move right";
        shotString = "Press to shot";

        uniFontTitle = build(8*gameContainer.getWidth()/100f);
        uniFontData = build(3*gameContainer.getWidth()/100f);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background, 0, 0);

        homeButton.render(gameContainer,graphics);

        uniFontTitle.drawString((gameContainer.getWidth() - uniFontTitle.getWidth(title))/2f,
                7*gameContainer.getHeight()/100f, title,Color.white);
        leftImage.draw(10*gameContainer.getWidth()/100f,26*gameContainer.getHeight()/100f);
        rightImage.draw(10*gameContainer.getWidth()/100f,26*gameContainer.getHeight()/100f + gameContainer.getHeight()/4f);
        shotImage.draw(10*gameContainer.getWidth()/100f,26*gameContainer.getHeight()/100f + gameContainer.getHeight()/2f);

        uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth(rightString))/1.25f,
                37*gameContainer.getHeight()/100f + gameContainer.getHeight()/4f, rightString, Color.green);

        uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth(leftString))/1.27f,
                37*gameContainer.getHeight()/100f, leftString, Color.green);

        uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth(shotString))/1.5f,
                37*gameContainer.getHeight()/100f + gameContainer.getHeight()/2.4f, shotString, Color.green);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    }

    @Override
    public void componentActivated(AbstractComponent source) {
        if (source == homeButton) {
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
        }
    }

    @Override
    public int getID() {
        return IDStates.TUTORIAL_STATE;
    }
}
