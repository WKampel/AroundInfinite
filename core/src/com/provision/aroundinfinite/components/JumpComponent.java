package com.provision.aroundinfinite.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.provision.aroundinfinite.gameObjects.GameObject;

public class JumpComponent extends Component {

    private boolean jumping, canJump;
    private float jumpTapVelocity, tapTime, maxTapTime, initialJumpVelocity;

    public JumpComponent(GameObject owner) {
        super(owner);

        initialJumpVelocity = -22f;
        jumpTapVelocity = -1.5f;
        tapTime = 0;
        maxTapTime = .25f;

        jumping = false;
        setCanJump(true);
    }

    @Override
    public void update() {
        if (canJump) {
            if (jumping && 1==2) {
                owner.gravityComponent.setVelocityY(owner.gravityComponent.getVelocityY() + jumpTapVelocity);
                tapTime += Gdx.graphics.getDeltaTime();

                if (tapTime >= maxTapTime) {
                    jumping = false;
                    tapTime = 0;
                }
            }
        }
    }

    public void pressed() {
        if(canJump) {
            jumping = true;
            float vel = owner.gravityComponent.getVelocityY() < 0 ? owner.gravityComponent.getVelocityY() + initialJumpVelocity : initialJumpVelocity;
            owner.gravityComponent.setVelocityY(vel);
        }

    }

    public void released() {
        jumping = false;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;

        if (!canJump) {
            this.jumping = false;
            this.tapTime = 0;
        }
    }

    public float getJumpInitialVelocity(){
        return initialJumpVelocity;
    }

    @Override
    public void render(SpriteBatch spriteBatch, Matrix4 matrix4) {

    }

    @Override
    public void destroy() {

    }
}
