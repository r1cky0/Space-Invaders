package logic.FileManager;

import java.io.*;

public class SaveCustomization {

    public static void saveCustomization(String name, String shipTypePath) {  // salva la current ship nel file
        try {
            String file = "res/players.txt";
            String oldLine = "";
            File fileToBeModified = new File(file);
            StringBuilder oldContent = new StringBuilder();
            BufferedReader in = new BufferedReader(new FileReader(fileToBeModified));
            String line = in.readLine();
            while (line != null) {
                String [] componenti = line.split("\\t");
                if(componenti[0].equals(name)){
                    oldLine = line;
                }
                oldContent.append(line).append(System.lineSeparator());
                line = in.readLine();
            }
            String [] componenti = oldLine.split("\\t");
            componenti[3] = shipTypePath;
            String newLine = componenti[0]+"\t"+componenti[1]+"\t"+componenti[2]+"\t"+componenti[3];
            String newContent = oldContent.toString().replaceAll(oldLine, newLine);
            FileWriter out = new FileWriter(fileToBeModified);
            out.write(newContent);

            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println("Errore nell'apertura del file");
        }
    }
}
