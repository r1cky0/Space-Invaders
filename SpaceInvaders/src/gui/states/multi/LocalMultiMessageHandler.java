package gui.states.multi;

import logic.manager.game.States;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.unmovable.Brick;

import java.util.ArrayList;

public class LocalMultiMessageHandler {

    public States getGameState(String data){
        return States.valueOf(data);
    }

    public int getScore(String data){
        return Integer.parseInt(data);
    }

    public ArrayList<Invader> invaderCreator(String data) {
        ArrayList<Invader> invaders = new ArrayList<>();
        for (String strings : data.split("\\t")) {
            invaders.add(new Invader(converter(strings)));
        }
        return invaders;
    }

    public BonusInvader bonusInvaderCreator(String data){
        if(!data.equals("")){
            return new BonusInvader(converter(data));
        }
        return null;
    }

    public ArrayList<InvaderBullet> invaderBulletCreator(String data) {
        ArrayList<InvaderBullet> invaderBullets = new ArrayList<>();
        for (String strings : data.split("\\t")) {
            if(!strings.equals("")) {
                invaderBullets.add(new InvaderBullet(converter(strings)));
            }
        }
        return invaderBullets;
    }

    public ArrayList<Brick> bunkerCreator(String data) {
        ArrayList<Brick> bricks = new ArrayList<>();
        for (String strings : data.split("\\t")) {
            if(!strings.equals("")) {
                Brick brick = new Brick(converter(strings));
                brick.setLife(Integer.parseInt(strings.split("_")[2]));
                bricks.add(brick);
            }
        }
        return bricks;
    }

    public ArrayList<SpaceShip> shipCreator(String data, int ID, ShipManager shipManager) {
        ArrayList<SpaceShip> spaceShips = new ArrayList<>();
        for (String strings : data.split("\\t")) {
            if (!strings.equals("")) {
                if (ID == Integer.parseInt(strings.split("_")[0])) {
                    spaceShips.add(shipManager.getSpaceShip());
                    shipManager.getSpaceShip().setLife(Integer.parseInt(strings.split("_")[3]));
                } else {
                    spaceShips.add(new SpaceShip(converter(strings)));
                }
            }
        }
        return spaceShips;
    }

    public ArrayList<SpaceShipBullet> shipBulletCreator(String data){
        ArrayList<SpaceShipBullet> spaceShipBullets = new ArrayList<>();
        for (String strings : data.split("\\t")) {
            if(!strings.equals("")) {
                spaceShipBullets.add(new SpaceShipBullet(converter(strings)));
            }
        }
        return spaceShipBullets;
    }

    public Coordinate converter(String data){
        return new Coordinate(Float.parseFloat(data.split("_")[0]),
                Float.parseFloat(data.split("_")[1]));
    }

}
