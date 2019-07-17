package network.server;

public class ServerLauncher {

    /**
     * Launcher server con porta sulla quale si attiva.
     * Ciclo che controlla la mappa di client e la svuota quando un giocatore abbandona o è terminata.
     */
    public static void main(String[] args){
        Server server = new Server(9999);
        while (true){
            server.checkEndClients();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
