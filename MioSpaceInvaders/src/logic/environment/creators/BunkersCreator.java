package logic.environment.creators;

import logic.sprite.unmovable.Bunker;
import main.Dimensions;

import java.util.ArrayList;

public class BunkersCreator implements Creator{

    private ArrayList<Bunker> bunkers;

    public BunkersCreator(){
        bunkers = new ArrayList<>();
    }

    /**
     * Inizializzazione della lista di bunker, con attenzione particolare alla distanza tra ognuno di essi
     * proporzionale alla dimensione della schermata di gioco
     */
    public ArrayList<Bunker> create(){
        double baseX = (Dimensions.MAX_WIDTH - 35* Dimensions.BRICK_SIZE)/2;
        double baseY = (Dimensions.MAX_HEIGHT - 4* Dimensions.BRICK_SIZE);
        double x = baseX;

        for(int i=1; i<5;i++){
            Bunker bunker = new Bunker(x,baseY, Dimensions.BRICK_SIZE);
            bunkers.add(bunker);
            x = baseX + (10* Dimensions.BRICK_SIZE)*i;
        }
        return bunkers;
    }
}
