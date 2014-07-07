package com.arisota.wings;

import com.badlogic.gdx.Game;

public class WingsGame extends Game {
    public TitleScreen title;
    public PlayScreen play;

	@Override
	public void create () {
        title = new TitleScreen(this);
        play = new PlayScreen(this);
        setScreen(title);
	}

	@Override
	public void render () {
        super.render();
	}

    @Override
    public void dispose() { }
}
