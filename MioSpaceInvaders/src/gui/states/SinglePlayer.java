package gui.states;

import logic.environment.manager.game.MovingDirections;
import logic.environment.manager.game.OfflineGameManager;
import logic.player.Player;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import org.newdawn.slick.Input;

public class SinglePlayer {

    private Player player;
    private OfflineGameManager offlineGameManager;

    public SinglePlayer(Player player, OfflineGameManager offlineGameManager) {
        this.player = player;
        this.offlineGameManager = offlineGameManager;
    }

    public void execCommand(Input input){
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            offlineGameManager.shipMovement(getSpaceShip(), MovingDirections.RIGHT);
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            offlineGameManager.shipMovement(getSpaceShip(), MovingDirections.LEFT);
        }
        if (input.isKeyPressed(Input.KEY_SPACE)) {
            offlineGameManager.shipShot(getSpaceShip());
        }
    }

    public void loop(){
        if (getSpaceShipBullet() != null) {
            player.getSpaceShip().getShipBullet().move();
        }
        if(getSpaceShipBullet() != null) {
            offlineGameManager.checkSpaceShipShotCollision(getSpaceShip());
        }
        for(Bullet bullet: offlineGameManager.getInvaderBullets()){
            bullet.move();
        }
        offlineGameManager.checkInvaderShotCollision(getSpaceShip());
    }

    public Player getPlayer() {
        return player;
    }

    public SpaceShip getSpaceShip(){
        return player.getSpaceShip();
    }

    public SpaceShipBullet getSpaceShipBullet(){
        return getSpaceShip().getShipBullet();
    }

    public OfflineGameManager getOfflineGameManager() {
        return offlineGameManager;
    }
}


