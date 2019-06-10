package network.data;

import java.net.DatagramPacket;

public class PacketHandler{

    private byte[] data;

    public void process(DatagramPacket packet) {
        data = packet.getData();
        String dati = new String(data);
        System.out.println(dati);
    }

}
