package logic.environment.manager.game;

import logic.environment.creators.BunkersCreator;
import logic.environment.creators.InvadersCreator;
import logic.player.Player;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.unmovable.Bunker;
import org.newdawn.slick.util.pathfinding.navmesh.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class GameManager {

    //DIMENSIONS
    private final double MIN_WIDTH = 0.0;
    private double maxHeight;
    private double maxWidth;
    private double bulletSize;
    private double brickSize;
    private double invaderSize;

    private InvadersCreator invadersCreator;
    private BunkersCreator bunkersCreator;

    //STATE
    private boolean gameOver;
    private boolean newHighscore;
    private boolean newLevel;

    private List<Invader> invaders;
    private ArrayList<Bunker> bunkers;
    private List<Bullet> invaderBullets;
    private MovingDirections md;
    private boolean goDown;
    private Difficulty difficulty;

    GameManager(double maxWidth, double maxHeight){
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;

        bulletSize = maxWidth / 60;
        brickSize = maxWidth / 40;

        invaderSize = maxWidth / 20;

        invadersCreator = new InvadersCreator(maxHeight,maxWidth,invaderSize);
        bunkersCreator = new BunkersCreator(maxHeight,maxWidth,brickSize);

        bunkers = new ArrayList<>();
        invaderBullets = new CopyOnWriteArrayList<>();
        invaders = new CopyOnWriteArrayList<>(  );
        md = MovingDirections.RIGHT;
        goDown = false;

        difficulty = new Difficulty(); //millisecondi pausa sparo/movimento alieni

        gameOver = false;
        newHighscore = false;
        newLevel = false;

        initComponents();
    }

    public void initComponents(){
        //inizializzazione di tutti gli elementi all'inizio del gioco
        invaders = invadersCreator.create();
        bunkers = bunkersCreator.create();
    }

    /**
     * Reinizializzazione degli invaders e incremento life ship al nuovo livello
     */
    public void nextLevel(){
        difficulty.incrementDifficulty();
        md = MovingDirections.RIGHT;
        invaders = invadersCreator.create();
        newLevel = true;
    }

    /**
     * Check di eventuale nuovo highscore implementato in modo diverso se di squadra (multiplayer)
     * o singolo (singleplayer)
     */
    public abstract void checkHighscore(Object obj);

    public void shipMovement(SpaceShip spaceShip, MovingDirections md){

        if(((spaceShip.getX() + spaceShip.getSize()) < maxWidth) && (md == MovingDirections.RIGHT)){
            spaceShip.moveRight();
        }

        if((spaceShip.getX() > MIN_WIDTH) && (md == MovingDirections.LEFT)){
            spaceShip.moveLeft();
        }

    }

    public void shipShot(SpaceShip spaceShip){

        if(!spaceShip.isShipShot()) {
            Coordinate coordinate = new Coordinate(spaceShip.getShape().getCenterX() - bulletSize/2, spaceShip.getY());
            spaceShip.setShipBullet(new SpaceShipBullet(coordinate, bulletSize));
            spaceShip.setShipShot(true);
        }
    }

    /**
     * Funzione per controllare la collisione dei proittili sparati dagli invaders: prima rispetto ai bunker(e i loro
     * brick) e poi rispetto alla ship. Eliminazione del bullet nel caso in cui non collida con niente e giunga a
     * fine schermata(y maggiore)
     */
    public void checkInvaderShotCollision(SpaceShip spaceShip) {
        for(Bullet bullet : invaderBullets){
            for (Bunker bunker : bunkers) {
                if (bunker.checkBrickCollision(bullet)) {
                    invaderBullets.remove(bullet);
                    return;
                }
            }

            if (spaceShip.collides(bullet)) {
                spaceShip.decreaseLife();
                if(spaceShip.getLife() == 0){
                    gameOver = true;
                }
                invaderBullets.remove(bullet);
                return;
            }
            if (bullet.getY() >= maxHeight) {
                invaderBullets.remove(bullet);
                return;
            }
        }
    }

    /**
     * Funzione per controllare la collisione dei proittili sparati dal giocatore: prima rispetto ai bunker(e i loro
     * brick) e poi rispetto agli invaders. Eliminazione del bullet nel caso in cui non collida con niente e giunga a
     * fine schermata(y minore)
     */
    public void checkSpaceShipShotCollision(SpaceShip spaceShip) {

        for (Bunker bunker : bunkers) {
            if (bunker.checkBrickCollision(spaceShip.getShipBullet())) {
                spaceShip.setShipShot(false);
                spaceShip.setShipBullet(null);
                return;
            }
        }

        for(Invader invader : invaders){
            if (invader.collides(spaceShip.getShipBullet())) {
                spaceShip.incrementCurrentScore(invader.getValue());
                invaders.remove(invader);
                spaceShip.setShipShot(false);
                spaceShip.setShipBullet(null);
                if (invaders.isEmpty()) {
                    nextLevel();
                }
                return;
            }
        }

        if (spaceShip.getShipBullet().getY() <= 0) {
            spaceShip.setShipShot(false);
            spaceShip.setShipBullet(null);
        }
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
        if((maxY + invaderSize) >= (maxHeight - 7*brickSize)){
            gameOver = true;
        }
        if(((maxX + invaderSize + Invader.HORIZONTAL_OFFSET) > maxWidth) && !goDown){
            goDown = true;
            return MovingDirections.DOWN;

        } else if((minX - Invader.HORIZONTAL_OFFSET < MIN_WIDTH) && !goDown) {
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
                invaderSize / 2 - bulletSize /2, invaders.get(random).getY()+invaderSize/2);

        invaderBullets.add(new InvaderBullet(coordinate, bulletSize));

    }

    public void setNewHighscore(boolean value){
        newHighscore = value;
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

    public List<Bullet> getInvaderBullets(){
        return invaderBullets;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public boolean isNewHighscore(){
        return newHighscore;
    }

    public boolean isNewLevel(){
        return newLevel;
    }

    public int getDifficulty(){
        return difficulty.getDifficulty();
    }
}
