package logic.environment.manager.game;

import logic.player.Team;

public class OnlineGameManager extends GameManager{

    public OnlineGameManager(double maxWidth, double maxHeight){
        super(maxWidth, maxHeight);
    }

    /**
     * Check di eventuale nuovo highscore di squadra
     */
    public void gameOver(Object obj){
        Team team = (Team) obj;
        if(team.getTeamHighScore() < team.getTeamCurrentScore()){
            team.setTeamHighScore(team.getTeamCurrentScore());
            setNewLevel(true);
        }else {
            setGameOver(true);
        }
    }

}
