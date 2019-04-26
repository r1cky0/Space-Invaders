package states;

import logic.Player;
import logic.elements.Bullet;
import logic.elements.Invader;
import logic.elements.MovingDirections;
import logic.elements.Ship;
import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import java.util.ArrayList;

public class SinglePlayerState extends BasicGameState{
    private GameContainer container;
    private Ship ship;
    private int score;
    private Image background;
    private Bullet bullet;
    private ArrayList<Invader> invaders;
    public boolean bulletShot = false;
    private int invX;
    private int invY;
    private float size;
    private static final float PROP_SIZE = 0.06f;

    private java.awt.Font UIFont1;
    private UnicodeFont uniFont;

    //private Player player;


    @Override
    public void init(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
        score = 0;
        this.container = container;
        //this.player = player;
        ship = new Ship(container);
        bullet = null;
        invX = 0;
        invY = 70;
        size = container.getWidth()*PROP_SIZE;
        invaders = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            String path = "res/Alien"+(i+1)+".jpg";
            for(int j = 0; j < 8; j++){
                invaders.add(new Invader(container, invX, invY, size, 100/(i+1), path));
                invX += size;
            }
            invX=0;
            invY += size;
        }
        background = new Image("res/space.png");

        try{
            UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("SlickInvaders/font/invaders_font.ttf"));
            UIFont1 = UIFont1.deriveFont(java.awt.Font.BOLD, container.getWidth()/30f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font

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
        ship.render(container,graphics);
        for(Invader inv : invaders){
            inv.render(container, graphics);
        }

        if(bullet != null) {
            bullet.render(container,graphics);
        }

        uniFont.drawString(20,15,"Lives: "+ship.getLife(),Color.white);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();

        if(input.isKeyDown(Input.KEY_LEFT)){
            ship.move(MovingDirections.LEFT);

        }
        if(input.isKeyDown(Input.KEY_RIGHT)){
            ship.move(MovingDirections.RIGHT);
        }

        if (bulletShot== true) {
            bullet.update(container,i);
        }

        if(bulletShot==true) {
            for(Invader inv: invaders){
                if(bullet.collides(inv.getShape())){
                    invaders.remove(inv);
                    bullet = null;
                    bulletShot = false;
                    score += inv.getValue();
                    break;
                }
            }
        }

        if(bulletShot==true) {
            if (bullet.endReached()) {
                System.err.println("PORCOCOACDIOPV SHIOAVASOHID");
                bullet = null;
                bulletShot = false;
            }
        }
    }



    public void keyPressed(int key, char c){
        if(key==Input.KEY_SPACE && !bulletShot){
            try {
                bullet = new Bullet(container, ship.getX(), ship.getY()-ship.getSize()/2);
                this.bulletShot = true;
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    public int getID() {
        return 1;
    }


}
