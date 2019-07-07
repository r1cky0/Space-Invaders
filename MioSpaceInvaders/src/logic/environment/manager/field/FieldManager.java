package logic.environment.manager.field;

import logic.environment.creators.BunkersCreator;
import logic.environment.creators.InvadersCreator;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.unmovable.Bunker;
import main.Dimensions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class FieldManager {

    private InvadersCreator invadersCreator;
    private BunkersCreator bunkersCreator;

    //STATE
    private boolean gameOver;
    private boolean endReached;
    private boolean newLevel;

    private List<Invader> invaders;
    private ArrayList<Bunker> bunkers;
    private List<InvaderBullet> invaderBullets;
    private MovingDirections md;
    private boolean goDown;
    private Difficulty difficulty;

    public FieldManager(){
        invadersCreator = new InvadersCreator();
        bunkersCreator = new BunkersCreator();

        bunkers = new ArrayList<>();
        invaderBullets = new CopyOnWriteArrayList<>();
        invaders = new CopyOnWriteArrayList<>();
        md = MovingDirections.RIGHT;
        goDown = false;

        difficulty = new Difficulty(); //millisecondi pausa sparo/movimento alieni

        endReached = false;
        gameOver = false;
        newLevel = false;

        initComponents();
    }

    private void initComponents(){
        //inizializzazione di tutti gli elementi all'inizio del gioco
        invaders = invadersCreator.create();
        bunkers = bunkersCreator.create();
    }

    /**
     * Reinizializzazione degli invaders e incremento life ship al nuovo livello
     */
    private void nextLevel(SpaceShip spaceShip){
        difficulty.incrementDifficulty();
        spaceShip.incrementLife();
        md = MovingDirections.RIGHT;
        invaders = invadersCreator.create();
        newLevel = true;
    }

    public void shipMovement(SpaceShip spaceShip, MovingDirections md, int delta){

        if(((spaceShip.getX() + Dimensions.SHIP_WIDTH) < Dimensions.MAX_WIDTH) && (md == MovingDirections.RIGHT)){
            spaceShip.moveRight(delta);
        }

        if((spaceShip.getX() > Dimensions.MIN_WIDTH) && (md == MovingDirections.LEFT)){
            spaceShip.moveLeft(delta);
        }

    }

    public void shipShot(SpaceShip spaceShip){
        if(!spaceShip.isShipShot()) {
            Coordinate coordinate = new Coordinate(spaceShip.getShape().getCenterX() - Dimensions.BULLET_WIDTH /2,
                    spaceShip.getY());
            spaceShip.setShipBullet(coordinate);
            spaceShip.setShipShot(true);
        }
    }

    /**
     * Funzione per controllare la collisione dei proittili sparati dagli invaders: prima rispetto ai bunker(e i loro
     * brick) e poi rispetto alla ship. Eliminazione del bullet nel caso in cui non collida con niente e giunga a
     * fine schermata(y maggiore)
     */
    public boolean checkInvaderShotCollision(SpaceShip spaceShip) {
        for(Bullet bullet : invaderBullets){
            for (Bunker bunker : bunkers) {
                if (bunker.checkBrickCollision(bullet)) {
                    invaderBullets.remove(bullet);
                    return false;
                }
            }

            if (spaceShip.collides(bullet)) {
                spaceShip.decreaseLife();
                if(spaceShip.getLife() == 0){
                    gameOver = true;
                }
                invaderBullets.remove(bullet);
                return true;
            }
            if (bullet.getY() >= Dimensions.MAX_HEIGHT) {
                invaderBullets.remove(bullet);
                return false;
            }
        }
        return false;
    }

    /**
     * Funzione per controllare la collisione dei proittili sparati dal giocatore: prima rispetto ai bunker(e i loro
     * brick) e poi rispetto agli invaders. Eliminazione del bullet nel caso in cui non collida con niente e giunga a
     * fine schermata(y minore)
     */
    public boolean checkSpaceShipShotCollision(SpaceShip spaceShip) {
        for (Bunker bunker : bunkers) {
            if (bunker.checkBrickCollision(spaceShip.getShipBullet())) {
                spaceShip.setShipShot(false);
                return true;
            }
        }
        for(Invader invader : invaders){
            if (invader.collides(spaceShip.getShipBullet())) {
                spaceShip.incrementCurrentScore(invader.getValue());
                invaders.remove(invader);
                spaceShip.setShipShot(false);
                if (invaders.isEmpty()) {
                    nextLevel(spaceShip);
                }
                return true;
            }
        }
        if (spaceShip.getShipBullet().getY() <= 0) {
            spaceShip.setShipShot(false);
        }
        return false;
    }

    /**
     * Gestione del movimento degli invaders. Se viene raggiunto il limite laterale rispetto alla direzione di movimento
     * tutti gli invaders shiftano verso il basso e la direzione laterale di movimento viene invertita settando il
     * corrispondendo Enum 'MovingDirections' fondamentale nella funzione successiva
     */
    public MovingDirections checkInvaderDirection() {
        double maxX = 0;
        double minX = Invader.HORIZONTAL_OFFSET;
        double maxY = 0;

        for (Invader invader : invaders) {
            if (maxX < invader.getX()) {
                maxX = invader.getX();
            }
            if (minX > invader.getX()) {
                minX = invader.getX();
            }
            if (maxY < invader.getY()) {
                maxY = invader.getY();
            }
        }
        if((maxY + Dimensions.INVADER_HEIGHT) >= (Dimensions.MAX_HEIGHT - 7* Dimensions.BRICK_HEIGHT)){
            endReached = true;
        }
        if(((maxX + Dimensions.INVADER_WIDTH + Invader.HORIZONTAL_OFFSET) > Dimensions.MAX_WIDTH) && !goDown){
            goDown = true;
            return MovingDirections.DOWN;

        } else if((minX - Invader.HORIZONTAL_OFFSET < Dimensions.MIN_WIDTH) && !goDown) {
            goDown = true;
            return MovingDirections.DOWN;
        }
        if(goDown && md == MovingDirections.RIGHT) {
            md = MovingDirections.LEFT;
            goDown = false;
        }else if(goDown && md == MovingDirections.LEFT) {
            md = MovingDirections.RIGHT;
            goDown = false;
        }
        return md;
    }

    /**
     * Funzione di movimento degli invaders. La direzione é inidicata dalla MovingDirections passata come parametro
     * @param md Enum che indica la direzione di movimento
     */
    public void invaderMovement(MovingDirections md){
        for(Invader invader:invaders) {
            switch (md) {
                case RIGHT:
                    invader.moveRight();
                    break;
                case LEFT:
                    invader.moveLeft();
                    break;
                case DOWN:
                    invader.moveDown();
                    break;
            }
        }
    }

    /**
     * Funzione di sparo degli invaders. Lo sparo non é ordindato, ma viene scelto randomicamente quale alieno debba
     * sparare
     */
    public void invaderShot() {
        Random rand = new Random();
        int random = rand.nextInt(invaders.size());
        Coordinate coordinate = new Coordinate(invaders.get(random).getX() +
                Dimensions.INVADER_WIDTH / 2 - Dimensions.BULLET_WIDTH /2,
                invaders.get(random).getY() + Dimensions.INVADER_HEIGHT /2);

        invaderBullets.add(new InvaderBullet(coordinate, Dimensions.BULLET_WIDTH, Dimensions.BULLET_HEIGHT));
    }

    public void setNewLevel(boolean value){
        newLevel = value;
    }

    public List<Invader> getInvaders() {
        return invaders;
    }

    public ArrayList<Bunker> getBunkers() {
        return bunkers;
    }

    public List<InvaderBullet> getInvaderBullets(){
        return invaderBullets;
    }

    public boolean isGameOver(){
        return gameOver;
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
}
