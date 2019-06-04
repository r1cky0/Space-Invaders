package gui.states;

import logic.thread.ThreadInvader;
import logic.environment.Field;
import logic.environment.Menu;
import logic.environment.MovingDirections;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.Invader;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;

public class SinglePlayerState extends BasicInvaderState {

    private Menu menu;
    private Field field;
    private GameContainer gameContainer;


    private java.awt.Font fontData;
    private UnicodeFont uniFontData;

    //IMAGES
    private Image background;
    private Image invaderImage;
    private Image spaceShipImage;
    private ArrayList<Image> brickImages = new ArrayList<>();
    private Image bulletImage;

    private ThreadInvader threadInvader;
    public boolean newThread;

    public SinglePlayerState(Menu menu){
        this.menu = menu;

        try {
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

        uniFontData = Build(3*gameContainer.getWidth()/100);
        /*try{
            fontData = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                    ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            fontData = fontData.deriveFont(java.awt.Font.BOLD,gameContainer.getWidth()/30);

            uniFontData = new UnicodeFont(fontData);

            uniFontData.getEffects().add(new ColorEffect(java.awt.Color.white));

            uniFontData.addAsciiGlyphs();
            uniFontData.loadGlyphs();
        }catch(Exception e){
            e.printStackTrace();
        }*/
        field = menu.getField();
        spaceShipImage = new Image(menu.getCustomization().getCurrentShip());
        newThread = false;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);
        Color color;
        int highscore;
        if(menu.getPlayer().getHighScore() >= field.getSpaceShip().getCurrentScore()){
            color = Color.white;
            highscore = menu.getPlayer().getHighScore();
        }else{
            color = Color.green;
            highscore = field.getSpaceShip().getCurrentScore();
        }
        uniFontData.drawString(85*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Lives: " + field.getSpaceShip().getLife(), Color.red);
        uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth("Score: "))/2,
                2*gameContainer.getHeight()/100f,"Score: " + field.getSpaceShip().getCurrentScore(), color);
        uniFontData.drawString(2*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Highscore: " + highscore, Color.green);

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

        if(!newThread){
            threadInvader = new ThreadInvader(field.getDifficulty(), field);
            threadInvader.start();
            newThread = true;
        }

        //STATO GIOCO
        if(field.isGameOver()){
            threadInvader.stop();
            stateBasedGame.enterState(3, new FadeOutTransition(), new FadeInTransition());
        }

        if(field.isNewHighscore()){
            threadInvader.stop();
            stateBasedGame.enterState(6, new FadeOutTransition(), new FadeInTransition());
        }

        if(field.isNewLevel()){
            threadInvader.stop();
            field.setNewLevel(false);
            newThread = false;
        }

        if(input.isKeyDown(Input.KEY_ESCAPE)){
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
            field.getShipBullet().move(delta);
        }

        if(field.getShipBullet()!= null) {
            field.checkSpaceShipShotCollision();
        }

        //MOVIMENTI E AZIONI INVADERS
        if (input.isKeyPressed(Input.KEY_0)) {
            field.invaderShot();
        }

        for(Bullet bullet: field.getInvaderBullets()){
            bullet.move(delta);
        }

        field.checkInvaderShotCollision();
    }

    @Override
    public int getID() {
        return 2;
    }
}