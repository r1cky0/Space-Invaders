package logic.manager.field.invader.controllers;

import logic.sprite.dinamic.invaders.Invader;
import java.util.List;

/**
 * Movimento in basso degli invaders.
 *
 */
public class InvaderMoveDown implements Controller {

    public void move(List<Invader> invaders) {
        for (Invader invader : invaders) {
            invader.moveDown();
        }
    }
}
