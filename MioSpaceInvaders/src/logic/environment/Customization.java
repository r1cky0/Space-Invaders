package logic.environment;

import logic.sprite.dinamic.SpaceShip;

import java.util.ArrayList;

public class Customization {

    private String currentShip;
    private ArrayList<String> spaceShips;

    public Customization() {
        spaceShips = new ArrayList<>();
        initShips();
        this.currentShip = spaceShips.get(0);
    }

    public ArrayList<String> getSpaceShips() {
        return spaceShips;
    }

    private void initShips() {
        spaceShips.add("res/images/SpaceShip0.png");
        spaceShips.add("res/images/SpaceShip1.png");
        spaceShips.add("res/images/SpaceShip2.png");
        spaceShips.add("res/images/SpaceShip3.png");
        spaceShips.add("res/images/SpaceShip4.png");
    }

    public String getCurrentShip() {
        return currentShip;
    }

    public void setCurrentShip(String currentShip) {
        this.currentShip = currentShip;
    }
}
