package Logic.Environment;

import Logic.Player.Player;

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
            }
            else{
                players.add(player);
            }
        }

    }

    public String toString(){
        String classifica = "";

        for(Player player: players){
            classifica += player.toString();
        }

        return classifica;
    }

}
