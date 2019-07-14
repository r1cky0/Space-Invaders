package test;

import logic.manager.field.FieldManager;
import logic.manager.field.MovingDirections;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.bullets.InvaderBullet;
import main.Dimensions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FieldManagerTest {

    //CTRL + SHIFT + F10 Per fare il test: se click in un metodo viene testato solo quel metodo,
    //altrimenti tutta la classe di test

    /**
     * Testiamo sparo di un invader e collisione con la spaceShip.
     * Il field manager fa sparare randomicamente uno degli invaders. La spaceShip viene spostata al di sotto
     * del bullet attraverso la funzione di shipMovement.
     * Fatto ció il bullet viene spostato verticalmente fino ad entrare in collisone con la nave(é fatto in modo
     * istantaneo anche se nel sistema avviene in tempo reale)
     */
    @Test
    void checkInvaderShotCollision() {
        FieldManager fieldManager = new FieldManager();
        Coordinate coordinate = new Coordinate(Dimensions.MAX_WIDTH/32,Dimensions.MAX_HEIGHT - Dimensions.SHIP_WIDTH);
        SpaceShip spaceShip = new SpaceShip(coordinate);
        fieldManager.invaderShot();
        List<Bullet> invaderBullets = fieldManager.getInvaderBullets();

        //Necessario per spostare la ship in corrispondenza dell' invaderBullet
        if(spaceShip.getX() < invaderBullets.get(0).getX()){
            while(spaceShip.getX() < invaderBullets.get(0).getX()){
                fieldManager.shipMovement(spaceShip,MovingDirections.RIGHT,1);
            }
        }
        else{
            while(spaceShip.getX() > invaderBullets.get(0).getX()){
                fieldManager.shipMovement(spaceShip,MovingDirections.LEFT,1);
            }
        }

        invaderBullets.get(0).setY(Dimensions.MAX_HEIGHT - Dimensions.SHIP_WIDTH/2);
        assertTrue(fieldManager.checkInvaderShotCollision(spaceShip));
    }

    /**
     *Simuliamo sparo e spostamento del shipBullet in verticale
     *Fatto in modo istantaneo, nel sistema in realtá sale in tempo reale,
     *ma a noi interessa solo testare che venga individuata la collisione
     *Settiamo quindi la y del proiettile appena sopra l' altezza della prima fila di invaders
     */
    @Test
    void checkSpaceShipShotCollision() {
        FieldManager fieldManager = new FieldManager();
        Coordinate coordinate = new Coordinate(Dimensions.MAX_WIDTH/32,Dimensions.MAX_HEIGHT - Dimensions.SHIP_WIDTH);
        SpaceShip spaceShip = new SpaceShip(coordinate);

        //Includiamo nel test anche la funzione di movimento della ship per verificarne il funzionamento
        //Il valore del delta, impostato manualmente a 1, é verosimile e verificato attraverso diverse prove
        fieldManager.shipMovement(spaceShip,MovingDirections.RIGHT,1);

        fieldManager.shipShot(spaceShip);
        spaceShip.getShipBullet().setY(Dimensions.MAX_HEIGHT/10 + Dimensions.MAX_HEIGHT/100);

        assertTrue(fieldManager.checkSpaceShipShotCollision(spaceShip));
    }
}