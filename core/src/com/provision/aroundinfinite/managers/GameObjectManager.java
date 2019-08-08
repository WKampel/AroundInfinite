package com.provision.aroundinfinite.managers;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.provision.aroundinfinite.gameObjects.GameObject;

import java.util.Collections;
import java.util.Comparator;

public class GameObjectManager extends Manager{

    private static GameObjectManager manager;

    public Array<GameObject> gameObjects;

    private Comparator<GameObject> zIndexComparator;

    public static GameObjectManager getManager(){
        if(manager == null){
            manager = new GameObjectManager();
        }
        return manager;
    }

    private GameObjectManager(){
        gameObjects = new Array<GameObject>();


        zIndexComparator = new Comparator<GameObject>() {
            @Override
            public int compare(GameObject gameObject1, GameObject gameObject2) {
                return gameObject1.getRenderIndex() - gameObject2.getRenderIndex(); // Ascending
            }

        };
    }

    public void createGameObject(GameObject gameObject){
        gameObjects.add(gameObject);
    }

    public void updateGameObjects(){
        for(GameObject gameObject : gameObjects){
            gameObject.update();
        }
    }

    public void renderGameObjects(Matrix4 matrix4){

        gameObjects.sort(zIndexComparator);

        for(GameObject gameObject : gameObjects){
            gameObject.render(UtilityManager.getManager().spriteBatch, matrix4);
        }
    }

    public void destroyObject(GameObject object){
        object.destroy();
        gameObjects.removeValue(object, true);
    }

    public void destroyAll(){
        for(GameObject gameObject : gameObjects){
            destroyObject(gameObject);
        }
    }

    @Override
    public void dispose() {
        destroyAll();
        manager = null;
    }
}
