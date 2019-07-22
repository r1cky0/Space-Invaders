package network.server.thread;

import logic.manager.game.commands.CommandType;
import logic.player.Player;
import network.data.Connection;
import network.data.MessageBuilder;
import network.data.PacketHandler;
import logic.manager.game.commands.Command;
import logic.manager.game.commands.MoveLeft;
import logic.manager.game.commands.MoveRight;
import logic.manager.game.commands.Shot;
import network.server.game.manager.Multiplayer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Thread che gestisce un client.
 * Si occupa di eseguire un comando quando gli viene passato nel buffer dal server e con un secondo
 * thread di inviare le informazioni di renderizzazione al client.
 */
public class ServerThread implements Runnable{
    private Multiplayer multiplayer;
    private Player player;
    private Connection connection;
    private PacketHandler handler;
    private MessageBuilder messageBuilder;
    private DatagramSocket socket;
    private CopyOnWriteArrayList<String[]> clientCommands;
    private HashMap<CommandType, Command> commands;
    private AtomicBoolean running;

    public ServerThread(Player player, Multiplayer multiplayer, Connection connection, DatagramSocket socket,
                        MessageBuilder messageBuilder) {
        this.player = player;
        this.multiplayer = multiplayer;
        this.connection = connection;
        this.socket = socket;
        this.messageBuilder = messageBuilder;
        clientCommands = new CopyOnWriteArrayList<>();
        handler = new PacketHandler();
        running = new AtomicBoolean(true);
        initCommands();
        start();
    }

    /**
     * Metodo che inizializza l'arrayList dei comandi.
     */
    private void initCommands(){
        commands = new HashMap<>();
        commands.put(CommandType.MOVE_LEFT, new MoveLeft());
        commands.put(CommandType.MOVE_RIGHT, new MoveRight());
        commands.put(CommandType.SHOT, new Shot());
    }

    /**
     * Metodo utilizzato per l'invio dei messaggi al client.
     *
     * @param mex contenuto messaggio
     */
    public void send(String mex) {
        try {
            socket.send(handler.build(String.valueOf(mex), connection));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attivazione thread listener.
     */
    private void start() {
        Thread listener = new Thread(this);
        listener.start();
    }

    /**
     * Metodo sincronizzato usato dal server per inserire nel buffer dei comandi il nuovo comando ricevuto.
     * Quando viene inserito un comando viene notificato al listener thread che si risveglia e lo esegue.
     *
     * @param data comando
     */
    public synchronized void setClientCommands(String[] data){
        clientCommands.add(data);
        notifyAll();
    }

    /**
     * Thread listener: rimane in attesa fino a che non viene inserito un comando nel buffer, a quel punto lo legge
     * e lo esegue e lo rimuove dal buffer.
     */
    public synchronized void run() {
        String[] data;
        running.set(true);
        while(running.get()) {
            if(clientCommands.isEmpty()){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            data = clientCommands.get(0);
            clientCommands.remove(0);
            exe(data);
        }
    }

    /**
     * Metodo di esecuzione del comando sul multiplayer.
     *
     * @param data comando da eseguire
     */
    private void exe(String[] data){
        CommandType command = CommandType.valueOf(data[1]);
        if(command == CommandType.EXIT){
            multiplayer.getTeam().removePlayer(Integer.parseInt(data[0]));
            stop();
        }else{
            commands.get(command).exe(multiplayer.getFieldManager(), player.getSpaceShip(), Multiplayer.DELTA);
        }
    }

    /**
     * Thread di invio dati al client.
     * Il server invia periodicamente a tutti i client le informazioni sullo stato del gioco e sulla posizione degli
     * elementi per permettere ai client di renderizzarli.
     */
    public void sender() {
        Thread sender = new Thread(() -> {
            running.set(true);
            while (running.get()) {
                try {
                    String info = messageBuilder.getInfo();
                    if(!info.isEmpty()) {
                        send(messageBuilder.getInfo());
                    }
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        sender.start();
    }

    /**
     * Metodo che stoppa i thread del server thread quando partita è finita o un giocatore la abbandona.
     */
    private void stop() {
        running.set(false);
    }

    public AtomicBoolean isRunning(){
        return running;
    }

    public Connection getConnection(){
        return connection;
    }

}