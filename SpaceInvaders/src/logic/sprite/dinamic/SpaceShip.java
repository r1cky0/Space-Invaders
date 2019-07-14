package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import main.Dimensions;

public class SpaceShip extends Sprite {
    private int life, currentScore;
    private static float HORIZONTAL_OFFSET = 0.05f;

    private Bullet shipBullet;
    private boolean shipShot;

    public SpaceShip(Coordinate coordinate) {
        super(coordinate, Dimensions.SHIP_WIDTH, Dimensions.SHIP_HEIGHT);
        init();
        shipShot = false;
    }

    public void init(){
        Coordinate defaultCoordinate = new Coordinate(Dimensions.MAX_WIDTH/2 - Dimensions.SHIP_WIDTH /2,
                Dimensions.MAX_HEIGHT - Dimensions.SHIP_WIDTH);
        setCoordinate(defaultCoordinate);
        shipBullet = null;
        shipShot = false;
        currentScore = 0;
        life = 3;
    }

    public void moveLeft(int delta) {
        super.setX(super.getX() - HORIZONTAL_OFFSET*delta);
    }

    public void moveRight(int delta) {
        super.setX(super.getX() + HORIZONTAL_OFFSET*delta);
    }

    public void decreaseLife() {
        life = life - 1;
    }

    public void incrementLife() {
        if(life<3) {
            life += 1;
        }
    }

    public void incrementCurrentScore(int value){
        currentScore += value;
    }

    public void setLife(int life){
        this.life = life;
    }

    public void setShipBullet(Coordinate coordinate) {
        shipBullet = new SpaceShipBullet(coordinate);
    }

    public void setShipShot(boolean value){
        shipShot = value;
    }

    public Bullet getShipBullet(){
        return shipBullet;
    }

    public boolean isShipShot(){
        return shipShot;
    }

    public int getLife() {
        return life;
    }

    public int getCurrentScore(){
        return currentScore;
    }

}
