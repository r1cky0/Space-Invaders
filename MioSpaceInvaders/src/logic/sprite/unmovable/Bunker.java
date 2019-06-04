package logic.sprite.unmovable;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.dinamic.Bullet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

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
     * Creazione bunker da lettura file in cui è presente la struttura da creare. Il file, chiamato bunkers.txt e
     * inserito nella directry res, é costituito da alternanza di asterischi e spazi
     * @param indexX: indice di partenza coordinata x
     * @param indexY: indice di partenza coordinata x
     */
    private void createBunker(double indexX, double indexY) {
        bricks = new ArrayList<>();
        double indX = indexX;
        double indY = indexY;

        try {
            BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\dario\\IdeaProjects\\Progetto 2\\Progetto-C19\\MioSpaceInvaders\\res\\bunker.txt"));
            String riga = in.readLine();
            while (riga != null) {
                for (int i = 0; i < riga.length(); i++) {
                    if (riga.charAt(i) == '*') {
                        Coordinate coordinate = new Coordinate(indX, indY);
                        Brick brick = new Brick(coordinate, brickSize);
                        bricks.add(brick);
                        indX += brickSize;
                    } else {
                        indX += brickSize;
                    }
                }
                indY -= brickSize;
                riga = in.readLine();
                indX = indexX;
            }
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
    }

    /**
     * Iteriamo tutti i brick che costituiscono il bunker per attuare un check di collisioni con lo sprite passsato
     * come parametro
     * @param sprite
     * @return
     */
    public boolean checkBrickCollision(Sprite sprite) {
        ListIterator<Brick> brickIter = bricks.listIterator();

        while (brickIter.hasNext()) {
            Brick brick = brickIter.next();
            if (brick.collides(sprite)) {
                brick.decreaseLife();
                if (brick.getLife() == 0) {
                    brickIter.remove();
                }
                return true;
            }
        }
        return false;
    }
}
