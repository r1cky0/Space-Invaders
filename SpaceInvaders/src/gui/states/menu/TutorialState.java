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

/**
 * Stato del tutorial.
 */
public class TutorialState extends BasicState implements ComponentListener {
    private Image leftImage;
    private Image rightImage;
    private Image shotImage;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer, stateBasedGame);
        leftImage = new Image(getReaderXmlFile().read("left")).getScaledCopy(24*gameContainer.getWidth()/100,18*gameContainer.getWidth()/100);
        rightImage = new Image(getReaderXmlFile().read("right")).getScaledCopy(24*gameContainer.getWidth()/100,18*gameContainer.getWidth()/100);
        shotImage = new Image(getReaderXmlFile().read("space")).getScaledCopy(24*gameContainer.getWidth()/100,6*gameContainer.getWidth()/100);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer, stateBasedGame, graphics);
        String title = "TUTORIAL";
        String leftString = "Keep pressed to move left";
        String rightString = "Keep pressed to move right";
        String shotString = "Press to shoot";

        getTitleFont().drawString((gameContainer.getWidth() - getTitleFont().getWidth(title))/2f,7*gameContainer.getHeight()/100f, title, Color.white);
        getDataFont().drawString((gameContainer.getWidth())/2f,37*gameContainer.getHeight()/100f, leftString, Color.green);
        getDataFont().drawString((gameContainer.getWidth())/2f,60*gameContainer.getHeight()/100f, rightString, Color.green);
        getDataFont().drawString((gameContainer.getWidth())/2f,85*gameContainer.getHeight()/100f, shotString, Color.green);

        float baseX = 11*gameContainer.getWidth()/100f;
        float baseY = 26*gameContainer.getHeight()/100f;
        float offset = gameContainer.getHeight()/4f;
        leftImage.draw(baseX, baseY);
        rightImage.draw(baseX,baseY + offset);
        shotImage.draw(11*gameContainer.getWidth()/100f,baseY + 2*offset + 60);
        getHomeButton().render(graphics);
    }

    @Override
    public int getID() {
        return IDStates.TUTORIAL_STATE;
    }

}
