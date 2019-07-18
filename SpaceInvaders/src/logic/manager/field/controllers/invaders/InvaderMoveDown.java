package logic.manager.field.controllers.invaders;

import logic.sprite.dinamic.invaders.Invader;
import java.util.List;

/**
 * Movimento in basso degli invaders.
 *
 */
public class InvaderMoveDown implements Controller {

    @Override
    public void move(List<Invader> invaders) {
        for (Invader invader : invaders) {
            invader.moveDown();
        }
    }
}
