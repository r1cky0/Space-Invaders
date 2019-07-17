package network.client;

import logic.manager.game.States;
import logic.sprite.Coordinate;
import logic.sprite.Target;
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

    private int life;

    public LocalMultiMessageHandler(){
        life = 3;
    }

    /**
     * Metodo che crea arraylist di invader.
     *
     * @param data info invader
     * @return arraylist invader
     */
    public ArrayList<Invader> invaderCreator(String data) {
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
    public BonusInvader bonusInvaderCreator(String data){
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
    public ArrayList<InvaderBullet> invaderBulletCreator(String data) {
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
    public ArrayList<Brick> bunkerCreator(String data) {
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
    public ArrayList<SpaceShip> shipCreator(String data, int ID) {
        ArrayList<SpaceShip> spaceShips = new ArrayList<>();
        boolean isLive = false;
        for (String strings : data.split("\\t")) {
            if (!strings.isEmpty()) {
                if (ID == Integer.parseInt(strings.split("_")[3])) {
                    spaceShips.add(new SpaceShip(converter(strings)));
                    life = Integer.parseInt(strings.split("_")[2]);
                    isLive = true;
                } else {
                    SpaceShip partnerShip = new SpaceShip(converter(strings));
                    partnerShip.setTarget(Target.SHIPMULTI);
                    spaceShips.add(partnerShip);
                }
            }
        }
        if(!isLive){
            life = 0;
        }
        return spaceShips;
    }

    /**
     * Metodo che crea arraylist di ship bullet.
     *
     * @param data info ship bullet
     * @return arraylist ship bullet
     */
    public ArrayList<SpaceShipBullet> shipBulletCreator(String data){
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

    public States getGameState(String data){
        return States.valueOf(data);
    }

    public int getScore(String data){
        return Integer.parseInt(data);
    }

    public int getLife(){
        return life;
    }

}
