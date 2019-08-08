package com.provision.aroundinfinite.managers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PlayerBodyManager extends Manager{

    private static PlayerBodyManager manager;

    private Array<Vector2> bodyPositions;

    public static PlayerBodyManager getManager() {
        if (manager == null) {
            manager = new PlayerBodyManager();
        }
        return manager;
    }

    private PlayerBodyManager(){
        bodyPositions = new Array<Vector2>();
    }

    public void render(Matrix4 matrix4){
        UtilityManager.getManager().shapeRenderer.begin();
        UtilityManager.getManager().shapeRenderer.setColor(ColorManager.getManager().playerBody);
        UtilityManager.getManager().shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        UtilityManager.getManager().shapeRenderer.setProjectionMatrix(matrix4);
        for(Vector2 position : bodyPositions){
            UtilityManager.getManager().shapeRenderer.circle(position.x, position.y, .5f, 15);
        }
        UtilityManager.getManager().shapeRenderer.end();

    }

    public void addBody(float x, float y){
        bodyPositions.add(new Vector2(x, y));
    }

    @Override
    public void dispose() {
        bodyPositions.clear();
        manager = null;
    }
}
