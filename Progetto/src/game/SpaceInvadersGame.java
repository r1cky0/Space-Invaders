package game;

import game.environment.Coordinate;
import game.sprite.buildings.Bunker;

public class SpaceInvadersGame {

    public static void main(String[] args) {

        System.out.println("SPACE INVADERS STA ARRIVANDO!!!");

        Coordinate c = new Coordinate(2,0);
        Bunker b = new Bunker();
        b.deleteBrick(c);

        c.setX(4);
        c.setX(5);
        b.deleteBrick(c);
    }
}
