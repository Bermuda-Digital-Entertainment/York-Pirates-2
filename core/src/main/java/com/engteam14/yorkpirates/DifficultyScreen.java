package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;


public class DifficultyScreen extends ScreenAdapter {

    private final YorkPirates game;
    private final GameScreen screen;
    private final Stage difficultyStage;
    private final Cell<Image> titleCell;
    private float elapsedTime = 0f;

    public DifficultyScreen(YorkPirates game, GameScreen screen){
        this.game = game;
        this.screen = screen;



        //Generate Title 
        TextureRegion titleT = game.logo.getKeyFrame(0f);
        Image title = new Image(titleT);
        title.setScaling(Scaling.fit);

        // Generate skin
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
        skin.addRegions(atlas);

        // Generate stage and table
        difficultyStage = new Stage(screen.getViewport());
        Table table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(difficultyStage);
        table.setBackground(skin.getDrawable("Selection"));
        if(YorkPirates.DEBUG_ON) table.setDebug(true);
        Label difficultyMessage = new Label("Choose difficulty", skin);

        TextButton easyButton = new TextButton("Easy", skin);
        easyButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.setDifficulty(1);
                screen.getPlayer().setDamage(200);
                screen.getPlayer().setHealth(200);
                screen.setPaused(false);
                game.setScreen(screen);
            }
        });

        TextButton mediumButton = new TextButton("Medium", skin);
        mediumButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.setDifficulty(2);
                screen.getPlayer().setDamage(100);
                screen.getPlayer().setHealth(100);
                screen.setPaused(false);
                game.setScreen(screen);
            }
        });

        TextButton hardButton = new TextButton("Hard", skin);
        hardButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.setDifficulty(3);
                screen.getPlayer().setDamage(50);
                screen.getPlayer().setHealth(50);
                screen.setPaused(false);
                game.setScreen(screen);
            }
        });

        // Add title to table
        table.row();
        titleCell = table.add(title).expand();
        table.row();
        table.add(difficultyMessage);

        // Add buttons to table
        table.row();
        table.add(easyButton).expand();
        table.row();
        table.add(mediumButton).expand();
        table.row();
        table.add(hardButton).expand();
        // Add table to the stage
        difficultyStage.addActor(table);

    }

        /**
     * Is called once every frame. Runs update() and then renders the title screen.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        elapsedTime += delta;
        Gdx.input.setInputProcessor(difficultyStage);
        TextureRegion frame = game.logo.getKeyFrame(elapsedTime, true);
        titleCell.setActor(new Image(frame));
        ScreenUtils.clear(0.6f, 0.6f, 1.0f, 1.0f);
        screen.render(delta); // Draws the gameplay screen as a background
        difficultyStage.draw(); // Draws the stage

    }

}