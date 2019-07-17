package network.client;

import logic.manager.field.MovingDirections;
import logic.manager.game.Commands;
import logic.manager.game.States;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
import network.data.PacketHandler;
import org.newdawn.slick.Input;

/**
 * Classe che si occupa di gestire il client locale.
 * Apre la connessione ed invia i comandi eseguiti dal giocatore al server
 */
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

    /**
     * Inizializzazione client con invio al server del messaggio nuovo client ("Hello").
     * Inizializzazione shipManager per la gestione della navicella in locale.
     */
    public void init() {
        client = new Client("localhost", 9999);
        client.send(handler.build("Hello", client.getConnection()));
        Coordinate coordinate = new Coordinate(0,0);
        SpaceShip defaultShip = new SpaceShip(coordinate);
        defaultShip.init();
        shipManager = new ShipManager(defaultShip);
        connectionState = States.INITIALIZATION;
    }

    /**
     * Controllo stato della connessione.
     * Stato waiting -> server ha comunicato l'ID al client che è in attesa di iniziare la partita.
     * Stato countdown -> server ha comunicato inizio della partita e client fa partire il countdown.
     */
    public void checkConnection() {
        if (client.getID() != -1) {
            connectionState = States.WAITING;
        }
        if (client.isGameStarted()) {
            connectionState = States.COUNTDOWN;
        }
    }

    /**
     * Metodo che gestisce l'uscita del giocatore dalla partita.
     * Invia al server il comando exit che elimina il giocatore dalla lista dei client e chiude la connessione.
     */
    public void exit() {
        message = client.getID() + "\n" + Commands.EXIT.toString();
        client.send(handler.build(message, client.getConnection()));
        client.close();
    }

    /**
     * Metodo che gestisce l'esecuzione dei comandi del giocatore.
     * Per i comandi di movimento, lo spostamento viene gestito in locale dallo ship manager
     * e viene inviata al server la nuova posizione.
     * In base al tasto premuto viene creata la stringa del comando da inviare al server.
     *
     * @param inputButton bottone premuto
     * @param delta velocità spostamento
     */
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
