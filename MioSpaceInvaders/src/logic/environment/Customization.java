package logic.environment;

import logic.sprite.dinamic.SpaceShip;

import java.util.ArrayList;

public class Customization {

    private ArrayList<String> spaceShips;

    public Customization() {
        spaceShips = new ArrayList<>();
        initShips();
    }

    public ArrayList<String> getSpaceShips() {
        return spaceShips;
    }

    private void initShips() {
        spaceShips.add("res/images/SpaceShip1.png");
        spaceShips.add("res/images/SpaceShip2.png");
        spaceShips.add("res/images/SpaceShip3.png");
        spaceShips.add("res/images/SpaceShip4.png");
        spaceShips.add("res/images/SpaceShip5.png");
    }
}
