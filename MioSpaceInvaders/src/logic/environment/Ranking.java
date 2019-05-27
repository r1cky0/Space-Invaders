package logic.environment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Ranking {

    private HashMap<String,Integer> rank;

    public Ranking(){
        rank = new HashMap<>();
    }

    public HashMap getRank() {
        return rank;
    }

    public void createRanking() throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("res/players.txt"));
        String riga = in.readLine();
        while (riga != null) {
            String[] componenti = riga.split("\\t");
            rank.put(componenti[0], Integer.parseInt(componenti[2]));
            riga = in.readLine();
        }
        in.close();
        sortRanking();
    }

    private void sortRanking() {
        List<Map.Entry<String, Integer> > list =
                new LinkedList<>(rank.entrySet());

        list.sort(Comparator.comparing(Map.Entry::getValue));

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        rank = temp;
    }




}
