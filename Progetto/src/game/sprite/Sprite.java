package game.sprite;

import game.environment.Coordinate;

public abstract class Sprite {

    private Coordinate coordinate;
    //image

    public Sprite(Coordinate coordinate){
        this.coordinate = coordinate;
    }


    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
