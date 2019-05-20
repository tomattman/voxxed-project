package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.services.Renderable;

public class Pipe implements Renderable {

    private Sprite topPipe;
    private Sprite bottomPipe;

    private Vector2 topPosition;
    private Vector2 bottomPosition;

    private float speed;

    private float screenWidth, screenHeight;

    private float topWidth, topHeight;
    private float bottomWidth, bottomHeight;

    public boolean isCounted = false;

    public Pipe(float screenWidth, float screenHeight, float resizeFactor, Bird bird){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        bottomPipe = new Sprite(new Texture("pipe.png"));
        bottomWidth = bottomPipe.getWidth() * (screenWidth / resizeFactor);
        bottomHeight = screenHeight;
        bottomPipe.setSize(bottomWidth, bottomHeight);
        bottomPipe.setPosition(screenWidth / 2 - bottomWidth / 2, screenHeight);

        topPipe = new Sprite(new Texture("toppipe.png"));
        topWidth = topPipe.getWidth() * (screenWidth / resizeFactor);
        topHeight = screenHeight;
        topPipe.setSize(topWidth, topHeight);
        topPipe.setPosition(screenWidth / 2 - topWidth / 2, screenHeight / 2 - topHeight / 2);

        float topSize = (float)(Math.random() * screenHeight / 1.5);
        float bottomSize = (float)(Math.random() * screenHeight - topSize - bird.getHeight() * 1.5);

        bottomPosition  = new Vector2(screenWidth, -screenHeight + bottomSize);
        topPosition = new Vector2(screenWidth, screenHeight - topSize);
        speed = -10;
    }

    @Override
    public void render(SpriteBatch batch) {
        topPipe.setPosition(topPosition.x, topPosition.y);
        bottomPipe.setPosition(bottomPosition.x, bottomPosition.y);
        topPipe.draw(batch);
        bottomPipe.draw(batch);
    }

    @Override
    public void changeState() {
        topPosition.x += speed;
        bottomPosition.x += speed;
    }

    @Override
    public void dispose() {
        topPipe.getTexture().dispose();
        bottomPipe.getTexture().dispose();
    }

    public boolean checkCollision(Bird bird){
        Rectangle birdRect = bird.getRectangle();
        Rectangle topRect = new Rectangle(topPosition.x, topPosition.y, topWidth, topHeight);
        Rectangle bottomRect = new Rectangle(bottomPosition.x, bottomPosition.y, bottomWidth, bottomHeight);

        return Intersector.overlaps(topRect, birdRect) || Intersector.overlaps(bottomRect, birdRect);
    }

    public boolean isOnScreen(){
        return this.topPosition.x + bottomWidth > 0 && this.topPosition.x < screenWidth;
    }

    public boolean isAfterBird(Bird bird){
        return topPosition.x + topWidth / 2 < bird.getPosition().x - bird.getWidth() / 2;
    }
}
