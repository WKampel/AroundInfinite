package com.provision.aroundinfinite.misc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public interface Drawable {
    void render(SpriteBatch batch, Matrix4 matrix);

    void render(ShapeRenderer renderer, Matrix4 matrix);
}
