package com.arisota.wings;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.arisota.wings.actors.AnimatedTexture;
import com.arisota.wings.tween.ActorAccessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TitleScreen implements Screen {
    public static final String SOUND_DISABLED = "soundEnabled";
    private WingsGame game;
    private Stage stage;
    private TweenManager manager;
    private Preferences prefs;

    TitleScreen(final WingsGame gam) {
        game = gam;
        manager = new TweenManager();
        prefs = Gdx.app.getPreferences("com.arisota.wings");
        stage = new Stage(new FitViewport(800, 480));
        Tween.registerAccessor(Actor.class, new ActorAccessor());
        Gdx.input.setInputProcessor(stage);
        Image background = new Image(new Texture(Gdx.files.internal("background.png")));
        BitmapFont blue = new BitmapFont(Gdx.files.internal("blue-alphabet.fnt"), Gdx.files.internal("blue-alphabet.png"), false);
        BitmapFont red = new BitmapFont(Gdx.files.internal("red-alphabet.fnt"), Gdx.files.internal("red-alphabet.png"), false);
        Button soundButton = makeSoundButton();
        Button start = makeStartButton();


        Label title = new Label("WINGS", new Label.LabelStyle(red, null));
        title.setFontScale(1.4f);
        Label instructions = new Label("TAP TO START", new Label.LabelStyle(blue, null));
        instructions.setFontScale(0.45f);
        Label instructions2 = new Label("TILT PHONE TO FLY", new Label.LabelStyle(blue, null));
        instructions2.setFontScale(0.45f);

        AnimatedTexture plane = new AnimatedTexture("redplane.png", 2, 15f);
        Image strings = new Image(new Texture("strings.png"));
        AnimatedTexture phone = new AnimatedTexture("phone.png", 2, 1.5f);
        Timeline.createSequence()
            .push(Tween.set(plane, ActorAccessor.POSITION_XY).target(-400f, 218f))
            .push(Tween.set(strings, ActorAccessor.POSITION_XY).target(-523f, 215f))
            .push(Tween.set(title, ActorAccessor.POSITION_XY).target(-900, 240f))
            .push(Tween.set(instructions, ActorAccessor.POSITION_XY).target(275, 90f))
            .push(Tween.set(instructions2, ActorAccessor.POSITION_XY).target(220, 50f))
            .push(Tween.set(phone, ActorAccessor.POSITION_XY).target(362f, 50f))
            .push(Tween.set(phone, ActorAccessor.ALPHA).target(0.0f))
            .push(Tween.set(soundButton, ActorAccessor.ALPHA).target(0.0f))
            .push(Tween.set(instructions, ActorAccessor.ALPHA).target(0.0f))
            .push(Tween.set(instructions2, ActorAccessor.ALPHA).target(0.0f))
            .beginParallel()
            .push(Tween.to(plane, ActorAccessor.POSITION_X, 3f).target(700).ease(TweenEquations.easeNone))
            .push(Tween.to(strings, ActorAccessor.POSITION_X, 3f).target(577).ease(TweenEquations.easeNone))
            .push(Tween.to(title, ActorAccessor.POSITION_X, 3f).target(213).ease(TweenEquations.easeNone))
            .end()
            .beginParallel()
            .push(Tween.to(plane, ActorAccessor.POSITION_X, 1f).target(939).ease(TweenEquations.easeNone))
            .push(Tween.to(strings, ActorAccessor.POSITION_XY, 0.3f).target(667, 140).ease(TweenEquations.easeNone))
            .push(Tween.to(phone, ActorAccessor.ALPHA, 1f).target(1.0f).ease(TweenEquations.easeNone))
            .push(Tween.to(strings, ActorAccessor.ALPHA, 0.3f).target(0f).ease(TweenEquations.easeNone))
            .push(Tween.to(instructions, ActorAccessor.ALPHA, 1f).target(1.0f).ease(TweenEquations.easeNone))
            .push(Tween.to(soundButton, ActorAccessor.ALPHA, 1f).target(1.0f).ease(TweenEquations.easeNone))
            .push(Tween.to(instructions2, ActorAccessor.ALPHA, 1f).target(1.0f).ease(TweenEquations.easeNone))
            .end()
            .start(manager);

        stage.addActor(background);
        stage.addActor(strings);
        stage.addActor(plane);
        stage.addActor(phone);
        stage.addActor(title);
        stage.addActor(instructions);
        stage.addActor(instructions2);
        stage.addActor(start);
        stage.addActor(soundButton);
    }

    private ImageButton makeStartButton() {
        ImageButton start = new ImageButton(new SpriteDrawable(new Sprite(new Texture("transparent.png"))));
        start.setWidth(800f);
        start.setHeight(450f);
        start.setBounds(0f, 0f, 800f, 480f);
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.play);
            }
        });
        return start;
    }

    private ImageButton makeSoundButton() {
        Texture buttons = new Texture("speaker_on_off.png");
        TextureRegion[][] tmp = TextureRegion.split(buttons, 85, 85);
        TextureRegionDrawable[] frames = new TextureRegionDrawable[2];
        for (int i = 0; i < 2; i++)
            frames[i] = new TextureRegionDrawable(tmp[0][i]);

        final ImageButton btn = new ImageButton(frames[0], null, frames[1]);
        btn.setBounds(700f, 0f, 85f, 85f);
        btn.setPosition(700f, 0f);
        btn.setHeight(85f);
        btn.setWidth(85f);
        btn.setChecked(prefs.getBoolean(SOUND_DISABLED));
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("clicked sound!");
                prefs.putBoolean(SOUND_DISABLED, btn.isChecked());
                prefs.flush();
            }
        });
        return btn;
    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
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