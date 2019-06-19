package network.server;

import logic.environment.manager.game.GameStates;
import logic.environment.manager.game.Multiplayer;
import network.data.Connection;
import network.data.PacketHandler;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe server UDP che riceve fino a massimo 4 client
 *
 */
public class Server implements Runnable {
    private Multiplayer multiplayer;
    //Arraylist connessioni al server da parte dei client
    private List<Connection> clients = new CopyOnWriteArrayList<>();
    private DatagramSocket socket;
    private PacketHandler handler;

    private Thread sender;
    private AtomicBoolean runningSender;
    private AtomicBoolean runningListener;

    private int port;
    private int maxPlayers = 2;

    Server(int port) {
        this.port = port;
        runningSender = new AtomicBoolean(false);
        runningListener = new AtomicBoolean(false);
        handler = new PacketHandler();
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
        runningSender.set(true);
        listener.start();
    }

    /**
     * Thread server invia pacchetti ai client contententi info sullo stato di gioco e resta in ascolto
     * per la ricezione di nuovi pacchetti
     */
    public void run() {
        runningListener.set(true);
        System.out.println("Server started on port: " + port);
        while (runningListener.get()) {
            byte[] rcvdata = new byte[64];
            DatagramPacket packet = new DatagramPacket(rcvdata, rcvdata.length);
            try {
                socket.receive(packet);
                if (multiplayer.getGameStates() == GameStates.START) {
                    int id = multiplayer.execCommand(handler.process(packet));
                    if(id != -1){
                        removeConnection(id);
                    }
                }else if(multiplayer.getGameStates() == GameStates.GAMEOVER){
                    clients.clear();
                }else if(multiplayer.getGameStates() == GameStates.WAITING){
                    addConnection(packet);
                }
                checkEmptyList();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            int ID = clients.size() - 1;
            multiplayer.init(ID, handler.process(packet));
            send(ID, Integer.toString(ID)); //INVIO AL CLIENT IL SUO ID = POSIZIONE NELL'ARRAYLIST DI CONNESSIONI
            if (clients.size() == maxPlayers) {
                broadcast(GameStates.START.toString());
                try {
                    //sleep per dare il tempo ai client di inizializzare il campo di gioco prima di iniziare
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                multiplayer.startGame();
                broadcastRenderInfos();
            }
        }
    }

    private void checkEmptyList(){
        if(clients.isEmpty()){
            multiplayer.stopGame();
            sender.interrupt();
            multiplayer.setGameStates(GameStates.WAITING);
        }
    }

    private void removeConnection(int id){
        clients.remove(id);
    }

    /**
     * Invio dati ad un singolo client
     */
    private void send(int id, String mex) {
        Connection connection = clients.get(id);
        try {
            socket.send(handler.build(mex, connection));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Invio dati al client.
     * Il server invia a tutti i client le informazioni sullo stato del gioco e sulla posizione degli
     * elementi per permettere ai client di renderizzarli.
     */
    private void broadcastRenderInfos() {
        sender = new Thread() {
            public void run() {
                runningSender.set(true);
                while (runningSender.get()) {
                    String infos = multiplayer.getInfos();
                    for (Connection connection : clients) {
                        try {
                            socket.send(handler.build(infos, connection));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            public void interrupt() {
                runningSender.set(false);
            }
        };
        sender.start();
    }

    private void broadcast(String mex){
        for(Connection connection : clients) {
            try {
                socket.send(handler.build(mex, connection));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
