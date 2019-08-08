package com.provision.aroundinfinite.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class ScoreManager extends Manager{

    private static ScoreManager manager;
    private FileHandle[] files;

    public static ScoreManager getManager() {
        if (manager == null) {
            manager = new ScoreManager();
        }
        return manager;
    }

    private ScoreManager(){
        files = new FileHandle[3];
        files[0] = null;
        files[1] = Gdx.files.local("highScore" + 1 + ".txt");
        files[2] = Gdx.files.local("highScore" + 2 + ".txt");
    }

    public void writeHighScore(int playerID, int score){
        if(score > readHighScore(playerID)) {
            files[playerID].writeString(String.valueOf(score), false);
        }
    }

    public int readHighScore(int playerID){
        int highScore = 0;

        if(files[playerID].exists()) {
            try {
                highScore = Integer.parseInt(files[playerID].readString());
            } catch (NumberFormatException e) {

            }
        }
        return highScore;
    }

    @Override
    public void dispose() {
        manager = null;
        files = null;
    }
}
