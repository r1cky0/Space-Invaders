package logic.FileManager;

import java.io.*;


public class AddHighScore {

    public static void saveHighscore(String name, int highscore) throws IOException {
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
        componenti[2] = Integer.toString(highscore);
        String newLine = componenti[0]+"\t"+componenti[1]+"\t"+componenti[2];
        String newContent = oldContent.toString().replaceAll(oldLine, newLine);
        FileWriter out = new FileWriter(fileToBeModified);
        out.write(newContent);

        in.close();
        out.close();
    }
}
