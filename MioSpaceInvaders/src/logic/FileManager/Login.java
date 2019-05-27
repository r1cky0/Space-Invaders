package logic.FileManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Login {

    public static boolean login(String name, String password)throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("res/players.txt"));
        String riga= in.readLine();
        while(riga!=null){
            String [] componenti = riga.split("\\t");
            if(componenti[0].equals(name)  && componenti[1].equals(password)){
                in.close();
                return true;
            }
            riga= in.readLine();
        }
        in.close();
        return false;
    }

}
