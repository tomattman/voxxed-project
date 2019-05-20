package com.mygdx.game.services;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Renderable {
    void render(SpriteBatch batch);
    void changeState();
    void dispose();
}
