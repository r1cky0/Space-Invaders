package network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientLauncher {

    public static void main(String[] args){

        Client client = new Client("10.65.30.146", 9999);

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
