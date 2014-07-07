package com.arisota.wings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class PlayScreen implements Screen {
    WingsGame game;

    public PlayScreen(WingsGame _game) {
        game = _game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize");
    }

    @Override
    public void show() {
        System.out.println("show");
    }

    @Override
    public void hide() {
        System.out.println("hide");
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
    }
}
