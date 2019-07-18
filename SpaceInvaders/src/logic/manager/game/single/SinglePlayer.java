package logic.manager.game.single;

import logic.manager.game.commands.CommandType;
import logic.manager.game.Game;
import logic.manager.game.States;
import logic.player.Player;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.unmovable.Bunker;
import main.Dimensions;
import logic.manager.game.commands.Command;
import logic.manager.game.commands.MoveLeft;
import logic.manager.game.commands.MoveRight;
import logic.manager.game.commands.Shot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe che rappresenta la modalità di gioco singola.
 * Si occupa di eseguire i comandi del giocatore e contollare movimento degli sprite,
 * le collisioni tra bullet e lo stato di gioco.
 *
 */
public class SinglePlayer extends Game {
    private Player player;
    private SpaceShip spaceShip;
    private HashMap<CommandType, Command> commands;

    public SinglePlayer(Player player) {
        this.player = player;
        spaceShip = getSpaceShip();
        initCommands();
    }

    /**
     * Metodo che inizializza l'arrayList dei comandi.
     */
    private void initCommands(){
        commands = new HashMap<>();
        commands.put(CommandType.MOVE_LEFT, new MoveLeft());
        commands.put(CommandType.MOVE_RIGHT, new MoveRight());
        commands.put(CommandType.SHOT, new Shot());
    }

    /**
     * Richiama start della superclasse che attiva thread invaders e inizializza la ship.
     */
    public void startGame(){
        super.startGame();
        spaceShip.init();
    }

    /**
     * Metodo che si occupa dell'esecuzione dei comandi dell'utente in base al tasto premuto.
     *
     * @param commandType comando da eseguire
     * @param delta velocità
     */
    public void execCommand(CommandType commandType, int delta){
        if(commandType == CommandType.EXIT){
            super.stopThreadInvader();
        }else{
            commands.get(commandType).exe(getFieldManager(), player.getSpaceShip(), delta);
        }
    }

    /**
     * Thread che gestisce l'aggiornamento dello stato e degli elementi di gioco.
     *
     * @param delta velocità
     */
    public void update(int delta) {
        for (Bullet bullet : getFieldManager().getInvaderBullets()) {
            bullet.move(delta);
        }
        if (spaceShip.isShipShot()) {
            spaceShip.getShipBullet().move(delta);
            getFieldManager().checkSpaceShipShotCollision(getSpaceShip());
        }
        if(isBonusInvader()) {
            if (getFieldManager().getBonusInvader().getX() + Dimensions.BONUSINVADER_WIDTH < Dimensions.MIN_WIDTH) {
                getFieldManager().setBonusInvader(false);
            } else {
                getFieldManager().getBonusInvader().moveLeft(delta);
            }
        }
        if(getFieldManager().checkInvaderShotCollision(spaceShip)){
            if(spaceShip.getLife() == 0){
                setGameState(States.GAMEOVER);
            }
        }
        checkGameState();
    }

    /**
     * Metodo che controlla lo stato di gioco.
     */
    private void checkGameState(){
        if (getGameState() == States.GAMEOVER || getFieldManager().isEndReached()) {
            super.stopThreadInvader();
            if (player.checkHighscore()) {
                setGameState(States.NEWHIGHSCORE);
            }else {
                setGameState(States.GAMEOVER);
            }
        }
        if(getFieldManager().isNewLevel()){
            super.stopThreadInvader();
            super.startThreadInvader();
            getFieldManager().setNewLevel(false);
            spaceShip.incrementLife();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public SpaceShip getSpaceShip(){
        return player.getSpaceShip();
    }

    public Bullet getSpaceShipBullet(){
        return getSpaceShip().getShipBullet();
    }

    public List<Bullet> getInvadersBullet(){
        return getFieldManager().getInvaderBullets();
    }

    public List<Bunker> getBunkers(){
        return getFieldManager().getBunkers();
    }

    public List<Invader> getInvaders(){
        return getFieldManager().getInvaders();
    }

    public BonusInvader getBonusInvader(){
        return getFieldManager().getBonusInvader();
    }

    public boolean isBonusInvader(){
        return getFieldManager().isBonusInvader();
    }

}


