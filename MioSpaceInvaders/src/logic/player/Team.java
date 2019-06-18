package logic.player;

import java.util.concurrent.CopyOnWriteArrayList;

public class Team {
    private CopyOnWriteArrayList<Player> players;
    private int teamCurrentScore;

    public Team(){
        players = new CopyOnWriteArrayList<>();
        teamCurrentScore = 0;
    }

    public void incrementLife(){
        for(Player player : players){
            player.getSpaceShip().incrementLife();
        }
    }

    public void calculateTeamCurrentScore(){
        teamCurrentScore = 0;
        for(Player player : players){
            teamCurrentScore += player.getSpaceShip().getCurrentScore();
        }
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    public void clear(){
        players.clear();
        teamCurrentScore = 0;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public CopyOnWriteArrayList<Player> getPlayers() {
        return players;
    }

    public int getTeamCurrentScore() {
        return teamCurrentScore;
    }
}
