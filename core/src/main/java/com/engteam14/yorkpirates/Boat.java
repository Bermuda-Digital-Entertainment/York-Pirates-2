package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Boat extends GameObject {

  Player player;

  public Boat(Array<Texture> frames, float x, float y, float scale, String team, Player player){
    super(frames, 0, x, y, frames.get(0).getWidth()*scale, frames.get(0).getHeight()*scale, team);
    this.player=player;
  }

  public void update(GameScreen screen, Array<Boat> otherShips, int idNum){}
}
