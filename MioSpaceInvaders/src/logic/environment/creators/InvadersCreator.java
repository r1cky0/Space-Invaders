package logic.environment.creators;

import logic.sprite.Coordinate;
import logic.sprite.dinamic.Invader;
import logic.sprite.unmovable.Bunker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InvadersCreator implements Creator{
    private double maxHeight;
    private double maxWidth;
    private double invaderSize;

    private List<Invader> invaders;

    public InvadersCreator(double maxHeight,double maxWidth,double invaderSize){
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.invaderSize = invaderSize;

        invaders = new CopyOnWriteArrayList<>();
    }

    /**
     * Inizializzazione degli invaders, posti in alto a sinistra nella schermata di gioco
     */
    public List<Invader> create(){
        final double HORIZONTAL_OFFSET = maxWidth/32;
        final double VERTICAL_OFFSET = maxHeight/100;
        double baseY = maxHeight/10;
        double x;

        for(int i=0; i<4; i++){
            x = Invader.HORIZONTAL_OFFSET;
            for(int j=0; j<8; j++){
                Coordinate coordinate = new Coordinate(x,baseY);
                Invader invader = new Invader(coordinate, invaderSize, 10);
                invaders.add(invader);
                x+= invaderSize + HORIZONTAL_OFFSET;
            }
            baseY+= invaderSize + VERTICAL_OFFSET;
        }
        return invaders;
    }
}
