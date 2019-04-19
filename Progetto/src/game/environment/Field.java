package game.environment;

import game.sprite.buildings.Bunker;
import game.sprite.elements.Bullet;
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
            Coordinate coordinate = new Coordinate(i,0);
            Invader invader = new Invader(coordinate, 10);
            invaders.add(invader);
        }

        int index = 20;
        for(int i=0; i<4; i++){
            Bunker bunker = new Bunker(index);
            bunkers.add(bunker);
            index = index + 36;
        }

        Coordinate start_position = new Coordinate(100,100);
        ship = new Ship("Navicella", start_position);

    }

    /**
     * Genera un bullet poco sopra la ship. Dopo ció controlla se il bullet va in collisione con un bunker(prima) o con
     * un invader(poi). Se succede, eliminiamo bullet ed elemento colpito
     */
    public void shot(){
        Coordinate bCoordinates = new Coordinate(ship.getCoordinate().getX(),ship.getCoordinate().getY()+10);
        Bullet b = new Bullet(bCoordinates);

        for(Bunker bun : bunkers){
            if(bun.deleteBrick(bCoordinates)){
                b = null;
                return;
            }
        }

        for(Invader i : invaders){
            if(bCoordinates.getX() >= i.getCoordinate().getX()-5 || bCoordinates.getX() <+ i.getCoordinate().getX()+5){
                b = null;
                invaders.remove(i);
            }
        }
    }
}
