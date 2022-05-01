package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import org.json.simple.JSONObject;

public class GameObject {

    public float x;
    public float y;
    public float width;
    public float height;

    public int maxHealth;
    public float currentHealth;

    public JSONObject objectJSON;
    String team;
    Texture sprite;
    Rectangle hitBox;
    Animation<Texture> anim;

    //ShaderProgram shader = new ShaderProgram(Gdx.files.internal("red.vsh"), Gdx.files.internal("red.fsh"));

    /**
     * Generates a generic object within the game with animated frame(s) and a hit-box.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param width     The size of the object in the x-axis.
     * @param height    The size of the object in the y-axis.
     * @param team      The team the object is on.
     */
    public GameObject(Array<Texture> frames, float fps, float x, float y, float width, float height, String team){
        changeImage(frames,fps);
        this.x = x;
        this.y = y;
        this.team = team;
        this.width = width;
        this.height = height;
        setHitbox();
    }

    /**
     * Called when the image needs to be changed or set.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     */
    public void changeImage(Array<Texture> frames, float fps){
        sprite = frames.get(0);
        anim = new Animation<>(fps==0?0:(1f/fps), frames);
    }

    /**
     * Called when the health of the object needs to be set.
     * @param mh    The health value for the object
     */
    public void setMaxHealth(int mh){
        maxHealth = mh;
        currentHealth = maxHealth;
    }

    /**
     * Called when a projectile hits the object.
     * @param screen            The main game screen.
     * @param damage            The damage dealt by the projectile.
     * @param projectileTeam    The team of the projectile.
     */
    public void takeDamage(GameScreen screen, float damage, String projectileTeam){
        currentHealth -= damage;
    }

    /**
     * Moves the object within the x and y-axis of the game world.
     * @param x     The amount to move the object within the x-axis.
     * @param y     The amount to move the object within the y-axis.
     */
    public void move(float x, float y){
        this.x += x * Gdx.graphics.getDeltaTime();
        this.y += y * Gdx.graphics.getDeltaTime();
    }

    /**
     * Sets the object's hit-box, based upon it's x, y, width and height values.
     */
    protected void setHitbox(){
        hitBox = new Rectangle();
        updateHitboxPos();
        hitBox.width = width;
        hitBox.height = height;
    }

    /**
     * Updates the object's hit-box location to match the object's rendered location.
     */
    public void updateHitboxPos() {
        hitBox.x = x - width/2;
        hitBox.y = y - height/2;
    }

    /**
     * Checks if this object overlaps with another.
     * @param rect  The other object to be checked against.
     * @return      True if overlapping, false otherwise.
     */
    public boolean overlaps(Rectangle rect){
        updateHitboxPos();
        return hitBox.overlaps(rect);
    }

    /**
     * Called when drawing the object.
     * @param batch         The batch to draw the object within.
     * @param elapsedTime   The current time the game has been running for.
     */
    public void draw(SpriteBatch batch, float elapsedTime){
        batch.draw(anim.getKeyFrame(elapsedTime, true), x - width/2, y - height/2, width, height);
    }

    /**
     * Generates a part of the core JSON file to store an object
     */
    public void genSave(){
      objectJSON = new JSONObject();
      objectJSON.put("x",x);
      objectJSON.put("y",y);
      objectJSON.put("maxHealth",maxHealth);
      objectJSON.put("currentHealth",currentHealth);
      objectJSON.put("team", team);
    }

    /**
     * Returns JSON object for the savegame.
     */
    public JSONObject returnSave(){
      return objectJSON;
    }

    /**
     * Returns a string of the JSON object for the savegame.
     */
    public String returnSaveString(){
      return objectJSON.toString();
    }
}
