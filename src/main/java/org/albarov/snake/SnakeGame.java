package org.albarov.snake;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;


public class SnakeGame extends Game {

    public static final int WIDTH = 25;
    public static final int HEIGHT = 25;
    public static final int GOAL = 28;

    private int turnDelay;

    private Snake snake;

    private Apple apple;

    private int score;

    private boolean isGameStopped;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        snake = new Snake(WIDTH/2, HEIGHT/2);
        createNewApple();
        turnDelay = 300;
        isGameStopped = false;
        setTurnTimer(turnDelay);
        drawScene();
        score = 0;
    }

    private void drawScene() {
        for (int x=0;x<WIDTH;x++) {
            for (int y=0;y<HEIGHT;y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void increaseScore() {
        score+=5;
        setScore(score);
    }

    private void increaseSpeed() {
        if (turnDelay > 50) {
            turnDelay-=10;
        }
        setTurnTimer(turnDelay);
    }

    private void createNewApple() {

        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            apple = new Apple(x, y);
        }
        while (snake.checkCollision(apple));


    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive) {
            increaseScore();
            increaseSpeed();
            createNewApple();
        }
        if (!snake.isAlive) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.WHITE, "GAME OVER", Color.RED, 22);
    }

    private void win() {
        stopTurnTimer();;
        isGameStopped = true;
        showMessageDialog(Color.WHITE, "YOU WIN", Color.GREEN, 22);
    }

    private void exit() {
        showMessageDialog(Color.WHITE, "GOODBYE!", Color.GREEN, 22);

        System.exit(0);


    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case DOWN: snake.setDirection(Direction.DOWN); break;
            case RIGHT: snake.setDirection(Direction.RIGHT); break;
            case LEFT: snake.setDirection(Direction.LEFT); break;
            case UP: snake.setDirection(Direction.UP); break;
            case SPACE: if (isGameStopped) createGame(); break;
            case ESCAPE: exit();

        }
    }
}
