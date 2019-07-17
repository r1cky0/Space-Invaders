package logic.manager.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Classe che si occupa del controllo dell'aggiunta dell'account di un nuovo giocatore.
 */
public class AddAccount {

    /**
     * Controlla che l'account non esista già e lo aggiunge al file dei giocatori, settando highscore a 0 e la
     * ship di default.
     *
     * @param name nickname
     * @param password password
     * @return valore che indica se operazione è andata a buon fine
     */
    public boolean newAccount(String name, String password){
        ReadXmlFile readXmlFile = new ReadXmlFile();
        try {
            String file = readXmlFile.read("filePlayers");
            int baseScore = 0;
            String baseShip = "ship0";
            BufferedReader in = new BufferedReader(new FileReader(file));
            String riga = in.readLine();
            while (riga != null) {
                String[] components = riga.split("\\t");
                if (components[0].equals(name)) {
                    in.close();
                    return false;
                }
                riga = in.readLine();
            }
            in.close();
            String textToAppend = (name + "\t" + password + "\t" + baseScore + "\t" + baseShip + "\n");
            Path path = Paths.get(file);
            Files.write(path, textToAppend.getBytes(), StandardOpenOption.APPEND);
            return true;
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
}
