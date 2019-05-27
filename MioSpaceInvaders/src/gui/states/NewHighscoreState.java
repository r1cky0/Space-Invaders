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


public class NewHighscoreState extends BasicGameState implements ComponentListener {

    private Image cupImage;
    private Image background;

    private GameContainer container;
    private StateBasedGame stateBasedGame;

    private MouseOverArea newGameButton;
    private Image newGame;
    private MouseOverArea menuButton;
    private Image menuImage;
    private Font UIFont1;
    private UnicodeFont uniFont;
    private String highscore;
    private String highscoreValue;

    private Menu menu;

    public NewHighscoreState(Menu menu) {
        this.menu = menu;
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container = gameContainer;
        this.stateBasedGame = stateBasedGame;
        this.background = new Image("res/images/BackgroundSpace.png");

        this.highscore = "NEW HIGHSCORE! ";
        if(menu.getPlayer() != null) {
            highscoreValue = "SCORE: " + Integer.toString(menu.getPlayer().getHighScore());
        }
        newGame = new Image("res/images/ButtonNewGame.png").getScaledCopy(gameContainer.getWidth()/3,
                gameContainer.getHeight()/10);
        newGameButton = new MouseOverArea(gameContainer, newGame,gameContainer.getWidth()/3,5*gameContainer.getHeight()/7,
                gameContainer.getWidth()/3,gameContainer.getHeight()/10,this);

        menuImage = new Image("res/images/ButtonMenu.png");
        menuButton = new MouseOverArea(gameContainer, menuImage,gameContainer.getWidth()/3,6*gameContainer.getHeight()/7,
                gameContainer.getWidth()/3,gameContainer.getHeight()/10,this);

        cupImage = new Image("res/images/Cup.png").getScaledCopy(0.65f);

        try {
            UIFont1 = Font.createFont(java.awt.Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            UIFont1 = UIFont1.deriveFont(java.awt.Font.BOLD, 60);
            uniFont = new UnicodeFont(UIFont1);
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);
        cupImage.draw(5*container.getWidth()/15f, 7*container.getHeight()/25f);
        newGameButton.render(gameContainer, graphics);
        menuButton.render(gameContainer,graphics);
        uniFont.drawString(5*container.getWidth()/20f, 4*container.getHeight()/20f,highscoreValue);
        uniFont.drawString(5*container.getWidth()/20f, container.getHeight()/20f,highscore);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return 6;
    }

    @Override
    public void componentActivated(AbstractComponent source) {
        if (source == newGameButton) {
            try {
                menu.restartGame();
                stateBasedGame.getState(2).init(container, stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(2, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == menuButton) {
            try {
                menu.restartGame();
                stateBasedGame.getState(2).init(container, stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
    }
}
