package network.data;

import java.net.DatagramPacket;

public class PacketHandler{

    public PacketHandler(){}

    public String process(DatagramPacket packet) {
        byte[] data = packet.getData();
        String dati = new String(data);
        System.out.println(dati);
        return dati;
    }

}
