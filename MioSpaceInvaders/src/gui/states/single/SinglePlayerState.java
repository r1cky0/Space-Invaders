package gui.states.single;

import gui.states.BasicInvaderState;
import gui.states.IDStates;
import logic.environment.manager.file.ReadXmlFile;
import logic.environment.manager.game.SinglePlayer;
import gui.drawer.SpriteDrawer;
import logic.environment.manager.menu.Menu;
import logic.environment.manager.game.Commands;
import logic.environment.manager.game.GameStates;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import main.Dimension;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import java.util.ArrayList;

public class SinglePlayerState extends BasicInvaderState {
    private Menu menu;
    private SpriteDrawer spriteDrawer;

    private SinglePlayer singlePlayer;
    private UnicodeFont uniFontData;

    //IMAGES
    private Image background;
    private Image invaderImage;
    private Image spaceShipImage;
    private ArrayList<Image> brickImages = new ArrayList<>();
    private Image bulletImage;

    public SinglePlayerState(Menu menu){
        this.menu = menu;

        spriteDrawer = new SpriteDrawer();

        try {
            invaderImage = new Image(ReadXmlFile.read("defaultInvader"));
            bulletImage = new Image(ReadXmlFile.read("defaultBullet"));
            for(int i=0; i<4; i++){
                brickImages.add(new Image(ReadXmlFile.read("brick"+i)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        background = new Image(ReadXmlFile.read("defaultBackground"));

        uniFontData = build(3*gameContainer.getWidth()/100f);

        singlePlayer = menu.getSinglePlayer();
        spaceShipImage = new Image(ReadXmlFile.read(menu.getCustomization().getCurrentShip()));
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.drawImage(background,0,0);

        Color color;
        int highscore;
        if(singlePlayer.getPlayer().getHighScore() >= singlePlayer.getSpaceShip().getCurrentScore()){
            color = Color.white;
            highscore = singlePlayer.getPlayer().getHighScore();
        }else{
            color = Color.green;
            highscore = singlePlayer.getSpaceShip().getCurrentScore();
        }

        uniFontData.drawString(85*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Lives: " + singlePlayer.getSpaceShip().getLife(), Color.red);
        uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth("Score: "))/2,
                2*gameContainer.getHeight()/100f,"Score: " + singlePlayer.getSpaceShip().getCurrentScore(), color);
        uniFontData.drawString(2*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Highscore: " + highscore, Color.green);

        spriteDrawer.render(spaceShipImage,singlePlayer.getSpaceShip().getX(),
                singlePlayer.getSpaceShip().getY(), Dimension.SHIP_WIDTH, Dimension.SHIP_HEIGHT);

        if(singlePlayer.getSpaceShipBullet() != null){
            spriteDrawer.render(bulletImage,singlePlayer.getSpaceShipBullet().getX(),
                    singlePlayer.getSpaceShipBullet().getY(), Dimension.BULLET_WIDTH, Dimension.BULLET_HEIGHT);
        }
        for (Invader invader : singlePlayer.getInvaders()) {
            spriteDrawer.render(invaderImage, invader.getX(), invader.getY(), Dimension.INVADER_WIDTH,
                    Dimension.INVADER_HEIGHT);
        }
        for (InvaderBullet invaderBullet : singlePlayer.getInvadersBullet()) {
            spriteDrawer.render(bulletImage,invaderBullet.getX(),invaderBullet.getY(), Dimension.BULLET_WIDTH,
                    Dimension.BULLET_HEIGHT);
        }
        for(Bunker bunker : singlePlayer.getBunkers()) {
            for(Brick brick : bunker.getBricks()) {
                spriteDrawer.render(brickImages.get(4 - brick.getLife()),brick.getX(),brick.getY(), Dimension.BRICK_WIDTH,
                        Dimension.BRICK_HEIGHT);
            }
        }
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        Input input = gameContainer.getInput();

        if(input.isKeyDown(Input.KEY_RIGHT)){
            singlePlayer.execCommand(Commands.MOVE_RIGHT, delta);
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            singlePlayer.execCommand(Commands.MOVE_LEFT, delta);
        }
        if(input.isKeyPressed(Input.KEY_SPACE)){
            singlePlayer.execCommand(Commands.SHOT, delta);
        }
        if (input.isKeyDown(Input.KEY_ESCAPE)){
            singlePlayer.execCommand(Commands.EXIT, delta);
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        singlePlayer.update(delta);

        //STATO GIOCO
        GameStates gameStates = singlePlayer.checkGameState();

        if (gameStates == GameStates.GAMEOVER) {
            stateBasedGame.enterState(IDStates.GAMEOVERSINGLE_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if (gameStates == GameStates.NEWHIGHSCORE) {
            stateBasedGame.enterState(IDStates.NEWHIGHSCORE_STATE, new FadeOutTransition(), new FadeInTransition());
        }
    }

     @Override
    public int getID() {
        return IDStates.SINGLEPLAYER_STATE;
    }
}