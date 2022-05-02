package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import org.json.simple.JSONObject;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.Objects;

public class Boat extends GameObject {

  protected Player player;

  protected int speed;

  protected Float timeLastShot;     //Time since last shot
  protected Float timeBetweenShots; //Minimum time between shots

  protected Float lastXMove;
  protected Float lastYMove;

  public Boat(Array<Texture> frames, float x, float y, float scale, String team, Player player){
    super(frames, 0, x, y, frames.get(0).getWidth()*scale, frames.get(0).getHeight()*scale, team);
    this.player=player;
    this.currentHealth=50;
    this.maxHealth=50;
    this.timeLastShot=0f;
    this.timeBetweenShots=2f;
    this.speed=50;
    this.lastXMove=0f;
    this.lastYMove=0f;
  }


  public void update(GameScreen screen, Array<Boat> otherShips){
    if (currentHealth>0){
      shoot(screen);
      followPlayer();
      colliders(screen,otherShips);
    }
    updateHitboxPos();
  }

  public void followPlayer(){
    Float xDir, yDir, distence;
    if (nearPlayer()){
      xDir=this.x-player.x;
      yDir=this.y-player.y;
      distence = (float) sqrt(xDir*xDir + yDir*yDir);
      this.lastXMove=0-(xDir/distence * speed);
      this.lastYMove=0-(yDir/distence * speed);
      move(0-(xDir/distence * speed), 0-(yDir/distence * speed));

    }
  }
  //public void followPlayer(){
    //if (nearPlayer){
      //move(){
    //}
  //}

  public void shoot(GameScreen screen){

    if (this.timeLastShot >= this.timeBetweenShots && nearPlayer()){
      Array<Texture> sprite = new Array<>();
      sprite.add(new Texture("tempProjectile.png"));
      screen.projectiles.add(new Projectile(sprite, 0, this, this.player.x, this.player.y, team));
      this.timeLastShot=0f;
    }
    else {
      this.timeLastShot = Gdx.graphics.getDeltaTime() + this.timeLastShot;
    }
  }

  /**
   *  Works out if the boat is in a position that it is supposed to be in and moves out of it if not.
   *  @param screen The main GameScreen
   *  @param otherShips An array of all the ships in the game
   */
  public void colliders(GameScreen screen, Array<Boat> otherShips){
    Boolean overlap = false;
    for(int i = 0; i < otherShips.size; i++) {
        if (overlaps(otherShips.get(i).hitBox)){
            if(!Objects.equals(this, otherShips.get(i))){ // Checks two ships overlap
                overlap = true;
            }
        }
    }
    if (overlaps(this.player.hitBox))  overlap=true;
    // if (this.safeMove(screen.getMain().edges)) {
    if (overlap == true || !this.safeMove(screen.getMain().edges)){
      move(0-this.lastXMove, 0-this.lastYMove);
    }
  }

  /**
   *  Calculate if the current boat position is safe to be in.
   * @param edges A 2d array containing safe/unsafe positions to be in.
   * @return      If the current position is safe.
   */
  protected Boolean safeMove(Array<Array<Boolean>> edges){
      return (
                      edges.get((int)((y+height/2)/16)).get((int)((x+width/2)/16)) &&
                      edges.get((int)((y+height/2)/16)).get((int)((x-width/2)/16)) &&
                      edges.get((int)((y-height/2)/16)).get((int)((x+width/2)/16)) &&
                      edges.get((int)((y-height/2)/16)).get((int)((x-width/2)/16))
      );
  }

  public Boolean nearPlayer(){
    return abs(this.x - player.x) < (Gdx.graphics.getWidth()/5f) && abs(this.y - player.y) < (Gdx.graphics.getHeight()/5f);
  }

  public void takeDamage(float damage){
    currentHealth-=damage;
  }

  @Override
  public void genSave(){
    super.genSave();
    objectJSON.put("speed", speed);
    objectJSON.put("timeLastShot", timeLastShot);
    objectJSON.put("timeBetweenShots", timeBetweenShots);
  }

  @Override
  public void loadSave(JSONObject objectJSON){
    super.loadSave(objectJSON);
    objectJSON.get("speed");
    objectJSON.get("timeLastShot");
    objectJSON.get("timeBetweenShots");
  }
}
