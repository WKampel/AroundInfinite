package com.provision.aroundinfinite.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Timer;
import com.provision.aroundinfinite.managers.GlobalsManager;
import com.provision.aroundinfinite.misc.ShakeOrthoCamera;

public class CenterCube extends Debris {

    private ShakeOrthoCamera camera;
    private Timer.Task task1, task2, task3;

    public CenterCube(ShakeOrthoCamera camera) {
        super();

        width = 5f;
        height = 5f;

        berserkScale = 2.5f;

        task1 = new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                randomScaleComponent.setEnabled(true);

                scheduleBerserkBegin();

            }
        }, GlobalsManager.getManager().DEBRIS_START_DELAY);

        randomScaleComponent.setEnabled(false);
        setScaleX(0);
        setScaleY(0);

        meteorComponent.xVelocity = 0;
        meteorComponent.yVelocity = 0;
        position.set(0, 0);

        maxRotateSpeed = 25f;
        rotateBy = MathUtils.random(20f, maxRotateSpeed);

        if (MathUtils.random(1) == 0) {
            rotateBy = -rotateBy;
        }

        this.camera = camera;

        scheduleBerserkBegin();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch, Matrix4 matrix4) {
        super.render(spriteBatch, matrix4);
    }

    private void scheduleBerserkBegin() {
        if (task2 != null) {
            task2.cancel();
        }
        if (task3 != null) {
            task3.cancel();
        }
        task2 = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                goBerserk();
                scheduleBerserkStop();
            }
        }, MathUtils.random(20, 45));
    }

    private void scheduleBerserkStop() {
        if (task2 != null) {
            task2.cancel();
        }
        if (task3 != null) {
            task3.cancel();
        }
        task3 = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                stopBerserk();

                scheduleBerserkBegin();
            }
        }, MathUtils.random(7, 20));
    }

    @Override
    public void goBerserk() {
        if(!frozen) {
            super.goBerserk();

            camera.setShakeScale(4f);
        }
    }

    @Override
    public void stopBerserk() {
        super.stopBerserk();

        camera.setShakeScale(1f);
    }

    @Override
    public void destroy() {
        stopBerserk();

        task1.cancel();
        if (task2 != null) {
            task2.cancel();
        }
        if (task3 != null) {
            task3.cancel();
        }
        super.destroy();
    }
}
