package gui.states;

import logic.environment.manager.file.ReadXmlFile;
import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import main.Dimensions;
import network.client.Client;
import network.data.PacketHandler;
import logic.environment.manager.game.Commands;
import logic.environment.manager.game.GameStates;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;
import java.util.HashMap;

public class MultiplayerState extends BasicInvaderState {
    private GameStates gameStates;
    private String message;
    private UnicodeFont uniFontData;

    private ArrayList<Sprite> invaderInfos;
    private ArrayList<Sprite> invaderBulletInfos;
    private HashMap<Sprite, Integer> brickInfos;
    private ArrayList<Sprite> spaceShipInfos;
    private ArrayList<Sprite> spaceShipBulletInfos;
    private String scoreInfos;
    private int life;

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
        invaderInfos = new ArrayList<>();
        invaderBulletInfos = new ArrayList<>();
        brickInfos = new HashMap<>();
        spaceShipInfos = new ArrayList<>();
        spaceShipBulletInfos = new ArrayList<>();
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

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.drawImage(background, 0, 0);

        uniFontData.drawString(85*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Lives: " + life, Color.red);
        uniFontData.drawString(2*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f,
                "Score: " + scoreInfos, Color.white);

        String[] rcvdata = client.getRcvdata();
        if(rcvdata != null) {
            create(rcvdata);
        }
        for (Sprite sprite : invaderInfos) {
            sprite.render(invaderImage);
        }
        for (Sprite sprite : invaderBulletInfos) {
            sprite.render(bulletImage);
        }
        for (Sprite sprite : brickInfos.keySet()) {
            if(4 - brickInfos.get(sprite) < 4) {
                sprite.render(brickImages.get(4 - brickInfos.get(sprite)));
            }
        }
        for (Sprite sprite : spaceShipInfos) {
            sprite.render(spaceShipImage);
        }
        for (Sprite sprite : spaceShipBulletInfos) {
            sprite.render(bulletImage);
        }
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        Input input = gameContainer.getInput();

        if(input.isKeyDown(Input.KEY_RIGHT)){
            message = client.getID() + "\n" + Commands.MOVE_RIGHT.toString();
            client.send(handler.build(message, client.getConnection()));
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            message = client.getID() + "\n" + Commands.MOVE_LEFT.toString();
            client.send(handler.build(message, client.getConnection()));
        }
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
        if(gameStates == GameStates.GAMEOVER){
            message = client.getID() + "\n" + Commands.EXIT.toString();
            client.send(handler.build(message, client.getConnection()));
            try {
                stateBasedGame.addState(new GameOverStateMulti(scoreInfos));
                stateBasedGame.getState(IDStates.GAMEOVERMULTI_STATE).init(gameContainer,stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(IDStates.GAMEOVERMULTI_STATE, new FadeOutTransition(), new FadeInTransition());
            client.close();
        }
    }

    private void create(String[] rcvdata) {
        gameStates = GameStates.valueOf(rcvdata[0]);

        invaderInfos.clear();
        for (String strings : rcvdata[1].split("\\t")) {
            if (!strings.equals("")) {
                invaderInfos.add(new Sprite(new Coordinate(Float.parseFloat(strings.split("_")[0]),
                        Float.parseFloat(strings.split("_")[1])), Dimensions.INVADER_SIZE));
            }
        }
        invaderBulletInfos.clear();
        for (String strings : rcvdata[2].split("\\t")) {
            if (!strings.equals("")) {
                invaderBulletInfos.add(new Sprite(new Coordinate(Float.parseFloat(strings.split("_")[0]),
                        Float.parseFloat(strings.split("_")[1])), Dimensions.BULLET_SIZE));
            }
        }
        brickInfos.clear();
        for (String strings : rcvdata[3].split("\\t")) {
            if (!strings.equals("")) {
                brickInfos.put(new Sprite(new Coordinate(Float.parseFloat(strings.split("_")[0]),
                                Float.parseFloat(strings.split("_")[1])), Dimensions.BRICK_SIZE),
                        Integer.parseInt(strings.split("_")[2]));
            }
        }
        spaceShipInfos.clear();
        int count = 0;
        for (String strings : rcvdata[4].split("\\t")) {
            if (!strings.equals("")) {
                spaceShipInfos.add(new Sprite(new Coordinate(Float.parseFloat(strings.split("_")[0]),
                        Float.parseFloat(strings.split("_")[1])), Dimensions.SHIP_SIZE));
                if (count == client.getID()) {
                    life = Integer.parseInt(strings.split("_")[2]);
                }
                count++;
            }
        }
        spaceShipBulletInfos.clear();
        for (String strings : rcvdata[5].split("\\t")) {
            if (!strings.equals("")) {
                spaceShipBulletInfos.add(new Sprite(new Coordinate(Float.parseFloat(strings.split("_")[0]),
                        Float.parseFloat(strings.split("_")[1])), Dimensions.BULLET_SIZE));
            }
        }
        scoreInfos = rcvdata[6];
    }

    @Override
    public int getID() {
        return IDStates.MULTIPLAYER_STATE;
    }
}
