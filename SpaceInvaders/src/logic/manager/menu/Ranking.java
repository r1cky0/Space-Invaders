package logic.manager.menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Classe che rappresenta la classifica del single player dei migliori 10 giocatori.
 */
public class Ranking {
    private HashMap<String,Integer> rank;

    Ranking(){
        rank = new HashMap<>();
    }

    /**
     * Creazione della classifica da lettura del file contenente gli highscore di tutti i giocatori salvati.
     */
    public void createRanking() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("res/players.txt"));
            String riga = in.readLine();
            while (riga != null) {
                String[] componenti = riga.split("\\t");
                rank.put(componenti[0], Integer.parseInt(componenti[2]));
                riga = in.readLine();
            }
            sortRanking();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ordinamento classifica dal punteggio piú alto a quello piú basso per i primi 10 classificati
     */
    private void sortRanking() {
        Comparator<Map.Entry<String,Integer>> comp = Comparator.comparingInt(Map.Entry::getValue);
        Comparator<Map.Entry<String,Integer>> rev = comp.reversed();

        Set<Map.Entry<String,Integer>> entries = rank.entrySet();
        List<Map.Entry<String,Integer>> entrylist = new ArrayList<>(entries);
        entrylist.sort(rev);
        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> map : entrylist) {
            temp.put(map.getKey(), map.getValue());
        }
        rank = temp;
    }

    public HashMap getRank() {
        return rank;
    }
}
