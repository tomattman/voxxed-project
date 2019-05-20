package com.mygdx.game.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.objects.Bird;
import com.mygdx.game.objects.Pipe;
import com.mygdx.game.services.Renderable;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements Renderable {

    private float width, height;
    private final static float RESIZE_FACTOR = 2500f;

    private Bird bird;
    private List<Pipe> pipes;
    private BitmapFont scoreFont;
    private BitmapFont startFont;

    private int score = 0;

    private boolean isGameStarted = false;

    public GameManager(float width, float height) {
        this.width = width;
        this.height = height;

        scoreFont = new BitmapFont();
        scoreFont.setColor(Color.RED);
        scoreFont.getData().setScale(5);

        startFont = new BitmapFont();
        startFont.setColor(Color.GREEN);
        startFont.getData().setScale(5);

        pipes = new ArrayList<Pipe>();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isGameStarted) {
            bird.render(batch);
            for (Pipe pipe : pipes) {
                pipe.render(batch);
            }

            if (checkCollisions()) {
                stopGame();
            }
        } else {
            startFont.draw(batch, "Click to start", width / 2, height / 2);
        }
        scoreFont.draw(batch, "Score : " + score, 100, 100);
    }

    @Override
    public void dispose() {
        bird.dispose();
        for (Pipe pipe : pipes) {
            pipe.dispose();
        }
    }

    @Override
    public void changeState() {
        if (isGameStarted) {
            bird.changeState();
            bird.move();
            for (Pipe pipe : pipes) {
                pipe.changeState();
                if(!pipe.isCounted && pipe.isAfterBird(bird)){
                    score ++;
                    pipe.isCounted = true;
                }
            }

            for (int i = 0; i < pipes.size(); i++) {
                if (!pipes.get(i).isOnScreen()) {
                    pipes.remove(i);
                    i--;
                }
            }

            if (pipes.size() < 1) {
                pipes.add(new Pipe(width, height, RESIZE_FACTOR, bird));
            }
        }
    }

    public boolean checkCollisions() {
        for (Pipe pipe : pipes) {
            if (pipe.checkCollision(bird)) {
                return true;
            }
        }
        return false;
    }

    public Bird getBird() {
        return bird;
    }

    public void startGame() {
        bird = new Bird(width, height, RESIZE_FACTOR);
        pipes.add(new Pipe(width, height, RESIZE_FACTOR, bird));
        score = 0;
        isGameStarted = true;

    }

    public void stopGame() {
        isGameStarted = false;
        pipes.clear();
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }
}
