package com.provision.aroundinfinite.collectables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.provision.aroundinfinite.components.CollisionComponent;
import com.provision.aroundinfinite.gameObjects.Player;
import com.provision.aroundinfinite.managers.AssetManager;
import com.provision.aroundinfinite.managers.AudioManager;
import com.provision.aroundinfinite.managers.CollectableManager;
import com.provision.aroundinfinite.managers.ColorManager;
import com.provision.aroundinfinite.managers.DebrisManager;

public class Freeze extends Collectable {

    public Freeze() {
        super(CollisionComponent.Channel.FREEZE);

        sprite = new Sprite((Texture) AssetManager.getManager().getAsset(AssetManager.Asset.TRIANGLE));

        sprite.setSize(width, height);
        sprite.setColor(ColorManager.getManager().freeze);
        sprite.setPosition(position.x, position.y);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch, Matrix4 matrix4) {
        super.render(spriteBatch, matrix4);
    }

    @Override
    protected void onPlayerCollision(Player player) {
        super.onPlayerCollision(player);

        DebrisManager.getManager().freezeDebris(MathUtils.random(5, 10));

        CollectableManager.getManager().destroy(this);

        AudioManager.getManager().playSound(AssetManager.Asset.FREEZE_START);
    }
}
