package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.services.Renderable;

import java.util.ArrayList;
import java.util.List;

public class Bird implements Renderable {
    private List<Sprite> sprites;
    private Vector2 position;

    private float speed;

    private int spriteNum;

    private float screenWidth, screenHeight;

    private float width, height;

    public Bird(float screenWidth, float screenHeight, float resizeFactor) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        sprites = new ArrayList<Sprite>();
        for (int i = 0; i < 6; i++) {
            Sprite sprite = new Sprite(new Texture("hero/hero_" + i + ".png"));
            width = sprite.getWidth() * (screenWidth / resizeFactor);
            height = sprite.getHeight() * (screenHeight / resizeFactor);
            sprite.setSize(width, height);
            sprite.setPosition(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2);
            sprites.add(sprite);
        }
        position = new Vector2(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2);
        spriteNum = 0;
        speed = 0;

    }

    public void move() {
        if (position.y + speed + height < screenHeight && position.y + speed > 0) {
            position.y = position.y + speed;
        } else {
            if(position.y + speed + height > screenHeight){
                position.y = screenHeight - height;
            } else {
                position.y = 0;
            }
        }
    }

    public void jump() {
        this.speed = 15;
    }


    @Override
    public void render(SpriteBatch batch) {
        sprites.get(spriteNum).setPosition(position.x, position.y);
        sprites.get(spriteNum).draw(batch);
    }

    @Override
    public void dispose() {
        for (Sprite sprite : sprites) {
            sprite.getTexture().dispose();
        }
    }

    @Override
    public void changeState() {
        speed--;
        spriteNum = spriteNum == 5 ? 0 : spriteNum + 1;
    }

    Vector2 getPosition() {
        return position;
    }

    float getWidth() {
        return width;
    }

    float getHeight() {
        return height;
    }

    Rectangle getRectangle() {
        return new Rectangle(position.x, position.y, width, height);
    }


}
