package logic.manager.field.controllers.invaders;

import logic.sprite.dinamic.invaders.Invader;
import java.util.List;

/**
 * Movimento a destra degli invaders.
 *
 */
public class InvaderMoveRight implements Controller {

    @Override
    public void move(List<Invader> invaders) {
            for(Invader invader:invaders) {
                invader.moveRight();
            }
    }
}
