package Logic.Environment;

import Logic.Coordinate;
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
    private ArrayList<Bullet> bullets;

    public Field(Player player){
        this.player = player;
        invaders = new ArrayList<>();
        bunkers = new ArrayList<>();
        bullets = new ArrayList<>();
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

    public boolean shipShot() {
        Bullet b = new Bullet(player.getSpaceShip().getCoordinate());
        return bullets.add(b);
    }

    public boolean removeShot(Coordinate coordinate) {
        ArrayList<Bullet> toBeRemoved = new ArrayList<>();
        for (Bullet b: bullets) {
            if (b.getCoordinate().equals(coordinate)) {
                toBeRemoved.add(b);
            }
        }
        return bullets.removeAll(toBeRemoved);
    }
}
