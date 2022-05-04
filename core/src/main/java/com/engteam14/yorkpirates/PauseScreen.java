package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Builds and initializes the Pause Screen (Menu)
 */
public class PauseScreen extends ScreenAdapter {

    private final YorkPirates game;
    private final GameScreen screen;
    private final Stage pauseStage;

    public PauseScreen(YorkPirates game, GameScreen screen){
        this.game = game;
        this.screen = screen;

        // Generate skin
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
        skin.addRegions(atlas);

        // Generate stage and table
        pauseStage = new Stage(screen.getViewport());
        Gdx.input.setInputProcessor(pauseStage);
        Table table = new Table();
        table.setFillParent(true);
        table.setTouchable(Touchable.enabled);
        table.setBackground(skin.getDrawable("Selection"));
        if(YorkPirates.DEBUG_ON) table.setDebug(true);

        // Generate title texture
        Texture titleT = new Texture(Gdx.files.internal("paused.png"));
        Image title = new Image(titleT);
        title.setScaling(Scaling.fit);

        // Generate buttons
        // Resume Button
        TextButton resume = new TextButton("Resume", skin);
        resume.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameContinue();
            }
        });

        // Restart Button
        TextButton restart = new TextButton("End Game", skin);
        restart.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.gameEnd(false);

            }
        });

        //Save/Load button
        TextButton loadSave = new TextButton("Save/Load", skin);
        loadSave.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.gameSave();
                screen.setPaused(true);
            }
        });

        //Quit button
        TextButton quit = new TextButton("Quit", skin);
        quit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();

            }
        });

        // Add title to table
        table.row();
        table.add(title).expand();

        // Add buttons to table
        table.row();
        table.add(resume).expand();
        table.row();
        table.add(restart).expand();
        table.row();
        table.add(loadSave).expand();
        table.row();
        table.add(quit).expand();

        // Add table to the stage
        pauseStage.addActor(table);
    }

    /**
     * Is called once every frame. Runs update() and then renders the title screen.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        Gdx.input.setInputProcessor(pauseStage);
        update();
        ScreenUtils.clear(0.6f, 0.6f, 1.0f, 1.0f);
        screen.render(delta); // Draws the gameplay screen as a background
        pauseStage.draw(); // Draws the stage
    }

    /**
     * Is called once every frame. Used for calculations that take place before rendering.
     */
    private void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            gameContinue();
        }
    }

    /**
     * Generates a HUD object within the game that controls elements of the UI.
     */
    private void gameContinue() {
        screen.setPaused(false);
        game.setScreen(screen);
    }
}
