package network.server.thread;

import logic.manager.game.Commands;
import logic.manager.game.States;
import logic.player.Player;
import network.data.Connection;
import network.data.MessageBuilder;
import network.data.PacketHandler;
import network.server.game.manager.Multiplayer;

import java.io.IOException;
import java.net.DatagramSocket;
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

    private CopyOnWriteArrayList<String[]> commands;

    private AtomicBoolean running;

    public ServerThread(Player player, Multiplayer multiplayer, Connection connection, DatagramSocket socket,
                        MessageBuilder messageBuilder) {
        this.player = player;
        this.multiplayer = multiplayer;
        this.connection = connection;
        this.socket = socket;
        this.messageBuilder = messageBuilder;
        commands = new CopyOnWriteArrayList<>();
        handler = new PacketHandler();
        running = new AtomicBoolean(true);
        start();
    }

    public void send(String mex) {
        try {
            //INVIO AL CLIENT IL SUO ID = POSIZIONE NELL'ARRAYLIST DI THREAD
            //E POI L'INIZIO DEL COUNTDOWN
            socket.send(handler.build(String.valueOf(mex), connection));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Thread listener = new Thread(this);
        listener.start();
    }

    public synchronized void setCommands(String[] data){
        commands.add(data);
        notifyAll();
    }

    public synchronized void run() {
        String[] data;
        running.set(true);
        while(running.get()) {
            if(commands.isEmpty()){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            data = commands.get(0);
            commands.remove(0);
            exe(data);
        }
    }

    public void exe(String[] data){
        switch (Commands.valueOf(data[1])) {
            case MOVE_LEFT:
            case MOVE_RIGHT:
                player.getSpaceShip().setX(Float.parseFloat(data[2]));
                break;
            case SHOT:
                multiplayer.getFieldManager().shipShot(player.getSpaceShip());
                break;
            case EXIT:
                multiplayer.getTeam().removePlayer(Integer.parseInt(data[0]));
                stop();
            default:
                break;
        }
    }

    /**
     * Invio dati al client.
     * Il server invia a tutti i client le informazioni sullo stato del gioco e sulla posizione degli
     * elementi per permettere ai client di renderizzarli.
     */
    public void sender() {
        Thread sender = new Thread(() -> {
            String info;
            running.set(true);
            while (running.get()) {
                try {
                    info = messageBuilder.getInfo();
                    socket.send(handler.build(info, connection));
                    Thread.sleep(10);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sender.start();
    }

    public void stop() {
        running.set(false);
    }

    public AtomicBoolean isRunning(){
        return running;
    }

    public Connection getConnection(){
        return connection;
    }

}