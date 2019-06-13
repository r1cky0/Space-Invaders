package network.data;

import java.net.DatagramPacket;

public class PacketHandler{

    public PacketHandler(){}

    public String[] process(DatagramPacket packet) {
        String data = new String(packet.getData());
        System.out.println(data);

        //split fine messaggio
        String[] infos = data.split("\r");

        //split informazioni
        return infos[0].split("\\t");
    }

    public DatagramPacket build(String data, Connection connection){
        byte[] infos = data.getBytes();
        return new DatagramPacket(infos, infos.length, connection.getDestAddress(), connection.getDestPort());
    }

}
