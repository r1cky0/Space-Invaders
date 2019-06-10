package network.client;

import network.data.Connection;
import network.data.PacketHandler;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe client UDP
 */
public class Client implements Runnable {

    private Connection connection;
    private DatagramSocket socket;
    private AtomicBoolean running;
    private PacketHandler handler;

    //INFORMAZIONI POSIZIONE SHIP, AZIONE SPARO
    private byte[] snddata;

    private Thread client;


    public Client(String destAddress, int destPort) {

        running = new AtomicBoolean(false);
        handler = new PacketHandler();
        try {
            //apertura connessione verso il server
            connection = new Connection(InetAddress.getByName(destAddress), destPort);
            this.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws SocketException {
        socket = new DatagramSocket(8888);
        client = new Thread(this);
        client.start();
    }

    /**
     * Invio dati dal client
     */
    public void send() {
        DatagramPacket packet = new DatagramPacket(snddata, snddata.length, connection.getDestAddress(), connection.getDestPort());
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ricezione dati dal server.
     * Il server invia ai client le informazioni sullo stato del gioco e sulla posizione degli
     * elementi per permettere ai client di renderizzare il tutto.
     */
    @Override
    public void run() {
        running.set(true);
        while(running.get()) {
            byte[] buffer = new byte[2048];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                //Packet contiene dati ricevuti
                handler.process(packet);
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
     * Chiusura connessione del client
     */
    public void close() {
        socket.close();
        running.set(false);
    }

}
