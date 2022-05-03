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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;


public class ShopScreen extends ScreenAdapter {

    private final YorkPirates game;
    private final GameScreen screen;
    private final Stage shopStage;

    // Player counters
    private final Label score;
    private final Label loot;
    private final Label cost;
    private final Label cost1;

    public ShopScreen(YorkPirates game, GameScreen screen){
        this.game = game;
        this.screen = screen;
        


        //Generate Title 
        String imageN;
        imageN="shop1.png";
        Texture titleT = new Texture(Gdx.files.internal(imageN));
        Image title = new Image(titleT);
        // Generate skin
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
        skin.addRegions(atlas);


        // Create score related actors
        Image coin = new Image(new Texture(Gdx.files.internal("loot.png")));
        Image star = new Image(new Texture(Gdx.files.internal("points.png")));
        coin.setScaling(Scaling.fit);
        star.setScaling(Scaling.fit);
        loot = new Label(screen.loot.GetString(), skin);
        score = new Label(screen.points.GetString(), skin);
        loot.setFontScale(1.2f);
        score.setFontScale(1.2f);
        cost = new Label("-50                         ", skin);
        cost1 = new Label("-50     ", skin);
        Image coin1 = new Image(new Texture(Gdx.files.internal("loot.png")));
        Image coin2 = new Image(new Texture(Gdx.files.internal("loot.png")));
        coin1.setScaling(Scaling.fit);
        coin2.setScaling(Scaling.fit);

        // Create player tracker
        Table tracker = new Table();
        tracker.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("transparent.png"))));
        if(YorkPirates.DEBUG_ON) tracker.debug();

        // Add score to player tracker
        Table scores = new Table();
        //scores.add(star).padRight(20);
        //scores.add(score).padRight(20);
        scores.add(coin).padRight(20);
        scores.add(loot).padRight(20);
        if(YorkPirates.DEBUG_ON) scores.setDebug(true);
        tracker.add(scores);


        // Generate stage and table
        shopStage = new Stage(screen.getViewport());
        Table table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(shopStage);
        table.setBackground(skin.getDrawable("Selection"));
        if(YorkPirates.DEBUG_ON) table.setDebug(true);

        TextButton damageButton = new TextButton("+50 Damage (£50)", skin);
        damageButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                damageUpgrade();
            }
        });

        TextButton healthButton = new TextButton("+50 Health (£50)", skin);
        healthButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                healthUpgrade();
            }
        });

        TextButton speedButton = new TextButton("+5 Speed (£75)", skin);
        speedButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                speedUpgrade();
            }
        });
        
        TextButton continueButton = new TextButton("  >  ", skin);
        continueButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.setPaused(false);
                game.setScreen(screen);
            }
        });
        // Add title to table
        table.row();
        table.add(title).expand();
        // add score tracker
        table.row();
        table.add(tracker);

        // Add buttons to table
        table.row();
        table.add(healthButton).center();
        table.row();
        table.add(damageButton).center();
        table.row();
        table.add(speedButton).center().expand();
        table.row();
        table.add(continueButton).expand();





        // Add table to the stage
        shopStage.addActor(table);

    }

        /**
     * Is called once every frame. Runs update() and then renders the title screen.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        Gdx.input.setInputProcessor(shopStage);
        // Update the score and loot

        score.setText(screen.points.GetString());
        loot.setText(screen.loot.GetString());
        update();
        ScreenUtils.clear(0.6f, 0.6f, 1.0f, 1.0f);
        screen.render(delta); // Draws the gameplay screen as a background
        shopStage.draw(); // Draws the stage

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

    public void healthUpgrade(){
        if(screen.loot.Get() >= 50){
            screen.loot.Subtract(50);
            screen.getPlayer().addHealth();
        }
        else if(screen.loot.Get()<50){
            assert true;
        }
    }
    public void damageUpgrade(){
        if(screen.loot.Get()>= 50){
            screen.loot.Subtract(50);
            screen.getPlayer().addDamage();
        }
        else if(screen.loot.Get()<50){
            assert true;
        }
    }

    public void speedUpgrade(){
        if(screen.loot.Get()>= 75){
            screen.loot.Subtract(75);
            screen.getPlayer().speedUp(5);
        }
        else if(screen.loot.Get()<50){
            assert true;
        }
    }
}