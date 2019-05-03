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


    //controllare che i player siano loggati(fare il controllo in "MenuPrincipale")

    private Player player;
    private ArrayList<Invader> invaders;
    private ArrayList<Bunker> bunkers;

    public Field(Player player){
        this.player = player;
        invaders = new ArrayList<>();
        bunkers = new ArrayList<>();
        max_height = 100;
        max_width = 100;
        startGame();
    }

    public Field(Player player, double max_height, double max_width){
        this.player = player;
        invaders = new ArrayList<>();
        bunkers = new ArrayList<>();
        this.max_height = max_height;
        this.max_width = max_width;
        startGame();
    }

    public void startGame(){
        //inizializzazione di tutti gli elementi all'inizio del gioco
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


        Bullet bullet = new Bullet(player.getSpaceShip().getCoordinate(), BULLET_SIZE);

        while(!(checkCollision(bullet)) && bullet.getY()>MIN_HEIGHT){
            //Qua va il ritardo in secondi
            bullet.moveUp();
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

        Random random = new Random();
        random.ints(1,33);
        Bullet bullet = new Bullet(invaders.get(random.nextInt()).getCoordinate(), BULLET_SIZE);

    }

}
