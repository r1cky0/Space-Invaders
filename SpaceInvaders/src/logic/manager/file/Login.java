package logic.manager.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe che si occupa del controllo del login del giocatore.
 */
public class Login {

    /**
     * Legge dal file dei giocatori nickname e password e controlla che corrispondano con quelli inseriti.
     * Se il login va a buon fine restituisce la riga corrispondente del giocatore contenente highscore e current ship.
     *
     * @param name nickname
     * @param password password
     * @return vettore informazioni giocatore
     */
    public String[] tryLogin(String name, String password){
        try {
            BufferedReader in = new BufferedReader(new FileReader("res/players.txt"));
            String riga = in.readLine();
            while (riga != null) {
                String[] components = riga.split("\\t");
                if (components[0].equals(name) && components[1].equals(password)) {
                    in.close();
                    return components;
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
