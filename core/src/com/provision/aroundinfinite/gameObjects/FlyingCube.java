package com.provision.aroundinfinite.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.World;
import com.provision.aroundinfinite.managers.ColorManager;
import com.provision.aroundinfinite.managers.UtilityManager;

public class FlyingCube extends Debris {

    public FlyingCube() {
        super();

        color = ColorManager.getManager().flyingCube;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch, Matrix4 matrix4) {
        super.render(spriteBatch, matrix4);
    }
}
