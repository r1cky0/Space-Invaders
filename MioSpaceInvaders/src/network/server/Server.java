package network.server;

import logic.environment.manager.game.GameStates;
import logic.environment.manager.game.Multiplayer;
import logic.player.Player;
import network.data.Connection;
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

    private ConcurrentHashMap<Integer, ServerThread> clients;
    private AtomicBoolean runningServer;

    private int port;
    private int maxPlayers = 2;

    Server(int port) {
        this.port = port;
        clients = new ConcurrentHashMap<>();
        runningServer = new AtomicBoolean(false);
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
                if(multiplayer.getGameStates() == GameStates.WAITING) {
                    addConnection(packet);
                }else {
                    String[] infos = handler.process(packet);
                    try {
                        int ID = Integer.parseInt(infos[0]);
                        clients.get(ID).execCommand(infos);
                    }catch (NumberFormatException ignored){}
                }
                checkClients();
                if(clients.isEmpty()){
                    multiplayer.stopGame();
                    multiplayer.setGameStates(GameStates.WAITING);
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
            clients.put(ID, new ServerThread(player, multiplayer, connection, socket));
            clients.get(ID).send(String.valueOf(ID));
            if (clients.size() == maxPlayers) {
                broadcast(GameStates.START.toString());
                try {
                    //sleep per dare il tempo ai client di inizializzare il campo di gioco prima di iniziare
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                multiplayer.startGame();
                for(int id : clients.keySet()){
                    clients.get(id).start();
                }
            }
        }
    }

    private void checkClients(){
        for(int ID : clients.keySet()){
            if(!clients.get(ID).isRunning().get()){
                clients.remove(clients.get(ID));
            }
        }
    }

    private void broadcast(String mex){
        for(int ID : clients.keySet()) {
            clients.get(ID).send(mex);
        }
    }
}
