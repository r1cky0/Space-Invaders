package logic.player;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Team {
    private ConcurrentHashMap<Integer, Player> players;
    private int teamCurrentScore;

    public Team(){
        players = new ConcurrentHashMap<>();
        teamCurrentScore = 0;
    }

    public void incrementLife(){
        for(Player player : players.values()){
            player.getSpaceShip().incrementLife();
        }
    }

    public void calculateTeamCurrentScore(){
        teamCurrentScore = 0;
        for(Player player : players.values()){
            teamCurrentScore += player.getSpaceShip().getCurrentScore();
        }
    }

    public void removePlayer(int ID){
        players.remove(ID);
    }

    public void clear(){
        players.clear();
        teamCurrentScore = 0;
    }

    public void addPlayer(int ID, Player player){
        players.put(ID, player);
    }

    public int getTeamCurrentScore(){
        return teamCurrentScore;
    }

    public ConcurrentHashMap<Integer, Player> getPlayers() {
        return players;
    }

}
