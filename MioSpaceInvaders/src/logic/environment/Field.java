package logic.environment;

import logic.player.Player;
import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.dinamic.Bullet;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class Field {

    private final double BULLET_SIZE = 1;
    private final double MIN_HEIGHT = 0;
    private final double MIN_WIDTH = 0;
    private double max_height;
    private double max_width;


    //Per ora fatto con single player

    private Player player;
    private ArrayList<Invader> invaders;
    private ArrayList<Bunker> bunkers;
    private Bullet shipBullet;
    private boolean shipShot;
    private Bullet invaderBullet;
    private boolean invaderShot;
    private boolean gameover;

    public Field(Player player, double max_height, double max_width){
        this.player = player;
        invaders = new ArrayList<>();
        bunkers = new ArrayList<>();
        this.max_height = max_height;
        this.max_width = max_width;
        shipBullet = null;
        shipShot = false;
        invaderBullet = null;
        invaderShot = false;
        gameover = false;
        startGame();
    }

    public void startGame(){
        //inizializzazione di tutti gli elementi all'inizio del gioco

        //DUBBIO DI SIMO: Ma per le dimensioni degli elementi come facciamo?
        //Facciamo ad esempio proporzionati alla larghezza che abbiamo passato dal costruttore? (ES: invader= 1/15 della larghezza)
    }

    public void nextLevel(){
        //metodo da richiamare quando finiscono gli invaders e si passa al livello successivo
    }

    public boolean gameOver(){
        return gameover;
    }

    public Coordinate shipMovement(MovingDirections md){
        if(((player.getSpaceShip().getX() + player.getSpaceShip().getSize()/2) < max_width)
                && (md == MovingDirections.RIGHT)){
            return player.getSpaceShip().moveRight();
        }
        if(((player.getSpaceShip().getX() - player.getSpaceShip().getSize()/2) > MIN_WIDTH)
                && (md == MovingDirections.LEFT)){
            return player.getSpaceShip().moveLeft();
        }
        return player.getSpaceShip().getCoordinate();
    }

    public void shipShot() {
        if(!shipShot) {
            shipBullet = new Bullet(player.getSpaceShip().getCoordinate(), BULLET_SIZE);
            shipShot = true;
            //DALLA GRAFICA: MOVIMENTO BULLET E CONTROLLO DELLA COLLISIONE
        }
    }

    private boolean checkCollision(Sprite sprite, Bullet bullet){

        for(Bunker bunker:bunkers){
            ListIterator<Brick> listIterator = bunker.getBricks().listIterator();
            while(listIterator.hasNext()){
                if(listIterator.next().collides(bullet)){
                    listIterator.remove();
                    if(sprite instanceof SpaceShip) {
                        shipBullet = null;
                        shipShot = false;
                    }
                    else {
                        invaderBullet = null;
                        invaderShot = false;
                    }
                    return true;
                }
            }
        }


        if(sprite instanceof SpaceShip) {
            ListIterator<Invader> listIterator = invaders.listIterator();
            while (listIterator.hasNext()) {
                Invader invader = listIterator.next();
                if (invader.collides(bullet)) {
                    player.getSpaceShip().incrementCurrentScore(invader.getValue());
                    listIterator.remove();
                    shipBullet = null;
                    shipShot = false;
                    return true;
                }
            }
            return false;
        }
        else{
            if(player.getSpaceShip().collides(bullet)){
                if(player.getSpaceShip().decreaseLife() == 0){
                    gameover = true;
                }
                else{
                    invaderBullet = null;
                    invaderShot = false;
                    return true;
                }
            }
            return false;
        }
    }

    public void invaderDirection(){

        MovingDirections md = MovingDirections.RIGHT;

        for(Invader invader: invaders){
            if((invader.getX() + invader.getSize()/2) >= max_width){
                invaderMovement(MovingDirections.DOWN);
                md = MovingDirections.LEFT;
            }
            else if((invader.getX() - invader.getSize()/2) <= MIN_WIDTH){
                invaderMovement(MovingDirections.DOWN);
                md = MovingDirections.RIGHT;
            }
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

    private void invaderShot(){
        if(!invaderShot){
            Random rand = new Random();
            invaderBullet = new Bullet(invaders.get(rand.nextInt(invaders.size())).getCoordinate(), BULLET_SIZE);
            invaderShot = true;

            //DALLA GRAFICA: MOVIMENTO BULLET E CONTROLLO DELLA COLLISIONE
        }
    }

    public boolean isShipShot() {
        return shipShot;
    }

    public boolean isInvaderShot() {
        return invaderShot;
    }
}
