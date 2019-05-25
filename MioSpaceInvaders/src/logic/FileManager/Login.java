package logic.FileManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Login {
    public boolean login(String name, String password)throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("players.txt"));
        String riga= in.readLine();
        while(riga!=null){
            String [] componenti = riga.split("\\t");
            if(componenti[0]== name && componenti[1]== password){
                in.close();
                return true;
            }
        }
        in.close();
        return false;
    }

}
