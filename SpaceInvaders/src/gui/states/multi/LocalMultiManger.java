package gui.states.multi;

import gui.drawer.SpriteDrawer;
import logic.manager.field.MovingDirections;
import logic.manager.game.Commands;
import logic.manager.game.States;
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

import java.util.ArrayList;

public class LocalMultiManger {
    private ShipManager shipManager;
    private Client client;
    private String message;
    private PacketHandler handler;
    private States gameState;
    private LocalMultiRender localMultiRender;

    public LocalMultiManger(){
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
                    localMultiRender = new LocalMultiRender(client.getID(), shipManager);
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
            gameState = localMultiRender.getGameState();
            localMultiRender.draw(rcvdata);
        }
    }

    public States getGameState(){
        return gameState;
    }

    public int getScore(){
        return localMultiRender.getScore();
    }

    public int getLife(){
        return shipManager.getSpaceShip().getLife();
    }

}
