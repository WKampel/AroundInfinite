package com.provision.aroundinfinite.collectables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.provision.aroundinfinite.components.CollisionComponent;
import com.provision.aroundinfinite.components.CollisionListener;
import com.provision.aroundinfinite.components.MeteorComponent;
import com.provision.aroundinfinite.gameObjects.GameObject;
import com.provision.aroundinfinite.gameObjects.Player;
import com.provision.aroundinfinite.managers.UtilityManager;

public class Collectable extends GameObject {

    protected Sprite sprite;
    protected CollisionComponent collisionComponent;
    protected float width, height;
    private Polygon polygon;
    private float rotateBy;
    private float[] verts;

    public Collectable(CollisionComponent.Channel channel) {

        super();

        CollisionComponent.Channel[] channels = {CollisionComponent.Channel.PLAYER};
        collisionComponent = new CollisionComponent(this, channel, channels, new CollisionListener() {
            @Override
            public void collided(CollisionComponent collidedComponent) {
                if (collidedComponent.channel == CollisionComponent.Channel.PLAYER) {
                    Player player = (Player) collidedComponent.owner;

                    if (player != null) {
                        if(player.hasTakenFirstJump) {
                            onPlayerCollision(player);
                        }
                    }
                }
            }
        });
        addComponent(collisionComponent);

        float maxRotateSpeed = 250f;
        rotateBy = MathUtils.random(-maxRotateSpeed, maxRotateSpeed);

        width = 1.22f;
        height = 1.22f;

        polygon = new Polygon();

        MeteorComponent meteorComponent = new MeteorComponent(this, 1, 4);
        addComponent(meteorComponent);

        setRenderIndex(-1);

        verts = new float[8];
    }

    @Override
    public void update() {
        super.update();

        polygon.setVertices(UtilityManager.getManager().rectangleToVerts(position.x + width / 2, position.y + height / 2, width, height, verts));
        collisionComponent.setCollisionVerts(polygon.getTransformedVertices());

        setRotation(getRotation() + rotateBy * Gdx.graphics.getDeltaTime());

        sprite.setPosition(position.x, position.y);
        sprite.setRotation(getRotation());
        sprite.setScale(getScale().x, getScale().y);
        sprite.setOriginCenter();
    }

    @Override
    public void render(SpriteBatch spriteBatch, Matrix4 matrix4) {
        super.render(spriteBatch, matrix4);

        UtilityManager.getManager().spriteBatch.begin();
        UtilityManager.getManager().spriteBatch.setProjectionMatrix(matrix4);
        sprite.draw(UtilityManager.getManager().spriteBatch);
        UtilityManager.getManager().spriteBatch.end();
    }

    protected void onPlayerCollision(Player player) {

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
