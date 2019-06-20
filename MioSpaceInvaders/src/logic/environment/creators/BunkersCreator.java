package logic.environment.creators;

import logic.sprite.unmovable.Bunker;
import main.Dimension;

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
        double baseX = (Dimension.MAX_WIDTH - 35*Dimension.BRICK_WIDTH)/2;
        double baseY = (Dimension.MAX_HEIGHT - 5*Dimension.BRICK_HEIGHT);
        double x = baseX;

        for(int i=1; i<5;i++){
            Bunker bunker = new Bunker(x,baseY);
            bunkers.add(bunker);
            x = baseX + (10*Dimension.BRICK_WIDTH)*i;
        }
        return bunkers;
    }
}
