package gui.states.multi;

import gui.drawer.SpriteDrawer;
import logic.manager.field.MovingDirections;
import logic.manager.game.Commands;
import logic.manager.game.States;
import logic.player.Player;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.unmovable.Brick;
import main.Dimensions;
import network.client.Client;
import network.data.PacketHandler;
import org.newdawn.slick.Input;
import java.util.Timer;
import java.util.TimerTask;

public class LocalMultiManger {
    private ShipManager shipManager;
    private SpriteDrawer spriteDrawer;
    private Client client;
    private String message;
    private PacketHandler handler;
    private int score;
    private States gameState;

    public LocalMultiManger(){
        spriteDrawer = new SpriteDrawer();
        gameState = States.INITIALIZATION;
        handler = new PacketHandler();
        Coordinate coordinate = new Coordinate((Dimensions.MAX_WIDTH / 2 - Dimensions.SHIP_WIDTH / 2),
                (Dimensions.MAX_HEIGHT - Dimensions.SHIP_HEIGHT));
        SpaceShip defaultShip = new SpaceShip(coordinate);
        shipManager = new ShipManager(defaultShip);
    }

    public void init(){
        client = new Client("localhost", 9999);
        client.send(handler.build("Hello", client.getConnection()));
    }

    public void checkConnection() {
        if (client.getID() != -1) {
            gameState = States.WAITING;
            if(client.getRcvdata() != null) {
                if (States.valueOf(client.getRcvdata()[0]) == States.START) {
                    gameState = States.START;
                }
            }
        }
    }

    public void exit() {
        message = client.getID() + "\n" + Commands.EXIT.toString();
        client.send(handler.build(message, client.getConnection()));
        client.close();
    }

    public void execCommand(int inputButton, int delta){
        message = client.getID() + "\n";
        if(inputButton == Input.KEY_RIGHT){
            shipManager.shipMovement(MovingDirections.RIGHT, delta);
            message += Commands.MOVE_RIGHT.toString() + "\n" + shipManager.getX();
        }
        if(inputButton == Input.KEY_LEFT){
            shipManager.shipMovement(MovingDirections.LEFT, delta);
            message += Commands.MOVE_LEFT.toString() + "\n" + shipManager.getX();
        }
        if(inputButton == Input.KEY_SPACE){
            message += Commands.SHOT.toString();
        }
        client.send(handler.build(message, client.getConnection()));
    }

    public void render(){
        String[] rcvdata = client.getRcvdata();
        if(rcvdata != null) {
            gameState = States.valueOf(rcvdata[0]);
            invaderDrawer(rcvdata[1]);
            bonusInvaderDrawer(rcvdata[2]);
            invaderBulletDrawer(rcvdata[3]);
            bunkerDrawer(rcvdata[4]);
            shipDrawer(rcvdata[5]);
            shipBulletDrawer(rcvdata[6]);
            score = Integer.parseInt(rcvdata[7]);
        }
    }

    private Coordinate converter(String data){
        return new Coordinate(Float.parseFloat(data.split("_")[0]),
                Float.parseFloat(data.split("_")[1]));
    }

    private void invaderDrawer(String data) {
        for (String strings : data.split("\\t")) {
            spriteDrawer.render(new Invader(converter(strings)));
        }
    }

    private void bonusInvaderDrawer(String data){
        if(!data.equals("")){
            spriteDrawer.render(new BonusInvader(converter(data)));
        }
    }

    private void invaderBulletDrawer(String data) {
        for (String strings : data.split("\\t")) {
            if(!strings.equals("")) {
                spriteDrawer.render(new InvaderBullet(converter(strings)));
            }
        }
    }

    private void bunkerDrawer(String data) {
        for (String strings : data.split("\\t")) {
            Brick brick = new Brick(converter(strings));
            brick.setLife(Integer.parseInt(strings.split("_")[2]));
            spriteDrawer.render(brick);
        }
    }

    private void shipDrawer(String data) {
        for (String strings : data.split("\\t")) {
            if (!strings.equals("")) {
                if (client.getID() == Integer.parseInt(strings.split("_")[0])) {
                    spriteDrawer.render(shipManager.getSpaceShip());
                    shipManager.getSpaceShip().setLife(Integer.parseInt(strings.split("_")[3]));
                } else {
                    spriteDrawer.render(new SpaceShip(converter(strings)));
                }
            }
        }
    }

    private void shipBulletDrawer(String data){
        for (String strings : data.split("\\t")) {
            if(!strings.equals("")) {
                spriteDrawer.render(new SpaceShipBullet(converter(strings)));
            }
        }
    }

    public States getGameState(){
        return gameState;
    }

    public int getScore(){
        return score;
    }

    public int getLife(){
        return shipManager.getSpaceShip().getLife();
    }

}
