package logic.environment.manager.menu;

import java.util.ArrayList;

public class Customization {
    private String currentShip;
    private ArrayList<String> spaceShips;

    public Customization() {
        spaceShips = new ArrayList<>();
        currentShip = "ship0";
        initShips();
    }

    public ArrayList<String> getSpaceShips() {
        return spaceShips;
    }

    private void initShips() {
        for(int i=0; i<5; i++){
            spaceShips.add("ship"+i);
        }
    }

    public int indexOfCurrentShip(){
        return spaceShips.indexOf(currentShip);
    }

    public String getCurrentShip() {
        return currentShip;
    }

    public void setCurrentShip(int index) {
        this.currentShip = spaceShips.get(index);
    }

    public void setCurrentShip(String currentShip) {
        this.currentShip = currentShip;
    }
}
