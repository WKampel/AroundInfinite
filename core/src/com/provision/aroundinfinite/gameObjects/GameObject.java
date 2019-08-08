package com.provision.aroundinfinite.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.provision.aroundinfinite.components.Component;
import com.provision.aroundinfinite.components.GravityComponent;

public class GameObject {

    private Array<Component> components;
    protected Vector2 position;
    private Vector2 scale;
    private float rotation;
    public GravityComponent gravityComponent;
    private int zRenderIndex;

    protected GameObject(){
        components = new Array<Component>();
        position = new Vector2(0, 0);
        scale = new Vector2(1,1);
        rotation = 0;

        gravityComponent = new GravityComponent(this);
        addComponent(gravityComponent);

        zRenderIndex = 0;
    }

    public void update(){
        for(int i = 0; i < components.size; i++){
            components.get(i).update();
        }
    }

    public void render(SpriteBatch spriteBatch, Matrix4 matrix4){
        spriteBatch.setProjectionMatrix(matrix4);
        spriteBatch.begin();
        for(Component component : components){
            component.render(spriteBatch, matrix4);
        }
        spriteBatch.end();
    }

    public void setRenderIndex(int zRenderIndex){
        this.zRenderIndex = zRenderIndex;
    }

    public int getRenderIndex(){
        return this.zRenderIndex;
    }

    public Component addComponent(Component component){
        components.add(component);
        return component;
    }

    public void removeComponent(Component component){
        components.removeValue(component, true);
        component.destroy();
    }

    public Component getComponentOfType(Class<?> cls){
        for(Component component : components){
            if(component.getClass() == cls){
                return component;
            }
        }
        return null;
    }

    public Vector2 getPosition(){
        return position;
    }

    public void setY(float y){
        position.y = y;
    }

    public void setX(float x){
        position.x = x;
    }

    public void setScaleX(float x){
        scale.x = x;
    }

    public void setScaleY(float y){
        scale.y = y;
    }

    public Vector2 getScale(){
        return scale;
    }

    public void setRotation(float rotation){
        this.rotation = rotation;
    }

    public float getRotation(){
        return this.rotation;
    }

    /* Destroy should only be called by GameObjectManager as it needs to remove the object from it's arraylist in order for it to be collected by the garbage collector */
    public void destroy(){
        for(Component component : components){
            component.destroy();
        }
    }

    public boolean hasComponentOfType(Class<?> type){
        for(Component component : components){
            if(component.getClass() == type){
                return true;
            }
        }
        return false;
    }
}
