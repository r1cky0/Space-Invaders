package game;

import game.environment.Coordinate;
import game.environment.Field;
import game.player.Player;
import game.sprite.buildings.Bunker;

public class SpaceInvadersGame {

    public static void main(String[] args) {

        System.out.println("SPACE INVADERS STA ARRIVANDO!!!");
        Player arr=new Player("Arrosto");
        Field f = new Field(arr);
    }
}
