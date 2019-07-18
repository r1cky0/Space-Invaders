package logic.manager.field;

import logic.manager.field.controllers.bunkers.BunkersManager;
import logic.manager.field.controllers.invaders.InvadersManager;
import logic.manager.field.controllers.ship.ShipManager;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.unmovable.Bunker;
import main.Dimensions;

import java.util.List;

/**
 * Classe che rappresenta il campo di gioco.
 * Gestisce tutti gli elemanti del gioco.
 */
public class FieldManager {
    private InvadersManager invadersManager;
    private ShipManager shipManager;
    private BunkersManager bunkersManager;
    //STATE
    private boolean endReached;
    private boolean newLevel;
    private Difficulty difficulty;

    public FieldManager(){
        bunkersManager = new BunkersManager();
        invadersManager = new InvadersManager();
        shipManager = new ShipManager();
        difficulty = new Difficulty(); //millisecondi pausa sparo/movimento alieni
        endReached = false;
        newLevel = false;
        initComponents();
    }

    /**
     * Inizializzazione degli elementi all'inizio del gioco.
     */
    private void initComponents(){
        invadersManager.init();
        bunkersManager.init();
    }

    /**
     * Reinizializzazione degli invaders al nuovo livello.
     *
     */
    private void nextLevel(){
        difficulty.incrementDifficulty();
        invadersManager.init();
        newLevel = true;
    }

    /**
     * Metodo per il movimento della ship che richiama ship manager.
     *
     * @param md direzione
     * @param delta velocità
     */
    public void shipMovement(SpaceShip spaceShip, MovingDirections md, int delta){
        shipManager.move(spaceShip, md, delta);
    }

    /**
     * Metodo di sparo della ship che richiama ship manager.
     *
     * @param spaceShip: ship che effettua lo sparo.
     */
    public void shipShot(SpaceShip spaceShip){
        shipManager.shot(spaceShip);
    }

    /**
     * Funzione per controllare la collisione dei proittili sparati dagli invaders: prima rispetto ai bunker
     * (e i loro brick) e poi rispetto alla ship.
     * Eliminazione del bullet nel caso in cui non collida con niente e giunga a fine schermata(y maggiore)
     *
     * @param spaceShip: ship con cui controllare collisione.
     */
    public boolean checkInvaderShotCollision(SpaceShip spaceShip) {
        for(Bullet bullet : invadersManager.getInvaderBullets()){
            for (Bunker bunker : bunkersManager.getBunkers()) {
                if (bunker.checkBrickCollision(bullet)) {
                    invadersManager.removeBullet(bullet);
                    return false;
                }
            }
            if (spaceShip.collides(bullet)) {
                spaceShip.decreaseLife();
                invadersManager.removeBullet(bullet);
                return true;
            }
            if (bullet.getY() >= Dimensions.MAX_HEIGHT) {
                invadersManager.removeBullet(bullet);
                return false;
            }
        }
        return false;
    }

    /**
     * Funzione per controllare la collisione dei proittili sparati dal giocatore: prima rispetto ai bunker
     * (e i loro brick) e poi rispetto agli invaders.
     * Eliminazione del bullet nel caso in cui non collida con niente e giunga a fine schermata(y minore)
     *
     * @param spaceShip: ship che effettua lo sparo.
     */
    public boolean checkSpaceShipShotCollision(SpaceShip spaceShip) {
        if (spaceShip.getShipBullet().getY() <= 0) {
            spaceShip.setShipShot(false);
        }

        for (Bunker bunker : bunkersManager.getBunkers()) {
            if (bunker.checkBrickCollision(spaceShip.getShipBullet())) {
                spaceShip.setShipShot(false);
                return true;
            }
        }
        for(Invader invader : invadersManager.getInvaders()){
            if (invader.collides(spaceShip.getShipBullet())) {
                spaceShip.incrementCurrentScore(invader.getValue());
                invadersManager.removeInvader(invader);
                spaceShip.setShipShot(false);
                if (invadersManager.getInvaders().isEmpty()) {
                    nextLevel();
                }
                return true;
            }
        }
        if(invadersManager.isBonus()){
            if (invadersManager.getBonusInvader().collides(spaceShip.getShipBullet())) {
                spaceShip.incrementCurrentScore(invadersManager.getBonusInvader().getValue());
                spaceShip.setShipShot(false);
                invadersManager.setBonus(false);
                return true;
            }
        }
        return false;
    }

    /**
     * Gestione del movimento degli invaders che richiama il metodo dell'Invader manager.
     *
     */
    public void moveInvaders() {
        invadersManager.checkInvaderDirection();
    }

    /**
     * Funzione di controllo di raggiungimento del livello dei bunker da parte degli alieni che implica il gameOver
     */
    public void checkInvadersEndReached(){
        double maxY = 0;
        for (Invader invader : invadersManager.getInvaders()) {
            if (maxY < invader.getY()) {
                maxY = invader.getY();
            }
            if ((maxY + Dimensions.INVADER_HEIGHT) >= (Dimensions.MAX_HEIGHT - 7 * Dimensions.BRICK_HEIGHT)) {
                endReached = true;
            }
        }
    }

    /**
     * Metodo per richiamare la creazione dell' invaders bonus nell' InvadersManager
     */
    public void bonusInvader(){
        invadersManager.createBonusInvader();
    }

    /**
     * Funzione di sparo degli invaders gestita in InvadersManager
     */
    public void invaderShot() {
        invadersManager.createInvaderBullet();
    }

    public void setNewLevel(boolean value){
        newLevel = value;
    }

    public List<Invader> getInvaders() {
        return invadersManager.getInvaders();
    }

    public BonusInvader getBonusInvader(){
        return invadersManager.getBonusInvader();
    }

    public List<Bunker> getBunkers() {
        return bunkersManager.getBunkers();
    }

    public List<Bullet> getInvaderBullets(){
        return invadersManager.getInvaderBullets();
    }

    public boolean isEndReached(){
        return endReached;
    }

    public boolean isNewLevel(){
        return newLevel;
    }

    public int getDifficulty(){
        return difficulty.getDifficulty();
    }

    public boolean isBonusInvader(){
        return invadersManager.isBonus();
    }

    public void setBonusInvader(boolean value){
        invadersManager.setBonus(value);
    }

}
