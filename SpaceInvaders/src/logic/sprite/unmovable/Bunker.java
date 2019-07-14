package logic.sprite.unmovable;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import main.Dimensions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class Bunker {
    private CopyOnWriteArrayList<Brick> bricks;

    public Bunker(float indexX, float indexY) {
        createBunker(indexX, indexY);
    }

    /**
     * Creazione bunker da lettura file in cui è presente la struttura da creare. Il file, chiamato bunker.txt e
     * inserito nella directry res, é costituito da alternanza di asterischi e spazi
     *
     * @param indexX: indice di partenza coordinata x
     * @param indexY: indice di partenza coordinata x
     */
    private void createBunker(float indexX, float indexY) {
        bricks = new CopyOnWriteArrayList<>(  );
        float indX = indexX;
        float indY = indexY;
        try {
            BufferedReader in = new BufferedReader(new FileReader("res/bunker.txt"));
            String riga = in.readLine();
            while (riga != null) {
                for (int i = 0; i < riga.length(); i++) {
                    if (riga.charAt(i) == '*') {
                        Coordinate coordinate = new Coordinate(indX, indY);
                        Brick brick = new Brick(coordinate);
                        bricks.add(brick);
                        indX += Dimensions.BRICK_WIDTH;
                    } else {
                        indX += Dimensions.BRICK_WIDTH;
                    }
                }
                indY -= Dimensions.BRICK_HEIGHT;
                riga = in.readLine();
                indX = indexX;
            }
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    /**
     * Iteriamo tutti i brick che costituiscono il bunker per attuare un check di collisioni con lo sprite passato
     * come parametro
     *
     * @param sprite: oggetto con cui controllare la collisione
     */
    public boolean checkBrickCollision(Sprite sprite) {
        for(Brick brick : bricks){
            if (brick.collides(sprite)) {
                brick.decreaseLife();
                if (brick.getLife() == 0) {
                    bricks.remove(brick);
                }
                return true;
            }
        }
        return false;
    }

    public CopyOnWriteArrayList<Brick> getBricks() {
        return bricks;
    }
}
