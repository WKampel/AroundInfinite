package com.provision.aroundinfinite.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.provision.aroundinfinite.gameObjects.GameObject;

public class GravityComponent extends Component {

    private float gravityX, gravityY, velocityY, maxVelocityY;
    public boolean gravityEnabled;

    public GravityComponent(GameObject owner) {
        super(owner);

        gravityX = 0;
        gravityY = 0;
        maxVelocityY = 75f;
        velocityY = 0;

        gravityEnabled = false;
    }

    @Override
    public void update() {

        if(gravityEnabled) {
            //Add gravity to velocity and clamp to ensure it doesn't surpass the max velocity
            velocityY += gravityY * Gdx.graphics.getDeltaTime();

            if(velocityY > maxVelocityY){
                velocityY = maxVelocityY;
            }

            if(velocityY < -maxVelocityY){
                velocityY = -maxVelocityY;
            }

            //Add the velocityY to the player's Y position
            owner.setY(Math.max(owner.getPosition().y - velocityY * Gdx.graphics.getDeltaTime(), 0));
            //Add the velocityX to the player's X position
            owner.setRotation(owner.getRotation() + gravityX * Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch, Matrix4 matrix4) {

    }

    public float getVelocityY() {
        return velocityY;
    }

    public float getGravityY() {
        return gravityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public void setGravityX(float gravityX) {
        this.gravityX = gravityX;
    }

    public void setGravityY(float gravityY) {
        this.gravityY = gravityY;
    }

    public void setMaxVelocityY(float maxVelocityY) {
        this.maxVelocityY = maxVelocityY;
    }

    @Override
    public void destroy() {

    }
}
