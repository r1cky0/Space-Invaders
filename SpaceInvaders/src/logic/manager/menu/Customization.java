package logic.manager.menu;

import java.util.ArrayList;

/**
 * Classe che rappresenta il menu di persionalizzazione della ship del giocatore.
 * Contiene ArrayList di ship che l'utente può scegliere e il tag della ship corrente.
 */
public class Customization {
    private String currentShip;
    private ArrayList<String> spaceShips;

    Customization() {
        spaceShips = new ArrayList<>();
        currentShip = "ship0";
        initShips();
    }

    /**
     * Salvataggio dei tag con cui vengono identificati i path delle diverse ship nel file xml
     */
    private void initShips() {
        for(int i=0; i<5; i++){
            spaceShips.add("ship"+i);
        }
    }

    /**
     *
     * @return indice ship corrente
     */
    public int indexOfCurrentShip(){
        return spaceShips.indexOf(currentShip);
    }

    public void setCurrentShip(String ship) {
        currentShip = ship;
    }

    public void setCurrentShip(int index) {
        currentShip = spaceShips.get(index);
    }

    public ArrayList<String> getSpaceShips() {
        return spaceShips;
    }

    public String getCurrentShip() {
        return currentShip;
    }
}
