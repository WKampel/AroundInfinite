package com.provision.aroundinfinite.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.provision.aroundinfinite.components.CollisionComponent;
import com.provision.aroundinfinite.components.CollisionListener;
import com.provision.aroundinfinite.components.JumpComponent;
import com.provision.aroundinfinite.components.ShieldComponent;
import com.provision.aroundinfinite.managers.AudioManager;
import com.provision.aroundinfinite.managers.GlobalsManager;
import com.provision.aroundinfinite.managers.PlayerBodyManager;
import com.provision.aroundinfinite.managers.AssetManager;
import com.provision.aroundinfinite.managers.ColorManager;
import com.provision.aroundinfinite.managers.DebrisManager;
import com.provision.aroundinfinite.managers.PlayerManager;
import com.provision.aroundinfinite.managers.ScoreManager;
import com.provision.aroundinfinite.managers.UtilityManager;

public class Player extends GameObject implements InputProcessor {

    private float playerWidth;
    public int playerID;

    private Rectangle touchInputBounds;

    private JumpComponent jumpComponent;

    private Color color;
    public int points;
    private int previousPoints;

    private Polygon polygon;

    private CollisionComponent collisionComponent;

    private InputMultiplexer inputMultiplexer;

    public boolean hasTakenFirstJump;

    private float distanceTraveled;
    private float previousRotation;

    private Vector2 vector2;

    private float[] verts;

    public Player(int playerID, InputMultiplexer inputMultiplexer) {
        super();

        vector2 = new Vector2();

        distanceTraveled = 0;

        this.inputMultiplexer = inputMultiplexer;
        this.inputMultiplexer.addProcessor(this);

        this.playerID = playerID;

        gravityComponent.setGravityX(-30f);
        gravityComponent.setGravityY(120f);
        gravityComponent.setMaxVelocityY(75f);

        jumpComponent = new JumpComponent(this);

        playerWidth = 1;

        setY(10);

        touchInputBounds = new Rectangle();

        points = 0;
        previousPoints = points;

        gravityComponent.gravityEnabled = true;

        CollisionComponent.Channel[] collisionChannels = {CollisionComponent.Channel.DEBRIS, CollisionComponent.Channel.BG_CIRCLE};
        collisionComponent = new CollisionComponent(this, CollisionComponent.Channel.PLAYER, collisionChannels, new CollisionListener() {
            @Override
            public void collided(CollisionComponent collidedComponent) {

                switch (collidedComponent.channel) {
                    case DEBRIS:
                        if (hasComponentOfType(ShieldComponent.class)) {
                            removeComponent(getComponentOfType(ShieldComponent.class));

                            boolean respawnCenter = collidedComponent.owner.getClass() == CenterCube.class;
                            DebrisManager.getManager().destroy((Debris) collidedComponent.owner);

                            if (respawnCenter) {
                                DebrisManager.getManager().spawnCenter();
                            }

                            AudioManager.getManager().playSound(AssetManager.Asset.SHIELD_BREAK);

                        } else {
                            kill();
                        }
                        break;
                }
            }
        });

        addComponent(collisionComponent);

        polygon = new Polygon();

        verts = new float[8];

    }

    @Override
    public void update() {

        distanceTraveled += Math.abs(previousRotation - getRotation());
        previousRotation = getRotation();
        points = (int) distanceTraveled / 360;

        if (previousPoints != points) {
            previousPoints = points;
            AudioManager.getManager().playSound(AssetManager.Asset.COIN);
        }

        super.update();

        jumpComponent.update();

        if (position.y > GlobalsManager.getManager().BG_CIRCLE_RADIUS - playerWidth / 2) {
            position.y = GlobalsManager.getManager().BG_CIRCLE_RADIUS - playerWidth / 2;

            if (gravityComponent.getVelocityY() < 0) {
                gravityComponent.setVelocityY(0);
            }
        }

        if (!hasTakenFirstJump) {
            gravityComponent.gravityEnabled = false;
        } else {
            gravityComponent.gravityEnabled = true;
        }


        polygon.setVertices(UtilityManager.getManager().rectangleToVerts(getPosition().x, getPosition().y, playerWidth, playerWidth, verts));
        polygon.setOrigin(0, 0);
        polygon.setRotation(getRotation());
        collisionComponent.setCollisionVerts(polygon.getTransformedVertices());

    }

    @Override
    public void render(SpriteBatch spriteBatch, Matrix4 matrix4) {
        super.render(spriteBatch, matrix4);

        if (hasComponentOfType(ShieldComponent.class)) {
            UtilityManager.getManager().shapeRenderer.setColor(ColorManager.getManager().shield);
        } else {
            if (hasTakenFirstJump) {
                UtilityManager.getManager().shapeRenderer.setColor(ColorManager.getManager().player);
            } else {
                UtilityManager.getManager().shapeRenderer.setColor(ColorManager.getManager().playerInactive);
            }
        }

        UtilityManager.getManager().shapeRenderer.begin();
        UtilityManager.getManager().shapeRenderer.setProjectionMatrix(matrix4);
        UtilityManager.getManager().shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        UtilityManager.getManager().shapeRenderer.circle(polygon.getBoundingRectangle().getCenter(vector2).x, polygon.getBoundingRectangle().getCenter(vector2).y, playerWidth / 2, 15);
        UtilityManager.getManager().shapeRenderer.end();
    }

    public void setInputBounds(int x, int y, int width, int height) {
        touchInputBounds.set(x, y, width, height);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        screenY = Gdx.graphics.getHeight() - screenY;
        if (touchInputBounds.contains(screenX, screenY)) {
            jumpComponent.pressed();
            AudioManager.getManager().playSound(AssetManager.Asset.TAP_SOUND);
            hasTakenFirstJump = true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY;
        if (touchInputBounds.contains(screenX, screenY)) {
            jumpComponent.released();
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private void kill() {
        gravityComponent.setGravityX(0);
        gravityComponent.setGravityY(0);
        gravityComponent.setVelocityY(0);
        gravityComponent.gravityEnabled = false;

        AudioManager.getManager().playSound(AssetManager.Asset.DEATH);

        PlayerBodyManager.getManager().addBody(polygon.getBoundingRectangle().getCenter(vector2).x, polygon.getBoundingRectangle().getCenter(vector2).y);

        PlayerManager.getManager().removePlayer(this);
    }

    public void addPoint() {
        this.points++;
    }

    @Override
    public void destroy() {
        super.destroy();

        ScoreManager.getManager().writeHighScore(playerID, points);
        inputMultiplexer.removeProcessor(this);
    }
}
