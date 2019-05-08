package logic.environment;

import logic.player.Player;

import java.util.ArrayList;

public class Classifica {

    private ArrayList<Player> players;

    public Classifica(){
        players = new ArrayList<>();
    }

    public void addScore(Player player){
        for(Player p: players){
            if(p.getHighscore() < player.getHighscore()){
                players.add(players.indexOf(p),player);
                break;
            }
        }
        //Questo fatto fuori da for perché altrimenti
        //avremmo aggiunto piú volte lo stesso punteggio
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
