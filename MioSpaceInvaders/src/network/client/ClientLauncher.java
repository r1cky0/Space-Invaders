package network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientLauncher {

    public static void main(String[] args){

        //INDIRIZZO IP SERVER, PORTA SERVER
        Client client = new Client("192.168.43.89", 9999);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            try {
                client.setData(br.readLine().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            client.send();
        }
    }
}
