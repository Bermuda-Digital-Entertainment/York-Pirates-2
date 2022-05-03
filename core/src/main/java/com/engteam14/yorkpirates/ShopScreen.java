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


public class ShopScreen extends ScreenAdapter {

    private final YorkPirates game;
    private final GameScreen screen;
    private final Stage shopStage;

    public ShopScreen(YorkPirates game, GameScreen screen){
        this.game = game;
        this.screen = screen;
        


        //Generate Title 
        String imageN;
        imageN="shop_title.png";
        Texture titleT = new Texture(Gdx.files.internal(imageN));
        Image title = new Image(titleT);
        // Generate skin
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
        skin.addRegions(atlas);

        // Generate stage and table
        shopStage = new Stage(screen.getViewport());
        Table table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(shopStage);
        table.setBackground(skin.getDrawable("Selection"));
        if(YorkPirates.DEBUG_ON) table.setDebug(true);

        TextButton damageButton = new TextButton("Damage", skin);
        damageButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                damageUpgrade();
            }
        });

        TextButton healthButton = new TextButton("Health", skin);
        healthButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                healthUpgrade();
            }
        });

        // Add title to table
        table.row();
        table.add(title).expand();

        // Add buttons to table
        table.row();
        table.add(healthButton).expand();
        table.row();
        table.add(damageButton).expand();

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
}