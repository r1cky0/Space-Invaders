package logic.environment.manager.menu;

import logic.environment.manager.file.ReadXmlFile;

import java.util.ArrayList;

public class Customization {

    private String currentShip;
    private ArrayList<String> spaceShips;

    public Customization(String currentTypeShip) {
        spaceShips = new ArrayList<>();
        initShips();
        this.currentShip = currentTypeShip;
    }

    public ArrayList<String> getSpaceShips() {
        return spaceShips;
    }

    private void initShips() {
        for(int i=0; i<5; i++){
            spaceShips.add(ReadXmlFile.read("ship"+i));
        }
    }

    public String getCurrentShip() {
        return currentShip;
    }

    public void setCurrentShip(String currentShip) {
        this.currentShip = currentShip;
    }
}
