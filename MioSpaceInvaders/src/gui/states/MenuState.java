package gui.states;

import logic.environment.Menu;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
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

public class MenuState extends BasicGameState implements ComponentListener {
    private Menu menu;

    private GameContainer gameContainer;
    private Image single;
    private Image multi;
    private Image exit;
    private Image settings;
    private Image background;
    private Image ranking;
    private String title;

    private StateBasedGame stateBasedGame;
    private MouseOverArea singleButton;
    private MouseOverArea multiButton;
    private MouseOverArea exitButton;
    private MouseOverArea settingsButton;
    private MouseOverArea rankingButton;

    private Font fontTitle;
    private UnicodeFont uniFontTitle;

    public MenuState(Menu menu){
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;
        this.background = new Image("res/images/BackgroundSpace.png");

        title = "SPACE INVADERS";

        single = new Image("res/images/ButtonSinglePlayer.png").getScaledCopy(30*gameContainer.getWidth()/100,
                10*gameContainer.getHeight()/100);
        singleButton = new MouseOverArea(gameContainer, single,(gameContainer.getWidth() - single.getWidth())/2,
                26*gameContainer.getHeight()/100,30*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        multi = new Image("res/images/ButtonMultiplayer.png").getScaledCopy(30*gameContainer.getWidth()/100,
                10*gameContainer.getHeight()/100);
        multiButton = new MouseOverArea(gameContainer, multi,(gameContainer.getWidth() - multi.getWidth())/2,
                45*gameContainer.getHeight()/100,30*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        settings = new Image("res/images/Settings.png").getScaledCopy(8*gameContainer.getWidth()/100,
                10*gameContainer.getHeight()/100);
        settingsButton = new MouseOverArea(gameContainer, settings,35*gameContainer.getWidth()/100,
                63*gameContainer.getHeight()/100,8*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        exit = new Image("res/images/ButtonExit.png").getScaledCopy(15*gameContainer.getWidth()/100,
                10*gameContainer.getHeight()/100);
        exitButton = new MouseOverArea(gameContainer, exit,(gameContainer.getWidth() - exit.getWidth())/2,
                80*gameContainer.getHeight()/100,15*gameContainer.getWidth()/100,10*gameContainer.getHeight()/100,
                this);

        ranking = new Image("res/images/Cup.png").getScaledCopy(12*gameContainer.getWidth()/100,
                12*gameContainer.getHeight()/100);
        rankingButton = new MouseOverArea(gameContainer, ranking,55*gameContainer.getWidth()/100,
                63*gameContainer.getHeight()/100,12*gameContainer.getWidth()/100,12*gameContainer.getHeight()/100,
                this);

        try{
            fontTitle = Font.createFont(Font.TRUETYPE_FONT,ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            fontTitle = fontTitle.deriveFont(Font.BOLD,9*gameContainer.getWidth()/100f);
            uniFontTitle = new UnicodeFont(fontTitle);
            uniFontTitle.addAsciiGlyphs();
            uniFontTitle.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFontTitle.addAsciiGlyphs();
            uniFontTitle.loadGlyphs();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);
        singleButton.render(gameContainer, graphics);
        multiButton.render(gameContainer, graphics);
        settingsButton.render(gameContainer,graphics);
        exitButton.render(gameContainer,graphics);
        rankingButton.render(gameContainer,graphics);
        uniFontTitle.drawString((gameContainer.getWidth() - uniFontTitle.getWidth(title))/2f,7*gameContainer.getHeight()/100f,
                title, Color.white);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
    }

    /**
     * Funzione che setta i gestori degli eventi di click sui bottoni
     * @param source Il tasto di cui dobbiamo settare il comportamento
     */
    public void componentActivated(AbstractComponent source) {
        if (source == singleButton ) {
            try {
                menu.restartGame();
                stateBasedGame.getState(2).init(gameContainer,stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(2, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == multiButton ) {
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == settingsButton) {
            try {
                stateBasedGame.getState(7).init(gameContainer, stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(7, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == rankingButton ) {
            try {
                stateBasedGame.getState(4).init(gameContainer, stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(4, new FadeOutTransition(), new FadeInTransition());
        }
        if(source == exitButton){
            menu.logOut();
            System.exit(0);
        }
    }

    @Override
    public int getID() {
        return 1;
    }
}