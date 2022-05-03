package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import org.json.simple.JSONObject;

public class Player extends GameObject {

    // Player constants
    private static final int POINT_FREQUENCY = 1000; // How often the player gains points by moving.
    private static final float CAMERA_SLACK = 0.01f; // What percentage of the screen the player can move in before the camera follows.
    private static float SPEED =70f; // Player movement speed.
    private static int HEALTH = 200;
    private static float damage = 50f;

    // Movement calculation values
    private int previousDirectionX;
    private int previousDirectionY;
    private float distance;
    private long lastMovementScore;

    private HealthBar playerHealth;
    private float splashTime;
    private long timeLastHit;
    private boolean doBloodSplash = false;

    /**
     * Generates a generic object within the game with animated frame(s) and a hit-box.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param width     The size of the object in the x-axis.
     * @param height    The size of the object in the y-axis.
     * @param team      The team the player is on.
     */
    public Player(Array<Texture> frames, float fps, float x, float y, float width, float height, String team){
        super(frames, fps, x, y, width, height, team);
        lastMovementScore = 0;
        splashTime = 0;

        // Generate health
        Array<Texture> sprites = new Array<>();
        sprites.add(new Texture("allyHealthBar.png"));
        setMaxHealth(HEALTH);
        playerHealth = new HealthBar(this,sprites);
    }

    /**
     * Called once per frame. Used to perform calculations such as player/camera movement.
     * @param screen    The main game screen.
     * @param camera    The player camera.
     */
    public void update(GameScreen screen, OrthographicCamera camera, int horizontal, int vertical){
        Vector2 oldPos = new Vector2(x,y); // Stored for next-frame calculations
        Boolean overlap = false;

        // Calculate collision && movement
        if (horizontal != 0 || vertical != 0){
            move(SPEED *horizontal, SPEED *vertical);
            for (int i=0; i < screen.boats.size; i++) {
              if (overlaps(screen.boats.get(i).hitBox)) overlap = true;
            }
            previousDirectionX = horizontal;
            previousDirectionY = vertical;
            if (overlap==true){ //Collision with enemy boat
              x = oldPos.x;
              y = oldPos.y;
            } else if (safeMove(screen.getMain().edges)) {
                if (TimeUtils.timeSinceMillis(lastMovementScore) > POINT_FREQUENCY) {
                    lastMovementScore = TimeUtils.millis();
                    screen.points.Add(1);
                }
            } else {    // Collision
                Vector2 newPos = new Vector2(x, y);
                x = oldPos.x;
                if (!safeMove(screen.getMain().edges) && overlap==false) {
                    x = newPos.x;
                    y = oldPos.y;
                    if (!safeMove(screen.getMain().edges) && overlap==false) {
                        x = oldPos.x;
                    }
                }
            }
        }
        updateHitboxPos();
        // Track distance travelled
        distance += Math.pow((Math.pow((x - oldPos.x),2f) + Math.pow((y - oldPos.y),2f)),0.5f)/10f;

        // Camera Calculations
        ProcessCamera(screen, camera);

        // Blood splash calculations
        if(doBloodSplash){
            if(splashTime > 1){
                doBloodSplash = false;
                splashTime = 0;
            }else{
                splashTime += 1;
            }
        }

        if (TimeUtils.timeSinceMillis(timeLastHit) > 10000){
            currentHealth += 0.03;
            if(currentHealth > maxHealth) currentHealth = maxHealth;
            playerHealth.resize(currentHealth);
        }
    }

    /**
     *  Calculate if the current player position is safe to be in.
     * @param edges A 2d array containing safe/unsafe positions to be in.
     * @return      If the current position is safe.
     */
    public Boolean safeMove(Array<Array<Boolean>> edges){
        return (
                        edges.get((int)((y+height/2)/16)).get((int)((x+width/2)/16)) &&
                        edges.get((int)((y+height/2)/16)).get((int)((x-width/2)/16)) &&
                        edges.get((int)((y-height/2)/16)).get((int)((x+width/2)/16)) &&
                        edges.get((int)((y-height/2)/16)).get((int)((x-width/2)/16))
        );
    }

    /**
     * Moves the player within the x and y-axis of the game world.
     * @param x     The amount to move the object within the x-axis.
     * @param y     The amount to move the object within the y-axis.
     */
    @Override
    public void move(float x, float y){
        this.x += x * Gdx.graphics.getDeltaTime();
        this.y += y * Gdx.graphics.getDeltaTime();
        playerHealth.move(this.x, this.y + height/2 + 2f); // Healthbar moves with player
    }

    /**
     * Called when a projectile hits the college.
     * @param screen            The main game screen.
     * @param damage            The damage dealt by the projectile.
     * @param projectileTeam    The team of the projectile.
     */
    public void takeDamage(GameScreen screen, float damage, String projectileTeam){
        if(screen.immunity == true){
            assert true;
        }
        else{
            timeLastHit = TimeUtils.millis();
            currentHealth -= damage;
            doBloodSplash = true;

            // Health-bar reduction
            if(currentHealth > 0){
                playerHealth.resize(currentHealth);
            }else{
                playerHealth = null;
                screen.gameEnd(false);
            }
        }
    }


    /**
     * Called after update(), calculates whether the camera should follow the player and passes it to the game screen.
     * @param screen    The main game screen.
     * @param camera    The player camera.
     */
    private void ProcessCamera(GameScreen screen, OrthographicCamera camera) {
        Vector2 camDiff = new Vector2(x - camera.position.x, y - camera.position.y);
        screen.toggleFollowPlayer(Math.abs(camDiff.x) > camera.viewportWidth / 2 * CAMERA_SLACK || Math.abs(camDiff.y) > camera.viewportWidth / 2 * CAMERA_SLACK);
    }

    /**
     * Called when drawing the player.
     * @param batch         The batch to draw the player within.
     * @param elapsedTime   The current time the game has been running for.
     */
    @Override
    public void draw(SpriteBatch batch, float elapsedTime){
        // Generates the sprite
        Texture frame = anim.getKeyFrame((currentHealth/maxHealth > 0.66f) ? 0 : ((currentHealth/maxHealth > 0.33f) ? 2 : 1), true);
        //if(doBloodSplash){
            //batch.setShader(shader); // Set our grey-out shader to the batch
        //}
        float rotation = (float) Math.toDegrees(Math.atan2(previousDirectionY, previousDirectionX));

        // Draws sprite and health-bar
        batch.draw(frame, x - width/2, y - height/2, width/2, height/2, width, height, 1f, 1f, rotation, 0, 0, frame.getWidth(), frame.getHeight(), false, false);
        batch.setShader(null);
    }

    /** Adds a health bar to SpriteBatch*/
    public void drawHealthBar(SpriteBatch batch){
        if(!(playerHealth == null)) playerHealth.draw(batch, 0);
    }

    /** Returns the distence*/
    public float getDistance() {
        return distance;
    }

    /** Adds health to the current health*/
    public void addHealth(){
        setMaxHealth(HEALTH+50);
        currentHealth += 50;
    }
    /** Heals the player */
    public void heal(int amount){
        if(currentHealth+amount < maxHealth){
        currentHealth += amount;
        }
        else{currentHealth = maxHealth;}
    }

    /** Returns the damage the a player generated projectile does*/
    public float getPlayerDamage(){
        return damage;
    }

    /** Adds to the that the player can do damage */
    public void addDamage(){
        damage+=50f;
    }
    public void setHealth(int amount){
        setMaxHealth(amount);
        currentHealth = amount;
    }

    public void takeInstantDamage(int amount){
        currentHealth = currentHealth - amount;
    }

    //** Adds to the speed of the player */
    public void speedUp(float amount){
        SPEED += amount;
    }

    public void slowDown(float amount){
        if(SPEED>30){
            SPEED -= amount;
        }
        else{assert true;}

    }
    public void setDamage(float amount){
        damage = amount;
    }

    @Override
    public void genSave(){
      super.genSave();
      objectJSON.put("HEALTH",HEALTH);
      objectJSON.put("damage",damage);
      objectJSON.put("previousDirectionX",previousDirectionX);
      objectJSON.put("previousDirectionY",previousDirectionY);
      objectJSON.put("distance",distance);
      objectJSON.put("lastMovementScore",lastMovementScore);
      objectJSON.put("splashTime",splashTime);
      objectJSON.put("timeLastHit",timeLastHit);
    }

    @Override
    public void loadSave(JSONObject objectJSON){
      super.loadSave(objectJSON);
      this.HEALTH = (int) ((long) objectJSON.get("HEALTH"));
      this.damage = ((Double) objectJSON.get("damage")).floatValue();
      this.previousDirectionX = (int) ((long) objectJSON.get("previousDirectionX"));
      this.previousDirectionY = (int) ((long) objectJSON.get("previousDirectionY"));
      this.distance = ((Double) objectJSON.get("distance")).floatValue();
      this.lastMovementScore = (int) ((long) objectJSON.get("lastMovementScore"));
      this.splashTime = ((Double) objectJSON.get("splashTime")).floatValue();
      this.timeLastHit = (long) objectJSON.get("timeLastHit");
    }
}
