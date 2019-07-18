package logic.manager.field.invader.controllers;

import logic.sprite.dinamic.invaders.Invader;
import java.util.List;

public interface Controller {
    public void move(List<Invader> invaders);
}
