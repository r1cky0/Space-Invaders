package logic.manager.field.controllers.invaders;

import logic.sprite.dinamic.invaders.Invader;
import java.util.List;

public interface Controller {
    public void move(List<Invader> invaders);
}
