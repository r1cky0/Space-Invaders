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
        spaceShips.add(ReadXmlFile.readXmlFile(0, "ship"));
        spaceShips.add(ReadXmlFile.readXmlFile(1, "ship"));
        spaceShips.add(ReadXmlFile.readXmlFile(2, "ship"));
        spaceShips.add(ReadXmlFile.readXmlFile(3, "ship"));
        spaceShips.add(ReadXmlFile.readXmlFile(4, "ship"));
    }

    public String getCurrentShip() {
        return currentShip;
    }

    public void setCurrentShip(String currentShip) {
        this.currentShip = currentShip;
    }
}
