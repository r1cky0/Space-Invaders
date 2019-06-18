package logic.environment.manager.game;

import logic.environment.manager.field.FieldManager;
import logic.environment.manager.field.MovingDirections;
import logic.player.Player;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.unmovable.Bunker;
import logic.thread.ThreadInvader;

import java.util.ArrayList;
import java.util.List;

public class SinglePlayer {
    private Player player;
    private FieldManager fieldManager;

    private ThreadInvader threadInvader;
    private boolean newThread;

    public SinglePlayer(Player player, FieldManager fieldManager) {
        this.player = player;
        this.fieldManager = fieldManager;
        player.getSpaceShip().init();
        newThread = false;
    }

    public void execCommand(Commands commands, int delta){
        switch (commands){
            case MOVE_LEFT:
                fieldManager.shipMovement(player.getSpaceShip(), MovingDirections.LEFT, delta);
                break;
            case MOVE_RIGHT:
                fieldManager.shipMovement(player.getSpaceShip(),MovingDirections.RIGHT, delta);
                break;
            case SHOT:
                fieldManager.shipShot(player.getSpaceShip());
                break;
            case EXIT:
                threadInvader.stop();
                break;
        }
    }

    public void update(int delta) {
        for (Bullet bullet : fieldManager.getInvaderBullets()) {
            bullet.move(delta);
        }
        if (getSpaceShipBullet() != null) {
            player.getSpaceShip().getShipBullet().move(delta);
            fieldManager.checkSpaceShipShotCollision(getSpaceShip());
        }

        fieldManager.checkInvaderShotCollision(getSpaceShip());
        threadManager();
    }

    public GameStates checkGameState(){
        if (fieldManager.isGameOver() || fieldManager.isEndReached()) {
            threadInvader.stop();

            if (player.checkHighscore()) {
                return GameStates.NEWHIGHSCORE;
            }
            return GameStates.GAMEOVER;
        }
        return null;
    }

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

}


