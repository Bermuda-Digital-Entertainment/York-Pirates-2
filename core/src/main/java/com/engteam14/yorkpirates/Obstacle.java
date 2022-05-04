package com.engteam14.yorkpirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Class Obstacle for implementing Obstacles in thegame
 */
public class Obstacle extends GameObject {

    private Player player;
    public Array<Texture> frame;
    public Boolean collision = false;
    public String power;
    public Boolean hostile;

    /**
     * Initializes the Obstacle
     * @param frames    List of sprites for the Obstacle
     * @param fps       Number of different frames for animation
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param width     Width of the in game graphic
     * @param height    Height of the in game graphic
     * @param team      Which team it belongs to
     * @param power     What effect the Obstacle has
     */
    public Obstacle(Array<Texture> frames, float fps, float x, float y, float width, float height, String team, String power) {
        super(frames, fps, x, y, width, height, team);
        this.hostile = true;
        this.power = power;
        frame = new Array<>();
        for(int i = 0; i < frames.size; i++) {
            frame.add(frames.get(i));
        }
        

    }

    /**
     * Draws the Obstacle on the game screen
     * @param batch     the SpriteBatch on which the Obstacle will be drawn on
     * @param elapsedTime
     */
    @Override
    public void draw(SpriteBatch batch, float elapsedTime){
        batch.draw(frame.get(0), this.x, this.y, this.width, this.height);
    }

    /**
     * Checks for collision with player
     * @param batch     What SpriteBatch to check collisions on for
     * @param player    Player with whom Obstacle may collide
     * @return          Whether collision happened
     */
    public Boolean collides(SpriteBatch batch, Player player){
        if(overlaps(player.hitBox)){
            return true;
        }
        else{return false;}
    }

    /**
     * Checks the value of collision Boolean
     * @return      collision Boolean
     */
    public Boolean getCollision(){
        return collision;
    }

    /**
     * Set whether the Obstacle will have an effect on the player
     * @param hostility     True - next collision has effect. False - no effect on collision
     */
    public void setHostility(Boolean hostility){
        this.hostile = hostility;
    }
}
