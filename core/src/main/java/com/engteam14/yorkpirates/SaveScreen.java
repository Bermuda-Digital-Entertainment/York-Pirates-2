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


public class SaveScreen extends ScreenAdapter {

    private final YorkPirates game;
    private final GameScreen screen;
    private final Stage saveStage;
    private TextField textBox;


    /**
     * Initializes the Save Menu Screen 
     * @param game      Game the screen is for
     * @param screen    Screen to get data from
     */
    public SaveScreen(YorkPirates game, GameScreen screen){
        this.game = game;
        this.screen = screen;



        //Generate Title
        String imageN;
        imageN="paused.png";
        Texture titleT = new Texture(Gdx.files.internal(imageN));
        Image title = new Image(titleT);
        // Generate skin
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
        skin.addRegions(atlas);

        // Generate stage and table
        saveStage = new Stage(screen.getViewport());
        Table table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(saveStage);
        table.setBackground(skin.getDrawable("Selection"));
        if(YorkPirates.DEBUG_ON) table.setDebug(true);

        // Generate Buttons
        TextButton saveButton = new TextButton("Save Game", skin);
        saveButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("SAVE");
                String directory = getDir();
                SaveLoad save = new SaveLoad(directory);
                //Save all values
                save.saveObject(screen.getPlayer());
                save.saveColleges(screen.colleges);
                save.saveBoats(screen.boats);
                save.saveScreen(screen);
                save.saveSave();
            }
        });

        //Load button
        TextButton loadButton = new TextButton("Load Game", skin);
        loadButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
              String directory = getDir();
              SaveLoad save = new SaveLoad(directory);
              // Load commands
              save.loadSave();
              save.resumeSave(screen);
            }
        });

        //Directory text box
        textBox = new TextField("Directory", skin, "edges");
        textBox.setAlignment(Align.center);
        textBox.setOnlyFontChars(true);
        textBox.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                textBox.setText("");
            }});

        // Add title to table
        table.row();
        table.add(title).expand();

        // Add buttons to table
        table.row();
        table.add(textBox).expand();
        table.row();
        table.add(loadButton).expand();
        table.row();
        table.add(saveButton).expand();

        // Add table to the stage
        saveStage.addActor(table);

    }

        /**
     * Is called once every frame. Runs update() and then renders the screen.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        Gdx.input.setInputProcessor(saveStage);
        update();
        ScreenUtils.clear(0.6f, 0.6f, 1.0f, 1.0f);
        screen.render(delta); // Draws the gameplay screen as a background
        saveStage.draw(); // Draws the stage
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
     * Gets the directory specified by user to save/load the game to/from
     * @return  Directory path.
     */
    private String getDir(){
        String Dir;
        if ( textBox.getText().equals("Directory") || textBox.getText().equals("")) {
            Dir = "Directory";

        } else{
            Dir = textBox.getText();
        }
        return Dir;
    }
    /**
     * Unpauses the game
     */
    private void gameContinue() {
        screen.setPaused(false);
        game.setScreen(screen);
    }

}
