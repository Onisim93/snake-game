package org.albarov.snake;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private List<GameObject> snakeParts = new ArrayList<>();

    private Direction direction = Direction.LEFT;

    public boolean isAlive = true;

    public Snake(int x, int y) {
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x+1, y));
        snakeParts.add(new GameObject(x+2, y));
    }

    public GameObject createNewHead() {
        GameObject newHead = null;
        GameObject oldHead = snakeParts.get(0);

        switch (direction) {
            case LEFT: newHead = new GameObject(oldHead.x-1, oldHead.y); break;
            case UP: newHead = new GameObject(oldHead.x, oldHead.y-1); break;
            case RIGHT: newHead = new GameObject(oldHead.x + 1, oldHead.y); break;
            case DOWN: newHead = new GameObject(oldHead.x, oldHead.y+1); break;
        }

        return newHead;
    }

    public int getLength() {
        return snakeParts.size();
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size()-1);
    }

    public boolean checkCollision(GameObject object) {
        for (GameObject o : snakeParts) {
            if (object.x == o.x && object.y == o.y) {
                return true;
            }
        }
        return false;
    }

    public void draw(Game game) {
        boolean isHeadDraws = false;
        Color currentColor = isAlive ? Color.BLACK : Color.RED;
        for (GameObject o : snakeParts) {
            if (!isHeadDraws) {
                game.setCellValueEx(o.x, o.y, Color.NONE, HEAD_SIGN, currentColor, 75);
                isHeadDraws = true;
            }
            else {
                game.setCellValueEx(o.x, o.y, Color.NONE, BODY_SIGN, currentColor, 75);
            }
        }
    }

    public void setDirection(Direction direction) {
        if ((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        }
        if ((this.direction == Direction.UP || this.direction == Direction.DOWN) && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        }

        if (direction == Direction.UP && this.direction == Direction.DOWN) {
            return;
        } else if (direction == Direction.LEFT && this.direction == Direction.RIGHT) {
            return;
        } else if (direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        } else if (direction == Direction.DOWN && this.direction == Direction.UP) {
            return;
        }

        this.direction = direction;
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (newHead.x < 0 || newHead.x > SnakeGame.WIDTH-1 || newHead.y < 0 || newHead.y > SnakeGame.HEIGHT-1 || checkCollision(newHead)) {
            isAlive = false;
            return;
        }

        snakeParts.add(0, newHead);

        if (newHead.x == apple.x && newHead.y == apple.y) {
            apple.isAlive = false;
        }
        else {
            removeTail();
        }

    }
}
