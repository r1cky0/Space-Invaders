package gui.states;

import logic.environment.manager.file.ReadXmlFile;
import logic.environment.manager.menu.Menu;
import logic.service.Facade;
import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import network.client.Client;
import network.data.PacketHandler;
import network.server.Commands;
import network.server.GameStates;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

public class MultiplayerState extends BasicInvaderState {

    //DIMENSIONS
    private double maxWidth;
    private double bulletSize;
    private double brickSize;
    private double invaderSize;
    private double shipSize;

    private ArrayList<Sprite> invaderInfos;
    private ArrayList<Sprite> invaderBulletInfos;
    private HashMap<Sprite, Integer> brickInfos;
    private ArrayList<Sprite> spaceShipInfos;
    private ArrayList<Sprite> spaceShipBulletInfos;
    private String scoreInfos;
    private int life;
    private PacketHandler handler;
    private String message;
    private GameStates gameStates;

    private UnicodeFont uniFontData;

    //IMAGES
    private Image background;
    private Image invaderImage;
    private Image spaceShipImage;
    private ArrayList<Image> brickImages = new ArrayList<>();
    private Image bulletImage;

    private Menu menu;
    private static Client client;
    public boolean firstTime;

    public MultiplayerState(Menu menu) {
        this.menu = menu;
        invaderInfos = new ArrayList<>();
        invaderBulletInfos = new ArrayList<>();
        brickInfos = new HashMap<>();
        spaceShipInfos = new ArrayList<>();
        spaceShipBulletInfos = new ArrayList<>();
        handler = new PacketHandler();

        maxWidth = menu.getMaxWidth();
        shipSize = maxWidth / 20;
        bulletSize = maxWidth / 60;
        brickSize = maxWidth / 40;
        invaderSize = maxWidth / 20;

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
        firstTime = true;
    }

    public static void setClient(Client client){
        MultiplayerState.client = client;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background, 0, 0);

        uniFontData.drawString(85 * gameContainer.getWidth() / 100f, 2 * gameContainer.getHeight() / 100f,
                "Lives: " + life, Color.red);
        uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth("Score: ")) / 2,
                2 * gameContainer.getHeight() / 100f, "Score: " + scoreInfos, Color.white);

        create();

        for (Sprite sprite : invaderInfos) {
            sprite.render(invaderImage);
        }

        for (Sprite sprite : invaderBulletInfos) {
            sprite.render(bulletImage);
        }

        for (Sprite sprite : brickInfos.keySet()) {
            sprite.render(brickImages.get(4 - brickInfos.get(sprite)));
        }

        for (Sprite sprite : spaceShipInfos) {
            sprite.render(spaceShipImage);
        }

        for (Sprite sprite : spaceShipBulletInfos) {
            sprite.render(bulletImage);
        }

    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
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
    }

    private void create(){
        String[] rcvdata = client.getRcvdata();

        invaderInfos.clear();
        for (String strings : rcvdata[0].split("\\t")) {
            if (!strings.equals("")) {
                invaderInfos.add(new Sprite(new Coordinate(Float.parseFloat(strings.split("_")[0]),
                        Float.parseFloat(strings.split("_")[1])), invaderSize));
            }
        }

        invaderBulletInfos.clear();
        for (String strings : rcvdata[1].split("\\t")) {
            if (!strings.equals("")) {
                invaderBulletInfos.add(new Sprite(new Coordinate(Float.parseFloat(strings.split("_")[0]),
                        Float.parseFloat(strings.split("_")[1])), bulletSize));
            }
        }

        brickInfos.clear();
        for (String strings : rcvdata[2].split("\\t")) {
            if (!strings.equals("")) {
                brickInfos.put(new Sprite(new Coordinate(Float.parseFloat(strings.split("_")[0]),
                                Float.parseFloat(strings.split("_")[1])), brickSize),
                        Integer.parseInt(strings.split("_")[2]));
            }
        }

        spaceShipInfos.clear();
        int count = 0;
        for (String strings : rcvdata[3].split("\\t")) {
            if (!strings.equals("")) {
                spaceShipInfos.add(new Sprite(new Coordinate(Float.parseFloat(strings.split("_")[0]),
                        Float.parseFloat(strings.split("_")[1])), shipSize));
                if (count == client.getID()) {
                    life = Integer.parseInt(strings.split("_")[2]);
                }
                count++;
            }
        }

        spaceShipBulletInfos.clear();
        for (String strings : rcvdata[4].split("\\t")) {
            if (!strings.equals("")) {
                spaceShipBulletInfos.add(new Sprite(new Coordinate(Float.parseFloat(strings.split("_")[0]),
                        Float.parseFloat(strings.split("_")[1])), bulletSize));
            }
        }

        scoreInfos = rcvdata[5];
    }

    @Override
    public int getID() {
        return IDStates.MULTIPLAYER_STATE;
    }
}
