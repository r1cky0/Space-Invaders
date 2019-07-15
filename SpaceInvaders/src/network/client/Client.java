package network.client;

import network.data.Connection;
import network.data.PacketHandler;
import logic.manager.game.States;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client implements Runnable {
    private Connection connection;
    private DatagramSocket socket;
    private PacketHandler handler;
    private AtomicBoolean running;
    private int ID;
    private String[] rcvdata;

    public Client(String destAddress, int destPort) {
        running = new AtomicBoolean(false);
        handler = new PacketHandler();
        rcvdata = new String[7];
        ID = -1; //fintanto che il server non comunica un id al listener è settato a -1
        try {
            //aggiunta connessione verso il server
            connection = new Connection(InetAddress.getByName(destAddress), destPort);
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws SocketException {
        socket = new DatagramSocket(8888); //porta ascolto del client
        Thread listener = new Thread(this);
        listener.start();
    }

    public void send(DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException e) {
            close();
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
            byte[] buffer = new byte[3000];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                if(ID == -1){
                    ID = Integer.parseInt(handler.process(packet)[0]);
                }else {
                    rcvdata = handler.process(packet);
                }
            } catch (IOException e) {
                close();
            }
        }
    }

    public void close() {
        running.set(false);
        socket.close();
    }

    public String[] getRcvdata(){
        String[] rcvdata = this.rcvdata;
        clearBuffer();
        return rcvdata;
    }

    public void clearBuffer(){
        for(int i=0; i<rcvdata.length;i++){
            rcvdata[i] = "";
        }
    }

    public int getID(){
        return ID;
    }

    public Connection getConnection(){
        return connection;
    }


}
