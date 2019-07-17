package network.data;

import java.net.InetAddress;

/**
 * Classe che gestisce info connessione.
 * Contiene indirizzo IP e porta del destinatario.
 */
public class Connection {

    private InetAddress destAddress;
    private int destPort;

    public Connection(InetAddress destAddress, int destPort) {
        this.destAddress = destAddress;
        this.destPort = destPort;
    }

    public int getDestPort() {
        return this.destPort;
    }

    public InetAddress getDestAddress() {
        return this.destAddress;
    }

}
