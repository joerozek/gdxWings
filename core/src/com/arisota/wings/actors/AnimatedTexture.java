package com.arisota.wings.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Joe on 7/3/2014.
 */
public class AnimatedTexture extends Actor {
    private Texture tx;
    private TextureRegion[] frames;
    private Animation fly;
    private float stateTime;

    public AnimatedTexture(String filename, int numFrames, float fps) {
        super();
        tx = new Texture(filename);
        int frameWidth = tx.getWidth() / numFrames;
        TextureRegion[][] tmp = TextureRegion.split(tx, frameWidth, tx.getHeight());
        frames = new TextureRegion[numFrames];
        int index = 0;
        for (int i = 0; i < numFrames; i++) {
            frames[index++] = tmp[0][i];
        }
        fly = new Animation(1.0f/fps, frames);
    }

    public TextureRegion getAnimationFrame() {
        stateTime += Gdx.graphics.getDeltaTime();
        return fly.getKeyFrame(stateTime, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = batch.getColor();
        batch.setColor(getColor());
        batch.draw(getAnimationFrame(), getX(), getY());
    }
}
