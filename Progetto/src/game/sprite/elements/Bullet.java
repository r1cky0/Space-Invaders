package game.sprite.elements;

import game.environment.Coordinate;
import game.sprite.Sprite;

public class Bullet extends Sprite {

    public Bullet(Coordinate coordinate){
        super(coordinate);
    }

    /**Controlla se un proiettile ha colpito il punto indicato dall'argomento
     *
     * @param coordinate
     * @return
     */
    public boolean isHit(Coordinate coordinate){
        if (this.getCoordinate().equals(coordinate)){
            return true;
        }
        else return false;
    }

}
