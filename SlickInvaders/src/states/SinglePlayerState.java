package states;


import logic.Player;
import logic.elements.*;
import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import java.util.ArrayList;
import java.util.Random;

public class SinglePlayerState extends BasicGameState {
    private GameContainer container;
    private Ship ship;
    private Image background;
    private Bullet bullet;
    private InvaderBullet invaderBullet;
    private ArrayList<Invader> invaders;
    private boolean bulletShot = false;
    private boolean invaderShot = false;
    private int invX;
    private int invY;
    private float size;
    private MovingDirections md = MovingDirections.RIGHT;
    private static final float PROP_SIZE = 0.06f;

    private java.awt.Font UIFont1;
    private UnicodeFont uniFont;

    //private Player player;


    @Override
    public void init(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
        this.container = container;
        //this.player = player;
        ship = new Ship(container);
        bullet = null;
        invaderBullet = null;
        invX = 0;
        invY = 70;
        size = container.getWidth()*PROP_SIZE;
        invaders = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            String path = "res/Alien"+(i+1)+".png";
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
        uniFont.drawString(20,15,"Lives: "+ship.getLife(),Color.white);
        uniFont.drawString(container.getWidth()-300,15,"Score: "+ship.getScore(),Color.white);
        ship.render(container,graphics);

        for(Invader inv : invaders){
            inv.render(container, graphics);
        }

        if(bullet != null) {
            bullet.render(container,graphics);
        }

        if(invaderBullet != null){
            invaderBullet.render(container,graphics);
        }
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

        if (bulletShot) {
            bullet.update(container,i);
        }

        if(bulletShot) {
            for(Invader inv: invaders){
                if(bullet.collides(inv.getShape())){
                    invaders.remove(inv);
                    bullet = null;
                    bulletShot = false;
                    ship.increaseScore(inv.getValue());
                    break;
                }
            }
        }

        if(bulletShot) {
            if (bullet.endReached()) {
                bullet = null;
                bulletShot = false;
            }
        }

        if(invaders.isEmpty()){
            invaderShot = false;
            bulletShot = false;
            this.init(container,stateBasedGame);
        }

        //Creazione randomica bullet invader
        if(invaderShot == false){
            Random rand = new Random();
            int n = rand.nextInt(invaders.size());
            invaderBullet = new InvaderBullet(container,invaders.get(n).getX(),invaders.get(n).getY()+ invaders.get(n).getSize()/2f);
            invaderShot = true;
        }

        if(invaderShot){
            invaderBullet.update(container,i);
        }

        if(invaderShot){
            if(invaderBullet.collides(ship.getShape())){
                invaderBullet = null;
                invaderShot = false;
                ship.decreseLife();
            }
        }

        if(invaderShot) {
            if (invaderBullet.endReached()) {
                invaderBullet = null;
                invaderShot = false;
            }
        }

        if(ship.getLife() == 0){
            stateBasedGame.enterState(2, new FadeOutTransition(), new FadeInTransition());
        }

        //Movimento invaders
            for(Invader invader : invaders){
                invader.update(gameContainer,i);
            }

        for(Invader invader : invaders){
            if(invader.getX()+invader.getSize() >= container.getWidth()){
                md = MovingDirections.LEFT;
                getDownInvaders();
                break;
            }
        }

        for(Invader invader : invaders){
            if(invader.getX() <= 0){
                md = MovingDirections.RIGHT;
                getDownInvaders();
                break;
            }
        }

        for(Invader invader : invaders){
            invader.setMd(md);
        }
    }


    public void getDownInvaders(){
        for(Invader invader : invaders){
            invader.setMoveDown(true);
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
