package logic.manager.field.invader.controllers;

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
