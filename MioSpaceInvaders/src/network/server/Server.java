package network.server;

import logic.environment.manager.game.Multiplayer;
import network.data.Connection;
import network.data.PacketHandler;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe server UDP che riceve fino a massimo 4 client
 *
 */
public class Server implements Runnable {

    private int port;
    private AtomicBoolean running;
    private DatagramSocket socket;
    private PacketHandler handler;

    private Multiplayer multiplayer;
    private boolean gameStarted;

    //INFORMAZIONI SU: POSIZIONI SHIP, POSIZIONI INVADER, STATO BRICK
    private byte[] snddata;

    //Arraylist connessioni al server da parte dei client
    public List<Connection> clients = new CopyOnWriteArrayList<>();

    public Server(int port) {
        this.port = port;
        snddata = new byte[2048];
        running = new AtomicBoolean(false);
        handler = new PacketHandler();
        gameStarted = false;
        multiplayer = new Multiplayer();
        try {
            this.init();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void init() throws SocketException {
        this.socket = new DatagramSocket(this.port);
        Thread listener = new Thread(this);
        listener.start();
    }

    /**
     * Thread server invia pacchetti ai client contententi info sullo stato di gioco e resta in ascolto
     * per la ricezione di nuovi pacchetti
     */
    public void run() {
        running.set(true);
        System.out.println("Server started on port: " + port);
        while (running.get()) {
            byte[] rcvdata = new byte[1000];
            DatagramPacket packet = new DatagramPacket(rcvdata, rcvdata.length);
            try {
                socket.receive(packet);
                addConnection(packet);
                if (gameStarted) {
                    multiplayer.execCommand(handler.process(packet));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            broadcast();
        }
    }

    private void addConnection(DatagramPacket packet) {
        if (clients.size() <= 4) {
            for (Connection connection : clients) {
                if (connection.getDestAddress().equals(packet.getAddress())) {
                    return;
                }
            }
            clients.add(new Connection(packet.getAddress(), packet.getPort()));
            multiplayer.init(handler.process(packet));
            if (clients.size() == 1) {
                multiplayer.startGame();
                gameStarted = true;
            }
        }
    }

    public void removeConnection(InetAddress address){
        for(Connection connection : clients) {
            if (connection.getDestAddress() == address) {
                clients.remove(connection);
            }
        }
    }

    /**
     * Invio dati ad un singolo client
     */
    public void send(int id, String mex) {
        Connection connection = clients.get(id);
        DatagramPacket packet = new DatagramPacket(snddata, snddata.length, connection.getDestAddress(), connection.getDestPort());
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Invio dati al client.
     * Il server invia a tutti i client le informazioni sullo stato del gioco e sulla posizione degli
     * elementi per permettere ai client di renderizzarli.
     */
    public void broadcast() {
        for(Connection connection : clients) {
            DatagramPacket packet = new DatagramPacket(snddata, snddata.length, connection.getDestAddress(), connection.getDestPort());
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setData(byte[] data){
        this.snddata = data;
    }

    public int getPort() {
        return port;
    }
}
