package logic.environment.manager.game;

import logic.player.Player;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.SpaceShipBullet;

public class OfflineGameManager extends GameManager{

    public OfflineGameManager(Player player, double maxWidth, double maxHeight){
        super(maxWidth, maxHeight);
    }

    /**
     * Check di eventuale nuovo highscore personale
     */
    public void gameOver(Object obj){
        Player player = (Player) obj;
        if(player.getHighScore() < player.getSpaceShip().getCurrentScore()){
            player.setHighScore(player.getSpaceShip().getCurrentScore());
            setNewLevel(true);
        }else {
            setGameOver(true);
        }
    }
}
