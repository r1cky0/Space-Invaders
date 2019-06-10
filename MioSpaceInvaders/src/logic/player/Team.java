package logic.player;

import logic.player.Player;

import java.util.ArrayList;

public class Team {

    private ArrayList<Player> players;
    private int teamHighScore;
    private int teamCurrentScore;

    public Team(){
        players = new ArrayList<>();
        teamCurrentScore = 0;
    }

    public void incrementLife(){
        for(Player player : players){
            player.getSpaceShip().incrementLife();
        }
    }

    public void calculateTeamCurrentScore(){
        for(Player player : players){
            teamCurrentScore += player.getSpaceShip().getCurrentScore();
        }
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void setTeamHighScore(int teamHighScore) {
        this.teamHighScore = teamHighScore;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getTeamHighScore() {
        return teamHighScore;
    }

    public int getTeamCurrentScore() {
        return teamCurrentScore;
    }
}
