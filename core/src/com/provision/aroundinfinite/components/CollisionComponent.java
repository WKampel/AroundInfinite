package com.provision.aroundinfinite.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.provision.aroundinfinite.gameObjects.GameObject;
import com.provision.aroundinfinite.managers.UtilityManager;

public class CollisionComponent extends Component {

    public enum Channel{
        PLAYER, DEBRIS, BG_CIRCLE, FREEZE, SHIELD, POINT
    }

    //Collision channel
    public Channel channel;
    private Channel[] intersectChannels;
    private Polygon shape;

    private CollisionListener collisionListener;

    private static ObjectMap<Channel, Array<CollisionComponent>> collisionComponents;

    public CollisionComponent(GameObject owner, Channel channel, Channel[] intersectChannels, CollisionListener collisionListener) {
        super(owner);
        this.channel = channel;
        this.intersectChannels = intersectChannels;

        if(collisionComponents == null){
            collisionComponents = new ObjectMap<Channel, Array<CollisionComponent>>();
        }

        if(!collisionComponents.containsKey(channel)){
            collisionComponents.put(channel, new Array<CollisionComponent>());
        }
        collisionComponents.get(channel).add(this);

        shape = new Polygon();

        this.collisionListener = collisionListener;
    }

    @Override
    public void update() {
        for(Channel channel : intersectChannels) {
            if(collisionComponents.containsKey(channel)) {
                for (CollisionComponent collisionComponent : collisionComponents.get(channel)) {
                    if(UtilityManager.getManager().polygonLength(collisionComponent.getShape().getTransformedVertices()) > 0) {

                        if (channel == Channel.BG_CIRCLE && this.channel == Channel.PLAYER) {
                        }

                        if (Intersector.intersectPolygons(shape, collisionComponent.shape, null)) {
                            collisionListener.collided(collisionComponent);
                        } else if (Intersector.intersectPolygons(collisionComponent.shape, shape, null)) {
                            collisionListener.collided(collisionComponent);
                        }
                    }
                }
            }
        }
    }

    public static void drawCollision(Matrix4 matrix4){
        if(collisionComponents != null) {
            UtilityManager.getManager().shapeRenderer.begin();
            UtilityManager.getManager().shapeRenderer.setProjectionMatrix(matrix4);

            for (Channel channel : Channel.values()) {
                switch (channel) {
                    case BG_CIRCLE:
                        UtilityManager.getManager().shapeRenderer.setColor(Color.RED);
                        break;
                    case DEBRIS:
                        UtilityManager.getManager().shapeRenderer.setColor(Color.GREEN);
                        break;
                    case PLAYER:
                        UtilityManager.getManager().shapeRenderer.setColor(Color.BLUE);
                }
                if (collisionComponents.containsKey(channel)) {
                    for (CollisionComponent collisionComponent : collisionComponents.get(channel)) {
                        UtilityManager.getManager().shapeRenderer.polygon(collisionComponent.getShape().getTransformedVertices());
                    }
                }
            }
            UtilityManager.getManager().shapeRenderer.end();
        }

    }

    //Non scaled verts (pass in scaling to setScale)
    public void setCollisionVerts(float[] vertices){
        this.shape.setVertices(vertices);
    }

    public Polygon getShape(){
        return shape;
    }

    @Override
    public void render(SpriteBatch spriteBatch, Matrix4 matrix4) {

    }

    @Override
    public void destroy() {
        collisionComponents.get(channel).removeValue(this, true);
    }
}
