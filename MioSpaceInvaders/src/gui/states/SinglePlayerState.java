package gui.states;

import logic.environment.Field;
import logic.environment.Menu;
import logic.environment.MovingDirections;
import logic.player.Player;
import logic.sprite.dinamic.Bullet;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import java.util.ArrayList;

public class SinglePlayerState extends BasicGameState {

    private Menu menu;
    private Field field;
    private GameContainer gameContainer;
    private Image background;

    private java.awt.Font UIFont1;
    private UnicodeFont uniFont;

    private ArrayList<Bunker> bunkers;
    private ArrayList<Invader> invaders;
    private SpaceShip spaceShip;
    private Bullet shipBullet;
    private Bullet invaderBullet;


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
        //I METODI SOTTO VANNO BENE QUANDO AVREMO IL LOGIN. PER ORA BISOGNA INIZIALIZZARE A MANO LE COSE
        //field = menu.getField();
        //field.startGame();
        //spaceShip = menu.getPlayer().getSpaceShip();

        //METODI SEGUENTI FATTI PER PROVARE STATO SENZA AVERE IL LOGIN
        menu.setPlayer("arrosto");
        menu.setField();
        field = menu.getField();

        invaders = field.getInvaders();
        bunkers = field.getBunkers();
        spaceShip = menu.getPlayer().getSpaceShip();


    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);
        uniFont.drawString(8*gameContainer.getWidth()/10,15,"Lives: "+spaceShip.getLife(),Color.white);
        uniFont.drawString(20,15,"Score: "+spaceShip.getCurrentScore(),Color.white);

        System.err.println(spaceShip.getCoordinate());
        spaceShip.render("res/images/ship.png");

//        int cont = 0;
//        for(int i=0; i<4; i++){
//            String path = "res/images/Alien" + (i+1) + ".png";
//            for(int j=0; j<8;j++) {
//                invaders.get(cont).render(path);
//                cont++;
//            }
//        }

        for(Invader invader:invaders){
            invader.render("res/images/Alien1.png");
        }

        for(Bunker bunker: bunkers){
            for(Brick brick:bunker.getBricks()){
                brick.render("res/images/Brick.png");
            }
        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();

        if(input.isKeyDown(Input.KEY_LEFT)){
            field.shipMovement(MovingDirections.LEFT);
        }

        if(input.isKeyDown(Input.KEY_RIGHT)){
            field.shipMovement(MovingDirections.RIGHT);
        }

        if(input.isKeyPressed(Input.KEY_SPACE)) {
            field.shipShot();
        }


    }

    @Override
    public int getID() {
        return 2;
    }
}
