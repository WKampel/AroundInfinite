package com.provision.aroundinfinite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.provision.aroundinfinite.managers.AssetManager;
import com.provision.aroundinfinite.managers.CollectableManager;
import com.provision.aroundinfinite.managers.ColorManager;
import com.provision.aroundinfinite.managers.DebrisManager;
import com.provision.aroundinfinite.managers.GameObjectManager;
import com.provision.aroundinfinite.managers.GlobalsManager;
import com.provision.aroundinfinite.managers.PlayerBodyManager;
import com.provision.aroundinfinite.managers.PlayerManager;
import com.provision.aroundinfinite.managers.ScoreManager;
import com.provision.aroundinfinite.managers.UtilityManager;
import com.provision.aroundinfinite.misc.ShakeOrthoCamera;
import com.provision.aroundinfinite.screens.CustomScreen;
import com.provision.aroundinfinite.screens.Splash;

public class Game extends com.badlogic.gdx.Game {

	public ShakeOrthoCamera camera;
	public ExtendViewport viewport;

	private CustomScreen customScreen;

	public InputMultiplexer inputMultiplexer;

	static {
		GdxNativesLoader.load();
	}

	public Game(){
		camera = new ShakeOrthoCamera(GlobalsManager.getManager().WORLD_WIDTH, GlobalsManager.getManager().WORLD_HEIGHT);
		viewport = new ExtendViewport(GlobalsManager.getManager().WORLD_WIDTH, GlobalsManager.getManager().WORLD_HEIGHT, camera);

		camera.beginShake(.3f, .6f, 1f);

		inputMultiplexer = new InputMultiplexer();

	}
	
	@Override
	public void create () {
		setScreen(new Splash(this));

		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	private void update(){
		customScreen.update();
		GameObjectManager.getManager().updateGameObjects();

		camera.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void render () {

		update();

		super.render();

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		viewport.update(width, height);
	}

	@Override
	public void dispose () {
		super.dispose();

		AssetManager.getManager().dispose();
		UtilityManager.getManager().dispose();
		CollectableManager.getManager().dispose();
		ColorManager.getManager().dispose();
		DebrisManager.getManager().dispose();
		GameObjectManager.getManager().dispose();
		GlobalsManager.getManager().dispose();
		PlayerManager.getManager().dispose();
		ScoreManager.getManager().dispose();
		PlayerBodyManager.getManager().dispose();

		inputMultiplexer.clear();
		inputMultiplexer = null;
	}

	@Override
	public void setScreen(Screen screen) {

		if(getScreen() != null){
			getScreen().dispose();
		}

		super.setScreen(screen);

		customScreen = (CustomScreen) screen;
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
