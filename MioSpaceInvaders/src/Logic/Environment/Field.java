package Logic.Environment;

import Logic.Coordinate;
import Logic.Player.Player;
import Logic.Sprite.Dinamic.Bullet;
import Logic.Sprite.Dinamic.Invader;
import Logic.Sprite.Static.Bunker;

import java.util.ArrayList;
import java.util.Random;

public class Field {
    //LOGICAMENTE: dimensioni da 1 a 100. Nella grafica faremo proporzione con container.Width e .Height
    private final static int MIN_HEIGHT = 0;
    private final static int MAX_HEIGHT = 100;
    private final static int MIN_WIDTH = 0;
    private final static int MAX_WIDTH= 100;

    //controllare che i player siano loggati(fare il controllo in "MenuPrincipale")

    // metodo "startGame" con il movimento degli alieni
    private Player player;
    private ArrayList<Invader> invaders;
    private ArrayList<Bunker> bunkers;

    public Field(Player player){
        this.player = player;
        invaders = new ArrayList<>();
        bunkers = new ArrayList<>();
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
        if((player.getSpaceShip().getX() < MAX_WIDTH) && (md == MovingDirections.RIGHT)){
            player.getSpaceShip().moveRight();
        }
        if((player.getSpaceShip().getX() > MIN_WIDTH) && (md == MovingDirections.LEFT)){
            player.getSpaceShip().moveLeft();
        }
    }

    public void shipShot() {

        Bullet bullet = new Bullet(player.getSpaceShip().getCoordinate());

        while(!(checkCollision(bullet)) && bullet.getY()>MIN_HEIGHT){
            //Qua va il ritardo in secondi
            bullet.moveUp();
        }

    }

    private boolean checkCollision(Bullet bullet){

        for(Bunker bunker:bunkers){
            bunker.deleteBrick(bullet.getCoordinate());
            return true;
        }

        for(Invader invader:invaders){
            if(invader.getCoordinate().equals(bullet.getCoordinate())){
                int index = invaders.indexOf(invader);
                invaders.remove(index);
                return true;
            }
        }
        return false;
    }

    public void invaderMovement(){
        
    }

    private void invaderShot(){

        Random random = new Random();
        random.ints(1,33);
        Bullet bullet = new Bullet(invaders.get(random.nextInt()).getCoordinate());

    }

}
