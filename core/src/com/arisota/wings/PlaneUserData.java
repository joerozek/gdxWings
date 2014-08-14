package com.arisota.wings;

import com.arisota.wings.actors.AnimatedTexture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Joe on 8/4/2014.
 */
public class PlaneUserData implements WingsUserData {
    private final AnimatedTexture texture;
    private final Sprite sprite;

    public PlaneUserData() {
        texture = new AnimatedTexture("redplane.png", 2, 15);
        sprite = new Sprite();
        sprite.setSize(1f, 0.8295f);
        sprite.setOrigin(0f, 0f);
    }

    @Override
    public Type getType() {
        return Type.PLANE;
    }

    @Override
    public boolean hasBeenScored() {
        return false;
    }

    @Override
    public void setBeenScored(boolean value) {
        //no op
    }

    @Override
    public Sprite getSprite() {
        sprite.setRegion(texture.getAnimationFrame());
        return sprite;
    }
}
