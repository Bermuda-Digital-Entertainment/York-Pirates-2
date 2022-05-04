package com.engteam14.yorkpirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Creates the Power ups
 */
public class PowerUp extends GameObject {

    private Player player;
    public Array<Texture> frame;
    public Boolean collision = false;
    public String power;

    /**
     * Initialises the Power up
     * @param frames    Sprites to be used for the PowerUp object
     * @param fps       How many frames of animation
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param width     Width of the ingame sprite
     * @param height    Height of the ingame sprite
     * @param team      What team it belongs to
     * @param power     What effect it has on the player. Immunity, MaxHealth, Heal, DamageBoost, Speed
     */
    public PowerUp(Array<Texture> frames, float fps, float x, float y, float width, float height, String team, String power) {
        super(frames, fps, x, y, width, height, team);
        this.power = power;
        frame = new Array<>();
        for(int i = 0; i < frames.size; i++) {
            frame.add(frames.get(i));
        }
        

    }
    /**
     * Called to draw the object
     * @param batch        SpriteBatch the PowerUp will be drawn on
     * @param elapsedTime
     */
    @Override
    public void draw(SpriteBatch batch, float elapsedTime){
        batch.draw(frame.get(0), this.x, this.y, this.width, this.height);
    }

    /**
     * Checks for collisions with the player
     * @param batch     What SpriteBatch collisions are checked on
     * @param player    The Player object that collisions are checked ith
     * @return          Whether collision occured
     */
    public Boolean collides(SpriteBatch batch, Player player){
        if(overlaps(player.hitBox)){
            return true;
        }
        else{return false;}

    }

    /** Checks the collision Boolean */
    public Boolean getCollision(){
        return collision;
    }
}
