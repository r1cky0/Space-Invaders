package network.client;

import network.data.Connection;
import network.data.PacketHandler;
import logic.manager.game.States;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe client UDP.
 *
 */
public class Client implements Runnable {
    private Connection connection;
    private DatagramSocket socket;
    private PacketHandler handler;
    private AtomicBoolean running;
    private int ID;
    private boolean gameStarted;
    private String[] rcvdata;

    /**
     * Inizializzazione client con aggiunta di una nuova connessione e settaggio ID = -1.
     *
     * @param destAddress indirizzo IP server
     * @param destPort porta server
     */
    Client(String destAddress, int destPort) {
        running = new AtomicBoolean(false);
        handler = new PacketHandler();
        gameStarted = false;
        ID = -1; //fintanto che il server non comunica un id al listener è settato a -1
        try {
            connection = new Connection(InetAddress.getByName(destAddress), destPort);
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inizializzazione socket con porta di ascolto del client e avvio del thread listener.
     */
    private void init() throws SocketException {
        socket = new DatagramSocket(8888); //porta ascolto del client
        Thread listener = new Thread(this);
        listener.start();
    }

    /**
     * Metodo che si occupa di inviare pacchetti al server.
     *
     * @param packet pacchetto da inviare
     */
    void send(DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException e) {
            close();
        }
    }

    /**
     * Thread in ascolto di ricezione dati dal server.
     * Se l'ID = -1 è il primo pacchetto che invia il server contenente il nuovo ID.
     * Se il messaggio contiene lo stato di countdown, inizia la partita.
     * Le informazioni successive vengono inserite in un buffer che verrà letto dalle classi che si
     * occupano della renderizzazione in locale.
     */
    @Override
    public void run() {
        running.set(true);
        while (running.get()) {
            byte[] buffer = new byte[3000];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                if (ID == -1) {
                    ID = Integer.parseInt(handler.process(packet)[0]);
                } else if (!gameStarted) {
                    if (States.valueOf(handler.process(packet)[0]) == States.COUNTDOWN) {
                        gameStarted = true;
                    }
                } else {
                    rcvdata = handler.process(packet);
                }
            } catch (IOException e) {
                close();
            }
        }
    }

    /**
     * Chiusura connessione.
     * Chiusura socket e terminazione thread listener.
     */
    public void close() {
        running.set(false);
        socket.close();
    }

    public String[] getRcvdata(){
        return rcvdata;
    }

    public boolean isGameStarted(){
        return gameStarted;
    }

    public int getID(){
        return ID;
    }

    public Connection getConnection(){
        return connection;
    }

}
