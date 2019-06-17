package network.client;

import logic.player.Player;
import logic.sprite.dinamic.Invader;
import network.data.Connection;
import network.data.PacketHandler;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe listener UDP
 */
public class Client implements Runnable {

    private Connection connection;
    private DatagramSocket socket;
    private AtomicBoolean running;
    private PacketHandler handler;
    private int ID;
    private boolean initialization;
    private Player player;
    //INFORMAZIONI POSIZIONE SHIP, AZIONE SPARO
    private byte[] snddata;

    private Thread listener;


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
        socket = new DatagramSocket(8888);
        listener = new Thread(this);
        listener.start();
        send(handler.build(player.getName(), connection));
    }

    /**
     * Invio dati dal listener
     */
    public void send(DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
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
            byte[] buffer = new byte[2048];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                if(initialization){
                    ID = Integer.parseInt(handler.process(packet)[0]);
                    initialization = false;
                }else {
                    //Packet contiene dati ricevuti
                    //che client deve renderizzare
                    String []prova = handler.process(packet);
                    for (String stringa : prova) System.out.println(stringa);
                }
            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }
    }

    public void setData(byte[] data, int length){
        snddata = new byte[length];
        this.snddata = data;
    }

    /**
     * Chiusura connessione del listener
     */
    public void close() {
        socket.close();
        running.set(false);
    }

}
