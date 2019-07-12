package logic.environment.manager.game;

import logic.environment.manager.field.FieldManager;
import logic.environment.manager.field.MovingDirections;
import logic.environment.manager.file.FileModifier;
import logic.environment.manager.menu.Customization;
import logic.player.Player;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.unmovable.Bunker;
import logic.thread.ThreadInvader;
import main.Dimensions;

import java.util.ArrayList;
import java.util.List;

public class SinglePlayer {
    private Player player;
    private SpaceShip ship;
    private FieldManager fieldManager;

    private ThreadInvader threadInvader;
    private boolean newThread;

    public SinglePlayer(Player player, FieldManager fieldManager) {
        this.player = player;
        this.ship = getSpaceShip();
        this.fieldManager = fieldManager;
        player.getSpaceShip().init();
        newThread = false;
    }

    public void execCommand(Commands commands, int delta){
        switch (commands){
            case MOVE_LEFT:
                fieldManager.shipMovement(ship, MovingDirections.LEFT, delta);
                break;
            case MOVE_RIGHT:
                fieldManager.shipMovement(ship,MovingDirections.RIGHT, delta);
                break;
            case SHOT:
                fieldManager.shipShot(ship);
                break;
            case EXIT:
                threadInvader.stop();
                break;
        }
    }

    public boolean update(int delta) {
        for (Bullet bullet : fieldManager.getInvaderBullets()) {
            bullet.move(delta);
        }
        if (ship.isShipShot()) {
            ship.getShipBullet().move(delta);
            fieldManager.checkSpaceShipShotCollision(getSpaceShip());
        }
        if(isBonusInvader() &&
                !(fieldManager.getBonusInvader().getX() + Dimensions.INVADER_WIDTH < Dimensions.MIN_WIDTH)){
            fieldManager.getBonusInvader().moveLeft(delta);
        }

        boolean collision = fieldManager.checkInvaderShotCollision(ship);
        threadManager();
        return collision;
    }

    public States checkGameState(FileModifier fileModifier, Customization customization){
        if (fieldManager.isGameOver() || fieldManager.isEndReached()) {
            threadInvader.stop();

            if (player.checkHighscore(fileModifier,customization)) {
                return States.NEWHIGHSCORE;
            }
            return States.GAMEOVER;
        }
        return null;
    }

    /**
     * Attivaziome thread di aggiornamento degli invader (movimento e sparo) e check di completamento del livello
     */
    private void threadManager(){
        if (!newThread) {
            threadInvader = new ThreadInvader(fieldManager.getDifficulty(), fieldManager);
            threadInvader.start();
            newThread = true;
        }
        if (fieldManager.isNewLevel()) {
            threadInvader.stop();
            fieldManager.setNewLevel(false);
            newThread = false;
        }
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

    public List<InvaderBullet> getInvadersBullet(){
        return fieldManager.getInvaderBullets();
    }

    public ArrayList<Bunker> getBunkers(){
        return fieldManager.getBunkers();
    }

    public List<Invader> getInvaders(){
        return fieldManager.getInvaders();
    }

    public BonusInvader getSpecialInvader(){
        return fieldManager.getBonusInvader();
    }

    public boolean isBonusInvader(){
        return fieldManager.isBonusInvader();
    }

}


