package logic.environment;

import logic.player.Player;

import java.util.ArrayList;

public class Ranking {

    private ArrayList<Player> players;

    public Ranking(){
        players = new ArrayList<>();
    }

    public void addScore(Player player){
        for(Player p: players){
            if(p.getHighScore() < player.getHighScore()){
                players.add(players.indexOf(p),player);
            }
        }
        players.add(player);
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

}
