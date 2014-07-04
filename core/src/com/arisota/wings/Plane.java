package com.arisota.wings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Joe on 7/3/2014.
 */
public class Plane {
    private Texture tx;
    private TextureRegion[] frames;
    private static final int NUM_FRAMES = 2;
    private Animation fly;
    private float stateTime;

    Plane () {
        tx = new Texture("redplane.png");

        TextureRegion[][] tmp = TextureRegion.split(tx, tx.getWidth()/NUM_FRAMES, tx.getHeight());
        frames = new TextureRegion[NUM_FRAMES];
        int index = 0;
        for (int i = 0; i < NUM_FRAMES; i++) {
            frames[index++] = tmp[0][i];
        }
        fly = new Animation(0.067f, frames);
    }

    public TextureRegion getAnimationFrame() {
        stateTime += Gdx.graphics.getDeltaTime();
        return fly.getKeyFrame(stateTime, true);
    }

    public void dispose() {
        tx.dispose();
    }


}
