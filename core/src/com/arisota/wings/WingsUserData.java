package com.arisota.wings;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Joe on 8/12/2014.
 */
public interface WingsUserData {
    public enum Type {
        PLANE,
        STALAGMITE,
        STALACTITE
    }

    Type getType();
    boolean hasBeenScored();
    void setBeenScored(boolean value);
    Sprite getSprite();

}
