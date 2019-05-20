package logic.environment;

import logic.player.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Ranking {

    private ArrayList<Player> players;

    public Ranking(){
        players = new ArrayList<>();
    }

    public void addScore(Player player){
        if (players != null) {
            if (isInRanking(player)) {
                for (Player p: players) {
                    if(p.getName().equals(player.getName())) {
                        p.setHighScore(player.getHighScore());
                        orderRanking();
                    }
                }
            }
            else {
                players.add(player);
                orderRanking();
            }
        }
        else {
            players.add(player);
        }
    }

    public boolean isInRanking(Player player) {
        for (Player p: players) {
            if(p.getName().equals(player.getName())) {
                return true;
            }
        }
        return false;
    }

    public String toString(){
        if(players.isEmpty()){
            return "Nessun punteggio in classifica";
        }
        StringBuilder classifica = new StringBuilder();
        for(Player player: players){
            classifica.append(player.toString());
        }
        return classifica.toString();
    }

    public void orderRanking() {
        Collections.sort(players, Player::compareTo);
    }
}
