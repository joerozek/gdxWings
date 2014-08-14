package com.arisota.wings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Joe on 8/12/2014.
 */
public class RockUserData implements WingsUserData {
    private boolean beenScored = false;
    private final Sprite sprite;
    private final Type type;

    public RockUserData(Type t) {
        type = t;
        if (t == Type.STALAGMITE)
            sprite = new Sprite(new Texture("rockGrass.png"));
        else
            sprite = new Sprite((new Texture("rockGrassDown.png")));
        sprite.setSize(1f,2.2f);
        System.out.println(sprite.getWidth()+ " " + sprite.getHeight());
        sprite.setOrigin(0f, 0f);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public boolean hasBeenScored() {
        return beenScored;
    }

    @Override
    public void setBeenScored(boolean value) {
        beenScored = value;
    }

    @Override
    public Sprite getSprite() {
//        return null;
        return sprite;
    }
}
