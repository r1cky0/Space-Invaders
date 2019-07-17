package logic.player;

import java.util.concurrent.ConcurrentHashMap;

public class Team {
    private ConcurrentHashMap<Integer, Player> players;
    private int teamCurrentScore;
    private int scoreRemovedPlayer;

    public Team(){
        players = new ConcurrentHashMap<>();
        teamCurrentScore = 0;
        scoreRemovedPlayer = 0;
    }

    public void addPlayer(int ID, Player player){
        players.put(ID, player);
    }

    public void removePlayer(int ID){
        if(players.size()>1) {
            scoreRemovedPlayer += players.get(ID).getSpaceShip().getCurrentScore();
        }
        players.remove(ID);
    }

    public void clear(){
        players.clear();
        teamCurrentScore = 0;
        scoreRemovedPlayer = 0;
    }

    public void incrementLife(){
        for(Player player : players.values()){
            player.getSpaceShip().incrementLife();
        }
    }

    public void calculateTeamCurrentScore(){
        teamCurrentScore = 0;
        for(Player player : players.values()){
            teamCurrentScore += player.getSpaceShip().getCurrentScore() + scoreRemovedPlayer;
        }
    }

    public int getTeamCurrentScore(){
        return teamCurrentScore;
    }

    public ConcurrentHashMap<Integer, Player> getPlayers() {
        return players;
    }

}
