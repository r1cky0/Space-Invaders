package network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientLauncher {

    public static void main(String[] args){

        //INDIRIZZO IP SERVER, PORTA SERVER
        Client client = new Client("192.168.43.188", 9999);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        while(true) {
            try {
                if(br.readLine() == "\n") {
                    byte[] mex = br.readLine().getBytes();
                    client.setData(mex, mex.length);
                    client.send();


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] message = "0\tMOVE_LEFT\r".getBytes();
            client.setData(message, message.length);
            client.send();
        }


    }
}
