package com.provision.aroundinfinite.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.provision.aroundinfinite.gameObjects.Player;

public class PlayerManager extends Manager{

    private static PlayerManager manager;
    public int playerCount;
    public Array<Player> players;

    public static PlayerManager getManager() {
        if (manager == null) {
            manager = new PlayerManager();
        }
        return manager;
    }

    private PlayerManager() {
        playerCount = 0;

        players = new Array<Player>();
    }

    public Player addPlayer(InputMultiplexer inputMultiplexer) {
        playerCount++;

        Player player = new Player(playerCount, inputMultiplexer);
        players.add(player);
        GameObjectManager.getManager().createGameObject(player);

        return player;
    }

    public void removePlayer(Player player) {
        playerCount--;
        players.removeValue(player, true);
        GameObjectManager.getManager().destroyObject(player);

        if (playerCount <= 0) {

        }
    }

    public void spacePlayersEvenly() {
        int i = 0;
        for (Player player : players) {
            player.setRotation(i++ * 360 / playerCount);
        }
    }

    public void determinePlayersInputBounds() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight() / playerCount;
        for (Player player : players) {
            player.setInputBounds(0, height * (player.playerID - 1), width, height);
        }
    }

    public void render(SpriteBatch spriteBatch, Matrix4 matrix4) {
        for (Player player : players) {
            player.render(spriteBatch, matrix4);
        }
    }

    @Override
    public void dispose() {
        for(Player player : players){
            removePlayer(player);
        }
        manager = null;
    }
}
