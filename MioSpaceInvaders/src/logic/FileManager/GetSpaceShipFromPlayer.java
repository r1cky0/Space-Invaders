package logic.FileManager;

import java.io.*;

public class GetSpaceShipFromPlayer {

    public static String getSpaceShipFromPlayer (String name) {  // da chiamare nel menu, dato il player corrente trova la ship corrispondente nel file e la usa per inizializzare la prima volta
                                                                 // la variabile "currentShip"
        String shipFromPlayer = "";

        try {
            String file = "res/players.txt";
            String correctLine = "";
            File fileToBeRead = new File(file);
            BufferedReader in = new BufferedReader(new FileReader(fileToBeRead));
            String line = in.readLine();
            while (line != null) {
                String [] componenti = line.split("\\t");
                if(componenti[0].equals(name)){
                    correctLine = line;
                }
                line = in.readLine();
            }
            String [] componenti = correctLine.split("\\t");

            shipFromPlayer += componenti[3];

            in.close();

        } catch (IOException e) {
            System.err.println("Errore nell'apertura del file");
        }

        return shipFromPlayer;
    }
}
