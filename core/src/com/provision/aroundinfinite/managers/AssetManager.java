package com.provision.aroundinfinite.managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class AssetManager extends Manager {
    private com.badlogic.gdx.assets.AssetManager blAssetManager;

    private static AssetManager manager;

    public enum Asset {
        CLICK, DEATH, FREEZE_END, FREEZE_START, SHIELD_BREAK, SHIELD_GAIN, TAP_SOUND, COIN, COUNTDOWN, TRIANGLE
    }

    public static AssetManager getManager() {
        if (manager == null) {
            manager = new AssetManager();
        }
        return manager;
    }

    private AssetManager() {
        blAssetManager = new com.badlogic.gdx.assets.AssetManager();
    }

    public void loadAssets() {

        //Textures
        blAssetManager.load("sprites/triangle.png", Texture.class);

        //Audio
        blAssetManager.load("audio/click.wav", Sound.class);
        blAssetManager.load("audio/death.wav", Sound.class);
        blAssetManager.load("audio/freezeEnd.wav", Sound.class);
        blAssetManager.load("audio/freezeStart.wav", Sound.class);
        blAssetManager.load("audio/shieldBreak.wav", Sound.class);
        blAssetManager.load("audio/shieldGain.wav", Sound.class);
        blAssetManager.load("audio/tap.wav", Sound.class);
        blAssetManager.load("audio/coin.wav", Sound.class);
        blAssetManager.load("audio/countdown.wav", Sound.class);
    }

    public boolean isFinishedLoading() {
        return blAssetManager.update();
    }

    @Override
    public void dispose() {
        blAssetManager.dispose();

        manager = null;
    }

    public Object getAsset(Asset assetName) {

        switch (assetName) {
            case TRIANGLE:
                return blAssetManager.get("sprites/triangle.png");
            case CLICK:
                return blAssetManager.get("audio/click.wav");
            case DEATH:
                return blAssetManager.get("audio/death.wav");
            case FREEZE_END:
                return blAssetManager.get("audio/freezeEnd.wav");
            case FREEZE_START:
                return blAssetManager.get("audio/freezeStart.wav");
            case SHIELD_BREAK:
                return blAssetManager.get("audio/shieldBreak.wav");
            case SHIELD_GAIN:
                return blAssetManager.get("audio/shieldGain.wav");
            case TAP_SOUND:
                return blAssetManager.get("audio/tap.wav");
            case COIN:
                return blAssetManager.get("audio/coin.wav");
            case COUNTDOWN:
                return blAssetManager.get("audio/countdown.wav");
        }

        return null;
    }
}
