package Logic.Sprite.Static;

import Logic.Coordinate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Bunker {

    private ArrayList<Brick> bricks;

    public Bunker(double index_x, double index_y) {
        createBunker(index_x, index_y);
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public String toString() {
        String daRestituire = "";
        for (Brick b: bricks) {
            daRestituire += b.toString();
        }
        return daRestituire;
    }

    public void createBunker(double index_x, double index_y) {
        bricks = new ArrayList<>();
        double ind_x = index_x;
        double ind_y = index_y;

        try {
            BufferedReader in = new BufferedReader(new FileReader("resources/prova.txt"));
            String riga = in.readLine();
            while (riga != null) {
                for (int i = 0; i < riga.length(); i++) {
                    if (riga.charAt(i) == '*') {
                        Coordinate coordinate = new Coordinate(ind_x, ind_y);
                        Brick brick = new Brick(coordinate);
                        bricks.add(brick);
                        ind_x++;
                    }
                    else {
                        ind_x++;
                    }
                }
                ind_y--;
                riga = in.readLine();
                ind_x = index_x;
            }
        }catch (IOException err) {
            System.err.println(err.getMessage());
        }
    }

    public void addBrick(Coordinate coordinate) {
        Brick brick = new Brick(coordinate);
        bricks.add(brick);
    }

    public boolean deleteBrick(Coordinate coordinate) {
        ArrayList<Brick> toBeRemoved = new ArrayList<>();
        for (Brick b: bricks) {
            if (b.getCoordinate().equals(coordinate)) {
                toBeRemoved.add(b);
            }
        }
        return bricks.removeAll(toBeRemoved);
    }
}
