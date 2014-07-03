package com.arisota.wings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class TitleScreen implements Screen {
    WingsGame game;
    Texture background;
    Plane plane;
    Texture phone;
    OrthographicCamera camera;

    TitleScreen(final WingsGame gam) {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 450);
        background = new Texture("background.png");
        plane = new Plane();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
//        game.font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
//        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.draw(background, 0, 0);
        game.batch.draw(plane.getAnimationFrame(), 25, 25);
        game.batch.end();
    }


    @Override
    public void dispose() {
        background.dispose();
        plane.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
