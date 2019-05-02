package Logic.Environment;

import Logic.Player.Player;
import Logic.Sprite.Dinamic.Bullet;
import Logic.Sprite.Dinamic.Invader;
import Logic.Sprite.Static.Bunker;

import java.util.ArrayList;

public class Field {

    // fare una map<Player, int score>, controllare che i player siano loggati(fare il controllo qui oppure meglio in "MenuPrincipale")

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

    public void shipMovement(){

    }

    public void shipShot() {
        Bullet bullet = new Bullet(player.getSpaceShip().getCoordinate());

    }

    public void invaderMovement(){

    }

    private void invaderShot(){

    }

}
