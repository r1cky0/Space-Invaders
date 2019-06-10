package gui.states;

import logic.environment.manager.file_xml.ReadXmlFile;
import logic.thread.ThreadInvader;
import logic.environment.manager.game.OfflineGameManager;
import logic.environment.manager.menu.Menu;
import logic.environment.manager.game.MovingDirections;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.Invader;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;

public class SinglePlayerState extends BasicInvaderState {

    private Menu menu;
    private OfflineGameManager offlineGameManager;
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
            invaderImage = new Image(ReadXmlFile.readXmlFile(0, "invader"));
            spaceShipImage = new Image(menu.getCustomization().getCurrentShip());
            bulletImage = new Image(ReadXmlFile.readXmlFile(0, "bullet"));

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

        offlineGameManager = menu.getOfflineGameManager();
        spaceShipImage = new Image(menu.getCustomization().getCurrentShip());
        newThread = false;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);
        Color color;
        int highscore;
        if(menu.getPlayer().getHighScore() >= offlineGameManager.getSpaceShip().getCurrentScore()){
            color = Color.white;
            highscore = menu.getPlayer().getHighScore();
        }else{
            color = Color.green;
            highscore = offlineGameManager.getSpaceShip().getCurrentScore();
        }
        uniFontData.drawString(85*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Lives: " + offlineGameManager.getSpaceShip().getLife(), Color.red);
        uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth("Score: "))/2,
                2*gameContainer.getHeight()/100f,"Score: " + offlineGameManager.getSpaceShip().getCurrentScore(), color);
        uniFontData.drawString(2*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Highscore: " + highscore, Color.green);

        offlineGameManager.getSpaceShip().render(spaceShipImage);


        for (Invader invader: offlineGameManager.getInvaders()) {
            invader.render(invaderImage);
        }

        for(Bunker bunker: offlineGameManager.getBunkers()){
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

        if(offlineGameManager.getShipBullet() != null){
            offlineGameManager.getShipBullet().render(bulletImage);
        }

          for(Bullet bullet : offlineGameManager.getInvaderBullets()) {
            bullet.render(bulletImage);
        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gameContainer.getInput();

        if(!newThread){
            threadInvader = new ThreadInvader(offlineGameManager.getDifficulty(), offlineGameManager);
            threadInvader.start();
            newThread = true;
        }

        //STATO GIOCO
        if(offlineGameManager.isGameOver()){
            threadInvader.stop();
            stateBasedGame.enterState(3, new FadeOutTransition(), new FadeInTransition());
        }

        if(offlineGameManager.isNewHighscore()){
            threadInvader.stop();
            stateBasedGame.enterState(6, new FadeOutTransition(), new FadeInTransition());
        }

        if(offlineGameManager.isNewLevel()){
            threadInvader.stop();
            offlineGameManager.setNewLevel(false);
            newThread = false;
        }

        if(input.isKeyDown(Input.KEY_ESCAPE)){
            threadInvader.stop();
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }

        //MOVIMENTI E AZIONI SPACE SHIP
        if (input.isKeyDown(Input.KEY_LEFT)) {
            offlineGameManager.shipMovement(MovingDirections.LEFT, delta);
        }

        if (input.isKeyDown(Input.KEY_RIGHT)) {
            offlineGameManager.shipMovement(MovingDirections.RIGHT, delta);
        }

        if (input.isKeyPressed(Input.KEY_SPACE)) {
            offlineGameManager.shipShot();
        }

        if (offlineGameManager.getShipBullet() != null) {
            offlineGameManager.getShipBullet().move(delta);
        }

        if(offlineGameManager.getShipBullet()!= null) {
            offlineGameManager.checkSpaceShipShotCollision();
        }

        //MOVIMENTI E AZIONI INVADERS
        if (input.isKeyPressed(Input.KEY_0)) {
            offlineGameManager.invaderShot();
        }

        for(Bullet bullet: offlineGameManager.getInvaderBullets()){
            bullet.move(delta);
        }

        offlineGameManager.checkInvaderShotCollision();
    }

    @Override
    public int getID() {
        return 2;
    }
}