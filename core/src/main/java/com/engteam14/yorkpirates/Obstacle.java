package com.engteam14.yorkpirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;


public class Obstacle extends GameObject {

    private Player player;
    public Array<Texture> frame;
    public Boolean collision = false;
    public String power;
    public Boolean hostile;

    public Obstacle(Array<Texture> frames, float fps, float x, float y, float width, float height, String team, String power) {
        super(frames, fps, x, y, width, height, team);
        // dead water
        // hostile wreck
        this.hostile = true;
        this.power = power;
        frame = new Array<>();
        for(int i = 0; i < frames.size; i++) {
            frame.add(frames.get(i));
        }
        

    }

    @Override
    public void draw(SpriteBatch batch, float elapsedTime){
        batch.draw(frame.get(0), this.x, this.y, this.width, this.height);
    }
    public Boolean collides(SpriteBatch batch, Player player){
        if(overlaps(player.hitBox)){
            return true;
        }
        else{return false;}

    }
    public Boolean getCollision(){
        return collision;
    }

    public void setHostility(Boolean hostility){
        this.hostile = hostility;
    }
}
