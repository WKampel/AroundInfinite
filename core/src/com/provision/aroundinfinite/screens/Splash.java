package com.provision.aroundinfinite.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.provision.aroundinfinite.Game;
import com.provision.aroundinfinite.managers.AssetManager;
import com.provision.aroundinfinite.managers.ColorManager;
import com.provision.aroundinfinite.managers.UtilityManager;

public class Splash implements CustomScreen {

    private float elapsedTime;
    private Game game;
    private Texture texture;

    public Splash(Game game) {
        this.game = game;
        texture = new Texture(Gdx.files.internal("sprites/splash.png"));
        AssetManager.getManager().loadAssets();
    }

    @Override
    public void show() {

    }

    @Override
    public void update() {

        elapsedTime += Gdx.graphics.getDeltaTime();

        if (AssetManager.getManager().isFinishedLoading()) {
            if (elapsedTime >= 5) {
                this.game.setScreen(new Play(game));
            }
        } else {
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(ColorManager.getManager().space.r, ColorManager.getManager().space.g, ColorManager.getManager().space.b, ColorManager.getManager().space.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        UtilityManager.getManager().spriteBatch.begin();
        UtilityManager.getManager().spriteBatch.draw(texture, 0, Gdx.graphics.getHeight() / 2 - Gdx.graphics.getWidth() / 2, Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
        UtilityManager.getManager().spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
