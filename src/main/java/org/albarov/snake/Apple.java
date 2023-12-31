package org.albarov.snake;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

public class Apple extends GameObject{

    public boolean isAlive = true;
    private static final String APPLE_SIGN = "\uD83C\uDF4E";
    public Apple(int x, int y) {
        super(x, y);
    }

    public void draw(Game game) {
        game.setCellValueEx(x, y, Color.GREEN, APPLE_SIGN);
    }
}
