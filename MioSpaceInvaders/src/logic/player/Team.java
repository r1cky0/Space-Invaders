package logic.player;

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
        teamCurrentScore = 0;
        for(Player player : players){
            teamCurrentScore += player.getSpaceShip().getCurrentScore();
        }
    }

    /**
     * Check di eventuale nuovo highscore
     */
    public boolean checkHighscore(){
        if(teamHighScore < teamCurrentScore){
            teamHighScore = teamCurrentScore;
            return true;
        }
        return false;
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void setTeamHighScore(int teamHighScore) {
        this.teamHighScore = teamHighScore;
    }

    public int getTeamHighScore() {
        return teamHighScore;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getTeamCurrentScore() {
        return teamCurrentScore;
    }
}
