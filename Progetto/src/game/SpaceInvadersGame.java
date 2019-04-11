package game;

import game.environment.Coordinate;
import game.sprite.buildings.Bunker;

public class SpaceInvadersGame {

    public static void main(String[] args) {

        System.out.println("SPACE INVADERS STA ARRIVANDO!!!");

        Coordinate c = new Coordinate(0,1,0,1);
        Bunker b = new Bunker();
        b.stampa();
        b.deleteBrick(c);
        b.stampa();

        c.setX1(4);
        c.setX2(5);
        b.deleteBrick(c);
        b.stampa();
    }
}
