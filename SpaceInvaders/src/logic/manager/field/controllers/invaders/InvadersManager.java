package logic.manager.field.controllers.invaders;

import logic.manager.creators.InvadersCreator;
import logic.manager.field.MovingDirections;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.invaders.Invader;
import main.Dimensions;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Classe che gestisce gli invaders, il bonus invaders, il loro movimento e lo sparo.
 */
public class InvadersManager {
    private InvadersCreator invadersCreator;
    private List<Invader> invaders;
    private BonusInvader bonusInvader;
    private List<Bullet> invaderBullets;
    private HashMap<MovingDirections, Controller> controllers;
    private MovingDirections comingFrom;
    private MovingDirections md;
    private boolean bonus;
    private boolean bonusInLevel;


    public InvadersManager(){
        invadersCreator = new InvadersCreator();
        invaders = new CopyOnWriteArrayList<>();
        invaderBullets = new CopyOnWriteArrayList<>();
        initMoveController();
        comingFrom = MovingDirections.LEFT;
        md = MovingDirections.RIGHT;
        bonus = false;
        bonusInLevel = false; //Per far apparire solo una volta a livello il bonus
    }

    /**
     * Creazione lista di invader e inizializzazione direzione.
     */
    public void init(){
        invaders = invadersCreator.create();
        comingFrom = MovingDirections.LEFT;
        md = MovingDirections.RIGHT;
        bonusInLevel = false;
    }

    /**
     * Scelta direzione degli invader
     * Se viene raggiunto il limite laterale rispetto alla direzione di movimento tutti gli invaders shiftano
     * verso il basso e la direzione laterale di movimento viene invertita settando il corrispondente
     * Enum md per direzione da prendere
     * Enum comingFrom per direzione di provenienza
     */
    public void checkInvaderDirection() {
        double maxX = 0;
        double minX = Invader.HORIZONTAL_OFFSET;
        for (Invader invader : invaders) {
            if (maxX < invader.getX()) {
                maxX = invader.getX();
            }
            if (minX > invader.getX()) {
                minX = invader.getX();
            }
        }
        if(((maxX + Dimensions.INVADER_WIDTH + Invader.HORIZONTAL_OFFSET) > Dimensions.MAX_WIDTH)
                && !(md == MovingDirections.DOWN) && (comingFrom == MovingDirections.LEFT)){
            md = MovingDirections.DOWN;
        } else if((minX - Invader.HORIZONTAL_OFFSET < Dimensions.MIN_WIDTH)
                && !(md == MovingDirections.DOWN) && (comingFrom == MovingDirections.RIGHT)) {
            md = MovingDirections.DOWN;
        }
        moveInvaders();
        if(md == MovingDirections.DOWN && comingFrom == MovingDirections.RIGHT) {
            md = MovingDirections.RIGHT;
            comingFrom = MovingDirections.LEFT;
        }else if(md == MovingDirections.DOWN && comingFrom == MovingDirections.LEFT) {
            md = MovingDirections.LEFT;
            comingFrom = MovingDirections.RIGHT;
        }
    }

    /**
     * Movimento di tutti gli invader nella direzione impostata.
     */
    private void moveInvaders(){
         controllers.get(md).move(invaders);
    }

    /**
     * Inizializzazione mappa dei controller di movimento.
     */
    private void initMoveController(){
        controllers = new HashMap<>();
        controllers.put(MovingDirections.LEFT, new InvaderMoveLeft());
        controllers.put(MovingDirections.RIGHT, new InvaderMoveRight());
        controllers.put(MovingDirections.DOWN, new InvaderMoveDown());
    }

    /**
     * Funzione di creazione di una navicella invaders "Bonus" che passa orizontalmente durante un livello e, se colpito,
     * fornisce punti extra al giocatore
     */
    public void createBonusInvader(){
        double minY = Dimensions.MAX_HEIGHT;
        for (Invader invader : invaders) {
            if (minY > invader.getY()) {
                minY = invader.getY();
            }
        }
        if((minY >= Dimensions.MAX_HEIGHT/4) && (!bonusInLevel)){
            bonusInvader = new BonusInvader(new Coordinate(Dimensions.MAX_WIDTH, Dimensions.MAX_HEIGHT/10));
            bonus = true;
            bonusInLevel = true;
        }
    }

    /**
     * Funzione di sparo degli invaders. Lo sparo non é ordindato, ma viene scelto randomicamente quale alieno
     * debba sparare
     */
    public void createInvaderBullet() {
        Random rand = new Random();
        int random = rand.nextInt(invaders.size());
        Coordinate coordinate = new Coordinate(invaders.get(random).getX() +
                Dimensions.INVADER_WIDTH / 2 - Dimensions.BULLET_WIDTH /2,
                invaders.get(random).getY() + Dimensions.INVADER_HEIGHT /2);

        invaderBullets.add(new InvaderBullet(coordinate));
    }

    public void removeBullet(Bullet bullet){
        invaderBullets.remove(bullet);
    }

    public void removeInvader(Invader invader){
        invaders.remove(invader);
    }

    public List<Invader> getInvaders() {
        return invaders;
    }

    public BonusInvader getBonusInvader() {
        return bonusInvader;
    }

    public List<Bullet> getInvaderBullets() {
        return invaderBullets;
    }

    public boolean isBonus(){
        return bonus;
    }

    public void setBonus(boolean value){
        this.bonus = value;
    }
}
