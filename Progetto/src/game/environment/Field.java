package game.environment;

import game.sprite.buildings.Bunker;
import game.sprite.elements.Invader;
import game.sprite.elements.Ship;

import java.util.ArrayList;

public class Field {

    private Ship ship;
    private ArrayList<Invader> invaders;
    private ArrayList<Bunker> bunkers;
    private Coordinate size;

    public Field(){

        for(int i=0; i<32; i++){
            Coordinate coordinate = new Coordinate(i,i+1,i,i+1);
            Invader invader = new Invader(coordinate, 10);
            invaders.add(invader);
        }

        for(int i=0; i<4; i++){
            Bunker bunker = new Bunker();
            bunkers.add(bunker);
        }

        Coordinate start_position = new Coordinate(100,101,100,101);
        ship = new Ship("Navicella", start_position);

    }
}
