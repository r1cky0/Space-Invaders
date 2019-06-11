package gui.states;

import logic.environment.manager.game.MovingDirections;
import logic.environment.manager.game.OfflineGameManager;
import logic.player.Player;
import logic.sprite.dinamic.bullets.Bullet;
import org.newdawn.slick.Input;

public class SinglePlayer {

    private Player player;
    private OfflineGameManager offlineGameManager;

    public SinglePlayer(Player player, OfflineGameManager offlineGameManager) {
        this.player = player;
        this.offlineGameManager = offlineGameManager;
    }

    public void Exec(Input input){
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            offlineGameManager.shipMovement(player.getSpaceShip(), MovingDirections.RIGHT);
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            offlineGameManager.shipMovement(player.getSpaceShip(), MovingDirections.LEFT);
        }
        if (input.isKeyPressed(Input.KEY_SPACE)) {
            offlineGameManager.shipShot(player.getSpaceShip());
        }
        if (input.isKeyPressed(Input.KEY_0)) {
            offlineGameManager.invaderShot();
        }
    }

    public void Exec(){

        if (player.getSpaceShip().getShipBullet() != null) {
            player.getSpaceShip().getShipBullet().move();
        }

        if(player.getSpaceShip().getShipBullet() != null) {
            offlineGameManager.checkSpaceShipShotCollision(player.getSpaceShip());
        }

        for(Bullet bullet: offlineGameManager.getInvaderBullets()){
            bullet.move();
        }

        offlineGameManager.checkInvaderShotCollision(player.getSpaceShip());
    }
    public Player getPlayer() {
        return player;
    }

    public OfflineGameManager getOfflineGameManager() {
        return offlineGameManager;
    }
}


