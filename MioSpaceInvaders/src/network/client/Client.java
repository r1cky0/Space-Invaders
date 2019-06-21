package network.client;

import logic.player.Player;
import network.data.Connection;
import network.data.PacketHandler;
import logic.environment.manager.game.States;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe listener UDP
 */
public class Client implements Runnable {
    private Connection connection;
    private DatagramSocket socket;
    private PacketHandler handler;
    private Player player;

    private AtomicBoolean running;
    private int ID;
    private String[] rcvdata;
    private States gameState;

    public Client(Player player, String destAddress, int destPort) {
        this.player = player;
        running = new AtomicBoolean(false);
        handler = new PacketHandler();

        gameState = States.INITIALIZATION;
        ID = -1; //fintanto che il server non comunica un id al listener è settato a -1
        try {
            //aggiunta connessione verso il server
            connection = new Connection(InetAddress.getByName(destAddress), destPort);
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws SocketException {
        socket = new DatagramSocket(8888); //porta ascolto del client
        Thread listener = new Thread(this);
        listener.start();
    }

    /**
     * Invio dati dal listener
     */
    public void send(DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException e) {
            close();
        }
    }

    /**
     * Ricezione dati dal server.
     * Il server invia ai listener le informazioni sullo stato del gioco e sulla posizione degli
     * elementi per permettere ai listener di renderizzare il tutto.
     */
    @Override
    public void run() {
        running.set(true);
        while(running.get()) {
            byte[] buffer = new byte[3000];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                switch (gameState){
                    case INITIALIZATION:
                        ID = Integer.parseInt(handler.process(packet)[0]);
                        gameState = States.WAITING;
                        break;
                    case WAITING:
                        gameState = States.valueOf(handler.process(packet)[0]);
                        break;
                    case START:
                        rcvdata = handler.process(packet); //rcvdata contiene dati ricevuti che client deve renderizzare
                        break;
                }
            } catch (IOException e) {
                close();
            }
        }
    }

    /**
     * Chiusura connessione del listener
     */
    public void close() {
        running.set(false);
        socket.close();
    }

    public States getGameState(){
        return gameState;
    }

    public String[] getRcvdata(){
        return rcvdata;
    }

    public int getID(){
        return ID;
    }

    public Connection getConnection(){
        return connection;
    }

    public Player getPlayer(){
        return player;
    }
}
