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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;


public class TipsScreen extends ScreenAdapter {

    private final YorkPirates game;
    private final GameScreen screen;
    private final Stage tipsStage;


    /**
     * Initializes the tips screen
     * @param game      game the screen is on
     * @param screen    screen to get data from
     */
    public TipsScreen(YorkPirates game, GameScreen screen){
        this.game = game;
        this.screen = screen;



        //Generate Title
        Texture tips = new Texture(Gdx.files.internal("tips.png"));
        Image tipsImage = new Image(tips);
        // Generate skin
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
        skin.addRegions(atlas);

        // Generate stage and table
        tipsStage = new Stage(screen.getViewport());
        Table table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(tipsStage);
        table.setBackground(skin.getDrawable("Selection"));
        if(YorkPirates.DEBUG_ON) table.setDebug(true);

        TextButton continueButton = new TextButton(" > ", skin);
        continueButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.setPaused(false);
                game.setScreen(screen);
            }
        });

        // Add title to table
        table.row();
        table.add(tipsImage).expand();

        // Add buttons to table
        table.row();
        table.add(continueButton).expand();


        // Add table to the stage
        tipsStage.addActor(table);

    }

        /**
     * Is called once every frame. Runs update() and then renders the screen.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        Gdx.input.setInputProcessor(tipsStage);
        update();
        ScreenUtils.clear(0.6f, 0.6f, 1.0f, 1.0f);
        screen.render(delta); // Draws the gameplay screen as a background
        tipsStage.draw(); // Draws the stage
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
     * Unpauses the game
     */
    private void gameContinue() {
        screen.setPaused(false);
        game.setScreen(screen);
    }

}
