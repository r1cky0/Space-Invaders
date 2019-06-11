package logic.environment.manager.game;

import logic.player.Player;

public class OfflineGameManager extends GameManager{

    public OfflineGameManager(double maxWidth, double maxHeight){
        super(maxWidth, maxHeight);
        }

    /**
     * Check di eventuale nuovo highscore personale
     */
    public void gameOver(Object obj){
        Player player = (Player)obj;
        if(player.getHighScore() < player.getSpaceShip().getCurrentScore()){
            player.setHighScore(player.getSpaceShip().getCurrentScore());
            setNewLevel(true);
        }else {
            setGameOver(true);
        }
    }


}
