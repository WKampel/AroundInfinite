package com.provision.aroundinfinite.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class UtilityManager extends Manager{
    private static UtilityManager manager;

    public ShapeRenderer shapeRenderer;
    public SpriteBatch spriteBatch, hudSpriteBatch;

    public static UtilityManager getManager(){
        if(manager == null){
            manager = new UtilityManager();
        }
        return manager;
    }

    private UtilityManager(){
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        hudSpriteBatch = new SpriteBatch();
    }

    public Vector2 RotatePoint(Vector2 pointToRotate, float angleInDegrees)
    {
        float angleInRadians = angleInDegrees * (float) (Math.PI / 180);
        float cosTheta = (float) Math.cos(angleInRadians);
        float sinTheta = (float) Math.sin(angleInRadians);
        return new Vector2((cosTheta * (pointToRotate.x - 0) -
                sinTheta * (pointToRotate.y - 0) + 0), (sinTheta * (pointToRotate.x - 0 +
                cosTheta * (pointToRotate.y - 0) + 0)));
    }

    //X and Y should be center of rectangle. Takes verts array which allows for array re-use
    public float[] rectangleToVerts(float x, float y, float width, float height, float[] verts){
        verts[0] = x - width / 2;
        verts[6] = verts[0];

        verts[1] = y + height / 2;
        verts[3] = verts[1];

        verts[2] = x + width / 2;
        verts[4] = verts[2];

        verts[5] = y - height / 2;
        verts[7] = verts[5];

        return verts;
    }

    //Length of verts / 2 is the ammount of points
    public float[] circleToVerts(float x, float y, float radius, float[] verts){
        if(verts.length >= 2) { /* At least 1 point */
            for (int i = 0; i < verts.length; i++) {
                float rot = 360 / verts.length * i;
                float _x = radius * MathUtils.cos(MathUtils.degreesToRadians * rot) + x;
                float _y = radius * MathUtils.sin(MathUtils.degreesToRadians * rot) + y;
                verts[i] = _x;
                verts[++i] = _y;
            }
        }

        return verts;
    }

    public float polygonLength(float[] vertices){
        float previousX = -1, previousY = -1;
        float length = 0;

        for(int i = 0; i < vertices.length; i++){

            if(previousX != -1 || previousY != -1){
                length += distance(previousX, previousY, vertices[i], vertices[i + 1]);
            }

            previousX = vertices[i];
            previousY = vertices[i++];
        }

        return length;
    }

    float distance(float x1, float y1, float x2, float y2){
        return (float) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public Vector2 randomPointInCircle(float radius){
        float[] coor = new float[2];

        float a = MathUtils.random(1f) * 2 * MathUtils.PI;
        float r = (float) (radius * Math.sqrt(MathUtils.random(1f)));

        return new Vector2(r * MathUtils.cos(a), r * MathUtils.sin(a));
    }

    @Override
    public void dispose() {
        this.spriteBatch.dispose();
        this.shapeRenderer.dispose();

        manager = null;
    }
}
