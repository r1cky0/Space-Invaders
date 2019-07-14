package logic.manager.creators;

import logic.sprite.unmovable.Bunker;
import main.Dimensions;

import java.util.ArrayList;

public class BunkersCreator implements Creator{

    private ArrayList<Bunker> bunkers;

    public BunkersCreator(){
        bunkers = new ArrayList<>();
    }

    /**
     * Inizializzazione della lista di bunker.
     */
    public ArrayList<Bunker> create(){
        float baseX = (Dimensions.MAX_WIDTH - 35* Dimensions.BRICK_WIDTH)/2;
        float baseY = (Dimensions.MAX_HEIGHT - 5* Dimensions.BRICK_HEIGHT);
        float x = baseX;

        for(int i=1; i<5;i++){
            Bunker bunker = new Bunker(x,baseY);
            bunkers.add(bunker);
            x = baseX + (10* Dimensions.BRICK_WIDTH)*i;
        }
        return bunkers;
    }

}
