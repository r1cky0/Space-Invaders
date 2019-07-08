package logic.environment.manager.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Login {

    public String[] tryLogin(String name, String password){
        try {
            BufferedReader in = new BufferedReader(new FileReader("res/players.txt"));
            String riga = in.readLine();
            while (riga != null) {
                String[] componenti = riga.split("\\t");
                if (componenti[0].equals(name) && componenti[1].equals(password)) {
                    in.close();
                    return componenti;
                }
                riga = in.readLine();
            }
            in.close();
            return null;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
