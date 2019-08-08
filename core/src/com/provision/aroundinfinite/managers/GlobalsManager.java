package com.provision.aroundinfinite.managers;

public class GlobalsManager extends Manager {

    private static GlobalsManager manager;

    public final int WORLD_WIDTH, WORLD_HEIGHT;
    public final float BG_CIRCLE_RADIUS;
    public final float DEBRIS_START_DELAY;

    public static GlobalsManager getManager(){
        if(manager == null){
            manager = new GlobalsManager();
        }
        return manager;
    }

    private GlobalsManager(){
        WORLD_WIDTH = 50;
        WORLD_HEIGHT = 50;
        BG_CIRCLE_RADIUS = WORLD_WIDTH / 2 - .5f;
        DEBRIS_START_DELAY = 2f;
    }

    @Override
    public void dispose() {
        manager = null;
    }
}
