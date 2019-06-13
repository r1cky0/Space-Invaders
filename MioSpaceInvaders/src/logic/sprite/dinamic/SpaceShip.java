package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.dinamic.bullets.SpaceShipBullet;

public class SpaceShip extends Sprite {

    private int life, currentScore;
    private double horizontalOffset = 0.5;

    private SpaceShipBullet shipBullet;
    private boolean shipShot;

    public SpaceShip(Coordinate coordinate, double size) {
        super(coordinate, size);
        life = 3;
        currentScore = 0;
        shipBullet = null;
        shipShot = false;
    }

    public void init(){
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
