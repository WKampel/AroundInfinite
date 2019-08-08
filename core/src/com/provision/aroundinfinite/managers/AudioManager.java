package com.provision.aroundinfinite.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;

public class AudioManager extends Manager {

    private static AudioManager manager;

    private Music music;

    public static AudioManager getManager() {
        if (manager == null) {
            manager = new AudioManager();
        }
        return manager;
    }

    protected AudioManager() {
        super();
    }

    public void playRandomMusic() {

        int rand = MathUtils.random(4) + 1;

        FileHandle asset = null;

        switch (rand) {
            case 1:
                asset = Gdx.files.internal("audio/loop_1.mp3");
                break;
            case 2:
                asset = Gdx.files.internal("audio/loop_2.mp3");
                break;
            case 3:
                asset = Gdx.files.internal("audio/loop_3.mp3");
                break;
            case 4:
                asset = Gdx.files.internal("audio/loop_4.mp3");
                break;
            case 5:
                asset = Gdx.files.internal("audio/loop_5.mp3");
        }

        if(music != null){
            music.dispose();
        }

        music = Gdx.audio.newMusic(asset);
        music.setLooping(true);
        music.setVolume(.5f);

        music.play();
    }

    public void stopMusic() {
        if (music != null) {
            music.stop();
        }
    }

    public void playSound(AssetManager.Asset soundAsset) {
        playSound(soundAsset, 1f);
    }

    public void playSound(AssetManager.Asset soundAsset, float volume) {
        Sound sound = (Sound) AssetManager.getManager().getAsset(soundAsset);
        sound.play(volume);
    }

    @Override
    void dispose() {
        music.dispose();
        manager = null;
    }
}
