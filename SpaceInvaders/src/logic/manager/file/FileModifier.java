package logic.manager.file;

import java.io.*;

/**
 * Classe che si occupa della sovrascittura del file degli account.
 * Si utilizza per aggionrare l'highscore e per salvare quale sia lo spirte scelto per la spaceship.
 */
public class FileModifier {

    /**
     * Legge il file e modifica la riga del giocatore che sta giocando.
     *
     * @param name nome utente
     * @param highScore punteggio record
     * @param shipType tag ship
     */
    public void modifyFile(String name, int highScore, String shipType) {
        ReadXmlFile readXmlFile = new ReadXmlFile();
        try {
            String file = readXmlFile.read("filePlayers");
            String oldLine = "";
            File fileToBeModified = new File(file);
            StringBuilder oldContent = new StringBuilder();
            BufferedReader in = new BufferedReader(new FileReader(fileToBeModified));
            String line = in.readLine();
            while (line != null) {
                String[] components = line.split("\\t");
                if (components[0].equals(name)) {
                    oldLine = line;
                }
                oldContent.append(line).append(System.lineSeparator());
                line = in.readLine();
            }
            String[] componenti = oldLine.split("\\t");
            componenti[2] = Integer.toString(highScore);
            componenti[3] = shipType;
            String newLine = componenti[0] + "\t" + componenti[1] + "\t" + componenti[2] + "\t" + componenti[3];
            String newContent = oldContent.toString().replaceAll(oldLine, newLine);
            FileWriter out = new FileWriter(fileToBeModified);
            out.write(newContent);
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
