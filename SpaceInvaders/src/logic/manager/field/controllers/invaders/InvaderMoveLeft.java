package logic.manager.field.controllers.invaders;

import logic.sprite.dinamic.invaders.Invader;
import java.util.List;

/**
 * Movimento a sinistra degli invaders.
 *
 */
public class InvaderMoveLeft implements Controller{

    @Override
    public void move(List<Invader> invaders) {
            for(Invader invader:invaders) {
                invader.moveLeft();
            }
    }
}
