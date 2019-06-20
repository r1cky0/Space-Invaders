package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import main.Dimension;

public class SpaceShip extends Sprite {
    private int life, currentScore;
    private static double HORIZONTAL_OFFSET = 0.05;

    private SpaceShipBullet shipBullet;
    private boolean shipShot;

    public SpaceShip(Coordinate coordinate, double width, double height) {
        super(coordinate, width, height);
        init();
        shipBullet = null;
        shipShot = false;
    }

    public void init(){
        Coordinate defaultCoordinate = new Coordinate(Dimension.MAX_WIDTH/2 - Dimension.SHIP_WIDTH /2,
                Dimension.MAX_HEIGHT - Dimension.SHIP_WIDTH);
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
