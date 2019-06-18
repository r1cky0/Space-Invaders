package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import main.Dimensions;

public class SpaceShip extends Sprite {
    private int life, currentScore;
    private double horizontalOffset = 0.5;

    private SpaceShipBullet shipBullet;
    private boolean shipShot;

    public SpaceShip(Coordinate coordinate, double size) {
        super(coordinate, size);
        init();
        shipBullet = null;
        shipShot = false;
    }

    public void init(){
        Coordinate defaultCoordinate = new Coordinate(Dimensions.MAX_WIDTH/2 - Dimensions.SHIP_SIZE/2,
                Dimensions.MAX_HEIGHT - Dimensions.SHIP_SIZE);
        setCoordinate(defaultCoordinate);
        shipBullet = null;
        shipShot = false;
        currentScore = 0;
        life = 3;
    }

    public void moveLeft(int delta) {
        super.setX(super.getX() - horizontalOffset*delta);
    }

    public void moveRight(int delta) {
        super.setX(super.getX() + horizontalOffset*delta);
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

    public void setShipBullet(SpaceShipBullet shipBullet){
        this.shipBullet = shipBullet;
    }

    public void setShipShot(boolean value){
        shipShot = value;
    }

    public SpaceShipBullet getShipBullet(){
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
