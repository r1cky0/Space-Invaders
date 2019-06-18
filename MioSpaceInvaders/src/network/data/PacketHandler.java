package network.data;

import java.net.DatagramPacket;

public class PacketHandler{

    public PacketHandler(){}

    public String[] process(DatagramPacket packet) {
        String data = new String(packet.getData());

        //split fine messaggio
        String[] infos = data.split("\\r");

        //split informazioni
        return infos[0].split("\\n");
    }

    public DatagramPacket build(String data, Connection connection){
        data += "\r";
        byte[] infos = data.getBytes();
        return new DatagramPacket(infos, infos.length, connection.getDestAddress(), connection.getDestPort());
    }

}
