package game.sprite.buildings;

import game.environment.Coordinate;

import java.util.ArrayList;
import java.util.ListIterator;

public class Bunker {

    private ArrayList<Brick> bricks;
    private ListIterator<Brick> listIterator;   //Serve per iterare la lista di brick

    public Bunker(){

        bricks = new ArrayList<>();
        for(int i=0; i<10;i++){
            Coordinate coordinate = new Coordinate(i,i+1,0,1);
            Brick b = new Brick(coordinate);
            bricks.add(b);
        }
    }

    /**
     *  Itera la lista fino a che non trova un brick con coordinata uguale
     *  a quella passata e lo rimuove.
     *
     * @param coordinate: coordinata del brick colpito
     */
    public void deleteBrick(Coordinate coordinate){

        listIterator = bricks.listIterator();
        while (listIterator.hasNext()) {
            if(listIterator.next().getCoordinate().equals(coordinate)){
                listIterator.remove();
            }
        }
    }

}
