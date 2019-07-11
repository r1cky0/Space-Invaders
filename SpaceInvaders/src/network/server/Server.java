package network.server;

import logic.environment.manager.game.States;
import logic.environment.manager.game.Multiplayer;
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
        runningServer = new AtomicBoolean(false);
        handler = new PacketHandler();
        multiplayer = new Multiplayer(messageBuilder);
        try {
            this.init();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void init() throws SocketException {
        this.socket = new DatagramSocket(this.port);
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
                try{
                    Integer.parseInt(infos[0]);
                    clients.get(Integer.parseInt(infos[0])).setInfos(infos);
                }catch (NumberFormatException e){
                    addConnection(packet);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int id : clients.keySet()){
                    clients.get(id).sender();
                }
            }
        }
    }

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
