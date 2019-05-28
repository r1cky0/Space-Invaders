package logic.environment;

import logic.player.Player;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.Bullet;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.unmovable.Bunker;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class Field {

    //DIMENSIONS
    private final double MIN_HEIGHT = 0.0;
    private final double MIN_WIDTH = 0.0;
    private double maxHeight;
    private double maxWidth;
    private double invaderSize;
    private double bulletSize;
    private double brickSize;
    private int delta;

    //STATE
    private boolean gameOver;
    private boolean newHighscore;
    private boolean nextLevel;

    private Player player;
    private SpaceShip spaceShip;
    private ArrayList<Invader> invaders;
    private ArrayList<Bunker> bunkers;
    private Bullet shipBullet;
    private boolean shipShot;
    private ArrayList<Bullet> invaderBullets;
    private MovingDirections md = MovingDirections.RIGHT;


    public Field(Player player, double maxWidth, double maxHeight, int delta){
        this.player = player;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.delta = delta;

        invaderSize = maxWidth / 20;
        bulletSize = maxWidth / 60;
        brickSize = maxWidth / 40;

        spaceShip = player.getSpaceShip();
        invaderBullets = new ArrayList<>();
        shipShot = false;

        gameOver = false;
        newHighscore = false;
        nextLevel = false;

        initComponents();
    }

    public void initComponents(){
        //inizializzazione di tutti gli elementi all'inizio del gioco
        initInvaders();
        initBunkers();
        spaceShip.setLife();
        spaceShip.setCurrentScore();
    }

    /**
     * Reinizializzazione degli invaders e incremento life ship al nuovo livello
     */
    public void nextLevel(){
        initInvaders();
        md = MovingDirections.RIGHT;
        spaceShip.incrementLife();
        nextLevel = true;
    }

    /**
     * Inizializzazione degli invaders, posti in alto a sinistra nella schermata di gioco
     */
    private void initInvaders(){
        final double HORIZONTAL_OFFSET = maxWidth/32;
        final double VERTICAL_OFFSET = maxHeight/100;

        invaders = new ArrayList<>();
        double baseX = HORIZONTAL_OFFSET;
        double baseY = maxHeight/10;
        double x;

        for(int i=0; i<4; i++){
            x = baseX;

            for(int j=0; j<8; j++){
                Coordinate coordinate = new Coordinate(x,baseY);
                Invader invader = new Invader(coordinate, invaderSize, 10, delta);
                invaders.add(invader);
                x+= invaderSize + HORIZONTAL_OFFSET;
            }
            baseY+= invaderSize + VERTICAL_OFFSET;
        }
    }

    /**
     * Inizializzazione della lista di bunker, con attenzione particolare alla distanza tra ognuno di essi
     * proporzionale alla dimensione della schermata di gioco
     */
    private void initBunkers(){
        bunkers = new ArrayList<>();
        double baseX = (maxWidth - 35*brickSize)/2;
        double baseY = (maxHeight - 4*brickSize);
        double x = baseX;

        for(int i=1; i<5;i++){
            Bunker bunker = new Bunker(x,baseY, brickSize);
            bunkers.add(bunker);
            x = baseX + (10*brickSize)*i;
        }
    }

    /**
     * Check di eventuale nuovo highscore con segnalazione a livello superiore dell'evento attraverso un' eccezione
     */
    public void gameOver(){

        player.incrementCredit(spaceShip.getCurrentScore());
        if(player.getHighScore() < spaceShip.getCurrentScore()){
            player.setHighScore(spaceShip.getCurrentScore());
            newHighscore = true;
        }else {
            gameOver = true;
        }
    }

    public void shipMovement(MovingDirections md){

        if(((spaceShip.getX() + spaceShip.getSize()) < maxWidth) && (md == MovingDirections.RIGHT)){
            spaceShip.moveRight();
        }

        if((spaceShip.getX() > MIN_WIDTH) && (md == MovingDirections.LEFT)){
            spaceShip.moveLeft();
        }

    }

    public void shipShot(){

        if(!shipShot) {
            Coordinate coordinate = new Coordinate(spaceShip.getShape().getCenterX() - bulletSize/2, spaceShip.getY());
            shipBullet = new Bullet(coordinate, bulletSize, delta);
            shipShot = true;
        }
    }

    /**
     * Funzione per controllare la collisione dei proittili sparati dagli invaders: prima rispetto ai bunker(e i loro
     * brick) e poi rispetto alla ship. Eliminazione del bullet nel caso in cui non collida con niente e giunga a
     * fine schermata(y maggiore)
     */
    public void checkInvaderShotCollision() {

        ListIterator<Bullet> bulletIter = invaderBullets.listIterator();

        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            for (Bunker bunker : bunkers) {
                if (bunker.checkBrickCollision(bullet)) {
                    bulletIter.remove();
                    return;
                }
            }

            if (spaceShip.collides(bullet)) {
                spaceShip.decreaseLife();
                if (spaceShip.getLife() == 0) {
                    gameOver();
                }
                bulletIter.remove();
                return;
            }
            if (bullet.getY() >= maxHeight) {
                bulletIter.remove();
                return;
            }
        }
    }

    /**
     * Funzione per controllare la collisione dei proittili sparati dal giocatore: prima rispetto ai bunker(e i loro
     * brick) e poi rispetto agli invaders. Eliminazione del bullet nel caso in cui non collida con niente e giunga a
     * fine schermata(y minore)
     */
    public void checkSpaceShipShotCollision() {

        for (Bunker bunker : bunkers) {
            if (bunker.checkBrickCollision(shipBullet)) {
                shipShot = false;
                shipBullet = null;
                return;
            }
        }

        ListIterator<Invader> invaderIter = invaders.listIterator();

        while (invaderIter.hasNext()){
            Invader invader = invaderIter.next();

            if (invader.collides(shipBullet)) {
                spaceShip.incrementCurrentScore(invader.getValue());
                invaderIter.remove();
                shipShot = false;
                shipBullet = null;
                if (invaders.isEmpty()) {
                    nextLevel();
                }
                return;
            }
        }

        if (shipBullet.getY() <= 0) {
            shipShot = false;
        }
    }

    /**
     * Gestione del movimento degli invaders. Se viene raggiunto il limite laterale rispetto alla direzione di movimento
     * tutti gli invaders shiftano verso il basso e la direzione laterale di movimento viene invertita settando il
     * corrispondendo Enum 'MovingDirections' fondamentale nella funzione successiva
     */
    public void invaderDirection() {

        double maxX = 0;
        double minX = 10;
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

        if ((maxX + invaderSize) >= maxWidth) {
            invaderMovement(MovingDirections.DOWN);
            md = MovingDirections.LEFT;
        } else if (minX <= MIN_WIDTH) {
            invaderMovement(MovingDirections.DOWN);
            md = MovingDirections.RIGHT;
        }
        invaderMovement(md);

        if((maxY + invaderSize) >= (maxHeight - 7*brickSize)){
            gameOver();
        }
    }

    /**
     * Funzione di movimento degli invaders. La direzione é inidicata dalla MovingDirections passata come parametro
     * @param md
     */
    private void invaderMovement(MovingDirections md){

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

        invaderBullets.add(new Bullet(coordinate, bulletSize, delta));

    }

    public ArrayList<Invader> getInvaders() {
        return invaders;
    }

    public ArrayList<Bunker> getBunkers() {
        return bunkers;
    }

    public ArrayList<Bullet> getInvaderBullets(){
        return invaderBullets;
    }

    public Bullet getShipBullet(){
        return shipBullet;
    }

    public SpaceShip getSpaceShip(){
        return spaceShip;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public boolean isNewHighscore(){
        return newHighscore;
    }

    public boolean isNextLevel(){
        return nextLevel;
    }

}
