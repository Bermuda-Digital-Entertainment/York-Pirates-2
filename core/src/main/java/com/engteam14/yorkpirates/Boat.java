package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Boat extends GameObject {

  protected Player player;

  protected int speed;

  protected Float timeLastShot;     //Time since last shot
  protected Float timeBetweenShots; //Minimum time between shots

  public Boat(Array<Texture> frames, float x, float y, float scale, String team, Player player){
    super(frames, 0, x, y, frames.get(0).getWidth()*scale, frames.get(0).getHeight()*scale, team);
    this.player=player;
    this.currentHealth=50;
    this.maxHealth=50;
    this.timeLastShot=0f;
    this.timeBetweenShots=2f;
    this.speed=50;
  }

  public void update(GameScreen screen, Array<Boat> otherShips){
    shoot(screen);
    followPlayer();
    setHitbox();
  }

  public void followPlayer(){
    Float xDir, yDir, distence;
    if (nearPlayer()){
      xDir=this.x-player.x;
      yDir=this.y-player.y;
      distence = (float) sqrt(xDir*xDir + yDir*yDir);
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

  public Boolean nearPlayer(){
    return abs(this.x - player.x) < (Gdx.graphics.getWidth()/5f) && abs(this.y - player.y) < (Gdx.graphics.getHeight()/5f);
  }

  public void takeDamage(float damage){
    currentHealth-=damage;
  }
}
