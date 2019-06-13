package gui.states;

import logic.environment.manager.file.ReadXmlFile;
import logic.thread.ThreadInvader;
import logic.environment.manager.game.OfflineGameManager;
import logic.environment.manager.menu.Menu;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.Invader;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import network.server.Commands;
import network.server.GameStates;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;

public class SinglePlayerState extends BasicInvaderState {

    private Menu menu;
    private SinglePlayer singlePlayer;

    private UnicodeFont uniFontData;

    //IMAGES
    private Image background;
    private Image invaderImage;
    private Image spaceShipImage;
    private ArrayList<Image> brickImages = new ArrayList<>();
    private Image bulletImage;

//    private ThreadInvader threadInvader;
//    public boolean newThread;

    public SinglePlayerState(Menu menu){
        this.menu = menu;
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
//        newThread = false;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);
        Color color;
        int highscore;
        if(menu.getPlayer().getHighScore() >= singlePlayer.getPlayer().getSpaceShip().getCurrentScore()){
            color = Color.white;
            highscore = menu.getPlayer().getHighScore();
        }else{
            color = Color.green;
            highscore = singlePlayer.getPlayer().getSpaceShip().getCurrentScore();
        }
        uniFontData.drawString(85*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Lives: " + singlePlayer.getPlayer().getSpaceShip().getLife(), Color.red);
        uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth("Score: "))/2,
                2*gameContainer.getHeight()/100f,"Score: " + singlePlayer.getPlayer().getSpaceShip().getCurrentScore(), color);
        uniFontData.drawString(2*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Highscore: " + highscore, Color.green);

        singlePlayer.getSpaceShip().render(spaceShipImage);

        for (Invader invader: singlePlayer.getOfflineGameManager().getInvaders()) {
            invader.render(invaderImage);
        }

        for(Bunker bunker: singlePlayer.getOfflineGameManager().getBunkers()){
            for(Brick brick:bunker.getBricks()){
                brick.render(brickImages.get(4 - brick.getLife()));
            }
        }

        if(singlePlayer.getPlayer().getSpaceShip().getShipBullet() != null){
            singlePlayer.getPlayer().getSpaceShip().getShipBullet().render(bulletImage);
        }
        for(Bullet bullet : singlePlayer.getOfflineGameManager().getInvaderBullets()) {
            bullet.render(bulletImage);
        }
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gameContainer.getInput();

        if(input.isKeyDown(Input.KEY_RIGHT)){
            singlePlayer.execCommand(Commands.MOVE_RIGHT);
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            singlePlayer.execCommand(Commands.MOVE_LEFT);
        }
        if(input.isKeyDown(Input.KEY_SPACE)){
            singlePlayer.execCommand(Commands.SHOT);
        }
        if (input.isKeyDown(Input.KEY_ESCAPE)){
            singlePlayer.execCommand(Commands.EXIT);
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
        singlePlayer.loop();

        //STATO GIOCO
        if (singlePlayer.checkGameState() == GameStates.GAMEOVER) {
            stateBasedGame.enterState(3, new FadeOutTransition(), new FadeInTransition());
        }
        if (singlePlayer.checkGameState() == GameStates.NEWHIGHSCORE) {
            stateBasedGame.enterState(6, new FadeOutTransition(), new FadeInTransition());
        }
    }


    @Override
    public int getID() {
        return 2;
    }
}