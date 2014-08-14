package com.arisota.wings;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Joe on 8/4/2014.
 */
public class RockFactory {

    BodyDef bodyDef;
    FixtureDef fixture;
    World world;
    PhysicsLoader loader;

    public RockFactory(PhysicsLoader ldr, World wld) {
        loader = ldr;
        world = wld;
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(10, 0);

        fixture = new FixtureDef();
        fixture.density = 1.0f;
        fixture.restitution = .05f;
        fixture.friction = 10f;
    }

    public Body newRock() {
        Body rock;

        if (Math.random() < 0.5) {
            bodyDef.position.set(10, -3);
            rock = world.createBody(bodyDef);
            rock.setUserData(new RockUserData(WingsUserData.Type.STALAGMITE));
            loader.attachFixture(rock, "stalagmite", fixture, 1f);
        } else {
            bodyDef.position.set(10, 1);
            rock = world.createBody(bodyDef);
            rock.setUserData(new RockUserData(WingsUserData.Type.STALACTITE));
            loader.attachFixture(rock, "stalactite", fixture, 1f);
        }

        rock.setGravityScale(0);

        return rock;
    }

}
