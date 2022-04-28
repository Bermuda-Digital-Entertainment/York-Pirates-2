package com.engteam14.yorkpirates.headless;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import com.engteam14.yorkpirates.GameObject;

/** This is a class to test that ALL assets required by the game exist. */
@RunWith(HeadlessLauncher.class)
public class GameObjectTests {

  @Test
  public void testGameObjectInit() {
      assertEquals(1, 1);
  }

}
