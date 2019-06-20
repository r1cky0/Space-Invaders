package network.client;

import logic.player.Player;
import network.data.Connection;
import network.data.PacketHandler;
import logic.environment.manager.game.GameStates;
import org.lwjgl.Sys;

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
    private boolean initialization;
    private String[] rcvdata;
    private GameStates gameState;

    public Client(Player player, String destAddress, int destPort) {
        this.player = player;
        running = new AtomicBoolean(false);
        handler = new PacketHandler();
        initialization = true;
        ID = -1; //fintanto che il server non comunica un id al listener è settato a -1
        try {
            //aggiunta connessione verso il server
            connection = new Connection(InetAddress.getByName(destAddress), destPort);
            this.init();
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
                if(initialization){
                    try {
                        ID = Integer.parseInt(handler.process(packet)[0]);
                        initialization = false;
                    }catch (NumberFormatException ignored){
                    }
                }else if(gameState != GameStates.START) {
                    gameState = GameStates.valueOf(handler.process(packet)[0]);
                }
                else {
                    //Packet contiene dati ricevuti
                    //che client deve renderizzare
                    rcvdata = handler.process(packet);
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

    public GameStates getGameState(){
        return gameState;
    }

    public boolean getInitialization(){
        return initialization;
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
