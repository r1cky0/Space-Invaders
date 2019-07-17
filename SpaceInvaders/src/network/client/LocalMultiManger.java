package network.client;

import logic.manager.field.MovingDirections;
import logic.manager.game.Commands;
import logic.manager.game.States;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
import network.data.PacketHandler;
import org.newdawn.slick.Input;

public class LocalMultiManger {
    private ShipManager shipManager;
    private Client client;
    private String message;
    private PacketHandler handler;
    private States connectionState;

    public LocalMultiManger() {
        handler = new PacketHandler();
        connectionState = States.INITIALIZATION;
    }

    public void init() {
        client = new Client("localhost", 9999);
        client.send(handler.build("Hello", client.getConnection()));
        Coordinate coordinate = new Coordinate(0,0);
        SpaceShip defaultShip = new SpaceShip(coordinate);
        defaultShip.init();
        shipManager = new ShipManager(defaultShip);
        connectionState = States.INITIALIZATION;
    }

    public void checkConnection() {
        if (client.getID() != -1) {
            connectionState = States.WAITING;
        }
        if (client.isGameStarted()) {
            connectionState = States.COUNTDOWN;
        }
    }

    public void exit() {
        message = client.getID() + "\n" + Commands.EXIT.toString();
        client.send(handler.build(message, client.getConnection()));
        client.close();
    }

    public void execCommand(int inputButton, int delta) {
        message = client.getID() + "\n";
        if (inputButton == Input.KEY_RIGHT) {
            shipManager.shipMovement(MovingDirections.RIGHT, delta);
            message += Commands.MOVE_RIGHT.toString() + "\n" + shipManager.getX();
        }
        if (inputButton == Input.KEY_LEFT) {
            shipManager.shipMovement(MovingDirections.LEFT, delta);
            message += Commands.MOVE_LEFT.toString() + "\n" + shipManager.getX();
        }
        if (inputButton == Input.KEY_SPACE) {
            message += Commands.SHOT.toString();
        }
        client.send(handler.build(message, client.getConnection()));
    }

    public String[] getRcvdata() {
        return client.getRcvdata();
    }

    public States getConnectionState() {
        return connectionState;
    }

    public ShipManager getShipManager(){
        return shipManager;
    }

    public int getID(){
        return client.getID();
    }

}
