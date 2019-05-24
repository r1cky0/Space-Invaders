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
    public MenuState(Menu menu){
            this.menu = menu;
    }

    private GameContainer container;
    private Image single;
    private Image multi;
    private Image exit;
    private Image settings;
    private Image background;
    private Image ranking;


    private StateBasedGame stateBasedGame;
    private MouseOverArea singleButton;
    private MouseOverArea multiButton;
    private MouseOverArea exitButton;
    private MouseOverArea settingsButton;
    private MouseOverArea rankingButton;

    private Font UIFont1;
    private UnicodeFont uniFont;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container= gameContainer;
        this.stateBasedGame= stateBasedGame;
        this.background = new Image("res/images/BackgroundSpace.png");


        single = new Image("res/images/ButtonSinglePlayer.png").getScaledCopy(container.getWidth()/3, container.getHeight()/10);
        singleButton = new MouseOverArea(container, single, container.getWidth()/3, 2*container.getHeight()/6, container.getWidth()/3, container.getHeight()/10, this);

        multi = new Image("res/images/ButtonMultiplayer.png").getScaledCopy(container.getWidth()/3, container.getHeight()/10);
        multiButton = new MouseOverArea(container, multi, container.getWidth()/3, 3*container.getHeight()/6, container.getWidth()/3, container.getHeight()/10, this);

        settings =new Image("res/images/Settings.png").getScaledCopy(container.getWidth()/12,container.getHeight()/10);
        settingsButton =new MouseOverArea(container, settings,container.getWidth()/6, 4*container.getHeight()/6,container.getWidth()/3,container.getHeight()/10,this);

        exit=new Image("res/images/ButtonExit.png").getScaledCopy(container.getWidth()/6,container.getHeight()/10);
        exitButton=new MouseOverArea(container,exit,4*container.getWidth()/10,5*container.getHeight()/6,container.getWidth()/6,container.getHeight()/10,this);

        ranking =new Image("res/images/Cup.png").getScaledCopy(container.getWidth()/8,container.getHeight()/8);
        rankingButton =new MouseOverArea(container, ranking,container.getWidth()/2, 4*container.getHeight()/6,container.getWidth()/8,container.getHeight()/8,this);

        try{
            UIFont1 = Font.createFont(Font.TRUETYPE_FONT,ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            UIFont1 = UIFont1.deriveFont(Font.BOLD, container.getWidth()/11f);
            uniFont = new UnicodeFont(UIFont1);
            uniFont.addAsciiGlyphs();
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();
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

        uniFont.drawString(gameContainer.getWidth()/3f, container.getHeight()/8f, " MENU", Color.white);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
    }

    public void componentActivated(AbstractComponent source) {
        if (source == singleButton ) {
            stateBasedGame.enterState(2, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == multiButton ) {
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == settingsButton) {
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == rankingButton ) {
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
        if(source==exitButton){
            menu.logOut();
            System.exit(0);
        }
    }

    @Override


    public void keyPressed(int key, char c){
        if( key == Input.KEY_ESCAPE){
            System.exit(0);
        }
    }

    @Override
    public int getID() {
        return 1;
    }
}