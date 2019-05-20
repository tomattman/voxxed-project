package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.managers.GameManager;
import com.mygdx.game.managers.InputManager;

public class VoxGame extends ApplicationAdapter {
    private OrthographicCamera camera;
	SpriteBatch batch;

	float width, height;

	GameManager gameManager;
	InputManager inputManager;

	private long fps = 25;
	private long delta = 1000 / fps;

	private long stateChangeTime;




	
	@Override
	public void create () {
		batch = new SpriteBatch();

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(width, height);
        camera.setToOrtho(false);

		gameManager = new GameManager(width,height);
		inputManager = new InputManager(gameManager);


		stateChangeTime = System.currentTimeMillis();

	}

	@Override
	public void render () {
		if(stateChangeTime + delta < System.currentTimeMillis()){
			gameManager.changeState();
			stateChangeTime = System.currentTimeMillis();
		}

		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

		batch.begin();

		gameManager.render(batch);
		inputManager.handleInput(camera);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		gameManager.dispose();
	}
}
