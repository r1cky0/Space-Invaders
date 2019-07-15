package network.server;

import logic.manager.game.States;
import logic.manager.game.Multiplayer;
import logic.player.Player;
import network.data.Connection;
import network.data.MessageBuilder;
import network.data.PacketHandler;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe server UDP che riceve fino a massimo 4 client
 *
 */
public class Server implements Runnable {
    private Multiplayer multiplayer;
    private DatagramSocket socket;
    private PacketHandler handler;
    private MessageBuilder messageBuilder;

    private ConcurrentHashMap<Integer, ServerThread> clients;
    private AtomicBoolean runningServer;

    private int port;
    private int maxPlayers = 1;

    Server(int port) {
        this.port = port;
        clients = new ConcurrentHashMap<>();
        messageBuilder = new MessageBuilder();
        multiplayer = new Multiplayer(messageBuilder);
        runningServer = new AtomicBoolean(false);
        handler = new PacketHandler();
        try {
            init();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void init() throws SocketException {
        this.socket = new DatagramSocket(port);
        Thread server = new Thread(this);
        server.start();
    }

    /**
     * Thread server invia pacchetti ai client contententi info sullo stato di gioco e resta in ascolto
     * per la ricezione di nuovi pacchetti
     */
    public void run() {
        runningServer.set(true);
        System.out.println("Server started on port: " + port);
        while (runningServer.get()) {
            byte[] rcvdata = new byte[64];
            DatagramPacket packet = new DatagramPacket(rcvdata, rcvdata.length);
            try {
                socket.receive(packet);
                String[] infos = handler.process(packet);
                if(infos[0].equals("Hello")) {
                    addConnection(packet);
                }else {
                    clients.get(Integer.parseInt(infos[0])).setInfos(infos);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Il server attende la connessione del numero di client necessari (e impostati a mano tra gli attributi) per
     * l' inizio di una partita multiplayer. Istanzia per ognuno una nuova connessione
     *
     * @param packet datagramPacket
     */
    private void addConnection(DatagramPacket packet) {
        if (clients.size() <= 4) {
            for (int ID : clients.keySet()) {
                if (clients.get(ID).getConnection().getDestAddress().equals(packet.getAddress())) {
                    return;
                }
            }
            Connection connection = new Connection(packet.getAddress(), packet.getPort());
            int ID = clients.size();
            Player player = multiplayer.init(ID, handler.process(packet));
            clients.put(ID, new ServerThread(player, multiplayer, connection, socket, messageBuilder));
            clients.get(ID).send(String.valueOf(ID));
            if (clients.size() == maxPlayers) {
                broadcast(States.START.toString());
                multiplayer.startGame();
                for(int id : clients.keySet()){
                    clients.get(id).sender();
                }
            }
        }
    }

    /**
     * Rimozione dei client che hanno perso. Conclusione del gioco se la lista dei client é vuota
     */
    public void checkClients() {
        for (int ID : clients.keySet()) {
            if (!clients.get(ID).isRunning().get()) {
                clients.remove(ID);
                if (clients.isEmpty()) {
                    multiplayer.stopGame();
                }
            }
        }
    }

    private void broadcast(String mex){
        for(int ID : clients.keySet()) {
            clients.get(ID).send(mex);
        }
    }

}
