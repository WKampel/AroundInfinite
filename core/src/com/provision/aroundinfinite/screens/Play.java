package com.provision.aroundinfinite.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.provision.aroundinfinite.Game;
import com.provision.aroundinfinite.managers.AssetManager;
import com.provision.aroundinfinite.managers.AudioManager;
import com.provision.aroundinfinite.managers.PlayerBodyManager;
import com.provision.aroundinfinite.gameObjects.BackgroundCircle;
import com.provision.aroundinfinite.gameObjects.Player;
import com.provision.aroundinfinite.managers.CollectableManager;
import com.provision.aroundinfinite.managers.ColorManager;
import com.provision.aroundinfinite.managers.DebrisManager;
import com.provision.aroundinfinite.managers.GameObjectManager;
import com.provision.aroundinfinite.managers.GlobalsManager;
import com.provision.aroundinfinite.managers.PlayerManager;
import com.provision.aroundinfinite.managers.UtilityManager;
import com.provision.aroundinfinite.ui.UIStage;

public class Play implements CustomScreen {

    private Game game;
    private UIStage stage;

    public enum PlayState {
        READYING_UP, PLAYING
    }

    private PlayState playState;

    public Play(Game game) {
        this.game = game;

        this.stage = new UIStage(game.viewport);

        readyUp(2);
    }

    @Override
    public void show() {
        AssetManager.getManager().loadAssets();

        while(!AssetManager.getManager().isFinishedLoading()){

        }
    }

    @Override
    public void update() {

        stage.act(Gdx.graphics.getDeltaTime());

        if(playState == PlayState.PLAYING){

            boolean endGame = true;
            for(Player player : PlayerManager.getManager().players){
                if(player.hasTakenFirstJump){
                    stage.setCurrentScore(player.playerID, player.points);
                    endGame = false;
                }
            }

            if(endGame){
                readyUp(2);
            }
        } else if(playState == PlayState.READYING_UP){
            boolean ready = false;

            for(Player player : PlayerManager.getManager().players){
                if(player.hasTakenFirstJump){
                    ready = true;
                    break;
                }
            }

            if(ready){
               playGame();
            }
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(ColorManager.getManager().space.r, ColorManager.getManager().space.g, ColorManager.getManager().space.b, ColorManager.getManager().space.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glClearDepthf(1f);
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glDepthFunc(GL20.GL_LESS);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

        Gdx.gl.glDepthMask(true);
        Gdx.gl.glColorMask(false, false, false, false);

        //Mask shape (can add more)
        UtilityManager.getManager().shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        UtilityManager.getManager().shapeRenderer.circle(0, 0, GlobalsManager.getManager().BG_CIRCLE_RADIUS, 100);
        UtilityManager.getManager().shapeRenderer.end();

        Gdx.gl.glColorMask(true, true, true, true);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL20.GL_EQUAL);

        //Render everything below here that you WANT to be masked

        //Background circle
        UtilityManager.getManager().shapeRenderer.setAutoShapeType(true);
        UtilityManager.getManager().shapeRenderer.setProjectionMatrix(game.camera.combined);
        UtilityManager.getManager().shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        UtilityManager.getManager().shapeRenderer.setColor(ColorManager.getManager().backgroundCircle);
        UtilityManager.getManager().shapeRenderer.rect(-GlobalsManager.getManager().WORLD_WIDTH / 2, -GlobalsManager.getManager().WORLD_HEIGHT / 2, GlobalsManager.getManager().WORLD_WIDTH, GlobalsManager.getManager().WORLD_HEIGHT);
        UtilityManager.getManager().shapeRenderer.end();

        PlayerBodyManager.getManager().render(game.camera.combined);

        PlayerManager.getManager().render(UtilityManager.getManager().spriteBatch, game.camera.combined);

        GameObjectManager.getManager().renderGameObjects(game.camera.combined);

        Gdx.gl.glDepthFunc(GL20.GL_ALWAYS);
        //Render everything below here that you do NOT want to be masked

        stage.draw();

        //CollisionComponent.drawCollision(game.camera.combined);

        Gdx.gl.glDepthFunc(GL20.GL_EQUAL);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
        stage.position();
    }

    @Override
    public void pause() {
        UtilityManager.getManager().dispose();
        AssetManager.getManager().dispose();
    }

    @Override
    public void resume() {
        AssetManager.getManager().loadAssets();

        while(!AssetManager.getManager().isFinishedLoading()){

        }
    }

    @Override
    public void hide() {
        UtilityManager.getManager().dispose();
        AssetManager.getManager().dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void playGame() {

        this.playState = PlayState.PLAYING;

        CollectableManager.getManager().startSpawn();

        DebrisManager.getManager().setCamera(game.camera);
        DebrisManager.getManager().startSpawn();

        GameObjectManager.getManager().createGameObject(new BackgroundCircle());

        AudioManager.getManager().playRandomMusic();
    }

    public void readyUp(int playerCount) {

        PlayerManager.getManager().dispose();
        GameObjectManager.getManager().destroyAll();

        //Create players
        for (int i = 1; i <= playerCount; i++) {
            PlayerManager.getManager().addPlayer(game.inputMultiplexer);
        }

        PlayerManager.getManager().spacePlayersEvenly();
        PlayerManager.getManager().determinePlayersInputBounds();

        DebrisManager.getManager().destroyAll();
        DebrisManager.getManager().stopSpawn();

        this.playState = PlayState.READYING_UP;

        this.stage.loadHighScores();

        CollectableManager.getManager().stopSpawn();
        CollectableManager.getManager().destroyAll();

        AudioManager.getManager().stopMusic();
    }
}
