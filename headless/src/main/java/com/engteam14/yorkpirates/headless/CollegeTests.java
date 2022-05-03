package com.engteam14.yorkpirates.headless;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import com.engteam14.yorkpirates.College;

/** This is a class to test that ALL assets required by the game exist. */
@RunWith(HeadlessLauncher.class)
public class CollegeTests {

  @Test
  public void testCollegeInit() throws Exception {
    Array<Texture>testTexture = new Array<>();
    College testObject;
    Player player = new Player();
    testTexture.add(new Texture("alcuin_2.png"));
    testObject = new College(testTexture, 12, 10, 1, 0, 0, "red");
  }
}
