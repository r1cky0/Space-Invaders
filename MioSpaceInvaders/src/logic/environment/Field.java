package logic.environment;

import logic.player.Player;
import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.dinamic.Bullet;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import org.lwjgl.Sys;
import org.newdawn.slick.SlickException;

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
    private Player player;
    private ArrayList<Invader> invaders;
    private ArrayList<Bunker> bunkers;
    private Bullet shipBullet;
    private boolean shipShot;
    private Bullet invaderBullet;
    private boolean invaderShot;
    private MovingDirections md = MovingDirections.RIGHT;

    public Field(Player player, double maxWidth, double maxHeight){
        this.player = player;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;

        invaderSize = maxWidth / 20;
        bulletSize = maxWidth / 60;
        brickSize = maxWidth / 40;

        shipBullet = new Bullet(player.getSpaceShip().getCoordinate(), bulletSize);
        shipShot = false;
        invaderShot = false;

        startGame();
    }

    public void startGame(){
        //inizializzazione di tutti gli elementi all'inizio del gioco
        initInvaders();
        initBunkers();
        player.getSpaceShip().setLife();
    }

    public void nextLevel(){
        //reinizializzazione degli invaders e incremento life ship al nuovo livello
        initInvaders();
        player.getSpaceShip().incrementLife();
    }

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
                Invader invader = new Invader(coordinate, invaderSize, 10);
                invaders.add(invader);
                x+= invaderSize + HORIZONTAL_OFFSET;
            }
            baseY+= invaderSize + VERTICAL_OFFSET;
        }
    }

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

    public void gameOver(){

        if(player.getHighScore() < player.getSpaceShip().getCurrentScore()){
            player.setHighScore(player.getSpaceShip().getCurrentScore());
        }
        player.incrementCredit(player.getSpaceShip().getCurrentScore());
    }

    public void shipMovement(MovingDirections md){

        if(((player.getSpaceShip().getX() + player.getSpaceShip().getSize()) < maxWidth)
                && (md == MovingDirections.RIGHT)){
            player.getSpaceShip().moveRight();
        }

        if((player.getSpaceShip().getX() > MIN_WIDTH)
                && (md == MovingDirections.LEFT)){
            player.getSpaceShip().moveLeft();
        }

    }

    public Bullet shipShot(){

        if(!shipShot) {
            Coordinate coordinate = new Coordinate(player.getSpaceShip().getShape().getCenterX() -
                    bulletSize/2, player.getSpaceShip().getY());

            shipBullet = new Bullet(coordinate, bulletSize);
            shipShot = true;
        }
        return shipBullet;
    }

    public boolean checkCollision(Sprite sprite, Bullet bullet) {

        for (Bunker bunker : bunkers) {
            for (Brick brick : bunker.getBricks()) {
                if (brick.collides(bullet)) {
                    brick.decreaseLife();

                    if (brick.getLife() == 0) {
                        bunker.getBricks().remove(brick);
                    }
                    if (sprite instanceof SpaceShip) {
                        shipShot = false;
                    }else {
                        invaderShot = false;
                    }
                    return true;
                }
            }
        }
        if(sprite instanceof SpaceShip){
            for(Invader invader : invaders){
                if(invader.collides(bullet)){
                    player.getSpaceShip().incrementCurrentScore(invader.getValue());
                    invaders.remove(invader);
                    shipShot = false;

                    if (invaders.isEmpty()) {
                        nextLevel();
                    }
                    return true;
                }
            }
            if(shipBullet.getY() <= 0){
                shipShot = false;
                return true;
            }
            return false;
        }
        else if(sprite instanceof Invader) {
            if (player.getSpaceShip().collides(bullet)) {
                player.getSpaceShip().decreaseLife();

                if (player.getSpaceShip().getLife() == 0) {
                    gameOver();
                    return true;
                }else {
                    invaderBullet = null;
                    invaderShot = false;
                    return true;
                }
            }
            if(invaderBullet.getY() >= maxHeight){
                invaderShot = false;
                return true;
            }
            return false;
        }
        return false;
    }

    public void invaderDirection() {

        double maxX = 0;
        double minX = 100;

        for (Invader invader : invaders) {
            if (maxX < invader.getX()) {
                maxX = invader.getX();
            }
            if (minX > invader.getX()) {
                minX = invader.getX();
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
    }

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

    public Bullet invaderShot(){
        if (!invaderShot) {
            Random rand = new Random();
            int random = rand.nextInt(invaders.size());
            Coordinate coordinate = new Coordinate(invaders.get(random).getX() -
                    bulletSize / 2, invaders.get(random).getY());

            invaderBullet = new Bullet(coordinate, bulletSize);
            invaderShot = true;
        }
        return invaderBullet;
    }

    public ArrayList<Invader> getInvaders() {
        return invaders;
    }

    public ArrayList<Bunker> getBunkers() {
        return bunkers;
    }

}
