package logic.FileManager;

import java.io.*;

public class AddPlayer  {

    public boolean newPlayer(String name, String password)throws IOException {
        String file = "players.txt";
        BufferedReader in = new BufferedReader(new FileReader(file));
        String riga = in.readLine();
        while (riga!=null){
            String [] componenti = riga.split("\\t");
            if(componenti[0]== name){
                in.close();
                return false;
            }
        }
        PrintWriter out= new PrintWriter(new FileWriter(file));
        out.println(name+"\t"+password);
        out.close();
        return true;
    }
}
