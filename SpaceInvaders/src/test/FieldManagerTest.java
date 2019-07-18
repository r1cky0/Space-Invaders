package test;

import logic.manager.field.FieldManager;
import logic.manager.field.MovingDirections;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.Bullet;
import main.Dimensions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test sul metodo che controlla le collisioni del FieldManager.
 *
 */
class FieldManagerTest {

    private FieldManager fieldManager;
    private SpaceShip spaceShip;
    private List<Bullet> invaderBullets;

    public FieldManagerTest(){
        fieldManager = new FieldManager();
        Coordinate coordinate = new Coordinate(0, 0);
        spaceShip = new SpaceShip(coordinate);
        spaceShip.init();

    }
    //CTRL + SHIFT + F10 Per fare il test: se click in un metodo viene testato solo quel metodo,
    //altrimenti tutta la classe di test

    /**
     * Testiamo sparo di un invaders e collisione con la spaceShip.
     * Il field manager fa sparare randomicamente uno degli invaders. La spaceShip viene spostata al di sotto
     * del bullet attraverso la funzione di shipMovement.
     * Fatto ció il bullet viene spostato verticalmente fino ad entrare in collisone con la nave(é fatto in modo
     * istantaneo anche se nel sistema avviene in tempo reale)
     */
    @Test
    void checkTrueInvaderShotCollision() {
        fieldManager.invaderShot();
        invaderBullets = fieldManager.getInvaderBullets();
        //Necessario per spostare la ship in corrispondenza dell' invaderBullet
        while (spaceShip.getX() < invaderBullets.get(0).getX()) {
            fieldManager.shipMovement(spaceShip, MovingDirections.RIGHT, 4);
        }
        while (spaceShip.getX() > invaderBullets.get(0).getX()) {
            fieldManager.shipMovement(spaceShip, MovingDirections.LEFT, 4);
        }
        invaderBullets.get(0).setY(Dimensions.MAX_HEIGHT - Dimensions.SHIP_WIDTH / 2);
        assertTrue(fieldManager.checkInvaderShotCollision(spaceShip));
    }

    /**
     *Creiamo una spaceShip che viene posizionata all' estremo destro del campo di gioco.
     *In questo modo, facendo sparare subito uno degli invaders(creati,invece, a partire dall' estremo sinistro),
     *non dovrebbe esserci collisione
     */
    @Test
    void checkFalseInvaderShotCollision(){
        spaceShip.setX(Dimensions.MAX_WIDTH - Dimensions.SHIP_WIDTH);
        fieldManager.invaderShot();
        invaderBullets = fieldManager.getInvaderBullets();
        invaderBullets.get(0).setY(Dimensions.MAX_HEIGHT - Dimensions.SHIP_WIDTH/2);
        assertFalse(fieldManager.checkInvaderShotCollision(spaceShip));
    }

    /**
     *Simuliamo sparo e spostamento del shipBullet in verticale
     *Fatto in modo istantaneo, nel sistema in realtá sale in tempo reale,
     *ma a noi interessa solo testare che venga individuata la collisione
     *Settiamo quindi la y del proiettile appena sopra l' altezza della prima fila di invaders
     */
    @Test
    void checkTrueSpaceShipShotCollision() {
        //Includiamo nel test anche la funzione di movimento della ship per verificarne il funzionamento
        //Il valore del delta rappresenta la velocità
        fieldManager.shipMovement(spaceShip, MovingDirections.RIGHT,4);
        fieldManager.shipShot(spaceShip);
        spaceShip.getShipBullet().setY(Dimensions.MAX_HEIGHT/10 + Dimensions.MAX_HEIGHT/100);
        assertTrue(fieldManager.checkSpaceShipShotCollision(spaceShip));
    }

    /**
     *Creiamo una spaceShip che viene posizionata all' estremo destro del campo di gioco.
     *In questo modo, facendola sparare, nessuno degli invaders(creati,invece, a partire dall' estremo sinistro)
     *dovrebbe essere colpito
     */
    @Test
    void checkFalseSpaceShipShotCollision(){
        while(spaceShip.getX() < Dimensions.MAX_WIDTH - Dimensions.SHIP_WIDTH) {
            fieldManager.shipMovement(spaceShip, MovingDirections.RIGHT, 4);
        }
        fieldManager.shipShot(spaceShip);
        spaceShip.getShipBullet().setY(Dimensions.MAX_HEIGHT/10 + Dimensions.MAX_HEIGHT/100);
        assertFalse(fieldManager.checkSpaceShipShotCollision(spaceShip));
    }
}