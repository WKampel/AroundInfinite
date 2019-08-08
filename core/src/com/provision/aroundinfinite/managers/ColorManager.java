package com.provision.aroundinfinite.managers;

import com.badlogic.gdx.graphics.Color;

public class ColorManager extends Manager{

    private static ColorManager manager;

    public Color backgroundCircle, space, flyingCube, berserkDebris, flyingBomb, centerCube, player, playerInactive, countdown, freeze, playerBody, shield, point;

    public static ColorManager getManager() {
        if (manager == null) {
            manager = new ColorManager();
        }
        return manager;
    }

    private ColorManager(){
        space = new Color(25f / 255f, 25f / 255f, 25f / 255f, 1);
        backgroundCircle = new Color(75f / 255f, 75f / 255f, 75f / 255f, 1);
        flyingBomb = new Color(175/255f, 175f/255f, 0f/255f, 1);
        player = new Color(200f / 255f, 200f / 255f, 200f / 255f, 1);
        playerInactive = new Color(120f / 255f, 120f / 255f, 120f / 255f, 1);

        playerBody = new Color(75/255f, 65/255f, 65/255f, 1);

        shield = new Color(50/255f, 200/255f, 100/255f, 1);

        point = new Color(200/255f, 50/255f, 200/255f, 1);
        point = Color.WHITE;

//        space = new Color(245f / 255f, 245f / 255f, 245f / 255f, 1);
//        backgroundCircle = new Color(230f / 255f, 230f / 255f, 230f / 255f, 1);
//        centerCube = Color.RED;
//        flyingCube = new Color(50f / 255f, 50f / 255f, 50f / 255f, 1);
//        flyingCube = new Color(200f/255f, 0f/255f, 0f/255f, 1);
//        player = new Color(0, 220f / 255f, 1, 1);

        berserkDebris = new Color(200/255f, 0f/255f, 0f/255f, 1);
        countdown = new Color(200/255f, 0f/255f, 0f/255f, 1);
        centerCube = Color.BLACK;
        flyingCube = Color.BLACK;

        freeze = new Color(50/255f, 200/255f, 220/255f, 1);
    }

    @Override
    public void dispose() {
        manager = null;
    }
}
