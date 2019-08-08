package com.provision.aroundinfinite.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.provision.aroundinfinite.managers.ColorManager;
import com.provision.aroundinfinite.managers.ScoreManager;

public class UIStage extends Stage {

    private Table table1, table2;
    private HorizontalGroup horizontalGroup1, horizontalGroup2;

    private Label player1HighScore, player2HighScore, player1CurrentScore, player2CurrentScore;

    public UIStage(Viewport viewport) {
        super(viewport);

        //Font generator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/simple.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.magFilter = Texture.TextureFilter.Linear; // used for resizing quality
        parameter.minFilter = Texture.TextureFilter.Linear;

        //Font
        BitmapFont font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(.075f);
        font.setUseIntegerPositions(false);
        generator.dispose();

        //Bottom table
        table1 = new Table();
        table1.align(Align.center | Align.bottom);
        table1.setFillParent(false);
        table1.padBottom(5);

        //Top table
        table2 = new Table();
        table2.align(Align.center | Align.bottom);
        table2.setFillParent(false);
        table2.padBottom(5);

        //rotate
        table2.setTransform(true);

        addActor(table1);
        addActor(table2);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        player1HighScore = new Label("", labelStyle);
        player2HighScore = new Label("", labelStyle);
        player1CurrentScore = new Label("0 ", labelStyle);
        player2CurrentScore = new Label("0 ", labelStyle);

        player1HighScore.setColor(ColorManager.getManager().backgroundCircle);
        player2HighScore.setColor(ColorManager.getManager().backgroundCircle);

        player1CurrentScore.setColor(ColorManager.getManager().point);
        player2CurrentScore.setColor(ColorManager.getManager().point);

        horizontalGroup1 = new HorizontalGroup();
        horizontalGroup2 = new HorizontalGroup();

        table1.add(horizontalGroup1);
        table2.add(horizontalGroup2);

        horizontalGroup1.addActor(player1CurrentScore);
        horizontalGroup1.addActor(player1HighScore);

        horizontalGroup2.addActor(player2CurrentScore);
        horizontalGroup2.addActor(player2HighScore);

        position();

    }

    public void setCurrentScore(int playerID, int score) {
        switch (playerID) {
            case 1:
                player1CurrentScore.setText(Integer.toString(score) + " ");
                break;
            case 2:
                player2CurrentScore.setText(Integer.toString(score) + " ");
                break;
        }
    }

    public void loadHighScores() {
        player1HighScore.setText(Integer.toString(ScoreManager.getManager().readHighScore(1)));
        player2HighScore.setText(Integer.toString(ScoreManager.getManager().readHighScore(2)));
    }

    public void position(){
        table1.setSize(this.getViewport().getWorldWidth(), this.getViewport().getWorldHeight() / 2);
        table2.setSize(this.getViewport().getWorldWidth(), this.getViewport().getWorldHeight() / 2);

        table1.setPosition(-this.getViewport().getWorldWidth() / 2, -this.getViewport().getWorldHeight() / 2);
        table2.setPosition(-this.getViewport().getWorldWidth() / 2, 0);

        table2.setOrigin(table2.getWidth() / 2, table2.getHeight() / 2);
        table2.setRotation(180);
    }


}
