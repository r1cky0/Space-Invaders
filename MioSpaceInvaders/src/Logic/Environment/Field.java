package Logic.Environment;

import Logic.Player.Player;
import Logic.Sprite.Dinamic.Bullet;
import Logic.Sprite.Dinamic.Invader;
import Logic.Sprite.Static.Bunker;

import java.util.ArrayList;

public class Field {
    //LOGICAMENTE: dimensioni da 1 a 100. Nella grafica faremo proporzione con container.Width e .Height

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
        if(md == MovingDirections.RIGHT){player.getSpaceShip().moveRight();}
        if(md == MovingDirections.LEFT){player.getSpaceShip().moveLeft();}
    }

    public void shipShot() {
        Bullet bullet = new Bullet(player.getSpaceShip().getCoordinate());
    }

    public void invaderMovement(){

    }

    private void invaderShot(){

    }

}
