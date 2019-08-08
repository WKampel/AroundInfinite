package com.provision.aroundinfinite.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.provision.aroundinfinite.gameObjects.GameObject;
import com.provision.aroundinfinite.managers.GlobalsManager;

public class MeteorComponent extends Component {

    public float xVelocity, yVelocity;
    public boolean enabled;

    public MeteorComponent(GameObject owner, float minVelocity, float maxVelocity) {
        super(owner);

        enabled = true;

        int rand = MathUtils.random(3);
        float screenRadius = GlobalsManager.getManager().BG_CIRCLE_RADIUS + 5;

        switch(rand) {
            case 0: /* Top spawn */
                owner.setX(MathUtils.random(-screenRadius, screenRadius));
                owner.setY(screenRadius);

                yVelocity = -MathUtils.random(minVelocity, maxVelocity);
                xVelocity = MathUtils.random(-maxVelocity, maxVelocity);
                break;
            case 1: /* Right spawn */
                owner.setY(MathUtils.random(-screenRadius, screenRadius));
                owner.setX(screenRadius);

                xVelocity = -MathUtils.random(minVelocity, maxVelocity);
                yVelocity = MathUtils.random(-maxVelocity, maxVelocity);
                break;
            case 2: /* Bottom spawn */
                owner.setX(MathUtils.random(-screenRadius, screenRadius));
                owner.setY(-screenRadius);

                yVelocity = MathUtils.random(minVelocity, maxVelocity);
                xVelocity = MathUtils.random(-maxVelocity, maxVelocity);
                break;
            case 3: /* Left spawn */
                owner.setY(MathUtils.random(-screenRadius, screenRadius));
                owner.setX(-screenRadius);

                xVelocity = MathUtils.random(minVelocity, maxVelocity);
                yVelocity = MathUtils.random(-maxVelocity, maxVelocity);
        }

    }

    @Override
    public void update() {
        if(enabled){
            owner.setX(owner.getPosition().x + xVelocity * Gdx.graphics.getDeltaTime());
            owner.setY(owner.getPosition().y + yVelocity * Gdx.graphics.getDeltaTime());
            }
    }

    @Override
    public void render(SpriteBatch batch, Matrix4 matrix4) {

    }

    @Override
    public void destroy() {

    }
}
