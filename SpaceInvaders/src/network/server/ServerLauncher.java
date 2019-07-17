package network.server;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServerLauncher {

    /**
     * Launcher server con porta sulla quale si attiva.
     * Ciclo che controlla la mappa di client e la svuota quando un giocatore abbandona o è terminata.
     */
    public static void main(String[] args){
        Server server = new Server(9999);
        AtomicBoolean running = new AtomicBoolean(true);
        while (running.get()){
            server.checkEndClients();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
