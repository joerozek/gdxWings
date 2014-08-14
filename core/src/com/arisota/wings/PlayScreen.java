package com.arisota.wings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class PlayScreen implements Screen {
    public static final int POSITION_ITERATIONS = 2;
    public static final int VELOCITY_ITERATIONS = 5;
    public static final float TIMESTEP = 1/45F;

    private boolean gameIsOver = false;
    private int framesBetweenRocks = 50;
    private int frameCount = 0;
    private ArrayList<Body> rocks;
    private int score = 0;

    WingsGame game;
    World world;

    Body plane;
    RockFactory rockFactory;
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();
    Vector2 planeMovement = new Vector2(0f, 0f);
    Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private Sprite planeSprite;
    Texture background;
    Sprite bgSprite;
    float scrollTimer;

    public PlayScreen(WingsGame _game) {
        game = _game;
    }

    @Override
    public void render(float delta) {
        frameCount++;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, camera.combined);

        updateGame();
        plane.setLinearVelocity(planeMovement);
        world.step(TIMESTEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.setScale(3f);
        font.draw(batch, "score:" + score, 100f, 300f);
        scrollTimer += Gdx.graphics.getDeltaTime();
        if(scrollTimer>1.0f)
            scrollTimer = 0.0f;
        bgSprite.setU(scrollTimer);
        bgSprite.setU2(scrollTimer+1);
        bgSprite.setSize(9.0909f, 5.4545f);
        bgSprite.setPosition(-4.54545f, -2.72725f);
        bgSprite.draw(batch);
        Sprite s = ((WingsUserData)plane.getUserData()).getSprite();
        s.setPosition(plane.getPosition().x, plane.getPosition().y);
        s.setRotation(plane.getAngle() * MathUtils.radiansToDegrees);

        for (Body rock : rocks) {
            Sprite rs = ((WingsUserData)rock.getUserData()).getSprite();
            if (rs != null){
                rs.setPosition(rock.getPosition().x, rock.getPosition().y);
                rs.setRotation(rock.getAngle() * MathUtils.radiansToDegrees);
                rs.draw(batch);
            }
        }

        s.draw(batch);
        if (rocks.size() > 0)
            font.draw(batch, "size:" + rocks.get(0).getPosition().x, 100f, 200f);
        batch.end();
    }

    private void updateGame() {
        if (gameIsOver) return;
        calculateInput();
        updateScore();
        removeOffscreenRocks();
        if (frameCount == framesBetweenRocks)
            dispatchRock();
    }

    private void updateScore() {
        for (Body rock : rocks) {
            WingsUserData data = ((WingsUserData) rock.getUserData());
            if (rock.getPosition().x < plane.getPosition().x && !data.hasBeenScored()) {
                data.setBeenScored(true);
                score++;
            }
        }
    }

    private void removeOffscreenRocks() {
        for (int i = rocks.size()-1; i>=0; i--) {
            Body rock = rocks.get(i);
            if (rock.getPosition().x < -4.5454f) {
                world.destroyBody(rock);
                rocks.remove(i);
                rock.setUserData(null);
                rock = null;
            }
        }
    }

    private void calculateInput() {
        float x = Gdx.input.getAccelerometerX();
        if (x > 6) {
            planeMovement.y = 2f;
            plane.setTransform(plane.getPosition(), .17f);
        } else {
            planeMovement.y = -2f;
            plane.setTransform(plane.getPosition(), -.17f);
        }
    }

    private void dispatchRock() {
        Body rock = rockFactory.newRock();
        rock.setLinearVelocity(-4f, 0);
        rocks.add(rock);
        frameCount = 0;
    }


    @Override
    public void resize(int width, int height) {
        System.out.println("resize");
    }

    @Override
    public void show() {
        world = new World(new Vector2(0f,0f), true);
        rocks = new ArrayList<Body>(200);
        PhysicsLoader loader = new PhysicsLoader(Gdx.files.internal("plane.json"));
        rockFactory = new RockFactory(loader, world);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(9.0909f, 5.4545f);
        background = new Texture(Gdx.files.internal("background.png"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        bgSprite = new Sprite(background);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(-3, 0);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 1.0f;
        fixture.restitution = .5f;
        fixture.friction = 0f;
        PlaneUserData userData = new PlaneUserData();
        plane = world.createBody(bodyDef);
        loader.attachFixture(plane, "plane", fixture, 1f);
        plane.setUserData(userData);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);


        ChainShape boundaryShape = new ChainShape();
        boundaryShape.createChain(new Vector2[]{new Vector2(-10, -3), new Vector2(10, -3)});
        fixture.shape = boundaryShape;
        fixture.isSensor = true;

        world.createBody(bodyDef).createFixture(fixture);

        boundaryShape.createChain(new Vector2[]{new Vector2(-10, 3), new Vector2(10, 3)});
        world.createBody(bodyDef).createFixture(fixture);

        boundaryShape.dispose();
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (isPlane(contact.getFixtureA().getBody()) || isPlane(contact.getFixtureB().getBody())) {
                    if (gameIsOver == false)
                        gameOver();
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    private boolean isPlane(Body body) {
        return (body.getUserData() != null && ((WingsUserData) body.getUserData()).getType() == WingsUserData.Type.PLANE);
    }

    public void gameOver() {
        System.out.println("gameover");
        world.setGravity(new Vector2(0, -9.8f));
        plane.setLinearVelocity(0f, 0f);
        gameIsOver = true;
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