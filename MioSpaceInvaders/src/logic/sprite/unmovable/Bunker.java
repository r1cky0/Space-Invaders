package logic.sprite.unmovable;

import logic.sprite.Coordinate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Bunker {

    private double brickSize;
    private ArrayList<Brick> bricks;

    public Bunker(double indexX, double indexY, double brickSize) {
        this.brickSize = brickSize;
        createBunker(indexX, indexY);
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    /**
     * Creazione bunker da lettura file in cui è presente la struttura da creare.
     *
     * @param indexX: indice di partenza coordinata x
     * @param indexY: indice di partenza coordinata x
     */
    private void createBunker(double indexX, double indexY) {
        bricks = new ArrayList<>();
        double indX = indexX;
        double indY = indexY;

        try {
            BufferedReader in = new BufferedReader(new FileReader("res/bunker.txt"));
            String riga = in.readLine();
            while (riga != null) {
                for (int i = 0; i < riga.length(); i++) {
                    if (riga.charAt(i) == '*') {
                        Coordinate coordinate = new Coordinate(indX, indY);
                        Brick brick = new Brick(coordinate, brickSize);
                        bricks.add(brick);
                        indX+=brickSize;
                    }
                    else {
                        indX+= brickSize;
                    }
                }
                indY-= brickSize;
                riga = in.readLine();
                indX = indexX;
            }
        }catch (IOException err) {
            System.err.println(err.getMessage());
        }
    }

}
