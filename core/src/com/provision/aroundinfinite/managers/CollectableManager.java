package com.provision.aroundinfinite.managers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.provision.aroundinfinite.collectables.Collectable;
import com.provision.aroundinfinite.collectables.Freeze;
import com.provision.aroundinfinite.collectables.Shield;

public class CollectableManager extends Manager{

    private Array<Collectable> collectables;

    private static CollectableManager manager;

    private Timer.Task task, checkDestroyTask;
    private float destroyMinX, destroyMaxX, destroyMinY, destroyMaxY;

    public static CollectableManager getManager() {
        if (manager == null) {
            manager = new CollectableManager();
        }
        return manager;
    }

    private CollectableManager() {
        collectables = new Array<Collectable>();
        scheduleSpawn();

        float screenRadius = GlobalsManager.getManager().BG_CIRCLE_RADIUS;

        destroyMinX = -screenRadius * 1.5f;
        destroyMinY = -screenRadius * 1.5f;
        destroyMaxX = screenRadius * 1.5f;
        destroyMaxY = screenRadius * 1.5f;

        checkDestroyTask = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                shouldDestroy();
            }
        }, 1, 1);
    }

    private void spawnCollectable() {
        int num = MathUtils.random(1);

        Collectable collectable = null;

        if (num == 0) {
            collectable = new Freeze();
        } else if (num == 1) {
            collectable = new Shield();
        }

        collectables.add(collectable);
        GameObjectManager.getManager().createGameObject(collectable);

        scheduleSpawn();
    }

    public void startSpawn() {
        scheduleSpawn();
    }

    public void stopSpawn() {
        if (task != null) {
            task.cancel();
        }
    }

    public void destroyAll() {
        while (collectables.size > 0) {
            destroy(collectables.get(0));
        }
        stopSpawn();
    }

    public void destroy(Collectable collectable) {
        this.collectables.removeValue(collectable, true);
        GameObjectManager.getManager().destroyObject(collectable);
    }

    private void shouldDestroy() {
        for (Collectable collectable : collectables) {
            /* If object is out of bounds, destroy */
            if (collectable.getPosition().x < destroyMinX || collectable.getPosition().x > destroyMaxX || collectable.getPosition().y < destroyMinY || collectable.getPosition().y > destroyMaxY) {
                destroy(collectable);
            }
        }
    }

    public void scheduleSpawn() {
        if (task != null) {
            task.cancel();
        }
        task = Timer.schedule(new Timer.Task() {

            @Override
            public void run() {
                spawnCollectable();
            }
        }, MathUtils.random(5, 11));
    }

    @Override
    public void dispose() {
        destroyAll();
        task.cancel();
        checkDestroyTask.cancel();
        manager = null;
    }
}
