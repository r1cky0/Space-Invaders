package Logic.Environment;

import Logic.Coordinate;
import Logic.Player.Player;
import Logic.Sprite.Dinamic.Bullet;
import Logic.Sprite.Dinamic.Invader;
import Logic.Sprite.Static.Bunker;

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

    /*public Field(Player player){
        this.player = player;
        invaders = new ArrayList<>();
        bunkers = new ArrayList<>();
        max_height = 100;
        max_width = 100;
        startGame();
    }*/

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

    public void gameOver(){
        //metodo da richiamare quando finiscono le vite della ship
    }

    public void shipMovement(MovingDirections md){
        if(((player.getSpaceShip().getX() + player.getSpaceShip().getSize()/2) < max_width)
                && (md == MovingDirections.RIGHT)){
            player.getSpaceShip().moveRight();
        }
        if(((player.getSpaceShip().getX() - player.getSpaceShip().getSize()/2) > MIN_WIDTH)
                && (md == MovingDirections.LEFT)){
            player.getSpaceShip().moveLeft();
        }

    }

    public void shipShot() {
        if(!shipShot) {
            shipBullet = new Bullet(player.getSpaceShip().getCoordinate(), BULLET_SIZE);
            shipShot = true;
            while (!(checkCollision(shipBullet)) && shipBullet.getY() > MIN_HEIGHT) {
                //Qua va il ritardo in secondi
                shipBullet.moveUp();
            }
            shipShot = false;
        }
    }

    private boolean checkCollision(Bullet bullet){

        /*for(Bunker bunker:bunkers){
            if(bunker.deleteBrick(bullet.getCoordinate()))
                return true;
        }*/

        ListIterator<Invader> listIterator = invaders.listIterator();
        while (listIterator.hasNext()) {
            if(listIterator.next().intersect(bullet)){
                listIterator.remove();
                return true;
            }
        }
        return false;
    }

    public void invaderMovement(){

        double max_x = 0;
        double min_x = 0;
        double max_y = 0;

        for(Invader invader: invaders){
            if(invader.getX() > max_x){
                max_x = invader.getX();
            }
        }
    }

    private void invaderShot(){
        if(invaderShot == false){
            Random rand = new Random();
            invaderBullet = new Bullet(invaders.get(rand.nextInt(invaders.size())).getCoordinate(), BULLET_SIZE);
            invaderShot = true;
            while (!(checkCollision(invaderBullet)) && invaderBullet.getY() < max_height) {
                //Qua va il ritardo in secondi
                shipBullet.moveDown();
            }
        }
    }

}
