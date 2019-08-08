package com.provision.aroundinfinite.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.provision.aroundinfinite.gameObjects.CenterCube;
import com.provision.aroundinfinite.gameObjects.Debris;
import com.provision.aroundinfinite.gameObjects.FlyingBomb;
import com.provision.aroundinfinite.gameObjects.FlyingCube;
import com.provision.aroundinfinite.misc.ShakeOrthoCamera;

public class DebrisManager extends Manager{

    private Array<Debris> debris;

    //Bounds to destroy an enemy if left
    private float destroyMinX, destroyMaxX, destroyMinY, destroyMaxY;
    private static DebrisManager manager;
    private int maxDebris, currentMaxDebris;
    private Timer.Task task, playFreezeEndSoundTask;
    private ShakeOrthoCamera camera;

    public static DebrisManager getManager(){
        if(manager == null){
            manager = new DebrisManager();
        }
        return manager;
    }

    private DebrisManager() {
        super();

        debris = new Array<Debris>();

        float screenRadius = GlobalsManager.getManager().BG_CIRCLE_RADIUS;

        destroyMinX = -screenRadius * 1.5f;
        destroyMinY = -screenRadius * 1.5f;
        destroyMaxX = screenRadius * 1.5f;
        destroyMaxY = screenRadius * 1.5f;

        maxDebris = 20;
        currentMaxDebris = 1;
    }

    public void update(){

    }

    public void setCamera(ShakeOrthoCamera camera){
        this.camera= camera;
    }

    public void startSpawn() {

        stopSpawn();

        currentMaxDebris = 1;

        task = new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                shouldDestroy();
                spawnDebris();
                currentMaxDebris = (currentMaxDebris + 1) >= maxDebris ? maxDebris : currentMaxDebris + 1;
            }
        }, GlobalsManager.getManager().DEBRIS_START_DELAY, .5f);

        spawnCenter();
    }

    public void spawnCenter(){
        boolean isSpawned = false;
        for(Debris debris : debris){
            isSpawned = debris.getClass() == CenterCube.class;

            if(isSpawned){
                break;
            }
        }

        if(!isSpawned) {
            Debris debris = new CenterCube(camera);
            GameObjectManager.getManager().createGameObject(debris);
            this.debris.add(debris);
        }
    }

    public void stopSpawn() {
        if (task != null) {
            task.cancel();
        }
    }

    private void spawnDebris(){
        if(debris.size < currentMaxDebris){
            int num = MathUtils.random(10);

            Debris debris = null;

            if(num == 0){
                debris = new FlyingBomb();
            } else{
                debris = new FlyingCube();
            }

            GameObjectManager.getManager().createGameObject(debris);
            this.debris.add(debris);
        }
    }

    public void destroyAll(){
        if(debris != null) {
            while (debris.size > 0) {
                destroy(debris.get(0));
            }
        }

        if(playFreezeEndSoundTask != null) {
            playFreezeEndSoundTask.cancel();
        }
    }

    private void shouldDestroy(){
        for(Debris debris : debris){

            /* If object is out of bounds, destroy */
            if(debris.getPosition().x < destroyMinX || debris.getPosition().x > destroyMaxX || debris.getPosition().y < destroyMinY || debris.getPosition().y > destroyMaxY){
                destroy(debris);
            }
        }
    }

    public void destroy(Debris debris){
        this.debris.removeValue(debris, true);
        GameObjectManager.getManager().destroyObject(debris);
    }

    public void freezeDebris(float time){
        for(Debris debris : debris){
            debris.freeze(time);
        }

        if(playFreezeEndSoundTask != null){
            playFreezeEndSoundTask.cancel();
        }

        playFreezeEndSoundTask = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                AudioManager.getManager().playSound(AssetManager.Asset.FREEZE_END);
            }
        }, time - 2);
    }

    @Override
    public void dispose() {
        destroyAll();

        if(task != null) {
            task.cancel();
        }

        manager = null;
    }
}
