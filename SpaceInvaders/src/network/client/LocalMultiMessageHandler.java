package network.client;

import logic.manager.game.States;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.unmovable.Brick;

import java.util.ArrayList;

/**
 * Classe che si occupa di creare gli sprite dalle informazioni ricevuto nel messaggio inviato dal server.
 */
class LocalMultiMessageHandler {

    /**
     * Metodo che crea arraylist di invader.
     *
     * @param data info invader
     * @return arraylist invader
     */
    ArrayList<Invader> invaderCreator(String data) {
        ArrayList<Invader> invaders = new ArrayList<>();
        for (String strings : data.split("\\t")) {
            if(!strings.isEmpty()) {
                invaders.add(new Invader(converter(strings)));
            }
        }
        return invaders;
    }

    /**
     * Metodo che crea bonus invader.
     *
     * @param data info bonus invader
     * @return bonus invader
     */
    BonusInvader bonusInvaderCreator(String data){
        if(!data.isEmpty()) {
            return new BonusInvader(converter(data));
        }
        return null;
    }

    /**
     * Metodo che crea arraylist di invader bullet.
     *
     * @param data info invader bullet
     * @return arraylist invader bullet
     */
    ArrayList<InvaderBullet> invaderBulletCreator(String data) {
        ArrayList<InvaderBullet> invaderBullets = new ArrayList<>();
        for (String strings : data.split("\\t")) {
            if(!strings.isEmpty()) {
                invaderBullets.add(new InvaderBullet(converter(strings)));
            }
        }
        return invaderBullets;
    }

    /**
     * Metodo che crea arraylist di brick.
     *
     * @param data info brick
     * @return arraylist brick
     */
    ArrayList<Brick> bunkerCreator(String data) {
        ArrayList<Brick> bricks = new ArrayList<>();
        for (String strings : data.split("\\t")) {
            if(!strings.isEmpty()) {
                Brick brick = new Brick(converter(strings));
                brick.setLife(Integer.parseInt(strings.split("_")[2]));
                bricks.add(brick);
            }
        }
        return bricks;
    }

    /**
     * Metodo che crea arraylist di ship.
     * Se la ship è quella del client locale, utilizzo informazioni in locale dello shipManager.
     *
     * @param data info ship
     * @return arraylist ship
     */
    ArrayList<SpaceShip> shipCreator(String data, int ID, ShipManager shipManager) {
        ArrayList<SpaceShip> spaceShips = new ArrayList<>();
        for (String strings : data.split("\\t")) {
            if (!strings.isEmpty()) {
                if (ID == Integer.parseInt(strings.split("_")[3])) {
                    spaceShips.add(shipManager.getSpaceShip());
                    shipManager.getSpaceShip().setLife(Integer.parseInt(strings.split("_")[2]));
                } else {
                    spaceShips.add(new SpaceShip(converter(strings)));
                }
            }
        }
        return spaceShips;
    }

    /**
     * Metodo che crea arraylist di ship bullet.
     *
     * @param data info ship bullet
     * @return arraylist ship bullet
     */
    ArrayList<SpaceShipBullet> shipBulletCreator(String data){
        ArrayList<SpaceShipBullet> spaceShipBullets = new ArrayList<>();
        for (String strings : data.split("\\t")) {
            if(!strings.isEmpty()) {
                spaceShipBullets.add(new SpaceShipBullet(converter(strings)));
            }
        }
        return spaceShipBullets;
    }

    /**
     * Metodo che converte le informazioni della posizione da stringa a coordinata.
     *
     * @param data info posizione
     * @return coordinata
     */
    private Coordinate converter(String data){
        return new Coordinate(Float.parseFloat(data.split("_")[0]),
                Float.parseFloat(data.split("_")[1]));
    }

    States getGameState(String data){
        return States.valueOf(data);
    }

    int getScore(String data){
        return Integer.parseInt(data);
    }

}
