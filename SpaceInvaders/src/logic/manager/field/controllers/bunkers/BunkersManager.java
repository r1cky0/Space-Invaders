package logic.manager.field.controllers.bunkers;

import logic.manager.creators.BunkersCreator;
import logic.sprite.unmovable.Bunker;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe per la gestione dei bunker.
 */
public class BunkersManager {
    private BunkersCreator bunkersCreator;
    private ArrayList<Bunker> bunkers;

    public BunkersManager(){
        bunkersCreator = new BunkersCreator();
    }

    /**
     * Creazione lista dei bunker.
     */
    public void init(){
        bunkers = bunkersCreator.create();
    }

    public List<Bunker> getBunkers(){
        return bunkers;
    }
}
