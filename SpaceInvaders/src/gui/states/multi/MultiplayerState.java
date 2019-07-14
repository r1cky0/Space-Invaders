package gui.states.multi;

import gui.elements.GraphicShip;
import gui.states.GameState;
import gui.states.IDStates;
import logic.manager.field.MovingDirections;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
import main.Dimensions;
import network.client.Client;
import network.data.PacketHandler;
import logic.manager.game.Commands;
import logic.manager.game.States;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MultiplayerState extends GameState {
    private States states;
    private UnicodeFont uniFontData;

    private ShipManager shipManager;
    private String score;
    private int life;
    private float yShip;
    private boolean checkIsDead;

    private Client client;
    private PacketHandler handler;

    //IMAGES
    private Image background;

    public MultiplayerState(Client client) {
        this.client = client;
        handler = new PacketHandler();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        background = new Image(readerXmlFile.read("defaultBackground"));
        uniFontData = build(3 * gameContainer.getWidth() / 100f);
        yShip = Dimensions.MAX_HEIGHT - Dimensions.SHIP_HEIGHT;
    }

    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException{
        Coordinate coordinate = new Coordinate((Dimensions.MAX_WIDTH / 2 - Dimensions.SHIP_WIDTH / 2), yShip);
        SpaceShip spaceShip = new SpaceShip(coordinate, Dimensions.SHIP_WIDTH, Dimensions.MAX_HEIGHT);
        shipManager = new ShipManager(spaceShip);
        graphicShip = new GraphicShip(new Image(readerXmlFile.read("ship0")));
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

        String message;
        if(input.isKeyPressed(Input.KEY_SPACE)){
            message = client.getID() + "\n" + Commands.SHOT.toString();
            client.send(handler.build(message, client.getConnection()));
            audioplayer.shot();
        }
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            message = client.getID() + "\n" + Commands.EXIT.toString();
            client.send(handler.build(message, client.getConnection()));
            client.close();
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
            audioplayer.menu();
        }
        if(input.isKeyDown(Input.KEY_RIGHT)){
            shipManager.shipMovement(MovingDirections.RIGHT, delta);
            message = client.getID() + "\n" + Commands.MOVE_RIGHT.toString() + "\n" + shipManager.getX();
            client.send(handler.build(message, client.getConnection()));
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            shipManager.shipMovement(MovingDirections.LEFT, delta);
            message = client.getID() + "\n" + Commands.MOVE_LEFT.toString() + "\n" + shipManager.getX();
            client.send(handler.build(message, client.getConnection()));
        }

        if(states == States.GAMEOVER){
            message = client.getID() + "\n" + Commands.EXIT.toString();
            client.send(handler.build(message, client.getConnection()));
            try {
                stateBasedGame.addState(new GameOverSateteMulti(score));
                stateBasedGame.getState(IDStates.GAMEOVERMULTI_STATE).init(gameContainer,stateBasedGame);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(IDStates.GAMEOVERMULTI_STATE,new FadeOutTransition(),new FadeInTransition());
            client.close();
        }
        checkIsDead = true;
    }

    private void create(String[] rcvdata) {
        states = States.valueOf(rcvdata[0]);

        for (String strings : rcvdata[1].split("\\t")) {
            if (!strings.equals("")) {
                graphicInvader.render(Float.parseFloat(strings.split("_")[0]),
                        Float.parseFloat(strings.split("_")[1]));
            }
        }

        if(!rcvdata[2].equals("")){
            graphicBonusInvader.render(Float.parseFloat(rcvdata[2].split("_")[0]),
                    Float.parseFloat(rcvdata[2].split("_")[1]));
        }

        for (String strings : rcvdata[3].split("\\t")) {
            if (!strings.equals("")) {
                graphicBullet.render(Float.parseFloat(strings.split("_")[0]),
                        Float.parseFloat(strings.split("_")[1]));
            }
        }
        for (String strings : rcvdata[4].split("\\t")) {
            if (!strings.equals("")) {
                if (Integer.parseInt(strings.split("_")[2]) > 0) {
                    graphicBrick.render(Integer.parseInt(strings.split("_")[2]),
                            Float.parseFloat(strings.split("_")[0]), Float.parseFloat(strings.split("_")[1]));
                }
            }
        }
        for (String strings : rcvdata[5].split("\\t")) {
            if (!strings.equals("")) {
                if (client.getID() == Integer.parseInt(strings.split("_")[0])) {
                    graphicShip.render(shipManager.getX(), yShip);
                    if(life > Integer.parseInt(strings.split("_")[2])){
                        audioplayer.explosion();
                    }
                    life = Integer.parseInt(strings.split("_")[2]);
                    checkIsDead = false;
                }else{
                    graphicShip.render(Float.parseFloat(strings.split("_")[1]), yShip);
                }
                if(!strings.split("_")[3].equals(" ")) {
                    graphicBullet.render(Float.parseFloat(strings.split("_")[3]),
                            Float.parseFloat(strings.split("_")[4]));
                }
                if(checkIsDead){
                    life = 0;
                }
            }
        }
        score = rcvdata[6];
    }

    @Override
    public int getID() {
        return IDStates.MULTIPLAYER_STATE;
    }
}
