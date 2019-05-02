package Logic.Sprite.Dinamic;

import Logic.Coordinate;
import Logic.Sprite.Dinamic.Interface.Movable;
import Logic.Sprite.Sprite;

public class Invader extends AbstractMovable {

    private int value;

    public Invader(Coordinate coordinate, int value) {
        super(coordinate);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String toString() {
        return "{" + "value=" + value + '}' + super.toString();
    }

}
