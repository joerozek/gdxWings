package com.arisota.wings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WingsGame extends Game {
	SpriteBatch batch;

	@Override
	public void create () {
        batch = new SpriteBatch();
        setScreen(new TitleScreen(this));
	}

	@Override
	public void render () {
        super.render();
	}

    @Override
    public void dispose() {
        batch.dispose();
    }
}
