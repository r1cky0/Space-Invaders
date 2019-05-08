package logic;

import logic.environment.Field;
import logic.player.Player;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;

public class TesterLogic {

    public static void main(String[] args) throws InterruptedException {

        int SHIP_SIZE = 5;
        Coordinate coordinate = new Coordinate(20,180);
        SpaceShip spaceShip = new SpaceShip(coordinate,SHIP_SIZE);
        Player player = new Player("Arr", spaceShip);
        Field field = new Field(player, 200, 200);

        //field.shipShot();
        field.invaderShot();

    }
}
