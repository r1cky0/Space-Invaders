package states;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
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

import java.awt.*;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

public class MenuState extends BasicGameState implements ComponentListener {
    private GameContainer container;

    private Image single;
    private Image multi;
    private Image exit;
    private Image difficolta;
    private Image settings;
    private Image background;

    private StateBasedGame stateBasedGame;
    private MouseOverArea singleButton;
    private MouseOverArea multiButton;
    private MouseOverArea exitButton;
    private MouseOverArea difficoltaButton;
    private MouseOverArea settingButton;

    private Font UIFont1;
    private UnicodeFont uniFont;

    public MenuState(){}

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container= gameContainer;
        this.stateBasedGame= stateBasedGame;
        this. background = new Image("res/space.png");


        single = new Image("res/button_single_player2.png").getScaledCopy(container.getWidth()/3, container.getHeight()/10);
        singleButton = new MouseOverArea(container, single, container.getWidth()/3, 2*container.getHeight()/6, container.getWidth()/3, container.getHeight()/10, this);

        multi = new Image("res/button_multiplayer.png").getScaledCopy(container.getWidth()/3, container.getHeight()/10);
        multiButton = new MouseOverArea(container, multi, container.getWidth()/3, 3*container.getHeight()/6, container.getWidth()/3, container.getHeight()/10, this);

        difficolta=new Image("res/button_difficolta.png").getScaledCopy(container.getWidth()/3,container.getHeight()/10);
        difficoltaButton=new MouseOverArea(container,difficolta,container.getWidth()/3, 4*container.getHeight()/6,container.getWidth()/3,container.getHeight()/10,this);

        exit=new Image("res/button_exit.png").getScaledCopy(container.getWidth()/6,container.getHeight()/10);
        exitButton=new MouseOverArea(container,exit,4*container.getWidth()/10,5*container.getHeight()/6,container.getWidth()/6,container.getHeight()/10,this);

        //settings=new Image("res/button_settings2_new.png").getScaledCopy(90,90);
        //settingButton=new MouseOverArea(container,settings,660,490,80,80,this);

        try{
            UIFont1 = Font.createFont(Font.TRUETYPE_FONT,ResourceLoader.getResourceAsStream("SlickInvaders/font/invaders_font.ttf"));
            UIFont1 = UIFont1.deriveFont(Font.BOLD, container.getWidth()/12f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font

            //Since TrueTypeFont loading has many problems, we can use UnicodeFont to load the .ttf fonts(it's exactly the same thing).
            uniFont = new UnicodeFont(UIFont1);

            //uniFont.addAsciiGlyphs();
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
        difficoltaButton.render(gameContainer,graphics);
        exitButton.render(gameContainer,graphics);
        //settingButton.render(gameContainer,graphics);

        uniFont.drawString(gameContainer.getWidth()/8f, container.getHeight()/8f, "SPACE INVADERS", Color.white);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
    }

    public void componentActivated(AbstractComponent source) {
        if (source == singleButton ) {
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == multiButton ) {
            stateBasedGame.enterState(0, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == difficoltaButton ) {
            stateBasedGame.enterState(0, new FadeOutTransition(), new FadeInTransition());
        }
        /*if (source == settingButton ) {
            stateBasedGame.enterState(0, new FadeOutTransition(), new FadeInTransition());
        }*/
        if(source==exitButton){
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
        return 0;
    }
}