package gui.states;
import logic.environment.manager.file.ReadXmlFile;
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

import java.util.*;

public class RankingState extends BasicInvaderState implements ComponentListener {

    private StateBasedGame stateBasedGame;
    private GameContainer gameContainer;

    private String title;
    private String nameString;
    private String highscoreString;

    private UnicodeFont uniFontData;
    private UnicodeFont uniFontTitle;

    private Image goldMedal;
    private Image silverMedal;
    private Image bronzeMedal;
    private Image background;
    private Image homeImage;

    private MouseOverArea homeButton;

    private Menu menu;

    public RankingState(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;

        background = new Image(ReadXmlFile.read("defaultBackground"));

        goldMedal = new Image(ReadXmlFile.read("medalGold")).getScaledCopy(6*gameContainer.getWidth()/100,
                6*gameContainer.getWidth()/100);
        silverMedal = new Image(ReadXmlFile.read("medalSilver")).getScaledCopy(6*gameContainer.getWidth()/100,
                6*gameContainer.getWidth()/100);
        bronzeMedal = new Image(ReadXmlFile.read("medalBronze")).getScaledCopy(6*gameContainer.getWidth()/100,
                6*gameContainer.getWidth()/100);

        homeImage = new Image(ReadXmlFile.read("buttonHome")).getScaledCopy(6*gameContainer.getWidth()/100,
                6*gameContainer.getWidth()/100);
        homeButton = new MouseOverArea(gameContainer, homeImage,5*gameContainer.getWidth()/100,7*gameContainer.getHeight()/100,
                6*gameContainer.getWidth()/100,6*gameContainer.getHeight()/100,this);

        title = "TOP 10 RANKING";
        nameString = "nickname";
        highscoreString = "highscore";

        uniFontTitle = build(8*gameContainer.getWidth()/100f);
        uniFontData = build(4*gameContainer.getWidth()/100f);

        menu.createRanking();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background, 0, 0);

        int offset = 0;
        int numPlayer = 0;
        Iterator rankIter = menu.getRanking().getRank().entrySet().iterator();
        while (rankIter.hasNext() && numPlayer<10) {
            Map.Entry pair = (Map.Entry) rankIter.next();
            uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth(nameString))/4f,
                    28*gameContainer.getHeight()/100f + offset, (String) pair.getKey());
            uniFontData.drawString(65*gameContainer.getWidth()/100f, 28*gameContainer.getHeight()/100f + offset,
                    Integer.toString((int) pair.getValue()));
            if(numPlayer>2){
                offset += gameContainer.getHeight()/15;
            }else {
                offset += gameContainer.getHeight()/12;
            }
            numPlayer++;
        }

        goldMedal.draw(10*gameContainer.getWidth()/100f,26*gameContainer.getHeight()/100f);
        silverMedal.draw(10*gameContainer.getWidth()/100f,26*gameContainer.getHeight()/100f + gameContainer.getHeight()/12f);
        bronzeMedal.draw(10*gameContainer.getWidth()/100f,26*gameContainer.getHeight()/100f + gameContainer.getHeight()/6f);

        homeButton.render(gameContainer,graphics);

        uniFontTitle.drawString((gameContainer.getWidth() - uniFontTitle.getWidth(title))/2f,
                7*gameContainer.getHeight()/100f, title,Color.white);
        uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth(nameString))/4f,
                20*gameContainer.getHeight()/100f, nameString, Color.green);
        uniFontData.drawString(3*(gameContainer.getWidth() - uniFontData.getWidth(highscoreString))/4f,
                20*gameContainer.getHeight()/100f, highscoreString, Color.green);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    /**
     * Funzione che setta il gestore dell' evento di click sul bottone per ritorno nella home
     * @param source Il tasto di cui dobbiamo settare il comportamento
     */
    public void componentActivated(AbstractComponent source) {
        if (source == homeButton) {
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
    }

    @Override
    public int getID() {
        return 4;
    }
}
