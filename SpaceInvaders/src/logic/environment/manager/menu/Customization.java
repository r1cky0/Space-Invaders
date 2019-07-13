package logic.environment.manager.menu;

import java.util.ArrayList;

public class Customization {
    private String currentShip;
    private ArrayList<String> spaceShips;

    Customization() {
        spaceShips = new ArrayList<>();
        currentShip = "ship0";
        initShips();
    }

    public ArrayList<String> getSpaceShips() {
        return spaceShips;
    }

    /**
     * Salvataggio dei tag con cui vengono identificati i path delle diverse ship nel file xml di configurazione
     */
    private void initShips() {
        for(int i=0; i<5; i++){
            spaceShips.add("ship"+i);
        }
    }

    public int indexOfCurrentShip(){
        return spaceShips.indexOf(currentShip);
    }

    public void setCurrentShip(String ship) {
        currentShip = ship;
    }

    public void setCurrentShip(int index) {
        currentShip = spaceShips.get(index);
    }

    public String getCurrentShip() {
        return currentShip;
    }
}
