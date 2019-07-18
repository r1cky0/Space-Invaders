package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.Target;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import main.Dimensions;

/**
 * Classe che rappresenta la ship del giocatore.
 * Contiene una vita, il punteggio attuale di gioco e l'eventuale bullet quando viene sparato.
 */
public class SpaceShip extends Sprite {
    private int life, currentScore;
    private Bullet shipBullet;
    private boolean shipShot;
    private static float HORIZONTAL_OFFSET = 0.05f;

    public SpaceShip(Coordinate coordinate) {
        super(coordinate, Dimensions.SHIP_WIDTH, Dimensions.SHIP_HEIGHT, Target.SHIP);
        shipShot = false;
    }

    /**
     * Inizializzazione posizione ship al centro del campo di gioco, vita e score.
     *
     */
    public void init(){
        Coordinate defaultCoordinate = new Coordinate(Dimensions.MAX_WIDTH/2 - Dimensions.SHIP_WIDTH /2,
                Dimensions.MAX_HEIGHT - Dimensions.SHIP_WIDTH);
        setCoordinate(defaultCoordinate);
        shipShot = false;
        currentScore = 0;
        life = 3;
    }

    /**
     * Metodo per il movimento a sinistra della ship.
     *
     * @param delta velocità
     */
    public void moveLeft(int delta) {
        super.setX(super.getX() - HORIZONTAL_OFFSET*delta);
    }

    /**
     * Metodo per il movimento a destra della ship.
     *
     * @param delta velocità
     */
    public void moveRight(int delta) {
        super.setX(super.getX() + HORIZONTAL_OFFSET*delta);
    }

    /**
     * Metodo per il decremento della vita quando la ship viene colpita.
     *
     */
    public void decreaseLife() {
        life = life - 1;
    }

    /**
     * Metodo per l'incremento della vita quando si passa ad un nuovo livello.
     *
     */
    public void incrementLife() {
        if(life<3) {
            life += 1;
        }
    }

    /**
     * Metodo per l'incremento dello score quando si colpisce un alieno.
     *
     */
    public void incrementCurrentScore(int value){
        currentScore += value;
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
