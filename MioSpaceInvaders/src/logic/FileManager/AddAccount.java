package logic.FileManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AddAccount {

    public static boolean newAccount(String name, String password) throws IOException {
        String file = "res/players.txt";
        BufferedReader in = new BufferedReader(new FileReader(file));
        String riga = in.readLine();
        while (riga!=null){
            String [] componenti = riga.split("\\t");
            if(componenti[0].equals(name)){
                in.close();
                return false;
            }
            riga = in.readLine();
        }
        in.close();
        String textToAppend = (name+"\t"+password+"\t"+0+"\t"+"res/images/SpaceShip0.png"+"\n");
        Path path = Paths.get("res/players.txt");
        Files.write(path,textToAppend.getBytes(), StandardOpenOption.APPEND);
        return true;
    }

}
