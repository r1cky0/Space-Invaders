package network.server;

import logic.manager.game.States;
import logic.player.Player;
import network.data.Connection;
import network.data.MessageBuilder;
import network.data.PacketHandler;
import network.server.game.manager.Multiplayer;
import network.server.thread.ServerThread;
import network.server.timer.AddConnectionTimer;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe server UDP che riceve fino a massimo 4 client, gestisce aggiunta di nuovi giocatori in un hashmap contenente
 * ID del giocatore e thread corrispondente.
 * Al raggiungimento del maxPlayer o dopo un timer di 30s fa partire la partita con i giocatori connessi.
 * A partita iniziata riceve messaggi con i comandi dei client e li smista ai vari server Thread corrispondenti.
 *
 */
public class Server implements Runnable {
    private Multiplayer multiplayer;
    private DatagramSocket socket;
    private PacketHandler handler;
    private MessageBuilder messageBuilder;
    private AddConnectionTimer addConnectionTimer;

    private ConcurrentHashMap<Integer, ServerThread> clients;
    private AtomicBoolean runningServer;

    private int port;
    private final int maxPlayers = 4;

    Server(int port) {
        this.port = port;
        clients = new ConcurrentHashMap<>();
        messageBuilder = new MessageBuilder();
        multiplayer = new Multiplayer(messageBuilder);
        runningServer = new AtomicBoolean(false);
        handler = new PacketHandler();
        addConnectionTimer = new AddConnectionTimer(this);
        init();
    }

    public void init() {
        try {
            this.socket = new DatagramSocket(port);
            Thread server = new Thread(this);
            server.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
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
                String[] info = handler.process(packet);
                if(info[0].equals("Hello")) {
                    addConnection(packet);
                }else {
                    clients.get(Integer.parseInt(info[0])).setCommands(info);
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
        if (clients.size() <= maxPlayers && !multiplayer.getGameState().equals(States.START)) {
            for (int ID : clients.keySet()) {
                if (clients.get(ID).getConnection().getDestAddress().equals(packet.getAddress())) {
                    return;
                }
            }
            if(clients.isEmpty()){
                addConnectionTimer.startTimer();
            }
            Connection connection = new Connection(packet.getAddress(), packet.getPort());
            int ID = clients.size();
            Player player = multiplayer.init(ID);
            clients.put(ID, new ServerThread(player, multiplayer, connection, socket, messageBuilder));
            clients.get(ID).send(String.valueOf(ID));
            if(clients.size() == maxPlayers){
                startGame();
            }
        }
    }

    /**
     * Invio inizio countdown ai client, attesa di 3 secondi e inizio partita.
     * Avvio sender dei serverThread.
     */
    public void startGame() {
        addConnectionTimer.stopTimer();
        for (int id : clients.keySet()) {
            clients.get(id).send(States.COUNTDOWN.toString());
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        multiplayer.startGame();
        for (int id : clients.keySet()) {
            clients.get(id).sender();
        }
    }

    /**
     * Rimozione dei client che sono usciti. Conclusione del gioco se la lista dei client é vuota
     */
    void checkEndClients() {
        for (int ID : clients.keySet()) {
            if (!clients.get(ID).isRunning().get()) {
                clients.remove(ID);
                if (clients.isEmpty()) {
                    multiplayer.stopGame();
                }
            }
        }
        if(multiplayer.getGameState() == States.GAMEOVER){
            clients.clear();
            multiplayer.stopGame();
        }
    }

}
