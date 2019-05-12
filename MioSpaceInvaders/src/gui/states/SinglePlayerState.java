package gui.states;

import logic.environment.Menu;
import logic.sprite.dinamic.Bullet;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import java.util.ArrayList;

public class SinglePlayerState extends BasicGameState {

    private Menu menu;
    private GameContainer gameContainer;
    private Image background;

    private java.awt.Font UIFont1;
    private UnicodeFont uniFont;

    private SpaceShip spaceShip;
    private Bullet bullet;
    private Bullet invaderBullet;
    private ArrayList<Invader> invaders;

    public SinglePlayerState(Menu menu){
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        background = new Image("res/images/space.png");
        try{
            UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            UIFont1 = UIFont1.deriveFont(java.awt.Font.BOLD, gameContainer.getWidth()/30f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font

            //Since TrueTypeFont loading has many problems, we can use UnicodeFont to load the .ttf fonts(it's exactly the same thing).
            uniFont = new UnicodeFont(UIFont1);

            //uniFont.addAsciiGlyphs();
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));

            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();
        }catch(Exception e){
            e.printStackTrace();
        }

        menu.startGame();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);
        uniFont.drawString(20,15,"Lives: ",Color.white);
        uniFont.drawString(gameContainer.getWidth()-300,15,"Score: ",Color.white);

        //uniFont.drawString(20,15,"Lives: "+ship.getLife(),Color.white);
        //uniFont.drawString(container.getWidth()-300,15,"Score: "+ship.getScore(),Color.white);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return 2;
    }
}
