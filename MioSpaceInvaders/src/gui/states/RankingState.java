package gui.states;
import logic.environment.Menu;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.Font;
import java.util.*;

public class RankingState extends BasicGameState implements ComponentListener {

    private StateBasedGame stateBasedGame;
    private GameContainer gameContainer;

    private String title;
    private String nameString;
    private String highscoreString;

    private Font fontData;
    private Font fontTitle;
    private UnicodeFont uniFontData;
    private UnicodeFont uniFontTitle;

    private Image goldMedal;
    private Image silverMedal;
    private Image bronzeMedal;
    private Image background;
    private Image menuImage;
    private MouseOverArea menuButton;

    private Menu menu;

    public RankingState(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;
        background = new Image("res/images/BackgroundSpace.png");

        goldMedal = new Image("res/images/gold_medal.png").getScaledCopy(gameContainer.getWidth()/15,
                gameContainer.getHeight()/15);
        silverMedal = new Image("res/images/silver_medal.png").getScaledCopy(gameContainer.getWidth()/15,
                gameContainer.getHeight()/15);
        bronzeMedal = new Image("res/images/bronze_medal.png").getScaledCopy(gameContainer.getWidth()/15,
                gameContainer.getHeight()/15);

        menuImage = new Image("res/images/ButtonMenu.png");
        menuButton = new MouseOverArea(gameContainer, menuImage,gameContainer.getWidth()/3,88*gameContainer.getHeight()/100,
                gameContainer.getWidth()/3,gameContainer.getHeight()/10,this);

        title = "TOP 10 RANKING";
        nameString = "nickname";
        highscoreString = "highscore";

        try {
            fontData = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            fontData = fontData.deriveFont(Font.BOLD, 40);
            uniFontData = new UnicodeFont(fontData);
            uniFontData.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFontData.addAsciiGlyphs();
            uniFontData.loadGlyphs();

            fontTitle = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            fontTitle = fontTitle.deriveFont(Font.BOLD,60);
            uniFontTitle = new UnicodeFont(fontTitle);
            uniFontTitle.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFontTitle.addAsciiGlyphs();
            uniFontTitle.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth(nameString))/4,
                    28*gameContainer.getHeight()/100 + offset, (String) pair.getKey());
            uniFontData.drawString(2*gameContainer.getWidth()/3, 28*gameContainer.getHeight()/100 + offset,
                    Integer.toString((int) pair.getValue()));

            if(numPlayer>2){
                offset += 45;
            }else {
                offset += 63;
            }
            numPlayer++;
        }

        goldMedal.draw(gameContainer.getWidth()/10,26*gameContainer.getHeight()/100);
        silverMedal.draw(gameContainer.getWidth()/10,26*gameContainer.getHeight()/100 + 65);
        bronzeMedal.draw(gameContainer.getWidth()/10,26*gameContainer.getHeight()/100 + 130);

        menuButton.render(gameContainer,graphics);

        uniFontTitle.drawString((gameContainer.getWidth()-uniFontTitle.getWidth(title))/2,
                gameContainer.getHeight()/14, title,Color.white);
        uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth(nameString))/4,
                gameContainer.getHeight()/5, nameString, Color.green);
        uniFontData.drawString(3*(gameContainer.getWidth() - uniFontData.getWidth(highscoreString))/4,
                gameContainer.getHeight()/5, highscoreString, Color.green);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return 4;
    }

    public void componentActivated(AbstractComponent source) {
        if (source == menuButton) {
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
    }
}
