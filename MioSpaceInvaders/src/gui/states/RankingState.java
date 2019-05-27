package gui.states;
import logic.environment.Menu;
import logic.environment.Ranking;
import logic.player.Player;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
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

    private Font UIFont1;
    private Font UIFont2;
    private UnicodeFont uniFont;
    private UnicodeFont uniFont2;

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

        goldMedal = new Image("res/images/gold_medal.png");
        silverMedal = new Image("res/images/silver_medal.png");
        bronzeMedal = new Image("res/images/bronze_medal.png");

        menuImage = new Image("res/images/ButtonMenu.png");
        menuButton = new MouseOverArea(gameContainer, menuImage,gameContainer.getWidth()/3,5*gameContainer.getHeight()/7,
                gameContainer.getWidth()/3,gameContainer.getHeight()/10,this);

        title = "TOP 10 RANKING";
        nameString = "nickname";
        highscoreString = "highscore";

        try {
            UIFont1 = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            UIFont1 = UIFont1.deriveFont(Font.BOLD, 40);
            uniFont = new UnicodeFont(UIFont1);
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();

            UIFont2 = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            UIFont2 = UIFont2.deriveFont(Font.BOLD,gameContainer.getWidth()/11f);
            uniFont2 = new UnicodeFont(UIFont2);
            uniFont2.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFont2.addAsciiGlyphs();
            uniFont2.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace();
        }

        menu.createRanking();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background, 0, 0);
        goldMedal.draw(gameContainer.getWidth()/10f,3*gameContainer.getWidth()/10f,0.15f);
        silverMedal.draw(gameContainer.getWidth()/10f,3*gameContainer.getWidth()/10f,0.15f);
        bronzeMedal.draw(gameContainer.getWidth()/10f,3*gameContainer.getWidth()/10f,0.15f);
        menuButton.render(gameContainer,graphics);

        int offset = 0;
        int numPlayer = 0;

        Iterator rankIter = menu.getRanking().getRank().entrySet().iterator();
        while (rankIter.hasNext() && numPlayer<10) {
            Map.Entry pair = (Map.Entry) rankIter.next();
            uniFont.drawString(100, gameContainer.getHeight()/2 - offset, (String) pair.getKey());
            uniFont.drawString(600, gameContainer.getHeight()/2 - offset, Integer.toString((int) pair.getValue()));
            offset += 40;
            numPlayer++;
        }


        uniFont2.drawString(gameContainer.getWidth()/10f, gameContainer.getHeight()/12f, title);
        uniFont.drawString(100, gameContainer.getHeight()/4, nameString,Color.red);
        uniFont.drawString(600, gameContainer.getHeight()/4, highscoreString,Color.red);
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
