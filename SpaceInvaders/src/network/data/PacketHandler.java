package network.data;

import java.net.DatagramPacket;

/**
 * Classe che gestisce i pacchetti datagram nel client-server.
 */
public class PacketHandler{

    /**
     * Metodo per lo spacchettamento, splitta il messaggio con il carattere di terminazione informazioni (\r)
     * e restituisce un vettore delle informazioni già suddivise per gruppo.
     *
     * @param packet pacchetto ricevuto
     * @return vettore informazioni
     */
    public String[] process(DatagramPacket packet) {
        String data = new String(packet.getData());
        //split fine messaggio
        String[] info = data.split("\\r");
        //split informazioni
        return info[0].split("\\n");
    }

    /**
     * Metodo che costruisce il pacchetto da inviare sulla rete dopo aver aggiunto carattere di terminazione informazioni.
     *
     * @param data dati da inviare
     * @param connection oggetto che contiene ind IP e porta destinatario
     * @return pacchetto
     */
    public DatagramPacket build(String data, Connection connection){
        data += "\r"; //carattere fine messaggio
        byte[] infos = data.getBytes();
        return new DatagramPacket(infos, infos.length, connection.getDestAddress(), connection.getDestPort());
    }

}
