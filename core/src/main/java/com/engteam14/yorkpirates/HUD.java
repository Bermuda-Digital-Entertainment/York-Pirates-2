package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class HUD {

    
    // Stage
    public Stage stage;
    public OrthographicCamera camera;
    public FitViewport hudViewport;
    // Tutorial
    private final Table tutorial;
    private final Cell<Image> tutorialImg;
    private final Label tutorialLabel;
    private boolean tutorialComplete = false;
    private boolean canEndGame = false;

    // Player counters
    private final Label score;
    private final Label loot;

    // Player tasks
    private final Label tasksTitle;
    private final CheckBox collegesTask;
    private final CheckBox movementTask;
    private final CheckBox pointsTask;

    private int DISTANCE_GOAL = 1;
    private int POINT_GOAL = 1;

    private int DISTANCE_REWARD = 1;
    private int POINT_REWARD = 1;
    private boolean diffChange = false;
    /**
     * Generates a HUD object within the game that controls elements of the UI.
     * @param screen    The game screen which this is attached to.
     */
    public HUD(GameScreen screen){
        // Generate task goals and rewards based on difficulty 


        // Generate skin
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("Skin/YorkPiratesSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/YorkPiratesSkin.atlas")));
        skin.addRegions(atlas);

        // Generate stage and table
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(hudViewport);
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.setTouchable(Touchable.enabled);
        if(YorkPirates.DEBUG_ON) table.setDebug(true);

        // Create menu button
        ImageButton menuButton = new ImageButton(skin, "Menu");

        menuButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                screen.gamePause();
            }
        });
        // Create shop button
        Texture shopTexture = new Texture(Gdx.files.internal("shop_icon.png"));
        Texture shopPressed = new Texture(Gdx.files.internal("shop_icon.png"));
        ImageButton shopButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(shopTexture)),
            new TextureRegionDrawable(new TextureRegion(shopPressed))
        );
        shopButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                screen.gameShop();
                screen.setPaused(true);
            }
        });
        // Create tutorial actors
        Image tutorialImg = new Image(screen.getMain().keyboard.getKeyFrame(0f));
        tutorialImg.setScaling(Scaling.fit);
        tutorialLabel = new Label("WASD or Arrow Keys\n to Move.", skin);

        // Create score related actors
        Image coin = new Image(new Texture(Gdx.files.internal("loot.png")));
        Image star = new Image(new Texture(Gdx.files.internal("points.png")));
        coin.setScaling(Scaling.fit);
        star.setScaling(Scaling.fit);
        loot = new Label(screen.loot.GetString(), skin);
        score = new Label(screen.points.GetString(), skin);
        loot.setFontScale(1.2f);
        score.setFontScale(1.2f);


        // Create task related actors
        tasksTitle = new Label(screen.getPlayerName() + "'s Tasks:", skin);
        tasksTitle.setFontScale(0.5f, 0.5f);
        collegesTask = new CheckBox("", skin);
        movementTask = new CheckBox("", skin);
        pointsTask = new CheckBox("", skin);
        collegesTask.setChecked(true);
        movementTask.setChecked(true);
        pointsTask.setChecked(true);
        collegesTask.setDisabled(true);
        movementTask.setDisabled(true);
        pointsTask.setDisabled(true);

        // Create player tracker
        Table tracker = new Table();
        tracker.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("transparent.png"))));
        if(YorkPirates.DEBUG_ON) tracker.debug();

        // Add score to player tracker
        Table scores = new Table();
        scores.add(star).padRight(20);
        scores.add(score).padRight(20);
        scores.add(coin).padRight(20);
        scores.add(loot).padRight(20);
        if(YorkPirates.DEBUG_ON) scores.setDebug(true);
        tracker.add(scores);

        // Add tasks to player tracker
        tracker.row();
        tracker.add(tasksTitle).pad(1);
        tracker.row();
        tracker.add(collegesTask).left().pad(5);
        tracker.row();
        tracker.add(movementTask).left().pad(5);
        tracker.row();
        tracker.add(pointsTask).left().pad(5);

        // Create tutorial placeholder
        tutorial = new Table();
        tutorial.setBackground(tracker.getBackground());
        this.tutorialImg = tutorial.add(tutorialImg).expand().fill().minSize(200f).maxSize(500f);
        tutorial.row();
        tutorial.add(tutorialLabel);
        if(YorkPirates.DEBUG_ON) tutorial.setDebug(true);

        // Start main table

        // Add menu button to table
        table.row();
        table.add(menuButton).size(150).left().top().pad(25);
        // Add shop button to table
        table.row();
        table.add(shopButton).size(150).left().top().pad(25);

        // Add tutorial to table
        table.row();
        table.add(tutorial.pad(100f));

        // Add tracker to table
        table.add().expand();
        table.add(tracker);

        // Add table to the stage
        stage.addActor(table);
    }

    /**
     * Called to render the HUD elements
     * @param screen    The game screen which this is attached to.
     */
    public void renderStage(GameScreen screen){
        Gdx.input.setInputProcessor(stage);
        stage.draw();

        // Update the score and loot

        score.setText(screen.points.GetString());
        loot.setText(screen.loot.GetString());

        // difficulty dependent update
        // Sets tasks and task rewards depending on the difficulty level of current game
        if(screen.getDifficulty()==0){
            assert true;
        }
        else if(screen.getDifficulty()!=0 && diffChange==false){
            if(screen.getDifficulty()==1){
                DISTANCE_GOAL = MathUtils.random(40,50)*10;
                POINT_GOAL = MathUtils.random(15,20)*10;
                DISTANCE_REWARD = 25;
                POINT_REWARD = 25;
                diffChange = true;
            }
            else if(screen.getDifficulty()==2){
                DISTANCE_GOAL = MathUtils.random(60,70)*10;
                POINT_GOAL = MathUtils.random(25,37)*10;
                DISTANCE_REWARD = 35;
                POINT_REWARD = 35;
                diffChange = true;
            }
            else{
                DISTANCE_GOAL = MathUtils.random(80,100)*10;
                POINT_GOAL = MathUtils.random(60,69)*10;
                DISTANCE_REWARD = 45;
                POINT_REWARD = 45;
                diffChange = true;
            }
        }else{assert true;}

        // Calculate which part of the tutorial to show
        if(screen.getPlayer().getDistance() < 2){
            // Movement tutorial
            Image newimg = new Image(screen.getMain().keyboard.getKeyFrame(screen.getElapsedTime(), true));
            newimg.setScaling(Scaling.fit);
            tutorialImg.setActor(newimg);
            tutorialComplete = false;
        } else if(!tutorialComplete){
            // Shooting tutorial
            Image newimg = new Image(screen.getMain().mouse.getKeyFrame(screen.getElapsedTime(), true));
            newimg.setScaling(Scaling.fit);
            tutorialImg.setActor(newimg);
            tutorialLabel.setText("Click to shoot.");
        } else if(canEndGame) {
            // Able to end the game
            tutorial.setVisible(true);
            Image newimg = new Image(screen.getMain().enter.getKeyFrame(screen.getElapsedTime(), true));
            newimg.setScaling(Scaling.fit);
            tutorialImg.setActor(newimg);
            tutorialLabel.setText("Press Enter to end game.");
            canEndGame = false;
        } else {
            // Tutorial complete
            tutorial.setVisible(false);
        }

        // Decide on and then display main player goal
        if(College.capturedCount >= screen.colleges.size-1){
            collegesTask.setText("Return home to win.");
        } else {
            collegesTask.setText("Capture all colleges:  "+Math.min(College.capturedCount, screen.colleges.size-1)+"/"+(screen.colleges.size-1)+"  ");
        }

        // Distance related task calculations
        if(screen.getPlayer().getDistance() > DISTANCE_GOAL && movementTask.isChecked()) {
             screen.loot.Add(DISTANCE_REWARD);
             screen.points.Add(DISTANCE_REWARD); 
            }
        movementTask.setChecked(screen.getPlayer().getDistance() < DISTANCE_GOAL);
        movementTask.setText("Move "+DISTANCE_GOAL+"m:  "+Math.min((int)(screen.getPlayer().getDistance()), DISTANCE_GOAL)+"/"+DISTANCE_GOAL+"  ");

        // Points related task calculations
        if(screen.points.Get() > POINT_GOAL && pointsTask.isChecked()) { 
            screen.loot.Add(POINT_REWARD);
            screen.points.Add(POINT_REWARD);
         }
        pointsTask.setChecked(screen.points.Get() < POINT_GOAL);
        pointsTask.setText("Get "+POINT_GOAL+" points:  "+Math.min(screen.points.Get(), POINT_GOAL)+"/"+POINT_GOAL+"  ");
    }

    /**
     * Updates the player name on the screen
     * @param screen    the current game screen
     */
    public void updateName(GameScreen screen) { tasksTitle.setText(screen.getPlayerName() +"'s Tasks:"); }

    /**
     * Makes the tutorial table disappear
     */
    public void endTutorial() { tutorialComplete = true; }

    /**
     * Allows the game to be ended/won
     */
    public void setGameEndable() {canEndGame = true; }
}
