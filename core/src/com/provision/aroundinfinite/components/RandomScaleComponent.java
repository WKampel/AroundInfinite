package com.provision.aroundinfinite.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.provision.aroundinfinite.gameObjects.GameObject;

public class RandomScaleComponent extends Component{

    private Interpolation interpolation;
    private boolean scaling;
    private boolean enabled;
    private float fromScaleX, fromScaleY, toScaleX, toScaleY, minScale, maxScale, lifeTime, elapsed, minLifeTime, maxLifeTime, scaleScale;

    public RandomScaleComponent(GameObject owner) {
        super(owner);

        minScale = .5f;
        maxScale = 1.5f;
        minLifeTime = 1f;
        maxLifeTime = 2f;

        lifeTime = 0f;
        elapsed = 0f;

        scaleScale = 1f;

        interpolation = Interpolation.bounce;

        owner.setScaleX(0);
        owner.setScaleY(0);

        this.enabled = true;
    }

    public void setMinMaxScale(float minScale, float maxScale){
        this.minScale = minScale;
        this.maxScale = maxScale;
    }

    @Override
    public void update() {

        if(enabled) {
            elapsed += Gdx.graphics.getDeltaTime();

            scale();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch, Matrix4 matrix4) {

    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    private void scale(){

        if(!scaling){
            scaling = true;
            fromScaleX = owner.getScale().x;
            fromScaleY = owner.getScale().y;
            toScaleX = MathUtils.random(minScale * scaleScale, maxScale * scaleScale);
            toScaleY = MathUtils.random(minScale * scaleScale, maxScale * scaleScale);
            lifeTime = MathUtils.random(minLifeTime, maxLifeTime);
        }

        float progress = Math.min(1f, elapsed/lifeTime);  // 0 -> 1
        float alpha = interpolation.apply(progress);

        owner.setScaleX(MathUtils.lerp(fromScaleX, toScaleX, alpha));
        owner.setScaleY(MathUtils.lerp(fromScaleY, toScaleY, alpha));

        if(alpha == 1){
            scaling = false;
            elapsed = 0;
        }
    }

    public void scaleScale(float scale){
        //Scale the scale
        this.scaleScale = scale;
    }

    @Override
    public void destroy() {

    }
}
