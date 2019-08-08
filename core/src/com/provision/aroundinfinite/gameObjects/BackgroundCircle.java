package com.provision.aroundinfinite.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.provision.aroundinfinite.components.CollisionComponent;
import com.provision.aroundinfinite.components.CollisionListener;
import com.provision.aroundinfinite.managers.GlobalsManager;
import com.provision.aroundinfinite.managers.UtilityManager;

public class BackgroundCircle extends GameObject {

    CollisionComponent collisionComponent;

    public BackgroundCircle() {
        super();

        CollisionComponent.Channel[] collisionChannels = {CollisionComponent.Channel.DEBRIS, CollisionComponent.Channel.PLAYER};
        collisionComponent = new CollisionComponent(this, CollisionComponent.Channel.BG_CIRCLE, collisionChannels, new CollisionListener() {
            @Override
            public void collided(CollisionComponent collidedComponent) {

            }
        });

        addComponent(collisionComponent);

        float[] verts = new float[60];
        verts = UtilityManager.getManager().circleToVerts(0, 0, GlobalsManager.getManager().BG_CIRCLE_RADIUS, verts);
        collisionComponent.setCollisionVerts(verts);
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
