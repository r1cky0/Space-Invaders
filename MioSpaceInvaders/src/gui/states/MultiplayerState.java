package gui.states;

import gui.states.drawer.SpriteDrawer;
import logic.environment.manager.field.MovingDirections;
import logic.environment.manager.file.ReadXmlFile;
import logic.environment.manager.game.ShipManager;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
import main.Dimension;
import network.client.Client;
import network.data.PacketHandler;
import logic.environment.manager.game.Commands;
import logic.environment.manager.game.GameStates;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;

public class MultiplayerState extends BasicInvaderState {
    private GameStates gameStates;
    private String message;
    private UnicodeFont uniFontData;

    private SpriteDrawer spriteDrawer;
    private ShipManager shipManager;
    private String score;
    private int life;
    private boolean checkIsDead;

    private Client client;
    private PacketHandler handler;

    //IMAGES
    private Image background;
    private Image invaderImage;
    private Image spaceShipImage;
    private ArrayList<Image> brickImages = new ArrayList<>();
    private Image bulletImage;

    public MultiplayerState(Client client) {
        this.client = client;

        spriteDrawer = new SpriteDrawer();
        handler = new PacketHandler();

        try {
            invaderImage = new Image(ReadXmlFile.read("defaultInvader"));
            bulletImage = new Image(ReadXmlFile.read("defaultBullet"));
            for (int i = 0; i < 4; i++) {
                brickImages.add(new Image(ReadXmlFile.read("brick" + i)));
            }
            spaceShipImage = new Image(ReadXmlFile.read("ship0"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        background = new Image(ReadXmlFile.read("defaultBackground"));
        uniFontData = build(3 * gameContainer.getWidth() / 100f);
    }

    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
        Coordinate coordinate = new Coordinate((Dimension.MAX_WIDTH / 2 - Dimension.SHIP_WIDTH / 2),
                (Dimension.MAX_HEIGHT - Dimension.SHIP_HEIGHT));
        SpaceShip spaceShip = new SpaceShip(coordinate, Dimension.SHIP_WIDTH, Dimension.MAX_HEIGHT);
        shipManager = new ShipManager(spaceShip);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.drawImage(background, 0, 0);

        uniFontData.drawString(85*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Lives: " + life, Color.red);
        uniFontData.drawString(2*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Score: " + score, Color.white);

        String[] rcvdata = client.getRcvdata();
        if(rcvdata != null) {
            create(rcvdata);
        }
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        Input input = gameContainer.getInput();

        if(input.isKeyPressed(Input.KEY_SPACE)){
            message = client.getID() + "\n" + Commands.SHOT.toString();
            client.send(handler.build(message, client.getConnection()));
        }
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            message = client.getID() + "\n" + Commands.EXIT.toString();
            client.send(handler.build(message, client.getConnection()));
            client.close();
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if(input.isKeyDown(Input.KEY_RIGHT)){
            shipManager.shipMovement(MovingDirections.RIGHT, delta);
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            shipManager.shipMovement(MovingDirections.LEFT, delta);
        }
        message = client.getID() + "\n" + Commands.MOVE_RIGHT.toString() + "\n" + shipManager.getX();
        client.send(handler.build(message, client.getConnection()));

        if(gameStates == GameStates.GAMEOVER){
            message = client.getID() + "\n" + Commands.EXIT.toString();
            client.send(handler.build(message, client.getConnection()));
            try {
                stateBasedGame.addState(new GameOverStateMulti(score));
                stateBasedGame.getState(IDStates.GAMEOVERMULTI_STATE).init(gameContainer,stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(IDStates.GAMEOVERMULTI_STATE, new FadeOutTransition(), new FadeInTransition());
            client.close();
        }
        checkIsDead = true;
    }

    private void create(String[] rcvdata) {
        gameStates = GameStates.valueOf(rcvdata[0]);

        for (String strings : rcvdata[1].split("\\t")) {
            if (!strings.equals("")) {
                spriteDrawer.render(invaderImage, Float.parseFloat(strings.split("_")[0]),
                        Float.parseFloat(strings.split("_")[1]), Dimension.INVADER_WIDTH, Dimension.INVADER_HEIGHT);
            }
        }
        for (String strings : rcvdata[2].split("\\t")) {
            if (!strings.equals("")) {
                spriteDrawer.render(bulletImage, Float.parseFloat(strings.split("_")[0]),
                        Float.parseFloat(strings.split("_")[1]), Dimension.BULLET_WIDTH, Dimension.BULLET_HEIGHT);
            }
        }
        for (String strings : rcvdata[3].split("\\t")) {
            if (!strings.equals("")) {
                if (Integer.parseInt(strings.split("_")[2]) > 0) {
                    spriteDrawer.render(brickImages.get(4 - Integer.parseInt(strings.split("_")[2])),
                            Float.parseFloat(strings.split("_")[0]),
                            Float.parseFloat(strings.split("_")[1]), Dimension.BRICK_WIDTH, Dimension.BRICK_HEIGHT);
                }
            }
        }
        for (String strings : rcvdata[4].split("\\t")) {
            if (!strings.equals("")) {
                spriteDrawer.render(spaceShipImage, Float.parseFloat(strings.split("_")[1]),
                        Float.parseFloat(strings.split("_")[2]), Dimension.SHIP_WIDTH, Dimension.SHIP_HEIGHT);
                if (client.getID() == Integer.parseInt(strings.split("_")[0])) {
                    life = Integer.parseInt(strings.split("_")[3]);
                    checkIsDead = false;
                }else if(checkIsDead){
                    life = 0;
                }
            }
        }
        for (String strings : rcvdata[5].split("\\t")) {
            if (!strings.equals("")) {
                spriteDrawer.render(bulletImage, Float.parseFloat(strings.split("_")[0]),
                        Float.parseFloat(strings.split("_")[1]), Dimension.BULLET_WIDTH, Dimension.BULLET_HEIGHT);
            }
        }
        score = rcvdata[6];
    }

    @Override
    public int getID() {
        return IDStates.MULTIPLAYER_STATE;
    }
}
