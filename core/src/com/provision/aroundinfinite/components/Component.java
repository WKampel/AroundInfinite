package com.provision.aroundinfinite.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.provision.aroundinfinite.gameObjects.GameObject;

public abstract class Component {

    public GameObject owner;

    protected Component(GameObject owner){
        this.owner = owner;
    }

    public abstract void update();
    public abstract void render(SpriteBatch batch, Matrix4 matrix4);
    public abstract void destroy();

}
