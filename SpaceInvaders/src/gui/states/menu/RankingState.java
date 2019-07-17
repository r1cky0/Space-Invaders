package gui.states.menu;
import gui.states.BasicState;
import gui.states.IDStates;
import logic.manager.menu.Menu;
import logic.manager.menu.Ranking;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.StateBasedGame;

import java.util.*;

/**
 * Stato della classifica locale.
 */
public class RankingState extends BasicState implements ComponentListener {
    private Ranking ranking;

    private Image goldMedal;
    private Image silverMedal;
    private Image bronzeMedal;

    public RankingState(Menu menu){
        this.ranking = menu.getRanking();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer, stateBasedGame);
        int medalSide = 6 * gameContainer.getWidth() / 100;
        goldMedal = new Image(getReaderXmlFile().read("medalGold")).getScaledCopy(medalSide, medalSide);
        silverMedal = new Image(getReaderXmlFile().read("medalSilver")).getScaledCopy(medalSide, medalSide);
        bronzeMedal = new Image(getReaderXmlFile().read("medalBronze")).getScaledCopy(medalSide, medalSide);
        ranking.createRanking();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer, stateBasedGame, graphics);
        String title = "TOP 10 RANKING";
        String nameString = "nickname";
        String highscoreString = "highscore";
        getTitleFont().drawString((gameContainer.getWidth() - getTitleFont().getWidth(title))/2f,7*gameContainer.getHeight()/100f, title, Color.white);
        getDataFont().drawString((gameContainer.getWidth() - getDataFont().getWidth(nameString))/4f,20*gameContainer.getHeight()/100f, nameString, Color.green);
        getDataFont().drawString(3*(gameContainer.getWidth() - getDataFont().getWidth(highscoreString))/4f,20*gameContainer.getHeight()/100f, highscoreString, Color.green);
        getHomeButton().render(graphics);
        rankingRender();
    }

    /**
     * Funzione che disegna la classifica.
     */
    private void rankingRender(){
        int offset = 0;
        int numPlayer = 0;
        Iterator rankIter = ranking.getRank().entrySet().iterator();
        float baseY = 28*getGameContainer().getHeight()/100f;
        while (rankIter.hasNext() && numPlayer<10) {
            Map.Entry pair = (Map.Entry) rankIter.next();
            getDataFont().drawString(getGameContainer().getWidth()/4f,baseY + offset, (String)pair.getKey());
            getDataFont().drawString(65*getGameContainer().getWidth()/100f,baseY + offset, Integer.toString((int)pair.getValue()));
            if(numPlayer>2){
                offset += 6*getGameContainer().getHeight()/100;
            }else {
                offset += 9*getGameContainer().getHeight()/100;
            }
            numPlayer++;
        }
        float baseX = 12*getGameContainer().getWidth()/100f;
        baseY = 26*getGameContainer().getHeight()/100f;
        goldMedal.draw(baseX, baseY);
        silverMedal.draw(baseX,baseY + goldMedal.getHeight() + 10);
        bronzeMedal.draw(baseX,baseY + 2*(goldMedal.getHeight() + 10));
    }

    @Override
    public int getID() {
        return IDStates.RANKING_STATE;
    }
}
