package com.arisota.wings.tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Lidgx Actor tween accessor.
 *
 */
public class ActorAccessor implements TweenAccessor<Actor>
{
    public static final int POSITION_XY = 1;
    public static final int SCALE_XY = 2;
    public static final int ALPHA = 3;
    public static final int POSITION_X = 4;

    @Override
    public int getValues(Actor target, int tweenType, float[] returnValues)
    {
        switch (tweenType)
        {
            case POSITION_XY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;
            case POSITION_X:
                returnValues[0] = target.getX();
                return 1;
            case SCALE_XY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Actor target, int tweenType, float[] newValues)
    {
        switch (tweenType)
        {
            case POSITION_XY:
                target.setX(newValues[0]);
                target.setY(newValues[1]);
                break;
            case POSITION_X:
                target.setX(newValues[0]);
                break;
            case SCALE_XY:
                target.setScaleX(newValues[0]);
                target.setScaleY(newValues[1]);
                break;
            case ALPHA:
                Color c = target.getColor();
                target.setColor(c.r, c.g, c.b, newValues[0]);
                break;
            default:
                assert false;
        }
    }
}