package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputManager {
    private GameManager gameManager;

    public InputManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void handleInput(OrthographicCamera camera){
        if(Gdx.input.justTouched()){
            if(!gameManager.isGameStarted()){
                gameManager.startGame();
            } else {
                Vector3 point = getPoint(camera);
                gameManager.getBird().jump();
            }
        }
    }

    private Vector3 getPoint(OrthographicCamera camera){
        Vector3 point = new Vector3();
        point.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(point);
        return point;
    }
}
