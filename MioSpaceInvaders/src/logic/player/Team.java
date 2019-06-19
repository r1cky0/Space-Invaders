package logic.player;

import java.util.HashMap;

public class Team {
    private HashMap<Integer, Player> players;
    private int teamCurrentScore;

    public Team(){
        players = new HashMap<>();
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

    public void removePlayer(Player player){
        players.remove(player);
    }

    public void clear(){
        players.clear();
        teamCurrentScore = 0;
    }

    public void addPlayer(int ID, Player player){
        players.put(ID, player);
    }

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }

    public int getTeamCurrentScore() {
        return teamCurrentScore;
    }
}
