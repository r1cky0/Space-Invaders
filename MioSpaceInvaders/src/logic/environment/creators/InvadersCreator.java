package logic.environment.creators;

import logic.sprite.Coordinate;
import logic.sprite.dinamic.Invader;
import main.Dimension;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InvadersCreator implements Creator{

    private List<Invader> invaders;

    public InvadersCreator(){
        invaders = new CopyOnWriteArrayList<>();
    }

    /**
     * Inizializzazione degli invaders, posti in alto a sinistra nella schermata di gioco
     */
    public List<Invader> create(){
        final float HORIZONTAL_OFFSET = Dimension.MAX_WIDTH/32;
        final float VERTICAL_OFFSET = Dimension.MAX_HEIGHT/100;
        float baseY = Dimension.MAX_HEIGHT/10;
        float x;

        for(int i=0; i<4; i++){
            x = Invader.HORIZONTAL_OFFSET;
            for(int j=0; j<8; j++){
                Coordinate coordinate = new Coordinate(x,baseY);
                Invader invader = new Invader(coordinate, Dimension.INVADER_WIDTH, Dimension.INVADER_HEIGHT, 10);
                invaders.add(invader);
                x+= Dimension.INVADER_WIDTH + HORIZONTAL_OFFSET;
            }
            baseY+= Dimension.INVADER_HEIGHT + VERTICAL_OFFSET;
        }
        return invaders;
    }
}
