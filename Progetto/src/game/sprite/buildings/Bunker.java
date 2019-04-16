package game.sprite.buildings;

import game.environment.Coordinate;

import java.util.ArrayList;
import java.util.ListIterator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Bunker {

    private ArrayList<Brick> bricks = new ArrayList<>();
    private ListIterator<Brick> listIterator;   //Serve per iterare la lista di brick



    public Bunker(int index) {
        createBunker(index);
        listIterator = bricks.listIterator();
    }

    /**
     * Disegna il bunker da lettura file.
     * @param index_x: coordinata riferimento bunker
     */
    private void createBunker(int index) {

        int index_x = index;
        int index_y = 30;
        try{
            BufferedReader in = new BufferedReader(new FileReader("Progetto/resources/bunker.txt"));
            String riga = in.readLine();
            while (riga != null){
               for(int i=0; i<riga.length(); i++){
                    if (riga.charAt(i) == '*') {
                        Coordinate coordinate = new Coordinate(index_x,index_y);
                        Brick brick = new Brick(coordinate);
                        bricks.add(brick);
                        index_x++;
                    }
                    else{
                        index_x++;
                    }
                }
                index_y++;
                riga = in.readLine();
                index_x = index;
            }
            in.close();
        }
        catch (IOException err){
            System.err.println(err);
        }
    }

    public void stampa(){
        while (listIterator.hasNext()){
            System.out.println(listIterator.next().getCoordinate());
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
