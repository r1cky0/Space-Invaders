package logic.environment.creators;

import logic.sprite.Coordinate;
import logic.sprite.dinamic.Invader;
import main.Dimensions;

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
        final float HORIZONTAL_OFFSET = Dimensions.MAX_WIDTH/32;
        final float VERTICAL_OFFSET = Dimensions.MAX_HEIGHT/100;
        float baseY = Dimensions.MAX_HEIGHT/10;
        float x;

        for(int i=0; i<4; i++){
            x = Invader.HORIZONTAL_OFFSET;
            for(int j=0; j<8; j++){
                Coordinate coordinate = new Coordinate(x,baseY);
                Invader invader = new Invader(coordinate, Dimensions.INVADER_WIDTH, Dimensions.INVADER_HEIGHT, 10);
                invaders.add(invader);
                x+= Dimensions.INVADER_WIDTH + HORIZONTAL_OFFSET;
            }
            baseY+= Dimensions.INVADER_HEIGHT + VERTICAL_OFFSET;
        }
        return invaders;
    }
}
