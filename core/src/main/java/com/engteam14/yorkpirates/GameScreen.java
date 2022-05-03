package com.engteam14.yorkpirates;

import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.json.simple.JSONObject;

public class GameScreen extends ScreenAdapter {
    // Team name constants
    public static final String playerTeam = "PLAYER";
    public static final String enemyTeam = "ENEMY";
    // Difficulty Level
    public int difficulty = 0;

    // Score managers
    public ScoreManager points;
    public ScoreManager loot;

    // Colleges
    public Array<College> colleges;
    public Array<Boat> boats;
    public Array<Projectile> projectiles;

    // Power Ups
    public Array<PowerUp> powerUps;
    public Boolean immunity = false;
    private float immunityTimer = 0f;
    
    // Obstacles
    public Array<Obstacle> obstacles;

    // Sound
    public Music music;

    // Main classes
    private final YorkPirates game;

    // Player
    private final Player player;
    private String playerName;
    private Vector3 followPos;
    private boolean followPlayer = false;

    // Boats
    private Texture boatHealthBar;

    // UI & Camera
    private HUD gameHUD;
    private final SpriteBatch HUDBatch;
    private final OrthographicCamera HUDCam;
    private final FitViewport viewport;

    // Tilemap
    private final TiledMap tiledMap;
    private final OrthogonalTiledMapRenderer tiledMapRenderer;

    // Trackers
    private float elapsedTime = 0;
    private boolean isPaused = false;
    private float lastPause = 0;

    // Weather
    private Sprite fog;
    private int fogDecider = 0;
    private int fogLengthDecider = 0;
    private Boolean counterStarted = false;
    private long fogCounter;
    private long startTimeStamp;



    /**
     * Initialises the main game screen, as well as relevant entities and data.
     * @param game  Passes in the base game class for reference.
     */
    public GameScreen(YorkPirates game){
        this.game = game;
        playerName = "Player";

        // Initialise points and loot managers
        points = new ScoreManager();
        loot = new ScoreManager();

        // Initialise HUD
        HUDBatch = new SpriteBatch();
        HUDCam = new OrthographicCamera();
        HUDCam.setToOrtho(false, game.camera.viewportWidth, game.camera.viewportHeight);
        viewport = new FitViewport( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), HUDCam);
        gameHUD =  new HUD(this);

        //initialise sound
        music = Gdx.audio.newMusic(Gdx.files.internal("Pirate1_Theme1.ogg"));
        music.setLooping(true);
        music.setVolume(0);
        music.play();

        // Initialise sprites array to be used generating GameObjects
        Array<Texture> sprites = new Array<>();

        // Initialise player
        sprites.add(new Texture("ship1.png"), new Texture("ship2.png"), new Texture("ship3.png"));
        player = new Player(sprites, 2, 821, 489, 32, 16, playerTeam);
        sprites.clear();
        followPos = new Vector3(player.x, player.y, 0f);
        game.camera.position.lerp(new Vector3(760, 510, 0f), 1f);

        // Initialise Power Ups
        powerUps = new Array<>();
        Array<Texture> powerUpSprites = new Array<>();

        // Add damage boost
        powerUpSprites.add(new Texture("damageBoost.png"));
        powerUps.add(new PowerUp(powerUpSprites, 1, 1500, 700, 20 , 32, enemyTeam, "DamageBoost"));
        powerUpSprites.clear();
        // Add Max Health
        powerUpSprites.add(new Texture("maxHealthUp.png"));
        powerUps.add(new PowerUp(powerUpSprites, 1, 300, 1000, 20 , 32, enemyTeam, "MaxHealth"));
        powerUpSprites.clear();
        // Add Immunity
        powerUpSprites.add(new Texture("immunity.png"));
        powerUps.add(new PowerUp(powerUpSprites, 1, 900, 1500, 20 , 32, enemyTeam, "Immunity"));
        powerUpSprites.clear();
        // Add Heal
        powerUpSprites.add(new Texture("currentHealthUp.png"));
        powerUps.add(new PowerUp(powerUpSprites, 1, 1100, 2000, 20 , 32, enemyTeam, "Heal"));
        powerUpSprites.clear();
        // Add Speed
        powerUpSprites.add(new Texture("speed.png"));
        powerUps.add(new PowerUp(powerUpSprites, 1, 600, 750, 20 , 32, enemyTeam, "SpeedBoost"));
        powerUpSprites.clear();

        //Initialise Obstacles
        obstacles = new Array<>();
        Array<Texture> obstacleSprites = new Array<>();

        // Add hostile wrecks
        obstacleSprites.add(new Texture("hostile_wreck.png"));
        obstacles.add(new Obstacle(obstacleSprites, 1, 1300, 750, 32 , 50, enemyTeam, "HostileWreck"));
        obstacles.add(new Obstacle(obstacleSprites, 1, 700, 586, 32 , 50, enemyTeam, "HostileWreck"));
        obstacles.add(new Obstacle(obstacleSprites, 1, 643, 1700, 32 , 50, enemyTeam, "HostileWreck"));
        obstacles.add(new Obstacle(obstacleSprites, 1, 786, 1300, 32 , 50, enemyTeam, "HostileWreck"));
        obstacleSprites.clear();
        // Add Dead Waters
        obstacleSprites.add(new Texture("dead_water.png"));
        obstacles.add(new Obstacle(obstacleSprites, 1, 600, 600, 50 , 50, enemyTeam, "DeadWater"));
        obstacles.add(new Obstacle(obstacleSprites, 1, 900, 1354, 50 , 50, enemyTeam, "DeadWater"));
        obstacles.add(new Obstacle(obstacleSprites, 1, 765, 890, 50 , 50, enemyTeam, "DeadWater"));
        obstacles.add(new Obstacle(obstacleSprites, 1, 1534, 1923, 50 , 50, enemyTeam, "DeadWater"));
        obstacleSprites.clear();

        // Initialise tilemap
        tiledMap = new TmxMapLoader().load("FINAL_MAP.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        // Initialise colleges
        College.capturedCount = 0;
        colleges = new Array<>();
        College newCollege;
        Array<Texture> collegeSprites = new Array<>();

        //Initialise Boats
        boats = new Array<>();
        Array<Texture> boatSprites = new Array<>();
        boatHealthBar = new Texture(Gdx.files.internal("enemyHealthBar.png"));

        // Add alcuin
        collegeSprites.add( new Texture("alcuin.png"),
                            new Texture("alcuin_2.png"));
        newCollege = new College(collegeSprites, 1492, 665, 0.5f,"Alcuin", enemyTeam, player);
        colleges.add(newCollege);
        collegeSprites.clear();

        //Add Alcuin Boats
        boatSprites.add(new Texture("alcuin_boat.png"),
                        new Texture("shipwreck.png"));
        boats.add(new Boat(boatSprites, 1492f+80f, 665f-20f, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 1492f-80f, 665f+40f, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 1300, 700, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 1210, 650, 0.25f, enemyTeam, player));
        boatSprites.clear();

        // Add derwent
        collegeSprites.add( new Texture("derwent.png"),
                            new Texture("derwent_2.png"));
        newCollege = (new College(collegeSprites, 1815, 2105, 0.8f,"Derwent", enemyTeam, player));
        colleges.add(newCollege);
        collegeSprites.clear();

        // Add Derwent Boats
        boatSprites.add( new Texture("derwent_boat.png"),
                         new Texture("shipwreck.png"));
        boats.add(new Boat(boatSprites, 1815f-120f, 2105f+20f, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 1815f-80f, 2105f+40f, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 1815f-100f, 2105f+40f, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 1815f+800f, 2105f+40f, 0.25f, enemyTeam, player));
        boatSprites.clear();

        // Add langwith
        collegeSprites.add( new Texture("langwith.png"),
                            new Texture("langwith_2.png"));
        newCollege = (new College(collegeSprites, 1300, 1530, 1.0f,"Langwith", enemyTeam, player));
        colleges.add(newCollege);
        collegeSprites.clear();

        //Add Langwith Boats
        boatSprites.add(new Texture("langwith_boat.png"),
                        new Texture("shipwreck.png"));
        boats.add(new Boat(boatSprites, 1300f+120f, 1530f+20f, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 1300f+80f, 1530f+40f, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 1300f-120f, 1530f+20f, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 1300f-80f, 1530f+40f, 0.25f, enemyTeam, player));
        boatSprites.clear();

        //Add Constantine
        collegeSprites.add( new Texture("constantine.png"),
                            new Texture("constantine_2.png"));
        newCollege = (new College(collegeSprites, 500, 1580, 0.7f,"Constantine", enemyTeam, player));
        colleges.add(newCollege);
        collegeSprites.clear();

        //Add Constantine Boats
        boatSprites.add(new Texture("constantine_boat.png"),
                        new Texture("shipwreck.png"));
        boats.add(new Boat(boatSprites, 680, 1600, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 600, 1300, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 650, 1500, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 690, 1400, 0.25f, enemyTeam, player));
        boatSprites.clear();

        //Add James
        collegeSprites.add( new Texture("james.png"),
                            new Texture("james_2.png"));
        newCollege = (new College(collegeSprites, 700, 2180, 0.7f,"James", enemyTeam, player));
        colleges.add(newCollege);
        collegeSprites.clear();

        //Add James Boats
        boatSprites.add(new Texture("james_boat.png"),
                        new Texture("shipwreck.png"));
        boats.add(new Boat(boatSprites, 800, 2300, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 650, 1800, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 750, 2200, 0.25f, enemyTeam, player));
        boats.add(new Boat(boatSprites, 670, 1900, 0.25f, enemyTeam, player));
        boatSprites.clear();


        // Add goodricke
        collegeSprites.add( new Texture("goodricke.png"));
        colleges.add(new College(collegeSprites, 700, 525, 0.7f,"Home",playerTeam,player));


        // Initialise projectiles array to be used storing live projectiles
        projectiles = new Array<>();

        // Load the fog texture
        fog = new Sprite(new Texture(Gdx.files.internal("fog(1).png")));

        startTimeStamp = Instant.now().getEpochSecond();
    }


    /**
     * Is called once every frame. Runs update(), renders the game and then the HUD.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){

        // Only update if not paused
        if(!isPaused) {
            elapsedTime += delta;
            update();
        }
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(0.1f, 0.6f, 0.6f, 1.0f);

        // Gameplay drawing batch
        game.batch.begin();
        tiledMapRenderer.setView(game.camera); // Draw map first so behind everything
        tiledMapRenderer.render();

        // Draw Projectiles
        for(int i = 0; i < projectiles.size; i++) {
            projectiles.get(i).draw(game.batch, 0);
        }

        // Draw PowerUps
        for(int i = 0; i<powerUps.size; i++){
            if(powerUps.get(i).collides(game.batch, player)==false){
                powerUps.get(i).draw(game.batch, 0);
            }
            else{
                if(powerUps.get(i).power == "DamageBoost"){player.addDamage();}
                else if(powerUps.get(i).power == "MaxHealth"){player.addHealth();}
                else if(powerUps.get(i).power == "Heal"){player.heal(75);}
                else if(powerUps.get(i).power == "SpeedBoost"){player.speedUp(30);}
                else if(powerUps.get(i).power == "Immunity"){immunity = true;}
                powerUps.removeIndex(i);
            }
        }
        if(immunity == true && immunityTimer ==0){
            immunityTimer = Instant.now().getEpochSecond();
        }
        else if(immunity == true && Instant.now().getEpochSecond()-immunityTimer >25){
            immunity = false;
        }

        else{assert true;}


        // Draw Obstacles
        for(int i = 0; i<obstacles.size; i++){
            if(obstacles.get(i).collides(game.batch, player)==false){
                obstacles.get(i).draw(game.batch, 0);
            }
            else{
                obstacles.get(i).draw(game.batch, 0);
                if(obstacles.get(i).power == "HostileWreck" && obstacles.get(i).hostile == true){
                    player.takeInstantDamage(50);
                    obstacles.get(i).setHostility(false);
                }
                else if(obstacles.get(i).power == "DeadWater" && obstacles.get(i).hostile == true){
                    player.slowDown(25);
                    obstacles.get(i).setHostility(false);
                }
                
            }
        } 

        // Draw Player, Player Health and Player Name
        if(!isPaused) {
            player.drawHealthBar(game.batch);
            player.draw(game.batch, elapsedTime);
            HUDBatch.begin();
            Vector3 pos = game.camera.project(new Vector3(player.x, player.y, 0f));
            game.font.draw(HUDBatch, playerName, pos.x, pos.y + 170f, 1f, Align.center, true);
            HUDBatch.end();
        }


        // Draw Colleges
        for(int i = 0; i < colleges.size; i++) {
            colleges.get(i).draw(game.batch, 0);

        }


        // Draw boats
        for (int i = 0; i < boats.size; i++) {
          boats.get(i).draw(game.batch, 0);
          if(boats.get(i).currentHealth>0){
            game.batch.draw(boatHealthBar, boats.get(i).x-16, boats.get(i).y+10, boats.get(i).currentHealth/100*32, 2);
          }
          else{assert true;}
        }
   



        fog.setX(player.x-(fog.getWidth()/2));
        fog.setY(player.y-(fog.getHeight()/2));

        if(fogDecider == 0){
            fogDecider = MathUtils.random(30,60);
        }
        else{assert true;}

        if(Instant.now().getEpochSecond() - startTimeStamp > 10 && difficulty>1){
            fog.draw(game.batch, 1f);
            if(counterStarted==false){
                fogCounter = Instant.now().getEpochSecond();
                counterStarted = true;
                fogLengthDecider = MathUtils.random(15,30);
            }
            else{
                if( Instant.now().getEpochSecond() - fogCounter > fogLengthDecider){
                    startTimeStamp = Instant.now().getEpochSecond();
                    fog.setFlip(true, false);
                    counterStarted = false;
                    fogDecider = 0;
                    fogLengthDecider = 0;
                }
                else{assert true;}
            }
        }
        else{assert true;}

        game.batch.end();


        // Draw HUD
        HUDBatch.setProjectionMatrix(HUDCam.combined);
        if(!isPaused) {
            // Draw UI
            gameHUD.renderStage(this);
            HUDCam.update();
        }
    }

    /**
     * Is called once every time the window is resized. Sets the camera to the new resolution.
     * @param width The new width of the screen in pixels
     * @param delta The new height of the screen in pixels
     */
    @Override
    public void resize(int width,int height){
      HUDCam.setToOrtho(false, width, height);
    }

    /**
     * Is called once every frame. Used for game calculations that take place before rendering.
     */
    private void update(){

        // Check for user input for movement
        // Get input movement
        int horizontal = ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ? 1 : 0)
                - ((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) ? 1 : 0);
        int vertical = ((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) ? 1 : 0)
                - ((Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) ? 1 : 0);

        // Call updates for all relevant objects
        player.update(this, game.camera, horizontal, vertical);
        for(int i = 0; i < colleges.size; i++) {
            colleges.get(i).update(this,Gdx.input.isKeyPressed(Input.Keys.ENTER));
        }

        for(int i = 0; i < boats.size; i++) {
            boats.get(i).update(this,boats);
        }

        // Check for projectile creation, then call projectile update
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            Vector3 mouseVector = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
            Vector3 mousePos = game.camera.unproject(mouseVector);

            Array<Texture> sprites = new Array<>();
            sprites.add(new Texture("tempProjectile.png"));
            projectiles.add(new Projectile(sprites, 0, player, mousePos.x, mousePos.y, playerTeam));
            gameHUD.endTutorial();
        } for(int i = projectiles.size - 1; i >= 0; i--) {
            projectiles.get(i).update(this);
        }

        // Camera calculations based on player movement
        if(followPlayer) followPos = new Vector3(player.x, player.y, 0);
        if(Math.abs(game.camera.position.x - followPos.x) > 1f || Math.abs(game.camera.position.y - followPos.y) > 1f){
            game.camera.position.slerp(followPos, 0.1f);
        }

        // Call to pause the game
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && elapsedTime - lastPause > 0.1f){
            gamePause();
        }
    }

    /**
     * Called to switch from the current screen to the pause screen, while retaining the current screen's information.
     */
    public void gamePause(){
        isPaused = true;
        game.setScreen(new PauseScreen(game,this));
    }

    /**
     * Called to switch from the current screen to the end screen.
     * @param win   The boolean determining the win state of the game.
     */
    public void gameEnd(boolean win){
        game.setScreen(new EndScreen(game, this, win));
    }

    /**
     * Called to switch from the current screen to the title screen.
     */
    public void gameReset(){
        game.setScreen(new TitleScreen(game));
    }

    public void gameShop(){
        game.setScreen(new ShopScreen(game, this));
    }

    public void gameSave(){
        game.setScreen(new SaveScreen(game,this));
    }

    public void gameDifficulty(){
        game.setScreen(new DifficultyScreen(game, this));
    }
    
    public void gameTips(){
        game.setScreen(new TipsScreen(game, this));
    }
    /**
     * Used to encapsulate elapsedTime.
     * @return  Time since the current session started.
     */
    public float getElapsedTime() { return elapsedTime; }

    /**
     * Used to toggle whether the camera follows the player.
     * @param follow  Whether the camera will follow the player.
     */
    public void toggleFollowPlayer(boolean follow) { this.followPlayer = follow; }

    /**
     * Get the player's name for the current session.
     * @return  Player's name.
     */
    public String getPlayerName() { return playerName; }

    /**
     * Set the player's name.
     * @param playerName    Chosen player name.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        gameHUD.updateName(this);
    }

    /**
     * Get the player.
     * @return  The player.
     */
    public Player getPlayer() { return player; }

    /**
     * Get the main game class.
     * @return  The main game class.
     */
    public YorkPirates getMain() { return game; }

    /**
     * Get the game's HUD.
     * @return  The HUD.
     */
    public HUD getHUD() { return gameHUD; }

    /**
     * Set whether the game is paused or not.
     * @param paused    Whether the game is paused.
     */
    public void setPaused(boolean paused) {
        if (!paused && isPaused) lastPause = elapsedTime;
        isPaused = paused;
    }

    /**
     * Gets whether the game is paused.
     * @return  True if the game is paused.
     */
    public boolean isPaused() { return  isPaused; }

    /**
     * Get the viewport.
     * @return  The viewport.
     */
    public FitViewport getViewport() { return viewport; }

    /**
     * Disposes of disposables when game finishes execution.
     */
    @Override
    public void dispose(){
        HUDBatch.dispose();
        tiledMap.dispose();
        music.dispose();
    }

    public void setDifficulty(int level){
        difficulty = level;
    }

    public int getDifficulty(){
        return difficulty;
    }

    public JSONObject saveScreen(){
      JSONObject savedScreen = new JSONObject();
      savedScreen.put("difficulty", difficulty);
      savedScreen.put("points", points.Get());
      savedScreen.put("loot", loot.Get());
      savedScreen.put("fogDecider",fogDecider);
      savedScreen.put("fogCounter",fogCounter);
      savedScreen.put("fogLengthDecider",fogLengthDecider);
      savedScreen.put("counterStarted", counterStarted);
      savedScreen.put("startTimeStamp", startTimeStamp);
      savedScreen.put("elapsedTime", elapsedTime);
      return savedScreen;
    }

    public void loadSave(JSONObject savedScreen){
      difficulty = (int) ((long) savedScreen.get("difficulty"));
      points.Set(((int) ((long) savedScreen.get("points"))));
      loot.Set((int) ((long) savedScreen.get("loot")));
      fogDecider = (int) ((long) savedScreen.get("fogDecider"));
      fogCounter = (long) savedScreen.get("fogCounter");
      fogLengthDecider = (int) ((long) savedScreen.get("fogLengthDecider"));
      counterStarted = (Boolean) savedScreen.get("counterStarted");
      startTimeStamp = (long) savedScreen.get("startTimeStamp");
      elapsedTime = ((Double) savedScreen.get("elapsedTime")).floatValue();
    }
}
