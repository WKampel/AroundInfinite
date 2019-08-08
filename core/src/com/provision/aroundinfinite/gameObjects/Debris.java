package com.provision.aroundinfinite.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.provision.aroundinfinite.components.CollisionComponent;
import com.provision.aroundinfinite.components.CollisionListener;
import com.provision.aroundinfinite.components.MeteorComponent;
import com.provision.aroundinfinite.components.RandomScaleComponent;
import com.provision.aroundinfinite.managers.ColorManager;
import com.provision.aroundinfinite.managers.GlobalsManager;
import com.provision.aroundinfinite.managers.UtilityManager;

public class Debris extends GameObject {

    protected float width, height, rotateBy, maxRotateSpeed;
    protected CollisionComponent collisionComponent;
    protected RandomScaleComponent randomScaleComponent;
    protected MeteorComponent meteorComponent;
    private Polygon polygon;
    protected Color color, unfreezeTransitionColor;
    private boolean isBerserk;
    protected float berserkScale;
    private Interpolation unfreezeInterpolation;

    private float freezeTime;
    protected boolean frozen;

    private float[] verts;

    protected Debris() {
        super();

        color = Color.BLACK;

        width = 2f;
        height = 2f;

        maxRotateSpeed = 250f;
        rotateBy = MathUtils.random(-maxRotateSpeed, maxRotateSpeed);

        randomScaleComponent = new RandomScaleComponent(this);
        addComponent(randomScaleComponent);

        collisionComponent = new CollisionComponent(this, CollisionComponent.Channel.DEBRIS, new CollisionComponent.Channel[0], new CollisionListener() {
            @Override
            public void collided(CollisionComponent collidedComponent) {

            }
        });
        addComponent(collisionComponent);

        meteorComponent = new MeteorComponent(this, 1, 10);
        addComponent(meteorComponent);

        polygon = new Polygon();

        unfreezeTransitionColor = new Color();

        berserkScale = 2.5f;

        verts = new float[8];
        updatePolygon();

        unfreezeInterpolation = Interpolation.linear;

        setRenderIndex(1);
    }

    @Override
    public void update() {
        super.update();

        if (frozen) {
            if (freezeTime <= 0) {
                randomScaleComponent.setEnabled(true);
                frozen = false;
            }

            freezeTime -= Gdx.graphics.getDeltaTime();

            meteorComponent.enabled = false;
        }

        if (!frozen) {
            setRotation(getRotation() + rotateBy * berserkScale * Gdx.graphics.getDeltaTime());

            meteorComponent.enabled = true;

            updatePolygon();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch, Matrix4 matrix4) {
        super.render(spriteBatch, matrix4);

        UtilityManager.getManager().shapeRenderer.setProjectionMatrix(matrix4);
        UtilityManager.getManager().shapeRenderer.begin();
        if (isBerserk) {
            UtilityManager.getManager().shapeRenderer.setColor(ColorManager.getManager().berserkDebris);
        } else if (freezeTime > 0) {
            if (freezeTime <= 1) {
                float alpha = unfreezeInterpolation.apply(1 - freezeTime);

                unfreezeTransitionColor.set(ColorManager.getManager().freeze.r, ColorManager.getManager().freeze.g, ColorManager.getManager().freeze.b, ColorManager.getManager().freeze.a);
                UtilityManager.getManager().shapeRenderer.setColor(unfreezeTransitionColor.lerp(color, alpha));
            } else {
                UtilityManager.getManager().shapeRenderer.setColor(ColorManager.getManager().freeze);
            }
        } else {
            UtilityManager.getManager().shapeRenderer.setColor(color);
        }
        UtilityManager.getManager().shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        UtilityManager.getManager().shapeRenderer.rect(position.x - width / 2, position.y - height / 2, width / 2, height / 2, width, height, getScale().x, getScale().y, getRotation());
        UtilityManager.getManager().shapeRenderer.end();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    private void updatePolygon() {
        polygon.setVertices(UtilityManager.getManager().rectangleToVerts(getPosition().x, getPosition().y, width, height, verts));
        polygon.setScale(getScale().x, getScale().y);
        polygon.setOrigin(getPosition().x, getPosition().y);
        polygon.setRotation(getRotation());
        collisionComponent.setCollisionVerts(polygon.getTransformedVertices());
    }

    public void goBerserk() {
        randomScaleComponent.scaleScale(berserkScale);
        isBerserk = true;
    }

    public void stopBerserk() {
        randomScaleComponent.scaleScale(1);
        isBerserk = false;
    }

    public void freeze(float freezeTime) {
        this.freezeTime = freezeTime;
        this.frozen = true;
        randomScaleComponent.setEnabled(false);
        stopBerserk();
    }
}
