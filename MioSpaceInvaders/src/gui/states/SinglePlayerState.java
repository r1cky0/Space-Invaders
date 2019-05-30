package gui.states;

import gui.thread.ThreadInvader;
import gui.thread.ThreadInvaderShot;
import logic.environment.Field;
import logic.environment.Menu;
import logic.environment.MovingDirections;
import logic.sprite.dinamic.Bullet;
import logic.sprite.dinamic.Invader;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import java.util.ArrayList;

public class SinglePlayerState extends BasicGameState {

    private Menu menu;
    private Field field;
    private GameContainer gameContainer;


    private java.awt.Font fontData;
    private UnicodeFont uniFontData;

    //IMAGES
//    private Animation invadersAnimation;
    private Image background;
    private Image invaderImage;
    private Image spaceShipImage;
    private ArrayList<Image> brickImages = new ArrayList<>();
    private Image bulletImage;

    private ThreadInvaderShot threadInvaderShot;
    private ThreadInvader threadInvader;
    private boolean threadStarted = false;
    private boolean threadStarted2 = false;

    public SinglePlayerState(Menu menu){
        this.menu = menu;

        try {
//            Image[] invaderImages = new Image[]{new Image("res/images/Alien0a.png"), new Image("res/images/Alien0b.png")};
//            invadersAnimation = new Animation (invaderImages, 1000);

            invaderImage = new Image("res/images/Alien0a.png");
            spaceShipImage = new Image(menu.getCustomization().getCurrentShip());
            bulletImage = new Image("res/images/Shot.png");
            for(int i=0; i<4; i++){
                brickImages.add(new Image("res/images/Brick" + i + ".png"));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        background = new Image("res/images/BackgroundSpace.png");

        try{
            fontData = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                    ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            fontData = fontData.deriveFont(java.awt.Font.BOLD,3*gameContainer.getWidth()/100f);

            uniFontData = new UnicodeFont(fontData);

            uniFontData.getEffects().add(new ColorEffect(java.awt.Color.white));

            uniFontData.addAsciiGlyphs();
            uniFontData.loadGlyphs();
        }catch(Exception e){
            e.printStackTrace();
        }
        field = menu.getField();
        spaceShipImage = new Image(menu.getCustomization().getCurrentShip());
//        threadInvaderShot = new ThreadInvaderShot(500, field);
        threadInvader = new ThreadInvader(500, field);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);
        uniFontData.drawString(80*gameContainer.getWidth()/100f,15,"Lives: " + field.getSpaceShip().getLife(), Color.white);
        uniFontData.drawString(20,15,"Score: " + field.getSpaceShip().getCurrentScore(), Color.white);

        field.getSpaceShip().render(spaceShipImage);

        for (Invader invader: field.getInvaders()) {
            invader.render(invaderImage);
        }

        for(Bunker bunker: field.getBunkers()){
            for(Brick brick:bunker.getBricks()){
                switch (brick.getLife()){

                    case 4:
                        brick.render(brickImages.get(0));
                        break;
                    case 3:
                        brick.render(brickImages.get(1));
                        break;
                    case 2:
                        brick.render(brickImages.get(2));
                        break;
                    case 1:
                        brick.render(brickImages.get(3));
                        break;
                }
            }
        }

        if(field.getShipBullet() != null){
            field.getShipBullet().render(bulletImage);
        }

        for(Bullet bullet : field.getInvaderBullets()) {
            bullet.render(bulletImage);
        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gameContainer.getInput();

//        if(!threadInvaderShot.isRunning()){
//            threadInvaderShot.start();
//            threadStarted = true;
//        }

        if(!threadInvader.isRunning()){
            threadInvader.start();
            threadStarted2 = true;
        }


        //STATO GIOCO
        if(field.isGameOver()){
//            threadInvaderShot.stop();
            threadInvader.stop();
            stateBasedGame.enterState(3, new FadeOutTransition(), new FadeInTransition());
        }

        if(field.isNewHighscore()){
//            threadInvaderShot.stop();
            threadInvader.stop();
            stateBasedGame.enterState(6, new FadeOutTransition(), new FadeInTransition());
        }

        if(input.isKeyDown(Input.KEY_ESCAPE)){
//            threadInvaderShot.stop();
            threadInvader.stop();
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }

        //MOVIMENTI E AZIONI SPACE SHIP
        if (input.isKeyDown(Input.KEY_LEFT)) {
            field.shipMovement(MovingDirections.LEFT, delta);
        }

        if (input.isKeyDown(Input.KEY_RIGHT)) {
            field.shipMovement(MovingDirections.RIGHT, delta);
        }

        if (input.isKeyPressed(Input.KEY_SPACE)) {
            field.shipShot();
        }

        if (field.getShipBullet() != null) {
            field.getShipBullet().moveUp(delta);
        }

        if(field.getShipBullet()!= null) {
            field.checkSpaceShipShotCollision();
        }

        //MOVIMENTI E AZIONI INVADERS
//        field.invaderDirection(delta);

        if (input.isKeyPressed(Input.KEY_0)) {
            field.invaderShot();
        }

        for(Bullet bullet: field.getInvaderBullets()){
            bullet.moveDown(delta);
        }

        field.checkInvaderShotCollision();
    }

    @Override
    public int getID() {
        return 2;
    }
}