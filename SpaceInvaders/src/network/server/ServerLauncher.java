package network.server;

public class ServerLauncher {

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
