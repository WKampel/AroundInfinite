package com.provision.aroundinfinite.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.provision.aroundinfinite.managers.ColorManager;
import com.provision.aroundinfinite.managers.DebrisManager;
import com.provision.aroundinfinite.managers.UtilityManager;

public class FlyingBomb extends Debris {

    private float currentExplosionRadius, targetExplosionRadius, minExplosionRadius, maxExplosionRadius, explosionTime, minExplosionLifeTime, maxExplosionLifeTime, currentExplosionDuration;
    private float minTimeUntilExplosion, maxTimeUntilExplosion;
    private float timeUntilExplosion, explosionLifeTime;
    private Interpolation interpolation;
    private Color currentExplosionColor;

    private Polygon explosionCollision;

    private float[] verts;

    private enum State{
        CUBE, EXPLODING, EXPLODED, DISAPPEARING
    }

    private State state;

    public FlyingBomb() {
        super();

        state = State.CUBE;

        width = 1.5f;
        height = 1.5f;

        minExplosionRadius = 3f;
        maxExplosionRadius = 7f;

        minTimeUntilExplosion = 3;
        maxTimeUntilExplosion = 8;

        minExplosionLifeTime = 3f;
        maxExplosionLifeTime = 9f;

        targetExplosionRadius = MathUtils.random(minExplosionRadius, maxExplosionRadius);
        currentExplosionRadius = 0;

        explosionTime = 1f;

        interpolation = Interpolation.bounce;

        currentExplosionColor = new Color(ColorManager.getManager().flyingBomb);
        currentExplosionColor.a = 0;

        timeUntilExplosion = MathUtils.random(minTimeUntilExplosion, maxTimeUntilExplosion);
        explosionLifeTime = MathUtils.random(minExplosionLifeTime, maxExplosionLifeTime);

        color = ColorManager.getManager().flyingBomb;

        explosionCollision = new Polygon();

        verts = new float[12];
    }

    @Override
    public void update() {
        super.update();

        if(!frozen) {
            if (state != State.CUBE) {
                verts = UtilityManager.getManager().circleToVerts(getPosition().x, getPosition().y, currentExplosionRadius, verts);
                explosionCollision.setVertices(verts);
                collisionComponent.setCollisionVerts(explosionCollision.getTransformedVertices());
                currentExplosionDuration += Gdx.graphics.getDeltaTime();
            }

            float progress = Math.min(1f, currentExplosionDuration / explosionTime);  // 0 -> 1
            float alpha = interpolation.apply(progress);

            switch (state) {
                case CUBE:
                    timeUntilExplosion -= Gdx.graphics.getDeltaTime();

                    if (timeUntilExplosion <= 0) {
                        meteorComponent.xVelocity = 0;
                        meteorComponent.yVelocity = 0;
                        state = State.EXPLODING;
                    }
                    break;
                case EXPLODED:
                case EXPLODING:

                    currentExplosionRadius = MathUtils.lerp(0, targetExplosionRadius, alpha);

                    float r = MathUtils.lerp(ColorManager.getManager().flyingBomb.r, ColorManager.getManager().flyingCube.r, alpha);
                    float g = MathUtils.lerp(ColorManager.getManager().flyingBomb.g, ColorManager.getManager().flyingCube.b, alpha);
                    float b = MathUtils.lerp(ColorManager.getManager().flyingBomb.b, ColorManager.getManager().flyingCube.b, alpha);
                    float a = MathUtils.lerp(ColorManager.getManager().flyingBomb.a, ColorManager.getManager().flyingCube.a, alpha);

                    currentExplosionColor.set(r, g, b, a);

                    explosionLifeTime -= Gdx.graphics.getDeltaTime();
                    if (explosionLifeTime <= 0) {
                        state = State.DISAPPEARING;
                        currentExplosionDuration = 0;
                    }

                    break;
                case DISAPPEARING:

                    currentExplosionRadius = MathUtils.lerp(targetExplosionRadius, 0, alpha);

                    if (currentExplosionRadius <= 0) {
                        DebrisManager.getManager().destroy(this);
                    }
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch, Matrix4 matrix4) {

        super.render(spriteBatch, matrix4);

        if (state != State.CUBE) {

            width = 0;
            height = 0;

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            UtilityManager.getManager().shapeRenderer.setProjectionMatrix(matrix4);
            UtilityManager.getManager().shapeRenderer.begin();
            if(!frozen) {
                UtilityManager.getManager().shapeRenderer.setColor(currentExplosionColor);
            }
            UtilityManager.getManager().shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            UtilityManager.getManager().shapeRenderer.circle(position.x, position.y, currentExplosionRadius, 6);
            UtilityManager.getManager().shapeRenderer.end();
        }
    }
}
