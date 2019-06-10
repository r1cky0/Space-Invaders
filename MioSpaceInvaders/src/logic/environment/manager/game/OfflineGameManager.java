package logic.environment.manager.game;

import logic.player.Player;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.SpaceShipBullet;

public class OfflineGameManager extends GameManager{

    private Player player;

    public OfflineGameManager(Player player, double maxWidth, double maxHeight){
        super(maxWidth, maxHeight);
        this.player = player;
    }

    /**
     * Check di eventuale nuovo highscore personale
     */
    public void gameOver(){

        if(player.getHighScore() < player.getSpaceShip().getCurrentScore()){
            player.setHighScore(player.getSpaceShip().getCurrentScore());
            setNewLevel(true);
        }else {
            setGameOver(true);
        }
    }

    public void shipMovement(MovingDirections md, int delta){
        shipMovement(getSpaceShip(), md, delta);
    }

    public void shipShot(){
        shipShot(getSpaceShip());
    }

    public void checkInvaderShotCollision(){
        checkInvaderShotCollision(getSpaceShip());
    }

    public void checkSpaceShipShotCollision(){
        checkSpaceShipShotCollision(getSpaceShip());
    }

    public SpaceShip getSpaceShip(){
        return player.getSpaceShip();
    }

    public SpaceShipBullet getSpaceShipBullet(){
        return player.getSpaceShip().getShipBullet();
    }

}
