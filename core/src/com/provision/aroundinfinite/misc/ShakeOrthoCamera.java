package com.provision.aroundinfinite.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;

import java.util.EventListener;

public class ShakeOrthoCamera extends OrthographicCamera {

    private float xRange, yRange, fromX, fromY, targetX, targetY, lifeTime, elapsed, scale;
    private Interpolation interpolation;
    private boolean shaking;

    public ShakeOrthoCamera(int WIDTH, int HEIGHT) {
        super(WIDTH, HEIGHT);

        interpolation = Interpolation.smooth;
        scale = 1;
    }

    public void update(float delta) {
        elapsed += delta;

        if(shaking){
            float progress = Math.min(1f, elapsed/lifeTime * scale);  // 0 -> 1
            float alpha = interpolation.apply(progress);

            position.x = MathUtils.lerp(fromX, targetX, alpha);
            position.y = MathUtils.lerp(fromY, targetY, alpha);

            if(alpha == 1){
                elapsed = 0;

                fromX = position.x;
                fromY = position.y;
                createRandoms();
            }
        }

        super.update();
    }

    public void beginShake(float xRange, float yRange, float lifeTime){
        shaking = true;
        this.xRange = xRange;
        this.yRange = yRange;
        this.lifeTime = lifeTime;
        this.fromX = position.x;
        this.fromY = position.y;

        createRandoms();
    }

    public void setShakeScale(float scale){
        this.scale = scale;
    }

    public void endShake(){
        shaking = false;
    }

    private void createRandoms(){
        targetX = MathUtils.random(-xRange * scale, xRange * scale);
        targetY = MathUtils.random(-yRange * scale, yRange * scale);
    }
}
