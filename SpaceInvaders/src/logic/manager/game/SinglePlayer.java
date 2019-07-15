package logic.manager.game;

import logic.manager.field.MovingDirections;
import logic.player.Player;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.unmovable.Bunker;
import main.Dimensions;

import java.util.ArrayList;
import java.util.List;

public class SinglePlayer extends Game{
    private Player player;
    private SpaceShip spaceShip;

    public SinglePlayer(Player player) {
        this.player = player;
        spaceShip = getSpaceShip();
        spaceShip.init();
    }

    public void execCommand(Commands commands, int delta){
        switch (commands){
            case MOVE_LEFT:
                fieldManager.shipMovement(spaceShip, MovingDirections.LEFT, delta);
                break;
            case MOVE_RIGHT:
                fieldManager.shipMovement(spaceShip,MovingDirections.RIGHT, delta);
                break;
            case SHOT:
                fieldManager.shipShot(spaceShip);
                break;
            case EXIT:
                super.stopThreadInvader();
                break;
        }
    }

    public void update(int delta) {
        for (Bullet bullet : fieldManager.getInvaderBullets()) {
            bullet.move(delta);
        }
        if (spaceShip.isShipShot()) {
            spaceShip.getShipBullet().move(delta);
            fieldManager.checkSpaceShipShotCollision(getSpaceShip());
        }
        if(isBonusInvader()) {
            if (fieldManager.getBonusInvader().getX() + Dimensions.BONUSINVADER_WIDTH < Dimensions.MIN_WIDTH) {
                fieldManager.setBonusInvader(false);
            } else {
                fieldManager.getBonusInvader().moveLeft(delta);
            }
        }
        if(fieldManager.checkInvaderShotCollision(spaceShip)){
            if(spaceShip.getLife() == 0){
                gameState = States.GAMEOVER;
            }
        }
        if(fieldManager.isNewLevel()){
            super.stopThreadInvader();
            super.startThreadInvader();
            fieldManager.setNewLevel(false);
        }
        checkGameState();
    }

    private void checkGameState(){
        if (gameState == States.GAMEOVER || fieldManager.isEndReached()) {
            super.stopThreadInvader();
            if (player.checkHighscore()) {
                gameState = States.NEWHIGHSCORE;
            }else {
                gameState = States.GAMEOVER;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public SpaceShip getSpaceShip(){
        return player.getSpaceShip();
    }

    public Bullet getSpaceShipBullet(){
        return getSpaceShip().getShipBullet();
    }

    public List<Bullet> getInvadersBullet(){
        return fieldManager.getInvaderBullets();
    }

    public ArrayList<Bunker> getBunkers(){
        return fieldManager.getBunkers();
    }

    public List<Invader> getInvaders(){
        return fieldManager.getInvaders();
    }

    public BonusInvader getBonusInvader(){
        return fieldManager.getBonusInvader();
    }

    public boolean isBonusInvader(){
        return fieldManager.isBonusInvader();
    }

}


